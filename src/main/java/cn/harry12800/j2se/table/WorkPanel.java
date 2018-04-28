package cn.harry12800.j2se.table;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.j2se.component.btn.LabelButton;
import cn.harry12800.j2se.component.utils.GBC;
import cn.harry12800.tools.DbField;

/**
 * 操作面板
 */
@SuppressWarnings("serial")
public class WorkPanel<T> extends JPanel {
	public JLabel infoLabel=new JLabel();
	public LabelButton btnAdd;
	public LabelButton btnDelete;
	public LabelButton btnEdit;
	public LabelButton btnRefresh;
	public LabelButton btnSearch;
	public LabelButton btnImp;
	public LabelButton btnExp;
	public LabelButton btnBack;
	private DisplayPanel<T> context;

	public WorkPanel(DisplayPanel<T> displayPanel) {
		this.context = displayPanel;
		initButton();
		initPane();
	}
	private void initPane() {
		int index = 0;
		setOpaque(false);
		setLayout(new GridBagLayout());
		add(infoLabel, new GBC(0, index++, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
		if(btnAdd!=null)
		add(btnAdd, new GBC(0, index++, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
		if(btnEdit!=null)
		add(btnEdit, new GBC(0, index++, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
		if(btnDelete!=null)
		add(btnDelete, new GBC(0, index++, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
		if(btnRefresh!=null)
		add(btnRefresh, new GBC(0,index++, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
		if(btnSearch!=null)
		add(btnSearch, new GBC(0,index++, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
		if(btnImp!=null)
		add(btnImp, new GBC(0,index++, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
		if(btnExp!=null)
		add(btnExp, new GBC(0,index++, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
			
		List<Component> compList = context.getWorkComp();
		if(compList  !=null)
		for(int i =0 ;i < compList.size() ; i++) {
			add(compList.get(i), new GBC(0,index++, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1)); 
		}
		add(btnBack, new GBC(0,index++, GBC.WEST, new Insets(5, 5, 5, 5), 1, 1));
	}

	public void initButton() {
		boolean isCanAdd = false;
		boolean isCanEdit = false;
		boolean isCanSearch = false;
		boolean isCanImp = false;
		boolean isCanExp = false;
		for (Object[] objects : context.annotationList) {
			DbField ef= ((DbField)objects[0]);
			if(!ef.isKey()&&ef.canAdd()){
				 isCanAdd =  true;
			}
			if(!ef.isKey()&&ef.canEdit()){
				 isCanEdit = true;
			}
			if(!ef.isKey()&&ef.canSearch()){
				 isCanSearch = true;
			}
			if(!ef.isKey()&&ef.imp()){
				 isCanImp = true;
			}
			if(!ef.isKey()&&ef.exp()){
				 isCanExp = true;
			}
		}
		if(context.meta.isCanAdd&&isCanAdd)
		{
			btnAdd = new LabelButton("增加",100,32);
			btnAdd.setToolTipText(context.meta.addHint);
			btnAddListener();
		}
		btnRefresh =  new LabelButton("刷新",100,32);
		btnRefreshListener();
		if(context.meta.isCanDel)
		{
			btnDelete = new LabelButton("删除",100,32);
			btnDelete.setToolTipText(context.meta.deleteHint);
			btnDeleteListener();
		}
		if(context.meta.isCanEdit&&isCanEdit){
			btnEdit =  new LabelButton("编辑" ,100,32); 
			btnEdit.setToolTipText(context.meta.editHint);
			btnEditListener();
		}
		if(context.meta.isCanSearch&&isCanSearch){
			btnSearch =  new LabelButton("搜索",100,32); 
			btnSearch.setToolTipText(context.meta.searchHint);
			btnSearchListener();
		}
		if(context.meta.isCanImp&&isCanImp){
			btnImp =  new LabelButton("导入",100,32); 
			btnImp.setToolTipText(context.meta.impHint);
			btnImpListener();
		}
		if(context.meta.isCanExp&&isCanExp){
			btnExp =  new LabelButton("导出",100,32); 
			btnExp.setToolTipText(context.meta.expHint);
			btnExpListener();
		}
		btnBack = new LabelButton("分页",100,32); 
		btnBack.setToolTipText("分页");
		btnBackListener();
	}
	private void btnBackListener() {
		btnBack.addMouseListener(new MouseAdapter() {
			 @Override
			public void mouseReleased(MouseEvent e) {
				if(btnBack.contains(e.getPoint())){
					context.setPage(!context.isPage);
				}
			}
		});
	}

	private void btnExpListener() {
		btnExp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(btnExp.contains(e.getPoint())){
					context.excelExp();
				}
			}
		});
	}

	private void btnImpListener() {
		btnImp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(btnImp.contains(e.getPoint())){
					context.excelImp();
				}
			}
		});
	}

	private void btnRefreshListener() {
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(btnRefresh.contains(e.getPoint())){
					context.getTable().refreshLatest();
				}
			}
		});
	}

	private void btnSearchListener() {
		btnSearch.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(btnSearch.contains(e.getPoint())){
					context.showSearchDialog();
				}
			}
		});
	}

	private void btnEditListener() {
		btnEdit.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(btnEdit.contains(e.getPoint())){
					context.showEditDialog();
				}
			}
		});
	}

	private void btnDeleteListener() {
		btnDelete.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(btnDelete.contains(e.getPoint())){
					context.deleteData();
				}
			}
		});
	}

	private void btnAddListener() {
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(btnAdd.contains(e.getPoint())){
					System.out.println(e.getPoint());
					System.out.println(e.getX());
					System.out.println(e.getY());
					context.showAddDialog();
				}
			}
		});
	}

	public JLabel getInfoLabel() {
		return infoLabel;
	}
}
