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
 * �ⲿ���������ࣨ����ڷ���������ԣ� ��װ��������˷�����Ϊ���ֿͻ����ṩ����ÿ���ͻ��˵���һ������
 * ÿ����������InterfaceExample.example����ʵ�֡� �ͻ��˿����ǣ�VSOPҳ�桢VSOP�ⲿ�ӿڡ�VSOP��ʱ�����
 * 
 * @author cooltan
 * 
 */
public class VariedServerEngine {
	private static Logger logger = Logger.getLogger(VariedServerEngine.class);
	public static VariedServerEngine instance = new VariedServerEngine();
	private OrderDao orderDao;
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//ʡ�ݴ���

	public static VariedServerEngine getInstance() {
		return instance;
	}

	/**
	 * ��������ӿ�
	 * @param requestXml
	 * @return
	 */
	public String subscribeToVSOP(String requestXml) {		
	String responseXml=null;
	try {
		String startTime=DAOUtils.getFormatedDate();//��ʼʱ��
		long start = System.currentTimeMillis();
		CommonAccessService aCommonAccessService=new SubscribeReqSVAccessService();
		//����Map��Ϊ����Σ�ÿ��������ú󶼻���ԭ����map�����µ�key��
		//���벽��1ǰ��key includes :accessInObject accessCode   accessType
		//����1��ɺ���벽��2ǰ��key includes :accessInObject accessCode   accessType busiObject serviceCode
		//����2��ɺ���벽��3ǰ��key includes :accessInObject accessCode   accessType busiObject serviceCode retBusiObject resultCode resultMsg
		//����3��ɺ�key includes :accessInObject accessCode   accessType busiObject serviceCode  resultCode resultMsg accessOutObject
		//1�ӿ���ζ���ת���ɺ��Ķ���
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R1.1");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SubscribeReqSVAccessService");//CustomerOrder�Ѿ������ڷ��ص�accessInRet����
		if(null!=accessInRet.get("error") && "1".equals(accessInRet.get("error"))){
			String streamNo = XMLUtils.getSingleTagValue(requestXml, "StreamingNo");
			//ȫ���˶��У�������˶���ͳ+������ײͽ��з�����ʾ��Ϣ
			return toXml(streamNo,"1","�˶�ʧ��",(String) accessInRet.get("resultMsg"));
		}
		//2������
		OrderEngine aOrderEngine=new OrderEngine();
		//accessInRet.put("serviceCode", "0");
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3���Ķ���ת���ɽӿڳ���
		Map accessOut=aCommonAccessService.out(serviceOut,"SubscribeReqSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); //
		long end = System.currentTimeMillis();
		long processTime=end-start;//
		//4д�ӿ���־
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
	 * ������ϵͬ��
	 * ISMP��������ϵͬ����VSOP��Ϊͳһ������ϵ��ѯʹ�á�
	 * @param requestXml
	 * @return
	 */
	public String SubsInstSynFromISMP(String requestXml) {
		String startTime=DAOUtils.getFormatedDate();//��ʼʱ��
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubsInstSynFromISMPAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R8.4");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SubsInstSynFromISMPAccessService");//CustomerOrder�Ѿ������ڷ��ص�accessInRet����
		//2������
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3���Ķ���ת���ɽӿڳ���
		Map accessOut=aCommonAccessService.out(serviceOut,"SubsInstSynFromISMPAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); //
		long end = System.currentTimeMillis();
		long processTime=end-start;//
		//4д�ӿ���־
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
	 * ������Ȩ
	 * CRM��VSOP���𶩹���Ȩ����
	 * @param requestXml
	 * @return
	 */
	public String SubscribeAuthSV(String requestXml) {
		String startTime=DAOUtils.getFormatedDate();//��ʼʱ��
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubscribeAuthSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R2.2");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SubscribeAuthSVAccessService");//CustomerOrder�Ѿ������ڷ��ص�accessInRet����
		//2������
		List orderList = (List) accessInRet.get("busiObject"); //�˴�ΪcustomerOrder �б�
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
		//3���Ķ���ת���ɽӿڳ���
		Map accessOut=aCommonAccessService.out(allServiceOut,"SubscribeAuthSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		//4д�ӿ���־
		String endTime=DAOUtils.getFormatedDate(); //
		long end = System.currentTimeMillis();
		long processTime=end-start;//
		//4д�ӿ���־
		accessOut.put("busiObject", orderList.get(0)); //��д��־���ʱ���ǿת����ʱȡ��һ��
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
 * ����VSOPϵͳҳ��Ķ�������
 * @param staffCode ����
 * @param fromSystem ϵͳ��Դ
 * @param prodOfferNbrs����Ʒ����
 * @param accNbr�û���Ʒ����
 * @param lanCode�û���Ʒ������
 * @param mProductId�û���Ʒ����
 * @return
 */
	public Map orderProducts(String prodOfferNbrs, String accNbr,
			String lanCode, String mProductId, String staffCode, String fromSystem) throws Exception {
		String[] prodOfferIds=this.prodOfferNbrsToProdOfferIds(prodOfferNbrs);//������Ʒ����ת��������ƷID
		logger.debug("prodOfferIds:" + prodOfferIds.toString() + ",accNbr:"
				+ accNbr);
		// ����һ,ת��Ϊ���Ķ���
		
		
		Map prodOfferId_Products = null;// ����Ʒid�����Ӧ��ֵ��Ʒ��ӳ��
		prodOfferId_Products = AssemCoreEntityServices.getInstance().getProductVOByProdOfferIds(prodOfferIds);
		List prodOfferList = new ArrayList();// ����Ʒ�б�
		for (int i = 0; i < prodOfferIds.length; i++) {
			ProdOffVO prodOffVO = DcSystemParamManager.getInstance().getProdOffVOById(prodOfferIds[i]);
			prodOfferList.add(prodOffVO);
		}
		List productOfferInfoList = new ArrayList();// ��������Ʒ�б�
		for (Iterator iter = prodOfferList.iterator(); iter.hasNext();) {
			ProdOffVO prodOffVO = (ProdOffVO) iter.next();
			ProductOfferInfo prOffInfo = new ProductOfferInfo();// ��������Ʒ
			prOffInfo.setActioType("0");// ����
			prOffInfo.setOfferId(prodOffVO.getProdOffId());//����ƷID
			prOffInfo.setOfferNbr(prodOffVO.getOffNbr());//����Ʒ����
			prOffInfo.setEffDate(prodOffVO.getEffDate());//��Чʱ��
			prOffInfo.setExpDate(prodOffVO.getExpDate());//ʧЧʱ��
			prOffInfo.setOfferType(prodOffVO.getProdOffSubType());//����Ʒ����
			prOffInfo.setVproductInfoList((List) prodOfferId_Products.get(prodOffVO.getProdOffId()));
			productOfferInfoList.add(prOffInfo);//��������Ʒ
		}
		// ���Ҳ�Ʒʵ��
		ProdInstHelpDao prInDao = new ProdInstHelpDao();
		ProdInstVO prInVO = null;
		Map map = new HashMap();
		boolean exceptionFlag=false;
		Map resultMap=new HashMap();
		try {
			// �������ػ��������00X���û�ʵ��
			if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)) {
				// ��trueʱ��00X״̬�������
				prInVO = prInDao.qryProdInstByAccNbrAndProductId(accNbr, mProductId, true);
			} else {
				// ��falseʱֻ��00A��������00B��00C״̬Ϊ��Ч״̬��
				prInVO = prInDao
				.qryProdInstByAccNbrAndProductId(accNbr, mProductId,false);//target��falseʱֻ��00A��������00B��00C״̬Ϊ��Ч״̬��
			}
		} catch (Exception e) {
			logger.debug("ҳ�涩������: " + e.getMessage());
			 resultMap = new HashMap();
			resultMap.put("ResultCode", "-1");
			resultMap.put("ResultDesc", "û���ҵ���Ӧ��Ʒʵ��");
			exceptionFlag=true;
			throw e;
		}finally{
			if(exceptionFlag){//�쳣�����쳣��Ϣ
				return resultMap;
			}
		}
		CustomerOrder cuOrder = new CustomerOrder();// ��������
		cuOrder.setProductOfferInfoList(productOfferInfoList);// ���ö�������Ʒ
		cuOrder.setProdInst(prInVO);// �����û�
		cuOrder.setOrderSystem((fromSystem != null) ? fromSystem : SystemCode.VSOP);// ������Դϵͳ
		cuOrder.setLanId(prInVO.getLanId());// �û�lan
		cuOrder.setAccNbr(prInVO.getAccNbr());// ����
		cuOrder.setCustSoNumber(DateUtil.getVSOPDateTime14());// ��ˮ��ȡϵͳ��ǰ14λʱ��
		cuOrder.setStatus(OrderConstant.orderStateOfCreated);
		cuOrder.setProdInstId(prInVO.getProdInstId());// ��Ʒʵ��ID
		cuOrder.setProdId(prInVO.getProdId());// ��Ʒ����
		cuOrder.setOrderChn((fromSystem != null) ? fromSystem : SystemCode.VSOP);// order Channel
		cuOrder.setCustOrderType(OrderEngine.SERVICE_APPLY0_STR);//��������
		cuOrder.setStaffId(staffCode);
		// �������ֱ���÷����������ҳ�涩������
		VproductOrderService prOrService = new VproductOrderService();
		Map param = new HashMap();
		param.put("busiObject", cuOrder);
		param.put("accessCode", "0");
		param.put("accessType", "0");
		param.put("serviceCode", OrderEngine.SERVICE_APPLY0_STR);
		
		try {
			map = prOrService.concreteBusinessOpertions(param);
		} catch (Exception e) {
			logger.debug("ҳ�涩������: " + e.getMessage());
			 resultMap = new HashMap();
			resultMap.put("ResultCode", "-1");
			resultMap.put("ResultDesc", e.getMessage());
			exceptionFlag=true;
			throw e;
		}finally{
			if(exceptionFlag){//�쳣�����쳣��Ϣ
				return resultMap;
			}
		}
		// ���ݶ�����������������ϵͬ������ ����ͬ��
		if(this.provinceCode.equals(CrmConstants.GX_PROV_CODE)){
			//GX��ҳ�涩��ʱ����ͬ����ͳһ��R8.4ͬ��
		}else{
			String code=(String) map.get("resultCode");
			if (null != CRMSynchUtil.CRM_SYN
					&& "1".equals(CRMSynchUtil.CRM_SYN.trim()) && null != code && "0".equals(code)) {
				try {
					SynORInfoToCrmHelpDao sysDao = new SynORInfoToCrmHelpDao();
					sysDao.createChangeORQueueByCustomerOrder(cuOrder);//ҳ�����
				} catch (Exception e) {
					logger.debug("ҳ�涩������ͬ������: " + e.getMessage());
					 resultMap = new HashMap();
					resultMap.put("ResultCode", "-1");
					resultMap.put("ResultDesc", "ҳ�涩��-����ͬ������,����ϵ����Ա");
					exceptionFlag=true;
					throw e;
				}finally{
					if(exceptionFlag){//�쳣�����쳣��Ϣ
						return resultMap;
					}
				}
			}
		}

		String resultCode = (String) map.get("resultCode");// ������Ϣ
		String resultDesc = (String) map.get("resultMsg");// ������Ϣ

		// ������,���س��ζ���
		resultMap.put("ResultCode", resultCode);
		resultMap.put("ResultDesc", resultDesc);
		return resultMap;
	}

	/**
	 * ����ͨ��������ӿ�
	 * ֱ�ӱ���xml���ӿڱ�������ʱ����ɨ�账��
	 * @param requestXml
	 * @return
	 */
	public String workListFKToVSOP(String requestXml)  {
		String startTime=DAOUtils.getFormatedDate();//��ʼʱ��
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new WorkSheetAcceptSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R5.1");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"WorkSheetAcceptSVAccessService");//CustomerOrder�Ѿ������ڷ��ص�accessInRet����
		//2������
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3���Ķ���ת���ɽӿڳ���
		Map accessOut=aCommonAccessService.out(serviceOut,"WorkSheetAcceptSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		//4д�ӿ���־
		String endTime=DAOUtils.getFormatedDate(); //
		long end = System.currentTimeMillis();
		long processTime=end-start;//
		//4д�ӿ���־
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
	 * ����ͨ����������ʱ��������
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
		aCommonAccessService.in(accessIn,"WorkSheetAcceptJobAccessService");//CustomerOrder�Ѿ������ڷ��ص�accessInRet����
		//2������
		OrderEngine aOrderEngine=new OrderEngine();
		aOrderEngine.engine(accessIn);
		//3���Ķ���ת���ɽӿڳ���
		Map accessOut=aCommonAccessService.out(accessIn,"WorkSheetAcceptJobAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		//4д�ӿ���־
		return responseXml;
	}
	/**
	 * ������ϵ��ѯ�ӿ�
	 * @param requestXml
	 * @return
	 */
	public String subInstQryFromVSOP(String requestXml)  {
		String startTime=DAOUtils.getFormatedDate();//��ʼʱ��
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubsInstQrySVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R1.2");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SubsInstQrySVAccessService");//CustomerOrder�Ѿ������ڷ��ص�accessInRet����
		//2������
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3���Ķ���ת���ɽӿڳ���
		Map accessOut=aCommonAccessService.out(serviceOut,"SubsInstQrySVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		//4д�ӿ���־
		String endTime=DAOUtils.getFormatedDate(); //
		long end = System.currentTimeMillis();
		long processTime=end-start;//
		//4д�ӿ���־
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
	 * ���û��棬��ҳ�洫����������Ʒ����ת��Ϊ����ƷID
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
 * �˶�ѡ�����ֵ��Ʒ
 * @param relationIds
 * @return
 */
	public Map delOrdersByRelationId(String relationIds,String staff,String orderChannel) {
		Map in = new HashMap();
		try {
			//1.����ҳ�洫�͵Ķ�����ϵʵ����ʶ����ȡҪ�˶�����ֵ������ϵʵ���б�
			OrderRelationHelpDao orderRelationHelpDao = new OrderRelationHelpDao();
			List relations = orderRelationHelpDao.getReletionsByIds(relationIds);
			List productOfferInfoList = new ArrayList();
			List temp = new ArrayList();
			List reTemp = new ArrayList();//�����ظ�
			List result = new ArrayList();
			String accNbr = "";//��Ʒ����
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
				List vproductInfoList = new ArrayList();//��ֵ��Ʒ�б�List
				if(reTemp.contains(record)){
					for(int j=0;j<relations.size();j++){
						Map map = (Map)relations.get(j);
						if(record.equals((String)map.get("PACKAGE_ID")+(String)map.get("PPROD_OFFER_ID"))){
							String offerId = "";
							//���ݶ�����ϵʵ���б���Ʒ���������б�����ж϶�����ϵʵ��pprod_offer_id�ֶηǿ�ʱ����������Ʒofferid����ȡ����ֶ�ֵ��
							//offertype����2������package_id�ֶηǿ�ʱ��offerid����ȡ����ֶ�ֵ��offertype����1��������offerid����ֵȡprod_offer_id�ֶ�ֵ��offertype����0����
							if(!"".equals(map.get("PPROD_OFFER_ID"))&&map.get("PPROD_OFFER_ID")!=null){
								offerId = (String)map.get("PPROD_OFFER_ID");
								productOfferInfo.setOfferId(offerId);
								productOfferInfo.setOfferType("2");
								//��������ȡֵ��������˵������ȡֵ��
								productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));

							}
							else if(!"".equals(map.get("PACKAGE_ID"))&&map.get("PACKAGE_ID")!=null){
								offerId = (String)map.get("PACKAGE_ID");
								productOfferInfo.setOfferId(offerId);
								productOfferInfo.setOfferType("1");
								//��������ȡֵ��������˵������ȡֵ��
								productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
							}
							else{
								offerId = (String)map.get("PROD_OFFER_ID");
								productOfferInfo.setOfferId(offerId);
								productOfferInfo.setOfferType("0");
								//��������ȡֵ��������˵������ȡֵ��
								productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
							}
							//ͬʱ��Ҫ������ͬ������Ʒ�Ķ�����ϵʵ���鲢����������Ʒ��ֵ��Ʒ�б��£���ֵ��Ʒ����Ϊ1�˶���
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
					//��������ȡֵ��������˵������ȡֵ��
					productOfferInfo.setActioType(OrderConstant.orderTypeOfDel);					
				}
				else{
						String offerId = "";
						//���ݶ�����ϵʵ���б���Ʒ���������б�����ж϶�����ϵʵ��pprod_offer_id�ֶηǿ�ʱ����������Ʒofferid����ȡ����ֶ�ֵ��
						//offertype����2������package_id�ֶηǿ�ʱ��offerid����ȡ����ֶ�ֵ��offertype����1��������offerid����ֵȡprod_offer_id�ֶ�ֵ��offertype����0����
						if(!"".equals(resultMap.get("PPROD_OFFER_ID"))&&resultMap.get("PPROD_OFFER_ID")!=null){
							offerId = (String)resultMap.get("PPROD_OFFER_ID");
							productOfferInfo.setOfferId(offerId);
							productOfferInfo.setOfferType("2");
							//��������ȡֵ��������˵������ȡֵ��
							productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
						}
						else if(!"".equals(resultMap.get("PACKAGE_ID"))&&resultMap.get("PACKAGE_ID")!=null){
							offerId = (String)resultMap.get("PACKAGE_ID");
							productOfferInfo.setOfferId(offerId);
							productOfferInfo.setOfferType("1");
							//��������ȡֵ��������˵������ȡֵ��
							productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
						}
						else{
							offerId = (String)resultMap.get("PROD_OFFER_ID");
							productOfferInfo.setOfferId(offerId);
							productOfferInfo.setOfferType("0");
							//��������ȡֵ��������˵������ȡֵ��
							productOfferInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(offerId));
						}
						//ͬʱ��Ҫ������ͬ������Ʒ�Ķ�����ϵʵ���鲢����������Ʒ��ֵ��Ʒ�б��£���ֵ��Ʒ����Ϊ1�˶���
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
						//��������ȡֵ��������˵������ȡֵ��
						productOfferInfo.setEffDate((String)resultMap.get("EFF_DATE"));
						productOfferInfo.setExpDate((String)resultMap.get("EXP_DATE"));
						productOfferInfo.setActioType(OrderConstant.orderTypeOfDel);	
				}
				productOfferInfoList.add(productOfferInfo);
			}
			ProdInstHelpDao pInstHelpDao = new ProdInstHelpDao();
//			ProdInstVO prodInstVo = pInstHelpDao.qryProdInstByAccNbrAndLanCode(accNbr, null);
			ProdInstVO prodInstVo=null;
			// �������ػ��������00X���û�ʵ��
			if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)) {
				// ��trueʱ��00X״̬�������
				prodInstVo = pInstHelpDao.qryProdInstByAccNbrAndLanCode_GX(accNbr,null);
			} else {
				// ��falseʱֻ��00A��������00B��00C״̬Ϊ��Ч״̬��
				prodInstVo = pInstHelpDao.qryProdInstByAccNbrAndLanCode(accNbr, null);//target��falseʱֻ��00A��������00B��00C״̬Ϊ��Ч״̬��
			}
			
			CustomerOrder order = new CustomerOrder();
			order.setCustOrderType(OrderConstant.orderTypeOfDel);
			//crm Ϊ201��vsopΪ200
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
			//��������
			order.setStaffId(staff);
		
			//�˶�
			in.put("busiObject", order);
			in.put("serviceCode", OrderEngine.SERVICE_CANCEL1_STR);
			AbstractBusinessService abstractBusinessService = new VProductCancelService();
			abstractBusinessService.concreteBusinessOpertions(in);
			String code=(String) in.get("resultCode");
			
			if(this.provinceCode.equals(CrmConstants.GX_PROV_CODE)){
				//GX����ҳ���˶�����ͬ����ֻR8.4ͬ��
			}else{
				if (null != CRMSynchUtil.CRM_SYN
						&& "1".equals(CRMSynchUtil.CRM_SYN.trim()) && null != code && "0".equals(code)) {
					//���ݶ�����������������ϵͬ������ ����ͬ��
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
	 * ����������ϵͬ��
	 * ISMP��������ϵͬ����VSOP��Ϊͳһ������ϵ��ѯʹ�á�
	 * @param requestXml
	 * @return
	 */

	public com.chinatelecom.ismp.crm.rsp.Response SubsInstSynFromJXISMP(com.chinatelecom.ismp.crm.req.SubscriptionSyncReq requestObj) {
		com.chinatelecom.ismp.crm.rsp.Response responseObj=null;
		try {
			String startTime=DAOUtils.getFormatedDate();//��ʼʱ��
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
			//1���
			Map accessInRet=aCommonAccessService.in(accessIn,"SubsInstSynFromJXISMPAccessService");//CustomerOrder�Ѿ������ڷ��ص�accessInRet����
			//2������
			OrderEngine aOrderEngine=new OrderEngine();
			Map serviceOut=aOrderEngine.engine(accessInRet);
			//3���Ķ���ת���ɽӿڳ���
			Map accessOut=null;
			accessOut=aCommonAccessService.out(serviceOut,"SubsInstSynFromJXISMPAccessService");
			responseObj=(com.chinatelecom.ismp.crm.rsp.Response)accessOut.get("accessOutObject");
			logger.info("ismp SubscriptionSync response:resultCode:" + responseObj.getResultCode() + ",StreamingNo:" + responseObj.getStreamingNo());
			String endTime=DAOUtils.getFormatedDate(); 
			long end = System.currentTimeMillis();
			long processTime=end-start;
			//4д�ӿ���־
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
	 * Title:���Ź淶 Xƽ̨ͬ��������ϵ��VSOP
	 * Description: ������ϵͬ��(Xƽ̨-VSOP)
	 * </pre>
	 * 
	 * @author xulei xu.lei55@zte.com.cn
	 * @version 1.00.00
	 * 
	 * <pre>
	 * �޸ļ�¼
	 * �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����:
	 * </pre>
	 */
	public String SubsInstSynToVSOPSV(String requestXml) {
		String startTime=DAOUtils.getFormatedDate();//��ʼʱ��
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubsInstSynToVSOPSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R7.8");
		accessIn.put("accessType", "");
		
		//1��� //CustomerOrder�Ѿ������ڷ��ص�accessInRet����
		Map accessInRet=aCommonAccessService.in(accessIn,"SubsInstSynSVAccessService");
		//2������
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		
		//3���Ķ���ת���ɽӿڳ���
		Map accessOut=aCommonAccessService.out(serviceOut,"SubsInstSynSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); 
		long end = System.currentTimeMillis();
		long processTime=end-start;
		
		//4д�ӿ���־
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
	 * ������ϵͬ��
	 * Xƽ̨��������ϵͬ����VSOP��Ϊͳһ������ϵ��ѯʹ�á�
	 * @param requestXml
	 * @return
	 */
	public String subsInstSynSV(String requestXml) {
		String startTime=DAOUtils.getFormatedDate();//��ʼʱ��
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubsInstSynSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R7.1");
		accessIn.put("accessType", "");
		//1���
		Map accessInRet=aCommonAccessService.in(accessIn,"SubsInstSynSVAccessService");//CustomerOrder�Ѿ������ڷ��ص�accessInRet����
		//2������
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3���Ķ���ת���ɽӿڳ���
		Map accessOut=aCommonAccessService.out(serviceOut,"SubsInstSynSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); 
		long end = System.currentTimeMillis();
		long processTime=end-start;
		//4д�ӿ���־
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
	 * CRM�Ż�ͬ��
	 * CRM���Ż�ʵ��ͬ����VSOP
	 * @param requestXml
	 * @return
	 */
	public String prodOfferCrmToVsopSyn(String requestXml) {
		String startTime=DAOUtils.getFormatedDate();//��ʼʱ��
		long start = System.currentTimeMillis();
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubsInstSynSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R20.1");
		accessIn.put("accessType", "");
		//1���
		Map accessInRet=aCommonAccessService.in(accessIn,"OfferCrmToVsopSynAccessService");//CustomerOrder�Ѿ������ڷ��ص�accessInRet����
		//2������
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
		
		//3 ˢ�»���
		String offerId= (String)serviceOut.get("offerId");
		if(null != offerId && !"".equals(offerId)){
			//����ˢ������Ʒ�����Ϣ
			RefreshCacheHttpClient.getInstance().refreshAllServerCaches(DcSystemParamManager.CACHE_OBJECTTYPE_PRODOFFER1,offerId);
		}
		//4���Ķ���ת���ɽӿڳ���
		Map accessOut=aCommonAccessService.out(serviceOut,"OfferCrmToVsopSynAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); 
		long end = System.currentTimeMillis();
		long processTime=end-start;
		//5д�ӿ���־
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
	 * SP/CPҵ������ͬ��
	 * @param requestXml ������
	 * @return
	 */
	public String SPSignInfoSyn(String requestXml){
		String startTime=DAOUtils.getFormatedDate();//��ʼʱ��
		long start = System.currentTimeMillis();
		CommonAccessService aCommonAccessService=new SubsInstSynSVAccessService();
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SPSignInfoSynAccessService");
//		2������
		OrderEngine aOrderEngine=new OrderEngine();
		Map serviceOut=aOrderEngine.engine(accessInRet);
//		3���Ķ���ת���ɽӿڳ���
		String responseXml=null;
		Map accessOut=aCommonAccessService.out(serviceOut,"SPSignInfoSynAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		String endTime=DAOUtils.getFormatedDate(); 
		long end = System.currentTimeMillis();
		long processTime=end-start;
//		5д�ӿ���־
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
