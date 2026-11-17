package com.rahul.hrms.controller;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(ResponseStatusException.class) public ResponseEntity<Map<String,Object>> handleStatus(ResponseStatusException ex){
   return ResponseEntity.status(ex.getStatusCode()).body(Map.of("timestamp",Instant.now().toString(),"status",ex.getStatusCode().value(),"error",ex.getReason()==null?"Request failed":ex.getReason()));
 }
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException ex){
   Map<String,String> errors=new TreeMap<>();ex.getBindingResult().getFieldErrors().forEach(e->errors.put(e.getField(),e.getDefaultMessage()));
   return ResponseEntity.badRequest().body(Map.of("timestamp",Instant.now().toString(),"status",400,"error","Validation failed","fields",errors));
 }
 @ExceptionHandler(Exception.class) public ResponseEntity<Map<String,Object>> handleOther(Exception ex){
   return ResponseEntity.status(500).body(Map.of("timestamp",Instant.now().toString(),"status",500,"error","Unexpected server error"));
 }
}
