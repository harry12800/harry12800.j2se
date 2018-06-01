package cn.harry12800.j2se.action;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileTypeFilter extends FileFilter {

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "*.xls;*.xlsx;*.csv";
	}

	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		String name = file.getName();
		return file.isDirectory() || name.toLowerCase().endsWith(".xls") || name.toLowerCase().endsWith(".csv")
				|| name.toLowerCase().endsWith(".xlsx"); // 仅显示目录和xls、xlsx文件
	}
}
