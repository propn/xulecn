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
     * ������֯��ʶ��ѯ���в����ն˷�Χʵ��
     * @param pparty_id
     * @return ָ����֯��ʶ��Ӧ�����в����ն˷�Χʵ���б�RrDepartTermVO���ɵ�ArrayList��
     * @throws DAOSystemException
     */
    ArrayList getRrDepartTermsByParty(String pparty_id) throws DAOSystemException;
    
    long deleteByParty(String pparty_id) throws DAOSystemException;
    
    /**
     * ���ݵ�¼IP��ַ��ȡ��Ӧ�Ĳ����ն˷�Χʵ��
     * @param ip
     * @return ��¼IP��ַ��Ӧ�Ĳ����ն˷�Χʵ���б�RrDepartTermVO���ɵ�ArrayList��
     * @throws DAOSystemException
     */
    ArrayList getRrDepartTermsByIP(String ip) throws DAOSystemException;
    
}
