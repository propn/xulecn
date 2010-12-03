package com.ztesoft.crm.business.views.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.crm.business.views.dynamic.DataSet;
import com.ztesoft.crm.business.views.dynamic.Form;

public class HtmlBuilderServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public HtmlBuilderServlet() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost( request, response) ;
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 *
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	
	public final static String SPLIT="<inter>";
	
	public final static String DOU=",";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		String[]entryIds=request.getParameter("entryIds").split(DOU);
		String[]servIds=request.getParameter("servIds").split(DOU);
		
		String[] entryNames=new String(request.getParameter("entryNames").getBytes("iso-8859-1"),"GBK").split(DOU);
		
		//String[]entryNames=request.getParameter("entryNames").split(DOU);
	    //String allStr = "<xml id='__staffInfo'></xml><xml id='__PRODUCT_CATALOG_CATALOG_USAGE'><items><item label='销售使用' value='1' valueId='8315'></item><item label='维护使用' value='0' valueId='8314'></item></items></xml><CODE id='PRODUCT_CATALOG_CATALOG_USAGE' attrCode='PRODUCT_CATALOG_CATALOG_USAGE'></CODE><s><form id='staffInfoForm' class='formLayout3' onsubmit='return false;' submit='btn_confirm'><table cellspacing='0' cellpadding='0' border-collapse='true' width='100%'><tr><td style='width:29%; text-align:right; vertical-align: top; padding-top: 2px;'><label id='label_staffInfo_staffName' class='fieldlabel'  for='text_staffInfo_staffName'  style='width:100%;padding-right:0px;'>员工姓名：</label></td><td colspan='1' style='width:70%;'><input type='text' id='text_staffInfo_staffName' class='editor' dataset='staffInfo' field='staffName'  style='width:100%;color:black;background-color:;'   validType='require' msg='请输入员工姓名!' dropDown='PRODUCT_CATALOG_CATALOG_USAGE' required='true'  dataType2='string'  /></td><td id='_label_staffInfo_staffName' class='require_td'><span class='require'>*</span></td></tr><tr><td style='width:29%; text-align:right; vertical-align: top; padding-top: 2px;'><label id='label_staffInfo_staffCode' class='fieldlabel'  for='text_staffInfo_staffCode'  style='width:100%;padding-right:0px;'>员工工号：</label></td><td colspan='1' style='width:70%;'><input type='text' id='text_staffInfo_staffCode' class='editor' dataset='staffInfo' field='staffCode'  style='width:100%;color:black;background-color:;'   validType='require' msg='请输入员工工号!' required='true'  dataType2='string'  /></td><td id='_label_staffInfo_staffCode' class='require_td'><span class='require'>*</span></td></tr><tr><td style='width:29%; text-align:right; vertical-align: top; padding-top: 2px;'><label id='label_staffInfo_password' class='fieldlabel'  for='text_staffInfo_password'  style='width:100%;padding-right:0px;'>员工密码：</label></td><td colspan='1' style='width:70%;'><input type='password' id='text_staffInfo_password' class='editor' dataset='staffInfo' field='password'  style='width:100%;color:black;background-color:;'   required='false'  dataType2='string'  /></td><td id='_label_staffInfo_password' class='not_require_td'>&nbsp;</td></tr></table></form><s>Form.initial('staffInfoForm');<s>var __t=createDataset('staffInfo');  __t.type='reference';  __t.readOnly=false;  __t.editable=false;  __t.autoLoadPage=false;  __t.pageSize=30;  __t.pageIndex=1;  __t.pageCount=1;  __t.recordCount=0;  __t.async=true;  __t.loadDataAction='';  __t.loadDataActionMethod='';  __t._queryType='dataset';  __t.paginate=false;  __t.staticDataSource=true;  __t.xmlFormat='records';  __t.loadAsNeeded=false;  __t.masterDataset='';  __t.masterKeyFields='';  __t.detailKeyFields='';  __t.maskControl=false;  staffInfo=__t;  var __f = __t.addField('staffName', 'string');  __f.label='员工姓名';  __f.required=true;  __f.dropDown='PRODUCT_CATALOG_CATALOG_USAGE';  __f.validType='require';  __f.validMsg='请输入员工姓名!';  __f.size='10';  __f.dataType2='string';  __f.isResult=false;  var __f = __t.addField('staffCode', 'string');  __f.label='员工工号';  __f.required=true;  __f.validType='require';  __f.validMsg='请输入员工工号!';  __f.size='9';  __f.dataType2='string';  __f.isResult=false;  var __f = __t.addField('password', 'string');  __f.label='员工密码';  __f.required=false;  __f.size='15';  __f.dataType2='string';  __f.isResult=false;  fillDataset(__t); if (__t.masterDataset) {    __t.setMasterDataset(__t.masterDataset, __t.masterKeyFields, __t.detailKeyFields); }";
	    try{
	    	StringBuffer buffer=new StringBuffer();	
	    	StringBuffer xml=new StringBuffer();
	    	StringBuffer html=new StringBuffer("<table width='100%' border='0' cellpadding='0' cellspacing='0'>");
	    	StringBuffer script=new StringBuffer();
	    	
	    	StringBuffer attrCodes=new StringBuffer();
	    	for(int i=0;i<entryIds.length;i++){
		    	DataSet dataset=new DataSet(entryIds[i],servIds[i]);
		    	if(dataset==null)
		    		continue;
		    	Form form=new Form(dataset);
		    	xml.append(dataset.getXml());
		    	
		    	html.append("<tr><td align='center' width='100%'><fieldset style='border:0px solid silver;height:98%;width:100%; padding-top:3px;padding-bottom:3px;'>" +
		    					   		"<legend>"+ entryNames[i] +"<img src='/VsopWeb/public/skins/bsn/quickturnel/icon_arrow_leftmenu.gif' style='cursor:hand;filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=1);'></legend>"+
		    					   		form.getHTML()+
		    					   	"</fieldset></td></tr>");
		    	/*html.append("<tr></tr>"+
		    			"<tr><td width='1%' nowrap>&nbsp;</td>" +
		    			"<td>"+form.getHTML()+"</td></tr>" +
		    					"<tr><td nowrap>"+entryNames[i]+"</td>  <td><hr class='hr1'></td></tr>");*/	    	
		    	//html.append("<tr><td>"+form.getHTML()+"</td></tr>");
		    	script.append(form.getScript());
		    	script.append(dataset.getScript());
		    	attrCodes.append(dataset.getDropDownAttrCode());
	    	}
	    	
	    	html.append("</table>");
	    	buffer.append(xml).append(SPLIT).append(html).append(SPLIT).append(script).append(SPLIT).append(attrCodes);
	    	
	    	System.out.println("DYNAMIC:"+buffer.toString());
	    	
	    	out.println(buffer.toString());

	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	if(out!=null){
	    		out.close();
	    	}
	    }

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}

