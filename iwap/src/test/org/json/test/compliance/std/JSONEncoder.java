package org.json.test.compliance.std;

public interface JSONEncoder {
	/**
	 * Encode a meta graph into JSON text.
	 * 
	 * @param graph
	 *            - Can be one of the following:
	 *            org.json.test.compliance.std.JSONObjectMeta,
	 *            org.json.test.compliance.std.JSONArrayMeta
	 * @return JSON text
	 */
	String toJSONString(Object graph);
}
