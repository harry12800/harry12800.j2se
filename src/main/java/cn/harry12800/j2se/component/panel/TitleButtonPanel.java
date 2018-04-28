package cn.harry12800.j2se.component.panel;
 
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import cn.harry12800.j2se.action.MyActionListener;
import cn.harry12800.j2se.component.panel.TitlePanel.Builder;

public class TitleButtonPanel  extends JLayeredPane {
	
	private TitleButton minBtn;
	private TitleButton exitBtn;
	private TitleButton maxBtn;
	private Builder builder;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TitleButtonPanel(Builder builder) {
		setSize(100, 50);
		this.builder = builder;
		switch (builder.titleHeight) {
		case large:
			setLayout(new FlowLayout(FlowLayout.RIGHT,10,3));
			break;
		case middle:
			setLayout(new FlowLayout(FlowLayout.RIGHT,10,2));
			break;
		default:
			setLayout(new FlowLayout(FlowLayout.RIGHT,10,0));
			break;
		}
		setBorder(null);
//		setOpaque(false);
//		setBackground(new Color(0,0,0,0));
		if(builder.hasMenu) {
			MenuButton menuButton = new MenuButton(builder);
			add(menuButton);
		}
		if(builder.hasMin) {
			minBtn = new TitleButton("min","最小化",builder);
			add(minBtn);
		}
		if(builder.hasMax) {
			maxBtn = new TitleButton("max","最大化",builder);
			add(maxBtn);
		}
		exitBtn = new TitleButton("exit","关闭",builder);
		add(exitBtn);
		init();
	}
	
	class MenuButton extends JLabel {
		public int width;
		public int height;
		boolean hover =false;
		public MenuButton(final Builder builder) {
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
				this.width = 15;
				this.height = 15;
				break;
			default:
				break;
			}
			setPreferredSize(new Dimension(width, height));
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					hover = true;
					repaint();
				}
				@Override
				public void mouseExited(MouseEvent e) {
					hover = false;
					repaint();
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					if(contains(e.getPoint())){
						builder.popupFrame.show(e.getPoint(),MenuButton.this);
					}
				}
			});
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			if(hover){
				g2d.setColor(Color.WHITE);
			}else{
				g2d.setColor(Color.BLACK);
			}
			g2d.drawLine(2, 6, 14, 6);
			g2d.drawLine(2, 11, 14, 11);
			g2d.drawLine(2, 16, 14, 16);
			g2d.dispose();
		}
		 
	}
	private void init() {
		exitBtn.addActionListener(new MyActionListener() {
			@Override
			public void exe(MouseEvent e) {
				builder.window.dispose();
			}
		});
		exitBtn.setCursor(new Cursor(12));
		if(maxBtn!=null){
			maxBtn.setCursor(new Cursor(12));
			maxBtn.addActionListener(new MyActionListener() {
				@Override
				public void exe(MouseEvent e) {
					if(builder.window instanceof Frame){
						Frame s = (Frame)builder.window;
						if(s.getExtendedState() == JFrame.MAXIMIZED_BOTH){
							s.setExtendedState(JFrame.NORMAL);
						}
						else {
							s.setExtendedState(JFrame.MAXIMIZED_BOTH);
						}	
					}
				}
			});
		}
		if(minBtn!=null) {
			minBtn.setCursor(new Cursor(12));
			minBtn.addActionListener(new MyActionListener() {
				@Override
				public void exe(MouseEvent e) {
					if(builder.window instanceof Frame){
						Frame s = (Frame)builder.window;
						s.setExtendedState(JFrame.ICONIFIED);
					}
				}
			});
		}
	}
}
