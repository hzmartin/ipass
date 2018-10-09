package ipass.controller;

import java.util.List;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ipass.domain.IPass;
import ipass.service.IPassService;

@RestController
public class IPassController {

	@Value("${jasypt.test}")
	private String testJasypt;
	
	@Autowired
	private IPassService iPassService;

	@Autowired
	private StringEncryptor iEncryptor;

	@RequestMapping("/encrypt")
	public String encrypt(String text) {
		return iEncryptor.encrypt(text);
	}
	
	@RequestMapping("/testJasypt")
	public String testJasypt() {
		return testJasypt;
	}

	@RequestMapping("/create")
	public String create() {
		iPassService.create();
		return "success";
	}

	@RequestMapping("/selectAll")
	public List<IPass> selectAll() {
		return iPassService.selectAll();
	}

	@RequestMapping("/selectByAppUid")
	public List<IPass> selectByAppUid(@RequestParam String appuid) {
		return iPassService.selectByAppUid(appuid);
	}

	@RequestMapping("/insert")
	public int insert(@RequestParam String appuid, @RequestParam String password, @RequestParam String keyword,
			@RequestParam(required = false) String remark) {
		IPass o = new IPass();
		o.setAppuid(appuid);
		o.setPassword(password);
		o.setRemark(remark);
		o.setKeyword(keyword);
		return iPassService.insert(o);
	}
}
