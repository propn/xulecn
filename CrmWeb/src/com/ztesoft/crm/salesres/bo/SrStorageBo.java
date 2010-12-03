package com.ztesoft.crm.salesres.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.crm.salesres.common.CommonDAO;
import com.ztesoft.crm.salesres.dao.MpStorageDAO;
import com.ztesoft.crm.salesres.dao.RcPublicLogDAO;
import com.ztesoft.crm.salesres.dao.RcStockLimitDAO;
import com.ztesoft.crm.salesres.dao.RcStorageDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.StorageDepartRelaDAO;
import com.ztesoft.crm.salesres.vo.MpStorageVO;
import com.ztesoft.crm.salesres.vo.RcPublicLogVO;
import com.ztesoft.crm.salesres.vo.RcStockLimitVO;
import com.ztesoft.crm.salesres.vo.RcStorageVO;
import com.ztesoft.crm.salesres.vo.StorageDepartRelaVO;

public class SrStorageBo extends DictAction {
	public SrStorageBo() {
	}

	/**
	 * 查询仓库信息
	 * 
	 * @param map
	 *            HashMap
	 * @param pageIndex
	 *            int
	 * @param pageSize
	 *            int
	 * @throws DAOSystemException
	 * @return PageModel
	 */
	//public PageModel qryRcStorage(HashMap map, int pageIndex, int pageSize)throws DAOSystemException {
	public PageModel qryRcStorage(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        int pageIndex  = ((Integer)map.get("pageIndex")).intValue();
        int pageSize  = ((Integer)map.get("pageSize")).intValue();
        
		StringBuffer cond = new StringBuffer();
		String condSql = " a.RC_TYPE=-1 ";
		// if(map.get("provId")!=null &&
		// map.get("provId").equals(ParamsConsConfig.PROV_ID_GX)){
		// if(map.get("lanId")!=null && !"".equals(map.get("lanId"))){
		// if ( (map.get("townId") == null)||"".equals(map.get("townId"))) {
		// condSql +=" and exists (select 1 from Storage_Depart_Rela
		// b,mp_department c, rr_region d where"
		// +" a.storage_id = b.storage_id and b.depart_id=c.depart_id and
		// c.REGION_ID=d.REGION_ID "
		// +" and d.lan_id="+map.get("lanId")+ ")";
		// }else{
		// condSql +=" and exists (select 1 from Storage_Depart_Rela
		// b,mp_department c, rr_region d where"
		// +" a.storage_id = b.storage_id and b.depart_id=c.depart_id and
		// c.REGION_ID=d.REGION_ID "
		// +" and d.region_id="+map.get("townId")+ ")";
		// }
		// }
		// }else{
		// if (map.get("townId") == null||"".equals((String)map.get("townId")))
		// {
		// if ( ((map.get("lanId") !=
		// null)&&!"".equals((String)map.get("lanId")))) {
		// condSql +=
		// " and exists (select 1 from Storage_Depart_Rela b,mp_department c,
		// ra_town d"
		// + " where a.storage_id = b.storage_id and b.depart_id=c.depart_id and
		// c.town_id = d.town_id "
		// + " and d.lan_id= " + (String) map.get("lanId") + ")";
		// }
		// }
		// else {
		// condSql += " and exists (select 1 from Storage_Depart_Rela
		// b,mp_department c, ra_town d, rr_business e "
		// + " where a.storage_id = b.storage_id and b.depart_id=c.depart_id and
		// c.town_id = d.town_id"
		// + " and d.business_id=e.business_id and e.business_id= "
		// + (String) map.get("townId") + ")";
		// }
		// }
		if (map.get("lanId") != null && !"".equals(map.get("lanId"))) {
			condSql += " and a.lan_id =" + (String) map.get("lanId");
		}
		if (map.get("storageId") != null
				&& !"".equals((String) map.get("storageId"))) {
			condSql += " and a.storage_id =" + (String) map.get("storageId");
		}
		if (map.get("storageCode") != null
				&& !"".equals((String) map.get("storageCode"))) {
			condSql += " and a.storage_code ='"
					+ (String) map.get("storageCode") + "'";
		}
		if (map.get("storageName") != null
				&& !"".equals((String) map.get("storageName"))) {
			condSql += " AND a.storage_name like '%"
					+ (String) map.get("storageName") + "%'";
		}

		if (map.get("storageType") != null
				&& !"".equals((String) map.get("storageType"))) {
			condSql += " AND a.storage_type = '"
					+ (String) map.get("storageType") + "'";
		}
		// 河南的屏蔽回退
		// if
		// (map.get("provId")!=null&&((String)map.get("provId")).equals("11")) {
		// condSql += " and exists( select e.storage_id from STORAGE_DEPART_RELA
		// e "
		// + " where e.storage_id=a.storage_id and e.DEPART_ID in ("
		// + map.get("departId") + " ) union all "
		// + " select y.storage_id from mp_storage y where
		// a.storage_id=y.storage_id "
		// + " and y.oper_id="+map.get("operId")+" )";
		//		
		// }
		RcStorageDAO rcStorageDAO = SrDAOFactory.getInstance()
				.getRcStorageDAO();
		return PageHelper.popupPageModel(rcStorageDAO, condSql, pageIndex,
				pageSize);
	}

	/**
	 * 初始化仓库树信息
	 * 
	 * @param map
	 *            Map
	 * @return String
	 * @throws FrameException 
	 */
	public String qryStorageTree(DynamicDict dto ) throws FrameException {
		String upStorageId = RcStorageVO.TopStorageId;
		Map map = (Map)dto.getValueByName("parameter") ;
		if (map != null && map.get("upStorageId") != null
				&& ((String) map.get("upStorageId")).trim().length() > 0)
			upStorageId = (String) map.get("upStorageId");
		String cond = " a.RC_TYPE=-1 and a.up_storage_id=" + upStorageId + " ";
		String xml = "";
		if (map != null) {
			if (map.get("storageId") != null
					&& !"".equals(map.get("storageId"))) {
				cond += " and a.storage_id =" + (String) map.get("storageId");
			}
			if (map.get("storageName") != null
					&& !"".equals(map.get("storageName"))) {
				cond += " and a.storage_name like '%"
						+ (String) map.get("storageName") + "%'";
			}
		}
		if (map != null && map.get("lanId") != null
				&& ((String) map.get("lanId")).trim().length() > 0) {
			cond += " and ( a.lan_id is null or a.lan_id = "
					+ (String) map.get("lanId") + ") ";
		}
		// if(map!=null&&map.get("type")!=null&&((String)map.get("type")).trim().equals("1")){//仓库查询的仓库树，加上权限
		// String operId=(String)map.get("operId");
		// String departId=(String)map.get("departId");
		// if(operId!=null&&operId.trim().length()>0&&departId!=null&&departId.trim().length()>0){
		// cond += " and (exists (select 1 from mp_storage b where
		// a.storage_id=b.storage_id and "
		// +" b.oper_id="+operId+") or "
		// +" exists(select 1 from STORAGE_DEPART_RELA c where
		// c.storage_Id=a.storage_id "
		// +" and c.depart_id="+departId+")) ";
		// }
		// }
		RcStorageDAO dao = SrDAOFactory.getInstance().getRcStorageDAO();
		dao.setFlag(2); // 设置查找树的sql
		ArrayList list = (ArrayList) dao.findByCond(cond);
		// ArrayList listEx = new ArrayList(list);
		xml = XMLSegBuilder.toXmlItems(list);
		return xml;
	}

	/**
	 * 根据storageId查询对应的仓库详细信息
	 * 
	 * @param storageId
	 *            String
	 * @return RcStorageVO
	 */
	//public RcStorageVO qrySingleStorage(String storageId) {
	public RcStorageVO qrySingleStorage(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        String storageId  = (String)map.get("storageId");
        
		if (storageId == null || storageId.trim().length() < 1)
			return null;
		RcStorageDAO rcStorageDAO = SrDAOFactory.getInstance()
				.getRcStorageDAO();
		return rcStorageDAO.findByPrimaryKey(storageId);
	}

	/**
	 * 根据storageId查询该仓库的父级仓库的信息
	 * 
	 * @param storageId
	 *            String
	 * @return RcStorageVO
	 */
	//public RcStorageVO qryParentStorage(String storageId) {
	public RcStorageVO qryParentStorage(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        String storageId  = (String)map.get("storageId");
        
		if (storageId == null || storageId.trim().length() < 1)
			return null;
		RcStorageVO vo = null;
		RcStorageDAO dao = SrDAOFactory.getInstance().getRcStorageDAO();
		String cond = " a.storage_id=(select b.up_storage_id from rc_storage b where b.storage_id="
				+ DAOUtils.filterQureyCond(storageId) + ")";
		List list = dao.findByCond(cond);
		if (list != null && list.size() > 0)
			vo = (RcStorageVO) list.get(0);
		return vo;
	}

	/**
	 * 保存仓库信息
	 * 
	 * @param rcStorageVO
	 *            RcStorageVO
	 * @throws DAOSystemException
	 * @return String
	 */
	//public String saveRcStorage(RcStorageVO rcStorageVO, String lanId) throws DAOSystemException {
	public String saveRcStorage(DynamicDict dto) throws FrameException {
	    
	    Map map = (Map)dto.getValueByName("parameter") ;
	    RcStorageVO rcStorageVO  = (RcStorageVO)map.get("rcStorageVO");
	    String lanId  = (String)map.get("lanIdprovId");
	    
		String lanstrs[] = lanId.split("@");
		lanId = lanstrs[0];
		String provId = lanstrs[1];
		String StorageId = "";
		RcStorageDAO rcStorageDAO = SrDAOFactory.getInstance()
				.getRcStorageDAO();

		/*
		 * String[] sqlParams = { "", ""}; String SQL = " SELECT
		 * a.storage_id,a.storage_name,a.owner_id,a.storage_state," + "
		 * a.storage_desc,a.address,a.storage_code" + " FROM rc_storage a " + "
		 * WHERE a.storage_name = ? " + " AND exists " + " ( SELECT 1 FROM
		 * Storage_Depart_Rela b,mp_department c, ra_town d" + " WHERE
		 * a.storage_id = b.storage_id and b.depart_id=c.depart_id" + " and
		 * c.town_id = d.town_id " + " and d.lan_id=? )"; sqlParams[0] =
		 * rcStorageVO.getStorageName(); sqlParams[1] = lanId;
		 * 
		 * ArrayList list = rcStorageDAO.findBySql(SQL, sqlParams); if
		 * (list.size() > 0) { StorageId = "true"; return StorageId; }
		 */
		SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
		SequenceManageDAO sequenceManageDAO = seqDAOFactory
				.getSequenceManageDAO();
		StorageId = sequenceManageDAO.getNextSequence("RC_STORAGE",
				"STORAGE_ID");
		rcStorageVO.setStorageId(StorageId);
		rcStorageVO.setLan_id(lanId);
		// 设置为普通仓库
		rcStorageDAO.insert(rcStorageVO);

		String upId = rcStorageVO.getUpStorageId();
		if (upId == null || upId.length() <= 0)
			return StorageId;
		// 河南
		// 对上级仓库有权限的工号以及部门，都要分配该仓库的权限
		// 河南的屏蔽回退
		// if(provId.equals("11")){
		// CommonDAO dao = new CommonDAO();
		// //工号
		// // String sql="delete from mp_storage where storage_id="+upId+" and
		// storage_id in " +
		// // " (Select storage_id from rc_storage connect by storage_id= prior
		// up_storage_id start with storage_id="+upId+")";
		// // dao.update(sql);
		// String sql="insert into mp_storage(oper_id,storage_id,state) " +
		// " select distinct oper_id,"+rcStorageVO.getStorageId()+",'00' from
		// mp_storage where storage_id in " +
		// " (Select storage_id from rc_storage connect by storage_id= prior
		// up_storage_id start with storage_id="+upId+")";
		// dao.update(sql);
		// //部门
		// // sql="delete from storage_depart_rela where storage_id="+upId+" and
		// storage_id in " +
		// // "(Select storage_id from rc_storage connect by storage_id= prior
		// up_storage_id start with storage_id="+upId+")";
		// // dao.update(sql);
		// sql="insert into storage_depart_rela(depart_id,storage_id) " +
		// "select distinct depart_id ,"+rcStorageVO.getStorageId()+"from
		// storage_depart_rela where storage_id in " +
		// "(Select storage_id from rc_storage connect by storage_id= prior
		// up_storage_id start with storage_id="+upId+")";
		// dao.update(sql);
		// }

		return StorageId;
	}

	/**
	 * 修改仓库信息
	 * 
	 * @param rcStorageVO
	 *            RcStorageVO
	 * @throws DAOSystemException
	 */
	//public void updateRcStorage(Map map) throws DAOSystemException {
	public int updateRcStorage(DynamicDict dto) throws FrameException {
	    
	    Map map = (Map)dto.getValueByName("parameter") ;
	    
		if (map == null || map.get("vo") == null)
			return -1;
		String reworkIp = (String) map.get("reworkIp");
		String operId = (String) map.get("operId");
		RcStorageVO rcStorageVO = (RcStorageVO) map.get("vo");
		RcStorageDAO rcStorageDAO = SrDAOFactory.getInstance()
				.getRcStorageDAO();
		RcStorageVO vo_old = rcStorageDAO.findByPrimaryKey(rcStorageVO
				.getStorageId()); // 查询旧值记录日志
		rcStorageDAO.update(" storage_id=" + rcStorageVO.getStorageId(),
				rcStorageVO); // 更新
		// 插入相关通用日至
		RcPublicLogDAO logDao = SrDAOFactory.getInstance().getRcPublicLogDAO();
		RcPublicLogVO logVO = new RcPublicLogVO();
		logVO.setAct("M");
		logVO.setReworkTime(DAOUtils.getFormatedDate());
		logVO.setReworkTable("rc_storage");
		logVO.setReworkWen(operId);
		logVO.setReworkIp(reworkIp);
		logDao.logVO(logVO, vo_old, rcStorageVO);
		return 0;
	}

	/**
	 * 删除仓库信息
	 * 
	 * @param RcStorageVO
	 *            rcStorageVO
	 * @throws DAOSystemException
	 */
	//public int removeRcStorage(Map map) {
	public int removeRcStorage(DynamicDict dto) throws FrameException {
	    
	    Map map = (Map)dto.getValueByName("parameter") ;
	    
		if (map == null || map.get("vo") == null)
			return 0;
		String reworkIp = (String) map.get("reworkIp");
		String operId = (String) map.get("operId");
		RcStorageVO rcStorageVO = (RcStorageVO) map.get("vo");

		int retResult = 0;
		RcStorageDAO rcStorageDAO = SrDAOFactory.getInstance()
				.getRcStorageDAO();
		boolean isUsed = rcStorageDAO.checkStorageState(rcStorageVO
				.getStorageId());
		if (isUsed) {
			retResult = 1;
		} else {
			List downStorageList = rcStorageDAO.findBySql(
					"select * from rc_storage where up_storage_id="
							+ rcStorageVO.getStorageId(), new String[] {});

			if (downStorageList.size() > 0) {
				return 3;
			}
			rcStorageDAO.deleteByCond(" storage_id="
					+ rcStorageVO.getStorageId());
			// StorageDepartRelaDAO storageDepartRelaDAO =
			// SrDAOFactory.getInstance().getStorageDepartRelaDAO();
			// storageDepartRelaDAO.deleteByCond(" storage_id=" +
			// rcStorageVO.getStorageId());
			// MpStorageDAO mpStorageDAO =
			// SrDAOFactory.getInstance().getMpStorageDAO();
			// mpStorageDAO.deleteByCond(" storage_id=" +
			// rcStorageVO.getStorageId());
			retResult = 2;
			// 插入相关通用日至
			RcStorageVO vo_old = rcStorageDAO.findByPrimaryKey(rcStorageVO
					.getStorageId()); // 查询旧值记录日志
			RcPublicLogDAO logDao = SrDAOFactory.getInstance()
					.getRcPublicLogDAO();
			RcPublicLogVO logVO = new RcPublicLogVO();
			logVO.setAct("D");
			logVO.setReworkTime(DAOUtils.getFormatedDate());
			logVO.setReworkTable("rc_storage");
			logVO.setReworkWen(operId);
			logVO.setReworkIp(reworkIp);
			logDao.logVO(logVO, vo_old, null);
		}
		return retResult;
	}

	/**
	 * 查询仓库部门对应关系
	 * 
	 * @param map
	 *            HashMap
	 * @param pageIndex
	 *            int
	 * @param pageSize
	 *            int
	 * @throws DAOSystemException
	 * @return PageModel
	 */
	//public PageModel qryStorageDepartRela(HashMap map, int pageIndex,int pageSize) throws DAOSystemException {
	public PageModel qryStorageDepartRela(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        int pageIndex  = ((Integer)map.get("pageIndex")).intValue();
        int pageSize  = ((Integer)map.get("pageSize")).intValue();

		StringBuffer cond = new StringBuffer();

		if (!"".equals(map.get("storageId"))) {
			cond.append("  storage_id = ").append(map.get("storageId"));
		}

		StorageDepartRelaDAO storageDepartRelaDAO = SrDAOFactory.getInstance()
				.getStorageDepartRelaDAO();
		return PageHelper.popupPageModel(storageDepartRelaDAO, cond.toString(),
				pageIndex, pageSize);
	}

	/**
	 * 保存仓库部门对应关系
	 * 
	 * @param StorageDepartRelaVO
	 *            storageDepartRelaVO
	 * @throws DAOSystemException
	 * @return String
	 */
	//public boolean saveStorageDepartRela(StorageDepartRelaVO storageDepartRelaVO) throws DAOSystemException {
	public boolean saveStorageDepartRela(DynamicDict dto) throws FrameException {
	    
	    Map map = (Map)dto.getValueByName("parameter") ;
	    StorageDepartRelaVO storageDepartRelaVO  = (StorageDepartRelaVO)map.get("storageDepartRelaVO");
	
		boolean rtnresult = false;
		String provId = "";
		String storageIdstr = storageDepartRelaVO.getStorageId();
		String str[] = storageIdstr.split("@");
		if (str.length == 2) {
			storageDepartRelaVO.setStorageId(str[0]);
			provId = str[1];
		}
		StorageDepartRelaDAO storageDepartRelaDAO = SrDAOFactory.getInstance()
				.getStorageDepartRelaDAO();

		String SQL = "SELECT * FROM STORAGE_DEPART_RELA WHERE STORAGE_ID = "
				+ storageDepartRelaVO.getStorageId() + " and  DEPART_ID="
				+ storageDepartRelaVO.getDepartId();
		ArrayList list = storageDepartRelaDAO.findBySql(SQL, null);
		if (list.size() > 0) {
			rtnresult = true;
		} else {
			storageDepartRelaDAO.insert(storageDepartRelaVO);
		}
		// 河南的屏蔽回退
		// if(provId.equals("11")){
		// CommonDAO dao = new CommonDAO();
		// String sql="delete from storage_depart_rela where depart_id= "
		// +storageDepartRelaVO.getStorageId()+
		// "and storage_id in " +
		// "(Select storage_id from rc_storage connect by prior storage_id=
		// up_storage_id start with
		// storage_id="+storageDepartRelaVO.getStorageId()+")";
		// dao.update(sql);
		// sql="insert into storage_depart_rela(storage_id,depart_id) " +
		// "select storage_id,"+storageDepartRelaVO.getDepartId()+" from
		// rc_storage connect by prior storage_id= up_storage_id start with
		// storage_id="+storageDepartRelaVO.getStorageId();
		// dao.update(sql);
		// }
		return rtnresult;
	}

	/**
	 * 保存多条仓库部门对应关系
	 * 
	 * @param StorageDepartRelaVO
	 *            storageDepartRelaVO
	 * @throws DAOSystemException
	 * @return String
	 */
	//public boolean saveMultiStorageDepartRela(StorageDepartRelaVO storageDepartRelaVO) throws DAOSystemException {
	public boolean saveMultiStorageDepartRela(DynamicDict dto) throws FrameException {
	    
	    Map map = (Map)dto.getValueByName("parameter") ;
        StorageDepartRelaVO storageDepartRelaVO  = (StorageDepartRelaVO)map.get("storageDepartRelaVO");
	    
		String provId = "";
		String storageIdstr = storageDepartRelaVO.getStorageId();
		String str[] = storageIdstr.split("@");
		if (str.length == 2) {
			storageDepartRelaVO.setStorageId(str[0]);
			provId = str[1];
		}

		String departId = "";
		String strtmp = "";
		boolean rtnresult = false;
		StorageDepartRelaDAO storageDepartRelaDAO = SrDAOFactory.getInstance()
				.getStorageDepartRelaDAO();

		String SQL = "SELECT * FROM STORAGE_DEPART_RELA WHERE STORAGE_ID = "
				+ storageDepartRelaVO.getStorageId() + " and  DEPART_ID in ("
				+ storageDepartRelaVO.getDepartId() + ")";
		ArrayList list = storageDepartRelaDAO.findBySql(SQL, null);
		if (list.size() > 0) {
			rtnresult = true;
		} else {
			for (int i = 0; i < storageDepartRelaVO.getDepartId().length(); i++) {
				if (i + 1 == storageDepartRelaVO.getDepartId().length()
						|| i + 1 < storageDepartRelaVO.getDepartId().length()) {
					strtmp = strtmp
							+ storageDepartRelaVO.getDepartId().substring(i,
									i + 1);
					System.out.println("adf = " + strtmp);
					if (i + 2 < storageDepartRelaVO.getDepartId().length()) {
						System.out.println("substring = "
								+ storageDepartRelaVO.getDepartId().substring(
										i + 1, i + 2));
						if (",".equals(storageDepartRelaVO.getDepartId()
								.substring(i + 1, i + 2))) {
							StorageDepartRelaVO storageDepartRelaVOtmp = new StorageDepartRelaVO();
							storageDepartRelaVOtmp
									.setStorageId(storageDepartRelaVO
											.getStorageId());
							storageDepartRelaVOtmp
									.setDepartName(storageDepartRelaVO
											.getDepartName());

							storageDepartRelaVOtmp.setDepartId(strtmp);
							strtmp = "";
							storageDepartRelaDAO.insert(storageDepartRelaVOtmp);
							i = i + 1;
							System.out.println("strtmp = " + strtmp);
						}
					}
				}
			}
			System.out.println("strtmp1 = " + strtmp);
			storageDepartRelaVO.setDepartId(strtmp);
			storageDepartRelaDAO.insert(storageDepartRelaVO);
		}
		if (provId.equals("11")) {
			CommonDAO dao = new CommonDAO();
			String sql = "delete from storage_depart_rela where depart_id in( "
					+ storageDepartRelaVO.getDepartId()
					+ ") and  storage_id in "
					+ "(Select storage_id from rc_storage connect by  prior storage_id=  up_storage_id start with storage_id="
					+ storageDepartRelaVO.getStorageId() + ")";
			dao.update(sql);
			String ids[] = storageDepartRelaVO.getDepartId().split(",");
			for (int i = 0; i < ids.length; i++) {
				sql = "insert into storage_depart_rela(storage_id,depart_id)  "
						+ "select distinct storage_id,"
						+ ids[i]
						+ " from  rc_storage connect by  prior storage_id=  up_storage_id start with storage_id="
						+ storageDepartRelaVO.getStorageId();
				dao.update(sql);
			}
		}
		return rtnresult;
	}

	/**
	 * 修改仓库部门对应关系
	 * 
	 * @param StorageDepartRelaVO
	 *            storageDepartRelaVO
	 * @throws DAOSystemException
	 */
	//public void updateStorageDepartRela(StorageDepartRelaVO storageDepartRelaVO, String oldDepartId)throws DAOSystemException {
	public int updateStorageDepartRela(DynamicDict dto) throws FrameException {
	    
	     Map map = (Map)dto.getValueByName("parameter") ;
	     StorageDepartRelaVO storageDepartRelaVO  = (StorageDepartRelaVO)map.get("storageDepartRelaVO");
	     String oldDepartId  = (String)map.get("oldDepartId");
	    
		StorageDepartRelaDAO storageDepartRelaDAO = SrDAOFactory.getInstance()
				.getStorageDepartRelaDAO();

		/*
		 * String SQL = "SELECT * FROM STORAGE_DEPART_RELA WHERE STORAGE_ID = " +
		 * storageDepartRelaVO.getStorageId() + " and DEPART_ID=" +
		 * storageDepartRelaVO.getDepartId(); ArrayList list =
		 * storageDepartRelaDAO.findBySql(SQL, null); if (list.size() > 0) {
		 * throw new EJBException(new BmException(
		 * BmError.STORAGE_DEPART_RELA_PKEY_Error)); }
		 */
		storageDepartRelaDAO.update(" storage_id="
				+ storageDepartRelaVO.getStorageId() + " AND depart_id="
				+ oldDepartId, storageDepartRelaVO);
		
		return 0;
	}

	/**
	 * 删除仓库部门对应关系
	 * 
	 * @param StorageDepartRelaVO
	 *            storageDepartRelaVO
	 * @throws Exception
	 */
	//public String removeStorageDepartRela(StorageDepartRelaVO storageDepartRelaVO) throws Exception {
	public String removeStorageDepartRela(DynamicDict dto) throws FrameException {
	    
	    Map map = (Map)dto.getValueByName("parameter") ;
        StorageDepartRelaVO storageDepartRelaVO  = (StorageDepartRelaVO)map.get("storageDepartRelaVO");
	    
		String provId = "";
		String storageIdstr = storageDepartRelaVO.getStorageId();
		String str[] = storageIdstr.split("@");
		if (str.length == 2) {
			storageDepartRelaVO.setStorageId(str[0]);
			provId = str[1];
		}
		// 河南的屏蔽回退
		// if(provId.equals("11")){
		// SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
		// String sql="select depart_id from storage_depart_rela where depart_id
		// in( ?) and storage_id in " +
		// "(Select storage_id from rc_storage connect by storage_id= prior
		// up_storage_id start with storage_id=?) and storage_id<>?";
		// String params[] = new
		// String[]{storageDepartRelaVO.getDepartId(),storageDepartRelaVO.getStorageId(),storageDepartRelaVO.getStorageId()};
		// List flag = dao.executeQueryForMapList(sql, params);
		// if(flag!=null && flag.size()>0){
		// return "部门对该仓库的上级仓库有权限，不能删除！";
		// }else{
		// sql="delete from storage_depart_rela where depart_id in( ?) and
		// storage_id in " +
		// "(Select storage_id from rc_storage connect by storage_id= prior
		// up_storage_id start with storage_id=?) ";
		// params = new
		// String[]{storageDepartRelaVO.getDepartId(),storageDepartRelaVO.getStorageId()};
		// dao.excute(sql, params);
		// }
		// }else{

		StorageDepartRelaDAO storageDepartRelaDAO = SrDAOFactory.getInstance()
				.getStorageDepartRelaDAO();
		storageDepartRelaDAO.deleteByCond(" storage_id="
				+ storageDepartRelaVO.getStorageId() + " AND depart_id in ("
				+ storageDepartRelaVO.getDepartId() + ")");

		System.out.println(" storage_id=" + storageDepartRelaVO.getStorageId()
				+ " AND depart_id in " + storageDepartRelaVO.getDepartId());
		return "";
	}

	/**
	 * 查询操作员仓库关系
	 * 
	 * @param map
	 *            HashMap
	 * @param pageIndex
	 *            int
	 * @param pageSize
	 *            int
	 * @throws DAOSystemException
	 * @return PageModel
	 */
	//public PageModel qryMpStorage(HashMap map, int pageIndex, int pageSize) throws DAOSystemException {
	public PageModel qryMpStorage(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
	    
        int pageIndex  = ((Integer)map.get("pageIndex")).intValue();
        int pageSize  = ((Integer)map.get("pageSize")).intValue();

		StringBuffer cond = new StringBuffer();

		if (!"".equals(map.get("storageId"))) {
			cond.append("  storage_id = ").append(map.get("storageId"));
		}

		MpStorageDAO mpStorageDAO = SrDAOFactory.getInstance()
				.getMpStorageDAO();
		return PageHelper.popupPageModel(mpStorageDAO, cond.toString(),
				pageIndex, pageSize);
	}

	/**
	 * 保存操作员仓库关系
	 * 
	 * @param MpStorageVO
	 *            mpStorageVO
	 * @throws DAOSystemException
	 * @return String
	 */
	//public boolean saveMpStorage(MpStorageVO mpStorageVO) throws DAOSystemException {
	public boolean saveMpStorage(DynamicDict dto) throws FrameException {
        
	    Map map = (Map)dto.getValueByName("parameter") ;
	    MpStorageVO mpStorageVO  = (MpStorageVO)map.get("mpStorageVO");
	    
		boolean rtnresult = false;
		String provId = "";
		String storageIdstr = mpStorageVO.getStorageId();
		String str[] = storageIdstr.split("@");
		if (str.length == 2) {
			mpStorageVO.setStorageId(str[0]);
			provId = str[1];
		}
		MpStorageDAO mpStorageDAO = SrDAOFactory.getInstance()
				.getMpStorageDAO();
		String ids = mpStorageVO.getOperId();
		if (ids.indexOf(",") > 0) {// 多选，多个员工的添加
			String[] operIds = mpStorageVO.getOperId().split(",");
			String SQL = "SELECT * FROM MP_STORAGE WHERE STORAGE_ID = "
					+ mpStorageVO.getStorageId() + " and  oper_id in ( "
					+ mpStorageVO.getOperId() + " )";
			ArrayList list = mpStorageDAO.findBySql(SQL, null);
			if (list.size() > 0) {
				rtnresult = true;
			} else {
				for (int i = 0; i < operIds.length; i++) {
					mpStorageVO.setOperId(operIds[i]);
					mpStorageDAO.insert(mpStorageVO);
				}
			}

		} else {
			String SQL = "SELECT * FROM MP_STORAGE WHERE STORAGE_ID = "
					+ mpStorageVO.getStorageId() + " and  oper_id="
					+ mpStorageVO.getOperId();
			ArrayList list = mpStorageDAO.findBySql(SQL, null);
			if (list.size() > 0) {
				rtnresult = true;
			} else {
				mpStorageDAO.insert(mpStorageVO);
			}
		}

		// 河南
		// 对当前仓库的所有下级仓库的id
		// String sql = " select * from (Select storage_id from rc_storage
		// connect by prior storage_id= up_storage_id start with
		// storage_id="+mpStorageVO.getStorageId()+") a"
		// +" where a.storage_id <>"+mpStorageVO.getStorageId();//（排除当前仓库id）
		// 河南的屏蔽回退
		// if(provId.equals("11")){
		// String sql ="";
		// CommonDAO dao = new CommonDAO();
		// String []operIds = ids.split(",");
		// sql = "delete from mp_storage where storage_id in ("+"select
		// storage_id from (Select storage_id from rc_storage connect by prior
		// storage_id= up_storage_id start with
		// storage_id="+mpStorageVO.getStorageId()+") a"
		// +" where a.storage_id <>"+mpStorageVO.getStorageId()+" )and oper_id
		// in ( "+ids+")";
		// dao.update(sql);
		// for(int i=0;i<operIds.length;i++){
		// sql = " insert into mp_storage(storage_id,oper_id,state) select
		// storage_id ,"+operIds[i]+", '00' from (Select storage_id from
		// rc_storage connect by prior storage_id= up_storage_id start with
		// storage_id="+mpStorageVO.getStorageId()+") a"
		// +" where a.storage_id <>"+mpStorageVO.getStorageId();
		// dao.update(sql);
		// }
		// }

		return rtnresult;
	}

	/**
	 * 修改操作员仓库关系
	 * 
	 * @param StorageDepartRelaVO
	 *            storageDepartRelaVO
	 * @throws DAOSystemException
	 */
	//public void updateMpStorage(MpStorageVO mpStorageVO) throws DAOSystemException {
	public int updateMpStorage(DynamicDict dto) throws FrameException {
		MpStorageDAO mpStorageDAO = SrDAOFactory.getInstance()
				.getMpStorageDAO();
		
		Map map = (Map)dto.getValueByName("parameter") ;
        MpStorageVO mpStorageVO  = (MpStorageVO)map.get("mpStorageVO");

		/*
		 * String SQL = "SELECT * FROM MP_STORAGE WHERE STORAGE_ID = " +
		 * mpStorageVO.getStorageId() + " and oper_id=" +
		 * mpStorageVO.getOperId(); ArrayList list = mpStorageDAO.findBySql(SQL,
		 * null); if (list.size() > 0) { throw new EJBException(new BmException(
		 * BmError.MP_STORAGE_PKEY_Error)); }
		 */
		mpStorageDAO.update(" storage_id=" + mpStorageVO.getStorageId()
				+ " AND oper_id=" + mpStorageVO.getOperId(), mpStorageVO);
		return 1;
	}

	/**
	 * 删除操作员仓库关系
	 * 
	 * @param MpStorageVO
	 *            mpStorageVO
	 * @throws Exception
	 */
	//public String removeMpStorage(MpStorageVO mpStorageVO) throws Exception {
	public String removeMpStorage(DynamicDict dto) throws FrameException {
	    Map map = (Map)dto.getValueByName("parameter") ;
        MpStorageVO mpStorageVO  = (MpStorageVO)map.get("mpStorageVO");
	    
		String provId = "";
		String storageIdstr = mpStorageVO.getStorageId();
		String str[] = storageIdstr.split("@");
		if (str.length == 2) {
			mpStorageVO.setStorageId(str[0]);
			provId = str[1];
		}
		MpStorageDAO mpStorageDAO = SrDAOFactory.getInstance()
				.getMpStorageDAO();
		// 河南的屏蔽回退
		// if(provId.equals("11")){
		// SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
		// String sql="select oper_id from mp_storage where oper_id in( ? ) and
		// storage_id in " +
		// "(Select storage_id from rc_storage connect by storage_id= prior
		// up_storage_id start with storage_id=?) and storage_id<>?";
		// String params[] = new
		// String[]{mpStorageVO.getOperId(),mpStorageVO.getStorageId(),mpStorageVO.getStorageId()};
		// List flag = dao.executeQueryForMapList(sql, params);
		// if(flag!=null && flag.size()>0){
		// return "工号对该仓库的上级仓库有权限，不能删除！";
		// }else{
		// sql="delete from mp_storage where oper_id in( ?) and storage_id in "
		// +
		// "(Select storage_id from rc_storage connect by storage_id= prior
		// up_storage_id start with storage_id=?) ";
		// params = new
		// String[]{mpStorageVO.getOperId(),mpStorageVO.getStorageId()};
		// dao.excute(sql, params);
		// }
		// }else{
		mpStorageDAO.deleteByCond(" storage_id=" + mpStorageVO.getStorageId()
				+ " AND oper_id=" + mpStorageVO.getOperId());

		// }
//		return null;
		return "";
	}

	/**
	 * 查询仓库限制数量
	 * 
	 * @param map
	 *            Map
	 * @param pageIndex
	 *            int
	 * @param pageSize
	 *            int
	 * @throws DAOSystemException
	 * @return PageModel
	 */
	//public PageModel qryStockLimit(Map map, int pageIndex, int pageSize) {
	public PageModel qryStockLimit(DynamicDict dto) throws FrameException {

	    Map map = (Map)dto.getValueByName("parameter") ;
        int pageIndex  = ((Integer)map.get("pageIndex")).intValue();
        int pageSize  = ((Integer)map.get("pageSize")).intValue();
	    
		StringBuffer cond = new StringBuffer();

		if (map.get("storageId") != null && !"".equals(map.get("storageId"))) {
			cond.append(" and a.storage_id = ").append(
					(String) map.get("storageId"));
		}

		RcStockLimitDAO dao = SrDAOFactory.getInstance().getRcStockLimitDAO();
		return PageHelper.popupPageModel(dao, cond.toString(), pageIndex,
				pageSize);
	}

	/**
	 * 保存仓库的库存数量限制
	 * 
	 * @param MpStorageVO
	 *            mpStorageVO
	 * @throws DAOSystemException
	 * @return String
	 */
	//public int saveStockLimit(Map map) {
	public int saveStockLimit(DynamicDict dto) throws FrameException {
	    
	    Map map = (Map)dto.getValueByName("parameter");
	    
		if (map == null || map.get("vo") == null || map.get("opeType") == null) {
			return -1;
		}
		RcStockLimitVO vo = (RcStockLimitVO) map.get("vo");
		String opeType = (String) map.get("opeType");
		String reworkIp = (String) map.get("reworkIp");
		String operId = (String) map.get("operId");

		if (vo == null || vo.getStorageId() == null
				|| vo.getStorageId().trim().length() < 1
				|| vo.getFamilyId() == null
				|| vo.getFamilyId().trim().length() < 1 || opeType == null
				|| opeType.trim().length() < 1) {
			return -1;
		}
		RcPublicLogVO logVO = new RcPublicLogVO();
		RcStockLimitVO vo_old = null;

		int rtnresult = 1;
		RcStockLimitDAO dao = SrDAOFactory.getInstance().getRcStockLimitDAO();
		if ("add".equals(opeType.trim())) {
			String cond = " and a.STORAGE_ID = "
					+ DAOUtils.filterQureyCond(vo.getStorageId())
					+ " and  a.FAMILY_ID="
					+ DAOUtils.filterQureyCond(vo.getFamilyId());
			List list = dao.findByCond(cond);
			if (list.size() > 0) {
				rtnresult = -2;
			} else {
				dao.insert(vo);
			}
			logVO.setAct("A");
		} else if ("update".equals(opeType.trim())) {
			// 查询以前的纪录，以便于纪录日至
			vo_old = dao.findByPrimaryKey(vo.getFamilyId(), vo.getStorageId());
			boolean updateBoo = dao.update(vo.getFamilyId(), vo.getStorageId(),
					vo);
			if (!updateBoo) {
				rtnresult = 0;
			} else {
				rtnresult = 1;
			}
			logVO.setAct("M");
		} else {
			rtnresult = -1;
		}
		// 如果修改成功添加日至
		if (rtnresult == 1) {
			RcPublicLogDAO logDao = SrDAOFactory.getInstance()
					.getRcPublicLogDAO();
			logVO.setReworkTime(DAOUtils.getFormatedDate());
			logVO.setReworkTable("rc_stock_limit");
			logVO.setReworkWen(operId);
			logVO.setReworkIp(reworkIp);
			logDao.logVO(logVO, vo_old, vo);
		}
		return rtnresult;
	}

	/**
	 * 删除仓库库存限制条件
	 * 
	 * @param RcStockLimitVO
	 * @throws DAOSystemException
	 */
	//public long removeStockLimit(Map map) {
	public long removeStockLimit(DynamicDict dto) throws FrameException {
	    
	    Map map = (Map)dto.getValueByName("parameter");
	    
		if (map == null || map.get("vo") == null) {
			return -1;
		}
		RcStockLimitVO vo = (RcStockLimitVO) map.get("vo");
		RcStockLimitDAO dao = SrDAOFactory.getInstance().getRcStockLimitDAO();
		// 插入相关通用日至
		String reworkIp = (String) map.get("reworkIp");
		String operId = (String) map.get("operId");
		RcStockLimitVO vo_old = null;
		RcPublicLogDAO logDao = SrDAOFactory.getInstance().getRcPublicLogDAO();
		vo_old = dao.findByPrimaryKey(vo.getFamilyId(), vo.getStorageId());
		RcPublicLogVO logVO = new RcPublicLogVO();
		logVO.setAct("D");
		logVO.setReworkTime(DAOUtils.getFormatedDate());
		logVO.setReworkTable("rc_stock_limit");
		logVO.setReworkWen(operId);
		logVO.setReworkIp(reworkIp);
		logDao.logVO(logVO, vo_old, null);
		// 删除
		long rtn = dao.delete(vo.getFamilyId(), vo.getStorageId());
		return rtn;
	}

	/**
	 * 查询仓库信息，根据操作员的工号和其所在部门有权看到的部门集合。两者之和
	 * 
	 * @param map
	 *            Map
	 * @param pageIndex
	 *            int
	 * @param pageSize
	 *            int
	 * @throws DAOSystemException
	 * @return PageModel
	 */

	public PageModel qryStorageByOperDept(DynamicDict dto) throws FrameException {
	    Map map = (Map)dto.getValueByName("parameter") ;

		String operId = null;
		String departId = null;
		String storageName = null;
		String storageCode = null;
		String upStorageId = null;
		String lanId = null;
		String provId = null;
		int pageIndex = ((Integer)map.get("pageIndex")).intValue();
		int pageSize = ((Integer)map.get("pageSize")).intValue();
		if (map.get("operId") != null)
			operId = (String) map.get("operId");
		if (map.get("departId") != null)
			departId = (String) map.get("departId");
		if (map.get("storageName") != null)
			storageName = (String) map.get("storageName");
		if (map.get("storageCode") != null)
			storageCode = (String) map.get("storageCode");
		if (map.get("upStorageId") != null)
			upStorageId = (String) map.get("upStorageId");

		if (map.get("lanId") != null)
			lanId = (String) map.get("lanId");
		if (map.get("provId") != null)
			provId = (String) map.get("provId");

		String cond = " a.RC_TYPE=-1 ";
		if (lanId != null && lanId.trim().length() > 0 && provId.equals("20")) {
			cond += " and (a.lan_id is null or a.lan_id = " + lanId + ")";
		}
		if (operId != null && operId.trim().length() > 0 && departId != null
				&& departId.trim().length() > 0) {
			cond += " and (exists (select 1 from mp_storage b where a.storage_id=b.storage_id and "
					+ " b.oper_id="
					+ operId
					+ ") or "
					+ " exists(select 1 from STORAGE_DEPART_RELA c where c.storage_Id=a.storage_id "
					+ " and c.depart_id=" + departId + ")) ";
		}

		if (storageName != null && storageName.trim().length() > 0) {
			cond += " and a.storage_name like '%" + storageName + "%'";
		}
		if (storageCode != null && storageCode.trim().length() > 0) {
			cond += " and a.storage_code like '%" + storageCode + "%'";
		}
		if (upStorageId != null && upStorageId.trim().length() > 0) {
			cond += " and a.up_storage_id=" + upStorageId + " ";
		}

		RcStorageDAO rcStorageDAO = SrDAOFactory.getInstance()
				.getRcStorageDAO();
		// if(map.get("ListFlag")!=null && map.get("ListFlag").equals("Y")){
		// PageModel page = new PageModel();
		// List list = rcStorageDAO.findByCond(cond);
		// page.setList(list);
		// page.setTotalCount(list.size());
		// page.setPageCount(1);
		// page.setPageIndex(1);
		// page.setPageSize(list.size());
		// return page;
		// }
		return PageHelper.popupPageModel(rcStorageDAO, cond, pageIndex,
				pageSize);
	}

	public PageModel qryStorageByOper(Map map, int pageIndex, int pageSize) {
		String operId = null;
		String departId = null;
		String storageName = null;
		String storageCode = null;
		String lanId = null;
		String provId = null;
		if (map.get("operId") != null)
			operId = (String) map.get("operId");
		if (map.get("departId") != null)
			departId = (String) map.get("departId");
		if (map.get("storageName") != null)
			storageName = (String) map.get("storageName");
		if (map.get("storageCode") != null)
			storageCode = (String) map.get("storageCode");

		if (map.get("lanId") != null)
			lanId = (String) map.get("lanId");
		if (map.get("provId") != null)
			provId = (String) map.get("provId");

		String cond = " a.RC_TYPE=10120 ";
		if (operId != null && operId.trim().length() > 0 && departId != null
				&& departId.trim().length() > 0) {
			cond += " and (exists (select 1 from mp_storage b where a.storage_id=b.storage_id and "
					+ " b.oper_id in (" + operId + "))) ";
		}

		if (storageName != null && storageName.trim().length() > 0) {
			cond += " and a.storage_name like '%" + storageName + "%'";
		}
		if (storageCode != null && storageCode.trim().length() > 0) {
			cond += " and a.storage_code like '%" + storageCode + "%'";
		}

		RcStorageDAO rcStorageDAO = SrDAOFactory.getInstance()
				.getRcStorageDAO();
		// if(map.get("ListFlag")!=null && map.get("ListFlag").equals("Y")){
		// PageModel page = new PageModel();
		// List list = rcStorageDAO.findByCond(cond);
		// page.setList(list);
		// page.setTotalCount(list.size());
		// page.setPageCount(1);
		// page.setPageIndex(1);
		// page.setPageSize(list.size());
		// return page;
		// }
		return PageHelper.popupPageModel(rcStorageDAO, cond, pageIndex,
				pageSize);
	}

	/**
	 * 江西：查询仓库信息，根据操作员的工号和其所在部门，以及lanId，有权看到的部门集合。两者之和
	 * 
	 * @param map
	 *            Map
	 * @param pageIndex
	 *            int
	 * @param pageSize
	 *            int
	 * @throws DAOSystemException
	 * @return PageModel
	 */
	public PageModel qryStorageByOperDeptAndLanId(Map map, int pageIndex,
			int pageSize) {

		String operId = null;
		String departId = null;
		String storageName = null;
		String storageCode = null;
		String upStorageId = null;
		String lanId = null;
		String provId = null;
		if (map.get("operId") != null)
			operId = (String) map.get("operId");
		if (map.get("departId") != null)
			departId = (String) map.get("departId");
		if (map.get("storageName") != null)
			storageName = (String) map.get("storageName");
		if (map.get("storageCode") != null)
			storageCode = (String) map.get("storageCode");
		if (map.get("upStorageId") != null)
			upStorageId = (String) map.get("upStorageId");

		if (map.get("lanId") != null)
			lanId = (String) map.get("lanId");
		if (map.get("provId") != null)
			provId = (String) map.get("provId");

		String cond = " a.RC_TYPE=-1 ";
		if (lanId != null && lanId.trim().length() > 0) {
			cond += " and  a.lan_id = " + lanId;
		}
		if (operId != null && operId.trim().length() > 0 && departId != null
				&& departId.trim().length() > 0) {
			cond += " and (exists (select 1 from mp_storage b where a.storage_id=b.storage_id and "
					+ " b.oper_id="
					+ operId
					+ ") or "
					+ " exists(select 1 from STORAGE_DEPART_RELA c where c.storage_Id=a.storage_id "
					+ " and c.depart_id=" + departId + ")) ";
		}

		if (storageName != null && storageName.trim().length() > 0) {
			cond += " and a.storage_name like '%" + storageName + "%'";
		}
		if (storageCode != null && storageCode.trim().length() > 0) {
			cond += " and a.storage_code like '%" + storageCode + "%'";
		}
		if (upStorageId != null && upStorageId.trim().length() > 0) {
			cond += " and a.up_storage_id=" + upStorageId + " ";
		}

		RcStorageDAO rcStorageDAO = SrDAOFactory.getInstance()
				.getRcStorageDAO();
		// if(map.get("ListFlag")!=null && map.get("ListFlag").equals("Y")){
		// PageModel page = new PageModel();
		// List list = rcStorageDAO.findByCond(cond);
		// page.setList(list);
		// page.setTotalCount(list.size());
		// page.setPageCount(1);
		// page.setPageIndex(1);
		// page.setPageSize(list.size());
		// return page;
		// }
		return PageHelper.popupPageModel(rcStorageDAO, cond, pageIndex,
				pageSize);
	}

	//public int detectRela(String operId, String departId, String storageId) {
	public int detectRela(DynamicDict dto) throws FrameException {
        
	    Map map = (Map)dto.getValueByName("parameter") ;
        String operId  = (String)map.get("operId");
        String departId  = (String)map.get("departId");
        String storageId  = (String)map.get("storageId");
	    
		SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
		long retv = 0;
		String sql = "";
		sql = " select count(1) as COL_COUNTS from RC_STORAGE where storage_id = "
				+ storageId + " and C_OPER_ID = " + operId;
		retv = comDao.count(sql);
		if (retv == 0) {
			sql = " select count(1) as COL_COUNTS from MP_STORAGE where storage_id = "
					+ storageId + " and oper_id = " + operId;
			retv = comDao.count(sql);
			if (retv == 0) {
				sql = " select count(1) as COL_COUNTS from STORAGE_DEPART_RELA where storage_id = "
						+ storageId + " and  depart_id = " + departId;
				retv = comDao.count(sql);
			}
		}
		return (int) retv;
	}

	//public PageModel qryStorageByCond(HashMap map, int pageIndex, int pageSize) {
	public PageModel qryStorageByCond(DynamicDict dto) throws FrameException {
		RcStorageDAO dao = SrDAOFactory.getInstance()
				.getRcStorageDAOExt();
		
		Map map = (Map)dto.getValueByName("parameter");
	    int pageIndex  = ((Integer)map.get("pageIndex")).intValue();
	    int pageSize  = ((Integer)map.get("pageSize")).intValue();
		
		String lanId = (String) map.get("lanId");
		String departId = (String) map.get("departId");
		String operId = (String) map.get("operId");
		String storageName = (String) map.get("storageName");
		String storageId = (String) map.get("storageId");
		String operLan = (String) map.get("operLan");
		String storageType = (String) map.get("storageType");
		String cond = "";
		if (!"".equals(storageId) && storageId != null) {
			cond = " a.storage_id = " + storageId;
		} else {
			cond = " a.lan_id = " + lanId;
			if (operLan == null || !"1".equals(operLan)) {
				if (("".equals(departId) || departId == null)
						&& ("".equals(operId) || operId == null)) {
					String loginId = (String) map.get("loginId");
					String loginDepartId = (String) map.get("loginDepartId");
					if (loginId != null && loginId.trim().length() > 0
							&& loginDepartId != null
							&& loginDepartId.trim().length() > 0) {
						cond += " and (exists (select 1 from mp_storage e where a.storage_id=e.storage_id and "
								+ " e.oper_id="
								+ loginId
								+ ") or "
								+ " exists(select 1 from STORAGE_DEPART_RELA c where c.storage_Id=a.storage_id "
								+ " and c.depart_id="
								+ loginDepartId
								+ ") or a.c_oper_id = " + loginId + " ) ";
					}
				} else {
					if (!"".equals(departId) && departId != null) {
						cond += " and exists(select 1 from STORAGE_DEPART_RELA c where c.storage_Id=a.storage_id "
								+ " and c.depart_id=" + departId + ") ";
					}
					if (!"".equals(operId) && operId != null) {
						cond += " and (exists (select 1 from mp_storage e where a.storage_id=e.storage_id and "
								+ " e.oper_id="
								+ operId
								+ ") or a.c_oper_id = " + operId + " )";
					}
				}
			}

			if (!"".equals(storageName) && storageName != null) {
				cond += " and a.storage_name like '"
						+ DAOUtils.filterQureyCond(storageName) + "%'";
			}
			if (!"".equals(storageType) && storageType != null) {
				cond += " and a.storage_type = '"
						+ DAOUtils.filterQureyCond(storageType) + "'";
			}
		}
		return PageHelper.popupPageModel(dao, cond, pageIndex, pageSize);
	}

	/**
	 * 校验批量导入文件，提取可以插入mp_storage的列表
	 * 
	 * @param map
	 *            Map
	 * @throws DAOSystemException
	 * @return map Map
	 */
	//public Map batchAddMpStorage(Map map) {
    public Map batchAddMpStorage(DynamicDict dto) throws FrameException {

        Map map = (Map)dto.getValueByName("parameter") ;
		
        List dataList = (List) map.get("dataList");
		String operId = (String) map.get("operId");
		String departId = (String) map.get("departId");
		List failList = new ArrayList();
		List todoList = new ArrayList();
		List doList = new ArrayList();
		String errInfo1 = "操作员、仓库不存在或者登陆工号没有权限;";
		String errInfo2 = "操作员仓库关系已存在;";
		Map retMap = new HashMap();

		String[] param = null;
		String operator = "";
		String storageId = "";

		String sql = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		if (dataList == null || dataList.size() == 0)
			return null;
		try {
			conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB, this);

			// 校验操作员、仓库是否存在和登陆工号的权限
			sql = "select 1 from mp_operator a where a.oper_id =? "
					+ " and exists (select 1 from rc_storage b,mp_storage c,STORAGE_DEPART_RELA d where b.storage_id = ? and (b.c_oper_id = ? or ( b.storage_id = c.storage_id and c.oper_id = ?"
					+ " or (d.storage_id = ? and d.depart_id = ?))))";
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			for (int i = 0; i < dataList.size(); i++) {
				param = (String[]) dataList.get(i);
				operator = param[0];
				storageId = param[1];
				int index = 1;
				stmt.setString(index++, operator);
				stmt.setString(index++, storageId);
				stmt.setString(index++, operator);
				stmt.setString(index++, operId);
				stmt.setString(index++, storageId);
				stmt.setString(index++, departId);
				rs = stmt.executeQuery();
				if (!rs.next())
					failList.add(operator + " " + storageId + " --- "
							+ errInfo1);// 不存在或者没有权限的列表
				else
					todoList.add(param);
			}
			if (todoList == null || todoList.size() == 0) {
				retMap.put("failList", failList);
				retMap.put("successNum", "0");
				return retMap;
			}
			sql = "select 1 from mp_storage where storage_id = ? and oper_id = ?";
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			for (int i = 0; i < todoList.size(); i++) {
				param = (String[]) todoList.get(i);
				operator = param[0];
				storageId = param[1];

				int index = 1;
				stmt.setString(index++, storageId);
				stmt.setString(index++, operator);
				rs = stmt.executeQuery();
				if (rs.next())
					failList.add(operator + " " + storageId + " --- "
							+ errInfo2);
				else
					doList.add(param);// 待插入列表
			}
			if (doList == null || doList.size() == 0) {
				retMap.put("failList", failList);
				retMap.put("successNum", "0");
				return retMap;
			}
			String ret = batchInsertMpStorage(doList);
			if ("1".equals(ret)) {
				retMap.put("failList", failList);
				retMap.put("doneList", doList);
				retMap.put("result", ret);
				retMap.put("successNum", String.valueOf(doList.size()));
				return retMap;
			}
		} catch (SQLException se) {
			Debug.print(sql, this);
			//throw new DAOSystemException("SQLException while insert sql:\n" + sql, se);
			throw new FrameException(-1456798,"SQLException while insert sql:\n" + sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return retMap;
	}

	/**
	 * 批量插入mp_storage的列表
	 * 
	 * @param list
	 *            List
	 * @throws DAOSystemException
	 * @return list List
	 */
	public String batchInsertMpStorage(List list) {
		String sql = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		String[] param = null;
		String operator = "";
		String storageId = "";
		String ret = "0";
		try {
			conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB, this);

			sql = "insert into  mp_storage(storage_id,oper_id,state) values (?,?,'00')";
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			for (int i = 0; i < list.size(); i++) {
				param = (String[]) list.get(i);
				operator = param[0];
				storageId = param[1];
				int index = 1;
				stmt.setString(index++, storageId);
				stmt.setString(index++, operator);
				stmt.addBatch();
			}
			stmt.executeBatch();
			ret = "1";
		} catch (SQLException se) {
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while insert sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return ret;
	}

	public String checkRight(String storageId, String operId, String departId)
			throws Exception {

		String sql = "select 1 from mp_storage where storage_id = ? and oper_id=? "
				+ " union all select 1 from storage_depart_rela where storage_id=? and depart_id = ?";
		String[] params = new String[] { storageId, operId, storageId, departId };
		SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
		List ret = dao.executeQueryForMapList(sql, params);
		if (ret != null && ret.size() > 0) {
			return "1";
		}
		return "0";
	}
}
