package cn.harry12800.j2se.component.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.action.CtrlSAction;
import cn.harry12800.j2se.component.InputArea;


@SuppressWarnings("serial")
public class AreaTextPanel extends JPanel {
	final JViewport jViewport  = new JViewport();
	public InputArea area = new InputArea();
	private CtrlSAction ctrlSAction=null;
	public AreaTextPanel() {
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setLayout(new BorderLayout());
		jViewport.setView(area);
		add(jViewport,BorderLayout.CENTER);
		jViewport.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
	//				System.out.println(textPane.getBounds());
					int left = area.getLocation().x;
					int top = area.getLocation().y;
	//				System.out.println( e.getWheelRotation());
	//				Rectangle aRect = new Rectangle(left, top +e.getWheelRotation(), textPane.getBounds().width, textPane.getBounds().height);
	//				textPane.scrollRectToVisible(aRect );
	//				System.out.println(rootPane);
	//				System.out.println(componentCount);
	//				port.setViewPosition(new Point(0, 50));
					if( e.getWheelRotation()>0)
					for (int i = 0; i < 8; i++) {
						top = area.getLocation().y;
						if(jViewport.getHeight()-top<area.getBounds().height)
							area.setLocation(left, top - e.getWheelRotation());
					}
					if( e.getWheelRotation()<0)
						for (int i = 0; i < 8; i++) {
							top = area.getLocation().y;
							if(top<=0)
								area.setLocation(left, top - e.getWheelRotation());
						}
						
				}
			});
		area.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					controlPressed = false;
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					cPressed = false;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					controlPressed = true;
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					cPressed = true;
				}

				if (controlPressed && cPressed) {
					// 当Ctr + C 被按下时, 进行相应的处理.
					controlPressed = false;
					cPressed = false;
					if(ctrlSAction!=null){
						ctrlSAction.ctrlS();
					}
				}
				
			}
		});
	}
	 
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(0, 0,getWidth()-1, getHeight()-1);
	}
	public void setText(String srcByFilePath) {
		area.setText(srcByFilePath);
	}
	private boolean controlPressed = false;
	private boolean cPressed = false;
	/**
	 * 获取ctrlSAction
	 *	@return the ctrlSAction
	 */
	public CtrlSAction getCtrlSAction() {
		return ctrlSAction;
	}
	/**
	 * 设置ctrlSAction
	 * @param ctrlSAction the ctrlSAction to set
	 */
	public void setCtrlSAction(CtrlSAction ctrlSAction) {
		this.ctrlSAction = ctrlSAction;
	}
	
	@Override
	public synchronized void addKeyListener(KeyListener l) {
		area.addKeyListener(l);
	}
	public String getText(){
		return area.getText().trim();
	}
}
