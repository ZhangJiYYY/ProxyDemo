package com.util;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private StringUtil() {

    }
    /**
     * "file:/home/whf/cn/fh" -> "/home/whf/cn/fh"
     * "jar:file:/home/whf/foo.jar!cn/fh" -> "/home/whf/foo.jar"
     */
    public static String getRootPath(URL url) throws Exception{
        try {
            String fileUrl = url.getFile();
            int pos = fileUrl.indexOf('!');

            if (-1 == pos) {
                return fileUrl;
            }

            return fileUrl.substring(5, pos);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * "cn.fh.lightning" -> "cn/fh/lightning"
     * @param name
     * @return
     */
    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    /**
     * "Apple.class" -> "Apple"
     */
    public static String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }

    /**
     * /application/home -> /home
     * @param uri
     * @return
     */
    public static String trimURI(String uri) {
        String trimmed = uri.substring(1);
        int splashIndex = trimmed.indexOf('/');

        return trimmed.substring(splashIndex);
    }

    /**
     * 判断字符串是否为空：null、""均为空
     * 不允许空格：" "也为空
     *
     * @param str	需判断的字符串
     * @return	判断结果：true表示为空
     */
    public static boolean isEmpty(String str) {
        return isEmpty(str, false);
    }

    /**
     * 判断字符串是否为空：null、""均为空<br />
     * 不允许空格：" "也为空
     *
     * @param str	需判断的字符串
     * @param isExistSpace	是否允许存在空格
     * @return	判断结果：true表示为空
     */
    public static boolean isEmpty(String str, boolean isExistSpace) {
        if(null == str)	//对象为空
            return true;
        if("".equals(str) || str.length() == 0)	//对象长度为0
            return true;
        if(!isExistSpace && "".equals(str.trim())){	//允许存在空格
            return true;
        }
        return false;
    }

    /**
     * 字符串过滤
     *
     * @param str	待过滤字符串
     * @param replaceStr	替换的字符串
     * @param reg	过滤格式
     * @param type 2： 忽略大小写	1：全局匹配
     * @return	过滤后的字符串
     */
    public static String filterStr(String str, String replaceStr, String reg, Integer type) {

        if(isEmpty(str) || isEmpty(replaceStr) || isEmpty(reg)) {
            return str;
        }
        /*
         * CASE_INSENSITIVE	忽略大小写
         */
        Pattern pat = null;
        if(type == 1)
            pat = Pattern.compile(reg, Pattern.DOTALL);
        if(type == 2)
            pat = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pat.matcher(str);
        if(matcher.find()) {
            str = matcher.replaceAll(replaceStr);
        }

        return str;
    }

    /**
     * 字符串分割为字符串数组
     *
     * @param str
     * @param decollator
     * @return
     */
    public static String[] toArray(String str, String decollator) {
        if(isEmpty(str) || isEmpty(decollator))	//验证字符串是否为空
            return null;
        if(str.lastIndexOf(decollator) == str.length() - 1) //需分割的字符串最后一个字符不能为分隔符
            str = str.substring(0, str.length() - 1);
        if(isEmpty(str) || isEmpty(decollator))
            return null;
        if(str.lastIndexOf(decollator) == 0)	//需分割的字符串第一个字符不能为分隔符
            str = str.substring(1, str.length());
        if(isEmpty(str) || isEmpty(decollator))
            return null;
        String[] array = null ;
        if(str.indexOf(decollator) < 0) {
            array = new String[1];
            array[0] = str;
        } else {
            String temp = "";
            for(char c : decollator.toCharArray()) {
                if(c == '.' || c == '|' || c == '_' || c == '*' || c == '+') {
                    temp += "\\" + c;
                } else {
                    temp += c;
                }
            }
            array = str.split(temp);
        }

        return array;
    }

    /**
     * 首字母小写
     * @param oldStr
     * @return
     */
    public static String lowerFirst(String oldStr){

        char[]chars = oldStr.toCharArray();

        chars[0] += 32;

        return String.valueOf(chars);

    }

    /**
     * 字符串过滤
     *
     * @param str	待过滤字符串
     * @param reg	过滤格式
     * @param type 2： 忽略大小写	1：全局匹配
     * @return
     */
    public static boolean isIncludeStr(String str, String reg, Integer type) {
        if(isEmpty(str)) {
            return false;
        }

        /*
         * CASE_INSENSITIVE	忽略大小写
         */
        Pattern pat = null;
        if(type == 1)
            pat = Pattern.compile(reg, Pattern.DOTALL);
        if(type == 2)
            pat = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pat.matcher(str);
        return matcher.find();
    }
}