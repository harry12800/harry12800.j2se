package cn.harry12800.j2se.component.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.j2se.style.UI;
import cn.harry12800.tools.MachineUtils;

@SuppressWarnings("serial")
public  class SelectButtonDialog extends JDialog {

	int w,h;
	public SelectButtonDialog(List<String> list) {
		setUndecorated(true);
		setModal(true);
		getLayeredPane().setOpaque(false);
		getRootPane().setOpaque(false);
		w = 300;
		h = 40 * list.size();
		setSize(w, h);
		setLocationRelativeTo(null);
//		getLayeredPane().setBackground(new Color(237,184,132,123));
	
		// GridBagLayout gridBagLayout = new GridBagLayout();
		// this.setLayout( gridBagLayout);
		setBackground(new Color(237,184,132));
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		int i =0;
		contentPanel.setLayout(null);
		OButton o = new OButton(40, 40);
		o.setBounds(300-40,0, 40, 40);
		o.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		contentPanel.add(o );
		for (final String option : list) {
			LabelButton jPanel = new LabelButton(option, 300, 40);
			jPanel.setBounds(0,i*40, 300, 40);
			contentPanel.add(jPanel);
			jPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					dispose();
					exe( option);
				}
			});
			i++;
		}
		setContentPane(contentPanel );
		getContentPane().setBackground(new Color(237,184,132,255));
		setVisible(true);

	}
	public class OButton extends JLabel {
		boolean hover =false;
		private int w;
		private int h;
		public OButton( int w,int h) {
			this.w =w;
			this.h=h;
			setPreferredSize(new Dimension(this.w,h));
			setFont(UI.normalFont(10));
			Color color = new Color(231,224,224);
			setForeground(color);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Color color = new Color(255,255,255);
					setForeground(color);
					hover = true;
					revalidate();
				}
				@Override
				public void mouseExited(MouseEvent e) {
					Color color = new Color(186,186,186);
					setForeground(color);
					hover = false;
					revalidate();
				}
				public void mouseClicked(MouseEvent e) {
				}
			});
		}
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			GradientPaint p2;
		
			if(hover){
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				p2 = new GradientPaint(0, 1, new Color(186, 131, 164, 200), 0, h - 10,
						new Color(255, 255, 255, 255));
				g2d.setPaint(p2);
				g2d.setColor(new Color(12,154,53, 150));
//				g2d.fillRect(0, 0, 40, 40);
				g2d.setFont(new Font("宋体",Font.BOLD,20));
				g2d.fillOval(-1 ,-39, 80, 80);
				g2d.drawString("X", 20, 25);
			}
			else{
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				p2 = new GradientPaint(0, 1, new Color(150, 150, 150, 50), 0, h - 10,
						new Color(160,160, 160, 100));
				g2d.setPaint(p2);
				g2d.setColor(new Color(12,154,53, 70));
				g2d.fillOval(-1 ,-39, 80, 80);
				g2d.setFont(new Font("宋体",Font.BOLD,20));
				g2d.drawString("X", 20, 25);
			}
			GradientPaint p1 = new GradientPaint(0, 1, new Color(255, 255, 255, 255), 0, h - 10,
					new Color(255,255, 255, 255));
			g2d.setPaint(p1);
			g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); //设置新的画刷
			Font font = new Font("宋体", Font.BOLD, 12);
			g2d.setFont(font);
			g2d.setColor(Color.RED);
			g2d.dispose();
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}
	public static void main(String[] args) {
		List<String> list =  MachineUtils.getPrintName();
		new SelectButtonDialog(list);
	}

	/**
	 * 
	 * @param name
	 */
	public  void exe(String name){
//		System.out.println(name);
	};
}
