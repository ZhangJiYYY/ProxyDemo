package com.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 控制台输出
 * @author zhangji
 *
 */
@SuppressWarnings("unchecked")
public class PrintUtil {
	
	/**
	 * 输出数组
	 * @param objArr	输出数组对象
	 * @param isLineFeed	是否换行输出单个对象
	 */
	public static void printArr(Object objArr[], Boolean isLineFeed) {
		System.out.println("---------------------------------------------\n");
		if(null == isLineFeed) isLineFeed = true;
		for(Object obj : objArr) {
			if(isLineFeed)
				System.out.println(obj);
			else
				System.err.print(obj + ", ");
		}
		System.out.println("\n---------------------------------------------");
	}
	
	/**
	 * 输出集合List
	 * @param lists	输出集合对象
	 * @param isLineFeed	是否换行输出单个对象
	 */
	public static void printList(Object lists, Boolean isLineFeed) {
		System.out.println("---------------------------------------------\n");
		if(null == isLineFeed) isLineFeed = true;
		List<Object> list = (List<Object>) lists;
		for(Object obj : list) {
			if(isLineFeed)
				System.out.println(obj);
			else
				System.err.print(obj + ", ");
		}
		System.out.println("\n---------------------------------------------");
	}
	
	/**
	 * 输出Map
	 * @param map	输出Map对象
	 * @param isLineFeed	是否换行输出单个对象
	 */
	public static void printMap(Object map, Boolean isLineFeed) {
		System.out.println("---------------------------------------------\n");
		if(null == isLineFeed) isLineFeed = true;
		Map<String, Object> m = (Map<String, Object>) map;
		Set<String> set = m.keySet();
		for(String key : set) {
			System.out.print("[key : " + key + " ---> value : ");
			if(isLineFeed)
				System.out.println(m.get(key) + "]");
			else
				System.err.print(m.get(key) + "], ");
		}
		System.out.println("\n---------------------------------------------");
	}
}
