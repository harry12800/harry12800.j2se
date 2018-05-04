package cn.harry12800.j2se.tip;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import cn.harry12800.j2se.style.J2seColor;
import cn.harry12800.j2se.style.UI;

public class ActionButton extends JLabel {
	boolean hover = false;
	private int w;
	private int h;

	public ActionButton(String name, int w, int h) {
		setHorizontalAlignment(JLabel.CENTER);
		this.w = w;
		this.h = h;
		setText(name);
		setFont(UI.微软雅黑Font);
		setOpaque(false);
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
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2;
		Color backgroundColor = J2seColor.getBackgroundColor();
		if (hover) {
			Color color = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 255);
			p2 = new GradientPaint(0, 1, color, 0, h - 10, color);
			g2d.setPaint(p2);
			g2d.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
			color = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 100);
			p2 = new GradientPaint(0, 1, color, 0, h - 10, color);
			g2d.setPaint(p2);
			g2d.fillRoundRect(1, 1, w - 3, h - 3, 5, 5);
		} else {
			Color color = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 50);
			p2 = new GradientPaint(0, 1, color, 0, h - 10, color);
			g2d.setPaint(p2);
			g2d.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
		}
		g2d.dispose();
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
}
