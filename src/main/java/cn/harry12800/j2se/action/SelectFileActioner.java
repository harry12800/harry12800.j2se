package cn.harry12800.j2se.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import cn.harry12800.j2se.utils.Config;


public class SelectFileActioner implements ActionListener{


	private IFileChooserExecute fileChooserExecute = null;
	private static String  defaultPath;
	static {
		String prop = Config.getProp(SelectFileActioner.class, "defaultPath");
		if(prop==null) 
			defaultPath = System.getProperty("user.dir")+File.separator;
		else {
			defaultPath =prop;
		}
	}
	public SelectFileActioner(IFileChooserExecute fileChooserExecute, String path) {
		defaultPath=path;
		this.fileChooserExecute  = fileChooserExecute;
	}
	public SelectFileActioner(IFileChooserExecute fileChooserExecute) {
		this.fileChooserExecute  = fileChooserExecute;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jFileChooser = new JFileChooser();
		if(defaultPath!=null)
		jFileChooser.setCurrentDirectory(new File(defaultPath));
		jFileChooser.setFileFilter(new FileTypeFilter() );
        int i = jFileChooser.showOpenDialog(null);
        if(i== JFileChooser.APPROVE_OPTION){ //打开文件
            String path = jFileChooser.getSelectedFile().getAbsolutePath();
            String name = jFileChooser.getSelectedFile().getName();
            fileChooserExecute.execute(path,name);
            defaultPath =path;
            path= path.replaceAll("\\\\", "/");
            Config.setProp(SelectFileActioner.class, "defaultPath",path);
        }
	}

	public void open(){
		JFileChooser jFileChooser = new JFileChooser();
		if(defaultPath!=null)
		jFileChooser.setCurrentDirectory(new File(defaultPath));
		jFileChooser.setFileFilter(new FileTypeFilter() );
        int i = jFileChooser.showOpenDialog(null);
        if(i== JFileChooser.APPROVE_OPTION){ //打开文件
            String path = jFileChooser.getSelectedFile().getAbsolutePath();
            String name = jFileChooser.getSelectedFile().getName();
            fileChooserExecute.execute(path,name);
            defaultPath =path;
            path= path.replaceAll("\\\\", "/");
            Config.setProp(SelectFileActioner.class, "defaultPath",path);
        }
	}
}
