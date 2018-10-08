package ipass.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ipass.domain.IPass;

public interface IPassMapper {

	@Select("select * from ipass")
	List<IPass> selectAll();

	@Select("select count(1) from ipass")
	Integer count();
	
//	@Select("select * from ipass order by id asc {limit ${limit} offset ${offset}}")
//	List<IPass> selectSeveral(@Param("limit") int limit, @Param("offset") int offset) throws Exception;
//
	@Insert("insert into ipass(uid,appuid,keyword,password,remark, create_time, update_time) values(#{o.uid}, #{o.appuid}, #{o.keyword}, #{o.password}, #{o.remark, jdbcType=VARCHAR}, #{o.createTime}, #{o.updateTime})")
	int insert(@Param("o") IPass obj);
	
//	@Delete("<script>delete from ipass where id in <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>#{item}</foreach></script>")
//	void delete(@Param("ids") List<String> ids) throws Exception;
//
//	@Update("update ipass set name = #{o.name}, age = #{o.age}, sex = #{o.sex} where id = #{o.id}")
//	void update(@Param("o") IPass obj) throws Exception;

}
