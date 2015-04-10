package com.leo.common.util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密算法，JCE可用DES,DESede和Blowfish
 * @author leo
 * 
 */
public class JCEUtil {

	static byte[] wrap(String algo, byte[] key, byte[] iv, byte[] data, int mode) throws Exception {
		Cipher cipher = Cipher.getInstance(algo + "/CBC/PKCS5PADDING");
		SecretKeySpec skey = new SecretKeySpec(key, 0, Math.min(key.length, Cipher.getMaxAllowedKeyLength(algo) / 8), algo);
		IvParameterSpec ivParam = new IvParameterSpec(iv, 0, cipher.getBlockSize());
		cipher.init(mode, skey, ivParam);
		return cipher.doFinal(data);
	}

	static byte[] encrypt(String algo, byte[] key, byte[] iv, byte[] data) throws Exception {
		return wrap(algo, key, iv, data, Cipher.ENCRYPT_MODE);
	}

	static byte[] decrypt(String algo, byte[] key, byte[] iv, byte[] data) throws Exception {
		return wrap(algo, key, iv, data, Cipher.DECRYPT_MODE);
	}

	static byte[] key(String key) throws Exception {
		return MessageDigest.getInstance("MD5").digest(key.getBytes("UTF-8"));
	}

	static byte[] iv(long iv) {
		byte[] b = new byte[32];
		ByteBuffer.wrap(b).putLong(iv);
		return b;
	}

	static String encrypt(String algo, String key, long iv, String text) throws Exception {
		byte[] ciphertext = encrypt(algo, key(key), iv(iv), text.getBytes("UTF-8"));
		return CHexConver.byte2HexStr(ciphertext);
	}

	static String decrypt(String algo, String key, long iv, byte[] data) throws Exception {
		return new String(decrypt(algo, key(key), iv(iv), data), "UTF-8");
	}
	
	
	static String decrypt(String algo, String key, long iv, String hexCiphertext) throws Exception {
		return new String(decrypt(algo, key(key), iv(iv), CHexConver.hexStr2Bytes(hexCiphertext)), "UTF-8");
	}

	public static void main(String[] args) throws Exception {
		// 初始向量需要明文传递给解密者，以使相同的明文每次都产生不同的密文。
		for (int i = 0; i < 8; i++) {
			String algo = i < 4 ? "Blowfish" : "DES";
			// long iv = System.nanoTime();
			long iv = i; // 每次运行都会输出相同结果
			String data = encrypt(algo, "a", iv, algo + " encrypt/decrypt sample.");
			System.out.print("iv=" + Long.toHexString(iv) + "\t");
			System.out.print(data);
			
			System.out.println();
			
			String text = decrypt(algo, "a", iv, data);
			System.out.println(text);
		}
	}

}
