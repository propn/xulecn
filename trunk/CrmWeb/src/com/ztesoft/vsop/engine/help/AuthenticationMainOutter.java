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
 * ������Ȩ�����ⲿ������  ��ֲSimpleOrderVerify
 * @author cooltan
 *
 */
public class AuthenticationMainOutter{
	private static Logger logger = Logger.getLogger(AuthenticationMainOutter.class);
	/**
	 * ��Ȩ���� ׼������1
	 * @param order
	 * @param resp key:StreamingNo ResultCode ResultDesc resultInfo=List<Map<ProductOfferID,OPResult,OPDesc,VProductName,VProductID,ProductNo>>
	 * @return   ResultCode=0��Ȩͨ�� ResultDesc����Ȩ���� resultInfo����Ȩ��ͨ����ֵ��Ʒ�����������ֵ��Ʒ�� ÿ����ֵ��Ʒ�����Ȩ������resultinfo��
	 * @throws Exception
	 */
	public boolean auth(CustomerOrder order, Map resp) throws Exception {
		long s = System.currentTimeMillis();
		List resultInfoList=new ArrayList();
		String finalStreamNo=null;
		String finalResultCode=null;
		
		//1.ͨ����������Ʒ��װ������Ȩ��ֵ��Ʒ�б����
	    List incrLst = null;
	    if(OrderConstant.orderTypeOfAll.equals(order.getCustOrderType())){
	    	incrLst = this.createIncrProduct(order.getProductOfferInfoList());
	    }else{
	    	incrLst = this.createIncrProduct(order.getProductOfferInfoList());
	    }
	    //2.��װ������Ȩ��Ʒʵ������
	    HashMap productMap = new HashMap();
	    productMap.put("SPProdInfo", incrLst); //��ֵ��Ʒ
	    productMap.put("ProductNo", order.getAccNbr());//����
	    productMap.put("ProductId", order.getProdId());//��Ʒ����
	    productMap.put("LanId", order.getLanId());//����������
	    productMap.put("UserState", order.getProdInst().getStateCd());//�û�״̬
	    productMap.put("PayMode", order.getProdInst().getPaymentModeCd());//�û���������
	    productMap.put("ProdInstId", order.getProdInstId());//��Ʒʵ����ʶ
	    ArrayList productLst = new ArrayList();
	    productLst.add(productMap);
	    //3.���ü�Ȩ������
	    List result = null;
	    result = AuthenticationMainInner.getInstance().process(productLst, order.getOrderSystem());
	    //4.�����ؽ��
	    Map resultMap = (Map)result.get(0);
	    boolean bVerify = false;
	    if(resultMap != null){
	    	String resultCode = (String) resultMap.get("ResultCode");
	    	String resultDesc = (String) resultMap.get("ResultDesc");
	    	finalResultCode=resultCode;
	    	finalStreamNo=order.getCustSoNumber();
	    	resp.put("ResultCode", resultCode);
	    	resp.put("ResultDesc", resultDesc);
	    	//4.1ȫ���˶���Ҫ���⴦��
	    	if(OrderConstant.orderTypeOfAll.equals(order.getCustOrderType())){
				// �Ѳ��ֳɹ���Ȩ���˶������سɹ���Ȩ���ѳɹ���Ȩ����ֵ��Ʒ�˶���
	    		////4.1.0�˶�����Ĭ���ɵ����������𣬶��������������˶���ͳ����ֵ����Ʒ����Ҫ�޳���һ��������Ʒ���ͼ�Ȩ
	    		this.cancelAllRemoveOfferTypeOf2(order, resultInfoList);
	    		//4.1.1��ȡʧ�ܵ���ֵ��Ʒ��ʶMap
				List lst = (List) resultMap.get("lst");
				Map failVproductIdMap = new HashMap();//��Ȩʧ�ܵ���ֵ��Ʒ��ʶ
				if (lst != null) {
					for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
						Map resultDetMap = (Map) iterator.next();
						String ret = (String) resultDetMap.get("Result");
						String prodNo = (String) resultDetMap.get("ProductNo");
						String SPProdSpecId = (String) resultDetMap.get("SPProdSpecId");
						String SPProdSpecName= (String) resultDetMap.get("SPProdSpecName");
						if (!"0".equals(ret)) { // ��������δ��Ȩ�ɹ�����ֵ��Ʒid
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
				//4.1.2����Ȩ�ɹ�����ֵ��Ʒ���õ�order�С�
				this.fixProductOfferInfoList(order, failVproductIdMap);
				finalResultCode = "0";
				finalStreamNo = order.getCustSoNumber();
				bVerify = true;
	    	}else{ // 4.2�������˶�
	    		if("0".equals(resultCode)){
	    			finalResultCode="0";
	    			finalStreamNo=order.getCustSoNumber();
	    			bVerify = true;
	    			/**
	    			 * �������滻������Ҫ���⴦��
	    			 * �ο�Rule8.java
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
	 * ͨ����������Ʒ��װ������Ȩ��ֵ��Ʒ�б����
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
				spProdMap.put("SPProdSpecID", subprod.getVProductId());//��ֵ��Ʒ��ʶ
				spProdMap.put("ActionType", subprod.getActionType());//��ֵ��Ʒ����
				spProdMap.put("ProductOfferId", subprod.getOfferId());//��ֵ��Ʒ��Ӧ����Ʒ��ʶ
				spProdMap.put("ProductOfferType", subprod.getOfferType());//��ֵ��Ʒ��Ӧ����Ʒ��ʶ
				spProdMap.put("VProductNbr", subprod.getVProductNbr());//��ֵ��Ʒ�������  --add 20101009������rule12ʹ��
				incrLst.add(spProdMap);
			}
		}
		return incrLst;
	}
	/**
	 * ȫ���˶�ʱ���������м�Ȩ��ͨ��������Ʒ����ֵ��Ʒ�޳�����ֻ�˶���Ȩͨ��������Ʒ��ֵ��Ʒ
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
				if (null != failVproduct && !"".equals(failVproduct)) {//�޳���ǰ����Ʒ��Ȩ��ͨ������ֵ��Ʒ
					vSubProdInfoList.remove(i);
				} else {
					//do nothing
				}
			}
			if(vSubProdInfoList==null||vSubProdInfoList.size()==0){//�޳�������ֵ��Ʒ����Ȩ��ͨ��������Ʒ
				productOfferList.remove(j);
			}
		}
	}
	/**
	 * �˶����У��޳���ͳ����ֵ����Ʒ��ƴװ��������
	 * @param order
	 * @param resultInfoList
	 */
	private void cancelAllRemoveOfferTypeOf2(CustomerOrder order,List resultInfoList){
    	//ȫ���˶����ɵ����������𣬲����˶����󣫴�ͳ��ֵ����Ʒ
		List productOfferList = order.getProductOfferInfoList();
		for (int j=0;j<productOfferList.size();j++ ) {
			ProductOfferInfo productOfferInfo = (ProductOfferInfo) productOfferList.get(j);
			String prodOfferId = productOfferInfo.getOfferId();
			List vSubProdInfoList = productOfferInfo.getVproductInfoList();
			String offerType=productOfferInfo.getOfferType();
			if (OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID.equals(offerType)) {//��ͳ+��ֵ
				for (int i = 0; i < vSubProdInfoList.size(); i++) {
					VproductInfo vproduct = (VproductInfo) vSubProdInfoList.get(i);
					Map respMap = new HashMap();
	    			respMap.put("OPResult", "-1");
	    			respMap.put("OPDesc", "��ֵ��Ʒ"
	    					+DcSystemParamManager.getInstance().getProductnameById(vproduct.getVProductId())
	    					+"�����Ż��ײ�"
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
