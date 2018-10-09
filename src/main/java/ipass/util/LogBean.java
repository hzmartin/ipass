package ipass.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class LogBean {

	private static Logger logger = LoggerFactory.getLogger(LogBean.class);

	private String path = "";

	private int code = 200;

	private long spendTime = 0;

	private long beginTime = 0;

	private Object headers;

	private Object params;

	private Object payload;

	private Map<String, Object> props = new LinkedHashMap<String, Object>();

	private Object result;

	private Object error;

	private boolean disable = false;

	private String ip = "";

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public long getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(long spendTime) {
		this.spendTime = spendTime;
	}

	public Object getHeaders() {
		return headers;
	}

	public void setHeaders(Object headers) {
		this.headers = headers;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}

	public LogBean addProp(String key, Object value) {
		if (this.getProps() != null) {
			this.getProps().put(key, (value == null ? "null" : value));
		}
		return this;
	}

	public Object delProp(String key) {
		if (this.getProps() != null) {
			return this.getProps().remove(key);
		}
		return null;
	}

	public Object getProp(String key) {
		if (this.getProps() != null) {
			return this.getProps().get(key);
		}
		return null;
	}

	public void log() {
		if (!this.disable) {
			logger.info(JSON.toJSONString(this));
		}
	}

	public void print() {
		this.print(System.currentTimeMillis());
	}

	public void print(long now) {
		this.setSpendTime(now - this.getBeginTime());
		this.log();
	}

	private static final ThreadLocal<LogBean> LOG_BEAN_THREAD_LOCAL = ThreadLocal.withInitial(new Supplier<LogBean>() {
		@Override
		public LogBean get() {
			return new LogBean();
		}
	});

	public static LogBean get() {
		return LOG_BEAN_THREAD_LOCAL.get();
	}

	public static void remove() {
		LOG_BEAN_THREAD_LOCAL.remove();
	}

	public static LogBean start() {
		LogBean logBean = get();
		logBean.setBeginTime(System.currentTimeMillis());
		return logBean;
	}

	public static void end() {
		get().print();
		remove();
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}
}
