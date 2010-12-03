package com.ztesoft.vsop.spManage.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.JNDINames;

public class PartnerDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select partner_id,partner_code,partner_type,partner_desc,state,state_date,partner_level,partner_name,partner_password,partner_area_code,partner_url,partner_ip,create_date,partner_eng_name,partner_eng_desc,settle_cycle,settle_pay_method,settle_rate,cust_service_phone,if_roam,company_address,artificial_person,primary_person_name,primary_person_phone,primary_person_email,business_license,contract_exp_date,company_code,partner_number,cust_service_url,system_code from PARTNER where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from partner where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PARTNER (partner_id, partner_code, partner_type, partner_desc, state, state_date, partner_level, partner_name, partner_password, partner_area_code, partner_url, partner_ip, create_date, partner_eng_name, partner_eng_desc, settle_cycle, settle_pay_method, settle_rate, cust_service_phone, if_roam, company_address, artificial_person, primary_person_name, primary_person_phone, primary_person_email, business_license, contract_exp_date, company_code, partner_number, cust_service_url, system_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update PARTNER set partner_id=?, partner_code=?, partner_type=?, partner_desc=?, state=?, state_date=?, partner_level=?, partner_name=?, partner_password=?, partner_area_code=?, partner_url=?, partner_ip=?,  partner_eng_name=?, partner_eng_desc=?, settle_cycle=?, settle_pay_method=?, settle_rate=?, cust_service_phone=?, if_roam=?, company_address=?, artificial_person=?, primary_person_name=?, primary_person_phone=?, primary_person_email=?, business_license=?, contract_exp_date=?, company_code=?, partner_number=?, cust_service_url=?, system_code=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from PARTNER where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from PARTNER where partner_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update PARTNER set partner_id=?, partner_code=?, partner_type=?, partner_desc=?, state=?, state_date=?, partner_level=?, partner_name=?, partner_password=?, partner_area_code=?, partner_url=?, partner_ip=?,partner_eng_name=?, partner_eng_desc=?, settle_cycle=?, settle_pay_method=?, settle_rate=?, cust_service_phone=?, if_roam=?, company_address=?, artificial_person=?, primary_person_name=?, primary_person_phone=?, primary_person_email=?, business_license=?, contract_exp_date=?, company_code=?, partner_number=?, cust_service_url=?, system_code=? where partner_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select partner_id,partner_code,partner_type,partner_desc,state,state_date,partner_level,partner_name,partner_password,partner_area_code,partner_url,partner_ip,create_date,partner_eng_name,partner_eng_desc,settle_cycle,settle_pay_method,settle_rate,cust_service_phone,if_roam,company_address,artificial_person,primary_person_name,primary_person_phone,primary_person_email,business_license,contract_exp_date,company_code,partner_number,cust_service_url,system_code from PARTNER where partner_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.VSOP_DATASOURCE ;


	public PartnerDAO() {

	}
	private String GET_KEY = "select partner_id from partner p where p.system_code=? and p.partner_code=? " ;

	public String getKey(String systemCode , String partnerCode) throws Exception{
		List whereCondParams = new ArrayList();
		whereCondParams.add(systemCode) ;
		whereCondParams.add(partnerCode) ;
		
		List arrayList = findBySql( GET_KEY, whereCondParams , Const.UN_JUDGE_ERROR, Const.UN_JUDGE_ERROR, "");
		if (arrayList != null && arrayList.size()>0){
			Map m = (Map)arrayList.get(0);
			return Const.getStrValue(m, "partner_id") ;
		}else
			return "";
	}
	
	public String getKey(String partnerCode) throws Exception {
		List whereCondParams = new ArrayList();
		whereCondParams.add(partnerCode) ;
		
		String GET_KEY_1 = "select partner_id from partner p where p.partner_code=? " ;
		List arrayList = findBySql( GET_KEY_1, whereCondParams , Const.UN_JUDGE_ERROR, Const.UN_JUDGE_ERROR, "");
		if (arrayList != null && arrayList.size()>0){
			Map m = (Map)arrayList.get(0);
			return Const.getStrValue(m, "partner_id") ;
		}else
			return "";
	}
	

	/**
	 * Insert��������
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateInsertParams(Map map) throws FrameException{
		if(map == null || map.isEmpty())
			return null ;
		List params = new ArrayList() ;
							
		params.add(map.get("partner_id")) ;
									
		params.add(map.get("partner_code")) ;
									
		params.add(map.get("partner_type")) ;
									
		params.add(map.get("partner_desc")) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("partner_level")) ;
									
		params.add(map.get("partner_name")) ;
									
		params.add(map.get("partner_password")) ;
									
		params.add(map.get("partner_area_code")) ;
									
		params.add(map.get("partner_url")) ;
									
		params.add(map.get("partner_ip")) ;

		params.add(DAOUtils.getCurrentDate()) ;
									
		params.add(map.get("partner_eng_name")) ;
									
		params.add(map.get("partner_eng_desc")) ;
									
		params.add(map.get("settle_cycle")) ;
									
		params.add(map.get("settle_pay_method")) ;
									
		params.add(map.get("settle_rate")) ;
									
		params.add(map.get("cust_service_phone")) ;
									
		params.add(map.get("if_roam")) ;
									
		params.add(map.get("company_address")) ;
									
		params.add(map.get("artificial_person")) ;
									
		params.add(map.get("primary_person_name")) ;
									
		params.add(map.get("primary_person_phone")) ;
									
		params.add(map.get("primary_person_email")) ;
									
		params.add(map.get("business_license")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("contract_exp_date" ))) ;
									
		params.add(map.get("company_code")) ;
									
		params.add(map.get("partner_number")) ;
									
		params.add(map.get("cust_service_url")) ;
									
		params.add(map.get("system_code")) ;
						
		return params ;
	}
	

	/**
	 * update ��������
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo , List condParas) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		
		List params = new ArrayList() ;
				
					
		params.add(vo.get("partner_id")) ;
						
					
		params.add(vo.get("partner_code")) ;
						
					
		params.add(vo.get("partner_type")) ;
						
					
		params.add(vo.get("partner_desc")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("partner_level")) ;
						
					
		params.add(vo.get("partner_name")) ;
						
					
		params.add(vo.get("partner_password")) ;
						
					
		params.add(vo.get("partner_area_code")) ;
						
					
		params.add(vo.get("partner_url")) ;
						
					
		params.add(vo.get("partner_ip")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
					
		params.add(vo.get("partner_eng_name")) ;
						
					
		params.add(vo.get("partner_eng_desc")) ;
						
					
		params.add(vo.get("settle_cycle")) ;
						
					
		params.add(vo.get("settle_pay_method")) ;
						
					
		params.add(vo.get("settle_rate")) ;
						
					
		params.add(vo.get("cust_service_phone")) ;
						
					
		params.add(vo.get("if_roam")) ;
						
					
		params.add(vo.get("company_address")) ;
						
					
		params.add(vo.get("artificial_person")) ;
						
					
		params.add(vo.get("primary_person_name")) ;
						
					
		params.add(vo.get("primary_person_phone")) ;
						
					
		params.add(vo.get("primary_person_email")) ;
						
					
		params.add(vo.get("business_license")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("contract_exp_date" ))) ;
						
					
		params.add(vo.get("company_code")) ;
						
					
		params.add(vo.get("partner_number")) ;
						
					
		params.add(vo.get("cust_service_url")) ;
						
					
		params.add(vo.get("system_code")) ;
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
	}
	
		/**	
	 * �����������²�������
	 * @param vo
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParamsByKey(Map vo , Map keyCondMap) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(vo.get("partner_id")) ;
									
		params.add(vo.get("partner_code")) ;
									
		params.add(vo.get("partner_type")) ;
									
		params.add(vo.get("partner_desc")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("partner_level")) ;
									
		params.add(vo.get("partner_name")) ;
									
		params.add(vo.get("partner_password")) ;
									
		params.add(vo.get("partner_area_code")) ;
									
		params.add(vo.get("partner_url")) ;
									
		params.add(vo.get("partner_ip")) ;
						
		//params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
									
		params.add(vo.get("partner_eng_name")) ;
									
		params.add(vo.get("partner_eng_desc")) ;
									
		params.add(vo.get("settle_cycle")) ;
									
		params.add(vo.get("settle_pay_method")) ;
									
		params.add(vo.get("settle_rate")) ;
									
		params.add(vo.get("cust_service_phone")) ;
									
		params.add(vo.get("if_roam")) ;
									
		params.add(vo.get("company_address")) ;
									
		params.add(vo.get("artificial_person")) ;
									
		params.add(vo.get("primary_person_name")) ;
									
		params.add(vo.get("primary_person_phone")) ;
									
		params.add(vo.get("primary_person_email")) ;
									
		params.add(vo.get("business_license")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("contract_exp_date" ))) ;
									
		params.add(vo.get("company_code")) ;
									
		params.add(vo.get("partner_number")) ;
									
		params.add(vo.get("cust_service_url")) ;
									
		params.add(vo.get("system_code")) ;
						
							
		params.add(keyCondMap.get("partner_id")) ;
						
		return params ;
	}
		
		/**
	 * ����������������
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */	
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(keyCondMap.get("partner_id")) ;
						
		return params  ;
	}
	
	public void cancelPartnerById(String partnerId) {
		
		String updateStr = "update PARTNER set STATE=3, STATE_DATE=sysdate where PARTNER_ID=" + partnerId;
		
		//֧��informix
		String updateStr_informix = "update PARTNER set STATE=3, STATE_DATE=current where PARTNER_ID=" + partnerId;
		
		
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)){ 
			updateStr=updateStr_informix;		
		}
		
		try {
			updateBySQL(updateStr, new HashMap());
		} catch (FrameException e) {
			e.printStackTrace();
		}
	}
	
	/**��
	 * @param SQL update����
	 * @param vo��������������LinkedHashMapʵ�֣��������ܱ�������Ĳ���˳�򲻱�
	 * @return
	 * @throws FrameException
	 */
	public boolean updateBySQL(String SQL ,Map vo) throws FrameException {
  		List updateParams = new ArrayList();
  		Iterator it = vo.entrySet().iterator();
  		while (it.hasNext())
  			{
  				Map.Entry pairs = (Map.Entry)it.next();
  				updateParams.add(pairs.getValue());
  			}
  		return Base.update(this.getDbName(), SQL, updateParams, 1, Const.UN_JUDGE_ERROR, "") > 0 ;
	}
	
	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		return this.SQL_DELETE_KEY ;
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
					
		return this.SQL_UPDATE_KEY ;
				
	}
	
	public String getSelectSQL(){
		return this.SQL_SELECT ;
	}
	
	public String getSelectCountSQL(){
		return this.SQL_SELECT_COUNT ;
	}
	
	public String getInsertSQL(){
		return this.SQL_INSERT ;
	}
	
	public String getUpdateSQL(){
		return this.SQL_UPDATE ;
	}
	
	public String getDeleteSQL(){
		return this.SQL_DELETE ;
	}
	
	public String getSQLSQLByKey() throws FrameException {
					
		return this.SQL_SELECT_KEY ;
				
	}
	
}