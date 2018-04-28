package cn.harry12800.j2se.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;

import javax.swing.JLabel;

public class Progressar extends JLabel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7392957492657606793L;
	Type type = Type.normal;
//	private int w;
	private int h;
	private int total = 100;
	private int curr = 0;
	private String desc;

	public Progressar(String desc, Type type) {
//		this.w = 300;
		this.h = 30;
		this.desc = desc;
		if (type == Type.ball || type == Type.ballPercent)
			this.h = 60 + 1;
		this.setPreferredSize(new Dimension(1200, h));
		this.type = type;
	}

	public Progressar(String desc, Type type, int w, int h, int total) {
		 
		this.h = 30;
		setPreferredSize(new Dimension(w,h));
		setSize(new Dimension(w,h));
		this.desc = desc;
		if (type == Type.ball || type == Type.ballPercent)
			this.h = 60 + 1;
		this.setPreferredSize(new Dimension(w, h));
		this.type = type;
	}

	/**
	 * 获取total
	 * 
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 设置total
	 * 
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	public static enum Type {
		/**
		 * 正常类型
		 */
		normal,
		/**
		 * 下标类型
		 */
		subNormal,
		/**
		 * 百分比类型
		 */
		percent,
		/**
		 * 球型
		 */
		ball,
		/**
		 * 球型百分比
		 */
		ballPercent
	}

	public void paint(Graphics g) {
		switch (type) {
		case normal:
			paintNormal(g);
			break;
		case subNormal:
			paintSubnormal(g);
			break;
		case percent:
			paintPercent(g);
			break;
		case ball:
			paintBall(g);
			break;
		case ballPercent:
			paintBallPercent(g);
			break;
		}
	}

	private void paintPercent(Graphics g) {
		int w = getWidth();
		int h = 10;
	
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke stroke = g2d.getStroke();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.GRAY);
		GradientPaint p1 = new GradientPaint(0, 0, new Color(13, 84, 162), 0,
				h - 200, new Color(0, 255, 245));
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
				h - 1, 0, 0);
		g2d.clip(r2d);
		g2d.setPaint(p1);
		g2d.fillRoundRect(0, 0, w - 1, h - 1, 10, 10);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		// Float s = (float) (curr*1.0/total*w);
		int s1 = (int) (curr * 1.0 / total * w);
		p1 = new GradientPaint(0, 0, new Color(123, 123, 123), h - 1, w - 1,
				new Color(255, 255, 255));
		r2d = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 0, 0);
		g2d.clip(r2d);
		g2d.setPaint(p1);
		g2d.fillRoundRect(0, 0, s1 - 1, h - 1, 10, 10);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setColor(Color.BLACK);
		double num = curr * 1.0 / total * 100;
		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
		String str = df.format(num);// 返回的是String类型
		g2d.drawString(desc + str + "%", 0, 20);
		g2d.dispose();
		g2d.setStroke(stroke);
	}

	private void paintSubnormal(Graphics g) {
		int w = getWidth();
		int h = 10;
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.GRAY);
		GradientPaint p1 = new GradientPaint(0, 0, new Color(13, 84, 162), 0,
				h - 200, new Color(0, 255, 245));
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
				h - 1, 0, 0);
		g2d.clip(r2d);
		g2d.setPaint(p1);
		g2d.fillRoundRect(0, 0, w - 1, h - 1, 10, 10);
		g2d.dispose();

		g2d = (Graphics2D) g.create();
		// Float s = (float) (curr*1.0/total*w);
		int s1 = (int) (curr * 1.0 / total * w);
		p1 = new GradientPaint(0, 0, new Color(0, 0, 245), h - 1, w - 1,
				new Color(0, 0, 0));
		r2d = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 0, 0);
		g2d.clip(r2d);
		g2d.setPaint(p1);
		g2d.fillRoundRect(0, 0, s1 - 1, h - 1, 10, 10);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setColor(Color.BLACK);
		g2d.drawString(desc + curr + "/" + total, 0, 20);
		g2d.dispose();
	}

	private void paintNormal(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		h=10;
		setPreferredSize(new Dimension(w,10));
		  w = getWidth();
		  h = getHeight();
		System.out.println(w+"  "+h);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.red);
		GradientPaint p1 = new GradientPaint(0, 0, new Color(13, 84, 162), 0,
				h - 200, new Color(0, 255, 245));
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
				h - 1, 0, 0);
		g2d.clip(r2d);
		g2d.setPaint(p1);
		g2d.fillRoundRect(0, 0, w - 1, h - 1, 10, 10);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		// Float s = (float) (curr*1.0/total*w);
		int s1 = (int) (curr * 1.0 / total * w);
		p1 = new GradientPaint(0, 0, new Color(0, 0, 245), h - 1, w - 1,
				new Color(0, 0, 0));
		r2d = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 0, 0);
		g2d.clip(r2d);
		g2d.setPaint(p1);
		g2d.fillRoundRect(0, 0, s1 - 1, h - 1, 10, 10);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setColor(Color.WHITE);
		g2d.drawString(desc + curr + "/" + total, 0, 20);
		g2d.dispose();
	}

	private void paintBall(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.pink);
		h = w < h ? w : h;
		g2d.fillOval(0, 0, h, h);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.RED);
		g2d.fillArc(0, 0, h, h, 90, (int) (360 * -curr * 1.0 / total));
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.BLUE);
		g2d.fillOval(3, 3, h - 6, h - 6);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.green);
		g2d.drawString(desc + curr + "/" + total, 5, h / 2);
		g2d.dispose();
	}

	private void paintBallPercent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.pink);
		h = w < h ? w : h;
		g2d.fillOval(0, 0, h, h);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.RED);
		g2d.fillArc(0, 0, h, h, 90, (int) (360 * -curr * 1.0 / total));
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.RED);
		g2d.fillOval(3, 3, h - 6, h - 6);
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.green);
		double num = curr * 1.0 / total * 100;
		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
		String str = df.format(num);// 返回的是String类型
		g2d.drawString(desc + str + "%", 5, h / 2);
		g2d.dispose();
	}

	/**
	 * 获取desc
	 * 
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * 设置desc
	 * 
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setVal(int v) {
		curr = v;
		repaint();
	}

	public void setHeight(int i) {
		this.h  =3;
		setPreferredSize(new Dimension(0, 3));
	}

}
