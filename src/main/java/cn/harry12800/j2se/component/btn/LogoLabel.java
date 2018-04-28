package cn.harry12800.j2se.component.btn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import cn.harry12800.j2se.component.utils.ImageUtils;
import cn.harry12800.j2se.style.UI;


public class LogoLabel extends JPanel {
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
	BufferedImage costomimage = null;
	public LogoLabel(String src) {
		this(RECT);
		this.w = 25;
		this.h = 25;
		this.srcName = src;
		setSize(w, h) ;
		setOpaque(false);
		setPreferredSize(new Dimension(w, h));
		costomimage = ImageUtils.getByName(srcName + ".png");
		hoverImage = ImageUtils.getByName(srcName + ".png");
		setPreferredSize(new Dimension(w, h));
	}
	public LogoLabel(int style) {
		setFont(UI.normalFont(16));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(BUTTON_FOREGROUND_COLOR);
				hover = true;
				LogoLabel.this.getParent().repaint();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
				LogoLabel.this.getParent().repaint();
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		BufferedImage  image= null;
			if (hover) {
				image= hoverImage;
			} else {
				image= costomimage;
			}
		g2d.drawImage(image, 2, 2, w-4, h-4, null);
		g2d.dispose();
	}
	
	@Override
	public void update(Graphics g) {
		paintComponent(g);
	}
}
