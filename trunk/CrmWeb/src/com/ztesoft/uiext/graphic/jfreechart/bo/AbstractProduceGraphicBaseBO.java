package com.ztesoft.uiext.graphic.jfreechart.bo;

import java.awt.Font;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;

import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceCurveDataChartInterface;
import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceDataChartDaoFactory;
import com.ztesoft.uiext.graphic.jfreechart.dao.ProduceDataChartInterface;
import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceCurveDataResult;

public abstract class AbstractProduceGraphicBaseBO implements IProduceGraphicBO {

	protected String valueField = ""; // vo�е�ȡֵ�ֶ�

	protected String className = ""; // dao����

	protected String valueObj = ""; // voֵ��������

	protected List executeDataForChart(Map paramMap) {

		ProduceDataChartInterface dao = ProduceDataChartDaoFactory
				.getChartDataClass(className);

		return dao.findByCond(paramMap);

	}

	/**
	 * ʱ����ͼʹ��
	 * 
	 * @return
	 */
	protected ProduceCurveDataResult executeDataForCurveChart(Map paramMap) {

		ProduceCurveDataChartInterface dao = ProduceDataChartDaoFactory
				.getCurveChartDataClass(className);

		return dao.findByCond(paramMap);

	}

	/**
	 * 
	 * @param field
	 * @param className
	 * @return
	 */
	protected Object getValueFromVO(String field, Object obj) {
		if (field == null || field.equalsIgnoreCase("")) {
			return null;
		}
		String methodName = "get" + field.substring(0, 1).toUpperCase()
				+ field.substring(1);

		// ����δ���
		try {
			Class cls = obj.getClass();
			Method method = cls.getDeclaredMethod(methodName, null); // ȡ��vod���е�get����
			Object retobj = method.invoke(obj, null);

			return retobj;

		} catch (Exception ex) {

		}

		return null;
	}

	// �趨�����С���ͺ�
	protected Font getDefaultFont(int size) {
		// ���������ʽ
		Font font = new Font("����", Font.CENTER_BASELINE, size);

		return font;

	}

	/**
	 * ���ñ��⼰�������item����
	 * 
	 * @param chart
	 * @param title
	 */
	protected void setDefaultFontForChart(JFreeChart chart, String title) {
		// ����ͼƬ����
		Font font = this.getDefaultFont(12);
		TextTitle stitle = new TextTitle(title);

		// ���ñ���ĸ�ʽ
		stitle.setFont(font);
		// �ѱ������õ�ͼƬ����
		chart.setTitle(title);

		// ��������,�ǳ��ؼ���Ȼ����������,�·�������
		chart.getLegend().setItemFont(font);
		// Pieͼ������
	}

	protected String getValueFromMap(Map map, String key) {
		String strValue = "";
		try {

			if (map != null && !map.isEmpty()) {
				if (map.containsKey(key)) {
					strValue = (String) map.get(key);
				}
			}

		} catch (Exception ex) {

		}

		return strValue;
	}

	/**
	 * �ú��������ַ�����ȡ,�ڸ������ַ���(srcStr)��ȡ���λ�õ��ض��ַ���<br>
	 * ���磺 srcStr="111&&222&&333&&444";<br>
	 * getParmByIndex(srcStr, 1, "&&")�ķ���ֵ="111"��<br>
	 * getParmByIndex(pszData, 4, "&&")�ķ���ֵ="444"��<br>
	 * getParmByIndex(pszData, 5, "&&")�ķ���ֵ=""<br>
	 * 
	 * @param srcStr
	 *            String Դ���ݴ�
	 * @param index
	 *            int ������,��1��ʼ���
	 * @param mark
	 *            String �ָ���
	 * @return String ��ȡ�����Ӵ�
	 */
	protected String getParmByIndex(String srcStr, int index, String mark) {
		String destStr = "";
		int startPos = 0, endPos = 0;
		int pos;
		if (mark.length() <= 0)
			return destStr;
		for (int i = 1; i < index; i++) {
			if ((pos = srcStr.indexOf(mark, startPos)) < 0) {
				return destStr;
			}
			startPos = pos + mark.length();
			if (startPos >= srcStr.length())
				return destStr;
		}

		endPos = srcStr.indexOf(mark, startPos);
		if (endPos < 0)
			endPos = srcStr.length();

		destStr = srcStr.substring(startPos, endPos);
		return destStr;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	protected double transStringToDouble(String value) {
		double dbValue = 0.0d;
		try {

			dbValue = Double.parseDouble(value);

		} catch (Exception ex) {

		}
		return dbValue;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public String getValueObj() {
		return valueObj;
	}

	public void setValueObj(String valueObj) {
		this.valueObj = valueObj;
	}

}
