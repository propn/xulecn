package com.ztesoft.vsop.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.dao.ProdOfferSynDAO;
import com.ztesoft.vsop.order.exception.ProductHasNoPlatformException;
import com.ztesoft.vsop.order.vo.VProductInfo;
import com.ztesoft.vsop.order.vo.request.SubscribeToVSOPRequest;
import com.ztesoft.vsop.order.vo.request.WorkListFKToVSOPRequest;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.vo.InfProdOfferInst;
import com.ztesoft.vsop.webservice.vo.OfferDetailInfo;

public class ProdOfferSynBO {

	private static Logger logger = Logger.getLogger(ProdOfferSynBO.class);
	/**
	 * ��������CRM���Ż�ͬ����������
	 * ֱ�ӱ������ݵ��ӿڱ�������ʱ����ɨ�账��
	 * @param requestXml
	 * @return
	 */
	public String prodOfferCrmToVsopSyn(String requestXml)  {
		logger.info("orderBo.prodOfferCrmToVsopSyn start request:" + requestXml);
		boolean result = false;;
		String responseXml = "";
		String streamingNo = "";
		try {
			InfProdOfferInst order = new InfProdOfferInst();
			String xmlParseType = DcSystemParamManager.getParameter(VsopConstants.XML_PARSE_TYPE);
			if("string".equalsIgnoreCase(xmlParseType)){
				order.loadFromXml(requestXml);
			}else{
				order.loadFromXml(requestXml);
			}
			streamingNo = order.getStreamingNo();
			ProdOfferSynDAO offerDao = new ProdOfferSynDAO();
			String seq = "";
			
			List detailList = order.getOfferDetailInfo();
			//�ж��Ƿ���Ҫ�����Ż�ʵ��
			int num = 0;
			if(detailList != null){
				for(int i=0; i<detailList.size();i++){
					OfferDetailInfo detailVo = (OfferDetailInfo)detailList.get(i);
					//�ж��Ƿ���Ҫͬ����vsop���Ż��µ��Ż���ϸID������vsop��product���д��ڲ���Ҫͬ��
					if(isSyn(detailVo)){
						if(num == 0)seq = offerDao.getSequence("SEQ_INF_OFFER_INSTANCE_ID");
						order.setInfOfferInstanceId(seq);
						num++;
						//�����Ż�ʵ����ϸ�ӿڱ�
						result=offerDao.insertInfProdOffDetail(order, detailVo);
					}else result=true;
				}
			}
			//ֻ��vsop��Ҫͬ�����ŻݲŽ����Ż�ʵ���ӿڱ�
			if(num > 0){
				//�����Ż�ʵ���ӿڱ�
				result=offerDao.insertInfProdOffInst(order);
			}
			if(!result){
				LegacyDAOUtil.rollbackOnException();
				return getResult(streamingNo, "1", "�Żݽӿڱ��Ѿ������Ż�ʵ������!");
			}
		} catch (Exception e) {
			logger.info("#workListFKToVSOP ex.", e);
			responseXml = getResult(streamingNo, "1", e.getMessage());
			LegacyDAOUtil.rollbackOnException();
		}finally{
			LegacyDAOUtil.commitAndCloseConnection();
		}
		if(result){
			responseXml = getResult(streamingNo, "0", null);
		}else{
			responseXml = getResult(streamingNo, "1", null);
		}
		return responseXml;
	}
	private boolean isSyn(OfferDetailInfo detailVo) throws Exception{
		return new ProdOfferSynDAO().isSyn(detailVo);
	}
	private String getResult(String streamingNo,String resultCode,String resultName){
		String retXml = "";
		StringBuffer bf = new StringBuffer("");
//		String resultName = "";
		if(resultName == null || "".equals(resultName)){
			if(resultCode.equals("0")) resultName = "�ɹ�";
			else resultName = "ʧ��";
		}
		bf.append("<ProdOfferSyncCrmToVSOPResp>")
		.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
		.append("<ResultCode>").append(resultCode).append("</ResultCode>")
		.append("<ResultDesc>").append(resultName).append("</ResultDesc>")
		.append("</ProdOfferSyncCrmToVSOPResp>");
		retXml = bf.toString();
		return retXml;
	}	
	/**
	 * �ӽӿ���Ϣ���ȡ������ļ�¼
	 * @param threadId 
	 * @param size
	 * @return
	 */
	public List getUnDealOrders(int limit) {
		List ret = null;
		try {
			ret = new ProdOfferSynDAO().getUnDealOrders(limit);//״̬����Ϊ������
		} catch (SQLException e) {
			logger.info("#getUnDealOrders ex.", e);
		}finally{
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return ret;
	}
	public boolean dealOfferSync(InfProdOfferInst vo) throws Exception{
		String operType = vo.getOperType();
		//ȡ���Ż���ϸ�б�
		List offerDetail = getOfferDetail(vo.getInfOfferInstanceId());
		//��Ʒʵ��������
		if(offerDetail == null || offerDetail.size() == 0){
			if("D".equals(operType) || "M".equals(operType)){
				//�鵵��ɾ����ϸ�ӿڱ�����
				insertOfferDetail(vo.getInfOfferInstanceId());
				delOfferDetail(vo.getInfOfferInstanceId());
				//�鵵��ɾ���ӿڱ�����
				insertOffer(vo.getInfOfferInstanceId());
				delOffer(vo.getInfOfferInstanceId());
				LegacyDAOUtil.commitAndCloseConnection();
				return true;
			}else return false;
		}
		//��ǣ�����Ż�ʵ�����¼ֻ����һ��
		boolean onlyOne = true;
		int num = 0;//�¼��Ż�ʱ��Ҫ�ж�ÿ����ϸ���Ѹ��µ�������ϵ���С�
		for(int i = 0; i<offerDetail.size();i++){
			OfferDetailInfo detailVo = (OfferDetailInfo)offerDetail.get(i);
			try{
				//������Լ�Żݲ���ʱ������Ҫ���κ��жϣ�ֱ�Ӹ����Ż�ʵ�����Ż���ϸʵ������
				if("M".equals(operType)){
					//��Լ
					if(onlyOne){
						updateOfferInst(vo);
						onlyOne = false;
					}
					udpateOfferDetailInst(vo, detailVo);
					continue;
				}
				//�ж��Ƿ�ɼ���ִ�в����߼���vsop�Ƿ��Ѿ����Ż��¶�Ӧ����ֵ��Ʒ������������²������Żݣ�����ת��ȴ�
				String[] _value = isContinue(detailVo).split("#");
				if(_value != null && _value.length >= 2){
					List list = getProductId(_value[1], detailVo.getProductId());
					//��crm�������˳��Żݲ����Ͳ���Ҫ�ж��Ż��µ���ֵ��Ʒ�Ƿ��VSOP������ȴ�
					if("D".equals(operType)){
						//ȡ��
						if(onlyOne){
							//�Ż�ʵ�����Ż�ʵ����ϸһͬɾ��
							delOfferInst(vo.getOfferInstId());
							onlyOne = false;
						}
						for(int k=0;k<list.size();k++){
							String[] strs = ((String)list.get(k)).split(",");
							String productId = strs[0];
							String ismpCode = strs[1]; //����
							detailVo.setProductNbr(ismpCode);
							//���¶�����ϵ���ֶ�,�˳��Ż�
							updateOrderRela(detailVo, "", productId);
							//�ͼ���
							//����VSOPһ�׶���ʱ�Դ�ͳ+��ֵ����ƽ̨����
	//						createOrderForSPI(productId, vo, detailVo);
						}
						continue;
					}
					if(_value[0] != null && !"".equals(_value[0]) && Integer.parseInt(_value[0]) > 0){
						//ȡ���Ѿ���vsop���ɶ�����ϵʵ������ֵ��ƷID�������Ż�
						if("A".equals(operType)){
							//����
							//�����Ż�ʵ��
							if(onlyOne){
								insertOfferInst(vo);
								onlyOne = false;
							}
							//�����Ż���ϸʵ����
							insertOfferDetailInst(vo, detailVo);
							for(int k=0;k<list.size();k++){
								String[] strs = ((String)list.get(k)).split(",");
								String productId = strs[0];
								String ismpCode = strs[1]; //����
								detailVo.setProductNbr(ismpCode);
								//���¶�����ϵ���ֶ�,�����Ż�
								updateOrderRela(detailVo, vo.getOfferId(), productId);
								//�ͼ���,�ͼ���ʹ������
								//����VSOPһ�׶���ʱ�Դ�ͳ+��ֵ����ƽ̨����
	//							createOrderForSPI(productId, vo, detailVo);
							}
							num++;
						} 
					}
				}
			}catch(Exception e){
				LegacyDAOUtil.rollbackOnException();
				logger.info("error:"+e.getMessage());
				throw e;
			}
		}
		if("A".equals(operType)){
			if(num == offerDetail.size()){
				//�鵵��ɾ����ϸ�ӿڱ�����
				insertOfferDetail(vo.getInfOfferInstanceId());
				delOfferDetail(vo.getInfOfferInstanceId());
				//�鵵��ɾ���ӿڱ�����
				insertOffer(vo.getInfOfferInstanceId());
				delOffer(vo.getInfOfferInstanceId());
				LegacyDAOUtil.commitAndCloseConnection();
				return true;
			}
			else return false;
		}
		//�鵵��ɾ����ϸ�ӿڱ�����
		insertOfferDetail(vo.getInfOfferInstanceId());
		delOfferDetail(vo.getInfOfferInstanceId());
		//�鵵��ɾ���ӿڱ�����
		insertOffer(vo.getInfOfferInstanceId());
		delOffer(vo.getInfOfferInstanceId());
		LegacyDAOUtil.commitAndCloseConnection();
		return true;
	}
	/**
	 * �쳣����ӿڱ����ݣ����ʹ�����1
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void dealOperException(InfProdOfferInst vo) throws Exception{
		new ProdOfferSynDAO().dealOperException(vo);
		LegacyDAOUtil.commitAndCloseConnection();
	}
	/**
	 * ����״̬��Ϊʧ�ܣ��ȴ�����
	 * @param vo
	 * @throws Exception
	 */
	public void dealOperWait(InfProdOfferInst vo) throws Exception{
		new ProdOfferSynDAO().dealOperWait(vo);
		LegacyDAOUtil.commitAndCloseConnection();
	}
	private List getOfferDetail(String id) throws Exception{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.getOfferDetail(id);
	}
	/**
	 * �ж��Ƿ�ɼ���ִ�в����߼���vsop�Ƿ��Ѿ����Ż��¶�Ӧ����ֵ��Ʒ������������²������Żݣ�����ת��ȴ�
	 * @param detailVo
	 * @return
	 * @throws Exception
	 */
	private String isContinue(OfferDetailInfo detailVo) throws Exception{
		return new ProdOfferSynDAO().isContinue(detailVo);
	}
	private boolean insertOfferInst(InfProdOfferInst vo) throws Exception{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.insertOfferInst(vo);
	}
	private boolean insertOfferDetailInst(InfProdOfferInst vo, OfferDetailInfo detailVo) throws Exception{
		//����ǰ��ɾ��
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.insertOfferDetailInst(vo, detailVo);
	}
	private boolean updateOfferInst(InfProdOfferInst vo) throws Exception{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.updateOfferInst(vo);
	}
	private boolean udpateOfferDetailInst(InfProdOfferInst vo, OfferDetailInfo detailVo)throws Exception{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.udpateOfferDetailInst(vo, detailVo);
	}
	private boolean delOfferInst(String offerInstId) throws Exception{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		dao.delOfferInst(offerInstId);
		dao.delOfferDetailInst(offerInstId);
		return true;
	}
	private boolean updateOrderRela(OfferDetailInfo detailVo, String offerId, String productId)throws Exception{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.updateOrderRela(detailVo.getProductId(), offerId, productId);
	}
	private List getProductId(String str, String prodInstId)throws Exception{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.getProductId(str, prodInstId);
	}
	/**
	 * ������ģ�����ɶ��� 
	 * @throws ProductHasNoPlatformException 
	 * @throws SQLException 
	 */
	private void createOrderForSPI(String productId, InfProdOfferInst vo, OfferDetailInfo detailVo) throws ProductHasNoPlatformException, SQLException {
		// �����ʺϵ�xml
		logger.info("createOrderForSPI start");
		try {
			String spiXML = createSPIXML(productId, vo, detailVo);
			logger.info(spiXML);
			// ���浽���ݿ�
			long inOrderId = Long.parseLong(vo.getInfOfferInstanceId());
			String unuseClob = DcSystemParamManager.getParameter(VsopConstants.UNUSE_CLOB);
			OrderDao orderDao = new OrderDao();
			//ϵͳ�����ݶ�ϵͳ��ԴΪʡ��CRM:201
			if("true".equalsIgnoreCase(unuseClob)){
				orderDao.saveXMLWithoutClob(spiXML,"201", inOrderId ,detailVo.getAccNbr(),detailVo.getLanId());
			}else{
				orderDao.saveXML(spiXML,"201", inOrderId ,detailVo.getAccNbr(),detailVo.getLanId());
			}
		} catch (SQLException e) {
			LegacyDAOUtil.rollbackOnException();
			logger.error("#createOrderForSPI ex.", e);
			throw e;
		}
	}
	private String createSPIXML(String productId, InfProdOfferInst vo, OfferDetailInfo detailVo) throws SQLException, ProductHasNoPlatformException {
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<root>")
			.append("<msg_head>")
				.append("<from>").append("201").append("</from>")
				.append("<to>").append("VSOP").append("</to>")
				.append("<msg_type>").append("message_request").append("</msg_type>")
				.append("<serial>").append(vo.getStreamingNo()).append("</serial>")
				.append("<out_order_type>").append("VSOP001").append("</out_order_type>")
			.append("</msg_head>")
			
			.append("<interface_msg>")
				.append("<public>")
					.append("<order_id>").append(vo.getInfOfferInstanceId()).append("</order_id>")
					.append("<out_order_id>").append(vo.getInfOfferInstanceId()).append("</out_order_id>")
					.append("<order_act_type>").append(vo.getOperType()).append("</order_act_type>")
				.append("</public>")
				
				.append("<user_info>")
					.append("<pproductoffer_info>")
						.append("<ppproduct_act_type>").append(vo.getOperType()).append("</ppproduct_act_type>")
					.append("</pproductoffer_info>")
					.append("<prod_info>")
						.append("<prod_no>").append(detailVo.getAccNbr()).append("</prod_no>")
						.append("<prod_type>").append(detailVo.getProdType()).append("</prod_type>")
						.append("<area_code>").append(detailVo.getLanId()).append("</area_code>")
						.append("<prod_inst_id>").append(detailVo.getProductId()).append("</prod_inst_id>")
					.append("</prod_info>");
					
					String subProdXml = createSubProductsXml(productId,detailVo.getProductNbr(),vo);
					bf.append(subProdXml);
				bf.append("</user_info>")
			.append("</interface_msg>")
		.append("</root>");
		return bf.toString();
	}
	private String createSubProductsXml(String productId, String ismpCode, InfProdOfferInst vo) throws SQLException, ProductHasNoPlatformException {
		StringBuffer subProductNodeBuffer = new StringBuffer();
		subProductNodeBuffer.append("<sub_products>");
		List productIdArray = new ArrayList();
		Map prodHolder = new HashMap();
//		List vProductList = order.getVProductInfoList();
		productIdArray.add(productId);
		prodHolder.put(productId, vo);
//		for (Iterator iterator = vProductList.iterator(); iterator.hasNext();) {
//			VProductInfo p = (VProductInfo) iterator.next();
//			
//			prodHolder.put(productId, p);
//		}
//		String productids = joinStringList(productIdArray);
		logger.info("createSubProductsXml productid: " + productId);
		Map[] ret = null;
		OrderDao orderDao = new OrderDao();
		if(!"".equals(productId))
		ret = orderDao.findPlatform(productId);
		if(ret != null && ret.length == 2){
			//����control�ڵ�
			Map prodMapPlamform = ret[0];
			logger.info("create control node.");
			subProductNodeBuffer.append("<control>");
			List plamforms = (List)prodMapPlamform.get(productId);
			if(plamforms == null) throw new ProductHasNoPlatformException("cannot find any platform for product->" + productId);
			subProductNodeBuffer.append(joinStringList(plamforms));
				
			subProductNodeBuffer.append("</control>");
			logger.info("create control node done.");
			//����ҵ��ƽ̨N����ڵ�
			Map PlamformMapProd = ret[1];
			Set plamformIdSet = PlamformMapProd.keySet();
			logger.info("create sub plamform node.");
			for (Iterator iterator = plamformIdSet.iterator(); iterator.hasNext();) {
				String plamformId = (String) iterator.next();
				List productIdList = (List)PlamformMapProd.get(plamformId);
				subProductNodeBuffer.append("<p").append(plamformId).append(">");
					for (Iterator productItr = productIdList.iterator(); productItr.hasNext();) {
						String prodId = (String) productItr.next();
						InfProdOfferInst p = (InfProdOfferInst) prodHolder.get(prodId);
						String tmp = toXmlForSpi(p, ismpCode);
						subProductNodeBuffer.append(tmp);
					}
				
				subProductNodeBuffer.append("</p").append(plamformId).append(">");
			}
			logger.info("create sub plamform node end.");
		}
		subProductNodeBuffer.append("</sub_products>");
		return subProductNodeBuffer.toString();
	}
	private String joinStringList(List productIdArray) {
		int indx = 0;
		StringBuffer bf = new StringBuffer("");
		for (Iterator iterator = productIdArray.iterator(); iterator.hasNext();) {
			String tmp = (String) iterator.next();
			if(indx == 0){
				bf.append(tmp);
			}else{
				bf.append(",").append(tmp);
			}
			indx++;
		}
		return bf.toString();
	}
	private String toXmlForSpi(InfProdOfferInst vo, String ismpCode) {
		StringBuffer bf = new StringBuffer();
		String actType = "";
		String operType = vo.getOperType();
		if("A".equals(operType)){
			actType = "1";//�����ײ�
		}else if("D".equals(operType)){
			actType = "4";//ȡ���ײ�
		}
		bf 
		.append("<sub_product>")
			.append("<sub_product_code>").append(ismpCode).append("</sub_product_code>")
			.append("<oldsub_product_code>").append("").append("</oldsub_product_code>")
			.append("<act_type>").append(actType).append("</act_type>")
			.append("<idtype>").append("2").append("</idtype>") //����ֻ��2����
			.append("<id>").append(vo.getOfferId()).append("</id>")//�Ż�ID
			.append("<prodCharacters>").append("").append("</prodCharacters>")
		.append("</sub_product>");
		return bf.toString();
	}
	private boolean insertOfferDetail(String id) throws SQLException{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.insertOfferDetail(id);
	}
	private boolean delOfferDetail(String id) throws SQLException{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.delOfferDetail(id);
	}
	private boolean insertOffer(String id) throws SQLException{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.insertOffer(id);
	}
	private boolean delOffer(String id) throws SQLException{
		ProdOfferSynDAO dao = new ProdOfferSynDAO();
		return dao.delOffer(id);
	}
}