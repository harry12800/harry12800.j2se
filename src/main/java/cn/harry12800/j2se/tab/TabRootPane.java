package cn.harry12800.j2se.tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cn.harry12800.j2se.style.UI;
import cn.harry12800.tools.Lists;

public class TabRootPane extends JPanel {

	private static final long serialVersionUID = 1L;
	public List<Tab> tabList = Lists.newArrayList();
	public List<TabHeader> headList = Lists.newArrayList();
	public List<Container> panelList = Lists.newArrayList();
	private ContentPanel contentPanel;
	private int currIndex;

	public TabRootPane() {
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
	}

	public void addTab(Tab... tabs) {
		int i = 0;
		for (Tab tab : tabs) {
			tab.setParent(this);
			tab.setIndex(i);
			i++;
			tabList.add(tab);
			headList.add(tab.getTabHeader());
			panelList.add(tab.getContentPane());
		}
		HeaderPanel headerPanel = new HeaderPanel(headList);
		add(headerPanel, BorderLayout.NORTH);
		this.contentPanel = new ContentPanel(panelList);
		add(contentPanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		JFrame jFrame = new JFrame();
		jFrame.setSize(200, 100);
		jFrame.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
			}

			public void focusGained(FocusEvent e) {
			}
		});
		jFrame.setUndecorated(true);
		TabRootPane tabRootPane = new TabRootPane();
		Tab tabs1 = new Tab("选项一");
		JPanel jPanel1 = new JPanel();
		jPanel1.add(new JButton("一"));
		tabs1.setContentPane(jPanel1);
		Tab tabs2 = new Tab("选项二");
		JPanel jPanel2 = new JPanel();
		jPanel2.add(new JButton("二"));
		tabs2.setContentPane(jPanel2);

		Tab tabs3 = new Tab("选项三");
		JPanel jPanel3 = new JPanel();
		jPanel3.add(new JButton("san"));
		tabs3.setContentPane(jPanel3);

		tabRootPane.addTab(tabs1, tabs2, tabs3);
		jFrame.setContentPane(tabRootPane);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		jFrame.requestFocus();

	}

	protected void paintComponent1(Graphics g) {
		g.setColor(UI.backColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}

	public void showTab(Tab tab) {
		this.currIndex = tabList.indexOf(tab);
		contentPanel.showIndex(currIndex);
		//		headerPanel.repaint();
		for (TabHeader tabHeader : headList) {
			tabHeader.repaint();
		}
	}

	/**
	 * 获取currIndex
	 *	@return the currIndex
	 */
	public int getCurrIndex() {
		return currIndex;
	}

	/**
	 * 设置currIndex
	 * @param currIndex the currIndex to set
	 */
	public void setCurrIndex(int currIndex) {
		this.currIndex = currIndex;
	}

}
