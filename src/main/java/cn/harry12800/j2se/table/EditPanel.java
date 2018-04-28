package cn.harry12800.j2se.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import cn.harry12800.j2se.component.InputText;
import cn.harry12800.j2se.component.btn.LabelButton;
import cn.harry12800.j2se.component.utils.GBC;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.tools.DbField;

@SuppressWarnings("serial")
public class EditPanel<T> extends JPanel {

	private OperDialog<T> operDialog;
	private JLabel warnLabel = new JLabel("");
	/***
	 * "序号", "编码","旧编码","开单日期","订单日期", "描述","数量","尺寸","材质","非零号",
	 * "刀模号","颜色","备注","导入日期","最后修改日期"};
	 */
	private LabelButton btnUpdate = null;
	private LabelButton btnCancel = null;
	private DisplayPanel<T> context;
	
	LinkedHashMap<String, JLabel> labelMap = new LinkedHashMap<String, JLabel>();
	LinkedHashMap<String, JTextField> textFieldMap = new LinkedHashMap<String, JTextField>();
	/**
	 * 注解列表（Object[]{ ExcelField, Field/Method }）
	 */
	List<Object[]> annotationList = new ArrayList<>();
	Object bean;
	public EditPanel(OperDialog<T> operDialog,
			DisplayPanel<T> context, Object bean) {
		this.operDialog = operDialog;
		this.context =  context;
		this.bean = bean;
		annotationList = context.getAnnotationList();
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(context.meta.editPanelMeta.width, context.meta.editPanelMeta.height));
		setOpaque(false);
		initButton();
		initComponent();
		initContent(bean);
		//setBorder( BorderFactory.createTitledBorder(context.meta.editPanelMeta.showTitle)  );
	}
	
	private void initComponent( ) {
		for (Object[] objects : annotationList) {
			DbField ef= ((DbField)objects[0]);
			Field f= (( Field)objects[1]);
			if(!ef.isKey()&&ef.canEdit()){
				 JLabel jLabel = new JLabel(ef.title()+":");
				 jLabel.setForeground(UI.fontColor);
				labelMap.put(f.getName() ,jLabel);
				JTextField jTextField = new InputText(50);
//				Border createLineBorder = BorderFactory.createLineBorder(UI.borderColor,0,true);
				Border createEmptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
				jTextField.setBorder(createEmptyBorder);
				jTextField.setForeground(UI.fontColor);
				jTextField.setAlignmentX(50);
				
				String hint = ef.hint();
				if(!"".equals(hint))
				jTextField.setToolTipText(hint);
				textFieldMap.put(f.getName() ,jTextField );
			}
		}
		int i=0;
		for (   Entry<String, JLabel>  field: labelMap.entrySet() ) {
			add(field.getValue(), new GBC(0, i++, GBC.EAST, new Insets(1, 1, 1, 1), 1, 1));
		}
		add(btnUpdate, new GBC(0, i++, GBC.WEST, new Insets(1, 1, 1, 1), 1, 1));
		
		i=0;
		for (   Entry<String, JTextField>  field: textFieldMap.entrySet() ) {
			add(field.getValue(), new GBC(1, i++, GBC.WEST, new Insets(1, 1, 1, 1), 2, 1));
		}
		add(btnCancel, new GBC(1, i++, GBC.EAST, new Insets(1, 1, 1, 1), 2, 1));
		add(warnLabel, new GBC(0, i++, GBC.CENTER, new Insets(1, 1, 1, 1),3, 1));
	}
	private void initButton( ) {
		warnLabel.setForeground(Color.RED);
		btnUpdate =  new LabelButton("保存",100,25);
		btnCancel =  new LabelButton("取消",100,25);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(btnCancel.contains(e.getPoint())){
				operDialog.dispose();}
			}
		});
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(btnUpdate.contains(e.getPoint())){
				T collectVal = null;
				try {
					 collectVal = collectVal();
				} catch (Exception e1) {
					warnLabel.setText(e1.getMessage());
				}
				try {
					context.validateData(collectVal);
				} catch (Exception e1) {
					warnLabel.setText(e1.getMessage());
					return ;
				}
				int mark = context.updateData(collectVal);
				if(mark==1){
					warnLabel.setText("修改成功");
					context.getTable().reload(bean);
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							operDialog.dispose();
						}
					}, 500);
				}
				}
			}
		});
	}
	@SuppressWarnings("unchecked")
	public T collectVal() throws Exception {
		 
		for (Object[] objects : annotationList) {
			DbField f= ((DbField)objects[0]);
			Field ef= ((Field)objects[1]);
			if(f.isKey()){
				//Reflections.invokeSetter(bean,ef.getName(),StringUtils.getUUID());
				continue;
			}
			if(f.isMoidfyTime()){
				Reflections.invokeSetter(bean,ef.getName(),new Date());
				continue;
			}
			if(textFieldMap.get(ef.getName())==null)continue;
			String trim =  textFieldMap.get(ef.getName()).getText().trim();
			if(ef.getType() ==  String.class){
				Reflections.invokeSetter(bean,ef.getName(),trim);
			}
			else if(ef.getType() ==  Integer.class){
				if("".equals(trim)){
					trim="0";
				}
				Integer valueOf = Integer.valueOf(trim);
				Reflections.invokeSetter(bean,ef.getName(),valueOf);
			}else if(ef.getType() ==  Date.class){
				if("".equals(trim)) {
					Reflections.invokeSetter(bean,ef.getName(),null);
				}
				else{
					String replaceAll = trim.replaceAll(" ", "");
					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = dateFormat1.parse(replaceAll);
					} catch (ParseException e) {
						warnLabel.setText("时间类型数据异常，格式(yyyy-MM-dd)");
//						operDialog.showException(e);
//						context.showException(e);
						throw new Exception("时间类型数据异常，格式(yyyy-MM-dd)");
					}
					Reflections.invokeSetter(bean,ef.getName(),date);
				}
			} else {
				System.out.println(ef.getType());
				Integer.valueOf("a");
			}
			
		}
		return (T) bean;
	}
	private void initContent(Object id) {
		for (Object[] field : annotationList) {
			Field ef= ((Field)field[1]);
			Object invokeGetter = Reflections.invokeGetter(id,ef.getName());
			JTextField next =textFieldMap.get(ef.getName());
			if(invokeGetter!=null&&next!=null)
				 next.setText(invokeGetter.toString());
		}
	}
}
