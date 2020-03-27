package com.example.demo.helper;

import com.example.demo.models.EError;

public class Logger {

    static public void logDebug(String txt) {
        System.out.println(txt);
    }

    static public void logError(Exception ex) {
        Throwable rootCause = ex;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        String className=rootCause.getStackTrace()[0].getClassName();
        String methodName=rootCause.getStackTrace()[0].getMethodName();
        System.out.println(String.format("Exception caused by %s.%s",className,methodName));
        ex.printStackTrace();
    }
    
    static public void logError(EError error){
        String lastWordOfClassName=error.getClassName().substring(error.getClassName().lastIndexOf(".")+1);
        System.out.println(String.format("Exception caused by %s. Exception:",lastWordOfClassName,error.getMethodName()));
        if(error.getException()!=null)error.getException().printStackTrace();
    }
}
