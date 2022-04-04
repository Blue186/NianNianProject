package com.nian.business.validator;

import com.nian.business.utils.R;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerParamValidator {
        // <1> 处理 form data方式调用接口校验失败抛出的异常
        @ExceptionHandler(BindException.class)
        public R<?> bindExceptionHandler(
                HttpServletResponse response,
                BindException e
        ) {
            List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
            List<String> collect = fieldErrors.stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            return R.error().message("参数错误").detail(collect);
        }

        // <2> 处理 json 请求体调用接口校验失败抛出的异常
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public R<?> methodArgumentNotValidExceptionHandler(
                HttpServletResponse response,
                MethodArgumentNotValidException e
        ) {
            List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
            List<String> collect = fieldErrors.stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            return R.error().message("参数错误").detail(collect);
        }

        // <3> 处理单个参数校验失败抛出的异常
        @ExceptionHandler(ConstraintViolationException.class)
        public R<?> constraintViolationExceptionHandler(
                HttpServletResponse response,
                ConstraintViolationException e
        ) {
            Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
            List<String> collect = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            return R.error().message("参数错误").detail(collect);
        }
}
