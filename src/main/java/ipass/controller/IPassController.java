package ipass.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

	@RequestMapping("/selectByKeyword")
	public List<IPass> selectByKeyword(@RequestParam String keyword) {
		return iPassService.selectByKeyword(keyword);
	}

	@RequestMapping("/selectByAppUidKeyword")
	public List<IPass> selectByAppUidKeyword(@RequestParam String appuid, @RequestParam String keyword) {
		return iPassService.selectByAppUidKeyword(appuid, keyword);
	}

	@RequestMapping("/update")
	public int update(@RequestParam Long id, @RequestParam Long uid, @RequestParam String appuid,
			@RequestParam(required = false) String keyword, @RequestParam(required = false) String password,
			@RequestParam(required = false) String remark) {
		IPass pass = iPassService.selectById(id);
		if (pass == null) {
			return 0;
		}
		boolean update = false;
		pass.setUpdateTime(System.currentTimeMillis());
		if (!StringUtils.isBlank(appuid)) {
			pass.setAppuid(appuid);
			update = true;
		}
		if (!StringUtils.isBlank(keyword)) {
			pass.setAppuid(keyword);
			update = true;
		}
		if (!StringUtils.isBlank(remark)) {
			pass.setAppuid(remark);
			update = true;
		}
		if (update) {
			return iPassService.update(pass);
		} else {
			return 0;
		}
	}

	@RequestMapping("/delete")
	public int delete(@RequestParam(required = false) String ids, @RequestParam(required = false) Long id) {
		List<Long> idList = new ArrayList<Long>();
		if (id != null) {
			idList.add(id);
		}
		if (StringUtils.isNotBlank(ids)) {
			String[] idstrs = ids.split(",");
			for (String idstr : idstrs) {
				Long idd = Long.valueOf(idstr);
				idList.add(idd);
			}
		}
		if (idList.size() == 0) {
			return 0;
		}
		return iPassService.delete(idList);
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
