package com.flexibleemployment.enums;

/**
 * 响应状态对象
 * 
 * @author huangjianwen
 * @date 2018-03-15
 */
public enum ResponseStatusEnum {
	
    OK("200", "成功"),
    ERROR("990", "系统开小差啦"),
    FAIL("991", "操作失败"),
    PARAMETER_ERROR("301", "参数错误"),
    NO_AUTHORIZATION("302", "无Authorization传入"),
    ZUUL_FALL_BACK("303", "{0}无法访问服务，该服务可能由于某种未知原因被关闭，请重启服务！"),
    INTERNAL_ERROR("304", "内部服务异常，异常信息：{0}"),
    UNKNOW_ERROR("305", "未知错误"),
    ANALYSIS_TOKEN_FAIL("306","解析token失效"),
    UNAUTHORIZED("307","无访问权限"),
    LOGINTOKEN_REQUEST_FAIL("308","请求认证中心获取token失败"),
    GET_CURRENT_LOGIN_INFO_FAIL("309","获取当前登录信息失败"),
    OLDPASSWORD_FILL_ERROR("310","输入的原始密码错误"),
    CONFIRMPWD_NEWPASSWORD_NOTEQUALS_ERROR("311","输入的新密码密码和确认密码不一致"),
    MODIFYUSERPWD_FAIL("312","修改用户密码失败"),
    NO_BEARER_TOKEN_TYPE("313","授权类型不是bearer类型"),
    SENDSMS_TYPE_ERROR("314", "发送短信验证码的业务类型未定义"),
    NOT_QUERY_USER_INFO_ERROR("315", "无法查询到当前用户信息"),
    NOT_FILL_IDENTIY_INFO_ERROR("316", "未填写用户身份信息"),
    NOT_QUERY_USER_PERSONALINFO_ERROR("317", "未查到用户个人信息"),
    DATA_MORE_THAN_16M_ERROR("318", "数据大于16M"),
    DATA_OPERATE_FAIL("319", "数据操作失败"),
    INVALID_TOKEN("320", "access_token无效"),
    NOT_LOGIN_ERROR("321","访问此资源需要完全的身份验证"),
    NOT_EXIT_ROLE_FAIL("322","不存在的角色"),
    NOT_EXIT_ENABLED_PERMISSION_FAIL("323","授权资源权限中不属于可用状态的资源权限"),
    NOT_EXIT_MENU_FAIL("324","不存在的资源菜单"),
    NOT_EXIT_PARENTMENU_MENU_FAIL("325","不存在的父级菜单"),
    ADD_USER_FAIL("326","新增用户失败"),
    NOT_DEL_ADMIN_USER_INFO_ERROR("327", "无法删除系统管理员"),
    NOT_DEL_ADMIN_USER_ROLE_INFO_ERROR("328", "无法删除系统管理员角色"),
    NOT_DEL_ROOT_MENU_INFO_ERROR("329","无法删除根菜单"),
    NOT_DEL_ROOT_DEPT_INFO_ERROR("330", "无法删除系统根部门"),
    ILLEGAL_FORMAT_RESET_PWD_LINKS_FAIL("331", "非法格式重置密码链接"),
    OVERDUE_RESET_PWD_LINKS_FAIL("332", "过期重置密码链接"),
    RESET_PWD_FAIL("333", "重置密码失败"),
    AUTH_CODE_FAIL("334","验证码不正确"),
    LOGIN_AUTHENTICATION_FAILURE("618","登录验证失败"),
    WX_APPLET_GET_OPENID_AUTHENTICATION_FAIL("619","获取微信登录授权openId凭证失败"),
    WX_APPLET_GET_OPENID_REQ_STATUS_FAIL("620","获取微信登录授权openId凭证请求状态异常"),
    LOGIN_WX_GET_OPENID_REQUEST_FAIL("621","获取微信登录授权openId请求失败"),
    SENDSMSRATE_FAIL("622","短信次数校验-操作频率过快"),
    MOBILESMSCOUNT_FAIL("623","短信次数校验-当天短信发送数上限"),
    IPSMSCOUNT_FAIL("624","短信次数校验-IP当天短信发送数上限"),
    TOTALSMSCOUNT_FAIL("625","短信次数校验-当天短信发送数上限"),
    LOGIN_VALIDSMSCODE_FAIL("627","短信验证码验证失败"),
    ADD_USER_NOT_FILL_MOBILE_INFO_FAIL("628", "用户绑定手机号为空"),
    IDENTIFY_NULL("10001","鉴别师没有到位"),
    NOT_DEL_USED("628","已被使用不能删除"),
    UPLOADHEADIMAGE_FAIL("629", "个人头像上传失败"),
    UPLOADHEADFILE_FAIL("630", "上传文件失败"),
    WX_APPLET_GET_TOKEN_FAIL("631", "获取微信token失败"),
    WX_APPLET_GET_QRCODE_FAIL("632", "获取微信二维码失败"),
    NOT_PROCESSING_ORDER("633", "鉴别流程已结束，不能重复处理！"),
    NOT_EXIST_ORDER("634", "鉴别订单不存在！"),
    ALREADY_ASSISTANCE("635","已加速 本订单只能加速一次"),
    CANNOT_PROCESS_IN_WAITING_ADD_PICTURE("636","该订单待补充图片，暂时不能鉴别！"),
    CANNOT_PROCESS_NOT__IN_WAITING_ADD_PICTURE("637","该订单不是待补图状态，暂时不能提醒用户补图！"),

    PRODUCT_INFO_NULL("701","产品信息为空"),
    Discern_INFO_NULL("705","产品暂时未绑定鉴别师"),
    PRODUCT_IMG_PROBLEM_EXIT("702","还有图片待补充，请补充完再提交！"),
    ORDER_IS_NOTNEED_UPLOAD_IMG("703","订单非待补图状态，不能上传图片！"),
    CAN_NOT_ON_OWNER_OIL("704","不能自己给自己加速喔！"),
    COMMUTE_TURE_OR_FALSE("705","改判只能改判为真，假或者无法鉴别！"),

    WX_PUSH_MESSAGE_ACCRSSTOKEN_ISNULL("901","微信推送消息失败,AccessToken为空！"),
    WX_PUSH_MESSAGE_ERROR("902","微信推送消息失败！"),
    ANTI_CRAWLER_FAIL("903","反爬虫验证规则列表失败"),

    PRIZE_QUERY_ERROR("1001","活动id不存在"),
    PRIZE_OVER("1002","活动已结束"),
    PRIZE_NOT_BEGIN("1003","活动未开始"),
    PRIZE_USER_CONF_NOT_FOUND("1004","用户对于的当前活动配置不存在"),
    PRIZE_QUERY_MYINVTE("1002","输入的活动Id或用户id为0"),
    PLEASE_COMMIT_ORDER("1003","请重新发起鉴别"),
    PRIZE_RUNNING_EXIST("1005","存在正在运行的抽奖活动信息，暂时不能新增抽奖活动");


    private String code;
    private String message;

    public static ResponseStatusEnum getEnum(String code) {
        for (ResponseStatusEnum ele : ResponseStatusEnum.values()) {
            if (ele.code.equals(code)){
                return ele;
            }
        }
        return null;
    }

    /**
     * 构造方法
     *
     * @param code    错误码
     * @param message 错误消息
     */
    ResponseStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return 返回成员变量值
     */
    public String getCode() {
        return code;
    }

    /**
     * @return 返回成员变量值
     */
    public String getMessage() {
        return message;
    }
}