package cn.harry12800.j2se.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.MachineUtils;
import cn.harry12800.tools.Maps;

public class Config {
	static Map<String, String> map = Maps.newHashMap();
	static String path = homePath() + File.separator + "config.properties";
	static {
		try (InputStream stream = Config.class.getResourceAsStream("/config.properties");) {
			if (!new File(path).exists())
				FileUtils.inputStream2File(path, stream);
			Properties p = new Properties();
			p.load(new FileInputStream(new File(path)));
			Enumeration<?> en=p.propertyNames();
	         while (en.hasMoreElements()) {
	             String key=(String) en.nextElement();
	             String property=p.getProperty(key);
	             map.put(key, property);
	             System.out.println(key + "."+property);
	         }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String homePath() {
		boolean byClass = MachineUtils.getByClass(Config.class);
		String homePath = "";
		if (byClass) {
			String clazz = System.getProperty("sun.java.command");
			System.err.println("sun.java.command: " + clazz);
			File file = new File(clazz);
			// MachineUtils.printSystemProperties();
			if (file.exists()) {
				File file2 = new File(file.getAbsolutePath());
				File parentFile = file2.getParentFile();
				homePath = parentFile.getAbsolutePath();
			} else {
				homePath = System.getProperty("user.dir");
			}
		} else {
			homePath = System.getProperty("user.dir");
		}
		return homePath;
	}

	public static String getProp(String key) {
		return map.get(key);
	}

	public synchronized static void setProp(String key, String value) {
		FileUtils.appendContent(path, key + "=" + value + "\r\n");
		map.put(key, value);
	}

	public synchronized static void setProp(Class<?> class1, String propName,
			String propVal) {
		setProp(class1.getName() + "." + propName, propVal);
	}

	public static String getProp(Class<?> class1, String propName) {
		return getProp(class1.getName() + "." + propName);
	}
}
