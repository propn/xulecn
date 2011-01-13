package fortest.xa.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class TMasterVO extends ValueObject implements VO {

	private String id = "";
	private String mName = "";
	private String mDesc = "";

	
	private String sid = "";
	private String sName = "";
	private String sDesc = "";
	private String fId = "";
	
	
	
	public String getFId() {
		return fId;
	}

	public void setFId(String id) {
		fId = id;
	}

	public String getSDesc() {
		return sDesc;
	}

	public void setSDesc(String desc) {
		sDesc = desc;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSName() {
		return sName;
	}

	public void setSName(String name) {
		sName = name;
	}

	public TMasterVO() {}

	public TMasterVO( String pid, String pmName, String pmDesc ) {
		id = pid;
		mName = pmName;
		mDesc = pmDesc;
	}

	public String getId() {
		return id;
	}

	public String getMName() {
		return mName;
	}

	public String getMDesc() {
		return mDesc;
	}

	public void setId(String pId) {
		id = pId;
	}

	public void setMName(String pMName) {
		mName = pMName;
	}

	public void setMDesc(String pMDesc) {
		mDesc = pMDesc;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ID",this.id);
		hashMap.put("M_NAME",this.mName);
		hashMap.put("M_DESC",this.mDesc);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.id = (String) hashMap.get("ID");
			this.mName = (String) hashMap.get("M_NAME");
			this.mDesc = (String) hashMap.get("M_DESC");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "T_MASTER";
	}

}
