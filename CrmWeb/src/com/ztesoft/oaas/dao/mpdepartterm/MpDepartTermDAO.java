package com.ztesoft.oaas.dao.mpdepartterm;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.MpDepartTermVO;

public interface MpDepartTermDAO extends DAO {

	MpDepartTermVO findByPrimaryKey(String pdepart_id,String pterm_code) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pdepart_id, String pterm_code,MpDepartTermVO vo) throws DAOSystemException;

	long delete( String pdepart_id, String pterm_code) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * 根据部门ID查询所有部门班次
     * @param party_id
     * @return 指定部门的所有班次列表（MpDepartTermVO构成的ArrayList）
     * @throws DAOSystemException
     */
    ArrayList getMpDepartTermsByParty(String party_id) throws DAOSystemException;
    
    long deleteByParty(String pparty_id) throws DAOSystemException;
}
