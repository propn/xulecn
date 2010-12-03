package com.ztesoft.uiext.graphic.jfreechart.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProduceCurveDataResult {

	private Map map = new HashMap();

	private String beginDate;

	private String endDate;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public synchronized void putResult(String key, List rtResult)
			throws RuntimeException {
		try {

			if (map == null) {
				map = new HashMap();
			}

			map.put(key, rtResult);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Map getResult() {
		return map;
	}

	public int getSize() {
		if (map != null && !map.isEmpty()) {
			return map.size();
		}
		return 0;
	}

	public List getListByKey(String key) {
		if (map != null && !map.isEmpty()) {
			List list = (List) map.get(key);
			return list;
		}
		return null;
	}

}
