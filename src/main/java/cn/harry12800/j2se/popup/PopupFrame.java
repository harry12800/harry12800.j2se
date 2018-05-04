package cn.harry12800.j2se.popup;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import cn.harry12800.tools.Lists;

public class PopupFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int h;
	public boolean hasChildMark = false;
	public int defaultWidth = 188;
	public ListItem changeComponent = null;
	private List<Component> list = Lists.newArrayList();
	public PopupFrame parentFrame;

	public PopupFrame() {
		setUndecorated(true);
		setType(Frame.Type.UTILITY);
	}

	public PopupFrame(int width) {
		defaultWidth = width;
		setUndecorated(true);
		setType(Frame.Type.UTILITY);
	}

	public PopupFrame(Point point, List<Component> list) {
		this.list = list;
		setUndecorated(true);
		setType(Frame.Type.UTILITY);
	}

	public void show(Point point, Component sd) {
		toFront();
		setAlwaysOnTop(true);
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!hasChildMark)
					dispose();
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});

		h = 0;
		for (Component component : list) {
			h += component.getPreferredSize().height;
		}
		h -= list.size();
		ListPanel listPanel = new ListPanel(list);
		setContentPane(listPanel);
		setSize(defaultWidth, h);
		SwingUtilities.convertPointToScreen(point, sd);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = point.x;
		int y = point.y;
		if (x < 0) {
			x = 0;
		}
		if (x > size.width - defaultWidth) {
			x = point.x - defaultWidth;
		}
		if (y > size.height - h) {
			y -= h;
		}
		setLocation(x, y);
		setVisible(true);
		requestFocus();
	}

	public void show(Point point) {
		toFront();
		setAlwaysOnTop(true);
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!hasChildMark)
					dispose();
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
		setType(Frame.Type.UTILITY);
		h = 0;
		for (Component component : list) {
			h += component.getPreferredSize().height;
		}
		h -= list.size();
		ListPanel listPanel = new ListPanel(list);
		setContentPane(listPanel);
		setSize(defaultWidth, h);
		//		SwingUtilities.convertPointToScreen(point,this);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = point.x;
		int y = point.y;
		if (x < 0) {
			x = 0;
		}
		if (x > size.width - defaultWidth) {
			x = point.x - defaultWidth;
		}
		if (y > size.height - h) {
			y -= h;
		}
		setLocation(x, y);
		setVisible(true);
		requestFocus();
	}

	public void addSeparator() {
		list.add(new ListSplitItem(defaultWidth, 2));
	}

	public void addItem(ListItem editItem) {
		list.add(editItem);
		editItem.setRootFrame(this);
	}

	public void disposeChild() {
		for (Component c : list) {
			if (c instanceof ListItem) {
				((ListItem) c).disposeChild();
			}
		}
		this.hasChildMark = false;
		this.requestFocus();
	}

	public void disposeParent() {
		if (parentFrame != null) {
			parentFrame.disposeParent();
			parentFrame.dispose();
		}
	}

	@Override
	public void dispose() {
		if (parentFrame != null) {
			parentFrame.requestFocus();
		}
		super.dispose();
	}
}
