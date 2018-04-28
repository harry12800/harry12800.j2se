package cn.harry12800.j2se.component.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import cn.harry12800.j2se.style.UI;

public class LabelButton extends JLabel {
		boolean hover =false;
		private int w;
		private int h;
		private String name;
		private int nameLen = 0;
		public LabelButton(String name,int w,int h) {
			super("<html><body><div text-align='center'>"+name+"</div></body></html>");
			this.w =w;
			this.h=h;
			this.name=name;
			nameLen =  getFontSize(name)/2;
			setToolTipText(name);
			setPreferredSize(new Dimension(w,h));
			setFont(UI.normalFont(10));
			Color color = new Color(231,224,224);
			setForeground(color);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Color color = new Color(255,255,255);
					setForeground(color);
					hover = true;
					revalidate();
				}
				@Override
				public void mouseExited(MouseEvent e) {
					Color color = new Color(186,186,186);
					setForeground(color);
					hover = false;
					revalidate();
				}
				public void mouseClicked(MouseEvent e) {
//					dispose();
//					exe(LabelButton.this.name);
				}
			});
		}
		private int getFontSize(String content) {
			int byteLen = 0;
			for (int i = 0; i < content.length(); i++) {
				char ch = content.charAt(i);
				if (ch > 128)
					byteLen += 2;
				else
					byteLen++;
				 
			}
			return byteLen;
		}
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			GradientPaint p2;
			if(hover){
				p2 = new GradientPaint(0, 1, new Color(186, 131, 164, 200), 0, h - 10,
						new Color(255, 255, 255, 255));
				g2d.setPaint(p2);
				g2d.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
			}
			else{
				p2 = new GradientPaint(0, 1, new Color(150, 150, 150, 50), 0, h - 10,
						new Color(160,160, 160, 100));
				g2d.setPaint(p2);
				g2d.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
			}
			GradientPaint p1 = new GradientPaint(0, 1, new Color(255, 255, 255, 255), 0, h - 10,
					new Color(255,255, 255, 255));
			g2d.setPaint(p1);
			g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); //设置新的画刷
			Font font = new Font("宋体", Font.BOLD, 12);
			g2d.setFont(font);
			g2d.setColor(Color.RED);
			g2d.drawString(name, w/2-15*(nameLen/2), h/2+5);
			g2d.dispose();
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}