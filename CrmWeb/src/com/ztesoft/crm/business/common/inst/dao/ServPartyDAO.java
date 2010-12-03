package com.ztesoft.crm.business.common.inst.dao;

import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;


public class ServPartyDAO extends BusiDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,seq,old_seq,fcomp_inst_id,rel_type,rel_id,party_org_id,party_id,eff_date,exp_date from SERV_PARTY where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from serv_party where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SERV_PARTY (ord_item_id, cust_ord_id, ord_id, action_type, state_date, seq, old_seq, fcomp_inst_id, rel_type, rel_id, party_org_id, party_id, eff_date, exp_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update SERV_PARTY set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, seq=?, old_seq=?, fcomp_inst_id=?, rel_type=?, rel_id=?, party_org_id=?, party_id=?, eff_date=?, exp_date=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from SERV_PARTY where 1=1 ";
	
		
		
	
	//	��ǰDAO �������ݿ�name
	private String dbName = "ORD" ;


	public ServPartyDAO() {

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
				
		params.add((String)map.get("ord_item_id" ));
				
		params.add((String)map.get("cust_ord_id" ));
				
		params.add((String)map.get("ord_id" ));
				
		params.add((String)map.get("action_type" ));
				
		params.add((String)map.get("state_date" ));
				
		params.add((String)map.get("seq" ));
				
		params.add((String)map.get("old_seq" ));
				
		params.add((String)map.get("fcomp_inst_id" ));
				
		params.add((String)map.get("rel_type" ));
				
		params.add((String)map.get("rel_id" ));
				
		params.add((String)map.get("party_org_id" ));
				
		params.add((String)map.get("party_id" ));
				
		params.add((String)map.get("eff_date" ));
				
		params.add((String)map.get("exp_date" ));
				
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
				
		params.add((String)vo.get("ord_item_id" ));
				
		params.add((String)vo.get("cust_ord_id" ));
				
		params.add((String)vo.get("ord_id" ));
				
		params.add((String)vo.get("action_type" ));
				
		params.add((String)vo.get("state_date" ));
				
		params.add((String)vo.get("seq" ));
				
		params.add((String)vo.get("old_seq" ));
				
		params.add((String)vo.get("fcomp_inst_id" ));
				
		params.add((String)vo.get("rel_type" ));
				
		params.add((String)vo.get("rel_id" ));
				
		params.add((String)vo.get("party_org_id" ));
				
		params.add((String)vo.get("party_id" ));
				
		params.add((String)vo.get("eff_date" ));
				
		params.add((String)vo.get("exp_date" ));
				
		
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add((String)condParas.get(i));
			}
		}
		return params ;
		
	}
	
		
	
	
	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		throw new FrameException("��ǰSQLû����������ȷ�Ͽ��ṹ ��") ;	
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
						
		throw new FrameException("��ǰSQLû����������ȷ�Ͽ��ṹ ��") ;	
				
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
								
		throw new FrameException("��ǰSQLû����������ȷ�Ͽ��ṹ ��") ;	
				
	}
	
	//�����޸ģ����Ż�
	public void batchUpdate(List servstatemaps) throws FrameException {

		for (int i = 0; i < servstatemaps.size(); i++) {
            List params = new ArrayList();
            boolean isfirstchange = true;
            StringBuffer sql = new StringBuffer();
            
            sql.append(" update serv_party set ");
            
			Map map = (Map)servstatemaps.get(i);			
			Iterator it=map.keySet().iterator();   
			while(it.hasNext()){
			    String skey = it.next().toString();   
			    String svalue = (String)map.get(skey);   
			    System.out.println(skey+"--"+svalue);   
			    
			    //���Բ����Ǹ������޸�
			    if (   "ord_item_id".equalsIgnoreCase(skey)
			    	 || "cust_ord_id".equalsIgnoreCase(skey)
			    	 || "ord_id".equalsIgnoreCase(skey)
			    	 || "action_type".equalsIgnoreCase(skey)			    	 
			    	 || "state_date".equalsIgnoreCase(skey)
			    	 || "seq".equalsIgnoreCase(skey)			    	 			    	 
			    	 || "old_seq".equalsIgnoreCase(skey)
			    	 || "fcomp_inst_id".equalsIgnoreCase(skey)
			    	 || "rel_type".equalsIgnoreCase(skey)
			    	 || "rel_id".equalsIgnoreCase(skey)
			    	 || "party_org_id".equalsIgnoreCase(skey)
			    	 || "party_id".equalsIgnoreCase(skey)
			    	 || "eff_date".equalsIgnoreCase(skey)
			    	 || "exp_date".equalsIgnoreCase(skey)) {
                    //���ǵ�һ��set��������Ҫ�Ӷ���
                    if (isfirstchange) {
                        isfirstchange = false;
                    }else{
                        sql.append(" , ");
                    }
			    	
			    	sql.append(skey.toString());
			    	sql.append(" = ? ");
			    	params.add(svalue);
			    }
			    else {
			    	//do nothing
			    }
			} 
			
			//��������
			sql.append(" where attr_id=? and serv_product_id=?  ");
			
			params.add(map.get("attr_id"));
			params.add(map.get("serv_product_id"));
			
			update(sql.toString(), params);
		}
	}
}
