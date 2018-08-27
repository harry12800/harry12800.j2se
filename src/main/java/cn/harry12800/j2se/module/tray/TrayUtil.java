package cn.harry12800.j2se.module.tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JFrame;

import cn.harry12800.j2se.component.utils.ImageUtils;
import cn.harry12800.j2se.utils.OSUtil;

public class TrayUtil {
	private Image normalTrayIcon; // 正常时的任务栏图标
	private Image emptyTrayIcon; // 闪动时的任务栏图标
	public TrayIcon trayIcon;
	private boolean trayFlashing = false;
	private JFrame frame;
	private static TrayUtil context;
	private TrayInfo trayInfo;
	private CopyOnWriteArraySet<TrayInfo> trayInfoSet = new CopyOnWriteArraySet<TrayInfo>();
	//List lists = new CopyOnWriteArrayList() ;
	private Thread thread;
	private Timer timer;

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
				normalTrayIcon = ImageUtils.getIcon("image/ic_launcher_dark.png", 16, 16).getImage();
			} else {
				normalTrayIcon = ImageUtils.getIcon("image/ic_launcher.png", 16, 16).getImage();
			}
			emptyTrayIcon = ImageUtils.getIcon("image/ic_launcher_empty.png", 16, 16).getImage();
			trayIcon = new TrayIcon(ImageUtils.getByName("image/system.png"), tip);
			// trayIcon.setImageAutoSize(true);
			systemTray.add(trayIcon);
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == 1)
						trayLeftClick();
				}
			});
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	protected void trayLeftClick() {
		// 显示主窗口
		if (trayInfoSet.isEmpty()) {
			frame.setExtendedState(JFrame.NORMAL);
			frame.toFront();
			frame.setVisible(true);
		} else {
			// 任务栏图标停止闪动
			trayInfoSet.remove(trayInfo);
			trayInfo.e.exe(trayInfo);
		}
	}

	public void pushTrayInfo(TrayInfo trayInfo) {
		this.trayInfo = trayInfo;
		trayInfoSet.add(trayInfo);
		setTimerTrayFlashing();
		//		setThreadTrayFlashing();
	}

	public void popTrayInfo(String id, ETrayType type) {
		TrayInfo trayInfo = new TrayInfo();
		trayInfo.type = type;
		trayInfo.id = id;
		trayInfoSet.remove(trayInfo);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * 设置任务栏图标闪动 线程模式
	 */
	public void setThreadTrayFlashing() {
		trayFlashing = true;
		if (this.thread != null) {
			this.thread.interrupt();
			this.thread = null;
		}
		this.thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Iterator<TrayInfo> iterator = trayInfoSet.iterator();
				while (!trayInfoSet.isEmpty() && trayFlashing) {
					if (iterator.hasNext()) {
						TrayInfo next = iterator.next();
						trayInfo = next;
						try {
							trayIcon.setImage(next.icon.getImage());
							//							trayIcon.setImageAutoSize(true);
							Thread.sleep(500);
							trayIcon.setImage(emptyTrayIcon);
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						iterator = trayInfoSet.iterator();
					}
				}
				trayIcon.setImage(normalTrayIcon);
			}
		});
		thread.start();
	}

	/**
	 * 设置任务栏图标闪动 定时器模式
	 */
	static int x = 0;

	public synchronized void setTimerTrayFlashing() {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if (trayInfoSet.isEmpty()) {
					trayIcon.setImage(normalTrayIcon);
					timer.cancel();
					return;
				}
				if ((x & 1) == 0) {
					x++;
					Iterator<TrayInfo> iterator = trayInfoSet.iterator();
					try {
						TrayInfo next = iterator.next();
						trayInfo = next;
						trayIcon.setImage(next.icon.getImage());
					} catch (Exception e) {
						trayIcon.setImage(emptyTrayIcon);
						e.printStackTrace();
					}
				} else {
					x--;
					trayIcon.setImage(emptyTrayIcon);
				}
			}
		}, 0, 500);
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
