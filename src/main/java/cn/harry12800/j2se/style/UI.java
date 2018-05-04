package cn.harry12800.j2se.style;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Field;

import javax.swing.UIManager;

public class UI {
	public static Color foreColor = new Color(21, 92, 143);
	public static Color scrollColor = new Color(51, 143, 255);
	public static Color hoverColor = new Color(215, 220, 226);
	//	public static Color backColor = new Color(91,80,122);
	public static Color backColor = new Color(8, 38, 65);
	public static Color fontColor = new Color(255, 255, 255);
	public static Color borderColor = new Color(255, 255, 255);

	public static Font normalFont = new Font("宋体", Font.PLAIN, 12);
	public static Font 微软雅黑Font = new Font("微软雅黑", Font.PLAIN, 12);
	//	public static Color backColor = new Color(91,80,122);
	public static Color earnColor = new Color(255, 0, 0);
	public static Color voidColor = new Color(120, 120, 120, 120);
	public static Color transColor = new Color(0, 0, 0, 0);

	public static Font 华文新魏Font = new Font("华文新魏", Font.PLAIN, 14);
	public static Color hoverForeColor = Color.BLACK;

	public static Color foreColor(int op) {
		return new Color(foreColor.getRed(), foreColor.getGreen(), foreColor.getBlue(), op);
	}

	public static Color GREEN(int op) {
		return new Color(Color.green.getRed(), Color.green.getGreen(), Color.green.getBlue(), op);
	}

	public static Color RED(int op) {
		return new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), op);
	}

	public static Color backColor(int op) {
		return new Color(backColor.getRed(), backColor.getGreen(), backColor.getBlue(), op);
	}

	static {
		loadIndyFont();
		loadforeColor();
		loadBackColor();
		loadCustomProps();
	}

	public static void loadBackColor() {
		UIManager.put("CheckBox.background", foreColor);
		UIManager.put("Tree.background", foreColor);
		UIManager.put("Viewport.background", foreColor);
		UIManager.put("ProgressBar.background", foreColor);
		UIManager.put("RadioButtonMenuItem.background", foreColor);
		UIManager.put("FormattedTextField.background", foreColor);
		UIManager.put("ToolBar.background", foreColor);
		UIManager.put("ColorChooser.background", foreColor);
		UIManager.put("ToggleButton.background", foreColor);
		UIManager.put("Panel.background", foreColor);
		UIManager.put("TextArea.background", foreColor);
		UIManager.put("Menu.background", foreColor);
		UIManager.put("RadioButtonMenuItem.background", foreColor);
		UIManager.put("Spinner.background", foreColor);
		UIManager.put("Menu.background", foreColor);
		UIManager.put("CheckBoxMenuItem.background", foreColor);
		UIManager.put("TableHeader.background", foreColor);
		UIManager.put("TextField.background", foreColor);
		UIManager.put("OptionPane.background", foreColor);
		UIManager.put("MenuBar.background", foreColor);
		UIManager.put("Button.background", foreColor);
		UIManager.put("Label.background", foreColor);
		UIManager.put("PasswordField.background", foreColor);
		UIManager.put("InternalFrame.background", foreColor);
		UIManager.put("OptionPane.background", foreColor);
		UIManager.put("ScrollPane.background", foreColor);
		UIManager.put("MenuItem.background", foreColor);
		UIManager.put("ToolTip.background", foreColor);
		UIManager.put("List.background", foreColor);
		UIManager.put("OptionPane.background", foreColor);
		UIManager.put("EditorPane.background", foreColor);
		UIManager.put("Table.background", foreColor);
		UIManager.put("TabbedPane.background", foreColor);
		UIManager.put("RadioButton.background", foreColor);
		UIManager.put("CheckBoxMenuItem.background", foreColor);
		UIManager.put("TextPane.background", foreColor);
		UIManager.put("PopupMenu.background", foreColor);
		UIManager.put("TitledBorder.background", foreColor);
		UIManager.put("ComboBox.background", foreColor);
	}

	private static void loadCustomProps() {
		try {
			Class<?> child = Thread.currentThread().getContextClassLoader()
					.loadClass("cn.harry12800.j2se.style.UITT");
			Field[] fs = child.getDeclaredFields();
			Field[] currFs = UI.class.getDeclaredFields();
			for (Field f : currFs) {
				for (Field field : fs) {
					if (field.getName().equals(f.getName())) {
						f.setAccessible(true);
						f.set(null, field.get(null));
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

	}

	public static void loadforeColor() {
		UIManager.put("CheckBox.foreground", fontColor);
		UIManager.put("Tree.foreground", fontColor);
		UIManager.put("Viewport.foreground", fontColor);
		UIManager.put("ProgressBar.foreground", fontColor);
		UIManager.put("RadioButtonMenuItem.foreground", fontColor);
		UIManager.put("FormattedTextField.foreground", fontColor);
		UIManager.put("ToolBar.foreground", fontColor);
		UIManager.put("ColorChooser.foreground", fontColor);
		UIManager.put("ToggleButton.foreground", fontColor);
		UIManager.put("Panel.foreground", fontColor);
		UIManager.put("TextArea.foreground", fontColor);
		UIManager.put("Menu.foreground", fontColor);
		UIManager.put("RadioButtonMenuItem.foreground", fontColor);
		UIManager.put("Spinner.foreground", fontColor);
		UIManager.put("Menu.foreground", fontColor);
		UIManager.put("CheckBoxMenuItem.foreground", fontColor);
		UIManager.put("TableHeader.foreground", fontColor);
		UIManager.put("TextField.foreground", fontColor);
		UIManager.put("OptionPane.foreground", fontColor);
		UIManager.put("MenuBar.foreground", fontColor);
		UIManager.put("Button.foreground", fontColor);
		UIManager.put("Label.foreground", fontColor);
		UIManager.put("PasswordField.foreground", fontColor);
		UIManager.put("InternalFrame.foreground", fontColor);
		UIManager.put("OptionPane.foreground", fontColor);
		UIManager.put("ScrollPane.foreground", fontColor);
		UIManager.put("MenuItem.foreground", fontColor);
		UIManager.put("ToolTip.foreground", fontColor);
		UIManager.put("List.foreground", fontColor);
		UIManager.put("OptionPane.foreground", normalFont);
		UIManager.put("EditorPane.foreground", fontColor);
		UIManager.put("Table.foreground", fontColor);
		UIManager.put("TabbedPane.foreground", fontColor);
		UIManager.put("RadioButton.foreground", fontColor);
		UIManager.put("CheckBoxMenuItem.foreground", fontColor);
		UIManager.put("TextPane.foreground", fontColor);
		UIManager.put("PopupMenu.foreground", fontColor);
		UIManager.put("TitledBorder.foreground", fontColor);
		UIManager.put("ComboBox.foreground", fontColor);
	}

	public static void loadIndyFont() {
		UIManager.put("CheckBox.font", normalFont);
		UIManager.put("Tree.font", normalFont);
		UIManager.put("Viewport.font", normalFont);
		UIManager.put("ProgressBar.font", normalFont);
		UIManager.put("RadioButtonMenuItem.font", normalFont);
		UIManager.put("FormattedTextField.font", normalFont);
		UIManager.put("ToolBar.font", normalFont);
		UIManager.put("ColorChooser.font", normalFont);
		UIManager.put("ToggleButton.font", normalFont);
		UIManager.put("Panel.font", normalFont);
		UIManager.put("TextArea.font", normalFont);
		UIManager.put("Menu.font", normalFont);
		UIManager.put("RadioButtonMenuItem.acceleratorFont", normalFont);
		UIManager.put("Spinner.font", normalFont);
		UIManager.put("Menu.acceleratorFont", normalFont);
		UIManager.put("CheckBoxMenuItem.acceleratorFont", normalFont);
		UIManager.put("TableHeader.font", normalFont);
		UIManager.put("TextField.font", normalFont);
		UIManager.put("OptionPane.font", normalFont);
		UIManager.put("MenuBar.font", normalFont);
		UIManager.put("Button.font", normalFont);
		UIManager.put("Label.font", normalFont);
		UIManager.put("PasswordField.font", normalFont);
		UIManager.put("InternalFrame.titleFont", normalFont);
		UIManager.put("OptionPane.buttonFont", normalFont);
		UIManager.put("ScrollPane.font", normalFont);
		UIManager.put("MenuItem.font", normalFont);
		UIManager.put("ToolTip.font", normalFont);
		UIManager.put("List.font", normalFont);
		UIManager.put("OptionPane.messageFont", normalFont);
		UIManager.put("EditorPane.font", normalFont);
		UIManager.put("Table.font", normalFont);
		UIManager.put("TabbedPane.font", normalFont);
		UIManager.put("RadioButton.font", normalFont);
		UIManager.put("CheckBoxMenuItem.font", normalFont);
		UIManager.put("TextPane.font", normalFont);
		UIManager.put("PopupMenu.font", normalFont);
		UIManager.put("TitledBorder.font", normalFont);
		UIManager.put("ComboBox.font", normalFont);
	}

	public static Font normalFont(int i) {
		return new Font("宋体", Font.PLAIN, i);
	}
}
