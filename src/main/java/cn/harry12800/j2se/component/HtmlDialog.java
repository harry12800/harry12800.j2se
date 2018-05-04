package cn.harry12800.j2se.component;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class HtmlDialog extends BaseDialog {
	private JPanel display = null;
	private Dimension preferredSize = new Dimension(1000, 565);

	/**
	 * type 1是添加，type 2是修改
	 * @param type
	 */
	public HtmlDialog(String t) {
		super(t);
		setSize(1000, 600);
		setCenterScreen();
		setLocationRelativeTo(null);
		this.setVisible(true);

	}

	@Override
	protected void initObject(Object[] object) {
		setName("预览页面（只看数据是否正确。）");
	}

	@Override
	protected JComponent createCenterPanel() {
		display = new JPanel();
		JTextPane tp = new JTextPane();
		tp.setContentType("text/html; charset=utf-8");
		JScrollPane scroll = new JScrollPane(tp);
		scroll.setPreferredSize(preferredSize);
		String url = (String) object[0];

		//		System.out.println(url);
		try {
			tp.setPage(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		display.add(scroll);
		return scroll;
	}

	private void setCenterScreen() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int w = (int) d.getWidth();
		int h = (int) d.getHeight();
		this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
	}
}
