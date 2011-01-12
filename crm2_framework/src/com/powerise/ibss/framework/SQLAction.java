package com.powerise.ibss.framework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.powerise.ibss.framedata.vo.TfmActArgsVO;
import com.powerise.ibss.framedata.vo.TfmServicesVO;
import com.powerise.ibss.util.SysSet;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmParamsConfig;

public class SQLAction {
	// 动态SQL类型-数据库中定义的SELECT语句
	static final int ACTION_TYPE_SELECTFROMDB = 4;
	// 动态SQL类型-数据库中定义的修改(插入/删除/修改)语句
	static final int ACTION_TYPE_FROMDB = 2;
	// 参数类别 1:入参
	static final String IN_FLAG = "1";
	// 参数类别 2:出参
	static final String OUT_FLAG = "2";

	
	// 动态SQL的类别,4-查询,2-插入
	private DynamicDict dto;

	private HashMap parameter;
	private List parameters ;

	// 字段绑定关系
	private HashMap FieldsBind;

	private static Logger m_Logger = Logger.getLogger(SQLAction.class);

	private TfmServicesVO serviceVO = null ; 
	public static void getLogger() {
		if (m_Logger == null) {
			m_Logger = Logger
					.getLogger("com.powerise.ibss.framework.ActionDispatch");
		}
	}

	/**
	 * 通过Service集合调用动态SQL
	 *
	 * @param p_service_names
	 *            Service名字集合,名字与名字之间以逗号隔开
	 * @param p_input_parameters
	 *            入参,存在两种形式,一行记录和多条记录
	 */
	public SQLAction(DynamicDict dto ,TfmServicesVO v ) throws FrameException {
		if (dto.getValueByName(Const.ACTION_PARAMETER) != null) {
			Object p_input_parameters = dto.getValueByName(Const.ACTION_PARAMETER, false);
			if (p_input_parameters.getClass().getName().equalsIgnoreCase(
					"java.util.HashMap")) {
				// 当为插入一条记录或查询操作的时候,入参为HashMap 形式
				parameter = (HashMap) p_input_parameters;
			} else if (p_input_parameters.getClass().getName()
					.equalsIgnoreCase("java.util.ArrayList")) {
				// 当为插入一条记录或查询操作的时候,入参为HashMap 形式
				parameters = (ArrayList) p_input_parameters;
			}
		}
		serviceVO = v ;
		FieldsBind = new HashMap();
		this.dto = dto;
	}

	private static int getCheckTime(){
		int iCalc = 0 ;
		try {
			iCalc = Integer
					.parseInt(SysSet.getSystemSet("ConsumeSecs", "3000"));
		} catch (Exception e) {
			iCalc = 3000;
		}
		return iCalc ;
	}

	/**
	 * 主方法,执行客户端传过来的所有操作
	 */
	public void execute() throws FrameException {
		int iCalc = getCheckTime();
		// 计算时间
		long lStart =  System.currentTimeMillis();
		Date startTime = new Date();
		
		try {
			// 得到服务名列表
			String[] services = split(this.serviceVO.getService_name(), ",");
			for (int i = 0; i < services.length; i++) {
				// 调用后台服务
				processAction(services[i]);
				
				long lCalc = System.currentTimeMillis() - lStart;
				currentLog( lStart ,  iCalc ,lCalc,   services[i] ,  startTime) ;
//				Monitor.setInfo("Action:" + services[i], lCalc);
			}
		} catch (Throwable e) {
			throw new FrameException(-5020, e.getMessage(), LogHelper.getStackMsg(e));
		} 
	}

	private void currentLog(long lStart , int iCalc , long  lCalc, String service , Date startTime){
		
		if (lCalc >= iCalc) {
			Date endTime = new java.util.Date();
			StringBuffer logBuf = new StringBuffer();
			logBuf.append("Action:");
			logBuf.append(service);
			logBuf.append("/");
			logBuf.append(startTime);
			logBuf.append("/");
			logBuf.append(endTime);
			logBuf.append("/");
			logBuf.append(lCalc);
			logBuf.append("/");
			logBuf.append(" ");
			m_Logger.warn(logBuf.toString());
			logBuf = null;
		}
	}
	/**
	 * 通过ServiceName得到动态SQL绑定的字段列表,HashMap的key表示绑定的位置,value为绑定的字段名
	 *
	 * @param p_service_name
	 *            服务名,用来定位动态SQL
	 */
	private void bindFields( PreparedStatement pst , String service)
			throws FrameException {
		int iSize = 0;
		TfmActArgsVO actArg = null;
		String value = "";
		List vArgs = this.serviceVO.getSqlArgs().getArgs();
		boolean bFound = false;
		String argName = null;
		if (vArgs != null) {
			iSize = vArgs.size();
			try {
				for (int i = 0; i < iSize; i++) {
					actArg = (TfmActArgsVO) vArgs.get(i);
					argName = actArg.getArg_name().toLowerCase();
					if (parameter == null)
						bFound = false;
					else {
						if (parameter.get(argName) == null) {
							argName = argName.toUpperCase();//此处均是兼容原来的版本
							if (parameter.get(argName) == null)
								bFound = false;
							else
								bFound = true;

						} else
							bFound = true;
					}
					if (bFound == false) {
						throw new FrameException(-5017,service
								+ "动态SQL要求的绑定字段" + actArg.getArg_name()
								+ ",而入参没有此字段对应的值");
					}
					value = parameter.get(argName).toString().trim();
					// if(value.length() >length)
					// throw new
					// FrameException(-5016,"动态SQL要求的绑定字段"+actArg.m_Name+"的长度为"+actArg.m_Length+",而入参此字段对应的值大于此长度");
					
					if (this.IN_FLAG.equals(actArg.getIn_out_flag())) {
						String dataType = actArg.getArg_data_type() ;
						int seq = Integer.parseInt(actArg.getArg_seq())  ;
						if ("1".equals(dataType)) {
							pst.setInt( seq , Integer.parseInt(value));
						} else if ("2".equals(dataType))
							pst.setString(seq , value);
						else
							pst.setObject(seq , value);
						System.out.print("argName="+argName+",seq="+seq+",value="+value) ;
					} else if (this.OUT_FLAG.equals(actArg.getIn_out_flag())) {
						;
					} else
						throw new FrameException(-5015, argName+ " 为错误的参数类别");
				}
			} catch (SQLException e) {
				e.printStackTrace() ;
				throw new FrameException(-5014, "得到动态SQL参数列表信息时,出现异常.", e
						.getMessage());
			}catch(Exception e ){
				e.printStackTrace() ;
				throw new FrameException(-5014, "得到动态SQL参数列表信息时,出现异常.", e
						.getMessage());
			}
		}
	}

	private void processAction(String service) throws FrameException {
		int iType = Integer.parseInt(serviceVO.getSqlArgs().getActionType());
		if (iType == ACTION_TYPE_SELECTFROMDB) {
			processQueryAction(service);
		} else if (iType == ACTION_TYPE_FROMDB) {
			processModifyAction(service);
		} else {
			throw new FrameException(-5011, "错误的动态SQL类型");
		}
	}

	private void filterArgs(String service) throws FrameException {
		int iSize;
		TfmActArgsVO actArg = null;
		List vArg = this.serviceVO.getSqlArgs().getArgs();
		String strSQL = this.serviceVO.getSqlArgs().getSQL();
		Vector vNewArg = null;
		int iStart = 0, iCurPos = 0;
		if (vArg != null) {
			iSize = vArg.size();
			for (int i = 0; i < iSize; i++) {
				actArg = (TfmActArgsVO) vArg.get(i);
				if ("3".equals(actArg.getIn_out_flag())) // 入参标志 ,为3 表示需要替换的参数
				{
					// 查找seq指定的?在字符串的位置
					iStart = strSQL.indexOf('?', 0);
					int iNewSeq = Integer.parseInt(actArg.getArg_seq()) - iCurPos;
					for (int k = 1; k < iNewSeq; k++) {
						iStart = strSQL.indexOf('?', iStart + 1);
						if (iStart == -1)
							throw new FrameException(-22991004, service
									+ "配置的可替换的参数设置的序号与实际不符" + actArg.getArg_name());
					}
					// 直接替换内部可以识别的字符串,在绑定入参值时比较好地替换
					/*strSQL = strSQL.substring(0, iStart)
							+ parameter.get(actArg.m_Name.toLowerCase())
							+ strSQL.substring(iStart + 1);*/
          //前台传过来的parameter的key为大写
          String paraValue=null;
          paraValue=(String)parameter.get(actArg.getArg_name().toLowerCase());
          if(paraValue==null)
            paraValue=(String)parameter.get(actArg.getArg_name().toUpperCase());
            strSQL = strSQL.substring(0, iStart)
                + paraValue
                + strSQL.substring(iStart + 1);
					iCurPos++; // 计算当前已经替换了的替换参数数目
					continue; // 跳过,继续下一个处理
				}
				actArg.setArg_seq(""+(Integer.parseInt(actArg.getArg_seq())- iCurPos));
				if (vNewArg == null)
					vNewArg = new Vector();
				vNewArg.add(actArg);
			}
			if (iCurPos > 0) {
				vArg.clear();
				vArg = null;
				// 换成新的参数
				//注释 2010-01-22
//				m_CurActData.m_SQL = strSQL;
//				m_CurActData.m_Args = vNewArg;
			} else if (vNewArg != null) {
				vNewArg.clear();
				vNewArg = null;
			}
		}
	}

	/**
	 * 执行查询的动态SQL
	 */
	private void processQueryAction(String service) throws FrameException {
		String sql = "" ;
		Connection conn =  null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		String dbName = serviceVO.getEnv_id() ;
		String appName = CrmParamsConfig.getInstance().getParamValue("APP_NAME") ;//系统名称
		try {
			filterArgs(service);
			sql = serviceVO.getSqlArgs().getSQL() ;
			conn =  ConnectionContext.getContext().getConnection(dbName) ;
			ps = conn.prepareStatement(sql);
			bindFields(ps , service);
			rs = ps.executeQuery();
			if (rs != null) {
				this.dto.setValueByName(service, rs);
				//处理字段信息
				String p_fieldname=service+"_f";
//				Object t = this.dto.getValueByName(service) ;
				ResultSetMetaData rsmt=rs.getMetaData();
				ArrayList field_heads=new ArrayList();
				for(int i=0;i<rsmt.getColumnCount();i++){
					field_heads.add(i, rsmt.getColumnName(i+1));
				}
				this.dto.setValueByName(p_fieldname, field_heads);
			}
			if("VsopWeb".equals(appName)) //vsop 新增
				ConnectionContext.getContext().commit(dbName) ;

		} catch (SQLException sqle) {
			throw new FrameException(-5010, "执行动态时出现异常.\nSQL 为:"
					+ sql, sqle.getMessage());
		}
		finally{
			DAOUtils.closeResultSet(rs) ;
			DAOUtils.closeStatement(ps) ;
			if("VsopWeb".equals(appName)) {//vsop 新增
				try {
					ConnectionContext.getContext().closeConnection(dbName) ;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 执行查询的动态SQL
	 */
//	private void processQueryActionWithList(String service) throws FrameException {
//		// getLogger();
//		
//		processQueryAction( service);
//		//把map放置到list对象中
//		String sql = this.serviceVO.getSqlArgs().getSQL() ;
//		try {
//			filterArgs(service);
//			PreparedStatement ps = m_Conn.prepareStatement(sql);
//			bindFields( ps,service);
//			ResultSet rs = ps.executeQuery();
//			if (rs != null) {
//				this.dto.setValueByName(service, rs);
//				//处理字段信息
//				String p_fieldname=service+"_f";
//				ResultSetMetaData rsmt=rs.getMetaData();
//				ArrayList field_heads=new ArrayList();
//				for(int i=0;i<rsmt.getColumnCount();i++){
//					field_heads.add(i, rsmt.getColumnName(i+1));
//				}
//				this.dto.setValueByName(p_fieldname, field_heads);
//			}
//			rs.close();
//			ps.close();
//		} catch (SQLException sqle) {
//			throw new FrameException(-5010, "执行动态时出现异常.\nSQL 为:"
//					+ sql, sqle.getMessage());
//		}
//	}

	/**
	 * 执行修改的动态SQL
	 */
	private void processModifyAction(String service) throws FrameException {
		String sql = serviceVO.getSqlArgs().getSQL() ;
		Connection conn = null ;
		PreparedStatement ps = null ;
		try {
			conn = ConnectionContext.getContext().getConnection() ;
			ps = conn.prepareStatement(sql);
			bindFields(ps , service); ;
//			if (FieldsBind != null && FieldsBind.size() > 0)
//				for (int i = 0; i < FieldsBind.size(); i++) {
//					ps.setObject(i + 1, parameter.get(FieldsBind.get(String
//							.valueOf(i + 1))));
//				}
			int result = ps.executeUpdate() ;//.execute() ;
			if (result> 0 ) {
				this.dto.setValueByName(service, Boolean.valueOf(true));
			}else{
				this.dto.setValueByName(service, Boolean.valueOf(false));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace() ;
			throw new FrameException(-5010, "执行动态时出现异常.\nSQL 为:"+ sql, sqle.getMessage());
		} catch (Exception e) {
			e.printStackTrace() ;
			throw new FrameException(-5010, "执行动态时出现异常.\nSQL 为:"+ sql, e.getMessage());
		}finally{
			DAOUtils.closeStatement(ps) ;
		}
	}

	private static String[] split(String p_in, String p_tag) {
		Vector v = new Vector();
		int pos = p_in.indexOf(p_tag);
		while (pos > 0) {
			v.add(p_in.substring(0, pos));
			p_in = p_in.substring(pos + p_tag.length());
			pos = p_in.indexOf(p_tag);
		}
		v.add(p_in);
		String[] a = new String[v.size()];
		for (int i = 0; i < v.size(); i++) {
			a[i] = (String) v.get(i);
		}
		v.clear();
		return a;
	}

	/** 返回值对象 */
	public DynamicDict getDynamicDict() {
		return this.dto;
	}

	/** 根据名字返回存储的对象 */
	public Object getObject(String p_name) {
		Object obj = null;
		try {
			obj = this.dto.getValueByName(p_name);
		} catch (FrameException e) {
		}
		return obj;
	}

}
