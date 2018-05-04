package cn.harry12800.j2se.component.btn;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

import cn.harry12800.j2se.style.UI;

/**
 * 开关按钮
 * @author Yuexin
 *
 */
public class TurnButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean hover;
	int h = 0;// getHeight();
	int w = 0;// getWidth();
	int x = 2;
	private boolean value = false;

	public TurnButton(int w, int h) {
		this.w = w;
		this.h = h;
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setPreferredSize(new Dimension(w, h));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setLayout(null);
		//		final Parent parent = new Parent();
		//		add(parent);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
				repaint();
			}

			@Override
			synchronized public void mouseClicked(MouseEvent e) {
				value = !value;
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i <= TurnButton.this.w
								- TurnButton.this.h - 1; i++) {
							if (value)
								x++;
							else
								x--;
							try {
								Thread.sleep(5);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							TurnButton.this.repaint();

						}
					}
				}).start();
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (value) {
			g2d.setColor(Color.BLUE);
		} else
			g2d.setColor(Color.lightGray);
		g2d.fillRoundRect(2, 2, w - 4, h - 4, h, h);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (hover)
			g2d.setColor(Color.lightGray);
		else
			g2d.setColor(Color.GRAY);
		g2d.drawRoundRect(0, 0, w - 1, h - 1, h, h);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(3));
		Shape clip = new RoundRectangle2D.Double(x, 2, h - 4, h - 4, h - 4, h - 4);
		g2d.setClip(clip);
		GradientPaint paint = new GradientPaint(0, 0, UI.voidColor, getWidth(), 0,
				UI.foreColor);
		g2d.setPaint(paint);// 设置绘图对象的填充模式
		//		if (!value)
		//			g2d.setColor(UI.voidColor);
		//		else
		//			g2d.setColor(UI.foreColor);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		//		g2d.fillOval(x, 2, h - 4, h - 4);
		g2d.dispose();

	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean soundPlay) {
		value = soundPlay;
		if (value)
			x = getPreferredSize().width - getPreferredSize().height;
	}
}
