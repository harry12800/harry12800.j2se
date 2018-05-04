package cn.harry12800.j2se.component.utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GBC extends GridBagConstraints {

	/**
	 * @author harry12800
	 */
	private static final long serialVersionUID = 5920165338046425175L;

	/**
	 * 
	 * @param x  第几列
	 * @param y  第几行
	 * @param anchor 依靠方向
	 * @param insets 间隙
	 * @param rows  占几列
	 * @param column 占几行
	 */
	public GBC(int x, int y, int anchor, Insets insets, int column, int rows) {
		this.gridx = x;
		this.gridy = y;
		this.insets = insets;
		this.gridwidth = column;
		this.gridheight = rows;
		this.anchor = anchor;
		this.fill = BOTH;
	}

	public GBC(int x, int y, int anchor, Insets insets, int rows, int h, int weigth) {
		this.gridx = x;
		this.gridy = y;
		this.insets = insets;
		this.gridwidth = rows;
		this.gridheight = h;
		this.anchor = anchor;
		this.ipadx = 0;
		this.ipady = 0;
		this.fill = BOTH;
		this.weighty = weigth;
	}

	public GBC(int x, int y) {
		this.gridx = x;
		this.gridy = y;
	}
}
