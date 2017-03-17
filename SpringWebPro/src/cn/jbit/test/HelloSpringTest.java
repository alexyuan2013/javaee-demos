package cn.jbit.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jbit.biz.HelloSpring;

public class HelloSpringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 通过ClassPathXmlApplicationContext实例化Spring的上下文
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		// 通过ApplicationContext的getBean()方法，根据id来获取bean的实例
		HelloSpring helloSpring = (HelloSpring) context.getBean("helloSpring");
		// 执行print()方法
		helloSpring.print();
	}

}
