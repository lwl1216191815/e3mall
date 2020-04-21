package cn.e3mall.common.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json转换工具类
 * @author Dragon
 *
 */
public class JsonUtils implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 定义jackson对象
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	/**
	 * 将对象转换为json
	 * @param o
	 * @return
	 */
	public static String objectToJson(Object o) {
		try {
			String json = MAPPER.writeValueAsString(o);
			return json;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将json转为Object
	 * @param json
	 * @param bean
	 * @return
	 */
	public static <T> T jsonToObject(String json,Class<T> bean) {
		try {
			T t = MAPPER.readValue(json, bean);
			return t;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * json转为List
	 * @param json
	 * @param bean
	 * @return
	 */
	public static <T>List<T> jsonToList(String json,Class<T> bean){
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, bean);
		try {
			List<T> list = MAPPER.readValue(json, javaType);
			return list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Collections.EMPTY_LIST;
	}
}
