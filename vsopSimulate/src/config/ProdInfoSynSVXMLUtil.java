package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title:产品同步报文生成
 * Description: 产品同步报文生成
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ProdInfoSynSVXMLUtil {
	private static final String RequestFlag = "ProductSyncToVSOPReq";

	private String StreamingNo = "";

	private String TimeStamp = "";

	private String SystemId = "";

	private String SPID = "";

	private String OPFlag = "";

	private String ProductType = "";

	private String ProductID = "";

	private String PnameCN = "";

	private String PnameEN = "";

	private String PdescriptionCN = "";

	private String PdescriptionEn = "";

	private List ServiceCapabilityID = new ArrayList();

	private String Status = "";

	private String Scope = "";

	private String ProductHost = "";

	private List PlatForm = new ArrayList();

	private Map ProdRelation = new HashMap();

	public String getOPFlag() {
		return OPFlag;
	}

	public void setOPFlag(String flag) {
		OPFlag = flag;
	}

	public String getPdescriptionCN() {
		return PdescriptionCN;
	}

	public void setPdescriptionCN(String pdescriptionCN) {
		PdescriptionCN = pdescriptionCN;
	}

	public String getPdescriptionEn() {
		return PdescriptionEn;
	}

	public void setPdescriptionEn(String pdescriptionEn) {
		PdescriptionEn = pdescriptionEn;
	}

	public List getPlatForm() {
		return PlatForm;
	}

	public void setPlatForm(List platForm) {
		PlatForm = platForm;
	}

	public String getPnameCN() {
		return PnameCN;
	}

	public void setPnameCN(String pnameCN) {
		PnameCN = pnameCN;
	}

	public String getPnameEN() {
		return PnameEN;
	}

	public void setPnameEN(String pnameEN) {
		PnameEN = pnameEN;
	}

	public Map getProdRelation() {
		return ProdRelation;
	}

	public void setProdRelation(Map prodRelation) {
		ProdRelation = prodRelation;
	}

	public String getProductHost() {
		return ProductHost;
	}

	public void setProductHost(String productHost) {
		ProductHost = productHost;
	}

	public String getProductID() {
		return ProductID;
	}

	public void setProductID(String productID) {
		ProductID = productID;
	}

	public String getProductType() {
		return ProductType;
	}

	public void setProductType(String productType) {
		ProductType = productType;
	}

	public String getScope() {
		return Scope;
	}

	public void setScope(String scope) {
		Scope = scope;
	}

	public List getServiceCapabilityID() {
		return ServiceCapabilityID;
	}

	public void setServiceCapabilityID(List serviceCapabilityID) {
		ServiceCapabilityID = serviceCapabilityID;
	}

	public String getSPID() {
		return SPID;
	}

	public void setSPID(String spid) {
		SPID = spid;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getStreamingNo() {
		return StreamingNo;
	}

	public void setStreamingNo(String streamingNo) {
		StreamingNo = streamingNo;
	}

	public String getSystemId() {
		return SystemId;
	}

	public void setSystemId(String systemId) {
		SystemId = systemId;
	}

	public String getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}

	/**
	 * 构造产品同步报文
	 * @return
	 */

	public String getRequestXML() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(SoapRequestXMLUtil.getStart(RequestFlag));
		buffer.append("<Request><SessionBody>\n");
		buffer.append("<" + ProdInfoSynSVXMLUtil.RequestFlag + ">\n");

		buffer.append("<StreamingNo>" + StreamingNo + "</StreamingNo>\n");
		buffer.append("<TimeStamp>" + TimeStamp + "</TimeStamp>\n");
		buffer.append("<SystemId>" + SystemId + "</SystemId>\n");
		buffer.append("<SPID>" + SPID + "</SPID>\n");
		buffer.append("<OPFlag>" + OPFlag + "</OPFlag>\n");
		buffer.append("<ProductType>" + ProductType + "</ProductType>\n");
		buffer.append("<ProductID>" + ProductID + "</ProductID>\n");
		buffer.append("<PnameCN>" + PnameCN + "</PnameCN>\n");
		buffer.append("<PnameEN>" + PnameEN + "</PnameEN>\n");
		buffer.append("<PdescriptionCN>" + PdescriptionCN + "</PdescriptionCN>\n");
		buffer.append("<PdescriptionEn>" + PdescriptionEn + "</PdescriptionEn>\n");
		for (int i = 0; i < ServiceCapabilityID.size(); i++) {
			buffer.append("<ServiceCapabilityID>" + ServiceCapabilityID.get(i) + "</ServiceCapabilityID>\n");
		}
		buffer.append("<Status>" + Status + "</Status>\n");
		buffer.append("<Scope>" + Scope + "</Scope>\n");
		buffer.append("<ProductHost>" + ProductHost + "</ProductHost>\n");

		for (int i = 0; i < PlatForm.size(); i++) {
			buffer.append("<PlatForm>" + PlatForm.get(i) + "</PlatForm>\n");
		}

		buffer.append("</" + ProdInfoSynSVXMLUtil.RequestFlag + ">\n");
		buffer.append("</SessionBody></Request>\n");
		buffer.append(SoapRequestXMLUtil.getEnd(RequestFlag));
		return buffer.toString();
	}
}
