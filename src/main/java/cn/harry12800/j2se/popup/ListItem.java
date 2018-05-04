package cn.harry12800.j2se.popup;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import cn.harry12800.j2se.style.UI;

public class ListItem extends JLabel {
	boolean hover = false;
	private int w;
	private int h;
	private String name;
	private PopupFrame rootFrame;
	PopupFrame nextFrame;
	ListItem font1;
	ListItem font2;
	private boolean hasPop;

	public ListItem(String name) {
		this(name, 188, 25);
	}

	public ListItem(String name, int w) {
		this(name, w, 25);
	}

	public ListItem(String name, int w, int h) {
		super("<html><body><div text-align='center'>" + name + "</div></body></html>");
		this.w = w;
		this.h = h;
		this.name = name;
		setToolTipText(name);
		setPreferredSize(new Dimension(w, h));
		setFont(UI.normalFont(12));
		Color color = new Color(231, 224, 224);
		setForeground(color);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Color color = new Color(255, 255, 255);
				setForeground(color);
				hover = true;
				repaint();
				if (rootFrame.changeComponent != ListItem.this) {
					if (rootFrame.changeComponent != null) {
						rootFrame.changeComponent.hover = false;
						rootFrame.changeComponent.repaint();
					}
					rootFrame.disposeChild();
				}
				if (nextFrame != null) {
					rootFrame.hasChildMark = true;
					nextFrame.show(new Point(-180, 0), ListItem.this);
				}
				rootFrame.changeComponent = ListItem.this;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Color color = new Color(186, 186, 186);
				setForeground(color);
				if (rootFrame.hasChildMark && rootFrame.changeComponent == ListItem.this)
					;
				else {
					hover = false;
					rootFrame.disposeChild();
				}
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				hover = false;
				rootFrame.dispose();
				if (rootFrame.parentFrame != null) {
					rootFrame.parentFrame.hasChildMark = false;
					rootFrame.parentFrame.requestFocus();
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2;
		if (hover) {
			p2 = new GradientPaint(0, 1, new Color(0, 155, 219, 255), 0, h - 10,
					new Color(0, 155, 219, 255));
			g2d.setPaint(p2);
			g2d.fillRect(1, 1, w - 3, h - 3);
			//			g2d.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
		} else {
			p2 = new GradientPaint(0, 1, new Color(150, 150, 150, 50), 0, h - 10,
					new Color(160, 160, 160, 100));
			g2d.setPaint(p2);
			//			g2d.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
		}
		if (hasPop) {
			if (hover)
				g2d.setColor(Color.WHITE);
			else {
				g2d.setColor(Color.GRAY);
			}
			g2d.drawLine(170, 7, 176, 12);
			g2d.drawLine(170, 17, 176, 12);
		}
		GradientPaint p1 = new GradientPaint(0, 1, new Color(255, 255, 255, 255), 0, h - 10,
				new Color(255, 255, 255, 255));
		g2d.setPaint(p1);
		//		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); //设置新的画刷
		g2d.setStroke(new BasicStroke(1.0f)); //设置新的画刷
		g2d.setColor(Color.BLACK);
		Font font = new Font("华文新魏", Font.PLAIN, 12);
		g2d.setFont(font);
		g2d.drawString(name, 41, h / 2 + 5);
		g2d.dispose();
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 获取w
	 *	@return the w
	 */
	public int getW() {
		return w;
	}

	/**
	 * 设置w
	 * @param w the w to set
	 */
	public void setW(int w) {
		this.w = w;
	}

	/**
	 * 获取h
	 *	@return the h
	 */
	public int getH() {
		return h;
	}

	/**
	 * 设置h
	 * @param h the h to set
	 */
	public void setH(int h) {
		this.h = h;
	}

	public void setRootFrame(PopupFrame popupFrame) {
		this.rootFrame = popupFrame;
		if (nextFrame != null)
			nextFrame.parentFrame = rootFrame;
	}

	public void disposeChild() {
		if (nextFrame != null) {
			nextFrame.disposeChild();
			nextFrame.dispose();
			System.out.println(nextFrame);
		}
	}

	public void addPop(PopupFrame popupFrame) {
		nextFrame = popupFrame;
		hasPop = true;
		if (rootFrame != null)
			nextFrame.parentFrame = rootFrame;
	}

	public void dispose() {
		if (nextFrame != null)
			nextFrame.disposeParent();
		else
			rootFrame.disposeParent();
		rootFrame.dispose();
	}
}
