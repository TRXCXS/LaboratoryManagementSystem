package com.example.backend.utils.utilClasses;

import com.example.backend.entity.user.*;
import com.example.backend.exception.otherException.BatchImportUsersException;
import com.example.backend.mapper.user.*;
import com.example.backend.utils.enumClasses.model.Role;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@AllArgsConstructor
public class BatchImportUsers {
    private static BatchImportUsers batchImportUsers;
    private final UserRoleMapper userRoleMapper;
    private final UserMapper userMapper;
    private final AdministratorMapper administratorMapper;
    private final TechnicianMapper technicianMapper;
    private final InstructorMapper instructorMapper;
    private final StudentMapper studentMapper;

    @PostConstruct
    public void init() {
        batchImportUsers = this;
    }

    public void readAndImport(InputStream inputStream, boolean isXls, String userType) {
        /**
         * inputStream:文件输入流
         * isXls:true--xls文件；false--xlsx文件
         * userType:字符串形式表示要批量导入的用户类型
         */

        /**
         * 文件说明：
         * ①一次只能导入一张表，即excel文件的第一张表
         * ②不同的用户批量导入，数据须按照不同的模板组织，见developFiles/excel文件模板.xlsx
         * ③从第二行开始读取数据
         */

        try {
            Workbook workbook = null;
            if (isXls) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                workbook = new XSSFWorkbook(inputStream);
            }

            Sheet sheet = workbook.getSheetAt(0);
            switch (userType) {
                case "Administrator" -> {
                    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                        Row row = sheet.getRow(i);

                        String[] strings = new String[3];
                        for (int j = 0; j < 3; j++) {
                            Cell cell = row.getCell(j);
                            cell.setCellType(CellType.STRING);
                            strings[j] = cell.getStringCellValue();
                        }

                        User user = new User(null, strings[1], strings[2], null);
                        userMapper.insert(user);
                        Administrator administrator = new Administrator(user.getUserID(), strings[0]);
                        administratorMapper.insert(administrator);

                        Cell cell = row.getCell(3);
                        cell.setCellType(CellType.NUMERIC);
                        int roleNumber = (int) cell.getNumericCellValue();
                        for (int k = 0; k < roleNumber; k++) {
                            Cell tempCell = row.getCell(4 + k);
                            tempCell.setCellType(CellType.STRING);
                            String role = tempCell.getStringCellValue();

                            UserRole userRole = new UserRole(null, user.getUserID(), Role.valueOf(role));
                            userRoleMapper.insert(userRole);
                        }
                    }
                }
                case "Technician", "Instructor" -> {
                    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                        Row row = sheet.getRow(i);

                        String[] strings = new String[4];
                        for (int j = 0; j < 4; j++) {
                            Cell cell = row.getCell(j);
                            cell.setCellType(CellType.STRING);
                            strings[j] = cell.getStringCellValue();
                        }

                        User user = new User(null, strings[2], strings[3], null);
                        userMapper.insert(user);
                        if (userType.equals("Technician")) {
                            Technician technician = new Technician(user.getUserID(), strings[0], strings[1]);
                            technicianMapper.insert(technician);
                        } else {
                            Instructor instructor = new Instructor(user.getUserID(), strings[0], strings[1]);
                            instructorMapper.insert(instructor);
                        }

                        Cell cell = row.getCell(4);
                        cell.setCellType(CellType.NUMERIC);
                        int roleNumber = (int) cell.getNumericCellValue();
                        for (int k = 0; k < roleNumber; k++) {
                            Cell tempCell = row.getCell(5 + k);
                            tempCell.setCellType(CellType.STRING);
                            String role = tempCell.getStringCellValue();

                            UserRole userRole = new UserRole(null, user.getUserID(), Role.valueOf(role));
                            userRoleMapper.insert(userRole);
                        }
                    }
                }
                case "Student" -> {
                    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                        Row row = sheet.getRow(i);

                        String[] strings = new String[5];
                        for (int j = 0; j < 5; j++) {
                            Cell cell = row.getCell(j);
                            cell.setCellType(CellType.STRING);
                            strings[j] = cell.getStringCellValue();
                        }

                        User user = new User(null, strings[3], strings[4], null);
                        userMapper.insert(user);
                        Student student = new Student(user.getUserID(), strings[0], strings[1], strings[2]);
                        studentMapper.insert(student);
                        UserRole userRole = new UserRole(null, user.getUserID(), Role.ROLE_STUDENT);
                        userRoleMapper.insert(userRole);
                    }
                }
            }
        } catch (Exception e) {
            throw new BatchImportUsersException("批量导入出错！");
        }
    }
}
