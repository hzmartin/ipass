package ipass.service;

import java.util.List;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ipass.domain.IPass;
import ipass.mapper.IPassMapper;
import ipass.util.EncryptorFactory;

@Service
public class IPassService {
	@Autowired
	private IPassMapper iPassMapper;

	@Autowired
	private StringEncryptor systemEncryptor;

	@Autowired
	private CacheManager cacheManager;

	public String testEncrypt(String password, String text) {
		PooledPBEStringEncryptor encryptor = EncryptorFactory.createPooledPBEStringEncryptor(password);
		return encryptor.encrypt(text);
	}

	public String testDecrypt(String password, String text) {
		PooledPBEStringEncryptor encryptor = EncryptorFactory.createPooledPBEStringEncryptor(password);
		return encryptor.decrypt(text);
	}

	public String encrypt(String text) {
		return systemEncryptor.encrypt(text);
	}

	public String decrypt(String text) {
		return systemEncryptor.decrypt(text);
	}

	@Cacheable(value = { "local" }, key = "'pass_all'")
	public List<IPass> selectAll() {
		List<IPass> list = iPassMapper.selectAll();
		for (IPass iPass : list) {
			iPass.setRawPassword(systemEncryptor.decrypt(iPass.getPassword()));
		}
		return list;
	}

	@Cacheable(value = { "local" }, key = "'pass_'.concat(#id)")
	public IPass selectById(Long id) {
		return iPassMapper.selectById(id);
	}

	public List<IPass> selectLike(String q) {
		List<IPass> list = iPassMapper.selectLike(IPass.MASTER_UID, q);
		for (IPass iPass : list) {
			iPass.setRawPassword(systemEncryptor.decrypt(iPass.getPassword()));
		}
		return list;
	}

	public List<IPass> selectByAppuidLike(String appuid, String q) {
		List<IPass> list = iPassMapper.selectByAppuidLike(IPass.MASTER_UID, appuid, q);
		for (IPass iPass : list) {
			iPass.setRawPassword(systemEncryptor.decrypt(iPass.getPassword()));
		}
		return list;
	}

	@Transactional
	@Caching(evict = { @CacheEvict(value = { "local" }, key = "'pass_'.concat(#o.id)"),
			@CacheEvict(value = { "local" }, key = "'pass_all'") })
	public int update(IPass o) {
		return iPassMapper.update(o);
	}

	public int delete(List<Long> ids) {
		int count = iPassMapper.delete(ids);
		Cache cache = cacheManager.getCache("local");
		for (Long id : ids) {
			cache.evict("pass_" + id);
		}
		return count;
	}

	@Caching(evict = { @CacheEvict(value = { "local" }, key = "'pass_all'") })
	public int insert(IPass o) {
		return iPassMapper.insert(o);
	}

	public void create() {
		iPassMapper.create();
		iPassMapper.createIndex();
	}

}
