/**
 * 
 */
package com.ztesoft.component.ui.taglib;

import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;

/**
 * @author Administrator
 *
 */
public class DataLoader extends BodyTagSupport {
	private static final Logger logger = Logger.getLogger(DataLoader.class);
	private String appId ;
	private String dataType ;
	private String parameters ;
	private String datasetId ;
	/**
	 * @return Returns the logger.
	 */
	public static Logger getLogger() {
		return logger;
	}
	/**
	 * @return Returns the datasetId.
	 */
	public String getDatasetId() {
		return datasetId;
	}
	/**
	 * @param datasetId The datasetId to set.
	 */
	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}
	/**
	 * @return Returns the appId.
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * @param appId The appId to set.
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * @return Returns the parameters.
	 */
	public String getParameters() {
		return parameters;
	}
	/**
	 * @param parameters The parameters to set.
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	public int doAfterBody() throws JspException {
		HashMap parametersMap = new HashMap() ;
		String parameters = this.getParameters();
		String[] keyValues = parameters.split("&");
		for( int i = 0 ; i < keyValues.length ; i ++ ){
			String[] valuePaire = keyValues[i].split("=");
			parametersMap.put( valuePaire[0], valuePaire[1]);
		}
		HashMap returnMap = new HashMap() ;
		try{
			if( "static".equals(this.getDataType())){
//				returnMap = StaticLoadEng.load(this.getAppId(),parametersMap);
//				if( returnMap != null ){
//					this.pageContext.getRequest().setAttribute("____static_data",returnMap);
//				}
			}else if( "control".equals(this.getDataType())){
				LoginRespond loginRespond = (LoginRespond)this.pageContext.getSession().getAttribute("LoginRespond");
				RoleState[] roleStates = loginRespond.getRoleState();
				String[] staffPrivilegeCodes = new String[roleStates.length];
				for( int i = 0; i < roleStates.length ; i ++ ){
					staffPrivilegeCodes[i] = roleStates[i].getPrivilegeCode();
				}
				parametersMap.put("staffPrivilegeCodes",staffPrivilegeCodes);
//				ControlLoadPlanReturn controlLoadPlanReturn = ControlLoadEng.load(this.getAppId(),parametersMap);
//				if( controlLoadPlanReturn != null ){
//					String name = "____control_data_" + this.getDatasetId();
//					this.pageContext.getRequest().setAttribute("____control_data",controlLoadPlanReturn);
//					this.pageContext.getRequest().setAttribute(name,controlLoadPlanReturn);
//				}
			}
		}catch(Exception e ){
			logger.error("dataLoad��ǩ����ʧ��", e);
			throw new JspException( e ) ;
		}
		
		return super.doAfterBody();
	}
	
	public void release() {
		super.release();	
		
		this.appId = null ;
		this.parameters = null ;
	}
	/**
	 * @return Returns the dataType.
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType The dataType to set.
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
