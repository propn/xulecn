package com.ztesoft.uiext.graphic.jfreechart.bo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.ui.RectangleInsets;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceGraphicVO;

public class ProduceMeterChartBO extends AbstractProduceGraphicBaseBO {

	private double ticksize;

	private double minvalue;

	private double maxvalue;

	private final int tickNum = 12;

	public ProduceGraphicVO produce(String title, Map chartParams, Map paramMap)
			throws RuntimeException {
		// TODO Auto-generated method stub
		ProduceGraphicVO vo = new ProduceGraphicVO();
		//

		initParams(chartParams);
		//
		MeterPlot meterplot = new MeterPlot(createDataset(paramMap));

		JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
				meterplot, false);

		setSelfProperty(chart, vo, meterplot);

		// this.setDefaultFontForChart(chart, title);

		vo.setJFreeChart(chart);

		chart.setBackgroundPaint(Color.white);

		return vo;
	}

	private void initParams(Map map) {
		maxvalue = transStringToDouble(getValueFromMap(map, "maxvalue"));
		minvalue = transStringToDouble(getValueFromMap(map, "minvalue"));
		ticksize = transStringToDouble(getValueFromMap(map, "ticksize"));

		valueField = getValueFromMap(map, "valueField");
		className = getValueFromMap(map, "className");

	}

	/**
	 * ������������
	 * 
	 * @param localJFreeChart
	 * @param vo
	 * @param meterplot
	 */
	private void setSelfProperty(JFreeChart localJFreeChart,
			ProduceGraphicVO vo, MeterPlot meterplot) {
		// �������趨Բ�������ı���ɫ
		// �����ǽ���������ͼ����

		meterplot.setDialShape(DialShape.PIE);
		meterplot.setRange(new Range(minvalue, maxvalue)); // ����range
		meterplot.setTickSize(ticksize); // �趨���̺˶ȼ���

		double tempMax = maxvalue - minvalue;

		int tempRegion = (int) (tempMax / tickNum) * 1;

		double minR = minvalue, maxR = minvalue + tempRegion;
		for (int i = 0; i < tickNum; i++) {
			meterplot.addInterval(new MeterInterval("", new Range(minR, maxR),
					new Color(146, 159, 171), new BasicStroke(1.0F), new Color(
							227, 230, 233)));
			minR = maxR;
			maxR = maxR + tempRegion;
			if (i == tickNum - 2) {
				maxR = maxvalue;
			}
		}

		meterplot.setNeedlePaint(new Color(232, 0, 0)); // �趨ָ���color
		meterplot.setDialBackgroundPaint(new Color(146, 159, 171)); // �趨����ɫ
		meterplot.setInsets(new RectangleInsets(0, 0, 0, 0));
		meterplot.setDrawBorder(false);
		meterplot.setOutlineVisible(true);
		meterplot.setNoDataMessage("NO DATA!");
		meterplot.setUnits("%");
		meterplot.setForegroundAlpha(0.75f);
		meterplot.setOutlinePaint(new Color(205, 159, 171));
		meterplot.setOutlineStroke(new BasicStroke(2.0F));

		meterplot.setDialOutlinePaint(new Color(98, 115, 131)); // �趨�ⲿ�߿�color
		meterplot.setMeterAngle(360);
		meterplot.setTickLabelsVisible(true);
		meterplot.setTickLabelFont(new Font("Dialog", 1, 10));
		meterplot.setTickLabelPaint(Color.black); // �����̵�����color

		meterplot.setTickPaint(new Color(102, 118, 133)); // �趨����color
		meterplot.setValuePaint(Color.black);
		meterplot.setValueFont(new Font("Dialog", 1, 14));

		vo.setLegendItemCollection(meterplot.getLegendItems());
	}

	/**
	 * ��������Դ
	 * 
	 * @return
	 */
	private ValueDataset createDataset(Map paramMap) {
		List list = executeDataForChart(paramMap);

		return convertVO(list);

	}

	/**
	 * ת��form
	 * 
	 * @param list
	 * @return
	 */
	private ValueDataset convertVO(List list) {
		if (list != null && !list.isEmpty()) {
			// ProduceOneNameChartVO vo = new ProduceOneNameChartVO();
			Object obj = list.get(0);
			Object dbVlue = getValueFromVO(this.valueField, obj);
			double dbValue = Double.valueOf(String.valueOf(dbVlue))
					.doubleValue();
			ValueDataset valuedataset = new DefaultValueDataset(dbValue);
			return valuedataset;
		}
		return null;
	}

}
