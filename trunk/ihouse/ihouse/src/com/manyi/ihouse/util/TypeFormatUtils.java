package com.manyi.ihouse.util;

public class TypeFormatUtils {
    
    /**
     * 
     * 功能描述:装修情况中文描述
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月17日      新建
     * </pre>
     *
     * @param decorateType
     * @return
     */
    public static String decorateTypeStr(int decorateType){
        
        if(decorateType==1){
            return "毛坯";
        } else if(decorateType==2){
            return "精装修";
        } else{
            return "";
        }
    }

}
