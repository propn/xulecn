package com.ztesoft.uiext.graphic.jfreechart.bo;

import java.util.Map;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceGraphicVO;

public class ProduceChartFactory {
	private static ProduceChartFactory graphicFactoryBO = null;

	private ProduceChartFactory() {
	}

	public static ProduceChartFactory getInstance() {
		if (graphicFactoryBO == null) {
			graphicFactoryBO = new ProduceChartFactory();
		}
		return graphicFactoryBO;
	}

	public ProduceGraphicVO createChart(String chartType, String title,
			Map chartParams, Map paramMap)
			throws java.lang.RuntimeException {

		ProduceGraphicVO vo = null;
		IProduceGraphicBO iProduceGraphicBO = null;
		try {
			if (chartType.equals("pie")) {
				iProduceGraphicBO = new ProducePieChartBO();
			} else if (chartType.equals("bar")) {
				iProduceGraphicBO = new ProduceBarChartBO();
			} else if (chartType.equals("curve")) {
				iProduceGraphicBO = new ProduceCurveChartBO();
			} else if (chartType.equals("meter")) {
				iProduceGraphicBO = new ProduceMeterChartBO();
			}

			vo = iProduceGraphicBO.produce(title, chartParams, paramMap);

		} catch (RuntimeException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			throw e;
		}

		return vo;
	}
}
