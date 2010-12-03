package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ServRelationDAO extends AbstractDAO {

	//��ѯSQL
	private String SQL_SELECT = "select relation_id,serv_a_id,serv_z_id,relation_type_id,operation_flag,eff_date,exp_date,state,seq,party_role_id,party_id,oper_region_id,oper_date from SERVICE_RELATION where 1=1 ";

	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from service_relation where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SERVICE_RELATION (relation_id, serv_a_id, serv_z_id, relation_type_id, operation_flag, eff_date, exp_date, state, seq, party_role_id, party_id, oper_region_id, oper_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update SERVICE_RELATION set relation_id=?, serv_a_id=?, serv_z_id=?, relation_type_id=?, operation_flag=?, eff_date=?, exp_date=?, state=?, seq=?, party_role_id=?, party_id=?, oper_region_id=?, oper_date=? where 1=1 ";

	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from SERVICE_RELATION where 1=1 ";

		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from SERVICE_RELATION where relation_id=? ";

		//	��������update SQL
	private String SQL_UPDATE_KEY = "update SERVICE_RELATION set relation_id=?, serv_a_id=?, serv_z_id=?, relation_type_id=?, operation_flag=?, eff_date=?, exp_date=?, state=?, seq=?, party_role_id=?, party_id=?, oper_region_id=?, oper_date=? where relation_id=? and seq=?";

		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select relation_id,serv_a_id,serv_z_id,relation_type_id,operation_flag,eff_date,exp_date,state,seq,party_role_id,party_id,oper_region_id,oper_date from SERVICE_RELATION where relation_id=? and seq=? ";

	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ServRelationDAO() {

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

		params.add(map.get("relation_id")) ;

		params.add(map.get("serv_a_id")) ;

		params.add(map.get("serv_z_id")) ;

		params.add(map.get("relation_type_id")) ;

		params.add(map.get("operation_flag")) ;

		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;

		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;

		params.add(map.get("state")) ;

		params.add(map.get("seq")) ;

		params.add(map.get("party_role_id")) ;

		params.add(map.get("party_id")) ;

		params.add(map.get("oper_region_id")) ;

		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;

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


		params.add(vo.get("relation_id")) ;


		params.add(vo.get("serv_a_id")) ;


		params.add(vo.get("serv_z_id")) ;


		params.add(vo.get("relation_type_id")) ;


		params.add(vo.get("operation_flag")) ;


		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;


		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;


		params.add(vo.get("state")) ;


		params.add(vo.get("seq")) ;


		params.add(vo.get("party_role_id")) ;


		params.add(vo.get("party_id")) ;


		params.add(vo.get("oper_region_id")) ;


		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;

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

		params.add(vo.get("relation_id")) ;

		params.add(vo.get("serv_a_id")) ;

		params.add(vo.get("serv_z_id")) ;

		params.add(vo.get("relation_type_id")) ;

		params.add(vo.get("operation_flag")) ;

		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;

		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;

		params.add(vo.get("state")) ;

		params.add(vo.get("seq")) ;

		params.add(vo.get("party_role_id")) ;

		params.add(vo.get("party_id")) ;

		params.add(vo.get("oper_region_id")) ;

		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;


		params.add(keyCondMap.get("relation_id")) ;

		params.add(keyCondMap.get("seq")) ;

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

		params.add(keyCondMap.get("relation_id")) ;

		//params.add(keyCondMap.get("seq")) ;

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
