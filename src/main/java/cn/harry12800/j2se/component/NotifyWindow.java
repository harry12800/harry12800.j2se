package cn.harry12800.j2se.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import cn.harry12800.j2se.component.panel.TitlePanel;

public class NotifyWindow extends BaseWindow {

	private static final long serialVersionUID = 1L;
	static JTextPane textPane = new JTextPane();
	private static int buffer = 50000;
	static Timer time;
	//	static{
	//		System.setOut(new PrintStream(new Output()));
	//		System.setErr(new PrintStream(new Errput()));
	//	}

	static NotifyWindow instance = new NotifyWindow();

	private NotifyWindow() {
		//		this.setSize(250, 300);
		//		//setShape(new RoundRectangle2D.Double(0, 0, 400, 300, 0, 0));
		//		ImageIcon image = new ImageIcon("image\\logo\\3.png");
		//		picture = new JLabel(image);
		//		picture.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		//		this.getLayeredPane().add(picture, new Integer(Integer.MIN_VALUE));
		//		this.setOpacity(0.93f);
		//		setContentPane(createCenterPanel());
		//		
		//		setLeftTopScreen();
		//		this.setVisible(true);
		//		new DragListener(this);
	}

	protected JComponent createCenterPanel() {
		JPanel p1 = new JPanel();
		textPane.setPreferredSize(new Dimension(400, 300));
		textPane.setEditable(true);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(textPane);
		add(scroll, BorderLayout.CENTER);
		p1.add(scroll);
		add(p1, BorderLayout.CENTER);
		p.add(new TitlePanel(TitlePanel.createBuilder(this)), BorderLayout.NORTH);
		p.add(scroll, BorderLayout.CENTER);
		return p;
	}

	protected void setRightTopScreen() {
		// Insets screenInsets =
		// Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int w = (int) d.getWidth();
		// int h = (int) d.getHeight();
		this.setLocation(w - this.getWidth(), 0);
	}

	protected void setLeftTopScreen() {
		this.setLocation(0, 0);
	}

	@SuppressWarnings("unused")
	public synchronized static void error(String desc) {
		if (true)
			return;
		//	com.sun.awt.AWTUtilities.setWindowOpacity(NotifyWindow.instance, 0.0f);
		instance.setVisible(true);
		if (time != null) {
			time.cancel();
		}
		setErr(desc, false, 10);
		time = new Timer();
		time.schedule(new TimerTask() {

			@Override
			public void run() {
				// 渐隐效果显示
				float translucent = 0.01f;
				//com.sun.awt.AWTUtilities.setWindowOpacity(NotifyWindow.instance, 0.0f);
				while (translucent < 1) {
					//com.sun.awt.AWTUtilities.setWindowOpacity(NotifyWindow.instance, translucent);
					translucent += 0.04f;
				}
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				NotifyWindow.instance.closeAndDispose();
			}
		}, 10000);
	}

	@SuppressWarnings("unused")
	public synchronized static void out(String desc) {
		if (true)
			return;
		//	com.sun.awt.AWTUtilities.setWindowOpacity(NotifyWindow.instance, 0.0f);
		instance.setVisible(true);
		if (time != null) {
			time.cancel();
		}
		setOut(desc, false, 10);
		time = new Timer();
		time.schedule(new TimerTask() {

			@Override
			public void run() {
				// 渐隐效果显示
				float translucent = 0.01f;
				//com.sun.awt.AWTUtilities.setWindowOpacity(NotifyWindow.instance, 0.0f);
				while (translucent < 1) {
					//com.sun.awt.AWTUtilities.setWindowOpacity(NotifyWindow.instance, translucent);
					translucent += 0.04f;
				}
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				NotifyWindow.instance.closeAndDispose();
				//				NotifyWindow.instance.setVisible(false);
			}
		}, 10000);
	}

	private static void insert(String str, AttributeSet attrSet) {
		Document doc = textPane.getDocument();
		str = str + "\r\n";
		try {
			if (doc.getLength() > buffer) {
				doc.insertString(doc.getLength(), str, attrSet);
				textPane.setCaretPosition(doc.getLength() - 1);
			} else {
				doc.insertString(doc.getLength(), str, attrSet);
				textPane.setCaretPosition(doc.getLength() - 1);
			}
			textPane.setEditable(true);
			textPane.setEnabled(true);
		} catch (BadLocationException e) {
			System.out.println("BadLocationException: " + e);
		}
	}

	private static void setErr(String str, boolean bold, int fontSize) {
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, Color.RED);
		//Component c=new JLabel("asd");
		//StyleConstants.setComponent(attrSet, c);
		// 颜色
		if (bold == true) {
			StyleConstants.setBold(attrSet, true);
		} // 字体类型
		StyleConstants.setFontSize(attrSet, fontSize);
		// 字体大小
		// StyleConstants.setFontFamily(attrSet, "黑体");
		// 设置字体
		insert(str, attrSet);
	}

	private static void setOut(String str, boolean bold, int fontSize) {
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, Color.BLACK);
		//Component c=new JLabel("asd");
		//StyleConstants.setComponent(attrSet, c);
		// 颜色
		if (bold == true) {
			StyleConstants.setBold(attrSet, true);
		} // 字体类型
		StyleConstants.setFontSize(attrSet, fontSize);
		// 字体大小
		// StyleConstants.setFontFamily(attrSet, "黑体");
		// 设置字体
		insert(str, attrSet);
	}
}
