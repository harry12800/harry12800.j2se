package cn.harry12800.j2se.utils;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import cn.harry12800.j2se.component.utils.ImageUtils;

public class TrayUtil {
	private Image normalTrayIcon; // 正常时的任务栏图标
	private Image emptyTrayIcon; // 闪动时的任务栏图标
	public TrayIcon trayIcon;
	private boolean trayFlashing = false;
	private JFrame frame;
	private static TrayUtil context;
	private TrayInfo trayInfo;

	private TrayUtil() {
		context = this;
		initTray();
	}

	/**
	 * 初始化系统托盘图标
	 */
	private void initTray() {
		String tip = "账号：" + "未知用户" + "\r\nAuthor：harry12800\r\nQQ:804151219\r\n开发者常用功能";
		SystemTray systemTray = SystemTray.getSystemTray();// 获取系统托盘
		try {
			if (OSUtil.getOsType() == OSUtil.Mac_OS) {
				normalTrayIcon = IconUtil.getIcon(TrayUtil.class, "/image/ic_launcher_dark.png", 20, 20).getImage();
			} else {
				normalTrayIcon = IconUtil.getIcon(TrayUtil.class, "/image/ic_launcher.png", 20, 20).getImage();
			}
			emptyTrayIcon = IconUtil.getIcon(TrayUtil.class, "/image/ic_launcher_empty.png", 20, 20).getImage();
			trayIcon = new TrayIcon(ImageUtils.getByName("image/system.png"), tip);
			// trayIcon.setImageAutoSize(true);

			systemTray.add(trayIcon);
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					//					super.mouseClicked(e);
					trayLeftClick();
				}
			});
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	protected void trayLeftClick() {
		// 显示主窗口
		if (trayInfo == null) {
			frame.setVisible(true);
		} else {
			// 任务栏图标停止闪动
			if (trayFlashing) {
				trayFlashing = false;
				trayIcon.setImage(normalTrayIcon);
				trayInfo.e.exe(trayInfo);
			}
		}
	}

	public static interface TrayListener {
		public void exe(TrayInfo e);
	}

	public static class TrayInfo {
		public String id;
		public int Type;
		public TrayListener e;
		public Image icon;
	}

	public void pushTrayInfo(TrayInfo trayInfo) {
		this.trayInfo = trayInfo;
		setTrayFlashing();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * 设置任务栏图标闪动
	 */
	public void setTrayFlashing() {
		trayFlashing = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (trayFlashing) {
					try {
						trayIcon.setImage(emptyTrayIcon);
						Thread.sleep(800);

						trayIcon.setImage(trayInfo.icon);
						Thread.sleep(800);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	public boolean isTrayFlashing() {
		return trayFlashing;
	}

	public static TrayUtil getTray() {
		if (context == null)
			context = new TrayUtil();
		return context;
	}

	public void setTip(String tip) {
		trayIcon.setToolTip(tip);
	}

	public void setMenu(PopupMenu menu) {
		trayIcon.setPopupMenu(menu);
	}

	public void addMenuItem(MenuItem mit1) {
		trayIcon.getPopupMenu().insert(mit1, 1);
	}
}
