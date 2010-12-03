package com.ztesoft.crm.customer.custinfo.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.crm.salesres.common.CommonDAO;


/**
 * CodeGenerator.java
 *
 * @function: 新增客户，设置缺省信息
 *
 * @author nik
 * @since 2010-1-23
 * @modified
 */
public class CustHelper {
	//默认失效时间
    public static final String DEFAULT_EXPIRE_DATE = "2050-12-01";
    
    
    public static String getDefaultExpDate(){
    	return "to_date('"+CustHelper.DEFAULT_EXPIRE_DATE+"','yyyy-MM-dd')"; 
    }
    public static boolean compareHashMapValues(Map orginal,Map dest){
    	boolean result = true;
    	if(orginal==dest) return result;
    	Set keys = orginal.keySet();
    	for (Iterator key = keys.iterator(); key.hasNext();) {
			String k = (String) key.next();
			if("action_type".equals(k))continue;
			if(!orginal.get(k).equals(dest.get(k))){
				result = !result;
				break;
			}
		}
    	return result;
    }
    /**
     * 生成客户编码，参考重庆方式
     * @return
     */
    public String generatorCustCode() {
        String custCode = "";
        String Str = "";
        String seq = "";
        SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance()
                                                           .getSequenceManageDAO();
        seq = sequenceManageDAO.getNextSequence("CUST", "CUST_CODE");

        int n = seq.length();

        if (n < 8) {
            for (int k = 0; k < (8 - n); k++)
                Str += "0";

            Str = Str + seq;
        } else {
            Str = seq.substring(seq.length() - 8, seq.length());
        }

        custCode = "2023" + Str + "0000";

        return custCode;
    }
    
    public String generatorAcctCode() {
        String acctCode = "";
        String Str = "";
        String seq = "";
        SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance()
                                                           .getSequenceManageDAO();
        seq = sequenceManageDAO.getNextSequence("ACCT", "ACCT_CODE");

        int n = seq.length();

        if (n < 8) {
            for (int k = 0; k < (8 - n); k++)
                Str += "0";

            Str = Str + seq;
        } else {
            Str = seq.substring(seq.length() - 8, seq.length());
        }

        acctCode = "2023" + Str + "0000";

        return acctCode;
    }

    public String generatorCustId() {
    	SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance()
    	.getSequenceManageDAO();
    	
    	return sequenceManageDAO.getNextSequence("CUST", "CUST_ID");
    }
    public String generatorAcctId() {
        SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance()
                                                           .getSequenceManageDAO();

        return sequenceManageDAO.getNextSequence("ACCT", "ACCT_ID");
    }

    public String generatorContractId() {
        SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance()
                                                           .getSequenceManageDAO();

        return sequenceManageDAO.getNextSequence("CUST_CONTACT_INFO", "CONTACT_ID");
    }

    /**
     * 设置新增客户的缺省信息
     * @param cust
     */
    public void setDefaultValueForNewCust(Map cust) {
        if (cust == null) {
            return;
        }
        String custId = generatorCustId();
        setDefaultBaseInfo(cust, custId);
        setDefaultAcctInfo(cust, custId);
        setDefaultContractInfo(cust, custId);
        String typeFlag= (String)cust.get("type_flag");
		if("1".equals(typeFlag)){//个人客户
			setDefaultPersonExtInfo(cust);
		}else{
			setDefaultCorporateExtInfo(cust);
		}
    }

	private void setDefaultCorporateExtInfo(Map cust) {
		Map param = new HashMap();
		param.put("cust_id", cust.get("cust_id"));
		param.put("seq_nbr", "1");
		param.put("record_eff_date", cust.get("record_eff_date"));
		param.put("record_exp_date", cust.get("record_exp_date"));
		param.put("state_date", cust.get("state_date"));
		param.put("action_type", "A");
		param.put("org_name", cust.get("cust_name"));
		cust.put("CorporateExtInfo", param);
	}
	private void setDefaultPersonExtInfo(Map cust) {
		Map param = new HashMap();
		param.put("cust_id", cust.get("cust_id"));
		param.put("seq_nbr", "1");
		param.put("record_eff_date", cust.get("record_eff_date"));
		param.put("record_exp_date", cust.get("record_exp_date"));
		param.put("state_date", cust.get("state_date"));
		param.put("action_type", "A");
		String ctype = (String)cust.get("certi_type");
		if(ctype.equals("A")){//身份证
			String idCard = (String)cust.get("certi_number");
			String birthDate = null;
			if(idCard!=null){
				if (idCard.length()==18) {
					birthDate = idCard.substring(6, 14);
				} else {
					birthDate = "19"+idCard.substring(6, 12);
				}
			}
			param.put("birth_date", birthDate.substring(0,4)+"-"+birthDate.substring(4,6)+"-"+birthDate.substring(6,8));
		}
		//param.put("birth_date", cust.get("birth_date"));
		param.put("name", cust.get("cust_name"));
		
		cust.put("personExtInfo", param);
	}
	/**
	 * 客户非空值设置
	 * @param cust
	 * @param custId
	 */
	private void setDefaultBaseInfo(Map cust, String custId) {
		cust.put("cust_id", custId);
        cust.put("seq_nbr", "1");
        /** 1：增 2：删 3：改 */
        cust.put("action_type", "A");
        //管控级别=“省级”；营销责任地=服务归属地；运营商=“中国电信”
        cust.put("manage_type", "1");
        cust.put("telecom_id", "100");
        cust.put("market_region", cust.get("service_region"));
        cust.put("record_eff_date", DateFormatUtils.getFormatedDateTime());
        cust.put("record_exp_date", DEFAULT_EXPIRE_DATE);
        cust.put("state_date", DateFormatUtils.getFormatedDateTime());
        cust.put("create_date", DateFormatUtils.getFormatedDateTime());
        cust.put("cust_code", generatorCustCode());
        /** 1：个人客户 0：非个人客户 */
        if ("1".equals((String)cust.get("type_flag"))) {
        	/** 1：政企客户 2：家庭客户 3：个人客户 */
        	cust.put("cust_type", "3");
		} else {
			cust.put("cust_type", "1");
		}
        cust.put("is_vip", "0");
        
        setIndustryCode(cust);
        /** 100:潜在 200:在网 300:离网 */
        cust.put("cust_state", "200");
        /** 1:保密 2:不保密 */
        cust.put("secr_grade", "2");
        /**设置行业类别*/
        
	}

	private void setIndustryCode(Map cust) {
		String industryId = "";
		List reList = new CommonDAO().executeQueryForMapList(" select industry_id,count(1) from INDUSTRY_NAME_KEY where ? like NAME_KEY and rownum=1 group by industry_id order by count(1) desc ", new String[]{(String)cust.get("cust_name")});
		if(reList.size()>0){
			HashMap reMap = (HashMap) reList.get(0);
			industryId = (String)reMap.get("INDUSTRY_ID");
			cust.put("industry_code", industryId);
		}else{
			cust.put("industry_code", "0");
		}
		
	}
	/**
	 * 新增客户联系信息的缺省值设置
	 * @param cust
	 * @param custId
	 */
	public  void setDefaultContractInfo(Map cust, String custId) {
		Map contactInfo = (Map) cust.get("contract_info");
		doSetDefaultContactInfo( contactInfo,  custId);
	}
	/**
	 * 新增客户联系信息的缺省值设置
	 * @param cust
	 * @param custId
	 */
	public  void doSetDefaultContactInfo(Map contactInfo, String custId) {
		contactInfo.put("contact_id", generatorContractId());
		contactInfo.put("record_eff_date", DateFormatUtils.getFormatedDate());
		contactInfo.put("record_exp_date", DEFAULT_EXPIRE_DATE);
		contactInfo.put("state_date", DateFormatUtils.getFormatedDate());
		contactInfo.put("seq_nbr", "1");
		contactInfo.put("action_type", "A");
		contactInfo.put("cust_id", custId);
	}
    /**
     * 新增客户的账户缺省值设置
     * @param cust
     * @param custId
     */
    public void setDefaultAcctInfo(Map cust, String custId) {
        Map acctInfo = new HashMap();
        acctInfo.put("acct_id", generatorAcctId());
        acctInfo.put("acct_code", generatorAcctCode());
        acctInfo.put("seq_nbr", "1");
        acctInfo.put("record_eff_date", DateFormatUtils.getFormatedDate());
        acctInfo.put("record_exp_date", DEFAULT_EXPIRE_DATE);
        acctInfo.put("state_date", DateFormatUtils.getFormatedDate());
        acctInfo.put("cust_id", custId);
        Map contactInfo = (Map) cust.get("contract_info");
        acctInfo.put("post_name",(String)contactInfo.get("contact_name") );
        acctInfo.put("bill_post_addr",(String)contactInfo.get("post_addr") );
        acctInfo.put("acct_name", (String)cust.get("cust_name"));
        acctInfo.put("def_acct_flag", "Y");
        acctInfo.put("state", "00A");
        cust.put("acct_info", acctInfo);
    }
    /**
     * 设置新增客户默认扩展信息
     * @param extendInfo
     */
    public void setDefaultCustExtendInfo(Map extendInfo){
    	extendInfo.put("seq_nbr", "1");
    	extendInfo.put("action_type", "A");
    	extendInfo.put("record_eff_date", DateFormatUtils.getFormatedDate());
    	extendInfo.put("record_exp_date", DEFAULT_EXPIRE_DATE);
    	extendInfo.put("state_date", DateFormatUtils.getFormatedDate());
    }
}
