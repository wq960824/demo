package com.example.demo.tools;

import java.util.List;

public class StringUtils {
    /**
     * 判断给定字符串是否空白串
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0 ? true : false;
    }

    /**
     * 判断给定集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0 ? true : false;
    }

    public static boolean isEmpty(Object... obj) {
        return obj == null || obj.length == 0 ? true : false;
    }

    /**
     * 判断字符串不能为空
     *
     * @param integer
     * @return
     */
    public static boolean isNotEmpty(String integer) {
        return integer != null ? true : false;
    }

    /**
     * 处理字符串 返回一个以，隔开的字符串
     */
    public static String str(String s) {
        String ss = s.replace(",,", ",");
        while (ss.indexOf(",,") != -1) {
            ss = ss.replace(",,", ",");
        }
        return ss;
    }
    public static String str(List list){
        String replace=list.toString().replace("[","").replace("]","").trim().replace(" ","");
        while (String.valueOf(replace.charAt(replace.length()-1)).equals(",")){
            replace=replace.substring(0,replace.length()-1);
        }
       String s=replace.replace(",,",",");
        while (s.indexOf(",,")!=-1){
            s=s.replace(",,",",");
        }
        return s;
    }
}
