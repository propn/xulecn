package com.ztesoft.uiext.graphic.jfreechart.ztag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceParamVO;

public abstract class ProduceGraphicTag extends
		javax.servlet.jsp.tagext.BodyTagSupport {

	protected String id = "";

	protected String title = "";

	protected int width = 0;

	protected int height = 0;

	protected String className = "";

	protected String valueField = "";

	protected String nameField = "";

	protected final String dataUrl = "/servlet/ProduceGraphicServlet";

	protected List parameters = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	protected ProduceParamVO initParams(String chartType, HttpSession session) {

		Map map = null;

		if (session.getAttribute("GRAPHICPARAMETER_MAP") == null) {
			map = new HashMap();
		} else {
			try {
				map = (Map) session.getAttribute("GRAPHICPARAMETER_MAP");
				
			} catch (Exception ex) {
				map = new HashMap();
			}
		}

		ProduceParamVO vo = new ProduceParamVO();
		vo.setHeight(height);
		vo.setWidth(width);
		vo.setId(id);
		vo.setTitle(title);
		vo.setChartType(chartType);

		map.put(this.id, vo);

		session.setAttribute("GRAPHICPARAMETER_MAP", map);

		return vo;
	}

	/**
	 * The getInitializeXmlHttpRequestFunction method returns the javascript
	 * code to initialize the XmlHttpRequest object for asynchronous
	 * communication.
	 * 
	 * @return Javascript code to initialize the XmlHttpRequest
	 */
	protected String getInitializeXmlHttpRequestFunction() {
		StringBuffer html = new StringBuffer();
		html.append("function initializeXmlHttpRequest() {");
		html.append("if (window.ActiveXObject) {");
		// Support for Microsoft browsers
		html.append("req=new ActiveXObject('Microsoft.XMLHTTP');");
		html.append("}");
		// Support for non-Microsoft browsers (and IE7+)
		html.append("else {");
		html.append("req=new XMLHttpRequest();");
		html.append("}");
		html.append("}");

		return html.toString();
	}

	protected String invokeAjaxMethod(String path) {
		StringBuffer html = new StringBuffer();

		String tmpDataUrl = path + dataUrl;

		html.append("function ");
		html.append(this.getId());
		html.append("_onRemoteCall(whereCond) { ");
		html.append("initializeXmlHttpRequest();");
		html.append("var cond='keyId=");
		html.append(this.getId() + "';");
		html.append("  for(i=0;i<whereCond.length;i++)");
		html.append(" {");
		html.append(" 	  var para = whereCond[i];");
		html.append(" 	  cond +='&' + para.name + '=' + para.value;");
		html.append(" 	 } ");

		html.append("if (req!=null) {");
		html.append("req.onreadystatechange=eval('" + this.id
				+ "'  + '_onServerResponse');");

		html.append("req.open('POST','" + tmpDataUrl + "',true);");
		html
				.append("req.setRequestHeader('Content-Type','application/x-www-form-urlencoded');");

		html.append("req.send(cond);");
		html.append("}");
		html.append("}");
		return html.toString();

	}

	/**
	 * The getOnServerResponse method returns the javascript code for the on
	 * server response received event for the drop down
	 * 
	 * @return Javascript code for the on async callback event for the drop down
	 */
	protected String getServerReponseFunction() {
		StringBuffer html = new StringBuffer();

		html.append("function ");
		html.append(this.getId());
		html.append("_onServerResponse() {");

		html.append("if(req.readyState!=4) { return; }");
		html.append("if(req.status != 200) {");
		html.append("alert('An error occured retrieving data.');");
		html.append("return;");
		html.append("}");

		// Get response data
		html.append("var responseData = req.responseText;");
		html.append("var curControl = document.getElementById('dv");
		html.append(this.getId());
		html.append("');");
		// html.append(" alert(responseData);");
		html.append("curControl.innerHTML=\"<img id='i" + this.id
				+ "'  src='\" + responseData +\"' ");
		html.append("/>\"");

		html.append("}");

		return html.toString();

	}

	/**
	 * The getInitializeXmlHttpRequestFunction method returns the javascript
	 * code to initialize the XmlHttpRequest object for asynchronous
	 * communication.
	 * 
	 * @return Javascript code to initialize the XmlHttpRequest
	 */
	protected String getInitializeParameterFunction(String path) {
		StringBuffer html = new StringBuffer();
		html.append("function ParameterChart() {this._____parameters=[];} ");

		html.append("ParameterChart.prototype.setParameter = function(name,value){");
		html.append(" para={};");
		html.append("	  para.name=name;");
		html.append("	  para.value=value;");
		html.append("	  this._____parameters.push(para);");
		html.append("	};");
		html.append("ParameterChart.prototype.getParameter = function(){");
		html.append(" return this._____parameters;");
		html.append("	};");
		
		String tmpDataUrl = path + dataUrl;

		html.append("ParameterChart.prototype.onRemoteCall = function(whereCond) {");		
		html.append("initializeXmlHttpRequest();");
		html.append("var cond='keyId=");
		html.append(this.getId() + "';");
		html.append("  for(i=0;i<whereCond.length;i++)");
		html.append(" {");
		html.append(" 	  var para = whereCond[i];");
		html.append(" 	  cond +='&' + para.name + '=' + para.value;");
		html.append(" 	 } ");

		html.append("if (req!=null) {");
		html.append("req.onreadystatechange=eval('" + this.id
				+ "'  + '_onServerResponse');");

		html.append("req.open('POST','" + tmpDataUrl + "',true);");
		html
				.append("req.setRequestHeader('Content-Type','application/x-www-form-urlencoded');");

		html.append("req.send(cond);");
		html.append("}");
		html.append("};");

		// the last end
		html.append(" var ");
		html.append(this.id);
		html.append(" = new ParameterChart();");

		return html.toString();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
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

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public void setParameter(Object parameter) {
		if (parameters == null) {
			parameters = new ArrayList();
		}
		parameters.add(parameter);
	}

	public List getParameters() {
		return parameters;
	}

}
