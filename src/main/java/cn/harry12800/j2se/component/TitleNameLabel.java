package cn.harry12800.j2se.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import cn.harry12800.j2se.style.UI;

public class TitleNameLabel extends JLabel {
	int h = 74;// getHeight();
	int w = 100;// getWidth();
	public int width;
	public int height;

	public TitleNameLabel(String name) {
		super(name);
		this.width = 300;
		this.height = 20;
		this.w = width;
		this.h = height;
		setOpaque(false);
		setSize(w, h);
		setPreferredSize(new Dimension(w, h));
		setFont(UI.normalFont(14));
		Color color = new Color(231, 224, 224);
		setForeground(color);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Color color = new Color(255, 255, 255);
				setForeground(color);
				TitleNameLabel.this.getParent().repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Color color = new Color(231, 224, 224);
				setForeground(color);
				TitleNameLabel.this.getParent().repaint();
			}
		});
	}

	//	@Override
	//	protected void paintComponent(Graphics g) {
	// 
	//		super.paintComponent(g);
	//	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image offScreenImage = null; // 虚拟图片
	//	@Override
	//	public void update(Graphics g) {
	//		if (offScreenImage == null) {
	//			offScreenImage = this.createImage( width, height);
	//		}
	//		Graphics gOffScreen = offScreenImage.getGraphics();
	//		Color c = gOffScreen.getColor();
	//		gOffScreen.setColor(new Color(220,127,32));
	//		gOffScreen.fillRect(0, 0, width, height);
	//		gOffScreen.setColor(c);
	//		g.drawImage(offScreenImage, 0, 0, null);
	//	}
}
