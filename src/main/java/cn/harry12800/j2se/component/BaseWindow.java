package cn.harry12800.j2se.component;

import javax.swing.JWindow;

import cn.harry12800.j2se.action.DragListener;
import cn.harry12800.j2se.style.FadeOut;

@SuppressWarnings("serial")
public class BaseWindow extends JWindow{
	public BaseWindow() {
		new DragListener(this);
	}
	public void close() {
		new FadeOut(this).hide();
	}
	public void closeAndDispose() {
		new FadeOut(this).closeAndDispose();
	}
}
