package com.uib.common.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * @Title StringUtil
 * @Company: uib
 * @Copyright: Copyright(C) 2013
 * @Version   1.0
 * @author wanghuan
 * @date 2013年12月2日
 * @time 下午3:16:56
 * @Description
 */
public class StringUtil extends StringUtils {
	
	/**
	 * 如果参数字符串为NULL，返回""，否则返回参数本身
	 * @Title nvl
	 * @author wanghuan
	 * @param 
	 * @return String
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:18:12
	 * @Description
	 */
	public static String nvl(String sIn) {
		return (sIn == null) ? "" : sIn;
	}

	public static final int DIRECT_LEFT = 0;
	public static final int DIRECT_RIGHT = 1;

	/**
	 * 判断字符串是否为null或者为空字符串
	 * @Title isNullOrEmpty
	 * @author wanghuan
	 * @param 
	 * @return 是：true 否：false
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:18:44
	 * @Description
	 */
	public static boolean isNullOrEmpty(CharSequence argCharSeq) {

		if ((argCharSeq == null) || (argCharSeq.toString().trim().length() < 1)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断日期是否为null或者为空
	 * @Title isNullOrEmpty
	 * @author wanghuan
	 * @param 
	 * @return boolean
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:19:11
	 * @Description
	 */
	public static boolean isNullOrEmpty(Timestamp timestamp) {

		if ((timestamp == null) || (timestamp.toString().trim().length() < 1)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断两字符串是不是相同
	 * @Title equalsString
	 * @author wanghuan
	 * @param 
	 * @return boolean
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:19:20
	 * @Description
	 */
	public static boolean equalsString(String argStr1, String argStr2) {

		if ((argStr1 == null) && (argStr2 == null)) {
			return true;
		}
		if ((argStr1 == null) || (argStr2 == null)) {
			return false;
		}
		return argStr1.equals(argStr2);
	}

	/**
	 * 把字符串的首字母转换成小写
	 * @Title getFirstLowerCase
	 * @author wanghuan
	 * @param 
	 * @return String
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:19:30
	 * @Description
	 */
	public static String getFirstLowerCase(String argString) {

		if (isNullOrEmpty(argString)) {
			return "";
		}
		if (argString.length() < 2) {
			return argString.toLowerCase();
		}

		char ch = argString.charAt(0);
		ch = Character.toLowerCase(ch);

		return ch + argString.substring(1);
	}

	/**
	 * 把字符串的第一个字母转换成大写
	 * @Title getFirstUpperCase
	 * @author wanghuan
	 * @param 
	 * @return String
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:19:38
	 * @Description
	 */
	public static String getFirstUpperCase(String argString) {

		if (isNullOrEmpty(argString)) {
			return "";
		}
		if (argString.length() < 2) {
			return argString.toUpperCase();
		}

		char ch = argString.charAt(0);
		ch = Character.toUpperCase(ch);

		return ch + argString.substring(1);
	}

	/**
	 * 换行
	 * @Title appendLine
	 * @author wanghuan
	 * @param 
	 * @return void
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:20:02
	 * @Description
	 */
	public static void appendLine(StringBuffer argSbf) {
		argSbf.append(System.getProperty("line.separator"));
	}

	/**
	 * 格式化字符串成参数指定的格式
	 * @Title formatMsg
	 * @author wanghuan
	 * @param 
	 * @return String
	 * @throws 
	 * @date 2014年11月06日
	 * @time 下午3:20:10
	 * @Description
	 */
	public static String formatMsg(String src, Object... argParams) {
		return String.format(src, argParams);
	}

	/**
	 * 得到字符串的长度
	 * @Title getCount
	 * @author wanghuan
	 * @param 
	 * @return int
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:20:21
	 * @Description
	 */
	public static int getCount(String src, String strChar) {
		int result = 0;

		int beginIndex = 0;
		int endIndex = src.indexOf(strChar, beginIndex);

		while (endIndex >= 0) {
			result++;
			beginIndex = endIndex + strChar.length();
			endIndex = src.indexOf(strChar, beginIndex);
		}

		return result;
	}
	
	/**
	 * 替换字符串
	 * @Title replaceStr
	 * @author wanghuan
	 * @param 
	 * @return String
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:20:39
	 * @Description
	 */
	public static String replaceStr(String src, String sFnd, String sRep) {
		if (src == null || "".equals(nvl(sFnd))) {
			return src;
		}

		String sTemp = "";
		int endIndex = 0;
		int beginIndex = 0;
		do {
			endIndex = src.indexOf(sFnd, beginIndex);
			if (endIndex >= 0) { // mean found it.
				sTemp = sTemp + src.substring(beginIndex, endIndex) + nvl(sRep);
				beginIndex = endIndex + sFnd.length();
			} else if (beginIndex >= 0) {
				sTemp = sTemp + src.substring(beginIndex);
				break;
			}
		} while (endIndex >= 0);

		return sTemp;
	}

	/**
	 * 比较两个字符串
	 * @Title compare
	 * @author wanghuan
	 * @param 
	 * @return int
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:20:47
	 * @Description
	 */
	public static int compare(String argStr1, String argStr2) {
		if (argStr1 == null && argStr2 == null) {
			return 0;
		}
		if (argStr1 == null) {
			return -1;
		}
		if (argStr2 == null) {
			return 1;
		}

		return argStr1.compareTo(argStr2);
	}
	
	/**
	 * 比较两个字符串
	 * @Title paddingSpaceForChar
	 * @author wanghuan
	 * @param 
	 * @return String
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:20:53
	 * @Description
	 */
	public static String paddingSpaceForChar(String strIn, int len, int direct) {
		if (strIn == null) {
			return null;
		}
		int strInLen = getStrLength(strIn);
		if (strInLen == len) {
            return strIn;

        } else if (strInLen > len) {
            return chopStr(strIn, len);

        } else {
            StringBuffer sb = new StringBuffer((strIn == null ? "" : strIn));
            for (int i = 0; i < (len - strInLen); i++) {
                sb.append(" ");
            }
            return sb.toString();
        }
	}
	
	public static boolean isHalf(char c) {
		if (!(('\uFF61' <= c) && (c <= '\uFF9F'))
				&& !(('\u0020' <= c) && (c <= '\u007E'))) {
			return false;
		} else
			return true;
	}
	
	/**
	 * 返回字符串长度
	 * @Title getStrLength
	 * @author wanghuan
	 * @param 
	 * @return int
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:21:10
	 * @Description
	 */
	public static int getStrLength(String s) {
		if (s == null) {
			return 0;
		}

		int len = 0;
		for (int i = 0; i < s.length(); i++) {
			if (isHalf(s.charAt(i))) {
				len += 1;
			} else {
				len += 2;
			}
		}
		return len;
	}
	
	
	public static String chopStr(String s, int byteLen) {
		if (byteLen < 0) {
			return "";
		}
		if (s == null) {
			return null;
		}

		int len = 0;
		for (int i = 0; i < s.length(); i++) {
			if (isHalf(s.charAt(i))) {
				len += 1;
			} else {
				len += 2;
			}
			if (len > byteLen) {
				return s.substring(0, i);
			}
		}
		return s;
	}
	
	public static String toString(Object obj) {
		return obj == null ? "" : obj.toString();
	}
	
	/**
	 * 金额转换由','隔开的字符串
	 * @Title splitAmount
	 * @author wanghuan
	 * @param 
	 * @return String
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:21:36
	 * @Description
	 */
	public static String  splitAmount (BigDecimal decimal) {
		if(decimal == null) {
			return "";
		}
		//负数时，先将负号取出。使用其绝对值进行格式化，最后返回拼接后的字符串
		String sign = "";
		if(decimal.doubleValue() < 0) {
			sign = "-";
		}
		String str = decimal.setScale(2, BigDecimal.ROUND_HALF_UP).abs().toString();
		StringBuffer sb = new StringBuffer(str);
		int len = sb.length()-3;
		int i = 0;
		while(len > 3) {
			sb.insert(sb.length() -3 - (3 + i*4), ',');
			len -= 3;
			i++;
		}
		return sign+sb.toString();
	}
	
	
	/**
	 * String类型转换由,隔开的字符串
	 * @Title splitString
	 * @author wanghuan
	 * @param 
	 * @return String
	 * @throws 
	 * @date 2013年12月2日
	 * @time 下午3:21:47
	 * @Description
	 */
	public static String splitString (String str) {
		StringBuffer sb = new StringBuffer(str);
		int len = sb.length()-3;
		int i = 0;
		while(len > 3) {
			sb.insert(sb.length() -3 - (3 + i*4), ',');
			len -= 3;
			i++;
		}
		return sb.toString();
	}
	
	
	
	public static String replaceNullString(String str){
		  if(str == null ) return "";
		           else return str;
		}
}
