package cn.harry12800.j2se.component;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import cn.harry12800.j2se.component.btn.DIYButton;
import cn.harry12800.j2se.style.UI;

import com.sun.awt.AWTUtilities;

public class DIYDlg extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5287442184621107551L;
	private DIYButton btn = null;
	private String name = null;

	public DIYButton getBtn() {
		return btn;
	}

	public DIYDlg(String name, String idcard) {
		this.name = name;
		init();
		//getRootPane().setDefaultButton(btn); // ���ô���Ĭ�ϰ�ť
	}

	public static void main(String[] args) {
		new DIYDlg("asdfas", "asdfasasf");
	}

	public void init() {

		this.setSize(1000, 800);
		this.setCenterScreen();
		this.setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);
		setLocationRelativeTo(null);
		initButton();
		this.setVisible(true);
	}

	private void initButton() {
		btn = new DIYButton(DIYButton.ROUND_RECT, "/image/search20.gif", "搜索", 50, 37);
		btn.setSize(50, 37);
		//btn.setPreferredSize(new Dimension(200,80));
		btn.setFont(UI.normalFont(20));
		btn.setText(name);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		this.add(btn);
	}

	private void setCenterScreen() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize(); // �����Ļ�ֱ���
		int w = (int) d.getWidth();
		int h = (int) d.getHeight();
		this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		btn.setText(name);
	}
}
