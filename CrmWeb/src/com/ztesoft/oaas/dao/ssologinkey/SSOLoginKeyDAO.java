/**
 * 
 */
package com.ztesoft.oaas.dao.ssologinkey;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.oaas.vo.SSOLoginKeyVO;
/**
 * @author Administrator
 *
 */
public interface SSOLoginKeyDAO {

	public void insert( SSOLoginKeyVO vo ) throws DAOSystemException ;
	public int delete( String loginKey ) throws DAOSystemException ;
	public boolean update( String loginKey , SSOLoginKeyVO vo ) throws DAOSystemException;
	public SSOLoginKeyVO getSSOLoginKey( String loginKey ) throws DAOSystemException ;
	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;
	public long deleteByCond(String whereCond) throws DAOSystemException;
	public List findByCond(String whereCond) throws DAOSystemException;
}
