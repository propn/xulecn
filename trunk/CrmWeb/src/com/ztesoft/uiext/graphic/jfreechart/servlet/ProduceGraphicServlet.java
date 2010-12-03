package com.ztesoft.uiext.graphic.jfreechart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;

import com.ztesoft.uiext.graphic.jfreechart.bo.ProduceChartFactory;
import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceGraphicVO;
import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceParamVO;
import com.ztesoft.uiext.graphic.jfreechart.ztag.ParameterVo;

public class ProduceGraphicServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ProduceGraphicServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String keyId = request.getParameter("keyId");

		if (keyId == null || keyId.equalsIgnoreCase("")) {
			out.write("keyIderror");
			return;
		}

		Map map = (Map) request.getSession().getAttribute(
				"GRAPHICPARAMETER_MAP");

		if (map == null || map.isEmpty()) {
			out.write("maperror");
			return;
		}

		ProduceParamVO paramVO = (ProduceParamVO) map.get(keyId);

		if (paramVO == null || paramVO.getChartType() == null
				|| paramVO.getChartType().equalsIgnoreCase("")) {
			out.write("chartTypeerror");
			return;
		}
		// remove
		// map.remove(keyId);

		// sql condition	

		response.setContentType("text/html");

		Map paramMap = new HashMap();
		
		if (paramVO.getParametersList() != null
				&& !paramVO.getParametersList().isEmpty()) {
			for (int i = 0; i < paramVO.getParametersList().size(); i++) {
				ParameterVo vo = (ParameterVo) paramVO.getParametersList().get(
						i);
				paramMap.put(vo.getParameterId(), request.getParameter(vo.getParameterId()));
				System.out.println(vo.getParameterId() + "=" + request.getParameter(vo.getParameterId()));
			}
		}

		ProduceGraphicVO vo = ProduceChartFactory.getInstance().createChart(
				paramVO.getChartType(), paramVO.getTitle(),
				paramVO.getOtherMap(), paramMap);

		JFreeChart chart = vo.getJFreeChart();

		// produce a image
		String imageUrl = "";
		try {

			imageUrl = ServletUtilities.saveChartAsPNG(chart, paramVO
					.getWidth(), paramVO.getHeight(), null, request
					.getSession());
			imageUrl = request.getContextPath()
					+ "/servlet/DisplayChart?filename=" + imageUrl;
			out.write(imageUrl);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
