package com.hptpd.taskdispatcherserver.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CollectionUtil {

    public static <T> List<T> setToList(Set<T> set) {
        if (set != null) {
            List<T> list = Lists.newLinkedList();
            list.addAll(set);
            return list;
        } else {
            return null;
        }

    }

    public static <T> Set<T> listToSet(List<T> list) {
        Set<T> set = Sets.newLinkedHashSet();
        set.addAll(list);
        return set;
    }

    /**
     * 判断一个list是否为空
     *
     * @param list
     * @return
     */
    public static <T> boolean nullListCheck(Collection<T> list) {
        boolean flag = false;
        if (list == null || list.size() == 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断一个page是否为空
     *
     * @param page
     * @return
     */
    public static <T> boolean nullPageCheck(Page<T> page) {
        boolean flag = false;
        if (page == null || page.getContent().isEmpty() || page.getSize() == 0) {
            flag = true;
        }
        return flag;
    }


    /**
     * 去除list中的重复项
     *
     * @param list
     * @return
     */
    public static <T> List<T> removeDuplicate(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 获取对象属性，返回一个字符串数组
     *
     * @param o 对象
     * @return String[] 字符串数组
     */
    private static String[] getFiledName(Object o) {
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            String[] fieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fieldNames[i] = fields[i].getName();
            }
            return fieldNames;
        } catch (SecurityException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return null;
    }

    /**
     * 使用反射根据属性名称获取t属性的get方法
     *
     * @param fieldNames 属性名称
     * @param o          操作对象
     * @return List<Method> get方法
     */

    private static List<Method> getGetField(String[] fieldNames, Object o) {

        List<Method> methods = new ArrayList<Method>();
        for (String fieldName : fieldNames) {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = null;
            try {
                method = o.getClass().getMethod(getter, new Class[]{});
            } catch (NoSuchMethodException e) {
                System.out.println("属性不存在");
                continue;
            }
            //Object value = method.invoke(o, new Object[] {});
            methods.add(method);
        }
        return methods;

    }

    /**
     * 将list集合转换为二维string数组
     *
     * @param list 要转换的集合
     * @return String[][] 返回的sting数组
     */

    public static String[][] listToArrayWay(List list) {
        Object o = list.get(0);

        String[] filedNames = getFiledName(o);
        int filedNum = filedNames.length;
        int listSize = list.size();
        List<Method> methods = getGetField(filedNames, o);

        String[][] arrs = new String[listSize][filedNum];
        int i = 0;
        for (Object object : list) {
            int j = 0;
            for (Method method : methods) {
                Object value = null;
                try {
                    value = method.invoke(object, new Object[]{});
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("属性不存在" + e);
                }
                arrs[i][j] = (String) value;
                j++;
            }
            i++;
        }

        return arrs;
    }


    /**
     * 去除list中的重复项
     * @param list
     * @return
     */
//	public static  List<Item> removeDuplicateItem(List<Item> list) {
//		for (int i = 0; i < list.size() - 1; i++) {
//			for (int j = list.size() - 1; j > i; j--) {
//				if (list.get(j).getId() == list.get(i).getId()) {
//					list.remove(j);
//				}
//			}
//		}
//		return list;
//	}
}
