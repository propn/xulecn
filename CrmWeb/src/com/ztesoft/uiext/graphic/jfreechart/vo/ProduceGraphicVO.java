package com.ztesoft.uiext.graphic.jfreechart.vo;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemCollection;

public class ProduceGraphicVO implements java.io.Serializable {

	// 图表中各区域对应的数据对象,主要用于pie中
	private LegendItemCollection legendItemCollection = null;

	// 图表对象
	private JFreeChart jFreeChart = null;

	public JFreeChart getJFreeChart() {
		return jFreeChart;
	}

	public void setJFreeChart(JFreeChart freeChart) {
		jFreeChart = freeChart;
	}

	public LegendItemCollection getLegendItemCollection() {
		return legendItemCollection;
	}

	public void setLegendItemCollection(
			LegendItemCollection legendItemCollection) {
		this.legendItemCollection = legendItemCollection;
	}

	public ProduceGraphicVO() {

	}
}
