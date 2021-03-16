package com.ynns.handle.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESSION_NOT_FOUND("message自定义错误信息");
    private String message;
    @Override
    public String getMessage(){
        return message;
    }
    CustomizeErrorCode(String message){
        this.message=message;
    }
}
