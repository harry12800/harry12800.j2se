package cn.harry12800.j2se.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.MachineUtils;

public class Config {
	static String path = homePath() + File.separator + "config.properties";
	static Properties p = new Properties();
	static {
		load();
	}

	public static void load() {
		try (InputStream stream = Config.class.getResourceAsStream("/config.properties");) {
			if (!new File(path).exists() && stream != null)
				FileUtils.inputStream2File(path, stream);
			if (!new File(path).exists() && stream == null)
				FileUtils.createFile(path);
			p.load(new FileInputStream(new File(path)));
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
		return p.getProperty(key);
	}

	public static int getIntProp(String key) {
		return Integer.valueOf(p.getProperty(key));
	}
	public static boolean getBooleanProp(String key) {
		return Boolean.valueOf(p.getProperty(key));
	}
	public static String getPropForce(String key) {
		load();
		return p.getProperty(key);
	}

	public synchronized static void setProp(String key, String value) {
		p.put(key, value);
		try (FileOutputStream fos = new FileOutputStream(new File(path));) {
			p.store(fos, "配置文件");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void setProp(Class<?> clazz, String propName, String propVal) {
		setProp(clazz.getName() + "." + propName, propVal);
	}

	public static String getProp(Class<?> clazz, String propName) {
		return getProp(clazz.getName() + "." + propName);
	}

	public static String getPropForce(Class<?> clazz, String propName) {
		load();
		return getProp(clazz.getName() + "." + propName);
	}
}
