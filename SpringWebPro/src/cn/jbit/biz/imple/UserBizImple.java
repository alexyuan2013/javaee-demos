package cn.jbit.biz.imple;

import cn.jbit.biz.IUserBiz;
import cn.jbit.dao.IUserDao;
import cn.jbit.entity.User;

public class UserBizImple implements IUserBiz {
	// �����ӿ����͵����ã��;���ʵ��������
	private IUserDao dao;

	// dao ���Ե�setter���������ᱻSpring���ã�ʵ����ֵע��
	public void setDao(IUserDao dao) {
		this.dao = dao;
	}

	@Override
	public void addNewUser(User user) {
		// TODO Auto-generated method stub
		dao.save(user);
	}

}
