package com.ztesoft.vsop.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.AuthenticationMain;
import com.ztesoft.vsop.order.vo.ProductOfferInfo;
import com.ztesoft.vsop.order.vo.VSubProdInfo;
import com.ztesoft.vsop.order.vo.request.SubscribeToVSOPRequest;
import com.ztesoft.vsop.order.vo.response.SubscribeToVSOPResp;

public class SimpleOrderVerify implements IOrderVeriFy {
	private static Logger logger = Logger.getLogger(SimpleOrderVerify.class);
	
	public boolean auth(SubscribeToVSOPRequest order, SubscribeToVSOPResp resp) throws Exception {
		long s = System.currentTimeMillis();
	    List incrLst = null;
	    if(OrderConstant.orderTypeOfAll.equals(order.getActionType())){
	    	incrLst = order.getVproductList();
	    }else{
	    	incrLst = createIncrProduct(order.getProductOfferInfo());
	    }
	    
	    HashMap productMap = new HashMap();
	    productMap.put("SPProdInfo", incrLst); //增值产品
	    productMap.put("ProductNo", order.getProductNo());
	    productMap.put("ProductId", order.getProdSpecCode());
	    productMap.put("LanId", order.getLanCode());
	    productMap.put("UserState", order.getUserState());
	    productMap.put("PayMode", order.getPayMode());
	    productMap.put("ProdInstId", order.getProdInstId());
	    ArrayList productLst = new ArrayList();
	    productLst.add(productMap);
	    List result = null;
	    result = AuthenticationMain.process(productLst, order.getSystemId());
	    Map resultMap = (Map)result.get(0);
	    boolean bVerify = false;
	    if(resultMap != null){
	    	String resultCode = (String) resultMap.get("ResultCode");
	    	String resultDesc = (String) resultMap.get("ResultDesc");
	    	resp.setResultDesc(resultDesc);
	    	List ppofferList =order.getPprodOfferList();
	    	if(ppofferList != null){
	    		for (Iterator ppOfferItr = ppofferList.iterator(); ppOfferItr
	    		.hasNext();) {
	    			Map tmp = (Map) ppOfferItr.next();
	    			Map respMap = new HashMap();
	    			respMap.put("OPResult", "-1");
	    			respMap.put("OPDesc", "增值产品"+(String)tmp.get("PRODUCT_NAME")+"捆绑了销售品"+(String)tmp.get("PPROD_OFFER_NAME"));
	    			respMap.put("ProductOfferID", (String)tmp.get("ProductOfferId"));
	    			resp.getResultInfo().add(respMap);
	    		}
	    	}
	    	//全部退订需要特殊处理
	    	if(OrderConstant.orderTypeOfAll.equals(order.getActionType())){
//	    		if("0".equals(resultCode)){  // 退订所有 把部分成功鉴权的退订，返回成功鉴权，把成功鉴权的增值产品退订掉
	    			List lst = (List)resultMap.get("lst");
	    			Map failVproductIdMap = new HashMap();
	    			if(lst != null){ 
	    				for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
	    					Map resultDetMap = (Map) iterator.next();
	    					String ret = (String)resultDetMap.get("Result");
	    					String prodNo = (String)resultDetMap.get("ProductNo");
	    					String SPProdSpecId = (String)resultDetMap.get("SPProdSpecId");
	    					if(!"0".equals(ret)){ //保存所有未鉴权成功的增值产品id
	    						failVproductIdMap.put(SPProdSpecId, "true");
	    						String failNote = (String)resultDetMap.get("FailureNote");
	    						Map respMap = new HashMap();
	    						respMap.put("OPResult", ret);
	    						respMap.put("OPDesc", failNote);
	    						respMap.put("ProductOfferID", findProductOfferIdFromVproducts(order.getVproductList(),SPProdSpecId));
	    						resp.getResultInfo().add(respMap);
	    					}
	    				}
	    			}
	    			List vprodList = order.getVproductList();
	    			List VSubProdInfoList = new ArrayList();
	    			for (Iterator vprodItr = vprodList.iterator(); vprodItr
							.hasNext();) {
						Map tmp = (Map) vprodItr.next();
						String vproductId = (String)tmp.get("SPProdSpecID");
						String ProductOfferId = (String)tmp.get("ProductOfferId");
						String ORDER_RELATION_ID = (String)tmp.get("ORDER_RELATION_ID");
						String offerNbr = (String)tmp.get("OfferNbr");
						String productNbr = (String)tmp.get("ProductNbr");
						String ProductOfferType = (String)tmp.get("ProductOfferType");
						String failVproduct = (String)failVproductIdMap.get(vproductId);
						if(null != failVproduct && !"".equals(failVproduct)){
							continue;
						}else{
							VSubProdInfo vprod = new VSubProdInfo();
							vprod.setActionType(OrderConstant.orderTypeOfAll);
							vprod.setVProductID(vproductId);
							vprod.setVproductInstId(ORDER_RELATION_ID);
							vprod.setVproductNbr(productNbr);
							vprod.setProductOfferId(ProductOfferId);
							vprod.setProductOfferNbr(offerNbr);
							vprod.setProductOfferType(ProductOfferType);
							VSubProdInfoList.add(vprod);
						}
					}
	    			List productOfferInfoList = order.getProductOfferInfo();
	    			if(productOfferInfoList == null) {
	    				productOfferInfoList = new ArrayList();
	    				order.setProductOfferInfo(productOfferInfoList);
	    			}
	    			ProductOfferInfo prodOfferInfo = new ProductOfferInfo();
	    			prodOfferInfo.setActionType(OrderConstant.orderTypeOfAll);
	    			prodOfferInfo.setProdSpecCode(order.getProdSpecCode());
	    			prodOfferInfo.setProductNo(order.getProductNo());
	    			prodOfferInfo.setVSubProdInfoList(VSubProdInfoList);
	    			productOfferInfoList.add(prodOfferInfo);
//	    		}
	    		resp.setResultCode(SubscribeToVSOPResp.RESULT_SUCCESS);
    			resp.setStreamingNo(order.getStreamingNo());
	    		bVerify = true;
	    	}else{ //订购、退订
	    		if("0".equals(resultCode)){
	    			resp.setResultCode(SubscribeToVSOPResp.RESULT_SUCCESS);
	    			resp.setStreamingNo(order.getStreamingNo());
	    			bVerify = true;
	    		}else{
	    			List lst = (List)resultMap.get("lst");
	    			if(lst != null)
	    				for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
	    					Map resultDetMap = (Map) iterator.next();
	    					String ret = (String)resultDetMap.get("Result");
	    					String prodNo = (String)resultDetMap.get("ProductNo");
	    					String SPProdSpecId = (String)resultDetMap.get("SPProdSpecId");
//							String prodName = (String)resultDetMap.get("SPProdSpecName");
	    					String failNote = (String)resultDetMap.get("FailureNote");
	    					Map respMap = new HashMap();
	    					respMap.put("OPResult", ret);
	    					respMap.put("OPDesc", failNote);
							respMap.put("ProductOfferID", findProductOfferIdFromOrder(order,SPProdSpecId));
	    					resp.getResultInfo().add(respMap);
	    				}
	    			bVerify = false;
	    		}
	    		
	    	}
	    }
	    logger.info("auth cost " + (System.currentTimeMillis() - s));
		return bVerify;
	}
	private String findProductOfferIdFromVproducts(List vproductList,
			String prodSpecId) {
		String wanted = null;
		for (Iterator iterator = vproductList.iterator(); iterator.hasNext();) {
			Map vprodMap = (Map) iterator.next();
			String SPProdSpecID = (String)vprodMap.get("SPProdSpecID");
			String prodOfferId = (String)vprodMap.get("ProductOfferId");
			if(SPProdSpecID.equals(prodSpecId)) wanted = prodOfferId;
			if(wanted != null) break;
		}
		return (wanted != null) ? wanted : "";
	}
	private String findProductOfferIdFromOrder(SubscribeToVSOPRequest order,
			String prodSpecId) {
		List productOfferList = order.getProductOfferInfo();
		String wantedProdOfferId = null;
		for (Iterator prodOfferItr = productOfferList.iterator(); prodOfferItr
				.hasNext();) {
			ProductOfferInfo productOfferInfo = (ProductOfferInfo) prodOfferItr.next();
			String prodOfferId = productOfferInfo.getProductOfferID();
			List vSubProdInfoList = productOfferInfo.getVSubProdInfoList();
			for (Iterator vsubProditr = vSubProdInfoList.iterator(); vsubProditr
					.hasNext();) {
				VSubProdInfo name = (VSubProdInfo) vsubProditr.next();
				if(prodSpecId.equals(name.getVProductID())) {
					wantedProdOfferId = prodOfferId;
					break;
				}
			}
			if(wantedProdOfferId != null) break;
		}
		
		return (wantedProdOfferId != null) ? wantedProdOfferId:"";
	}
	/**
	 * 增值产品
	 * @param subProduct 
	 * @return
	 */
	private ArrayList createIncrProduct(List subProduct) {
		ArrayList incrLst = new ArrayList();
		for (Iterator iterator = subProduct.iterator(); iterator.hasNext();) {
			ProductOfferInfo pOfferInfo = (ProductOfferInfo)iterator.next();
			List vproductList = pOfferInfo.getVSubProdInfoList();
			for (Iterator vprodItr = vproductList.iterator(); vprodItr
					.hasNext();) {
				VSubProdInfo subprod = (VSubProdInfo) vprodItr.next();
				HashMap spProdMap = new HashMap();
				spProdMap.put("SPProdSpecID", subprod.getVProductID());
				spProdMap.put("ActionType", subprod.getActionType());
				spProdMap.put("ProductOfferId", subprod.getProductOfferId());
				spProdMap.put("ProductOfferType", subprod.getProductOfferType());
				incrLst.add(spProdMap);
			}
		}
		return incrLst;
	}

}
