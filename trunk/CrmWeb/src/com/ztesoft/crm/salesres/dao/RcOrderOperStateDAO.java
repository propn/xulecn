package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcOrderOperStateVO;

public interface RcOrderOperStateDAO extends DAO {

	RcOrderOperStateVO findByPrimaryKey(String papp_type,String pf_tache_id,String pstate_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String papp_type, String pf_tache_id, String pstate_id,RcOrderOperStateVO vo) throws DAOSystemException;

	long delete( String papp_type, String pf_tache_id, String pstate_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
