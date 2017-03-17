package cn.jbit.printer;

import java.awt.Color;

/**
 * ��ɫī�С�ColorInkʵ��Ink�ӿڡ�
 * 
 * @author Mintie
 *
 */
public class ColorInk implements Ink {

	@Override
	public String getColor(int r, int g, int b) {
		// TODO Auto-generated method stub
		Color color = new Color(r, g, b);
		return "#" + Integer.toHexString(color.getRGB()).substring(2);
	}

}
