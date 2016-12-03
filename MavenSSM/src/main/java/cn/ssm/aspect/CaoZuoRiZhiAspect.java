package cn.ssm.aspect;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.ssm.entity.CaoZuoRiZhi;
import cn.ssm.service.CaoZuoRiZhiService;

public class CaoZuoRiZhiAspect {

	@Autowired
	private CaoZuoRiZhiService caoZuoRiZhiService;

	/**
	 * 前置通知 操作之前通知的方法
	 * 
	 * @param joinPoint
	 *            链接点对象
	 */
	public void beforeMethod(JoinPoint joinPoint) {
	}

	/**
	 * 后置通知 操作方法之后回调的方法 不管是否抛出异常都会执行
	 * 
	 * @param joinPoint
	 *            链接点对象
	 */
	public void afterMethod(JoinPoint joinPoint) {
	}

	/**
	 * 后置返回通知 操作方法之后回调的方法 抛出异常不会执行
	 * 
	 * @param joinPoint
	 * @param result
	 */
	public void afterReturning(JoinPoint joinPoint, Object result) {
	}

	/**
	 * 后置抛出异常通知 当抛出异常 就会执行的方法
	 * 
	 * @param joinPoint
	 *            连接点
	 * @param e
	 *            抛出异常对象
	 */
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
	}

	/**
	 * 使用环绕通知进行日志拦截
	 * 
	 * @param pjd
	 * @return
	 * @throws Throwable
	 */
	public Object aroundMethod(ProceedingJoinPoint pjd) throws Throwable {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		Object result = null;
		// 读取session中的用户
		// 后台登录 放在session 的key 和 value
		Integer dengLuId = (Integer) session.getAttribute("dengLuId");
		// web 登录 放在session的 key 和 value
		Object userObject = session.getAttribute("CURRENTUSER");
		// 如果登录日志为null 不进行日志拦截
		if (dengLuId == null && userObject == null) {
			result = pjd.proceed();
			return result;
		}
		// 获取请求ip
		String ip = request.getRemoteAddr();

		// 获取用户请求方法的参数并序列化为JSON格式字符串
		String params = Arrays.asList(pjd.getArgs()).toString();

		// 把请求路径前面的/hd去掉
		String path = request.getRequestURI();
		// 获取当前项目的发布路径
		String contextPath = request.getContextPath();
		path = path.replace(contextPath, "");

		CaoZuoRiZhi caoZuoRiZhi = new CaoZuoRiZhi();

		caoZuoRiZhi.setCaoZuoRenIP(ip);
		caoZuoRiZhi.setDengLuId(dengLuId);
		caoZuoRiZhi.setCaoZuoLuJing(path);
		caoZuoRiZhi.setCaoZuoShiJian(new Date());

		caoZuoRiZhi.setCaoZuoCanShu(params);

		try {
			result = pjd.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 记录本地异常日志
			System.out.println("-----------------------------------------出现异常");
			caoZuoRiZhi.setCaoZuoYiChangMing(e.getMessage());
		}
		// 插入数据
		caoZuoRiZhiService.insertSelective(caoZuoRiZhi);
		return result;
	}
}
