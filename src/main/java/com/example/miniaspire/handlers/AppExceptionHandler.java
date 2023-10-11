package com.example.miniaspire.handlers;

import com.example.miniaspire.controllers.AdminController;
import com.example.miniaspire.exceptions.AppException;
import com.example.miniaspire.exceptions.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackageClasses = { AdminController.class, CustomerHandler.class })
@ResponseBody
public class AppExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionResult> handleThrowable(AppException ex) {
        return new ResponseEntity<>(new ExceptionResult(ex), ex.getErrorCode());

    }
}
