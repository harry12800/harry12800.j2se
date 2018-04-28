package cn.harry12800.j2se.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDialog;

import cn.harry12800.j2se.action.DragListener;
import cn.harry12800.j2se.component.panel.ContentPanel;
import cn.harry12800.j2se.style.FadeOut;

@SuppressWarnings("serial")
public abstract class BaseDialog extends JDialog {
	protected Object object[];
	public BaseDialog(Window win,Object...object)  {
		super(win);
		this.object = object;
		initObject(object);
		setUndecorated(true);
		setLayout(new BorderLayout(0,0));
		setModal(true);
		new DragListener(this);
		getLayeredPane().setOpaque(false);
		getRootPane().setOpaque(false);
		getLayeredPane().setBackground (new Color (0, 0, 0, 0));
		setContentPane(new ContentPanel(this){
			@Override
			protected Component createCenter() {
				return createCenterPanel();
			}
			
		});
	}
	public BaseDialog(Object...object)  {
		this.object = object;
		initObject(object);
		setUndecorated(true);
		setLayout(new BorderLayout(0,0));
		setModal(true);
		new DragListener(this);
		getLayeredPane().setOpaque(false);
		getRootPane().setOpaque(false);
		getLayeredPane().setBackground (new Color (0, 0, 0, 0));
		setContentPane(new ContentPanel(this){
			@Override
			protected Component createCenter() {
				return createCenterPanel();
			}
			
		});
	}

	/**
	 * 将传到父类的对象还原到子类中初始化使用
	 * @param object2
	 */
	protected void initObject(Object[] object2) {
	}

	protected abstract JComponent createCenterPanel();
	public void leftClick() {
		
	}
	public void rightClick() {
		
	}
	public void close() {
		new FadeOut(this).hide();
	}
	public void closeAndDispose() {
		new FadeOut(this).closeAndDispose();
	}
}
