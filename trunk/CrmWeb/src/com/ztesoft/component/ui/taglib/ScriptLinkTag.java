package com.ztesoft.component.ui.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class ScriptLinkTag 	extends BodyTagSupport{
	
	protected String tableCode = "";
	protected String lanId = "";
	
	public String getLanId() {
		return lanId;
	}

	public void setLanId(String lanId) {
		this.lanId = lanId;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	
	public int doStartTag() throws JspException {

 
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(this.getScriptLink());

		} catch (IOException e) {
			e.printStackTrace();
			//throw new JspException(e.getMessage());
		}
		return super.doStartTag();

	}
	
	private String getScriptLink(){
		
		List retList = new ArrayList();
		
		if(null==this.tableCode||"".equals(this.tableCode)){
			return "";
		}
		if(null==this.lanId||"".equals(this.lanId)){
			return "";
		}
		retList = this.getScriptList(tableCode,lanId);
		
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("<script>");		
		
//		if(null!=retList&&retList.size()>0){
//			
//			for(int i=0;i<retList.size();i++){
//				
//				ScriptLinkVO vo = (ScriptLinkVO)retList.get(i);
//				sbuffer.append("function table_"+vo.getDataset()+"_"+vo.getFieldCode()+"_onRefresh(cell, value, record){ ");
//				sbuffer.append(" cell.innerHTML = '<a class=hrefLink href=# onclick=linkFlash("+vo.getLinkId()+"); return false; >'+value+'</a>'; ");
//				sbuffer.append("}");
//			}
//		}

		sbuffer.append("</script>");
		return sbuffer.toString();
	}
	
	
	private List getScriptList(String tableCode,String lanId){
	
//		ScriptLinkHelperBO bo = new ScriptLinkHelperBO();
//		return bo.getScriptLink(tableCode,lanId);
		return null ;
	}

}
