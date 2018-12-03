package cn.harry12800.j2se.component.rc;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.basic.BasicMenuItemUI;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;

/**
 * Created by harry12800 on 2017/6/5.
 */
public class RCMenuItemUI extends BasicMenuItemUI {

	private int width;
	private int height;

	public RCMenuItemUI() {
		this(70, 30);
	}

	public RCMenuItemUI(int width, int height) {

		this.width = width;
		this.height = height;
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);

		c.setPreferredSize(new Dimension(width, height));
		c.setBackground(Colors.FONT_WHITE);
		c.setFont(FontUtil.getDefaultFont(12));
		c.setBorder(null);
		selectionForeground = Colors.FONT_BLACK;
		selectionBackground = Colors.SCROLL_BAR_TRACK_LIGHT;
	}

	@Override
	protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text) {
		int x = (int) ((menuItem.getSize().getWidth() - textRect.width) / 2);

		g.setColor(Colors.FONT_BLACK);
		Rectangle newRect = new Rectangle(x, textRect.y, textRect.width, textRect.height);
		super.paintText(g, menuItem, newRect, text);
	}
}
