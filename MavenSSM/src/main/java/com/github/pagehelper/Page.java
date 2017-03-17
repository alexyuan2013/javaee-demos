package com.github.pagehelper;

import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;

public class Page<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;

	/**
	 * ������count��ѯ
	 */
	private static final int NO_SQL_COUNT = -1;
	/**
	 * ����count��ѯ
	 */
	private static final int SQL_COUNT = 0;
	/**
	 * ҳ�룬��1��ʼ
	 */
	private int pageNum;
	/**
	 * ҳ���С
	 */
	private int pageSize;
	/**
	 * ��ʼ��
	 */
	private int startRow;
	/**
	 * ĩ��
	 */
	private int endRow;
	/**
	 * ����
	 */
	private long total;
	/**
	 * ��ҳ��
	 */
	private int pages;
	/**
	 * ��ҳ����
	 */
	private Boolean reasonable;
	/**
	 * ������Ϊtrue��ʱ�����pagesize����Ϊ0����RowBounds��limit=0�����Ͳ�ִ�з�ҳ������ȫ�����
	 */
	private Boolean pageSizeZero;

	public Page() {
		super();
	}

	public Page(int pageNum, int pageSize) {
		this(pageNum, pageSize, SQL_COUNT, null);
	}

	public Page(int pageNum, int pageSize, boolean count) {
		this(pageNum, pageSize, count ? Page.SQL_COUNT : Page.NO_SQL_COUNT,
				null);
	}

	private Page(int pageNum, int pageSize, int total, Boolean reasonable) {
		super(0);
		if (pageNum == 1 && pageSize == Integer.MAX_VALUE) {
			pageSizeZero = true;
			pageSize = 0;
		}
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = total;
		calculateStartAndEndRow();
		setReasonable(reasonable);
	}

	public Page(RowBounds rowBounds, boolean count) {
		this(rowBounds, count ? Page.SQL_COUNT : Page.NO_SQL_COUNT);
	}

	public Page(RowBounds rowBounds, int total) {
		super(0);
		if (rowBounds.getOffset() == 0
				&& rowBounds.getLimit() == Integer.MAX_VALUE) {
			pageSizeZero = true;
			this.pageSize = 0;
		} else {
			this.pageSize = rowBounds.getLimit();
		}
		this.startRow = rowBounds.getOffset();
		// RowBounds��ʽĬ�ϲ���count�������������count,�����޸�����ΪSQL_COUNT
		this.total = total;
		this.endRow = this.startRow + rowBounds.getLimit();
	}

	public List<E> getResult() {
		return this;
	}

	public int getPages() {
		return pages;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		// ��ҳ��������Բ������ҳ���Զ�����
		this.pageNum = ((reasonable != null && reasonable) && pageNum <= 0) ? 1
				: pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		if (pageSize > 0) {
			pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
		} else {
			pages = 0;
		}
		// ��ҳ��������Բ������ҳ���Զ�����
		if ((reasonable != null && reasonable) && pageNum > pages) {
			// pageNum = pages;
			calculateStartAndEndRow();
		}
	}

	public void setReasonable(Boolean reasonable) {
		if (reasonable == null) {
			return;
		}
		this.reasonable = reasonable;
		// ��ҳ��������Բ������ҳ���Զ�����
		if (this.reasonable && this.pageNum <= 0) {
			this.pageNum = 1;
			calculateStartAndEndRow();
		}
	}

	public Boolean getReasonable() {
		return reasonable;
	}

	public Boolean getPageSizeZero() {
		return pageSizeZero;
	}

	public void setPageSizeZero(Boolean pageSizeZero) {
		if (pageSizeZero != null) {
			this.pageSizeZero = pageSizeZero;
		}
	}

	/**
	 * ������ֹ�к�
	 */
	private void calculateStartAndEndRow() {
		this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize
				: 0;
		this.endRow = this.startRow + this.pageSize
				* (this.pageNum > 0 ? 1 : 0);
	}

	public boolean isCount() {
		return this.total > NO_SQL_COUNT;
	}

	// ������ʽ���÷���

	/**
	 * ����ҳ��
	 *
	 * @param pageNum
	 * @return
	 */
	public Page<E> pageNum(int pageNum) {
		// ��ҳ��������Բ������ҳ���Զ�����
		this.pageNum = ((reasonable != null && reasonable) && pageNum <= 0) ? 1
				: pageNum;
		return this;
	}

	/**
	 * ����ҳ���С
	 *
	 * @param pageSize
	 * @return
	 */
	public Page<E> pageSize(int pageSize) {
		this.pageSize = pageSize;
		calculateStartAndEndRow();
		return this;
	}

	/**
	 * �Ƿ�ִ��count��ѯ
	 *
	 * @param count
	 * @return
	 */
	public Page<E> count(Boolean count) {
		this.total = count ? Page.SQL_COUNT : Page.NO_SQL_COUNT;
		return this;
	}

	/**
	 * ���ú���
	 *
	 * @param reasonable
	 * @return
	 */
	public Page<E> reasonable(Boolean reasonable) {
		setReasonable(reasonable);
		return this;
	}

	/**
	 * ������Ϊtrue��ʱ�����pagesize����Ϊ0����RowBounds��limit=0�����Ͳ�ִ�з�ҳ������ȫ�����
	 *
	 * @param pageSizeZero
	 * @return
	 */
	public Page<E> pageSizeZero(Boolean pageSizeZero) {
		setPageSizeZero(pageSizeZero);
		return this;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Page{");
		sb.append("pageNum=").append(pageNum);
		sb.append(", pageSize=").append(pageSize);
		sb.append(", startRow=").append(startRow);
		sb.append(", endRow=").append(endRow);
		sb.append(", total=").append(total);
		sb.append(", pages=").append(pages);
		sb.append(", reasonable=").append(reasonable);
		sb.append(", pageSizeZero=").append(pageSizeZero);
		sb.append('}');
		return sb.toString();
	}
}
