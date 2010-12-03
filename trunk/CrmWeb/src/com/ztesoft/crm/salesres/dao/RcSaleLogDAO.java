package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcSaleLogVO;

public interface RcSaleLogDAO extends DAO {

	RcSaleLogVO findByPrimaryKey(String plog_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String plog_id,RcSaleLogVO vo) throws DAOSystemException;

	long delete( String plog_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
	
	public void setSQL_SELECT(String sql_select);

	public void setSQL_SELECT_COUNT(String sql_select_count);
	
	public void setFlag(int flag);

    /**
     * 根据条件查询出记录总数和其中一个字段的统计总数
     * sql中必须有COL_COUNTS 和 totalAmount两个字段
     * @param whereCond
     * @return
     * @throws DAOSystemException
     */
	public long[] countSumByCond(String whereCond) throws DAOSystemException ;
	
}
