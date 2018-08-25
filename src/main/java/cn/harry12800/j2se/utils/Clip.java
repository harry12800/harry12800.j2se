package cn.harry12800.j2se.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import cn.harry12800.tools.NetworkUtils;

public class Clip {
	static {
		if (shouldLoad32Bit()) {
			// System.load(System.getProperty("user.dir")+File.separator+"aa32.dll");
		} else {
			// System.loadLibrary(System.getProperty("user.dir")+File.separator+"aa64.dll");
		}
	}

	public static void writeClipboard(String content) {
		StringSelection stringSelection = new StringSelection(content);
		// 系统剪贴板
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	public static String readClipboard(String content) {
		// 系统剪贴板
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		DataFlavor flavor = DataFlavor.stringFlavor;
		Transferable contents = clipboard.getContents(Clip.class);
		if (contents.isDataFlavorSupported(flavor)) {
			try {
				return (String) contents.getTransferData(flavor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 从剪切板获得图片。
	 */
	public static Image getImageFromClipboard() throws Exception {
		Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable cc = sysc.getContents(null);
		if (cc == null)
			return null;
		else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
			return (Image) cc.getTransferData(DataFlavor.imageFlavor);
		return null;
	}

	/**
	 * 获取小图标
	 * 
	 * @param f
	 * @return
	 */
	public static Icon getSmallIcon(File f) {
		if (f != null && f.exists()) {
			FileSystemView fsv = FileSystemView.getFileSystemView();
			return (fsv.getSystemIcon(f));
		}
		return (null);
	}

	/**
	 * 获取大图标
	 * @param f
	 * @return
	 */
	public static ImageIcon getBigIcon(File f) {
		if (f != null && f.exists()) {
			try {
				sun.awt.shell.ShellFolder sf = sun.awt.shell.ShellFolder
						.getShellFolder(f);
				ImageIcon imageIcon = new ImageIcon(sf.getIcon(true));
				//				System.out.println(imageIcon);
				return imageIcon;
			} catch (FileNotFoundException e) {
				/* TODO Auto-generated catch block */
				e.printStackTrace();
			}
		}
		return (null);
	}

	/**
	 * 复制图片到剪切板。
	 */
	public static void setClipboardImage(final Image image) {
		Transferable trans = new Transferable() {
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.imageFlavor };
			}

			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return DataFlavor.imageFlavor.equals(flavor);
			}

			public Object getTransferData(DataFlavor flavor)
					throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor))
					return image;
				throw new UnsupportedFlavorException(flavor);
			}

		};
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(trans, null);
	}

	/**
	 * @param path
	 * @throws IOException
	 */
	public static void openFile(String path) throws Exception {
		java.awt.Desktop.getDesktop().open(new File(path));
	}

	public static native boolean modifyPrintByName(String name);

	public static boolean shouldLoad32Bit() {
		String bits = System.getProperty("sun.arch.data.model", "?");
		// System.err.println(bits);
		if (bits.equals("32"))
			return true;
		if (bits.equals("64"))
			return false;
		String arch = System.getProperty("java.vm.name", "?");
		// System.out.println(arch);
		return arch.toLowerCase().indexOf("64-bit") < 0;
	}

	public static void seeCom(Component s) {
		JFrame jFrame = new JFrame();
		//		jFrame.setUndecorated(true);
		//		jFrame.setBackground(new Color(0,0,0,0));
		jFrame.setSize(400, 400);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.add(s, BorderLayout.CENTER);
		jPanel.setOpaque(false);
		jFrame.setContentPane(jPanel);
		jFrame.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		JLabel jLabel = new JLabel();
		try {
			jLabel.setBounds(1, 1, 50, 50);
			jLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			jLabel.setIcon(NetworkUtils.getImageByUrl("http://www.baidu.com/"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		seeCom(jLabel);

	}

	public static void setStyle(Component c) {
		try {
			/*
			 * String
			 * windows="com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
			 */
			String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			/*
			 * String metal="javax.swing.plaf.metal.MetalLookAndFeel"; String
			 * motif="com.sun.java.swing.plaf.motif.MotifLookAndFeel";
			 */
			UIManager.setLookAndFeel(windows);
			SwingUtilities.updateComponentTreeUI(c);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(c, "can't set such style");
		}
	}

	/**
	 * 
	 * @param frame
	 * @param count 抖动次数 
	 */
	public static void shakeFrame(JFrame frame, int count) {
		Point point = frame.getLocation();// 窗体位置
		frame.setVisible(true);
		new Thread(
				new Runnable() {
					public void run() {
						for (int i = 10; i > 0; i--) {
							// 设置 真的
							for (int j = count; j > 0; j--) {
								point.y += i;
								frame.setLocation(point);
								point.x += i;
								frame.setLocation(point);
								point.y -= i;
								frame.setLocation(point);
								point.x -= i;
								frame.setLocation(point);

							}
						}
					}
				}).start();
	}
}
