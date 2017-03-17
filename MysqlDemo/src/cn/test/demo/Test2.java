package cn.test.demo;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import cn.test.entity.Role;
import cn.test.entity.UserInfo;
import cn.test.mapping.RoleMapper;
import cn.test.mapping.UserInfoMapper;

public class Test2 {
	
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	
	static {
		try {
			reader = Resources.getResourceAsReader("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}
	
	public static void main(String[] args) {
		
		SqlSession session = sqlSessionFactory.openSession();
		try{
//			RoleMapper mapper = session.getMapper(RoleMapper.class);
//			Role role = mapper.selectByPrimaryKey(1);
			UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
			UserInfo user = mapper.selectByName("ÕÅÈý");
			if(user!=null){
				System.out.println(user.getUsername());
			}
		} catch (Exception e){
			
		} finally {
			session.close();
		}
	}

}
