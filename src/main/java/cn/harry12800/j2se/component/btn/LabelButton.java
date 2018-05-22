package cn.harry12800.j2se.component.btn;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;

import cn.harry12800.j2se.style.UI;

public class LabelButton extends JButton {
	boolean hover = false;
	private int w;
	private int h;
	private String name;
	private int nameLen = 0;
	public Builder builder;
	private ClickAction clickAction;
	private ChangeListener changeAction;

	public static class Builder {
		public boolean hasborder = true;
		public boolean handCursor = true;
		public boolean hasTip;
		public boolean hasCheck = false;
		public boolean checked = false;
		public File file = null;
		public Color bgcolor;
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
	public LabelButton(String name, int w, int h, final Builder builder) {
		this.w = w;
		this.h = h;
		this.name = name;
		this.builder = builder;
		nameLen = name.length();
		if (builder.hasTip)
			setToolTipText(name);
		setOpaque(false);
		setBorder(null);
		setContentAreaFilled(false);
		setMinimumSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w, h));
		setPreferredSize(new Dimension(w, h));
		setFont(UI.normalFont(14));
		Color color = new Color(231, 224, 224);
		setForeground(color);
		if (builder.handCursor)
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

			@Override
			public void mouseReleased(MouseEvent e) {
				if (builder.hasCheck) {
					builder.checked = !builder.checked;
					repaint();
					if (changeAction != null)
						changeAction.changed(builder.checked);
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

	public void addChangeListener(ChangeListener a) {
		this.changeAction = a;
	}

	public LabelButton(String name, int w, int h) {
		this(name, w, h, createBuilder());
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2;
		if (builder.hasborder)
			if (hover) {
				p2 = new GradientPaint(0, 1, new Color(186, 131, 164, 200), 0, h - 10, new Color(255, 255, 255, 255));
				g2d.setPaint(p2);
				g2d.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
			} else {
				p2 = new GradientPaint(0, 1, new Color(150, 150, 150, 50), 0, h - 10, new Color(160, 160, 160, 100));
				g2d.setPaint(p2);
				g2d.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
			}
		if (builder.bgcolor != null) {
			g2d.setColor(builder.bgcolor);
			g2d.fillRoundRect(2, 2, w - 5, h - 5, 5, 5);
		}
		GradientPaint p1 = new GradientPaint(0, 1, new Color(255, 255, 255, 255), 0, h - 10,
				new Color(255, 255, 255, 255));
		g2d.setPaint(p1);
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); // 设置新的画刷
		g2d.setFont(UI.normalFont(14));
		g2d.drawString(name, w / 2 - 15 * (nameLen / 2), h / 2 + 5);
		g2d.setStroke(stroke);
		if (builder.hasCheck) {
			stroke = new BasicStroke(1.0f);
			g2d.setStroke(stroke);
			if (hover || builder.checked)
				g2d.setColor(Color.WHITE);
			else {
				g2d.setColor(Color.GRAY);
			}
			if (builder.checked) {
				g2d.drawRoundRect(8, 8, h - 15, h - 15, 3, 3);
				g2d.setColor(Color.BLUE);
				g2d.drawString("√", 8, 16);
//				g2d.fillRoundRect(10, 10, h - 18, h - 18, 5, 5);
			} else {
				g2d.drawRoundRect(8, 8, h - 15, h - 15, 3, 3);
			}
		}
		g2d.dispose();
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public static Builder createBgColorBuilder(Color color) {
		Builder createBuilder = createBuilder();
		createBuilder.bgcolor = color;
		return createBuilder;
	}
}
