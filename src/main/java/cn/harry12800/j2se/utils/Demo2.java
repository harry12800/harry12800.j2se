package cn.harry12800.j2se.utils;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/** 
 * JScrollPane优化示例 
 *  
 * @author SongFei 
 * @date 2015年5月17日 
 */
public class Demo2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel content;
	private JTree tree;
	private JScrollPane scrollPane;

	public Demo2() {
		setSize(300, 400);
		setTitle("course");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		tree = new JTree(getRootNode());

		content = new JPanel();
		content.setLayout(new BorderLayout());

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(tree);
		scrollPane.getVerticalScrollBar().setUI(new DemoScrollBarUI());
		content.add(scrollPane, BorderLayout.CENTER);

		getContentPane().add(content);
	}

	/** 
	 * 创建root节点 
	 * @return 
	 */
	private DefaultMutableTreeNode getRootNode() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		root.add(new DefaultMutableTreeNode("node1"));
		root.add(new DefaultMutableTreeNode("node2"));
		root.add(new DefaultMutableTreeNode("node3"));
		root.add(new DefaultMutableTreeNode("node4"));
		root.add(new DefaultMutableTreeNode("node5"));
		root.add(new DefaultMutableTreeNode("node6"));
		root.add(new DefaultMutableTreeNode("node7"));
		root.add(new DefaultMutableTreeNode("node8"));
		root.add(new DefaultMutableTreeNode("node9"));
		root.add(new DefaultMutableTreeNode("node10"));
		root.add(new DefaultMutableTreeNode("node11"));
		root.add(new DefaultMutableTreeNode("node12"));
		root.add(new DefaultMutableTreeNode("node13"));
		root.add(new DefaultMutableTreeNode("node14"));
		root.add(new DefaultMutableTreeNode("node15"));
		root.add(new DefaultMutableTreeNode("node16"));
		root.add(new DefaultMutableTreeNode("node17"));
		root.add(new DefaultMutableTreeNode("node18"));
		root.add(new DefaultMutableTreeNode("node19"));
		root.add(new DefaultMutableTreeNode("node20"));
		return root;
	}

	public static void main(String[] args) {
		Demo2 demo1 = new Demo2();
		demo1.setVisible(true);
		demo1.setLocationRelativeTo(null);
	}

}