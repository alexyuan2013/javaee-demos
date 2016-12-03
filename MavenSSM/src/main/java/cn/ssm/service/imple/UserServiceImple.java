package cn.ssm.service.imple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ssm.dao.UserMapper;
import cn.ssm.entity.User;
import cn.ssm.service.UserService;

@Service
public class UserServiceImple implements UserService {
	@Autowired
	private UserMapper dao;

	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		if (dao.insertSelective(user) > 0) {
			return true;
		}
		return false;
	}

	public boolean editUser(User user) {
		// TODO Auto-generated method stub
		if (dao.updateByPrimaryKeySelective(user) > 0) {
			return true;
		}
		return false;
	}

	public boolean deleteUser(Integer id) {
		// TODO Auto-generated method stub
		if (dao.deleteByPrimaryKey(id) > 0) {
			return true;
		}
		return false;
	}

	public User getUserOne(Integer id) {
		// TODO Auto-generated method stub
		return dao.selectByPrimaryKey(id);
	}

	public List<User> getUserAll(User user) {
		// TODO Auto-generated method stub
		return dao.selectAllSelective(user);
	}

	public User login(String name, int age) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		map.put("pwd", age);
		
		return dao.selectByUserNameAndPwd(map);
	}

	// public List<User> selectAllBySelective(Integer id, String name) {
	// // TODO Auto-generated method stub
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("userId", id);
	// map.put("userName", name);
	//
	// return dao.selectAllBySelective(map);
	// }

}
