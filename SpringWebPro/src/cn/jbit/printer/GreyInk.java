package cn.jbit.printer;

import java.awt.Color;

/**
 * ��ɫī�С�GreyInkʵ��Ink�ӿڡ�
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
