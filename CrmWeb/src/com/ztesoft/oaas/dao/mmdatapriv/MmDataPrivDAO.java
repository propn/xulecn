package com.ztesoft.oaas.dao.mmdatapriv;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.MmDataPrivVO;
import com.ztesoft.oaas.vo.OffWarrRequementVO;

public interface MmDataPrivDAO extends DAO {

	MmDataPrivVO findByPrimaryKey(String pdata_pkey_1,String pdata_pkey_2,String pdata_pkey_3,String pprivilege_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pdata_pkey_1, String pdata_pkey_2, String pdata_pkey_3, String pprivilege_id,MmDataPrivVO vo) throws DAOSystemException;

	long delete( MmDataPrivVO[] voLs ) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

	ArrayList findPrivilegeData( String sql, String privId ) throws DAOSystemException;
	
	ArrayList findDataByPrivilegeId( String sql, String privilegeId ) throws DAOSystemException;
	
	ArrayList getCustDataPrivilege(String privilegeId, String custTypeId, String datasetId ) throws DAOSystemException;
	
	ArrayList getCustDataPrivilegeByPrivilegeId( String privilegeId ) throws DAOSystemException;
	
	PageModel getProdOfferDataPrivilegeByPrivilegeId(String privilegeId, int pageIndex, int pageSize ) throws DAOSystemException;
	
	PageModel getPriceCataContentByPrivilegeId(String privilegeId, int pageIndex, int pageSize) throws DAOSystemException;

	PageModel getOfferWarrByPrivilegeId( String privilegeId , int pageIndex, int pageSize ) throws DAOSystemException ;
		
	PageModel queryOfferWarr(OffWarrRequementVO vo, int pageIndex,
			int pageSize) throws DAOSystemException;
			
	ArrayList getOfferServ(String isAllOffer, String privilegeId, String offerId ) throws DAOSystemException;
	
	PageModel queryServiceOffer(String serviceOfferName, int pageIndex , int pageSize ) throws DAOSystemException;
	
	PageModel getProductOfferList(String serviceOfferId, String privilegeId, boolean selected, int pageIndex, int pageSize ) throws DAOSystemException;
	
	ArrayList getOfferServiceByPrivilege( String offerId, ArrayList privilegeIds ) throws DAOSystemException;
	
	ArrayList getRnLevelByPrivilege( ArrayList privilegeIds ) throws DAOSystemException;
	
	ArrayList getPricingParamCatalogByPrivilege( ArrayList privilegeIds ) throws DAOSystemException;

	ArrayList getCustCtrlDataInfoFromDatabase(String pageCode, String datasetCode, String custTypeId, ArrayList privilegeIds ) throws DAOSystemException;
	
	ArrayList getMenuDataPrivilege(String privilegeId) throws DAOSystemException ;
	
	ArrayList getMenuIdByPrivilege( String privId ) throws  DAOSystemException ;
	
	PageModel queryAttachProductList( String privilegeId , String productCode, String productName, int pageIndex, int pageSize ) throws DAOSystemException;
	
	PageModel getAttachProductByPrivilegeId( String privId , int pageIndex , int pageSize ) throws DAOSystemException;
	
	ArrayList getOrganizationByPrivilege(ArrayList privilegeIds,String cProductId) throws DAOSystemException;
	
	ArrayList getOrgForNoHeadByPrivilege(ArrayList privilegeIds,String noFamilyId) throws DAOSystemException;
	
	ArrayList getOrgForDeliverNoByPrivilege(ArrayList privilegeIds,String noFamilyId) throws DAOSystemException;
}
