package com.example.backend.controller.user;

import com.example.backend.controller.requestbody.ResetPasswordRequestBody;
import com.example.backend.controller.requestbody.UserRequestBody;
import com.example.backend.controller.requestbody.UserRequestBodyForUpdate;
import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.controller.responsebody.UserData;
import com.example.backend.entity.user.Instructor;
import com.example.backend.entity.user.Student;
import com.example.backend.entity.user.Technician;
import com.example.backend.exception.uploaddownload.FileTypeNotSupportedException;
import com.example.backend.exception.user.userRequestException.MultipleRoleException;
import com.example.backend.exception.user.userRequestException.RoleSpecificInfoNotFoundException;
import com.example.backend.service.user.UserService;
import com.example.backend.utils.enumClasses.model.Role;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
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
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/technician")
    public GeneralFormattedResponseBody<Object>
    createTechnician(
            @RequestBody UserRequestBody technicianInfo,
            HttpServletResponse response
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
        response.setStatus(HttpServletResponse.SC_CREATED);
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
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/instructor")
    public GeneralFormattedResponseBody<Object>
    createInstructor(
            @RequestBody UserRequestBody instructorInfo,
            HttpServletResponse response
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
        response.setStatus(HttpServletResponse.SC_CREATED);
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
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/student")
    public GeneralFormattedResponseBody<Object>
    createStudent(
            @RequestBody UserRequestBody studentInfo,
            HttpServletResponse response
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
        response.setStatus(HttpServletResponse.SC_CREATED);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping
    public GeneralFormattedResponseBody<Object>
    deleteUser(@RequestParam Integer userID) {
        userService.deleteUser(userID);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(null)
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/password")
    public GeneralFormattedResponseBody<Object>
    resetPassword(@RequestBody ResetPasswordRequestBody resetPasswordRequestInfo) {
        userService.resetPassword(resetPasswordRequestInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(null)
                .build();
    }

    /**
     * 创建用户，可以是多角色用户
     * @param userInfo 带有想要的角色类型和对应的完整角色信息
     */
    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createUser(
            @RequestBody UserRequestBody userInfo,
            HttpServletResponse response
    ) {
        userService.checkRolesAndInfo(userInfo);
        userService.createUser(userInfo);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/technician")
    public GeneralFormattedResponseBody<Object>
    updateTechnician(@RequestBody UserRequestBodyForUpdate userUpdate) {
        if (!userUpdate.getRoles().contains(Role.ROLE_TECHNICIAN)) {
            throw new MultipleRoleException("该接口只接受实验员信息更改");
        }
        userService.updateTechnician(userUpdate);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(null)
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/instructor")
    public GeneralFormattedResponseBody<Object>
    updateInstructor(@RequestBody UserRequestBodyForUpdate userUpdate) {
        if (!userUpdate.getRoles().contains(Role.ROLE_INSTRUCTOR)) {
            throw new MultipleRoleException("该接口只接受教师信息更改");
        }
        userService.updateInstructor(userUpdate);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(null)
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/student")
    public GeneralFormattedResponseBody<Object>
    updateStudent(@RequestBody UserRequestBodyForUpdate userUpdate) {
        if (!userUpdate.getRoles().contains(Role.ROLE_STUDENT)) {
            throw new MultipleRoleException("该接口只接受学生信息更改");
        }
        userService.updateStudent(userUpdate);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(null)
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/technician/all")
    public GeneralFormattedResponseBody<List<UserData>>
    getAllTechnicians() {
        return GeneralFormattedResponseBody
                .<List<UserData>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getAllTechnicians())
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/instructor/all")
    public GeneralFormattedResponseBody<List<UserData>>
    getAllInstructors() {
        return GeneralFormattedResponseBody
                .<List<UserData>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getAllInstructors())
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/student/all")
    public GeneralFormattedResponseBody<List<UserData>>
    getAllStudents() {
        return GeneralFormattedResponseBody
                .<List<UserData>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getAllStudents())
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/technician/name")
    public GeneralFormattedResponseBody<List<UserData>>
    getTechniciansByName(@RequestParam String name) {
        return GeneralFormattedResponseBody
                .<List<UserData>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getTechniciansByName(name))
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/instructor/name")
    public GeneralFormattedResponseBody<List<UserData>>
    getInstructorsByName(@RequestParam String name) {
        return GeneralFormattedResponseBody
                .<List<UserData>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getInstructorsByName(name))
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/student/name")
    public GeneralFormattedResponseBody<List<UserData>>
    getStudentsByName(@RequestParam String name) {
        return GeneralFormattedResponseBody
                .<List<UserData>>builder()
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
    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GeneralFormattedResponseBody<Object>
    batchImport(@RequestParam(value="table", required = false) MultipartFile table,
                @RequestParam(value="usertype", required = false) String usertype,
                HttpServletResponse response)
            throws IOException {
        File t = multipartToFile(table);
        userService.batchImport(t, checkFileType(t), usertype);
        t.delete();
        response.setStatus(HttpServletResponse.SC_CREATED);
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

    @GetMapping
    public GeneralFormattedResponseBody<UserData>
    getUserByID(@RequestParam Integer userID) {
        return GeneralFormattedResponseBody
                .<UserData>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(userService.getUserByID(userID))
                .build();
    }
}
