package cn.jbit.biz;

public class HelloSpring {
	// ����who���ԣ������Ե�ֵ��ͨ��Spring��ܽ�������
	private String who = null;

	/**
	 * �����ӡ���������һ���������ʺ�
	 */
	public void print() {
		System.out.println("Hello," + this.getWho() + "!");
	}

	/**
	 * ��� who��
	 * 
	 * @return who
	 */
	public String getWho() {
		return who;
	}

	/**
	 * ���� who��
	 * 
	 * @param who
	 */
	public void setWho(String who) {
		this.who = who;
	}
}
