package cn.harry12800.j2se.component.btn;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class ImageBtn extends JButton implements MouseListener {

	/**
	 * 
	 */
	private boolean isHover = false;
	BufferedImage bufferedImage;
	private int w = 24;
	private int h = 24;
	private static final long serialVersionUID = 1L;

	public ImageBtn(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		setBorderPainted(false);
		setContentAreaFilled(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(this);
	}

	public ImageBtn(BufferedImage bufferedImage, int w, int h) {
		this.w = w;
		this.h = h;
		setSize(w, h);
		setPreferredSize(new Dimension(w, h));
		this.bufferedImage = bufferedImage;
		setBorderPainted(false);
		setContentAreaFilled(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(this);
	}

	protected void paintComponent(java.awt.Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		if (isHover)
			g2d.drawImage(bufferedImage, 0, 0, w, h, this);
		else
			g2d.drawImage(bufferedImage, 0, 0, w, h, this);
		g2d.dispose();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		isHover = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		isHover = false;
		repaint();
	}
}
