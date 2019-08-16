package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Data
public class ResultVO<T> {
    public static final String SUCCESS_CODE = "0000";
    public static final String VALID_ERROR_CODE = "0001";
    public static final String UNKNOW_ERROR_CODE = "0002";
    public static final String UNBIND_ERROR_CODE = "0003";
    public static final String UNLOGIN_ERROR_CODE = "0004";
    public static final String NOPERMISSION_ERROR_CODE = "0005";
    public static final String UNREGISTER_ERROR_CODE = "0006";
    /**
     * 业务响应码 0003 未绑定APP帐号
     */
    @ApiModelProperty("业务响应码 0000 成功，0001失败，0002 未知错误，0003 未绑定APP帐号,0004 未登录，0005 无权限操作 0006 未注册")
    private String respCode;
    /**
     * 业务数据
     */
    private T data;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 详细
     */
    private String errorMessage;
    private Object errorMessageDetail;


    private ResultVO(String respCode, T data, String message, String errorMessage, Object errorMessageDetail) {
        this.respCode = respCode;
        this.data = data;
        this.message = message;
        this.errorMessage = errorMessage;
        this.errorMessageDetail = errorMessageDetail;
    }

    /**
     * 处理成功
     *
     * @param data 业务数据
     */
    public static <T> ResultVO<T> success(T data) {
        return success(data, "success");
    }

    public static <T> ResultVO<T> success(T data, String message) {
        return new ResultVO<>(SUCCESS_CODE, data, message, null, null);
    }

    /**
     * 校验错误
     *
     * @param message 错误消息
     */
    public static <T> ResultVO<T> validError(String message) {
        return new ResultVO<>(VALID_ERROR_CODE, null, message, null, null);
    }

    /**
     * 未知异常
     */
    public static <T> ResultVO<T> unknowError(Exception e) {
        return new ResultVO<>(UNKNOW_ERROR_CODE, null, e.getMessage(), e.getMessage(), ExceptionUtils.getRootCauseStackTrace(e));
    }

    public ResultVO<T> setErrorMessageDetail(Object errorMessageDetail) {
        this.errorMessageDetail = errorMessageDetail;
        return this;
    }

    public ResultVO<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ResultVO<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResultVO<T> setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
