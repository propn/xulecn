package com.ztesoft.crm.salesres.dao;
import com.ztesoft.crm.salesres.dao.impl.MpDepartDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcEntityDAOImpl2;
import com.ztesoft.crm.salesres.dao.impl.StaffDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.AgDepartRelaDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.AgSrStatisticsDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.AgStorageStatisticsDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.AttrDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.AttrValueDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.DcDataInfoDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.DcDeviceDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.DcDeviceGrpDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.DfFeeItemDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.InfRcToCardDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.InflRcToCardDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.MpOperDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.MpOperDepartDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.MpStorageDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcAppTypeDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcApplicationDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcEntityDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcEntityOutLogDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcEntityReportDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcFamilyDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcFamilyEntityRelaDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcLevelDefDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcOrderAgentDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcOrderDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcOrderExcDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcOrderListDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcOrderOperStateDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcPublicLogDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcSaleLogDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcServAcceptDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcServReturnDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcStateDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcStateDefDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcStockDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcStockLimitDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcStorageDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RescNumDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RrRegionDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.SaleRescPricDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.SalesRescDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.SalesRescIdRelaDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.SqlComDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.SrDepositoryDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.StAreaDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.StDepartAreaDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.StorageDepartRelaDAOImpl;

import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.organization.OrganizationDAOImpl;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAO;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAOImpl;
import com.ztesoft.oaas.dao.rrlan.RrLanDAO;
import com.ztesoft.oaas.dao.rrlan.RrLanDAOImpl;


public class SrDAOFactory {

	private static SrDAOFactory instance = new SrDAOFactory();

	public SrDAOFactory() {
	}

	public static SrDAOFactory getInstance() {
		return instance;
	}

	public RcStockDAO getRcStockDAO() {
		return new RcStockDAOImpl();
	}

	public RcSaleLogDAO getRcSaleLogDAO() {
		return new RcSaleLogDAOImpl();
	}

	public SalesRescDAO getSalesRescDAO() {
		return new SalesRescDAOImpl();
	}

	public RcFamilyDAO getRcFamilyDAO() {
		return new RcFamilyDAOImpl();
	}

	public RcStorageDAO getRcStorageDAO() {
		return new RcStorageDAOImpl();
	}

	public StorageDepartRelaDAO getStorageDepartRelaDAO() {
		return new StorageDepartRelaDAOImpl();
	}

	public MpStorageDAO getMpStorageDAO() {
		return new MpStorageDAOImpl();
	}

	public OrganizationDAO getOrganizationDAO() {
		return new OrganizationDAOImpl();
	}

	public StaffDAO getStaffDAO() {
		return new StaffDAOImpl();
	}

	public RcEntityDAO getRcEntityDAO() {
		return new RcEntityDAOImpl();
	}
	public RcEntityDAO2 getRcEntityDAO2() {
		return new RcEntityDAOImpl2();
	}

	public RcApplicationDAO getRcApplicationDAO() {
		return new RcApplicationDAOImpl();
	}

	public RcOrderExcDAO getRcOrderExcDAO() {
		return new RcOrderExcDAOImpl();
	}

	public RcPublicLogDAO getRcPublicLogDAO() {
		return new RcPublicLogDAOImpl();
	}

	public RcOrderOperStateDAO getRcOrderOperStateDAO() {
		return new RcOrderOperStateDAOImpl();
	}

	public RcOrderDAO getRcOrderDAO() {
		return new RcOrderDAOImpl();
	}

	public RrLanDAO getRrLanDAO() {
		return new RrLanDAOImpl();
	}

	public DcDataInfoDAO getDcDataInfoDAO() {
		return new DcDataInfoDAOImpl();
	}

	public RrRegionDAO getRrRegionDAO() {
		return new RrRegionDAOImpl();
	}

	public MpDepartDAO getMpDepartDAO() {
		return new MpDepartDAOImpl();
	}

	public RcStateDAO getRcStateDAO() {
		return new RcStateDAOImpl();
	}

	public RcStateDefDAO getRcStateDefDAO() {
		return new RcStateDefDAOImpl();
	}

	public RescNumDAO getRescNumDAO() {
		return new RescNumDAOImpl();
	}

	public AttrDAO getAttrDAO() {
		return new AttrDAOImpl();
	}

	public AttrValueDAO getAttrValueDAO() {
		return new AttrValueDAOImpl();
	}

	public DcDeviceDAO getDeviceDAO() {
		return new DcDeviceDAOImpl();
	}

	public SalesRescIdRelaDAO getSalesRescIdRelaDAO() {
		return new SalesRescIdRelaDAOImpl();
	}

	public RrBusinessDAO getRrBusinessDAO() {
		return new RrBusinessDAOImpl();
	}

	public SaleRescPricDAO getSaleRescPricDAO() {
		return new SaleRescPricDAOImpl();
	}

	public RcAppTypeDAO getRcAppTypeDAO() {
		return new RcAppTypeDAOImpl();
	}

	public RcEntityOutLogDAO getRcEntityOutLogDAO() {
		return new RcEntityOutLogDAOImpl();
	}

	public RcOrderAgentDAO getRcOrderAgentDAO() {
		return new RcOrderAgentDAOImpl();
	}

	public RcLevelDefDAO getRcLevelDefDAO() {
		return new RcLevelDefDAOImpl();
	}

	public RcOrderListDAO getRcOrderListDAO() {
		return new RcOrderListDAOImpl();
	}

	public SqlComDAO getSqlComDAO() {
		return new SqlComDAOImpl();
	}

	public RcFamilyEntityRelaDAO getRcFamilyEntityRelaDAO() {
		return new RcFamilyEntityRelaDAOImpl();
	}

	public RcStockLimitDAO getRcStockLimitDAO() {
		return new RcStockLimitDAOImpl();
	}

	public DfFeeItemDAO getDfFeeItemDAO() {
		return new DfFeeItemDAOImpl();
	}

	public SrDepositoryDAO getSrDepositoryDAO() {
		return new SrDepositoryDAOImpl();
	}

	public StAreaDAO getStAreaDAO() {
		return new StAreaDAOImpl();
	}

	public StDepartAreaDAO getStDepartAreaDAO() {
		return new StDepartAreaDAOImpl();
	}

	public AgDepartRelaDAO getAgDepartRelaDAO() {
		return new AgDepartRelaDAOImpl();
	}

	public AgSrStatisticsDAO getAgSrStatisticsDAO() {
		return new AgSrStatisticsDAOImpl();
	}

	public MpOperDAO getMpOperDAO() {
		return new MpOperDAOImpl();
	}

	public AgStorageStatisticsDAO getAgStorageStatisticsDAO() {
		return new AgStorageStatisticsDAOImpl();
	}

	public RcServAcceptDAO getRcServAcceptDAO() {
		return new RcServAcceptDAOImpl();
	}

	public RcServReturnDAO getRcServReturnDAO() {
		return new RcServReturnDAOImpl();
	}

	public InfRcToCardDAO getInfRcToCardDAO() {
		return new InfRcToCardDAOImpl();
	}

	public InflRcToCardDAO getInflRcToCardDAO() {
		return new InflRcToCardDAOImpl();
	}

	public MpOperDepartDAO getMpOperDepartDAO() {
		return new MpOperDepartDAOImpl();
	}

	public RcStorageDAO getRcStorageDAOExt() {
		return new RcStorageDAOImpl();
	}

	public RcEntityReportDAO getRcEntityReportDAO() {

		return new RcEntityReportDAOImpl();

	}

	public DcDeviceGrpDAO getDcDeviceGrpDAO() {

		return new DcDeviceGrpDAOImpl();

	}

}
