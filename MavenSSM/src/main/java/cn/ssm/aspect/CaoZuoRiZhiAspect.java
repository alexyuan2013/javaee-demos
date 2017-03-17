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
	 * ǰ��֪ͨ ����֮ǰ֪ͨ�ķ���
	 * 
	 * @param joinPoint
	 *            ���ӵ����
	 */
	public void beforeMethod(JoinPoint joinPoint) {
	}

	/**
	 * ����֪ͨ ��������֮��ص��ķ��� �����Ƿ��׳��쳣����ִ��
	 * 
	 * @param joinPoint
	 *            ���ӵ����
	 */
	public void afterMethod(JoinPoint joinPoint) {
	}

	/**
	 * ���÷���֪ͨ ��������֮��ص��ķ��� �׳��쳣����ִ��
	 * 
	 * @param joinPoint
	 * @param result
	 */
	public void afterReturning(JoinPoint joinPoint, Object result) {
	}

	/**
	 * �����׳��쳣֪ͨ ���׳��쳣 �ͻ�ִ�еķ���
	 * 
	 * @param joinPoint
	 *            ���ӵ�
	 * @param e
	 *            �׳��쳣����
	 */
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
	}

	/**
	 * ʹ�û���֪ͨ������־����
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
		// ��ȡsession�е��û�
		// ��̨��¼ ����session ��key �� value
		Integer dengLuId = (Integer) session.getAttribute("dengLuId");
		// web ��¼ ����session�� key �� value
		Object userObject = session.getAttribute("CURRENTUSER");
		// �����¼��־Ϊnull ��������־����
		if (dengLuId == null && userObject == null) {
			result = pjd.proceed();
			return result;
		}
		// ��ȡ����ip
		String ip = request.getRemoteAddr();

		// ��ȡ�û����󷽷��Ĳ��������л�ΪJSON��ʽ�ַ���
		String params = Arrays.asList(pjd.getArgs()).toString();

		// ������·��ǰ���/hdȥ��
		String path = request.getRequestURI();
		// ��ȡ��ǰ��Ŀ�ķ���·��
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
			// ��¼�����쳣��־
			System.out.println("-----------------------------------------�����쳣");
			caoZuoRiZhi.setCaoZuoYiChangMing(e.getMessage());
		}
		// ��������
		caoZuoRiZhiService.insertSelective(caoZuoRiZhi);
		return result;
	}
}
