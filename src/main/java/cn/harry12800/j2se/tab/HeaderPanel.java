package cn.harry12800.j2se.tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import cn.harry12800.j2se.style.UI;

@SuppressWarnings("serial")
public class HeaderPanel extends JPanel {

	public HeaderPanel(List<TabHeader> headList) {
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);
		//		setBackground(new Color(0,0,0,0));
		setLayout(new FlowLayout(FlowLayout.LEFT, -1, 0));
		for (TabHeader tabHeader : headList) {
			add(tabHeader);
		}
	}

	protected void paintComponent1(Graphics g) {
		g.setColor(UI.backColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2 = new GradientPaint(0, 0, new Color(255, 255, 255, 255), getWidth(), 0, new Color(255, 255, 255, 0));
		g2d.setPaint(p2);
		g2d.drawLine(2, 24, getWidth() - 2, 24);
	}
}
