package cn.harry12800.j2se.popup;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;

import cn.harry12800.tools.Lists;

/**
 * @Author harry12800
 * @QQ 804151219
 * 
 */
public class ListPanel extends JPanel {

	private static final long serialVersionUID = 4529266044762990227L;

	private List<Component> list;
	private int w = 32;
	private int h = 100;
	boolean hover = false;
	private Popup pop;
	private JComponent showDate;

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

	public ListPanel(List<Component> lsit) {
		this.list = lsit;
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, -1));
		setOpaque(false);
		initPanel();
	}

	@Override
	protected void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.green);
		GradientPaint p1;
		p1 = new GradientPaint(0, 0, new Color(13, 84, 162, 20), 30, h - 200,
				new Color(13, 84, 162, 100));
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
				h - 1, 0, 0);
		g2d.clip(r2d);
		/*
		 * int x = 0, y = 0; test.jpg是测试图片，与Demo.java放在同一目录下
		 */

		g2d.setPaint(p1);
		g2d.fillRect(0, 0, 30, h);
		p1 = new GradientPaint(0, 0, new Color(13, 84, 162, 100), 0, h - 200,
				new Color(13, 84, 162, 100));
		/*
		 * g.drawImage(image, x, y, getSize().width, getSize().height, this);
		 * while (true) { g2d.drawImage(image, 0, 0, w, h, null); if (x >
		 * getSize().width && y > getSize().height) break; //
		 * 这段代码是为了保证在窗口大于图片时，图片仍能覆盖整个窗口 if (x > getSize().width) { x = 0;
		 * image.getw //y += ic.getIconHeight(); } else ;//x +=
		 * ic.getIconWidth(); } g2d.fillRect(0, 0, w, h); g2d.setClip(clip);
		 */
		g2d.setPaint(p1);
		g2d.drawRoundRect(0, 0, w - 1, h - 1, 0, 0);
		/*
		 * g2d.setPaint(p2); g2d.drawRoundRect(0, 0, w - 1, h - 1, 0, 0);
		 */
	}

	// 显示日期选择面板
	public void showPanel(Point show) {
		if (pop != null) {
			pop.hide();
		}
		SwingUtilities.convertPointToScreen(show, this);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = show.x;
		int y = show.y;
		if (x < 0) {
			x = 0;
		}
		if (x > size.width - w) {
			x = show.x - w;
		}
		if (y < size.height - h) {
		} else {
			y -= h;
		}
		pop = PopupFactory.getSharedInstance()
				.getPopup(null, this, x, y);
		setFocusable(true);
		requestFocus();
		JPanel parent2 = (JPanel) this.getParent();
		parent2.setBackground(new Color(0, 0, 0, 0));
		JLayeredPane parent3 = (JLayeredPane) parent2.getParent();
		parent3.setBackground(new Color(0, 0, 0, 0));
		JRootPane parent4 = (JRootPane) parent3.getParent();
		parent4.setBackground(new Color(0, 0, 0, 0));
		List<Component> list = Lists.newArrayList();
		JButton item = new JButton("aaa");
		JButton item1 = new JButton("bbb");
		list.add(item);
		list.add(item1);
		parent4.setContentPane(new ListPanel(list));
		Container parent5 = parent4.getParent();
		final Frame parent6 = (Frame) parent5.getParent();
		parent6.setType(Frame.Type.UTILITY);
		parent6.setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		parent6.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent e) {
				System.out.println("windowLostFocus");
				parent6.setVisible(false);
				//				System.exit(1);
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				System.out.println("windowGainedFocus");
			}
		});
		parent6.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("focusLost");
			}

			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("focusGained");
			}
		});
		parent6.toFront();
		parent6.setAlwaysOnTop(true);
		parent6.setUndecorated(true);
		parent6.setSize(this.getW(), this.getH());
		//		parent6.removeAll();
		//		parent6.add(this);
		parent6.repaint();
		parent6.setLocation(x, y);
		parent6.setVisible(true);
		parent6.requestFocus();
		//		System.out.println(parent6.requestFocusInWindow());
		//		System.out.println(parent6.getFocusableWindowState());
		//		System.out.println(parent6.hasFocus());
	}

	/**
	 * 是否允许用户选择
	 */
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		showDate.setEnabled(b);
	}

	private void initPanel() {
		for (Component c : list) {
			add(c);
		}
	}

	public void refresh() {
		SwingUtilities.updateComponentTreeUI(this);
	}

	// 隐藏日期选择面板
	public void hidePanel() {
		if (pop != null) {
			pop.hide();
			pop = null;
		}
	}
}