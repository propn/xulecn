package com.ztesoft.uiext.graphic.jfreechart.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceDataChartInterface;
import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceOneNameChartVO;

public class ProduceMeterDataChartDAOImpl implements ProduceDataChartInterface {

	public List findByCond(Map paramMap) throws RuntimeException {
		// TODO Auto-generated method stub
		System.out.println("paramMap==" + paramMap);

		List list = new ArrayList();

		ProduceOneNameChartVO vo = new ProduceOneNameChartVO();
		vo.setName("");
		vo.setValue(new Double(67.9));
		list.add(vo);

		return list;
	}

}
