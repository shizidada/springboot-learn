package org.learn.manager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.learn.common.Constants;
import org.learn.service.model.MemberModel;

import java.util.Date;
import java.util.HashMap;

public class JwtTokenManager {

    // 创建token
    public static String createToken(String username, String role, boolean isRememberMe) {
        long expiration = isRememberMe ? Constants.JWT.EXPIRATION_REMEMBER : Constants.JWT.EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constants.JWT.ROLE_CLAIMS, role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, Constants.JWT.SECRET)
                .setClaims(map)
                .setIssuer(Constants.JWT.ISS) // 签发者
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    // 获取用户角色
    public static String getUserRole(String token) {
        return (String) getTokenBody(token).get(Constants.JWT.ROLE_CLAIMS);
    }

    // 获取用户基本信息
    public static MemberModel getMemberInfo(String token) {
        return (MemberModel) getTokenBody(token).get(Constants.JWT.ROLE_INFO);
    }

    // 是否已过期
    public static boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    // 可能是一个伪造 token 导致签名不一致
    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(Constants.JWT.SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
