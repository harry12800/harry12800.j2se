package cn.harry12800.j2se.tip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.component.btn.ImageBtn;
import cn.harry12800.j2se.component.utils.ImageUtils;
import cn.harry12800.j2se.style.J2seColor;
import cn.harry12800.j2se.style.UI;

/**
 * @Author harry12800
 * @QQ 804151219
 * 
 */
@SuppressWarnings("serial")
public class TextPanel extends JPanel {

	private static final long serialVersionUID = 4529266044762990227L;
	private Timer timer = null;
	float s = 30;
	int time;
	private JPanel initHead;
	@SuppressWarnings("unused")
	private JPanel initBody;
	@SuppressWarnings("unused")
	private JPanel initFoot;
	private TipFrame context;

	public TextPanel(TipFrame tipFrame) {
		this.context = tipFrame;
		this.setLayout(new BorderLayout());
		this.initHead = initHead(tipFrame.builder.headTitle);
		this.initBody = initBody();
		this.initFoot = initFoot();
		setOpaque(false);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				s -= 0.1;
				if (s - 0.0f < 0.1) {
					timer.cancel();
					context.closeAndDispose();
				}
				initHead.repaint();
			}
		}, 1000, 100);
	}

	private JPanel initFoot() {
		JPanel foot = new JPanel();
		foot.setBackground(UI.foreColor);
		foot.setLayout(new FlowLayout(FlowLayout.RIGHT));
		ActionButton labelButton = new ActionButton(context.getBuilder().actionName, 100, 20);
		labelButton.setForeground(Color.WHITE);
		foot.add(labelButton);
		add(foot, BorderLayout.SOUTH);
		labelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseevent) {
				context.setVisible(false);
				if (context.getHandler() != null)
					context.getHandler().execute();
			}
		});
		return foot;
	}

	private JPanel initBody() {
		JPanel body = new JPanel();
		body.setBackground(UI.foreColor);
		BoxLayout box = new BoxLayout(body, BoxLayout.Y_AXIS);
		body.setLayout(box);
		body.setBorder(new EmptyBorder(0, 15, 0, 15));
		for (String string : context.getBuilder().dataList) {
			JLabel jLabel = new JLabel(string);
			jLabel.setForeground(UI.fontColor);
			jLabel.setFont(UI.微软雅黑Font);
			body.add(jLabel);
		}
		add(body, BorderLayout.CENTER);
		return body;
	}

	private JPanel initHead(String title) {
		JPanel head = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				int w = getWidth();
				int h = getHeight();
				Graphics2D g2d = (Graphics2D) g.create();
				GradientPaint p2;
				Color backgroundColor = UI.foreColor;
				Color color = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 50);
				p2 = new GradientPaint(0, 1, backgroundColor, 0, h - 10,
						backgroundColor);
				g2d.setPaint(p2);
				g2d.fillRect(0, 0, w, h);

				Color backColor = UI.backColor(100);
				p2 = new GradientPaint(0, 1, backColor, 0, h - 10,
						color);
				g2d.setPaint(p2);
				int lv = (int) ((s * 1.0 / 30) * w);
				g2d.fillRect(0, 0, lv, h);
				g2d.dispose();
				super.paintComponent(g);
			}

		};
		head.setPreferredSize(new Dimension(1000, 30));

		head.setOpaque(false);
		add(head, BorderLayout.NORTH);
		JLabel jLabel = new JLabel(title == null ? "  " : "  " + title);
		ImageBtn closeButton = new ImageBtn(ImageUtils.getByName("close24.png"));
		closeButton.setPreferredSize(new Dimension(24, 24));
		closeButton.setSize(new Dimension(24, 24));
		jLabel.setFont(UI.微软雅黑Font);
		jLabel.setForeground(UI.fontColor);
		head.setLayout(new BorderLayout());
		jLabel.setOpaque(false);
		head.add(jLabel, BorderLayout.WEST);
		head.add(closeButton, BorderLayout.EAST);
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				context.dispose();
			}
		});
		return head;
	}

	@Override
	protected void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2;
		p2 = new GradientPaint(0, 1, J2seColor.getBackgroundColor(), 0, h - 10,
				J2seColor.getBackgroundColor());
		g2d.setPaint(p2);
		g2d.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
		//		g2d.drawLine(1, 1, w-1,1);
		//		g2d.drawLine(1, 2, w-1, 2);
		g2d.dispose();
	}

}