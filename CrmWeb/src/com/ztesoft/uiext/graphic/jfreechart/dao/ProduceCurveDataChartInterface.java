package com.ztesoft.uiext.graphic.jfreechart.dao;

import java.util.Map;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceCurveDataResult;

public interface ProduceCurveDataChartInterface {
	/**
	 * ����Դ�ⲿ�ӿ�
	 * 
	 * @param whereCond
	 * @return
	 * @throws RuntimeException
	 */
	public ProduceCurveDataResult findByCond(Map paramMap)
			throws RuntimeException;
}
