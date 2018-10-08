package ipass;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement 
@MapperScan("ipass.mapper")
public class IpassApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpassApplication.class, args);
	}
}
