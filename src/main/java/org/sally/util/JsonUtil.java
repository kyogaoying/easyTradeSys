package org.sally.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.sally.constant.EasyTradeConstants;

import java.util.List;

public class JsonUtil
{
    /**
     * 生成返回页面查询的JSON字符串,当callback不为空,则返回jsonp格式
     *
     * @param list
     * @param callback
     * @return
     */
    public static String genQueryJsonString(List<?> list, String callback)
    {
        int total = list.size();
        String result = JSON.toJSONString(list);
        JSONArray arr = JSON.parseArray(result);
        JSONObject obj = new JSONObject();
        obj.put("total", total);
        obj.put("rows", arr);
        result = JSON.toJSONString(obj);
        if(!StringUtils.isBlank(callback))
        {
            result = callback + "("+result+")";
        }

        return result;
    }

    public static String genQueryJsonString(List<?> list, int totalPages, String callback)
    {
	    	int total = list.size();
	    	String result = JSON.toJSONString(list);
	    	JSONArray arr = JSON.parseArray(result);
	    	JSONObject obj = new JSONObject();
	    	obj.put("total", total);
	    	obj.put("rows", arr);
	    	obj.put("totalPages", totalPages);
	    	result = JSON.toJSONString(obj);
	    	if(!StringUtils.isBlank(callback))
	    	{
	    		result = callback + "("+result+")";
	    	}
	    	
	    	return result;
    }

}
