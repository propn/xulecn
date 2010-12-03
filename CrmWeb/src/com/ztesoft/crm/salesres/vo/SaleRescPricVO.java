package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class SaleRescPricVO extends ValueObject implements VO {

	private String salesRescId = "";
	private String businessId = "";
	private String businessName = "";
	private String rescLevel = "";
	private String price = "";
	private String acctItemId = "";
	/** 多个营业区的id字符串，不对应数据库字段 **/
	private String businessIds = "";
    private String feeitemName = "";
    private String provId = "";
    
    private String price2 = "";
    private String price3 = "";
    
	public SaleRescPricVO() {}

	public SaleRescPricVO( String psalesRescId, String pbusinessId, String prescLevel, String pprice, String pacctItemId ) {
		salesRescId = psalesRescId;
		businessId = pbusinessId;
		rescLevel = prescLevel;
		price = pprice;
		acctItemId = pacctItemId;
	}

	/**
	 * 根据输入的主键判断和本对象是否相同
	 * @param salesRescId
	 * @param businessId
	 * @param rescLevel
	 * @return
	 */
	public boolean equalsByPk(SaleRescPricVO vo){
		String psalesRescId = vo.getSalesRescId();
		String pbusinessId = vo.getBusinessId();
		String prescLevel = vo.getRescLevel();
		if((this.salesRescId==null&&psalesRescId==null)||(psalesRescId!=null&&psalesRescId.equals(this.salesRescId)))
			if((this.businessId==null&&pbusinessId==null)||(pbusinessId!=null&&pbusinessId.equals(this.businessId)))
				if((this.rescLevel==null&&prescLevel==null)||(prescLevel!=null&&prescLevel.equals(this.rescLevel)))
					return true;

		return false;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public String getRescLevel() {
		return rescLevel;
	}

	public String getPrice() {
		return price;
	}

	public String getAcctItemId() {
		return acctItemId;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setBusinessId(String pBusinessId) {
		businessId = pBusinessId;
	}

	public void setRescLevel(String pRescLevel) {
		rescLevel = pRescLevel;
	}

	public void setPrice(String pPrice) {
		price = pPrice;
	}

	public void setAcctItemId(String pAcctItemId) {
		acctItemId = pAcctItemId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("BUSINESS_ID",this.businessId);
		hashMap.put("RESOURCE_LEVEL",this.rescLevel);
		hashMap.put("PRICE",this.price);
		hashMap.put("ACCT_ITEM_ID",this.acctItemId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.businessId = (String) hashMap.get("BUSINESS_ID");
			this.rescLevel = (String) hashMap.get("RESOURCE_LEVEL");
			this.price = (String) hashMap.get("PRICE");
			this.acctItemId = (String) hashMap.get("ACCT_ITEM_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("BUSINESS_ID");
		arrayList.add("RESOURCE_LEVEL");
		arrayList.add("SALES_RESOURCE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "SALE_RESOURCE_PRIC";
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessIds() {
		return businessIds;
	}

    public String getFeeitemName() {
        return feeitemName;
    }

    public void setBusinessIds(String businessIds) {
		this.businessIds = businessIds;
	}

    public void setFeeitemName(String feeitemName) {
        this.feeitemName = feeitemName;
    }

    public String[] splitBusinessIds(){
		if(businessIds==null||businessIds.trim().length()<1)
		   return null;
		String[] arr = null;
		StringTokenizer token = new StringTokenizer(businessIds,",");
		arr = new String[token.countTokens()];
        for(int i=0;token.hasMoreElements();i++){
        	arr[i] = token.nextToken();
        }
        return arr;
	}


	public static void main(String[] args){
//		SaleRescPricVO vo = new SaleRescPricVO();
//		String str = ",";
//		vo.setBusinessIds(str);
//		String[] arr = vo.splitBusinessIds();
//		if(arr==null||arr.length==0)
//			System.out.println("str唯恐!!!");
//		else{
//			for(int i=0;i<arr.length;i++){
//				System.out.println(arr[i]);
//			}
//		}
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		map1.put("1", "10.12");
		map1.put("2", "20.12");
		map1.put("3", "30.12");
//		map2.put("3", "40.12");
//		map2.put("5", "50.12");
		map1.putAll(map2);
		System.out.println("map1:"+map1);
	}

	public String getProvId() {
		return provId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public String getPrice2() {
		return price2;
	}

	public void setPrice2(String price2) {
		this.price2 = price2;
	}

	public String getPrice3() {
		return price3;
	}

	public void setPrice3(String price3) {
		this.price3 = price3;
	}

}
