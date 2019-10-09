package com.flexibleemployment.shiro;

import com.flexibleemployment.enums.DeviceTypeEnum;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by zhuhecheng 2019-01-08
 */
public class ManageUserNamePasswordToken extends UsernamePasswordToken {
    private static final long serialVersionUID = -6986234946672320990L;

    private DeviceTypeEnum deviceType;

    public ManageUserNamePasswordToken(String userName, String password) {
        super(userName, password);
        this.deviceType = DeviceTypeEnum.MANAGER;
    }

    public ManageUserNamePasswordToken(String openId, String password, DeviceTypeEnum deviceType) {
        super(openId, password);
        this.deviceType = deviceType;
    }

    public DeviceTypeEnum getDeviceType() {
        return deviceType;
    }

}
