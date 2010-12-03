package com.ztesoft.component.common.log.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.common.log.dao.PbExceptionLogDAO;
import com.ztesoft.component.common.log.dao.PbLogDAO;
import com.ztesoft.component.common.log.dao.PbLogTypeDAO;
import com.ztesoft.component.common.log.dao.PbModelDAO;
import com.ztesoft.component.common.log.dao.StaffOnlineDAO;
import com.ztesoft.component.common.log.dao.factory.PbExceptionLogDAOFactory;
import com.ztesoft.component.common.log.dao.factory.PbLogDAOFactory;
import com.ztesoft.component.common.log.dao.factory.PbLogTypeDAOFactory;
import com.ztesoft.component.common.log.dao.factory.PbModelDAOFactory;
import com.ztesoft.component.common.log.dao.factory.StaffOnlineDAOFactory;
import com.ztesoft.component.common.log.vo.PbExceptionLogVO;
import com.ztesoft.component.common.log.vo.PbLogVO;
import com.ztesoft.component.common.log.vo.StaffOnlineVO;

public class LogBean extends DictAction {

	/**
	 * 修改员工在线日志
	 * 
	 */
	public void updateStaffOnlineLog(StaffOnlineVO staffOnlineVO)
			throws Exception {
		StaffOnlineDAO staffOnlineDao = StaffOnlineDAOFactory
				.getStaffOnlineDAO();
		staffOnlineDao.update(staffOnlineVO.getLogInfoId(), staffOnlineVO);
	}

	/**
	 * 插入员工在线日志
	 * 
	 */
	public StaffOnlineVO addStaffOnlineLog(StaffOnlineVO staffOnlineVO)
			throws Exception {
		staffOnlineVO.setOnlineState("10A");//初始状态为10A表示有效
		StaffOnlineDAO staffOnlineDao = StaffOnlineDAOFactory
				.getStaffOnlineDAO();
		staffOnlineDao.insert(staffOnlineVO);
		return staffOnlineVO;
	}

	/**
	 * 根据日志ID获取异常日志记录
	 * 
	 */
	public PbExceptionLogVO getExceptionLogVO( String logId ) throws Exception{
		PbExceptionLogDAO exceptionDao = PbExceptionLogDAOFactory
		.getPbExceptionLogDAO();
		return exceptionDao.findByPrimaryKey(logId);
	}
	/**
	 * 插入异常日志,需要同时插入异常日志表和系统日志表
	 * 
	 */
	public String addExceptionLog(PbExceptionLogVO exceptionVO, PbLogVO pbLogVO)
			throws Exception {
		pbLogVO.setState("10A");//初始状态10A表示有效
		addPbLog(pbLogVO);
		
		PbExceptionLogDAO exceptionDao = PbExceptionLogDAOFactory
				.getPbExceptionLogDAO();
		exceptionVO.setLogId(pbLogVO.getId());
		exceptionDao.insert(exceptionVO);
		return pbLogVO.getId();
	}

	/**
	 * 增加系统日志
	 * 
	 */
	public String addPbLog(PbLogVO pbLogVO) throws Exception {
		pbLogVO.setState("10A");//初始状态10A表示有效
		PbLogDAO pbLogDao = PbLogDAOFactory.getPbLogDAO();
		pbLogDao.insert(pbLogVO);
		return pbLogVO.getId();
	}
	/**
	 * 查询日志类型静态数据表
	 */
	public List queryLogType() throws Exception{
		PbLogTypeDAO logTypeDao = PbLogTypeDAOFactory.getPbLogTypeDAO();
		return logTypeDao.findByCond( "1=1" ) ;
	}
	/**
	 * 查询日志模块静态数据表
	 */
	public List queryLogModel() throws Exception{
		PbModelDAO modelDao = PbModelDAOFactory.getPbModelDAO();
		return modelDao.findByCond( "1=1" ) ;
	}
	
	/**
	 * 通过工号查询参与人角色ID
	 * @param staffCode
	 * @return
	 * @throws DAOSystemException
	 */
	private String getPartyRoleIdByStaffCode( String staffCode ) throws DAOSystemException{
		Connection conn = null ;
		PreparedStatement stmt = null ;
		ResultSet rs = null ;
		String SQL ="";
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			SQL = "select party_role_id from staff where staff_code = '" + staffCode + "'";
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			rs = stmt.executeQuery();

			if( rs.next() ) {
				return rs.getString("party_role_id");
			}else{
				return "";
			}
		}catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting sql:\n"+SQL, se);
		}finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}
	/**
	 * 以分页的形式查询员工在线日志表,查询条件为记录日志的起始时间和结束时间段
	 */
	public PageModel queryStaffOnlineLog(String startTime, String endTime, String staffCode, int pageIndex, int pageSize) throws Exception {
		StaffOnlineDAO staffOnlineDao = StaffOnlineDAOFactory.getStaffOnlineDAO();
		String cond = "1=1" ;
		
		startTime = DatabaseFunction.to_date( startTime ) ;
		endTime = DatabaseFunction.to_date( endTime ) ;
		if( !"".equals(startTime) && !"".equals(endTime)){
			cond = cond + " AND LOGON_DATE between " + startTime + " AND " + endTime;
		}else if( !"".equals(startTime) && "".equals(endTime)){
			cond = cond + " AND LOGON_DATE > " + startTime;
		}else if( !"".equals(endTime) && "".equals(startTime)){
			cond = cond + " AND LOGON_DATE < " + endTime;
		}
		if( !"".equals(staffCode)){
			String partyRoleId = getPartyRoleIdByStaffCode(staffCode) ;
			if("".equals(partyRoleId)){
				return new PageModel();
			}else{
				cond = cond + " AND STAFF_ID = " + partyRoleId ;
			}
		}
		return PageHelper.popupPageModel(staffOnlineDao, cond, pageIndex, pageSize ) ;
	}
	
	/**
	 * 以分页的方式查询系统日志表,查询条件为记录日子的时间段和模块标识, 模块标识为PB_LOG_MODULE表的静态数据.
	 * 16000的标识系统管理模块,99999标识异常日志,
	 */
	public PageModel queryPbLog(String modelId,String startTime, String endTime, String staffCode, int pageIndex, int pageSize) throws Exception {
		PbLogDAO pbLogDao = PbLogDAOFactory.getPbLogDAO();
		String cond = " 1=1 ";
		startTime = DatabaseFunction.to_date( startTime ) ;
		endTime = DatabaseFunction.to_date( endTime ) ;
		if( !"".equals(startTime) && !"".equals(endTime)){
			cond = cond + " AND CREATE_DATE between " + startTime + " AND " + endTime + "";
		}else if( !"".equals(startTime) && "".equals(endTime)){
			cond = cond + " AND CREATE_DATE > " + startTime + "";
		}else if( !"".equals(endTime) && "".equals(startTime)){
			cond = cond + " AND CREATE_DATE < " + endTime  + "";
		}
		if( !"".equals(staffCode)){
			String partyRoleId = getPartyRoleIdByStaffCode(staffCode) ;
			if("".equals(partyRoleId)){
				return new PageModel();
			}else{
				cond = cond + " AND STAFF_ID = " + partyRoleId ;
			}
		}
		cond = cond + " AND MODULE_ID = " + modelId ;
		return PageHelper.popupPageModel(pbLogDao, cond , pageIndex, pageSize ) ;
	}
	/**
	 * 以分页的方式查询异常日志,查询条件为记录日子的时间段
	 * 16000的标识系统管理模块,99999标识异常日志,
	 */
	public PageModel queryExceptionLog(String startTime, String endTime, String staffCode, int pageIndex, int pageSize ) throws Exception {
		PbExceptionLogDAO exceptionDao = PbExceptionLogDAOFactory.getPbExceptionLogDAO();
		String cond = "1=1" ;
		startTime = DatabaseFunction.to_date( startTime ) ;
		endTime = DatabaseFunction.to_date( endTime ) ;
		if( !"".equals(startTime) && !"".equals(endTime)){
			cond = cond + " AND CREATE_DATE between " + startTime + " AND " + endTime;
		}else if( !"".equals(startTime) && "".equals(endTime)){
			cond = cond + " AND CREATE_DATE > " + startTime;
		}else if( !"".equals(endTime) && "".equals(startTime)){
			cond = cond +  " AND CREATE_DATE < " + endTime;
		}
		if( !"".equals(staffCode)){
			String partyRoleId = getPartyRoleIdByStaffCode(staffCode) ;
			if("".equals(partyRoleId)){
				return new PageModel();
			}else{
				cond = cond + " AND STAFF_ID = " + partyRoleId ;
			}
		}
		PbLogDAO pbLogDao = PbLogDAOFactory.getPbLogDAO();
		List list = pbLogDao.findByCond( cond );
		String ids = "";
		for( int i = 0; i < list.size(); i ++ ){
			ids = ids + ((PbLogVO)list.get(i)).getId() + ",";
		}
		if( !"".equals(ids)){
			ids = ids.substring(0,ids.length() -1 );
			return PageHelper.popupPageModel(exceptionDao, " LOG_ID in ( " + ids + ") ", pageIndex,pageSize);
		}else{
			return PageHelper.popupPageModel(exceptionDao, " 1=1 ", pageIndex,pageSize);
		}
	}
	/**
	 * 以分页的方式查询员工在线日志,查询条件为时间段
	 */
	public long deleteStaffOnlineLog(String startTime, String endTime, String logState ) throws Exception{
		String cond = " 1=1 " ;
		startTime = DatabaseFunction.to_date( startTime ) ;
		endTime = DatabaseFunction.to_date( endTime ) ;
		if( !"".equals(startTime) && !"".equals(endTime)){ //开始时间和结束时间都不空,删除在两者之间
			cond = cond + " AND LOGON_DATE BETWEEN " + startTime + " AND " + endTime ;
		}
		if( !"".equals(startTime) && "".equals(endTime)){//开始时间不为空,结束时间为空,删除在开始时间以后的所有日志
			cond = cond + " AND LOGON_DATE > " + startTime ;
		}
		if( "".equals(startTime) && !"".equals(endTime)){//开始时间为空,结束时间不为空,删除在结束时间之前的所有日志
			cond = cond + " AND LOGON_DATE < " + endTime;
		}
		//日志状态,暂时 10A 表示正常, 10B 表示已经备份, 10P 表示无效
		if( "backup".equals(logState)){
			cond = cond + " AND ONLINE_STATE = '10B'";//状态为10B的日志表示是已经备份过的了.
		}
		StaffOnlineDAO staffOnlineDao = StaffOnlineDAOFactory.getStaffOnlineDAO();
		return staffOnlineDao.deleteByCond( cond );
	}
	/**
	 * 删除系统管理日志
	 */
	public long deleteSystemManageModuleLog( String startTime, String endTime, String logState ) throws Exception{
		String cond = " MODULE_ID = 16000 " ;//系统管理的模块ID固定为16000
		startTime = DatabaseFunction.to_date( startTime ) ;
		endTime = DatabaseFunction.to_date( endTime ) ;
		if( !"".equals(startTime) && !"".equals(endTime)){ //开始时间和结束时间都不空,删除在两者之间
			cond = cond + " AND LOG_DATE BETWEEN " + startTime + " AND " + endTime;
		}
		if( !"".equals(startTime) && "".equals(endTime)){//开始时间不为空,结束时间为空,删除在开始时间以后的所有日志
			cond = cond + " AND LOG_DATE > " + startTime ;
		}
		if( "".equals(startTime) && !"".equals(endTime)){//开始时间为空,结束时间不为空,删除在结束时间之前的所有日志
			cond = cond + " AND LOG_DATE < " + endTime;
		}
		//日志状态,暂时 10A 表示正常, 10B 表示已经备份, 10P 表示无效
		if( "backup".equals(logState)){
			cond = cond + " AND STATE = '10B'";//状态为10B的日志表示是已经备份过的了.
		}
		PbLogDAO pbLogDao = PbLogDAOFactory.getPbLogDAO();
		return pbLogDao.deleteByCond( cond ) ;
	}
	/**
	 * 删除异常日志
	 */
	public long deleteExceptionLog( String startTime, String endTime, String logState ) throws Exception {
		String cond = " MODULE_ID = 99999 " ;//异常日志的模块ID固定为99999
		startTime = DatabaseFunction.to_date( startTime ) ;
		endTime = DatabaseFunction.to_date( endTime ) ;
		if( !"".equals(startTime) && !"".equals(endTime)){ //开始时间和结束时间都不空,删除在两者之间
			cond = cond + " AND LOG_DATE BETWEEN " + startTime + " AND " + endTime;
		}
		if( !"".equals(startTime) && "".equals(endTime)){//开始时间不为空,结束时间为空,删除在开始时间以后的所有日志
			cond = cond + " AND LOG_DATE > " + startTime;
		}
		if( "".equals(startTime) && !"".equals(endTime)){//开始时间为空,结束时间不为空,删除在结束时间之前的所有日志
			cond = cond + " AND LOG_DATE < " + endTime;
		}
		//日志状态,暂时 10A 表示正常, 10B 表示已经备份, 10P 表示无效
		if( "backup".equals(logState)){
			cond = cond + " AND STATE = '10B'";//状态为10B的日志表示是已经备份过的了.
		}
		
		PbLogDAO pbLogDao = PbLogDAOFactory.getPbLogDAO();
		List list = pbLogDao.findByCond( cond ) ;
		if( list.size() == 0 ){
			return 0 ;
		}
		pbLogDao.deleteByCond( cond ) ;
		
		PbExceptionLogDAO pbExceptionLogDao = PbExceptionLogDAOFactory.getPbExceptionLogDAO();
		
		String logIds = "";
		for( int i = 0 ; i < list.size() ; i ++ ){
			logIds = logIds + ((PbLogVO)list.get(i)).getId() + ",";
		}
		if( !"".equals(logIds)){
			logIds = logIds.substring(0, logIds.length() -1);
		}
		
		return pbExceptionLogDao.deleteByCond( " LOG_ID in ( " + logIds + ")");
	}
}
