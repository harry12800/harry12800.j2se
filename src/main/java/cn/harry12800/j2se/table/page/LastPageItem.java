package cn.harry12800.j2se.table.page;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import cn.harry12800.j2se.style.J2seColor;

class LastPageItem<T> extends JButton {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	boolean hover = false;
	int w = 60;
	int h = 30;
	private int index;

	public LastPageItem(final PagePanel<T> pagePanel, int index) {
		super("末页");
		this.index = index;
		setPreferredSize(new Dimension(w, h));
		setBorderPainted(false);
		setContentAreaFilled(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setPreferredSize(new Dimension(w, h));
		setSize(w, h);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				hover = true;
				revalidate();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
				revalidate();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				pagePanel.selectIndex(LastPageItem.this.index);
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2;
		if (hover) {
			p2 = new GradientPaint(0, 1, J2seColor.getBackgroundColor(), 0,
					h - 10, J2seColor.getBackgroundColor());
			g2d.setPaint(p2);
			g2d.drawRect(1, 1, w - 3, h - 3);
		} else {
			Color backgroundColor = J2seColor.getBackgroundColor();
			Color color = new Color(
					backgroundColor.getRed(),
					backgroundColor.getGreen(),
					backgroundColor.getBlue(), 150);
			p2 = new GradientPaint(0, 1, color, 0,
					h - 10, color);
			g2d.setPaint(p2);
			g2d.drawRect(1, 1, w - 3, h - 3);
		}
		//			GradientPaint p1 = new GradientPaint(0, 1, new Color(255, 255, 255,
		//					255), 0, h - 10, new Color(255, 255, 255, 255));
		//			g2d.setPaint(p1);
		//			g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
		//					BasicStroke.JOIN_ROUND)); // 设置新的画刷
		//			g2d.setFont(pagePanel.getFont());
		//			if(hover)
		//				g2d.setColor(Color.WHITE);
		//			else {
		//				g2d.setColor(Color.BLACK);
		//			}
		//			g2d.drawString("末页", 2, h / 2 + 5);
		g2d.dispose();
	}
}