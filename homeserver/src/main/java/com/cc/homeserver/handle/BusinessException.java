package com.cc.homeserver.handle;


public class BusinessException extends Exception{
    public enum  ResultEnum{
        IMG_NOT_EMPTY, IMG_FORMAT_ERROR,SAVE_IMG_ERROE
    }

    private ResultEnum resultEnum;

    public BusinessException(ResultEnum resultEnum){
        this.resultEnum = resultEnum;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }
}
