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
import cn.harry12800.tools.StringUtils;
 
@SuppressWarnings("serial")
public class AddChildPanel<T> extends JPanel {
	
	LinkedHashMap<String, JLabel> labelMap = new LinkedHashMap<String, JLabel>();
	LinkedHashMap<String, JTextField> textFieldMap = new LinkedHashMap<String, JTextField>();
	/**
	 * 注解列表（Object[]{ ExcelField, Field/Method }）
	 */
	List<Object[]> annotationList = new ArrayList<>();
	private JLabel warnLabel =  new JLabel("");
	
	private LabelButton btnSave = null;
	private LabelButton btnCancel = null;
	private OperDialog<?> operDialog;
	private DisplayPanel<T> context;
	public AddChildPanel(DisplayPanel<T> context,OperDialog<?> operDialog) {
		this.operDialog  =operDialog;
		this.context =context;
		this.annotationList=context.getAnnotationList();
		this.setLayout(new GridBagLayout());
		setPreferredSize( new Dimension(600, 550)  );
		setOpaque(false);
		initButton();
		initComponent();
	}

	private void initButton() {
		warnLabel.setForeground(Color.RED);
		btnSave =  new LabelButton("保存",100,25);
		btnCancel =  new LabelButton("取消",100,25);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(btnCancel.contains(e.getPoint())){
					operDialog.dispose();
				}
			}
		});
		 
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(btnSave.contains(e.getPoint())){
				T collectVal = null;
				try {
					collectVal = collectVal();
				} catch (Exception e1) {
					warnLabel.setText(e1.getMessage());
					return ;
				}
				try {
					context.validateData(collectVal);
				} catch (Exception e1) {
					warnLabel.setText(e1.getMessage());
					return ;
				}
				int mark = context.saveData(collectVal);
				if(mark==1) {
						warnLabel.setText("添加成功");
						context.refresh();
				}
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						operDialog.dispose();
					}
				}, 1000);
				}
			}
		});
	}
	public boolean saveData(T t){
		return false;
		
	}
	private void initComponent() {
		for (Object[] objects : annotationList) {
			DbField ef= ((DbField)objects[0]);
			Field f= (( Field)objects[1]);
			if(!ef.isKey()&&ef.canAdd()){
				JLabel jLabel = new JLabel(ef.title()+":");
				jLabel.setForeground(UI.fontColor);
				labelMap.put(f.getName() ,jLabel );
				JTextField jTextField = new InputText(50);
				Border createEmptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
				jTextField.setBorder(createEmptyBorder);
				jTextField.setForeground(UI.fontColor);
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
		add(btnSave, new GBC(0, i++, GBC.WEST, new Insets(1, 1, 1, 1), 1, 1));
		i=0;
		for (   Entry<String, JTextField>  field: textFieldMap.entrySet() ) {
			add(field.getValue(), new GBC(1, i++, GBC.WEST, new Insets(1, 1, 1, 1), 2, 1));
		}
		add(btnCancel, new GBC(2, i++, GBC.EAST, new Insets(1, 1, 1, 1), 2, 1));
		add(warnLabel, new GBC(1, i++, GBC.CENTER, new Insets(1, 1, 1, 1),1, 1));
	}
	public T collectVal() throws Exception {
		T newInstance = null;
		try {
			newInstance = (T) context.getBeanClazz().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Object[] objects : annotationList) {
			DbField f= ((DbField)objects[0]);
			Field ef= ((Field)objects[1]);
			if(f.isKey()){
				if(ef.getType() == Integer.class){
					 
					continue;
				}
				Reflections.invokeSetter(newInstance,ef.getName(),StringUtils.getUUID());
				continue;
			}
			if(f.isCreateTime()){
				Reflections.invokeSetter(newInstance,ef.getName(),new Date());
				continue;
			}
			if(textFieldMap.get(ef.getName())==null)continue;
			String trim =  textFieldMap.get(ef.getName()).getText().trim();
			if(ef.getType() ==  String.class){
				Reflections.invokeSetter(newInstance,ef.getName(),trim);
			}
			else if(ef.getType() ==  Integer.class){
				if("".equals(trim)){
					trim="0";
				}
				Integer valueOf = Integer.valueOf(trim);
				Reflections.invokeSetter(newInstance,ef.getName(),valueOf);
			}else if(ef.getType() ==  Date.class){
				if("".equals(trim)) {
					Reflections.invokeSetter(newInstance,ef.getName(),null);
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
					Reflections.invokeSetter(newInstance,ef.getName(),date);
				}
			} else {
				System.out.println(ef.getType());
				Integer.valueOf("a");
			}
		}
		return newInstance;
	}
}
