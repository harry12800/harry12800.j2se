package cn.harry12800.j2se.notify;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JComponent;

import cn.harry12800.j2se.component.BaseWindow;
import cn.harry12800.j2se.component.panel.TitlePanel;


public abstract class RemindWindow extends BaseWindow {

	private static final long serialVersionUID = 5575710694424302773L;
  
	protected Object[] object = null;
	public RemindWindow(String title,Object... object) {
		this.setName(title);
		this.object = object;
		this.setLayout(new BorderLayout());// GridBagLayout();
		initPanel();
		setRightBottomScreen();
		// this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);//
		// 采用指定的窗口装饰风格
	}
	
	protected abstract JComponent createCenterPanel();
	private void initPanel() {
		add(new TitlePanel(TitlePanel.createBuilder(this)),BorderLayout.NORTH);
		add(createCenterPanel(), BorderLayout.CENTER);
	}

	/**
	 * 使窗体出现在右下角
	 */
	protected void setRightBottomScreen() {
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int w = (int) d.getWidth();
		int h = (int) d.getHeight();
		this.setLocation(w - this.getWidth(), h - this.getHeight() - screenInsets.bottom);
	}
}
