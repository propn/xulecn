package com.ztesoft.uiext.graphic.jfreechart.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceCurveDataChartInterface;
import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceOneNameChartVO;
import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceCurveDataResult;

public class ProduceCurveDataChartDAOImpl implements ProduceCurveDataChartInterface {

	public ProduceCurveDataResult findByCond(Map paramMap)
			throws RuntimeException {
		// TODO Auto-generated method stub
		System.out.println("paramMap==" + paramMap);
		ProduceCurveDataResult result = new ProduceCurveDataResult();
		List list = new ArrayList();

		ProduceOneNameChartVO vo = new ProduceOneNameChartVO();
		vo.setName("2009-06-30 10:20:34");
		vo.setValue(new Double(45.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2010-07-01 10:20:34");
		vo.setValue(new Double(12.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2012-07-02 10:20:34");
		vo.setValue(new Double(59.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2013-07-03 10:20:34");
		vo.setValue(new Double(123.0));
		list.add(vo);
		result.putResult("name1", list);

		//
		list = new ArrayList();

		vo = new ProduceOneNameChartVO();
		vo.setName("2009-06-30 10:20:34");
		vo.setValue(new Double(123.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2010-07-01 10:20:34");
		vo.setValue(new Double(16.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2012-07-02 10:20:34");
		vo.setValue(new Double(19.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2013-07-03 10:20:34");
		vo.setValue(new Double(103.0));
		list.add(vo);
		result.putResult("name2", list);

		list = new ArrayList();

		vo = new ProduceOneNameChartVO();
		vo.setName("2009-06-30 10:20:34");
		vo.setValue(new Double(34.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2013-07-01 10:20:34");
		vo.setValue(new Double(122.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2012-07-02 10:20:34");
		vo.setValue(new Double(159.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2010-07-03 10:20:34");
		vo.setValue(new Double(210.0));
		list.add(vo);
		result.putResult("name3", list);

		return result;
	}

}
