package cn.jbit.printer;

/**
 * ֽ�Žӿ�
 * 
 * @author Mintie
 *
 */
public interface Paper {
	public static final String newline = "\r\n";

	/**
	 * ���һ���ַ���ֽ�š�
	 */
	public void putInChar(char c);

	/**
	 * �õ������ֽ���ϵ����ݡ�
	 */
	public String getContent();
}
