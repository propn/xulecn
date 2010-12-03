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
 * ��������������ϵͬ��(IMSP-VSOP)
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
		//ȡ��ǰʱ��
		String TimeStamp=DAOUtils.getFormatedDate();
		//�ӿ�û�����������Ĭ�Ͻ���ISMP��M��ϵͳ����Ϊ204
		String SystemId = "204";
		String UserID = reqObj.getUserID();
		String userIDType =Integer.toString(reqObj.getUserIDType());

		String offerType = "";
		String ProductID = "";
		String prodOfferId ="";
		String oldProdOfferId="";

		String ProductNbr = reqObj.getProductID();	//��Ʒ��ʶ
		if(ProductNbr!=null&&!ProductNbr.equals("")){
			ProductID=DcSystemParamManager.getInstance().getProductIdByNbr(ProductNbr);
		}
		
		String PackageID = reqObj.getPackageID();//��������Ʒ��ʶ
		if(PackageID!=null&&!PackageID.equals("")){
			offerType = OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID;
			prodOfferId =DcSystemParamManager.getInstance().getProdOfferIdByNbr(PackageID);
		}
		String OldpackageID =  reqObj.getOldPackageID();//OPTypeΪ6�滻ʱ�����ֶ���Ч
		if(OldpackageID!=null&&!OldpackageID.equals("")){
			OldpackageID = DcSystemParamManager.getInstance().getProdOfferIdByNbr(OldpackageID);
		}
		
		String oldProductNbr = reqObj.getOldProductID();//OPTypeΪ6�滻ʱ�����ֶ���Ч
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
		
		if("".equals(offerType)){//������Ǵ�ͳ+��ֵ���� Ҳ���������ײͣ�ֱ��ȡ��Ʒ��Ӧ������Ʒ
			offerType = OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID;
			//ProdVO prodVo = DcSystemParamManager.getInstance().getProductVOByid(ProductID);
			//prodOfferId =prodVo.getProdOffId();
			prodOfferId= DcSystemParamManager.getInstance().getofferIdByProductId(ProductID);
		}
		
		String OPType =Integer.toString(reqObj.getOPType());
		
		String EffDate = "";//��ʱ�Ϳ�
		String ExpDate = "";//��ʱ�Ϳ�
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
			orderType="1"; //����Ǵ�������ʱ���˶��Ļ�����תΪ�ڲ��˶���ʶ
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
		//0�ɹ�����0���ɹ�
		respObj.setStreamingNo(streamingNo);
		respObj.setResultCode( Integer.parseInt(resultCode));
		return respObj;
	}	
}
