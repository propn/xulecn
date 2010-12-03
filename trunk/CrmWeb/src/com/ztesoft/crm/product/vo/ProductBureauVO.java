package com.ztesoft.crm.product.vo;

import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class ProductBureauVO extends ValueObject implements VO {
	

	private String product_id;
	

	private String region_id;
	
	private String parent_region_id;
	
	private String eff_date;
	
	private String region_name;
	

	private String exp_date;
	

	private String default_flag;
	

	private String state;
	

	private String seq;
	

	private String party_id;
	

	private String party_role_id;
	

	private String oper_region_id;
	

	private String oper_date;
	

	public String getProduct_id() {
		return product_id;
	}
	
	public String getParent_region_id() {
		return parent_region_id;
	}
	
	public void setParent_region_id(String parent_region_id) {
		this.parent_region_id = parent_region_id;
	}
	
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getRegion_id() {
		return region_id;
	}
	
	public String getRegion_name() {
		return region_name;
	}	

	public void setRegion_name(String region_name) {
		
		this.region_name = region_name;
	}	
	
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getEff_date() {
		return eff_date;
	}
	
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getExp_date() {
		return exp_date;
	}
	
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	public String getDefault_flag() {
		return default_flag;
	}
	
	public void setDefault_flag(String default_flag) {
		this.default_flag = default_flag;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getSeq() {
		return seq;
	}
	
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getParty_id() {
		return party_id;
	}
	
	
	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}
	public String getParty_role_id() {
		return party_role_id;
	}
	
	public void setParty_role_id(String party_role_id) {
		this.party_role_id = party_role_id;
	}
	public String getOper_region_id() {
		return oper_region_id;
	}
	
	public void setOper_region_id(String oper_region_id) {
		this.oper_region_id = oper_region_id;
	}
	public String getOper_date() {
		return oper_date;
	}
	
	public void setOper_date(String oper_date) {
		this.oper_date = oper_date;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("product_id",this.product_id);	
		
		hashMap.put("parent_region_id",this.parent_region_id);	

		hashMap.put("region_name",this.region_name);			
				
		hashMap.put("region_id",this.region_id);	
				
		hashMap.put("eff_date",this.eff_date);	
				
		hashMap.put("exp_date",this.exp_date);	
				
		hashMap.put("default_flag",this.default_flag);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("seq",this.seq);	
				
		hashMap.put("party_id",this.party_id);	
				
		hashMap.put("party_role_id",this.party_role_id);	
				
		hashMap.put("oper_region_id",this.oper_region_id);	
				
		hashMap.put("oper_date",this.oper_date);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			System.out.println(hashMap.toString());
						
			this.product_id = Const.getStrValue(hashMap, "product_id");
						
			this.region_id = Const.getStrValue(hashMap, "region_id");
			
			this.region_name = Const.getStrValue(hashMap, "region_name");
			
			this.parent_region_id = Const.getStrValue(hashMap, "parent_region_id");
									
			this.eff_date = Const.getStrValue(hashMap, "eff_date");
						
			this.exp_date = Const.getStrValue(hashMap, "exp_date");
						
			this.default_flag = Const.getStrValue(hashMap, "default_flag");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.seq = Const.getStrValue(hashMap, "seq");
						
			this.party_id = Const.getStrValue(hashMap, "party_id");
						
			this.party_role_id = Const.getStrValue(hashMap, "party_role_id");
						
			this.oper_region_id = Const.getStrValue(hashMap, "oper_region_id");
						
			this.oper_date = Const.getStrValue(hashMap, "oper_date");
						
		}
	}
	
	public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
	{
	        sbXml.append("<item ");
	        sbXml.append("region_name='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.region_name));
	        sbXml.append("' ");
	        sbXml.append("region_id='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.region_id));
	        sbXml.append("' ");
	        sbXml.append("parent_region_id='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.parent_region_id));
	        sbXml.append("' ");
	        sbXml.append(">");
	        return sbXml;
	 }
	 
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "PRODUCT_BUREAU";
	}
	
}
