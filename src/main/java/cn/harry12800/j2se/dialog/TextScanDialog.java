package cn.harry12800.j2se.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.action.DragListener;
import cn.harry12800.j2se.component.panel.AreaTextPanel;
import cn.harry12800.j2se.component.panel.OperatePanel;
import cn.harry12800.j2se.tab.Tab;
import cn.harry12800.j2se.tab.TabRootPane;
import cn.harry12800.tools.Lists;

@SuppressWarnings("serial")
public class TextScanDialog extends JDialog {
	public static TextScanDialog instance;
	public TextScanDialog(String text) {
		super();
		instance = this;
		setType(JFrame.Type.UTILITY);
		setSize(500,600);
		setModal(true);
		TabRootPane tabRootPane = new TabRootPane();
		tabRootPane.add(new OperatePanel(this),BorderLayout.SOUTH);
		List<Tab> tabs = Lists.newArrayList();
		Tab tab = new Tab("文本查看");
		AreaTextPanel areaTextPanel = new AreaTextPanel();
		areaTextPanel.setText(text);
		tab.setContentPane(areaTextPanel);
		areaTextPanel.requestFocus();
		tabs.add(tab);
		Collections.reverse(tabs);
		tabRootPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println(e);
				if(e.getKeyCode()==27) {
					TextScanDialog.this.dispose();
				}
			}
		});
		tabRootPane.addTab(tabs.toArray(new Tab[]{}));
		JPanel jpeg  =new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.drawRect(0, 0, getWidth()-1, getHeight()-1);
			}
		};
		jpeg.setBorder(new EmptyBorder(1, 1, 1, 1));
		jpeg.setLayout(new BorderLayout());
		jpeg.add(tabRootPane,BorderLayout.CENTER);
		setContentPane(jpeg);
		setAlwaysOnTop(true);
		setUndecorated(true);
		new DragListener(this);
//		MainFrame.instance.addWindow(this);
	}
	public void setCenterScreen() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int w = (int) d.getWidth();
		int h = (int) d.getHeight();
		this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
	}
}
