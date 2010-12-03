/**
 * 
 */
package com.ztesoft.vsop.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.engine.ErrorCode;

/**
 * <pre>
 * Title:�������������
 * Description: �����ܵ����� 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class SPSignInfoSynDao {
	
	/**
	 * ����ҵ������
	 * @param list
	 * @throws Exception
	 */
	public void addSPSignInfo(List list)throws Exception{
		String selectIdSQL = "select  MAX(PARTNER_SERVICE_ABILITY_ID) FROM  PARTNER_SERVICE_ABILITY";
		String insertSQL = "insert into  PARTNER_SERVICE_ABILITY(PARTNER_SERVICE_ABILITY_ID,SPSignID,SERVICE_CODE,PARTNER_ID,EFF_DATE,EXP_DATE) values(?,?,?,?,to_date(?,"+DatabaseFunction.getDataFormat(3)+"),to_date(?,"+DatabaseFunction.getDataFormat(3)+"))";
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
		PreparedStatement ps1 = null;
		PreparedStatement ps = null;
			
		try {
			ps1 = conn.prepareStatement(selectIdSQL);
			ResultSet resultset= ps1.executeQuery();
			String maxid = "0";
			if (resultset.next()) {
				maxid=resultset.getString(1);
			}
			int id = Integer.parseInt(maxid);
			ps = conn.prepareStatement(insertSQL);
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				ps.setInt(1, ++id);
				ps.setString(2, (String) map.get("SPSignID"));
				ps.setString(3, (String) map.get("serviceCapabilityID"));
				ps.setString(4, (String) map.get("SPID"));
				ps.setString(5, (String) map.get("effectiveTime"));
				ps.setString(6, (String) map.get("expiryTime"));
				
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps1);
			DAOUtils.closeStatement(ps);
			
		}
		
	}
	/**
	 * ����ҵ������
	 * @param list
	 * @throws Exception
	 */
	public void updateSPSignInfo(List list)throws Exception{

		String insertSQL = "update  PARTNER_SERVICE_ABILITY  set SERVICE_CODE=?,EFF_DATE=to_date(?,"+DatabaseFunction.getDataFormat(3)+"),EXP_DATE=to_date(?,"+DatabaseFunction.getDataFormat(3)+") where PARTNER_ID=? and SPSignID=?";
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(insertSQL);
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				
				ps.setString(1, (String) map.get("serviceCapabilityID"));
				ps.setString(2, (String) map.get("effectiveTime"));
				ps.setString(3, (String) map.get("expiryTime"));
				ps.setString(4, (String) map.get("SPID"));
				ps.setString(5, (String) map.get("SPSignID"));
			}
			int num=ps.executeUpdate();
			//������µĸ�����һ��  ˵���е����������ݿ�����û�е�  ˵�����͵��������쳣
			if(num!=list.size()){
				throw new Exception(ErrorCode.DATA_ERROR);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}

	}
	/**
	 * ɾ��ҵ������
	 * @param list
	 * @throws Exception
	 */
	public void deteteSPSignInfo(List list)throws Exception{

		String insertSQL = "delete  PARTNER_SERVICE_ABILITY   where PARTNER_ID=? and SPSignID=?";
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(insertSQL);
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				

				ps.setString(1, (String) map.get("SPID"));
				ps.setString(2, (String) map.get("SPSignID"));
			}
			int num=ps.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
}
