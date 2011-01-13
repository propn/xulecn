package com.ztesoft.oaas.vo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;
public class MmDataPrivVO extends ValueObject implements VO {

	private String privId = "";
	private String dataPkey1 = "";
	private String dataPkey2 = "";
	private String dataPkey3 = "";
	private String dataPkey4 = "";
	private String name = "";
	
	private String datasetName = "";
	private String fieldName = "";
	
	private String offerName = "";
	private String serviceOfferName = "";

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getServiceOfferName() {
		return serviceOfferName;
	}

	public void setServiceOfferName(String serviceOfferName) {
		this.serviceOfferName = serviceOfferName;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MmDataPrivVO() {}

	public MmDataPrivVO( String pprivId, String pdataPkey1, String pdataPkey2, String pdataPkey3,String pdataPkey4 ) {
		privId = pprivId;
		dataPkey1 = pdataPkey1;
		dataPkey2 = pdataPkey2;
		dataPkey3 = pdataPkey3;
		dataPkey4 = pdataPkey4;
	}

	public String getPrivId() {
		return privId;
	}

	public String getDataPkey1() {
		return dataPkey1;
	}

	public String getDataPkey2() {
		return dataPkey2;
	}

	public String getDataPkey3() {
		return dataPkey3;
	}

	public void setPrivId(String pPrivId) {
		privId = pPrivId;
	}

	public void setDataPkey1(String pDataPkey1) {
		dataPkey1 = pDataPkey1;
	}

	public void setDataPkey2(String pDataPkey2) {
		dataPkey2 = pDataPkey2;
	}

	public void setDataPkey3(String pDataPkey3) {
		dataPkey3 = pDataPkey3;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PRIVILEGE_ID",this.privId);
		hashMap.put("DATA_PKEY_1",this.dataPkey1);
		hashMap.put("DATA_PKEY_2",this.dataPkey2);
		hashMap.put("DATA_PKEY_3",this.dataPkey3);
		hashMap.put("DATA_PKEY_4",this.dataPkey4);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.privId = (String) hashMap.get("PRIVILEGE_ID");
			this.dataPkey1 = (String) hashMap.get("DATA_PKEY_1");
			this.dataPkey2 = (String) hashMap.get("DATA_PKEY_2");
			this.dataPkey3 = (String) hashMap.get("DATA_PKEY_3");
			this.dataPkey4 = (String) hashMap.get("DATA_PKEY_4");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("DATA_PKEY_1");
		arrayList.add("DATA_PKEY_2");
		arrayList.add("DATA_PKEY_3");
		arrayList.add("DATA_PKEY_4");
		arrayList.add("PRIVILEGE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "MM_DATA_PRIVILEGE";
	}

	/**
	 * @return Returns the dataPkey4.
	 */
	public String getDataPkey4() {
		return dataPkey4;
	}

	/**
	 * @param dataPkey4 The dataPkey4 to set.
	 */
	public void setDataPkey4(String dataPkey4) {
		this.dataPkey4 = dataPkey4;
	}

}