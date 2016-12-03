package cn.ssm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.ssm.entity.User;

@Repository
public interface UserMapper {
	Integer deleteByPrimaryKey(Integer id);

	Integer insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	Integer updateByPrimaryKeySelective(User record);

	List<User> selectAllSelective(User record);

	User selectByUserNameAndPwd(Map map);
}