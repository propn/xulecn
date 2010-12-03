package com.ztesoft.vsop.engine.service.access;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;

import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.help.AssemCoreEntityServices;
import com.ztesoft.vsop.engine.help.VproductHelp;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.OrderRelationVO;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.order.SystemCode;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.exception.NoVproductException;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * 订购申请接口接入组件
 * @author panshaohua
 *
 */
public class SubscribeReqSVAccessService extends CommonAccessService implements IAccess{
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//省份代码
	public Map concreteInOpertion(Map in) throws Exception {
		String requestXML = (String) in.get("accessInObject");
		CustomerOrder order = new CustomerOrder();
		in.put("busiObject", order);
		loadFromXML(requestXML,order);
		String actionType = order.getCustOrderType();
		String serviceCode = "0";
		if("1".equals(actionType)){
			serviceCode ="1";
		}else if("2".equals(actionType)){
			serviceCode ="2";
		}
		in.put("serviceCode",serviceCode );
		//*******
		//广西本地化，全部退订中,(传统+增值)销售品退订失败标志,
		//successAll 在流程中，表示此流程不是全部成功，可能是部分成功，可能是全部失败,受业务规则影响
		//successALLReson表示非全部成功返回的原因
		//*******
		if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode) && !order.isSuccessAll()){
			in.put("successAll", "1");
			in.put("successAllReson", order.getSuccessAllReason());
		}
		return in;
	}

	public Map concreteOutOpertion(Map in) throws Exception {
		CustomerOrder order =(CustomerOrder) in.get("busiObject");
		String streamingNo =order.getCustSoNumber();
		String resultCode = (String) in.get("resultCode");
		String resultMsg = (String) in.get("resultMsg");
		List resultInfo = (List) in.get("retBusiObject");
		//*******
		//广西本地化，全部退订中,(传统+增值)销售品退订失败标志, 此时返回resultCode非0
		//*******
		if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)
				&& null!=in.get("successAll")&& "1".equals(in.get("successAll"))){
			resultCode=(String) in.get("successAll");
			List successAllReason=(List) in.get("successAllReson");
			List resultInfoGX= new ArrayList();
			if(resultInfo!=null){
				resultInfoGX.addAll(resultInfo);
			}
			if(successAllReason!=null){
				resultInfoGX.addAll(successAllReason);
			}
			resultInfo=resultInfoGX;
			resultMsg="退订失败!";
		}
		String responseXML = toXml(streamingNo,resultCode,resultMsg,resultInfo);
		in.put("accessOutObject", responseXML);
		return in;
	}
	public String toXml(String streamingNo,String resultCode,String resultMsg,List resultInfo){
		StringBuffer bf = new StringBuffer();
		bf.append("<Response>");
		bf.append("<").append("SubscribeToVSOPResp").append(">")
				.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
				.append("<ResultCode>").append(resultCode).append("</ResultCode>")
				.append("<ResultDesc>").append(resultMsg).append("</ResultDesc>");
				if(resultInfo != null && resultInfo.size() > 0){
					for (Iterator iterator = resultInfo.iterator(); iterator
							.hasNext();) {
						Map	 tmp = (Map) iterator.next();
						bf.append("<resultInfo>")
							.append("<ProductOfferID>").append((String)tmp.get("ProductOfferID")).append("</ProductOfferID>")
							.append("<OPResult>").append((String)tmp.get("OPResult")).append("</OPResult>")
							.append("<OPDesc>").append((String)tmp.get("OPDesc")).append("</OPDesc>")
						.append("</resultInfo>");
					}
				}
		bf.append("</").append("SubscribeToVSOPResp").append(">");
		bf.append("</Response>");
		return bf.toString();
	}
	/**
	 * 根据请求串得到订单信息
	 * @param inXML
	 * @return
	 * @throws Exception
	 */
	private CustomerOrder loadFromXML(String inXML,CustomerOrder order) throws Exception{
		long s1 = System.currentTimeMillis();
		order.setCustSoNumber(XMLUtils.getSingleTagValue(inXML, "StreamingNo"));
		order.setReceiveDate(XMLUtils.getSingleTagValue(inXML, "TimeStamp"));
		String systemId = XMLUtils.getSingleTagValue(inXML, "SystemId");
		order.setOrderSystem(systemId);
		order.setOrderChn(systemId);
		order.setCustOrderType(XMLUtils.getSingleTagValue(inXML, "ActionType"));
		//order.setOrderSystem(XMLUtils.getSingleTagValue(inXML, "OrderId"));
		String productId = XMLUtils.getSingleTagValue(inXML, "ProdSpecCode");//主产品id
		String accNbr = XMLUtils.getSingleTagValue(inXML, "ProductNo");//产品号码
		order.setProdId(productId);
		order.setAccNbr(accNbr);
		order.setStatus(OrderConstant.orderStateOfCreated);
		order.setOtherSysOrderId(XMLUtils.getSingleTagValue(inXML, "OrderId"));
		//江西本地化处理
		//yi.chengfeng 2010-7-10 支持产品类型传产品大类 start
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			AssemCoreEntityServices aAssemCoreEntityServices=new AssemCoreEntityServices();
			aAssemCoreEntityServices.resetProdInstInfo(order);
		}
		//yi.chengfeng 2010-7-10 支持产品类型传产品大类 end
		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
		ProdInstVO prodInst=null;
		// 广西本地化处理，查非00X的用户实例
		if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)) {
			// 当true时非00X状态都查出来
			prodInst = prodInstDao.qryProdInstByAccNbrAndProductId(order
					.getAccNbr(), order.getProdId(), true);
		} else {
			// 当false时只有00A（正常）00B，00C状态为有效状态。
			prodInst = prodInstDao.qryProdInstByAccNbrAndProductId(order
					.getAccNbr(), order.getProdId(), false);
		}
		order.setProdInst(prodInst);
		order.setProdInstId(prodInst.getProdInstId());
		order.setLanId(prodInst.getLanId());
		//order.setProdInstId(XMLUtils.getSingleTagValue(inXML, "ProdInstId"));
		long s = System.currentTimeMillis();
		parseProductOfferInfo(inXML,order);
		logger.info("pattern parseProductOfferInfo cost:"+(System.currentTimeMillis()-s));
		s = System.currentTimeMillis();
		//如果业务动作时退订所有
		if(OrderConstant.orderTypeOfAll.equals(order.getCustOrderType())){
			//Map ret = new OrderDao().getOrderedVproducts(order.getProdId(),order.getAccNbr(),OrderConstant.orderTypeOfDel);//actionType 用退订的，鉴权复用退订的逻辑
			//如果是退定全部，需要查处所有有效的订购关系，然后转化为增值销售品对象
			//*********
			//广西本地化,全部退订情况下，存在传统+增值则返回提示信息,由于鉴权无法检测这个，暂用抛出异常进行识别
			//*********
			if(CrmConstants.GX_PROV_CODE.equals(this.provinceCode)){
				StringBuffer sb=new StringBuffer("");
				try{
					getAllProdOfferByProdInstId(order.getProdInst().getProdInstId(),order);
				}catch(Exception e){
					sb.append("<resultInfo><ProductOfferID></ProductOfferID><OPResult>1</OPResult><OPDesc>")
					  .append(e.getMessage()).append("</OPDesc>").append("</resultInfo>");
				}
				if(!"".equals(sb.toString())){
					throw new Exception(sb.toString());
				}
			}else{
				getAllProdOfferByProdInstId(order.getProdInst().getProdInstId(),order);		
			}
		}
		logger.info("pattern orderTypeOfAll cost:"+(System.currentTimeMillis()-s));
		s = System.currentTimeMillis();
		validate(order);
		logger.info("pattern loadFromXML cost:"+(System.currentTimeMillis()-s1));
		return order;
	}
	/**
	 * 
	 * 退订所有，得到所有的销售品
	 * @param prodInstId
	 * @throws Exception 
	 */
	private void getAllProdOfferByProdInstId(String prodInstId,CustomerOrder order) throws Exception {
		OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
		//根据产品实例得到所有的订购关系
		List orderRelations = orderRelationDao.qryORSByProdInstId(prodInstId);
		List prodOfferList = new ArrayList();
		List pprodOfferIdList= new ArrayList();
		int pprodOfferNum = 0;//主产品+捆绑销售品数量
		Map offermap = new HashMap();
		for (int i = 0; i < orderRelations.size(); i++) {
			OrderRelationVO orderRelation = (OrderRelationVO) orderRelations.get(i);
			String packageId= orderRelation.getPackageId();
			String pProdOfferId = orderRelation.getPprodOffId();
			String prodOfferId = orderRelation.getProdOffId();
			String offerType = "0"; //纯增值
			if(null != packageId && !"".equals(packageId)){
				offerType="1";
				prodOfferId =packageId;
			}else if(null != pProdOfferId && !"".equals(pProdOfferId)){
				pprodOfferIdList.add(prodOfferId);
				offerType = "2"	;
				prodOfferId =pProdOfferId;
				pprodOfferNum++;
			}
			ProductOfferInfo productOfferInfo =(ProductOfferInfo) offermap.get(prodOfferId); //是否已经添加进map,下面判断，如果已经添加进去，就不用再添加
			if(null == productOfferInfo){
				productOfferInfo = new ProductOfferInfo();
				productOfferInfo.setOfferId(prodOfferId);
				productOfferInfo.setOfferType(offerType);
				productOfferInfo.setActioType(order.getCustOrderType());
				offermap.put(prodOfferId, productOfferInfo);
			}
			List productList = productOfferInfo.getVproductInfoList();
			if(null == productList)
				productList = new ArrayList();
			VproductInfo product = new VproductInfo();
			product.setOfferId(prodOfferId);
			product.setOfferType(offerType);
			product.setActionType("1"); //退订
			product.setVProductId(orderRelation.getProdId());			
			product.setVProductInstID(orderRelation.getOrderRelationId());
			product.setVProductNbr(DcSystemParamManager.getInstance().getProductNbrById(orderRelation.getProdId()));
			productList.add(product);
			productOfferInfo.setVproductInfoList(productList);
			//prodOfferList.add(productOfferInfo);
		}
    	java.util.Set set1=offermap.keySet();
    	java.util.Iterator it1=set1.iterator();
    	while(it1.hasNext()){
    		String key=(String)it1.next();
    		ProductOfferInfo offer=(ProductOfferInfo)offermap.get(key);
    		prodOfferList.add(offer);
    	}
    	//**********
    	//广西本地化,全部退订里，如果存在传统+增值的话，不能退订传统+增值部分
    	//successAll 在流程中，表示此流程不是全部成功，可能是部分成功，可能是全部失败,受业务规则影响
    	//**********
		if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)
				&& pprodOfferNum != 0) {
			order.setSuccessAll(false);
			int orSize = orderRelations.size();
			if (orSize == 0) {
				throw new Exception("没有可以退订的增值产品!");
			}
			if (orSize > 0 && orSize == pprodOfferNum) {
				order.setBreakScream(true);
				List resultInfoList = new ArrayList();
				for (Iterator iter = pprodOfferIdList.iterator(); iter.hasNext();) {
					String pprodofferId = (String) iter.next();
					Map resultInfoMap = new HashMap();
					resultInfoMap.put("ProductOfferID", pprodofferId);
					resultInfoMap.put("OPResult", "10");
					
					String prodOfferName=DcSystemParamManager.getInstance().getProdOfferNameById(pprodofferId);
					StringBuffer bf=new StringBuffer("");
					bf.append("增值产品")
						.append(prodOfferName)
						.append("捆绑了优惠套餐")
						.append(",必须通过营业厅取消优惠套餐进行退订!");
					resultInfoMap.put("OPDesc",bf.toString());
					resultInfoList.add(resultInfoMap);
				}
				order.setSuccessAllReason(resultInfoList);
			}
		}else{
	    	int orSize = orderRelations.size() ;
	    	if(orSize==0 || ( orSize>0 && orSize == pprodOfferNum)){
	    		throw new Exception("没有可以退订的增值产品!");
	    	}
		}
    	order.setProductOfferInfoList(prodOfferList);
	}
	/**
	 * 验证是否有销售品
	 * @param order
	 * @throws DocumentException
	 * @throws NoVproductException
	 */
	private void validate(CustomerOrder order) throws DocumentException {
		if(OrderConstant.orderTypeOfAdd.equals(order.getCustOrderType()) || OrderConstant.orderTypeOfDel.equals(order.getCustOrderType())){
			if(order.getProductOfferInfoList().size()<1){
				throw new DocumentException("ProductOfferInfo node required!");
			}
		}
	}
	/**
	 * 解析销售品信息
	 * @param inXML
	 * @throws Exception
	 */
	private void parseProductOfferInfo(String inXML,CustomerOrder order) throws Exception {
		//如果是退订所有不需要解析该节点
		if (OrderConstant.orderTypeOfAll.equals(order.getCustOrderType())) {
			return;
		}
		String tagName = "ProductOfferInfo";
		StringBuffer bf = new StringBuffer();
		bf.append("<").append(tagName).append(">([\\s\\S]*?)</").append(tagName).append(">");
		Pattern pattern = Pattern.compile(bf.toString());
		Matcher matcher = pattern.matcher(inXML);
		List productOfferInfos = new ArrayList();
		//由于在非全部退订的情况下，退订中如果传统+增值在当前的鉴权中无法处理，现在暂时利用抛出Exception的方法处理
		StringBuffer sb=new StringBuffer("");
		while(matcher.find()){
			String result = matcher.group(1);
			try{
				ProductOfferInfo productOfferInfo = getProdOfferInfo(result, order.getCustOrderType(), order.getProdId(), order.getAccNbr(),order);
				productOfferInfos.add(productOfferInfo);
			}catch(Exception e){
				sb.append("<resultInfo><ProductOfferID></ProductOfferID><OPResult>1</OPResult><OPDesc>")
				  .append(e.getMessage()).append("</OPDesc>").append("</resultInfo>");
			}
		}
		if(!"".equals(sb.toString())){
			throw new Exception(sb.toString());
		}
		order.setProductOfferInfoList(productOfferInfos);
	}
	/**
	 * 解析xml得到销售品对象
	 * @param inXML
	 * @param actionType
	 * @param prodSpecCode
	 * @param productNo
	 * @return
	 * @throws Exception
	 */
	public ProductOfferInfo getProdOfferInfo(String inXML, String actionType,
			String prodSpecCode, String productNo,CustomerOrder order) throws Exception {
		long s = System.currentTimeMillis();
		ProductOfferInfo prodOfferInfo = new ProductOfferInfo();
		prodOfferInfo.setOfferType(XMLUtils.getSingleTagValue(inXML, "ProductOfferType"));
		prodOfferInfo.setEffDate(XMLUtils.getSingleTagValue(inXML, "EffDate"));
		prodOfferInfo.setExpDate(XMLUtils.getSingleTagValue(inXML, "ExpDate"));
		String offNbr = XMLUtils.getSingleTagValue(inXML, "OffNbr");
		if("".equals(offNbr)){
			prodOfferInfo.setOfferNbr(XMLUtils.getSingleTagValue(inXML, "ProductOfferID"));
			String productOfferId=DcSystemParamManager.getInstance().getProdOfferIdByNbr(prodOfferInfo.getOfferNbr());
			//江西本地化报文运行通过
			if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE))){
				// 
			}else{
				if(null ==productOfferId ||"".equals(productOfferId)){
					throw new Exception("销售品状态不可用!");
				}
			}
			prodOfferInfo.setOfferId(productOfferId);
		}else{
			prodOfferInfo.setOfferId(XMLUtils.getSingleTagValue(inXML, "ProductOfferID"));
			prodOfferInfo.setOfferNbr(offNbr);
		}
		logger.info("replaceProductOfferId cost:" +(System.currentTimeMillis()-s));
		//setProdSpecCode(prodSpecCode);----- 没有这个字段
		prodOfferInfo.setActioType(actionType);
		s = System.currentTimeMillis();
		parseVSubProdInfo(inXML,prodOfferInfo, order);
		logger.info("parseVSubProdInfo cost:" +(System.currentTimeMillis()-s));
		//如果业务动作是订购，并且VSubProdInfo节点是空的，也就是没有增值产品，则表示订购该销售品下的所有增值产品
		if(OrderConstant.orderTypeOfAdd.equals(actionType) && prodOfferInfo.getVproductInfoList().size() == 0){
			List vproductList = new ArrayList();
			List vProductIdlist  =DcSystemParamManager.getInstance().getVproductIdsByOfferId(prodOfferInfo.getOfferId());
			for (int i = 0; i < vProductIdlist.size(); i++) {
				VproductInfo vprodInfo = new VproductInfo();
				vprodInfo.setOfferId(prodOfferInfo.getOfferId());
				vprodInfo.setEffDate(prodOfferInfo.getEffDate());
				vprodInfo.setExpDate(prodOfferInfo.getExpDate());
				vprodInfo.setVProductId((String) vProductIdlist.get(i));
				vprodInfo.setActionType(actionType);
				vprodInfo.setOfferType(prodOfferInfo.getOfferType());
				//增值产品外码
				vprodInfo.setVProductNbr(DcSystemParamManager.getInstance().getProductNbrById((String) vProductIdlist.get(i)));
				//销售品外码
				vprodInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(prodOfferInfo.getOfferId()));
				vproductList.add(vprodInfo);
			}
			prodOfferInfo.setVproductInfoList(vproductList);
		}
		//如果业务动作是退订,并且VSubProdInfo节点是空的，也就是没有增值产品，则表示退订销售品下的所有增值产品
		else if(OrderConstant.orderTypeOfDel.equals(actionType) && prodOfferInfo.getVproductInfoList().size() == 0){
			List vproductList = null;
			String prodInstId = order.getProdInst().getProdInstId();
			String prodOfferId = prodOfferInfo.getOfferId();
			String prodOfferNbr=prodOfferInfo.getOfferNbr();
			VproductHelp vproductHelp = new VproductHelp();
			String offerType = prodOfferInfo.getOfferType();
			if(offerType.equals(OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID)){//捆绑销售品退订时
				vproductList=vproductHelp.getVProductsByPackageId(prodOfferId, prodInstId);
			}else if(offerType.equals(OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID)){//纯增值销售品
				vproductList = vproductHelp.getVProductsByProdOfferId(prodOfferId, prodInstId);
			}else if(offerType.equals(OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID)){//(传统+增值)捆绑销售品时
				vproductList=vproductHelp.getVProductsByPProdOfferId(prodOfferNbr, prodInstId);
			}
			for (int i = 0; i < vproductList.size(); i++) {
				VproductInfo vprodInfo=(VproductInfo) vproductList.get(i);
				vprodInfo.setActionType(prodOfferInfo.getActioType());
				vprodInfo.setEffDate(prodOfferInfo.getEffDate());
				vprodInfo.setExpDate(prodOfferInfo.getExpDate());
			}
			//vproductList = new OrderDao().getOrderedVProductsByProdOfferId(getProdSpecCode(), productNo, getProductOfferID(), actionType, getEffDate(), getExpDate());
			if(vproductList == null || (vproductList != null && vproductList.size() == 0)){
				throw new Exception("你没有订购任何销售品!");
			}
			prodOfferInfo.setVproductInfoList(vproductList);
		}
		return prodOfferInfo;
	}
	/**
	 * 解析增值产品信息
	 * @param inXML
	 * @param prodOfferInfo
	 * @param order TODO
	 * @throws SQLException
	 */
	private void parseVSubProdInfo(String inXML,ProductOfferInfo prodOfferInfo, CustomerOrder order) throws Exception {
		String tagName = "VSubProdInfo";
		StringBuffer bf = new StringBuffer();
		bf.append("<").append(tagName).append(">([\\s\\S]*?)</").append(tagName).append(">");
		Pattern pattern = Pattern.compile(bf.toString());
		Matcher matcher = pattern.matcher(inXML);
		List VSubProdInfoList = new ArrayList();
		while(matcher.find()){
			String result = matcher.group(1);
			VproductInfo vprodcutInfo = getVproductInfo(result,prodOfferInfo, order);
			if(null != vprodcutInfo)
				VSubProdInfoList.add(vprodcutInfo);
		}
		prodOfferInfo.setVproductInfoList(VSubProdInfoList);
	}	
/*	private void parseVSubProdInfo(Element root,ProductOfferInfo prodOfferInfo) throws SQLException {
		Element element;
		List VSubProdInfoList = new ArrayList();
		for (Iterator it = root.elementIterator("VSubProdInfo"); it.hasNext();) {
			element = (Element) it.next();
			VproductInfo vProdInfo = getVproductInfo(element,prodOfferInfo.getEffDate(),prodOfferInfo.getExpDate(),prodOfferInfo.getOfferType(), prodOfferInfo.getOfferId(), prodOfferInfo.getActioType(), prodOfferInfo.getOfferNbr());
			VSubProdInfoList.add(vProdInfo);
		}
		prodOfferInfo.setVproductInfoList(VSubProdInfoList);
	}
	*/
	/**
	 * 解析串得到增值产品信息
	 * @param order TODO
	 */
	public VproductInfo getVproductInfo(String inXML,ProductOfferInfo prodOfferInfo, CustomerOrder order ) throws Exception {
		VproductInfo vprodInfo = new VproductInfo();
		String vproductNbr = XMLUtils.getSingleTagValue(inXML, "VProductNbr");
		long s = System.currentTimeMillis();
		if("".equals(vproductNbr)){  //若没有VProductNbr节点则取VProductID，然后转换
			String prodId = XMLUtils.getSingleTagValue(inXML, "VProductID");
			if(null == prodId || "".equals(prodId)) return null;
			vprodInfo.setVProductNbr(prodId);
			replaceVproductId(vprodInfo);
		}else{  //下面是二次确认的时候解析报文用的
			vprodInfo.setVProductNbr(vproductNbr);
			vprodInfo.setVProductId(XMLUtils.getSingleTagValue(inXML, "VProductID"));
		}
		
		vprodInfo.setEffDate(prodOfferInfo.getEffDate()); //目前以销售品的时间为准 
		vprodInfo.setExpDate(prodOfferInfo.getExpDate());
		vprodInfo.setActionType(prodOfferInfo.getActioType());
		vprodInfo.setOfferNbr(prodOfferInfo.getOfferNbr());
		SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
		String orderRelationId  = sequenceManage.getNextSequence("SEQ_ORDER_RELATION_ID");
		vprodInfo.setVProductInstID(orderRelationId);
		vprodInfo.setOfferId(prodOfferInfo.getOfferId());
		String offerType = prodOfferInfo.getOfferType();
		vprodInfo.setOfferType(offerType);
		//江西本地化修改:根据增值产品编码查询销售品编码 start yi.chengfeng 2010-07-14
		String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
		if(CrmConstants.JX_PROV_CODE.equals(provinceCode)){
			queryAndSetProductOfferIdByVProductId(vprodInfo.getVProductId(), prodOfferInfo, order.getOrderSystem(), vprodInfo);
		}
		return vprodInfo;
	}
	public void replaceVproductId(VproductInfo vProdInfo) throws SQLException {
		String productId = null;
		productId=DcSystemParamManager.getInstance().getProductIdByNbr(vProdInfo.getVProductNbr());
		vProdInfo.setVProductId(productId);
	}
	private void queryAndSetProductOfferIdByVProductId(String vProductID, ProductOfferInfo prodOfferInfo, String systemId, VproductInfo vprodInfo) throws Exception{
		//it侧的几个平台特殊处理
		if(SystemCode.CT10000.equals(systemId) || SystemCode.SEAT_10000.equals(systemId) ||
				SystemCode.SMS.equals(systemId) || SystemCode.VOICE_10000.equals(systemId) || SystemCode.WAP.equals(systemId)){
			//纯增值销售品并且没有传销售品编码则根据增值产品编码查询销售品编码
			if(OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID.equals(prodOfferInfo.getOfferType()) && (null == prodOfferInfo.getOfferId() || "".equals(prodOfferInfo.getOfferId())) ){
				String offerId = DcSystemParamManager.getInstance().getofferIdByProductId(vProductID);
				String offerNbr = DcSystemParamManager.getInstance().getProdOfferNbrById(offerId);
				if(null == offerNbr) throw new Exception("销售品不存在!");
//				Map offerIdAndOfferNbrMap = new OrderDao().queryAndSetProductOfferIdByVProductId(vProductID);
				prodOfferInfo.setOfferId(offerId);
				prodOfferInfo.setOfferNbr(offerNbr);
				vprodInfo.setOfferId(offerId);
				vprodInfo.setOfferNbr(offerNbr);
			}
		}
	}
	public Map inExceptionHandle(Map in, Exception e) {
		in.put("resultCode", "-98");
		in.put("resultMsg", e.getMessage());
		in.put("error", "1");
		return in;
	}
}
