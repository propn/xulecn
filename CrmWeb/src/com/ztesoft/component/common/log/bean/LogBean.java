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
	 * �޸�Ա��������־
	 * 
	 */
	public void updateStaffOnlineLog(StaffOnlineVO staffOnlineVO)
			throws Exception {
		StaffOnlineDAO staffOnlineDao = StaffOnlineDAOFactory
				.getStaffOnlineDAO();
		staffOnlineDao.update(staffOnlineVO.getLogInfoId(), staffOnlineVO);
	}

	/**
	 * ����Ա��������־
	 * 
	 */
	public StaffOnlineVO addStaffOnlineLog(StaffOnlineVO staffOnlineVO)
			throws Exception {
		staffOnlineVO.setOnlineState("10A");//��ʼ״̬Ϊ10A��ʾ��Ч
		StaffOnlineDAO staffOnlineDao = StaffOnlineDAOFactory
				.getStaffOnlineDAO();
		staffOnlineDao.insert(staffOnlineVO);
		return staffOnlineVO;
	}

	/**
	 * ������־ID��ȡ�쳣��־��¼
	 * 
	 */
	public PbExceptionLogVO getExceptionLogVO( String logId ) throws Exception{
		PbExceptionLogDAO exceptionDao = PbExceptionLogDAOFactory
		.getPbExceptionLogDAO();
		return exceptionDao.findByPrimaryKey(logId);
	}
	/**
	 * �����쳣��־,��Ҫͬʱ�����쳣��־���ϵͳ��־��
	 * 
	 */
	public String addExceptionLog(PbExceptionLogVO exceptionVO, PbLogVO pbLogVO)
			throws Exception {
		pbLogVO.setState("10A");//��ʼ״̬10A��ʾ��Ч
		addPbLog(pbLogVO);
		
		PbExceptionLogDAO exceptionDao = PbExceptionLogDAOFactory
				.getPbExceptionLogDAO();
		exceptionVO.setLogId(pbLogVO.getId());
		exceptionDao.insert(exceptionVO);
		return pbLogVO.getId();
	}

	/**
	 * ����ϵͳ��־
	 * 
	 */
	public String addPbLog(PbLogVO pbLogVO) throws Exception {
		pbLogVO.setState("10A");//��ʼ״̬10A��ʾ��Ч
		PbLogDAO pbLogDao = PbLogDAOFactory.getPbLogDAO();
		pbLogDao.insert(pbLogVO);
		return pbLogVO.getId();
	}
	/**
	 * ��ѯ��־���;�̬���ݱ�
	 */
	public List queryLogType() throws Exception{
		PbLogTypeDAO logTypeDao = PbLogTypeDAOFactory.getPbLogTypeDAO();
		return logTypeDao.findByCond( "1=1" ) ;
	}
	/**
	 * ��ѯ��־ģ�龲̬���ݱ�
	 */
	public List queryLogModel() throws Exception{
		PbModelDAO modelDao = PbModelDAOFactory.getPbModelDAO();
		return modelDao.findByCond( "1=1" ) ;
	}
	
	/**
	 * ͨ�����Ų�ѯ�����˽�ɫID
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
	 * �Է�ҳ����ʽ��ѯԱ��������־��,��ѯ����Ϊ��¼��־����ʼʱ��ͽ���ʱ���
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
	 * �Է�ҳ�ķ�ʽ��ѯϵͳ��־��,��ѯ����Ϊ��¼���ӵ�ʱ��κ�ģ���ʶ, ģ���ʶΪPB_LOG_MODULE��ľ�̬����.
	 * 16000�ı�ʶϵͳ����ģ��,99999��ʶ�쳣��־,
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
	 * �Է�ҳ�ķ�ʽ��ѯ�쳣��־,��ѯ����Ϊ��¼���ӵ�ʱ���
	 * 16000�ı�ʶϵͳ����ģ��,99999��ʶ�쳣��־,
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
	 * �Է�ҳ�ķ�ʽ��ѯԱ��������־,��ѯ����Ϊʱ���
	 */
	public long deleteStaffOnlineLog(String startTime, String endTime, String logState ) throws Exception{
		String cond = " 1=1 " ;
		startTime = DatabaseFunction.to_date( startTime ) ;
		endTime = DatabaseFunction.to_date( endTime ) ;
		if( !"".equals(startTime) && !"".equals(endTime)){ //��ʼʱ��ͽ���ʱ�䶼����,ɾ��������֮��
			cond = cond + " AND LOGON_DATE BETWEEN " + startTime + " AND " + endTime ;
		}
		if( !"".equals(startTime) && "".equals(endTime)){//��ʼʱ�䲻Ϊ��,����ʱ��Ϊ��,ɾ���ڿ�ʼʱ���Ժ��������־
			cond = cond + " AND LOGON_DATE > " + startTime ;
		}
		if( "".equals(startTime) && !"".equals(endTime)){//��ʼʱ��Ϊ��,����ʱ�䲻Ϊ��,ɾ���ڽ���ʱ��֮ǰ��������־
			cond = cond + " AND LOGON_DATE < " + endTime;
		}
		//��־״̬,��ʱ 10A ��ʾ����, 10B ��ʾ�Ѿ�����, 10P ��ʾ��Ч
		if( "backup".equals(logState)){
			cond = cond + " AND ONLINE_STATE = '10B'";//״̬Ϊ10B����־��ʾ���Ѿ����ݹ�����.
		}
		StaffOnlineDAO staffOnlineDao = StaffOnlineDAOFactory.getStaffOnlineDAO();
		return staffOnlineDao.deleteByCond( cond );
	}
	/**
	 * ɾ��ϵͳ������־
	 */
	public long deleteSystemManageModuleLog( String startTime, String endTime, String logState ) throws Exception{
		String cond = " MODULE_ID = 16000 " ;//ϵͳ�����ģ��ID�̶�Ϊ16000
		startTime = DatabaseFunction.to_date( startTime ) ;
		endTime = DatabaseFunction.to_date( endTime ) ;
		if( !"".equals(startTime) && !"".equals(endTime)){ //��ʼʱ��ͽ���ʱ�䶼����,ɾ��������֮��
			cond = cond + " AND LOG_DATE BETWEEN " + startTime + " AND " + endTime;
		}
		if( !"".equals(startTime) && "".equals(endTime)){//��ʼʱ�䲻Ϊ��,����ʱ��Ϊ��,ɾ���ڿ�ʼʱ���Ժ��������־
			cond = cond + " AND LOG_DATE > " + startTime ;
		}
		if( "".equals(startTime) && !"".equals(endTime)){//��ʼʱ��Ϊ��,����ʱ�䲻Ϊ��,ɾ���ڽ���ʱ��֮ǰ��������־
			cond = cond + " AND LOG_DATE < " + endTime;
		}
		//��־״̬,��ʱ 10A ��ʾ����, 10B ��ʾ�Ѿ�����, 10P ��ʾ��Ч
		if( "backup".equals(logState)){
			cond = cond + " AND STATE = '10B'";//״̬Ϊ10B����־��ʾ���Ѿ����ݹ�����.
		}
		PbLogDAO pbLogDao = PbLogDAOFactory.getPbLogDAO();
		return pbLogDao.deleteByCond( cond ) ;
	}
	/**
	 * ɾ���쳣��־
	 */
	public long deleteExceptionLog( String startTime, String endTime, String logState ) throws Exception {
		String cond = " MODULE_ID = 99999 " ;//�쳣��־��ģ��ID�̶�Ϊ99999
		startTime = DatabaseFunction.to_date( startTime ) ;
		endTime = DatabaseFunction.to_date( endTime ) ;
		if( !"".equals(startTime) && !"".equals(endTime)){ //��ʼʱ��ͽ���ʱ�䶼����,ɾ��������֮��
			cond = cond + " AND LOG_DATE BETWEEN " + startTime + " AND " + endTime;
		}
		if( !"".equals(startTime) && "".equals(endTime)){//��ʼʱ�䲻Ϊ��,����ʱ��Ϊ��,ɾ���ڿ�ʼʱ���Ժ��������־
			cond = cond + " AND LOG_DATE > " + startTime;
		}
		if( "".equals(startTime) && !"".equals(endTime)){//��ʼʱ��Ϊ��,����ʱ�䲻Ϊ��,ɾ���ڽ���ʱ��֮ǰ��������־
			cond = cond + " AND LOG_DATE < " + endTime;
		}
		//��־״̬,��ʱ 10A ��ʾ����, 10B ��ʾ�Ѿ�����, 10P ��ʾ��Ч
		if( "backup".equals(logState)){
			cond = cond + " AND STATE = '10B'";//״̬Ϊ10B����־��ʾ���Ѿ����ݹ�����.
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
