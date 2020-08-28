package com.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class ReflectUtil {

	private ReflectUtil() {
	}

	public static <T> T getObject(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}

	public static Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> cls = Class.forName(className);

		return cls.newInstance();
	}

	/**
	 * 生成对象
	 * @param map：key属性名->value值
	 * @return
	 * @throws Exception 
	 */
	public static <T> T getObject(Class<T> clazz, Map<String, Object> map) throws Exception{
		Set<String> set = map.keySet();
		T tt = getObject(clazz);
		for (String key : set) {
			try {
				Field fieldDec = clazz.getDeclaredField(key);// 这里必须调用getDeclaredFiedl();
				fieldDec.setAccessible(true);// 是否取消java的语法检查，因为此变量是私有的，如果不取消则无法赋值
				Object value = map.get(key);
				fieldDec.set(tt, value);
			} catch (NoSuchFieldException | SecurityException e) {
				throw new Exception(e.getMessage()+ "\n" + clazz + ":" + key + " is not found!");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new Exception(e.getMessage() + "\n赋值失败!");
			} 
		}
		return tt;
	}

	/**
	 *  设置obj 内属性field值为v
	 * @param obj
	 * @param field
	 * @param v
	 * @throws Exception
	 */
	public static void setFieldValue(Object obj, Field field, Object v) throws Exception {
		try {
			if(null == v) {
				throw new Exception();
			}
			field.setAccessible(true);
			field.set(obj, v);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
