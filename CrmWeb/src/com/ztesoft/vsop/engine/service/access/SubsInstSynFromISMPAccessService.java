package com.ztesoft.vsop.engine.service.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.OrderEngine;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.help.AssemCoreEntityServices;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.vo.ProdOffVO;
import com.ztesoft.vsop.web.vo.ProdVO;

/**
 * ������ϵͬ��(IMSP-VSOP)
 * @author panshaohua
 *
 */
public class SubsInstSynFromISMPAccessService extends CommonAccessService implements IAccess{
	private static Logger logger = Logger.getLogger(SubsInstSynFromISMPAccessService.class);
	public Map concreteInOpertion(Map in) throws Exception {
		CustomerOrder order = new CustomerOrder();
		String requestXML = (String) in.get("accessInObject");
		order = parseXml(requestXML);
		in.put("busiObject", order);
		in.put("serviceCode", String.valueOf(OrderEngine.SERVICE_ORSYNFROMISMP30));
		return in;
	}

	public Map concreteOutOpertion(Map in) throws Exception {
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		String streamingNo =order.getCustSoNumber();
		String resultCode = (String) in.get("resultCode");
		List resultInfo = (List) in.get("retBusiObject");
		String responseXML = getResult(streamingNo,resultCode);
		in.put("accessOutObject", responseXML);
		return in;
	}
	public CustomerOrder parseXml(String xmlStr) throws Exception{
		logger.info("SubsInstSynFromISMPAccessService.parseXml start");
		System.out.println("SubsInstSynFromISMPAccessService.parseXml start");
		CustomerOrder order = new CustomerOrder();
		long t1 = System.currentTimeMillis();
		logger.info("SubsInstSynFromISMPAccessService.parseXml xmlStr:"+xmlStr);
		System.out.println("SubsInstSynFromISMPAccessService.parseXml xmlStr:"+xmlStr);
		String StreamingNo = XMLUtils.getSingleTagValue(xmlStr,"StreamingNo");
		String TimeStamp = XMLUtils.getSingleTagValue(xmlStr,"TimeStamp");
		String SystemId = XMLUtils.getSingleTagValue(xmlStr,"SystemId");
		String UserID = XMLUtils.getSingleTagValue(xmlStr,"UserID");
		String userIDType = XMLUtils.getSingleTagValue(xmlStr,"UserIDType");
		String ProductNbr = XMLUtils.getSingleTagValue(xmlStr,"ProductID");
		String offerType = "";
		String ProductID = "";
		String prodOfferId ="";
		String oldProdOfferId="";
		
		if(ProductNbr!=null&&!ProductNbr.equals("")){
			ProductID=DcSystemParamManager.getInstance().getProductIdByNbr(ProductNbr);
		}
		
		String oldProductNbr = XMLUtils.getSingleTagValue(xmlStr,"OldProductID");
		String oldProductID = "";
		if(oldProductNbr!=null&&!oldProductNbr.equals("")){
			oldProductID =DcSystemParamManager.getInstance().getProductIdByNbr(oldProductNbr);
		}
		
		String PackageID = XMLUtils.getSingleTagValue(xmlStr,"PackageID");
		if(PackageID!=null&&!PackageID.equals("")){
			offerType = OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID;
			prodOfferId =DcSystemParamManager.getInstance().getProdOfferIdByNbr(PackageID);
		}		
		String OldpackageID = XMLUtils.getSingleTagValue(xmlStr,"OldpackageID");
		if(OldpackageID!=null&&!OldpackageID.equals("")){
			OldpackageID = DcSystemParamManager.getInstance().getProdOfferIdByNbr(OldpackageID);
		}
		
		String PProductOfferID = XMLUtils.getSingleTagValue(xmlStr,"PProductOfferID");
		if(PProductOfferID!=null&&!PProductOfferID.equals("")){
			offerType = OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID;
			PProductOfferID = DcSystemParamManager.getInstance().getProdOfferIdByNbr(PProductOfferID);
		}
		String OldPProductOfferID = XMLUtils.getSingleTagValue(xmlStr,"OldPProductOfferID");
		if(OldPProductOfferID!=null&&!OldPProductOfferID.equals("")){
			OldPProductOfferID = DcSystemParamManager.getInstance().getProdOfferIdByNbr(OldPProductOfferID);
		}
		
		if("".equals(offerType)){//������Ǵ�ͳ+��ֵ���� Ҳ���������ײͣ�ֱ��ȡ��Ʒ��Ӧ������Ʒ
			offerType = OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID;
			//ProdVO prodVo = DcSystemParamManager.getInstance().getProductVOByid(ProductID);
			//prodOfferId =prodVo.getProdOffId();
			prodOfferId= DcSystemParamManager.getInstance().getofferIdByProductId(ProductID);
		}
		
		String OPType = XMLUtils.getSingleTagValue(xmlStr,"OPType");
		String EffDate = XMLUtils.getSingleTagValue(xmlStr,"EffDate");
		String ExpDate = XMLUtils.getSingleTagValue(xmlStr,"ExpDate");
		String VerUserId = XMLUtils.getSingleTagValue(xmlStr,"VerUserId");
		
		ProdInstVO prodInst = new ProdInstVO();
		//*****
		//�������ػ���������ϵͬ������ʱ�����������ûͬ���û���Ϣ��VSOP������дͬ�������Ķ�����ϵ��order_relation_middle��
		//*****
		if(this.provinceCode.equals(CrmConstants.GX_PROV_CODE)){
			//���û�,��00X���û��������
			prodInst = getProdInstByUserType_GX(userIDType,UserID);
			if((prodInst.getAccNbr()==null || "".equals(prodInst.getAccNbr()))){
				order.setExistProdInst(false);
				prodInst.setProdInstId("-1");// �û�ʵ��ID,��־�˺�����û���Ϣ��δͬ����VSOP
				prodInst.setLanId("-1");// �û�������
				prodInst.setAccNbr(UserID);
				prodInst.setProdId(userIDType);
			}
		}else{
			prodInst = getProdInstByUserType(userIDType,UserID);
		}
		
		order.setProdInst(prodInst);
		order.setProdInstId(prodInst.getProdInstId());
		order.setCustSoNumber(StreamingNo);
		order.setAccNbr(UserID);
		order.setStatus(OrderConstant.orderStateOfCreated);
		order.setProdId(prodInst.getProdId()); 
		order.setLanId(prodInst.getLanId());
		String orderType = "0";
		if("3".equals(OPType)){
			orderType="1"; //����Ǵ�������ʱ���˶��Ļ�����תΪ�ڲ��˶���ʶ
		}
		order.setCustOrderType(orderType);
		//orderRelationISMPVO.setVerUserId(VerUserId);
		order.setChnId(SystemId);
		order.setOrderChn(SystemId);
		order.setOrderSystem(SystemId);
		ProductOfferInfo productOffer = new ProductOfferInfo();
		productOffer.setEffDate(EffDate);
		productOffer.setExpDate(ExpDate);
		productOffer.setActioType(orderType);
		productOffer.setOfferId(prodOfferId);
		productOffer.setOfferType(offerType);
		productOffer.setOldOfferId(oldProdOfferId);
		
		List vproductInfoList = new ArrayList();
		//�������ػ�����ISMP���������Ǵ���ֵ��������Ʒʱ,���ض�Ӧ����ֵ��Ʒ
		if (offerType.equals(OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID) 
				&& !"".equals(prodOfferId) && this.provinceCode.equals(CrmConstants.GX_PROV_CODE)) {
			AssemCoreEntityServices acs = new AssemCoreEntityServices();
			String[] prodOfferIds = new String[] { prodOfferId };// {prodOfferId};
			Map vProductMap = this.getProductVOByProdOfferIds(prodOfferIds,orderType);
			vproductInfoList = (List) vProductMap.get(prodOfferId);
		} else {
			VproductInfo product = new VproductInfo();
			product.setVProductId(ProductID);
			product.setOfferId(prodOfferId);
			product.setEffDate(EffDate);
			product.setExpDate(ExpDate);
			product.setVProductNbr(ProductNbr);
			SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
			String productInstID = aSequenceManageDAOImpl
					.getNextSequence("SEQ_ORDER_RELATION_ID");
			product.setVProductInstID(productInstID);
			product.setActionType(orderType);
			product.setOldVProductId(oldProductID);
			vproductInfoList.add(product);
		}
		productOffer.setVproductInfoList(vproductInfoList);
		List productOfferInfoList = new ArrayList();
		productOfferInfoList.add(productOffer);
		order.setProductOfferInfoList(productOfferInfoList);

		long t2 = System.currentTimeMillis();
		logger.info("SubsInstSynFromISMPAccessService.parseXml end");
		return order;
	}
	private ProdInstVO getProdInstByUserType(String userIdType,String accNbr) throws Exception{
		String prodTypeStr = "";
		if ("0".equals(userIdType)) {
			prodTypeStr = DcSystemParamManager.getParameter("DC_MSISDN");
		} else if ("1".equals(userIdType)) {
			prodTypeStr = DcSystemParamManager.getParameter("DC_PHS");
		} else {
			prodTypeStr = DcSystemParamManager.getParameter("DC_PSTN");
		}
		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
		ProdInstVO prodInst = prodInstDao.qryProdInstByAccNbrAndProductIds(accNbr, prodTypeStr);
		return prodInst;
	}
	
	private ProdInstVO getProdInstByUserType_GX(String userIdType,String accNbr) throws Exception{
		String prodTypeStr = "";
		if ("0".equals(userIdType)) {
			prodTypeStr = DcSystemParamManager.getParameter("DC_MSISDN");
		} else if ("1".equals(userIdType)) {
			prodTypeStr = DcSystemParamManager.getParameter("DC_PHS");
		} else {
			prodTypeStr = DcSystemParamManager.getParameter("DC_PSTN");
		}
		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
		ProdInstVO prodInst = prodInstDao.qryProdInstByAccNbrAndProductIds_GX(accNbr, prodTypeStr);
		return prodInst;
	}
	
	public static String getResult(String streamingNo,String resultCode){
		String retXml = "";
		StringBuffer bf = new StringBuffer("");
		String resultName = "";
		if(resultCode.equals("0")) resultName = "�ɹ�";
		else resultName = "ʧ��";
		bf.append("<Response>");
		bf.append("<SubsInstSynFromISMPResp>")
		.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
		.append("<ResultCode>").append(resultCode).append("</ResultCode>")
		.append("<ResultDesc>").append(resultName).append("</ResultDesc>")
		.append("</SubsInstSynFromISMPResp>");
		bf.append("</Response>");
		retXml = bf.toString();
		return retXml;
	}	
	
	/**
	 * ��������ƷID������Ӧ������ֵ��Ʒ��Ϣ
	 * @param prodOfferIds
	 *            ����ƷID����
	 * @param actionType ��ֵ��Ʒ����
	 * @return HashMap key--����ƷID,value--�������ƷID��Ӧ������ֵ��Ʒ�б�
	 */
	private Map getProductVOByProdOfferIds(String[] prodofferIdsArry,String actionType) {
		Map retMap = new HashMap();
		List prodOfferVOList = new ArrayList();
		List vProductIds = new ArrayList();
		List subVProducts = null;
		for (int i = 0; i < prodofferIdsArry.length; i++) {
			prodOfferVOList.add(DcSystemParamManager.getInstance().getProdOffVOById(prodofferIdsArry[i]));
		}
		
		SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
		// ������Ʒ��������в�Ʒ�ӽ���
		for (Iterator iterator = prodOfferVOList.iterator(); iterator.hasNext();) {
			ProdOffVO prodOffVO = (ProdOffVO) iterator.next();
			vProductIds = DcSystemParamManager.getInstance().getVproductIdsByOfferIdWithAllState(prodOffVO.getProdOffId());
			subVProducts = new ArrayList();
			if(vProductIds!=null){
				//����ֵ��Ʒת���ɶ�����ֵ��Ʒ�ӽ�����ͬʱ,����������ֵ��Ʒ��Ķ�����ϵʵ����ʶ
				for (Iterator iterator1 = vProductIds.iterator(); iterator1.hasNext();) {
					String seq = aSequenceManageDAOImpl.getNextSequence("SEQ_ORDER_RELATION_ID");
					ProdVO prodVO=DcSystemParamManager.getInstance().getProductVOByid((String) iterator1.next());
					subVProducts.add(prodVO.toVproductInfo(prodOffVO,actionType,OrderConstant.orderStateOfCreated,seq));
				}
			}
			retMap.put(prodOffVO.getProdOffId(), subVProducts);
		}
		return retMap;
	}
}
