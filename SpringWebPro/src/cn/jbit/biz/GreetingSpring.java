package cn.jbit.biz;

public class GreetingSpring {
	// ˵������
	private String person = "Nobody";
	// ˵��������
	private String words = "nothing";

	/**
	 * ��� ˵�����ˡ�
	 * 
	 * @return ˵������
	 */
	public String getPerson() {
		return person;
	}

	/**
	 * ���� ˵�����ˡ�
	 * 
	 * @param person
	 *            ˵������
	 */
	public void setPerson(String person) {
		this.person = person;
	}

	/**
	 * ��� ˵�������ݡ�
	 * 
	 * @return ˵��������
	 */
	public String getWords() {
		return words;
	}

	/**
	 * ���� ˵�������ݡ�
	 * 
	 * @param words
	 *            ˵��������
	 */
	public void setWords(String words) {
		this.words = words;
	}

	/**
	 * ����˵��������
	 */
	public void sayGreeting() {
		System.out.println(person + "˵����" + words + "��");
	}
}
