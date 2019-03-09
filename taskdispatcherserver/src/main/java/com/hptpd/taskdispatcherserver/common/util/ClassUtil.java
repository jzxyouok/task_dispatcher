package com.hptpd.taskdispatcherserver.common.util;

import com.google.common.collect.Maps;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * class工具类
 * 
 * @author Tracy4262
 * @version jquery.0
 * @since 2012-10-26
 */
public class ClassUtil {

	private static Map<String, String> firstUpperClsNameMap = Maps.newLinkedHashMap();

	/**
	 * 获取class首字母小写的类名
	 * 
	 * @param cls
	 * @return
	 */
	public static String getFirstLowerClsName(Class<?> cls) {
		String fUpperClsName = firstUpperClsNameMap.get(cls.getName());
		if (fUpperClsName == null) {
			StringBuffer sb = new StringBuffer(cls.getSimpleName());
			sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
			fUpperClsName = sb.toString();
			firstUpperClsNameMap.put(cls.getName(), fUpperClsName);
		}
		return fUpperClsName;
	}

	/**
	 * 复制o2对象中不为空的属性值到o1对象属性值
	 * 
	 * @param o1
	 * @param o2
	 */
	public static <T> void copyPriperties(T o1, T o2) {

		String fileName, str, getName, setName;
		List fields = new ArrayList();
		Method getMethod = null;
		Method setMethod = null;
		try {
			Class<? extends Object> c1 = o1.getClass();
			Class<? extends Object> c2 = o2.getClass();

			Field[] fs1 = c1.getDeclaredFields();
			Field[] fs2 = c2.getDeclaredFields();
			// 两个类属性比较剔除不相同的属性，只留下相同的属性
			for (int i = 0; i < fs2.length; i++) {
				for (int j = 0; j < fs1.length; j++) {
					if (fs1[j].getName().equals(fs2[i].getName())) {
						fields.add(fs1[j]);
						break;
					}
				}
			}
			if (null != fields && fields.size() > 0) {
				for (int i = 0; i < fields.size(); i++) {
					// 获取属性名称
					Field f = (Field) fields.get(i);
					fileName = f.getName();
					// 属性名第一个字母大写
					str = fileName.substring(0, 1).toUpperCase();
					// 拼凑getXXX和setXXX方法名
					getName = "get" + str + fileName.substring(1);
					setName = "set" + str + fileName.substring(1);
					// 获取get、set方法
					getMethod = c1.getMethod(getName, new Class[] {});
					setMethod = c2.getMethod(setName,
							new Class[] { f.getType() });

					// 获取属性值
					Object o = getMethod.invoke(o1, new Object[] {});
					System.out.println(fileName + " : " + o);
					// 将属性值放入另一个对象中对应的属性
					if (null != o) {
						System.out.println("o2.setMethod = " + setMethod);
						setMethod.invoke(o2, new Object[] { o });
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static <T> String getFieldValueByString(Field field, T object)
			throws NoSuchMethodException, SecurityException, Exception {
		Serializable value = getFieldValue(field, object);
		if (value != null){
			if (value.getClass() == Float.class) {
                return String.valueOf(value);
            } else if (value.getClass() == String.class) {
                return (String) ((value == null) ? "" : value);
            }
		}
		
		return null;
		
	}

	public static <T> T setFieldValue(String fieldName, String value, T obj)
			throws NoSuchMethodException, Exception {

		Field field = obj.getClass().getDeclaredField(fieldName);

		Method m = (Method) obj.getClass().getMethod(
				"set" + getMethodName(field.getName()), field.getType());

//		if (StringUtil.isNumber(value))
//			m.invoke(obj, Float.valueOf(value));
//		else
			m.invoke(obj, value);// 调用getter方法获取属性值

		return obj;
	}

	@SuppressWarnings("unchecked")
	private static <T, E extends Serializable> E getFieldValue(Field field,
			T object) throws NoSuchMethodException, SecurityException,
			Exception {
		// 如果类型是String
		E value = null;

		if ("class java.lang.String".equals(field.getGenericType().toString())) {
			// 如果type是类类型，则前面包含"class "，后面跟类名
			// 拿到该属性的gettet方法
			/**
			 * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
			 * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX） 如果出现NoSuchMethod异常
			 * 就说明它找不到那个gettet方法 需要做个规范
			 */
			Method m = (Method) object.getClass().getMethod(
					"get" + getMethodName(field.getName()));

			value = (E) m.invoke(object);
			// 调用getter方法获取属性值
			if (value != null) {
				System.out.println("String type:" + value);
			}

		}

		// 如果类型是Integer
		if ("class java.lang.Integer".equals(field.getGenericType().toString())) {
			Method m = (Method) object.getClass().getMethod(
					"get" + getMethodName(field.getName()));
			value = (E) m.invoke(object);
			if (value != null) {
				System.out.println("Integer type:" + value);
			}

		}

		// 如果类型是Float
		if ("class java.lang.Float".equals(field.getGenericType().toString())) {
			Method m = (Method) object.getClass().getMethod(
					"get" + getMethodName(field.getName()));
			value = (E) m.invoke(object);
			if (value != null) {
				System.out.println("Float type:" + value);
			}

		}

		// 如果类型是Double
		if ("class java.lang.Double".equals(field.getGenericType().toString())) {
			Method m = (Method) object.getClass().getMethod(
					"get" + getMethodName(field.getName()));
			value = (E) m.invoke(object);
			if (value != null) {
				System.out.println("Double type:" + value);
			}

		}

		// 如果类型是Boolean 是封装类
		if ("class java.lang.Boolean".equals(field.getGenericType().toString())) {
			Method m = (Method) object.getClass().getMethod(field.getName());
			value = (E) m.invoke(object);
			if (value != null) {
				System.out.println("Boolean type:" + value);
			}

		}

		// 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
		// 反射找不到getter的具体名
		if ("boolean".equals(field.getGenericType().toString())) {
			Method m = (Method) object.getClass().getMethod(field.getName());
			value = (E) m.invoke(object);
			if (value != null) {
				System.out.println("boolean type:" + value);
			}

		}
		// 如果类型是Date
		if ("class java.util.Date".equals(field.getGenericType().toString())) {
			Method m = (Method) object.getClass().getMethod(
					"get" + getMethodName(field.getName()));
			value = (E) m.invoke(object);
			if (value != null) {
				System.out.println("Date type:" + value);
			}

		}
		// 如果类型是Short
		if ("class java.lang.Short".equals(field.getGenericType().toString())) {
			Method m = (Method) object.getClass().getMethod(
					"get" + getMethodName(field.getName()));
			value = (E) m.invoke(object);
			if (value != null) {
				System.out.println("Short type:" + value);
			}

		}
		// 如果还需要其他的类型请自己做扩展

		return value;

	}

	// 把一个字符串的第一个字母大写、效率是最高的、
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
}