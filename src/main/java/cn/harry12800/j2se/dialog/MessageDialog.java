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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;

import javax.swing.JDialog;
import javax.swing.JPanel;

import cn.harry12800.j2se.action.DragListener;
import cn.harry12800.j2se.component.MButton1;
import cn.harry12800.j2se.component.btn.ClickAction;
import cn.harry12800.j2se.style.UI;

@SuppressWarnings("serial")
public class MessageDialog extends JDialog {

	int length = 0;
	private Frame frame;

	public MessageDialog(Frame frame, String title, String text) {
		super(frame);
		this.frame = frame;
		try {
			length = text.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		setUndecorated(true);
		setModal(true);
		//		getLayeredPane().setOpaque(false);
		//		getRootPane().setOpaque(false);
		//		getRootPane().setBackground (new Color (0, 0, 0, 0));
		//		getLayeredPane().setBackground (new Color (0, 0, 0, 0));
		setLayout(null);
		ContentPane contentPane = new ContentPane(title, text, length);
		setContentPane(contentPane);
		setSize(contentPane.getWidth(), contentPane.getHeight());
		//		getGlassPane().setBackground (new Color (0, 0, 0, 0));
		setCenterScreen();
		setBackground(new Color(0, 0, 0, 0));
		new DragListener(this);
		setVisible(true);

	}

	public void setCenterScreen() {
		//		Toolkit tk = Toolkit.getDefaultToolkit();
		//		Dimension d = tk.getScreenSize();
		//		
		//		int w = (int) d.getWidth();
		//		int h = (int) d.getHeight();
		if (frame == null) {
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();
			int w = (int) d.getWidth();
			int h = (int) d.getHeight();
			this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
			return;
		}
		int x2 = frame.getX();
		int y2 = frame.getY();
		int width2 = frame.getWidth();
		int height2 = frame.getHeight();
		//		this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
		this.setLocation(x2 + (width2 - this.getWidth()) / 2, y2 + (height2 - this.getHeight()) / 2);
	}

	class ContentPane extends JPanel {
		private String title;
		private String text;
		private int len;
		MButton1 save = new MButton1("确定", 70, 28, MButton1.createBgColorBuilder(UI.foreColor));

		public ContentPane(String title, String text, int length) {
			this.title = title;
			this.text = text;
			this.len = length / 2;
			setBackground(UI.backColor);
			int width = len * 15;
			if (width < 300) {
				width = 300;
			}
			setLayout(null);
			add(save);
			save.setBounds(width / 2 - save.getWidth() / 2, 50, save.getWidth(), save.getHeight());
			save.addMouseListener(new ClickAction(save) {
				@Override
				public void leftClick(MouseEvent e) {
					MessageDialog.this.dispose();
				}
			});
			setSize(width, 90);
			save.setFocusable(true);
			save.requestFocus();
			save.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					int keyCode = e.getKeyCode();
					if (keyCode == 10)
						MessageDialog.this.dispose();
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {

			int w = getWidth();
			int h = getHeight();
			Graphics2D g2d = (Graphics2D) g.create();

			g2d.setColor(UI.backColor);
			g2d.fillRect(0, 0, w, h);
			Stroke stroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); //设置新的画刷
			g2d.setColor(Color.WHITE);
			g2d.drawRect(0, 0, w - 1, h - 1);
			g2d.setStroke(stroke);
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("宋体", Font.PLAIN, 12));
			g2d.drawString(title, 5, 15);
			GradientPaint p2 = new GradientPaint(0, 0, new Color(255, 255, 255, 255), w, 0, new Color(255, 255, 255, 0));
			g2d.setPaint(p2);
			g2d.drawLine(2, 20, w - 2, 20);
			g2d.setFont(new Font("宋体", Font.PLAIN, 12));
			g2d.setColor(Color.WHITE);
			g2d.drawString(text, w / 2 - 15 * (len / 2) + 10, 45);
			g2d.dispose();
		}
	}
}
