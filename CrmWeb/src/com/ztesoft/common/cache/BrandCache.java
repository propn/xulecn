package com.ztesoft.common.cache;

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
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAO;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAOImpl;
import com.ztesoft.oaas.dao.staff.StaffDAO;
import com.ztesoft.oaas.dao.staff.StaffDAOFactory;
import com.ztesoft.oaas.vo.MmMenuVO;

/*import com.ztesoft.crm.uas.core.data.dao.DcProdBrandDAO;
import com.ztesoft.crm.uas.core.data.dao.DcProdBrandDAOImpl;
*/
public final class BrandCache {
	
	public BrandCache(){}

	/*static DcProdBrandDAO dao=new DcProdBrandDAOImpl();*/
	static MmMenuDAO dao = new MmMenuDAOImpl();
	public static List list=null;
	private int maxRows;
	public static List allList=null;
	
	public static List notMainList=null;
	
	private static final String MAIN_BRAND = "0";
	private static final String NOT_MAIN_BRAND = "1";
	
	static{
		loadData();
	}
	//加载所有档次数据到
	public static void  loadData(){
		//
		/*String SQL_EFF_BRAND_SELECT =
			" select distinct a.brand_id, a.brand_name, a.oper_id, a.mtime, a.note, a.catalog_id, a.brand_wid" +
			" from dc_prod_brand a, dc_prod_productbag b where a.brand_id=b.brand_id";

		//所有的品牌
		allList = dao.findBySql(DcProdBrandDAOImpl.SQL_EFF_BRAND_SELECT_ALL, null);
		
		String mainBrandIdTmp = dao.findBySql2(DcProdBrandDAOImpl.SQL_EFF_BRAND_ID, new String[]{MAIN_BRAND});
		String mainBrandId = "";
		String notMainBrandIdmp = dao.findBySql2(DcProdBrandDAOImpl.SQL_EFF_BRAND_ID, new String[]{NOT_MAIN_BRAND});
		String notMainBrandId = "";
		
		while(mainBrandIdTmp.startsWith("/")){
			mainBrandIdTmp = mainBrandIdTmp.substring(1);
		}
		while(mainBrandIdTmp.endsWith("/")){
			mainBrandIdTmp = mainBrandIdTmp.substring(0, mainBrandIdTmp.length()-1);
		}
		mainBrandId = "'" + mainBrandIdTmp.replaceAll("/", "','") + "'";
		
		while(notMainBrandIdmp.startsWith("/")){
			notMainBrandIdmp = notMainBrandIdmp.substring(1);
		}
		while(notMainBrandIdmp.endsWith("/")){
			notMainBrandIdmp = notMainBrandIdmp.substring(0, notMainBrandIdmp.length()-1);
		}
		notMainBrandId = "'" + notMainBrandIdmp.replaceAll("/", "','") + "'";
		
		//主品牌
		list = dao.findBySql(SQL_EFF_BRAND_SELECT + " AND a.brand_id IN(" + mainBrandId + ")", null);	
		Strin
		//非主品牌
		notMainList = dao.findBySql(SQL_EFF_BRAND_SELECT + " AND a.brand_id IN(" + notMainBrandId + ")", null);*/
		String parentMenuId = "-1";
		String sql = "SELECT distinct menu.menu_id, menu.system_id, menu.menu_code, menu.menu_name, "
				+ " menu.order_id, menu.target_name, menu.parameter, menu.open_flag, menu.privilege_flag, "
				+ " menu.valid_flag, menu.menu_type, menu.menu_grade, menu.super_id, menu.image_path, "
				+ " menu.comments, menu.path_code "
				+ " FROM MM_MENU menu, MM_DATA_PRIVILEGE mr , PRIVILEGE PRIV"
				+ " WHERE cast( menu.menu_id as varchar(20) ) =mr.DATA_PKEY_1  AND mr.PRIVILEGE_ID = PRIV.PRIVILEGE_ID"
				+ " AND PRIV.PRIVILEGE_TYPE = '99B' AND valid_flag = '0' and menu.menu_id not in(99999,31700,94000) and super_id = "
				+ parentMenuId;

		StaffDAO staffDAO = StaffDAOFactory.getStaffDAO();
		boolean isSuperStaff = false;
		sql = sql + " ORDER BY menu.path_code";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			//stmt.setMaxRows(maxRows);
			rs = stmt.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				MmMenuVO vo = new MmMenuVO();
				vo.setSuperId(rs.getString("super_id"));
				vo.setMenuId(rs.getString("menu_id"));
				vo.setSystemId(rs.getString("system_id"));
				vo.setMenuCode(rs.getString("menu_code"));
				vo.setMenuName(rs.getString("menu_name"));
				vo.setOrderId(rs.getString("order_id"));
				vo.setTargetName(rs.getString("target_name"));
				vo.setPara(rs.getString("parameter"));
				vo.setPrivFlag(rs.getString("privilege_flag"));
				vo.setValidFlag(rs.getString("valid_flag"));
				vo.setMenuType(rs.getString("menu_type"));
				vo.setMenuGrade(rs.getString("menu_grade"));
				vo.setImagePath(rs.getString("image_path"));
				vo.setComments(rs.getString("comments"));
				vo.setPathCode(rs.getString("path_code"));
				vo.setOpenFlag( rs.getString("open_flag"));
				if (vo.getTargetName() != null) {
					vo.setTargetName(vo.getTargetName().trim());
				} else {
					vo.setTargetName("");
				}
				resultList.add(vo);
			}
			 list=resultList;
		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(stmt);
			//DAOUtils.closeConnection(conn, this);
		}
	}

}
