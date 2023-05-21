package com.example.backend.utils.utilClasses;

import com.example.backend.entity.user.*;
import com.example.backend.exception.otherException.BatchImportUsersException;
import com.example.backend.exception.user.userException.UserNotExistException;
import com.example.backend.mapper.user.*;
import com.example.backend.utils.enumClasses.model.Role;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final IsEntityExists isEntityExists;

    private static final Logger logger = LoggerFactory.getLogger(BatchImportUsers.class);

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
                    logger.info("arrived administrator tag");
                    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                        // 从第二行开始读

                        Row row = sheet.getRow(i);

                        String[] strings = new String[3];
                        for (int j = 0; j < 3; j++) {
                            Cell cell = row.getCell(j);
                            cell.setCellType(CellType.STRING);
                            strings[j] = cell.getStringCellValue();
                        }

                        if (isEntityExists.isUserExistsByLoginID(strings[1])) {
                            throw new UserNotExistException("Excel第" + i + "条数据，由于loginID已存在，无法继续导入！");
                        }

                        User user = new User(null, strings[1], strings[2], null);
                        userMapper.insert(user);
                        Administrator administrator = new Administrator(user.getUserID(), strings[0]);
                        administratorMapper.insert(administrator);
                        UserRole userRole = new UserRole(null, user.getUserID(), Role.ROLE_ADMIN);
                        userRoleMapper.insert(userRole);
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

                        if (isEntityExists.isUserExistsByLoginID(strings[2])) {
                            throw new UserNotExistException("Excel第" + i + "条数据，由于loginID已存在，无法继续导入！");
                        }

                        User user = new User(null, strings[2], strings[3], null);
                        userMapper.insert(user);
                        if (userType.equals("Technician")) {
                            Technician technician = new Technician(user.getUserID(), strings[0], strings[1]);
                            technicianMapper.insert(technician);
                            UserRole userRole = new UserRole(null, user.getUserID(), Role.ROLE_TECHNICIAN);
                            userRoleMapper.insert(userRole);
                        } else {
                            Instructor instructor = new Instructor(user.getUserID(), strings[0], strings[1]);
                            instructorMapper.insert(instructor);
                            UserRole userRole = new UserRole(null, user.getUserID(), Role.ROLE_INSTRUCTOR);
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

                        if (isEntityExists.isUserExistsByLoginID(strings[3])) {
                            throw new UserNotExistException("Excel第" + i + "条数据，由于loginID已存在，无法继续导入！");
                        }

                        User user = new User(null, strings[3], strings[4], null);
                        userMapper.insert(user);
                        Student student = new Student(user.getUserID(), strings[0], strings[1], strings[2]);
                        studentMapper.insert(student);
                        UserRole userRole = new UserRole(null, user.getUserID(), Role.ROLE_STUDENT);
                        userRoleMapper.insert(userRole);
                    }
                }
                default -> System.out.println("用户类型不正确！");
            }
        } catch (Exception e) {
            throw new BatchImportUsersException("批量导入出错！");
        }
    }
}
