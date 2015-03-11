package com.dianfeng.utils;
/**
 * json格式转换
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.google.gson.Gson;


public class JsonHelp {
	
	public static String mapToJson(Map<String,String> map){
	    Set<String> keys = map.keySet();
	    String key = "";
	    String value = "";
	    StringBuffer jsonBuffer = new StringBuffer();
	    jsonBuffer.append("{");    
	    for(Iterator<String> it = keys.iterator();it.hasNext();){
	        key =  (String)it.next();
	        value = map.get(key);
	        jsonBuffer.append(key+":"+value);
	        if(it.hasNext()){
	             jsonBuffer.append(",");
	        }
	    }
	    jsonBuffer.append("}");
	    return jsonBuffer.toString();
	}
	
	public static <T> String objectToJsonStr(T object) {
		if(object == null) return null;
		String jsonStr = JSONArray.fromObject(object).toString();
		jsonStr = jsonStr.substring(1, jsonStr.length()-1);
//		if (jsonStr.indexOf("[") != -1) {
//			jsonStr = jsonStr.replace("[", "");
//		}
//		if (jsonStr.indexOf("]") != -1) {
//			jsonStr = jsonStr.replace("]", "");
//		}
		return jsonStr;
	}

	/**
	 * JSONbject 使用方法 强制转换成传入的 jsonStr 类型 SimInfo
	 * simInfo=(SimInfo)JSONObject.toBean(obj, SimInfo.class);
	 */
	public static <T>Object strToJsonObject(Class<T> cla,String jsonStr) {
		if (jsonStr.indexOf("[") != -1 &&  jsonStr.indexOf("[") == 0) {
			jsonStr = jsonStr.substring(1,jsonStr.length()-1);
		}
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Gson gson = new Gson();
		jsonStr = gson.toJson(jsonObject);
		T obj = (T) (gson.fromJson(jsonStr ,cla));
		return obj;
	}

	/**
	 * List<SimInfo> simInfos = jsonStrToJsonObjectList(SimInfo.class,jsons);
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> jsonStrToJsonObjectList(Class<T> cla, String jsons) {
		List<T> objs = null;
		JSONArray jsonArray = JSONArray.fromObject(jsons);
		if (jsonArray != null) {
			objs = new ArrayList<T>();
			List<T> list = (List<T>) JSONSerializer.toJava(jsonArray);
			for (Object o : list) {
				JSONObject jsonObject = JSONObject.fromObject(o);
				Gson gson = new Gson();
				String jsonStr = gson.toJson(jsonObject);
				T obj = (T) (gson.fromJson(jsonStr ,cla));
				objs.add(obj);
			}
		}
		return objs;
	}
	/** list to */
	public static <T> String listToJsonStr(List<T> list){
		if(list == null) return null;
		if(list.size() == 0) return null;
		return JSONArray.fromObject(list).toString();  
	}
	//去掉自包含
	public static <T> String listCycleToJsonString(List<T> list){
		if(list == null) return null;
		if(list.size() == 0) return null;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		return JSONArray.fromObject(list, jsonConfig).toString();
	}
}
