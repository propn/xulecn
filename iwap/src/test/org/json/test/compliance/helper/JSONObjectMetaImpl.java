package org.json.test.compliance.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The underlining data structure is a TreeMap, so key value pairs are ordered by keys.
 */
import org.json.test.compliance.std.JSONObjectMeta;

public class JSONObjectMetaImpl implements JSONObjectMeta {
	private Map map = new TreeMap();

	static class ItemEntry implements Entry {
		private String name;
		private Object value;

		public ItemEntry(String name, Object value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public Object getValue() {
			return value;
		}

	};

	/**
	 * Return all entries of this map.
	 * 
	 * @return All entries of this map. getValue() of each entry must be one of
	 *         the following: java.lang.String, java.lang.Number,
	 *         java.lang.Boolean, null,
	 *         org.json.test.compliance.std.JSONObjectMeta
	 *         org.json.test.compliance.std.JSONArrayMeta
	 */
	public List<Entry> entries() {
		List<Entry> list = new ArrayList<Entry>();
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			list.add(new ItemEntry(entry.getKey().toString(), entry.getValue()));
		}
		return list;
	}

	/**
	 * Get JSON value from name.
	 * 
	 * @param name
	 * @return Must be one of the following: java.lang.String, java.lang.Number,
	 *         java.lang.Boolean, null,
	 *         org.json.test.compliance.std.JSONObjectMeta
	 *         org.json.test.compliance.std.JSONArrayMeta
	 */
	public Object get(String name) {
		return map.get(name);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}
