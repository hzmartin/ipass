package ipass;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
 
import com.alibaba.druid.pool.DruidDataSource;
 
@Configuration
public class DruidDataSourceConfiguration {
	@Bean  
	@Primary
    @ConfigurationProperties(prefix = "spring.datasource") 
    public DataSource druidDataSource() {  
        return new DruidDataSource();  
    }  
}