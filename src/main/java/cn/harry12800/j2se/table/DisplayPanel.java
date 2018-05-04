package cn.harry12800.j2se.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import cn.harry12800.j2se.action.IFileChooserExecute;
import cn.harry12800.j2se.action.SelectDirActioner;
import cn.harry12800.j2se.action.SelectFileActioner;
import cn.harry12800.j2se.popup.PopupFrame;
import cn.harry12800.j2se.style.MyScrollBarUI;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.j2se.table.page.PagePanel;
import cn.harry12800.tools.DbField;
import cn.harry12800.tools.DbTable;
import cn.harry12800.tools.StringUtils;

@SuppressWarnings("serial")
public abstract class DisplayPanel<T> extends JPanel {
	/**
	 * 注解列表（Object[]{ ExcelField, Field/Method }）
	 */
	List<Object[]> annotationList = new ArrayList<>();
	protected MyTable<T> table = null;
	private WorkPanel<T> workPanel = null;
	private Class<?> entityClass;
	private List<String> fieldNameList = new ArrayList<String>(0);
	private List<String> dbFieldNameList = new ArrayList<String>(0);
	private List<String> titleList = new ArrayList<String>(0);
	private List<DbField> tableFieldList = new ArrayList<DbField>(0);
	public int pageSize = 25;
	public boolean isPage = false;
	/**
	 * 映射
	 */
	private Map<DbField, Field> map = new HashMap<DbField, Field>();

	public static class Meta {
		public boolean isCanEdit = true;
		public boolean isCanImp = true;
		public boolean isCanExp = true;
		public boolean isCanAdd = true;
		public boolean isCanDel = true;
		public boolean isCanSearch = true;
		public boolean isRefresh = true;
		public boolean immediatelyUpdate = false;

		public String deleteHint;
		public String refreshHint;
		public String addHint;
		public String editHint;
		public String searchHint;
		public String impHint;
		public String expHint;
		public AddPanelMeta addPanelMeta = new AddPanelMeta();
		public EditPanelMeta editPanelMeta = new EditPanelMeta();
		public SearchPanelMeta searchPanelMeta = new SearchPanelMeta();
	}

	public static class AddPanelMeta {
		public int width = 500;
		public int height = 350;
		public String showTitle = "手动添加内容方式";
		public String title = "手动添加内容方式";
	}

	public static class EditPanelMeta {
		public int width = 500;
		public int height = 350;
		public String showTitle = "修改数据";
		public String title = "修改数据";
	}

	public static class SearchPanelMeta {
		public int width = 500;
		public int height = 350;
		public String showTitle = "查询数据";
		public String title = "查询数据";
	}

	public Meta meta = new Meta();

	private String tableName;

	public DisplayPanel() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		this.entityClass = getTClass();
		initMeta(meta);
		initCls();
		initTable();
		System.out.println(getTClass());
	}

	/**
	 * 初始化配置
	 * @param meta
	 */
	protected abstract void initMeta(Meta meta);

	public Class<?> getTClass() {
		Type genType = getClass().getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] actualTypeArguments = ((ParameterizedType) genType).getActualTypeArguments();
		return (Class<?>) actualTypeArguments[0];
	}

	/**
	 * 获取isPage
	 *	@return the isPage
	 */
	public boolean isPage() {
		return isPage;
	}

	/**
	 * 设置isPage
	 * @param isPage the isPage to set
	 */
	public void setPage(boolean isPage) {
		if (isPage) {
			this.pagePanel = new PagePanel<>(this);
			if (this.isPage == false) {
				add(pagePanel, BorderLayout.SOUTH);
				revalidate();
				this.isPage = true;
				this.getTable().refresh(getInitSql());
			}
		} else {
			if (!isPage) {
				remove(pagePanel);
				revalidate();
				this.isPage = false;
				this.getTable().refresh(getInitSql());
			}
		}
		this.isPage = isPage;
	}

	public List<Object[]> getAnnotationList() {
		return annotationList;
	};

	protected PopupFrame popupMenu;
	private OperDialog<T> impDialog;
	private OperDialog<T> searchDialog;

	/**
	 * 读取注解信息
	 */
	private void initCls() {
		DbTable annotation = entityClass.getAnnotation(DbTable.class);
		this.tableName = annotation.tableName();
		Field[] fs = entityClass.getDeclaredFields();
		int[] groups = new int[] {};
		int type = 1;
		for (Field f : fs) {
			DbField ef = f.getAnnotation(DbField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == type)) {
				if (groups != null && groups.length > 0) {
					boolean inGroup = false;
					for (int g : groups) {
						if (inGroup) {
							break;
						}
						for (int efg : ef.groups()) {
							if (g == efg) {
								inGroup = true;
								annotationList.add(new Object[] { ef, f });
								map.put(ef, f);
								break;
							}
						}
					}
				} else {
					annotationList.add(new Object[] { ef, f });
					map.put(ef, f);
				}
			}
		}

		// Get annotation method
		Method[] ms = entityClass.getDeclaredMethods();
		for (Method m : ms) {
			DbField ef = m.getAnnotation(DbField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == type)) {
				if (groups != null && groups.length > 0) {
					boolean inGroup = false;
					for (int g : groups) {
						if (inGroup) {
							break;
						}
						for (int efg : ef.groups()) {
							if (g == efg) {
								inGroup = true;
								annotationList.add(new Object[] { ef, m });
								break;
							}
						}
					}
				} else {
					annotationList.add(new Object[] { ef, m });
				}
			}
		}
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((DbField) o1[0]).sort()).compareTo(
						new Integer(((DbField) o2[0]).sort()));
			};
		});
		for (Object[] objects : annotationList) {
			DbField ef = ((DbField) objects[0]);
			Field f = ((Field) objects[1]);
			dbFieldNameList.add(ef.dbFieldName());
			titleList.add(ef.title());
			fieldNameList.add(f.getName());
			tableFieldList.add(ef);
		}
	}

	PagePanel<T> pagePanel = null;

	/**
	 * 初始化表格
	 */
	private void initTable() {
		table = new MyTable<T>(this);
		table.setToolTipText("asdsfdfsaasddfsa");
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setOpaque(false);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component searchDlog = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
						row, column);
				if (isSelected) {
					((JLabel) searchDlog).setOpaque(true);
					searchDlog.setBackground(UI.foreColor);
				} else {
					((JLabel) searchDlog).setOpaque(false);
				}
				return searchDlog;
			}
		};
		render.setOpaque(false); // 将渲染器设置为透明

		tableHeader.setDefaultRenderer(render);
		TableCellRenderer headerRenderer = tableHeader.getDefaultRenderer();
		if (headerRenderer instanceof JLabel) {
			((JLabel) headerRenderer).setHorizontalAlignment(JLabel.CENTER);
			((JLabel) headerRenderer).setOpaque(false);
		}
		table.setShowHorizontalLines(false);//  水平线不显示
		table.setShowVerticalLines(false); //垂直线不显示
		//		setIntercellSpacing()：修改单元格间隔，因此也将影响网格线的粗细。
		//		setRowMargin   (0);设置相邻两行单元格的距离
		//
		//		myJTable1.setIntercellSpacing(new Dimension(0,1));  只显示水平网格线
		tableHeader.getTable().setOpaque(false);// 设置头部里面的表格透明
		// 将这个渲染器设置到fileTable里。这个设置在没有另外专门对column设置的情况下有效
		// 若你对某个column特殊指定了渲染器，则对于这个column，它将不调用render渲染器
		// 因此为了保证透明，如果你对column额外指定了渲染器，那么在额外的渲染器里也应该设置透明
		table.setDefaultRenderer(Object.class, render);
		//		add(tableHeader,BorderLayout.NORTH);
		JScrollPane scroll = new JScrollPane();
		add(scroll, BorderLayout.CENTER);
		//		scroll.setBackground(new Color(0,0,0,0));
		workPanel = new WorkPanel<T>(this);
		scroll.getViewport().setOpaque(false);//将JScrollPane设置为透明  
		scroll.setOpaque(false);//将中间的viewport设置为透明  
		scroll.setViewportView(table);//装在表格  
		table.setOpaque(false);
		table.setSelectionBackground(UI.foreColor);
		table.setSelectionForeground(Color.WHITE);
		scroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		//		scroll.getViewport().setBackground( UI.backColor);
		//		scroll.setBackground(UI.backColor);
		scroll.getVerticalScrollBar().setBackground(UI.backColor);
		scroll.setColumnHeaderView(table.getTableHeader());//设置头部（HeaderView部分）
		scroll.getColumnHeader().setOpaque(false);//再取出头部，并设置为透明
		scroll.setBorder(new EmptyBorder(0, 0, 0, 0));
		add(workPanel, BorderLayout.EAST);
		if (isPage) {
			pagePanel = new PagePanel<T>(this);
			add(pagePanel, BorderLayout.SOUTH);
		}
		String initSql = getInitSql();
		if (initSql != null)
			table.refresh(initSql);
	}

	/**
	 * 获取pagePanel
	 *	@return the pagePanel
	 */
	public PagePanel<T> getPagePanel() {
		return pagePanel;
	}

	/**
	 * 设置pagePanel
	 * @param pagePanel the pagePanel to set
	 */
	public void setPagePanel(PagePanel<T> pagePanel) {
		this.pagePanel = pagePanel;
	}

	public MyTable<T> getTable() {
		return table;
	}

	public void refresh(String sqlurl) {
		table.refresh(sqlurl);
	}

	public void refresh() {
		table.refreshLatest();
	}

	protected String getBaseSql() {
		List<String> titles = new ArrayList<String>(0);
		for (Object[] objects : annotationList) {
			DbField ef = ((DbField) objects[0]);
			titles.add(tableName + "." + ef.dbFieldName());
		}
		return "select " + StringUtils.getCommaMerge(titles);
	};

	protected String getInitSql() {
		return " from " + tableName;
	};

	protected List<String> getTitles() {
		return titleList;
	};

	protected WorkPanel<T> getWorkPane() {
		return workPanel;
	}

	/**
	 * 给右侧操作面板添加自定义控件
	 * @return
	 */
	abstract public List<Component> getWorkComp();

	/**
	 * 添加窗口
	 */
	public void showAddDialog() {
		new OperDialog<T>(this, OperType.ADD);
	}

	/**
	 * 保持数据到数据库
	 * @param t
	 * @return
	 */
	public abstract int saveData(T t);

	/**
	 * 更新数据到数据库
	 * @param t
	 * @return
	 */
	public abstract int updateData(T t);

	public int searchData(T t) {
		table.refresh(getSearchData(t));
		return 1;
	};

	/**
	 * 实现查询数据的方法
	 * @param t
	 * @return
	 */
	public abstract List<T> getSearchData(T t);

	public abstract void validateData(T t) throws Exception;

	public void showEditDialog() {
		int x[] = getTable().getSelectedRows();
		if (0 == x.length) {
			JOptionPane.showMessageDialog(null, "没选择记录");
		} else {
			//			String id = "" + getTable().getModel().getValueAt(x[0], 0);
			new OperDialog<T>(this, OperType.EDIT, getSelectBean());
		}
	}

	//	abstract protected T getBeanById(String id)  ;
	public abstract List<T> executeQuery(String sql);

	@SuppressWarnings("unchecked")
	public Class<T> getBeanClazz() {
		return (Class<T>) entityClass;
	}

	/**
	 * 
	 * @param columnModel
	 */
	public void addRender(TableColumnModel columnModel) {
		int col = 0;
		for (Object[] objects : annotationList) {
			DbField ef = ((DbField) objects[0]);
			Field f = ((Field) objects[1]);
			if (!ef.show()) {
				TableColumn column = columnModel.getColumn(col);
				column.setMinWidth(0);
				column.setMaxWidth(0);
				column.setWidth(0);
				column.setPreferredWidth(0);
			} else {
				if (ef.width() > 0) {
					TableColumn column = columnModel.getColumn(col);
					column.setMinWidth(ef.width());
					column.setMaxWidth(0);
					column.setWidth(ef.width());
					column.setPreferredWidth(0);
				}
				TableColumn column = columnModel.getColumn(col);
				if (f.getType() == java.util.Date.class) {
					setDateRander(column);
				}
			}
			col++;
		}
	}

	class DateTableCellEditor extends JTextField implements TableCellEditor {
		private EventListenerList listenerList = new EventListenerList();
		//ChangeEvent用于通知感兴趣的参与者事件源中的状态已发生更改。  
		private ChangeEvent changeEvent = new ChangeEvent(this);
		Object valueObject = null;

		public DateTableCellEditor(TableColumn column) {

		}

		@Override
		public boolean stopCellEditing() {
			//			System.out.println("stopCellEditing");
			fireEditingStopped();
			return true;
		}

		@Override
		public boolean shouldSelectCell(EventObject arg0) {
			return true;
		}

		@Override
		public void removeCellEditorListener(CellEditorListener l) {
			listenerList.remove(CellEditorListener.class, l);
		}

		private void fireEditingStopped() {
			CellEditorListener listener;
			Object[] listeners = listenerList.getListenerList();
			for (int i = 0; i < listeners.length; i++) {
				if (listeners[i] == CellEditorListener.class) {
					// 之所以是i+1，是因为一个为CellEditorListener.class（Class对象），
					// 接着的是一个CellEditorListener的实例
					listener = (CellEditorListener) listeners[i + 1];
					// 让changeEvent去通知编辑器已经结束编辑
					// 在editingStopped方法中，JTable调用getCellEditorValue()取回单元格的值，
					// 并且把这个值传递给TableValues(TableModel)的setValueAt()
					listener.editingStopped(changeEvent);
				}
			}
		}

		@Override
		public boolean isCellEditable(EventObject arg0) {

			return true;
		}

		@Override
		public Object getCellEditorValue() {
			//			System.out.println("getCellEditorValue");
			SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date parse = sdfDateFormat.parse(getText());
				return parse;
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, "格式不对！");
				//				e.printStackTrace();
			}
			return valueObject;
		}

		@Override
		public void cancelCellEditing() {
			//			System.out.println("cancelCellEditing");
		}

		@Override
		public void addCellEditorListener(CellEditorListener l) {
			listenerList.add(CellEditorListener.class, l);
			//			System.out.println("addCellEditorListener");
		}

		@Override
		public Component getTableCellEditorComponent(JTable arg0, Object arg1,
				boolean arg2, int arg3, int arg4) {
			Date sDate = (Date) arg1;
			valueObject = arg1;
			SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (sDate == null) {
				this.setText("");
			} else {
				String format = sdfDateFormat.format(sDate);
				this.setText(format);
			}
			return this;
		}
	}

	private void setDateRander(TableColumn column) {
		column.setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				Date sDate = (Date) value;
				SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String format;
				if (sDate == null)
					format = "";
				else
					format = sdfDateFormat.format(sDate);
				JLabel jLabel = new JLabel(format);
				if (isSelected) {
					jLabel.setOpaque(true);
					jLabel.setBackground(getTable().getSelectionBackground());
				}
				return jLabel;
			}
		});
		column.setCellEditor(new DateTableCellEditor(column));
	}

	/**
	 * 得到右键菜单
	 * @return
	 */
	public PopupFrame getPop() {
		return null;
	}

	public void showException(ParseException e) {
	}

	/**
	 * 实现删除数据，从选只数据
	 * @return
	 */
	public abstract int deleteData();

	/**
	 * 更新数据
	 * @param row
	 * @param col
	 * @param value
	 */
	public void updateColumn(int row, int col, Object value) {
		if (!tableFieldList.get(col).canEdit())
			return;
		T beanByRow = getTable().getBeanByRow(row);
		Field field = map.get(tableFieldList.get(col));
		if (field.getType() == Integer.class)
			value = Integer.valueOf(value + "");
		Reflections.invokeSetter(beanByRow, getPropertyNameByColumn(col), value);
		try {
			updateData(beanByRow);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "修改失败！");
		}
	}

	public String getPropertyNameByColumn(int col) {
		return fieldNameList.get(col);
	}

	public Component getAddDialogNorthComp(OperDialog<T> dialog) {
		return null;
	}

	public int getColumnByFieldName(String fieldName) {
		return fieldNameList.indexOf(fieldName);
	}

	//	private void excelExp(String path) {
	//		List<T> executeQuery = getTable().list;
	//		path = path+File.separator+"export.xlsx";
	//		if(new File(path).exists()){
	//			int showConfirmDialog = JOptionPane.showConfirmDialog(null, "文件已存在！是否替换？", "确认框", JOptionPane.YES_NO_OPTION);
	//			if(showConfirmDialog==1)return ;
	//		}
	//		Set<ExcelPosition> set = new LinkedHashSet<>();
	//		int col = 0;
	//		for (DbField tField : tableFieldList) {
	//			if(tField.exp()){
	//				ExcelPosition ePosition=new ExcelPosition(0, col, tField.value());
	//				set.add(ePosition);
	//				col++;
	//			}
	//		}
	//		int row= 1;
	//		for (T t : executeQuery) {
	//			int i =0;
	//			col = 0;
	//			for (DbField tField : tableFieldList) {
	//				if(tField.exp()){
	//					Object fieldValue = Reflections.getFieldValue(t, fieldNameList.get(i));
	//					ExcelPosition ePosition=new ExcelPosition(row, col, fieldValue+"");
	//					set.add(ePosition);
	//					col++;
	//				}
	//				i++;
	//			}
	//			row++;
	//		}
	//		try {
	//			ExcelReader.createExcel(path, set);
	//			Clip.openFile(path);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
	public void excelExp() {
		SelectDirActioner selectFileActioner = new SelectDirActioner(new IFileChooserExecute() {
			@Override
			public int execute(String path, String name) {
				//				excelExp(path);
				return 0;
			}
		});
		selectFileActioner.actionPerformed(null);
	}

	public void excelImp() {
		if (impDialog != null) {
			impDialog.setVisible(true);
			return;
		}
		SelectFileActioner selectFileActioner = new SelectFileActioner(new IFileChooserExecute() {
			@Override
			public int execute(String path, String name) {
				excelImp(path);
				return 0;
			}
		});
		selectFileActioner.open();
	}

	/**
	 * 设置impDialog
	 * @param impDialog the impDialog to set
	 */
	public void setImpDialog(OperDialog<T> impDialog) {
		this.impDialog = impDialog;
	}

	/**
	 * 获取impDialog
	 *	@return the impDialog
	 */
	public OperDialog<T> getImpDialog() {
		return impDialog;
	}

	public void excelImp(String path) {
		new OperDialog<T>(this, OperType.IMP, path);
	}

	protected boolean unSelectTable() {
		int x[] = getTable().getSelectedRows();
		if (0 == x.length) {
			JOptionPane.showMessageDialog(null, "没选择记录");
			return true;
		} else {
			return false;
		}
	}

	protected Set<String> getTableSelectId() {
		int x[] = getTable().getSelectedRows();
		Set<String> set = new HashSet<String>(0);
		for (int i = 0; i < x.length; ++i) {
			String id = "" + getTable().getModel().getValueAt(x[i], 0);
			set.add(id);
			if (i >= 2000)
				break;
		}
		return set;
	}

	protected String getTableSelectMamaId() {
		int x[] = getTable().getSelectedRows();
		Set<String> set = new HashSet<String>(0);
		for (int i = 0; i < x.length; ++i) {
			String id = "" + getTable().getModel().getValueAt(x[i], 0);
			set.add(id);
		}
		String sql = StringUtils.getSQLInSentence(set, "id");
		return sql;
	}

	protected String getTableSelectFirstId() {
		int x[] = getTable().getSelectedRows();
		return "" + getTable().getModel().getValueAt(x[0], 0);
	}

	public List<T> getSelectBeans() {
		List<T> list = new ArrayList<T>();
		int[] selectedRows = getTable().getSelectedRows();
		for (int i : selectedRows) {
			list.add(getTable().list.get(i));
		}
		return list;
	}

	public T getSelectBean() {
		int selectedRow = getTable().getSelectedRow();
		if (selectedRow < 0)
			return null;
		return getTable().list.get(selectedRow);
	}

	public void showSearchDialog() {
		if (searchDialog != null) {
			searchDialog.setVisible(true);
			return;
		}
		this.searchDialog = new OperDialog<T>(this, OperType.SEARCH);
	}

	public Object getIntegerKey() {
		return null;
	}

	public abstract int executeQueryCount(String mysqlPageContent);

	protected void impBegin() {
	}

	protected void impEnd(int count) {
	}

	/**
	 * 导入时自己加工处理
	 * @param t
	 */
	protected void impOperBean(T t) {
	}

	protected void impException(Exception e, T t) {
	}

	/**
	 * 获取pageSize
	 *	@return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置pageSize
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
