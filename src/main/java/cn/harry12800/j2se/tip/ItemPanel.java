package cn.harry12800.j2se.tip;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TooManyListenersException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.j2se.style.UI;
import cn.harry12800.j2se.tip.ListPanel.ListCallBack;

public class ItemPanel<T extends Letter> extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel;
	private LineLabel a;
	private JLabel dateLabel;
	private T letter;
	public boolean isopen = false;
	public ItemPanel<T> parentItem;

	/**
	 * 获取letter
	 * 
	 * @return the letter
	 */
	public T getLetter() {
		return letter;
	}

	/**
	 * 设置letter
	 * 
	 * @param letter
	 *            the letter to set
	 */
	public void setLetter(T letter) {
		this.letter = letter;
	}

	public ItemPanel(T letter) {
		this.letter = letter;
		//		setTransferHandler(new MyTransferHandler(this) );
		DropTarget dt1 = new DropTarget();
		dt1.setComponent(this);
		dt1.setDefaultActions(DnDConstants.ACTION_COPY_OR_MOVE);
		try {
			dt1.addDropTargetListener(new DropTargetListener() {

				@Override
				public void dropActionChanged(DropTargetDragEvent dtde) {

				}

				@Override
				public void drop(DropTargetDropEvent dtde) {

				}

				@Override
				public void dragOver(DropTargetDragEvent dtde) {

				}

				@Override
				public void dragExit(DropTargetEvent dte) {

				}

				@Override
				public void dragEnter(DropTargetDragEvent dtde) {

				}
			});
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		setRequestFocusEnabled(true);
		setFocusable(true);
		this.titleLabel = new JLabel(letter.getTitle());
		this.a = new LineLabel(letter.getContent());
		titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
		this.dateLabel = new JLabel(letter.getDate() + "  ");
		// titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10,5, 0));
		titleLabel.setFont(UI.微软雅黑Font);
		a.setFont(UI.微软雅黑Font);
		dateLabel.setFont(UI.微软雅黑Font);
		int x = 0;
		int r = 0, g = 0, b = 0;
		while (x < 250) {
			r = (int) (Math.random() * 255);
			g = (int) (Math.random() * 255);
			b = (int) (Math.random() * 255);
			x = r * r + g * g + b * b;
		}
		setBackground(new Color(r, g, b));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		Box titleBox = Box.createHorizontalBox();
		Component hglue = Box.createHorizontalGlue();
		titleBox.add(titleLabel);
		titleBox.add(hglue);
		// createVerticalBox1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		titleBox.setOpaque(false);

		Box mainBox = Box.createVerticalBox();
		mainBox.setBackground(Color.red);

		Box dateBox = Box.createHorizontalBox();
		Component createHorizontalGlue = Box.createHorizontalGlue();
		dateBox.add(createHorizontalGlue);
		dateBox.add(dateLabel);
		// createVerticalBox1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		dateBox.setOpaque(false);

		Box contentBox = Box.createHorizontalBox();
		Component createHorizontalGlue1 = Box.createHorizontalGlue();
		contentBox.add(a);
		contentBox.add(createHorizontalGlue1);
		// createVerticalBox1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		contentBox.setOpaque(false);
		add(titleBox);
		if (!"".equals(letter.getContent().trim())) {
			add(contentBox);
		}
		if (!"".equals(letter.getDate().trim())) {
			add(dateBox);
		}
		// add(mainBox);
		// Box createVerticalBox1 = Box.createVerticalBox();
		ComponentOrientation o1 = ComponentOrientation.LEFT_TO_RIGHT;
		mainBox.setComponentOrientation(o1);
		//		Box rootBox = Box.createHorizontalBox();
		// createVerticalBox1.add(new JButton("adas22"));
		// add(createVerticalBox1);
	}

	public ItemPanel(final ListPanel<?> listPanel, final T letter) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.letter = letter;
		setRequestFocusEnabled(true);
		setFocusable(true);
		setOpaque(false);
		this.titleLabel = new JLabel(letter.getTitle());
		a = new LineLabel(letter.getContent());
		titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
		dateLabel = new JLabel(letter.getDate() + "  ");
		// titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10,5, 0));
		titleLabel.setFont(UI.微软雅黑Font);
		a.setFont(UI.微软雅黑Font);
		dateLabel.setFont(UI.微软雅黑Font);
		int x = 0;
		int r = 0, g = 0, b = 0;
		while (x < 250) {
			r = (int) (Math.random() * 255);
			g = (int) (Math.random() * 255);
			b = (int) (Math.random() * 255);
			x = r * r + g * g + b * b;
		}
		setBackground(new Color(37, 46, 33));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		Box titleBox = Box.createHorizontalBox();
		Component hglue = Box.createHorizontalGlue();
		titleBox.add(Box.createHorizontalStrut(letter.getIndent()));
		//		ImageBtn imageBtn = new ImageBtn(ImageUtils.getByName("downG.png"));
		JLabel label = new JLabel("   +");
		titleBox.add(label);

		titleBox.add(titleLabel);
		titleBox.add(hglue);
		// createVerticalBox1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		titleBox.setOpaque(false);

		Box mainBox = Box.createVerticalBox();
		mainBox.setBackground(Color.red);

		Box dateBox = Box.createHorizontalBox();
		Component createHorizontalGlue = Box.createHorizontalGlue();
		dateBox.add(createHorizontalGlue);
		dateBox.add(dateLabel);
		// createVerticalBox1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		dateBox.setOpaque(false);

		Box contentBox = Box.createHorizontalBox();
		Component createHorizontalGlue1 = Box.createHorizontalGlue();
		contentBox.add(a);
		contentBox.add(createHorizontalGlue1);
		// createVerticalBox1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		contentBox.setOpaque(false);
		add(titleBox);
		if (letter.getContent() != null && !"".equals(letter.getContent().trim())) {
			add(contentBox);
		}
		if (letter.getDate() != null && !"".equals(letter.getDate().trim())) {
			add(dateBox);
		}
		// add(mainBox);
		// Box createVerticalBox1 = Box.createVerticalBox();
		ComponentOrientation o1 = ComponentOrientation.LEFT_TO_RIGHT;
		mainBox.setComponentOrientation(o1);
		// createVerticalBox1.add(new JButton("adas22"));
		// add(createVerticalBox1);
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("unchecked")
				ListCallBack<T> callback = (ListCallBack<T>) listPanel.getCallback();
				if (callback != null)
					callback.item(ItemPanel.this, ItemPanel.this.letter);
			}
		});
	}

	/**
	 * 获取titleLabel
	 * 
	 * @return the titleLabel
	 */
	public JLabel getTitleLabel() {
		return titleLabel;
	}

	/**
	 * 设置titleLabel
	 * 
	 * @param titleLabel
	 *            the titleLabel to set
	 */
	public void setTitleLabel(JLabel titleLabel) {
		this.titleLabel = titleLabel;
	}

	public static void main(String[] args) {
		// Clip.seeCom(new
		// ItemPanel("asfdssfdgsfgddfsa","asdf",DateUtils.getCurrTimeByFormat("yyyy年MM月dd日")));
	}

	@Override
	public String toString() {
		return "ItemPanel [letter=" + letter + ", isopen=" + isopen + "]";
	}

}
