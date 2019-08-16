package com.flexibleemployment.controller;

import com.flexibleemployment.utils.BizException;
import com.flexibleemployment.vo.response.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 异常处理
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    private final Pattern DUPLICATE_PATTERN = Pattern.compile(".*Duplicate entry '(.*)' for key '(.*)'.*", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private final Pattern CANNOT_BE_NULL_PATTERN = Pattern.compile(".*Column '(.*)' cannot be null.*", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    @ExceptionHandler
    @ResponseBody
    public ResultVO<?> handle(HandlerMethod handlerMethod, Exception e) {
        log.error("接口\"" + handlerMethod.getBeanType().getName() + "." + handlerMethod.getMethod().getName() + "\"异常", e);

        ResultVO<?> resultVO = ResultVO.validError("网络异常，请稍后重试！");
        if (e instanceof DuplicateKeyException) {
            Matcher matcher = DUPLICATE_PATTERN.matcher(e.getMessage());
            if (matcher.matches()) {
                String value = matcher.group(1);
                String uniqIdx = matcher.group(2);
                return resultVO.setMessage(getUniqDesc(uniqIdx) + "'" + value + "'重复， 请修改后重试");
            }
            return resultVO.setMessage("数据有重复，请检查参数");
        } else if (e instanceof DataIntegrityViolationException) {
            Matcher matcher = CANNOT_BE_NULL_PATTERN.matcher(e.getMessage());
            if (matcher.matches()) {
                String column = matcher.group(1);
                return resultVO.setMessage("参数'" + getColumnDesc(column) + "'不能为空， 请设值");
            }
            return resultVO.setMessage("数据缺失，请检查参数");
        } else if (e instanceof BizException) {
            return resultVO.setMessage(e.getMessage());
        }

        return resultVO;
    }

    private String getColumnDesc(String column) {
        switch (column) {
            case "user_id":
                return "用户ID";
            case "user_name":
                return "用户名";
        }
        return column;
    }

    private String getUniqDesc(String uniqIdx) {
        switch (uniqIdx) {
            case "uniq_idx_user_id":
                return "用户ID";
            default:
                return "数据";
        }
    }
}
