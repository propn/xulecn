package com.ztesoft.crm.business.common.utils;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.util.JNDINames;

/**
 * Ӫҵ��������ȡֵ������
 * */
public class SeqUtil {

	private static SeqUtil instance=null;
	
	private SeqUtil(){
		
	}
	public static SeqUtil getInst() {

		if(instance==null){
			synchronized(SeqUtil.class){
				
				if(instance==null){
					instance=new SeqUtil();
				}	
			}
		}
		return instance;
	}
	
	private SeqDAO seqDao=new SeqDAO();
	
	/**
	 * ͨ�����ݿ����ж���ȡ����ֵ
	 * @param dbSeqCode sequence code
	 * */
	public  synchronized String getNext(String dbSeqCode) {
		
		try {
			return seqDao.findByCode(dbSeqCode);
		} catch (FrameException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * ͨ��������sequence_management������ݽ��к�ȥ����ֵ
	 * @param tableCode ���ݿ����
	 * @param fieldCode ���ݿ��ֶ���
	 * */
	public synchronized String getNext(String tableCode,
			String fieldCode) {
			try {
				return this.getNext(seqDao.findSeqCode(tableCode,fieldCode));
			} catch (FrameException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
	}
	
	
	/**
	 * ��ѯ������
	 * */
	 class SeqDAO {
		 /**
		  * ��sequence_management���л�ȡ��Ҫȡֵ�����б���
		  * */
		 public String findSeqCode(String tableCode,String fieldCode) throws FrameException {
				
			 
			 String sql = DAOSQLUtils.getFilterSQL("SELECT sequence_code FROM sequence_management "
					+ " WHERE table_code=? AND field_code=?");
			 	String[] parameters=new String[]{tableCode,fieldCode};
				
				return Base.query_string(JNDINames.CRM_DATASOURCE, sql, parameters,Const.UN_JUDGE_ERROR);
				
		}
		/**
		 * ��ȡ����ֵ
		 * */
		public String findByCode(String dbSeqCode) throws FrameException {
			
			String sql = "SELECT "
				+ DAOSQLUtils.getTableName(dbSeqCode)
				+ ".nextval seq_value FROM dual";
			
			return Base.query_string(JNDINames.CRM_DATASOURCE, sql, Const.UN_JUDGE_ERROR);
			
		}
	}
	
	
	
	
}
