package com.flexibleemployment.enums;

import lombok.AllArgsConstructor;

/**
 * 登录操作类型枚举
 */
@AllArgsConstructor
public enum LoginTypeEnum {
    LOGIN((byte) 1, "登录"), LOGOUT((byte) 2, "退出");

    public final Byte value;
    public final String desc;

    public static LoginTypeEnum enumOf(Byte value) {
        for (LoginTypeEnum e : LoginTypeEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
