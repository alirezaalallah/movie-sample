package com.example.moviesample.exception;

import com.example.moviesample.dto.api.ApiErrorDto;
import com.example.moviesample.dto.api.ApiResponseDto;
import com.example.moviesample.dto.api.Reason;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MovieExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Reason reason = Reason.INVALID_VALUE;

        List<ApiErrorDto> errors = ex.getBindingResult().getAllErrors()
                .stream().map(
                        e -> ApiErrorDto.builder()
                                .reason(reason)
                                .message(e.getDefaultMessage())
                                .param(((FieldError) e).getField())
                                .build()
                ).collect(Collectors.toList());

        ApiResponseDto response = ApiResponseDto.builder().errors(errors).badRequest().build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ApiCommunicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleApiCommunicationException(ApiCommunicationException ex, WebRequest request) {
        Reason reason = Reason.OMDB_COMMUNICATION_ERROR;
        ApiErrorDto error = ApiErrorDto.builder()
                .reason(reason)
                .message(ex.getMessage())
                .build();
        ApiResponseDto response = ApiResponseDto.builder().errors(error).badRequest().build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request) {
        Reason reason = Reason.OMDB_COMMUNICATION_ERROR;
        ApiErrorDto error = ApiErrorDto.builder()
                .reason(reason)
                .message(ex.getMessage())
                .build();
        ApiResponseDto response = ApiResponseDto.builder().errors(error).badRequest().build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserDuplicateRateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleUserDuplicateRateException(UserDuplicateRateException ex, WebRequest request) {
        Reason reason = Reason.USER_DUPLICATE_RATE;
        ApiErrorDto error = ApiErrorDto.builder()
                .reason(reason)
                .message(ex.getMessage())
                .build();
        ApiResponseDto response = ApiResponseDto.builder().errors(error).badRequest().build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ApiEmptyResultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleApiEmptyResultException(ApiEmptyResultException ex, WebRequest request) {
        Reason reason = Reason.OMBD_EMPTY_RESULT;
        ApiErrorDto error = ApiErrorDto.builder()
                .reason(reason)
                .message(ex.getMessage())
                .build();
        ApiResponseDto response = ApiResponseDto.builder().errors(error).badRequest().build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(OMDBBusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleOMNBusinessException(OMDBBusinessException ex, WebRequest request) {
        Reason reason = Reason.OMDB_API_BUSINESS_ERROR;
        ApiErrorDto error = ApiErrorDto.builder()
                .reason(reason)
                .message(ex.getMessage())
                .build();
        ApiResponseDto response = ApiResponseDto.builder().errors(error).badRequest().build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<ApiErrorDto> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();

            String param = propertyPath.contains(".") ?
                    propertyPath.substring(propertyPath.indexOf(".") + 1) :
                    propertyPath;

            errors.add(
                    ApiErrorDto.builder()
                            .reason(Reason.INVALID_VALUE)
                            .message(violation.getMessage())
                            .param(param)
                    .build());
        }

        ApiResponseDto response = ApiResponseDto.builder().errors(errors).badRequest().build();
        return ResponseEntity.badRequest().body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDto error = ApiErrorDto.builder()
                .reason(Reason.REQUIRED_FIELD)
                .message("Param is missing")
                .param(ex.getParameterName())
                .build();
        ApiResponseDto response = ApiResponseDto.builder().errors(error).badRequest().build();
        return ResponseEntity.badRequest().body(response);
    }

}
