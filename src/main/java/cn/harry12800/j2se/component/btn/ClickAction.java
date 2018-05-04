package cn.harry12800.j2se.component.btn;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class ClickAction extends MouseAdapter {

	public ClickAction(Component component) {
		this.component = component;
	}

	private Component component;

	public final void mouseReleased(MouseEvent e) {
		if (component.contains(e.getPoint())) {
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
				leftClick(e);
			} else if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
				doubleClick(e);
			}
			if (e.getButton() == MouseEvent.BUTTON3) {
				rightClick(e);
			}
		}
	}

	public abstract void leftClick(MouseEvent e);

	public void rightClick(MouseEvent e) {
	};

	public void doubleClick(MouseEvent e) {
	};
}
