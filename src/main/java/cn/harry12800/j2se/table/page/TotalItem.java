package cn.harry12800.j2se.table.page;

import java.awt.Dimension;

import javax.swing.JLabel;

class TotalItem<T> extends JLabel {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		int w = 80;
		int h = 30;
		public TotalItem(final PagePanel<T> pagePanel,int totalpage) {
			super("共"+totalpage+"页");
			setPreferredSize(new Dimension(w,h));
		}
	}