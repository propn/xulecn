package com.ztesoft.uiext.graphic.jfreechart.ztag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceParamVO;

public class ProductMeterGraphicTag extends ProduceGraphicTag {

	private String minvalue = ""; // 最小值

	private String maxvalue = ""; // 最大值

	private String ticksize = ""; // 间隔值

	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		parameters = new ArrayList();
		return super.doStartTag();
	}

	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub

		try {
			// get request object
			HttpServletRequest rs = (HttpServletRequest) pageContext
					.getRequest();
			HttpSession session = (HttpSession) pageContext.getSession();

			ProduceParamVO vo = initParams("meter", session);

			StringBuffer html = new StringBuffer();

			html
					.append("<script type='text/javascript' language='javascript'>");
			html.append("var req; \n var  " + this.id + "=new JFreeChartObj("
					+ this.id + "); ");
			html.append(this.id + ".jid='" + this.id + "'; \n");
			// html.append(this.getInitializeXmlHttpRequestFunction());
			// html.append(this.invokeAjaxMethod(rs.getContextPath()));
			html.append(this.getServerReponseFunction());

			// html.append(getInitializeParameterFunction(rs.getContextPath()));
			html.append("</script>");

			html.append("<div id='dv" + this.id + "' ");
			html.append(">");
			html.append("</div>");

			pageContext.getOut().write(html.toString());

			getParameterMap(vo);

		} catch (Exception e) {
			e.printStackTrace();
			throw new JspException("TimeTag Error:" + e.getMessage());
		}

		return super.doEndTag();
	}

	public void release() {
		// TODO Auto-generated method stub
		id = "";

		title = "";

		width = 0;

		height = 0;

		className = "";

		valueField = "";

		nameField = "";

		parameters = null;

		minvalue = ""; // 最小值

		maxvalue = ""; // 最大值

		ticksize = ""; // 间隔值
	}

	public String getMinvalue() {
		return minvalue;
	}

	public void setMinvalue(String minvalue) {
		this.minvalue = minvalue;
	}

	public String getTicksize() {
		return ticksize;
	}

	public void setTicksize(String ticksize) {
		this.ticksize = ticksize;
	}

	public String getMaxvalue() {
		return maxvalue;
	}

	public void setMaxvalue(String maxvalue) {
		this.maxvalue = maxvalue;
	}

	private void getParameterMap(ProduceParamVO vo) {
		Map map = vo.getOtherMap();
		if (map == null) {
			map = new HashMap();
		}
		map.put("minvalue", minvalue);
		map.put("maxvalue", maxvalue);
		map.put("ticksize", ticksize);
		map.put("valueField", valueField);
		map.put("className", className);
		vo.setOtherMap(map);
		vo.setParametersList(this.getParameters());
	}

}
