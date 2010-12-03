package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class MmMenuVO extends ValueObject implements VO, XMLItem{

	private String menuId = "";//菜单ID
	private String systemId = "";//系统ID
	private String menuCode = "";//菜单编码
	private String menuName = "";//菜单名称
	private String orderId = "";//排序ID=====================
	private String targetName = "";//菜单连接
	private String para = "";//菜单参数======================
	private String openFlag = "";//是否打开==================
	private String privFlag = "";//权限校验标志===================
	private String validFlag = "";//有效标志==================
	private String menuType = "";//菜单类型=================
	private String menuGrade = "";//菜单级别================
	private String superId = "";//上级菜单ID==================
	private String imagePath = "";//菜单图片路径==============
	private String comments = "";//菜单说明==================
	private String pathCode = "";
    private String privilegeId = "";//权限ID
    private String privilegeName = "";//权限名称

	public MmMenuVO() {}

	public MmMenuVO( String pmenuId, String psystemId, String pmenuCode, String pmenuName, String porderId, String ptargetName, String ppara, String popenFlag, String pprivFlag, String pvalidFlag, String pmenuType, String pmenuGrade, String psuperId, String pimagePath, String pcomments, String ppathCode ) {
		menuId = pmenuId;
		systemId = psystemId;
		menuCode = pmenuCode;
		menuName = pmenuName;
		orderId = porderId;
		targetName = ptargetName;
		para = ppara;
		openFlag = popenFlag;
		privFlag = pprivFlag;
		validFlag = pvalidFlag;
		menuType = pmenuType;
		menuGrade = pmenuGrade;
		superId = psuperId;
		imagePath = pimagePath;
		comments = pcomments;
		pathCode = ppathCode;
	}

	public String getMenuId() {
		return menuId;
	}

	public String getSystemId() {
		return systemId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getTargetName() {
		return targetName;
	}

	public String getPara() {
		return para;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public String getPrivFlag() {
		return privFlag;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public String getMenuType() {
		return menuType;
	}

	public String getMenuGrade() {
		return menuGrade;
	}

	public String getSuperId() {
		return superId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getComments() {
		return comments;
	}

    public String getPathCode() {
        return pathCode;
    }
    
	public void setMenuId(String pMenuId) {
		menuId = pMenuId;
	}

	public void setSystemId(String pSystemId) {
		systemId = pSystemId;
	}

	public void setMenuCode(String pMenuCode) {
		menuCode = pMenuCode;
	}

	public void setMenuName(String pMenuName) {
		menuName = pMenuName;
	}

	public void setOrderId(String pOrderId) {
		orderId = pOrderId;
	}

	public void setTargetName(String pTargetName) {
		targetName = pTargetName;
	}

	public void setPara(String pPara) {
		para = pPara;
	}

	public void setOpenFlag(String pOpenFlag) {
		openFlag = pOpenFlag;
	}

	public void setPrivFlag(String pPrivFlag) {
		privFlag = pPrivFlag;
	}

	public void setValidFlag(String pValidFlag) {
		validFlag = pValidFlag;
	}

	public void setMenuType(String pMenuType) {
		menuType = pMenuType;
	}

	public void setMenuGrade(String pMenuGrade) {
		menuGrade = pMenuGrade;
	}

	public void setSuperId(String pSuperId) {
		superId = pSuperId;
	}

	public void setImagePath(String pImagePath) {
		imagePath = pImagePath;
	}

	public void setComments(String pComments) {
		comments = pComments;
	}

	public void setPathCode(String pPathCode) {
		pathCode = pPathCode;
	}

    public String getPrivilegeId()
    {
        return privilegeId;
    }
    
    public void setPrivilegeId(String pPrivilegeId)
    {
        privilegeId = pPrivilegeId;
    }
    
    public String getPrivilegeName()
    {
        return privilegeName;
    }
    
    public void setPrivilegeName(String pPrivilegeName)
    {
        privilegeName = pPrivilegeName;
    }
    
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("MENU_ID",this.menuId);
		hashMap.put("SYSTEM_ID",this.systemId);
		hashMap.put("MENU_CODE",this.menuCode);
		hashMap.put("MENU_NAME",this.menuName);
		hashMap.put("ORDER_ID",this.orderId);
		hashMap.put("TARGET_NAME",this.targetName);
		hashMap.put("PARAMETER",this.para);
		hashMap.put("OPEN_FLAG",this.openFlag);
		hashMap.put("PRIVILEGE_FLAG",this.privFlag);
		hashMap.put("VALID_FLAG",this.validFlag);
		hashMap.put("MENU_TYPE",this.menuType);
		hashMap.put("MENU_GRADE",this.menuGrade);
		hashMap.put("SUPER_ID",this.superId);
		hashMap.put("IMAGE_PATH",this.imagePath);
		hashMap.put("COMMENTS",this.comments);
		hashMap.put("PATH_CODE",this.pathCode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.menuId = (String) hashMap.get("MENU_ID");
			this.systemId = (String) hashMap.get("SYSTEM_ID");
			this.menuCode = (String) hashMap.get("MENU_CODE");
			this.menuName = (String) hashMap.get("MENU_NAME");
			this.orderId = (String) hashMap.get("ORDER_ID");
			this.targetName = (String) hashMap.get("TARGET_NAME");
			this.para = (String) hashMap.get("PARAMETER");
			this.openFlag = (String) hashMap.get("OPEN_FLAG");
			this.privFlag = (String) hashMap.get("PRIVILEGE_FLAG");
			this.validFlag = (String) hashMap.get("VALID_FLAG");
			this.menuType = (String) hashMap.get("MENU_TYPE");
			this.menuGrade = (String) hashMap.get("MENU_GRADE");
			this.superId = (String) hashMap.get("SUPER_ID");
			this.imagePath = (String) hashMap.get("IMAGE_PATH");
			this.comments = (String) hashMap.get("COMMENTS");
			this.pathCode = (String) hashMap.get("PATH_CODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("MENU_ID");
		return arrayList;
	}

	public String getTableName() {
		return "MM_MENU";
	}

    /**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("menuId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.menuId));
        sbXml.append("' ");
        sbXml.append("systemId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.systemId));
        sbXml.append("' ");
        sbXml.append("menuCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.menuCode));
        sbXml.append("' ");
        sbXml.append("menuName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.menuName));
        sbXml.append("' ");
        sbXml.append("orderId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orderId));
        sbXml.append("' ");
        sbXml.append("targetName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.targetName));
        sbXml.append("' ");
        sbXml.append("para='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.para));
        sbXml.append("' ");
        sbXml.append("openFlag='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.openFlag));
        sbXml.append("' ");
        sbXml.append("privFlag='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privFlag));
        sbXml.append("' ");
        sbXml.append("validFlag='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.validFlag));
        sbXml.append("' ");
        sbXml.append("menuType='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.menuType));
        sbXml.append("' ");
        sbXml.append("menuGrade='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.menuGrade));
        sbXml.append("' ");
        sbXml.append("superId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.superId));
        sbXml.append("' ");
        sbXml.append("imagePath='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.imagePath));
        sbXml.append("' ");
        sbXml.append("comments='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.comments));
        sbXml.append("' ");
        sbXml.append("pathCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.pathCode));
        sbXml.append("' ");
        sbXml.append("privilegeId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privilegeId));
        sbXml.append("' ");
        sbXml.append("privilegeName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privilegeName));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }

    public String pathInTree() {
        return pathCode==null?"":pathCode;
    }

}
