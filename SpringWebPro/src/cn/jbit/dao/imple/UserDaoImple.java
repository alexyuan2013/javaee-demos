package cn.jbit.dao.imple;

import cn.jbit.dao.IUserDao;
import cn.jbit.entity.User;

public class UserDaoImple implements IUserDao {

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		System.out.println("用户添加成功");
	}

}
