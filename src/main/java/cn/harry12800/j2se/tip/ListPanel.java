package cn.harry12800.j2se.tip;

import java.awt.Color;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import cn.harry12800.j2se.utils.Clip;
import cn.harry12800.tools.Lists;

public class ListPanel<T extends Letter> extends JPanel {
	/**
	 * 
	 */
	//	private Color selectColor = new Color(18, 171, 130);
	//	private Color selectBackColor = new Color(18, 171, 130);
	private static final long serialVersionUID = 1L;
	private ListCallBack<T> callback;
	private List<ItemPanel<T>> items = Lists.newArrayList();
	/**
	 * 父子缩进
	 */
	public int indent = 5;
	/**
	 * 两个单元格的间距
	 */
	public int itemGap = 0;

	public ListPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ItemPanel<T>> getItems() {
		return items;
	}

	public int getItemGap() {
		return itemGap;
	}

	public void setItems(List<T> letters) {
		for (T letter : letters) {
			ItemPanel<T> itemPanel = new ItemPanel<T>(this, letter);
			addItem(itemPanel);
		}
		updateUI();
	}

	public int getComponentAt(ItemPanel<T> itemPanel) {
		return items.indexOf(itemPanel) * 2;
	}

	public void addItem(ItemPanel<T> itemPanel, int index) {
		itemPanel.setBackground(new Color(114, 102, 234));
		items.add(index / 2 + 1, itemPanel);
		add(itemPanel, index + 2);
		add(Box.createVerticalStrut(itemGap), index + 3);
		updateUI();
	}

	public void addItem(ItemPanel<T> itemPanel) {
		items.add(itemPanel);
		add(itemPanel);
		add(Box.createVerticalStrut(itemGap));
	}

	/**
	 * 获取callback
	 * 
	 * @return the callback
	 */
	public ListCallBack<T> getCallback() {
		return callback;
	}

	/**
	 * 设置callback
	 * 
	 * @param callback
	 *            the callback to set
	 */
	public void setCallback(ListCallBack<T> callback) {
		this.callback = callback;
	}

	public static interface ListCallBack<T extends Letter> {
		void item(ItemPanel<T> itemPanel, T letter);
	}

	public void addCallBack(ListCallBack<T> a) {
		this.callback = a;
	}

	public static void main(String[] args) {
		List<Letter> a = Lists.newArrayList();
		a.add(new Letter("ad", "adsf", "asd"));
		ListPanel<Letter> listPanel = new ListPanel<Letter>();
		listPanel.setItems(a);
		Clip.seeCom(listPanel);
	}

	public boolean removeItem(int componentAt) {
		ItemPanel<?> component2 = (ItemPanel<?>) getComponent(componentAt + 2);
		if (component2.parentItem != null) {
			remove(component2);
			items.remove(component2);
			remove(componentAt + 2);
			return true;
		} else
			return false;
	}

}
