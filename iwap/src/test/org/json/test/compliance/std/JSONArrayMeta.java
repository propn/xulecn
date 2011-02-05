package org.json.test.compliance.std;

public interface JSONArrayMeta {
	/**
	 * Get JSON value at postion i.
	 * 
	 * @param i
	 *            - postion, zero based.
	 * @return Must be one of the following: java.lang.String, java.lang.Number,
	 *         java.lang.Boolean, null,
	 *         org.json.test.compliance.std.JSONObjectMeta
	 *         org.json.test.compliance.std.JSONArrayMeta
	 */
	Object get(int i);

	/**
	 * @return Size of this array
	 */
	int size();
}
