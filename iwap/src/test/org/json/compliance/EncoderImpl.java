package org.json.compliance;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.test.compliance.std.JSONEncoder;

public class EncoderImpl implements JSONEncoder {
	/**
	 * Encode obj into JSON text.
	 * 
	 * @param obj
	 *            - Can be one of the following:
	 *            org.json.test.compliance.std.JSONObjectMeta,
	 *            org.json.test.compliance.std.JSONArrayMeta
	 * @return JSON text
	 */
	public String toJSONString(Object obj) {
		Object implValue = Mapper.transformToImpl(obj);
		if (implValue instanceof JSONObject) {
			try {
				return ((JSONObject) implValue).toString(2);
			} catch (Exception e) {
				e.printStackTrace();
				return "{}";
			}
		} else {
			try {
				return ((JSONArray) implValue).toString(2);
			} catch (Exception e) {
				e.printStackTrace();
				return "[]";
			}
		}
	}

}
