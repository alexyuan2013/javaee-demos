package cn.jbit.printer;

/**
 * 墨盒接口
 * 
 * @author Mintie
 *
 */
public interface Ink {
	/**
	 * 定义打印采用的颜色的方法。
	 * 
	 * @param r
	 *            红色值
	 * @param g
	 *            绿色值
	 * @param b
	 *            蓝色值
	 * @return 返回打印采用的颜色
	 */
	public String getColor(int r, int g, int b);
}
