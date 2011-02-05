package org.json.test.compliance.std;

import java.util.List;

public interface JSONObjectMeta {
	static interface Entry {
		String getName();

		Object getValue();
	};

	/**
	 * Get JSON value from name.
	 * 
	 * @param name
	 * @return Must be one of the following: java.lang.String, java.lang.Number,
	 *         java.lang.Boolean, null,
	 *         org.json.test.compliance.std.JSONObjectMeta
	 *         org.json.test.compliance.std.JSONArrayMeta
	 */
	Object get(String name);

	/**
	 * Return all entries of this map.
	 * 
	 * @return All entries of this map. getValue() of each entry must be one of
	 *         the following: java.lang.String, java.lang.Number,
	 *         java.lang.Boolean, null,
	 *         org.json.test.compliance.std.JSONObjectMeta
	 *         org.json.test.compliance.std.JSONArrayMeta
	 */
	List<Entry> entries();
}
