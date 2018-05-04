package cn.harry12800.j2se.tip;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;

import javax.swing.JLabel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class LineLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int w = 0;

	private String content;

	public LineLabel(String content) {
		this.content = "    " + content;
		addHierarchyBoundsListener(new HierarchyBoundsListener() {

			@Override
			public void ancestorResized(HierarchyEvent e) {
				w = getParent().getWidth();
				setContext();
			}

			@Override
			public void ancestorMoved(HierarchyEvent e) {
			}
		});
		addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				// TODO Auto-generated method stub
			}

			@Override
			public void ancestorAdded(AncestorEvent event) {
			}
		});

		setText(this.content);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void setContext() {
		FontMetrics fontMetrics = getFontMetrics(getFont());
		char[] charArray = content.toCharArray();
		int start = 0;
		int len = 0;
		if (w <= 0)
			return;
		StringBuffer snBuffer = new StringBuffer("<html>");

		while (start + len < content.length()) {
			while (true) {
				len++;
				if (start + len > content.length())
					break;
				if (fontMetrics.charsWidth(charArray, start, len) > w) {
					break;
				}
			}
			snBuffer.append(charArray, start, len - 1).append("<br>");
			start = start + len - 1;
			len = 0;
		}
		snBuffer.append(charArray, start, content.length() - start);
		snBuffer.append("</html>");
		String string = snBuffer.toString();
		// System.out.println(string);
		// String replaceAll = string.replaceAll(" ","&nbsp;");
		// System.out.println(replaceAll);
		setText(string.replaceAll(" ", "&nbsp;"));
	}
}
