package cn.harry12800.j2se.table.page;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import cn.harry12800.j2se.style.UI;

class NextItem<T> extends JButton {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	boolean hover = false;
	int w = 80;
	int h = 20;

	public NextItem(final PagePanel<T> pagePanel) {
		super("下一页");
		setHorizontalAlignment(SwingConstants.CENTER);
		setPreferredSize(new Dimension(w, h));
		setBorder(BorderFactory.createLineBorder(UI.borderColor, 0, true));
		setContentAreaFilled(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Color color = new Color(255, 255, 255);
				setForeground(color);
				hover = true;
				revalidate();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Color color = new Color(186, 186, 186);
				setForeground(color);
				hover = false;
				revalidate();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				pagePanel.selectNextIndex();
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//			Graphics2D g2d = (Graphics2D) g.create();
		//			GradientPaint p2;
		//			if (hover) {
		//				p2 = new GradientPaint(0, 1, J2seColor.getBackgroundColor(), 0,
		//						h - 10, J2seColor.getBackgroundColor());
		//				g2d.setPaint(p2);
		//				g2d.drawRect(1, 1, w - 3, h - 3);
		//			} else {
		//				Color backgroundColor = J2seColor.getBackgroundColor();
		//				Color color = new Color(
		//						backgroundColor.getRed(), 
		//						backgroundColor.getGreen(),
		//						backgroundColor.getBlue(), 150);
		//				p2 = new GradientPaint(0, 1,color, 0,
		//						h - 10, color);
		//				g2d.setPaint(p2);
		//				g2d.drawRect(1, 1, w - 3, h - 3);
		//			}
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
		//			g2d.drawString("下一页",2, h / 2 + 5);
		//			g2d.dispose();
	}
}