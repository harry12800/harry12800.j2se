package cn.harry12800.j2se.tab;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.style.UI;

public class TabHeader extends JButton {
	boolean hover = false;
	private int w;
	private int h;
	private String name;
	private int index;
	private Tab tab;

	public TabHeader(final Tab tab, String name, int h) {
		this.h = h;
		this.tab = tab;
		this.name = name;
		setOpaque(false);
		if (this.name == null) {
			this.name = "未知名称 ";
		}
		setText(name);
		setPreferredSize(new Dimension(200, h));
		setBorderPainted(false);
		setContentAreaFilled(false);
		setSize(200, h);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			// @Override
			public void mouseEntered(MouseEvent e) {
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
				repaint();
			}

			public void mouseClicked(MouseEvent e) {
				// if(tab.getCurrIndex()!=index)
				// tab.showTab();
			}

			public void mouseReleased(MouseEvent e) {
				// 防止在按钮之外的地方松开鼠标
				if (contains(e.getX(), e.getY())) {
					hover = true;
					if (tab.getCurrIndex() != index)
						tab.showTab();
				} else
					hover = false;
				repaint();
			}
		});
	}

	public boolean contains(int x, int y) {
		// 不判定的话会越界，在组件之外也会激发这个方法
		if (!super.contains(x, y))
			return false;
		if (x > w || y > h)
			return false;
		repaint();
		return true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2;
		if (tab.getCurrIndex() == index) {
			p2 = new GradientPaint(0, 0, UI.foreColor(100), 0, h, UI.foreColor(100));
			g2d.setPaint(p2);
			g2d.fillRect(0, 0, w, h);
			g2d.setColor(Color.WHITE);
			g2d.drawRect(0, 0, w - 1, h + 1);
		} else if (hover) {
			p2 = new GradientPaint(0, 0, UI.foreColor(100), 0, h, new Color(255, 255, 255, 0));
			g2d.setPaint(p2);
			g2d.fillRect(0, 0, w, h);
			g2d.setColor(Color.WHITE);
			g2d.drawRect(0, 0, w - 1, h - 1);
		} else {
			p2 = new GradientPaint(0, 0, UI.backColor(50), 0, h, UI.backColor(50));
			g2d.setPaint(p2);
			g2d.fillRect(0, 0, w, h);
			g2d.setColor(Color.WHITE);
			g2d.drawRect(0, 0, w - 1, h - 1);
		}
		g2d.dispose();
	}

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public void setIndex(int i) {
		this.index = i;
	}
}