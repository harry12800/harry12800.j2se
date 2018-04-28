package cn.harry12800.j2se.component.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import cn.harry12800.j2se.action.MyActionListener;
import cn.harry12800.j2se.component.panel.TitlePanel.Builder;
import cn.harry12800.j2se.component.utils.ImageUtils;

public class TitleButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Color BUTTON_FOREGROUND_COLOR = Color.BLACK;
	private boolean hover;
	public int width;
	public int height;
	public String srcName = null;
	BufferedImage hoverImage;
	BufferedImage image;

	public TitleButton(String src, String hit,Builder builder) {
		this();
		switch (builder.titleHeight) {
		case large:
			this.width = 20;
			this.height = 20;
			break;
		case small:
			this.width = 10;
			this.height = 10;
			break;
		case middle:
			this.width = 20;
			this.height = 20;
			break;
		default:
			break;
		}
		this.srcName = src;
		setToolTipText(hit);
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		hoverImage = ImageUtils.getByName(srcName+".png");
		image = ImageUtils.getByName(srcName+"_hover.png");
	}
	public TitleButton(String src, String hit) {
		this();
		this.width = 10;
		this.height = 10;
		this.srcName = src;
		
		setToolTipText(hit);
		setPreferredSize(new Dimension(width, height));
		hoverImage = ImageUtils.getByName(srcName+"_hover.png");
		image = ImageUtils.getByName(srcName+".png");
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image==null||hoverImage==null)return ;
//		Graphics2D g2d = (Graphics2D) g.create();
//		Paint p1 = new GradientPaint(0, 1,J2seColor.getBackgroundColor(), 0, height - 10,
//				J2seColor.getBackgroundColor());
//		g2d.setPaint(p1);
//		g2d.drawRect(1, 1, w - 3, h - 3);
		//sg2d.fillRect(0, 0, width , height);
//		g2d.dispose();
		Graphics2D g2d = (Graphics2D) g.create();
//		float tran = hover ? 1.0f : 0.8f;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (hover) g2d.drawImage(hoverImage, 0, 0, width, height, null);
		else g2d.drawImage(image, 0, 0, width, height, null);
//		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
//				tran));
		g2d.dispose();
	}
//
//	@Override
//	public void paint(Graphics g) {
//		if(image==null||hoverImage==null)return ;
//		//update(g);
//		Graphics2D g2d = (Graphics2D) g.create();
//		float tran = hover ? 1.0f : 0.8f;
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//				RenderingHints.VALUE_ANTIALIAS_ON);
//		if (hover)
//			g2d.drawImage(hoverImage, 0, 0, w, h, null);
//		else
//			g2d.drawImage(image, 0, 0, w, h, null);
//		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
//				tran));
//		g2d.dispose();
//	}


	private TitleButton() {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setBorder(null);
		setBorderPainted(false);
		setOpaque(false);
		setContentAreaFilled(false);
		addMouseListener(new MouseHandler(this));
	}

	public Image bgImage;
	private MyActionListener action;
	Image offScreenImage = null; // 虚拟图片
//	@Override
//	public void update(Graphics g) {
//		 g.clearRect(0, 0, width, height);  
//		if (offScreenImage == null) {
//			offScreenImage = this.createImage( width, height);
//		}
//		Graphics gOffScreen = offScreenImage.getGraphics();
//		Color c = gOffScreen.getColor();
//		gOffScreen.setColor(new Color(237,184,132));
//		gOffScreen.fillRect(0, 0, width, height);
//		gOffScreen.setColor(c);
//		g.drawImage(offScreenImage, 0, 0, null);
//	}

	public void addActionListener(MyActionListener a) {
		this.action = a;
	}
	 // 处理进入、离开图片范围的消息  
    class MouseHandler extends MouseAdapter {  
        private TitleButton titleButton;

		public MouseHandler(TitleButton titleButton) {
        	this.titleButton  = titleButton;
		}

		public void mouseExited(MouseEvent e) {  
//        	if (contains(e.getX(), e.getY()))  
//            	hover = true;  
//            else  
            	hover = false;  
            	titleButton.repaint(); 
        }  
  
        public void mouseEntered(MouseEvent e) {  
        	hover = true;
        	titleButton.repaint(); 
        }  
  
        public void mouseReleased(MouseEvent e) {  
//            // 防止在按钮之外的地方松开鼠标  
            if (contains(e.getX(), e.getY()))  {
            	action.exe(e);
            }
        }
    }  
}
