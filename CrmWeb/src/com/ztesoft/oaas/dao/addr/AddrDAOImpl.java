package com.ztesoft.oaas.dao.addr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.AddrVO;

public class AddrDAOImpl   implements AddrDAO
{

    private String SQL_SELECT = "SELECT address_id,province_name,city_name,county_name,street_name,street_nbr,detail,postcode,alias,BANK_BRANCH_ID,BANK_ACCT_NO,INCR_TAX_NO FROM ADDRESS";

    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM ADDRESS";

    private int maxRows;

    private String SQL_INSERT = "INSERT INTO ADDRESS ( address_id,province_name,city_name,county_name,street_name,street_nbr,detail,postcode,alias,BANK_BRANCH_ID,BANK_ACCT_NO,INCR_TAX_NO  ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,? )";

    private String SQL_UPDATE = "UPDATE ADDRESS SET  province_name = ?, city_name = ?, county_name = ?, street_name = ?, street_nbr = ?, detail = ?, postcode = ?, alias = ?,BANK_BRANCH_ID = ?,BANK_ACCT_NO = ? ,INCR_TAX_NO = ? WHERE address_id = ? ";

    private String SQL_DELETE = "DELETE FROM ADDRESS WHERE address_id = ? ";

    private String SQL_DELETE_BY_WHERE = "DELETE FROM ADDRESS ";

    public AddrDAOImpl()
    {

    }

    public AddrVO findByPrimaryKey(String paddress_id)
            throws DAOSystemException
    {
        ArrayList arrayList = findBySql(
                SQL_SELECT + " WHERE address_id = ? ",
                new String[]{
                    paddress_id
                });
        if(arrayList.size() > 0) return (AddrVO)arrayList.get(0);
        else return (AddrVO)getEmptyVO();
    }

    public ArrayList findBySql(String sql, String[] sqlParams)
            throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setMaxRows(maxRows);
            for(int i = 0; sqlParams != null && i < sqlParams.length; i++)
            {
                stmt.setString(i + 1, sqlParams[i]);
            }

            rs = stmt.executeQuery();

            return fetchMultiResults(rs);
        }
        catch (SQLException se)
        {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while getting sql:\n"
                    + sql, se);
        }
        finally
        {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
    }

    protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException
    {
        ArrayList resultList = new ArrayList();
        while (rs.next())
        {
            AddrVO vo = new AddrVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }
        return resultList;
    }

    protected void populateDto(AddrVO vo, ResultSet rs) throws SQLException
    {
        vo.setAddrId(rs.getString("address_id"));
        vo.setProvinceName(rs.getString("province_name"));
        vo.setCityName(rs.getString("city_name"));
        vo.setCountyName(rs.getString("county_name"));
        vo.setStreetName(rs.getString("street_name"));
        vo.setStreetNbr(rs.getString("street_nbr"));
        vo.setDeta(rs.getString("detail"));
        vo.setPostcode(rs.getString("postcode"));
        vo.setAlias(rs.getString("alias"));
        vo.setBankBranchId(rs.getString("BANK_BRANCH_ID"));
        vo.setBankAccNo(rs.getString("BANK_ACCT_NO"));
        vo.setIncrTaxNo(rs.getString("INCR_TAX_NO"));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException
    {
        AddrVO vo = new AddrVO();
        try
        {
            populateDto(vo, rs);
        }
        catch (SQLException se)
        {
            Debug.print("populateCurrRecord³ö´í", this);
            throw new DAOSystemException(
                    "SQLException while populateDto:\n",
                    se);
        }
        return vo;
    }

    public List findByCond(String whereCond) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            SQL = SQL_SELECT + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setMaxRows(maxRows);
            rs = stmt.executeQuery();

            return fetchMultiResults(rs);
        }
        catch (SQLException se)
        {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n"
                    + SQL, se);
        }
        finally
        {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
    }

    public void insert(VO vo) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        try
        {
        	SequenceManageDAO smDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
        	conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
        	String addressId = smDAO.getNextSequence(vo.getTableName(), "ADDRESS_ID");
        	((AddrVO)vo).setAddrId(addressId);
            
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));
            
            int index = 1;
            if("".equals(((AddrVO)vo).getAddrId()))
            {
                ((AddrVO)vo).setAddrId(null);
            }
            if( "".equals(((AddrVO)vo).getAlias())){
            	((AddrVO)vo).setAlias(null);
            }
            stmt.setString(index++, ((AddrVO)vo).getAddrId());
            stmt.setString(index++, ((AddrVO)vo).getProvinceName());
            stmt.setString(index++, ((AddrVO)vo).getCityName());
            stmt.setString(index++, ((AddrVO)vo).getCountyName());
            stmt.setString(index++, ((AddrVO)vo).getStreetName());
            stmt.setString(index++, ((AddrVO)vo).getStreetNbr());
            stmt.setString(index++, ((AddrVO)vo).getDeta());
            stmt.setString(index++, ((AddrVO)vo).getPostcode());
            stmt.setString(index++, ((AddrVO)vo).getAlias());
            if( "".equals(((AddrVO)vo).getBankBranchId())){
            	((AddrVO)vo).setBankBranchId(null);
            }
            stmt.setString(index++,((AddrVO)vo).getBankBranchId());
            if( "".equals(((AddrVO)vo).getBankAccNo())){
            	((AddrVO)vo).setBankAccNo(null);
            }
            stmt.setString(index++,((AddrVO)vo).getBankAccNo());
            if( "".equals(((AddrVO)vo).getIncrTaxNo())){
            	((AddrVO)vo).setIncrTaxNo(null);
            }            
            stmt.setString(index++,((AddrVO)vo).getIncrTaxNo());
            int rows = stmt.executeUpdate();
        }
        catch (SQLException se)
        {
            Debug.print(SQL_INSERT, this);
            throw new DAOSystemException("SQLException while insert sql:\n"
                    + SQL_INSERT, se);
        }
        finally
        {
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
    }

    public boolean update(String paddress_id, AddrVO vo)
            throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            sql
                    .append("UPDATE ADDRESS SET province_name = ?,city_name = ?,county_name = ?,street_name = ?,street_nbr = ?,detail = ?,postcode = ?,alias = ?,BANK_BRANCH_ID=?,BANK_ACCT_NO=?,INCR_TAX_NO=?");
            sql.append(" WHERE  address_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
                    .toString()));
            int index = 1;
            if( "".equals(vo.getIncrTaxNo())){
            	vo.setIncrTaxNo(null);
            }
            if( "".equals(vo.getBankAccNo())){
            	vo.setBankAccNo(null);
            }
            if( "".equals(vo.getBankAccNo())){
            	vo.setBankAccNo(null);
            }     
            if( "".equals(vo.getAlias())){
            	vo.setAlias(null);
            }
            if( "".equals(vo.getBankBranchId())){
            	vo.setBankBranchId(null);
            }

            stmt.setString(index++, vo.getProvinceName());
            stmt.setString(index++, vo.getCityName());
            stmt.setString(index++, vo.getCountyName());
            stmt.setString(index++, vo.getStreetName());
            stmt.setString(index++, vo.getStreetNbr());
            stmt.setString(index++, vo.getDeta());
            stmt.setString(index++, vo.getPostcode());
            stmt.setString(index++, vo.getAlias());
            stmt.setString(index++,vo.getBankBranchId());
            stmt.setString(index++,vo.getBankAccNo());
            stmt.setString(index++,vo.getIncrTaxNo());
            stmt.setString(index++, paddress_id);            
            int rows = stmt.executeUpdate();
            if(rows > 0)
            {
                bResult = true;
            }
        }
        catch (SQLException se)
        {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n"
                    + sql.toString(), se);
        }
        finally
        {
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        return bResult;
    }

    public boolean update(String whereCond, VO vo) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            sql
                    .append("UPDATE ADDRESS SET address_id = ?,province_name = ?,city_name = ?,county_name = ?,street_name = ?,street_nbr = ?,detail = ?,postcode = ?,alias = ?,BANK_BRANCH_ID=?,BANK_ACCT_NO=?,INCR_TAX_NO=?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
                    .toString()));
            int index = 1;
            if("".equals(((AddrVO)vo).getAddrId()))
            {
                ((AddrVO)vo).setAddrId(null);
            }
            if( "".equals(((AddrVO)vo).getIncrTaxNo())){
            	((AddrVO)vo).setIncrTaxNo(null);
            }
            if( "".equals(((AddrVO)vo).getBankAccNo())){
            	((AddrVO)vo).setBankAccNo(null);
            }
            if( "".equals(((AddrVO)vo).getBankAccNo())){
            	((AddrVO)vo).setBankAccNo(null);
            }     
            if( "".equals(((AddrVO)vo).getAlias())){
            	((AddrVO)vo).setAlias(null);
            }
            if( "".equals(((AddrVO)vo).getBankBranchId())){
            	((AddrVO)vo).setBankBranchId(null);
            }
            stmt.setString(index++, ((AddrVO)vo).getAddrId());
            stmt.setString(index++, ((AddrVO)vo).getProvinceName());
            stmt.setString(index++, ((AddrVO)vo).getCityName());
            stmt.setString(index++, ((AddrVO)vo).getCountyName());
            stmt.setString(index++, ((AddrVO)vo).getStreetName());
            stmt.setString(index++, ((AddrVO)vo).getStreetNbr());
            stmt.setString(index++, ((AddrVO)vo).getDeta());
            stmt.setString(index++, ((AddrVO)vo).getPostcode());
            stmt.setString(index++, ((AddrVO)vo).getAlias());
            stmt.setString(index++,((AddrVO)vo).getBankBranchId());
            stmt.setString(index++,((AddrVO)vo).getBankAccNo());
            stmt.setString(index++,((AddrVO)vo).getIncrTaxNo());
            int rows = stmt.executeUpdate();
            if(rows > 0)
            {
                bResult = true;
            }
        }
        catch (SQLException se)
        {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n"
                    + sql.toString(), se);
        }
        finally
        {
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        return bResult;
    }

    public long countByCond(String whereCond) throws DAOSystemException
    {
        Connection conn = null;
        long lCount = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        try
        {

            int orderbyIndex = whereCond.toUpperCase().lastIndexOf("ORDER BY");
            if(orderbyIndex > 0)
            {
                whereCond = whereCond.substring(0, orderbyIndex);
            }
            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            rs = stmt.executeQuery();

            while (rs.next())
            {
                lCount = rs.getLong("COL_COUNTS");
            }
        }
        catch (SQLException se)
        {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n"
                    + SQL, se);
        }
        finally
        {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        return lCount;
    }

    public long deleteByCond(String whereCond) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "";
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            rows = stmt.executeUpdate();

        }
        catch (SQLException se)
        {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while deleting sql:\n"
                    + SQL, se);
        }
        finally
        {
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        return rows;
    }

    public long delete(String paddress_id) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));
            int index = 1;
            stmt.setString(index++, paddress_id);
            rows = stmt.executeUpdate();

        }
        catch (SQLException se)
        {
            Debug.print(SQL_DELETE, this);
            throw new DAOSystemException("SQLException while deleting sql:\n"
                    + SQL_DELETE, se);
        }
        finally
        {
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        return rows;
    }

    public int getMaxRows()
    {
        return maxRows;
    }

    public void setMaxRows(int maxRows)
    {
        this.maxRows = maxRows;
    }

    public VO getEmptyVO()
    {
        return new AddrVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
            throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + " WHERE " + whereCond;
        String filterSQL = SQL;
        if(queryFilter != null)
        {
            filterSQL = queryFilter.doPreFilter(SQL);
        }
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            SQL = filterSQL;
            stmt = conn.prepareStatement(
                    DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();

            List retList = null;
            if(queryFilter != null)
            {
                retList = queryFilter.doPostFilter(rs, this);
            }
            else
            {
                retList = fetchMultiResults(rs);
            }
            return retList;
        }
        catch (SQLException se)
        {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n"
                    + SQL, se);
        }
        finally
        {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
    }

}
