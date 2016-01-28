package basic;

import static org.junit.Assert.*;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
 
import dao.AffairMapper;
import domain.Affair;
import utils.JsonUtil;

public class Mybatis {
	 private  static SqlSessionFactory sqlSessionFactory=null;
	    static {
	        String resource = "mybatis-config.xml";
	        Reader reader = null;
	        try {
	            reader = Resources.getResourceAsReader(resource);
	            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            System.out.println(e.getMessage());
	        }
	    }
 
	    public static SqlSessionFactory getSqlSessionFactory() {
	        return sqlSessionFactory;
	    }
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void insert(){
		SqlSession sqlSession=sqlSessionFactory.openSession();
		try {
			AffairMapper mapper=sqlSession.getMapper(AffairMapper.class);
			Affair a=new Affair();
			a.setWhat("这是我的第三个测试");
			mapper.insert(a);
			sqlSession.commit();
			System.out.println("success");
        }catch(Exception e){ 
        	e.printStackTrace();
        } finally {
            sqlSession.close();
        }
	}
	@Test
	public void all(){
		SqlSession sqlSession=null;
		try {
			sqlSession=sqlSessionFactory.openSession();
			AffairMapper mapper=sqlSession.getMapper(AffairMapper.class);
			List<Affair> list=mapper.selectAll();
			for (Affair affair : list) {
				System.out.println(JsonUtil.ob2json(affair));
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(sqlSession!=null)
				sqlSession.close();
		}
	}
	@Test
	public void update(){
		SqlSession sqlSession=null;
		try {
		 sqlSession=sqlSessionFactory.openSession();
			AffairMapper mapper=sqlSession.getMapper(AffairMapper.class);
			Affair record=new Affair();
			record.setId(1);
			record.setWhat("不可能");
			int i=mapper.updateByPrimaryKeySelective(record);
			System.out.println(i);
		}catch(Exception e){
        	e.printStackTrace();
        } finally {
        	if(sqlSession!=null)
			sqlSession.close();
		}
	}
	@Test
	public void selectOne(){
		SqlSession sqlSession=null;
		try {
		 sqlSession=sqlSessionFactory.openSession();
			AffairMapper mapper=sqlSession.getMapper(AffairMapper.class);
			Affair a=mapper.selectByPrimaryKey(1);
			System.err.println(JsonUtil.ob2json(a));
			System.err.println(a.getCreateTime());
			
		}catch(Exception e){
        	e.printStackTrace();
        } finally {
        	if(sqlSession!=null)
			sqlSession.close();
		}
	}
}
