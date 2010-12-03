package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcEntityReportVO extends ValueObject implements VO {

	private String lanId = "";
	private String termType = "";
	private String reportType = "";
	private String stockNumEnd = "";
	private String addNum = "";
	private String addNum2 = "";
	private String saleNum = "";
	private String stockNumCurr = "";
	private String differentnum = "";
	private String satdate = "";

	public RcEntityReportVO() {}

	public RcEntityReportVO( String planId, String ptermType, String preportType, String pstockNumEnd, String paddNum, String paddNum2, String psaleNum, String pstockNumCurr, String pdifferentnum, String psatdate ) {
		lanId = planId;
		termType = ptermType;
		reportType = preportType;
		stockNumEnd = pstockNumEnd;
		addNum = paddNum;
		addNum2 = paddNum2;
		saleNum = psaleNum;
		stockNumCurr = pstockNumCurr;
		differentnum = pdifferentnum;
		satdate = psatdate;
	}

	public String getLanId() {
		return lanId;
	}

	public String getTermType() {
		return termType;
	}

	public String getReportType() {
		return reportType;
	}

	public String getStockNumEnd() {
		return stockNumEnd;
	}

	public String getAddNum() {
		return addNum;
	}

	public String getAddNum2() {
		return addNum2;
	}

	public String getSaleNum() {
		return saleNum;
	}

	public String getStockNumCurr() {
		return stockNumCurr;
	}

	public String getDifferentnum() {
		return differentnum;
	}

	public String getSatdate() {
		return satdate;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setTermType(String pTermType) {
		termType = pTermType;
	}

	public void setReportType(String pReportType) {
		reportType = pReportType;
	}

	public void setStockNumEnd(String pStockNumEnd) {
		stockNumEnd = pStockNumEnd;
	}

	public void setAddNum(String pAddNum) {
		addNum = pAddNum;
	}

	public void setAddNum2(String pAddNum2) {
		addNum2 = pAddNum2;
	}

	public void setSaleNum(String pSaleNum) {
		saleNum = pSaleNum;
	}

	public void setStockNumCurr(String pStockNumCurr) {
		stockNumCurr = pStockNumCurr;
	}

	public void setDifferentnum(String pDifferentnum) {
		differentnum = pDifferentnum;
	}

	public void setSatdate(String pSatdate) {
		satdate = pSatdate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("TERM_TYPE",this.termType);
		hashMap.put("REPORT_TYPE",this.reportType);
		hashMap.put("STOCK_NUM_END",this.stockNumEnd);
		hashMap.put("ADD_NUM",this.addNum);
		hashMap.put("ADD_NUM2",this.addNum2);
		hashMap.put("SALE_NUM",this.saleNum);
		hashMap.put("STOCK_NUM_CURR",this.stockNumCurr);
		hashMap.put("DIFFERENTNUM",this.differentnum);
		hashMap.put("SATDATE",this.satdate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.lanId = (String) hashMap.get("LAN_ID");
			this.termType = (String) hashMap.get("TERM_TYPE");
			this.reportType = (String) hashMap.get("REPORT_TYPE");
			this.stockNumEnd = (String) hashMap.get("STOCK_NUM_END");
			this.addNum = (String) hashMap.get("ADD_NUM");
			this.addNum2 = (String) hashMap.get("ADD_NUM2");
			this.saleNum = (String) hashMap.get("SALE_NUM");
			this.stockNumCurr = (String) hashMap.get("STOCK_NUM_CURR");
			this.differentnum = (String) hashMap.get("DIFFERENTNUM");
			this.satdate = (String) hashMap.get("SATDATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_ENTITY_REPORT";
	}

}
