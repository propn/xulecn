package com.ztesoft.uiext.graphic.jfreechart.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProduceParamVO implements java.io.Serializable {
	private String id = "";

	private String title = "";

	private int width = 0;

	private int height = 0;

	private String chartType = "";

	private List parametersList = new ArrayList();

	private Map otherMap = new HashMap();

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Map getOtherMap() {
		return otherMap;
	}

	public void setOtherMap(Map otherMap) {
		this.otherMap = otherMap;
	}

	public List getParametersList() {
		return parametersList;
	}

	public void setParametersList(List parametersList) {
		this.parametersList = parametersList;
	}

}
