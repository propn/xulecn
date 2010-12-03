package com.ztesoft.vsop.spManage.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.spManage.dao.PartnerDAO;
import com.ztesoft.vsop.spManage.dao.PartnerServAbilityDAO ;
import com.ztesoft.vsop.webservice.client.SoapClient;

public class PartnerServAbilityBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchPartnerServAbilityData �ķ����Ĳ�������
 		3. findPartnerServAbilityByCond(String partner_service_ability_id) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertPartnerServAbility(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map PartnerServAbility = (Map) param.get("PartnerServAbility") ;
		String partnerAbilityId = (String)PartnerServAbility.get("partner_service_ability_id");
		if(partnerAbilityId == null || "".equals(partnerAbilityId)){
			SequenceManageDAO sDao = new SequenceManageDAOImpl();
			partnerAbilityId = sDao.getNextSequence("PARTNER_SERVICE_ABILITY", "PARTNER_SERVICE_ABILITY_ID");
			PartnerServAbility.put("partner_service_ability_id", partnerAbilityId);
		}
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		boolean result = dao.insert(PartnerServAbility) ;
		return result ;
	}

	
	public boolean updatePartnerServAbility(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map PartnerServAbility = (Map) param.get("PartnerServAbility") ;
		String keyStr = "partner_service_ability_id";
		Map keyCondMap  = Const.getMapForTargetStr(PartnerServAbility,  keyStr) ;
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		boolean result = dao.updateByKey( PartnerServAbility, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchPartnerServAbilityData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "iParam1")){
			whereCond.append(" and partner_id = ? ");
			para.add(Const.getStrValue(param , "iParam1")) ;
		}
//		if(Const.containStrValue(param , "param2")){
//			whereCond.append(" and param2 = ? ");
//			para.add(Const.getStrValue(param , "param2")) ;
//		}
//		if(Const.containStrValue(param , "param3")){
//			whereCond.append(" and param3 = ? ");
//			para.add(Const.getStrValue(param , "param3")) ;
//		}
		whereCond.append(" and state in ('0','1','2')");
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getPartnerServAbilityById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findPartnerServAbilityByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deletePartnerServAbilityById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	public boolean insertPartnerServAbilitySop(DynamicDict dto  ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
//		PartnerDAO dao = new PartnerDAO();
//		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
//		
//		return result ;
		//���ú�̨�ӿ�
		String ret=SoapClient.getInstance().spcpSynCapStub(this.createCPSPInfoSyncAbXml("1",keyCondMap));
		ret=StringUtil.getInstance().getTagValue("resultCode", ret);
		System.out.println("-------------:"+ret);
		if("0".equals(ret))return true;
		else return false;
	}

	
	public boolean updatePartnerServAbilitySop(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
//		PartnerDAO dao = new PartnerDAO();
//		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
//		
//		return result ;
		//���ú�̨�ӿ�
		String ret=SoapClient.getInstance().spcpSynCapStub(this.createCPSPInfoSyncAbXml("2",keyCondMap));
		ret=StringUtil.getInstance().getTagValue("resultCode", ret);
		System.out.println("-------------:"+ret);
		if("0".equals(ret))return true;
		else return false;
	}
	public boolean deletePartnerServAbilityByIdSop(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
//		PartnerDAO dao = new PartnerDAO();
//		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
//		
//		return result ;
		//���ú�̨�ӿ�
		String ret=SoapClient.getInstance().spcpSynCapStub(this.createCPSPInfoSyncAbXml("3",keyCondMap));
		ret=StringUtil.getInstance().getTagValue("resultCode", ret);
		System.out.println("-------------:"+ret);
		if("0".equals(ret))return true;
		else return false;
	}
	private String createCPSPInfoSyncAbXml(String actType,Map SiAccpet){
//		String SystemId="0";
		String partner_service_ability_id =(String)SiAccpet.get("partner_service_ability_id");
		String partner_code = (String)SiAccpet.get("partner_code");
		String service_code = (String)SiAccpet.get("service_code");
		String eff_date = (String)SiAccpet.get("eff_date");
		String exp_date =(String)SiAccpet.get("exp_date");
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<CSPCapabilitySyncReq>")
			.append("<StreamingNo>").append("123456").append("</StreamingNo>")
			.append("<TimeStamp>").append("20100412").append("</TimeStamp>")
			.append("<OPFlag>").append(actType).append("</OPFlag>")
			.append("<SPSignInfo>")
			
			.append("<SPSignID>").append(partner_service_ability_id).append("</SPSignID>")
			.append("<SPID>").append(partner_code).append("</SPID>")
			.append("<serviceCapabilityID>").append(service_code).append("</serviceCapabilityID>")
			.append("<effectiveTime>").append(eff_date).append("</effectiveTime>")
			.append("<expiryTime>").append(exp_date).append("</expiryTime>")
			.append("<chargingPolicyID>").append("").append("</chargingPolicyID>")
			.append("</SPSignInfo>");

			//bf.append("</ProductInfo>");
			
		bf.append("</CSPCapabilitySyncReq>");
		
		System.out.println("-------CSPCapabilitySyncReq------------="+bf.toString());
		return bf.toString();
	}
}
