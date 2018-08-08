package com.gateway.services;

import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;


/**
 * Created by daip on 2018/8/7.
 */
@Service
public class CryptoService {
    private final static Encoder encoder=Base64.getEncoder();
    private final static Decoder decoder=Base64.getDecoder();
    private volatile String defaultCharSet="UTF-8";

    private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public void setDefaultCharSet(String charSet) {
        this.defaultCharSet=charSet;
    }

    //base64加密
    public String base64Encode(String s) throws UnsupportedEncodingException {
        return base64Encode(s,defaultCharSet);
    }

    public String base64Encode(String s,String charSet) throws UnsupportedEncodingException {
        String result=null;
        if(s!=null&&s.length()>0) {
            result=encoder.encodeToString(s.getBytes(charSet));
        }
        return result;
    }

    //base64解密
    public String base64Decode(String s) throws UnsupportedEncodingException {
        return base64Decode(s,defaultCharSet);
    }

    public String base64Decode(String s,String charSet) throws UnsupportedEncodingException {
        String result=null;
        if(s!=null&&s.length()>0) {
            byte[] b=decoder.decode(s);
            result=new String(b,charSet);
        }
        return result;
    }

    //hmacsha1加密
    public String hmacsha1Encode(String s,String key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
        return hmacsha1Encode(s,key,defaultCharSet);
    }

    public String hmacsha1Encode(String s,String key,String charSet) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
        String result=null;
        SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(charSet), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signinKey);
        byte[] rawHmac = mac.doFinal(s.getBytes(charSet));
        if(rawHmac!=null&&rawHmac.length>0) {
            result="";
            for(int i=0;i<rawHmac.length;i++) {
                result+=Integer.toHexString(rawHmac[i] & 0XFF);
            }
        }
        return result;
    }

    public String getUsernameByToken(String token){
        String firstDecodeStr =null;
        try {
            firstDecodeStr = base64Decode(token, defaultCharSet);
        }
        catch (Exception e){
            return null;
        }
        if(firstDecodeStr!=null&&firstDecodeStr.indexOf(":")>0){

            return firstDecodeStr.split(":")[0].trim();
        }
        return null;
    }
}
