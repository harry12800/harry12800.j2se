package cn.harry12800.j2se.table;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyTableModel<T> extends AbstractTableModel {
	

	private static final long serialVersionUID = 1L;

	private Vector<Object> content = null;

	private String[] title;

	private DisplayPanel<T> context;

//	private MyTable<T> table;

	public MyTableModel(DisplayPanel<T> context, MyTable<T> myTable) {
		content = new Vector<Object>();
		this.context = context;
//		this.table = myTable;
		this.title = context.getTitles().toArray(new String[0]);
		
	}

	public void removeRow(int row) {
		content.remove(row);
	}

	public void removeRows(int row, int count) {
		for (int i = 0; i < count; i++) {
			if (content.size() > row) {
				content.remove(row);
			}
		}
	}

	/**
	 * 让表格中某些值可修改，但需要setValueAt(Object value, int row, int col)方法配合才能使修改生效
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 使修改的内容生效
	 */
	@SuppressWarnings("unchecked")
	public void setValueAt(Object value, int row, int col) {
		 ((Vector<Object>) content.get(row)).remove(col);
		 ((Vector<Object>) content.get(row)).add(col, value);
		if(context.meta.immediatelyUpdate){
			context.updateColumn(row,col,value);
		}
		this.fireTableCellUpdated(row, col);
	}

	public String getColumnName(int col) {
		return title[col];
	}

	public int getColumnCount() {
		return title.length;
	}

	public int getRowCount() {
		return content.size();
	}

	@SuppressWarnings("unchecked")
	public Object getValueAt(int row, int col) {
		return  ((Vector<Object>) content.get(row)).get(col);
	}
//	/**
//	 * 返回数据类型
//	 */
//	public Class<?> getColumnClass(int col) {
//		Class<?> columnClass = super.getColumnClass(col);
//		 
//		return columnClass;
//	}
	public void addRow(T t) {
		Vector<Object> v = new Vector<Object>(title.length);
		//"序号", "物料编码", "旧物料编码",
		//"订单状态", "开单日期", "要求日期", "描述", 
		//"数量(工厂开单数量)", "尺寸", "材质", "非零号",
		//"刀模号", "颜色", "备注", "导入日期", "最后修改日期" };
		List<Object[]> annotationList = context.getAnnotationList();
		int i =0;
		for (Object[] field : annotationList) {
			Field ef= ((Field)field[1]);
			Object invokeGetter = Reflections.invokeGetter(t,ef.getName());
			v.add(i++,invokeGetter);
		}
		content.add(v);
	}
	 public void setContent(Vector<Object> content) {
		this.content = content;
		//setDataVector(Vector data, Vector names);
	}

}