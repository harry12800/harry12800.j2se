package cn.harry12800.j2se.component.rc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;

/**
 * Created by harry12800 on 17-5-29.
 */
@SuppressWarnings("serial")
public class RCTextField extends JTextField {
	private String placeholder;

	public RCTextField() {
		setBackground(Colors.FONT_WHITE);
		setForeground(Colors.FONT_BLACK);
		setCaretColor(Color.GRAY);
		setBorder(new EmptyBorder(new Insets(0, 20, 0, 0)));
		setMargin(new Insets(0, 20, 0, 0));
		getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (getText().isEmpty()) {
					repaint();
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		if (getText().isEmpty()) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setBackground(Color.gray);
			g2.setFont(FontUtil.getDefaultFont());
			g2.setColor(Color.GRAY);
			g2.drawString(placeholder, 20, 25);
			g2.dispose();
		}

	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
}
