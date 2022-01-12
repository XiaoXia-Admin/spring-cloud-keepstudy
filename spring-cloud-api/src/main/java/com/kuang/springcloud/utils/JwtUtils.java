package com.kuang.springcloud.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author XiaoXia
 * @date 2021/12/28 20:16
 */

public class JwtUtils {
    //token过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    //秘钥
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";


    /**
     * 生成token字符串，参数将会被包含进主体
     */
    public static String getJwtToken(String id, String nickname) {

        String jwtToken = Jwts.builder()
                //设置头部头部信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                //设置过期时间
                .setSubject("guli-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                //设置token主体部分 ，存储用户信息
                .claim("id", id)
                .claim("nickname", nickname)

                //设置防伪码
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return jwtToken;
    }

    /**
     * 判断token是否存在与是否有效，根据字符串
     */
    public static boolean checkToken(String jwtToken) {
        boolean flag = true;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 判断token是否存在与是否有效，根据请求对象
     */
    public static boolean checkToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        boolean flag = true;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 从请求对象中取出token字符串，然后从字符串中取出id
     * 方法要么成功取出，要么抛出异常
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        String id = (String) claims.get("id");
        return id;
    }
}
