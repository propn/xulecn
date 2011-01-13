package com.ztesoft.uiext.graphic.jfreechart.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceCurveDataChartInterface;
import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceOneNameChartVO;
import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceCurveDataResult;

public class ProduceCurve2DataChartDAOImpl implements ProduceCurveDataChartInterface {

	public ProduceCurveDataResult findByCond(Map paramMap)
			throws RuntimeException {
		// TODO Auto-generated method stub
		System.out.println("paramMap==" + paramMap);
		ProduceCurveDataResult result = new ProduceCurveDataResult();
		List list = new ArrayList();

		ProduceOneNameChartVO vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 10:20:34");
		vo.setValue(new Double(65.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 11:20:34");
		vo.setValue(new Double(120.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 12:20:34");
		vo.setValue(new Double(159.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 15:20:34");
		vo.setValue(new Double(23.0));
		list.add(vo);
		result.putResult("Ïß1", list);

		//
		list = new ArrayList();

		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 10:20:34");
		vo.setValue(new Double(23.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 11:20:34");
		vo.setValue(new Double(160.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 12:20:34");
		vo.setValue(new Double(190.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 15:20:34");
		vo.setValue(new Double(103.0));
		list.add(vo);
		result.putResult("Ïß2", list);

		list = new ArrayList();

		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 10:20:34");
		vo.setValue(new Double(174.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 11:20:34");
		vo.setValue(new Double(127.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 12:20:34");
		vo.setValue(new Double(159.0));
		list.add(vo);
		vo = new ProduceOneNameChartVO();
		vo.setName("2009-07-01 15:20:34");
		vo.setValue(new Double(210.0));
		list.add(vo);
		result.putResult("Ïß3", list);

		return result;
	}

}
