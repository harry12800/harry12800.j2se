package cn.harry12800.j2se.component;

import cn.harry12800.j2se.style.ui.MyGradientProgressBarUI;

public class MyRCProgressBar extends RCProgressBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyRCProgressBar() {
		setMaximum(100);
		setMinimum(0);
		setName("等待更新中");
		setUI(new MyGradientProgressBarUI());
		// TODO Auto-generated constructor stub
	}

	public void setDesc(String string) {
		setName(string);
	}

	public void setVal(int pros) {
		setValue(pros);
	}

}
