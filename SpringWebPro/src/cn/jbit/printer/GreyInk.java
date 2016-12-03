package cn.jbit.printer;

import java.awt.Color;

/**
 * 灰色墨盒。GreyInk实现Ink接口。
 * 
 * @author Mintie
 *
 */
public class GreyInk implements Ink {

	@Override
	public String getColor(int r, int g, int b) {
		// TODO Auto-generated method stub
		int c = (r + g + b) / 3;
		Color color = new Color(c, c, c);
		return "#" + Integer.toHexString(color.getRGB()).substring(2);
	}

}
