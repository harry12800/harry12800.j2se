package cn.harry12800.j2se.table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.table.TableColumn;

public class MyTableMouseListener<T> implements MouseListener {

	private DisplayPanel<T> context;
	private MyTable<T> table;

	public MyTableMouseListener(DisplayPanel<T> displayPanel, MyTable<T> myTable) {
		this.context = displayPanel;
		this.table = myTable;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//int col = table.getSelectedColumn();
		//int row = table.getSelectedRow();
		// table.setEditBeforeValue(table.getValueAt(row, col) + "");
		if (e.getButton() == MouseEvent.BUTTON1) {

		}

		if (e.getButton() == MouseEvent.BUTTON3) {
			if (table.getPopupMenu() != null)
				table.getPopupMenu().show(e.getPoint(), table);
		}
	}

	public void mousePressed(MouseEvent evt) {
		if ((evt.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			int modifiers = evt.getModifiers();
			modifiers -= MouseEvent.BUTTON3_MASK;
			modifiers |= MouseEvent.BUTTON1_MASK;
			MouseEvent ne = new MouseEvent(evt.getComponent(), evt.getID(),
					evt.getWhen(), modifiers, evt.getX(), evt.getY(),
					evt.getClickCount(), false);
			table.dispatchEvent(ne);
		}
		if (evt.isPopupTrigger()) {
			/*// 取得右键点击所在行
			//int row = evt.getY() / table.getRowHeight();
			*/
			/**
			 * 取得是表名的那一列
			 */
			int tableNameColumn = -1;

			for (int i = 0; i < context.getTable().getColumnModel().getColumnCount(); i++) {
				TableColumn selColumn = context.getTable().getColumnModel().getColumn(i);
				String columnHeader = (String) selColumn.getHeaderValue();

				if (columnHeader.equals("Table Name")) {
					tableNameColumn = i;
					break;
				}
			}
			table.getPopupMenu().show(evt.getPoint(), table);
			/**
			 * 取得表名并弹出菜单
			 */
			if (tableNameColumn != -1) {
				/**
				 * 修改菜单首条的名称
				 */
				/*String tableName = (String) table.getValueAt(row,
					tableNameColumn);
				tableNameItem.setText(tableName);
				
				弹出菜单
				//evt.getComponent()
				 * 
				 * *
				 */
				table.getPopupMenu().show(evt.getPoint(), table);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//		System.out.println("mouseEntered");
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
