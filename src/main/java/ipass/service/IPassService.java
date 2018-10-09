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

	public List<IPass> selectLike(String q) {
		return iPassMapper.selectLike(IPass.MASTER_UID, q);
	}

	public List<IPass> selectByAppuidLike(String appuid, String q) {
		return iPassMapper.selectByAppuidLike(IPass.MASTER_UID, appuid, q);
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
		iPassMapper.createIndex();
	}

}
