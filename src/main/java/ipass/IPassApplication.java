package ipass;

import javax.sql.DataSource;

import org.jasypt.encryption.StringEncryptor;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

import com.alibaba.druid.pool.DruidDataSource;

import ipass.util.EncryptorFactory;

@SpringBootApplication
@MapperScan("ipass.mapper")
@EnableCaching
public class IPassApplication {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(IPassApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(IPassApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource druidDataSource() {
		return new DruidDataSource();
	}

	@Bean(name = "systemEncryptor")
	public StringEncryptor stringEncryptor() {
		return EncryptorFactory.createPooledPBEStringEncryptor();
	}
	
	@Bean
	public CacheManager cacheManager(){
		return new ConcurrentMapCacheManager("local");
	}
	
}
