package dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import domain.Affair;

public interface AffairMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Affair record);

    int insertSelective(Affair record);

    Affair selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Affair record);

    int updateByPrimaryKey(Affair record);
    
    @Select("select * from affair")
    @ResultMap("dao.AffairMapper.BaseResultMap")
	public List<Affair> selectAll();
}