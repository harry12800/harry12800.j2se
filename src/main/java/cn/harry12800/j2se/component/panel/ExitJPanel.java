package cn.harry12800.j2se.component.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.action.MyActionListener;

public class ExitJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Window w;
	private TitleButton exitBtn = new TitleButton("exit","关闭");
	public ExitJPanel(Window mainFrame) {
		this.w = mainFrame;
		setLayout(new FlowLayout(FlowLayout.RIGHT,0,-1));
		setBorder(new EmptyBorder(1,1,1,1));
		//setOpaque(false);
		setBackground(new Color(237,184,132));
		add(exitBtn,BorderLayout.EAST);
		exitBtn.addActionListener(new MyActionListener() {
			
			@Override
			public void exe(MouseEvent e) {
				ExitJPanel.this.w.setVisible(false);
			}
		});
	}
}
