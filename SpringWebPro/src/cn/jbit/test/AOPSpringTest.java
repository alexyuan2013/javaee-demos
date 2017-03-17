package cn.jbit.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jbit.biz.IUserBiz;
import cn.jbit.entity.User;

public class AOPSpringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		IUserBiz biz = (IUserBiz) ctx.getBean("biz");

		User user = new User();
		user.setUserName("admin");
		user.setUserPassword("admin");

		biz.addNewUser(user);
	}

}
