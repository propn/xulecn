package com.ztesoft.uiext.graphic.jfreechart.dao;

import java.util.Map;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceCurveDataResult;

public interface ProduceCurveDataChartInterface {
	/**
	 * 数据源外部接口
	 * 
	 * @param whereCond
	 * @return
	 * @throws RuntimeException
	 */
	public ProduceCurveDataResult findByCond(Map paramMap)
			throws RuntimeException;
}
