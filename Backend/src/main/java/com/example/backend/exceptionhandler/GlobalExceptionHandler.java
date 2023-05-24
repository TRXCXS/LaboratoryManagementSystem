package com.example.backend.exceptionhandler;

import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.exception.arrangement.longArrangementException.LongArrangementHasExistedException;
import com.example.backend.exception.arrangement.longArrangementException.LongArrangementNotExistsException;
import com.example.backend.exception.arrangement.shortArrangementException.ShortArrangementHasExistedException;
import com.example.backend.exception.arrangement.shortArrangementException.ShortArrangementNotExistsException;
import com.example.backend.exception.enumException.*;
import com.example.backend.exception.model.ResponsibleForException.ResponsibleForHasExistedException;
import com.example.backend.exception.model.ResponsibleForException.ResponsibleForNotExistedException;
import com.example.backend.exception.model.laboratoryException.LabIsRepairingException;
import com.example.backend.exception.model.laboratoryException.LaboratoryHasBeenArrangedException;
import com.example.backend.exception.model.laboratoryException.LaboratoryNotExistedException;
import com.example.backend.exception.model.semesterException.SemesterHasExistedException;
import com.example.backend.exception.model.semesterException.SemesterNotExistedException;
import com.example.backend.exception.model.semesterException.SetCurrentSemesterException;
import com.example.backend.exception.otherException.BatchImportUsersException;
import com.example.backend.exception.otherException.NumberIllegalException;
import com.example.backend.exception.request.instructorRequestException.InstructorRequestNotExistException;
import com.example.backend.exception.request.repairRequestException.RepairRequestHasExistedException;
import com.example.backend.exception.request.repairRequestException.RepairRequestNotExistException;
import com.example.backend.exception.request.studentRequestException.AdminMessageNullException;
import com.example.backend.exception.request.studentRequestException.StudentRequestHasExistedException;
import com.example.backend.exception.request.studentRequestException.StudentRequestNotExistException;
import com.example.backend.exception.uploaddownload.FileTypeNotSupportedException;
import com.example.backend.exception.user.administratorException.AdministratorHasExistedException;
import com.example.backend.exception.user.administratorException.AdministratorNotExistException;
import com.example.backend.exception.user.instructorException.InstructorHasExistedException;
import com.example.backend.exception.user.instructorException.InstructorNotExistException;
import com.example.backend.exception.user.studentException.StudentCountIllegalException;
import com.example.backend.exception.user.studentException.StudentHasExistedException;
import com.example.backend.exception.user.studentException.StudentNotExistException;
import com.example.backend.exception.user.technicianException.TechnicianHasExistedException;
import com.example.backend.exception.user.technicianException.TechnicianNotExistException;
import com.example.backend.exception.user.userException.*;
import com.example.backend.exception.user.userRequestException.MultipleRoleException;
import com.example.backend.exception.user.userRequestException.RoleSpecificInfoNotFoundException;
import com.example.backend.exception.user.userRoleException.UserRoleHasExistedException;
import com.example.backend.exception.user.userRoleException.UserRoleNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.TooManyListenersException;

@ControllerAdvice
public class GlobalExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            LongArrangementHasExistedException.class,
            ShortArrangementHasExistedException.class,
            LabIsRepairingException.class,
            LaboratoryHasBeenArrangedException.class,
            SemesterHasExistedException.class,
            ResponsibleForHasExistedException.class,
            InstructorHasExistedException.class,
            RepairRequestHasExistedException.class,
            StudentRequestHasExistedException.class,
            AdministratorHasExistedException.class,
            InstructorHasExistedException.class,
            StudentHasExistedException.class,
            TechnicianHasExistedException.class,
            UserRoleHasExistedException.class,
            UserHasExistedException.class
    })
    protected ResponseEntity<GeneralFormattedResponseBody<Object>>
    handleConflict(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                GeneralFormattedResponseBody.<Object>builder()
                        .status(HttpStatus.CONFLICT.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = {
            LongArrangementNotExistsException.class,
            ShortArrangementNotExistsException.class,
            LaboratoryNotExistedException.class,
            SemesterNotExistedException.class,
            ResponsibleForNotExistedException.class,
            InstructorRequestNotExistException.class,
            RepairRequestNotExistException.class,
            StudentRequestNotExistException.class,
            AdministratorNotExistException.class,
            InstructorNotExistException.class,
            StudentNotExistException.class,
            TechnicianNotExistException.class,
            UserRoleNotExistException.class,
            UserNotExistException.class
    })
    protected ResponseEntity<GeneralFormattedResponseBody<Object>>
    handleNotFound(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                GeneralFormattedResponseBody.<Object>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = {
            FileTypeNotSupportedException.class
    })
    protected ResponseEntity<GeneralFormattedResponseBody<Object>>
    handleUnsupportedMediaType(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(
                GeneralFormattedResponseBody.<Object>builder()
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = {
            InstructorRequestStatusTypeNotExistException.class,
            LabTypeNotExistException.class,
            RepairRequestStatusTypeNotExistException.class,
            RoleTypeNotExistException.class,
            SeasonTypeNotExistException.class,
            SlotTypeNotExistException.class,
            StudentRequestStatusTypeNotExistException.class,
            WeekdayTypeNotExistException.class,
            UserRoleConflictException.class
    })
    protected ResponseEntity<GeneralFormattedResponseBody<Object>>
    handleUnprocessableEntity(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                GeneralFormattedResponseBody.<Object>builder()
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = {
            SetCurrentSemesterException.class
    })
    protected ResponseEntity<GeneralFormattedResponseBody<Object>>
    handlePreconditionFailed(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(
                GeneralFormattedResponseBody.<Object>builder()
                        .status(HttpStatus.PRECONDITION_FAILED.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = {
            NumberIllegalException.class,
            AdminMessageNullException.class,
            StudentCountIllegalException.class,
            MultipleRoleException.class,
            RoleSpecificInfoNotFoundException.class,
            NoClazzException.class,
            NoMajorException.class,
            NoNameException.class,
            NoTitleException.class,
            NoUserRoleException.class,
    })
    protected ResponseEntity<GeneralFormattedResponseBody<Object>>
    handleBadRequest(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                GeneralFormattedResponseBody.<Object>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = {
            BatchImportUsersException.class,
            TooManyListenersException.class
    })
    protected ResponseEntity<GeneralFormattedResponseBody<Object>>
    handleInternalServerError(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                GeneralFormattedResponseBody.<Object>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(ex.getMessage())
                        .build()
        );
    }
}
