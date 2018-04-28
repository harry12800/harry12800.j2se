package cn.harry12800.j2se.dialog;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;

import cn.harry12800.j2se.component.InputText;
import cn.harry12800.j2se.component.MButton1;
import cn.harry12800.j2se.component.btn.ClickAction;
import cn.harry12800.j2se.style.UI;

@SuppressWarnings("serial")
public class InputMessageDialog extends JDialog{

	int length = 0;
	private Frame frame;
	public interface Callback{
		void callback(String string);
	}
	public InputMessageDialog(Frame frame,String title,String text,Callback callback) {
		super(frame);
		this.frame = frame;
		setUndecorated(true);
		setModal(true);
		getLayeredPane().setOpaque(false);
		getRootPane().setOpaque(false);
//		getRootPane().setBackground (new Color (0, 0, 0, 0));
//		getLayeredPane().setBackground (new Color (0, 0, 0, 0));
		setLayout(null);
		ContentPane contentPane = new ContentPane(title,text,callback);
		setContentPane(contentPane);
		setSize(contentPane.getWidth(),contentPane.getHeight());
//		getGlassPane().setBackground (new Color (0, 0, 0, 0));
		setCenterScreen();
		setBackground ( UI.backColor);
		new cn.harry12800.j2se.action.DragListener(this);
		setVisible(true);
	}
	public void setCenterScreen() {
//		Toolkit tk = Toolkit.getDefaultToolkit();
//		Dimension d = tk.getScreenSize();
//		
//		int w = (int) d.getWidth();
//		int h = (int) d.getHeight();
		int x2 = frame.getX();
		int y2 = frame.getY();
		int width2 = frame.getWidth();
		int height2 = frame.getHeight();
//		this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
		this.setLocation(x2+(width2- this.getWidth()) / 2,y2+ (height2 - this.getHeight()) / 2);
	}
	class ContentPane extends JPanel{
		private String title;
		MButton1 save = new MButton1("确定", 70, 28,MButton1.createBgColorBuilder(UI.foreColor));
		MButton1 cancel = new MButton1("取消", 70, 28,MButton1.createBgColorBuilder(UI.foreColor));
		public ContentPane(String title, final String text,final Callback callback) {
			this.title =title;
			setBackground(UI.backColor);
			int width = 300;
			setLayout(null);
			add(save);
			final InputText inputText = new InputText(1);
			inputText.setBounds(80,23,120,28);
			inputText.setText(text);
			inputText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode()==10){
						if(!text.equals(inputText.getText()))
							callback.callback(inputText.getText());
						InputMessageDialog.this.dispose();
					}
				}
			});
			add(inputText);
			save.setBounds(40,54,save.getWidth(),save.getHeight());
			add(cancel);
			cancel.setBounds(180,54,save.getWidth(),save.getHeight());
			save.addMouseListener(new ClickAction(save) {
				@Override
				public void leftClick(MouseEvent e) {
					callback.callback(inputText.getText());
					InputMessageDialog.this.dispose();
				}
			});
			cancel.addMouseListener(new ClickAction(save) {
				@Override
				public void leftClick(MouseEvent e) {
					InputMessageDialog.this.dispose();
				}
			});
			setSize(width,90);
			cancel.requestFocus();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
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
}
