package ipass;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ipass.mapper")
public class IPassApplication {

	public static void main(String[] args) {
		SpringApplication.run(IPassApplication.class, args);
	}
}
