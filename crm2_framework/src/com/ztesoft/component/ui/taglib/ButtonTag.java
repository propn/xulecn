package com.ztesoft.component.ui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.CrmParamsConfig;

public class ButtonTag extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ButtonTag.class);

	private String id;

	private String accessKey;

	private String style;

	private String disabled;

	private String dynaControlId;

	private String display;
	
	private String content;

	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * @param display
	 *            the display to set
	 */
	public void setDisplay(String display) {
		this.display = display;
	}

	/**
	 * @return the dynaControlId
	 */
	public String getDynaControlId() {
		return dynaControlId;
	}

	/**
	 * @param dynaControlId
	 *            the dynaControlId to set
	 */
	public void setDynaControlId(String dynaControlId) {
		this.dynaControlId = dynaControlId;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getStyle() {
		return (style != null ? style : "");
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getButtonHTML(String id, String accessKey, String value,
			String controlResult, String disable,String elementEventName,String datasetId,String fieldName,String actionId,String toolTip) {
		//add by lhw,������ݿ���������ʾ����ȡ���ݿ����ʾ����

		StringBuffer sbuffer = new StringBuffer();
		
//		ControlLoadPlanReturn controlLoadPlanReturn = (ControlLoadPlanReturn)pageContext.getRequest().getAttribute("____control_data");
//		if( controlLoadPlanReturn != null ){
//			ArrayList readOnlyList = controlLoadPlanReturn.getReadonlyAttrList();
//			ArrayList unvisibleList = controlLoadPlanReturn.getUnvisibleAttrList() ;
//			if( readOnlyList != null ){
//				for( int i = 0 ; i < readOnlyList.size() ; i ++ ){
//					String readOnlyId = (String)readOnlyList.get(i);
//					if( this.id.equalsIgnoreCase(readOnlyId)){
//						controlResult = "disabled";
//					}
//				}
//			}
//			if( unvisibleList != null ){
//				for( int i = 0 ; i < unvisibleList.size() ; i ++ ){
//					String unvisibleId = (String)unvisibleList.get(i);
//					if( this.id.equalsIgnoreCase(unvisibleId)){
//						controlResult = "invisible";
//					}
//				}
//			}
//		}

		if ("invisible".equalsIgnoreCase(controlResult)) {
			// �������
		} else {

			if (this.dynaControlId != null && !"".equals(this.dynaControlId)) {
				try {
					PageTagUtils.getInstance().setButtonTagDynaElement(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String style = "";
			if ("disabled".equalsIgnoreCase(controlResult)) {
				style = "disabled='true'";
			}
			String disableString = "";
			if (this.getDisabled() != null) {
				disableString = "disabled='" + this.getDisabled() + "'";
			}
			if(this.getDisplay() == null){
				this.setDisplay("");
			}
			sbuffer.append("<button class='coolButton2' id='" + id
					+ "' initialized='true' " + style + " style='"
					+ this.getStyle() + this.getDisplay() + "' " + disable
					+ " " + disableString + " ");
			
			if(toolTip!=null&&!"".equals(toolTip)){
				sbuffer.append(" title='"+toolTip+"'");
			}
			if (accessKey != null && !"".equalsIgnoreCase(accessKey)) {
				sbuffer.append(" accesskey='" + accessKey + "'");
			}
			if(elementEventName!=null&&!"".equals(elementEventName)){
				sbuffer.append(" onclick='btnOnClick(this ,\""+ elementEventName +"\","+ datasetId +",\""+ fieldName+"\",\""+ actionId +"\")'");
//				sbuffer.append(" onclick='if(this.disabled) return false;"+elementEventName+"(\""+datasetId+"\",\""+fieldName+"\");return false;' ");
			}else{
				sbuffer.append(" onclick='btnOnClick(this)'");
//				sbuffer.append(" onclick='if(this.disabled) return false;Document.fireUserEvent(Document.getElementEventName(this, \"onClick\"), [this]);return false;' ");
			}
			sbuffer.append(">");
			sbuffer.append("<span class='button_left'>&nbsp;</span>");
			sbuffer.append("<span class='button_center'>");
			
			if(this.getContent()!=null&&!"".equals(this.getContent())){
				value=content;
			}
			
			sbuffer.append(value);
			if (accessKey != null && !"".equalsIgnoreCase(accessKey)) {
				sbuffer.append("(<span style='text-decoration:underline;'>"
						+ accessKey + "</span>)");
			}
			sbuffer.append("</span>");
			sbuffer.append("<span class='button_right'>&nbsp;</span>");
			sbuffer.append("</button>");
		}

		return sbuffer.toString();
	}



	public String getButtonHTML(String id, String accessKey, String value,
			String controlResult, String disable,String elementEventName,String datasetId,String fieldName) {
		//add by lhw,������ݿ���������ʾ����ȡ���ݿ����ʾ����

		StringBuffer sbuffer = new StringBuffer();
		
//		ControlLoadPlanReturn controlLoadPlanReturn = (ControlLoadPlanReturn)pageContext.getRequest().getAttribute("____control_data");
//		if( controlLoadPlanReturn != null ){
//			ArrayList readOnlyList = controlLoadPlanReturn.getReadonlyAttrList();
//			ArrayList unvisibleList = controlLoadPlanReturn.getUnvisibleAttrList() ;
//			if( readOnlyList != null ){
//				for( int i = 0 ; i < readOnlyList.size() ; i ++ ){
//					String readOnlyId = (String)readOnlyList.get(i);
//					if( this.id.equalsIgnoreCase(readOnlyId)){
//						controlResult = "disabled";
//					}
//				}
//			}
//			if( unvisibleList != null ){
//				for( int i = 0 ; i < unvisibleList.size() ; i ++ ){
//					String unvisibleId = (String)unvisibleList.get(i);
//					if( this.id.equalsIgnoreCase(unvisibleId)){
//						controlResult = "invisible";
//					}
//				}
//			}
//		}

		if ("invisible".equalsIgnoreCase(controlResult)) {
			// �������
		} else {

			if (this.dynaControlId != null && !"".equals(this.dynaControlId)) {
				try {
					PageTagUtils.getInstance().setButtonTagDynaElement(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String style = "";
			if ("disabled".equalsIgnoreCase(controlResult)) {
				style = "disabled='true'";
			}
			String disableString = "";
			if (this.getDisabled() != null) {
				disableString = "disabled='" + this.getDisabled() + "'";
			}
			if(this.getDisplay() == null){
				this.setDisplay("");
			}
			sbuffer.append("<button class='coolButton2' id='" + id
					+ "' initialized='true' " + style + " style='"
					+ this.getStyle() + this.getDisplay() + "' " + disable
					+ " " + disableString + " ");
			if (accessKey != null && !"".equalsIgnoreCase(accessKey)) {
				sbuffer.append(" accesskey='" + accessKey + "'");
			}
			if(elementEventName!=null&&!"".equals(elementEventName)){
				sbuffer.append(" onclick='btnOnClick(this ,\""+ elementEventName +"\","+ datasetId +",\""+ fieldName+"\")'");
//				sbuffer.append(" onclick='if(this.disabled) return false;"+elementEventName+"(\""+datasetId+"\",\""+fieldName+"\");return false;' ");
			}else{
				sbuffer.append(" onclick='btnOnClick(this)'");
//				sbuffer.append(" onclick='if(this.disabled) return false;Document.fireUserEvent(Document.getElementEventName(this, \"onClick\"), [this]);return false;' ");
			}
			sbuffer.append(">");
			sbuffer.append("<span class='button_left'>&nbsp;</span>");
			sbuffer.append("<span class='button_center'>");
			
			if(this.getContent()!=null&&!"".equals(this.getContent())){
				value=content;
			}
			
			sbuffer.append(value);
			if (accessKey != null && !"".equalsIgnoreCase(accessKey)) {
				sbuffer.append("(<span style='text-decoration:underline;'>"
						+ accessKey + "</span>)");
			}
			sbuffer.append("</span>");
			sbuffer.append("<span class='button_right'>&nbsp;</span>");
			sbuffer.append("</button>");
		}

		return sbuffer.toString();
	}

	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub

		String controlResult = "";
		Object permission = pageContext.getAttribute("PERMISSION");
		if (permission != null && permission instanceof PermissionVo) {
			PermissionVo perm = (PermissionVo) permission;
			controlResult = perm.getControlResult(this.getId());
		}
		String appName = CrmParamsConfig.getInstance().getParamValue("APP_NAME") ;//ϵͳ����
		String buttonHTML = null ;
		if(!"PPMWeb".equals(appName))
			buttonHTML = this.getButtonHTML(this.getId(), this
				.getAccessKey(), getBodyContent().getString(), controlResult,
				"",null,null,null);
		else //PPM��չ
			buttonHTML = this.getButtonHTML(this.getId(), this
					.getAccessKey(), getBodyContent().getString(), controlResult,
					"",null,null,null,null,null);
		
		try {
			String result = buttonHTML;
			if (!ContentManage.isSetParentContent(this, result)) {
				getBodyContent().getEnclosingWriter().println(result);
			}
		} catch (Exception e) {
			logger.error("��ť��ǩ����ʧ��", e);
		}
		return super.doAfterBody();
	}

	public void release() {
		// TODO Auto-generated method stub
		super.release();

		this.id = null;
		this.accessKey = null;
		this.style = null;
		this.disabled = null;

	}

	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
