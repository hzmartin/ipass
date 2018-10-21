package ipass.util;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptorFactory {

	private static final Logger log = LoggerFactory.getLogger(EncryptorFactory.class);

	public static PooledPBEStringEncryptor createPooledPBEStringEncryptor() {
		// get pass from system prop or env
		String key = "IPASS_PASSWORD";
		String secret = System.getProperty(key);
		if (StringUtils.isBlank(secret)) {
			secret = System.getenv(key);
		}
		if (StringUtils.isBlank(secret)) {
			// default pass set
			secret = "iPass";
		}
		log.info("effective secret password: " + secret);
		return createPooledPBEStringEncryptor(secret);
	}

	public static PooledPBEStringEncryptor createPooledPBEStringEncryptor(String secret) {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(secret);
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("2048");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("hexadecimal");
		encryptor.setConfig(config);
		return encryptor;
	}
}
