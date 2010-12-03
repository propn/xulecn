package com.ztesoft.oaas.dao.cpromotion;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.CPromotionVO;

public interface CPromotionDAO extends DAO {

	CPromotionVO findByPrimaryKey(String ppromotion_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String ppromotion_id,CPromotionVO vo) throws DAOSystemException;

	long delete( String ppromotion_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
	
	PageModel queryCPromotionList( CPromotionVO vo, int pageIndex, int pageSize ) throws DAOSystemException;
	
	int countBySQL( String countSQL );
}
