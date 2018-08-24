package com.payudon.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class JsonWrapper {

	public static HashMap<String, Object> successWrapper(Object pojo) {
		HashMap<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("success", Boolean.valueOf(true));
		wrapper.put("data", pojo);
		return wrapper;
	}
	
	public static HashMap<String, Object> successWrapper(Object pojo,String msg) {
		HashMap<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("success", Boolean.valueOf(true));
		wrapper.put("data", pojo);
		wrapper.put("msg", msg);
		return wrapper;
	}
	
	public static HashMap<String, Object> successWrapperMsg(String msg) {
		HashMap<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("success", Boolean.valueOf(true));
		wrapper.put("msg", msg);
		return wrapper;
	}

	public static HashMap<String, Object> failureWrapper(Object pojo) {
		HashMap<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("success", Boolean.valueOf(false));
		wrapper.put("data", pojo);
		return wrapper;
	}

	public static HashMap<String, Object> failureWrapperMsg(String msg) {
		HashMap<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("success", Boolean.valueOf(false));
		wrapper.put("msg", msg);
		return wrapper;
	}

	public static HashMap<String, Object> failureWrapper(Object... pojo) {
		return wrapper(false, pojo);
	}

	public static HashMap<String, Object> successWrapper(Object... pojo) {
		return wrapper(true, pojo);
	}

	public static HashMap<String, Object> wrapper(boolean success,
			Object... pojo) {
		HashMap<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("success", Boolean.valueOf(success));
		int length = pojo.length;
		if (pojo.length % 2 == 1) {
			length--;
		}
		length /= 2;
		for (int i = 0; i < length; i++) {
			wrapper.put(String.valueOf(pojo[(i * 2)]), pojo[(i * 2 + 1)]);
		}
		return wrapper;
	}

	public static HashMap<String, Object> wrapperMap(boolean success,
			Map<String, Object> map) {
		HashMap<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("success", Boolean.valueOf(success));
		wrapper.put("data", map);
		return wrapper;
	}

	public static Map<String, Object> wrapperDataRows(List<Object> dataRows) {
		HashMap<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("success", Boolean.valueOf(true));
		wrapper.put("Rows", dataRows);
		return wrapper;
	}
	  //转换为Jquery easyUI适配的Json数组
    public static HashMap<String,Object> wrapperPage(List<?> list,int total){
    	HashMap<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("total",total);
		wrapper.put("rows", list);
    	return wrapper;
    }
	//转换为Jquery easyUI适配的Json数组
	public static HashMap<String,Object> wrapperPage(Map<String,Object> map,int total){
		HashMap<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("total",total);
		wrapper.put("rows", map);
		return wrapper;
	}
}
