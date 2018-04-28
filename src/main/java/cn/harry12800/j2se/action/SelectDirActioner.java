package cn.harry12800.j2se.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import cn.harry12800.j2se.utils.Config;


public class SelectDirActioner implements ActionListener{


	private IFileChooserExecute fileChooserExecute = null;
	private static String  defaultPath;
	static {
		String prop = Config.getProp(SelectDirActioner.class, "defaultPath");
		if(prop==null) 
			defaultPath = System.getProperty("user.dir")+File.separator;
		else {
			defaultPath =prop;
		}
	}
	public SelectDirActioner(IFileChooserExecute fileChooserExecute,String path) {
		defaultPath=path;
		this.fileChooserExecute  = fileChooserExecute;
	}
	public SelectDirActioner(IFileChooserExecute fileChooserExecute) {
		this.fileChooserExecute  = fileChooserExecute;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(defaultPath!=null)
			jFileChooser.setCurrentDirectory(new File(defaultPath));
		jFileChooser.setFileFilter(new FileTypeFilter() );
        int i = jFileChooser.showOpenDialog(null);
        if(i== JFileChooser.APPROVE_OPTION){ //打开文件
            String path = jFileChooser.getSelectedFile().getAbsolutePath();
            String name = jFileChooser.getSelectedFile().getName();
//            System.out.println(defaultPath);
            defaultPath= path;
            path= path.replaceAll("\\\\", "/");
            Config.setProp(SelectDirActioner.class, "defaultPath",path);
            fileChooserExecute.execute(path,name);
        }
	}
	public static void main(String[] args) {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setCurrentDirectory(new File("D:/"));
		jFileChooser.setSelectedFile(new File("D:/a.text"));
		jFileChooser.setName("abc");
		
		jFileChooser.setFileFilter(new FileTypeFilter() );
        int i = jFileChooser.showOpenDialog(null);
        if(i== JFileChooser.APPROVE_OPTION){ //打开文件
            String path = jFileChooser.getSelectedFile().getAbsolutePath();
            String name = jFileChooser.getSelectedFile().getName();
            System.out.println(path);
            System.out.println(name);
          //  fileChooserExecute.execute(path,name);
        }
	}
}
