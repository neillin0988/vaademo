package com.prototype.vaadin.core.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prototype.vaadin.core.Context.DateFormat;

/**
 * json parser util 
 * 
 * @author neil.lin
 *
 */
public class JsonUtils {
	
	private static Gson gson;
	
	/**
	 * parse obj to json
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static String obj2Json(Object obj) {
		return getGsonInstance().toJson(obj);
	}
	

	/**
	 * parse json to obj of type instance
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T json2Obj(String json, Type type) {
		return getGsonInstance().fromJson(json, type);
	}

	/**
	 * parse json to obj of class instance
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T json2Obj(String json, Class<T> classOfT) {
		return getGsonInstance().fromJson(json, classOfT);
	}

	/**
	 * parse json to Map<String, Object>
	 * 
	 * @param json
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Mp(String json) {
		return json2Obj(json, HashMap.class);
	}
	
	/**
	 * parse json to List<Map<String, Object>>
	 * 
	 * @param json
	 * @return List<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> json2Ls(String json) {
		return json2Obj(json, ArrayList.class);
	}
	
	/**
	 * get Gson Instance
	 * 
	 * @return Gson
	 */
	private static Gson getGsonInstance() {
		if (gson == null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			
			// Date format
			gsonBuilder.setDateFormat(DateFormat.DATE_ONLY.getFormat()); 
			// out put pretty string
			gsonBuilder.setPrettyPrinting();
			
			gson = gsonBuilder.create();
		}
		
		return gson;
	}

}
