package com.hptpd.taskdispatcherserver.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.*;

public class JsonUtil {
	private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JsonUtil.class.getName());

	public static <T> String objectToJson(T obj) {
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		try {
			result = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public static <T> List<T> jsonToList(String jsonString, Class<List> list,
			Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		List<T> resultList = new ArrayList<>();
		JavaType javaType = getCollectionType(mapper, List.class, clazz);
		try {
			resultList = mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}

	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since jquery.0
	 */
	public static JavaType getCollectionType(ObjectMapper mapper,
                                             Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass,
				elementClasses);
	}

	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(String jsonString, Class<T> clazz) {
		T obj = null;
		try {
			obj = (T) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		try {
			obj = mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 返回dataDrid数据
	 * @param list
	 * @param <T>
     * @return
     */
	public static <T> Map<String, Object> easyuiJsonFormatter(Collection<T> list) {
		Map<String, Object> map = Maps.newLinkedHashMap();
		if (!CollectionUtil.nullListCheck(list)) {
			map.put("total", list.size());
			map.put("rows", list);
		}
		return map;
	}
	/**
	 * 返回带有footer统计功能的datagrid数据
	 * @param list	待显示的数据
	 * @param listCount    footer中显示的统计数据
	 * @param <T> 类型信息
     * @return
     */
	public static <T> Map<String, Object> easyuiJsonWithFooter(Collection<T> list, Collection<T> listCount) {
		Map<String, Object> map = Maps.newLinkedHashMap();
		if (!CollectionUtil.nullListCheck(list)) {
			map.put("total", list.size());
			map.put("rows", list);
		}
		if (listCount != null) {
			map.put("footer", listCount);
		}
		return map;
	}

	/**
	 * 返回列表json数据
	 * @param page
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, Object> easyuiJsonForm(Page<T> page) {
		Map<String, Object> map = new HashMap<>();
		if (!CollectionUtil.nullPageCheck(page)) {
			map.put("total", page.getTotalElements());
			map.put("rows", page.getContent());
		}
		return map;
	}
	public static <T> Map<String, Object> resultForm(List<T> list) {
		Map<String, Object> map = new HashMap<>();
		if (!CollectionUtil.nullListCheck(list)) {
			map.put("total", list.size());
			map.put("rows", list);
		}
		return map;
	}

}
