package com.ztesoft.oaas.dao.prodFamily;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.ProdFamilyVO;

public interface ProdFamilyDAO extends DAO {

	ProdFamilyVO findByPrimaryKey(String pproduct_family_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pproduct_family_id,ProdFamilyVO vo) throws DAOSystemException;

	long delete( String pproduct_family_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    ArrayList getAllProdFamily() throws DAOSystemException;
}