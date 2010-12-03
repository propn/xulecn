package com.ztesoft.oaas.dao.contactmedium;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.ContactMediumVO;

public interface ContactMediumDAO extends DAO {

	ContactMediumVO findByPrimaryKey(String pcontact_medium_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pcontact_medium_id,ContactMediumVO vo) throws DAOSystemException;

	long delete( String pcontact_medium_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
    
    long deleteByParty(String pparty_id) throws DAOSystemException;
    
    ArrayList getContactMediumsByParty(String pparty_id) throws DAOSystemException;

    long deleteByAddr(String paddr_id) throws DAOSystemException;
    
    ArrayList getContactMediumsByAddr(String paddr_id) throws DAOSystemException;

}
