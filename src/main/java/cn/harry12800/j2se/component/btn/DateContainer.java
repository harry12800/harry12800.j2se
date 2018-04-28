package cn.harry12800.j2se.component.btn;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import cn.harry12800.j2se.calendar.DatePanel;
import cn.harry12800.j2se.calendar.DatePanel.DateActionListener;
import cn.harry12800.j2se.calendar.Lunar;
import cn.harry12800.j2se.utils.Clip;

/**
 * @Author harry12800
 * @QQ 804151219 日期选择器，可以指定日期的显示格式
 */
public class DateContainer implements MouseListener, AncestorListener,
		FocusListener, DateActionListener {

	private DatePanel datePanel;
	public String value="单击选择日期";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private JComponent showDate;
	public Lunar selectLunar;
//	private Date initDate;
//	private Calendar now = Calendar.getInstance();
	public Calendar select  = Calendar.getInstance();
	public DateContainer relative(JComponent c) {
		this.showDate = c;
		datePanel = DatePanel.getInstance();
		datePanel.setPreferredSize(new Dimension(500, 350));
		showDate.addAncestorListener(this);
		showDate.addMouseListener(this);
		showDate.addFocusListener(this);
		datePanel.setDateSelectListener(this);
		return this;
	}

	
	// 隐藏日期选择面板
	private void hidePanel() {
		if (pop != null) {
			isShow = false;
			pop.hide();
			pop = null;
		}
	}

	public static void main(String[] args) {
		JButton jButton = new JButton("点击");
		jButton.setFocusPainted(false);
		new DateContainer().relative(jButton);
		Clip.seeCom(jButton);
	}

	private Popup pop;
	public boolean isShow = false;

	// 显示日期选择面板
	private void showPanel() {
		// System.out.println("显示");
		if (pop != null) {
			pop.hide();
		}
		Point show = new Point(0, showDate.getHeight());
		SwingUtilities.convertPointToScreen(show, showDate);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = show.x;
		int y = show.y;
		if (x < 0) {
			x = 0;
		}
		if (x > size.width - 295) {
			x = size.width - 295;
		}
		if (y < size.height - 170) {
		} else {
			y -= 188;
		}
		pop = PopupFactory.getSharedInstance().getPopup(showDate, datePanel, x, y);
		pop.show();
		isShow = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		showPanel();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("mouseEntered");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("mouseExited");
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		System.out.println("ancestorAdded");
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		System.out.println("ancestorRemoved");
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		hidePanel();
	}

	@Override
	public void focusGained(FocusEvent e) {
		System.out.println("focusGained");
	}

	@Override
	public void focusLost(FocusEvent e) {
		hidePanel();
	}
	@Override
	public void dateActionClick(Calendar calendar,String date) {
		this.select = calendar;
		selectLunar = new Lunar(calendar);
		value = sdf.format(select.getTime());
		value = value+"("+selectLunar+")";
		if (showDate instanceof JTextField) {
			((JTextField) showDate).setText(value);
		} else if (showDate instanceof JLabel) {
			((JLabel) showDate).setText(value);
		}else if (showDate instanceof JButton) {
			((JButton) showDate).setText(value);
		}
		hidePanel();
	}
}