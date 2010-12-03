package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcStockVO;

public interface RcStockDAO extends DAO {

	RcStockVO findByPrimaryKey(String psales_resource_id,String pstorage_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String psales_resource_id, String pstorage_id,RcStockVO vo) throws DAOSystemException;

	long delete( String psales_resource_id, String pstorage_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

	/**
	 * ���ݲֿ�id�޸ĸòֿ�Ŀ������
	 * @param storageId
	 * @param stockAmount
	 * @return
	 * @throws DAOSystemException
	 */
	public boolean updateAmount(String storageId,String salesRescId,String stockAmount) throws DAOSystemException;

        public void setSQL_SELECT(String SQL_SELECT);

        public void setSQL_SELECT_COUNT(String SQL_SELECT_COUNT);

        public void setFlag(int flag);
        public void setFlag2(int flag);
        public void setFiltered(boolean filtered);

		void setUpDown(String string);

		boolean getRealCfg(String lanId, String tableName);
		
	    /**
	     * ����������ѯ����¼����������һ���ֶε�ͳ������
	     * sql�б�����COL_COUNTS �� totalAmount�����ֶ�
	     * @param whereCond
	     * @return
	     * @throws DAOSystemException
	     */
		public long[] countSumByCond(String whereCond) throws DAOSystemException ;
		
}
