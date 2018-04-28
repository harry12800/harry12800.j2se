package cn.harry12800.j2se.component.btn;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class TextAndPicButton extends JButton {
	/**
	 * 
	 * @param filename
	 * @param text
	 */
	public TextAndPicButton(String filename, String text) {
		InputStream in = TextAndPicButton.class.getResourceAsStream(filename);
		try {
			TextAndPic(in,text);
		} catch (Exception e) {
			setHorizontalTextPosition(SwingConstants.CENTER);
			setVerticalTextPosition(SwingConstants.TOP);
			setText(text);
			ImageIcon icon = new ImageIcon(filename);
			if (icon != null)
				setIcon(icon);
		}
	}

	/**
	 * 
	 * @param filename
	 * @param text
	 * @throws IOException 
	 */
	public void TextAndPic(InputStream in, String text) throws Exception {
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.TOP);
		setText(text);
		BufferedImage image = ImageIO.read(in);
		ImageIcon icon = new ImageIcon(image);
		if (icon != null)
			setIcon(icon);
	}
}
