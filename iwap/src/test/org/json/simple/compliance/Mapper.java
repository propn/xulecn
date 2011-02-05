package org.json.simple.compliance;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.test.compliance.helper.JSONArrayMetaImpl;
import org.json.test.compliance.helper.JSONObjectMetaImpl;
import org.json.test.compliance.std.JSONArrayMeta;
import org.json.test.compliance.std.JSONObjectMeta;
import org.leixu.iwap.json.JSONArray;
import org.leixu.iwap.json.JSONObject;

public class Mapper {
	public static Object transformObjectToImpl(JSONObjectMeta obj) {
		JSONObject result = new JSONObject();

		List<JSONObjectMeta.Entry> entries = obj.entries();
		for (int i = 0; i < entries.size(); i++) {
			JSONObjectMeta.Entry entry = entries.get(i);
			result.put(entry.getName(), transformToImpl(entry.getValue()));
		}
		return result;
	}

	public static Object transformArrayToImpl(JSONArrayMeta array) {
		JSONArray result = new JSONArray();
		for (int i = 0; i < array.size(); i++) {
			Object value = array.get(i);
			result.add(transformToImpl(value));
		}
		return result;
	}

	public static Object transformToImpl(Object value) {
		if (value == null)
			return null;
		else if (value instanceof String)
			return value;
		else if (value instanceof Number)
			return value;
		else if (value instanceof Boolean)
			return value;
		else if (value instanceof JSONObjectMeta) {
			return transformObjectToImpl((JSONObjectMeta) value);
		} else if (value instanceof JSONArrayMeta) {
			return transformArrayToImpl((JSONArrayMeta) value);
		}
		return null;
	}

	static Object transformObjectToStd(JSONObject obj) {
		JSONObjectMetaImpl result = new JSONObjectMetaImpl();
		Iterator iter = obj.entrySet().iterator();

		try {
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				Object value = entry.getValue();
				value = transformToStd(value);
				result.getMap().put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	static Object transformArrayToStd(JSONArray array) {
		JSONArrayMetaImpl result = new JSONArrayMetaImpl();

		try {
			for (int i = 0; i < array.size(); i++) {
				Object value = array.get(i);
				value = transformToStd(value);
				result.getList().add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static Object transformToStd(Object value) {
		if (value == null)
			return null;
		else if (value instanceof String)
			return value;
		else if (value instanceof Number)
			return value;
		else if (value instanceof Boolean)
			return value;
		else if (value instanceof JSONObject) {
			return transformObjectToStd((JSONObject) value);
		} else if (value instanceof JSONArray) {
			return transformArrayToStd((JSONArray) value);
		}
		return null;
	}
}
