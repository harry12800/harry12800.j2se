package cn.harry12800.j2se.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.style.UI;

public class MButton extends JButton {
	boolean hover = false;
	private int w;
	private int h;
	private String name;
	private ClickAction clickAction;
	private HoverListener hoverAction;
	public boolean hasborder = true;
	public boolean handCursor = true;
	public boolean hasTip;
	public boolean hasCheck = false;
	public boolean checked = false;
	public Image image= null;
	public Color bgcolor;
	public int borderRadius= 0;
	/**
	 * 
	 * @param name
	 * @param w
	 * @param h
	 * @param hasBorder
	 * @param handCursor
	 * @param hasTip
	 * @param hasCheck
	 * @param checked
	 */
	public MButton(String name, int w, int h) {
		this.w = w;
		this.h = h;
		this.name = name;
		setPreferredSize(new Dimension(this.w, this.h));
		setSize(this.w, this.h);
		setText(name);
		setFont(UI.微软雅黑Font);
		setOpaque(false);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==10){
					if(clickAction!=null){
						clickAction.leftClick(null);
					}
				}
			}
		});
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				hover = false;
				repaint();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				hover = true;
				repaint();				
			}
		});
		setMinimumSize(new Dimension(w, h) );
		setMaximumSize( new Dimension(w, h) );
		setPreferredSize(new Dimension(w, h));
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setForeground(UI.fontColor);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hover = true;
				repaint();
				if(hoverAction!=null)
					hoverAction.hover(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
				repaint();
				if(hoverAction!=null)
					hoverAction.out();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
				}
				super.mouseReleased(e);
			}
		});
	}

	public interface ActionListener {
		void changed(boolean checked);
	}

	public void addMouseListener(ClickAction a) {
		this.clickAction = a;
		super.addMouseListener(clickAction);
	}

	public interface ChangeListener {
		void changed(boolean checked);
	}
	public interface HoverListener {
		void hover( MouseEvent e);
		void out();
	}
	public void addHoverListener(HoverListener a) {
		this.hoverAction = a;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(hover) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(UI.foreColor(200));
			g2d.fillRoundRect(0, 0, w-1 , h-1, w,h/2);
		}else if(bgcolor!=null) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(bgcolor);
			g2d.fillRoundRect(0, 0, w-1 , h-1, w,h/2);
		}
		g.dispose();
//		Graphics2D g2d = (Graphics2D) g.create();
//		GradientPaint p2;
//		if (builder.bgcolor != null) {
//			g2d.setColor(builder.bgcolor);
//			g2d.fillRoundRect(0, 0, w , h, builder.borderRadius*2, builder.borderRadius*2);
//		}
//		if (builder.hasborder)
//			if (hover) {
//				p2 = new GradientPaint(0, 0, new Color(186, 131, 164, 200), 0, h, new Color(255, 255, 255, 255));
//				g2d.setPaint(p2);
//				g2d.drawRoundRect(0, 0, w-1 , h-1, builder.borderRadius*2, builder.borderRadius*2);
//			} else {
//				p2 = new GradientPaint(0, 1, new Color(150, 150, 150, 50), 0, h, new Color(160, 160, 160, 100));
//				g2d.setPaint(p2);
//				g2d.drawRoundRect(0, 0, w-1 , h-1, builder.borderRadius*2, builder.borderRadius*2);
//			}
//		
//		GradientPaint p1 = new GradientPaint(0, 1, new Color(255, 255, 255, 255), 0, h ,
//				new Color(255, 255, 255, 255));
//		g2d.setPaint(p1);
//		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); // 设置新的画刷
//		g2d.setFont(new Font("宋体", Font.PLAIN, 12));
//		g2d.drawString(name, w / 2 - 15 * (nameLen / 2), h / 2 + 5);
//		if(builder.image!=null)
//		g2d.drawImage(builder.image, 3, 3, h-6, h-6, null);
//		g2d.dispose();
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	public void setHover(boolean hover) {
		this.hover = hover;
		repaint();
	}

	/**
	 * 获取name
	 *	@return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
