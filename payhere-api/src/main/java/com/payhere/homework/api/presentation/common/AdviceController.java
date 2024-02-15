package com.payhere.homework.api.presentation.common;

import com.payhere.homework.api.application.exception.BadRequestException;
import com.payhere.homework.api.application.exception.NoPermissionException;
import com.payhere.homework.api.application.exception.NotFoundException;
import com.payhere.homework.api.application.exception.UnauthorizedException;
import com.payhere.homework.api.presentation.common.dto.ApiResponse;
import com.payhere.homework.api.presentation.common.dto.ErrorResponse;
import com.payhere.homework.api.presentation.common.dto.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleRuntimeException(final RuntimeException e) {
        log.error("AdviceController > handleRuntimeException: {}", e.getMessage(), e);

        ApiResponse<ErrorResponse> body = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "알수없는 에러가 발생하였습니다.",
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNotFoundException(final NotFoundException e) {
        ApiResponse<ErrorResponse> body = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUnauthorizedException(final UnauthorizedException e) {
        ApiResponse<ErrorResponse> body = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNoPermissionException(final NoPermissionException e) {
        ApiResponse<ErrorResponse> body = new ApiResponse<>(
                HttpStatus.FORBIDDEN.value(),
                e.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(body);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadRequestException(final BadRequestException e) {
        ApiResponse<ErrorResponse> body = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ValidationErrorResponse>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            response.getErrors().add(error.getDefaultMessage());
        }

        ApiResponse<ValidationErrorResponse> body = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                response,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(body);
    }


}
