package com.atguigu.ucenter.controller;

import com.atguigu.commonutils.HttpClientUtils;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.ucenter.entiy.UcenterMember;
import com.atguigu.ucenter.service.UcenterMemberService;
import com.atguigu.ucenter.utils.ConstantPropertiesUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
//@CrossOrigin
@RequestMapping("/api/educenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("/callback")
    public String wxLogin(String code, String state) throws UnsupportedEncodingException {
        System.out.println("code:" + code);
        System.out.println("state:" + state);

        try {
            // 请求第一个固定地址，获得openid、access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);
            // 发送get请求，使用自定义的工具类，基于httpClient技术
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            // 返回的是一个json字符串，可以转化成map
            Gson gson = new Gson();
            HashMap<String, String> tokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = tokenMap.get("access_token");
            String openId = tokenMap.get("openid");

            // 查询数据库是否存在
            UcenterMember member = ucenterMemberService.getWxMember(openId);
            // 根据openid查询不到，证明没有扫码登录过
            if(member == null) {
                // 发送第二个固定请求，获取微信信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl,
                        accessToken,
                        openId);
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap<String, String> userMap = gson.fromJson(userInfo, HashMap.class);
                // String openId = userMap.get("openid");
                String nickname = userMap.get("nickname");
                String headimgurl = userMap.get("headimgurl");

                // 存入数据库
                UcenterMember ucenterMember = new UcenterMember();
                ucenterMember.setOpenid(openId);
                ucenterMember.setNickname(nickname);
                ucenterMember.setAvatar(headimgurl);
                ucenterMemberService.save(ucenterMember);

                // 根据id和nickname生成token，通过地址返回
                String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
                return "redirect:http://localhost:3000?token=" + jwtToken;
            }

            // 根据id和nickname生成token，通过地址返回
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "微信登录失败！");
        }

    }


    @GetMapping("/login")
    public String getWxCode() throws UnsupportedEncodingException {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu");

        return "redirect:" + qrcodeUrl;
    }

}
