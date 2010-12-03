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
	 * 处理来自CRM的优惠同步工单请求
	 * 直接保存数据到接口表，后续定时任务扫描处理
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
			//判断是否需要插入优惠实例
			int num = 0;
			if(detailList != null){
				for(int i=0; i<detailList.size();i++){
					OfferDetailInfo detailVo = (OfferDetailInfo)detailList.get(i);
					//判断是否需要同步给vsop：优惠下的优惠明细ID必须在vsop的product表中存在才需要同步
					if(isSyn(detailVo)){
						if(num == 0)seq = offerDao.getSequence("SEQ_INF_OFFER_INSTANCE_ID");
						order.setInfOfferInstanceId(seq);
						num++;
						//插入优惠实例明细接口表
						result=offerDao.insertInfProdOffDetail(order, detailVo);
					}else result=true;
				}
			}
			//只有vsop需要同步的优惠才进入优惠实例接口表
			if(num > 0){
				//插入优惠实例接口表
				result=offerDao.insertInfProdOffInst(order);
			}
			if(!result){
				LegacyDAOUtil.rollbackOnException();
				return getResult(streamingNo, "1", "优惠接口表已经存在优惠实例数据!");
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
			if(resultCode.equals("0")) resultName = "成功";
			else resultName = "失败";
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
	 * 从接口消息表获取待处理的记录
	 * @param threadId 
	 * @param size
	 * @return
	 */
	public List getUnDealOrders(int limit) {
		List ret = null;
		try {
			ret = new ProdOfferSynDAO().getUnDealOrders(limit);//状态更新为处理中
		} catch (SQLException e) {
			logger.info("#getUnDealOrders ex.", e);
		}finally{
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return ret;
	}
	public boolean dealOfferSync(InfProdOfferInst vo) throws Exception{
		String operType = vo.getOperType();
		//取出优惠明细列表
		List offerDetail = getOfferDetail(vo.getInfOfferInstanceId());
		//产品实例不存在
		if(offerDetail == null || offerDetail.size() == 0){
			if("D".equals(operType) || "M".equals(operType)){
				//归档并删除明细接口表数据
				insertOfferDetail(vo.getInfOfferInstanceId());
				delOfferDetail(vo.getInfOfferInstanceId());
				//归档并删除接口表数据
				insertOffer(vo.getInfOfferInstanceId());
				delOffer(vo.getInfOfferInstanceId());
				LegacyDAOUtil.commitAndCloseConnection();
				return true;
			}else return false;
		}
		//标记，标记优惠实例表记录只插入一次
		boolean onlyOne = true;
		int num = 0;//新加优惠时需要判断每个明细都已更新到订购关系表中。
		for(int i = 0; i<offerDetail.size();i++){
			OfferDetailInfo detailVo = (OfferDetailInfo)offerDetail.get(i);
			try{
				//当是续约优惠操作时，不需要做任何判断，直接更新优惠实例和优惠明细实例即可
				if("M".equals(operType)){
					//续约
					if(onlyOne){
						updateOfferInst(vo);
						onlyOne = false;
					}
					udpateOfferDetailInst(vo, detailVo);
					continue;
				}
				//判断是否可继续执行操作逻辑：vsop是否已经有优惠下对应的增值产品订购，有则更新并加入优惠，无则转入等待
				String[] _value = isContinue(detailVo).split("#");
				if(_value != null && _value.length >= 2){
					List list = getProductId(_value[1], detailVo.getProductId());
					//当crm过来的退出优惠操作就不需要判断优惠下的增值产品是否进VSOP，无需等待
					if("D".equals(operType)){
						//取消
						if(onlyOne){
							//优惠实例和优惠实例明细一同删除
							delOfferInst(vo.getOfferInstId());
							onlyOne = false;
						}
						for(int k=0;k<list.size();k++){
							String[] strs = ((String)list.get(k)).split(",");
							String productId = strs[0];
							String ismpCode = strs[1]; //外码
							detailVo.setProductNbr(ismpCode);
							//更新订购关系表字段,退出优惠
							updateOrderRela(detailVo, "", productId);
							//送激活
							//广西VSOP一阶段暂时对传统+增值不送平台激活
	//						createOrderForSPI(productId, vo, detailVo);
						}
						continue;
					}
					if(_value[0] != null && !"".equals(_value[0]) && Integer.parseInt(_value[0]) > 0){
						//取得已经在vsop生成订购关系实例的增值产品ID，加入优惠
						if("A".equals(operType)){
							//订购
							//插入优惠实例
							if(onlyOne){
								insertOfferInst(vo);
								onlyOne = false;
							}
							//插入优惠明细实例表
							insertOfferDetailInst(vo, detailVo);
							for(int k=0;k<list.size();k++){
								String[] strs = ((String)list.get(k)).split(",");
								String productId = strs[0];
								String ismpCode = strs[1]; //外码
								detailVo.setProductNbr(ismpCode);
								//更新订购关系表字段,加入优惠
								updateOrderRela(detailVo, vo.getOfferId(), productId);
								//送激活,送激活使用外码
								//广西VSOP一阶段暂时对传统+增值不送平台激活
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
				//归档并删除明细接口表数据
				insertOfferDetail(vo.getInfOfferInstanceId());
				delOfferDetail(vo.getInfOfferInstanceId());
				//归档并删除接口表数据
				insertOffer(vo.getInfOfferInstanceId());
				delOffer(vo.getInfOfferInstanceId());
				LegacyDAOUtil.commitAndCloseConnection();
				return true;
			}
			else return false;
		}
		//归档并删除明细接口表数据
		insertOfferDetail(vo.getInfOfferInstanceId());
		delOfferDetail(vo.getInfOfferInstanceId());
		//归档并删除接口表数据
		insertOffer(vo.getInfOfferInstanceId());
		delOffer(vo.getInfOfferInstanceId());
		LegacyDAOUtil.commitAndCloseConnection();
		return true;
	}
	/**
	 * 异常后处理接口表数据，发送次数加1
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void dealOperException(InfProdOfferInst vo) throws Exception{
		new ProdOfferSynDAO().dealOperException(vo);
		LegacyDAOUtil.commitAndCloseConnection();
	}
	/**
	 * 仅将状态改为失败，等待操作
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
	 * 判断是否可继续执行操作逻辑：vsop是否已经有优惠下对应的增值产品订购，有则更新并加入优惠，无则转入等待
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
		//插入前先删除
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
	 * 给激活模块生成订单 
	 * @throws ProductHasNoPlatformException 
	 * @throws SQLException 
	 */
	private void createOrderForSPI(String productId, InfProdOfferInst vo, OfferDetailInfo detailVo) throws ProductHasNoPlatformException, SQLException {
		// 创建适合的xml
		logger.info("createOrderForSPI start");
		try {
			String spiXML = createSPIXML(productId, vo, detailVo);
			logger.info(spiXML);
			// 保存到数据库
			long inOrderId = Long.parseLong(vo.getInfOfferInstanceId());
			String unuseClob = DcSystemParamManager.getParameter(VsopConstants.UNUSE_CLOB);
			OrderDao orderDao = new OrderDao();
			//系统编码暂定系统来源为省级CRM:201
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
			//生成control节点
			Map prodMapPlamform = ret[0];
			logger.info("create control node.");
			subProductNodeBuffer.append("<control>");
			List plamforms = (List)prodMapPlamform.get(productId);
			if(plamforms == null) throw new ProductHasNoPlatformException("cannot find any platform for product->" + productId);
			subProductNodeBuffer.append(joinStringList(plamforms));
				
			subProductNodeBuffer.append("</control>");
			logger.info("create control node done.");
			//生成业务平台N编码节点
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
			actType = "1";//加入套餐
		}else if("D".equals(operType)){
			actType = "4";//取消套餐
		}
		bf 
		.append("<sub_product>")
			.append("<sub_product_code>").append(ismpCode).append("</sub_product_code>")
			.append("<oldsub_product_code>").append("").append("</oldsub_product_code>")
			.append("<act_type>").append(actType).append("</act_type>")
			.append("<idtype>").append("2").append("</idtype>") //服开只有2类型
			.append("<id>").append(vo.getOfferId()).append("</id>")//优惠ID
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