package com.ztesoft.vsop.webservice.server;

import java.io.StringReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.crm.business.common.utils.SeqUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.spManage.dao.PartnerDAO;
import com.ztesoft.vsop.spManage.dao.PartnerSystemInfoDAO;
import com.ztesoft.vsop.spManage.vo.PartnerInfo;

/**
 * 
 * cooltan modify on 20100604 增加增量刷新缓存操作
 * liuyuming modify 20100908  修改注销spcp时直接根据动作更新partner表的state字段，不再对其他字段更新。
 *
 */
public class CRMSPSynManager  extends DictAction{
//	操作常量
	private static final String ADD = "1";

	private static final String UPDATE = "2";

	private static final String DELETE = "3";


	private static final String ResultCode_SUCESS = "0";

	private static final String ResultCode_FAIL = "-1";
	
	/**
	 * 产品同步处理接口
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Map spcpInfoSyn(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		String xml = (String) param.get("xml");
		PartnerInfo sysInfo = parsePartnerXml(xml);
		
		// 判断操作类型
		Map partner = sysInfo.getPartner();
		
		// 1：增加 2：修改 3：删除(注销)
		String OPFlag = Const.getStrValue(partner, "OPFlag");
		String partner_id = Const.getStrValue(partner, "partner_id");
		List platFormList = sysInfo.getPlatForms();

		try {
			if (ADD.equals(OPFlag)) {
				param.put("Partner", partner);
				dto.setValueByName(Const.ACTION_PARAMETER, param);
				this.insertPartner(dto);
				BatchInsertPlatForm(platFormList);//插入接入平台
				
				//重新获取新增的partner_id
				partner_id=Const.getStrValue(partner, "partner_id");
			} else if (DELETE.equals(OPFlag)) {
				// 更改SP状态为注销即可
				param.put("Partner", partner);
				dto.setValueByName(Const.ACTION_PARAMETER, param);
				//cooltan add
//				this.updatePartner(dto);
				updatePartnerState(partner_id);
			} else if (UPDATE.equals(OPFlag)) {
				param.put("Partner", partner);
				dto.setValueByName(Const.ACTION_PARAMETER, param);
				this.updatePartner(dto);
				
				PartnerSystemInfoDAO dao=new PartnerSystemInfoDAO();
				dao.batchDelete(partner_id);
				if (platFormList != null && !platFormList.isEmpty()) {
					BatchInsertPlatForm(platFormList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		StringUtil su = StringUtil.getInstance();
		String responseXml=su.getVsopResponse("CSPInfoSyncToVSOPResp", 
				 Const.getStrValue(partner, "StreamingNo"),
		   		 ResultCode_SUCESS,
		   		 "同步成功", 
		   		 null);
		Map map=new HashMap();
		map.put("responseXml", responseXml);
		map.put("partner_id", partner_id);
		return map;
	}
	
	//批量插入接入平台
	private void BatchInsertPlatForm(List platFroms) throws Exception{
		List resultPara = new ArrayList();
		Iterator it = platFroms.iterator();
		PartnerSystemInfoDAO dao=new PartnerSystemInfoDAO();
		while(it.hasNext()) {
			List servList = new ArrayList();
			Map map=(Map)it.next();
			servList=dao.translateInsertParams(map);
			resultPara.add(servList);
		}
		dao.batchInsertPlatForm(resultPara);
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
	public boolean updatePartnerState(String partnerId) throws Exception {
		PartnerDAO dao = new PartnerDAO();
		dao.cancelPartnerById( partnerId);
		
		return true ;
	}
	
	/**
	 * xml > 返回SP对象
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static PartnerInfo parsePartnerXml(String xmlStr)
			throws DocumentException, Exception {
		PartnerInfo synInfo = new PartnerInfo();

		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		Document doc = saxReader.read(reader);
		Element rootRequest = doc.getRootElement();
		Element sessionBody = rootRequest.element("SessionBody");
		Element root = sessionBody.element("CSPInfoSyncToVSOPReq");
		
		Element spinfo = root.element("SPInfo") ;
		String OPFlag = XMLUtils.getElementStringValue(root,"OPFlag");
		String SystemId = XMLUtils.getElementStringValue(root,"SystemId");
		String SPID =XMLUtils.getElementStringValue(spinfo,"SPID");
		String StreamingNo = XMLUtils.getElementStringValue(root,"StreamingNo");
		
		String partnerId = new PartnerDAO().getKey( SystemId ,  SPID) ;
		if(partnerId == null || "".equals(partnerId)){
			SequenceManageDAO sDao = new SequenceManageDAOImpl();
			partnerId = sDao.getNextSequence("PARTNER_INFO", "PARTNER_ID");
		}
		// 获取SP对象
		Map partner = getPartner(spinfo, SPID , SystemId , partnerId);
		partner.put("OPFlag", OPFlag) ;
		partner.put("StreamingNo", StreamingNo) ;
		
		// 业务能力 List
		List platFormList=getPlatFormList(spinfo, partnerId);

		// 设置到Map返回
		synInfo.setPartner(partner);
		synInfo.setPlatForms(platFormList) ;
		return synInfo;
	}
	
	/**
	 * 接入平台 xml > Map
	 * @return
	 * @throws Exception
	 */
	private static List getPlatFormList(Element root, String partner_id)
			throws Exception {
		String state =XMLUtils.getElementStringValue(root,"Status");
		ArrayList platformLst = new ArrayList();
		List PlatForm = root.elements("PlatForm");
		if (PlatForm != null) {
			for (int i = 0; i < PlatForm.size(); i++) {
				Element subElement = (Element) PlatForm.get(i);
				String platform_id = subElement.getStringValue();
				Map pf = new HashMap();
			    pf.put("partner_system_info_id", SeqUtil.getInst().getNext("PARTNER_SYSTEM_SEQ"));
				pf.put("system_code", platform_id);
				pf.put("partner_id", partner_id);
				pf.put("create_date", DateFormatUtils.getFormatedDate());
				pf.put("state", state);
				pf.put("state_date", DateFormatUtils.getFormatedDate());
				platformLst.add(pf);
			}
		}
		return platformLst;
	}

	/**
	 * SP xml > Map
	 * @return
	 * @throws Exception
	 */
	private static Map getPartner(Element spinfo, String SPID ,String SystemId , String partnerId)
			throws Exception {
		String Type = XMLUtils.getElementStringValue(spinfo,"Type");
		String NameCN = XMLUtils.getElementStringValue(spinfo,"NameCN");


		String NameEN = XMLUtils.getElementStringValue(spinfo,"NameEN");

		String DescriptionCN = XMLUtils.getElementStringValue(spinfo,"DescriptionCN");
		String DescriptionEN = XMLUtils.getElementStringValue(spinfo,"DescriptionEN");
		String CustomerCare = XMLUtils.getElementStringValue(spinfo,"CustomerCare");
		String WebsiteURL = XMLUtils.getElementStringValue(spinfo,"WebsiteURL");
		String ProvinceID = XMLUtils.getElementStringValue(spinfo,"ProvinceID");
		String RoamProperty = XMLUtils.getElementStringValue(spinfo,"RoamProperty");

		String CompanyAddress = XMLUtils.getElementStringValue(spinfo,"CompanyAddress");
		String LegalRepresentative = XMLUtils.getElementStringValue(spinfo,"LegalRepresentative");
		String Principal = XMLUtils.getElementStringValue(spinfo,"Principal");

		String principalTel = XMLUtils.getElementStringValue(spinfo,"PrincipalTel");

		String principalEmail = XMLUtils.getElementStringValue(spinfo,"PrincipalEmail");

		String ServiceManager = XMLUtils.getElementStringValue(spinfo,"ServiceManager");
		String ServiceManagerTel = XMLUtils.getElementStringValue(spinfo,"ServiceManagerTel");
		String ServiceManagerEmail = XMLUtils.getElementStringValue(spinfo,"ServiceManagerEmail");
		String ServiceManagerAddr = XMLUtils.getElementStringValue(spinfo,"ServiceManagerAddr");
		String ServiceManagerPC = XMLUtils.getElementStringValue(spinfo,"ServiceManagerPC");
		String ServiceManagerFax = XMLUtils.getElementStringValue(spinfo,"ServiceManagerFax");
		
		String License = XMLUtils.getElementStringValue(spinfo,"License");
		String ContractExpireDate = XMLUtils.getElementStringValue(spinfo,"ContractExpireDate");
		String AccessNO = XMLUtils.getElementStringValue(spinfo,"AccessNO");

		String SettlementCycle = XMLUtils.getElementStringValue(spinfo,"SettlementCycle");

		String SettlementPercent = XMLUtils.getElementStringValue(spinfo,"SettlementPercent");

		String Status = XMLUtils.getElementStringValue(spinfo,"Status");
		String SettlementPayType = XMLUtils.getElementStringValue(spinfo,"SettlementPayType");
		
		Map partner = new HashMap();
		partner.put("partner_eng_name", NameEN);
		partner.put("partner_eng_desc", DescriptionEN);
		partner.put("ServiceManager", ServiceManager);
		partner.put("ServiceManagerEmail", ServiceManagerEmail);
		partner.put("ServiceManagerAddr", ServiceManagerAddr);
		partner.put("ServiceManagerPC", ServiceManagerPC);
		partner.put("ServiceManagerFax", ServiceManagerFax);
		
		partner.put("settle_pay_method", SettlementPayType);
		partner.put("partner_code", SPID);
		partner.put("partner_id", partnerId);
		partner.put("partner_type", Type);
		partner.put("partner_name", NameCN);
		partner.put("system_code", SystemId);
		partner.put("state_date", DAOUtils.getFormatedDate());
		partner.put("partner_desc", DescriptionCN);
		partner.put("state", Status);
		
		partner.put("cust_service_phone", CustomerCare);
		partner.put("partner_url", WebsiteURL);
		partner.put("partner_area_code", ProvinceID);
		partner.put("if_roam", RoamProperty);

		partner.put("company_address", CompanyAddress);
		partner.put("artificial_person", LegalRepresentative);
		partner.put("primary_person_name", Principal);
		partner.put("primary_person_phone", principalTel);
		partner.put("primary_person_email", principalEmail);
		partner.put("product_desc", ServiceManagerTel);
		partner.put("business_license", License);
		
		// 转换ContractExpireDate
		partner.put("contract_exp_date", ContractExpireDate);
		if (ContractExpireDate != null && !ContractExpireDate.equals("")) {
			if (ContractExpireDate.length() == 14) {
				Date d = DAOUtils.parseDateTimeFormat_14(ContractExpireDate);
				partner.put("contract_exp_date", DAOUtils.getFormatedDateTime(d));
			}
		}
		
		partner.put("partner_number", AccessNO);
		partner.put("settle_cycle", SettlementCycle);
		partner.put("settle_rate", SettlementPercent);
		
		return partner;
	}
}
