package com.gateway;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class CryptoDemo {

	private final static Encoder encoder=Base64.getEncoder();
	private final static Decoder decoder=Base64.getDecoder();
	private static String defaultCharSet="UTF-8";
	
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	public static void main(String[] args) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, IllegalStateException {
		long time=System.currentTimeMillis();
		
		String hmacsha1=hmacsha1Encode(String.valueOf(time), "123456");
		String hmacbase641=base64Encode(hmacsha1);
		String httpbasic=base64Encode("zhangsan:"+hmacbase641);
		System.out.println(String.valueOf(time));
		System.out.println(httpbasic);
	}
	
	public void setDefaultCharSet(String charSet) {
		this.defaultCharSet=charSet;
	}
	
	public static String base64Encode(String s) throws UnsupportedEncodingException {
		return base64Encode(s,defaultCharSet);
	}
	
	public static String base64Encode(String s,String charSet) throws UnsupportedEncodingException {
		String result=null;
		if(s!=null&&s.length()>0) {
			result=encoder.encodeToString(s.getBytes(charSet));
		}
		return result;
	}
	
	public static String base64Decode(String s) throws UnsupportedEncodingException {
		return base64Decode(s,defaultCharSet);
	}
	
	public static String base64Decode(String s,String charSet) throws UnsupportedEncodingException {
		String result=null;
		if(s!=null&&s.length()>0) {
			byte[] b=decoder.decode(s);
			result=new String(b,charSet);
		}
		return result;
	}
	
	public static String hmacsha1Encode(String s,String key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
		return hmacsha1Encode(s,key,defaultCharSet);
	}

	public static String hmacsha1Encode(String s,String key,String charSet) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
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
}
