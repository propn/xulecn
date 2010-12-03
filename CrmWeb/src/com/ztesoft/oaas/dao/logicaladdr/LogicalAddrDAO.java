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
     * ��ȡָ����ַ��ʶ��Ӧ�������߼���ַ
     * @param paddr_id
     * @return ָ����ַ��ʶ��Ӧ�������߼���ַ��LogicalAddrVO��ɵ�ArrayList��
     * @throws DAOSystemException
     */
    ArrayList getLogicalAddrsByAddr(String paddr_id) throws DAOSystemException;
    
    long deleteByAddr(String paddr_id) throws DAOSystemException;

}
