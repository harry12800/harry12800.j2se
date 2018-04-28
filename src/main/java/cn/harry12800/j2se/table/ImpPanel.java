package cn.harry12800.j2se.table;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.newtec.tree2word.excel.ExcelReader;

import cn.harry12800.j2se.component.Progressar;
import cn.harry12800.j2se.component.Progressar.Type;
import cn.harry12800.j2se.component.utils.GBC;
import cn.harry12800.tools.DbField;
import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.StringUtils;


@SuppressWarnings("serial")
public class ImpPanel<T> extends JPanel {

	JLabel fileLabel = new JLabel("请选择文件");
	JButton impBtn = new JButton("选择导入文件");
	JButton ensureBtn = new JButton("确定");
	String path = "";
	private DisplayPanel<T> context;
	private Dialog dialog;
	private static Progressar readExcelProgressbar =null;
	private static Progressar writeExcelProgressbar =null;
	public ImpPanel( DisplayPanel<T> context,OperDialog<T> dialog,String name,String path) {
		this.context= context;
		this.path= path;
		this.dialog = dialog;
		context.setImpDialog(dialog);
		setLayout(new GridBagLayout());
		setBorder(new EmptyBorder(1,1,1,1));
		setOpaque(false);
		setBorder( BorderFactory.createTitledBorder("Excel/CSV文件导入订单")  );
		readExcelProgressbar = new Progressar("读取进度：", Type.percent);
		writeExcelProgressbar = new Progressar("保存进度：", Type.percent);
		//progressbar.setPreferredSize(new Dimension(495, 20));
		readExcelProgressbar.setBackground(Color.pink);
		writeExcelProgressbar.setBackground(Color.pink);
		add(readExcelProgressbar, new GBC(0, 0, GBC.EAST, new Insets(1, 1, 1, 1), 5, 1));
		add(writeExcelProgressbar, new GBC(0, 1, GBC.EAST, new Insets(1, 1, 1, 1), 5, 1));
		add(fileLabel, new GBC(0, 2, GBC.EAST, new Insets(1, 1, 1, 1), 2, 1));
		add(impBtn, new GBC(3, 2, GBC.EAST, new Insets(1, 1, 1, 1), 1, 1));
		add(ensureBtn, new GBC(4, 2, GBC.EAST, new Insets(1, 1, 1, 1), 1, 1));
		fileLabel.setText(name);
		processThread();
	}
	 
	private void processThread() {
        Thread thread = new Thread() {
            public void run() {
            	try {
            		ensureBtn.setVisible(false);
            		if(path.endsWith("csv")){
            			readCSV();
            		}else {
            			realExcel();
					}
            		fileLabel.setText("导入完成！");
            		context.refresh();
            		
				} catch (Exception e) {
					e.printStackTrace();
					fileLabel.setText(e.getMessage());
//					NotifyWindow.error(e.getMessage());
				} finally{
					path=null;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fileLabel.setText("请选择导入文件");
					dialog.dispose();
					dialog= null;
            		context.setImpDialog(null);
				}
            }
        };
        thread.start();
    }
	private void readCSV() throws Exception{
		List<String> title = readCSVTitle(path);
		IndexTemplate checkTitle = checkTitle(title);
		Map<Integer, List<String>> map = readCSVContent(path);
		importOrder(map,checkTitle);
	}
	private void importOrder(Map<Integer, List<String>> map,
			IndexTemplate checkTitle) {
		int i = 0;
		List<T> list=new ArrayList<T>();
		for (Entry<Integer, List<String>> entry : map.entrySet()) {
			i++;
			List<String> data = entry.getValue();
			readExcelProgressbar.setVal((int) (i*100.0/map.size()));
			/**
			 * StringUtils.printList("" + entry.getKey(), data);
			 */
			if (null == data || data.isEmpty())
				continue;
			T bean = null;
			try {
				bean = context.getBeanClazz().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (Object[] objects : context.getAnnotationList()) {
				DbField f= ((DbField)objects[0]);
				Field ef= ((Field)objects[1]);
				if(f.isKey()){
					Reflections.invokeSetter(bean,ef.getName(),StringUtils.getUUID());
					continue;
				}
				if(f.isCreateTime()){
					Reflections.invokeSetter(bean,ef.getName(),new Date());
					continue;
				}
				Integer integer = IndexTemplate.map.get(ef.getName());
				if(integer==null||integer<0||integer>=data.size()){
					continue;
				}
				String cellData = data.get( IndexTemplate.map.get(ef.getName()));
				Object obj = cellData;
				if(ef.getType() == Integer.class) {
					obj = Integer.valueOf(cellData);
				}
				Reflections.invokeSetter(bean, ef.getName(),obj);
			}
			list.add(bean);
		}
		fileLabel.setText("读取文件完成，等待保存至数据库……");
		i=0;
		context.impBegin();
		for (T t : list) {
			context.impOperBean(t);
			try {
				context.saveData(t);
			} catch (Exception e) {
				context.impException(e,t);
				break;
			}
			i++;
			writeExcelProgressbar.setVal((int) (i*100.0/list.size()));
		}
		context.impEnd(i);
	}
	static class IndexTemplate{
		static final Map<String, Integer> map = new HashMap<String, Integer>();
	}
	private IndexTemplate checkTitle(List<String> title) throws Exception{
		IndexTemplate indexTemplate = new IndexTemplate();
		for (Object[] objects : context.getAnnotationList()) {
			DbField f= ((DbField)objects[0]);
			Field ef= ((Field)objects[1]);
			if(f.imp()){
				int find = find(title,f.value());
				if(find<0) throw new Exception("导入文件中未发现（"+f.value()+"）列");
				IndexTemplate.map.put(ef.getName(), find);
			}else {
				int find = find(title,f.value());
				if(find>0)
				IndexTemplate.map.put(ef.getName(), find);
			}
		}
		return indexTemplate;
	}
	protected void realExcel() throws Exception {
		String[] title= ExcelReader.readExcelTitle(path);
		List<String> asList = Arrays.asList(title);
		IndexTemplate checkTitle = checkTitle(asList);
		Map<Integer, List<String>> map = ExcelReader.readExcelContent(path);
		importOrder(map,checkTitle);
	}
	public static List<String>  csvSplit(String rowContent) {
        Pattern p= Pattern.compile("\"(.*?)\"");
        Matcher m=p.matcher(rowContent);
        List<String> list = new ArrayList<String>();
        while (m.find()){
        	list.add(m.group(1).trim());
        }
        return list;
    }
	private Map<Integer, List<String>> readCSVContent(String path2) {
		Map<Integer, List<String>> map= new LinkedHashMap<>();
		List<String> rows = FileUtils.getRowByFile(new File(path));
		for (int i = 0; i < rows.size(); i++) {
			if(i==0)continue;
			List<String> value = csvSplit(rows.get(i));
			map.put(i, value );
		}
		return map;
	}

	private List<String> readCSVTitle(String path) {
		StringBuilder row = FileUtils.getRowByFile(new File(path),0,1);
		return  csvSplit(row.toString());
	}

	public JLabel getFileLabel() {
		return fileLabel;
	}

	public void setFileLabel(JLabel fileLabel) {
		this.fileLabel = fileLabel;
	}

	public JButton getImpBtn() {
		return impBtn;
	}

	public void setImpBtn(JButton impBtn) {
		this.impBtn = impBtn;
	}

	public JButton getEnsureBtn() {
		return ensureBtn;
	}

	public void setEnsureBtn(JButton ensureBtn) {
		this.ensureBtn = ensureBtn;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private int find(List<String> titles, String titleName) {
		return titles.indexOf(titleName.trim());
	}
}
