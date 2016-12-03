package cn.jbit.biz;

public class HelloSpring {
	// 定义who属性，该属性的值将通过Spring框架进行设置
	private String who = null;

	/**
	 * 定义打印方法，输出一句完整的问候。
	 */
	public void print() {
		System.out.println("Hello," + this.getWho() + "!");
	}

	/**
	 * 获得 who。
	 * 
	 * @return who
	 */
	public String getWho() {
		return who;
	}

	/**
	 * 设置 who。
	 * 
	 * @param who
	 */
	public void setWho(String who) {
		this.who = who;
	}
}
