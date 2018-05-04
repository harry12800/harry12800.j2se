package cn.harry12800.j2se.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AddPanel<T> extends JPanel {

	private OperDialog<T> operDialog;
	private DisplayPanel<T> context;

	public AddPanel(DisplayPanel<T> object, OperDialog<T> operDialog) {
		this.context = object;
		this.operDialog = operDialog;
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setOpaque(false);
		Dimension ds = new Dimension(context.meta.addPanelMeta.width, context.meta.addPanelMeta.height);
		setPreferredSize(ds);
		Component northComp = getNorthComp();
		if (northComp != null)
			add(northComp, BorderLayout.NORTH);
		AddChildPanel<T> diyPanel = new AddChildPanel<T>(context, this.operDialog);
		add(diyPanel, BorderLayout.CENTER);
	}

	private Component getNorthComp() {
		return context.getAddDialogNorthComp(this.operDialog);
	}
}
