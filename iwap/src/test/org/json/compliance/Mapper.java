package org.json.compliance;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.test.compliance.helper.JSONArrayMetaImpl;
import org.json.test.compliance.helper.JSONObjectMetaImpl;
import org.json.test.compliance.std.JSONArrayMeta;
import org.json.test.compliance.std.JSONObjectMeta;

public class Mapper {
	static Object transformObjectToStd(JSONObject obj) {
		JSONObjectMetaImpl result = new JSONObjectMetaImpl();
		Iterator iter = obj.keys();

		try {
			while (iter.hasNext()) {
				String key = (String) iter.next();
				Object value = obj.get(key);
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
			for (int i = 0; i < array.length(); i++) {
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
		if (JSONObject.NULL.equals(value))
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
		return JSONObject.NULL;
	}

	public static Object transformObjectToImpl(JSONObjectMeta obj) {
		JSONObject result = new JSONObject();

		List<JSONObjectMeta.Entry> entries = obj.entries();
		for (int i = 0; i < entries.size(); i++) {
			JSONObjectMeta.Entry entry = entries.get(i);
			try {
				result.put(entry.getName(), transformToImpl(entry.getValue()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static Object transformArrayToImpl(JSONArrayMeta array) {
		JSONArray result = new JSONArray();
		for (int i = 0; i < array.size(); i++) {
			Object value = array.get(i);
			result.put(transformToImpl(value));
		}
		return result;
	}

	public static Object transformToImpl(Object value) {
		if (value == null)
			return JSONObject.NULL;
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
		return JSONObject.NULL;
	}
}
