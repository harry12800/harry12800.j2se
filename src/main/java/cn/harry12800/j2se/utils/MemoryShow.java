package cn.harry12800.j2se.utils;

import java.text.NumberFormat;

import cn.harry12800.j2se.tip.TipFrame;


public class MemoryShow {


	public MemoryShow() {

	}
	public static void show( ) {
		show(true, true);
	}
	public static void show(Boolean sound,boolean reader) {
		Runtime run = Runtime.getRuntime();
		long free = run.freeMemory();
		long total = run.totalMemory();
		String freeString = getMemorySize(free);
		String totalString = getMemorySize(total);
		TipFrame.show(sound,reader,"内存使用情况", "ok", "剩余空间："+freeString+"\r\n"+"使用空间："+totalString);
	}
	/**
	 * 获取文件的大小 ，非文件夹，如果是文件夹 或者文件不存在 将返回 "";
	 * 
	 * @param file
	 * @return
	 */
	public static String getMemorySize(long length){
		char[] ch= new char[]{'B','K','M','G','T','P','Z','E'};
		int i = 0;
		double size = length;
		while(size>=1024){
			size = size/1024;
			i++;
		}
		NumberFormat nf = NumberFormat.getNumberInstance();
	    nf.setMaximumFractionDigits(2);
//				DecimalFormat df = new DecimalFormat ("#.00");
		return nf.format(size)+ch[i];
	}
}
