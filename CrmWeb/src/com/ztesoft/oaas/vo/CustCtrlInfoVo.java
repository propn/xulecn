package com.ztesoft.oaas.vo;

import java.io.Serializable;

public class CustCtrlInfoVo implements Serializable {
	private String pageCode = "";
	private String datasetCode = "";
	private String custTypeId = "" ;
	private String fieldCode = "";
	public String getCustTypeId() {
		return custTypeId;
	}
	public void setCustTypeId(String custTypeId) {
		this.custTypeId = custTypeId;
	}
	public String getDatasetCode() {
		return datasetCode;
	}
	public void setDatasetCode(String datasetCode) {
		this.datasetCode = datasetCode;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	public String getPageCode() {
		return pageCode;
	}
	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}
}
