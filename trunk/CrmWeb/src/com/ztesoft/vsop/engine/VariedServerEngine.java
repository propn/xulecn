package com.ztesoft.vsop.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.CRMSynchUtil;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.dao.SynORInfoToCrmHelpDao;
import com.ztesoft.vsop.engine.help.AssemCoreEntityServices;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.access.SubsInstQrySVAccessService;
import com.ztesoft.vsop.engine.service.access.SubsInstSynFromISMPAccessService;
import com.ztesoft.vsop.engine.service.access.SubsInstSynFromJXISMPAccessService;
import com.ztesoft.vsop.engine.service.access.SubsInstSynSVAccessService;
import com.ztesoft.vsop.engine.service.access.SubsInstSynToVSOPSVAccessService;
import com.ztesoft.vsop.engine.service.access.SubscribeAuthSVAccessService;
import com.ztesoft.vsop.engine.service.access.SubscribeReqSVAccessService;
import com.ztesoft.vsop.engine.service.access.WorkSheetAcceptJobAccessService;
import com.ztesoft.vsop.engine.service.access.WorkSheetAcceptSVAccessService;
import com.ztesoft.vsop.engine.service.business.VProductCancelService;
import com.ztesoft.vsop.engine.service.business.VproductOrderService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.SystemCode;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.RefreshCacheHttpClient;
import com.ztesoft.vsop.web.vo.ProdOffVO;

/**
 * 外部服务引擎类（相对于服务引擎而言） 封装各个服务端方法，为多种客户端提供服务，每个客户端调用一个方法
 * 每个方法参照InterfaceExample.example进行实现。 客户端可以是：VSOP页面、VSOP外部接口、VSOP定时任务等
 * 
 * @author cooltan
 * 
 */
public class VariedServerEngine {
	private static Logger logger = Logger.getLogger(VariedServerEngine.class);
	public static VariedServerEngine instance = new VariedServerEngine();
	private OrderDao orderDao;
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//省份代码

	public static VariedServerEngine getInstance() {
		return instance;
	}

	/**
	 * 订购申请接口
	 * @param requestXml
	 * @return
	 */
	public String subscribeToVSOP(String requestXml) {		
	String responseXml=null;
	try {
		String startTime=DAOUtils.getFormatedDate();//开始时间
		long start = System.currentTimeMillis();
		CommonAccessService aCommonAccessService=new SubscribeReqSVAccessService();
		//采用Map作为出入参，每个步骤调用后都会在原来的map递增新的key，
		//进入步骤1前：key includes :accessInObject accessCode   accessType
		//步骤1完成后进入步骤2前：key includes :accessInObject accessCode   accessType busiObject serviceCode
		//步骤2完成后进入步骤3前：key includes :accessInObject accessCode   accessType busiObject serviceCode retBusiObject resultCode resultMsg
		//步骤3完成后：key includes :accessInObject accessCode   accessType busiObject serviceCode  resultCode resultMsg accessOutObject
		//1接口入参对象转换成核心对象
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R1.1");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SubscribeReqSVAccessService");//CustomerOrder已经包含在返回的accessInRet中了
		if(null!=accessInRet.get("error") && "1".equals(accessInRet.get("error"))){
			String streamNo = XMLUtils.getSingleTagValue(requestXml, "StreamingNo");
			//全部退订中，如果有退订传统+捆绑的套餐进行返回提示信息
			return toXml(streamNo,"1","退订失败",(String) accessInRet.get("resultMsg"));
		}
		//2服务处理
		OrderEngine aOrderEngine=new OrderEngine();
		//accessInRet.put("serviceCode", "0");
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3核心对象转换成接口出参
		Map accessOut=aCommonAccessService.out(serviceOut,"SubscribeReqSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); //
		long end = System.currentTimeMillis();
		long processTime=end-start;//
		//4写接口日志
		accessOut.put("startTime",startTime);
		accessOut.put("endTime",endTime);
		accessOut.put("processTime",String.valueOf(processTime));
		DataTranslate._String(ServiceManager.callJavaBeanService("InterfaceLogService","insertLog" ,accessOut));
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("", e);
	}
	return responseXml;
	}
	
	private String toXml(String streamingNo,String resultCode,String resultMsg,String resultInfo){
		StringBuffer bf = new StringBuffer();
		bf.append("<Response>");
		bf.append("<").append("SubscribeToVSOPResp").append(">")
				.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
				.append("<ResultCode>").append(resultCode).append("</ResultCode>")
				.append("<ResultDesc>").append(resultMsg).append("</ResultDesc>");
				if(resultInfo != null && !"".equals(resultInfo)){
					bf.append(resultInfo);
				}
		bf.append("</").append("SubscribeToVSOPResp").append(">");
		bf.append("</Response>");
		return bf.toString();
	}
	
	/**
	 * 订购关系同步
	 * ISMP将订购关系同步到VSOP，为统一订购关系查询使用。
	 * @param requestXml
	 * @return
	 */
	public String SubsInstSynFromISMP(String requestXml) {
		String startTime=DAOUtils.getFormatedDate();//开始时间
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubsInstSynFromISMPAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R8.4");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SubsInstSynFromISMPAccessService");//CustomerOrder已经包含在返回的accessInRet中了
		//2服务处理
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3核心对象转换成接口出参
		Map accessOut=aCommonAccessService.out(serviceOut,"SubsInstSynFromISMPAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); //
		long end = System.currentTimeMillis();
		long processTime=end-start;//
		//4写接口日志
		accessOut.put("startTime",startTime);
		accessOut.put("endTime",endTime);
		accessOut.put("processTime",String.valueOf(processTime));
		try {
			DataTranslate._String(ServiceManager.callJavaBeanService("InterfaceLogService","insertLog" ,accessOut));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return responseXml;

	}

	/**
	 * 订购鉴权
	 * CRM向VSOP发起订购鉴权请求
	 * @param requestXml
	 * @return
	 */
	public String SubscribeAuthSV(String requestXml) {
		String startTime=DAOUtils.getFormatedDate();//开始时间
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubscribeAuthSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R2.2");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SubscribeAuthSVAccessService");//CustomerOrder已经包含在返回的accessInRet中了
		//2服务处理
		List orderList = (List) accessInRet.get("busiObject"); //此处为customerOrder 列表
		OrderEngine aOrderEngine=new OrderEngine();
		Map resultMap = new HashMap();
		Map oneResultMap = new HashMap();
		for (int i = 0; i < orderList.size(); i++) {
			accessInRet.remove("busiObject");
			CustomerOrder tmpOrder=(CustomerOrder)orderList.get(i);
			accessInRet.put("busiObject", tmpOrder);
			Map serviceOut=aOrderEngine.engine(accessInRet);
			resultMap.put(tmpOrder.getAccNbr(), serviceOut);
		}
		Map allServiceOut = new HashMap();
		allServiceOut.put("retBusiObject", resultMap);
		allServiceOut.put("busiObject",orderList);
		//cooltan add
		allServiceOut.put("accessCode", "R2.2");
		allServiceOut.put("serviceCode", String.valueOf(OrderEngine.SERVICE_ORDERAUTH31));
		//3核心对象转换成接口出参
		Map accessOut=aCommonAccessService.out(allServiceOut,"SubscribeAuthSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		//4写接口日志
		String endTime=DAOUtils.getFormatedDate(); //
		long end = System.currentTimeMillis();
		long processTime=end-start;//
		//4写接口日志
		accessOut.put("busiObject", orderList.get(0)); //在写日志表的时候会强转，暂时取第一个
		accessOut.put("startTime",startTime);
		accessOut.put("endTime",endTime);
		accessOut.put("processTime",String.valueOf(processTime));
		try {
			DataTranslate._String(ServiceManager.callJavaBeanService("InterfaceLogService","insertLog" ,accessOut));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return responseXml;

	}
/**
 * 处理VSOP系统页面的订购请求
 * @param staffCode 工号
 * @param fromSystem 系统来源
 * @param prodOfferNbrs销售品编码
 * @param accNbr用户产品号码
 * @param lanCode用户产品本地网
 * @param mProductId用户产品类型
 * @return
 */
	public Map orderProducts(String prodOfferNbrs, String accNbr,
			String lanCode, String mProductId, String staffCode, String fromSystem) throws Exception {
		String[] prodOfferIds=this.prodOfferNbrsToProdOfferIds(prodOfferNbrs);//把销售品编码转换成销售品ID
		logger.debug("prodOfferIds:" + prodOfferIds.toString() + ",accNbr:"
				+ accNbr);
		// 步骤一,转换为核心对象
		
		
		Map prodOfferId_Products = null;// 销售品id与其对应增值产品的映射
		prodOfferId_Products = AssemCoreEntityServices.getInstance().getProductVOByProdOfferIds(prodOfferIds);
		List prodOfferList = new ArrayList();// 销售品列表
		for (int i = 0; i < prodOfferIds.length; i++) {
			ProdOffVO prodOffVO = DcSystemParamManager.getInstance().getProdOffVOById(prodOfferIds[i]);
			prodOfferList.add(prodOffVO);
		}
		List productOfferInfoList = new ArrayList();// 订单销售品列表
		for (Iterator iter = prodOfferList.iterator(); iter.hasNext();) {
			ProdOffVO prodOffVO = (ProdOffVO) iter.next();
			ProductOfferInfo prOffInfo = new ProductOfferInfo();// 订单销售品
			prOffInfo.setActioType("0");// 动作
			prOffInfo.setOfferId(prodOffVO.getProdOffId());//销售品ID
			prOffInfo.setOfferNbr(prodOffVO.getOffNbr());//销售品编码
			prOffInfo.setEffDate(prodOffVO.getEffDate());//生效时间
			prOffInfo.setExpDate(prodOffVO.getExpDate());//失效时间
			prOffInfo.setOfferType(prodOffVO.getProdOffSubType());//销售品类型
			prOffInfo.setVproductInfoList((List) prodOfferId_Products.get(prodOffVO.getProdOffId()));
			productOfferInfoList.add(prOffInfo);//订单销售品
		}
		// 查找产品实例
		ProdInstHelpDao prInDao = new ProdInstHelpDao();
		ProdInstVO prInVO = null;
		Map map = new HashMap();
		boolean exceptionFlag=false;
		Map resultMap=new HashMap();
		try {
			// 广西本地化处理，查非00X的用户实例
			if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)) {
				// 当true时非00X状态都查出来
				prInVO = prInDao.qryProdInstByAccNbrAndProductId(accNbr, mProductId, true);
			} else {
				// 当false时只有00A（正常）00B，00C状态为有效状态。
				prInVO = prInDao
				.qryProdInstByAccNbrAndProductId(accNbr, mProductId,false);//target当false时只有00A（正常）00B，00C状态为有效状态。
			}
		} catch (Exception e) {
			logger.debug("页面订购出错: " + e.getMessage());
			 resultMap = new HashMap();
			resultMap.put("ResultCode", "-1");
			resultMap.put("ResultDesc", "没有找到对应产品实例");
			exceptionFlag=true;
			throw e;
		}finally{
			if(exceptionFlag){//异常返回异常信息
				return resultMap;
			}
		}
		CustomerOrder cuOrder = new CustomerOrder();// 订单对象
		cuOrder.setProductOfferInfoList(productOfferInfoList);// 设置订单销售品
		cuOrder.setProdInst(prInVO);// 设置用户
		cuOrder.setOrderSystem((fromSystem != null) ? fromSystem : SystemCode.VSOP);// 设置来源系统
		cuOrder.setLanId(prInVO.getLanId());// 用户lan
		cuOrder.setAccNbr(prInVO.getAccNbr());// 号码
		cuOrder.setCustSoNumber(DateUtil.getVSOPDateTime14());// 流水号取系统当前14位时间
		cuOrder.setStatus(OrderConstant.orderStateOfCreated);
		cuOrder.setProdInstId(prInVO.getProdInstId());// 产品实例ID
		cuOrder.setProdId(prInVO.getProdId());// 产品编码
		cuOrder.setOrderChn((fromSystem != null) ? fromSystem : SystemCode.VSOP);// order Channel
		cuOrder.setCustOrderType(OrderEngine.SERVICE_APPLY0_STR);//订单类型
		cuOrder.setStaffId(staffCode);
		// 步骤二，直接用服务组件进行页面订购处理
		VproductOrderService prOrService = new VproductOrderService();
		Map param = new HashMap();
		param.put("busiObject", cuOrder);
		param.put("accessCode", "0");
		param.put("accessType", "0");
		param.put("serviceCode", OrderEngine.SERVICE_APPLY0_STR);
		
		try {
			map = prOrService.concreteBusinessOpertions(param);
		} catch (Exception e) {
			logger.debug("页面订购出错: " + e.getMessage());
			 resultMap = new HashMap();
			resultMap.put("ResultCode", "-1");
			resultMap.put("ResultDesc", e.getMessage());
			exceptionFlag=true;
			throw e;
		}finally{
			if(exceptionFlag){//异常返回异常信息
				return resultMap;
			}
		}
		// 根据订单对象新增订购关系同步队列 反向同步
		if(this.provinceCode.equals(CrmConstants.GX_PROV_CODE)){
			//GX在页面订购时，不同步，统一由R8.4同步
		}else{
			String code=(String) map.get("resultCode");
			if (null != CRMSynchUtil.CRM_SYN
					&& "1".equals(CRMSynchUtil.CRM_SYN.trim()) && null != code && "0".equals(code)) {
				try {
					SynORInfoToCrmHelpDao sysDao = new SynORInfoToCrmHelpDao();
					sysDao.createChangeORQueueByCustomerOrder(cuOrder);//页面进来
				} catch (Exception e) {
					logger.debug("页面订购反向同步出错: " + e.getMessage());
					 resultMap = new HashMap();
					resultMap.put("ResultCode", "-1");
					resultMap.put("ResultDesc", "页面订购-反向同步出错,请联系管理员");
					exceptionFlag=true;
					throw e;
				}finally{
					if(exceptionFlag){//异常返回异常信息
						return resultMap;
					}
				}
			}
		}

		String resultCode = (String) map.get("resultCode");// 返回信息
		String resultDesc = (String) map.get("resultMsg");// 返回信息

		// 步骤三,返回出参对象
		resultMap.put("ResultCode", resultCode);
		resultMap.put("ResultDesc", resultDesc);
		return resultMap;
	}

	/**
	 * 服务开通工单请求接口
	 * 直接保存xml到接口表，后续定时任务扫描处理
	 * @param requestXml
	 * @return
	 */
	public String workListFKToVSOP(String requestXml)  {
		String startTime=DAOUtils.getFormatedDate();//开始时间
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new WorkSheetAcceptSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R5.1");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"WorkSheetAcceptSVAccessService");//CustomerOrder已经包含在返回的accessInRet中了
		//2服务处理
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3核心对象转换成接口出参
		Map accessOut=aCommonAccessService.out(serviceOut,"WorkSheetAcceptSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		//4写接口日志
		String endTime=DAOUtils.getFormatedDate(); //
		long end = System.currentTimeMillis();
		long processTime=end-start;//
		//4写接口日志
		accessOut.put("startTime",startTime);
		accessOut.put("endTime",endTime);
		accessOut.put("processTime",String.valueOf(processTime));
		try {
			DataTranslate._String(ServiceManager.callJavaBeanService("InterfaceLogService","insertLog" ,accessOut));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return responseXml;
	}
	/**
	 * 服务开通工单请求处理定时任务处理方法
	 * @param requestXml
	 * @return
	 */
	public String workListFKToVSOPJob(String requestXml)  {
		String responseXml=null;
		CommonAccessService aCommonAccessService=new WorkSheetAcceptJobAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R5.1");
		accessIn.put("accessType", "");
		aCommonAccessService.in(accessIn,"WorkSheetAcceptJobAccessService");//CustomerOrder已经包含在返回的accessInRet中了
		//2服务处理
		OrderEngine aOrderEngine=new OrderEngine();
		aOrderEngine.engine(accessIn);
		//3核心对象转换成接口出参
		Map accessOut=aCommonAccessService.out(accessIn,"WorkSheetAcceptJobAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		//4写接口日志
		return responseXml;
	}
	/**
	 * 订购关系查询接口
	 * @param requestXml
	 * @return
	 */
	public String subInstQryFromVSOP(String requestXml)  {
		String startTime=DAOUtils.getFormatedDate();//开始时间
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubsInstQrySVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R1.2");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SubsInstQrySVAccessService");//CustomerOrder已经包含在返回的accessInRet中了
		//2服务处理
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3核心对象转换成接口出参
		Map accessOut=aCommonAccessService.out(serviceOut,"SubsInstQrySVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		//4写接口日志
		String endTime=DAOUtils.getFormatedDate(); //
		long end = System.currentTimeMillis();
		long processTime=end-start;//
		//4写接口日志
		accessOut.put("startTime",startTime);
		accessOut.put("endTime",endTime);
		accessOut.put("processTime",String.valueOf(processTime));
		try {
			DataTranslate._String(ServiceManager.callJavaBeanService("InterfaceLogService","insertLog" ,accessOut));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return responseXml;
	}

	/**
	 * 利用缓存，把页面传过来的销售品编码转换为销售品ID
	 * @param prodOfferNbrs
	 * @return
	 */
	private String[] prodOfferNbrsToProdOfferIds(String prodOfferNbrs) {
		String[] ids = prodOfferNbrs.split(",");
		for (int i = 0; i < ids.length; i++) {
			ids[i] = DcSystemParamManager.getInstance().getProdOfferIdByNbr(
					ids[i]);
		}
		return ids;
	}
/**
 * 退订选择的增值产品
 * @param relationIds
 * @return
 */
	public Map delOrdersByRelationId(String relationIds,String staff,String orderChannel) {
		Map in = new HashMap();
		try {
			//1.根据页面传送的订购关系实例标识串获取要退订的增值订购关系实例列表
			OrderRelationHelpDao orderRelationHelpDao = new OrderRelationHelpDao();
			List relations = orderRelationHelpDao.getReletionsByIds(relationIds);
			List productOfferInfoList = new ArrayList();
			List temp = new ArrayList();
			List reTemp = new ArrayList();//属性重复
			List result = new ArrayList();
			String accNbr = "";//产品号码
			for(int i=0 ;i<relations.size();i++){
				Map map = (Map)relations.get(i);
				String packageId = (String)map.get("PACKAGE_ID");
				String pProdOfferId = (String)map.get("PPROD_OFFER_ID");
				accNbr = (String)map.get("ACC_NBR");
				if(!temp.contains(packageId+pProdOfferId)||(("".equals(packageId)||packageId==null)&&("".equals(pProdOfferId)||pProdOfferId==null))){
					temp.add(packageId+pProdOfferId);
					result.add(relations.get(i));
				}
				else{ 
					if((!"".equals(packageId)&&packageId!=null)||(!"".equals(pProdOfferId)&&pProdOfferId!=null)){
						reTemp.add(packageId+pProdOfferId);
					}
				}
			}
			for(int i=0;i<result.size();i++){
				Map resultMap = (Map)result.get(i);
				String record = (String)resultMap.get("PACKAGE_ID")+(String)resultMap.get("PPROD_OFFER_ID");
				ProductOfferInfo productOfferInfo = new ProductOfferInfo();
				List vproductInfoList = new ArrayList();//增值产品列表List
				if(reTemp.contains(record)){
					for(int j=0;j<relations.size();j++){
						Map map = (Map)relations.get(j);
						if(record.equals((String)map.get("PACKAGE_ID")+(String)map.get("PPROD_OFFER_ID"))){
							String offerId = "";
							//依据订购关系实例列表构造品订单销售列表对象。判断订购关系实例pprod_offer_id字段非空时，订单销售品offerid属性取这个字段值，
							//offertype＝‘2’；当package_id字段非空时，offerid属性取这个字段值，offertype＝‘1’，否则offerid属性值取prod_offer_id字段值，offertype＝‘0’；
							if(!"".equals(map.get("PPROD_OFFER_ID"))&&map.get("PPROD_OFFER_ID")!=null){
								offerId = (String)map.get("PPROD_OFFER_ID");
								productOfferInfo.setOfferId(offerId);
								productOfferInfo.setOfferType("2");
								//其他属性取值依据属性说明进行取值。
								productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));

							}
							else if(!"".equals(map.get("PACKAGE_ID"))&&map.get("PACKAGE_ID")!=null){
								offerId = (String)map.get("PACKAGE_ID");
								productOfferInfo.setOfferId(offerId);
								productOfferInfo.setOfferType("1");
								//其他属性取值依据属性说明进行取值。
								productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
							}
							else{
								offerId = (String)map.get("PROD_OFFER_ID");
								productOfferInfo.setOfferId(offerId);
								productOfferInfo.setOfferType("0");
								//其他属性取值依据属性说明进行取值。
								productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
							}
							//同时需要将属于同个销售品的订购关系实例归并到订单销售品增值产品列表下，增值产品动作为1退订；
							VproductInfo vProductInfo = new VproductInfo();
							vProductInfo.setActionType("1");
							vProductInfo.setVProductId((String)map.get("PRODUCT_ID"));
							vProductInfo.setVProductNbr(DcSystemParamManager.getInstance().getProductNbrById((String)map.get("PRODUCT_ID")));
							vProductInfo.setEffDate((String)map.get("EFF_DATE"));
							vProductInfo.setExpDate((String)map.get("EXP_DATE"));
							vProductInfo.setVProductInstID((String)map.get("ORDER_RELATION_ID"));
							vProductInfo.setState(OrderConstant.orderStateOfCreated);
							vProductInfo.setOfferId(offerId);
							vProductInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
							vProductInfo.setOfferType(DcSystemParamManager.getInstance().getProdOfferTypeById(offerId));
							vproductInfoList.add(vProductInfo);
						}
					}
					productOfferInfo.setEffDate((String)resultMap.get("EFF_DATE"));
					productOfferInfo.setExpDate((String)resultMap.get("EXP_DATE"));
					productOfferInfo.setVproductInfoList(vproductInfoList);
					//其他属性取值依据属性说明进行取值。
					productOfferInfo.setActioType(OrderConstant.orderTypeOfDel);					
				}
				else{
						String offerId = "";
						//依据订购关系实例列表构造品订单销售列表对象。判断订购关系实例pprod_offer_id字段非空时，订单销售品offerid属性取这个字段值，
						//offertype＝‘2’；当package_id字段非空时，offerid属性取这个字段值，offertype＝‘1’，否则offerid属性值取prod_offer_id字段值，offertype＝‘0’；
						if(!"".equals(resultMap.get("PPROD_OFFER_ID"))&&resultMap.get("PPROD_OFFER_ID")!=null){
							offerId = (String)resultMap.get("PPROD_OFFER_ID");
							productOfferInfo.setOfferId(offerId);
							productOfferInfo.setOfferType("2");
							//其他属性取值依据属性说明进行取值。
							productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
						}
						else if(!"".equals(resultMap.get("PACKAGE_ID"))&&resultMap.get("PACKAGE_ID")!=null){
							offerId = (String)resultMap.get("PACKAGE_ID");
							productOfferInfo.setOfferId(offerId);
							productOfferInfo.setOfferType("1");
							//其他属性取值依据属性说明进行取值。
							productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
						}
						else{
							offerId = (String)resultMap.get("PROD_OFFER_ID");
							productOfferInfo.setOfferId(offerId);
							productOfferInfo.setOfferType("0");
							//其他属性取值依据属性说明进行取值。
							productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
						}
						//同时需要将属于同个销售品的订购关系实例归并到订单销售品增值产品列表下，增值产品动作为1退订；
						VproductInfo vProductInfo = new VproductInfo();
						vProductInfo.setActionType("1");
						vProductInfo.setVProductId((String)resultMap.get("PRODUCT_ID"));
						vProductInfo.setVProductNbr(DcSystemParamManager.getInstance().getProductNbrById((String)resultMap.get("PRODUCT_ID")));
						vProductInfo.setEffDate((String)resultMap.get("EFF_DATE"));
						vProductInfo.setExpDate((String)resultMap.get("EXP_DATE"));
						vProductInfo.setVProductInstID((String)resultMap.get("ORDER_RELATION_ID"));
						vProductInfo.setState(OrderConstant.orderStateOfCreated);
						vProductInfo.setOfferId(offerId);
						vProductInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
						vProductInfo.setOfferType(DcSystemParamManager.getInstance().getProdOfferTypeById(offerId));
						vproductInfoList.add(vProductInfo);
						
						productOfferInfo.setVproductInfoList(vproductInfoList);
						//其他属性取值依据属性说明进行取值。
						productOfferInfo.setEffDate((String)resultMap.get("EFF_DATE"));
						productOfferInfo.setExpDate((String)resultMap.get("EXP_DATE"));
						productOfferInfo.setActioType(OrderConstant.orderTypeOfDel);	
				}
				productOfferInfoList.add(productOfferInfo);
			}
			ProdInstHelpDao pInstHelpDao = new ProdInstHelpDao();
//			ProdInstVO prodInstVo = pInstHelpDao.qryProdInstByAccNbrAndLanCode(accNbr, null);
			ProdInstVO prodInstVo=null;
			// 广西本地化处理，查非00X的用户实例
			if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)) {
				// 当true时非00X状态都查出来
				prodInstVo = pInstHelpDao.qryProdInstByAccNbrAndLanCode_GX(accNbr,null);
			} else {
				// 当false时只有00A（正常）00B，00C状态为有效状态。
				prodInstVo = pInstHelpDao.qryProdInstByAccNbrAndLanCode(accNbr, null);//target当false时只有00A（正常）00B，00C状态为有效状态。
			}
			
			CustomerOrder order = new CustomerOrder();
			order.setCustOrderType(OrderConstant.orderTypeOfDel);
			//crm 为201，vsop为200
			order.setOrderSystem(orderChannel);
			order.setOrderChn(orderChannel);
			order.setAccNbr(prodInstVo.getAccNbr());
			order.setProdInstId(prodInstVo.getProdInstId());
			order.setLanId(prodInstVo.getLanId());
			order.setProdInst(prodInstVo);
			order.setStatus(OrderConstant.orderStateOfCreated);
			order.setProductOfferInfoList(productOfferInfoList);
			order.setCustSoNumber(String.valueOf(System.currentTimeMillis()));
			order.setProdId(prodInstVo.getProdId());
			//操作工号
			order.setStaffId(staff);
		
			//退订
			in.put("busiObject", order);
			in.put("serviceCode", OrderEngine.SERVICE_CANCEL1_STR);
			AbstractBusinessService abstractBusinessService = new VProductCancelService();
			abstractBusinessService.concreteBusinessOpertions(in);
			String code=(String) in.get("resultCode");
			
			if(this.provinceCode.equals(CrmConstants.GX_PROV_CODE)){
				//GX，在页面退订，不同步，只R8.4同步
			}else{
				if (null != CRMSynchUtil.CRM_SYN
						&& "1".equals(CRMSynchUtil.CRM_SYN.trim()) && null != code && "0".equals(code)) {
					//根据订单对象新增订购关系同步队列 反向同步
					SynORInfoToCrmHelpDao sysDao = new SynORInfoToCrmHelpDao();
					sysDao.createChangeORQueueByCustomerOrder(order);
				}
				
			}
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return in;
	}
	/**
	 * 江西订购关系同步
	 * ISMP将订购关系同步到VSOP，为统一订购关系查询使用。
	 * @param requestXml
	 * @return
	 */

	public com.chinatelecom.ismp.crm.rsp.Response SubsInstSynFromJXISMP(com.chinatelecom.ismp.crm.req.SubscriptionSyncReq requestObj) {
		com.chinatelecom.ismp.crm.rsp.Response responseObj=null;
		try {
			String startTime=DAOUtils.getFormatedDate();//开始时间
			logger.info("ismp SubscriptionSyncReq:" + "OldProductID:" + requestObj.getOldProductID() + ",OldProductID:" + requestObj.getOldProductID()
					+ ",OPType:" + requestObj.getOPType() + ",PackageID:" + requestObj.getPackageID()
					+ ",ProductID:" + requestObj.getProductID() + ",StreamingNo:" + requestObj.getStreamingNo()
					+ ",UserID:" + requestObj.getUserID() + ",UserIDType:" + requestObj.getUserIDType() 
			);
			long start = System.currentTimeMillis();
			
			CommonAccessService aCommonAccessService=new SubsInstSynFromJXISMPAccessService();
			Map accessIn =new HashMap();
			accessIn.put("accessInObject", requestObj);
			accessIn.put("accessCode", "R8.4");
			accessIn.put("accessType", "");
			//1入参
			Map accessInRet=aCommonAccessService.in(accessIn,"SubsInstSynFromJXISMPAccessService");//CustomerOrder已经包含在返回的accessInRet中了
			//2服务处理
			OrderEngine aOrderEngine=new OrderEngine();
			Map serviceOut=aOrderEngine.engine(accessInRet);
			//3核心对象转换成接口出参
			Map accessOut=null;
			accessOut=aCommonAccessService.out(serviceOut,"SubsInstSynFromJXISMPAccessService");
			responseObj=(com.chinatelecom.ismp.crm.rsp.Response)accessOut.get("accessOutObject");
			logger.info("ismp SubscriptionSync response:resultCode:" + responseObj.getResultCode() + ",StreamingNo:" + responseObj.getStreamingNo());
			String endTime=DAOUtils.getFormatedDate(); 
			long end = System.currentTimeMillis();
			long processTime=end-start;
			//4写接口日志
			accessOut.put("startTime",startTime);
			accessOut.put("endTime",endTime);
			accessOut.put("processTime",String.valueOf(processTime));
			DataTranslate._String(ServiceManager.callJavaBeanService("InterfaceLogService","insertLog" ,accessOut));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return responseObj;

	}

	/**
	 * <pre>
	 * Title:集团规范 X平台同步订购关系到VSOP
	 * Description: 订购关系同步(X平台-VSOP)
	 * </pre>
	 * 
	 * @author xulei xu.lei55@zte.com.cn
	 * @version 1.00.00
	 * 
	 * <pre>
	 * 修改记录
	 * 修改后版本:     修改人：  修改日期:     修改内容:
	 * </pre>
	 */
	public String SubsInstSynToVSOPSV(String requestXml) {
		String startTime=DAOUtils.getFormatedDate();//开始时间
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubsInstSynToVSOPSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R7.8");
		accessIn.put("accessType", "");
		
		//1入参 //CustomerOrder已经包含在返回的accessInRet中了
		Map accessInRet=aCommonAccessService.in(accessIn,"SubsInstSynSVAccessService");
		//2服务处理
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		
		//3核心对象转换成接口出参
		Map accessOut=aCommonAccessService.out(serviceOut,"SubsInstSynSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); 
		long end = System.currentTimeMillis();
		long processTime=end-start;
		
		//4写接口日志
		accessOut.put("startTime",startTime);
		accessOut.put("endTime",endTime);
		accessOut.put("processTime",String.valueOf(processTime));
		
		try {
			DataTranslate._String(ServiceManager.callJavaBeanService("InterfaceLogService","insertLog" ,accessOut));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return responseXml;

	}
	
	/**
	 * 订购关系同步
	 * X平台将订购关系同步到VSOP，为统一订购关系查询使用。
	 * @param requestXml
	 * @return
	 */
	public String subsInstSynSV(String requestXml) {
		String startTime=DAOUtils.getFormatedDate();//开始时间
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubsInstSynSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R7.1");
		accessIn.put("accessType", "");
		//1入参
		Map accessInRet=aCommonAccessService.in(accessIn,"SubsInstSynSVAccessService");//CustomerOrder已经包含在返回的accessInRet中了
		//2服务处理
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3核心对象转换成接口出参
		Map accessOut=aCommonAccessService.out(serviceOut,"SubsInstSynSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); 
		long end = System.currentTimeMillis();
		long processTime=end-start;
		//4写接口日志
		accessOut.put("startTime",startTime);
		accessOut.put("endTime",endTime);
		accessOut.put("processTime",String.valueOf(processTime));
		try {
			DataTranslate._String(ServiceManager.callJavaBeanService("InterfaceLogService","insertLog" ,accessOut));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return responseXml;

	}
	/**
	 * CRM优惠同步
	 * CRM将优惠实例同步给VSOP
	 * @param requestXml
	 * @return
	 */
	public String prodOfferCrmToVsopSyn(String requestXml) {
		String startTime=DAOUtils.getFormatedDate();//开始时间
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubsInstSynSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R20.1");
		accessIn.put("accessType", "");
		//1入参
		Map accessInRet=aCommonAccessService.in(accessIn,"OfferCrmToVsopSynAccessService");//CustomerOrder已经包含在返回的accessInRet中了
		//2服务处理
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		
		//3 刷新缓存
		String offerId= (String)serviceOut.get("offerId");
		if(null != offerId && !"".equals(offerId)){
			//增量刷新销售品相关信息
			RefreshCacheHttpClient.getInstance().refreshAllServerCaches(DcSystemParamManager.CACHE_OBJECTTYPE_PRODOFFER1,offerId);
		}
		//4核心对象转换成接口出参
		Map accessOut=aCommonAccessService.out(serviceOut,"OfferCrmToVsopSynAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); 
		long end = System.currentTimeMillis();
		long processTime=end-start;
		//5写接口日志
		accessOut.put("startTime",startTime);
		accessOut.put("endTime",endTime);
		accessOut.put("processTime",String.valueOf(processTime));
		try {
			DataTranslate._String(ServiceManager.callJavaBeanService("InterfaceLogService","insertLog" ,accessOut));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return responseXml;

	}
	/**
	 * SP/CP业务能力同步
	 * @param requestXml 请求报文
	 * @return
	 */
	public String SPSignInfoSyn(String requestXml){
		String startTime=DAOUtils.getFormatedDate();//开始时间
		long start = System.currentTimeMillis();
		CommonAccessService aCommonAccessService=new SubsInstSynSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SPSignInfoSynAccessService");
//		2服务处理
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
//		3核心对象转换成接口出参
		String responseXml=null;
		Map accessOut=aCommonAccessService.out(serviceOut,"SPSignInfoSynAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); 
		long end = System.currentTimeMillis();
		long processTime=end-start;
//		5写接口日志
		accessOut.put("startTime",startTime);
		accessOut.put("endTime",endTime);
		accessOut.put("processTime",String.valueOf(processTime));
		try {
			DataTranslate._String(ServiceManager.callJavaBeanService("InterfaceLogService","insertLog" ,accessOut));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return responseXml;
		
	}
}
