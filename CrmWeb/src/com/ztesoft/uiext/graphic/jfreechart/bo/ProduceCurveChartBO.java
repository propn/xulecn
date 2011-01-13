package com.ztesoft.uiext.graphic.jfreechart.bo;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceCurveDataResult;
import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceGraphicVO;

public class ProduceCurveChartBO extends AbstractProduceGraphicBaseBO {

	private String horizontaltitle = ""; // 纵坐标标题

	private String verticaltitle = ""; // 横坐标标题

	private String timeField = "";

	private static final long MAXSPLITTIME = 90000001l;// 27680160l;

	public ProduceGraphicVO produce(String title, Map chartParams, Map paramMap)
			throws RuntimeException {
		// TODO Auto-generated method stub
		ProduceGraphicVO vo = new ProduceGraphicVO();
		// init parameters
		initParams(chartParams);
		//
		JFreeChart localJFreeChart = ChartFactory.createTimeSeriesChart(title,
				verticaltitle, horizontaltitle, createDataset(paramMap), true, true,
				false);

		setSelfProperty(localJFreeChart, vo);

		this.setDefaultFontForChart(localJFreeChart, title);

		vo.setJFreeChart(localJFreeChart);

		return vo;
	}

	private void setSelfProperty(JFreeChart localJFreeChart, ProduceGraphicVO vo) {
		localJFreeChart.setBackgroundPaint(Color.white);
		XYPlot localXYPlot = (XYPlot) localJFreeChart.getPlot();
		localXYPlot.setOrientation(PlotOrientation.VERTICAL);
		localXYPlot.setBackgroundPaint(Color.white);
		localXYPlot.setDomainGridlinePaint(Color.lightGray);
		localXYPlot.setRangeGridlinePaint(Color.lightGray);
		localXYPlot.setAxisOffset(new RectangleInsets(5.0D, 5.0D, 5.0D, 5.0D));
		XYItemRenderer localXYItemRenderer = localXYPlot.getRenderer();
		localXYItemRenderer.setSeriesPaint(0, Color.blue);
		localXYPlot.setNoDataMessage("NO DATA!");

		final DateAxis axis = (DateAxis) localXYPlot.getDomainAxis();
		axis.setAutoTickUnitSelection(false);
		getSplitTime(axis);

		vo.setLegendItemCollection(localXYPlot.getLegendItems());
	}

	/**
	 * 取数据,填充图表
	 * 
	 * @return
	 */
	private XYDataset createDataset(Map paramMap) {
		TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
		ProduceCurveDataResult result = this.executeDataForCurveChart(paramMap);
		Map resultMap = result.getResult();
		if (resultMap == null || resultMap.isEmpty()) {
			return null;
		}

		for (Iterator it = resultMap.keySet().iterator(); it.hasNext();) {
			String strName = (String) it.next();
			List rtsList = result.getListByKey(strName);
			localTimeSeriesCollection.addSeries(createTimeSeries(strName,
					rtsList));
		}

		return localTimeSeriesCollection;
	}

	/**
	 * 创建数据
	 * 
	 * @param keyName
	 * @param list
	 * @return
	 */
	private TimeSeries createTimeSeries(String keyName, List list) {
		TimeSeries localTimeSeries = new TimeSeries(keyName, Second.class);

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			Object timeValue = getValueFromVO(this.timeField, obj);
			String strTimeValue = String.valueOf(timeValue);
			// System.out.println("strTimeValue=" + strTimeValue);
			String[] toDate = formartterDate(strTimeValue);

			Object objValue = getValueFromVO(this.valueField, obj);
			double dbValue = Double.valueOf(String.valueOf(objValue))
					.doubleValue();

			localTimeSeries.addOrUpdate(new Second(Integer.parseInt(toDate[5]),
					Integer.parseInt(toDate[4]), Integer.parseInt(toDate[3]),
					Integer.parseInt(toDate[2]), Integer.parseInt(toDate[1]),
					Integer.parseInt(toDate[0])), dbValue);
		}

		return localTimeSeries;
	}

	/**
	 * init param
	 * 
	 * @param map
	 */
	private void initParams(Map map) {
		horizontaltitle = getValueFromMap(map, "horizontaltitle");
		verticaltitle = getValueFromMap(map, "verticaltitle");

		timeField = getValueFromMap(map, "timeField");
		valueField = getValueFromMap(map, "valueField");
		className = getValueFromMap(map, "className");
	}

	/**
	 * 计算的时间隔
	 * 
	 * @param axis
	 */
	protected int getSplitTime(DateAxis axis) {

		String chartType = "";
		int timeSplit = 1;
		long temSplitTime = axis.getMaximumDate().getTime()
				- axis.getMinimumDate().getTime();

		if (temSplitTime < MAXSPLITTIME) {
			chartType = "Hour";
		} else if (temSplitTime < (MAXSPLITTIME * 30)) {
			chartType = "Day";
		} else if (temSplitTime < (MAXSPLITTIME * 30 * 12)) {
			chartType = "Month";
		} else {
			chartType = "Year";
		}

		if (chartType.equalsIgnoreCase("Year")) {
			int times = (int) ((((temSplitTime / 1000.0D) / 60.0D / 60.0D
					/ 60.0D / 30.0D / 12.0D) / 10.0D) * 10.0D); // 自定义时用

			if (times == 0) {
				times = 1;
			}
			timeSplit = times;
			axis.setDateFormatOverride(new SimpleDateFormat("yyyy"));

			axis.setTickUnit(new DateTickUnit(DateTickUnit.YEAR, timeSplit));

		} else if (chartType.equalsIgnoreCase("Month")) {
			int times = (int) ((((temSplitTime / 1000.0D) / 60.0D / 60.0D / 60.0D / 30.0D) / 10.0D) * 10.0D); // 自定义时用

			if (times == 0) {
				times = 1;
			}
			timeSplit = times;
			axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));

			axis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, timeSplit));

		} else if (chartType.equalsIgnoreCase("Day")) {
			int times = (int) ((((temSplitTime / 1000.0D) / 60.0D / 60.0D / 60.0D) / 10.0D) * 10.0D); // 自定义时用

			if (times == 0) {
				times = 1;
			}
			timeSplit = times;
			axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

			axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, timeSplit));
		} else if (chartType.equalsIgnoreCase("Hour")) {

			int times = (int) (((temSplitTime / 1000.0D / 60.0D) / 10.0D / 10.0D) * 10.0D) + 1; // 自定义时用

			if (times == 0) {
				times = 1;
			}
			timeSplit = times;
			axis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));
			axis.setTickUnit(new DateTickUnit(DateTickUnit.MINUTE, timeSplit));

		} else {

			timeSplit = 1;

		}
		return timeSplit;
	}

	/**
	 * 计算日期,返回成指定的格式yyyy-MM-dd HH:mi.ss 返回一数组
	 * 
	 * @param stDate
	 * @return
	 */
	public String[] formartterDate(String stDate) {

		String[] toDate = new String[6];

		try {
			String[] stDay = stDate.split("-");

			String sYear = stDay[0].trim();
			toDate[0] = sYear.trim();

			String sMonth = stDay[1].trim();
			toDate[1] = sMonth.trim();

			String sHMSDay = stDay[2];
			// System.out.println("sHMSDay==" + sHMSDay);

			String sDay = this.getParmByIndex(sHMSDay, 1, " ");
			toDate[2] = sDay.trim();
			// System.out.println("sDay.trim()==" + sDay.trim());

			String sHour = this.getParmByIndex(sHMSDay, 1, ":");
			String tem[] = sHour.split(" ");
			toDate[3] = tem[1].trim();
			// System.out.println("sHour.trim()==" + toDate[3]);

			String sMinute = this.getParmByIndex(sHMSDay, 2, ":");
			toDate[4] = sMinute.trim();
			// System.out.println("sMinute.trim()==" + sMinute.trim());

			String sSecond = this.getParmByIndex(sHMSDay, 3, ":");
			toDate[5] = sSecond.trim();
			// System.out.println("sSecond.trim()==" + sSecond.trim());

		} catch (Exception ex) {
			toDate = null;
		}
		return toDate;
	}

	public String getVerticaltitle() {
		return verticaltitle;
	}

	public void setVerticaltitle(String verticaltitle) {
		this.verticaltitle = verticaltitle;
	}

}
