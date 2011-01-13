package com.ztesoft.uiext.graphic.jfreechart.dao;

public class ProduceTwoNameChartVO {
	private String horizontalName = ""; // ×Ý×ø±ê

	private String verticalName = ""; // ºá×ø±ê

	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getHorizontalName() {
		return horizontalName;
	}

	public void setHorizontalName(String horizontalName) {
		this.horizontalName = horizontalName;
	}

	public String getVerticalName() {
		return verticalName;
	}

	public void setVerticalName(String verticalName) {
		this.verticalName = verticalName;
	}

}
