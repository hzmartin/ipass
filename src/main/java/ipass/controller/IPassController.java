package ipass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ipass.domain.IPass;
import ipass.service.IPassService;

@RestController
public class IPassController {

	@Autowired
	private IPassService iPassService;

	@RequestMapping("/selectAll")
	public List<IPass> selectAll() {
		return iPassService.selectAll();
	}
	
	@RequestMapping("/selectByAppUid")
	public List<IPass> selectByAppUid(@RequestParam String appuid) {
		return iPassService.selectByAppUid(appuid);
	}

	@RequestMapping("/insert")
	public int insert(@RequestParam String appuid, 
			@RequestParam String password,
			@RequestParam String keyword,
			@RequestParam(required = false) String remark) {
		IPass o = new IPass();
		o.setAppuid(appuid);
		o.setPassword(password);
		o.setRemark(remark);
		o.setKeyword(keyword);
		return iPassService.insert(o);
	}
}
