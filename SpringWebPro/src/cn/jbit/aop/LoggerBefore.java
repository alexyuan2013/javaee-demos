package cn.jbit.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * ͨ��MethodBeforeAdviceʵ��ǰ����ǿ
 * 
 * @author Mintie
 *
 */
public class LoggerBefore implements MethodBeforeAdvice {
	private static final Logger log = Logger.getLogger(LoggerBefore.class);

	@Override
	public void before(Method method, Object[] arguments, Object target)
			throws Throwable {
		log.info("���� " + target + " �� " + method.getName() + " ������������Σ�"
				+ Arrays.toString(arguments));
	}

}
