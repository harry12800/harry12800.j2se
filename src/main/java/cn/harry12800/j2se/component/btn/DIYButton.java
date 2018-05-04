package cn.harry12800.j2se.component.btn;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import cn.harry12800.j2se.style.UI;
import cn.harry12800.j2se.utils.Clip;

/**
 * ���ΰ�ť
 */
public class DIYButton extends JButton {
	private static final long serialVersionUID = 39082560987930759L;
	public static final Color BUTTON_COLOR1 = new Color(205, 255, 205);
	public static final Color BUTTON_COLOR2 = Color.LIGHT_GRAY;
	public static final Color BUTTON_FOREGROUND_COLOR = Color.BLACK;
	private boolean hover;
	private int style;
	public static final int ROUND_RECT = 0;
	public static final int LEFT_ROUND_RECT = 1;
	public static final int RIGHT_ROUND_RECT = 2;
	public static final int RECT = 3;
	public static final int BALL = 4;
	public int width;
	public int height;
	public String backgroundImgSrc = null;
	int h = 74;// getHeight();
	int w = 100;// getWidth();
	private String value = null;

	public DIYButton() {
		this(ROUND_RECT);
	}

	public DIYButton(int width, int height) {
		this(ROUND_RECT);
		this.width = width;
		this.height = height;
		this.w = width;
		this.h = height;
		setPreferredSize(new Dimension(width, height));
	}

	public DIYButton(int style, int width, int height) {
		this(style);
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
	}

	public DIYButton(int style, String src, int width, int height) {
		this(style);
		this.width = width;
		this.height = height;
		this.w = width;
		this.h = height;
		this.backgroundImgSrc = src;
		setPreferredSize(new Dimension(width, height));
	}

	public DIYButton(int style, String src, String value, int width, int height) {
		this(style);
		this.width = width;
		this.height = height;
		this.w = width;
		this.h = height;
		this.backgroundImgSrc = src;
		this.value = value;
		setPreferredSize(new Dimension(width, height));
	}

	public DIYButton(int style) {
		this.style = style;
		if (BALL == style) {
			setPreferredSize(new Dimension(42, 42));
		} else if (ROUND_RECT == style) {
			setPreferredSize(new Dimension(70, 42));
		}
		setFont(UI.normalFont);
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

	public static void main(String[] args) {
		Clip.seeCom(new DIYButton(0));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		float tran = 1.0F;
		if (!hover) {
			tran = 0.3F;
		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint p1;
		GradientPaint p2;
		if (getModel().isPressed()) {
			p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1,
					new Color(100, 100, 100));
			p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 10,
					new Color(255, 255, 255, 100));
		} else {
			p1 = new GradientPaint(0, 0, new Color(0, 0, 255), 0, h - 1,
					new Color(0, 0, 0));
			p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0, h - 3, new Color(0, 0, 0, 50));
		}
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				tran));
		GradientPaint gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F,
				h, BUTTON_COLOR2, true);
		g2d.setPaint(gp);
		switch (style) {
		case ROUND_RECT: {
			RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0,
					w - 1, h - 1, 20, 20);
			Shape clip = g2d.getClip();
			g2d.clip(r2d);
			g2d.fillRect(0, 0, w, h);
			g2d.setClip(clip);
			g2d.setPaint(p1);
			g2d.drawRoundRect(0, 0, w - 1, h - 1, 20, 20);
			g2d.setPaint(p2);
			g2d.drawRoundRect(1, 1, w - 3, h - 3, 18, 18);
			break;
		}
		case RECT: {
			//			RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0,
			//					w , h , 1, 1);
			//			Shape clip = g2d.getClip();
			//			g2d.clip(r2d);
			//			g2d.fillRect(0, 0, w, h);
			//			g2d.setClip(clip);
			//			g2d.setPaint(p1);
			//			g2d.drawRoundRect(0, 0, w  , h  , 1, 1);
			//			g2d.setPaint(p2);
			//			g2d.drawRoundRect(0, 0, w , h , 1, 1);
			break;
		}
		case LEFT_ROUND_RECT: {
			RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0,
					(w - 1) + 20, h - 1, 20, 20);
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
			break;
		}
		case RIGHT_ROUND_RECT: {
			RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(-20, 0,
					(w - 1) + 20, h - 1, 20, 20);
			Shape clip = g2d.getClip();
			g2d.clip(r2d);
			g2d.fillRect(0, 0, w, h);
			g2d.setClip(clip);
			g2d.setPaint(p1);
			g2d.drawRoundRect(-20, 0, (w - 1) + 20, h - 1, 20, 20);
			g2d.setPaint(p2);
			g2d.drawRoundRect(-19, 1, (w - 3) + 20, h - 3, 18, 18);
			g2d.setPaint(p1);
			g2d.drawLine(0, 1, 0, h);
			g2d.setPaint(p2);
			g2d.drawLine(1, 2, 1, h - 1);
			break;
		}
		case BALL: {
			Arc2D.Float a2d = new Arc2D.Float(0, 0, w, h, 0, 360, Arc2D.CHORD);
			Shape clip = g2d.getClip();
			g2d.clip(a2d);
			g2d.fillRect(0, 0, w, h);
			g2d.setClip(clip);
			g2d.setPaint(p1);
			// gph.fill3DRect(0, 0, w+5, h+5, false);
			g2d.drawArc(0, 0, w - 1, h - 1, 0, 720); // draw shape's border
														// line; and param own
														// this angle
														// gph.drawOval(0, 0, w - 1, h - 1);
			g2d.setPaint(p2);
			// gph.drawOval(1, 1, w - 3, h - 3);
			break;
		}
		default:
			break;
		}

		if (backgroundImgSrc != null) {
			Image icon = Toolkit.getDefaultToolkit().getImage(backgroundImgSrc);
			BufferedImage image = null;
			try {
				InputStream in = DIYButton.class.getResourceAsStream(backgroundImgSrc);
				image = ImageIO.read(in);
			} catch (IOException e) {
			}
			if (image != null)
				g2d.drawImage(image, 0, 0, w, h, null);
			else {
				g2d.drawImage(icon, 0, 0, w, h, null);
			}
		}
		if (value != null) {
			g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); //设置新的画刷
			Font font = new Font("宋体", Font.BOLD, 20);
			g.setFont(font);
			g2d.setPaint(p1);
			g2d.drawString(value, 0, h / 2);
		}
		g2d.dispose();
		super.paintComponent(g);
	}
}
