package com.hptpd.taskdispatcherserver.common.util;

import org.apache.commons.lang3.ArrayUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

/**
 * 字符串工具类
 * 
 * @author tracy
 * @version 1.0
 * @since 2012-10-26
 */
public class StringUtil {

	/**
	 * 去掉字符串首尾部空格
	 * 
	 * @param s
	 * @return
	 */
	public static String trim(String s) {
		int i = s.length();// 字符串最后一个字符的位置
		int j = 0;// 字符串第一个字符
		int k = 0;// 中间变量
		char[] arrayOfChar = s.toCharArray();// 将字符串转换成字符数组
		while ((j < i) && (arrayOfChar[(k + j)] <= ' ')) {
            ++j;// 确定字符串前面的空格数
        }
		while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' ')) {
            --i;// 确定字符串后面的空格数
        }
		return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);// 返回去除空格后的字符串
	}

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null) = true</li>
	 * <li>SysUtils.isEmpty("") = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value
	 *            待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		if (value == null || "".equals(value.trim())) {
			return true;
		}
		return false;
	}

	public static String changePdfSuffix(String name) {
		String str = name.substring(0, name.lastIndexOf(".")) + ".pdf";
		return str;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值
	 * 
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualArr(Object value, Object... equalArr) {
		String result = null;
		if (value == null || "".equals(result = value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if (result.equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值[不区分大小写]
	 * 
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualsIcArr(Object value, Object... equalArr) {
		String result = null;
		if (value == null
				|| "".equalsIgnoreCase(result = value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if (result.equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值
	 * 
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualArrSin(Object value, Object[] equalArr) {
		String result = null;
		if (value == null || "".equals(result = value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if (result.equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否不等于一个数组中的一个值
	 * 
	 * @param value
	 * @param notEqualArr
	 * @return
	 */
	public static boolean isNotEqualArr(Object value, Object... notEqualArr) {
		String result = null;
		if (value == null || "".equals(result = value.toString().trim())) {
			return true;
		}
		for (Object object : notEqualArr) {
			if (result.equals(object.toString())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串是否不为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null) = false</li>
	 * <li>SysUtils.isEmpty("") = false</li>
	 * <li>SysUtils.isEmpty("   ") = false</li>
	 * <li>SysUtils.isEmpty("abc") = true</li>
	 * </ul>
	 * 
	 * @param value
	 *            待检查的字符串
	 * @return true/false
	 */
	public static boolean isNotEmpty(String value) {
		if (value != null && !"".equals(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 检查对象是否为数字型字符串。
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		String str = obj.toString();
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumber(String args) {

		return args.matches("-*\\d+\\.?\\d*");

	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 把通用字符编码的字符串转化为汉字编码。
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

	/**
	 * 过滤不可见字符
	 */
//	public static String stripNonValidXMLCharacters(String input) {
//		if (input == null || ("".equals(input)))
//			return "";
//		StringBuilder out = new StringBuilder();
//		char current;
//		for (int i = 0; i < input.length(); i++) {
//			current = input.charAt(i);
//			if ((current == 0x9) || (current == 0xA) || (current == 0xD)
//					|| ((current >= 0x20) && (current <= 0xD7FF))
//					|| ((current >= 0xE000) && (current <= 0xFFFD))
//					|| ((current >= 0x10000) && (current <= 0x10FFFF)))
//				out.append(current);
//		}
//		return out.toString();
//	}

	/**
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}

		return join(array, separator, 0, array.length);
	}

	/**
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 */
	public static String join(Object[] array, char separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return "";
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex]
				.toString().length()) + 1);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * 字符串以指定字符分隔,然后随机获取一个字符串
	 * 
	 * @param string
	 * @param split
	 * @return
	 */
	public static String getStrsRandomStr(String string, String split) {
		if (isEmpty(string)) {
			return "";
		}
		int len = string.length();
		if (split.equals(string.substring(len - 1, len))) {
			string = string.substring(0, len - 1);
		}
		String[] arr = string.split(split);
		if (arr.length == 0) {
			return "";
		}
		int index = (int) (Math.random() * arr.length);
		if (index >= arr.length) {
			index = arr.length - 1;
		}
		return arr[index];
	}

	/**
	 * 去掉前后所有小于该char的字符，如最后字符为(char)1，而参数传为(char)2，同样(char)1会被删除。
	 * 
	 * @param str
	 * @param ch
	 * @return
	 */
	public static String trim(String str, char ch) {
		int i = str.length();
		int j = 0;
		int k = 0;
		char[] arrayOfChar = str.toCharArray();
		while ((j < i) && (arrayOfChar[(k + j)] <= ' ')) {
            ++j;
        }
		while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' ')) {
            --i;
        }
		return (((j > 0) || (i < str.length())) ? str.substring(j, i) : str);
	}

	/**
	 * 将字符串的json不能识别字符转为可识别字符
	 * 
	 * @param string
	 * @return
	 */
	public static String cvtJsonIglChar(String string) {
		StringBuffer str = new StringBuffer();
		str.append(string.replaceAll("	", " "));
		return str.toString();
	}

	/**
	 * 将数组转换成用逗号分隔的字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String formatComma(List<?> strs) {
		// 删除集合中空节点
		strs.remove(null);
		String str = ArrayUtils.toString(strs);
		return str.substring(1, str.length() - 1);
	}

	/**
	 * 将数组转换成用逗号分隔的字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String formatComma(Object[] strs) {
		// 删除集合中空节点
		ArrayUtils.removeElement(strs, null);
		String str = ArrayUtils.toString(strs);
		return str.substring(1, str.length() - 1);
	}

	/**
	 * 判断一个list是否为空
	 * 
	 * @param list
	 * @return
	 */
	public static <T> boolean nullListCheck(List<T> list) {
		boolean flag = false;
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}

		return sb.toString();

	}

//	public static void main(String[] args) {
//		String str = (char) 1 + "dasda" + (char) 2 + "sdasd" + (char) 1
//				+ "asdasdas" + (char) 1 + (char) 1 + (char) 1;
//		System.out.println(str);
//		System.out.println(trim(str, (char) 3));
//		System.out.println("isEmpty: " + isEmpty(null));
//		System.out.println("isNotEmpty: " + isNotEmpty("s"));
//		System.out.println("isEqualArr: " + isEqualArr(2, 20521, 20513, 10024));
//		System.out.println("isNotEqualArr: " + isNotEqualArr(null, 1, 2, 3));
//		System.out.println("getStrsRandomStr: "
//				+ getStrsRandomStr("哈哈;嗯嗯;", ";"));
//	}
public static String hashKeyForDisk(String key) {
	String cacheKey;
	try {
		final MessageDigest mDigest = MessageDigest.getInstance("MD5");
		mDigest.update(key.getBytes());
		cacheKey = bytesToHexString(mDigest.digest());
	} catch (NoSuchAlgorithmException e) {
		cacheKey = String.valueOf(key.hashCode());
	}
	return cacheKey;
}

	private static String bytesToHexString(byte[] bytes) {
		// http://stackoverflow.com/questions/332079
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
}