package ipass.xcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ipass.service.IPassService;

@RestController
public class XController {

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

}
