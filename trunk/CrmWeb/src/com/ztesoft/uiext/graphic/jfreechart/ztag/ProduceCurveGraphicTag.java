package com.ztesoft.uiext.graphic.jfreechart.ztag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceParamVO;

public class ProduceCurveGraphicTag extends ProduceGraphicTag {

	private String horizontaltitle = ""; // 纵坐标标题

	private String verticaltitle = ""; // 横坐标标题

	private String timeField = ""; // 时间值字段

	public String getHorizontaltitle() {
		return horizontaltitle;
	}

	public void setHorizontaltitle(String horizontaltitle) {
		this.horizontaltitle = horizontaltitle;
	}

	public String getVerticaltitle() {
		return verticaltitle;
	}

	public void setVerticaltitle(String verticaltitle) {
		this.verticaltitle = verticaltitle;
	}

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

			ProduceParamVO vo = initParams("curve", session);

			StringBuffer html = new StringBuffer();

			html
					.append("<script type='text/javascript' language='javascript'>");
			html.append("var req; \n var  " + this.id + "=new JFreeChartObj("
					+ this.id + "); ");
			html.append(this.id + ".jid='" + this.id + "'; \n");
			// html.append(this.getInitializeXmlHttpRequestFunction());
			// html.append(this.invokeAjaxMethod(rs.getContextPath()));
			html.append(this.getServerReponseFunction());

			// html.append(this
			// .getInitializeParameterFunction(rs.getContextPath()));
			html.append("</script>");
			// get chart object

			// imageUrl = rs.getContextPath() +
			// "/servlet/DisplayChart?filename="
			// + imageUrl;

			html.append("<div id='dv" + this.id + "' ");
			html.append(">");
			html.append("</div>");
			pageContext.getOut().write(html.toString());

			// pageContext.getOut().write();
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

		horizontaltitle = ""; // 纵坐标标题

		verticaltitle = ""; // 横坐标标题

		timeField = ""; // 时间值字段

	}

	private void getParameterMap(ProduceParamVO vo) {
		Map map = vo.getOtherMap();
		if (map == null) {
			map = new HashMap();
		}
		map.put("horizontaltitle", horizontaltitle);
		map.put("verticaltitle", verticaltitle);
		map.put("valueField", valueField);
		map.put("timeField", timeField);
		map.put("className", className);

		vo.setOtherMap(map);
		vo.setParametersList(this.getParameters());

	}

	public String getTimeField() {
		return timeField;
	}

	public void setTimeField(String timeField) {
		this.timeField = timeField;
	}
}
