package ipass;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.alibaba.druid.pool.DruidDataSource;

@SpringBootApplication
@MapperScan("ipass.mapper")
public class IPassApplication {

	private static final Logger log = LoggerFactory.getLogger(IPassApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(IPassApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource druidDataSource() {
		return new DruidDataSource();
	}

	@Bean(name = "iEncryptor")
	public StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		String key = "IPASS_PASSWORD";
		String jasyptPassword = System.getProperty(key);
		if (StringUtils.isBlank(jasyptPassword)) {
			jasyptPassword = System.getenv(key);
		}
		if (StringUtils.isBlank(jasyptPassword)) {
			jasyptPassword = "iPass";
		}
		log.info("effective jasypt password: " + jasyptPassword);
		config.setPassword(jasyptPassword);
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
