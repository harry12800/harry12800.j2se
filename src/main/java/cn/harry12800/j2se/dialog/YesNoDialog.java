package cn.harry12800.j2se.dialog;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;

import cn.harry12800.j2se.action.DragListener;
import cn.harry12800.j2se.component.MButton1;
import cn.harry12800.j2se.component.btn.ClickAction;
import cn.harry12800.j2se.style.UI;

@SuppressWarnings("serial")
public class YesNoDialog extends JDialog{

	private Frame frame;
	private cn.harry12800.j2se.dialog.YesNoDialog.Callback callback;
	public interface Callback{
		void callback(boolean isYes);
	}
	public YesNoDialog(Frame frame,String title,Callback callback) {
		super(frame);
		this.frame = frame;
		setUndecorated(true);
		setModal(true);
		setLayout(null);
		this.callback=callback;
		ContentPane contentPane = new ContentPane(title);
		setContentPane(contentPane);
		setSize(contentPane.getWidth(),contentPane.getHeight());
		setCenterScreen();
		setBackground (new Color (0, 0, 0, 0));
		new DragListener(this);
		setVisible(true);
	}
	public void setCenterScreen() {
//		Toolkit tk = Toolkit.getDefaultToolkit();
//		Dimension d = tk.getScreenSize();
		if(frame == null){
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();
			int w = (int) d.getWidth();
			int h = (int) d.getHeight();
			this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
			return ;
		}
		int x2 = frame.getX();
		int y2 = frame.getY();
		int width2 = frame.getWidth();
		int height2 = frame.getHeight();
//		this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
		this.setLocation(x2+(width2- this.getWidth()) / 2,y2+ (height2 - this.getHeight()) / 2);
	}
	class ContentPane extends JPanel{
		private String title;
		MButton1 yes = new MButton1("确定", 70, 28,MButton1.createBgColorBuilder(UI.foreColor));
		MButton1 no = new MButton1("取消", 70, 28,MButton1.createBgColorBuilder(UI.foreColor));
		public ContentPane(String title) {
			this.title =title;
			setBackground(UI.backColor);
			int width = 300;
			setLayout(null);
			add(yes);
			yes.setBounds(width/2-yes.getWidth()-20,50,yes.getWidth(),yes.getHeight());
			yes.addMouseListener(new ClickAction(yes) {
				@Override
				public void leftClick(MouseEvent e) {
					YesNoDialog.this.dispose();
					YesNoDialog.this.callback.callback(true);
				}
			});
			add(no);
			no.setBounds(width/2+20,50,no.getWidth(),no.getHeight());
			no.addMouseListener(new ClickAction(no) {
				@Override
				public void leftClick(MouseEvent e) {
					YesNoDialog.this.dispose();
					YesNoDialog.this.callback.callback(false);
				}
			});
			setSize(width,90);
		}

		@Override
		protected void paintComponent(Graphics g) {
			 
			int w = getWidth();
			int h = getHeight();
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(UI.backColor);
			g2d.fillRect(0, 0, w,h);
			Stroke stroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); //设置新的画刷
			g2d.setColor(Color.WHITE);
			g2d.drawRect(0, 0, w-1,h-1);
			g2d.setStroke(stroke);
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("宋体",Font.PLAIN,12));
			g2d.drawString(title,5,15);
			GradientPaint p2 = new GradientPaint(0, 0,new Color(255, 255, 255,255),w, 0, new Color(255, 255, 255,0));
			g2d.setPaint(p2);
			g2d.drawLine(2, 20, w-2, 20); 
			g2d.dispose();
		}
	}
	public static void main(String[] args) {
		 new YesNoDialog(null,	"wens",null);
	}
}
