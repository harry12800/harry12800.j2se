package cn.harry12800.j2se.component.panel;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.component.TitleNameLabel;
import cn.harry12800.j2se.component.btn.LogoLabel;
import cn.harry12800.j2se.component.panel.TitlePanel.Builder;

public class TitleNamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private Builder builder;
	private LogoLabel logoBtn ;
	private JLabel logolab ;
	public TitleNamePanel(Builder builder) {
//		this.builder = builder;
		setLayout(new FlowLayout(FlowLayout.RIGHT,10,-1));
		setBorder(new EmptyBorder(1,1,1,1));
		setOpaque(false);
		if(builder.hasLogo) {
			logoBtn= new LogoLabel("logo");
			add(logoBtn);
		}
		if(builder.hasTitle) {
			logolab = new TitleNameLabel(builder.window.getName());			
			add(logolab);
		}
	}
}
