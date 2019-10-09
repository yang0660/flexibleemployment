package com.flexibleemployment.controller;

import com.alibaba.fastjson.JSONObject;
import com.flexibleemployment.dao.entity.User;
import com.flexibleemployment.dao.entity.WhiteList;
import com.flexibleemployment.enums.DeviceTypeEnum;
import com.flexibleemployment.helper.ShiroHelper;
import com.flexibleemployment.service.IWechatService;
import com.flexibleemployment.service.UserService;
import com.flexibleemployment.service.WhiteListService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.shiro.ManageUserNamePasswordToken;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.utils.WechatDecryptDataUtil;
import com.flexibleemployment.vo.request.EncryptedDataReqVO;
import com.flexibleemployment.vo.request.LoginTokenRequsetDTOExt;
import com.flexibleemployment.vo.request.UserReqVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.UserTokenRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/app/login")
@Api(tags = {"用户认证登录模块"})
@Slf4j
public class LoginController {

    @Autowired
    IWechatService iWechatService;

    @Autowired
    UserService userService;

    @Autowired
    WhiteListService whiteListService;


    /**
     * 获取api访问令牌
     *
     * @param wxcode
     * @return
     */
    @PostMapping(value = "/loginToken")
    @ApiOperation(value = "获取api访问令牌")
    @AuthIgnore
    public ResultVO<UserTokenRespVO> loginForToken0(@RequestBody LoginTokenRequsetDTOExt wxcode) {
        //取得code，解密出openId，unionId，sessionKey
        Map<String, String> sessionMap = iWechatService.wxCodeToSession(wxcode.getWxcode());
        String openId = sessionMap.get("openid");
//        String openId="12345";
        //判断openId在用户表里是否已存在，不存在则以openId创建一个空用户，
        User user = userService.query(openId);
        UserTokenRespVO respVO = null;
        if (user == null) {
            respVO = new UserTokenRespVO();
            UserReqVO reqVO = new UserReqVO();
            reqVO.setOpenId(openId);
            Integer add = userService.add(reqVO);
            respVO.setMember(false);
            respVO.setBindMobile(false);
            respVO.setOpenId(openId);
        }else{
            respVO = ConvertUtils.convert(user, UserTokenRespVO.class);
            if(null == user.getMobile() || user.getMobile().equals("")){
                respVO.setBindMobile(false);
            }else{
                respVO.setBindMobile(true);
            }
            if(null != user.getIsWhiteList()){
                respVO.setMember((byte)1 == user.getIsWhiteList().byteValue()?true:false);
            }
        }
        //获取token
        Subject subject = SecurityUtils.getSubject();
        ManageUserNamePasswordToken usernamePasswordDeviceToken = new ManageUserNamePasswordToken(openId,null, DeviceTypeEnum.H5);
        //登录
        subject.login(usernamePasswordDeviceToken);
        String sessionId = (String) subject.getSession().getId();
        respVO.setToken(sessionId);

        respVO.setSessionKey(sessionMap.get("session_key"));
//        respVO.setSessionKey("asss");
        return ResultVO.success(respVO);
    }


    /**
     * 解密手机号码
     *
     * @param
     * @return
     */
    @PostMapping(value = "/deciphering")
    @ApiOperation("解密手机号码")
    public ResultVO<Integer> deciphering(@RequestBody EncryptedDataReqVO reqVO) {
        WechatDecryptDataUtil util = new WechatDecryptDataUtil();
        String encryptedData = reqVO.getEncryptedData();
        String session_key = reqVO.getSession_key();
        String iv = reqVO.getIv();
        //解密出手机号码
        String mobile=null;
        try {
            String res = util.decryptS5(encryptedData, "utf-8",session_key, iv);
            if(null == res || res.isEmpty()){
                return ResultVO.error("0002","手机号获取错误");
            }
            JSONObject object = JSONObject.parseObject(res);
            mobile = object.getString("phoneNumber");

            if(null!=mobile && !mobile.isEmpty()){
                String openId = ShiroHelper.getSessionOpenId();
                log.debug("解密手机号:openid="+openId);
                //更新用户信息
                UserReqVO userReqVO = new UserReqVO();
                userReqVO.setMobile(mobile);
                userReqVO.setOpenId(openId);
                Integer integer = userService.update0(userReqVO);
                log.debug("更新用户信息：手机号成功:"+integer);
            }

            log.info("解析手机号："+mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserReqVO user = new UserReqVO();
        user.setMobile(mobile);
        //更新用户的手机号码字段
        return ResultVO.success(userService.update(user));
    }


}
