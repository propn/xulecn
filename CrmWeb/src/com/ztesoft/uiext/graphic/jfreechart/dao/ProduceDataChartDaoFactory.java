package com.ztesoft.uiext.graphic.jfreechart.dao;

public class ProduceDataChartDaoFactory {
	/**
	 * 取一般的图形的类
	 * 
	 * @param className
	 * @return
	 */
	public static ProduceDataChartInterface getChartDataClass(String className) {
		ProduceDataChartInterface chartDao = null;
		try {
			Class cls1 = Class.forName(className);

			Object obj = cls1.newInstance();

			if (obj instanceof ProduceDataChartInterface) {
				chartDao = (ProduceDataChartInterface) obj;
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e2) {
			e2.printStackTrace();
		} catch (InstantiationException e3) {
			e3.printStackTrace();
		}

		return chartDao;
	}

	/**
	 * 取时间曲线图的类
	 * 
	 * @param className
	 * @return
	 */
	public static ProduceCurveDataChartInterface getCurveChartDataClass(
			String className) {
		ProduceCurveDataChartInterface curvechartDao = null;
		try {
			Class cls1 = Class.forName(className);

			Object obj = cls1.newInstance();

			if (obj instanceof ProduceCurveDataChartInterface) {
				curvechartDao = (ProduceCurveDataChartInterface) obj;
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e2) {
			e2.printStackTrace();
		} catch (InstantiationException e3) {
			e3.printStackTrace();
		}

		return curvechartDao;
	}

	public static Object getDyObject(String className) {
		Object obj = null;
		Class cls = null;
		try {
			cls = Class.forName(className);

			obj = cls.newInstance();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e2) {
			e2.printStackTrace();
		} catch (InstantiationException e3) {
			e3.printStackTrace();
		}

		return obj;
	}

}
