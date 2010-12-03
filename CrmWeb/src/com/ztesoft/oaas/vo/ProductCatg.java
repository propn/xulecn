package com.ztesoft.oaas.vo;

import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;

public class ProductCatg implements XMLItem{
	private String catalog_name;
	private String catalog_id;
	private String catalog_type;
	private String catalog_item_id;
	
	public ProductCatg(){
		
	}
	
	public ProductCatg(String catalog_name,String catalog_id,String catalog_type,String catalog_item_id){
		this.catalog_name = catalog_name;
		this.catalog_id = catalog_id;
		this.catalog_type = catalog_type;
		this.catalog_item_id = catalog_item_id;
		
	}

	/**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("catalog_name='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.catalog_name));
        sbXml.append("' ");
        sbXml.append("catalog_id='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.catalog_id));
        sbXml.append("' ");
        sbXml.append("catalog_type='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.catalog_type));
        sbXml.append("' ");
        sbXml.append("catalog_item_id='");	//根目录的'catalog_item_id'没有意义，设置为空
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.catalog_item_id));
        sbXml.append("' ");
        sbXml.append("parent_catalog_item_id='");
        sbXml.append("");
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
		this.catalog_name = (String)map.get("catalog_name") ;
		this.catalog_id = (String)map.get("catalog_id") ;
		this.catalog_type = (String)map.get("catalog_type") ;
		this.catalog_item_id = (String)map.get("catalog_item_id") ;
	}

	public HashMap unloadToHashMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//getter & setter
	public String getCatalog_name() {
		return catalog_name;
	}

	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}

	public String getCatalog_id() {
		return catalog_id;
	}

	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}

	public String getCatalog_type() {
		return catalog_type;
	}

	public void setCatalog_type(String catalog_type) {
		this.catalog_type = catalog_type;
	}

	public String getCatalog_item_id() {
		return catalog_item_id;
	}

	public void setCatalog_item_id(String catalog_item_id) {
		this.catalog_item_id = catalog_item_id;
	}
}
