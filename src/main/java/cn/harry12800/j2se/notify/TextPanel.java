package cn.harry12800.j2se.notify;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class TextPanel extends JPanel {
	static JTextPane textPane = new JTextPane();
	public static final Color BUTTON_COLOR1 = new Color(205, 255, 205);
	public static final Color BUTTON_COLOR2 = Color.LIGHT_GRAY;
	static int w = 50;
	static int h = 50;

	public TextPanel() {
		setOpaque(false);
		textPane.setEditable(true);
		textPane.setPreferredSize(new Dimension(400, 300));
	}

	@Override
	public void paintComponents(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		//		float tran = 1.0F;
		//		if (!hover) {
		//			tran = 0.3F;
		//		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint p1;
		GradientPaint p2;

		p1 = new GradientPaint(0, 0, new Color(0, 0, 255), 0, h - 1,
				new Color(0, 0, 0));
		p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0, h - 3, new Color(0, 0, 0, 50));
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
		GradientPaint gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F,
				h, BUTTON_COLOR2, true);
		g2d.setPaint(gp);
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, (w - 1) + 20, h - 1, 20, 20);
		Shape clip = g2d.getClip();
		g2d.clip(r2d);
		g2d.fillRect(0, 0, w, h);
		g2d.setClip(clip);
		g2d.setPaint(p1);
		g2d.drawRoundRect(0, 0, (w - 1) + 20, h - 1, 20, 20);
		g2d.setPaint(p2);
		g2d.drawRoundRect(1, 1, (w - 3) + 20, h - 3, 18, 18);
		g2d.setPaint(p1);
		g2d.drawLine(w - 1, 1, w - 1, h);
		g2d.setPaint(p2);
		g2d.drawLine(w - 2, 2, w - 2, h - 1);
		g2d.dispose();
	}
}
