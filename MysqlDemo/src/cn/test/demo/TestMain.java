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

public class TestMain {
	
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
		//UserInfo zhangsan=new UserInfo();
		//zhangsan.setUsername("张三");
		//zhangsan.setPassword("123456");
		
		Role role = new Role();
		role.setRolename("admin");
		role.setRoletype(1);
		
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			//UserInfoMapper userInfoMapper=session.getMapper(UserInfoMapper.class);
			//userInfoMapper.insert(zhangsan);
			RoleMapper mapper = session.getMapper(RoleMapper.class);
			mapper.insert(role);
			session.commit();
			
			System.out.println("用户添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("用户添加失败");
		}finally {
			session.close();
		}
	}

}
