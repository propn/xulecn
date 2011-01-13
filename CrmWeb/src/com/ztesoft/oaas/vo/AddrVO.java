package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class AddrVO extends ValueObject implements VO {

	private String addrId = "";
	private String provinceName = "";
	private String cityName = "";
	private String countyName = "";
	private String streetName = "";
	private String streetNbr = "";
	private String deta = "";
	private String postcode = "";
	private String alias = "";
	private String bankBranchId = "";
	private String bankAccNo = "";
	private String incrTaxNo = "" ;

	
	private String addDescription = "" ;
	
	public String getAddDescription() {
		return addDescription;
	}

	public void setAddDescription(String addDescription) {
		this.addDescription = addDescription;
	}

	public AddrVO() {}

	public AddrVO( String paddrId, String pprovinceName, String pcityName, String pcountyName, String pstreetName, String pstreetNbr, String pdeta, String ppostcode, String palias ) {
		addrId = paddrId;
		provinceName = pprovinceName;
		cityName = pcityName;
		countyName = pcountyName;
		streetName = pstreetName;
		streetNbr = pstreetNbr;
		deta = pdeta;
		postcode = ppostcode;
		alias = palias;
	}

	public String getAddrId() {
		return addrId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getStreetNbr() {
		return streetNbr;
	}

	public String getDeta() {
		return deta;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getAlias() {
		return alias;
	}

	public void setAddrId(String pAddrId) {
		addrId = pAddrId;
	}

	public void setProvinceName(String pProvinceName) {
		provinceName = pProvinceName;
	}

	public void setCityName(String pCityName) {
		cityName = pCityName;
	}

	public void setCountyName(String pCountyName) {
		countyName = pCountyName;
	}

	public void setStreetName(String pStreetName) {
		streetName = pStreetName;
	}

	public void setStreetNbr(String pStreetNbr) {
		streetNbr = pStreetNbr;
	}

	public void setDeta(String pDeta) {
		deta = pDeta;
	}

	public void setPostcode(String pPostcode) {
		postcode = pPostcode;
	}

	public void setAlias(String pAlias) {
		alias = pAlias;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ADDRESS_ID",this.addrId);
		hashMap.put("PROVINCE_NAME",this.provinceName);
		hashMap.put("CITY_NAME",this.cityName);
		hashMap.put("COUNTY_NAME",this.countyName);
		hashMap.put("STREET_NAME",this.streetName);
		hashMap.put("STREET_NBR",this.streetNbr);
		hashMap.put("DETAIL",this.deta);
		hashMap.put("POSTCODE",this.postcode);
		hashMap.put("ALIAS",this.alias);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.addrId = (String) hashMap.get("ADDRESS_ID");
			this.provinceName = (String) hashMap.get("PROVINCE_NAME");
			this.cityName = (String) hashMap.get("CITY_NAME");
			this.countyName = (String) hashMap.get("COUNTY_NAME");
			this.streetName = (String) hashMap.get("STREET_NAME");
			this.streetNbr = (String) hashMap.get("STREET_NBR");
			this.deta = (String) hashMap.get("DETAIL");
			this.postcode = (String) hashMap.get("POSTCODE");
			this.alias = (String) hashMap.get("ALIAS");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ADDRESS_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ADDRESS";
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(String bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public String getIncrTaxNo() {
		return incrTaxNo;
	}

	public void setIncrTaxNo(String incrTaxNo) {
		this.incrTaxNo = incrTaxNo;
	}

}
