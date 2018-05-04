package cn.harry12800.j2se.component.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import cn.harry12800.j2se.component.btn.DIYButton;
import cn.harry12800.tools.Lists;
import cn.harry12800.tools.Maps;
import sun.net.www.protocol.jar.JarURLConnection;

/**
 * 图片资源装载器
 * @author Yuexin
 *
 */
@SuppressWarnings("restriction")
public class ImageUtils {
	public static Map<String, BufferedImage> map = Maps.newHashMap();
	static {
		addImage(ImageUtils.class);
	}

	public static void addImage(Class<?> clazz) {
		String name = clazz.getName();
		name = "/" + name.replace(".", "/") + ".class";
		InputStream in = null;
		URL resource = DIYButton.class.getResource(name);
		try {
			URLConnection openConnection = resource.openConnection();
			//			System.out.println(openConnection);
			if (openConnection instanceof sun.net.www.protocol.jar.JarURLConnection) {
				//				URL jarFileURL = ((JarURLConnection) openConnection).getJarFileURL();
				JarFile jarFile = ((JarURLConnection) openConnection).getJarFile();
				Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements()) {
					name = entries.nextElement().getName();
					Pattern p = Pattern.compile("image/((.*?)((.png)|(.PNG)|(.jpg))+)");
					Matcher matcher = p.matcher(name);
					if (matcher.find()) {
						String group = matcher.group(1);
						in = DIYButton.class.getResourceAsStream("/" + name);
						BufferedImage read = ImageIO.read(in);
						map.put(group, read);
						in.close();
					}

				}
			} else if (openConnection instanceof sun.net.www.protocol.file.FileURLConnection) {
				String replaceAll = resource.getFile().replaceAll(name, "");
				replaceAll = URLDecoder.decode(replaceAll, "UTF-8");
				replaceAll = replaceAll + File.separator + "image";
				File[] filterClassFiles = filterClassFiles(replaceAll);
				if (filterClassFiles != null)
					for (File file : filterClassFiles) {
						FileInputStream fileInputStream = new FileInputStream(file);
						BufferedImage read = ImageIO.read(fileInputStream);
						map.put(file.getName(), read);
						fileInputStream.close();
					}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private static File[] filterClassFiles(String pkgPath) {
		if (pkgPath == null) {
			return null;
		}
		// 接收 .class 文件 或 类文件夹  
		return new File(pkgPath).listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return (file.isFile() && (file.getName().endsWith(".png") || file.getName().endsWith(".jpg")));
			}
		});
	}

	public static BufferedImage getByName(String name) {
		return map.get(name);
	}

	public static void main(String[] args) {

		List<String> list = Lists.newArrayList();
		list.add("image/");
		list.add("image/desk.jpg");
		list.add("image/exit.png");
		list.add("image/exit_hover.png");
		list.add("image/harry12800.j2se.ini");
		list.add("image/logo.png");
		list.add("image/max.png");
		list.add("image/max_hover.png");
		list.add("image/min.png");
		list.add("image/min_hover.png");
		list.add("image/turn.jpg");
		list.add("image/update.jpg");
		list.add("image/update.png");
		list.add("image/view.jpg");
		list.add("image/view.png");
		list.add("image/warn.wav");
		for (String string : list) {
			Pattern p = Pattern.compile("image/((.*?)((.png)|(.PNG)|(.jpg))+)");
			Matcher matcher = p.matcher(string);
			if (matcher.find()) {
				System.out.println(matcher.group(1));
			}
		}
		//DeveloperUtils.generateCodeSuffixPrefix(ImageUtils.class, "list.add(\"", "\");", 99, 114);
	}
}
