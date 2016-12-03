package cn.jbit.printer;

/**
 * �ı���ӡֽ��ʵ�֡�TextPaperʵ��Paper�ӿڡ�
 * 
 * @author Mintie
 *
 */
public class TextPaper implements Paper {
	// ÿ���ַ���
	private int charPerLine = 16;
	// ÿҳ����
	private int linePerPage = 5;
	// ֽ��������
	private String content = "";
	// ��ǰ����λ�ã���0��charPerLine-1
	private int posX = 0;
	// ��ǰ��������0��linePerPage-1
	private int posY = 0;
	// ��ǰҳ��
	private int posP = 1;

	@Override
	public void putInChar(char c) {
		// TODO Auto-generated method stub
		content += c;
		++posX;
		// �ж��Ƿ���
		if (posX == charPerLine) {
			content += Paper.newline;
			posX = 0;
			++posY;
		}
		// �ж��Ƿ�ҳ
		if (posY == linePerPage) {
			content += "== ��" + posP + "ҳ ==";
			content += Paper.newline + Paper.newline;
			posY = 0;
			++posP;
		}
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		String ret = this.content;
		// ���뱾ҳ���У�����ʾҳ��
		if (!(posX == 0 && posY == 0)) {
			int count = linePerPage - posY;
			for (int i = 0; i < count; ++i) {
				ret += Paper.newline;
			}
			ret += "== ��" + posP + "ҳ ==";
		}
		return ret;
	}

	// setter��������������ע��
	public void setCharPerLine(int charPerLine) {
		this.charPerLine = charPerLine;
	}

	// setter��������������ע��
	public void setLinePerPage(int linePerPage) {
		this.linePerPage = linePerPage;
	}
}
