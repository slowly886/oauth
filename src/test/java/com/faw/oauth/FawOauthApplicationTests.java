package com.faw.oauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FawOauthApplicationTests {

    private String s = "secret";

    @Test
    public void createJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX25hbWUyIjoicGVuZ3J1aSIsInVzZXJfbmFtZTEiOiJwZW5ncnVpIiwidXNlcl9uYW1lNCI6InBlbmdydWkiLCJ1c2VyX25hbWUzIjoicGVuZ3J1aSJ9.cZ-7a0cw9CevMMHbp1-zmSV47S7S6vH75aZ2o4rpPaU";
//        Map<String, String> headers = JwtHelper.headers(token);
//        SignatureVerifier signatureVerifier = new RsaVerifier("secret");
//        Jwt decode = JwtHelper.decodeAndVerify(token, signatureVerifier);
//        System.out.println(headers);
        Claims body = Jwts.parser().setSigningKey("secret".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        System.out.println("");

    }


    @Test
    public void createJWT() {
        Map map = new HashMap();
        map.put("user_name1", "pengrui");
        map.put("user_name2", "pengrui");
        map.put("user_name3", "pengrui");
        map.put("user_name4", "pengrui");
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder()
                .setClaims(map)                                // 自定义属性
                .signWith(signatureAlgorithm, "secret".getBytes(StandardCharsets.UTF_8));           // 签名算法以及密匙
        System.out.println(builder.compact());
    }

    @Test
    public void jwt() {
    }
}
