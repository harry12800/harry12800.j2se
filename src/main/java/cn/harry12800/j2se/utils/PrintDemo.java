package cn.harry12800.j2se.utils;

import java.io.File;
import java.io.IOException;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;

import cn.harry12800.tools.FileUtils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 建立与打印机的连接
 * 
 * @author Administrator
 *
 */
public class PrintDemo {

	public static void main(String[] args) throws IOException {
		byte[] t = FileUtils.readFile("C:/Users/harry12800/Desktop/export.csv");
		for (int s = 0; s < t.length; s++) {
			System.err.println(t[s]);
		}
		String ar = FileUtils.getSrcByFilePath("C:/Users/harry12800/Desktop/export.csv");
		System.err.println(ar);
	}

	public static void main2(String[] args) {

		// 构建打印请求属性集
		HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		// 设置打印格式，因为未确定类型，所以选择autosense
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		// 查找所有的可用的打印服务
		PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
		for (PrintService printService2 : printService) {
			System.out.println(printService2.getName());
		}
		// 定位默认的打印服务
		PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
		System.out.println(defaultService.getName());
		//			// 显示打印对话框
		//			PrintService service = ServiceUI.printDialog(null, 200, 200, printService, defaultService, flavor, pras);
		//			if (service != null) {
		//				try {
		//					DocPrintJob job = service.createPrintJob(); // 创建打印作业
		//					FileInputStream fis = new FileInputStream(file); // 构造待打印的文件流
		//					DocAttributeSet das = new HashDocAttributeSet();
		//					Doc doc = new SimpleDoc(fis, flavor, das);
		//					
		//					job.print(doc, pras);
		//				} catch (Exception e) {
		//					e.printStackTrace();
		//				}
		//			}
	}

	/**
	 * 功能:实现excel打印工作
	 */
	public static boolean printExcelFile(File f) {
		if (f != null && f.exists()) {
			try {
				ComThread.Release();
				ComThread.InitSTA();
			} catch (RuntimeException e) {
				return false;
			}

			ActiveXComponent xl = new ActiveXComponent("Excel.Application");

			// System.out.println("version=" +
			// xl.getProperty("Version"));
			// 不打开文档
			// Dispatch.put(xl, "Visible", new Variant(false));
			Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();
			// 打开文档
			Dispatch excel = Dispatch.call(workbooks, "Open",
					f.getAbsolutePath()).toDispatch();
			// 横向打印(2013/05/24)
			// Dispatch currentSheet =
			// Dispatch.get(excel,"ActiveSheet").toDispatch();
			// Dispatch pageSetup = Dispatch.get(currentSheet,
			// "PageSetup").toDispatch();
			// Dispatch.put(pageSetup, "Orientation", new Variant(2));
			// 每张表都横向打印2013-10-31
			Dispatch sheets = Dispatch.get((Dispatch) excel, "Sheets")
					.toDispatch();
			// 获得几个sheet
			int count = Dispatch.get(sheets, "Count").getInt();
			// System.out.println(count);
			for (int j = 1; j <= count; j++) {
				Dispatch sheet = Dispatch.invoke(sheets, "Item", Dispatch.Get,
						new Object[] { new Integer(j) }, new int[1])
						.toDispatch();
				Dispatch pageSetup = Dispatch.get(sheet, "PageSetup")
						.toDispatch();
				Dispatch.put(pageSetup, "Orientation", new Variant(1));
				Dispatch.call(sheet, "PrintOut");
			}
			// 开始打印
			if (excel != null) {
				// Dispatch.call(excel, "PrintOut");
				// 增加以下三行代码解决文件无法删除bug
				Dispatch.call(excel, "save");
				Dispatch.call(excel, "Close", new Variant(true));
				excel = null;
			}
			xl.invoke("Quit", new Variant[] {});
			xl = null;
			// 始终释放资源
			ComThread.Release();
			return true;
		}
		return false;
	}

	public static boolean printWordFile(File f) {
		if (f != null && f.exists()) {
			ComThread.InitSTA();
			ActiveXComponent wd = new ActiveXComponent("Word.Application");
			try {
				// 不打开文档
				Dispatch.put(wd, "Visible", new Variant(false));
				Dispatch document = wd.getProperty("Documents").toDispatch();
				// 打开文档
				Dispatch doc = Dispatch
						.invoke(document, "Open", Dispatch.Method, new Object[] { f.getAbsolutePath() }, new int[1])
						.toDispatch();
				// 开始打印
				if (doc != null) {
					Dispatch.call(doc, "PrintOut");
					// 增加以下三行代码解决文件无法删除bug
					Dispatch.call(doc, "save");
					Dispatch.call(doc, "Close", new Variant(true));
					doc = null;
				}
				wd.invoke("Quit", new Variant[] {});
				wd = null;
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				// 始终释放资源
				ComThread.Release();
			}
		} else {
			return false;
		}

	}
}
