package dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import domain.Beauty;

public interface BeautyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Beauty record);

    int insertSelective(Beauty record);

    Beauty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Beauty record);

    int updateByPrimaryKey(Beauty record);
    
    @Select("select * from beauty")
    @ResultMap("dao.BeautyMapper.BaseResultMap")
	public List<Beauty> selectAll();
}