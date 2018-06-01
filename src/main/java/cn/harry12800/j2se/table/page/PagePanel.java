package cn.harry12800.j2se.table.page;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import cn.harry12800.j2se.table.DisplayPanel;

@SuppressWarnings("serial")
public class PagePanel<T> extends JPanel {

	public int count = -1;
	private int cur;
	private DisplayPanel<T> context;

	public PagePanel(DisplayPanel<T> displayPanel) {
		this.context = displayPanel;
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.CENTER));
	}

	public void update(int count, int cur) {
		this.cur = cur;
		this.count = count;
		removeAll();
		int totalPage = (count - 1) / context.getPageSize() + 1;
		if (totalPage > 0)
			add(new FristPageItem<T>(this, 1));
		if (cur > 1)
			add(new LastItem<T>(this));
		if (cur > 3) {
			add(new PageItem<T>(this, cur - 3));
			add(new PageItem<T>(this, cur - 2));
			add(new PageItem<T>(this, cur - 1));
		} else {
			if (cur - 3 > 0)
				add(new PageItem<T>(this, cur - 3));
			if (cur - 2 > 0)
				add(new PageItem<T>(this, cur - 2));
			if (cur - 1 > 0)
				add(new PageItem<T>(this, cur - 1));
		}
		add(new PageItem<T>(this, cur));
		if (cur < totalPage) {
			if (cur + 1 <= totalPage)
				add(new PageItem<T>(this, cur + 1));
			if (cur + 2 <= totalPage)
				add(new PageItem<T>(this, cur + 2));
			if (cur + 3 <= totalPage)
				add(new PageItem<T>(this, cur + 3));
		}
		if (cur < totalPage) {
			add(new NextItem<T>(this));
		}
		if (totalPage > 0)
			add(new LastPageItem<T>(this, totalPage));
		add(new TotalItem<T>(this, totalPage));
		revalidate();
	}

	public void selectIndex(int x) {
		context.getTable().refresh(count, x);
		update(count, x);
	}

	public void selectLastIndex() {
		selectIndex(cur - 1);
	}

	public void selectNextIndex() {
		selectIndex(cur + 1);
	}

	/**
	 * 获取cur
	 * 
	 * @return the cur
	 */
	public int getCur() {
		return cur;
	}

	/**
	 * 设置cur
	 * 
	 * @param cur
	 *            the cur to set
	 */
	public void setCur(int cur) {
		this.cur = cur;
	}
}
