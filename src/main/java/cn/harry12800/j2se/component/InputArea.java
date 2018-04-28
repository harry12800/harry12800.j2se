package cn.harry12800.j2se.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.style.UI;


public class InputArea extends JTextArea {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InputArea() {
			EmptyBorder emptyBorder = new EmptyBorder(5, 5, 5, 5);
			setBorder(emptyBorder);
			setSelectionColor(new Color(36,106,204));
			setSelectedTextColor(Color.WHITE);
			setForeground(Color.WHITE);
			setBackground(UI.backColor);
			setFont(new Font("宋体", 12, 12));
			setCaretColor(Color.WHITE);
			setLineWrap(true);        //激活自动换行功能 
			//setWrapStyleWord(true); 
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
//			int h = getHeight();
//			int w = getWidth();
//			Graphics2D g2d = (Graphics2D) g.create();
//			g2d.drawRoundRect(0, 0, w - 2, h - 2, 4, 4);
//			g2d.setColor(new Color(70,70,70));
//			g2d.fillRoundRect(1, 1, w - 3, h - 3, 5, 5);
//			g2d.dispose();
			
		}
	}

	