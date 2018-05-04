package cn.harry12800.j2se.component;

import javax.swing.JFrame;

import cn.harry12800.j2se.action.DragListener;
import cn.harry12800.j2se.style.FadeOut;

@SuppressWarnings("serial")
public class BaseJFrame extends JFrame {
	public BaseJFrame() {
		new DragListener(this);
	}

	public void close() {
		new FadeOut(this).hide();
	}

	public void closeAndDispose() {
		new FadeOut(this).closeAndDispose();
	}
}
