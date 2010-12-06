package config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title:SP/CPͬ����������
 * Description: SP/CP��Ʒͬ����������
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class SCPInfoSynSVXMLUtil {
	private String RequestFlag = "CSPInfoSyncToVSOPReq";

	private String StreamingNo = "";

	private String TimeStamp = "";

	private String SystemId = "";

	private String OPFlag = "";

	private Map SPInfo = new HashMap();

	private static Map CSPTypeMap = null;//SP/CP����

	private static Map RoamPropertyMap = null;//�Ƿ��������

	private static Map SettlementCycleMap = null;

	private static Map SettlementPayTypeMap = null;

	private static Map OPFlagMap = null;

	private static Map StatusMap = null;

	private static void init() {
		if (CSPTypeMap == null) {
			CSPTypeMap = new HashMap();
			CSPTypeMap.put("CP+SP", "0");
			CSPTypeMap.put("CP", "1");
			CSPTypeMap.put("SP", "2");
		}
		if (RoamPropertyMap == null) {
			RoamPropertyMap = new HashMap();
			RoamPropertyMap.put("������", "0");
			RoamPropertyMap.put("����", "1");

		}
		if (SettlementCycleMap == null) {
			SettlementCycleMap = new HashMap();
			SettlementCycleMap.put("����", "0");
			SettlementCycleMap.put("������", "1");
			SettlementCycleMap.put("����", "2");

		}
		if (SettlementPayTypeMap == null) {
			SettlementPayTypeMap = new HashMap();
			SettlementPayTypeMap.put("һ���Ը���", "0");
			SettlementPayTypeMap.put("��������", "1");
		}
		if (OPFlagMap == null) {
			OPFlagMap = new HashMap();
			OPFlagMap.put("����", "1");
			OPFlagMap.put("�޸�", "2");
			OPFlagMap.put("ɾ��", "3");
		}
		if (StatusMap == null) {
			StatusMap = new HashMap();
			StatusMap.put("����", "0");
			StatusMap.put("����", "1");
			StatusMap.put("Ԥע��", "2");
			StatusMap.put("ע��", "3");

		}
	}

	/**
	 *  ����SP/CPͬ������
	 * @return
	 */
	public String getRequestXML() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(SoapRequestXMLUtil.getStart(RequestFlag));
		buffer.append("<Request><SessionBody>\n");
		buffer.append("<" + this.RequestFlag + ">\n");
		buffer.append("<StreamingNo>" + StreamingNo + "</StreamingNo>\n");
		buffer.append("<TimeStamp>" + TimeStamp + "</TimeStamp>\n");
		buffer.append("<SystemId>" + SystemId + "</SystemId>\n");
		buffer.append("<OPFlag>" + OPFlag + "</OPFlag>\n");
		buffer.append("<SPInfo>\n");
		buffer.append("<SPID>" + SPInfo.get("SPID") + "</SPID>\n");
		buffer.append("<Type>" + SPInfo.get("Type") + "</Type>\n");
		buffer.append("<NameCN>" + SPInfo.get("NameCN") + "</NameCN>\n");
		buffer.append("<NameEN>" + SPInfo.get("NameEN") + "</NameEN>\n");
		buffer.append("<DescriptionCN>" + SPInfo.get("DescriptionCN") + "</DescriptionCN>\n");
		buffer.append("<DescriptionEN>" + SPInfo.get("DescriptionEN") + "</DescriptionEN>\n");
		buffer.append("<CustomerCare>" + SPInfo.get("CustomerCare") + "</CustomerCare>\n");
		buffer.append("<WebsiteURL>" + SPInfo.get("WebsiteURL") + "</WebsiteURL>\n");
		buffer.append("<ProvinceID>" + SPInfo.get("ProvinceID") + "</ProvinceID>\n");
		buffer.append("<RoamProperty>" + SPInfo.get("RoamProperty") + "</RoamProperty>\n");
		buffer.append("<CompanyAddress>" + SPInfo.get("CompanyAddress") + "</CompanyAddress>\n");
		buffer.append("<LegalRepresentative>" + SPInfo.get("LegalRepresentative") + "</LegalRepresentative>\n");
		buffer.append("<Principal>" + SPInfo.get("Principal") + "</Principal>\n");
		buffer.append("<PrincipalTel>" + SPInfo.get("PrincipalTel") + "</PrincipalTel>\n");
		buffer.append("<PrincipalEmail>" + SPInfo.get("PrincipalEmail") + "</PrincipalEmail>\n");
		buffer.append("<ServiceManager>" + SPInfo.get("ServiceManager") + "</ServiceManager>\n");
		buffer.append("<ServiceManagerTel>" + SPInfo.get("ServiceManagerTel") + "</ServiceManagerTel>\n");
		buffer.append("<ServiceManagerEmail>" + SPInfo.get("ServiceManagerEmail") + "</ServiceManagerEmail>\n");
		buffer.append("<ServiceManagerAddr>" + SPInfo.get("ServiceManagerAddr") + "</ServiceManagerAddr>\n");
		buffer.append("<ServiceManagerPC>" + SPInfo.get("ServiceManagerPC") + "</ServiceManagerPC>\n");
		buffer.append("<ServiceManagerFax>" + SPInfo.get("ServiceManagerFax") + "</ServiceManagerFax>\n");
		buffer.append("<License>" + SPInfo.get("License") + "</License>\n");
		buffer.append("<ContractExpireDate>" + SPInfo.get("ContractExpireDate") + "</ContractExpireDate>\n");
		buffer.append("<AccessNO>" + SPInfo.get("AccessNO") + "</AccessNO>\n");
		buffer.append("<SettlementCycle>" + SPInfo.get("SettlementCycle") + "</SettlementCycle>\n");
		buffer.append("<SettlementPayType>" + SPInfo.get("SettlementPayType") + "</SettlementPayType>\n");
		buffer.append("<SettlementPercent>" + SPInfo.get("SettlementPercent") + "</SettlementPercent>\n");
		buffer.append("<CSWebsite>" + SPInfo.get("CSWebsite") + "</CSWebsite>\n");
		buffer.append("<Status>" + SPInfo.get("Status") + "</Status>\n");
		List list = (List) SPInfo.get("PlatForm");
		for (int i = 0; i < list.size(); i++) {
			buffer.append("<PlatForm>" + list.get(i) + "</PlatForm>\n");
		}

		buffer.append("</SPInfo>\n");
		buffer.append("</" + this.RequestFlag + ">\n");
		buffer.append("</SessionBody></Request>\n");
		buffer.append(SoapRequestXMLUtil.getEnd(RequestFlag));

		return buffer.toString();
	}

	public String getOPFlag() {
		return OPFlag;
	}

	public void setOPFlag(String flag) {
		OPFlag = flag;
	}

	public Map getSPInfo() {
		return SPInfo;
	}

	public void setSPInfo(Map info) {
		SPInfo = info;
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

	public static String getCSPType(String key) {
		if (CSPTypeMap == null) {
			init();
		}
		return (String) CSPTypeMap.get(key);
	}

	public static String getRoamProperty(String key) {
		if (RoamPropertyMap == null) {
			init();
		}
		return (String) RoamPropertyMap.get(key);
	}

	public static String getSettlementCycle(String key) {
		if (SettlementCycleMap == null) {
			init();
		}
		return (String) SettlementCycleMap.get(key);
	}

	public static String getSettlementPayType(String key) {
		if (SettlementPayTypeMap == null) {
			init();
		}
		return (String) SettlementPayTypeMap.get(key);
	}

	public static String getOPFlag(String key) {
		if (OPFlagMap == null) {
			init();
		}
		return (String) OPFlagMap.get(key);
	}

	public static String getStatus(String key) {
		if (StatusMap == null) {
			init();
		}
		return (String) StatusMap.get(key);
	}
}
