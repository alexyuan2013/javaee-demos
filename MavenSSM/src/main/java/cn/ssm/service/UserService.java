package cn.ssm.service;

import java.util.List;
import java.util.Map;

import cn.ssm.entity.User;

public interface UserService {
	boolean addUser(User user);

	boolean editUser(User user);

	boolean deleteUser(Integer id);

	User getUserOne(Integer id);

	List<User> getUserAll(User user);

	User login(String name, int age);
}
