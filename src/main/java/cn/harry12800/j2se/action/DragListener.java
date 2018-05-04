package cn.harry12800.j2se.action;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLayeredPane;
import javax.swing.JWindow;

public class DragListener extends MouseAdapter {
	private int xx;
	private int yy;
	private boolean isDraging;
	private Container com;
	private Cursor leftUp;
	private Cursor leftDown;
	private Cursor left;
	private Cursor rightUp;
	private Cursor rightDown;
	private Cursor right;
	private Cursor up;
	private Cursor down;
	private Cursor normal;
	private Container target;
	private boolean isFrame = false;

	public DragListener(Container component) {
		initCursor();
		this.com = component;
		if (component instanceof JWindow) {
			isFrame = true;
			this.target = ((JWindow) component).getContentPane();
		}
		if (target == null) {
			target = component;
		}
		target.addMouseListener(this);
		target.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int type = com.getCursor().getType();
				if (isDraging
						&& type == Cursor.DEFAULT_CURSOR) {
					int left = com.getLocation().x;
					int top = com.getLocation().y;
					com.setLocation(left + e.getX() - xx, top + e.getY() - yy);
				} else if (isDraging && isFrame) {
					int left = com.getLocation().x;
					int top = com.getLocation().y;
					int width = com.getWidth();
					int height = com.getHeight();
					switch (type) {
					case Cursor.NW_RESIZE_CURSOR:
						com.setLocation(left + e.getX() - xx, top + e.getY() - yy);
						com.setSize(width - e.getX() + xx, height - e.getY() + yy);
						break;
					case Cursor.SW_RESIZE_CURSOR:
						com.setLocation(left + e.getX() - xx, top);
						com.setSize(width - e.getX() + xx, e.getY());
						break;
					case Cursor.W_RESIZE_CURSOR:
						com.setLocation(left + e.getX() - xx, top);
						com.setSize(width - e.getX() + xx, height);
						break;
					case Cursor.NE_RESIZE_CURSOR:
						com.setLocation(left, top + e.getY() - yy);
						com.setSize(e.getX(), height - e.getY() + yy);
						break;
					case Cursor.SE_RESIZE_CURSOR:
						com.setSize(e.getX(), e.getY());
						break;
					case Cursor.E_RESIZE_CURSOR:
						com.setSize(e.getX(), height);
						break;
					case Cursor.N_RESIZE_CURSOR:
						com.setLocation(left, top + e.getY() - yy);
						com.setSize(width, height - e.getY() + yy);
						break;
					case Cursor.S_RESIZE_CURSOR:
						com.setSize(width, e.getY());
						break;
					default:
						break;
					}
					;
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				int width = com.getWidth();
				int height = com.getHeight();
				int xx = e.getX();
				int yy = e.getY();
				if (!isFrame)
					return;
				if (xx < 5 && yy < 5) { // 左上角
					com.setCursor(leftUp);
				} else if (xx < 5 && height - yy < 5) { // 左下角
					com.setCursor(leftDown);
				} else if (xx < 5) { // 左边
					com.setCursor(left);
				} else if (width - xx < 5 && yy < 5) { // 右上角
					com.setCursor(rightUp);
				} else if (width - xx < 5 && height - yy < 5) { // 右下角
					com.setCursor(rightDown);
				} else if (width - xx < 5) { // 右边
					com.setCursor(right);
				} else if (yy < 5) { // 上边
					com.setCursor(up);
				} else if (height - yy < 5) { // 下边
					com.setCursor(down);
				} else { // 里面
					com.setCursor(normal);
				}
			}
		});
	}

	private void initCursor() {
		leftUp = new Cursor(Cursor.NW_RESIZE_CURSOR);
		leftDown = new Cursor(Cursor.SW_RESIZE_CURSOR);
		left = new Cursor(Cursor.W_RESIZE_CURSOR);
		rightUp = new Cursor(Cursor.NE_RESIZE_CURSOR);
		rightDown = new Cursor(Cursor.SE_RESIZE_CURSOR);
		right = new Cursor(Cursor.E_RESIZE_CURSOR);
		up = new Cursor(Cursor.N_RESIZE_CURSOR);
		down = new Cursor(Cursor.S_RESIZE_CURSOR);
		normal = new Cursor(Cursor.DEFAULT_CURSOR);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		System.out.println(11);
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			isDraging = true;
			Container parent = com.getParent();
			if (parent instanceof JLayeredPane) {
				((JLayeredPane) parent).moveToFront(com);
				parent.repaint();
			}
			xx = e.getX();
			yy = e.getY();
			// int x = com.getX();
			// int y = com.getY();
			// Point locationOnScreen = com.getLocationOnScreen();
			// System.out.println(xx+"__"+x);
			// System.out.println(yy+"__"+y);
			// System.out.println(locationOnScreen);
		}
	}

	public void mouseReleased(MouseEvent e) {
		isDraging = false;
	}
}
