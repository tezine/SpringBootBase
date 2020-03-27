package com.example.demo.models;

import lombok.Data;


@Data
public class EError {
    private String className;
    private String methodName;
    private String message;
    private Exception exception;
    
}
