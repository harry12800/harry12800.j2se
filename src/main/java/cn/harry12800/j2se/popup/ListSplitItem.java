package cn.harry12800.j2se.popup;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import cn.harry12800.j2se.style.UI;

public class ListSplitItem extends JLabel {
	boolean hover = false;
	private int w;
	private int h;

	public ListSplitItem(int w, int h) {
		this.w = w;
		this.h = h;
		setPreferredSize(new Dimension(w, h));
		setFont(UI.normalFont(14));
		Color color = new Color(231, 224, 224);
		setForeground(color);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2;
		p2 = new GradientPaint(0, 1, new Color(34, 34, 34, 200), 0, h - 10,
				new Color(34, 30, 34, 200));
		g2d.setPaint(p2);
		g2d.setStroke(new BasicStroke(0.5f));
		g2d.drawLine(33, h / 2 - 1, w - 10, h / 2 - 1);
		g2d.dispose();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
