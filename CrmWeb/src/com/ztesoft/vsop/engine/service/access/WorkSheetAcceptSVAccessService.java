package com.ztesoft.vsop.engine.service.access;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.AproductInfo;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * ����ͨ��������ӿڽ������
 * @author cooltan
 *
 */
public class WorkSheetAcceptSVAccessService extends CommonAccessService
		implements IAccess {

	public WorkSheetAcceptSVAccessService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * ���Ի��������  ��ÿ��������д
	 * @param in
	 * @return
	 */
	public  Map concreteInOpertion(Map in) throws Exception{
		String requestXml=(String)in.get("accessInObject");
		CustomerOrder order=new CustomerOrder();
		this.loadFromXml(requestXml, order);
		in.put("busiObject", order);
		in.put("serviceCode", String.valueOf(33));//33:����ͨ��������
//		in.put("serviceCode", order.getCustOrderType());
		return in;
	}
	/**
	 * ���Ի����ز���  ��ÿ��������д
	 * @param in
	 * @return
	 */
	public  Map concreteOutOpertion(Map in) throws Exception{
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		StringBuffer bf = new StringBuffer();
		String resultCode=(String)in.get("resultCode");
		String restulMsg=(String)in.get("resultMsg");
		bf.append("<").append("WorkListFKToVSOPResp").append(">")
				.append("<StreamingNo>").append(order.getCustSoNumber()).append("</StreamingNo>")
				.append("<ResultCode>").append(resultCode).append("</ResultCode>")
				.append("<ResultDesc>").append(restulMsg).append("</ResultDesc>");
		bf.append("</").append("WorkListFKToVSOPResp").append(">");
		in.put("accessOutObject",bf.toString());
		return in;
	}
	private void loadFromXml(String inXML,CustomerOrder order) throws  Exception {
		order.setCustSoNumber(XMLUtils.getSingleTagValue(inXML,"StreamingNo"));
		order.setReceiveDate(XMLUtils.getSingleTagValue(inXML,"TimeStamp"));
		order.setOrderSystem(XMLUtils.getSingleTagValue(inXML,"SystemId"));
		order.setOrderChn(XMLUtils.getSingleTagValue(inXML,"SystemId"));
		order.setOtherSysOrderId(XMLUtils.getSingleTagValue(inXML,"OrderId"));
		order.setCustOrderType(XMLUtils.getSingleTagValue(inXML,"ActionType"));
		order.setProdInstId(XMLUtils.getSingleTagValue(inXML,"PorductInstID"));
		order.setLanId(XMLUtils.getSingleTagValue(inXML,"ReginID"));
		String prodSpecCode= XMLUtils.getSingleTagValue(inXML,"ProdSpecCode");
		order.setAccNbr(XMLUtils.getSingleTagValue(inXML,"ProductNo"));
		order.setOldAccNbr(XMLUtils.getSingleTagValue(inXML,"OldProductNo"));
		//�������ػ�����
		//panshaohua 2010-8-9 ���ݽӿڵ�����Ʒ���룬�õ���Ʒid
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			if(!("C7".equals(prodSpecCode) || "I8".equals(prodSpecCode)) ){  //��������ֻ���������+������Ϊ��Ʒ����
				order.setAccNbr(order.getLanId()+order.getAccNbr());
				order.setOldAccNbr(order.getLanId()+order.getOldAccNbr());
			}
			prodSpecCode=DcSystemParamManager.getInstance().getProductIdByNbr(prodSpecCode);
			order.setProdId(prodSpecCode);
		}else {
			order.setProdId(prodSpecCode);
		}
		order.setStatus(OrderConstant.orderStateOfCreated);
	}

}
