package com.example.demo.helper;

import com.example.demo.models.EError;
import java.util.Optional;


public class Helper {

    static public EError prepareError(Exception ex){
        EError error=new EError();
        StackTraceElement stackElement=Thread.currentThread().getStackTrace()[2];
        error.setClassName(stackElement.getClassName());
        error.setMethodName(stackElement.getMethodName());
        error.setException(ex);
        return error;
    }
}
