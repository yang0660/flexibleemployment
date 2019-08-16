package com.flexibleemployment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangwanli on 2017/8/25.
 */
@Getter
@AllArgsConstructor
public enum DeviceTypeEnum {
    ANDROID_MOBILE("Android"),
    IOS_MOBILE("IOS"),
    H5("H5");
    private String desc;

    public static DeviceTypeEnum enumOf(String desc) {
        if (StringUtils.isBlank(desc)) {
            return null;
        }
        for (DeviceTypeEnum deviceTypeEnum : values()) {
            if (deviceTypeEnum.desc.equals(desc)) {
                return deviceTypeEnum;
            }
        }
        return null;
    }

    public static String CANDIDATE_PATTERN = ANDROID_MOBILE.desc + "|" + IOS_MOBILE.desc + "|" + H5.desc;
}
