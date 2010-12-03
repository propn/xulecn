package com.ztesoft.uiext.graphic.jfreechart.bo;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceGraphicVO;

public class ProduceBarChartBO extends AbstractProduceGraphicBaseBO {

	private String horizontaltitle = ""; // 纵坐标标题

	private String verticaltitle = ""; // 横坐标标题

	private String horizontalField = ""; // 纵坐标字段

	private String verticalField = ""; // 横坐标字段

	public ProduceGraphicVO produce(String title, Map chartParams, Map paramMap)
			throws RuntimeException {
		// TODO Auto-generated method stub
		// init parameters
		initParams(chartParams);
		//

		JFreeChart localJFreeChart = ChartFactory.createBarChart(title,
				verticaltitle, horizontaltitle, createDataset(paramMap),
				PlotOrientation.VERTICAL, true, true, false);

		ProduceGraphicVO vo = new ProduceGraphicVO();

		setSelfProperty(localJFreeChart, vo);

		this.setDefaultFontForChart(localJFreeChart, title);

		vo.setJFreeChart(localJFreeChart);

		return vo;
	}

	private void setSelfProperty(JFreeChart localJFreeChart, ProduceGraphicVO vo) {
		CategoryPlot localCategoryPlot = (CategoryPlot) localJFreeChart
				.getPlot();
		localCategoryPlot.setBackgroundPaint(Color.white);
		localCategoryPlot.setRangeGridlinePaint(Color.lightGray);
		localCategoryPlot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis localNumberAxis = (NumberAxis) localCategoryPlot
				.getRangeAxis();
		// localNumberAxis.setRange(0D, 100.0D);
		localCategoryPlot.setNoDataMessage("NO DATA!");

		localNumberAxis.setStandardTickUnits(NumberAxis
				.createIntegerTickUnits());
		BarRenderer localBarRenderer = (BarRenderer) localCategoryPlot
				.getRenderer();

		localBarRenderer
				.setGradientPaintTransformer(new StandardGradientPaintTransformer(
						GradientPaintTransformType.HORIZONTAL));
		localBarRenderer.setDrawBarOutline(false);
		localBarRenderer
				.setLegendItemToolTipGenerator(new StandardCategorySeriesLabelGenerator(
						"Tooltip: {0}:{1}{2}"));
		localBarRenderer
				.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
						"{0}, {1}) = {2} per 100,000", new DecimalFormat("0")));

		// 显示每个柱的数值，并修改该数值的字体属性

		localBarRenderer
				.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		localBarRenderer.setItemLabelsVisible(true);

		localBarRenderer.setDrawBarOutline(false);

		localCategoryPlot.setRenderer(localBarRenderer);

		CategoryAxis categoryaxis = localCategoryPlot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

		vo.setLegendItemCollection(localCategoryPlot.getLegendItems());
	}

	private CategoryDataset createDataset(Map paramMap) throws RuntimeException {

		List list = executeDataForChart(paramMap);

		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		convertVO(list, localDefaultCategoryDataset);

		return localDefaultCategoryDataset;
	}

	/**
	 * 转换form
	 * 
	 * @param list
	 * @return
	 */
	private void convertVO(List list,
			DefaultCategoryDataset localDefaultCategoryDataset)
			throws RuntimeException {
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);

			Object hname = getValueFromVO(this.horizontalField, obj);
			String strHName = String.valueOf(hname); // 纵坐标字段的值

			Object vname = getValueFromVO(this.verticalField, obj);
			String strVName = String.valueOf(vname); // 横坐标字段的值

			Object dbVlue = getValueFromVO(this.valueField, obj); // bar的值
			double dbValue = Double.valueOf(String.valueOf(dbVlue))
					.doubleValue();

			localDefaultCategoryDataset.addValue(dbValue, strHName, strVName);
		}
	}

	private void initParams(Map map) {
		horizontaltitle = getValueFromMap(map, "horizontaltitle");
		verticaltitle = getValueFromMap(map, "verticaltitle");
		horizontalField = getValueFromMap(map, "horizontalField");
		verticalField = getValueFromMap(map, "verticalField");
		valueField = getValueFromMap(map, "valueField");
		className = getValueFromMap(map, "className");

	}
}
