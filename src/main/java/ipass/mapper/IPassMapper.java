package ipass.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ipass.domain.IPass;

public interface IPassMapper {

	@Update("CREATE TABLE IPASS" + 
			"(" + 
			"ID BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 100, INCREMENT BY 1)," + 
			"UID BIGINT," + 
			"APPUID VARCHAR(256)," + 
			"KEYWORD VARCHAR(256)," + 
			"PASSWORD VARCHAR(1024)," + 
			"REMARK VARCHAR(1024)," + 
			"CREATE_TIME BIGINT," + 
			"UPDATE_TIME BIGINT," + 
			"PRIMARY KEY (ID)" +
			")")
	void create();
	
	@Update("CREATE INDEX IPASS_UID ON IPASS (UID, APPUID)")
	void createIndex();
	
	List<IPass> selectAll();

	@Select("select * from ipass where id = #{id}")
	IPass selectById(@Param("id") Long id);
	
	@Select("select count(1) from ipass")
	Integer count();
	
	@Select("select * from ipass where uid = #{uid} and appuid like '%${appuid}%' and (keyword like '%${q}%' or remark like '%${q}%')")
	List<IPass> selectByAppuidLike(@Param("uid") Long uid, @Param("appuid") String appuid, @Param("q") String q);

	@Select("select * from ipass where uid = #{uid} and (appuid like '%${q}%' or keyword like '%${q}%' or remark like '%${q}%')")
	List<IPass> selectLike(@Param("uid") Long uid, @Param("q") String q);

	@Insert("insert into ipass(uid,appuid,keyword,password,remark, create_time, update_time) values(#{o.uid}, #{o.appuid}, #{o.keyword}, #{o.password}, #{o.remark, jdbcType=VARCHAR}, #{o.createTime}, #{o.updateTime})")
	int insert(@Param("o") IPass obj);

	@Delete("<script>delete from ipass where id in <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>#{item}</foreach></script>")
	int delete(@Param("ids") List<Long> ids);

	@Update("update ipass set appuid = #{o.appuid}, password = #{o.password}, remark = #{o.remark}, keyword = #{o.keyword}, update_time = #{o.updateTime} where id = #{o.id} and uid = #{o.uid}")
	int update(@Param("o") IPass obj);

}
