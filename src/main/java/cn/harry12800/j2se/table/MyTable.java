package cn.harry12800.j2se.table;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import cn.harry12800.j2se.popup.PopupFrame;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.tools.SQLUtils;

public class MyTable<T> extends JTable {
	private PopupFrame popupMenu = null;
	private String selectSql = "";

	public String getSelectSql() {
		return selectSql;
	}

	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}

	private String editBeforeValue = "";
	private String sql = null;

	public String getEditBeforeValue() {
		return editBeforeValue;
	}

	public void setEditBeforeValue(String editBeforeValue) {
		this.editBeforeValue = editBeforeValue;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8009377415024813734L;
	private MyTableModel<T> tm = null;
	private DisplayPanel<T> context;
	public List<T> list = null;
	static int x = 0;
	private String lastestSql;

	@Override
	public JToolTip createToolTip() {
		return new MyToolTip(x++);
	}

	public MyTable(DisplayPanel<T> displayPanel) {
		//this.setFont(J2seFont.getDefinedFont(14.0f));
		this.context = displayPanel;

		setRowHeight(20);
		setBackground(UI.backColor);
		setForeground(Color.WHITE);
		sql = displayPanel.getBaseSql();
		addMouseListener(new MyTableMouseListener<T>(displayPanel, this));
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent arg0) {
				//				int row=MyTable.this.rowAtPoint(arg0.getPoint());
				//				Component componentAt = getComponentAt(arg0.getPoint());
				//				Component component2 = arg0.getComponent();
				//				System.out.println("mouseMoved:"+row);
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		addPop();

	}

	@Override
	protected void paintComponent(Graphics g) {
		getTableHeader().setFont(UI.normalFont(14));
		super.paintComponent(g);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column > 0)
			return true;
		else {
			return false;
		}
	}

	public void setTM(MyTableModel<T> tm) {
		this.tm = tm;
		this.tm.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = MyTable.this.getEditingRow();
				int column = MyTable.this.getEditingColumn();
				String editAfterValue = MyTable.this.getModel().getValueAt(row, column) + "";
				if (editBeforeValue.equals(editAfterValue)) {
					//					System.out.println("没修改");
				} else {
					MyTable.this.setValueAt(editBeforeValue, row, column);
					((AbstractTableModel) MyTable.this.getModel()).fireTableCellUpdated(row, column);
				}
			}
		});
	}

	public void setTableContent(List<T> list) {
		tm = new MyTableModel<T>(context, this);
		for (int i = 0; i < list.size(); ++i) {
			tm.addRow(list.get(i));
		}
		setModel(tm);
	}

	public void refresh(String sqlurl) {
		String mysqlPageSQL = sql + sqlurl;
		if (context.isPage) {
			mysqlPageSQL = SQLUtils.getMysqlPageSQL(sql + sqlurl, context.getPageSize(), 1);
		}
		this.lastestSql = mysqlPageSQL;
		list = context.executeQuery(mysqlPageSQL);
		if (context.isPage) {
			int count = context.executeQueryCount(SQLUtils.getMysqlPageContent(sql + sqlurl));
			context.getPagePanel().update(count, 1);
		}
		selectSql = sqlurl;
		context.getWorkPane().getInfoLabel().setText("共" + list.size() + "条记录");
		setTableContent(list);
		TableColumnModel columnModel = getColumnModel();
		TableColumn column = columnModel.getColumn(0);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
		context.addRender(columnModel);
	}

	public void refresh(int count, int cur) {
		String mysqlPageSQL = SQLUtils.getMysqlPageSQL(sql + selectSql, context.getPageSize(), cur);
		//		System.out.println(mysqlPageSQL);
		list = context.executeQuery(mysqlPageSQL);
		this.lastestSql = mysqlPageSQL;
		//		list = context.executeQuery(sql + selectSql);
		context.getWorkPane().getInfoLabel().setText("共" + list.size() + "条记录");
		setTableContent(list);
		TableColumnModel columnModel = getColumnModel();
		TableColumn column = columnModel.getColumn(0);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
		context.addRender(columnModel);
	}

	public void refresh(List<T> list) {
		this.list = list;
		context.getWorkPane().getInfoLabel().setText("共" + list.size() + "条记录");
		setTableContent(list);
		TableColumnModel columnModel = getColumnModel();
		TableColumn column = columnModel.getColumn(0);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
		context.addRender(columnModel);
	}

	public T getBeanByRow(int row) {
		return list.get(row);
	}

	public void refreshLatest() {
		//		System.out.println(mysqlPageSQL);
		list = context.executeQuery(lastestSql);
		context.getWorkPane().getInfoLabel().setText("共" + list.size() + "条记录");
		setTableContent(list);
		TableColumnModel columnModel = getColumnModel();
		TableColumn column = columnModel.getColumn(0);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
		context.addRender(columnModel);
	}

	public void addPop() {
		popupMenu = context.getPop();
	}

	public PopupFrame getPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(PopupFrame popupMenu) {
		this.popupMenu = popupMenu;
	}

	public void reload(Object bean) {
		int indexOf = list.indexOf(bean);
		//		System.out.println(bean);
		//		System.out.println(indexOf);
		if (indexOf >= 0) {
			List<Object[]> annotationList = context.getAnnotationList();
			int i = 0;
			for (Object[] field : annotationList) {
				Field ef = ((Field) field[1]);
				Object invokeGetter = Reflections.invokeGetter(bean, ef.getName());
				setValueAt(invokeGetter, indexOf, i++);
			}
			tm.fireTableRowsUpdated(indexOf, indexOf);
		}
	}
}
