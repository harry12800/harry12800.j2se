//package cn.harry12800.j2se.style;
//
//import java.awt.Font;
//import java.io.IOException;
//import java.io.InputStream;
//
//import cn.harry12800.j2se.utils.Config;
//
///**
// * 字体类
// * @author Yuexin
// *
// */
//public class J2seFont {
//	
//	static Font definiedFont = null;
//
//	static {
//		String prop = Config.getProp(J2seFont.class, "definiedFont");
//		if (prop==null) {
//			 setDefinedFont(-1);
//		}
//		else {
//			setDefinedFont(Integer.valueOf(prop));
//		}
//	}
//	public static Font  getDefinedFont() {
//		return definiedFont;
//	}
//	public static Font getDefinedFont(float size) {
//		return getDefinedFont().deriveFont(size);
//	}
//	public static Font getDefinedFont(int style ,float size) {
//		return  getDefinedFont().deriveFont(size).deriveFont(style);
//	}
//	public static void setDefinedFont(int x){ 
//		InputStream fi =null;
//		try {
//		//	if (x==-1) {
//				definiedFont= new Font("宋体", Font.PLAIN, 12);
//				Config.setProp(J2seFont.class, "definiedFont","-1");
//				return ;
//		//	}
//			/*if (x==0) {
//				fi = Thread.currentThread().getContextClassLoader()
//						.getResourceAsStream("font/萝莉体.ttc");// Class类的一个方法，将本地的资源加载成一个输入流
//			}
//			if (x==1) {
//				fi = Thread.currentThread().getContextClassLoader()
//						.getResourceAsStream("font/华文行楷.ttf");// Class类的一个方法，将本地的资源加载成一个输入流
//			}
//			if (x==2) {
//				fi = Thread.currentThread().getContextClassLoader()
//						.getResourceAsStream("font/汉仪舒同体简.ttf");// Class类的一个方法，将本地的资源加载成一个输入流
//			}
//			if (x==3) {
//				fi = Thread.currentThread().getContextClassLoader()
//						.getResourceAsStream("font/汉仪中圆繁.ttf");// Class类的一个方法，将本地的资源加载成一个输入流
//			} 
//			if (x==4) {
//				fi = Thread.currentThread().getContextClassLoader()
//						.getResourceAsStream("font/汉仪小隶书简.ttf");// Class类的一个方法，将本地的资源加载成一个输入流
//			}*/
//		//	BufferedInputStream bis = new BufferedInputStream(fi);
//		//	definiedFont = Font.createFont(Font.TRUETYPE_FONT, bis);// 使用TRUETYPE类型的字体来创建新的字体】、
//		//	Config.setProp(J2seFont.class, "definiedFont",""+x);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			if(fi!=null){
//				try {
//					fi.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	public final static int 微软雅黑=-1;
//	public final static int 萝莉体=0;
//	public final static int 华文行楷=1;
//	public final static int 汉仪舒同体简=2;
//	public final static int 汉仪中圆繁=3;
//	public final static int 汉仪小隶书简=4;
//}
