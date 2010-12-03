package com.ztesoft.vsop.order.vo.request;

import java.sql.SQLException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.SystemCode;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class SubInstQueryRequest {
	private String streamingNo = "";
	private String timestamp = "";
	/**
	 * ����ϵͳID
	 */
	private String systemId = "";
	/**
	 * ��Ʒ����
	 */
	private String prodSpecCode = "";
	/**
	 * �û�����
	 */
	private String productNo = "";
	private String prodInstId = "";
	
	public void loadFromXml(String requestXml) throws DocumentException, SQLException {
		setStreamingNo(StringUtil.getInstance().getTagValue("StreamingNo", requestXml));
		setTimestamp(StringUtil.getInstance().getTagValue("TimeStamp", requestXml));
		setSystemId(StringUtil.getInstance().getTagValue("SystemId", requestXml));
		setProdSpecCode(StringUtil.getInstance().getTagValue("ProdSpecCode", requestXml));
		setProductNo(StringUtil.getInstance().getTagValue("ProductNo", requestXml));
		validate();
		//yi.chengfeng 2010-7-10 ֧��û�в�Ʒ������ε��޸� start
		String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
		if(CrmConstants.JX_PROV_CODE.equals(provinceCode)){
			//����ʡProductNo=����+��Ʒ���룬���ݸ���Ϣ��ѯProdSpecCode�Ȳ���ֵ
			resetProdSpecCode();
		}
		//yi.chengfeng 2010-7-10 ֧��û�в�Ʒ������ε��޸� end
	}

	private void validate() throws DocumentException {
		if("".equals(getProdSpecCode()) || "".equals(getProductNo()) || "".equals(getSystemId())){
			throw new DocumentException("��������,��Ʒ����,��Ʒ����,ϵͳ���벻��Ϊ��!");
		}
	}

	/**
	 * ProductNo=����+��Ʒ���룬���ݸ���Ϣ��ѯProdSpecCode�Ȳ���ֵ
	 * ������������10000��ϯ��������10000����ϵͳ����ǲ�Ʒ���࣬��Ҫת��;�����Ƿ���Ҫ�ж�ϵͳ���룬���б���ͳһ��������
	 * yi.chengfeng add 2010-7-10
	 * @throws SQLException 
	 * @throws SQLException 
	 */
	private void resetProdSpecCode() throws SQLException {
		String origProdSpecCode = getProdSpecCode(); //ԭ���Ĳ�Ʒ���ͣ���Ʒ����
		String systemCode = getSystemId();  //��ϵͳ����
		String lanCodeTmp = null;
		String productNoTmp = null;
		if(SystemCode.CT10000.equals(systemCode) || SystemCode.SEAT_10000.equals(systemCode) ||
				SystemCode.SMS.equals(systemCode) || SystemCode.VOICE_10000.equals(systemCode) || SystemCode.WAP.equals(systemCode)){
			
			if(OrderConstant.PROD_SPEC_CODE_TELEPHONE.equals(origProdSpecCode) || 
					OrderConstant.PROD_SPEC_CODE_PHS.equals(origProdSpecCode) ||
					OrderConstant.PROD_SPEC_CODE_WAN.equals(origProdSpecCode)||
					OrderConstant.PROD_SPEC_CODE_MOBILE.equals(origProdSpecCode)){
				
				if(OrderConstant.PROD_SPEC_CODE_MOBILE.equals(origProdSpecCode)){
					productNoTmp = getProductNo();
				}else{
					lanCodeTmp = getProductNo().substring(0,4);
					productNoTmp = getProductNo().substring(4);
				}
				String[] prodInfos = new OrderDao().getProdInstInfos(lanCodeTmp,productNoTmp);
				setProdInstId(prodInfos[0]);
				setProdSpecCode(prodInfos[4]);
				setProductNo(productNoTmp);
			}
		}
	}

	public String getStreamingNo() {
		return streamingNo;
	}

	public void setStreamingNo(String streamingNo) {
		this.streamingNo = streamingNo;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getProdSpecCode() {
		return prodSpecCode;
	}

	public void setProdSpecCode(String prodSpecCode) {
		this.prodSpecCode = prodSpecCode;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getProdInstId() {
		return prodInstId;
	}

	public void setProdInstId(String prodInstId) {
		this.prodInstId = prodInstId;
	}
	

}
