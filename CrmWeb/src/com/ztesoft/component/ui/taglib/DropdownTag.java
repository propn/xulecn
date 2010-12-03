package com.ztesoft.component.ui.taglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.ztesoft.common.exception.CommonError;
import com.ztesoft.common.exception.CommonException;
import com.ztesoft.common.exception.CrmException;
import com.ztesoft.common.exception.ExceptionVO;
import com.ztesoft.component.common.staticdata.service.StaticAttrService;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;

public class DropdownTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DropdownTag.class);

	private String id;
	private String type;
	private String mapValue;
	private String staticDataSource;
	private String fixed;
	private String maxHeight;
	private String cachable;
	private String showColumnHeader;
	private String attrCode;
	private String subList;
	private String blankValue;
	private String blankId;
	//private String configParams;

	
	public String getBlankId() {
		return blankId;
	}

	public void setBlankId(String blankId) {
		this.blankId = blankId;
	}

	public String getBlankValue() {
		return blankValue;
	}

	public void setBlankValue(String blankValue) {
		this.blankValue = blankValue;
	}

	public String getSubList() {
		return subList;
	}

	public void setSubList(String subList) {
		this.subList = subList;
	}

	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public String getCachable() {
		return (cachable != null ? cachable : "true");
	}

	public void setCachable(String cachable) {
		this.cachable = cachable;
	}

	public String getFixed() {
		return (fixed != null ? fixed : "true");
	}

	public void setFixed(String fixed) {
		this.fixed = fixed;
	}

	public String getMapValue() {
		return (mapValue != null ? mapValue : "true");
	}

	public void setMapValue(String mapValue) {
		this.mapValue = mapValue;
	}

	public String getMaxHeight() {
		return (maxHeight != null ? maxHeight : "220");
	}

	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}

	public String getShowColumnHeader() {
		return (showColumnHeader != null ? showColumnHeader : "false");
	}

	public void setShowColumnHeader(String showColumnHeader) {
		this.showColumnHeader = showColumnHeader;
	}

	public String getStaticDataSource() {
		return (staticDataSource != null ? staticDataSource : "true");
	}

	public void setStaticDataSource(String staticDataSource) {
		this.staticDataSource = staticDataSource;
	}

	public String getType() {
		return (type != null ? type : "list");
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static void setPageContextAttribute(PageContext pageContext,  String blankId, ArrayList items, String AttributeId){

		if(pageContext.getAttribute(AttributeId)==null || "".equals(pageContext.getAttribute(AttributeId))){
			if(blankId==null){
				pageContext.setAttribute(AttributeId, ((StaticAttrVO)(items.get(0))).getAttrValue());
			}
			else{
				pageContext.setAttribute(AttributeId, blankId);
			}
		}	
		
	}
	
	public static String getDropdownControl(PageContext pageContext, String attrCode, String parentValue, String blankId, String blankValue, String maxHeight, String configParams) throws Exception{
		if(attrCode==null) return "";
		
		StringBuffer sbuffer = new StringBuffer();
		StringBuffer errbuffer = new StringBuffer();
		CrmException crmEx = null;			
        
		
		sbuffer.append("<xml id='__" + attrCode + "'>");
		sbuffer.append("<items>");
		
		String bak_attrCode = attrCode;
		ArrayList items = null;
		try {
			if(!"".equals(attrCode)){
				if(parentValue!=null && !"".equalsIgnoreCase(parentValue)){
					StaticAttrService service = new StaticAttrService();
					service.setSession(pageContext.getSession());
					items = service.getSubStaticAttr(attrCode, parentValue);
				
				}else if(configParams!=null && !"".equalsIgnoreCase(configParams)){
					
					items = (new StaticAttrService()).loadDataByConfigParams(attrCode, configParams);
				
				}else {
					HashMap static_data_map = (HashMap)pageContext.getRequest().getAttribute("____static_data");
					if( static_data_map != null ){
						items = (ArrayList)static_data_map.get(attrCode);
					}
					if( items == null ){
						items = (new StaticAttrService()).getStaticAttr(attrCode);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("��ȡ�����ؼ�����ʧ�ܣ�", e);
			e.printStackTrace();

			if (e instanceof CrmException) {
				crmEx = (CrmException) e;
				errbuffer.append((new ExceptionVO(crmEx)).toJsObject());
			} else {
				crmEx = new CommonException(new CommonError(CommonError.COMMON_ERROR), e);
				errbuffer.append((new ExceptionVO(crmEx)).toJsObject());
			}
		}	
		if (items != null) {
			if(items.size()>0){
				if("DC_LAN_CODE".equalsIgnoreCase(bak_attrCode)){
					setPageContextAttribute(pageContext, blankId, items, "LAN_ID");								
				}
				else if("DC_BUSINESS_CODE".equalsIgnoreCase(bak_attrCode)){
					setPageContextAttribute(pageContext, blankId, items, "BUSINESS_ID");
				}
				else if("DC_DEAL_EXCH_ID".equalsIgnoreCase(bak_attrCode)){
					setPageContextAttribute(pageContext, blankId, items, "DEAL_EXCH_ID");
				}
				else if("DC_MASTER_EXCH_ID".equalsIgnoreCase(bak_attrCode)){
					setPageContextAttribute(pageContext, blankId, items, "MASTER_EXCH_ID");					
				}	
				else if("Z_DC_LAN_CODE".equalsIgnoreCase(bak_attrCode)){
					setPageContextAttribute(pageContext, blankId, items, "Z_LAN_ID");									
				}
				else if("Z_DC_BUSINESS_CODE".equalsIgnoreCase(bak_attrCode)){
					setPageContextAttribute(pageContext, blankId, items, "Z_BUSINESS_ID");	
				}
				else if("Z_DC_DEAL_EXCH_ID".equalsIgnoreCase(bak_attrCode)){
					setPageContextAttribute(pageContext, blankId, items, "Z_DEAL_EXCH_ID");	
				}
				else if("Z_DC_MASTER_EXCH_ID".equalsIgnoreCase(bak_attrCode)){
					setPageContextAttribute(pageContext, blankId, items, "Z_MASTER_EXCH_ID");						
				}
			}
			
			Iterator iterator = items.iterator();
			while (iterator.hasNext()) {
				StaticAttrVO item = (StaticAttrVO) iterator.next();
				sbuffer.append("<i l='" + item.getAttrValueDesc() + "' v='"
						+ item.getAttrValue() + "' vi='" + item.getAttrValueId() + "'/>");
			}
		}	
		
		
		

		sbuffer.append("</items>");
		sbuffer.append("</xml>");
		
		sbuffer.append("<CODE id='" + bak_attrCode + "' attrCode='"+attrCode+"'");
		if(blankValue!=null){
			if("true".equalsIgnoreCase(blankValue)){
				sbuffer.append(" blankValue='' blankId='"+(blankId==null?"":blankId)+"'");				
			}else{
				sbuffer.append(" blankValue='"+blankValue+"' blankId='"+(blankId==null?"":blankId)+"'");					
			}							
		}
		if(maxHeight!=null)sbuffer.append(" maxHeight='"+maxHeight+"'");
		if(configParams!=null)sbuffer.append(" configParams='"+configParams+"'");
		
		sbuffer.append("></CODE>");
		if (crmEx != null) {
			sbuffer.append(errbuffer);
		}
		
		return sbuffer.toString();
		
	}
		
	public int doEndTag() throws JspException {

		StringBuffer sbuffer = new StringBuffer();
		StringBuffer errbuffer = new StringBuffer();
		CrmException crmEx = null;

		if ("false".equalsIgnoreCase(this.getStaticDataSource())) {

			sbuffer.append("<xml id='__" + this.getId() + "'>");
			sbuffer.append("<items>");
			if (this.getAttrCode() != null) {
				ArrayList items = null;
				
				HashMap static_data_map = (HashMap)pageContext.getRequest().getAttribute("____static_data");
				if( static_data_map != null ){
					items = (ArrayList)static_data_map.get(this.getAttrCode());
				}
				
				if( items == null ){
					try {				
						items = (new StaticAttrService()).getStaticAttr(this.getAttrCode());
					} catch (Exception e) {
						logger.error("��ȡ�����ؼ�����ʧ�ܣ�", e);
		
						if (e instanceof CrmException) {
							crmEx = (CrmException) e;
							errbuffer.append((new ExceptionVO(crmEx)).toJsObject());
						} else {
							crmEx = new CommonException(new CommonError(CommonError.COMMON_ERROR), e);
							errbuffer.append((new ExceptionVO(crmEx)).toJsObject());
						}
					}
				}
				
				if (items != null) {
					Iterator iterator = items.iterator();
					while (iterator.hasNext()) {
						StaticAttrVO item = (StaticAttrVO) iterator.next();
						sbuffer.append("<item label='" + item.getAttrValueDesc() + "' value='"
								+ item.getAttrValue() + "' valueId='" + item.getAttrValueId() + "'></item>");
					}
				}
			}
				

			sbuffer.append("</items>");
			sbuffer.append("</xml>");
		
		}

		//sbuffer.append("<script>jspTaglibErrors[jspTaglibErrors.length]=new JspTaglibError('1','2','3','4','5');</script>");
		
		
		sbuffer.append("<CODE");
		sbuffer.append(" id='" + this.getId() + "'");
		
		if(!"list".equalsIgnoreCase(this.getType()))
			sbuffer.append(" type='" + this.getType() + "' ");
		
		if(!"true".equalsIgnoreCase(this.getMapValue()))
			sbuffer.append(" mapValue='" + this.getMapValue() + "' ");
		
		if(this.getAttrCode()!=null)
			sbuffer.append(" attrCode='" + this.getAttrCode() + "' ");	
		
		if (this.getBlankValue() != null) 
			sbuffer.append(" blankValue='" + this.getBlankValue() + "' ");

		if (this.getBlankId() != null) 
			sbuffer.append(" blankId='" + this.getBlankId() + "' ");		
		
		if (this.getFixed() != null) 
			sbuffer.append(" fixed='" + this.getFixed() + "' ");
		
		if (this.getMaxHeight() != null) 
			sbuffer.append(" maxHeight='" + this.getMaxHeight() + "' ");
		
		if (this.getCachable() != null) 
			sbuffer.append(" cachable='" + this.getCachable() + "' ");
		
		if (this.getShowColumnHeader() != null) 
			sbuffer.append(" showColumnHeader='" + this.getShowColumnHeader() + "' ");
		
		if (this.getSubList() != null) {
			sbuffer.append(" subList='" + this.getSubList() + "' ");
			sbuffer.append(" staticDataSource='true' ");
		}else{
			sbuffer.append(" staticDataSource='" + this.getStaticDataSource() + "' ");
		}

		sbuffer.append("></CODE>");
		//error js object
		if (crmEx != null) {
			sbuffer.append(errbuffer);
		}
		try {			
			String result = sbuffer.toString();
			if(!ContentManage.isSetParentContent(this, result)){	
				pageContext.getOut().println(result);		
			}
		} catch (Exception e) {
			logger.error("���������ǩ���ʹ���", e);
		}
		return super.doEndTag();
	}	
	
	public void release() {
		super.release();

		this.id = null;
		this.type = null;
		this.mapValue = null;
		this.staticDataSource = null;
		this.fixed = null;
		this.maxHeight = null;
		this.cachable = null;
		this.showColumnHeader = null;
		this.attrCode = null;
		this.subList = null;
		this.blankValue = null;
		this.blankId = null;

	}


}
