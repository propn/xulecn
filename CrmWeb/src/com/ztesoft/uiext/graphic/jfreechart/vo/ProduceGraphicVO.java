package com.ztesoft.uiext.graphic.jfreechart.vo;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemCollection;

public class ProduceGraphicVO implements java.io.Serializable {

	// ͼ���и������Ӧ�����ݶ���,��Ҫ����pie��
	private LegendItemCollection legendItemCollection = null;

	// ͼ�����
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
