package com.ztesoft.uiext.graphic.jfreechart.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceDataChartInterface;
import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceOneNameChartVO;

public class ProducePieChartDAOImpl implements ProduceDataChartInterface {

	public List createChartData(List originalDataList) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByCond(Map paramMap) throws RuntimeException {
		// TODO Auto-generated method stub
		System.out.println("paramMap==" + paramMap);

		List list = new ArrayList();

		ProduceOneNameChartVO vo = new ProduceOneNameChartVO();
		vo.setName("name1");
		vo.setValue(new Double(45.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("name2");
		vo.setValue(new Double(12.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("name3");
		vo.setValue(new Double(59.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("name4");
		vo.setValue(new Double(123.0));
		list.add(vo);

		return list;
	}

}
