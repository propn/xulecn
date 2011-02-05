package org.json.test.compliance.helper;

import java.util.ArrayList;
import java.util.List;

import org.json.test.compliance.std.JSONArrayMeta;

public class JSONArrayMetaImpl implements JSONArrayMeta {
	private List list = new ArrayList();

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
	public Object get(int i) {
		return list.get(i);
	}

	public int size() {
		return list.size();
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}
