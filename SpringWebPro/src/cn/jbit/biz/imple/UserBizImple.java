package cn.jbit.biz.imple;

import cn.jbit.biz.IUserBiz;
import cn.jbit.dao.IUserDao;
import cn.jbit.entity.User;

public class UserBizImple implements IUserBiz {
	// 声明接口类型的引用，和具体实现类解耦合
	private IUserDao dao;

	// dao 属性的setter访问器，会被Spring调用，实现设值注入
	public void setDao(IUserDao dao) {
		this.dao = dao;
	}

	@Override
	public void addNewUser(User user) {
		// TODO Auto-generated method stub
		dao.save(user);
	}

}
