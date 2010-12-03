package com.ztesoft.uiext.graphic.jfreechart.bo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceGraphicVO;

public class ProducePieChartBO extends AbstractProduceGraphicBaseBO {
	private String nameField = "";

	public ProduceGraphicVO produce(String title, Map chartParams, Map paramMap)
			throws RuntimeException {
		// TODO Auto-generated method stub
		JFreeChart chart = null;
		ProduceGraphicVO vo = new ProduceGraphicVO();
		//

		initParams(chartParams);

		// ������ݽӿ�

		// --
		chart = ChartFactory.createPieChart3D(title, createDataset(paramMap),
				true, true, false);

		setSelfProperty(chart, vo);

		this.setDefaultFontForChart(chart, title);

		vo.setJFreeChart(chart);

		return vo;
	}

	private DefaultPieDataset createDataset(Map paramMap) {

		List list = executeDataForChart(paramMap);

		DefaultPieDataset data = new DefaultPieDataset();
		convertVO(list, data);

		// ��һ������Ϊ������ƣ��ڶ�������Ϊ��ռ�İٷֱ� --demo
		// data.setValue("ϲ��Ӣ��", 20.32);
		// data.setValue("ϲ����ѧ", 60.22);
		// data.setValue("English", 60.22);

		return data;
	}

	/**
	 * ת��form
	 * 
	 * @param list
	 * @return
	 */
	private void convertVO(List list, DefaultPieDataset data) {
		for (int i = 0; i < list.size(); i++) {
			// ProduceOneNameChartVO vo = new ProduceOneNameChartVO();
			Object obj = list.get(i);
			Object name = getValueFromVO(this.nameField, obj);
			String strName = String.valueOf(name);
			// vo.setName(String.valueOf(name));
			Object dbVlue = getValueFromVO(this.valueField, obj);
			double dbValue = Double.valueOf(String.valueOf(dbVlue))
					.doubleValue();
			// vo.setValue(Double.valueOf(String.valueOf(dbVlue)).doubleValue());
			data.setValue(strName, dbValue);
			// rstList.add(vo);
		}
	}

	/**
	 * ������������
	 * 
	 * @param chart
	 */
	private void setSelfProperty(JFreeChart chart, ProduceGraphicVO vo) {
		if (chart == null)
			return;

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setInteriorGap(0.0d);
		plot.setLabelGap(0.0d);

		plot.setNoDataMessage("NO DATA!");
		chart.setBorderVisible(false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);

		// plot.setInsets(new RectangleInsets(0, 0, 0, 0));
		plot.setOutlineVisible(false);
		plot.setBackgroundAlpha(0.0f);
		plot.setDepthFactor(0);
		plot.setLabelGenerator(null);
		plot.setCircular(true);
		// plot.setMaximumLabelWidth(0.0);
		// plot.setMinimumArcAngleToDraw(0.0);

		// //���ÿ�ֵ,0ֵ,��ֵ�Ƿ���ʾ����,�����ʾ�Ļ�����false
		plot.setIgnoreNullValues(true);
		plot.setIgnoreZeroValues(true);

		// �����������ʽ,0��ʾKEY,1��ʾVALUE,2��ʾ�ٷ�֮��,DecimalFormat������ʾ�ٷֱȵĸ�ʽ
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}[{1}({2})]", NumberFormat.getNumberInstance(),
				new DecimalFormat("#.##%")));

		// �������淽�����ʽ,0��ʾKEY,1��ʾVALUE,2��ʾ�ٷ�֮��,DecimalFormat������ʾ�ٷֱȵĸ�ʽ
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}:{1}({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("#.##%")));

		plot.setLabelFont(this.getDefaultFont(12));
		vo.setLegendItemCollection(plot.getLegendItems());
	}

	private void initParams(Map map) {

		valueField = getValueFromMap(map, "valueField");
		className = getValueFromMap(map, "className");
		nameField = getValueFromMap(map, "nameField");

	}

}
