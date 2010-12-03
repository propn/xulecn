package com.ztesoft.vsop.bsn.webservice;

import java.net.URL;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.bsn.webservice.cq.CodeRequDto;
import com.ztesoft.vsop.bsn.webservice.cq.QueryCashFeeRespDto;
import com.ztesoft.vsop.bsn.webservice.cq.WebServiceFaceSoapBindingStub;



public class bsnSoapClient {
	public static bsnSoapClient instance=new bsnSoapClient();
	private static Logger logger = Logger.getLogger(bsnSoapClient.class);
	public static bsnSoapClient getInstance(){
		return instance;
	}
	public String getBalance(String systemId, 
						     String productId, 
						     String productNo, 
						     String contectType){
		String balance = "-1";
		try{
			String urlStr = com.ztesoft.vsop.web.DcSystemParamManager.getParameter("HB_FEE_URL");
			java.net.URL url = new java.net.URL(urlStr);
			WSSServiceLocator s = new WSSServiceLocator();
			WSS axisSoap = s.getWSS(url);
			String requestXml = feeQuery(productNo, productId);
			System.out.println("requestXml:"+requestXml);
			String respXML = axisSoap.call(requestXml);
			System.out.println("respXML:"+respXML);
			String RespCode = StringUtil.getInstance().getTagValue("RespCode", respXML);
			if(RespCode == null || !"0".equals(RespCode))return balance;
			balance = StringUtil.getInstance().getTagValue("Balance", respXML);
		}catch(Exception e){
			e.printStackTrace();
			return "-1";
		}
		
		return balance;
	}
	/**
	 * 组装余额查询请求报文
	 * @return
	 */
	private  String feeQuery(String productNo, String productId) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<?xml version='1.0' encoding='UTF-8'?>")
				.append("<Root>")
				.append("<Domain>WSS</Domain>")
				.append("<Passwd>123456</Passwd>")
				.append("<SrvCode>BalanceOper</SrvCode>")
				.append("<SrvModule>PrepayMoneyGXSrv</SrvModule>")
				.append("<SrvFunction>prepayMoney</SrvFunction>")
				.append(
						"<Content><![CDATA[<?xml version='1.0' encoding='UTF-8'?><PrepayMoneyRequest><Nbr>"+productNo+"</Nbr><QryType>1</QryType></PrepayMoneyRequest>]]></Content>")
				.append("</Root>");
		return sb.toString();
	}
	/**
	 * 重庆计费余额查询接口
	 * 当返回节点cashFee（未缴费金额，单位分）值大于0，侧说明该用户欠费，其他属于正常。
	 * @param productNo
	 * @param productId
	 * @throws Exception
	 */
	public  String QueryCashFee(String productNo, String productId) throws Exception{
		URL url = new URL("http://136.5.9.67:9080/BSNWeb/services/WebServiceFace");
		WebServiceFaceSoapBindingStub stub = new WebServiceFaceSoapBindingStub(url,null );
		
		CodeRequDto dto= new CodeRequDto();
		dto.setQueryCode(productNo);
		dto.setUserType(productId);
		dto.setQueryType("1");
		
		QueryCashFeeRespDto resp = stub.queryCashFee(dto);
		System.out.println(resp.getResult());
		System.out.println(resp.getDesc());
		String resXml = resp.getCashFee();
		System.out.println(resXml);
		return resXml;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String value = bsnSoapClient.getInstance().getBalance("", "", "18977141299", "");
		System.out.println("value:"+value);
	}

}
