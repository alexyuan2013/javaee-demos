package cn.jbit.printer;

/**
 * ī�нӿ�
 * 
 * @author Mintie
 *
 */
public interface Ink {
	/**
	 * �����ӡ���õ���ɫ�ķ�����
	 * 
	 * @param r
	 *            ��ɫֵ
	 * @param g
	 *            ��ɫֵ
	 * @param b
	 *            ��ɫֵ
	 * @return ���ش�ӡ���õ���ɫ
	 */
	public String getColor(int r, int g, int b);
}
