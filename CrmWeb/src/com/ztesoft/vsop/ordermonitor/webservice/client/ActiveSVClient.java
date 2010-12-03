package com.ztesoft.vsop.ordermonitor.webservice.client;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.ordermonitor.webservice.ActiveSVStub;
import com.ztesoft.vsop.ordermonitor.webservice.vo.ActiveReq;
import com.ztesoft.vsop.ordermonitor.webservice.vo.ActiveRequest;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 激活异常客户端统一接口类
 * 
 * @author yuanyang
 * 
 */

public class ActiveSVClient {
	private String activeSVEndPoint = "http://134.192.204.40:9830/vsopactive/services/activeSV";
	private static final Logger logger = Logger.getLogger(ActiveSVClient.class);
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//省份代码
	public static ActiveSVClient getInstance() {
		return new ActiveSVClient();
	}
	// 从缓存中取服务地址
	public ActiveSVClient() {
		String sys_end_point = null;
		sys_end_point = DcSystemParamManager.getParameter("VSOP_ACTIVESV_URL")
				.trim();
		if (null != sys_end_point && !"".equals(sys_end_point)) {
			this.activeSVEndPoint = sys_end_point;
		}

	}

	/**
	 * 客户端统一入口方法
	 * 
	 * @param activeType
	 * @param mainMsg
	 * @param helpMsg
	 * @return
	 * @throws Exception 
	 */
	public String active(String activeType, String mainMsg, String helpMsg)
			throws Exception {
		//**********
		//广西本地化，利用SOCKET与服务端通信，处理异常工单
		//**********
//		if(CrmConstants.GX_PROV_CODE.equals(this.provinceCode)){
//			ActiveSVSkeleton sl=new ActiveSVSkeleton();
//			ActiveRequest actParam= new ActiveRequest();
//			actParam.setActivetype(activeType);
//			actParam.setMainmsg(mainMsg);
//			actParam.setHelpmsg(helpMsg);
//			ActiveReq actReq= new ActiveReq();
//			actReq.setActiveReq(actParam);
//			ActiveResp actResp=sl.active(actReq);
//			return actResp.getActiveResp().getResponse();
//		}else{
			ActiveSVStub activeSVStub = new ActiveSVStub(this.activeSVEndPoint);
			String ret = null;
			StringBuffer tempSB=null;
			try {
				ActiveRequest param = new ActiveRequest();
				param.setActivetype(activeType);
				param.setMainmsg(mainMsg);
				param.setHelpmsg(helpMsg);
				ActiveReq activeReq12 = new ActiveReq();
				activeReq12.setActiveReq(param);
				logger.info("activeType: " +activeType + " mainMsg: " + mainMsg + "helpMsg: " + helpMsg);
				ret = activeSVStub.active(activeReq12).getActiveResp()
						.getResponse();
			} catch (Exception e) {
				tempSB=new StringBuffer();
				tempSB.append("<ResultCode>-1</ResultCode>");
				tempSB.append("<ResultMsg>");
				tempSB.append(e.toString());
				tempSB.append("</ResultMsg>");
				ret=tempSB.toString();
				throw e;
			}finally{
			}
			return ret;
//		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ActiveSVClient client = ActiveSVClient.getInstance();
		client.activeSVEndPoint="http://134.192.204.40:9830/vsopactive/services/activeSV";
		String s = "<root>" + "<msg_head>" + "<from>IOM</from>"
		+ "<to>VSOP</to>" + "<msg_type>message_request</msg_type>"
		+ "<serial>10000</serial>"
		+ "<out_order_type>VSOP001</out_order_type>"
		+ "</msg_head>" + "<interface_msg>" + "<public>"
		+ "<order_id>222222</order_id>"
		+ "<order_act_type>ADD</order_act_type>" + "</public>"
		+ "<user_info>" + "<pproductoffer_info>"
		+ "<ppproduct_act_type>ADD</ppproduct_act_type>"
		+ "</pproductoffer_info>" +
		"<prod_info>"
		+ "<prod_no>07314321892</prod_no>"
		+ "<prod_type>b001</prod_type>"
		+ "<area_code>1001</area_code>" + "</prod_info>"
		+ "<sub_products>"
		+ "<control>NE1</control>" + "<NE1>" + "<sub_product>"
		+ "<sub_product_code>A001</sub_product_code>"
		+ "<oldsub_product_code>A001</oldsub_product_code>"
		+ "<act_type>ADD</act_type>" + "<idtype>1</idtype>"
		+ "<id>1</id>"
		+ "<prodCharacters>A1=2,A2=3</prodCharacters>"
		+ "</sub_product>" + "<sub_product>"
		+ "<sub_product_code>A002</sub_product_code>"
		+ "<oldsub_product_code>A002</oldsub_product_code>"
		+ "<act_type>ADD</act_type>" + "<idtype>1</idtype>"
		+ "<id>1</id>"
		+ "<prodCharacters>A1=2,A2=3</prodCharacters>"
		+ "</sub_product>" + "</NE1>" + "</sub_products>"
		+ "</user_info>" + "</interface_msg>" + "</root>";
		try {
			String resp = client.active("20", s, "3");
			System.out.println("resp:"+resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
//		new SoapClient().subscriptionSync("0", "200003590", "13500000000000000212311", "270", "133177115531", "0");
		
	}
}