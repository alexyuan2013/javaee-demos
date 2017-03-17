package cn.jbit.biz;

public class GreetingSpring {
	// 说话的人
	private String person = "Nobody";
	// 说话的内容
	private String words = "nothing";

	/**
	 * 获得 说话的人。
	 * 
	 * @return 说话的人
	 */
	public String getPerson() {
		return person;
	}

	/**
	 * 设置 说话的人。
	 * 
	 * @param person
	 *            说话的人
	 */
	public void setPerson(String person) {
		this.person = person;
	}

	/**
	 * 获得 说话的内容。
	 * 
	 * @return 说话的内容
	 */
	public String getWords() {
		return words;
	}

	/**
	 * 设置 说话的内容。
	 * 
	 * @param words
	 *            说话的内容
	 */
	public void setWords(String words) {
		this.words = words;
	}

	/**
	 * 定义说话方法。
	 */
	public void sayGreeting() {
		System.out.println(person + "说：“" + words + "”");
	}
}
