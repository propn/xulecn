package com.ztesoft.crm.customer.custinfo.vo;

import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class BankVO extends ValueObject implements VO {
	

	private String bank_id;
	

	private String bank_name;
	

	private String bank_code;
	

	private String parent_bank_id;
	

	private String region_id;
	

	private String acct_len;
	
	private String pathCode = null;
	
	public String getpathCode() {
        return pathCode;
    }
    
    public void setpathCode(String pathCode) {
        this.pathCode = pathCode;
    }
	
	
	public String getBank_id() {
		return bank_id;
	}
	
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getBank_name() {
		return bank_name;
	}
	
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_code() {
		return bank_code;
	}
	
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getParent_bank_id() {
		return parent_bank_id;
	}
	
	public void setParent_bank_id(String parent_bank_id) {
		this.parent_bank_id = parent_bank_id;
	}
	public String getRegion_id() {
		return region_id;
	}
	
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getAcct_len() {
		return acct_len;
	}
	
	public void setAcct_len(String acct_len) {
		this.acct_len = acct_len;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("bank_id",this.bank_id);	
				
		hashMap.put("bank_name",this.bank_name);	
				
		hashMap.put("bank_code",this.bank_code);	
				
		hashMap.put("parent_bank_id",this.parent_bank_id);	
				
		hashMap.put("region_id",this.region_id);	
				
		hashMap.put("acct_len",this.acct_len);	
		
		hashMap.put("pathCode",this.pathCode);	
		
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.bank_id = Const.getStrValue(hashMap, "BANK_ID");
						
			this.bank_name = Const.getStrValue(hashMap, "BANK_NAME");
						
			this.bank_code = Const.getStrValue(hashMap, "BANK_CODE");
						
			this.parent_bank_id = Const.getStrValue(hashMap, "PARENT_BANK_ID");
						
			this.region_id = Const.getStrValue(hashMap, "REGION_ID");
						
			this.acct_len = Const.getStrValue(hashMap, "ACCT_LEN");
			this.pathCode = Const.getStrValue(hashMap, "PATHCODE");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "BANK";
	}
	
	 public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
	    {
	        sbXml.append("<item ");
	        sbXml.append("bank_id='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.bank_id));
	        sbXml.append("' ");
	        sbXml.append("bank_name='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.bank_name));
	        sbXml.append("' ");
	        sbXml.append("parent_bank_id='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.parent_bank_id));
	        sbXml.append("' ");
	        sbXml.append("region_id='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.region_id));
	        sbXml.append("' ");
	        sbXml.append("pathCode='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.pathCode));
	        sbXml.append("' ");      
	        sbXml.append(">");
	        return sbXml;
	    }

	    public String pathInTree() {
	        return null;
	    }   
	
}
