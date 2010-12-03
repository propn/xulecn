package com.ztesoft.vsop.engine.help;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * 订购鉴权主控外部处理类  移植SimpleOrderVerify
 * @author cooltan
 *
 */
public class AuthenticationMainOutter{
	private static Logger logger = Logger.getLogger(AuthenticationMainOutter.class);
	/**
	 * 鉴权主控 准备数据1
	 * @param order
	 * @param resp key:StreamingNo ResultCode ResultDesc resultInfo=List<Map<ProductOfferID,OPResult,OPDesc,VProductName,VProductID,ProductNo>>
	 * @return   ResultCode=0鉴权通过 ResultDesc＝鉴权描述 resultInfo＝鉴权不通过增值产品描述（多个增值产品－ 每个增值产品多个鉴权规则多个resultinfo）
	 * @throws Exception
	 */
	public boolean auth(CustomerOrder order, Map resp) throws Exception {
		long s = System.currentTimeMillis();
		List resultInfoList=new ArrayList();
		String finalStreamNo=null;
		String finalResultCode=null;
		
		//1.通过订单销售品封装订购鉴权增值产品列表对象
	    List incrLst = null;
	    if(OrderConstant.orderTypeOfAll.equals(order.getCustOrderType())){
	    	incrLst = this.createIncrProduct(order.getProductOfferInfoList());
	    }else{
	    	incrLst = this.createIncrProduct(order.getProductOfferInfoList());
	    }
	    //2.封装订购鉴权产品实例对象
	    HashMap productMap = new HashMap();
	    productMap.put("SPProdInfo", incrLst); //增值产品
	    productMap.put("ProductNo", order.getAccNbr());//号码
	    productMap.put("ProductId", order.getProdId());//产品类型
	    productMap.put("LanId", order.getLanId());//本地网编码
	    productMap.put("UserState", order.getProdInst().getStateCd());//用户状态
	    productMap.put("PayMode", order.getProdInst().getPaymentModeCd());//用户付费类型
	    productMap.put("ProdInstId", order.getProdInstId());//产品实例标识
	    ArrayList productLst = new ArrayList();
	    productLst.add(productMap);
	    //3.调用鉴权主控类
	    List result = null;
	    result = AuthenticationMainInner.getInstance().process(productLst, order.getOrderSystem());
	    //4.处理返回结果
	    Map resultMap = (Map)result.get(0);
	    boolean bVerify = false;
	    if(resultMap != null){
	    	String resultCode = (String) resultMap.get("ResultCode");
	    	String resultDesc = (String) resultMap.get("ResultDesc");
	    	finalResultCode=resultCode;
	    	finalStreamNo=order.getCustSoNumber();
	    	resp.put("ResultCode", resultCode);
	    	resp.put("ResultDesc", resultDesc);
	    	//4.1全部退订需要特殊处理
	    	if(OrderConstant.orderTypeOfAll.equals(order.getCustOrderType())){
				// 把部分成功鉴权的退订，返回成功鉴权，把成功鉴权的增值产品退订掉
	    		////4.1.0退订所有默认由电子渠道发起，而电子渠道不能退订传统＋增值销售品，需要剔除这一部分销售品不送鉴权
	    		this.cancelAllRemoveOfferTypeOf2(order, resultInfoList);
	    		//4.1.1获取失败的增值产品标识Map
				List lst = (List) resultMap.get("lst");
				Map failVproductIdMap = new HashMap();//鉴权失败的增值产品标识
				if (lst != null) {
					for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
						Map resultDetMap = (Map) iterator.next();
						String ret = (String) resultDetMap.get("Result");
						String prodNo = (String) resultDetMap.get("ProductNo");
						String SPProdSpecId = (String) resultDetMap.get("SPProdSpecId");
						String SPProdSpecName= (String) resultDetMap.get("SPProdSpecName");
						if (!"0".equals(ret)) { // 保存所有未鉴权成功的增值产品id
							failVproductIdMap.put(SPProdSpecId, "true");
							String failNote = (String) resultDetMap.get("FailureNote");
							Map respMap = new HashMap();
							respMap.put("ProductNo", prodNo);
							respMap.put("VProductID", SPProdSpecId);
							respMap.put("VProductName", SPProdSpecName);
							respMap.put("OPResult", ret);
							respMap.put("OPDesc", failNote);
							respMap.put("ProductOfferID",findProductOfferIdFromOrder(order,SPProdSpecId));
							resultInfoList.add(respMap);
						}
					}
				}
				//4.1.2将鉴权成功的增值产品设置到order中。
				this.fixProductOfferInfoList(order, failVproductIdMap);
				finalResultCode = "0";
				finalStreamNo = order.getCustSoNumber();
				bVerify = true;
	    	}else{ // 4.2订购、退订
	    		if("0".equals(resultCode)){
	    			finalResultCode="0";
	    			finalStreamNo=order.getCustSoNumber();
	    			bVerify = true;
	    			/**
	    			 * 江西的替换规则需要特殊处理
	    			 * 参考Rule8.java
	    			 */
	    			if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE))){
		    			List lst = (List)resultMap.get("lst");
		    			if(lst != null){
		    				for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
		    					Map resultDetMap = (Map) iterator.next();
		    					String ret = (String)resultDetMap.get("Result");
		    					if("-65".equals(ret)){
		    						String SPProdSpecId = (String)resultDetMap.get("SPProdSpecId");
		    						String oldSPProdSpecId = (String)resultDetMap.get("oldSPProdSpecId");
		    						List productOfferInfoList = order.getProductOfferInfoList();
		    						if(productOfferInfoList != null && productOfferInfoList.size()>0){
		    							for (Iterator iterator2 = productOfferInfoList.iterator(); iterator2.hasNext();) {
		    								ProductOfferInfo aProductOfferInfo=(ProductOfferInfo)iterator2.next();
		    								List vproductInfoList=aProductOfferInfo.getVproductInfoList();
		    								for (Iterator iterator3 = vproductInfoList.iterator(); iterator3.hasNext();) {
		    									VproductInfo vproduct = (VproductInfo) iterator3.next();
		    									if(SPProdSpecId != null && SPProdSpecId.equals(vproduct.getVProductId())){
		    										vproduct.setDbActionType(OrderConstant.VProductActionTypeOfReplace);
		    										vproduct.setOldVProductId(oldSPProdSpecId);
		    									}
		    								}
		    							}
		    						}
		    					}
		    				}
		    			}
	    			}
	    		}else{
	    			List lst = (List)resultMap.get("lst");
	    			if(lst != null)
	    				for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
	    					Map resultDetMap = (Map) iterator.next();
	    					String ret = (String)resultDetMap.get("Result");
	    					String prodNo = (String)resultDetMap.get("ProductNo");
	    					String SPProdSpecId = (String)resultDetMap.get("SPProdSpecId");
	    					String SPProdSpecName= (String) resultDetMap.get("SPProdSpecName");
	    					String failNote = (String)resultDetMap.get("FailureNote");
	    					Map respMap = new HashMap();
	    					respMap.put("ProductNo", prodNo);
	    					respMap.put("VProductID", SPProdSpecId);
							respMap.put("VProductName", SPProdSpecName);
	    					respMap.put("OPResult", ret);
	    					respMap.put("OPDesc", failNote);
							respMap.put("ProductOfferID", findProductOfferIdFromOrder(order,SPProdSpecId));
							resultInfoList.add(respMap);
	    				}
	    			bVerify = false;
	    		}
	    		
	    	}
	    }
	    resp.put("ResultCode", finalResultCode);
	    resp.put("StreamingNo", finalStreamNo);
	    resp.put("resultInfo", resultInfoList);
	    logger.info("auth cost " + (System.currentTimeMillis() - s));
		return bVerify;
	}
	private String findProductOfferIdFromVproducts(List vproductList,String prodSpecId) {
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
	private String findProductOfferIdFromOrder(CustomerOrder order,String prodSpecId) {
		List productOfferList = order.getProductOfferInfoList();
		String wantedProdOfferId = null;
		for (Iterator prodOfferItr = productOfferList.iterator(); prodOfferItr.hasNext();) {
			ProductOfferInfo productOfferInfo = (ProductOfferInfo) prodOfferItr.next();
			String prodOfferId = productOfferInfo.getOfferId();
			List vSubProdInfoList = productOfferInfo.getVproductInfoList();
			for (Iterator vsubProditr = vSubProdInfoList.iterator(); vsubProditr.hasNext();) {
				VproductInfo vproduct = (VproductInfo) vsubProditr.next();
				if(prodSpecId.equals(vproduct.getVProductId())) {
					wantedProdOfferId = prodOfferId;
					break;
				}
			}
			if(wantedProdOfferId != null) break;
		}
		
		return (wantedProdOfferId != null) ? wantedProdOfferId:"";
	}
	/**
	 * 通过订单销售品封装订购鉴权增值产品列表对象
	 * @param subProduct 
	 * @return
	 */
	private ArrayList createIncrProduct(List subProduct) {
		ArrayList incrLst = new ArrayList();
		for (Iterator iterator = subProduct.iterator(); iterator.hasNext();) {
			ProductOfferInfo pOfferInfo = (ProductOfferInfo)iterator.next();
			List vproductList = pOfferInfo.getVproductInfoList();
			for (Iterator vprodItr = vproductList.iterator(); vprodItr.hasNext();) {
				VproductInfo subprod = (VproductInfo) vprodItr.next();
				HashMap spProdMap = new HashMap();
				spProdMap.put("SPProdSpecID", subprod.getVProductId());//增值产品标识
				spProdMap.put("ActionType", subprod.getActionType());//增值产品动作
				spProdMap.put("ProductOfferId", subprod.getOfferId());//增值产品对应销售品标识
				spProdMap.put("ProductOfferType", subprod.getOfferType());//增值产品对应销售品标识
				spProdMap.put("VProductNbr", subprod.getVProductNbr());//增值产品对外编码  --add 20101009新增，rule12使用
				incrLst.add(spProdMap);
			}
		}
		return incrLst;
	}
	/**
	 * 全部退订时，将订单中鉴权不通过的销售品、增值产品剔除掉，只退订鉴权通过的销售品增值产品
	 * @param order
	 * @param failVproductIdMap
	 */
	private void fixProductOfferInfoList(CustomerOrder order,Map failVproductIdMap){
		List productOfferList = order.getProductOfferInfoList();
		for (int j=0;j<productOfferList.size();j++ ) {
			ProductOfferInfo productOfferInfo = (ProductOfferInfo) productOfferList.get(j);
			String prodOfferId = productOfferInfo.getOfferId();
			List vSubProdInfoList = productOfferInfo.getVproductInfoList();
			for (int i=0;i<vSubProdInfoList.size();i++ ) {
				VproductInfo vproduct = (VproductInfo) vSubProdInfoList.get(i);
				String failVproduct = (String) failVproductIdMap.get(vproduct.getVProductId());
				if (null != failVproduct && !"".equals(failVproduct)) {//剔除当前销售品鉴权不通过的增值产品
					vSubProdInfoList.remove(i);
				} else {
					//do nothing
				}
			}
			if(vSubProdInfoList==null||vSubProdInfoList.size()==0){//剔除所有增值产品都鉴权不通过的销售品
				productOfferList.remove(j);
			}
		}
	}
	/**
	 * 退订所有：剔除传统＋增值销售品；拼装返回描述
	 * @param order
	 * @param resultInfoList
	 */
	private void cancelAllRemoveOfferTypeOf2(CustomerOrder order,List resultInfoList){
    	//全部退订都由电子渠道发起，不能退订捆绑＋传统增值销售品
		List productOfferList = order.getProductOfferInfoList();
		for (int j=0;j<productOfferList.size();j++ ) {
			ProductOfferInfo productOfferInfo = (ProductOfferInfo) productOfferList.get(j);
			String prodOfferId = productOfferInfo.getOfferId();
			List vSubProdInfoList = productOfferInfo.getVproductInfoList();
			String offerType=productOfferInfo.getOfferType();
			if (OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID.equals(offerType)) {//传统+增值
				for (int i = 0; i < vSubProdInfoList.size(); i++) {
					VproductInfo vproduct = (VproductInfo) vSubProdInfoList.get(i);
					Map respMap = new HashMap();
	    			respMap.put("OPResult", "-1");
	    			respMap.put("OPDesc", "增值产品"
	    					+DcSystemParamManager.getInstance().getProductnameById(vproduct.getVProductId())
	    					+"捆绑优惠套餐"
	    					+DcSystemParamManager.getInstance().getProdOfferNameById(prodOfferId));
	    			respMap.put("ProductOfferID", prodOfferId);
	    			respMap.put("ProductNo", order.getAccNbr());
					respMap.put("VProductID", vproduct.getVProductId());
					respMap.put("VProductName", DcSystemParamManager.getInstance().getProductnameById(vproduct.getVProductId()));
	    			resultInfoList.add(respMap);
				}
				productOfferList.remove(j);
				j--;
			}
			
		}
	}

}
