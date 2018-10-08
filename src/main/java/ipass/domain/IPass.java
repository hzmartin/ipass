package ipass.domain;

public class IPass {
	
	public static final Long MASTER_UID = 1L;

	private Long id;
	
	/** registered user **/
	private Long uid = MASTER_UID;
	
	/** app uid **/
	private String appuid;
	
	/** keyword used for search **/
	private String keyword;
	
	/** encrypted password **/
	private String password;
	
	/** remark for the password **/
	private String remark;
	
	private Long createTime = System.currentTimeMillis();
	
	private Long updateTime = System.currentTimeMillis();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getAppuid() {
		return appuid;
	}

	public void setAppuid(String appuid) {
		this.appuid = appuid;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
}
