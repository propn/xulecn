package com.ztesoft.oaas.dao.chsalearea;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.ChSaleAreaVO;

public interface ChSaleAreaDAO extends DAO {

	ChSaleAreaVO findByPrimaryKey(String parea_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String parea_id,ChSaleAreaVO vo) throws DAOSystemException;

	long delete( String parea_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
