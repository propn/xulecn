package com.ztesoft.vsop.product.vo;

import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class ProductType  extends ValueObject implements VO, XMLItem {

//	t.product_type_id,t.product_type_name,t.service_code 
	
	
	private String product_type_id ;
	private String product_type_name ;
	private String service_code ;
	
	public ProductType(){
		
	}
	
	public ProductType(String product_type_id , String product_type_name , String service_code){
		this.product_type_id = product_type_id ;
		this.product_type_name = product_type_name ;
		this.service_code = service_code ;
	}
    public String getProduct_type_id() {
		return product_type_id;
	}

	public void setProduct_type_id(String product_type_id) {
		this.product_type_id = product_type_id;
	}

	public String getProduct_type_name() {
		return product_type_name;
	}

	public void setProduct_type_name(String product_type_name) {
		this.product_type_name = product_type_name;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	/**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("product_type_id='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.product_type_id));
        sbXml.append("' ");
        sbXml.append("product_type_name='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.product_type_name));
        sbXml.append("' ");
        sbXml.append("service_code='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.service_code));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }

    /**
     * 树路径
     */
    public String pathInTree() {
        return null;
    }

	
	public List getKeyFields() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void loadFromHashMap(HashMap map) {
		// TODO Auto-generated method stub
		this.product_type_id = (String)map.get("product_type_id") ;
		this.product_type_name = (String)map.get("product_type_name") ;
		this.service_code = (String)map.get("service_code") ;
	}

	public HashMap unloadToHashMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
