package cn.harry12800.j2se.component.utils.sample;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Calendar;

import javax.swing.JFrame;

import cn.harry12800.j2se.calendar.DatePanel.DateActionListener;
import cn.harry12800.j2se.component.Progressar;
import cn.harry12800.j2se.component.btn.DateChooser;
import cn.harry12800.j2se.component.btn.TurnButton;

/**
 * 得设置布局。 简单的可重用时间swing控件
 * @author harry12800
 *
 */
public class DateChooserSample {
	/**
	 * 这基本样式都可以自己在DateChooser的paintComponent方法中修改。
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame("时间控件测试");
		f.setBackground(Color.BLACK);
		f.setSize(300, 200);
		f.setLayout(new FlowLayout());
		TurnButton turnButton = new TurnButton(150, 40);
		f.add(turnButton);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	/**
	 * 这基本样式都可以自己在DateChooser的paintComponent方法中修改。
	 * @param args
	 */
	public static void main2(String[] args) {
		JFrame f = new JFrame("jidut控件测试");
		f.setBackground(Color.BLACK);
		f.setSize(300, 200);
		f.setLayout(new FlowLayout());
		final Progressar progressar = new Progressar("", Progressar.Type.ballPercent);

		f.add(progressar);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <= 100; i++) {

					progressar.setVal(i);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * 这基本样式都可以自己在DateChooser的paintComponent方法中修改。
	 * @param args
	 */
	public static void main1(String[] args) {
		JFrame f = new JFrame("时间控件测试");
		f.setBackground(Color.BLACK);
		f.setSize(300, 200);
		f.setLayout(new FlowLayout());
		DateChooser dateChooser = DateChooser.getInstance();
		dateChooser.addDateActionListener(new DateActionListener() {
			public void dateActionClick(Calendar calendar, String date) {
				//				System.out.println("你要做的事："+date);
			}
		});
		f.add(dateChooser);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
