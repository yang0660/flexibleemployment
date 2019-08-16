package com.flexibleemployment.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DeleteStatusEnum {
    NORMAL((byte) 0, "正常"), DELETED((byte) 1, "已删除");

    public final Byte value;
    public final String desc;

    public static DeleteStatusEnum enumOf(Byte value) {
        for (DeleteStatusEnum e : DeleteStatusEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
