package ipass.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipass.domain.IPass;
import ipass.mapper.IPassMapper;

@Service
public class IPassService {
	@Autowired
	private IPassMapper iPassMapper;

	public List<IPass> selectAll() {
		return iPassMapper.selectAll();
	}

	public IPass selectById(Long id) {
		return iPassMapper.selectById(id);
	}
	
	public List<IPass> selectByAppUid(String appuid) {
		return iPassMapper.selectByAppUid(IPass.MASTER_UID, appuid);
	}

	public List<IPass> selectByKeyword(String keyword) {
		return iPassMapper.selectByKeyword(IPass.MASTER_UID, keyword);
	}

	public List<IPass> selectByAppUidKeyword(String appuid, String keyword) {
		return iPassMapper.selectByAppUidKeyword(IPass.MASTER_UID, appuid, keyword);
	}

	public int update(IPass o) {
		return iPassMapper.update(o);
	}

	public int delete(List<Long> ids) {
		return iPassMapper.delete(ids);
	}

	public int insert(IPass o) {
		return iPassMapper.insert(o);
	}

	public void create() {
		iPassMapper.create();
	}

}
