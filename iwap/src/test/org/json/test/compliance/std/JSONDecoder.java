package org.json.test.compliance.std;

public interface JSONDecoder {
	/**
	 * Parse jsonString into a meta graph.
	 * 
	 * @param jsonString
	 *            - JSON text
	 * @return Can be one of the following:
	 *         org.json.test.compliance.std.JSONObjectMeta,
	 *         org.json.test.compliance.std.JSONArrayMeta
	 */
	Object fromJSONString(String jsonString) throws DecodeException;
}
