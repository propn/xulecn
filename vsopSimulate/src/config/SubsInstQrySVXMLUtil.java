package config;

/**
 * <pre>
 * Title:订购关系查询
 * Description: 订购关系查询 
 * </pre>
 * @author caozj  caozj@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class SubsInstQrySVXMLUtil {
	private String RequestFlag = "SubInstQryFromVSOPReq";

	private String StreamingNo = "";

	private String TimeStamp = "";

	private String SystemId = "";

	private String ProdSpecCode = "";

	private String ProductNo = "";

	public String getRequestXML() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(SoapRequestXMLUtil.getStart(RequestFlag));
		buffer.append("<Request><SessionBody>\n");
		buffer.append("<" + this.RequestFlag + ">\n");
		buffer.append("<StreamingNo>" + StreamingNo + "</StreamingNo>\n");
		buffer.append("<TimeStamp>" + TimeStamp + "</TimeStamp>\n");
		buffer.append("<SystemId>" + SystemId + "</SystemId>\n");
		buffer.append("<ProdSpecCode>" + ProdSpecCode + "</ProdSpecCode>\n");
		buffer.append("<ProductNo>" + ProductNo + "</ProductNo>\n");

		buffer.append("</" + this.RequestFlag + ">\n");
		buffer.append("</SessionBody></Request>\n");
		buffer.append(SoapRequestXMLUtil.getEnd(RequestFlag));

		return buffer.toString();
	}

	public String getProdSpecCode() {
		return ProdSpecCode;
	}

	public void setProdSpecCode(String prodSpecCode) {
		ProdSpecCode = prodSpecCode;
	}

	public String getProductNo() {
		return ProductNo;
	}

	public void setProductNo(String productNo) {
		ProductNo = productNo;
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
}
