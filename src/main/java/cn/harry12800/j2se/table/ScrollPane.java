package cn.harry12800.j2se.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JViewport;

import cn.harry12800.j2se.style.UI;

@SuppressWarnings("serial")
public class ScrollPane extends JLayeredPane {

	int contentHeight = 100;
	int height = 100;
	int barHeight = 0;
	Bar bar;
	boolean enter =	false;
	int barmove;
	int panelmove;
	private JComponent p;
	final JViewport port = new JViewport();
	public ScrollPane(final JComponent p) {
		this.p = p;
//		setSize(p.getWidth()+2, height+2);
		setLayout(new BorderLayout());
//		setBackground(Color.WHITE);
		//p.setBounds(1, 1, p.getWidth(), p.getHeight());
//		JPanel jPanel = new JPanel() {
//			protected void paintComponent(Graphics g) {
//				g.setColor(Color.WHITE);
//				g.drawRect(0, 0, getWidth()-1, getHeight()-1);
//				g.dispose();
//			}
//		};
//		jPanel.setOpaque(false);
		//jPanel.setBounds(0, 0, getWidth(), getHeight());
//		add(jPanel,BorderLayout.CENTER);
//		moveToFront(jPanel);
		
		port.setView(p);
		add(port,BorderLayout.CENTER);
	 	p.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) { 
			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
				barmove = height - barHeight;
				panelmove = contentHeight - height;
				if(bar==null)return ;
				int left = bar.getLocation().x;
				int top = bar.getLocation().y;
				int x = top + e.getWheelRotation();
				if (x > 0 && x < barmove) {
					bar.setLocation(left, top + e.getWheelRotation());
					port.setLocation(0, -new Double(1.0 * x * panelmove / barmove)
							.intValue());
				}
				for (int i = 0; i <8 ; i++) {
					left = bar.getLocation().x;
					top = bar.getLocation().y;
					x = top + e.getWheelRotation();
					if (x > 0 && x < barmove) {
						bar.setLocation(left, top + e.getWheelRotation());
						port.setLocation(0, -new Double(1.0 * x * panelmove / barmove)
								.intValue());
					}
				}
				
			}
		});
	}

	@Override
	protected void paintChildren(Graphics g) {
		contentHeight = p.getHeight();
		height = getHeight();
		barHeight = new Double(height * 1.0 / contentHeight * height)
		.intValue();
		if (barHeight < height) {
			if(bar == null)
			{	bar = new Bar(barHeight);
				setLayout(null);
				bar.setBounds(p.getWidth() - 5, 0, 5, barHeight);
				port.setBounds(0, 0, p.getWidth(), p.getHeight());
				add(bar);
				moveToFront(bar);
			}
		}
		super.paintChildren(g);
	}
	public class Bar extends JPanel {

		boolean drag = false;
		public Bar(int barHeight) {
			setSize(5, barHeight);
			setOpaque(false);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					drag =true;
					super.mouseEntered(e);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					drag =false;
					super.mouseExited(e);
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(UI.foreColor);
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 2, 2);
			g2d.dispose();
		}
	}

	public static void main(String[] args) {
		JPanel p = new JPanel();
		p.setSize(150, 360);
		p.setLayout(null);
		for (int i = 0; i < 100; i++) {
			JButton jButton = new JButton("btn" + i);
			jButton.setBounds(0, i * 30, 100, 30);
			p.add(jButton);
		}
		JPanel jp = new JPanel();
		jp.setLayout(null);
//		SlidePanel slidePanel = new SlidePanel(p);
//		slidePanel.setBounds(0, 0, slidePanel.getWidth(),
//				slidePanel.getHeight());
//		jp.add(slidePanel);
//		Clip.seeCom(jp);
	}

	public void setView(final JPanel p, int height) {
		this.p =p;
		this.height = height;
		contentHeight = p.getHeight();
		setSize(p.getWidth()+2, height+2);
		barHeight = new Double(height * 1.0 / contentHeight * height)
				.intValue();
		setLayout(null);
		p.setBounds(1, 1, p.getWidth(), p.getHeight());
		if (barHeight < height) {
			bar = new Bar(barHeight);
			bar.setBounds(p.getWidth() - 2, 0, 2, barHeight);
			add(bar);
			moveToFront(bar);
		}
		setBackground(Color.BLACK);
		barmove = height - barHeight;
		panelmove = contentHeight - height;
		add(p);
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				enter = true;
				bar.setVisible(true);
//				System.out.println("mouseEntered");
			}

			@Override
			public void mouseExited(MouseEvent e) {
//				int x2 = e.getX();
//				int y2 = e.getY();
				//System.out.println(x2+" "+y2);
				//System.out.println(getWidth()+" "+getHeight());
//				enter=false;
//				if(x2<=0||x2>=getWidth())
//					bar.setVisible(false);
//				if(y2<=0||y2>=getHeight())
//					bar.setVisible(false);
				//System.out.println("mouseExited");
			}
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				//System.out.println("mouseWheelMoved");
			}
		});
		addMouseWheelListener(new MouseWheelListener() {

			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int left = bar.getLocation().x;
				int top = bar.getLocation().y;
				int x = top + e.getWheelRotation();
				if (x > 0 && x < barmove) {
					bar.setLocation(left, top + e.getWheelRotation());
					p.setLocation(0, -new Double(1.0 * x * panelmove / barmove)
							.intValue());
				}
				left = bar.getLocation().x;
				top = bar.getLocation().y;
				x = top + e.getWheelRotation();
				if (x > 0 && x < barmove) {
					bar.setLocation(left, top + e.getWheelRotation());
					p.setLocation(0, -new Double(1.0 * x * panelmove / barmove)
							.intValue());
				}
				left = bar.getLocation().x;
				top = bar.getLocation().y;
				x = top + e.getWheelRotation();
				if (x >= 0 && x <= barmove) {
					bar.setLocation(left, top + e.getWheelRotation());
					p.setLocation(0, -new Double(1.0 * x * panelmove / barmove)
							.intValue());
				}
				left = bar.getLocation().x;
				top = bar.getLocation().y;
				x = top + e.getWheelRotation();
				if (x >= 0 && x <= barmove) {
					bar.setLocation(left, top + e.getWheelRotation());
					p.setLocation(0, -new Double(1.0 * x * panelmove / barmove)
							.intValue());
				}
				left = bar.getLocation().x;
				top = bar.getLocation().y;
				x = top + e.getWheelRotation();
				if (x >= 0 && x <= barmove) {
					bar.setLocation(left, top + e.getWheelRotation());
					p.setLocation(0, -new Double(1.0 * x * panelmove / barmove)
							.intValue());
				}
				left = bar.getLocation().x;
				top = bar.getLocation().y;
				x = top + e.getWheelRotation();
				if (x >= 0 && x <= barmove) {
					bar.setLocation(left, top + e.getWheelRotation());
					p.setLocation(0, -new Double(1.0 * x * panelmove / barmove)
							.intValue());
				}
			}
		});
	}

	public void reset() {
		int x=0;
		if(bar!=null){
			x= bar.getLocation().y;
			remove(bar);
		}
		contentHeight = p.getHeight();
		setSize(p.getWidth()+2, height+2);
		barHeight = new Double(height * 1.0 / contentHeight * height)
				.intValue();
		setLayout(null);
		p.setBounds(1, 1, p.getWidth(), p.getHeight());
		if (barHeight < height) {
			bar = new Bar(barHeight);
			bar.setBounds(p.getWidth() - 2,x, 2, barHeight);
			add(bar);
			moveToFront(bar);
		}
		setBackground(Color.BLACK);
		barmove = height - barHeight;
		panelmove = contentHeight - height;
		return ;
	}
	public Point getData(){
		return p.getLocation();
	}
	public void setData(Point p){
		this.p.setLocation(p);
	}
}
