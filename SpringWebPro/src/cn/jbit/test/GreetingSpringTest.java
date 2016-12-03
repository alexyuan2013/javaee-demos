package cn.jbit.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jbit.biz.GreetingSpring;

public class GreetingSpringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		GreetingSpring greetingSpring = (GreetingSpring) context
				.getBean("zhangGaSay");
		greetingSpring.sayGreeting();
	}

}
