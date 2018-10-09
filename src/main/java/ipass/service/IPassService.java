package ipass.service;

import java.util.List;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipass.domain.IPass;
import ipass.mapper.IPassMapper;

@Service
public class IPassService {
	@Autowired
	private IPassMapper iPassMapper;

	@Autowired
	private StringEncryptor iEncryptor;

	public String testEncrypt(String password, String text) {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(password);
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("2048");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("hexadecimal");
		encryptor.setConfig(config);
		return encryptor.encrypt(text);
	}

	public String testDecrypt(String password, String text) {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(password);
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("2048");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("hexadecimal");
		encryptor.setConfig(config);
		return encryptor.decrypt(text);
	}

	public String encrypt(String text) {
		return iEncryptor.encrypt(text);
	}

	public String decrypt(String text) {
		return iEncryptor.decrypt(text);
	}

	public List<IPass> selectAll() {
		List<IPass> list = iPassMapper.selectAll();
		for (IPass iPass : list) {
			iPass.setRawPassword(iEncryptor.decrypt(iPass.getPassword()));
		}
		return list;
	}

	public IPass selectById(Long id) {
		return iPassMapper.selectById(id);
	}

	public List<IPass> selectLike(String q) {
		List<IPass> list = iPassMapper.selectLike(IPass.MASTER_UID, q);
		for (IPass iPass : list) {
			iPass.setRawPassword(iEncryptor.decrypt(iPass.getPassword()));
		}
		return list;
	}

	public List<IPass> selectByAppuidLike(String appuid, String q) {
		List<IPass> list = iPassMapper.selectByAppuidLike(IPass.MASTER_UID, appuid, q);
		for (IPass iPass : list) {
			iPass.setRawPassword(iEncryptor.decrypt(iPass.getPassword()));
		}
		return list;
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
