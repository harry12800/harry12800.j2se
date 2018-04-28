package cn.harry12800.j2se.table;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.ParseException;

import javax.swing.JPanel;

import cn.harry12800.j2se.component.BaseDialog;

@SuppressWarnings("serial")
public class OperDialog<T> extends BaseDialog {
	private JPanel display = null;
	/**
	 * type 1是添加，type 2是修改
	 * @param type
	 */
	DisplayPanel<T> context;
	OperType type;
	T t;
	int width;
	int height;
	String name;
	String path;
	public OperDialog(DisplayPanel<T> context,OperType type,Object...objects) {
		super(context,type,objects);
		setSize(width,height);
		setCenterScreen();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void initObject(Object[] object) {
		context = (DisplayPanel<T>) object[0];
		type = (OperType) object[1];
		if(type == OperType.EDIT){
			Object[] obj = (Object[]) object[2];
			t =(T) obj[0];
			width = context.meta.editPanelMeta.width;
			height =context.meta.editPanelMeta.height;
			name = context.meta.editPanelMeta.title;
		}else if(type == OperType.ADD){
			width = context.meta.addPanelMeta.width;
			height =context.meta.addPanelMeta.height;
			name = context.meta.addPanelMeta.title;
		}else if(type == OperType.SEARCH){
			width = context.meta.searchPanelMeta.width;
			height =context.meta.searchPanelMeta.height;
			name = context.meta.searchPanelMeta.title;
		}else if(type == OperType.IMP){
			width = 500;
			height =200;
			Object[] obj = (Object[]) object[2];
			path =  (String) obj[0];
			name = "文件导入";
		}
		setName(name);
	}
	protected JPanel createCenterPanel() {
		if(type == OperType.ADD){
			return  initAddPanel();
		}else if(type == OperType.EDIT){
			return  initEditPanel();
		}else if(type == OperType.SEARCH){
			return  initSearchPanel();
		}else if(type == OperType.IMP){
			return  initImpPanel();
		}
		return display;
	}
	
	private JPanel initImpPanel() {
		display = new ImpPanel<T>( context,this,path,path);
		return display;
	}
	private JPanel initAddPanel() {
		display = new AddPanel<T>(context,this);
		return display;
	}
	private JPanel initEditPanel() {
		display = new EditPanel<T>(this,context,t);
		return display;
	}
	private JPanel initSearchPanel() {
		display = new SearchPanel<T>(context,this);
		return display;
	}
	private void setCenterScreen() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int w = (int) d.getWidth();
		int h = (int) d.getHeight();
		this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
	}
	public void showException(ParseException e) {
		// TODO Auto-generated method stub
		
	}
}
