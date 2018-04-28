package cn.harry12800.j2se.style;

import java.awt.Color;

import cn.harry12800.j2se.utils.Config;

public class J2seColor {

	private static Color backgroudColor =  null;
	static Color borderColor= new Color(200,200,200);
	public static Color getBackgroundColor() {
		if(backgroudColor==null	){
			String prop = Config.getProp(J2seColor.class, "backgroudColor");
			if(prop==null){
				backgroudColor=new Color(153,133,245);
				Config.setProp(J2seColor.class, "backgroudColor",backgroudColor.getRed()
						+"."+backgroudColor.getGreen()
						+"."+backgroudColor.getBlue() );
				return backgroudColor;
			}
			String[] split = prop.split("[.]");
			backgroudColor = new Color(Integer.valueOf(split[0]),
					Integer.valueOf(split[1]),
					Integer.valueOf(split[2]));
		}
		return backgroudColor;
	}
	public static Color getBorderColor(){
		return borderColor;
	}
	public static void setBackgroudColor(Color blue) {
		backgroudColor= blue;
		Config.setProp(J2seColor.class, "backgroudColor",backgroudColor.getRed()
				+"."+backgroudColor.getGreen()
				+"."+backgroudColor.getBlue() );
	}
}
