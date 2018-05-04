package cn.harry12800.j2se.tab;

import java.awt.CardLayout;
import java.awt.Container;
import java.util.List;

import javax.swing.JLayeredPane;

public class ContentPanel extends JLayeredPane {

	private CardLayout cardLayout;

	public ContentPanel(List<Container> panelList) {
		setOpaque(false);
		//		setBackground(UI.backColor);
		this.cardLayout = new CardLayout(0, 0);
		setLayout(cardLayout);
		int i = 0;
		for (Container jPanel : panelList) {
			add("" + i, jPanel);
			i++;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void showIndex(int indexOf) {
		cardLayout.show(this, "" + indexOf);
	}
	//	@Override
	//	protected void paintComponent(Graphics g) {
	//		g.setColor(Style.backColor);
	//		g.fillRect(0, 0, getWidth(), getHeight());
	//		g.setColor(Color.WHITE);
	//		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
	//	}
}
