package com.ztesoft.crm.salesres.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.common.EncryptDESUtils;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.common.SqlExcuteByStr;
import com.ztesoft.crm.salesres.dao.DcDeviceGrpDAO;
import com.ztesoft.crm.salesres.dao.RcApplicationDAO;
import com.ztesoft.crm.salesres.dao.RcEntityDAO;
import com.ztesoft.crm.salesres.dao.RcEntityDAO2;
import com.ztesoft.crm.salesres.dao.RcLevelDefDAO;
import com.ztesoft.crm.salesres.dao.RcNoDAO;
import com.ztesoft.crm.salesres.dao.RcOrderDAO;
import com.ztesoft.crm.salesres.dao.RcOrderExcDAO;
import com.ztesoft.crm.salesres.dao.RcSimDAO;
import com.ztesoft.crm.salesres.dao.RcStockDAO;
import com.ztesoft.crm.salesres.dao.RescNumDAO;
import com.ztesoft.crm.salesres.dao.SalesRescDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.dao.impl.SqlComDAOImpl;
import com.ztesoft.crm.salesres.vo.RcApplicationVO;
import com.ztesoft.crm.salesres.vo.RcEntityVO;
import com.ztesoft.crm.salesres.vo.RcEntityVO2;
import com.ztesoft.crm.salesres.vo.RcLevelDefVO;
import com.ztesoft.crm.salesres.vo.RcOrderExcVO;
import com.ztesoft.crm.salesres.vo.RcOrderVO;
import com.ztesoft.crm.salesres.vo.RcStockVO;
import com.ztesoft.crm.salesres.vo.RescNumVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;
import com.ztesoft.oaas.dao.rrlan.RrLanDAO;

public class RcEntityBo extends DictAction{

//    public PageModel getRcEntityInfo(Map map, int pi, int ps) {
    public PageModel getRcEntityInfo( DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        int pi  = ((Integer)map.get("pageIndex")).intValue();
        int ps  = ((Integer)map.get("pageSize")).intValue();
        
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		String sType = "";
		String familyId = "";
		String sContent = "";
		String sRegionId = "";
		String status = "";
		String beginDate = "";
		String endDate = "";
		String lanId = "";
		String gjselect = "";
		String storageId = "";
		String usage = "";
		String provId = "";
		String operId = "";
		String departId = "";
		familyId=(String)map.get("familyId");
		if (map.get("sType") != null)
			sType = (String) map.get("sType");
		if (map.get("sContent") != null)
			sContent = (String) map.get("sContent");
		if (map.get("sRegionId") != null)
			sRegionId = (String) map.get("sRegionId");
		if (map.get("status") != null)
			status = (String) map.get("status");
		if (map.get("beginDate") != null)
			beginDate = (String) map.get("beginDate");
		if (map.get("endDate") != null)
			endDate = (String) map.get("endDate");
		if (map.get("lanId") != null)
			lanId = (String) map.get("lanId");
		if (map.get("gjselect") != null)
			gjselect = (String) map.get("gjselect");
		if (map.get("storageId") != null)
			storageId = (String) map.get("storageId");
		if (map.get("provId") != null)
			provId = (String) map.get("provId");
		if (map.get("usage") != null)
			usage = (String) map.get("usage");
		if(map.get("operId")!= null){
			operId = (String) map.get("operId");
		}
		if(map.get("departId")!=null){
			departId = (String) map.get("departId");
		}
		PageModel pm = new PageModel();
		String sql = " and b.rc_type=-1 ";
		if(familyId!=null&&familyId.trim().length()>0){
			sql += " and c.family_id = "+familyId; 
		}
		if (sContent == null) {
			sContent = "";
		}
		if (sType == null) {
			sType = "";
		}
		if (sRegionId == null) {
			sRegionId = "";
		}
		if (status == null) {
			status = "";
		}
		
		if (!("".equals(sContent))) {
			sContent = sContent.replaceAll(" ", "_");
			if ("code".equals(sType)) {
				if (sContent.indexOf(",")>0) {
					if (sContent.endsWith(",")) {
						sql += "  and a.RESOURCE_INSTANCE_CODE in ('" + sContent.substring(0, sContent.length()-1).replaceAll(",", "','")+"' )";
					} else {
						sql += "  and a.RESOURCE_INSTANCE_CODE in ('" + sContent.replaceAll(",", "','")+"')";
					}
				} else {
					sql += "  and a.RESOURCE_INSTANCE_CODE ='" + sContent+"'";
				}
			}  else if ("sname".equals(sType)) {
				sql += "  and c.sales_resource_name like '%" + sContent + "%' ";
			}
		}
		if (storageId != null && storageId.trim().length() > 0) {

			sql += " and a.STORAGE_ID=" + DAOUtils.filterQureyCond(storageId)
					+ " ";

		} else if (!"".equals(operId)&&!"".equals(departId)) {
			if (provId != null && (provId.equals(ParamsConsConfig.PROV_ID_GX) || provId.equals(ParamsConsConfig.PROV_ID_HN))) { // 广西不需要地区限制
				sql += " and exists (select 1  from Storage_Depart_Rela g where a.storage_id=g.storage_id "
					+" and g.depart_id="+departId;// + ")";
			} else if (!"".equals(sRegionId)){
				sql += " and exists (select 1  from mp_department e, Storage_Depart_Rela g ,ra_town j    where   e.depart_id = g.depart_id  and  e.town_id=j.town_id  and a.storage_id=g.storage_id and j.business_id ="
						+ sRegionId+" and g.depart_id="+departId;// + " )";
			}
			sql+=" union all select 1 from mp_storage m where m.storage_id=a.storage_id and m.oper_id="+operId+ " )";
			// sql +=" and a.storage_id in (select distinct g.storage_id from
			// mp_department e, Storage_Depart_Rela g where e.depart_id =
			// g.depart_id and e.region_id="+sRegionId+" )";
		}
		if (!"".equals(lanId)) {
			sql += "  and a.lan_id=" + lanId;
		}
		if (!"".equals(status)) {
			if (!"000".equals(status)) {
				sql += "  and a.curr_state='" + status + "'";
			}
		}
		if (!"".equals(usage)) {
			sql += " and a.usage='" + usage + "' ";
		}
		if ("INFORMIX".equals(databaseType)) {
			if (!"".equals(beginDate)) {
				sql += "  and a.EFF_DATE>=to_date('" + beginDate
						+ "','%Y-%m-%d')  ";
			}
			if (!"".equals(endDate)) {
				sql += "  and a.EXP_DATE<=to_date('" + endDate
						+ "','%Y-%m-%d')  ";
			}
			if (gjselect != null && !"".equals(gjselect)) {
				if ("true".equals(gjselect)) {
					sql += "  and a.EXP_DATE<to_date('"
							+ DateFormatUtils.getFormatedDate()
							+ "','%Y-%m-%d')  ";
				}
			}

		} else {
			if (!"".equals(beginDate)) {
				sql += "  and a.EFF_DATE>=to_date('" + beginDate
						+ "','yyyy-mm-dd')  ";
			}
			if (!"".equals(endDate)) {
				sql += "  and a.EXP_DATE<=to_date('" + endDate
						+ "','yyyy-mm-dd')  ";
			}
			if (gjselect != null && !"".equals(gjselect)) {
				if ("true".equals(gjselect)) {
					sql += "  and a.EXP_DATE<to_date('"
							+ DateFormatUtils.getFormatedDate()
							+ "','yyyy-mm-dd') ";
				}
			}

		}
		RcEntityDAO2 rdao = (RcEntityDAO2) SrDAOFactory.getInstance()
				.getRcEntityDAO2();
		String table =CommonUtilBo.getTableNameByFamilyId(familyId);
		
		table =("".equals(table))?"rc_entity2 ":table;//默认为实体表rc_entity
		
		if ("rc_entity2".equalsIgnoreCase(table)) {
		rdao.setSQL_SELECT("SELECT a.resource_instance_id,a.sales_resource_id,a.resource_instance_code,a.resource_level,a.resource_kind,a.lan_id,a.owner_type,a.owner_id,a.storage_id,a.curr_state,a.state,a.create_date,a.eff_date,a.exp_date,a.pk_calbody,a.cinventoryid,a.vbatchcode,b.storage_name,c.sales_resource_name ,a.e_pwd,a.manufacturer,a.provider,a.shopkeeper FROM "+table+" a, RC_STORAGE b,SALES_RESOURCE c where a.STORAGE_ID=b.STORAGE_ID and a.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID  ");
		rdao.setSQL_SELECT_COUNT("SELECT COUNT(a.resource_instance_id) AS COL_COUNTS FROM "+table+" a ,RC_STORAGE b,SALES_RESOURCE c where a.STORAGE_ID=b.STORAGE_ID and a.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID  ");
		}
		
		if (usage != null && !"".equals(usage)) {
			rdao.setUsageFlag("1");
			rdao.setSQL_SELECT("SELECT a.resource_instance_id,a.sales_resource_id,a.resource_instance_code,a.resource_level,a.resource_kind,a.lan_id,a.owner_type,a.owner_id,a.storage_id,a.curr_state,a.state,a.create_date,a.eff_date,a.exp_date,a.pk_calbody,a.cinventoryid,a.vbatchcode,b.storage_name,a.manufacturer,a.provider,a.shopkeeper,c.sales_resource_name,a.usage,c.manage_mode FROM " +
					table +" a, RC_STORAGE b,SALES_RESOURCE c,RC_FAMILY d where a.STORAGE_ID=b.STORAGE_ID and a.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID and c.FAMILY_ID=d.FAMILY_ID ");
		}
		pm = PageHelper.popupPageModel(rdao, sql, pi, ps);
		return pm;
	}

	/**
	 * 批量导入资源实体数据;
	 * 
	 * @param fileList
	 * @return
	 */
	public String[] addMultiRcEntity(List fileList) {
		int retResult = 0;
		List retList = new ArrayList();
		String msg = "";
		RcEntityDAO2 rdao =  SrDAOFactory.getInstance()
				.getRcEntityDAO2();
		RcEntityVO2 rvo = null;
		retList.add("Begin time:" + DateFormatUtils.getFormatedDateTime());
		String resourceInstanceId = "";
		String tableName="";
		try {
			if (fileList != null) {
				for (int k = 0; k < fileList.size(); k++) {
					String lineStr = (String) fileList.get(k);
					String[] line = lineStr.split(",");
						msg = "";
						boolean retv = rdao.checkSalesRescId(line[0].trim());
						if (retv) {
							boolean retv0 = rdao.checkStorageId(line[4].trim());
							if (retv0) {
								resourceInstanceId = SeqDAOFactory
										.getInstance().getSequenceManageDAO()
										.getNextSequence("RC_ENTITY",
												"RcEntity_ID");
								rvo = new RcEntityVO2();
								rvo.setRescInstanceId(resourceInstanceId);
								rvo.setSalesRescId(line[0]);
								boolean rs = rdao.checkManageMode(" sales_resource_id = "+line[0]+"");
								if(!rs){
									String checkManageMsg[] = {"包含有非实体管理的营销资源,不能导入!"};
									return checkManageMsg;
								}
								rvo.setRescInstanceCode(line[1]);
								rvo.setRescLevel("");
								rvo.setRescKind(line[2]);
								rvo.setLanId(line[3]);
								rvo.setOwnerType("5");
								rvo.setOwnerId(line[4]);
								rvo.setStorageId(line[4]);
								rvo.setCurrState(line[5]);
								rvo.setState(line[6]);
								rvo.setCreateDate(line[7]);
								rvo.setEffDate(line[8]);
								rvo.setExpDate(line[9]);
								rvo.setPkCalbody(line[10]);
								rvo.setCinventoryid(line[11]);
								rvo.setVbatchcode(line[12]);
								rvo.setEPwd(EncryptDESUtils.getInstance()
										.encrypt(line[14]));
								if (rvo.getEPwd().length() > 64) {
									String rtn[] = new String[] {
											"资源实例密码长度超过限制!", "0条数据成功导入" };
									return rtn;
								}

								try {
									if (rvo.getRescInstanceCode() == null
											|| "".equals(rvo
													.getRescInstanceCode())) {
										rvo.setRescInstanceCode(rvo
												.getRescInstanceId());
									}
									boolean rst = CommonUtilBo.checkRescInstanceCodeExist(line[0], line[4], line[1]);
									if (rst) {
										msg = "资源标识为"
												+ line[0]
												+ ",仓库标识为"
												+ line[4]
												+ "的记录，其资源实例编码已经在表中存在,不能保存此资源实例数据!";
									} else {
										tableName=  CommonUtilBo.getTableNameByResouceId(rvo.getSalesRescId());
										if (!"".equals(tableName)) {//在RC_FAMILY_ENTITY_RELA表有配置
											rdao.insert((VO) rvo);
										} else {
											rdao.insert2((VO) rvo);
										}
										retResult++;
										// 设置相应的库存统计(RESOURCE_NUM)
										rvo.setAddNumber("1");
										this.refreashAddRsNum(rvo);
									}
								} catch (Exception xx) {
									msg = "资源标识为" + line[0] + ",仓库标识为" + line[4]
											+ "的记录，保存时出错，在执行插入时报错,请查看后台日志文件";
									xx.printStackTrace();
								}
							} else {
								msg = "仓库标识为" + line[4]+ "的记录不存在.";
							}
						} else {
							msg = "营销资源标识为" + line[0] + ",仓库标识为" + line[4]
									+ "的记录，营销资源标识" + line[0] + "不存在.";
						}
						if (!"".equals(msg)) {
							retList.add(msg);
						}
					
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		retList.add("End time:" + DateFormatUtils.getFormatedDateTime());
		int lsize = 0;
		if (retList != null && retList.size() > 0) {
			lsize = retList.size();
		}
		String[] retMsgArr = new String[lsize + 1];
		for (int i = 0; i < lsize; i++) {
			retMsgArr[i] = (String) retList.get(i);
		}
		retMsgArr[lsize] = retResult + "条数据导入成功!";
		return retMsgArr;
	}

//	/**
//	 * 新增一条资源实体数据;
//	 * 
//	 * @param rvo
//	 * @return
//	 */
//	public int saveRcEntity(RcEntityVO rvo) {
//		int retResult = 0;
//		
//		com.ztesoft.crm.sr.dao.RcEntityDAO rcentityDAO = SrDAOFactory.getInstance().getRcEntityDAO();
//	    rcentityDAO.setFlag(2);
//	    //long lon  = rcentityDAO.countByCond(" sales_resource_id ="+rvo.getSalesRescId()+" and sales_resource_id in  (select sales_resource_id from sales_resource_id_rela) ");
//	    //if(lon>0)return 0;
//		RcEntityDAO rdao = (RcEntityDAO) SrDAOFactory.getInstance()
//				.getRcEntityDAO2();
//		String tableName = CommonUtilBo.getTableNameByResouceId(rvo.getSalesRescId());
//		String addNumber = rvo.getAddNumber();
//		/*if (addNumber == null || "".equals(addNumber)) {
//			addNumber = "1";
//		}*/
//		int totalNum = Integer.parseInt(addNumber);
//		for (int i = 0; i < totalNum; i++) {
//			String resourceInstanceId = SeqDAOFactory.getInstance()
//					.getSequenceManageDAO().getNextSequence("RC_ENTITY",
//							"RcEntity_ID");
//			rvo.setRescInstanceId(resourceInstanceId);
//			if (totalNum > 1) {
//				rvo.setRescInstanceCode(resourceInstanceId);
//			}
//			rvo.setOwnerType("5");
//			rvo.setOwnerId(rvo.getStorageId());
//
//			if (rvo.getEPwd() != null && !rvo.getEPwd().equals("")) {
//				String pwdStr = rvo.getEPwd();
//				String encStr = EncryptDESUtils.getInstance().encrypt(pwdStr);
//				rvo.setEPwd(encStr);
//			}
//			if ("rc_entity2".equals(tableName)) {
//				rdao.insert2((VO) rvo);
//			} else {
//				rdao.insert((VO) rvo);
//			}
//			retResult++;
//		}
//		addRcEntity(rvo);
//		// 更新对应的库存统计(RESOURCE_NUM)
//		this.refreashAddRsNum(rvo);
//		//System.out.println(retResult);
//		return retResult;
//
//	}

	/**
	 * 新增一条资源实体数据;
	 * 
	 * @param rvo
	 * @return
	 */
	public int saveRcEntity(DynamicDict dto) throws FrameException {
	  	Map map = (Map)dto.getValueByName("parameter") ;
	  	RcEntityVO2 rvo = (RcEntityVO2)map.get("vo");
		int retResult = 0;
		
		RcEntityDAO rcentityDAO = SrDAOFactory.getInstance().getRcEntityDAO();
	    rcentityDAO.setFlag(2);
	    //long lon  = rcentityDAO.countByCond(" sales_resource_id ="+rvo.getSalesRescId()+" and sales_resource_id in  (select sales_resource_id from sales_resource_id_rela) ");
	    //if(lon>0)return 0;
		RcEntityDAO2 rdao =  SrDAOFactory.getInstance().getRcEntityDAO2();
		SqlExcuteByStr G = new SqlExcuteByStr();
		String tableName = CommonUtilBo.getTableNameByResouceId(rvo.getSalesRescId());
		String addNumber = rvo.getAddNumber();
		/*if (addNumber == null || "".equals(addNumber)) {
			addNumber = "1";
		}*/
		int totalNum = Integer.parseInt(addNumber);
		for (int i = 0; i < totalNum; i++) {
			String resourceInstanceId = SeqDAOFactory.getInstance()
					.getSequenceManageDAO().getNextSequence("RC_ENTITY",
							"RcEntity_ID");
			rvo.setRescInstanceId(resourceInstanceId);
			if (totalNum > 1) {
				rvo.setRescInstanceCode(resourceInstanceId);
			}else{ // 检查编码是否重复
				String sqlStr = " select count(1) as result from "+tableName+" where RESOURCE_INSTANCE_CODE='"
						+ rvo.getRescInstanceCode() + "' and SALES_RESOURCE_ID = "+ rvo.getSalesRescId();

				// 查询号码是否重复
				String count = G.getString(sqlStr);
				if (Integer.parseInt(count, 10) > 0) {
					return -2;
				}
				// 设置前置中间后置编码
				setRescCodeInfo(rvo);
			}
			rvo.setOwnerType("5");
			rvo.setOwnerId(rvo.getStorageId());

			if (rvo.getEPwd() != null && !rvo.getEPwd().equals("")) {
				String pwdStr = rvo.getEPwd();
				String encStr = EncryptDESUtils.getInstance().encrypt(pwdStr);
				rvo.setEPwd(encStr);
			}
			if ("rc_entity2".equals(tableName)) {
				rdao.insert2((VO) rvo);
			} else {
				rdao.insert((VO) rvo);
			}
			retResult++;
		}
		//addRcEntity(rvo);
		// 更新对应的库存统计(RESOURCE_NUM)
		this.refreashAddRsNum(rvo);
		//System.out.println(retResult);
		return retResult;
	}
	
	/**
	 * 插入一条资源实体数据;
	 * 
	 * @param rvo
	 * @return 包含主键的实体VO
	 */
	public RcEntityVO2 insertRcEntity(RcEntityVO2 rvo,String tableName) {
		RcEntityDAO2 rdao = SrDAOFactory.getInstance()
				.getRcEntityDAO2();
		rvo.setAddNumber("1");
		String resourceInstanceId = SeqDAOFactory.getInstance()
				.getSequenceManageDAO().getNextSequence("RC_ENTITY",
						"RcEntity_ID");
		rvo.setRescInstanceId(resourceInstanceId);
		if (rvo.getRescInstanceCode() == null
				|| rvo.getRescInstanceCode().trim().length() < 1) {
			rvo.setRescInstanceCode(resourceInstanceId);
		}
		rvo.setOwnerType("5");
		rvo.setOwnerId(rvo.getStorageId());
		if (rvo.getCreateDate() == null
				|| rvo.getCreateDate().trim().length() < 1)
			rvo.setCreateDate(DateFormatUtils.getFormatedDateTime());
		if ("rc_entity2".equals(tableName)) {
			rdao.insert2((VO) rvo);
		}else{
			rdao.insert((VO) rvo);
		}
		// 更新对应的库存统计(RESOURCE_NUM)
		// this.refreashAddRsNum(rvo);
		return rvo;
	}

	/**
	 * 修改资源实体.
	 * 
	 * @param rvo
	 * @return
	 */
//	public int updateRcEntity(RcEntityVO2 rvo) {
	public int updateRcEntity(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        RcEntityVO2 rvo  = (RcEntityVO2)map.get("vo");
	    
		int retResult = 0;
	    RcEntityDAO rcentityDAO = SrDAOFactory.getInstance().getRcEntityDAO();
	    rcentityDAO.setFlag(2);
	    //long lon  = rcentityDAO.countByCond(" resource_instance_id ="+rvo.getRescInstanceId()+" and sales_resource_id in  (select sales_resource_id from sales_resource_id_rela) ");
	    //if(lon>0)return 0;
		String tableName = CommonUtilBo.getTableNameByResouceId(rvo.getSalesRescId());
		RcEntityDAO2 rdao =  SrDAOFactory.getInstance()
				.getRcEntityDAO2();
		//try {

			this.refreashModifyRsNum(rvo);
			String whereCond = "  resource_instance_id="
					+ rvo.getRescInstanceId();
			if (rvo.getEPwd() != null && !rvo.getEPwd().equals("")) {
				String pwdStr = rvo.getEPwd();
				rvo.setEPwd(EncryptDESUtils.getInstance().encrypt(pwdStr));
			}
			// 设置实例编码的前缀中间码和结尾码
			setRescCodeInfo(rvo);
			if ("rc_entity2".equals(tableName)) {
				rdao.update2(whereCond, (VO) rvo);
			} else {
				rdao.update(whereCond, (VO) rvo);
			}
			retResult = 1;
		//} catch (Exception ex) {
		//	ex.printStackTrace();
		//}
		return retResult;
	}

	/**
	 * 删除一条资源实体数据;
	 * 
	 * @param resourceInstanceId
	 * @return
	 */
//	public boolean deleteRcEntity(String resourceInstanceId,String salesRescId) {
	public boolean deleteRcEntity(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        String resourceInstanceId  = (String)map.get("resourceInstanceId");
        String salesRescId  = (String)map.get("salesRescId");
        
		boolean retResult = false;
		RcEntityDAO rcentityDAO = SrDAOFactory.getInstance().getRcEntityDAO();
	    rcentityDAO.setFlag(2);
	    //long lon  = rcentityDAO.countByCond(" resource_Instance_Id ="+resourceInstanceId+" and sales_resource_id in  (select sales_resource_id from sales_resource_id_rela) ");
	    //if(lon>0)return false;
	    String tableName = CommonUtilBo.getTableNameByResouceId(salesRescId);
		RcEntityDAO2 rdao =  SrDAOFactory.getInstance()
				.getRcEntityDAO2();
		// 删除相关表库存统计(RESOURCE_NUM)数目
		this.refreashDelRsNum(resourceInstanceId);
		// 删除实例属性
		rdao.deleteAttrInfoAll(resourceInstanceId);
		// 删除实例本身
		String whereCond = "  resource_instance_id=" + resourceInstanceId;
		long count =0;
		if ("rc_entity2".equals(tableName)) {
			count=rdao.deleteByCond2(whereCond);
		} else {
			count = rdao.deleteByCond(whereCond);
		}
		if (count>0) {
			retResult = true;
		}
		return retResult;
	}

	/**
	 * 取得所有的本地网数据;
	 * 
	 * @return
	 */
	public List getRegionInfo() {
		List list = new ArrayList();
		RrLanDAO rrdao = (RrLanDAO) SrDAOFactory.getInstance().getRrLanDAO();
		try {
			String whereCond = "  2=2 ";
			list = rrdao.findByCond(whereCond);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;

	}

	/**
	 * 新增营销资源实例时更新相应的RESOURCE_NUM中的记录
	 * 
	 * @param vo
	 *            RcEntityVO
	 * @return boolean
	 */
	public boolean refreashAddRsNum(RcEntityVO2 vo) {
		if (vo == null || vo.getCinventoryid() == null || vo.getLanId() == null
				|| vo.getPkCalbody() == null || vo.getSalesRescId() == null
				|| vo.getStorageId() == null || vo.getVbatchcode() == null) {
			return false;
		}
		boolean result = true;
		String addNum = "1";
		if (vo.getAddNumber() != null && vo.getAddNumber().trim().length() > 0) {
			addNum = vo.getAddNumber();
		}
		RescNumDAO dao1 = SrDAOFactory.getInstance().getRescNumDAO();
		RescNumVO numvo = dao1.findByPrimaryKey(vo.getCinventoryid(), vo
				.getLanId(), vo.getPkCalbody(), vo.getSalesRescId(), vo
				.getStorageId(), vo.getVbatchcode());
		try {
			if (numvo == null) {
				numvo = new RescNumVO();
				numvo.setSalesRescId(vo.getSalesRescId());
				numvo.setStorageId(vo.getStorageId());
				numvo.setLanId(vo.getLanId());
				numvo.setPkCalbody(vo.getPkCalbody());
				numvo.setCinventoryid(vo.getCinventoryid());
				numvo.setVbatchcode(vo.getVbatchcode());
				numvo.setNum(addNum);
				// 写入资源家族标识
				SalesRescDAO dao2 = SrDAOFactory.getInstance()
						.getSalesRescDAO();
				SalesRescVO voTemp = dao2.findByPrimaryKey(vo.getSalesRescId());
				if (voTemp != null) {
					numvo.setFamilyId(voTemp.getFamilyId());
				}
				dao1.insert(numvo);
			} else {
				int num = Integer.parseInt(addNum);
				if (numvo.getNum() != null
						&& numvo.getNum().trim().length() > 0) {
					num += Integer.parseInt(numvo.getNum());
				}
				numvo.setNum(String.valueOf(num));
				dao1.update(numvo.getCinventoryid(), numvo.getLanId(), numvo
						.getPkCalbody(), numvo.getSalesRescId(), numvo
						.getStorageId(), numvo.getVbatchcode(), numvo);
			}
		} catch (NumberFormatException ex) {
			result = false;
		} catch (DAOSystemException ex) {
			result = false;
			throw ex;
		}
		return result;
	}

	/**
	 * 删除营销资源实例时更新相应的RESOURCE_NUM中的记录,当更新该营销资源实例时也可调用该方法将老的
	 * 
	 * @param vo
	 *            RcEntityVO
	 * @return boolean
	 */
	public boolean refreashDelRsNum(String resourceInstanceId) {
		boolean result = true;
		try {
			RcEntityDAO dao1 = SrDAOFactory
					.getInstance().getRcEntityDAO();
			RescNumDAO dao2 = SrDAOFactory.getInstance().getRescNumDAO();
			RcEntityVO vo = dao1
					.findByPrimaryKey(resourceInstanceId);
			if (vo == null) {
				return false;
			}
			RescNumVO numvo = dao2.findByPrimaryKey(vo.getCinventoryid(), vo
					.getLanId(), vo.getPkCalbody(), vo.getSalesRescId(), vo
					.getStorageId(), vo.getVbatchcode());
			if (numvo == null) {
				return true;
			}
			int num = 0;
			if (numvo.getNum() == null || numvo.getNum().trim().length() <= 0) {
				num = 0;
			} else {
				num = Integer.parseInt(numvo.getNum());
			}
			num--;
			if (num <= 0) {
				dao2.delete(numvo.getCinventoryid(), numvo.getLanId(), numvo
						.getPkCalbody(), numvo.getSalesRescId(), numvo
						.getStorageId(), numvo.getVbatchcode());
			} else {
				numvo.setNum(String.valueOf(num));
				dao2.update(numvo.getCinventoryid(), numvo.getLanId(), numvo
						.getPkCalbody(), numvo.getSalesRescId(), numvo
						.getStorageId(), numvo.getVbatchcode(), numvo);
			}
		} catch (NumberFormatException ex) {
			result = false;
		} catch (DAOSystemException ex) {
			result = false;
			throw ex;
		}
		return result;
	}

	/**
	 * 修改资源实体时修改RESOURCE_NUM的操作
	 * 
	 * @param vo
	 *            RcEntityVO
	 * @return boolean
	 */
	public boolean refreashModifyRsNum(RcEntityVO2 vo) {
		if (vo == null) {
			return false;
		}
		boolean result = true;
		try {
			this.refreashDelRsNum(vo.getRescInstanceId());
			this.refreashAddRsNum(vo);
		} catch (Exception ex) {
			result = false;
			throw new DAOSystemException(ex);
		}
		return result;
	}

	/**
	 * 查询家族资源的属性。
	 * 
	 * @param rescInstanceId
	 * @return
	 */
	public List qrySrAttrInfo(String rescInstanceId) {
		List list = new ArrayList();
		try {
			if (rescInstanceId == null) {
				rescInstanceId = "";
			} else {
				RcEntityDAO2 pdao = SrDAOFactory.getInstance().getRcEntityDAO2();
				list = pdao.searchAttrInfo(rescInstanceId,"");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;

	}

	/**
	 * 保存实体资源的属性信息。
	 * 
	 * @param rescInstanceId,attrId,attrValueId
	 * @return
	 */
	public String insertRcEnAttrInfo(String rescInstanceId, String attrId,
			String attrValue) {
		String flag = "";
		boolean bFlag = false;
		try {
			RcEntityDAO2 pdao = SrDAOFactory.getInstance().getRcEntityDAO2();
			long lCount = pdao.countAttrInfo(rescInstanceId, attrId);
			if (lCount != 0) {
				flag = "1";
				return flag;
			}
			bFlag = pdao.insertRcEnAttrInfo(rescInstanceId, attrId, attrValue);
			if (bFlag) {
				flag = "2";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return flag;

	}

	/**
	 * 删除一条家族资源的属性。
	 * 
	 * @param rcFamilyId,attrValue
	 * @return
	 */
	public int deleteAttrInfo(String rescInstanceId, String attrValue) {
		int retResult = 0;
		try {
			RcEntityDAO2 pdao = SrDAOFactory.getInstance().getRcEntityDAO2();

			if (attrValue == null || "".equals(attrValue)
					|| rescInstanceId == null || "".equals(rescInstanceId)) {
				return retResult;
			} else {
				retResult = pdao.deleteAttrInfo(rescInstanceId, attrValue);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retResult;
	}

	/**
	 * 查询物资实例列表,根据营销资源不同，会查询不同的表
	 * 
	 * @param map:查询参数列表
	 * @param pi
	 * @param ps
	 * @return
	 */
	public PageModel qryRcEntityPage(Map map, int pi, int ps) {
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		String salesRescId = null;
		String storageId = null;
		String state = null;
		String rescInstanceCode = null;
		String provId = null;
		if (map.get("salesRescId") != null
				&& ((String) map.get("salesRescId")).trim().length() > 0) {
			salesRescId = (String) map.get("salesRescId");
			salesRescId = DAOUtils.filterQureyCond(salesRescId);
		}
		if (map.get("storageId") != null
				&& ((String) map.get("storageId")).trim().length() > 0) {
			storageId = (String) map.get("storageId");
			storageId = DAOUtils.filterQureyCond(storageId);
		}
		if (map.get("state") != null
				&& ((String) map.get("state")).trim().length() > 0) {
			state = (String) map.get("state");
			state = DAOUtils.filterQureyCond(state);
		}
		if (map.get("rescInstanceCode") != null
				&& ((String) map.get("rescInstanceCode")).trim().length() > 0) {
			rescInstanceCode = (String) map.get("rescInstanceCode");
			rescInstanceCode = DAOUtils.filterQureyCond(rescInstanceCode);
		}
		if (map.get("provId") != null
				&& ((String) map.get("provId")).trim().length() > 0)
			provId = (String) map.get("provId");

		// 查询要查询的表的名称
		String tableName = "rc_entity";

		if (salesRescId != null && salesRescId.trim().length() > 0) {
			//SrOrderBo bo = new SrOrderBo();
			tableName = CommonUtilBo.getTableNameByResouceId(salesRescId);
		}

		PageModel pm = new PageModel();
		String sql = " ";
		String flag = "a.";

		if ("rc_sim".equalsIgnoreCase(tableName))
			flag = "R.";
		else if ("rc_no".equalsIgnoreCase(tableName))
			flag = "";

		if (salesRescId != null && salesRescId.trim().length() > 0) {
			sql += " and " + flag + "sales_resource_id=" + salesRescId;
		}
		if (storageId != null && storageId.trim().length() > 0) {

			sql += " and " + flag + "STORAGE_ID=" + storageId;

		}
		if (state != null && state.trim().length() > 0) {
			sql += "  and " + flag + "state='" + state + "'";
		}
		if (rescInstanceCode != null && rescInstanceCode.trim().length() > 0) {
			sql += " and " + flag + "resource_instance_code like '%"
					+ rescInstanceCode + "%' ";
		}

		// 根据表名不同从而去使用不同的dao查询分页信息
		if ("rc_sim".equalsIgnoreCase(tableName)) {
			RcSimDAO rdao = (RcSimDAO) SrNSDAOFactory.getInstance()
					.getRcSimDAO();
			pm = PageHelper.popupPageModel(rdao, sql, pi, ps);
		} else if ("rc_no".equalsIgnoreCase(tableName)) {
			RcNoDAO rdao = (RcNoDAO) SrNSDAOFactory.getInstance().getRcNoDAO();
			rdao.setFlag(1);
			pm = PageHelper.popupPageModel(rdao, " 1=1 " + sql, pi, ps);
		} else {
			RcEntityDAO2 rdao = SrDAOFactory.getInstance()
					.getRcEntityDAO2();
			rdao.setTableName(tableName);
			pm = PageHelper.popupPageModel(rdao, sql, pi, ps);
		}

		return pm;
	}

	/**
	 * 简易导入资源实体数据;
	 * 
	 * @param paraMap
	 * @return
	 */
	public Map addMultiRcEntityEasy(Map paraMap) {
		if (paraMap == null)
			return null;
		Map map = new HashMap();
		RcEntityVO2 vo = (RcEntityVO2) paraMap.get("RcEntityVO");
		List list = (List) paraMap.get("InputList");
		String operId = null;
		String departId = null;
		String appId = "";
		if (paraMap.get("operId") != null) {
			operId = (String) paraMap.get("operId");
		}
		if (paraMap.get("departId") != null) {
			departId = (String) paraMap.get("departId");
		}
		RcEntityDAO rcentityDAO = SrDAOFactory.getInstance().getRcEntityDAO();
	    rcentityDAO.setFlag(2);
	    //long lon  = rcentityDAO.countByCond(" sales_resource_id ="+vo.getSalesRescId()+" and sales_resource_id in  (select sales_resource_id from sales_resource_id_rela) ");
		int successCount = 0;
		int failCount = 0;
		String messCode1 = "处理失败，系统异常，缺少参数，或者字符串太长";
		String messCode2 = "处理失败，该物资编码已经存在";
		List failList = new ArrayList();
		if (vo == null || list == null || list.size() < 1 ) {
			map.put("result", "0");
			map.put("successCount", String.valueOf(successCount));
			map.put("failCount", String.valueOf(list==null?0:list.size()));
			map.put("orderId", "");
			return map;
		}

		RcEntityVO2 insertVO = null;
		String rtn = null;
		SqlComDAO comDao = new SqlComDAOImpl(true);

		// 插入rc_order_list的sql
		String sql_orderlist = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE ) values ";
		String orderId = SeqDAOFactory.getInstance().getSequenceManageDAO()
				.getNextSequence("rc_order", "order_id");

		String resourceInstanceId = "";
		for (int i = 0; i < list.size(); i++) {
			insertVO = (RcEntityVO2) list.get(i);
			if (insertVO != null) {
				if (insertVO.getRescInstanceCode() == null
						|| insertVO.getRescInstanceCode().trim().length() < 1) {
					failCount++;
					continue;
				} else {
					// 设置实例VO
					insertVO.setRescInstanceId(resourceInstanceId);
					insertVO.setSalesRescId(vo.getSalesRescId());
					insertVO.setEffDate(vo.getEffDate());
					insertVO.setExpDate(vo.getExpDate());
					insertVO.setStorageId(vo.getStorageId());
					insertVO.setState(vo.getState());
					insertVO.setCurrState(vo.getCurrState());
					insertVO.setOwnerType("0");
					insertVO.setOwnerId("0");
					insertVO.setManufacturer(vo.getManufacturer());
					insertVO.setProvider(vo.getProvider());
					insertVO.setShopkeeper(vo.getShopkeeper());
					insertVO.setLanId(vo.getLanId());
					insertVO.setCreateDate(DateFormatUtils
							.getFormatedDateTime());
					
					long rtnLong = this.addRcEntity(insertVO);
					rtn = String.valueOf(rtnLong);
					if (rtn == null || "".equals(rtn) || "-2".equals(rtn)
							|| "-1".equals(rtn)) {
						if(rtn==null||"".equals(rtn)|| "-1".equals(rtn)){
							failList.add(insertVO.getRescInstanceCode()+" --- "+messCode1);
						}else{
							failList.add(insertVO.getRescInstanceCode()+" --- "+messCode2);
						}
						failCount++;
					} else {
						successCount++;
						// 新增成功要把号码预插入rc_order_list中
						comDao.addBatchSql(sql_orderlist + " (" + orderId + ","
								+ rtn + "," + insertVO.getSalesRescId() + ",'"
								+ insertVO.getRescInstanceCode() + "')");
					}
				}
			}
		}
		if (successCount > 0) {
			// 插入rc_order中订单信息
			appId = this.insertOrderInfoNo("C", insertVO.getSalesRescId(),
					operId, departId, vo.getStorageId(), String
							.valueOf(successCount), null, null, null, orderId,
					"in");
			comDao.batchExecute(null); // 插入rc_order_list表中的内容
		}
		comDao.closeConnection();
		map.put("result", "");
		map.put("successCount", String.valueOf(successCount));
		map.put("failCount", String.valueOf(failCount));
		map.put("orderId", orderId);
		map.put("appId", appId);
		map.put("failList", failList);
		return map;
	}

	/**
	 * 资源新增时需要生成订单
	 * 
	 * @param appType
	 *            String
	 * @param salesRescId
	 *            String
	 * @param operId
	 *            String
	 * @param departId
	 *            String
	 * @param inStorageId
	 *            String
	 * @param appAmount
	 *            String
	 * @param resBCode
	 *            String
	 * @param resECode
	 *            String
	 * @return boolean
	 */
	private String insertOrderInfoNo(String appType, String salesRescId,
			String operId, String departId, String storageId, String appAmount,
			String resBCode, String resECode, String noSegId, String orderId,
			String isIn) {
		if (appType == null || appType.trim().length() < 1 || storageId == null
				|| storageId.trim().length() < 1)
			return "-1";
		RcOrderDAO orderDao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		RcApplicationDAO appDao = (RcApplicationDAO) SrDAOFactory.getInstance()
				.getRcApplicationDAO();
		RcOrderExcDAO execDao = (RcOrderExcDAO) SrDAOFactory.getInstance()
				.getRcOrderExcDAO();
		SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance()
				.getSequenceManageDAO();
		String appId = sequenceManageDAO.getNextSequence("rc_application",
				"app_id");
		if (orderId == null || orderId.trim().length() < 1)
			orderId = sequenceManageDAO.getNextSequence("rc_order", "order_id");
		String date = DateFormatUtils.getFormatedDateTime();

		RcApplicationVO appVO = new RcApplicationVO();
		RcOrderVO rcOrderVO = new RcOrderVO();
		RcOrderExcVO rcOrderExcVO = new RcOrderExcVO();

		// 装载rc_application
		appVO.setAppId(appId);
		appVO.setAppTime(date);
		appVO.setAppType(appType);
		appVO.setDepartId(departId);
		appVO.setOperId(operId);
		// 装载rc_order
		rcOrderVO.setOrderId(orderId);
		rcOrderVO.setAppId(appId);
		rcOrderVO.setAppType(appType);
		rcOrderVO.setOperId(operId);
		rcOrderVO.setDepartId(departId);
		rcOrderVO.setTacheId("5");
		rcOrderVO.setStateId("n");
		rcOrderVO.setAppTime(date);
		rcOrderVO.setTacheTime(date);
		rcOrderVO.setEndTime(date);
		if ("in".equals(isIn)) {
			rcOrderVO.setInStorageId(storageId);
		} else {
			rcOrderVO.setOutStorageId(storageId);
		}
		rcOrderVO.setAppStorageId(storageId);
		rcOrderVO.setSalesRescId(salesRescId);
		rcOrderVO.setAppAmount(appAmount);
		rcOrderVO.setActAmount(appAmount);
		rcOrderVO.setResBCode(resBCode);
		rcOrderVO.setResECode(resECode);
		// 装载rc_order_exc
		String logId = sequenceManageDAO.getNextSequence("rc_order_exc",
				"log_id");
		rcOrderExcVO.setLogId(logId);
		rcOrderExcVO.setOrderId(rcOrderVO.getOrderId());
		rcOrderExcVO.setTacheId("5");
		rcOrderExcVO.setExcTime(rcOrderVO.getTacheTime());
		rcOrderExcVO.setDepartId(departId);
		rcOrderExcVO.setOperId(operId);
		rcOrderExcVO.setStateId(rcOrderVO.getStateId());

		appDao.insert(appVO);
		orderDao.insert(rcOrderVO);
		execDao.insert(rcOrderExcVO);
		return appId;
	}

	/**
	 * 增加一个实例，返回新增实例ID；
	 * 
	 * @param vo
	 *            RcNoVO
	 * @return int:-1缺少参数，-2:实例已存在错误，其余pk
	 */
	public long addRcEntity(RcEntityVO2 vo) {
		long rtn = 1;
		String count = "";
		String sqlStr = "";
		String tableName ="rc_entity";
		
		if (vo != null&&vo.getSalesRescId()!=null&&vo.getRescInstanceCode()!=null
			&&vo.getSalesRescId().trim().length()>0&&vo.getRescInstanceCode().trim().length()>0) {
			tableName=  CommonUtilBo.getTableNameByResouceId(vo.getSalesRescId());
			SqlExcuteByStr G = new SqlExcuteByStr();
			sqlStr = " select count(1) as result from "+tableName+" where RESOURCE_INSTANCE_CODE='"
					+ vo.getRescInstanceCode()
					+ "' and SALES_RESOURCE_ID = "
					+ vo.getSalesRescId();

			// 查询号码是否重复
			count = G.getString(sqlStr);
			if (Integer.parseInt(count, 10) > 0) {
				rtn = -2;
			} else {
				// 设置实例级别
				vo.setRescLevel(this.setEntityLevel(vo.getSalesRescId(), null));
				// 插入实例ID
				String pk = SeqDAOFactory.getInstance().getSequenceManageDAO()
						.getNextSequence("RC_ENTITY", "RcEntity_ID");
				vo.setRescInstanceId(pk);
				
				// 设置实例编码的前缀中间码和结尾码
				setRescCodeInfo(vo);
				
				RcEntityDAO2 rdao =  SrDAOFactory.getInstance()
						.getRcEntityDAO2();
				
				if ("rc_entity2".equalsIgnoreCase(tableName)) {
					rdao.insert2(vo);
				}else{
					rdao.insert(vo);
				}
				rtn = Long.parseLong(pk);
			}
		} else {
			rtn = -1;
		}
		return rtn;
	}

	/**
	 * 根据传入的级别集合确定传入实例的级别,此处要保证传入的levelList中的等级是按优先级由小到大顺序排列的(important)
	 * 
	 * @param vo
	 *            RcEntityVO
	 * @param levelList
	 *            List
	 * @return RcEntityVO
	 */
	public String setEntityLevel(String code, List levelList) {
		if (code == null) {
			return "";
		}
		SqlExcuteByStr G = new SqlExcuteByStr();
		String sqlStr = " select family_id  as result from SALES_RESOURCE where SALES_RESOURCE_ID = "
				+ code;
		if (levelList == null || levelList.size() < 1) {
			String familyId = G.getString(sqlStr);
			RcLevelDefDAO levelDao = SrDAOFactory.getInstance()
					.getRcLevelDefDAO();
			if (familyId != null && familyId.trim().length() > 0) {
				levelList = levelDao.findByCond(" family_id=" + familyId, null);
			}
		}
		if (levelList == null || levelList.size() < 1) {
			return "";
		}
		RcLevelDefVO levelVO = null;
		// int prior = Integer.MAX_VALUE;
		String levelId = "";
		String rule = "";
		for (int i = 0; i < levelList.size(); i++) {
			levelVO = (RcLevelDefVO) levelList.get(i);
			if (levelVO != null && levelVO.getRuleString() != null) {
				rule = levelVO.getRuleString();
				if (code.matches(rule)) {
					levelId = levelVO.getRcLevelId();
					break;
				}
			}
		}
		return levelId;
	}

	public String delInputRcEntity(String orderId, String appId) {
		String sqlStr = "";
		SqlComDAO G = SrDAOFactory.getInstance().getSqlComDAO();
		if ("".equals(orderId) || orderId == null || "".equals(appId)
				|| appId == null) {
			return "0";
		}
		sqlStr = " delete from rc_entity where RESOURCE_INSTANCE_ID in ( select RESOURCE_INSTANCE_ID from rc_order_list where order_id = "
				+ orderId + " )";
		G.excute(sqlStr);
		sqlStr = " delete from rc_order_list where order_id = " + orderId;
		G.excute(sqlStr);
		sqlStr = " delete from RC_ORDER_EXC where order_id = " + orderId;
		G.excute(sqlStr);
		sqlStr = " delete from rc_order where order_id = " + orderId;
		G.excute(sqlStr);
		sqlStr = " delete from RC_APPLICATION where app_id = " + appId;
		G.excute(sqlStr);
		return "1";
	}

	public Map addMultiRcEntityAttr(List list) {
		if (list.size() == 0) {
			return null;
		}
		Map map = new HashMap();
		int sCount = 0;
		int fCount = 0;
		String recordCount = "";
		String rsInstanceId = "";
		String rsInstanceCd = "";
		String attrId = "";
		String attrValue = "";
		String sqlStr = "";
		String insertSql = "";
		SqlExcuteByStr G = new SqlExcuteByStr();
		SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
		for (int i = 0; i < list.size(); i++) {
			String[] str = (String[]) list.get(i);
			if (str.length != 3) {
				fCount++;
				continue;
			}
			if (!"".equals(str[0]) && str[0] != null) {
				rsInstanceCd = str[0];
				sqlStr = " select RESOURCE_INSTANCE_ID  as result from RC_ENTITY where RESOURCE_INSTANCE_CODE='"
						+ rsInstanceCd + "' union select RESOURCE_INSTANCE_ID  as result from RC_ENTITY2 where RESOURCE_INSTANCE_CODE='"
						+ rsInstanceCd + "'";
				rsInstanceId = G.getString(sqlStr);
				if ("".equals(rsInstanceId) || rsInstanceId == null) {
					fCount++;
					continue;
				}
			} else {
				fCount++;
				continue;
			}
			if (!"".equals(str[1]) && str[1] != null) {
				attrId = str[1];
				if (!isNumeric(attrId)) {
					fCount++;
					continue;
				}
			} else {
				fCount++;
				continue;
			}

			if (!"".equals(str[2]) && str[2] != null) {
				attrValue = str[2];
			} else {
				fCount++;
				continue;
			}

			sqlStr = " select count(1) as result from rc_entity_attr where ENTITY_ID = "
					+ rsInstanceId + " and attr_id = " + attrId;
			recordCount = G.getString(sqlStr);
			if (Integer.parseInt(recordCount) > 0) {
				fCount++;
				continue;
			}
			insertSql = " insert into RC_ENTITY_ATTR values(" + rsInstanceId
					+ "," + attrId + ",'" + attrValue + "')";
			dao.excute(insertSql);
			sCount++;
		}
		map.put("sCount", String.valueOf(sCount));
		map.put("fCount", String.valueOf(fCount));
		return map;
	}

	// 判断是否为数字
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public PageModel getQuantityInfo(String salesRescId, String storageId,
			int pi, int ps) {
		RcStockDAO sr = SrDAOFactory.getInstance().getRcStockDAO();
		String sql = "";
		if (!"".equals(salesRescId)) {
			sql += " and a.sales_resource_id =" + salesRescId;
		}
		if (!"".equals(storageId)) {
			sql += " and a.storage_id =" + storageId;
		}
		sr.setFlag(5);
		return PageHelper.popupPageModel(sr, sql, pi, ps);
	}
	
	//查询终端分组信息
	public PageModel qryDeviceGroup(String qryType, String qryValue,
			int pi, int ps) {
		DcDeviceGrpDAO dao = SrDAOFactory.getInstance().getDcDeviceGrpDAO();
		String sql = " 1=1 ";
		if ("1".equals(qryType)) {
			sql += " and Dc_device_scode like'" + qryValue+"%'";
		}		
		else if ("2".equals(qryType)) {
			sql += " and Group_code like'" + qryValue+"%'";
		}		
		else if ("3".equals(qryType)) {
			sql += " and Group_name like'" + qryValue+"%'";
		}
//		sr.setFlag(5);
		return PageHelper.popupPageModel(dao, sql, pi, ps);
	}
	/**
	 * 
	 * @param action
	 * @param storageId
	 * @param salesRescId
	 * @param stockAmount
	 * @param operId
	 * @param departId
	 * @return 0 出错 1 正常 2 出库仓库的存量资源不够,不能做出库处理
	 */
	public String dealQuantityStock(String action, String storageId,
			String salesRescId, String stockAmount, String operId,
			String departId) {
		RcStockDAO sr = SrDAOFactory.getInstance().getRcStockDAO();
		RcStockVO vo = sr.findByPrimaryKey(salesRescId, storageId);
		if ("in".equals(action)) {// 入库采购
			if (vo != null) {
				int total = Integer.parseInt(stockAmount)
						+ Integer.parseInt(vo.getStockAmount());
				vo.setStockAmount(String.valueOf(total));
				sr.update(salesRescId, storageId, vo);

			} else {
				vo = new RcStockVO();
				vo.setSalesRescId(salesRescId);
				vo.setStorageId(storageId);
				vo.setStockAmount(stockAmount);
				sr.insert(vo);
			}
			// //写rc_application、rc_order、rc_order_exc ，订单的类型为“入库”，环节为“结束”
			insertOrderInfoNo("C", salesRescId, operId, departId, storageId,
					stockAmount, "0", "0", null, null, "in");
			return "1";

		} else {
			if (vo != null) {
				int tmp1 = Integer.parseInt(stockAmount);
				int tmp2 = Integer.parseInt(vo.getStockAmount());
				if (tmp1 > tmp2) {
					return "2";// 出库仓库的存量资源不够,不能做出库处理
				} else {
					int total = tmp2 - tmp1;
					vo.setStockAmount(String.valueOf(total));
					sr.update(salesRescId, storageId, vo);
					// 写rc_application、rc_order、rc_order_exc ，订单的类型为“退库”，环节为“结束”
					insertOrderInfoNo("T", salesRescId, operId, departId,
							storageId, stockAmount, "0", "0", null, null, "out");
					return "1";
				}
			} else {
				return "0";
			}
		}
	}

	public PageModel getRecycleEntityInfo(Map map, int pi, int ps) {
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		String sType = "";
		String sContent = "";
		String sRegionId = "";
		String status = "";
		String beginDate = "";
		String endDate = "";
		String lanId = "";
		String gjselect = "";
		String storageId = "";
		String usage = "";
		String provId = "";
		if (map.get("sType") != null)
			sType = (String) map.get("sType");
		if (map.get("sContent") != null)
			sContent = (String) map.get("sContent");
		if (map.get("sRegionId") != null)
			sRegionId = (String) map.get("sRegionId");
		if (map.get("status") != null)
			status = (String) map.get("status");
		if (map.get("beginDate") != null)
			beginDate = (String) map.get("beginDate");
		if (map.get("endDate") != null)
			endDate = (String) map.get("endDate");
		if (map.get("lanId") != null)
			lanId = (String) map.get("lanId");
		if (map.get("gjselect") != null)
			gjselect = (String) map.get("gjselect");
		if (map.get("storageId") != null)
			storageId = (String) map.get("storageId");
		if (map.get("provId") != null)
			provId = (String) map.get("provId");
		if (map.get("usage") != null)
			usage = (String) map.get("usage");
		PageModel pm = new PageModel();
		String sql = " ";
		if (sContent == null) {
			sContent = "";
		}
		if (sType == null) {
			sType = "";
		}
		if (sRegionId == null) {
			sRegionId = "";
		}
		if (status == null) {
			status = "";
		}
		if (!("".equals(sContent))) {
			if ("code".equals(sType)) {
				sql += "  and a.RESOURCE_INSTANCE_CODE like '%" + sContent
						+ "%' ";
			} else if ("fname".equals(sType)) {
				sql += "  and d.family_name like '%" + sContent + "%' ";
			} else if ("sname".equals(sType)) {
				sql += "  and c.sales_resource_name like '%" + sContent + "%' ";
			}
		}
		if (storageId != null && storageId.trim().length() > 0) {

			sql += " and a.STORAGE_ID=" + DAOUtils.filterQureyCond(storageId)
					+ " ";

		} else if (!"".equals(sRegionId)) {
			// sql += " and a.lan_id="+sRegionId;
			if (provId != null && provId.equals(ParamsConsConfig.PROV_ID_GX)) {
				sql += " and a.storage_id in (select g.storage_id  from mp_department e, Storage_Depart_Rela g ,rr_region j  where   e.depart_id = g.depart_id  and  e.region_id=j.region_id and "
						+ " j.region_id=" + sRegionId + ")";
			} else {
				sql += " and a.storage_id in (select g.storage_id  from mp_department e, Storage_Depart_Rela g ,ra_town j    where   e.depart_id = g.depart_id  and  e.town_id=j.town_id  and j.business_id ="
						+ sRegionId + " )";
			}
			// sql +=" and a.storage_id in (select distinct g.storage_id from
			// mp_department e, Storage_Depart_Rela g where e.depart_id =
			// g.depart_id and e.region_id="+sRegionId+" )";
		}
		if (!"".equals(lanId)) {
			sql += "  and a.lan_id=" + lanId;
		}
		if (!"".equals(status)) {
			if (!"000".equals(status)) {
				sql += "  and a.curr_state='" + status + "'";
			}
		}
		if (!"".equals(usage)) {
			sql += " and a.usage='" + usage + "' ";
		}
		if ("INFORMIX".equals(databaseType)) {
			if (!"".equals(beginDate)) {
				sql += "  and a.EFF_DATE>=to_date('" + beginDate
						+ "','%Y-%m-%d')  ";
			}
			if (!"".equals(endDate)) {
				sql += "  and a.EXP_DATE<=to_date('" + endDate
						+ "','%Y-%m-%d')  ";
			}
			if (gjselect != null && !"".equals(gjselect)) {
				if ("true".equals(gjselect)) {
					sql += "  and a.EXP_DATE<to_date('"
							+ DateFormatUtils.getFormatedDate()
							+ "','%Y-%m-%d')  ";
				}
			}

		} else {
			if (!"".equals(beginDate)) {
				sql += "  and a.EFF_DATE>=to_date('" + beginDate
						+ "','yyyy-mm-dd')  ";
			}
			if (!"".equals(endDate)) {
				sql += "  and a.EXP_DATE<=to_date('" + endDate
						+ "','yyyy-mm-dd')  ";
			}
			if (gjselect != null && !"".equals(gjselect)) {
				if ("true".equals(gjselect)) {
					sql += "  and a.EXP_DATE<to_date('"
							+ DateFormatUtils.getFormatedDate()
							+ "','yyyy-mm-dd') ";
				}
			}

		}
		RcEntityDAO2 rdao = (RcEntityDAO2) SrDAOFactory.getInstance()
				.getRcEntityDAO2();
		//rdao.set
		
		String SQL_SELECT = " SELECT a.resource_instance_id,a.sales_resource_id,a.resource_instance_code,a.resource_level,a.resource_kind,a.lan_id,a.owner_type,a.owner_id,a.storage_id,a.curr_state,a.state,a.create_date,a.eff_date,a.exp_date,a.pk_calbody,a.cinventoryid,a.vbatchcode,b.storage_name,c.sales_resource_name ,a.e_pwd,a.MANUFACTURER,a.PROVIDER,a.SHOPKEEPER FROM RC_ENTITY_OUT_LOG a, RC_STORAGE b,SALES_RESOURCE c,RC_FAMILY d where a.STORAGE_ID=b.STORAGE_ID and a.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID and c.FAMILY_ID=d.FAMILY_ID ";
		String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ENTITY_OUT_LOG a ,RC_STORAGE b,SALES_RESOURCE c,RC_FAMILY d where a.STORAGE_ID=b.STORAGE_ID and a.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID and c.FAMILY_ID=d.FAMILY_ID ";
		rdao.setSQL_SELECT(SQL_SELECT);
		rdao.setSQL_SELECT_COUNT(SQL_SELECT_COUNT);
		
		pm = PageHelper.popupPageModel(rdao, sql, pi, ps);

		return pm;
	}

	public String recycleEntity(String rescInstanceId,String storageId, String operId, String departId,String salesId) {
		SqlComDAO sqc  = SrDAOFactory.getInstance().getSqlComDAO();
		if (!sqc.checkStorageRelation(storageId,  operId,  departId)) {
			return "2";//验证所选中记录所在的仓库，是否与登陆的工号或登陆的部门有关联
		}
		String tableName =  CommonUtilBo.getTableNameByResouceId(salesId);
		if ("".equals(tableName)) {
			tableName="rc_entity2";
		}
		String sql1 = "insert into "+tableName+" select * from RC_ENTITY_OUT_LOG where resource_instance_id = "+rescInstanceId;
		String sql2 = "insert into RC_ENTITY_ATTR select * from RC_ENTITY_ATTR_LOG where ENTITY_ID = "+rescInstanceId;
		sqc.excute(sql1);
		sqc.excute(sql2);
		sql1 = "delete from RC_ENTITY_OUT_LOG where resource_instance_id = "+rescInstanceId;
		sql2 = "delete from RC_ENTITY_ATTR_LOG where ENTITY_ID = "+rescInstanceId;
		sqc.excute(sql2);
		sqc.excute(sql1);
		return "1";
	}

	public List qrySrAttrInfoOut(String rescInstanceId) {
		List list = new ArrayList();
		try {
			if (rescInstanceId == null) {
				rescInstanceId = "";
			} else {
				RcEntityDAO2 pdao = SrDAOFactory.getInstance().getRcEntityDAO2();
				list = pdao.searchAttrInfo(rescInstanceId,"out");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}
	
	private RcEntityVO2 setRescCodeInfo(RcEntityVO2 rvo){
		if(rvo==null)
			return rvo;
		String code= rvo.getRescInstanceCode();
		//panyazong  
	    String []ret = new String[3];
	    ret = splitStr(code);
	    rvo.setPreCode(ret[0]);
	    rvo.setMiddleCode(ret[1]);
	    rvo.setPreCode(ret[2]);
	    /*
		if (!"".equals(code)) {
			String[] s= code.split("[0-9]+");
			String[] t=code.split("[a-zA-Z]+");
			if(s!=null&&s.length>0){
				if (s.length==1) {
					rvo.setPreCode(s[0]);
					if (s[0].length()>0) {
						if(t!=null&&t.length>1)
						   rvo.setMiddleCode(t[1]);
					}else{
						if(t!=null&&t.length>0)
						   rvo.setMiddleCode(t[0]);
					}
				}else if (s.length==2) {
					rvo.setPreCode(s[0]);
					rvo.setPostCode(s[1]);
					if (s[0].length()>0) {
						if(t!=null&&t.length>1)
						   rvo.setMiddleCode(t[1]);
					}else{
						if(t!=null&&t.length>0)
						   rvo.setMiddleCode(t[0]);
					}
				}else{
					rvo.setMiddleCode(code);
				}
			}else{
				rvo.setMiddleCode(code);
			}
		}
		*/
		return rvo;
	}
	
//addde by panyazong	
	public String[] splitStr (String code){

//	    code = "we444packetstransmittedpacket6789sreceived";

	    String patternStr = "";
	    // 正则式
	    patternStr = "\\d+";

	    String []a = code.split(patternStr);   
	    String []ret = new String[3];
	    if(a==null ||a.length==0){//只有数字
	    	System.out.println("全部是数字");
	    	ret[0]=""; 
	    	ret[1]=code;
	    	ret[2]="";
	    	return ret;
	    }
	    if(a[0]==null||a[0].equals("")){//开头就是数字
	    	System.out.println("开头就是数字");
	    	ret[0]="";
	    	ret[1] = code.substring(0,code.indexOf(a[1]));
		    ret[2]=code.substring(ret[1].length());
	    	return ret;
	    }
	    if(a.length==1 && a[0].length()>0){//结尾就是数字或者没有数字
	    	System.out.println("结尾是数字");
	    	ret[0]=a[0];
	    	ret[1] = code.substring(ret[0].length());
		    ret[2]="";
	    	return ret;
	    }
	    //开头不是数字
    	System.out.println("开头不是数字");
    	ret[0]=a[0];
    	ret[1] = code.substring(ret[0].length(),ret[0].length()+code.substring(ret[0].length()).indexOf(a[1]));
	    ret[2]=code.substring(a[0].length()+ret[1].length());
    	return ret;
	}
	
	
    public String updateEntitysState(DynamicDict dto) throws FrameException {
	  	Map map = (Map)dto.getValueByName("parameter") ;
	  	String familyId = (String)map.get("familyId");
	  	String rescInstanceCode = (String)map.get("rescInstanceCode");
	  	String currState = (String)map.get("currState");
     
    	String table = CommonUtilBo.getTableNameByFamilyId(familyId);
    	rescInstanceCode = rescInstanceCode.replaceAll(",", "','");
    	String sql = "update "+table+" set curr_state = '"+currState+"' where resource_Instance_Code in ('"+rescInstanceCode+"')";
   	    SqlComDAO comdao = SrDAOFactory.getInstance().getSqlComDAO();
   	    comdao.excute(sql);
    	return "1";
    }
	
}
