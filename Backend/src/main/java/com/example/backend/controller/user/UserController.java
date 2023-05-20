package com.example.backend.controller.user;

import com.example.backend.controller.requestbody.ResetPasswordRequestBody;
import com.example.backend.controller.requestbody.UserRequestBody;
import com.example.backend.controller.requestbody.UserRequestBodyForUpdate;
import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.user.Instructor;
import com.example.backend.entity.user.Student;
import com.example.backend.entity.user.Technician;
import com.example.backend.exception.uploaddownload.FileTypeNotSupportedException;
import com.example.backend.exception.user.userRequestException.MultipleRoleException;
import com.example.backend.exception.user.userRequestException.RoleSpecificInfoNotFoundException;
import com.example.backend.service.user.UserService;
import com.example.backend.utils.enumClasses.model.Role;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    /**
     * 创建实验员
     * 该接口只接受单角色实验员用户
     * 要求在角色特定信息roleSpecificInfo里填写name和title
     */
    @PostMapping("/technician")
    public GeneralFormattedResponseBody<Object>
    createTechnician(
            @RequestBody UserRequestBody technicianInfo
    ) {
        if (technicianInfo.getRoles().size() != 1
                || technicianInfo.getRoles().get(0) != Role.ROLE_TECHNICIAN) {
            throw new MultipleRoleException("该接口只接受单角色用户创建");
        }
        Map<String, String> roleSpecificInfo = technicianInfo.getRoleSpecificInfo();
        if (!roleSpecificInfo.containsKey("name")
                || !roleSpecificInfo.containsKey("title")) {
            throw new RoleSpecificInfoNotFoundException(
                    "创建角色实验员要求填写姓名和职称"
            );
        }
        userService.createTechnician(technicianInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    /**
     * 创建教师
     * 单角色用户创建
     * 要求在角色特定信息roleSpecificInfo里填写name和title
     */
    @PostMapping("/instructor")
    public GeneralFormattedResponseBody<Object>
    createInstructor(
            @RequestBody UserRequestBody instructorInfo
    ) {
        if (instructorInfo.getRoles().size() != 1
                || instructorInfo.getRoles().get(0) != Role.ROLE_INSTRUCTOR) {
            throw new MultipleRoleException("该接口只接受单角色用户创建");
        }
        Map<String, String> roleSpecificInfo = instructorInfo.getRoleSpecificInfo();
        if (!roleSpecificInfo.containsKey("name")
                || !roleSpecificInfo.containsKey("title")) {
            throw new RoleSpecificInfoNotFoundException(
                    "创建角色教师要求填写姓名和职称"
            );
        }
        userService.createInstructor(instructorInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    /**
     * 创建学生
     * 单角色用户创建
     * 要求在角色特定信息（roleSpecificInfo）里填写
     * name和major和clazz
     */
    @PostMapping("/student")
    public GeneralFormattedResponseBody<Object>
    createStudent(
            @RequestBody UserRequestBody studentInfo
    ) {
        if (studentInfo.getRoles().size() != 1
                || studentInfo.getRoles().get(0) != Role.ROLE_STUDENT) {
            throw new MultipleRoleException("该接口只接受单角色用户创建");
        }
        Map<String, String> roleSpecificInfo = studentInfo.getRoleSpecificInfo();
        if (!roleSpecificInfo.containsKey("name")
                || !roleSpecificInfo.containsKey("major")
                || !roleSpecificInfo.containsKey("clazz")
        ) {
            throw new RoleSpecificInfoNotFoundException(
                    "创建角色学生要求填写姓名、专业和班级"
            );
        }
        userService.createStudent(studentInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }


    @DeleteMapping
    public GeneralFormattedResponseBody<Object>
    deleteUser(@RequestParam Integer userID) {
        userService.deleteUser(userID);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

    @PutMapping("/password")
    public GeneralFormattedResponseBody<Object>
    resetPassword(@RequestBody ResetPasswordRequestBody resetPasswordRequestInfo) {
        userService.resetPassword(resetPasswordRequestInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

    /**
     * 创建用户，可以是多角色用户
     * @param userInfo 带有想要的角色类型和对应的完整角色信息
     */
    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createUser(
            @RequestBody UserRequestBody userInfo
    ) {
        userService.checkRolesAndInfo(userInfo);
        userService.createUser(userInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    @PutMapping("/technician")
    public GeneralFormattedResponseBody<Object>
    updateTechnician(UserRequestBodyForUpdate userUpdate) {
        if (!userUpdate.getRoles().contains(Role.ROLE_TECHNICIAN)) {
            throw new MultipleRoleException("该接口只接受实验员信息更改");
        }
        userService.updateTechnician(userUpdate);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

    @PutMapping("/instructor")
    public GeneralFormattedResponseBody<Object>
    updateInstructor(UserRequestBodyForUpdate userUpdate) {
        if (!userUpdate.getRoles().contains(Role.ROLE_INSTRUCTOR)) {
            throw new MultipleRoleException("该接口只接受教师信息更改");
        }
        userService.updateInstructor(userUpdate);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

    @PutMapping("/student")
    public GeneralFormattedResponseBody<Object>
    updateStudent(UserRequestBodyForUpdate userUpdate) {
        if (!userUpdate.getRoles().contains(Role.ROLE_STUDENT)) {
            throw new MultipleRoleException("该接口只接受学生信息更改");
        }
        userService.updateInstructor(userUpdate);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

    @GetMapping("/technician/all")
    public GeneralFormattedResponseBody<List<Technician>>
    getAllTechnicians() {
        return GeneralFormattedResponseBody
                .<List<Technician>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getAllTechnicians())
                .build();
    }

    @GetMapping("/instructor/all")
    public GeneralFormattedResponseBody<List<Instructor>>
    getAllInstructors() {
        return GeneralFormattedResponseBody
                .<List<Instructor>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getAllInstructors())
                .build();
    }


    @GetMapping("/student/all")
    public GeneralFormattedResponseBody<List<Student>>
    getAllStudents() {
        return GeneralFormattedResponseBody
                .<List<Student>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getAllStudents())
                .build();
    }


    @GetMapping("/technician/name")
    public GeneralFormattedResponseBody<List<Technician>>
    getTechniciansByName(@RequestParam String name) {
        return GeneralFormattedResponseBody
                .<List<Technician>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getTechniciansByName(name))
                .build();
    }

    @GetMapping("/instructor/name")
    public GeneralFormattedResponseBody<List<Instructor>>
    getInstructorsByName(@RequestParam String name) {
        return GeneralFormattedResponseBody
                .<List<Instructor>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getInstructorsByName(name))
                .build();
    }

    @GetMapping("/student/name")
    public GeneralFormattedResponseBody<List<Student>>
    getStudentsByName(@RequestParam String name) {
        return GeneralFormattedResponseBody
                .<List<Student>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getStudentsByName(name))
                .build();
    }

    /**
     * 用户批量导入
     * 注意使用form-data请求进行调用
     * com.example.backend.utils.utilClasses.BatchImportUsers 在这里确定要输入什么字符串
     *
     * @param table 1个.xls或者.xlsx文件
     * @param usertype 导入什么类型的用户
     */
    @PostMapping("/batch")
    public GeneralFormattedResponseBody<Object>
    batchImport(@RequestParam(name="table", required = false) MultipartFile table,
                @RequestParam(name="usertype", required = false) String usertype)
            throws IOException {
        File t = multipartToFile(table);
        userService.batchImport(t, checkFileType(t), usertype);
        t.delete();
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    private @NotNull File multipartToFile(@NotNull MultipartFile m) throws IOException {
        InputStream is = m.getInputStream();
        File f = new File(m.getOriginalFilename());

        OutputStream os = new FileOutputStream(f);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();

        is.close();
        return f;
    }

    private boolean checkFileType(@NotNull File file) {
        Path path = file.toPath();
        boolean ret = true;
        try {
            String fileType = Files.probeContentType(path);
            System.out.println(file.exists());
            System.out.println(file.length());
            System.out.println("path: " + path.toString());
            System.out.println("File type: " + fileType);
            if(!fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    && !fileType.equals("application/vnd.ms-excel")) {
                throw new FileTypeNotSupportedException("文件类型不受支持");
            }
            if(fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                ret = false;
            }
        } catch (IOException e) {
            System.err.println("Error reading file type: " + e.getMessage());
        }
        return ret;
    }
}
