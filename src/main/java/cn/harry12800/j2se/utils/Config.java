package cn.harry12800.j2se.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.Maps;

public class Config {
	static Map<String, String> map = Maps.newHashMap();
	static String path = System.getProperty("user.dir") + File.separator + "config.properties";
	static {
		try {
			if (!new File(path).exists())
				FileUtils.createFile(path);
			map = FileUtils.properties2Map(new FileInputStream(new File(path)));
			FileUtils.map2Properties(map, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
