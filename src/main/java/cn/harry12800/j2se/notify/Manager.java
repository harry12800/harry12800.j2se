package cn.harry12800.j2se.notify;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.j2se.component.utils.GBC;

public class Manager extends RemindWindow {
	/**
	 * 
	 */
	static JPanel containter = new JPanel();

	public JPanel getContainter() {
		return containter;
	}

	private static final long serialVersionUID = 1L;
	private static Manager manager = null;
	public static Map<String, Notify> notifyMap = new LinkedHashMap<String, Notify>(0);

	private Manager() {
		super("消息队列");
		for (int i = 0; i < 10; i++) {
			Manager.addNotify("" + i, new TestNotify(i));
		}
		revalidate();
		setVisible(true);
	}

	public static Manager getInstance() {
		if (manager == null) {
			manager = new Manager();
		}
		return manager;
	}

	public static void main(String[] args) {
		new Manager().setVisible(true);
	}

	@Override
	protected JComponent createCenterPanel() {
		containter.removeAll();
		containter.setLayout(new FlowLayout(FlowLayout.LEFT));
		//containter.setSize(300, notifyMap.size()*40+40);
		setSize(300, notifyMap.size() * 50 + 40);
		setRightBottomScreen();
		containter.setBackground(Color.WHITE);
		System.out.println(notifyMap.size() + "a");
		for (Entry<String, Notify> entry : notifyMap.entrySet()) {
			Notify notify = entry.getValue();
			final JPanel item = new JPanel();
			final String id = entry.getKey();
			item.setLayout(new GridBagLayout());
			Dimension preferredSize = new Dimension(300, 50);
			item.setPreferredSize(preferredSize);
			JLabel title = new JLabel(notify.getTitle());
			JLabel content = new JLabel(notify.getContent());
			JLabel icon = new JLabel(notify.getIcon());
			JLabel times = new JLabel(notify.getTimes() + "");
			item.add(icon, new GBC(0, 0, GBC.WEST, new Insets(5, 5, 5, 5), 1, 2));
			item.add(title, new GBC(1, 0, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
			item.add(content, new GBC(1, 1, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
			item.add(times, new GBC(2, 0, GBC.WEST, new Insets(5, 5, 5, 5), 1, 2));
			item.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseExited(MouseEvent e) {
					item.setBackground(Color.WHITE);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					item.setBackground(Color.BLUE);
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					notifyMap.remove(id);
					Manager.getInstance().getContainter().remove(item);
					Manager.getInstance().revalidate();
				}
			});
			containter.add(item);
			containter.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		return containter;
	}

	public static void addNotify(String id, Notify notify) {
		notifyMap.put(id, notify);
	}

	public static void removeNotify(String id) {
		notifyMap.remove(id);
	}

	public static Map<String, Notify> getNotifyMap() {
		return notifyMap;
	}

	public static void setNotifyMap(Map<String, Notify> notifyMap) {
		Manager.notifyMap = notifyMap;
	}
}
