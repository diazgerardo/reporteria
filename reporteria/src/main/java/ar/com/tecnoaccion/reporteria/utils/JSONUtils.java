package ar.com.tecnoaccion.reporteria.utils;

import java.util.List;
import java.util.Map;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class JSONUtils {

	/* libreria estatica */
	private JSONUtils() {}
	
	public static JSONArray toJSONArray(List<Map<String, Object>> list) {
		JSONArray json_arr=new JSONArray();
		    for (Map<String, Object> map : list) {
		        JSONObject json_obj=new JSONObject();
		        for (Map.Entry<String, Object> entry : map.entrySet()) {
		            String key = entry.getKey();
		            Object value = entry.getValue();
		            try {
		                json_obj.put(key,value);
		            } catch (JSONException e) {
		                e.printStackTrace();
		            }                           
		        }
		        json_arr.put(json_obj);
		    }
		return json_arr;
	}

}
