package com.ztesoft.crm.business.common.utils;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.util.JNDINames;

/**
 * 营业受理序列取值工具类
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
	 * 通过数据库序列定义取序列值
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
	 * 通过配置在sequence_management表的数据进行后去序列值
	 * @param tableCode 数据库表名
	 * @param fieldCode 数据库字段名
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
	 * 查询工具类
	 * */
	 class SeqDAO {
		 /**
		  * 从sequence_management表中获取需要取值的序列编码
		  * */
		 public String findSeqCode(String tableCode,String fieldCode) throws FrameException {
				
			 
			 String sql = DAOSQLUtils.getFilterSQL("SELECT sequence_code FROM sequence_management "
					+ " WHERE table_code=? AND field_code=?");
			 	String[] parameters=new String[]{tableCode,fieldCode};
				
				return Base.query_string(JNDINames.CRM_DATASOURCE, sql, parameters,Const.UN_JUDGE_ERROR);
				
		}
		/**
		 * 获取序列值
		 * */
		public String findByCode(String dbSeqCode) throws FrameException {
			
			String sql = "SELECT "
				+ DAOSQLUtils.getTableName(dbSeqCode)
				+ ".nextval seq_value FROM dual";
			
			return Base.query_string(JNDINames.CRM_DATASOURCE, sql, Const.UN_JUDGE_ERROR);
			
		}
	}
	
	
	
	
}
