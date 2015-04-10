package com.manyi.iw.soa.common;

public class StringUtils {
    /**
     * 功能描述:判断字符串是否有值（不包含空串）
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月17日      新建
     * </pre>
     *
     * @param s
     * @return
     */
    public static boolean hasValue(String s){
        return s!=null && !"".equals(s);
    }
}
