package cn.harry12800.j2se.component.btn;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import cn.harry12800.j2se.component.utils.ImageUtils;
import cn.harry12800.j2se.style.UI;

/**
 * 资源目录下的图片名称  自动转成图片
 * @author Administrator
 *
 */
public class ImageButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static final Color BUTTON_COLOR1 = new Color(205, 255, 205);
//	private static final Color BUTTON_COLOR2 = Color.LIGHT_GRAY;
	private static final Color BUTTON_FOREGROUND_COLOR = Color.BLACK;
	private boolean hover;
	private static final int RECT = 3;
	public String srcName = null;
	int h = 0;
	int w = 0;
	private BufferedImage image;
	public ImageButton(String src) {
		this(RECT);
		this.w = 30;
		this.h = 30;
		this.srcName = src;
		image = ImageUtils.getByName(srcName);
		setPreferredSize(new Dimension(w, h));
	}
	public ImageButton(String src,int w,int h) {
		this(RECT);
		this.w = w;
		this.h = h;
		this.srcName = src;
		image = ImageUtils.getByName(srcName);
		setPreferredSize(new Dimension(w, h));
	}
	public ImageButton(int style) {
		setFont(UI.normalFont(16));
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(BUTTON_FOREGROUND_COLOR);
				hover = true;
				repaint();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
				repaint();
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		float tran = 1.0F;
		if (!hover) {
			tran = 0.8F;
		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
//		GradientPaint p1;
//		GradientPaint p2;
//		if (getModel().isPressed()) {
//			p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1,
//					new Color(100, 100, 100));
//			p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 10,
//					new Color(255, 255, 255, 100));
//		} else {
//			p1 = new GradientPaint(0, 0, new Color(0, 0, 255), 0, h - 1,
//					new Color(0, 0, 0));
//			p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0,h - 3, new Color(0, 0, 0, 50));
//		}
		g2d.drawImage(image, 0, 0, w, h, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,tran));
//		GradientPaint gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F,
//				h, BUTTON_COLOR2, true);
//		g2d.setPaint(gp);
//		Arc2D.Float a2d = new Arc2D.Float(0, 0, w, h, 0, 360, Arc2D.CHORD);
//		Shape clip = g2d.getClip();
//		g2d.clip(a2d);
//		g2d.fillRect(0, 0, w, h);
//		g2d.setClip(clip);
//		g2d.setPaint(p1);
		// gph.fill3DRect(0, 0, w+5, h+5, false);
//		g2d.drawArc(0, 0, w - 1, h - 1, 0, 720); // draw shape's border // line; and param own
													// this angle
		// gph.drawOval(0, 0, w - 1, h - 1);
//		g2d.setPaint(p2);
		// gph.drawOval(1, 1, w - 3, h - 3);
		
		g2d.dispose();
		super.paintComponent(g);
	}
}
