package com.ztesoft.uiext.graphic.jfreechart.dao;

import java.util.List;
import java.util.Map;

public interface ProduceDataChartInterface {

	/**
	 * ����Դ�ⲿ�ӿ�
	 * 
	 * @param whereCond
	 * @return
	 * @throws RuntimeException
	 */
	public List findByCond(Map paramMap) throws RuntimeException;
}
