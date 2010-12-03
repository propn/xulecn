package com.ztesoft.oaas.dao.region;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RegionVO;

public interface RegionDAO extends DAO
{
   
    RegionVO findByPrimaryKey(String pregion_id) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
            throws DAOSystemException;

    boolean update(String pregion_id, RegionVO vo) throws DAOSystemException;
    
    public boolean update( VO vo ) throws DAOSystemException;

    long delete(String pregion_id) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    ArrayList getAllRegions() throws DAOSystemException;

    ArrayList getAllBillRegions(String parentRegionId) throws DAOSystemException;

    ArrayList getAllSaleRegions(String parentRegionId) throws DAOSystemException;

    ArrayList getAllRescRegions(String parentRegionId) throws DAOSystemException;
    
    ArrayList getRegionsByCond(String regionIds,String regionLevel,String regionType) throws DAOSystemException;
    
    boolean checkSubRegionNGNFlag(String regionId ) throws DAOSystemException;
    
    List getResourceRegionListByFilter(String parentRegionId ,String regionLevel, ArrayList regionIds ) throws DAOSystemException;
    
    List getResourceRegionListWithPrivFlag(String parentRegionId) throws DAOSystemException;

    List getOrganizationRegionListWithPrivFlag( String parentRegionID ) throws DAOSystemException;
    
    List getOrganizationRegionListByFilter(String parentRegionId,ArrayList regionIds,String orgTypeId) throws DAOSystemException;
    
    List getBillRegionByRegionIds(Set regionIdSet) throws DAOSystemException ;
    
    void insertChnBranch(String regionId) throws DAOSystemException ;
}
