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
import cn.harry12800.j2se.utils.Clip;

public class OperButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color BUTTON_FOREGROUND_COLOR = Color.BLACK;
	private boolean hover;
	private static final int RECT = 3;
	public String srcName = null;
	int h = 0;// getHeight();
	int w = 0;// getWidth();
	BufferedImage hoverImage = null;
	BufferedImage image = null;

	public static void main(String[] args) {
		Clip.seeCom(new OperButton("exit.png", 20, 20));
	}

	public OperButton(String src, int w, int h) {
		this(RECT);
		this.w = w;
		this.h = h;
		this.srcName = src;
		setPreferredSize(new Dimension(w, h));
		hoverImage = ImageUtils.getByName(src);
		image = ImageUtils.getByName(src);

	}

	public OperButton(int style) {
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
		BufferedImage image = null;
		if (hover)
			g2d.drawImage(hoverImage, 0, 0, w, h, null);
		else
			g2d.drawImage(image, 0, 0, w, h, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				tran));
		g2d.dispose();
		super.paintComponent(g);
	}
}
