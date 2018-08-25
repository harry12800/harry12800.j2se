package cn.harry12800.j2se.component.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.sun.imageio.plugins.bmp.BMPImageReader;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;

import cn.harry12800.tools.Lists;
import cn.harry12800.tools.Maps;
import sun.net.www.protocol.jar.JarURLConnection;

/**
 * 图片资源装载器
 * @author Yuexin
 *
 */
public class ImageUtils {
	public static Map<String, BufferedImage> map = Maps.newHashMap();
	static {
		addImage(ImageUtils.class);
	}

	public static synchronized void addImage(Class<?> clazz) {
		String name = clazz.getName();
		name = "/" + name.replace(".", "/") + ".class";
		InputStream in = null;
		URL resource = ImageUtils.class.getResource(name);
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
						in = ImageUtils.class.getResourceAsStream("/" + name);
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
		BufferedImage bufferedImage = map.get(name);
		return bufferedImage != null ? bufferedImage : getResource(name);
	}

	private static BufferedImage getResource(String name) {
		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		BufferedImage read = null;
		try {
			read = ImageIO.read(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return read;
	}

	public static BufferedImage getByCustom(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g.dispose();
		g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.white);
		g.drawRoundRect(0, 1, width / 2, height - 2, 4, 4);
		g.drawLine(0, height / 4, width / 2, height / 4);
		g.drawLine(0, height / 4 * 3, width / 2, height / 4 * 3);
		g.dispose();
		return image;
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

	public static Icon getIcon(String name) {
		ImageIcon icon = new ImageIcon(getByName(name));
		return icon;
	}

	public static ImageIcon getPicture(String name) {
		//		ImageIcon icon = new ImageIcon(MyScrollBarUI.class.getClassLoader()
		//				.getResource("image/"+name));
		ImageIcon icon = new ImageIcon(getByName(name));
		return icon;
	}

	public static String getType(String path) throws Exception {
		File file = new File(path);
		byte[] buf = new byte[10240];
		FileInputStream fi = new FileInputStream(file);
		fi.read(buf);
		String contentType = getContentType(buf);
		fi.close();
		return contentType;
	}

	// Returns the format of the image in the file 'f'.
	// Returns null if the format is not known.
	public static String getFormatInFile(File f) {
		return getFormatName(f);
	}

	// Returns the format name of the image in the object 'o'.
	// Returns null if the format is not known.
	private static String getFormatName(Object o) {
		try {
			// Create an image input stream on the image
			ImageInputStream iis = ImageIO.createImageInputStream(o);
			// Find all image readers that recognize the image format
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				// No readers found
				return null;
			}
			// Use the first reader
			ImageReader reader = iter.next();

			// Close stream
			iis.close();

			// Return the format name
			return reader.getFormatName();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

		// The image could not be read
		return null;
	}

	public static String getContentType(byte[] mapObj) throws IOException {
		String type = "";
		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mcis = null;
		try {
			bais = new ByteArrayInputStream(mapObj);
			mcis = new MemoryCacheImageInputStream(bais);
			Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
			while (itr.hasNext()) {
				ImageReader reader = (ImageReader) itr.next();
				if (reader instanceof GIFImageReader)
					type = "image/gif";
				else if (reader instanceof JPEGImageReader)
					type = "image/jpeg";
				else if (reader instanceof PNGImageReader)
					type = "image/png";
				else if (reader instanceof BMPImageReader)
					type = "application/x-bmp";
			}
		} finally {
			if (bais != null)
				try {
					bais.close();
				} catch (IOException ioe) {
				}
			if (mcis != null)
				try {
					mcis.close();
				} catch (IOException ioe) {
				}
		}
		return type;
	}
}
