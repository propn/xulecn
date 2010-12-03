package com.ztesoft.oaas.dao.rrdepartterm;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RrDepartTermVO;

public interface RrDepartTermDAO extends DAO {

	RrDepartTermVO findByPrimaryKey(String pend_ipaddr,String pipaddr) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pend_ipaddr, String pipaddr,RrDepartTermVO vo) throws DAOSystemException;

	long delete( String pend_ipaddr, String pipaddr) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * 根据组织标识查询所有部门终端范围实体
     * @param pparty_id
     * @return 指定组织标识对应的所有部门终端范围实体列表（RrDepartTermVO构成的ArrayList）
     * @throws DAOSystemException
     */
    ArrayList getRrDepartTermsByParty(String pparty_id) throws DAOSystemException;
    
    long deleteByParty(String pparty_id) throws DAOSystemException;
    
    /**
     * 根据登录IP地址获取对应的部门终端范围实体
     * @param ip
     * @return 登录IP地址对应的部门终端范围实体列表（RrDepartTermVO构成的ArrayList）
     * @throws DAOSystemException
     */
    ArrayList getRrDepartTermsByIP(String ip) throws DAOSystemException;
    
}
