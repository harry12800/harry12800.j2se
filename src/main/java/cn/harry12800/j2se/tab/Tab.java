package cn.harry12800.j2se.tab;

import java.awt.Container;

import javax.swing.JPanel;

public class Tab   {

	TabHeader tabHeader= null;
	private Container contentPane;
	private TabRootPane tabRootPane;
	public Tab(String name) {
		tabHeader = new TabHeader(this,name,25);
	}
	public void setContentPanel(JPanel p){
		this.contentPane = p;
	}
	public TabHeader getTabHeader() {
		return tabHeader;
	}
	public void setTabHeader(TabHeader tabHeader) {
		this.tabHeader = tabHeader;
	}
	public Container getContentPane() {
		return contentPane;
	}
	public void setContentPane(Container contentPane) {
		this.contentPane = contentPane;
	}
	public void showTab() {
		tabRootPane.showTab(this);
	}
	public void setParent(TabRootPane tabRootPane) {
		this.tabRootPane = tabRootPane;
	}
	public void setIndex(int i) {
		tabHeader.setIndex(i);
	}
	public int getCurrIndex(){
		return tabRootPane.getCurrIndex();
	}
}
