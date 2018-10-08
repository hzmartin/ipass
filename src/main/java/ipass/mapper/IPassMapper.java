package ipass.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ipass.domain.IPass;

public interface IPassMapper {

	@Select("select * from ipass")
	List<IPass> selectAll();

	@Select("select count(1) from ipass")
	Integer count();

	@Select("select * from ipass where uid = #{uid} and appuid = #{appuid}")
	List<IPass> selectByAppUid(@Param("uid") Long uid, @Param("appuid") String appuid);

	@Select("select * from ipass where uid = #{uid} and appuid = #{appuid} and keyword like '%#{keyword}%'")
	List<IPass> selectByAppUidKeyword(@Param("uid") Long uid, @Param("appuid") String appuid, @Param("keyword") String keyword);
	
	@Select("select * from ipass where uid = #{uid} and keyword like '%#{keyword}%'")
	List<IPass> selectByKeyword(@Param("uid") Long uid, @Param("keyword") String keyword);

	@Insert("insert into ipass(uid,appuid,keyword,password,remark, create_time, update_time) values(#{o.uid}, #{o.appuid}, #{o.keyword}, #{o.password}, #{o.remark, jdbcType=VARCHAR}, #{o.createTime}, #{o.updateTime})")
	int insert(@Param("o") IPass obj);

	@Delete("<script>delete from ipass where id in <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>#{item}</foreach></script>")
	void delete(@Param("ids") List<Long> ids);

	@Update("update ipass set password = #{o.password}, remark = #{o.remark}, keyword = #{o.keyword}, update_time = #{o.updateTime} where uid = #{uid} and appuid = #{o.appuid}")
	void update(@Param("o") IPass obj);

}
