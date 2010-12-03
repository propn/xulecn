/**
 * 
 */
package com.ztesoft.oaas.dao.agentprop;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.AgentPropertyVO;
/**
 * @author –Ì»Ò∫¿
 *
 */
public interface AgentPropertyDAO extends DAO {
	AgentPropertyVO findByPrimaryKey(String partyId) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
            throws DAOSystemException;

    boolean update(String partyId, AgentPropertyVO vo) throws DAOSystemException;

    long delete(String partyId) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
}
