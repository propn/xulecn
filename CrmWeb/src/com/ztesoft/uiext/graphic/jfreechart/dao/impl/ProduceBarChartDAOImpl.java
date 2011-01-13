package com.ztesoft.uiext.graphic.jfreechart.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceDataChartInterface;
import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceTwoNameChartVO;

public class ProduceBarChartDAOImpl implements ProduceDataChartInterface {

	public List findByCond(Map paramMap) throws RuntimeException {
		// TODO Auto-generated method stub
		System.out.println("paramMap==" + paramMap);

		List list = new ArrayList();

		ProduceTwoNameChartVO vo = new ProduceTwoNameChartVO();
		vo.setHorizontalName("��׼");
		vo.setVerticalName("����(��)");
		vo.setValue(new Double(100));
		list.add(vo);

		vo = new ProduceTwoNameChartVO();
		vo.setHorizontalName("ʵ��");
		vo.setVerticalName("����(��)");
		vo.setValue(new Double(120));
		list.add(vo);

		vo = new ProduceTwoNameChartVO();
		vo.setHorizontalName("��׼");
		vo.setVerticalName("Ƿ��(��)");
		vo.setValue(new Double(56.9));
		list.add(vo);
		vo = new ProduceTwoNameChartVO();
		vo.setHorizontalName("ʵ��");
		vo.setVerticalName("Ƿ��(��)");
		vo.setValue(new Double(26.9));
		list.add(vo);

		vo = new ProduceTwoNameChartVO();
		vo.setHorizontalName("��׼");
		vo.setVerticalName("�����û�(��)");
		vo.setValue(new Double(42.9));
		list.add(vo);
		vo = new ProduceTwoNameChartVO();
		vo.setHorizontalName("ʵ��");
		vo.setVerticalName("�����û�(��)");
		vo.setValue(new Double(56.9));
		list.add(vo);

		return list;
	}

}
