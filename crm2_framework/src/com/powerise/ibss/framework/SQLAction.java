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
	// ��̬SQL����-���ݿ��ж����SELECT���
	static final int ACTION_TYPE_SELECTFROMDB = 4;
	// ��̬SQL����-���ݿ��ж�����޸�(����/ɾ��/�޸�)���
	static final int ACTION_TYPE_FROMDB = 2;
	// ������� 1:���
	static final String IN_FLAG = "1";
	// ������� 2:����
	static final String OUT_FLAG = "2";

	
	// ��̬SQL�����,4-��ѯ,2-����
	private DynamicDict dto;

	private HashMap parameter;
	private List parameters ;

	// �ֶΰ󶨹�ϵ
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
	 * ͨ��Service���ϵ��ö�̬SQL
	 *
	 * @param p_service_names
	 *            Service���ּ���,����������֮���Զ��Ÿ���
	 * @param p_input_parameters
	 *            ���,����������ʽ,һ�м�¼�Ͷ�����¼
	 */
	public SQLAction(DynamicDict dto ,TfmServicesVO v ) throws FrameException {
		if (dto.getValueByName(Const.ACTION_PARAMETER) != null) {
			Object p_input_parameters = dto.getValueByName(Const.ACTION_PARAMETER, false);
			if (p_input_parameters.getClass().getName().equalsIgnoreCase(
					"java.util.HashMap")) {
				// ��Ϊ����һ����¼���ѯ������ʱ��,���ΪHashMap ��ʽ
				parameter = (HashMap) p_input_parameters;
			} else if (p_input_parameters.getClass().getName()
					.equalsIgnoreCase("java.util.ArrayList")) {
				// ��Ϊ����һ����¼���ѯ������ʱ��,���ΪHashMap ��ʽ
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
	 * ������,ִ�пͻ��˴����������в���
	 */
	public void execute() throws FrameException {
		int iCalc = getCheckTime();
		// ����ʱ��
		long lStart =  System.currentTimeMillis();
		Date startTime = new Date();
		
		try {
			// �õ��������б�
			String[] services = split(this.serviceVO.getService_name(), ",");
			for (int i = 0; i < services.length; i++) {
				// ���ú�̨����
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
	 * ͨ��ServiceName�õ���̬SQL�󶨵��ֶ��б�,HashMap��key��ʾ�󶨵�λ��,valueΪ�󶨵��ֶ���
	 *
	 * @param p_service_name
	 *            ������,������λ��̬SQL
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
							argName = argName.toUpperCase();//�˴����Ǽ���ԭ���İ汾
							if (parameter.get(argName) == null)
								bFound = false;
							else
								bFound = true;

						} else
							bFound = true;
					}
					if (bFound == false) {
						throw new FrameException(-5017,service
								+ "��̬SQLҪ��İ��ֶ�" + actArg.getArg_name()
								+ ",�����û�д��ֶζ�Ӧ��ֵ");
					}
					value = parameter.get(argName).toString().trim();
					// if(value.length() >length)
					// throw new
					// FrameException(-5016,"��̬SQLҪ��İ��ֶ�"+actArg.m_Name+"�ĳ���Ϊ"+actArg.m_Length+",����δ��ֶζ�Ӧ��ֵ���ڴ˳���");
					
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
						throw new FrameException(-5015, argName+ " Ϊ����Ĳ������");
				}
			} catch (SQLException e) {
				e.printStackTrace() ;
				throw new FrameException(-5014, "�õ���̬SQL�����б���Ϣʱ,�����쳣.", e
						.getMessage());
			}catch(Exception e ){
				e.printStackTrace() ;
				throw new FrameException(-5014, "�õ���̬SQL�����б���Ϣʱ,�����쳣.", e
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
			throw new FrameException(-5011, "����Ķ�̬SQL����");
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
				if ("3".equals(actArg.getIn_out_flag())) // ��α�־ ,Ϊ3 ��ʾ��Ҫ�滻�Ĳ���
				{
					// ����seqָ����?���ַ�����λ��
					iStart = strSQL.indexOf('?', 0);
					int iNewSeq = Integer.parseInt(actArg.getArg_seq()) - iCurPos;
					for (int k = 1; k < iNewSeq; k++) {
						iStart = strSQL.indexOf('?', iStart + 1);
						if (iStart == -1)
							throw new FrameException(-22991004, service
									+ "���õĿ��滻�Ĳ������õ������ʵ�ʲ���" + actArg.getArg_name());
					}
					// ֱ���滻�ڲ�����ʶ����ַ���,�ڰ����ֵʱ�ȽϺõ��滻
					/*strSQL = strSQL.substring(0, iStart)
							+ parameter.get(actArg.m_Name.toLowerCase())
							+ strSQL.substring(iStart + 1);*/
          //ǰ̨��������parameter��keyΪ��д
          String paraValue=null;
          paraValue=(String)parameter.get(actArg.getArg_name().toLowerCase());
          if(paraValue==null)
            paraValue=(String)parameter.get(actArg.getArg_name().toUpperCase());
            strSQL = strSQL.substring(0, iStart)
                + paraValue
                + strSQL.substring(iStart + 1);
					iCurPos++; // ���㵱ǰ�Ѿ��滻�˵��滻������Ŀ
					continue; // ����,������һ������
				}
				actArg.setArg_seq(""+(Integer.parseInt(actArg.getArg_seq())- iCurPos));
				if (vNewArg == null)
					vNewArg = new Vector();
				vNewArg.add(actArg);
			}
			if (iCurPos > 0) {
				vArg.clear();
				vArg = null;
				// �����µĲ���
				//ע�� 2010-01-22
//				m_CurActData.m_SQL = strSQL;
//				m_CurActData.m_Args = vNewArg;
			} else if (vNewArg != null) {
				vNewArg.clear();
				vNewArg = null;
			}
		}
	}

	/**
	 * ִ�в�ѯ�Ķ�̬SQL
	 */
	private void processQueryAction(String service) throws FrameException {
		String sql = "" ;
		Connection conn =  null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		String dbName = serviceVO.getEnv_id() ;
		String appName = CrmParamsConfig.getInstance().getParamValue("APP_NAME") ;//ϵͳ����
		try {
			filterArgs(service);
			sql = serviceVO.getSqlArgs().getSQL() ;
			conn =  ConnectionContext.getContext().getConnection(dbName) ;
			ps = conn.prepareStatement(sql);
			bindFields(ps , service);
			rs = ps.executeQuery();
			if (rs != null) {
				this.dto.setValueByName(service, rs);
				//�����ֶ���Ϣ
				String p_fieldname=service+"_f";
//				Object t = this.dto.getValueByName(service) ;
				ResultSetMetaData rsmt=rs.getMetaData();
				ArrayList field_heads=new ArrayList();
				for(int i=0;i<rsmt.getColumnCount();i++){
					field_heads.add(i, rsmt.getColumnName(i+1));
				}
				this.dto.setValueByName(p_fieldname, field_heads);
			}
			if("VsopWeb".equals(appName)) //vsop ����
				ConnectionContext.getContext().commit(dbName) ;

		} catch (SQLException sqle) {
			throw new FrameException(-5010, "ִ�ж�̬ʱ�����쳣.\nSQL Ϊ:"
					+ sql, sqle.getMessage());
		}
		finally{
			DAOUtils.closeResultSet(rs) ;
			DAOUtils.closeStatement(ps) ;
			if("VsopWeb".equals(appName)) {//vsop ����
				try {
					ConnectionContext.getContext().closeConnection(dbName) ;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ִ�в�ѯ�Ķ�̬SQL
	 */
//	private void processQueryActionWithList(String service) throws FrameException {
//		// getLogger();
//		
//		processQueryAction( service);
//		//��map���õ�list������
//		String sql = this.serviceVO.getSqlArgs().getSQL() ;
//		try {
//			filterArgs(service);
//			PreparedStatement ps = m_Conn.prepareStatement(sql);
//			bindFields( ps,service);
//			ResultSet rs = ps.executeQuery();
//			if (rs != null) {
//				this.dto.setValueByName(service, rs);
//				//�����ֶ���Ϣ
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
//			throw new FrameException(-5010, "ִ�ж�̬ʱ�����쳣.\nSQL Ϊ:"
//					+ sql, sqle.getMessage());
//		}
//	}

	/**
	 * ִ���޸ĵĶ�̬SQL
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
			throw new FrameException(-5010, "ִ�ж�̬ʱ�����쳣.\nSQL Ϊ:"+ sql, sqle.getMessage());
		} catch (Exception e) {
			e.printStackTrace() ;
			throw new FrameException(-5010, "ִ�ж�̬ʱ�����쳣.\nSQL Ϊ:"+ sql, e.getMessage());
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

	/** ����ֵ���� */
	public DynamicDict getDynamicDict() {
		return this.dto;
	}

	/** �������ַ��ش洢�Ķ��� */
	public Object getObject(String p_name) {
		Object obj = null;
		try {
			obj = this.dto.getValueByName(p_name);
		} catch (FrameException e) {
		}
		return obj;
	}

}
