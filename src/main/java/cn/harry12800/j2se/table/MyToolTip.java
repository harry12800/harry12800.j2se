package cn.harry12800.j2se.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.JButton;
import javax.swing.JToolTip;

public class MyToolTip extends JToolTip {

	private static final long serialVersionUID = 1L;
	private JButton jb;

	public MyToolTip(int x) {
		super();
		jb = new JButton("Push me!" + x);
		jb.setLocation(10, 30);
		jb.setSize(100, 30);
		add(jb);
	}

	public Dimension getPreferredSize() {
		return new Dimension(200, 100);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int width = (int) (getPreferredSize().getWidth());
		int height = (int) (getPreferredSize().getHeight());
		Paint oldPaint = g2d.getPaint();
		g2d.setPaint(Color.CYAN);
		g2d.fillRect(0, 0, width, height);
		g2d.setPaint(oldPaint);

		if (getTipText() != null) {
			g2d.drawString(getTipText(), 10, 20);
		}

	}

	public void paintChildren(Graphics g) {
		jb.repaint();
	}

	public JButton getButton() {
		return jb;
	}

}