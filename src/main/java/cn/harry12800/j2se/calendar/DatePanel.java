package cn.harry12800.j2se.calendar;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import cn.harry12800.j2se.style.UI;
import cn.harry12800.j2se.utils.Clip;
import cn.harry12800.tools.Lists;
import cn.harry12800.tools.Maps;

/**
 * @Author harry12800
 * @QQ 804151219 日期选择器，可以指定日期的显示格式
 */
public class DatePanel extends JPanel {

	private static final long serialVersionUID = 4529266044762990227L;

	private Date initDate;
	private Calendar now = Calendar.getInstance();
	private Calendar select;
	private JP1 jp1;// 四块面板,组成
	private JP2 jp2;
	private JP3 jp3;
	private JP4 jp4;
	String value = "单击选择日期";
	boolean hover = false;
	private Font font = new Font("宋体", Font.PLAIN, 12);
	private final LabelManager lm = new LabelManager();
	private SimpleDateFormat sdf;
	private DateActionListener dateListener;
	private JComponent showDate;
	static Map<String, String> holidayMap = Maps.newHashMap();

	static class Holiday {
		public String showText = "";
		public boolean isLunar;
		public int year;
		public int month;
		public int day;
		public Color color;

		public Holiday(String showText, boolean isLunar, int year, int month,
				int day, Color color) {
			super();
			this.showText = showText;
			this.isLunar = isLunar;
			this.year = year;
			this.month = month;
			this.day = day;
			this.color = color;
		}

		public boolean isDay(MyLabel myb) {
			if (isLunar) {
				if (myb.lunar.year == year && myb.lunar.month == month
						&& myb.lunar.day == day)
					return true;
			} else {
				if (myb.year == year && (myb.month + 1) == month
						&& myb.day == day)
					return true;
			}
			return false;
		}
	}

	private static List<Holiday> holidays = Lists.newArrayList();
	static {
		holidays.add(new Holiday("休", true, 2017, 7, 29, UI.GREEN(150)));
		holidays.add(new Holiday("班", false, 2017, 9, 30, UI.RED(150)));
		holidays.add(new Holiday("休", false, 2017, 10, 1, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2017, 10, 2, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2017, 10, 3, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2017, 10, 4, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2017, 10, 5, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2017, 10, 6, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2017, 10, 7, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2017, 10, 8, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2018, 1, 1, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2018, 2, 15, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2018, 2, 16, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2018, 2, 17, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2018, 2, 18, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2018, 2, 19, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2018, 2, 20, UI.GREEN(150)));
		holidays.add(new Holiday("休", false, 2018, 2, 21, UI.GREEN(150)));
	}

	public static DatePanel getInstance() {
		return new DatePanel();
	}

	public static DatePanel getInstance(Date date) {
		return new DatePanel(date);
	}

	public static DatePanel getInstance(String format) {
		return new DatePanel(format);
	}

	public static DatePanel getInstance(Date date, String format) {
		return new DatePanel(date, format);
	}

	/**
	 * Creates a new instance of DateChooser
	 */
	private DatePanel() {
		this(new Date());
	}

	private DatePanel(Date date) {
		this(date, "yyyy年MM月dd日");
	}

	private DatePanel(String format) {
		this(new Date(), format);
	}

	private DatePanel(Date date, String format) {
		initDate = date;
		setFont(UI.normalFont(14));
		sdf = new SimpleDateFormat(format);
		select = Calendar.getInstance();
		select.setTime(initDate);
		setSize(new Dimension(100, 32));
		setPreferredSize(new Dimension(100, 32));
		initPanel();
	}

	public void addDateActionListener(DateActionListener dateListener) {
		this.dateListener = dateListener;
	}

	/**
	 * 是否允许用户选择
	 */
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		showDate.setEnabled(b);
	}

	/**
	 * 得到当前选择框的日期
	 */
	public Date getDate() {
		return select.getTime();
	}

	public String getStrDate() {
		return sdf.format(select.getTime());
	}

	public String getStrDate(String format) {
		sdf = new SimpleDateFormat(format);
		return sdf.format(select.getTime());
	}

	// 根据初始化的日期,初始化面板
	private void initPanel() {
		setLayout(new BorderLayout());
		setOpaque(false);
		JPanel up = new JPanel(new BorderLayout());
		up.setOpaque(false);
		up.add(jp1 = new JP1(), BorderLayout.NORTH);
		up.add(jp2 = new JP2(), BorderLayout.CENTER);
		add(jp3 = new JP3(), BorderLayout.CENTER);
		add(up, BorderLayout.NORTH);
		add(jp4 = new JP4(), BorderLayout.SOUTH);
		setOpaque(false);
		this.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {

			}

			public void ancestorRemoved(AncestorEvent event) {

			}

			// 只要祖先组件一移动,马上就让popup消失
			public void ancestorMoved(AncestorEvent event) {
				// hidePanel();
			}
		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				hover = true;
				getParent().repaint();
				repaint();
				if (DatePanel.this.isEnabled()) {
					DatePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}

			public void mouseExited(MouseEvent me) {
				hover = false;
				getParent().repaint();
				repaint();
				if (DatePanel.this.isEnabled()) {
					DatePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}

			public void mousePressed(MouseEvent me) {
				if (DatePanel.this.isEnabled()) {
				}
			}

			public void mouseReleased(MouseEvent me) {
				if (DatePanel.this.isEnabled()) {
					// DateChooser.this.setForeground(Color.BLACK);
				}
			}
		});
		this.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				// hidePanel();
			}

			public void focusGained(FocusEvent e) {

			}
		});
	}

	// 根据新的日期刷新
	private void refresh() {
		jp1.updateDate();
		jp2.updateDate();
		jp3.updateDate();
		jp4.updateDate();
		SwingUtilities.updateComponentTreeUI(this);
	}

	// 提交日期
	private void commit() {
		value = sdf.format(select.getTime());
		if (dateListener != null)
			dateListener.dateActionClick(select, sdf.format(select.getTime()));
		//		this.getParent().repaint();
		//		repaint();
	}

	/**
	 * 最上面的面板用来显示月份的增减
	 */
	private class JP1 extends JPanel {
		private static final long serialVersionUID = -5638853772805561174L;
		JLabel yearleft, yearright, monthleft, monthright, center,
				centercontainer;

		public JP1() {
			super(new BorderLayout());
			setBackground(UI.foreColor);
			initJP1();
		}

		private void initJP1() {
			yearleft = new JLabel("  <<", JLabel.CENTER);
			yearleft.setToolTipText("上一年");
			yearleft.setOpaque(false);
			yearright = new JLabel(">>  ", JLabel.CENTER);
			yearright.setToolTipText("下一年");
			yearleft.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
			yearright.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

			monthleft = new JLabel("  <", JLabel.RIGHT);
			monthleft.setToolTipText("上一月");
			monthright = new JLabel(">  ", JLabel.LEFT);
			monthright.setForeground(UI.fontColor);
			monthleft.setForeground(UI.fontColor);
			monthright.setToolTipText("下一月");
			monthleft.setBorder(BorderFactory.createEmptyBorder(2, 30, 0, 0));
			monthright.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 30));

			centercontainer = new JLabel("", JLabel.CENTER);
			centercontainer.setLayout(new BorderLayout());
			centercontainer.setOpaque(false);
			center = new JLabel("", JLabel.CENTER);
			center.setFont(UI.微软雅黑Font);
			centercontainer.add(monthleft, BorderLayout.WEST);
			centercontainer.add(center, BorderLayout.CENTER);
			centercontainer.add(monthright, BorderLayout.EAST);

			this.add(yearleft, BorderLayout.WEST);
			this.add(centercontainer, BorderLayout.CENTER);
			this.add(yearright, BorderLayout.EAST);
			this.setPreferredSize(new Dimension(295, 25));

			updateDate();

			yearleft.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent me) {
					yearleft.setCursor(new Cursor(Cursor.HAND_CURSOR));
					// yearleft.setForeground(Color.RED);
				}

				public void mouseExited(MouseEvent me) {
					yearleft.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					// yearleft.setForeground(Color.BLACK);
				}

				public void mousePressed(MouseEvent me) {
					select.add(Calendar.YEAR, -1);
					// yearleft.setForeground(Color.WHITE);
					refresh();
				}

				public void mouseReleased(MouseEvent me) {
					yearleft.setForeground(UI.fontColor);
				}
			});
			yearright.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent me) {
					yearright.setCursor(new Cursor(Cursor.HAND_CURSOR));
					yearright.setForeground(Color.RED);
				}

				public void mouseExited(MouseEvent me) {
					yearright.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					yearright.setForeground(UI.fontColor);
				}

				public void mousePressed(MouseEvent me) {
					select.add(Calendar.YEAR, 1);
					yearright.setForeground(Color.WHITE);
					refresh();
				}

				public void mouseReleased(MouseEvent me) {
					yearright.setForeground(UI.fontColor);
				}
			});
			monthleft.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent me) {
					monthleft.setCursor(new Cursor(Cursor.HAND_CURSOR));
					monthleft.setForeground(Color.RED);
				}

				public void mouseExited(MouseEvent me) {
					monthleft.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					monthleft.setForeground(UI.fontColor);
				}

				public void mousePressed(MouseEvent me) {
					select.add(Calendar.MONTH, -1);
					monthleft.setForeground(Color.WHITE);
					refresh();
				}

				public void mouseReleased(MouseEvent me) {
					monthleft.setForeground(UI.fontColor);
				}
			});
			monthright.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent me) {
					monthright.setCursor(new Cursor(Cursor.HAND_CURSOR));
					monthright.setForeground(Color.RED);
				}

				public void mouseExited(MouseEvent me) {
					monthright.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					monthright.setForeground(UI.fontColor);
				}

				public void mousePressed(MouseEvent me) {
					select.add(Calendar.MONTH, 1);
					monthright.setForeground(Color.WHITE);
					refresh();
				}

				public void mouseReleased(MouseEvent me) {
					monthright.setForeground(UI.fontColor);
				}
			});
		}

		private void updateDate() {
			center.setText(select.get(Calendar.YEAR) + "年"
					+ (select.get(Calendar.MONTH) + 1) + "月");
		}
	}

	private class JP2 extends JPanel {
		private static final long serialVersionUID = -8176264838786175724L;

		public JP2() {
			super(new GridLayout(1, 7));
			setBackground(UI.foreColor);
			setBorder(new EmptyBorder(0, 0, 5, 0));
			// this.setPreferredSize(new Dimension(295, 20));
			String string = "日一二三四五六";
			int length = "日一二三四五六".length();
			for (int i = 0; i < length; i++) {
				JLabel jLabel = new JLabel(string.charAt(i) + "", JLabel.CENTER);
				jLabel.setFont(UI.微软雅黑Font);
				jLabel.setOpaque(false);
				add(jLabel);
			}
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setFont(font);
			g.setColor(UI.fontColor);
			g.drawLine(5, getHeight() - 2, getWidth() - 5, getHeight() - 2);
		}

		private void updateDate() {

		}
	}

	private class JP3 extends JPanel {
		private static final long serialVersionUID = 43157272447522985L;

		public JP3() {
			super(new GridLayout(6, 7));
			this.setPreferredSize(new Dimension(395, 200));
			initJP3();
		}

		private void initJP3() {
			updateDate();
		}

		public void updateDate() {
			this.removeAll();
			lm.clear();
			Date temp = select.getTime();
			Calendar select = Calendar.getInstance();
			select.setTime(temp);
			select.set(Calendar.DAY_OF_MONTH, 1);
			int index = select.get(Calendar.DAY_OF_WEEK);
			int sum = (index == 1 ? 8 : index);
			select.add(Calendar.DAY_OF_MONTH, 0 - sum);
			for (int i = 0; i < 42; i++) {
				select.add(Calendar.DAY_OF_MONTH, 1);
				int x = select.get(Calendar.YEAR);
				int y = select.get(Calendar.MONTH);
				int z = select.get(Calendar.DAY_OF_MONTH);
				MyLabel myLabel = new MyLabel(x, y, z);
				MyLabelContainer myLabelContainer = new MyLabelContainer(myLabel);
				lm.addLabel(myLabelContainer);
			}
			for (MyLabelContainer my : lm.getLabels()) {
				this.add(my);
			}
			select.setTime(temp);
		}
	}

	private class MyLabelContainer extends JPanel implements Comparator<MyLabel>,
			MouseListener, MouseMotionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private MyLabel myb;
		private int holiday;
		private int year, month, day;
		private boolean isSelected;

		// private Holiday holiday;
		public MyLabelContainer(MyLabel myb) {
			this.myb = myb;
			this.year = myb.year;
			this.month = myb.month;
			this.day = myb.day;
			setLayout(new BorderLayout());
			setBorder(new EmptyBorder(5, 0, 5, 0));
			setOpaque(false);
			add(this.myb);
			this.holiday = HolidayHelper.getHoliday(this);
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}

		@Override
		protected void paintComponent(Graphics g) {
			if (holiday <= 0) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(UI.foreColor);
				g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
				g2d.setColor(UI.fontColor);
				g2d.setFont(UI.微软雅黑Font);
				g2d.drawString("", 2, 15);
			} else if (holiday == 1) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(UI.GREEN(150));
				g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
				g2d.setColor(UI.fontColor);
				g2d.setFont(UI.微软雅黑Font);
				g2d.drawString("休", 2, 15);
			} else if (holiday == 2) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(UI.GREEN(125));
				g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
				g2d.setColor(UI.fontColor);
				g2d.setFont(UI.微软雅黑Font);
				g2d.drawString("休", 2, 15);
			}
			if (isSelected) {// 如果被选中了就画出一个虚线框出来
				Stroke s = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
						BasicStroke.JOIN_BEVEL, 1.0f,
						new float[] { 2.0f, 2.0f }, 1.0f);
				Graphics2D gd = (Graphics2D) g;
				gd.setStroke(s);
				gd.setColor(Color.BLACK);
				Polygon p = new Polygon();
				p.addPoint(0, 0);
				p.addPoint(getWidth() - 1, 0);
				p.addPoint(getWidth() - 1, getHeight() - 1);
				p.addPoint(0, getHeight() - 1);
				gd.drawPolygon(p);
			}
			super.paintComponent(g);
		}

		public boolean getIsSelected() {
			return isSelected;
		}

		public void setSelected(boolean b, boolean isDrag) {
			isSelected = b;
			if (b && !isDrag) {
				int temp = select.get(Calendar.MONTH);
				select.set(year, month, day);
				if (temp == month) {
					SwingUtilities.updateComponentTreeUI(jp3);
				} else {
					refresh();
				}
			}
			this.repaint();
		}

		public boolean contains(Point p) {
			return this.getBounds().contains(p);
		}

		private void update() {
			repaint();
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			update();
		}

		public void mouseReleased(MouseEvent e) {
			Point p = SwingUtilities.convertPoint(this, e.getPoint(), jp3);
			lm.setSelect(p, false);
			commit();
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseDragged(MouseEvent e) {
			Point p = SwingUtilities.convertPoint(this, e.getPoint(), jp3);
			lm.setSelect(p, true);
		}

		public void mouseMoved(MouseEvent e) {
		}

		public int compare(MyLabel o1, MyLabel o2) {
			Calendar c1 = Calendar.getInstance();
			c1.set(o1.year, o2.month, o1.day);
			Calendar c2 = Calendar.getInstance();
			c2.set(o2.year, o2.month, o2.day);
			return c1.compareTo(c2);
		}
	}

	static class HolidayHelper {

		public static int getHoliday(MyLabelContainer myb) {
			String format = String.format("%d%02d%02d", myb.year,
					myb.month + 1, myb.day);
			String string = holidayMap.get(format);
			if (string == null)
				return -1;
			return Integer.valueOf(string);
		}

	}

	static SimpleDateFormat chineseDateFormat = new SimpleDateFormat(
			"yyyy年MM月dd日");

	private class MyLabel extends JPanel {
		private static final long serialVersionUID = 3668734399227577214L;

		JLabel aJLabel = new JLabel();
		JLabel bJLabel = new JLabel();
		Lunar lunar;
		private int year;
		private int day;
		private int month;

		public MyLabel(int year, int month, int day) {
			super(new GridLayout(2, 1, 0, 0));
			MyLabel.this.setPreferredSize(new Dimension(50, 20));
			MyLabel.this.setSize(50, 20);
			setOpaque(false);
			this.year = year;
			this.day = day;
			this.month = month;
			aJLabel = new JLabel();
			bJLabel = new JLabel();
			aJLabel.setHorizontalAlignment(JLabel.CENTER);
			bJLabel.setHorizontalAlignment(JLabel.CENTER);
			Calendar today = Calendar.getInstance();
			String timeString = year + "年" + (month + 1) + "月" + day + "日";
			try {
				today.setTime(chineseDateFormat.parse(timeString));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			lunar = new Lunar(today);
			add(aJLabel);
			add(bJLabel);
			if (month == select.get(Calendar.MONTH)) {
				bJLabel.setForeground(Color.WHITE);
				aJLabel.setForeground(Color.WHITE);
			} else {
				bJLabel.setForeground(Color.LIGHT_GRAY);
				aJLabel.setForeground(Color.LIGHT_GRAY);
			}
			bJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 10));
			aJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			aJLabel.setText(day + "");
			String festival1 = getOuterFestival(month + 1, day);
			String festival2 = getInnerFestival(lunar.month, lunar.day);
			if (festival1 == null && festival2 == null)
				bJLabel.setText(Lunar.getChinaDayString(MyLabel.this.lunar.day));
			else if (festival1 == null && festival2 != null) {
				bJLabel.setText(festival2);
			} else if (festival1 != null && festival2 == null) {
				bJLabel.setText(festival1);
			} else {
				bJLabel.setText(festival1 + "/" + festival2);
			}
			this.setFont(font);
			if (month == select.get(Calendar.MONTH)) {
				this.setForeground(UI.fontColor);
			} else {
				this.setForeground(UI.voidColor);
			}
			if (day == select.get(Calendar.DAY_OF_MONTH)) {
				this.setBackground(new Color(160, 185, 215));
			} else {
				this.setBackground(Color.WHITE);
			}
		}

		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			if (day == select.get(Calendar.DAY_OF_MONTH)
					&& month == select.get(Calendar.MONTH)) {
				// 如果当前日期是选择日期,则高亮显示
				g2d.setColor(UI.scrollColor);
				g2d.fillRoundRect(20, 0, getWidth() - 40, getHeight(), 3, 3);
			}
			if (year == now.get(Calendar.YEAR)
					&& month == now.get(Calendar.MONTH)
					&& day == now.get(Calendar.DAY_OF_MONTH)) {
				// 如果日期和当前日期一样,则用红框
				g2d.setColor(Color.RED);
				Polygon p = new Polygon();
				p.addPoint(0, 0);
				p.addPoint(getWidth() - 1, 0);
				p.addPoint(getWidth() - 1, getHeight() - 1);
				p.addPoint(0, getHeight() - 1);
				// gd.drawPolygon(p);
				g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
						getWidth() - 1, getHeight() - 1);
			}
		}

	}

	private class LabelManager {
		private List<MyLabelContainer> list;

		public LabelManager() {
			list = new ArrayList<MyLabelContainer>();
		}

		public List<MyLabelContainer> getLabels() {
			return list;
		}

		public void addLabel(MyLabelContainer my) {
			list.add(my);
		}

		public void clear() {
			list.clear();
		}

		@SuppressWarnings("unused")
		public void setSelect(MyLabel my, boolean b) {
			for (MyLabelContainer m : list) {
				if (m.equals(my)) {
					m.setSelected(true, b);
				} else {
					m.setSelected(false, b);
				}
			}
		}

		public void setSelect(Point p, boolean b) {
			// 如果是拖动,则要优化一下,以提高效率
			if (b) {
				// 表示是否能返回,不用比较完所有的标签,能返回的标志就是把上一个标签和
				// 将要显示的标签找到了就可以了
				boolean findPrevious = false, findNext = false;
				for (MyLabelContainer m : list) {
					if (m.contains(p)) {
						findNext = true;
						if (m.getIsSelected()) {
							findPrevious = true;
						} else {
							m.setSelected(true, b);
						}
					} else if (m.getIsSelected()) {
						findPrevious = true;
						m.setSelected(false, b);
					}
					if (findPrevious && findNext) {
						return;
					}
				}
			} else {
				MyLabelContainer temp = null;
				for (MyLabelContainer m : list) {
					if (m.contains(p)) {
						temp = m;
					} else if (m.getIsSelected()) {
						m.setSelected(false, b);
					}
				}
				if (temp != null) {
					temp.setSelected(true, b);
				}
			}
		}

	}

	private class JP4 extends JPanel {
		private static final long serialVersionUID = -6391305687575714469L;

		public JP4() {
			super(new BorderLayout());
			this.setPreferredSize(new Dimension(295, 20));
			// this.setBackground(new Color(160, 185, 215));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			Calendar today = Calendar.getInstance();
			today.setTime(new Date());
			Lunar lunar = new Lunar(today);
			final JLabel jl = new JLabel("今天: " + sdf.format(new Date()) + "（"
					+ lunar + "）");
			jl.setFont(UI.微软雅黑Font);
			jl.setBackground(UI.backColor(0));
			jl.setToolTipText("点击选择今天日期");
			this.add(jl, BorderLayout.CENTER);
			jl.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent me) {
					jl.setCursor(new Cursor(Cursor.HAND_CURSOR));
					jl.setForeground(Color.RED);
					jl.setBackground(Color.GRAY);
				}

				public void mouseExited(MouseEvent me) {
					jl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					jl.setForeground(UI.fontColor);
					jl.setBackground(UI.backColor(0));
				}

				public void mousePressed(MouseEvent me) {
					jl.setForeground(Color.WHITE);
					select.setTime(new Date());
					refresh();
					commit();
				}

				public void mouseReleased(MouseEvent me) {
					// jl.setForeground(Color.BLACK);
				}
			});
		}

		private void updateDate() {

		}
	}

	public static void main(String[] args) {
		Clip.seeCom(new DatePanel());
	}

	private String getInnerFestival(int month2, int day2) {
		if (month2 == 8 && day2 == 15) {
			return "中秋节";
		}
		if (month2 == 1 && day2 == 15) {
			return "元宵节";
		}
		if (month2 == 1 && day2 == 1) {
			return "春节";
		}
		if (month2 == 5 && day2 == 5) {
			return "端午节";
		}
		if (month2 == 7 && day2 == 7) {
			return "情人节";
		}
		if (month2 == 7 && day2 == 15) {
			return "中元节";
		}
		if (month2 == 9 && day2 == 9) {
			return "重阳节";
		}
		if (month2 == 12 && day2 == 8) {
			return "腊八节";
		}
		return null;
	}

	private String getOuterFestival(int month2, int day2) {
		if (month2 == 1 && day2 == 1) {
			return "元旦节";
		}
		if (month2 == 10 && day2 == 1) {
			return "国庆节";
		}
		if (month2 == 2 && day2 == 14) {
			return "情人节";
		}
		if (month2 == 3 && day2 == 8) {
			return "妇女节";
		}
		if (month2 == 3 && day2 == 12) {
			return "植树节";
		}
		if (month2 == 5 && day2 == 1) {
			return "劳动节";
		}
		if (month2 == 5 && day2 == 1) {
			return "青年节";
		}
		if (month2 == 6 && day2 == 1) {
			return "儿童节";
		}
		if (month2 == 7 && day2 == 1) {
			return "建党节";
		}
		if (month2 == 8 && day2 == 1) {
			return "建军节";
		}
		if (month2 == 4 && day2 == 5) {
			return "清明节";
		}
		if (month2 == 9 && day2 == 10) {
			return "教师节";
		}
		if (month2 == 12 && day2 == 24) {
			return "平安夜";
		}
		if (month2 == 12 && day2 == 25) {
			return "圣诞节";
		}
		if (month2 == 10 && day2 == 31) {
			return "万圣节";
		}
		return null;
	}

	public void lastMonth() {
		select.add(Calendar.MONTH, -1);
		refresh();
	}

	public void nextMonth() {
		select.add(Calendar.MONTH, 1);
		refresh();
	}

	public void lastYear() {
		select.add(Calendar.YEAR, -1);
		refresh();
	}

	public void nextYear() {
		select.add(Calendar.YEAR, 1);
		refresh();
	}

	static {
		holidayMap.put("20170101", "2");
		holidayMap.put("20170102", "2");
		holidayMap.put("20170103", "0");
		holidayMap.put("20170104", "0");
		holidayMap.put("20170105", "0");
		holidayMap.put("20170106", "0");
		holidayMap.put("20170107", "1");
		holidayMap.put("20170108", "1");
		holidayMap.put("20170109", "0");
		holidayMap.put("20170110", "0");
		holidayMap.put("20170111", "0");
		holidayMap.put("20170112", "0");
		holidayMap.put("20170113", "0");
		holidayMap.put("20170114", "1");
		holidayMap.put("20170115", "1");
		holidayMap.put("20170116", "0");
		holidayMap.put("20170117", "0");
		holidayMap.put("20170118", "0");
		holidayMap.put("20170119", "0");
		holidayMap.put("20170120", "0");
		holidayMap.put("20170121", "1");
		holidayMap.put("20170122", "0");
		holidayMap.put("20170123", "0");
		holidayMap.put("20170124", "0");
		holidayMap.put("20170125", "0");
		holidayMap.put("20170126", "0");
		holidayMap.put("20170127", "2");
		holidayMap.put("20170128", "2");
		holidayMap.put("20170129", "2");
		holidayMap.put("20170130", "2");
		holidayMap.put("20170131", "2");
		holidayMap.put("20170201", "2");
		holidayMap.put("20170202", "2");
		holidayMap.put("20170203", "0");
		holidayMap.put("20170204", "0");
		holidayMap.put("20170205", "1");
		holidayMap.put("20170206", "0");
		holidayMap.put("20170207", "0");
		holidayMap.put("20170208", "0");
		holidayMap.put("20170209", "0");
		holidayMap.put("20170210", "0");
		holidayMap.put("20170211", "1");
		holidayMap.put("20170212", "1");
		holidayMap.put("20170213", "0");
		holidayMap.put("20170214", "0");
		holidayMap.put("20170215", "0");
		holidayMap.put("20170216", "0");
		holidayMap.put("20170217", "0");
		holidayMap.put("20170218", "1");
		holidayMap.put("20170219", "1");
		holidayMap.put("20170220", "0");
		holidayMap.put("20170221", "0");
		holidayMap.put("20170222", "0");
		holidayMap.put("20170223", "0");
		holidayMap.put("20170224", "0");
		holidayMap.put("20170225", "1");
		holidayMap.put("20170226", "1");
		holidayMap.put("20170227", "0");
		holidayMap.put("20170228", "0");
		holidayMap.put("20170301", "0");
		holidayMap.put("20170302", "0");
		holidayMap.put("20170303", "0");
		holidayMap.put("20170304", "1");
		holidayMap.put("20170305", "1");
		holidayMap.put("20170306", "0");
		holidayMap.put("20170307", "0");
		holidayMap.put("20170308", "0");
		holidayMap.put("20170309", "0");
		holidayMap.put("20170310", "0");
		holidayMap.put("20170311", "1");
		holidayMap.put("20170312", "1");
		holidayMap.put("20170313", "0");
		holidayMap.put("20170314", "0");
		holidayMap.put("20170315", "0");
		holidayMap.put("20170316", "0");
		holidayMap.put("20170317", "0");
		holidayMap.put("20170318", "1");
		holidayMap.put("20170319", "1");
		holidayMap.put("20170320", "0");
		holidayMap.put("20170321", "0");
		holidayMap.put("20170322", "0");
		holidayMap.put("20170323", "0");
		holidayMap.put("20170324", "0");
		holidayMap.put("20170325", "1");
		holidayMap.put("20170326", "1");
		holidayMap.put("20170327", "0");
		holidayMap.put("20170328", "0");
		holidayMap.put("20170329", "0");
		holidayMap.put("20170330", "0");
		holidayMap.put("20170331", "0");
		holidayMap.put("20170401", "0");
		holidayMap.put("20170402", "2");
		holidayMap.put("20170403", "2");
		holidayMap.put("20170404", "2");
		holidayMap.put("20170405", "0");
		holidayMap.put("20170406", "0");
		holidayMap.put("20170407", "0");
		holidayMap.put("20170408", "1");
		holidayMap.put("20170409", "1");
		holidayMap.put("20170410", "0");
		holidayMap.put("20170411", "0");
		holidayMap.put("20170412", "0");
		holidayMap.put("20170413", "0");
		holidayMap.put("20170414", "0");
		holidayMap.put("20170415", "1");
		holidayMap.put("20170416", "1");
		holidayMap.put("20170417", "0");
		holidayMap.put("20170418", "0");
		holidayMap.put("20170419", "0");
		holidayMap.put("20170420", "0");
		holidayMap.put("20170421", "0");
		holidayMap.put("20170422", "1");
		holidayMap.put("20170423", "1");
		holidayMap.put("20170424", "0");
		holidayMap.put("20170425", "0");
		holidayMap.put("20170426", "0");
		holidayMap.put("20170427", "0");
		holidayMap.put("20170428", "0");
		holidayMap.put("20170429", "2");
		holidayMap.put("20170430", "2");
		holidayMap.put("20170501", "2");
		holidayMap.put("20170502", "0");
		holidayMap.put("20170503", "0");
		holidayMap.put("20170504", "0");
		holidayMap.put("20170505", "0");
		holidayMap.put("20170506", "1");
		holidayMap.put("20170507", "1");
		holidayMap.put("20170508", "0");
		holidayMap.put("20170509", "0");
		holidayMap.put("20170510", "0");
		holidayMap.put("20170511", "0");
		holidayMap.put("20170512", "0");
		holidayMap.put("20170513", "1");
		holidayMap.put("20170514", "1");
		holidayMap.put("20170515", "0");
		holidayMap.put("20170516", "0");
		holidayMap.put("20170517", "0");
		holidayMap.put("20170518", "0");
		holidayMap.put("20170519", "0");
		holidayMap.put("20170520", "1");
		holidayMap.put("20170521", "1");
		holidayMap.put("20170522", "0");
		holidayMap.put("20170523", "0");
		holidayMap.put("20170524", "0");
		holidayMap.put("20170525", "0");
		holidayMap.put("20170526", "0");
		holidayMap.put("20170527", "0");
		holidayMap.put("20170528", "2");
		holidayMap.put("20170529", "2");
		holidayMap.put("20170530", "2");
		holidayMap.put("20170531", "0");
		holidayMap.put("20170601", "0");
		holidayMap.put("20170602", "0");
		holidayMap.put("20170603", "1");
		holidayMap.put("20170604", "1");
		holidayMap.put("20170605", "0");
		holidayMap.put("20170606", "0");
		holidayMap.put("20170607", "0");
		holidayMap.put("20170608", "0");
		holidayMap.put("20170609", "0");
		holidayMap.put("20170610", "1");
		holidayMap.put("20170611", "1");
		holidayMap.put("20170612", "0");
		holidayMap.put("20170613", "0");
		holidayMap.put("20170614", "0");
		holidayMap.put("20170615", "0");
		holidayMap.put("20170616", "0");
		holidayMap.put("20170617", "1");
		holidayMap.put("20170618", "1");
		holidayMap.put("20170619", "0");
		holidayMap.put("20170620", "0");
		holidayMap.put("20170621", "0");
		holidayMap.put("20170622", "0");
		holidayMap.put("20170623", "0");
		holidayMap.put("20170624", "1");
		holidayMap.put("20170625", "1");
		holidayMap.put("20170626", "0");
		holidayMap.put("20170627", "0");
		holidayMap.put("20170628", "0");
		holidayMap.put("20170629", "0");
		holidayMap.put("20170630", "0");
		holidayMap.put("20170701", "1");
		holidayMap.put("20170702", "1");
		holidayMap.put("20170703", "0");
		holidayMap.put("20170704", "0");
		holidayMap.put("20170705", "0");
		holidayMap.put("20170706", "0");
		holidayMap.put("20170707", "0");
		holidayMap.put("20170708", "1");
		holidayMap.put("20170709", "1");
		holidayMap.put("20170710", "0");
		holidayMap.put("20170711", "0");
		holidayMap.put("20170712", "0");
		holidayMap.put("20170713", "0");
		holidayMap.put("20170714", "0");
		holidayMap.put("20170715", "1");
		holidayMap.put("20170716", "1");
		holidayMap.put("20170717", "0");
		holidayMap.put("20170718", "0");
		holidayMap.put("20170719", "0");
		holidayMap.put("20170720", "0");
		holidayMap.put("20170721", "0");
		holidayMap.put("20170722", "1");
		holidayMap.put("20170723", "1");
		holidayMap.put("20170724", "0");
		holidayMap.put("20170725", "0");
		holidayMap.put("20170726", "0");
		holidayMap.put("20170727", "0");
		holidayMap.put("20170728", "0");
		holidayMap.put("20170729", "1");
		holidayMap.put("20170730", "1");
		holidayMap.put("20170731", "0");
		holidayMap.put("20170801", "0");
		holidayMap.put("20170802", "0");
		holidayMap.put("20170803", "0");
		holidayMap.put("20170804", "0");
		holidayMap.put("20170805", "1");
		holidayMap.put("20170806", "1");
		holidayMap.put("20170807", "0");
		holidayMap.put("20170808", "0");
		holidayMap.put("20170809", "0");
		holidayMap.put("20170810", "0");
		holidayMap.put("20170811", "0");
		holidayMap.put("20170812", "1");
		holidayMap.put("20170813", "1");
		holidayMap.put("20170814", "0");
		holidayMap.put("20170815", "0");
		holidayMap.put("20170816", "0");
		holidayMap.put("20170817", "0");
		holidayMap.put("20170818", "0");
		holidayMap.put("20170819", "1");
		holidayMap.put("20170820", "1");
		holidayMap.put("20170821", "0");
		holidayMap.put("20170822", "0");
		holidayMap.put("20170823", "0");
		holidayMap.put("20170824", "0");
		holidayMap.put("20170825", "0");
		holidayMap.put("20170826", "1");
		holidayMap.put("20170827", "1");
		holidayMap.put("20170828", "0");
		holidayMap.put("20170829", "0");
		holidayMap.put("20170830", "0");
		holidayMap.put("20170831", "0");
		holidayMap.put("20170901", "0");
		holidayMap.put("20170902", "1");
		holidayMap.put("20170903", "1");
		holidayMap.put("20170904", "0");
		holidayMap.put("20170905", "0");
		holidayMap.put("20170906", "0");
		holidayMap.put("20170907", "0");
		holidayMap.put("20170908", "0");
		holidayMap.put("20170909", "1");
		holidayMap.put("20170910", "1");
		holidayMap.put("20170911", "0");
		holidayMap.put("20170912", "0");
		holidayMap.put("20170913", "0");
		holidayMap.put("20170914", "0");
		holidayMap.put("20170915", "0");
		holidayMap.put("20170916", "1");
		holidayMap.put("20170917", "1");
		holidayMap.put("20170918", "0");
		holidayMap.put("20170919", "0");
		holidayMap.put("20170920", "0");
		holidayMap.put("20170921", "0");
		holidayMap.put("20170922", "0");
		holidayMap.put("20170923", "1");
		holidayMap.put("20170924", "1");
		holidayMap.put("20170925", "0");
		holidayMap.put("20170926", "0");
		holidayMap.put("20170927", "0");
		holidayMap.put("20170928", "0");
		holidayMap.put("20170929", "0");
		holidayMap.put("20170930", "0");
		holidayMap.put("20171001", "2");
		holidayMap.put("20171002", "2");
		holidayMap.put("20171003", "2");
		holidayMap.put("20171004", "2");
		holidayMap.put("20171005", "2");
		holidayMap.put("20171006", "2");
		holidayMap.put("20171007", "2");
		holidayMap.put("20171008", "2");
		holidayMap.put("20171009", "0");
		holidayMap.put("20171010", "0");
		holidayMap.put("20171011", "0");
		holidayMap.put("20171012", "0");
		holidayMap.put("20171013", "0");
		holidayMap.put("20171014", "1");
		holidayMap.put("20171015", "1");
		holidayMap.put("20171016", "0");
		holidayMap.put("20171017", "0");
		holidayMap.put("20171018", "0");
		holidayMap.put("20171019", "0");
		holidayMap.put("20171020", "0");
		holidayMap.put("20171021", "1");
		holidayMap.put("20171022", "1");
		holidayMap.put("20171023", "0");
		holidayMap.put("20171024", "0");
		holidayMap.put("20171025", "0");
		holidayMap.put("20171026", "0");
		holidayMap.put("20171027", "0");
		holidayMap.put("20171028", "1");
		holidayMap.put("20171029", "1");
		holidayMap.put("20171030", "0");
		holidayMap.put("20171031", "0");
		holidayMap.put("20171101", "0");
		holidayMap.put("20171102", "0");
		holidayMap.put("20171103", "0");
		holidayMap.put("20171104", "1");
		holidayMap.put("20171105", "1");
		holidayMap.put("20171106", "0");
		holidayMap.put("20171107", "0");
		holidayMap.put("20171108", "0");
		holidayMap.put("20171109", "0");
		holidayMap.put("20171110", "0");
		holidayMap.put("20171111", "1");
		holidayMap.put("20171112", "1");
		holidayMap.put("20171113", "0");
		holidayMap.put("20171114", "0");
		holidayMap.put("20171115", "0");
		holidayMap.put("20171116", "0");
		holidayMap.put("20171117", "0");
		holidayMap.put("20171118", "1");
		holidayMap.put("20171119", "1");
		holidayMap.put("20171120", "0");
		holidayMap.put("20171121", "0");
		holidayMap.put("20171122", "0");
		holidayMap.put("20171123", "0");
		holidayMap.put("20171124", "0");
		holidayMap.put("20171125", "1");
		holidayMap.put("20171126", "1");
		holidayMap.put("20171127", "0");
		holidayMap.put("20171128", "0");
		holidayMap.put("20171129", "0");
		holidayMap.put("20171130", "0");
		holidayMap.put("20171201", "0");
		holidayMap.put("20171202", "1");
		holidayMap.put("20171203", "1");
		holidayMap.put("20171204", "0");
		holidayMap.put("20171205", "0");
		holidayMap.put("20171206", "0");
		holidayMap.put("20171207", "0");
		holidayMap.put("20171208", "0");
		holidayMap.put("20171209", "1");
		holidayMap.put("20171210", "1");
		holidayMap.put("20171211", "0");
		holidayMap.put("20171212", "0");
		holidayMap.put("20171213", "0");
		holidayMap.put("20171214", "0");
		holidayMap.put("20171215", "0");
		holidayMap.put("20171216", "1");
		holidayMap.put("20171217", "1");
		holidayMap.put("20171218", "0");
		holidayMap.put("20171219", "0");
		holidayMap.put("20171220", "0");
		holidayMap.put("20171221", "0");
		holidayMap.put("20171222", "0");
		holidayMap.put("20171223", "1");
		holidayMap.put("20171224", "1");
		holidayMap.put("20171225", "0");
		holidayMap.put("20171226", "0");
		holidayMap.put("20171227", "0");
		holidayMap.put("20171228", "0");
		holidayMap.put("20171229", "0");
		holidayMap.put("20171230", "1");
		holidayMap.put("20171231", "1");
		holidayMap.put("20180101", "0");
		holidayMap.put("20180102", "0");
		holidayMap.put("20180103", "0");
		holidayMap.put("20180104", "0");
		holidayMap.put("20180105", "0");
		holidayMap.put("20180106", "1");
		holidayMap.put("20180107", "1");
		holidayMap.put("20180108", "0");
		holidayMap.put("20180109", "0");
		holidayMap.put("20180110", "0");
		holidayMap.put("20180111", "0");
		holidayMap.put("20180112", "0");
		holidayMap.put("20180113", "1");
		holidayMap.put("20180114", "1");
		holidayMap.put("20180115", "0");
		holidayMap.put("20180116", "0");
		holidayMap.put("20180117", "0");
		holidayMap.put("20180118", "0");
		holidayMap.put("20180119", "0");
		holidayMap.put("20180120", "1");
		holidayMap.put("20180121", "1");
		holidayMap.put("20180122", "0");
		holidayMap.put("20180123", "0");
		holidayMap.put("20180124", "0");
		holidayMap.put("20180125", "0");
		holidayMap.put("20180126", "0");
		holidayMap.put("20180127", "1");
		holidayMap.put("20180128", "1");
		holidayMap.put("20180129", "0");
		holidayMap.put("20180130", "0");
		holidayMap.put("20180131", "0");
		holidayMap.put("20180201", "0");
		holidayMap.put("20180202", "0");
		holidayMap.put("20180203", "1");
		holidayMap.put("20180204", "1");
		holidayMap.put("20180205", "0");
		holidayMap.put("20180206", "0");
		holidayMap.put("20180207", "0");
		holidayMap.put("20180208", "0");
		holidayMap.put("20180209", "0");
		holidayMap.put("20180210", "1");
		holidayMap.put("20180211", "1");
		holidayMap.put("20180212", "0");
		holidayMap.put("20180213", "0");
		holidayMap.put("20180214", "0");
		holidayMap.put("20180215", "0");
		holidayMap.put("20180216", "0");
		holidayMap.put("20180217", "1");
		holidayMap.put("20180218", "1");
		holidayMap.put("20180219", "0");
		holidayMap.put("20180220", "0");
		holidayMap.put("20180221", "0");
		holidayMap.put("20180222", "0");
		holidayMap.put("20180223", "0");
		holidayMap.put("20180224", "1");
		holidayMap.put("20180225", "1");
		holidayMap.put("20180226", "0");
		holidayMap.put("20180227", "0");
		holidayMap.put("20180228", "0");
		holidayMap.put("20180301", "0");
		holidayMap.put("20180302", "0");
		holidayMap.put("20180303", "1");
		holidayMap.put("20180304", "1");
		holidayMap.put("20180305", "0");
		holidayMap.put("20180306", "0");
		holidayMap.put("20180307", "0");
		holidayMap.put("20180308", "0");
		holidayMap.put("20180309", "0");
		holidayMap.put("20180310", "1");
		holidayMap.put("20180311", "1");
		holidayMap.put("20180312", "0");
		holidayMap.put("20180313", "0");
		holidayMap.put("20180314", "0");
		holidayMap.put("20180315", "0");
		holidayMap.put("20180316", "0");
		holidayMap.put("20180317", "1");
		holidayMap.put("20180318", "1");
		holidayMap.put("20180319", "0");
		holidayMap.put("20180320", "0");
		holidayMap.put("20180321", "0");
		holidayMap.put("20180322", "0");
		holidayMap.put("20180323", "0");
		holidayMap.put("20180324", "1");
		holidayMap.put("20180325", "1");
		holidayMap.put("20180326", "0");
		holidayMap.put("20180327", "0");
		holidayMap.put("20180328", "0");
		holidayMap.put("20180329", "0");
		holidayMap.put("20180330", "0");
		holidayMap.put("20180331", "1");
		holidayMap.put("20180401", "1");
		holidayMap.put("20180402", "0");
		holidayMap.put("20180403", "0");
		holidayMap.put("20180404", "0");
		holidayMap.put("20180405", "0");
		holidayMap.put("20180406", "0");
		holidayMap.put("20180407", "1");
		holidayMap.put("20180408", "1");
		holidayMap.put("20180409", "0");
		holidayMap.put("20180410", "0");
		holidayMap.put("20180411", "0");
		holidayMap.put("20180412", "0");
		holidayMap.put("20180413", "0");
		holidayMap.put("20180414", "1");
		holidayMap.put("20180415", "1");
		holidayMap.put("20180416", "0");
		holidayMap.put("20180417", "0");
		holidayMap.put("20180418", "0");
		holidayMap.put("20180419", "0");
		holidayMap.put("20180420", "0");
		holidayMap.put("20180421", "1");
		holidayMap.put("20180422", "1");
		holidayMap.put("20180423", "0");
		holidayMap.put("20180424", "0");
		holidayMap.put("20180425", "0");
		holidayMap.put("20180426", "0");
		holidayMap.put("20180427", "0");
		holidayMap.put("20180428", "1");
		holidayMap.put("20180429", "1");
		holidayMap.put("20180430", "0");
		holidayMap.put("20180501", "0");
		holidayMap.put("20180502", "0");
		holidayMap.put("20180503", "0");
		holidayMap.put("20180504", "0");
		holidayMap.put("20180505", "1");
		holidayMap.put("20180506", "1");
		holidayMap.put("20180507", "0");
		holidayMap.put("20180508", "0");
		holidayMap.put("20180509", "0");
		holidayMap.put("20180510", "0");
		holidayMap.put("20180511", "0");
		holidayMap.put("20180512", "1");
		holidayMap.put("20180513", "1");
		holidayMap.put("20180514", "0");
		holidayMap.put("20180515", "0");
		holidayMap.put("20180516", "0");
		holidayMap.put("20180517", "0");
		holidayMap.put("20180518", "0");
		holidayMap.put("20180519", "1");
		holidayMap.put("20180520", "1");
		holidayMap.put("20180521", "0");
		holidayMap.put("20180522", "0");
		holidayMap.put("20180523", "0");
		holidayMap.put("20180524", "0");
		holidayMap.put("20180525", "0");
		holidayMap.put("20180526", "1");
		holidayMap.put("20180527", "1");
		holidayMap.put("20180528", "0");
		holidayMap.put("20180529", "0");
		holidayMap.put("20180530", "0");
		holidayMap.put("20180531", "0");
		holidayMap.put("20180601", "0");
		holidayMap.put("20180602", "1");
		holidayMap.put("20180603", "1");
		holidayMap.put("20180604", "0");
		holidayMap.put("20180605", "0");
		holidayMap.put("20180606", "0");
		holidayMap.put("20180607", "0");
		holidayMap.put("20180608", "0");
		holidayMap.put("20180609", "1");
		holidayMap.put("20180610", "1");
		holidayMap.put("20180611", "0");
		holidayMap.put("20180612", "0");
		holidayMap.put("20180613", "0");
		holidayMap.put("20180614", "0");
		holidayMap.put("20180615", "0");
		holidayMap.put("20180616", "1");
		holidayMap.put("20180617", "1");
		holidayMap.put("20180618", "0");
		holidayMap.put("20180619", "0");
		holidayMap.put("20180620", "0");
		holidayMap.put("20180621", "0");
		holidayMap.put("20180622", "0");
		holidayMap.put("20180623", "1");
		holidayMap.put("20180624", "1");
		holidayMap.put("20180625", "0");
		holidayMap.put("20180626", "0");
		holidayMap.put("20180627", "0");
		holidayMap.put("20180628", "0");
		holidayMap.put("20180629", "0");
		holidayMap.put("20180630", "1");
		holidayMap.put("20180701", "1");
		holidayMap.put("20180702", "0");
		holidayMap.put("20180703", "0");
		holidayMap.put("20180704", "0");
		holidayMap.put("20180705", "0");
		holidayMap.put("20180706", "0");
		holidayMap.put("20180707", "1");
		holidayMap.put("20180708", "1");
		holidayMap.put("20180709", "0");
		holidayMap.put("20180710", "0");
		holidayMap.put("20180711", "0");
		holidayMap.put("20180712", "0");
		holidayMap.put("20180713", "0");
		holidayMap.put("20180714", "1");
		holidayMap.put("20180715", "1");
		holidayMap.put("20180716", "0");
		holidayMap.put("20180717", "0");
		holidayMap.put("20180718", "0");
		holidayMap.put("20180719", "0");
		holidayMap.put("20180720", "0");
		holidayMap.put("20180721", "1");
		holidayMap.put("20180722", "1");
		holidayMap.put("20180723", "0");
		holidayMap.put("20180724", "0");
		holidayMap.put("20180725", "0");
		holidayMap.put("20180726", "0");
		holidayMap.put("20180727", "0");
		holidayMap.put("20180728", "1");
		holidayMap.put("20180729", "1");
		holidayMap.put("20180730", "0");
		holidayMap.put("20180731", "0");
		holidayMap.put("20180801", "0");
		holidayMap.put("20180802", "0");
		holidayMap.put("20180803", "0");
		holidayMap.put("20180804", "1");
		holidayMap.put("20180805", "1");
		holidayMap.put("20180806", "0");
		holidayMap.put("20180807", "0");
		holidayMap.put("20180808", "0");
		holidayMap.put("20180809", "0");
		holidayMap.put("20180810", "0");
		holidayMap.put("20180811", "1");
		holidayMap.put("20180812", "1");
		holidayMap.put("20180813", "0");
		holidayMap.put("20180814", "0");
		holidayMap.put("20180815", "0");
		holidayMap.put("20180816", "0");
		holidayMap.put("20180817", "0");
		holidayMap.put("20180818", "1");
		holidayMap.put("20180819", "1");
		holidayMap.put("20180820", "0");
		holidayMap.put("20180821", "0");
		holidayMap.put("20180822", "0");
		holidayMap.put("20180823", "0");
		holidayMap.put("20180824", "0");
		holidayMap.put("20180825", "1");
		holidayMap.put("20180826", "1");
		holidayMap.put("20180827", "0");
		holidayMap.put("20180828", "0");
		holidayMap.put("20180829", "0");
		holidayMap.put("20180830", "0");
		holidayMap.put("20180831", "0");
		holidayMap.put("20180901", "1");
		holidayMap.put("20180902", "1");
		holidayMap.put("20180903", "0");
		holidayMap.put("20180904", "0");
		holidayMap.put("20180905", "0");
		holidayMap.put("20180906", "0");
		holidayMap.put("20180907", "0");
		holidayMap.put("20180908", "1");
		holidayMap.put("20180909", "1");
		holidayMap.put("20180910", "0");
		holidayMap.put("20180911", "0");
		holidayMap.put("20180912", "0");
		holidayMap.put("20180913", "0");
		holidayMap.put("20180914", "0");
		holidayMap.put("20180915", "1");
		holidayMap.put("20180916", "1");
		holidayMap.put("20180917", "0");
		holidayMap.put("20180918", "0");
		holidayMap.put("20180919", "0");
		holidayMap.put("20180920", "0");
		holidayMap.put("20180921", "0");
		holidayMap.put("20180922", "1");
		holidayMap.put("20180923", "1");
		holidayMap.put("20180924", "0");
		holidayMap.put("20180925", "0");
		holidayMap.put("20180926", "0");
		holidayMap.put("20180927", "0");
		holidayMap.put("20180928", "0");
		holidayMap.put("20180929", "1");
		holidayMap.put("20180930", "1");
		holidayMap.put("20181001", "0");
		holidayMap.put("20181002", "0");
		holidayMap.put("20181003", "0");
		holidayMap.put("20181004", "0");
		holidayMap.put("20181005", "0");
		holidayMap.put("20181006", "1");
		holidayMap.put("20181007", "1");
		holidayMap.put("20181008", "0");
		holidayMap.put("20181009", "0");
		holidayMap.put("20181010", "0");
		holidayMap.put("20181011", "0");
		holidayMap.put("20181012", "0");
		holidayMap.put("20181013", "1");
		holidayMap.put("20181014", "1");
		holidayMap.put("20181015", "0");
		holidayMap.put("20181016", "0");
		holidayMap.put("20181017", "0");
		holidayMap.put("20181018", "0");
		holidayMap.put("20181019", "0");
		holidayMap.put("20181020", "1");
		holidayMap.put("20181021", "1");
		holidayMap.put("20181022", "0");
		holidayMap.put("20181023", "0");
		holidayMap.put("20181024", "0");
		holidayMap.put("20181025", "0");
		holidayMap.put("20181026", "0");
		holidayMap.put("20181027", "1");
		holidayMap.put("20181028", "1");
		holidayMap.put("20181029", "0");
		holidayMap.put("20181030", "0");
		holidayMap.put("20181031", "0");
		holidayMap.put("20181101", "0");
		holidayMap.put("20181102", "0");
		holidayMap.put("20181103", "1");
		holidayMap.put("20181104", "1");
		holidayMap.put("20181105", "0");
		holidayMap.put("20181106", "0");
		holidayMap.put("20181107", "0");
		holidayMap.put("20181108", "0");
		holidayMap.put("20181109", "0");
		holidayMap.put("20181110", "1");
		holidayMap.put("20181111", "1");
		holidayMap.put("20181112", "0");
		holidayMap.put("20181113", "0");
		holidayMap.put("20181114", "0");
		holidayMap.put("20181115", "0");
		holidayMap.put("20181116", "0");
		holidayMap.put("20181117", "1");
		holidayMap.put("20181118", "1");
		holidayMap.put("20181119", "0");
		holidayMap.put("20181120", "0");
		holidayMap.put("20181121", "0");
		holidayMap.put("20181122", "0");
		holidayMap.put("20181123", "0");
		holidayMap.put("20181124", "1");
		holidayMap.put("20181125", "1");
		holidayMap.put("20181126", "0");
		holidayMap.put("20181127", "0");
		holidayMap.put("20181128", "0");
		holidayMap.put("20181129", "0");
		holidayMap.put("20181130", "0");
		holidayMap.put("20181201", "1");
		holidayMap.put("20181202", "1");
		holidayMap.put("20181203", "0");
		holidayMap.put("20181204", "0");
		holidayMap.put("20181205", "0");
		holidayMap.put("20181206", "0");
		holidayMap.put("20181207", "0");
		holidayMap.put("20181208", "1");
		holidayMap.put("20181209", "1");
		holidayMap.put("20181210", "0");
		holidayMap.put("20181211", "0");
		holidayMap.put("20181212", "0");
		holidayMap.put("20181213", "0");
		holidayMap.put("20181214", "0");
		holidayMap.put("20181215", "1");
		holidayMap.put("20181216", "1");
		holidayMap.put("20181217", "0");
		holidayMap.put("20181218", "0");
		holidayMap.put("20181219", "0");
		holidayMap.put("20181220", "0");
		holidayMap.put("20181221", "0");
		holidayMap.put("20181222", "1");
		holidayMap.put("20181223", "1");
		holidayMap.put("20181224", "0");
		holidayMap.put("20181225", "0");
		holidayMap.put("20181226", "0");
		holidayMap.put("20181227", "0");
		holidayMap.put("20181228", "0");
		holidayMap.put("20181229", "1");
		holidayMap.put("20181230", "1");
		holidayMap.put("20181231", "0");
		holidayMap.put("20190101", "0");
		holidayMap.put("20190102", "0");
		holidayMap.put("20190103", "0");
		holidayMap.put("20190104", "0");
		holidayMap.put("20190105", "1");
		holidayMap.put("20190106", "1");
		holidayMap.put("20190107", "0");
		holidayMap.put("20190108", "0");
		holidayMap.put("20190109", "0");
		holidayMap.put("20190110", "0");
		holidayMap.put("20190111", "0");
		holidayMap.put("20190112", "1");
		holidayMap.put("20190113", "1");
		holidayMap.put("20190114", "0");
		holidayMap.put("20190115", "0");
		holidayMap.put("20190116", "0");
		holidayMap.put("20190117", "0");
		holidayMap.put("20190118", "0");
		holidayMap.put("20190119", "1");
		holidayMap.put("20190120", "1");
		holidayMap.put("20190121", "0");
		holidayMap.put("20190122", "0");
		holidayMap.put("20190123", "0");
		holidayMap.put("20190124", "0");
		holidayMap.put("20190125", "0");
		holidayMap.put("20190126", "1");
		holidayMap.put("20190127", "1");
		holidayMap.put("20190128", "0");
		holidayMap.put("20190129", "0");
		holidayMap.put("20190130", "0");
		holidayMap.put("20190131", "0");
		holidayMap.put("20190201", "0");
		holidayMap.put("20190202", "1");
		holidayMap.put("20190203", "1");
		holidayMap.put("20190204", "0");
	}

	public static interface DateActionListener {
		public void dateActionClick(Calendar calendar, String date);
	}

	public void setDateSelectListener(DateActionListener listener) {
		this.dateListener = listener;
	}
}