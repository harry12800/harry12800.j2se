package cn.harry12800.j2se.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.utils.Clip;

public class PlainButton extends JButton {
	boolean hover = false;
	private int w;
	private int h;
	private String name;
	private int nameLen = 0;
	public Builder builder;
	private ClickAction clickAction;
	private HoverListener hoverAction;

	public static class Builder {
		public boolean hasborder = true;
		public boolean handCursor = true;
		public boolean hasTip;
		public boolean hasCheck = false;
		public boolean checked = false;
		public Image image = null;
		public Color bgcolor;
		public int borderRadius = 0;
	}

	public static Builder createLabelBuilder() {
		Builder builder = new Builder();
		builder.hasborder = false;
		builder.handCursor = false;
		builder.hasTip = false;
		builder.hasCheck = false;
		builder.checked = false;
		return builder;
	}

	/**
	 * 默认builder 只是yi ge
	 * @return
	 */
	public static Builder createBuilder() {
		Builder builder = new Builder();
		builder.hasborder = true;
		builder.handCursor = true;
		builder.hasTip = true;
		builder.hasCheck = false;
		builder.checked = false;
		return builder;
	}

	public static Builder createCheckBuilder(Boolean check) {
		Builder builder = new Builder();
		builder.hasborder = false;
		builder.handCursor = true;
		builder.hasTip = true;
		builder.hasCheck = true;
		builder.checked = check;
		return builder;
	}

	/**
	 * 
	 * @param name
	 * @param w
	 * @param h
	 * @param hasBorder
	 * @param handCursor
	 * @param hasTip
	 * @param hasCheck
	 * @param checked
	 */
	public PlainButton(String name, int w, int h, final Builder builder) {
		this.w = w;
		this.h = h;
		this.name = name;
		this.builder = builder;
		setOpaque(false);
		setContentAreaFilled(false);
		setPreferredSize(new Dimension(w, h));
		setSize(w, h);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		try {
			nameLen = name.getBytes("GBK").length / 2;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//		if (builder.hasTip)
		//			setToolTipText(name);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					if (clickAction != null) {
						clickAction.leftClick(null);
					}
				}
			}
		});
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {

				hover = false;
				repaint();
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				hover = true;
				repaint();
			}
		});
		setMinimumSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w, h));
		setPreferredSize(new Dimension(w, h));
		setFont(new Font("宋体", Font.PLAIN, 12));
		Color color = new Color(231, 224, 224);
		setForeground(color);
		if (builder.handCursor)
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				Color color = new Color(255, 255, 255);
				setForeground(color);
				hover = true;
				repaint();
				if (hoverAction != null)
					hoverAction.hover(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Color color = new Color(186, 186, 186);
				setForeground(color);
				hover = false;
				repaint();
				if (hoverAction != null)
					hoverAction.out();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
				}
				super.mouseReleased(e);
			}
		});
	}

	public interface ActionListener {
		void changed(boolean checked);
	}

	public void addMouseListener(ClickAction a) {
		this.clickAction = a;
		super.addMouseListener(clickAction);
	}

	public interface ChangeListener {
		void changed(boolean checked);
	}

	public interface HoverListener {
		void hover(MouseEvent e);

		void out();
	}

	public void addHoverListener(HoverListener a) {
		this.hoverAction = a;
	}

	public PlainButton(String name, int w, int h) {
		this(name, w, h, createBuilder());
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2;
		if (builder.bgcolor != null) {
			g2d.setColor(builder.bgcolor);
			g2d.fillRoundRect(0, 0, w, h, builder.borderRadius * 2, builder.borderRadius * 2);
		}
		if (hover) {
			p2 = new GradientPaint(0, 0, new Color(186, 131, 164, 200), 0, h, new Color(255, 255, 255, 255));
			g2d.setPaint(p2);
			g2d.drawRoundRect(0, 0, w - 1, h - 1, builder.borderRadius * 2, builder.borderRadius * 2);
		}
		GradientPaint p1 = new GradientPaint(0, 1, new Color(255, 255, 255, 255), 0, h,
				new Color(255, 255, 255, 255));
		g2d.setPaint(p1);
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); // 设置新的画刷
		g2d.setFont(new Font("宋体", Font.PLAIN, 12));
		g2d.drawString(name, w / 2 - 15 * (nameLen / 2), h / 2 + 5);
		g2d.setStroke(stroke);
		if (builder.image != null)
			g2d.drawImage(builder.image, 3, 3, h - 6, h - 6, null);
		g2d.dispose();
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public static Builder createBgColorBuilder(Color color, File file) {
		Builder createBuilder = createBuilder();
		createBuilder.bgcolor = color;
		ImageIcon bigIcon = Clip.getBigIcon(file);
		if (bigIcon != null) {
			createBuilder.image = bigIcon.getImage();
		}
		return createBuilder;
	}

	public static Builder createBgColorBuilder(Color color, BufferedImage byName) {
		Builder createBuilder = createBuilder();
		createBuilder.bgcolor = color;
		createBuilder.image = byName;
		return createBuilder;
	}

	public static Builder createBgColorBuilder(Color color) {
		Builder createBuilder = createBuilder();
		createBuilder.bgcolor = color;
		return createBuilder;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
		repaint();
	}

	/**
	 * 获取name
	 *	@return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
