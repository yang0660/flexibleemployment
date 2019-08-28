package com.flexibleemployment.utils;

import com.flexibleemployment.enums.ResponseStatusEnum;

public class BaseBizException extends RuntimeException {

    private String errorCode;
    private String msg;

    public BaseBizException() {
        super();
    }

    public BaseBizException(ResponseStatusEnum responseStatusEnum) {
        super(responseStatusEnum.getMessage());
        this.errorCode = responseStatusEnum.getCode();
        this.msg = responseStatusEnum.getMessage();
    }

    public BaseBizException(ResponseStatusEnum responseStatusEnum,String msg) {
        super(msg);
        this.errorCode = responseStatusEnum.getCode();
        this.msg = msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
