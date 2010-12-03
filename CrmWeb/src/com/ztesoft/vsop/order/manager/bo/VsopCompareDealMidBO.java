package com.ztesoft.vsop.order.manager.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;

import com.ztesoft.vsop.order.dao.SqlUtil;
import com.ztesoft.vsop.order.dao.VsopCompareDealMidDAO ;
import com.ztesoft.vsop.order.manager.dao.OrderRelationDAO;

public class VsopCompareDealMidBO extends DictAction  {
	private static Logger logger = Logger.getLogger(VsopCompareDealMidBO.class);
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchVsopCompareDealMidData �ķ����Ĳ�������
 		3. findVsopCompareDealMidByCond() ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertVsopCompareDealMid(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map VsopCompareDealMid = (Map) param.get("VsopCompareDealMid") ;
		
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		boolean result = dao.insert(VsopCompareDealMid) ;
		return result ;
	}

	
	public boolean updateVsopCompareDealMid(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map VsopCompareDealMid = (Map) param.get("VsopCompareDealMid") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(VsopCompareDealMid,  keyStr) ;
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		boolean result = dao.updateByKey( VsopCompareDealMid, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchVsopCompareDealMidData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "oa")){
			whereCond.append(" and oa = ? ");
			para.add(Const.getStrValue(param , "oa")) ;
		}
		if(Const.containStrValue(param , "oaType")){
			whereCond.append(" and oa_type = ? ");
			para.add(Const.getStrValue(param , "oaType")) ;
		}
		if(Const.containStrValue(param , "status")){
			whereCond.append(" and status = ? ");
			para.add(Const.getStrValue(param , "status")) ;
		}
		if(Const.containStrValue(param , "fileName")){
			whereCond.append(" and file_name like '%'|| ? || '%' ");
			para.add(Const.getStrValue(param , "fileName")) ;
		}
		if(Const.containStrValue(param , "isAdjust")){
			whereCond.append(" and is_adjust = ? ");
			para.add(Const.getStrValue(param , "isAdjust")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getVsopCompareDealMidById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findVsopCompareDealMidByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteVsopCompareDealMidById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	/**
	 * �����ֹ�����������ϵ
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int batchAdjustOrder(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto);
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		//0:VSOP�����Ķ�����ϵ��1��VSOP��ȱ�ٵĶ�����ϵ��2�����충����ϵ
		String flag = (String)param.get("flag");
		OrderRelationDAO dao = new OrderRelationDAO();
		boolean target = false;
		String result = dao.isOrderRelaChange(prodType, accNbr, productId);
		if("".equals(result))
			result ="0";
		double num = Double.parseDouble(result);
		//��ǰҪ�ֹ������Ķ�����ϵ�ڵ����Ѿ��ֱ�������
		if(num < 0 ){
			updateAjustState(param);
			return 1;
		}
		String reason = "";//�鵵ʱ����ԭ��
		String disposalResult="";//�鵵ʱ����ԭ��
		if("0".equals(flag)){
			target = delOrderRelation(param);
			reason = "VSOP�ȶԶ��ඩ����ϵ����";
			disposalResult = "�����ɹ�";
		}else if("1".equals(flag)){
			target = addOrderRelation(param);
			reason = "VSOP�ȶ�ȱ�ٵĶ�����ϵ����";
			disposalResult = "�����ɹ�";
		}else{
			target = adjustOrderRelation(param);
			reason = "VSOP�ȶԲ��충����ϵ����";
			disposalResult = "���³ɹ�";
		}
		if(!target)return 2;
		//���ݺ���Ͳ�Ʒ���͵õ���Ʒʵ��id��lan_id
		OrderRelationDAO orderRelationDao = new OrderRelationDAO();
		String[] prodInstIdStr = orderRelationDao.getIdTypeStr(accNbr,prodType).split(",");
		param.put("prod_inst_id", prodInstIdStr[0]);
		param.put("lan_id", prodInstIdStr[2]);
		//�鵵
		//�����鵵
		String custOrderId = SqlUtil.getInstance().getSequence("SEQ_ORDER_INFO_ID");
		addCustomerHis(param,custOrderId,reason,disposalResult);
		//������鵵
		addOrderItemHis(param,custOrderId,reason,disposalResult);
		//����ȶ���־��
		insertOrderLog(param);
		//ɾ���ȶԱ��¼  Ӧ�����ɾ���ȶԼ�¼
		delOrderComMid(param);
		return 0;
	}

	/**
	 * ����������ϵ
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean addOrderRelation(Map param)throws Exception {
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.addOrderRelation(prodType, accNbr, productId);
		
	}
	/**
	 * ɾ��������ϵ
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean delOrderRelation(Map param)throws Exception {
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.delOrderRelation(prodType, accNbr, productId);
		
	}
	/**
	 * ���¶�����ϵ
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean adjustOrderRelation(Map param)throws Exception {
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String packageId = (String)param.get("packageId");
		String pprodOfferId = (String)param.get("pprodOfferId");
		String status = (String)param.get("status");
		String productId = (String)param.get("productId");
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.adjustOrderRelation(prodType, accNbr, packageId, pprodOfferId, status, productId);
		
	}
	/**
	 * �����鵵
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean addCustomerHis(Map param,
								   String custOrderId, 
								   String reason,
								   String disposalResult)throws Exception {
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.addCustomerHis(param, custOrderId, reason, disposalResult);
		
	}
	/**
	 * ������鵵
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean addOrderItemHis(Map param,
								   String custOrderId, 
								   String reason,
								   String disposalResult)throws Exception {
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.addOrderItemHis(param, custOrderId, reason, disposalResult);
		
	}
	/**
	 * �ֹ�����������ϵ��־
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean insertOrderLog(Map param)throws Exception {
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.insertOrderLog(param);
		
	}
	/**
	 * ɾ���ȶ��м����ִ���ֹ������ļ�¼
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean delOrderComMid(Map param)throws Exception {
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		return dao.delOrderComMid(prodType, accNbr, productId);
		
	}
	/**
	 * �����ֹ�����״̬
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean updateAjustState(Map param)throws Exception {
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		return dao.updateOrderComMid(prodType, accNbr, productId);
		
	}
}
