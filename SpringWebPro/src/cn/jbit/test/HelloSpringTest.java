package cn.jbit.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jbit.biz.HelloSpring;

public class HelloSpringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ͨ��ClassPathXmlApplicationContextʵ����Spring��������
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		// ͨ��ApplicationContext��getBean()����������id����ȡbean��ʵ��
		HelloSpring helloSpring = (HelloSpring) context.getBean("helloSpring");
		// ִ��print()����
		helloSpring.print();
	}

}
