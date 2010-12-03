package com.ztesoft.crm.customer.custinfo.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.crm.customer.custinfo.common.CustHelper;
import com.ztesoft.crm.customer.custinfo.dao.AcctDAO;
import com.ztesoft.crm.customer.custinfo.dao.BankDAO;
import com.ztesoft.crm.customer.custinfo.dao.CustContactInfoDAO;
import com.ztesoft.crm.customer.custinfo.dao.CustCorporateInfoDAO;
import com.ztesoft.crm.customer.custinfo.dao.CustDAO;
import com.ztesoft.crm.customer.custinfo.dao.CustPersonInfoDAO;
import com.ztesoft.crm.customer.custinfo.vo.IndustryVO;
import com.ztesoft.crm.salesres.common.CommonDAO;

/**
 * CustBO.java
 * 
 * @function:
 * 
 * @author nik
 * @since 2010-1-23
 * @modified
 */
public class CustBO extends DictAction {
	
	/**
	 * �ͻ���ҵ���Ͳ�ѯ
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String queryIndustryXml(DynamicDict dto) throws Exception{
		Map param = Const.getParam(dto);
		String pAttrId = (String) param.get("attrId");
		String pAttrValueId = (String) param.get("attrValueId");
		
		List retlist = null;
		
		String sql = "";
		CommonDAO dao = new CommonDAO();
		if("-1".equals(pAttrId)){
			sql =" select attr_id ,attr_value_id ,attr_value as industry_code,attr_value_desc as industry_name from attribute_value where attr_id = ?";
			retlist = (ArrayList)dao.executeQueryForMapList(sql, new String[]{"40000"});
		}else{
			sql =" select a.attr_id ,a.attr_value_id ,a.attr_value as industry_code,a.attr_value_desc as industry_name " +
					"from attribute_value a,attribute_value_rela b " +
					"where a.attr_id =b.z_attr_id and a.attr_value_id =b.z_attr_value_id " +
					"and b.a_attr_id = ? and b.a_attr_value_id = ?";
			retlist = dao.executeQueryForMapList(sql, new String[]{pAttrId,pAttrValueId});
		}
		ArrayList vos = new ArrayList();
		for (Iterator iterator = retlist.iterator(); iterator.hasNext();) {
			HashMap item = (HashMap) iterator.next();
			IndustryVO vo = new IndustryVO();
			vo.loadFromHashMap(item);
			vos.add(vo);
		}
		String retXml = XMLSegBuilder.toXmlItems(vos);
		return retXml;
	}
	/**
	 * ��������ͻ�
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Map insertCust(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map Cust = (Map) param.get("Cust");

		// ���ÿͻ����룬��ҵ��𣬵�Ĭ������
		CustHelper helper = new CustHelper();
		helper.setDefaultValueForNewCust(Cust);

		Map contractInfo = (Map) Cust.get("contract_info");
		Map acctInfo = (Map) Cust.get("acct_info");

		CustDAO dao = new CustDAO();
		boolean result = dao.insert(Cust);

		if (result) {
			CustContactInfoDAO custCtDao = new CustContactInfoDAO();
			custCtDao.insert(contractInfo);
			AcctDAO acctDao = new AcctDAO();
			acctDao.insert(acctInfo);
			if(Cust.get("personExtInfo")!=null){
				CustPersonInfoDAO pdao = new CustPersonInfoDAO();
				pdao.insert((Map)Cust.get("personExtInfo"));
			}
			if(Cust.get("CorporateExtInfo")!=null){
				CustCorporateInfoDAO cdao = new CustCorporateInfoDAO();
				cdao.insert((Map)Cust.get("CorporateExtInfo"));
			}
		}
		// �����½��ͻ�
		Map keyCondMap = Cust;
		Map newCust = dao.findByPrimaryKey(keyCondMap);
		return newCust;
	}

	public Map updateCust(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map Cust = (Map) param.get("Cust");
		Map baseInfo = (Map)Cust.get("custBaseInfo");
		String custType =(String)baseInfo.get("type_flag");//���˿ͻ�����ҵ�ͻ�
		Map custConstract = (Map)Cust.get("custConstract");
		Map custExtendInfo = (Map)Cust.get("custExtendInfo");
		
		List currentEntity = (List)custConstract.get("currentEntity");
		String actionType = null,contactId= null,custId =(String)baseInfo.get("cust_id");
		
		doUpdateCustBaseInfo(baseInfo); 
		
		for (Iterator iterator = currentEntity.iterator(); iterator.hasNext();) {
			Map entity = (Map) iterator.next();
			actionType = (String)entity.get("action_type");
			contactId = (String)entity.get("contact_id");
			if ("M".equals(actionType)&&!"".equals(contactId)) {//��ԭ��¼���޸�
				doUpdateConstractInfo( entity);
			}
			if ("A".equals(actionType)) {//������¼���޸�
				if("".equals(contactId)){
					doAddConstractInfo( entity,custId);
				}
			}
		}
		
		List deleteEntity = (List)custConstract.get("deleteEntity");
		for (Iterator iterator = deleteEntity.iterator(); iterator.hasNext();) {
			Map entity = (Map) iterator.next();
			doDeleteConstactInfo(entity);
		}
		
		if(custType.equals("1")){
			doUpdatePersonInfo(custExtendInfo); 
		}else{
			doUpdateCorporateInfo(custExtendInfo); 
		}
		return baseInfo;
	}
	/**
	 * ��������ɾ�������ǰ�״̬�ĳ�ʧЧ
	 * @param constactInfo
	 * @throws FrameException
	 */
	public void doDeleteConstactInfo(Map constactInfo)throws FrameException{
		CustContactInfoDAO custCtDao = new CustContactInfoDAO();
		Map update = new HashMap(2);
		update.put("record_exp_date", DateFormatUtils.getFormatedDate());
		update.put("state_date", DateFormatUtils.getFormatedDate());
		//:����ҵ��Ҫ��ɾ������ʵ��Ϊ��������¼��״ֵ̬ΪʧЧ
		custCtDao.updateByKey(update,constactInfo);
	}
	/**
	 * ������ϵ����Ϣ
	 * @param constactInfo
	 * @param custId
	 * @throws FrameException
	 */
	public void doAddConstractInfo(Map constactInfo,String custId)throws FrameException{
		CustHelper helper = new CustHelper();
		helper.doSetDefaultContactInfo(constactInfo,custId);
		CustContactInfoDAO custCtDao = new CustContactInfoDAO();
		custCtDao.insert(constactInfo);
	}

	private void doUpdateConstractInfo(Map constactInfo) throws FrameException {
		String keyStr = "contact_id,seq_nbr";
		Map keyCondMap = Const.getMapForTargetStr(constactInfo, keyStr);
		CustContactInfoDAO dao = new CustContactInfoDAO();
		
		Map orgnialConstactInfo = dao.findByPrimaryKey(keyCondMap);
		if (orgnialConstactInfo.size()>0) {//ԭ��¼����
			if( !CustHelper.compareHashMapValues(orgnialConstactInfo, constactInfo)){
				//ԭ��¼���޸Ĺ����޸�ԭ��¼��״̬ʱ�䣬ʧЧʱ�䣬����ֵΪʧЧ
				Map update = new HashMap(2);
				update.put("record_exp_date", DateFormatUtils.getFormatedDate());
				update.put("state_date", DateFormatUtils.getFormatedDate());
				dao.updateByKey(update, keyCondMap);
				//�����µļ�¼
				String seqNbr = (String)constactInfo.get("seq_nbr");
				int newSeqNbr = new Integer(seqNbr).intValue();
				newSeqNbr+=1;
				constactInfo.put("seq_nbr", newSeqNbr+"");
				constactInfo.put("action_type", "M");
				constactInfo.put("state_date", DateFormatUtils.getFormatedDate());
				dao.insert(constactInfo);
			}
		}
	}
	private void doUpdateCustBaseInfo(Map baseInfo) throws FrameException {
		String keyStr = "cust_id,seq_nbr";
		Map keyCondMap = Const.getMapForTargetStr(baseInfo, keyStr);
		CustDAO dao = new CustDAO();
		
		Map orgnialBaseInfo = dao.findByPrimaryKey(keyCondMap);
		if (orgnialBaseInfo.size()>0) {//ԭ��¼����
			if( !CustHelper.compareHashMapValues(orgnialBaseInfo, baseInfo)){
				//ԭ��¼���޸Ĺ����޸�ԭ��¼��״̬ʱ�䣬ʧЧʱ�䣬����ֵΪʧЧ
				Map update = new HashMap(2);
				update.put("record_exp_date", DateFormatUtils.getFormatedDate());
				update.put("state_date", DateFormatUtils.getFormatedDate());
				dao.updateByKey(update, keyCondMap);
				//�����µļ�¼
				String seqNbr = (String)baseInfo.get("seq_nbr");
				int newSeqNbr = new Integer(seqNbr).intValue();
				newSeqNbr+=1;
				baseInfo.put("seq_nbr", newSeqNbr+"");
				baseInfo.put("action_type", "M");
				baseInfo.put("state_date", DateFormatUtils.getFormatedDate());
				dao.insert(baseInfo);
			}
		}
	}
	private void doUpdatePersonInfo(Map personInfo) throws FrameException {
		String keyStr = "cust_id,seq_nbr";
		Map keyCondMap = Const.getMapForTargetStr(personInfo, keyStr);
		CustPersonInfoDAO dao = new CustPersonInfoDAO();
		
		Map orgnialPersonInfo = dao.findByPrimaryKey(keyCondMap);
		if (orgnialPersonInfo.size()>0) {//ԭ��¼����
			if( !CustHelper.compareHashMapValues(orgnialPersonInfo, personInfo)){
				//ԭ��¼���޸Ĺ����޸�ԭ��¼��״̬ʱ�䣬ʧЧʱ�䣬����ֵΪʧЧ
				Map update = new HashMap(2);
				update.put("record_exp_date", DateFormatUtils.getFormatedDate());
				update.put("state_date", DateFormatUtils.getFormatedDate());
				dao.updateByKey(update, keyCondMap);
				//�����µļ�¼
				String seqNbr = (String)personInfo.get("seq_nbr");
				int newSeqNbr = new Integer(seqNbr).intValue();
				newSeqNbr+=1;
				personInfo.put("seq_nbr", newSeqNbr+"");
				personInfo.put("action_type", "M");
				personInfo.put("state_date", DateFormatUtils.getFormatedDate());
				dao.insert(personInfo);
			}
		}else{
			CustHelper helper = new CustHelper();
			helper.setDefaultCustExtendInfo(personInfo);
			dao.insert(personInfo);
		}
	}
	private void doUpdateCorporateInfo(Map corporateInfo) throws FrameException {
		String keyStr = "cust_id,seq_nbr";
		Map keyCondMap = Const.getMapForTargetStr(corporateInfo, keyStr);
		CustCorporateInfoDAO dao = new CustCorporateInfoDAO();
		
		Map orgnialCorporateInfo = dao.findByPrimaryKey(keyCondMap);
		if (orgnialCorporateInfo.size()>0) {//ԭ��¼����
			if( !CustHelper.compareHashMapValues(orgnialCorporateInfo, corporateInfo)){
				//ԭ��¼���޸Ĺ����޸�ԭ��¼��״̬ʱ�䣬ʧЧʱ�䣬����ֵΪʧЧ
				Map update = new HashMap(2);
				update.put("record_exp_date", DateFormatUtils.getFormatedDate());
				update.put("state_date", DateFormatUtils.getFormatedDate());
				dao.updateByKey(update, keyCondMap);
				//�����µļ�¼
				String seqNbr = (String)corporateInfo.get("seq_nbr");
				int newSeqNbr = new Integer(seqNbr).intValue();
				newSeqNbr+=1;
				corporateInfo.put("seq_nbr", newSeqNbr+"");
				corporateInfo.put("action_type", "M");
				corporateInfo.put("state_date", DateFormatUtils.getFormatedDate());
				dao.insert(corporateInfo);
			}
		}else{
			CustHelper helper = new CustHelper();
			helper.setDefaultCustExtendInfo(corporateInfo);
			dao.insert(corporateInfo);
		}
	}
	
	public PageModel getCustData(DynamicDict dto) throws Exception {
        //��������
        Map param = Const.getParam(dto);
        StringBuffer whereCond = new StringBuffer();
        List para = new ArrayList();

        String sPartyType  = (String)param.get("sPartyType");
        String sQueryCond  = (String)param.get("sQueryCond");
        String sContent  = (String)param.get("sContent");
        
        if(sContent == null){ sContent = "";}
        if(sPartyType == null ){ sPartyType = "" ;} //��ʱ����ͻ��� 
        if(sQueryCond == null ){ sQueryCond = "" ;}
        
        whereCond.append(" and  record_exp_date =").append(CustHelper.getDefaultExpDate());
        
        if(!("".equals(sContent)))
        {
            if("scode".equals(sQueryCond))
            {
                whereCond.append("  and cust_code like  ? ");
                para.add("%" + Const.getStrValue(param, "sContent") + "%");
            }
            if("sname".equals(sQueryCond))
            {
                whereCond.append("  and cust_name like  ? ");
                para.add("%" + Const.getStrValue(param, "sContent") + "%");
            }
            if("scertnum".equals(sQueryCond))
            {
                whereCond.append("  and certi_number like  ? ");
                para.add("%" + Const.getStrValue(param, "sContent") + "%");
            }
            if("saddr".equals(sQueryCond))
            {
                whereCond.append("  and cust_addr like  ? ");
                para.add("%" + Const.getStrValue(param, "sContent") + "%");
            }
        }

        int pageSize = Const.getPageSize(param);
        int pageIndex = Const.getPageIndex(param);

        //����DAO����
        CustDAO dao = new CustDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

	/**
	 * ��ѯ�ͻ����ṩ���ͻ��Ӵ�����
	 * 
	 * @param lan_id
	 * @param search_type
	 *            ��ѯ���� A:ͨ���ͻ���ʶ��ѯ�� B:ͨ���ͻ����ƽ��в�ѯ��C:ͨ��֤���������
	 * @param search_value
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws FrameException
	 */
	public PageModel qryCustInfo(DynamicDict dto) throws FrameException {

		Map param = Const.getParam(dto);
		if (!Const.containStrValue(param, "lan_id")
				|| !Const.containStrValue(param, "lan_id")) {
			return new PageModel();
		}
		String lan_id = Const.getStrValue(param, "lan_id");
		String search_type = Const.getStrValue(param, "search_type");
		String search_value = Const.getStrValue(param, "search_value");
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		if (search_value == null || search_value.length() == 0) {
			return new PageModel();
		}
		StringBuffer sbuffer = new StringBuffer(" and a.service_region =? ");
		sbuffer.append(" and a.record_exp_date =").append(CustHelper.getDefaultExpDate());
		List para = new ArrayList();
		para.add(lan_id);

		if ("A".equals(search_type)) {
			sbuffer.append(" and a.cust_id =? ");
			para.add(search_value);
		} else if ("B".equals(search_type)) {
			sbuffer.append(" and a.cust_name like ? ");
			para.add(search_value);
		} else if ("C".equals(search_type)) {
			sbuffer.append(" and a.certi_number =? ");
			para.add(search_value);
		} else {
			return new PageModel();
		}

		// ����DAO����
        CustDAO dao = new CustDAO();
        dao.setSelectSQL( " select a.cust_id,a.seq_nbr,a.cust_order_id,a.order_id,a.action_type,a.record_eff_date,a.record_exp_date,a.state_date,a.cust_name,a.cust_code,a.certi_type,a.certi_number,a.certi_address,a.certi_org,a.service_region,a.market_region,a.type_flag,a.cust_type,a.cust_type_attr,a.sub_cust_type_attr,a.cust_brand,a.sub_cust_brand,a.is_vip,a.importance_level,a.service_level,a.industry_code,a.manage_type,a.town_flag,a.lieu_id,a.telecom_id,a.cust_state,a.secr_grade,a.canton_no,a.city_no,a.cust_addr,a.addr_id,a.cust_mgr,a.become_date,a.create_date,a.ini_password,a.def_acct_id,a.grp_cust_code,a.charge_province_code,a.charge_latn_id,a.real_flag,a.not_real_reason,a.accept_channel,a.notes " +
							   " from CUST a where 1=1 ");
        dao.setSelectCountSQL(" select count(*) as col_counts from CUST a  where 1=1 ");
        PageModel result = dao.searchByCond(sbuffer.toString(), para,
                pageIndex, pageSize);

		return result;
	}

	/**
	 * �ͻ��������
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchCustData(DynamicDict dto) throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer(" and a.service_region=? and a.record_exp_date = ");
		
		whereCond.append(CustHelper.getDefaultExpDate());
		List para = new ArrayList();
		para.add(Const.getStrValue(param, "servRegoin"));
		CustDAO dao = new CustDAO();
		PageModel result =null;
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		if (Const.containStrValue(param, "qryType")) {
			String qryType = Const.getStrValue(param, "qryType");
			if(qryType.equals("product_no")){//ҵ�����
				Map p= new HashMap(2);
				p.put("searchType", "D");
				p.put("searchValue", Const.getStrValue(param, "qryParam"));
				p.put("lanId", Const.getStrValue(param, "servRegoin"));
				p.put("pageIndex", new Integer(pageIndex));
				p.put("pageSize", new Integer(pageSize));
				result = DataTranslate._PageModel(ServiceManager
						.callJavaBeanService(ServiceList.CustReceptionBO, "getCustList",
								p));
				return result;
			} else if (qryType.equals("id_card")) {// �������֤��ѯ
				whereCond.append(" and a.certi_type='A' and a.certi_number = ? ");
				para.add(Const.getStrValue(param, "qryParam"));
			} else if (qryType.equals("cust_name")) {// �ͻ����Ʋ�ѯ
				whereCond.append(" and a.cust_name like ? ");
				para.add(Const.getStrValue(param, "qryParam") + "%");
			} else if (qryType.equals("pay_number")) {// �˻���ѯ
				whereCond.append(" and c.acct_code = ? ");
				dao.setSelectCountSQL("select count(*) as col_counts from CUST a left join ACCT c on a.cust_id = c.cust_id and c.record_exp_date="+CustHelper.getDefaultExpDate()+" where 1=1 ");
				dao.setSelectSQL("select a.cust_id,a.seq_nbr,a.cust_order_id,a.order_id,a.action_type,a.record_eff_date,a.record_exp_date,a.state_date,a.cust_name,a.cust_code,a.certi_type,a.certi_number,a.certi_address,a.certi_org,a.service_region,a.market_region,a.type_flag,a.cust_type,a.cust_type_attr,a.sub_cust_type_attr,a.cust_brand,a.sub_cust_brand,a.is_vip,a.importance_level,a.service_level,a.industry_code,a.manage_type,a.town_flag,a.lieu_id,a.telecom_id,a.cust_state,a.secr_grade,a.canton_no,a.city_no,a.cust_addr,a.addr_id,a.cust_mgr,a.become_date,a.create_date,a.ini_password,a.def_acct_id,a.grp_cust_code,a.charge_province_code,a.charge_latn_id,a.real_flag,a.not_real_reason,a.accept_channel,a.notes from CUST a "+
								 " left join ACCT c on a.cust_id = c.cust_id and c.record_exp_date="+CustHelper.getDefaultExpDate()+" where 1=1 ");
				para.add(Const.getStrValue(param, "qryParam"));
			} else if (qryType.equals("vip_card")) {// VIP���Ų�ѯ
			} 
		}

		// ����DAO����
	    result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;

	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getCustById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		CustDAO dao = new CustDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List getCustHisInfo(DynamicDict dto) throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer(" and a.cust_id=? and a.record_eff_date >= ");
		String expDate= Const.getStrValue(param, "expDate");
		whereCond.append(" to_date('").append(expDate).append("','yyyy-mm-dd hh24:mi:ss')");
		whereCond.append(" order by seq_nbr");
		List para = new ArrayList();
		para.add(Const.getStrValue(param, "custId"));
		CustDAO dao = new CustDAO();
		
		// ����DAO����
		List result = dao.findByCond(whereCond.toString(), para);
		return result;
		
	}
	public List getCustCorporateHisInfo(DynamicDict dto) throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer(" and cust_id=? and record_eff_date >= ");
		String expDate= Const.getStrValue(param, "expDate");
		whereCond.append(" to_date('").append(expDate).append("','yyyy-mm-dd hh24:mi:ss')");
		whereCond.append(" order by seq_nbr");
		List para = new ArrayList();
		para.add(Const.getStrValue(param, "custId"));
		CustCorporateInfoDAO dao = new CustCorporateInfoDAO();
		
		// ����DAO����
		List result = dao.findByCond(whereCond.toString(), para);
		return result;
		
	}
	public List getCustPersonHisInfo(DynamicDict dto) throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer(" and cust_id=? and record_eff_date >= ");
		String expDate= Const.getStrValue(param, "expDate");
		whereCond.append(" to_date('").append(expDate).append("','yyyy-mm-dd hh24:mi:ss')");
		whereCond.append(" order by seq_nbr");
		List para = new ArrayList();
		para.add(Const.getStrValue(param, "custId"));
		CustPersonInfoDAO dao = new CustPersonInfoDAO();
		
		// ����DAO����
		List result = dao.findByCond(whereCond.toString(), para);
		return result;
		
	}
	public List getCustContactHisInfo(DynamicDict dto) throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer(" and contact_id =? and cust_id=? and record_eff_date >= ");
		String expDate= Const.getStrValue(param, "expDate");
		whereCond.append(" to_date('").append(expDate).append("','yyyy-mm-dd hh24:mi:ss')");
		whereCond.append(" order by seq_nbr");
		List para = new ArrayList();
		para.add(Const.getStrValue(param, "contactId"));
		para.add(Const.getStrValue(param, "custId"));
		CustContactInfoDAO dao = new CustContactInfoDAO();
		
		// ����DAO����
	    List result = dao.findByCond(whereCond.toString(), para);
		return result;

	}
	public List findCustByCond(DynamicDict dto) throws Exception {
		// ��������
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// ��֯����DAO���������
		String whereCond = where.getWhereCond();
		List para = where.getPara();

		// ����DAO����
		CustDAO dao = new CustDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteCustById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		CustDAO dao = new CustDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}

	/**
	 * ��ѯ�ͻ���ϵ����Ϣ
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchCustContactInfoData(DynamicDict dto)
			throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer(" and record_exp_date = ? ");
		List para = new ArrayList();
		para.add(CustHelper.getDefaultExpDate());

		if (Const.containStrValue(param, "custId")) {
			whereCond.append(" and cust_id = ? ");
			para.add(Const.getStrValue(param, "custId"));
		}

		if (Const.containStrValue(param, "seqNbr")) {
			whereCond.append(" and seq_nbr = ? ");
			para.add(Const.getStrValue(param, "seqNbr"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// ����DAO����
		CustContactInfoDAO dao = new CustContactInfoDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	public List findCustContactInfoByCond(DynamicDict dto) throws Exception {
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		String whereCond = where.getWhereCond();
		whereCond+=" and record_exp_date = "+CustHelper.getDefaultExpDate()+" order by seq_nbr";
		List para = where.getPara();

		CustContactInfoDAO dao = new CustContactInfoDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getCustCorporateInfoById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		CustCorporateInfoDAO dao = new CustCorporateInfoDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getCustPersonInfoById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		CustPersonInfoDAO dao = new CustPersonInfoDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

}
