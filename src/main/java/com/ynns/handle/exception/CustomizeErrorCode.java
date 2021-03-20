package com.ynns.handle.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESSION_NOT_FOUND(2001,"CustomizeErrorCode=====>message自定义错误信息---"),
    TARGET_PARAM_NOT_FOUND(2002,"CustomizeErrorCode=====>code自定义错误信息---回复"),
    NOT_LOGIN(2003,"CustomizeErrorCode=====>code自定义错误信息---登录"),
    SYS_ERROR(2004,"CustomizeErrorCode=====>code自定义错误信息---"),
    TYPE_PARAM_WRONG(2005,"CustomizeErrorCode=====>code自定义错误信息---评论类型不存在"),
    COMMENT_NOT_FOUND(2006,"CustomizeErrorCode=====>code自定义错误信息---回复的评论不存在"),
    CONTENT_IS_EMPTY(2007,"Server----->回复列表不能为空"),
    READ_NOTIFICATION_FAIL(2008,"Notification----->正在读取他人信息"),
    NOTIFICATION_NOT_FOUND(2009,"Notification----->消息读取不到"),
    ;
    private Integer code;
    private String message;
    @Override
    public String getMessage(){
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message){
        this.message=message;
        this.code=code;
    }
}
