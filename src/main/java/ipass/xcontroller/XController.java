package ipass.xcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ipass.service.IPassService;

@RestController
public class XController {

	@Value("${jasypt.test}")
	private String testJasypt;

	@Autowired
	private IPassService iPassService;

	@RequestMapping("/x/encrypt")
	public String encrypt(String password, String text) {
		return iPassService.testEncrypt(password, text);
	}

	@RequestMapping("/x/decrypt")
	public String decrypt(String password, String text) {
		return iPassService.testDecrypt(password, text);
	}

	@RequestMapping("/x/create")
	public String create() {
		iPassService.create();
		return "success";
	}

	@RequestMapping("/x/testJasypt")
	public String testJasypt() {
		return testJasypt;
	}

}
