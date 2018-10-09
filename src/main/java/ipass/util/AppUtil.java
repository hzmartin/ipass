package ipass.util;

import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class AppUtil {

	public static String get32UUid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * natural order, sha1hex
	 */
	public static final String sign(String... params) {
		if (params == null) {
			throw new IllegalArgumentException("params required");
		}
		for (String param : params) {
			if (param == null) {
				throw new IllegalArgumentException("param illegal");
			}
		}
		Arrays.sort(params);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < params.length; i++) {
			sb.append(params[i]);
		}
		return DigestUtils.sha1Hex(sb.toString());
	}

	public static void main(String[] args) {
		System.out.println("appkey: " + get32UUid());
		System.out.println("secret: " + get32UUid());
		System.out.println(sign("a", "b"));
	}
}
