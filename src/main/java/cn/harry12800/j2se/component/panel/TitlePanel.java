package cn.harry12800.j2se.component.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.action.MyActionListener;
import cn.harry12800.j2se.component.TitleNameLabel;
import cn.harry12800.j2se.component.btn.LogoLabel;
import cn.harry12800.j2se.component.utils.ImageUtils;
import cn.harry12800.j2se.popup.PopupFrame;
import cn.harry12800.j2se.style.J2seColor;

public class TitlePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TitleButton minBtn;
	private TitleButton exitBtn;
	private TitleButton maxBtn;
	private MenuButton menuButton;
	private Builder builder;
	private LogoLabel logoBtn;
	public static enum TitleHeight {
		large, middle, small;
	}

	public static class Builder {
		public boolean hasLogo = false;
		public boolean hasMax = false;
		public boolean hasMenu = false;
		public boolean hasMin = false;
		public boolean hasTitle = false;
		public String logoImageName;
		public String name;
		public Window window;
		public TitleHeight titleHeight = TitleHeight.small;
		public PopupFrame popupFrame;
	}

	@Override
	protected void paintComponent(Graphics g) {
		setSize(getWidth(), getHeight());
		setPreferredSize(new Dimension(getWidth(), getHeight()));
		if(exitBtn!=null){
			exitBtn.setBounds(getWidth()-exitBtn.getWidth()-10,5, exitBtn.getWidth(), exitBtn.getHeight());
		}
		if(maxBtn!=null){
			maxBtn.setBounds(getWidth()-exitBtn.getWidth()*2-25,5, maxBtn.getWidth(), maxBtn.getHeight());
		}
		if(minBtn!=null){
			minBtn.setBounds(getWidth()-minBtn.getWidth()*3-40,5, minBtn.getWidth(), minBtn.getHeight());
		}
		if(menuButton!=null){
			menuButton.setBounds(getWidth()-minBtn.getWidth()*4-55,5, menuButton.getWidth(), menuButton.getHeight());
		} 
		int h = getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(J2seColor.getBackgroundColor());
		GradientPaint p1;
		p1 = new GradientPaint(0, 0, new Color(255, 255, 255,0), getWidth(),0, new Color(
				255, 255, 255,200));
		g2d.setPaint(p1);
		g2d.drawLine(0, h-1,getWidth(), h-1);
		super.paintComponent(g);
	}

	public TitlePanel(Builder builder) {
		this.builder = builder;
		if(builder.titleHeight == TitleHeight.large)		{
			setSize(200, 30);
			setPreferredSize(new Dimension(200, 30));
		}
		if(builder.titleHeight == TitleHeight.middle)		{
			setSize(200, 30);
			setPreferredSize(new Dimension(200, 30));
		}
		if(builder.titleHeight == TitleHeight.small)		{
			setSize(200, 10);
			setPreferredSize(new Dimension(200, 10));
		}
		setBorder(new EmptyBorder(5, 0, 5, 0));
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		if (builder.hasLogo) {
			logoBtn = new LogoLabel("logo_lan");
			logoBtn.setBounds(0, 0, logoBtn.getWidth(), logoBtn.getHeight());
			add(logoBtn);
		}
		if (builder.hasTitle) {
			TitleNameLabel logolab = new TitleNameLabel(
					builder.window.getName());
			logolab.setBounds(logoBtn==null?0:logoBtn.getWidth()+4,3, logolab.getWidth(),
					logolab.getHeight());
			add(logolab);
		}
		if (builder.hasMenu) {
			menuButton = new MenuButton(builder);
			add(menuButton);
		}
		if (builder.hasMin) {
			minBtn = new TitleButton("min", "最小化", builder);
			add(minBtn);
		}
		if (builder.hasMax) {
			maxBtn = new TitleButton("max", "最大化", builder);
			add(maxBtn);
		}
		exitBtn = new TitleButton("close", "关闭", builder);
		add(exitBtn);
		init();
	}

	private void init() {
		exitBtn.addActionListener(new MyActionListener() {
			@Override
			public void exe(MouseEvent e) {
				builder.window.dispose();
			}
		});
		exitBtn.setCursor(new Cursor(12));
		if (maxBtn != null) {
			maxBtn.setCursor(new Cursor(12));
			maxBtn.addActionListener(new MyActionListener() {
				@Override
				public void exe(MouseEvent e) {
					if (builder.window instanceof Frame) {
						Frame s = (Frame) builder.window;
						if (s.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
							s.setExtendedState(JFrame.NORMAL);
						} else {
							s.setExtendedState(JFrame.MAXIMIZED_BOTH);
						}
						s.revalidate();
					}
				}
			});
		}
		if (minBtn != null) {
			minBtn.setCursor(new Cursor(12));
			minBtn.addActionListener(new MyActionListener() {
				@Override
				public void exe(MouseEvent e) {
					if (builder.window instanceof Frame) {
						Frame s = (Frame) builder.window;
						s.setExtendedState(JFrame.ICONIFIED);
					}
				}
			});
		}
	}

	public class MenuButton extends JButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int width;
		public int height;
		boolean hover = false;
		BufferedImage byName;
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
				this.width = 20;
				this.height = 20;
				break;
			default:
				break;
			}
			byName= ImageUtils.getByName("selected-bars.png");
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			setIcon(new ImageIcon(byName));
			setOpaque(false);
			setContentAreaFilled(false);
			setBorder(null);
			setSize(width,height);
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
					if (contains(e.getPoint())) {
						builder.popupFrame.show(e.getPoint(), MenuButton.this);
					}
				}
			});
		}
	}

	public static Builder createBuilder() {
		return new Builder();
	}

	public static Builder createBuilder(Window window) {
		Builder builder = new Builder();
		builder.window = window;
		return builder;
	}

	public static Builder createHasLogoBuilder(Window window) {
		Builder builder = new Builder();
		builder.window = window;
		builder.hasLogo = true;
		return builder;
	}

	public static Builder createHasMaxMinBuilder(Window window) {
		Builder builder = new Builder();
		builder.window = window;
		builder.hasMax = true;
		builder.hasMin = true;
		return builder;
	}
}
