package com.neoris.turnosrotativos.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status",status.value());
        responseBody.put("errors",status.name());
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        responseBody.put("message", errors);

        return new ResponseEntity<>(responseBody, headers, status);


    }
    @ExceptionHandler(Excepciones.class)
    public ResponseEntity<Object> hadlerExcepciones(Excepciones ex, WebRequest request){
        Map<String,Object> responseBody=new LinkedHashMap<>();


        responseBody.put("status",ex.getStatus());
        responseBody.put("error",ex.getStatusNom());
        responseBody.put("message",ex.getMessage());

        switch (ex.getStatus()){
            case 400:
                return new ResponseEntity<>(responseBody,HttpStatus.BAD_REQUEST);

            case 404:
                return new ResponseEntity<>(responseBody,HttpStatus.NOT_FOUND);

            default:
                return new ResponseEntity<>(responseBody,HttpStatus.CONFLICT);


        }




    }

}
