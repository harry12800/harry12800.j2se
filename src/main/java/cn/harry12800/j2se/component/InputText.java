package cn.harry12800.j2se.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.action.CtrlSAction;
import cn.harry12800.j2se.action.EnterAction;
import cn.harry12800.j2se.style.UI;

public class InputText extends JTextField {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected boolean controlPressed;
		protected boolean cPressed;
		public InputText(int x) {
			super(x);
			EmptyBorder emptyBorder = new EmptyBorder(0, 5, 0, 5);
			setBorder(emptyBorder);
			setSelectionColor(new Color(36,106,204));
			setSelectedTextColor(Color.WHITE);
			setForeground(Color.WHITE);
			setBackground(UI.backColor);
			setFont(new Font("宋体", 12, 12));
			setCaretColor(Color.WHITE);
			addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
					
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
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						if(enterAction!=null){
							enterAction.enter();
						}
					}
					
				}
			});
		}
		private CtrlSAction ctrlSAction=null;
		private EnterAction enterAction=null;
		 
	
		
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
		/**
		 * 获取enterAction
		 *	@return the enterAction
		 */
		public EnterAction getEnterAction() {
			return enterAction;
		}
		/**
		 * 设置enterAction
		 * @param enterAction the enterAction to set
		 */
		public void setEnterAction(EnterAction enterAction) {
			this.enterAction = enterAction;
		}
		public InputText(int x,int fontSize) {
			super(x);
			EmptyBorder emptyBorder = new EmptyBorder(1, 1, 1, 1);
			setBorder(emptyBorder);
			setSelectionColor(new Color(36,106,204));
			setSelectedTextColor(Color.WHITE);
			setForeground(Color.WHITE);
			setBackground(UI.backColor);
			setFont(new Font("宋体", 12, fontSize));
			setCaretColor(Color.WHITE);
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int h = getHeight();
			int w = getWidth();
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawRoundRect(0, 0, w - 2, h - 2, 4, 4);
//			g2d.setColor(new Color(70,70,70));
//			g2d.fillRoundRect(1, 1, w - 3, h - 3, 5, 5);
			g2d.dispose();
			
		}
	}

	