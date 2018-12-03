package cn.harry12800.j2se.component;

import cn.harry12800.j2se.component.rc.RCProgressBar;
import cn.harry12800.j2se.style.ui.MyGradientProgressBarUI;
import cn.harry12800.j2se.utils.FontUtil;

public class MyRCProgressBar extends RCProgressBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyRCProgressBar() {
		setMaximum(100);
		setMinimum(0);
		setName("");
		setUI(new MyGradientProgressBarUI());
		// TODO Auto-generated constructor stub
		setFont(FontUtil.getDefaultFont(12));
	}

	public void setDesc(String string) {
		setName(string);
	}

	public void setVal(int pros) {
		setValue(pros);
	}

}
