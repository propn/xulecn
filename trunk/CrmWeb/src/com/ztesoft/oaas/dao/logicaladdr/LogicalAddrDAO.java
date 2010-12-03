package com.ztesoft.oaas.dao.logicaladdr;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.LogicalAddrVO;

public interface LogicalAddrDAO extends DAO {

	LogicalAddrVO findByPrimaryKey(String plogical_address_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String plogical_address_id,LogicalAddrVO vo) throws DAOSystemException;

	long delete( String plogical_address_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
    
    /**
     * 获取指定地址标识对应的所有逻辑地址
     * @param paddr_id
     * @return 指定地址标识对应的所有逻辑地址（LogicalAddrVO组成的ArrayList）
     * @throws DAOSystemException
     */
    ArrayList getLogicalAddrsByAddr(String paddr_id) throws DAOSystemException;
    
    long deleteByAddr(String paddr_id) throws DAOSystemException;

}
