package com.pcs.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pcs.model.UserDTO;

public class SecurityUtil {
	
	private static final String salt = "haberdash";
	private static final String secret = "12345";
	private static final String jwtIssuer = "PCS";
	private static final String algorithm = "AES/CBC/PKCS5Padding";
	private static final String encSecret = "password";
	private static final long expInterval = 20 * 60000;
	private static final JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secret))
			.withIssuer(jwtIssuer)
			.withClaimPresence("token")
			.build();
	
	public static String secureHash(String in) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		
		byte[] encodedhash = digest.digest(
		  in.getBytes(StandardCharsets.UTF_8));
		return bytesToHex(encodedhash);
		} catch (NoSuchAlgorithmException e) {
			// TODO fix logging
			e.printStackTrace();
			return null;
		}
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	public static String genAuthHeader(UserDTO user ) throws InvalidKeyException, IllegalArgumentException, JWTCreationException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException 
	{
		long now = System.currentTimeMillis();
		long expireAt = System.currentTimeMillis() + expInterval;
	    IvParameterSpec iv = generateIv();
		return JWT.create()
				.withIssuedAt(new Date(now))
				.withExpiresAt( new Date(expireAt))
				.withIssuer(jwtIssuer)
				.withClaim("token", encrypt(String.format("%d~%s~%s~%s", user.getUserId(), user.getEmail(), user.getPass(),salt),generateKey(), iv)+"%$%"+iv.toString())
				.sign(Algorithm.HMAC512(secret));
	}
	
	public static UserDTO validateAuthHeader(String header) {
		try {
			DecodedJWT decoded = verifier.verify(header);
			String[] a = decoded.getClaim("token").asString().split("%$%");
			String[] decrypted = decrypt(a[0],new IvParameterSpec(a[1].getBytes())).split("~");
			return new UserDTO(Integer.parseInt(decrypted[0]),decrypted[1],decrypted[2]);
			
		} catch(Exception e) {
			return null;
		}
	}
	
	private static String encrypt(String unencrypted, SecretKey key, IvParameterSpec iv) 
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException 
	{
	    Cipher cipher = Cipher.getInstance(algorithm);
	    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
	    byte[] cipherText = cipher.doFinal(unencrypted.getBytes());
	    return Base64.getEncoder()
	        .encodeToString(cipherText);
	}
	
	private static String decrypt(String encrypted, IvParameterSpec iv) 
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException 
	{
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.DECRYPT_MODE, generateKey(), iv);
		    byte[] plainText = cipher.doFinal(Base64.getDecoder()
		        .decode(encrypted));
		    return new String(plainText);
	}
	
	private static SecretKey generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	    KeySpec spec = new PBEKeySpec(encSecret.toCharArray(), salt.getBytes(), 65536, 256);
	    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
	        .getEncoded(), "AES");
	    return secret;
	}
	
	private static IvParameterSpec generateIv() {
	    byte[] iv = new byte[16];
	    new SecureRandom().nextBytes(iv);
	    return new IvParameterSpec(iv);
	}

}
