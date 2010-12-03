package com.ztesoft.crm.business.common.history.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;

public class UhContactDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select serv_contact_id,cust_ord_id,product_offer_instance_id,contact_name,tel,mobile_phone,fax,bp,email,addr,post_code from UH_CONTACT where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from uh_contact where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into UH_CONTACT (serv_contact_id, cust_ord_id, product_offer_instance_id, contact_name, tel, mobile_phone, fax, bp, email, addr, post_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update UH_CONTACT set serv_contact_id=?, cust_ord_id=?, product_offer_instance_id=?, contact_name=?, tel=?, mobile_phone=?, fax=?, bp=?, email=?, addr=?, post_code=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from UH_CONTACT where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from UH_CONTACT where serv_contact_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update UH_CONTACT set serv_contact_id=?, cust_ord_id=?, product_offer_instance_id=?, contact_name=?, tel=?, mobile_phone=?, fax=?, bp=?, email=?, addr=?, post_code=? where serv_contact_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select serv_contact_id,cust_ord_id,product_offer_instance_id,contact_name,tel,mobile_phone,fax,bp,email,addr,post_code from UH_CONTACT where serv_contact_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = "ORD" ;


	public UhContactDAO() {

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
							
		params.add(map.get("serv_contact_id")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("product_offer_instance_id")) ;
									
		params.add(map.get("contact_name")) ;
									
		params.add(map.get("tel")) ;
									
		params.add(map.get("mobile_phone")) ;
									
		params.add(map.get("fax")) ;
									
		params.add(map.get("bp")) ;
									
		params.add(map.get("email")) ;
									
		params.add(map.get("addr")) ;
									
		params.add(map.get("post_code")) ;
						
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
				
					
		params.add(vo.get("serv_contact_id")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("product_offer_instance_id")) ;
						
					
		params.add(vo.get("contact_name")) ;
						
					
		params.add(vo.get("tel")) ;
						
					
		params.add(vo.get("mobile_phone")) ;
						
					
		params.add(vo.get("fax")) ;
						
					
		params.add(vo.get("bp")) ;
						
					
		params.add(vo.get("email")) ;
						
					
		params.add(vo.get("addr")) ;
						
					
		params.add(vo.get("post_code")) ;
						
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
							
		params.add(vo.get("serv_contact_id")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("product_offer_instance_id")) ;
									
		params.add(vo.get("contact_name")) ;
									
		params.add(vo.get("tel")) ;
									
		params.add(vo.get("mobile_phone")) ;
									
		params.add(vo.get("fax")) ;
									
		params.add(vo.get("bp")) ;
									
		params.add(vo.get("email")) ;
									
		params.add(vo.get("addr")) ;
									
		params.add(vo.get("post_code")) ;
						
							
		params.add(keyCondMap.get("serv_contact_id")) ;
						
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
							
		params.add(keyCondMap.get("serv_contact_id")) ;
						
		return params  ;
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
