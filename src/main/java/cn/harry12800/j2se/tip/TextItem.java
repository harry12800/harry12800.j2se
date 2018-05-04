package cn.harry12800.j2se.tip;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import cn.harry12800.j2se.style.UI;

public class TextItem extends JLabel {
	private int w;
	private int h;
	private String text;

	public TextItem(String text) {
		this(text, 188, 25);
	}

	public TextItem(String text, int w, int h) {
		super("<html><body><div text-align='center'>" + text + "</div></body></html>");
		this.w = w;
		this.h = h;
		this.text = text;
		setPreferredSize(new Dimension(w, h));
		setFont(UI.normalFont(12));
		Color color = new Color(231, 224, 224);
		setForeground(color);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2;

		p2 = new GradientPaint(0, 1, new Color(150, 150, 150, 50), 0, h - 10,
				new Color(160, 160, 160, 100));
		g2d.setPaint(p2);
		//			g2d.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
		GradientPaint p1 = new GradientPaint(0, 1, new Color(255, 255, 255, 255), 0, h - 10,
				new Color(255, 255, 255, 255));
		g2d.setPaint(p1);
		//		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); //设置新的画刷
		g2d.setStroke(new BasicStroke(1.0f)); //设置新的画刷
		g2d.setColor(Color.BLACK);
		Font font = new Font("微软雅黑", Font.PLAIN, 12);
		g2d.setFont(font);
		g2d.drawString(text, 2, h / 2 + 5);
		g2d.dispose();
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 获取w
	 *	@return the w
	 */
	public int getW() {
		return w;
	}

	/**
	 * 设置w
	 * @param w the w to set
	 */
	public void setW(int w) {
		this.w = w;
	}

	/**
	 * 获取h
	 *	@return the h
	 */
	public int getH() {
		return h;
	}

	/**
	 * 设置h
	 * @param h the h to set
	 */
	public void setH(int h) {
		this.h = h;
	}
}
