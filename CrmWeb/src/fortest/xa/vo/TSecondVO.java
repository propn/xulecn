package fortest.xa.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class TSecondVO extends ValueObject implements VO {

	private String sid = "";
	private String sName = "";
	private String sDesc = "";
	private String fId = "";

	public TSecondVO() {}

	public TSecondVO( String pid, String psName, String psDesc, String pfId ) {
		sid = pid;
		sName = psName;
		sDesc = psDesc;
		fId = pfId;
	}

	public String getSid() {
		return sid;
	}

	public String getSName() {
		return sName;
	}

	public String getSDesc() {
		return sDesc;
	}

	public String getFId() {
		return fId;
	}

	public void setSid(String pId) {
		sid = pId;
	}

	public void setSName(String pSName) {
		sName = pSName;
	}

	public void setSDesc(String pSDesc) {
		sDesc = pSDesc;
	}

	public void setFId(String pFId) {
		fId = pFId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ID",this.sid);
		hashMap.put("S_NAME",this.sName);
		hashMap.put("S_DESC",this.sDesc);
		hashMap.put("F_ID",this.fId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.sid = (String) hashMap.get("ID");
			this.sName = (String) hashMap.get("S_NAME");
			this.sDesc = (String) hashMap.get("S_DESC");
			this.fId = (String) hashMap.get("F_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "T_SECOND";
	}

}
