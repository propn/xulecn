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

	protected String valueField = ""; // vo中的取值字段

	protected String className = ""; // dao类名

	protected String valueObj = ""; // vo值对象类名

	protected List executeDataForChart(Map paramMap) {

		ProduceDataChartInterface dao = ProduceDataChartDaoFactory
				.getChartDataClass(className);

		return dao.findByCond(paramMap);

	}

	/**
	 * 时间线图使用
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

		// 看这段代码
		try {
			Class cls = obj.getClass();
			Method method = cls.getDeclaredMethod(methodName, null); // 取到vod类中的get方法
			Object retobj = method.invoke(obj, null);

			return retobj;

		} catch (Exception ex) {

		}

		return null;
	}

	// 设定字体大小及型号
	protected Font getDefaultFont(int size) {
		// 定义字体格式
		Font font = new Font("宋体", Font.CENTER_BASELINE, size);

		return font;

	}

	/**
	 * 设置标题及各区域的item字体
	 * 
	 * @param chart
	 * @param title
	 */
	protected void setDefaultFontForChart(JFreeChart chart, String title) {
		// 定义图片标题
		Font font = this.getDefaultFont(12);
		TextTitle stitle = new TextTitle(title);

		// 设置标题的格式
		stitle.setFont(font);
		// 把标题设置到图片里面
		chart.setTitle(title);

		// 设置字体,非常关键不然会出现乱码的,下方的字体
		chart.getLegend().setItemFont(font);
		// Pie图的字体
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
	 * 该函数用于字符串截取,在给定的字符串(srcStr)中取相关位置的特定字符串<br>
	 * 例如： srcStr="111&&222&&333&&444";<br>
	 * getParmByIndex(srcStr, 1, "&&")的返回值="111"。<br>
	 * getParmByIndex(pszData, 4, "&&")的返回值="444"。<br>
	 * getParmByIndex(pszData, 5, "&&")的返回值=""<br>
	 * 
	 * @param srcStr
	 *            String 源数据串
	 * @param index
	 *            int 索引号,从1开始编号
	 * @param mark
	 *            String 分隔符
	 * @return String 即取出的子串
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
