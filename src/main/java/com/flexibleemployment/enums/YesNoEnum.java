package com.flexibleemployment.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum YesNoEnum {
    NO((byte) 0, "否"), YES((byte) 1, "是");

    public final Byte value;
    public final String desc;

    public static YesNoEnum enumOf(Byte value) {
        for (YesNoEnum e : YesNoEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
