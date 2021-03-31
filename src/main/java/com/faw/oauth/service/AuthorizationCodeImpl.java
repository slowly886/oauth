package com.faw.oauth.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: AuthorizationCodeImpl
 * @Package com.faw.oauth.service
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/3/3/0003 17:03
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/3/3/0003     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
@Service
public class AuthorizationCodeImpl extends RandomValueAuthorizationCodeServices {

    private final String PREFIX = "faw:cloud:oauth:";

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    protected void store(String key, OAuth2Authentication oAuth2Authentication) {
        redisTemplate.opsForValue().set(PREFIX + key, toHexString(SerializationUtils.serialize(oAuth2Authentication)), 30, TimeUnit.SECONDS);
    }

    @Override
    protected OAuth2Authentication remove(String key) {
        OAuth2Authentication authentication = null;
        String value = (String) redisTemplate.opsForValue().get(PREFIX + key);
        if (StringUtils.isNotBlank(value)) {
            authentication = (OAuth2Authentication) SerializationUtils.deserialize(toByteArray(value));
            redisTemplate.delete(key);
        }
        return authentication;
    }

    /**
     * 字节数组转成16进制表示格式的字符串
     *
     * @param byteArray 需要转换的字节数组
     * @return 16进制表示格式的字符串
     **/
    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10) {
                //0~F前面不零
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }

    public static byte[] toByteArray(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            throw new IllegalArgumentException("this hexString must not be empty");
        }

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
}
