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
 * 订购关系同步(IMSP-VSOP)
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
		
		if("".equals(offerType)){//如果不是传统+增值捆绑， 也不是捆绑套餐，直接取产品对应的销售品
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
		//广西本地化，订购关系同步过来时，如果服开还没同步用户信息到VSOP，则先写同步过来的订购关系到order_relation_middle表
		//*****
		if(this.provinceCode.equals(CrmConstants.GX_PROV_CODE)){
			//查用户,非00X的用户都查出来
			prodInst = getProdInstByUserType_GX(userIDType,UserID);
			if((prodInst.getAccNbr()==null || "".equals(prodInst.getAccNbr()))){
				order.setExistProdInst(false);
				prodInst.setProdInstId("-1");// 用户实例ID,标志此号码的用户信息还未同步到VSOP
				prodInst.setLanId("-1");// 用户地区号
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
			orderType="1"; //如果是传过来的时候退订的话，就转为内部退订标识
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
		//广西本地化，当ISMP传过来的是纯增值捆绑销售品时,加载对应的增值产品
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
		if(resultCode.equals("0")) resultName = "成功";
		else resultName = "失败";
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
	 * 根据销售品ID获得其对应订单增值产品信息
	 * @param prodOfferIds
	 *            销售品ID数组
	 * @param actionType 增值产品动作
	 * @return HashMap key--销售品ID,value--与此销售品ID对应订单增值产品列表
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
		// 把销售品下面的所有产品加进来
		for (Iterator iterator = prodOfferVOList.iterator(); iterator.hasNext();) {
			ProdOffVO prodOffVO = (ProdOffVO) iterator.next();
			vProductIds = DcSystemParamManager.getInstance().getVproductIdsByOfferIdWithAllState(prodOffVO.getProdOffId());
			subVProducts = new ArrayList();
			if(vProductIds!=null){
				//把增值产品转换成订单增值产品加进来，同时,新增订单增值产品里的订购关系实例标识
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
