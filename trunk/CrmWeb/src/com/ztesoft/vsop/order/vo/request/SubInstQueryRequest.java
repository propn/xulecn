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
	 * 请求系统ID
	 */
	private String systemId = "";
	/**
	 * 产品编码
	 */
	private String prodSpecCode = "";
	/**
	 * 用户号码
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
		//yi.chengfeng 2010-7-10 支持没有产品类型入参的修改 start
		String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
		if(CrmConstants.JX_PROV_CODE.equals(provinceCode)){
			//江西省ProductNo=区号+产品号码，根据该信息查询ProdSpecCode等并赋值
			resetProdSpecCode();
		}
		//yi.chengfeng 2010-7-10 支持没有产品类型入参的修改 end
	}

	private void validate() throws DocumentException {
		if("".equals(getProdSpecCode()) || "".equals(getProductNo()) || "".equals(getSystemId())){
			throw new DocumentException("参数错误,产品编码,产品号码,系统编码不能为空!");
		}
	}

	/**
	 * ProductNo=区号+产品号码，根据该信息查询ProdSpecCode等并赋值
	 * 江西：网厅、10000坐席，短厅、10000语言系统入参是产品大类，需要转换;后续是否不需要判断系统编码，所有编码统一这样处理？
	 * yi.chengfeng add 2010-7-10
	 * @throws SQLException 
	 * @throws SQLException 
	 */
	private void resetProdSpecCode() throws SQLException {
		String origProdSpecCode = getProdSpecCode(); //原来的产品类型，产品大类
		String systemCode = getSystemId();  //外系统编码
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
