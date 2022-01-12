package com.kuang.controller;

/**
 * @author: XiaoXia
 */

import com.google.gson.Gson;
import com.kuang.service.UserLoginService;
import com.kuang.springcloud.exceptionhandler.XiaoXiaException;
import com.kuang.springcloud.pojo.UserLogin;
import com.kuang.springcloud.utils.JwtUtils;
import com.kuang.springcloud.utils.R;
import com.kuang.util.ConstantWxUtils;
import com.kuang.util.HttpClientUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ulogin/wx")
@CrossOrigin
public class WxLoginController {

    @Autowired
    private UserLoginService userLoginService;

    /**
     * 微信登录 生成二维码
     *
     * @return 重定向的地址
     */
    @GetMapping("login")
    public String login() {
        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
//        //对redirect_url进行URLEncoder编码，失败了也无所谓
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, String.valueOf(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        //设置%s里面值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "keepstudy"
        );
//        //重定向到请求微信地址里面
        return "redirect:" + url;
    }

    /**
     * 微信扫描回调方法
     *
     * @param code  微信登录的临时票据
     * @param state 重定向的参数
     * @return
     */
    @GetMapping("callback")
    public String callback(String code, String state) {
        //获取code临时票据，类似验证码
        //用code向微信请求响应值，accsess_token (访问凭证) 和 openid(每个微信的唯一标识)
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //拼接三个参数 ：id、秘钥、code （封装好的地址)
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code
        );
        //请求地址来获取access_token,openid
        //远程调用微信接口
        String accessTokenInfo = null;
        UserLogin member = null;
        String jwtToken = null;
        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //取得响应值
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");
            member = userLoginService.getOpenIdMember(openid);
            if (member == null) {
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println(userInfo);
                //获取返回userinfo字符串扫描人信息
                HashMap<String,Object> userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headImgUrl = (String) userInfoMap.get("headimgurl");
                Double temp = (Double) userInfoMap.get("sex");
                Integer sex = temp.intValue();
                member = new UserLogin();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headImgUrl);
                member.setSex(sex);
                userLoginService.insertUserInfo(member);
            }
            String id = member.getId();
            String nickname = member.getNickname();
            jwtToken = JwtUtils.getJwtToken(id, nickname);
            return "redirect:http://localhost:8080?token=" + jwtToken;
        } catch (Exception e) {
            throw new XiaoXiaException(20001, "失败");
        }
    }

    /**
     * 获取用户的信息
     * @param request
     * @return
     */
    @GetMapping("getMemberInfo")
    @ResponseBody
    public R getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UserLogin member = userLoginService.getById(memberId);
        return R.ok().data("userInfo",member);
    }
}
