package com.ztesoft.component.ui.taglib;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import com.ztesoft.common.exception.CommonError;
import com.ztesoft.common.exception.CommonException;
import com.ztesoft.common.exception.CrmException;
import com.ztesoft.common.exception.ExceptionVO;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.oaas.service.PrivilegeService;

public class PermissionTag extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PermissionTag.class);
	
	private String id;
	private String disabled;
	
	private ArrayList securities;
	
	public ArrayList getSecurities() {
		if(securities == null){
			securities = new ArrayList();			
		}		
		return securities;
	}
	public void setSecurities(ArrayList securities) {
		this.securities = securities;
	}
	public void setSecurity(Object security){
		if(securities == null){
			securities = new ArrayList();			
		}
		securities.add(security);
	}
	
	public String getDisabled() {
		return (disabled!=null ? disabled : "false");
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		
		if("false".equalsIgnoreCase(this.getDisabled())){
			String[] privilegeCodes = new String[this.getSecurities().size()];
					
			for(int i=0; i<this.getSecurities().size(); i++){
				privilegeCodes[i] = ((SecurityVo)this.getSecurities().get(i)).getPrivilegeCode();			
			}
			
			ArrayList targetCodes = new ArrayList(); 
			
			try{ 
				targetCodes = new PrivilegeService().privilegesCheckWithSession(privilegeCodes, ((HttpServletRequest)pageContext.getRequest()).getSession());
			}catch(Exception e){
				logger.error("��ȡ�ؼ�Ȩ���б�ʧ�ܣ�", null);
				
				StringBuffer sbuffer = new StringBuffer();
				if (e instanceof CrmException) {					
					CrmException crmEx = (CrmException) e;
					sbuffer.append((new ExceptionVO(crmEx)).toJsObject());		
				}else{
					CommonException commonEx = new CommonException(new CommonError(CommonError.COMMON_ERROR), e); 
					sbuffer.append((new ExceptionVO(commonEx)).toJsObject());	
				}
				
				try{
					String result = sbuffer.toString();
					if(!ContentManage.isSetParentContent(this, result)){
						pageContext.getOut().println(result);
					}
				}catch(Exception err){		
				}
			}
			
			if(targetCodes!=null){
				ArrayList targetSecurityVo = new ArrayList();
				for(int i=0; i<targetCodes.size(); i++){
					for(int j=0; j<this.getSecurities().size(); j++){
						String privilegeCode = ((SecurityVo)this.getSecurities().get(j)).getPrivilegeCode();
						if(privilegeCode.equalsIgnoreCase((String)targetCodes.get(i))){
							targetSecurityVo.add(this.getSecurities().get(j));
						}
					}
				}
				if(targetSecurityVo.size()>0){
					PermissionVo permissionVo = new PermissionVo();
					permissionVo.setSecurityVo(targetSecurityVo);
					pageContext.setAttribute("PERMISSION", permissionVo);
				}
			}			
			
		}	
		
		return super.doEndTag();
	}
	public void release() {
		// TODO Auto-generated method stub
		super.release();
		this.id = null;
		this.disabled = null;
	}
	

}
