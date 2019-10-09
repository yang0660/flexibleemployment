package com.flexibleemployment.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flexibleemployment.enums.ResponseStatusEnum;
import com.flexibleemployment.utils.BaseBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class IWechatService {
//    @Value("${wx.applet.wxAppid}")
    private String wxAppid="wxcf77273f875cf9ce";

//    @Value("${wx.applet.wxAppSecret}")
    private String wxAppSecret="0fd268a70bbaa3130303ae09113329c0";

    @Autowired
    private RestTemplate restTemplate;

    private final static String code2session = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    public Map<String, String> wxCodeToSession(String wxCode) {
        String fullApi = String.format(code2session,wxAppid,wxAppSecret,wxCode);
        log.info(">>> 完整api:{} <<<", fullApi);
        ResponseEntity<String> wxResponse = restTemplate.getForEntity(fullApi, String.class);
        if (wxResponse.getStatusCode() != HttpStatus.OK) {
            log.error(">>>请求微信code2session异常 <<<");
            throw new BaseBizException(ResponseStatusEnum.WX_APPLET_GET_OPENID_REQ_STATUS_FAIL);
        }
        String wxJsonData = wxResponse.getBody();
        log.info(">>> 微信小程序code2session响应报文：{}", wxJsonData);
        JSONObject wxJsonDataJsonObj = JSON.parseObject(wxJsonData);
        if (null != wxJsonDataJsonObj) {
            //{"errcode":40029,"errmsg":"invalid code, hints: [ req_id: GiMb07aLRa-0VM.5 ]"}
            if (wxJsonDataJsonObj.containsKey("errcode")) {
                log.error(">>> 获取微信授权凭证code2session错误,错误信息：{}<<<", wxJsonData);
                throw new BaseBizException(ResponseStatusEnum.WX_PUSH_MESSAGE_ACCRSSTOKEN_ISNULL);
            }

            String session_key = wxJsonDataJsonObj.getString("session_key");
            String openid = wxJsonDataJsonObj.getString("openid");
            String unionid = wxJsonDataJsonObj.getString("unionid");

            Map<String,String> codeSession = new HashMap<>();
            codeSession.put("session_key",session_key);
            codeSession.put("openid",openid);
            codeSession.put("unionid",unionid);


            return codeSession;
        } else {
            log.error(">>> 登录oauth2认证中心鉴权失败,鉴权错误信息：{}<<<", wxJsonData);
            throw new BaseBizException(ResponseStatusEnum.WX_PUSH_MESSAGE_ACCRSSTOKEN_ISNULL);
        }
    }
}
