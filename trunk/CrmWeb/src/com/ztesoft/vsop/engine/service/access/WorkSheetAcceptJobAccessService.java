package com.ztesoft.vsop.engine.service.access;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.AproductInfo;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.OrderRelationVO;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.provinceUtil.AppendLanCode;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.vo.ProdOffVO;

/**
 * ����ͨ��������ʱ����������
 * @author cooltan
 *
 */
public class WorkSheetAcceptJobAccessService extends CommonAccessService
		implements IAccess {

	public WorkSheetAcceptJobAccessService() {
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
		in.put("serviceCode",order.getCustOrderType());
		return in;
	}
	/**
	 * ���Ի����ز���  ��ÿ��������д
	 * @param in
	 * @return
	 */
	public  Map concreteOutOpertion(Map in) throws Exception{
		StringBuffer bf = new StringBuffer();
		String resultCode=(String)in.get("resultCode");
		String resultMsg=(String)in.get("resultMsg");
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		bf.append("<").append("ResultInfo").append(">")
		.append("<orderId>").append(order.getCustOrderId()).append("</orderId>")
		.append("<resultCode>").append(resultCode).append("</resultCode>")
		.append("<resultMessage>").append(resultMsg).append("</resultMessage>")
		.append("</").append("ResultInfo").append(">");
		in.put("accessOutObject", bf.toString());
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
		//order.setProdId(XMLUtils.getSingleTagValue(inXML,"ProdSpecCode"));
		order.setAccNbr(XMLUtils.getSingleTagValue(inXML,"ProductNo"));
		order.setOldAccNbr(XMLUtils.getSingleTagValue(inXML,"OldProductNo"));
		order.setStatus(OrderConstant.orderStateOfCreated);
		
		//userName UIMNO oldUIMNO
		order.setUserName(XMLUtils.getSingleTagValue(inXML,"UserName"));
		order.setUimNO(XMLUtils.getSingleTagValue(inXML,"UIMNo"));
		order.setOldUimNO(XMLUtils.getSingleTagValue(inXML,"OldUIMNo"));
		
		String prodSpecCode= XMLUtils.getSingleTagValue(inXML,"ProdSpecCode");
		//�������ػ�����
		//panshaohua 2010-8-9 ���ݽӿڵ�����Ʒ���룬�õ���Ʒid
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			if(!("C7".equals(prodSpecCode) || "I8".equals(prodSpecCode)) ){  //��������ֻ���������+������Ϊ��Ʒ����
				order.setAccNbr(order.getLanId()+order.getAccNbr());
				order.setOldAccNbr(order.getLanId()+order.getOldAccNbr());
			}
			prodSpecCode=DcSystemParamManager.getInstance().getProductIdByNbr(prodSpecCode);
			order.setProdId(prodSpecCode);
		}else 
			order.setProdId(prodSpecCode);
		//���ػ������������Ҫ������ƴ���ں���ǰ��
		String pAccNbr=AppendLanCode.getInstance().appendAccNbrLan(order.getAccNbr(), order.getProdId(), order.getLanId());
		order.setAccNbr(pAccNbr);
		
		String seq = XMLUtils.getSingleTagValue(inXML,"InOrderId");
		if(!"".equals(seq)){
			order.setCustOrderId(seq);
		}
		String pStateCd=XMLUtils.getSingleTagValue(inXML,"UserState");
		String pPaymentModeCd=XMLUtils.getSingleTagValue(inXML,"UserPayType");
		//�������ػ������ѷ�����CRM��������������ת����VSOPʶ��ĸ�������(crm��-1)
		if(CrmConstants.GX_PROV_CODE.equals(this.provinceCode) && null!=pPaymentModeCd 
				&& !"".equals(pPaymentModeCd)){
			int  intpayMentCd=Integer.parseInt(pPaymentModeCd);
			pPaymentModeCd=String.valueOf(intpayMentCd-1);
		}
		String fkProdInstid=order.getProdInstId();
		//�����������Ʒ���½�һ����Ʒʵ�� ��ȡ��Ʒʵ����ʶ����
		if(OrderConstant.orderTypeOfInstall.equals(order.getCustOrderType())){
				String prodInstId = null;
				SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
				if("".equals(order.getProdInstId()))
				prodInstId= aSequenceManageDAOImpl.getNextSequence("seq_prod_inst_id");
				else prodInstId = order.getProdInstId();
				order.setProdInstId(prodInstId);
				ProdInstVO aProdInstVO=new ProdInstVO();
				aProdInstVO.setProdInstId(prodInstId);
				aProdInstVO.setAccNbr(order.getAccNbr());
				aProdInstVO.setProdId(order.getProdId());
				aProdInstVO.setPaymentModeCd(pPaymentModeCd);
				aProdInstVO.setStateCd(ProdInstVO.StateOfEffective);
				aProdInstVO.setLanId(order.getLanId());
				order.setProdInst(aProdInstVO);
				////����������Ͳ�Ʒʵ����ʶ���Բ�Ʒʵ����ʶ��ѯ��ʵ�� �������ػ�
		}else if(fkProdInstid!=null&&!"".equals(fkProdInstid)
				&&CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
			ProdInstVO aProdInstVO = null;
			aProdInstVO = aProdInstHelpDao.qryProdInstByProdInstId(fkProdInstid);
			aProdInstVO.setStateCd(pStateCd);
			aProdInstVO.setPaymentModeCd(pPaymentModeCd);
			order.setProdInstId(aProdInstVO.getProdInstId());
			order.setProdInst(aProdInstVO);
			
		}else{//����ͨ�����롢��Ʒ���Ͳ�Ʒʵ��
			ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
			String accNbr=order.getAccNbr();
			if(OrderConstant.orderTypeOfModifyNo.equals(order.getCustOrderType())
					||OrderConstant.orderTypeOfTransferUser.equals(order.getCustOrderType())){
				if(null != order.getOldAccNbr() || !"".equals(order.getOldAccNbr()))
					accNbr=order.getOldAccNbr();
			}
			String productId=order.getProdId();
			String actType = order.getCustOrderType();
			ProdInstVO aProdInstVO = null;
			//���û�״̬��������ĺš��ĸ������Ͷ���ֻҪ�Ƿ�00X���û����ɲ���
			if(actType != null && 
			   ("11".equals(actType) || "15".equals(actType) 
			    || "12".equals(actType) || "16".equals(actType) || "20".equals(actType))){
				//��trueʱ��00X�������״̬�Ķ�Ϊ��Ч״̬��
				aProdInstVO=aProdInstHelpDao.qryProdInstByAccNbrAndProductId(accNbr, productId, true);
			}else{
				//��falseʱֻ��00A��������00B��00C״̬Ϊ��Ч״̬��
				aProdInstVO=aProdInstHelpDao.qryProdInstByAccNbrAndProductId(accNbr, productId, false);
			}
			//�������ػ�
			//�����Ĺ̻��ƻ�ҵ���ʱ��(�ĺ���͸ķ�����),������͸ķ�����,����Ҫ��oldAccNbr
			if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode) &&
				OrderConstant.orderTypeOfModifyServiceFunction.equals(order.getCustOrderType())){
				if(null != order.getOldAccNbr() || !"".equals(order.getOldAccNbr())){
					if(null == aProdInstVO.getProdInstId() || "".equals(aProdInstVO.getProdInstId())){
						accNbr = order.getOldAccNbr();
						aProdInstVO=aProdInstHelpDao.qryProdInstByAccNbrAndProductId(accNbr, productId, false);
						order.setAccNbr(accNbr);
					}
				}
			}
			aProdInstVO.setStateCd(pStateCd);
			aProdInstVO.setPaymentModeCd(pPaymentModeCd);
			order.setProdInstId(aProdInstVO.getProdInstId());
			order.setProdInst(aProdInstVO);
		}
		//�������ػ��������������ڲ������ָ�����Ʒ����ֵ��Ʒ�������Ὣ��������ֵ��Ʒ������ֵ��Ʒ����ʽ������ֵ��Ʒ�ڵ�����Ч��ʧЧʱ�䣩
		//�ṩ��vsop��������20��vsop�ڽ���xml�������ֳ�������Ʒ����ֵ��Ʒ
		//������������Ʒ��Ϣ
		//�������ػ�����
		//panshaohua 2010-8-14 
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			this.jxParseVProductInfoList(inXML,order);
		}else 
			this.parseVProductInfoList(inXML,order);
		//���������͹�����xml����û�и�����Ʒ�ڵ�vproductinfo
		//��������ҵ������������Ʒ��Ϣ
		this.parseAProductInfoList(inXML,order);
	}
	/**
	 * ��ȡ����ҵ������������Ʒ�б����
	 * @param inXML
	 * @param order
	 */
	private void parseAProductInfoList(String inXML,CustomerOrder order) {
		if(OrderConstant.orderTypeOfInstall.equals(order.getCustOrderType()) 
				|| OrderConstant.orderTypeOfModifyAProduct.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfModifyServiceFunction.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfUninstall.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfTransferUser.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfModifyPayType.equals(order.getCustOrderType())){
			List aproductInfoList = new ArrayList();
			
			String result  = "";
			StringBuffer bf = new StringBuffer();
			String tagName = "AProductInfo";
			bf.append("<").append(tagName ).append(">([\\s\\S]*?)</").append(tagName).append(">");
			Pattern pattern = Pattern.compile(bf.toString());
			Matcher matcher = pattern.matcher(inXML);
			while(matcher.find()){
				result = matcher.group(1);
				String aProductActionType=XMLUtils.getSingleTagValue(result,"ActionType");
				String aProductID=XMLUtils.getSingleTagValue(result,"AProductID");
				if(null == aProductID || "".equals(aProductID))
					continue;
				String aProductInstID=XMLUtils.getSingleTagValue(result,"AProductInstID");
				//ҵ�����Ƕ���������û�д�ʵ��id������������һ��
				if(OrderConstant.orderTypeOfAdd.equals(aProductActionType) && "".equals(aProductInstID)){
					SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
					aProductInstID= aSequenceManageDAOImpl
							.getNextSequence("SEQ_BIZ_CAPABILITY_INST_ID");
				}
					
				AproductInfo aproduct=new AproductInfo();
				aproduct.setActionType(aProductActionType);
				aproduct.setAProductID(aProductID);
				aproduct.setAProductInstID(aProductInstID);
				aproduct.setLanCode(order.getLanId());
				aproductInfoList.add(aproduct);
			}
			if(null!=order.getAproductInfoList()){//������ǰ���Ѿ�������������������Ʒ
				aproductInfoList.addAll(order.getAproductInfoList());
			}
			order.setAproductInfoList(aproductInfoList);
		}else{
			order.setAproductInfoList(new ArrayList());
		}
	}
	/**
	 * ��ȡ������ֵ��Ʒ�б���Ϣ
	 * @param inXML
	 * @param order
	 * @throws SQLException
	 */
	private void parseVProductInfoList(String inXML,CustomerOrder order) throws SQLException {
		if(OrderConstant.orderTypeOfInstall.equals(order.getCustOrderType())
				|| OrderConstant.orderTypeOfModifyVProduct.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfModifyServiceFunction.equals(order.getCustOrderType())){
			List productOfferInfoList = new ArrayList();
			String offNbr = XMLUtils.getSingleTagValue(inXML, "OffNbr");
			String offerId=null;
			if("".equals(offNbr)){
				offNbr=XMLUtils.getSingleTagValue(inXML, "ProductOfferId");
				offerId=DcSystemParamManager.getInstance().getProdOfferIdByNbr(offNbr);
			}else{
				offerId=XMLUtils.getSingleTagValue(inXML, "ProductOfferId");
			}
			ProductOfferInfo aProductOfferInfo=new ProductOfferInfo();
			aProductOfferInfo.setOfferId(offerId);
			aProductOfferInfo.setOfferNbr(offNbr);
			String offerType=OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID;
			//������crm����������Ʒ����Ҳ�Ǵ���ֵ�ģ��紫ͳ+��ֵ�͸�ismp�ӿ��ǲ����ݵ� liuyuming 20100926
			if(CrmConstants.GX_PROV_CODE.equals(this.provinceCode)){
				offerType=OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID;
			}
			aProductOfferInfo.setOfferType(offerType);
			/*aProductOfferInfo.setExpDate(expDate);
			aProductOfferInfo.setEffDate(effDate);
			aProductOfferInfo.setActioType(actioType);*/
			
			List vproductInfoList=new ArrayList();
			String result  = "";
			StringBuffer bf = new StringBuffer();
			String tagName = "VProductInfo";
			bf.append("<").append(tagName ).append(">([\\s\\S]*?)</").append(tagName).append(">");
			Pattern pattern = Pattern.compile(bf.toString());
			Matcher matcher = pattern.matcher(inXML);
			//�������ػ��������������ڲ������ָ�����Ʒ����ֵ��Ʒ�������Ὣ��������ֵ��Ʒ������ֵ��Ʒ����ʽ������ֵ��Ʒ�ڵ�����Ч��ʧЧʱ�䣩
			//�ṩ��vsop��������20��vsop�ڽ���xml�������ֳ�������Ʒ����ֵ��Ʒ  start
			String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
			if(CrmConstants.GX_PROV_CODE.equals(provinceCode)){
				//������Ʒ�б�
				List aproductInfoList = new ArrayList();
				while(matcher.find()){
					result = matcher.group(1);
					//ȡ��VProductID��ֵ��Ʒid����ڻ�����serviceAbilityҵ��������������ڣ����Ǹ�����Ʒ����֮������ֵ��Ʒ
					String vProdId = XMLUtils.getSingleTagValue(result,"VProductID");//��������������Ʒ��id��nbrһ��
					String _value = (String)DcSystemParamManager.getInstance().getCrmCProdByCode(vProdId);
					if(_value == null || "".equals(_value)){
						//�Ҳ���������Ʒƥ��ı����������ֵ��Ʒ
						String isActive = (String)DcSystemParamManager.getParameter("IS_ACTIVE");
						//����VSOP�Խӷ����ֲ����ߣ��Ȳ�������ֵ��Ʒ�������ɷ�����ҵ��ƽ̨�����֮���ٸĳ�VSOP�ͼ���
						//liuyuming 20100913
						String accNbrStr = (String)DcSystemParamManager.getParameter("IS_FK_TEST_NBR");
						if(isActive == null || "T".equals(isActive) || order.getAccNbr().indexOf(accNbrStr)>-1){
							//isActive��T�ͼ��F���ͼ���
							VproductInfo vproduct = new VproductInfo();
							this.parseAVproductInfo(result, vproduct, order,offerId,offNbr,offerType);
							vproductInfoList.add(vproduct);
						}
						//end
					}else{
						//������Ǹ�����Ʒ�ڵ�����
						String aProductActionType=XMLUtils.getSingleTagValue(result,"ActionType");
						String aProductID=vProdId;//ֱ���÷�������������������ƷID
						String aProductInstID=XMLUtils.getSingleTagValue(result,"VProductInstID");
						//ҵ�����Ƕ���������û�д�ʵ��id������������һ��
						if(OrderConstant.orderTypeOfAdd.equals(aProductActionType) && "".equals(aProductInstID)){
							SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
							aProductInstID= aSequenceManageDAOImpl
									.getNextSequence("SEQ_BIZ_CAPABILITY_INST_ID");
						}
							
						AproductInfo aproduct=new AproductInfo();
						aproduct.setActionType(aProductActionType);
						aproduct.setAProductID(aProductID);
						aproduct.setAProductInstID(aProductInstID);
						aproduct.setLanCode(order.getLanId());
						aproductInfoList.add(aproduct);
					}
				}
				order.setAproductInfoList(aproductInfoList);
				//�������ػ� end;
			}else{
				while(matcher.find()){
					result = matcher.group(1);
					VproductInfo vproduct = new VproductInfo();
					this.parseAVproductInfo(result, vproduct, order,offerId,offNbr,offerType);
					vproductInfoList.add(vproduct);
				}
			}
			if(vproductInfoList.size() > 0){
				aProductOfferInfo.setVproductInfoList(vproductInfoList);
				productOfferInfoList.add(aProductOfferInfo);
				order.setProductOfferInfoList(productOfferInfoList);
			}else{
				order.setProductOfferInfoList(productOfferInfoList);
			}
		}else{
			order.setProductOfferInfoList(new ArrayList());
		}
		
	}
	/**
	 * ͨ��xml���쵥��������ֵ��Ʒ��Ϣ
	 * @param xml
	 * @param vproduct
	 * @param order
	 * @param offerId
	 * @param offerNbr
	 * @param offerType
	 * @throws SQLException
	 */
	public void parseAVproductInfo(String xml, 
			VproductInfo vproduct,CustomerOrder order,String offerId,String offerNbr,
			String offerType) throws SQLException {
		
		vproduct.setActionType(XMLUtils.getSingleTagValue(xml, "ActionType"));
		String vproductNbr = XMLUtils.getSingleTagValue(xml, "VProductNbr");
		if("".equals(vproductNbr)){ //��û��VProductNbr�ڵ���ȡVProductID��Ȼ��ת��
			String tmpNbr = "";
			String tmpId = "";
			//�����ӷ����͹�������ֵ��Ʒ�ı�����crm���룬Ӧת����ҵ��ƽ̨�����vsop���ڲ�
			String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
			if(CrmConstants.GX_PROV_CODE.equals(provinceCode)){
				String id = XMLUtils.getSingleTagValue(xml,"VProductID");//���������Ķ�ӦCRM_PRODUCT_CODE
				tmpNbr = DcSystemParamManager.getInstance().getCrmCodeIsmpNbrByCode(id);//ISMP_PRODUCT_NBR
				tmpId = DcSystemParamManager.getInstance().getProductIdByNbr(tmpNbr);//VSOP_PRODUCT_ID
				//�����͹���crm��������ֵ��Ʒ��Ӧ�Ĵ���ֵ����Ʒ
				if(offerId == null || "".equals(offerId))
					offerId = DcSystemParamManager.getInstance().getofferIdByProductId(tmpId);//VSOP_OFFER_ID
				if(offerNbr == null || "".equals(offerNbr))
					offerNbr = DcSystemParamManager.getInstance().getProdOfferNbrById(offerId);//ISMP_OFFER_NBR
			}else{
				tmpNbr=XMLUtils.getSingleTagValue(xml,"VProductID");
				tmpId=DcSystemParamManager.getInstance().getProductIdByNbr(tmpNbr);
			}
			vproduct.setVProductNbr(tmpNbr);
			vproduct.setVProductId(tmpId);
		}else{  //�����Ƿ���ͨ��ʱ�����ʱ����������õ�
			vproduct.setVProductNbr(vproductNbr);
			String tmpId=XMLUtils.getSingleTagValue(xml, "VProductID");
			vproduct.setVProductId(tmpId);
		}
		String vProductInstID=XMLUtils.getSingleTagValue(xml,"VProductInstID");
		//ҵ�����Ƕ���������û�д�ʵ��id������������һ��
		if(OrderConstant.orderTypeOfAdd.equals(vproduct.getActionType()) 
				&& "".equals(vProductInstID)){
			SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
			String s= aSequenceManageDAOImpl.getNextSequence("SEQ_ORDER_RELATION_ID");
			vproduct.setVProductInstID(s);
		}else if(OrderConstant.orderTypeOfDel.equals(vproduct.getActionType())){
			OrderRelationHelpDao aOrderRelationHelpDao=new OrderRelationHelpDao();
			//��ѯ���ݿ��ȡ������ϵʵ����ʶ
			OrderRelationVO orderVO=aOrderRelationHelpDao.qryORByProdInstIdAndProductId(order.getProdInstId(),
					vproduct.getVProductId());
			String vprodInstId="";
			if(null!=orderVO){
				vprodInstId =orderVO.getOrderRelationId();
			}
			vproduct.setVProductInstID(vprodInstId);
		}
		vproduct.setEffDate(XMLUtils.getSingleTagValue(xml,"EffDate"));
		vproduct.setExpDate(XMLUtils.getSingleTagValue(xml,"ExpDate"));
		vproduct.setOfferId(offerId);
		vproduct.setOfferNbr(offerNbr);
		vproduct.setOfferType(offerType);
	}

	
	
	//�������ػ�
	/**
    1��productOfferId ��Ϊ��
      vprocuctinfo  ȫΪ��������Ʒ   VProductID��ֵ��Ʒ����
     2:productOfferId Ϊ��
    2.1  VProductID Ϊ����Ʒ���� �ж� ��offer_sub_type Ϊ1 ����Ϊ2�� ������Ʒ���ж��Ƿ��Ǵ���ֵ��������Ʒ���ߴ�ͳ��+��ֵ��������Ʒ��
       ����ǣ�������������Ʒ�����߼����д��� ������ǣ�������ֵ��Ʒ���� 
       2.2  VproductId �Ǵ���ֵ��Ʒ��������ֵ��Ʒȥ����Ӧ�Ĵ���ֵ����Ʒ
	
	 * ��ȡ������ֵ��Ʒ�б���Ϣ
	 * @param inXML
	 * @param order
	 * @throws SQLException
	 */
	private void jxParseVProductInfoList(String inXML,CustomerOrder order) throws SQLException {
		if(OrderConstant.orderTypeOfInstall.equals(order.getCustOrderType())
				|| OrderConstant.orderTypeOfModifyVProduct.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfModifyServiceFunction.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfModifyPayType.equals(order.getCustOrderType())
//				||OrderConstant.orderTypeOfUninstall.equals(order.getCustOrderType())  �������Ҫ���������ͼ��� yi.chengfeng @2010-9-29 22:38
				||OrderConstant.orderTypeOfTransferUser.equals(order.getCustOrderType())){
			List productOfferInfoList = new ArrayList();
			
			String offNbr=XMLUtils.getSingleTagValue(inXML, "ProductOfferId");
			String offerId=DcSystemParamManager.getInstance().getProdOfferIdByNbr(offNbr);
			ProductOfferInfo aProductOfferInfo=new ProductOfferInfo();
			if(null != offerId && !"".equals(offerId)){
				ProdOffVO offer = DcSystemParamManager.getInstance().getProdOffVOById(offerId);
				aProductOfferInfo.setOfferId(offerId);
				aProductOfferInfo.setOfferNbr(offNbr);
				String offerType=offer.getProdOffSubType();
				aProductOfferInfo.setOfferType(offerType);
			}
			
			List vproductInfoList=new ArrayList();
			String result  = "";
			StringBuffer bf = new StringBuffer();
			String tagName = "VProductInfo";
			bf.append("<").append(tagName ).append(">([\\s\\S]*?)</").append(tagName).append(">");
			Pattern pattern = Pattern.compile(bf.toString());
			Matcher matcher = pattern.matcher(inXML);
			//�������ڲ������ָ�����Ʒ����ֵ��Ʒ�������Ὣ��������ֵ��Ʒ������ֵ��Ʒ����ʽ������ֵ��Ʒ�ڵ�����Ч��ʧЧʱ�䣩
			//�ṩ��vsop��������20��vsop�ڽ���xml�������ֳ�������Ʒ����ֵ��Ʒ 
				//������Ʒ�б�
				List aproductInfoList = new ArrayList();
				while(matcher.find()){
					result = matcher.group(1);
					//ȡ��VProductID��ֵ��Ʒid����ڻ�����serviceAbilityҵ��������������ڣ����Ǹ�����Ʒ����֮������ֵ��Ʒ
					String vProdId = XMLUtils.getSingleTagValue(result,"VProductID");//��������������Ʒ��id��nbrһ��
					String _value = (String)DcSystemParamManager.getInstance().getCrmCProdByCode(vProdId);
					if(_value == null || "".equals(_value)){
						//�Ҳ���������Ʒƥ��ı����������ֵ��Ʒ��������Ʒ
						if(null == offerId || "".equals(offerId)){//û������Ʒid�����ж�VproductId ����ֵ��Ʒ��������Ʒ
							String productOfferId = DcSystemParamManager.getInstance().getProdOfferIdByNbr(vProdId);
							if(null == productOfferId || "".equals(productOfferId)){//vprodIdΪ��ֵ��Ʒid����Ҫ������ֵ��Ʒidȥ���Ҵ���ֵ����Ʒ
								String productId = DcSystemParamManager.getInstance().getProductIdByNbr(vProdId);
								productOfferId = DcSystemParamManager.getInstance().getofferIdByProductId(productId);
							}
							ProductOfferInfo productOfferInfo=new ProductOfferInfo();
							ProdOffVO offer = DcSystemParamManager.getInstance().getProdOffVOById(productOfferId);
							productOfferInfo.setOfferId(productOfferId);
							productOfferInfo.setOfferNbr(offer.getOffNbr());
							String offerType=offer.getProdOffSubType();
							productOfferInfo.setOfferType(offerType);
							productOfferInfo.setActioType(XMLUtils.getSingleTagValue(result, "ActionType"));
							this.getProductByOfferId(productOfferInfo,order,result);//�õ�����Ʒ��Ӧ��������ֵ��Ʒ
							productOfferInfoList.add(productOfferInfo);
						}else {// �����������Ʒid,vproductId���� ��ֵ��Ʒ
							VproductInfo vproduct = new VproductInfo();
							this.parseAVproductInfo(result, vproduct, order,offerId,offNbr,aProductOfferInfo.getOfferType());
							vproductInfoList.add(vproduct);
						}
					}else{
						//������Ǹ�����Ʒ�ڵ�����
						String aProductActionType=XMLUtils.getSingleTagValue(result,"ActionType");
						String aProductID=vProdId;//ֱ���÷�������������������ƷID
						String aProductInstID=XMLUtils.getSingleTagValue(result,"VProductInstID");
						//ҵ�����Ƕ���������û�д�ʵ��id������������һ��
						if(OrderConstant.orderTypeOfAdd.equals(aProductActionType) && "".equals(aProductInstID)){
							SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
							aProductInstID= aSequenceManageDAOImpl
									.getNextSequence("SEQ_BIZ_CAPABILITY_INST_ID");
						}
							
						AproductInfo aproduct=new AproductInfo();
						aproduct.setActionType(aProductActionType);
						aproduct.setAProductID(_value);
						aproduct.setAProductInstID(aProductInstID);
						aproduct.setLanCode(order.getLanId());
						aproductInfoList.add(aproduct);
					}
				}
				order.setAproductInfoList(aproductInfoList);
				
			if(vproductInfoList.size() > 0){
				aProductOfferInfo.setVproductInfoList(vproductInfoList);
				productOfferInfoList.add(aProductOfferInfo);
				order.setProductOfferInfoList(productOfferInfoList);
			}else{
				order.setProductOfferInfoList(productOfferInfoList);
			}
		}else{
			order.setProductOfferInfoList(new ArrayList());
		}
	}
	private void  getProductByOfferId(ProductOfferInfo prodOfferInfo,CustomerOrder order,String xml) throws SQLException{
		List vproductList = new ArrayList();
		List vProductIdlist  =DcSystemParamManager.getInstance().getVproductIdsByOfferId(prodOfferInfo.getOfferId());
		for (int i = 0; i < vProductIdlist.size(); i++) {
			VproductInfo vprodInfo = new VproductInfo();
			vprodInfo.setOfferId(prodOfferInfo.getOfferId());
			vprodInfo.setVProductId((String) vProductIdlist.get(i));
			vprodInfo.setActionType(prodOfferInfo.getActioType());
			vprodInfo.setOfferType(prodOfferInfo.getOfferType());
			
			String vProductInstID=XMLUtils.getSingleTagValue(xml,"VProductInstID");
			//ҵ�����Ƕ���������û�д�ʵ��id������������һ��
			if(OrderConstant.orderTypeOfAdd.equals(vprodInfo.getActionType()) 
					&& "".equals(vProductInstID)){
				SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
				String s= aSequenceManageDAOImpl.getNextSequence("SEQ_ORDER_RELATION_ID");
				vprodInfo.setVProductInstID(s);
			}else if(OrderConstant.orderTypeOfDel.equals(vprodInfo.getActionType())){
				OrderRelationHelpDao aOrderRelationHelpDao=new OrderRelationHelpDao();
				//��ѯ���ݿ��ȡ������ϵʵ����ʶ
				OrderRelationVO orderVO=aOrderRelationHelpDao.qryORByProdInstIdAndProductId(order.getProdInstId(),
						vprodInfo.getVProductId());
				String vprodInstId="";
				if(null!=orderVO){
					vprodInstId =orderVO.getOrderRelationId();
				}
				vprodInfo.setVProductInstID(vprodInstId);
			}
			vprodInfo.setEffDate(XMLUtils.getSingleTagValue(xml,"EffDate"));
			vprodInfo.setExpDate(XMLUtils.getSingleTagValue(xml,"ExpDate"));

			//��ֵ��Ʒ����
			vprodInfo.setVProductNbr(DcSystemParamManager.getInstance().getProductNbrById((String) vProductIdlist.get(i)));
			//����Ʒ����
			vprodInfo.setOfferNbr(prodOfferInfo.getOfferNbr());
			vproductList.add(vprodInfo);
		}
		prodOfferInfo.setVproductInfoList(vproductList);
	}
}
