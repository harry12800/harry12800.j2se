package cn.harry12800.j2se.component.label;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import cn.harry12800.j2se.component.utils.ImageUtils;

public class HeaderJLabel extends JLabel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String logo = "logo.png";
	private boolean isHover = false;
	BufferedImage backImage2 = ImageUtils.getByName("point_black.png");

	public HeaderJLabel() {
		addMouseListener(this);
	}

	protected void paintComponent(java.awt.Graphics g) {
		// int w =getWidth();
		// int h = getHeight();
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(backImage2, 0, 0, getWidth() - 1,
				getWidth() - 1, this);
		if (isHover) {
			Shape clip = new RoundRectangle2D.Double(0, 0, getWidth() - 1,
					getWidth() - 1, getWidth() - 1, getWidth() - 1);
			g2d.setClip(clip);
			// g2d.fillRoundRect(1, 1,128,128, 128,128);
			// g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_SQUARE,
			// BasicStroke.JOIN_ROUND)); //设置新的画刷

			// g2d.drawRoundRect(0, 0, 127,127, 127,127)a;
			g2d.drawImage(ImageUtils.getByName(logo), 0, 0, getWidth() - 1,
					getWidth() - 1, this);
		} else {
			Shape clip = new RoundRectangle2D.Double(2, 2, getWidth() - 5,
					getWidth() - 5, getWidth() - 5, getWidth() - 5);
			g2d.setClip(clip);
			// g2d.fillRoundRect(1, 1,128,128, 128,128);
			// g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_SQUARE,
			// BasicStroke.JOIN_ROUND)); //设置新的画刷
			// g2d.drawRoundRect(0, 0, 127,127, 127,127);
			g2d.drawImage(ImageUtils.getByName(logo), 0, 0, getWidth() - 1,
					getWidth() - 1, this);

		}
		g2d.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

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
