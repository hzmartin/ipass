package ipass.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ipass.domain.IPass;
import ipass.mapper.IPassMapper;

@Service
public class IPassService {
	@Autowired
	private IPassMapper iPassMapper;

	public List<IPass> selectAll() {
		return iPassMapper.selectAll();
	}
	
	public List<IPass> selectByAppUid(String appuid) {
		return iPassMapper.selectByAppUid(IPass.MASTER_UID, appuid);
	}
	
	@Transactional
	public int insert(IPass o) {
		return iPassMapper.insert(o);
	}

	public void create() {
		iPassMapper.create();
	}

}
