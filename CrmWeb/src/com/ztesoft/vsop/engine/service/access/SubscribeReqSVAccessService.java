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
 * ��������ӿڽ������
 * @author panshaohua
 *
 */
public class SubscribeReqSVAccessService extends CommonAccessService implements IAccess{
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//ʡ�ݴ���
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
		//�������ػ���ȫ���˶���,(��ͳ+��ֵ)����Ʒ�˶�ʧ�ܱ�־,
		//successAll �������У���ʾ�����̲���ȫ���ɹ��������ǲ��ֳɹ���������ȫ��ʧ��,��ҵ�����Ӱ��
		//successALLReson��ʾ��ȫ���ɹ����ص�ԭ��
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
		//�������ػ���ȫ���˶���,(��ͳ+��ֵ)����Ʒ�˶�ʧ�ܱ�־, ��ʱ����resultCode��0
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
			resultMsg="�˶�ʧ��!";
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
	 * �������󴮵õ�������Ϣ
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
		String productId = XMLUtils.getSingleTagValue(inXML, "ProdSpecCode");//����Ʒid
		String accNbr = XMLUtils.getSingleTagValue(inXML, "ProductNo");//��Ʒ����
		order.setProdId(productId);
		order.setAccNbr(accNbr);
		order.setStatus(OrderConstant.orderStateOfCreated);
		order.setOtherSysOrderId(XMLUtils.getSingleTagValue(inXML, "OrderId"));
		//�������ػ�����
		//yi.chengfeng 2010-7-10 ֧�ֲ�Ʒ���ʹ���Ʒ���� start
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			AssemCoreEntityServices aAssemCoreEntityServices=new AssemCoreEntityServices();
			aAssemCoreEntityServices.resetProdInstInfo(order);
		}
		//yi.chengfeng 2010-7-10 ֧�ֲ�Ʒ���ʹ���Ʒ���� end
		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
		ProdInstVO prodInst=null;
		// �������ػ��������00X���û�ʵ��
		if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)) {
			// ��trueʱ��00X״̬�������
			prodInst = prodInstDao.qryProdInstByAccNbrAndProductId(order
					.getAccNbr(), order.getProdId(), true);
		} else {
			// ��falseʱֻ��00A��������00B��00C״̬Ϊ��Ч״̬��
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
		//���ҵ����ʱ�˶�����
		if(OrderConstant.orderTypeOfAll.equals(order.getCustOrderType())){
			//Map ret = new OrderDao().getOrderedVproducts(order.getProdId(),order.getAccNbr(),OrderConstant.orderTypeOfDel);//actionType ���˶��ģ���Ȩ�����˶����߼�
			//������˶�ȫ������Ҫ�鴦������Ч�Ķ�����ϵ��Ȼ��ת��Ϊ��ֵ����Ʒ����
			//*********
			//�������ػ�,ȫ���˶�����£����ڴ�ͳ+��ֵ�򷵻���ʾ��Ϣ,���ڼ�Ȩ�޷��������������׳��쳣����ʶ��
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
	 * �˶����У��õ����е�����Ʒ
	 * @param prodInstId
	 * @throws Exception 
	 */
	private void getAllProdOfferByProdInstId(String prodInstId,CustomerOrder order) throws Exception {
		OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
		//���ݲ�Ʒʵ���õ����еĶ�����ϵ
		List orderRelations = orderRelationDao.qryORSByProdInstId(prodInstId);
		List prodOfferList = new ArrayList();
		List pprodOfferIdList= new ArrayList();
		int pprodOfferNum = 0;//����Ʒ+��������Ʒ����
		Map offermap = new HashMap();
		for (int i = 0; i < orderRelations.size(); i++) {
			OrderRelationVO orderRelation = (OrderRelationVO) orderRelations.get(i);
			String packageId= orderRelation.getPackageId();
			String pProdOfferId = orderRelation.getPprodOffId();
			String prodOfferId = orderRelation.getProdOffId();
			String offerType = "0"; //����ֵ
			if(null != packageId && !"".equals(packageId)){
				offerType="1";
				prodOfferId =packageId;
			}else if(null != pProdOfferId && !"".equals(pProdOfferId)){
				pprodOfferIdList.add(prodOfferId);
				offerType = "2"	;
				prodOfferId =pProdOfferId;
				pprodOfferNum++;
			}
			ProductOfferInfo productOfferInfo =(ProductOfferInfo) offermap.get(prodOfferId); //�Ƿ��Ѿ���ӽ�map,�����жϣ�����Ѿ���ӽ�ȥ���Ͳ��������
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
			product.setActionType("1"); //�˶�
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
    	//�������ػ�,ȫ���˶��������ڴ�ͳ+��ֵ�Ļ��������˶���ͳ+��ֵ����
    	//successAll �������У���ʾ�����̲���ȫ���ɹ��������ǲ��ֳɹ���������ȫ��ʧ��,��ҵ�����Ӱ��
    	//**********
		if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)
				&& pprodOfferNum != 0) {
			order.setSuccessAll(false);
			int orSize = orderRelations.size();
			if (orSize == 0) {
				throw new Exception("û�п����˶�����ֵ��Ʒ!");
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
					bf.append("��ֵ��Ʒ")
						.append(prodOfferName)
						.append("�������Ż��ײ�")
						.append(",����ͨ��Ӫҵ��ȡ���Ż��ײͽ����˶�!");
					resultInfoMap.put("OPDesc",bf.toString());
					resultInfoList.add(resultInfoMap);
				}
				order.setSuccessAllReason(resultInfoList);
			}
		}else{
	    	int orSize = orderRelations.size() ;
	    	if(orSize==0 || ( orSize>0 && orSize == pprodOfferNum)){
	    		throw new Exception("û�п����˶�����ֵ��Ʒ!");
	    	}
		}
    	order.setProductOfferInfoList(prodOfferList);
	}
	/**
	 * ��֤�Ƿ�������Ʒ
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
	 * ��������Ʒ��Ϣ
	 * @param inXML
	 * @throws Exception
	 */
	private void parseProductOfferInfo(String inXML,CustomerOrder order) throws Exception {
		//������˶����в���Ҫ�����ýڵ�
		if (OrderConstant.orderTypeOfAll.equals(order.getCustOrderType())) {
			return;
		}
		String tagName = "ProductOfferInfo";
		StringBuffer bf = new StringBuffer();
		bf.append("<").append(tagName).append(">([\\s\\S]*?)</").append(tagName).append(">");
		Pattern pattern = Pattern.compile(bf.toString());
		Matcher matcher = pattern.matcher(inXML);
		List productOfferInfos = new ArrayList();
		//�����ڷ�ȫ���˶�������£��˶��������ͳ+��ֵ�ڵ�ǰ�ļ�Ȩ���޷�����������ʱ�����׳�Exception�ķ�������
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
	 * ����xml�õ�����Ʒ����
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
			//�������ػ���������ͨ��
			if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE))){
				// 
			}else{
				if(null ==productOfferId ||"".equals(productOfferId)){
					throw new Exception("����Ʒ״̬������!");
				}
			}
			prodOfferInfo.setOfferId(productOfferId);
		}else{
			prodOfferInfo.setOfferId(XMLUtils.getSingleTagValue(inXML, "ProductOfferID"));
			prodOfferInfo.setOfferNbr(offNbr);
		}
		logger.info("replaceProductOfferId cost:" +(System.currentTimeMillis()-s));
		//setProdSpecCode(prodSpecCode);----- û������ֶ�
		prodOfferInfo.setActioType(actionType);
		s = System.currentTimeMillis();
		parseVSubProdInfo(inXML,prodOfferInfo, order);
		logger.info("parseVSubProdInfo cost:" +(System.currentTimeMillis()-s));
		//���ҵ�����Ƕ���������VSubProdInfo�ڵ��ǿյģ�Ҳ����û����ֵ��Ʒ�����ʾ����������Ʒ�µ�������ֵ��Ʒ
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
				//��ֵ��Ʒ����
				vprodInfo.setVProductNbr(DcSystemParamManager.getInstance().getProductNbrById((String) vProductIdlist.get(i)));
				//����Ʒ����
				vprodInfo.setOfferNbr(DcSystemParamManager.getInstance().getProdOfferNbrById(prodOfferInfo.getOfferId()));
				vproductList.add(vprodInfo);
			}
			prodOfferInfo.setVproductInfoList(vproductList);
		}
		//���ҵ�������˶�,����VSubProdInfo�ڵ��ǿյģ�Ҳ����û����ֵ��Ʒ�����ʾ�˶�����Ʒ�µ�������ֵ��Ʒ
		else if(OrderConstant.orderTypeOfDel.equals(actionType) && prodOfferInfo.getVproductInfoList().size() == 0){
			List vproductList = null;
			String prodInstId = order.getProdInst().getProdInstId();
			String prodOfferId = prodOfferInfo.getOfferId();
			String prodOfferNbr=prodOfferInfo.getOfferNbr();
			VproductHelp vproductHelp = new VproductHelp();
			String offerType = prodOfferInfo.getOfferType();
			if(offerType.equals(OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID)){//��������Ʒ�˶�ʱ
				vproductList=vproductHelp.getVProductsByPackageId(prodOfferId, prodInstId);
			}else if(offerType.equals(OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID)){//����ֵ����Ʒ
				vproductList = vproductHelp.getVProductsByProdOfferId(prodOfferId, prodInstId);
			}else if(offerType.equals(OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID)){//(��ͳ+��ֵ)��������Ʒʱ
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
				throw new Exception("��û�ж����κ�����Ʒ!");
			}
			prodOfferInfo.setVproductInfoList(vproductList);
		}
		return prodOfferInfo;
	}
	/**
	 * ������ֵ��Ʒ��Ϣ
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
	 * �������õ���ֵ��Ʒ��Ϣ
	 * @param order TODO
	 */
	public VproductInfo getVproductInfo(String inXML,ProductOfferInfo prodOfferInfo, CustomerOrder order ) throws Exception {
		VproductInfo vprodInfo = new VproductInfo();
		String vproductNbr = XMLUtils.getSingleTagValue(inXML, "VProductNbr");
		long s = System.currentTimeMillis();
		if("".equals(vproductNbr)){  //��û��VProductNbr�ڵ���ȡVProductID��Ȼ��ת��
			String prodId = XMLUtils.getSingleTagValue(inXML, "VProductID");
			if(null == prodId || "".equals(prodId)) return null;
			vprodInfo.setVProductNbr(prodId);
			replaceVproductId(vprodInfo);
		}else{  //�����Ƕ���ȷ�ϵ�ʱ����������õ�
			vprodInfo.setVProductNbr(vproductNbr);
			vprodInfo.setVProductId(XMLUtils.getSingleTagValue(inXML, "VProductID"));
		}
		
		vprodInfo.setEffDate(prodOfferInfo.getEffDate()); //Ŀǰ������Ʒ��ʱ��Ϊ׼ 
		vprodInfo.setExpDate(prodOfferInfo.getExpDate());
		vprodInfo.setActionType(prodOfferInfo.getActioType());
		vprodInfo.setOfferNbr(prodOfferInfo.getOfferNbr());
		SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
		String orderRelationId  = sequenceManage.getNextSequence("SEQ_ORDER_RELATION_ID");
		vprodInfo.setVProductInstID(orderRelationId);
		vprodInfo.setOfferId(prodOfferInfo.getOfferId());
		String offerType = prodOfferInfo.getOfferType();
		vprodInfo.setOfferType(offerType);
		//�������ػ��޸�:������ֵ��Ʒ�����ѯ����Ʒ���� start yi.chengfeng 2010-07-14
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
		//it��ļ���ƽ̨���⴦��
		if(SystemCode.CT10000.equals(systemId) || SystemCode.SEAT_10000.equals(systemId) ||
				SystemCode.SMS.equals(systemId) || SystemCode.VOICE_10000.equals(systemId) || SystemCode.WAP.equals(systemId)){
			//����ֵ����Ʒ����û�д�����Ʒ�����������ֵ��Ʒ�����ѯ����Ʒ����
			if(OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID.equals(prodOfferInfo.getOfferType()) && (null == prodOfferInfo.getOfferId() || "".equals(prodOfferInfo.getOfferId())) ){
				String offerId = DcSystemParamManager.getInstance().getofferIdByProductId(vProductID);
				String offerNbr = DcSystemParamManager.getInstance().getProdOfferNbrById(offerId);
				if(null == offerNbr) throw new Exception("����Ʒ������!");
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
