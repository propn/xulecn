package com.ztesoft.vsop.engine.service.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.ztesoft.vsop.web.vo.ProdVO;

/**
 * 订购关系同步(X平台-VSOP)
 * @author wendm
 *SubsInstSynSVSkeleton
 *SubsInstSynSVAccessService
 */
public class SubsInstSynSVAccessService extends CommonAccessService implements IAccess{
	
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
		CustomerOrder order = new CustomerOrder();
		long t1 = System.currentTimeMillis();

		String StreamingNo = XMLUtils.getSingleTagValue(xmlStr,"StreamingNo");//StreamingNo
		String TimeStamp = XMLUtils.getSingleTagValue(xmlStr,"TimeStamp");//TimeStamp
		String SystemId = XMLUtils.getSingleTagValue(xmlStr,"SystemId");//SystemId
		
		String ProductNo = XMLUtils.getSingleTagValue(xmlStr,"ProductNo");//ProductNo
		String ProdSpecCode = XMLUtils.getSingleTagValue(xmlStr,"ProdSpecCode");//ProdSpecCode
		String ProductNbr = XMLUtils.getSingleTagValue(xmlStr,"VproductID");//VproductID
		String offerType = XMLUtils.getSingleTagValue(xmlStr,"ProductOfferType");//ProductOfferType;
		String prodOfferNbr =XMLUtils.getSingleTagValue(xmlStr,"ProductOfferID");//ProductOfferID;
		String Status =XMLUtils.getSingleTagValue(xmlStr,"Status");//Status;
		String ProductID = "";	
		if(ProductNbr!=null&&!ProductNbr.equals("")){
			ProductID=DcSystemParamManager.getInstance().getProductIdByNbr(ProductNbr);
		}
		String prodOfferId = "";
		if(prodOfferNbr!=null&&!prodOfferNbr.equals("")){
			prodOfferId =DcSystemParamManager.getInstance().getProdOfferIdByNbr(prodOfferNbr);
		}		
		
		if("0".equals(offerType)||"".equals(offerType)){//如果不是传统+增值捆绑， 也不是捆绑套餐，直接取产品对应的销售品
			offerType = OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID;
			if(ProductID!=null&&!ProductID.equals("")){
				prodOfferId= DcSystemParamManager.getInstance().getofferIdByProductId(ProductID);
			}
			
		}
		
		String OPType = "";
		if("6".equals(Status)){
			OPType = "3";
		}
		else{
			OPType = "0";
		}
		
		String EffDate = XMLUtils.getSingleTagValue(xmlStr,"EffDate");//EffDate
		String ExpDate = XMLUtils.getSingleTagValue(xmlStr,"ExpDate");//ExpDate
		String ChannelPlayerID = XMLUtils.getSingleTagValue(xmlStr,"ChannelPlayerID");
		order.setProdId(ProdSpecCode);
		order.setOrderSystem(SystemId);
		order.setAccNbr(ProductNo);
		//江西本地化处理
		//panshaohua 2010-8-9 根据接口的主产品编码，得到产品id
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			AssemCoreEntityServices aAssemCoreEntityServices=new AssemCoreEntityServices();
			aAssemCoreEntityServices.resetProdInstInfo(order);
		}else {
			ProdInstVO prodInst = getProdInstByProcProd(ProdSpecCode,ProductNo);
			order.setProdInst(prodInst);
			order.setProdInstId(prodInst.getProdInstId());
			order.setCustSoNumber(StreamingNo);
			order.setProdId(prodInst.getProdId()); 
		}
		order.setStatus(OrderConstant.orderStateOfCreated);
		
		String orderType = "0";
		if("3".equals(OPType)){
			orderType="1"; //如果是传过来的时候退订的话，就转为内部退订标识
		}
		order.setCustOrderType(orderType);
		order.setChnId(ChannelPlayerID);
		
		ProductOfferInfo productOffer = new ProductOfferInfo();
		productOffer.setEffDate(EffDate);
		productOffer.setExpDate(ExpDate);
		productOffer.setActioType(orderType);
		productOffer.setOfferId(prodOfferId);
		productOffer.setOfferType(offerType);
		
		VproductInfo product = new VproductInfo();
		product.setVProductId(ProductID);
		product.setOfferId(prodOfferId);
		product.setEffDate(EffDate);
		product.setExpDate(ExpDate);
		SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
		String productInstID = aSequenceManageDAOImpl.getNextSequence("SEQ_ORDER_RELATION_ID");
		product.setVProductInstID(productInstID);
		product.setActionType(orderType);
		List vproductInfoList = new ArrayList();
		vproductInfoList.add(product);
		
		productOffer.setVproductInfoList(vproductInfoList);
		productOffer.setOfferType(offerType);
		productOffer.setOfferId(DcSystemParamManager.getInstance().getProdOfferIdByNbr(prodOfferNbr));
		productOffer.setOfferNbr(prodOfferNbr);
		List productOfferInfoList = new ArrayList();
		productOfferInfoList.add(productOffer);
		order.setProductOfferInfoList(productOfferInfoList);
		
		return order;
	}

	private ProdInstVO getProdInstByProcProd(String prodTypeStr,String accNbr) throws Exception{
		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
		ProdInstVO prodInst = prodInstDao.qryProdInstByAccNbrAndProductIds(accNbr, prodTypeStr);
		return prodInst;
	}
	
	public static String getResult(String streamingNo,String resultCode){
		String retXml = "";
		StringBuffer bf = new StringBuffer("");
		String resultName = "";
		if(resultCode.equals("0")) resultName = "成功";
		else resultName = "失败";
		bf.append("<Response>");
		bf.append("<SubsInstSynToVSOPResp>")
		.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
		.append("<ResultCode>").append(resultCode).append("</ResultCode>")
		.append("<ResultDesc>").append(resultName).append("</ResultDesc>")
		.append("</SubsInstSynToVSOPResp>");
		bf.append("</Response>");
		retXml = bf.toString();
		return retXml;
	}	
}
