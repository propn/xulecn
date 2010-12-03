package com.ztesoft.vsop.engine.service.access;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.help.AssemCoreEntityServices;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.OrderRelationVO;
import com.ztesoft.vsop.order.SystemCode;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.vo.PartnerVO;
/**
 * ������ϵ��ѯ�ӿڽ������
 * @author cooltan
 *
 */
public class SubsInstQrySVAccessService extends CommonAccessService implements
		IAccess {

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
		this.loadFromXml(requestXml,order);
		in.put("busiObject", order);
		in.put("serviceCode", "32");
		return in;
	}
	
	/**
	 * ���Ի����ز���  ��ÿ��������д
	 * @param in
	 * @return
	 */
	public  Map concreteOutOpertion(Map in) throws Exception{
		List ls=(List)in.get("retBusiObject");
		String resultCode=(String)in.get("resultCode");
		String resultMsg=(String)in.get("resultMsg");
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		String responseXml=this.toXml(ls,resultCode,resultMsg,order);
		in.put("accessOutObject", responseXml);
		return in;
	}
	private void loadFromXml(String requestXml,CustomerOrder order) throws SQLException {
		order.setCustSoNumber(StringUtil.getInstance().getTagValue("StreamingNo", requestXml));
		order.setReceiveDate(StringUtil.getInstance().getTagValue("TimeStamp", requestXml));
		String systemId=StringUtil.getInstance().getTagValue("SystemId", requestXml);
		order.setOrderSystem(systemId);
		order.setOrderChn(systemId);
		order.setProdId(StringUtil.getInstance().getTagValue("ProdSpecCode", requestXml));
		//�������ػ�������ϵͳ��Ʒ���Ͳ�׼��������ѯ������ϵֻ�������ѯ��
		if(CrmConstants.GX_PROV_CODE.equals(this.provinceCode)){
			order.setProdId("");
		}
		order.setAccNbr(StringUtil.getInstance().getTagValue("ProductNo", requestXml));
		//�������ػ�����
		//yi.chengfeng 2010-7-10 ֧�ֲ�Ʒ���ʹ���Ʒ���� start
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			AssemCoreEntityServices aAssemCoreEntityServices=new AssemCoreEntityServices();
			aAssemCoreEntityServices.resetProdInstInfo(order);
		}
		//yi.chengfeng 2010-7-10 ֧�ֲ�Ʒ���ʹ���Ʒ���� end
	}
	
	private String toXml(List ls,String resultCode,String resultMsg,CustomerOrder order){
		
		//��������������Ҫ���⴦�� yi.chengfeng 2010-9-7  ��189����Ͱ�����������ǰ�� 
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode) && SystemCode.SMS.equalsIgnoreCase(order.getOrderSystem())){
			return toXmlForSMS(ls,resultCode,resultMsg,order);
		}
		
		StringBuffer bf = new StringBuffer("");
		bf.append("").append("<Response>").append("<SubInstQryFromVSOPResp>")
			.append("<StreamingNo>").append(order.getCustSoNumber()).append("</StreamingNo>")
			.append("<ResultCode>").append(resultCode).append("</ResultCode>")
			.append("<ResultDesc>").append(resultMsg).append("</ResultDesc>");
		if(ls != null){
			orderRelationListToXml(ls, bf);
		}
		bf.append("</SubInstQryFromVSOPResp>").append("</Response>");
		return bf.toString();
	}
	private void orderRelationListToXml(List ls, StringBuffer bf) {
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			OrderRelationVO or = (OrderRelationVO) iterator.next();
			if(!"1".equals(or.getActiveState())) continue;//���� ����״̬��Ϊ 1����ɹ� �ļ�¼
			String offerId=null;
			String offerType=null;
			String packageId=or.getPackageId();
			String pprodOfferid=or.getPprodOffId();
			String state = or.getState();
			String status = "0";
			if("00X".equals(state)){
				status = "5";
			}
			if(pprodOfferid!=null&&!"".equals(pprodOfferid)){
				offerType="2";
				offerId=pprodOfferid;
//					offerNbr = or.getPOfferNbr();
			}else if(packageId!=null&&!"".equals(packageId)){
				offerType="1";
				offerId=packageId;
//					offerNbr = or.getOfferNbr();
			}else{
				offerType="0";
				offerId=or.getProdOffId();
//					offerNbr = or.getProdOfferNbr();
//					offerNbr = or.getProductNbr();
			}

				bf.append("<ResultInfo>")
				.append("<VproductID>").append(aDcSystemParamManager.getProductNbrById(or.getProdId())).append("</VproductID>")//��ֵ��Ʒ����ҲӦ������ϵͳ����
				.append("<VproductName>").append(aDcSystemParamManager.getProductnameById(or.getProdId())).append("</VproductName>")
				.append("<SPID>").append(getPartnerIdByProductId(or)).append("</SPID>")
				.append("<SPName>").append(getPartnerNameByProductId(or)).append("</SPName>")
				.append("<ProductOfferType>").append(offerType).append("</ProductOfferType>");
				bf.append("<ProductOfferID>").append(aDcSystemParamManager.getProdOfferNbrById(offerId)).append("</ProductOfferID>");//
				bf.append("<ProductOfferName>").append(aDcSystemParamManager.getProdOfferNameById(offerId)).append("</ProductOfferName>");
				bf.append("<ChargingPolicyCN>").append(aDcSystemParamManager.getProdOffVOById(offerId).getChargingpolicyCn()).append("</ChargingPolicyCN>");
				bf.append("<Status>").append(status).append("</Status>");
				bf.append("<SubscribeTime>").append(or.getCreateDate()).append("</SubscribeTime>");
				
				//����������Ҫ���⴦�� wendm 2010-9-20  ������Чʱ�� ʧЧʱ��Ϊ�յ����
				if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
					if ("".equals(or.getEffDate())||null==or.getEffDate()){//��Чʱ��Ϊ��
						bf.append("<EffDate>").append(or.getCreateDate()).append("</EffDate>");
					}else{
						bf.append("<EffDate>").append(or.getEffDate()).append("</EffDate>");
					}
					if ("".equals(or.getExpDate())||null==or.getExpDate()){//ʧЧʱ��Ϊ��
						bf.append("<ExpDate>").append("20300101000000").append("</ExpDate>");
					}else{
						bf.append("<ExpDate>").append(or.getExpDate()).append("</ExpDate>");
					}
					
				}else{
					bf.append("<EffDate>").append(or.getEffDate()).append("</EffDate>");
					bf.append("<ExpDate>").append(or.getExpDate()).append("</ExpDate>");	
				}
				bf.append("<ChannelPlayerID>").append(or.getOrderChn()).append("</ChannelPlayerID>");
				bf.append("</ResultInfo>");

		}
	}
	
	/**
	 * ���¶��б����򣬶�189����Ͱ����ַ�����ǰ�档
	 * @author yi.chengfeng 2010-9-7
	 * @param ls
	 * @param resultCode
	 * @param resultMsg
	 * @param order
	 * @return
	 */
	private String toXmlForSMS(List ls, String resultCode, String resultMsg,
			CustomerOrder order) {
		List has189OrMusic = new ArrayList();//189������߰�����
		List other = new ArrayList();
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			OrderRelationVO or = (OrderRelationVO) iterator.next();
			String productNbr = aDcSystemParamManager.getProductNbrById(or.getProdId());
			if(is189mailOrImusic(productNbr)){
				has189OrMusic.add(or);
			}else{
				other.add(or);
			}
		}
		has189OrMusic.addAll(other);
		StringBuffer bf = new StringBuffer("");
		bf.append("").append("<Response>").append("<SubInstQryFromVSOPResp>")
			.append("<StreamingNo>").append(order.getCustSoNumber()).append("</StreamingNo>")
			.append("<ResultCode>").append(resultCode).append("</ResultCode>")
			.append("<ResultDesc>").append(resultMsg).append("</ResultDesc>");
		if(ls != null){
			orderRelationListToXml(has189OrMusic, bf);
		}
		bf.append("</SubInstQryFromVSOPResp>").append("</Response>");
		return bf.toString();
	}
	
	private boolean is189mailOrImusic(String productNbr) {
		String mailOrImusicProductNbr = DcSystemParamManager.getParameter(VsopConstants.MAIL_IMUSIC_PRODUCTNBR);
		if(mailOrImusicProductNbr.indexOf(productNbr) != -1) return true;
		return false;
	}
	private String getPartnerNameByProductId(OrderRelationVO or) {
		PartnerVO partner = aDcSystemParamManager.getProductVOByid(or.getProdId()).getPartnerVO();
		return partner == null ? "" :  partner.getPartnerName();
	}
	private String getPartnerIdByProductId(OrderRelationVO or) {
		PartnerVO partner = aDcSystemParamManager.getProductVOByid(or.getProdId()).getPartnerVO();
		return partner == null ? "" :  partner.getPartnerId();
	}

}
