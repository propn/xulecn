package com.ztesoft.vsop.order.vo.request;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.SystemCode;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class SubInstHisQueryRequest {
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
	private String beginTime = "";
	private String endTime = "";
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
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
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
	public String getProdInstId() {
		return prodInstId;
	}
	public void setProdInstId(String prodInstId) {
		this.prodInstId = prodInstId;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void loadFromXml(String requestXml) throws DocumentException, SQLException {
		setStreamingNo(StringUtil.getInstance().getTagValue("StreamingNo", requestXml));
		setTimestamp(StringUtil.getInstance().getTagValue("TimeStamp", requestXml));
		setSystemId(StringUtil.getInstance().getTagValue("SystemId", requestXml));
		setProdSpecCode(StringUtil.getInstance().getTagValue("ProdSpecCode", requestXml));
		String productNo=StringUtil.getInstance().getTagValue("ProductNo", requestXml);
		setProductNo(productNo);
		String productInstID=null;
		if(null!=productNo && !"".equals(productNo)){
			ProdInstHelpDao prodDao=new ProdInstHelpDao();
			productInstID=prodDao.getProdInstIdByAccBnr(productNo);
		}
		setProdInstId(productInstID);
		setBeginTime(StringUtil.getInstance().getTagValue("BeginTime", requestXml));
		setEndTime(StringUtil.getInstance().getTagValue("EndTime", requestXml));
		validate();
		//yi.chengfeng 2010-7-10 ֧��û�в�Ʒ������ε��޸� start
		String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
		if(CrmConstants.JX_PROV_CODE.equals(provinceCode)){
			//����ʡProductNo=����+��Ʒ���룬���ݸ���Ϣ��ѯProdSpecCode�Ȳ���ֵ
			resetProdSpecCode();
		}
		//yi.chengfeng 2010-7-10 ֧��û�в�Ʒ������ε��޸� end
	}
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
					productNoTmp = getProductNo();
				}
				String[] prodInfos = new OrderDao().getProdInstInfos(lanCodeTmp,productNoTmp);
				setProdInstId(prodInfos[0]);
				setProdSpecCode(prodInfos[4]);
				setProductNo(productNoTmp);
			}
		}else{
			ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
			ProdInstVO prodInstvo=aProdInstHelpDao.qryProdInstByAccNbrAndProductNbr(getProductNo(), getProdSpecCode());
			
			setProdInstId(prodInstvo.getProdInstId());
			setProdSpecCode(prodInstvo.getProdId());
			setProductNo(prodInstvo.getAccNbr());
//			set(prodInstvo.getLanId());
		}
	}
	
	private void validate() throws DocumentException {
		if("".equals(getProdSpecCode()) || "".equals(getProductNo()) || "".equals(getSystemId())){
			throw new DocumentException("��������,��Ʒ����,��Ʒ����,ϵͳ���벻��Ϊ��!");
		}
		if("".equals(getBeginTime()) || "".equals(getEndTime()) ){
			throw new DocumentException("��ѯ��ʼʱ����߲�ѯ����ʱ�䲻��Ϊ��!");
		}
		//У���ѯ��ʼʱ��ͽ���ʱ��
		if(getBeginTime().length() !=8 || !StringUtils.isNumeric(getBeginTime())){
			throw new DocumentException("��ѯ��ʼʱ�������8λ���ֵ�������:yyyymmdd!");
		}
		if(getEndTime().length() !=8 || !StringUtils.isNumeric(getEndTime())){
			throw new DocumentException("��ѯ����ʱ�������8λ���ֵ�������:yyyymmdd!");
		}
	}
	
	
}
