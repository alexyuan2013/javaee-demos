package cn.jbit.printer;

/**
 * ��ӡ��
 * 
 * @author Mintie
 *
 */
public class Printer {
	// ����ӿڱ�̣������Ǿ����ʵ����
	private Ink ink = null;
	// ����ӿڱ�̣������Ǿ����ʵ����
	private Paper paper = null;

	/**
	 * ��ֵע�������setter������
	 * 
	 * @param ink
	 *            ����ī�в���
	 */
	public void setInk(Ink ink) {
		this.ink = ink;
	}

	/**
	 * ��ֵע�������setter������
	 * 
	 * @param paper
	 *            ����ֽ�Ų���
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	/**
	 * ��ӡ����ӡ������
	 * 
	 * @param str
	 *            �����ӡ����
	 */
	public void print(String str) {
		// �����ɫ���
		System.out.println("ʹ��" + ink.getColor(255, 200, 0) + "��ɫ��ӡ:\n");
		// ���ַ������ֽ��
		for (int i = 0; i < str.length(); ++i) {
			paper.putInChar(str.charAt(i));
		}
		// ��ֽ�ŵ��������
		System.out.print(paper.getContent());
	}
}
