package com.ztesoft.vsop.engine.service.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.OrderEngine;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.vo.ProdVO;
import com.chinatelecom.ismp.crm.rsp.Response;
import com.chinatelecom.ismp.crm.req.SubscriptionSyncReq;


/**
 * 江西——订购关系同步(IMSP-VSOP)
 * @author wendm
 *
 */
public class SubsInstSynFromJXISMPAccessService extends CommonAccessService implements IAccess{
	
	public Map concreteInOpertion(Map in) throws Exception {
		CustomerOrder order = new CustomerOrder();
		SubscriptionSyncReq requestObj = (SubscriptionSyncReq) in.get("accessInObject");
		order = parseObj(requestObj);
		in.put("busiObject", order);
		in.put("serviceCode", String.valueOf(OrderEngine.SERVICE_ORSYNFROMISMP30));
		return in;
	}

	public Map concreteOutOpertion(Map in) throws Exception {
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		String streamingNo =order.getCustSoNumber();
		String resultCode = (String) in.get("resultCode");
		//List resultInfo = (List) in.get("retBusiObject");
		Response responseObj = getResult(resultCode,streamingNo);
		in.put("accessOutObject", responseObj);
		return in;
	}
	public CustomerOrder parseObj(SubscriptionSyncReq reqObj) throws Exception{
		CustomerOrder order = new CustomerOrder();
		long t1 = System.currentTimeMillis();

		String StreamingNo = reqObj.getStreamingNo();
		//取当前时间
		String TimeStamp=DAOUtils.getFormatedDate();
		//接口没传这个参数，默认江西ISMP－M的系统编码为204
		String SystemId = "204";
		String UserID = reqObj.getUserID();
		String userIDType =Integer.toString(reqObj.getUserIDType());

		String offerType = "";
		String ProductID = "";
		String prodOfferId ="";
		String oldProdOfferId="";

		String ProductNbr = reqObj.getProductID();	//产品标识
		if(ProductNbr!=null&&!ProductNbr.equals("")){
			ProductID=DcSystemParamManager.getInstance().getProductIdByNbr(ProductNbr);
		}
		
		String PackageID = reqObj.getPackageID();//捆绑销售品标识
		if(PackageID!=null&&!PackageID.equals("")){
			offerType = OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID;
			prodOfferId =DcSystemParamManager.getInstance().getProdOfferIdByNbr(PackageID);
		}
		String OldpackageID =  reqObj.getOldPackageID();//OPType为6替换时，该字段有效
		if(OldpackageID!=null&&!OldpackageID.equals("")){
			OldpackageID = DcSystemParamManager.getInstance().getProdOfferIdByNbr(OldpackageID);
		}
		
		String oldProductNbr = reqObj.getOldProductID();//OPType为6替换时，该字段有效
		String oldProductID = "";
		if(oldProductNbr!=null&&!oldProductNbr.equals("")){
			oldProductID =DcSystemParamManager.getInstance().getProductIdByNbr(oldProductNbr);
		}
		
		/*		
		 * 
		 * 
		String PProductOfferID = XMLUtils.getSingleTagValue(xmlStr,"PProductOfferID");
		if(PProductOfferID!=null&&!PProductOfferID.equals("")){
			offerType = OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID;
			PProductOfferID = DcSystemParamManager.getInstance().getProdOfferIdByNbr(PProductOfferID);
		}
		String OldPProductOfferID = XMLUtils.getSingleTagValue(xmlStr,"OldPProductOfferID");
		if(OldPProductOfferID!=null&&!OldPProductOfferID.equals("")){
			OldPProductOfferID = DcSystemParamManager.getInstance().getProdOfferIdByNbr(OldPProductOfferID);
		}
		*/
		
		if("".equals(offerType)){//如果不是传统+增值捆绑， 也不是捆绑套餐，直接取产品对应的销售品
			offerType = OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID;
			//ProdVO prodVo = DcSystemParamManager.getInstance().getProductVOByid(ProductID);
			//prodOfferId =prodVo.getProdOffId();
			prodOfferId= DcSystemParamManager.getInstance().getofferIdByProductId(ProductID);
		}
		
		String OPType =Integer.toString(reqObj.getOPType());
		
		String EffDate = "";//暂时送空
		String ExpDate = "";//暂时送空
		ProdInstVO prodInst = getProdInstByUserType(userIDType,UserID);
		order.setProdInst(prodInst);
		
		order.setCustSoNumber(StreamingNo);
		order.setAccNbr(UserID);
		order.setProdInstId(prodInst.getProdInstId());
		order.setProdId(prodInst.getProdId());
		order.setOrderChn(SystemId);
		order.setLanId(prodInst.getLanId());
		order.setStatus(OrderConstant.orderStateOfCreated);
		String orderType = "0";
		if("3".equals(OPType)){
			orderType="1"; //如果是传过来的时候退订的话，就转为内部退订标识
		}
		order.setCustOrderType(orderType);
		order.setChnId(SystemId);
		order.setCustOrderType(orderType);
		ProductOfferInfo productOffer = new ProductOfferInfo();
		productOffer.setEffDate(EffDate);
		productOffer.setExpDate(ExpDate);
		productOffer.setActioType(orderType);
		productOffer.setOfferId(prodOfferId);
		productOffer.setOfferType(offerType);
		productOffer.setOldOfferId(oldProdOfferId);
		
		VproductInfo product = new VproductInfo();
		product.setVProductId(ProductID);
		product.setOfferId(prodOfferId);
		product.setEffDate(EffDate);
		product.setExpDate(ExpDate);
		product.setOldVProductId(oldProductID);
		product.setActionType(orderType);
		List vproductInfoList = new ArrayList();
		vproductInfoList.add(product);
		productOffer.setVproductInfoList(vproductInfoList);
		
		List productOfferInfoList = new ArrayList();
		productOfferInfoList.add(productOffer);
		order.setProductOfferInfoList(productOfferInfoList);
		
		long t2 = System.currentTimeMillis();
		
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
	public static Response getResult(String resultCode,String streamingNo){
		Response respObj = new Response();
		StringBuffer bf = new StringBuffer("");
		int returnCode = 0;
		//0成功，非0不成功
		respObj.setStreamingNo(streamingNo);
		respObj.setResultCode( Integer.parseInt(resultCode));
		return respObj;
	}	
}
