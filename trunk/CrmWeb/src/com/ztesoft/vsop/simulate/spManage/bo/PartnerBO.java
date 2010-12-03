package com.ztesoft.vsop.simulate.spManage.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.simulate.spManage.dao.PartnerDAO;
import com.ztesoft.vsop.simulate.spManage.dao.PartnerServAbilityDAO;
import com.ztesoft.vsop.spManage.dao.PartnerSystemInfoDAO;
import com.ztesoft.vsop.webservice.client.SoapClient;

public class PartnerBO extends DictAction  {
	private static final int ADD = 1 ;
	private static final int UPDATE = 2 ;
	private static final int DELETE = 3 ;
	
	/**
	 * 调用webservice接口
	 * @param actionType
	 * @param result
	 * @return
	 */
	private static String callRemoteService(int actionType ,String partner_id ) throws Exception{
		//调用后台接口
		Map result = getInterfaceMap(actionType ,  partner_id ) ;
		String ret=SoapClient.getInstance().spcpSyn2VSOP(spcpXml(actionType ,  result) );
		System.out.println("server return xml ======="+ret) ;
		
		System.out.println("actionType==="+actionType+
				",ResultCode="+StringUtil.getInstance().getTagValue("ResultCode", ret)+
				",ResultDesc="+StringUtil.getInstance().getTagValue("ResultDesc", ret)) ;
		return ret ;
	}
	
	private static Map getInterfaceMap(int type , String partner_id )throws Exception {
		Map result = new HashMap() ;
		
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		
		whereCond.append(" and partner_id = ? ");
		para.add(partner_id);
		
		//Map partner
		Map keyCondMap = new HashMap() ;
		keyCondMap.put("partner_id", partner_id) ;
		PartnerDAO productDao = new PartnerDAO();
		Map product = productDao.findByPrimaryKey(keyCondMap);
		result.put("partner", product) ;
		
		//List servAbility
		//PartnerServAbilityDAO abilityDao = new PartnerServAbilityDAO();
		PartnerSystemInfoDAO platDao=new PartnerSystemInfoDAO();
		List platForms = platDao.findByCond(whereCond.toString(), para) ;
		result.put("platForms", platForms) ;
		
		return result ;
	}
	
	
	public boolean insertPartner(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map Partner = (Map) param.get("Partner") ;
		String partnerId = (String)Partner.get("partner_id");
		if(partnerId == null || "".equals(partnerId)){
			SequenceManageDAO sDao = new SequenceManageDAOImpl();
			partnerId = sDao.getNextSequence("PARTNER_INFO", "PARTNER_ID");
			Partner.put("partner_id", partnerId);
		}
		PartnerDAO dao = new PartnerDAO();
		boolean result = dao.insert(Partner) ;
		//调用接口，同步数据
		callRemoteService(ADD,partnerId );
		
		return result ;
	}

	
	public boolean updatePartner(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map Partner = (Map) param.get("Partner") ;
		String keyStr = "partner_id";
		Map keyCondMap  = Const.getMapForTargetStr(Partner,  keyStr) ;
		PartnerDAO dao = new PartnerDAO();
		boolean result = dao.updateByKey( Partner, keyCondMap );
		
		//调用接口，同步数据
		callRemoteService(UPDATE,Const.getStrValue(Partner , keyStr) );
		
		return result ;
	}
	
	
	public PageModel searchPartnerData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "iParam1")){
			whereCond.append(" and partner_code = ? ");
			para.add(Const.getStrValue(param , "iParam1")) ;
		}
		if(Const.containStrValue(param , "iParam2")){
			whereCond.append(" and partner_name like ? ");
			//模糊查询
			para.add("%"+Const.getStrValue(param , "iParam2")+"%") ;
		}
		whereCond.append(" and state in ('0','1','2')");
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		PartnerDAO dao = new PartnerDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getPartnerById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		PartnerDAO dao = new PartnerDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findPartnerByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		PartnerDAO dao = new PartnerDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deletePartnerById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		PartnerDAO dao = new PartnerDAO();
		
		//调用接口，同步数据
		callRemoteService(DELETE,Const.getStrValue(keyCondMap, "partner_id") );
		
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}

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
		
		//调用接口，同步数据
		callRemoteService(UPDATE,Const.getStrValue(PartnerServAbility, "partner_id") );

		return result ;
	}

	
	public boolean updatePartnerServAbility(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map PartnerServAbility = (Map) param.get("PartnerServAbility") ;
		PartnerServAbility.put("state_date", "") ;
		
		String keyStr = "partner_service_ability_id";
		Map keyCondMap  = Const.getMapForTargetStr(PartnerServAbility,  keyStr) ;
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		boolean result = dao.updateByKey( PartnerServAbility, keyCondMap );
		

		//调用接口，同步数据
		callRemoteService(UPDATE,Const.getStrValue(PartnerServAbility, "partner_id") );
		return result ;
	}
	
	
	public PageModel searchPartnerServAbilityData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "iParam1")){
			whereCond.append(" and partner_id = ? ");
			para.add(Const.getStrValue(param , "iParam1")) ;
		}
		whereCond.append(" and state in ('0','1','2')");
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getPartnerServAbilityById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findPartnerServAbilityByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deletePartnerServAbilityById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		PartnerServAbilityDAO dao = new PartnerServAbilityDAO();
		Map m = dao.findByPrimaryKey(keyCondMap) ;
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		//调用接口，同步数据
		callRemoteService(UPDATE,Const.getStrValue(m, "partner_id") );

		return result ;
	}
	
	private static  String spcpXml(int actionType , Map partnerMap){
		Map partner = (Map)partnerMap.get("partner") ;
		List platForms = (List)partnerMap.get("platForms") ;
		
		String partner_code =(String)partner.get("partner_code");
		String partner_type = (String)partner.get("partner_type");
		String partner_desc = (String)partner.get("partner_desc");
		String partner_name = (String)partner.get("partner_name");
		String partner_eng_name =(String)partner.get("partner_eng_name");
		String partner_eng_desc =(String)partner.get("partner_eng_desc");
		String cust_service_phone = (String)partner.get("cust_service_phone");
		
		String partner_url = (String)partner.get("partner_url");
		String partner_area_code = (String)partner.get("partner_area_code");
		String if_roam = (String)partner.get("if_roam");
		String company_address = (String)partner.get("company_address");
		String artificial_person = (String)partner.get("artificial_person");
		String primary_person_name = (String)partner.get("primary_person_name");
		String primary_person_email = (String)partner.get("primary_person_email");
		String primary_person_phone = (String)partner.get("primary_person_phone");
		String business_license = (String)partner.get("business_license");
		String contract_exp_date = (String)partner.get("contract_exp_date");
		String partner_number = (String)partner.get("partner_number");
		String settle_cycle = (String)partner.get("settle_cycle");
		String settle_pay_method = (String)partner.get("settle_pay_method");
		String settle_rate = (String)partner.get("settle_rate");
		String state = (String)partner.get("state");
		String system_code = (String)partner.get("system_code");
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<Request>")
		.append("<SessionBody>")
		.append("<CSPInfoSyncToVSOPReq>")
			.append("<StreamingNo>").append("123456").append("</StreamingNo>")
			.append("<TimeStamp>").append("20100412").append("</TimeStamp>")
			.append("<OPFlag>").append(actionType).append("</OPFlag>")
			.append("<SystemId>").append(system_code).append("</SystemId>")
			.append("<SPInfo>")
			
				.append("<SPID>").append(partner_code).append("</SPID>")
				.append("<Type>").append(partner_type).append("</Type>")
				.append("<NameCN>").append(partner_name).append("</NameCN>")
				.append("<NameEN>").append(partner_eng_name).append("</NameEN>")
				.append("<DescriptionCN>").append(partner_desc).append("</DescriptionCN>")
				.append("<DescriptionEN>").append(partner_eng_desc).append("</DescriptionEN>")
				.append("<CustomerCare>").append(cust_service_phone).append("</CustomerCare>")
				.append("<WebsiteURL>").append(partner_url).append("</WebsiteURL>")
				.append("<ProvinceID>").append(partner_area_code).append("</ProvinceID>")
				.append("<RoamProperty>").append(if_roam).append("</RoamProperty>")
				.append("<CompanyAddress>").append(company_address).append("</CompanyAddress>")
				.append("<LegalRepresentative>").append(artificial_person).append("</LegalRepresentative>")
				.append("<Principal>").append(primary_person_name).append("</Principal>")
				.append("<PrincipalTel>").append(primary_person_phone).append("</PrincipalTel>")
				.append("<PrincipalEmail>").append(primary_person_email).append("</PrincipalEmail>")
			
				.append("<ServiceManager>").append("").append("</ServiceManager>")
				.append("<ServiceManagerTel>").append("").append("</ServiceManagerTel>")
				.append("<ServiceManagerEmail>").append("").append("</ServiceManagerEmail>")
				.append("<ServiceManagerAddr>").append("").append("</ServiceManagerAddr>")
				.append("<ServiceManagerPC>").append("").append("</ServiceManagerPC>")
				.append("<ServiceManagerFax>").append("").append("</ServiceManagerFax>")
				.append("<License>").append(business_license).append("</License>")
				.append("<ContractExpireDate>").append(contract_exp_date).append("</ContractExpireDate>")
				.append("<AccessNO>").append(partner_number).append("</AccessNO>")
				.append("<SettlementCycle>").append(settle_cycle).append("</SettlementCycle>")
				.append("<SettlementPayType>").append(settle_pay_method).append("</SettlementPayType>")
				.append("<SettlementPercent>").append(settle_rate).append("</SettlementPercent>")
				.append("<CSWebsite>").append("").append("</CSWebsite>")
				.append("<Status>").append(state).append("</Status>");
				if(platForms != null && !platForms.isEmpty()){
					Iterator it = platForms.iterator() ;
					while ( it.hasNext() ){
						Map m = (Map) it.next() ;
						xml.append("<PlatForm>").append(Const.getStrValue(m, "system_code")).append("</PlatForm>") ;
					}
				}
				
			xml.append("</SPInfo>")
		.append("</CSPInfoSyncToVSOPReq>")
		.append("</SessionBody>")
		.append("</Request>");
		
		System.out.println("-------CSPInfoSyncReq------------="+xml.toString());
		return xml.toString();
	}
	
}
