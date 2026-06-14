package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    // wx登录接口
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties wechatProperties;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        log.info("微信用户登录参数：{}", userLoginDTO);
        String openid = getWxOpenid(userLoginDTO.getCode());
        if (openid == null) {
            log.error("微信登录失败，用户信息获取失败");
            throw new RuntimeException("微信登录失败，用户信息获取失败");
        }
        // 查询用户信息
        User user = userMapper.getByOpenid(openid);
        if (user == null) {
            // 新用户，自动完成注册
            user = User.builder()
                    .openid(openid)
                    .build();
            userMapper.insert(user);
        }
        return user;
    }

    /**
     * 获取微信登录openid
     * @param code
     * @return
     */
    private String getWxOpenid(String code) {
        // 调用wx登录接口，获取微信登录结果
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("appid", wechatProperties.getAppid());
        paramMap.put("secret", wechatProperties.getSecret());
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN_URL, paramMap);
        JSONObject jsonObject = JSONObject.parseObject(json);
        // 用户信息合法校验
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
