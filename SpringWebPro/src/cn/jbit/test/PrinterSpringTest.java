package cn.jbit.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jbit.printer.Printer;

public class PrinterSpringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		// ͨ��Printer bean��id����ȡPrinterʵ��
		Printer printer = (Printer) context.getBean("printer");

		String content = "��λ�����������������������ض���˵����Щ�����ǳ���"
				+ "�ã���Ϊ����ʵ���ˡ����Ʒ�ת����������˵����������Ի󣺿�"
				+ "�Ʒ�ת�ǿ�������е����������������Ϊʹ���˿��Ʒ�ת����Ϊ"
				+ "��Щ�������������ڲ�ͬ���ͺ�����˵���ҵĽγ������ڲ�ͬ�ģ�" + "��Ϊ����4�����ӡ���";
		printer.print(content);
	}

}
