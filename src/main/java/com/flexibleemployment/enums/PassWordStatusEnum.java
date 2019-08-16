package com.flexibleemployment.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PassWordStatusEnum {
    EFFECTIVE((byte) 0, "有效"), EXPIRE((byte) 1, "过期"), RESET((byte) 2, "重置");

    public final Byte value;
    public final String desc;

}
