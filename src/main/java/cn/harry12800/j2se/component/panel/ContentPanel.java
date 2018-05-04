package cn.harry12800.j2se.component.panel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.component.BaseDialog;
import cn.harry12800.j2se.component.panel.TitlePanel.Builder;
import cn.harry12800.j2se.component.panel.TitlePanel.TitleHeight;
import cn.harry12800.j2se.component.utils.ImageUtils;

public abstract class ContentPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;

	public ContentPanel(BaseDialog window) {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setOpaque(true);
		this.image = ImageUtils.getByName("desk.jpg");
		Builder builder = TitlePanel.createBuilder(window);
		builder.hasTitle = true;
		builder.titleHeight = TitleHeight.middle;
		add(new TitlePanel(builder), BorderLayout.NORTH);
		add(createCenter(), BorderLayout.CENTER);
	}

	protected abstract Component createCenter();

	@Override
	protected void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.green);
		GradientPaint p1;
		p1 = new GradientPaint(0, 0, new Color(13, 84, 162, 100), 0, h - 200,
				new Color(13, 84, 162, 100));
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
				h - 1, 0, 0);
		g2d.clip(r2d);
		/*
		 * int x = 0, y = 0; test.jpg是测试图片，与Demo.java放在同一目录下
		 */

		g2d.drawImage(image, 1, 1, w - 2, h - 2, null);
		/*
		 * g.drawImage(image, x, y, getSize().width, getSize().height, this);
		 * while (true) { g2d.drawImage(image, 0, 0, w, h, null); if (x >
		 * getSize().width && y > getSize().height) break; //
		 * 这段代码是为了保证在窗口大于图片时，图片仍能覆盖整个窗口 if (x > getSize().width) { x = 0;
		 * image.getw //y += ic.getIconHeight(); } else ;//x +=
		 * ic.getIconWidth(); } g2d.fillRect(0, 0, w, h); g2d.setClip(clip);
		 */
		g2d.setPaint(p1);
		g2d.drawRoundRect(0, 0, w - 1, h - 1, 0, 0);
		g2d.setStroke(stroke);
		super.paintComponent(g);
	}
}
