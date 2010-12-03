package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class DcDeviceVO extends ValueObject implements VO {

	private String scode = "";
	private String sname = "";
	private String devType = "";
	private String dflag = "";
	private String sortby = "";
	private String vflag = "";
	private String comments = "";

	public DcDeviceVO() {}

	public DcDeviceVO( String pscode, String psname, String pdevType, String pdflag, String psortby, String pvflag, String pcomments ) {
		scode = pscode;
		sname = psname;
		devType = pdevType;
		dflag = pdflag;
		sortby = psortby;
		vflag = pvflag;
		comments = pcomments;
	}

	public String getScode() {
		return scode;
	}

	public String getSname() {
		return sname;
	}

	public String getDevType() {
		return devType;
	}

	public String getDflag() {
		return dflag;
	}

	public String getSortby() {
		return sortby;
	}

	public String getVflag() {
		return vflag;
	}

	public String getComments() {
		return comments;
	}

	public void setScode(String pScode) {
		scode = pScode;
	}

	public void setSname(String pSname) {
		sname = pSname;
	}

	public void setDevType(String pDevType) {
		devType = pDevType;
	}

	public void setDflag(String pDflag) {
		dflag = pDflag;
	}

	public void setSortby(String pSortby) {
		sortby = pSortby;
	}

	public void setVflag(String pVflag) {
		vflag = pVflag;
	}

	public void setComments(String pComments) {
		comments = pComments;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("SCODE",this.scode);
		hashMap.put("SNAME",this.sname);
		hashMap.put("DEV_TYPE",this.devType);
		hashMap.put("DFLAG",this.dflag);
		hashMap.put("SORTBY",this.sortby);
		hashMap.put("VFLAG",this.vflag);
		hashMap.put("COMMENTS",this.comments);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.scode = (String) hashMap.get("SCODE");
			this.sname = (String) hashMap.get("SNAME");
			this.devType = (String) hashMap.get("DEV_TYPE");
			this.dflag = (String) hashMap.get("DFLAG");
			this.sortby = (String) hashMap.get("SORTBY");
			this.vflag = (String) hashMap.get("VFLAG");
			this.comments = (String) hashMap.get("COMMENTS");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "DC_DEVICE";
	}

}
