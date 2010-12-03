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
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.spManage.dao.PartnerDAO;
import com.ztesoft.vsop.webservice.client.SoapClient;
import com.powerise.ibss.framework.FrameException;

public class PartnerBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchPartnerData 改方法的参数名称
 		3. findPartnerByCond(String partner_id) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
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
		return result ;
	}

	
	public boolean updatePartner(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map Partner = (Map) param.get("Partner") ;
		String keyStr = "partner_id";
		Map keyCondMap  = Const.getMapForTargetStr(Partner,  keyStr) ;
		PartnerDAO dao = new PartnerDAO();
		boolean result = dao.updateByKey( Partner, keyCondMap );
		
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
		if(Const.containStrValue(param , "iParam3")){
			whereCond.append(" and partner_type = ? ");
			//模糊查询
			para.add(Const.getStrValue(param , "iParam3")) ;
		}
		if(Const.containStrValue(param , "iParam4")){
			whereCond.append(" and state = ? ");
			//模糊查询
			para.add(Const.getStrValue(param , "iParam4")) ;
		}
		else whereCond.append(" and state in ('0','1','2')");
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
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	/**
	 * 模拟器
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean insertPartnerSop(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map Partner = (Map) param.get("Partner") ;
//		String partnerId = (String)Partner.get("partner_id");
//		if(partnerId == null || "".equals(partnerId)){
//			SequenceManageDAO sDao = new SequenceManageDAOImpl();
//			partnerId = sDao.getNextSequence("PARTNER_INFO", "PARTNER_ID");
//			Partner.put("partner_id", partnerId);
//		}
//		PartnerDAO dao = new PartnerDAO();
//		boolean result = dao.insert(Partner) ;
		
		
		//调用后台接口
		String ret=SoapClient.getInstance().spcpSynStub(this.createCPSPInfoSyncXml("1",Partner));
		ret=StringUtil.getInstance().getTagValue("resultCode", ret);
		System.out.println("-------------:"+ret);
		if("0".equals(ret))return true;
		else return false;
	}

	/**
	 * 模拟器
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean updatePartnerSop(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map Partner = (Map) param.get("Partner") ;
//		String keyStr = "partner_id";
//		Map keyCondMap  = Const.getMapForTargetStr(Partner,  keyStr) ;
//		PartnerDAO dao = new PartnerDAO();
//		boolean result = dao.updateByKey( Partner, keyCondMap );
		//调用后台接口
		String ret=SoapClient.getInstance().spcpSynStub(this.createCPSPInfoSyncXml("2",Partner));
		ret=com.ztesoft.vsop.StringUtil.getInstance().getTagValue("resultCode", ret);
		System.out.println("-------------:"+ret);
		if("0".equals(ret))return true;
		else return false;
	  }
	public boolean deletePartnerByIdSop(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		PartnerDAO dao = new PartnerDAO();
//		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
//		
//		return result ;
		//调用后台接口
		String ret=SoapClient.getInstance().spcpSynStub(this.createCPSPInfoSyncXml("3",keyCondMap));
		ret=StringUtil.getInstance().getTagValue("resultCode", ret);
		System.out.println("-------------:"+ret);
		if("0".equals(ret))return true;
		else return false;
	}
	private String createCPSPInfoSyncXml(String actType,Map SiAccpet){
//		String SystemId="0";
		String partner_code =(String)SiAccpet.get("partner_code");
		String partner_type = (String)SiAccpet.get("partner_type");
		String partner_desc = (String)SiAccpet.get("partner_desc");
		String partner_name = (String)SiAccpet.get("partner_name");
		String partner_eng_name =(String)SiAccpet.get("partner_eng_name");
		String cust_service_phone = (String)SiAccpet.get("cust_service_phone");
		
		String partner_url = (String)SiAccpet.get("partner_url");
		String partner_area_code = (String)SiAccpet.get("partner_area_code");
		String if_roam = (String)SiAccpet.get("if_roam");
		String company_address = (String)SiAccpet.get("company_address");
		String artificial_person = (String)SiAccpet.get("artificial_person");
		String primary_person_name = (String)SiAccpet.get("primary_person_name");
		String primary_person_email = (String)SiAccpet.get("primary_person_email");
		String primary_person_phone = (String)SiAccpet.get("primary_person_phone");
		String business_license = (String)SiAccpet.get("business_license");
		String contract_exp_date = (String)SiAccpet.get("contract_exp_date");
		String partner_number = (String)SiAccpet.get("partner_number");
		String settle_cycle = (String)SiAccpet.get("settle_cycle");
		String settle_pay_method = (String)SiAccpet.get("settle_pay_method");
		String settle_rate = (String)SiAccpet.get("settle_rate");
		String state = (String)SiAccpet.get("state");
		String system_code = (String)SiAccpet.get("system_code");
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<CSPInfoSyncReq>")
			.append("<streamingNo>").append("123456").append("</streamingNo>")
			.append("<TimeStamp>").append("20100412").append("</TimeStamp>")
			.append("<OPFlag>").append(actType).append("</OPFlag>")
			.append("<SPInfo>")
			
			.append("<SPID>").append(partner_code).append("</SPID>")
			.append("<type>").append(partner_type).append("</type>")
			.append("<nameCN>").append(partner_name).append("</nameCN>")
			.append("<nameEN>").append(partner_eng_name).append("</nameEN>")
			.append("<descriptionCN>").append(partner_desc).append("</descriptionCN>")
			.append("<descriptionEN>").append(partner_eng_name).append("</descriptionEN>")
			.append("<customerCare>").append(cust_service_phone).append("</customerCare>")
			.append("<websiteURL>").append(partner_url).append("</websiteURL>")
			.append("<provinceID>").append(partner_area_code).append("</provinceID>")
			.append("<roamProperty>").append(if_roam).append("</roamProperty>")
			.append("<companyAddress>").append(company_address).append("</companyAddress>")
			.append("<legalRepresentative>").append(artificial_person).append("</legalRepresentative>")
			.append("<principal>").append(primary_person_name).append("</principal>")
			.append("<principalTel>").append(primary_person_phone).append("</principalTel>")
			.append("<principalEmail>").append(primary_person_email).append("</principalEmail>")
			
			.append("<serviceManager>").append("").append("</serviceManager>")
			.append("<serviceManagerTel>").append("").append("</serviceManagerTel>")
			.append("<serviceManagerEmail>").append("").append("</serviceManagerEmail>")
			.append("<serviceManagerAddr>").append("").append("</serviceManagerAddr>")
			.append("<serviceManagerPC>").append("").append("</serviceManagerPC>")
			.append("<serviceManagerFax>").append("").append("</serviceManagerFax>")
			.append("<license>").append(business_license).append("</license>")
			.append("<contractExpireDate>").append(contract_exp_date).append("</contractExpireDate>")
			.append("<accessNO>").append(partner_number).append("</accessNO>")
			.append("<settlementCycle>").append(settle_cycle).append("</settlementCycle>")
			.append("<settlementPayType>").append(settle_pay_method).append("</settlementPayType>")
			.append("<settlementPercent>").append(settle_rate).append("</settlementPercent>")
			.append("<CSWebsite>").append("").append("</CSWebsite>")
			.append("<status>").append(state).append("</status>")
			.append("<PlatForm>").append(system_code).append("</PlatForm>")
			.append("</SPInfo>");

			//bf.append("</ProductInfo>");
			
		bf.append("</CSPInfoSyncReq>");
		
		System.out.println("-------CSPInfoSyncReq------------="+bf.toString());
		return bf.toString();
	}
}
