package cn.harry12800.j2se.style.ui;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

import cn.harry12800.j2se.style.UI;

public class MyScrollBarUI extends BasicScrollBarUI {

	@Override
	protected void configureScrollBarColors() {
		// 把手
		thumbColor = UI.backColor;
		thumbHighlightColor = UI.backColor;
		thumbDarkShadowColor = UI.backColor;
		thumbLightShadowColor = UI.backColor;

		// 滑道
		trackColor = UI.backColor;
		trackHighlightColor = UI.backColor;

	}

	public Dimension getPreferredSize(JComponent c) {
		scrollBarWidth = 3;
		return (scrollbar.getOrientation() == JScrollBar.VERTICAL) ? new Dimension(scrollBarWidth, 3)
				: new Dimension(3, scrollBarWidth);
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		trackBounds.width = 5;
		// g.translate(trackBounds.x, trackBounds.y);
		// super.paintTrack(g, c, trackBounds);
		// System.out.println(trackBounds);
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		// 这句一定要加上啊，不然拖动就失效了
		g.translate(thumbBounds.x, thumbBounds.y);
		g.setColor(UI.scrollColor);// 设置边框颜色
		g.drawRoundRect(0, 0, 5, thumbBounds.height, 2, 2); // 画一个圆角矩形
		// 消除锯齿
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.addRenderingHints(rh);
		// 半透明
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		// 设置填充颜色，这里设置了渐变，由下往上
		// g2.setPaint(new GradientPaint(c.getWidth() / 2, 1, Color.GRAY, c.getWidth() /
		// 2, c.getHeight(), Color.GRAY));
		// 填充圆角矩形
		g2.fillRoundRect(0, 0, 5, thumbBounds.height, 2, 2);
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(0, 0));
		button.setSize(0, 0);
		button.setBorder(null);
		return button;
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(0, 0));
		button.setSize(0, 0);
		button.setBorder(null);
		return button;
	}

	public static ImageIcon getPicture(String name) {
		ImageIcon icon = new ImageIcon(MyScrollBarUI.class.getClassLoader().getResource("image/" + name));
		return icon;
	}
	// @Override
	// protected void setThumbBounds(int x, int y, int width, int height) {
	// System.err.println(x+" "+ y +" "+width +" "+height);
	// super.setThumbBounds(x, y, width, height);
	// }
}
