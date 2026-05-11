package com.globexial.controller;

import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public record ApiResponse<T>(
        boolean success,
        HttpStatus httpStatus,
        T data,
        String message,
        Object error,
        Timestamp date
) {

    public  static <T> ApiResponse<T> ok(T t,HttpStatus code,String message){
        return new ApiResponse<>(true,code,t,message,null,new Timestamp(System.currentTimeMillis()));
    }

    public  static ApiResponse<?> error(HttpStatus code,String message,Object error){
        return new ApiResponse<>(false,code,null,message,error,new Timestamp(System.currentTimeMillis()));
    }

}
