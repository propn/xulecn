package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcEntityOutLogVO;

public interface RcEntityOutLogDAO extends DAO {

	RcEntityOutLogVO findByPrimaryKey(String pRESOURCE_INSTANCE_ID) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pRESOURCE_INSTANCE_ID,RcEntityOutLogVO vo) throws DAOSystemException;

	long delete( String pRESOURCE_INSTANCE_ID) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

        /**
         * �ѵ���ʵ����Ϣ���뵽����������
         * @param resBCode String
         * @param resECode String
         * @throws DAOSystemException
         * @return int
         */
        public int insertFromSingleEntity(String rescInstanceCode) throws DAOSystemException ;

       /**
        * ������ʵ��������ʼʵ���������ֹ����֮���ʵ�����뵽����������
        * @param resBCode String
        * @param resECode String
        * @throws DAOSystemException
        * @return int
        */
       public int insertFromEntity(String cond) throws DAOSystemException;

}
