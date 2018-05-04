package cn.harry12800.j2se.table.page;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import cn.harry12800.j2se.style.J2seColor;

class PageItem<T> extends JButton {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	boolean hover = false;
	int w = 40;
	int h = 30;
	private int index;
	private PagePanel<T> pagePanel;

	public PageItem(final PagePanel<T> pagePanel, int index) {
		super("" + index);
		this.pagePanel = pagePanel;
		this.index = index;
		setBorderPainted(false);
		setContentAreaFilled(false);
		if (index > 9 && index < 100) {
			w = 50;
		}
		if (index > 99 && index < 1000) {
			w = 55;
		}
		if (index > 999 && index < 10000) {
			w = 60;
		}
		setPreferredSize(new Dimension(w, h));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Color color = new Color(255, 255, 255);
				setForeground(color);
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Color color = new Color(186, 186, 186);
				setForeground(color);
				hover = false;
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				pagePanel.selectIndex(PageItem.this.index);
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2;
		if (hover) {
			p2 = new GradientPaint(0, 1, J2seColor.getBackgroundColor(), 0,
					h - 10, J2seColor.getBackgroundColor());
			g2d.setPaint(p2);
			g2d.drawRect(1, 1, w - 3, h - 3);
		} else {
			Color backgroundColor = J2seColor.getBackgroundColor();
			Color color = new Color(
					backgroundColor.getRed(),
					backgroundColor.getGreen(),
					backgroundColor.getBlue(), 150);
			p2 = new GradientPaint(0, 1, color, 0,
					h - 10, color);
			g2d.setPaint(p2);
			g2d.drawRect(1, 1, w - 3, h - 3);
		}
		if (pagePanel.getCur() == index) {
			Color backgroundColor = J2seColor.getBackgroundColor();
			Color color = new Color(
					backgroundColor.getRed(),
					backgroundColor.getGreen(),
					backgroundColor.getBlue(), 150);
			p2 = new GradientPaint(0, 1, color, 0,
					h - 10, color);
			g2d.setPaint(p2);
			g2d.fillRect(1, 1, w - 3, h - 3);
		}
		g2d.dispose();
	}
}