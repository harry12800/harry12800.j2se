package cn.harry12800.j2se.module.tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

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
	private Collection<TrayInfo> trayInfoSet = new LinkedBlockingDeque<>();
	private Thread thread;

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
				normalTrayIcon = ImageUtils.getIcon("image/ic_launcher_dark.png", 20, 20).getImage();
			} else {
				normalTrayIcon = ImageUtils.getIcon("image/ic_launcher.png", 20, 20).getImage();
			}
			emptyTrayIcon = ImageUtils.getIcon("image/ic_launcher_empty.png", 20, 20).getImage();
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
				trayInfo.e.exe(trayInfo);
				trayInfoSet.remove(trayInfo);
			}
		}
	}

	public void pushTrayInfo(TrayInfo trayInfo) {
		this.trayInfo = trayInfo;
		trayInfoSet.add(trayInfo);
		setTrayFlashing();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		System.err.println(frame);
		this.frame = frame;
	}

	/**
	 * 设置任务栏图标闪动
	 */
	public void setTrayFlashing() {
		trayFlashing = true;
		if(this.thread!=null)
		{
			this.thread.interrupt();
			this.thread = null;
		}
		this.thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Iterator<TrayInfo> iterator = trayInfoSet.iterator();
				while (!trayInfoSet.isEmpty()) {
					if(iterator.hasNext()) {
						TrayInfo next = iterator.next();
						trayInfo = next;
						try {
							trayIcon.setImage(next.icon);
							trayIcon.setImageAutoSize(true);
							Thread.sleep(800);
							trayIcon.setImage(emptyTrayIcon);
							trayIcon.setImageAutoSize(true);
							Thread.sleep(800);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else {
						iterator = trayInfoSet.iterator();
					}
				}
				trayIcon.setImage(normalTrayIcon);
				trayIcon.setImageAutoSize(true);
			}
		});
		thread.start();
				
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
