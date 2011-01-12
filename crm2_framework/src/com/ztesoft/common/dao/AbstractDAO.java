package com.ztesoft.common.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.util.PageModel;

/**
 * 
 * �����������̳���
 * ����������ʵ�ֵ�DAOֻ��Ҫʵ����Ӧ�����༴��
 * DAO ����װSQL �� SQL��ز����� 
 * 
 * @author easonwu 2010-10-11
 *
 */
public abstract class AbstractDAO {

	private int maxRows;

	public void setMaxRows( int maxRows ) {
		this.maxRows = maxRows ;
	}
	
	public int getMaxRows(){
		return this.maxRows ;
	}
	
	public abstract String getDbName() ;
	
	public abstract  String getDeleteSQLByKey()throws FrameException ;
	
	public abstract  String getUpdateSQLByKey()throws FrameException ;
	
	public abstract  String getSelectSQL();
	
	public abstract  String getSelectCountSQL() ;
	
	public abstract  String getInsertSQL(); 
	
	public abstract  String getUpdateSQL(); 
	
	public abstract  String getDeleteSQL(); 
	
	public abstract  String getSQLSQLByKey() throws FrameException ;
	
	
	
	//��HashMap����
	public HashMap getEmptyMap() {
			return new HashMap();
	}

	/**
	 * ����������ѯ��¼
	 * @param whereCondParams
	 * @param whereCond
	 * @param etype
	 * @param ecode
	 * @param edesc
	 * @return
	 * @throws FrameException
	 */
  	public HashMap findByPrimaryKey(Map keyCondMap ,int  etype , int ecode ,String edesc ) throws FrameException {
  		List whereCondParams = this.translateKeyCondMap(keyCondMap) ;
		List arrayList = findBySql( getSQLSQLByKey(), whereCondParams , ecode, etype, edesc);
		if (arrayList != null && arrayList.size()>0)
			return (HashMap)arrayList.get(0);
		else
			return getEmptyMap();
	}
	
  	/**
  	 * �Զ���SQL��ѯ
  	 * @param sql SQL
  	 * @param sqlParams ��������
  	 * @param etype 
  	 * @param ecode
  	 * @param edesc
  	 * @return
  	 * @throws DAOSystemException
  	 */
	public List findBySql(String sql, List whereCondParams ,
			int  etype , int ecode ,String edesc) throws FrameException {
		DynamicDict dto =  Base.query(this.getDbName(), sql, whereCondParams , Const.ACTION_RESULT, ecode, etype, edesc) ;
		return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}


	/**
	 * ����������ѯ
	 * @param whereCond ����
	 * @param whereCondParams ����ֵ
	 * @param etype ��������
	 * @param ecode �������
	 * @param edesc ��������
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List findByCond(String whereCond,List whereCondParams ,
			int  etype , int ecode ,String edesc) throws FrameException {
		String SQL = this.getSelectSQL() + whereCond ;
		DynamicDict dto =  Base.query(this.getDbName(), SQL, whereCondParams , Const.ACTION_RESULT, ecode, etype, edesc) ;
		return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}


	
	/**
	 * ���ط�ҳ��ѯ���
	 * @param whereCond ��ѯ����
	 * @param para ������������
	 * @param pageIndex ��ʼ��������
	 * @param pageSize ÿҳ����
	 * @param dto ���ݹ�����DTO
	 * @param ecode
	 * @param etype
	 * @param edesc
	 * @return
	 * @throws FrameException
	 * 
	 */
	public PageModel searchByCond(String whereCond ,List para,int pageIndex, int pageSize ,  
			DynamicDict dto, int ecode, int etype, String edesc) throws FrameException {
		String querySQL= getSelectSQL() +  whereCond;
		String countSQL= getSelectCountSQL() +  whereCond  ;

		dto = Base.queryPage(this.getDbName(), querySQL, countSQL, para, pageIndex, pageSize, 
				dto,Const.ACTION_RESULT, ecode, etype, edesc) ;
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}
	
	
	/**
	 * ���ط�ҳ��ѯ���
	 * @param querySQL ָ����ѯSQL
	 * @param countSQL ָ��������ѯSQL
	 * @param para ������������
	 * @param pageIndex ��ʼ��������
	 * @param pageSize ÿҳ����
	 * @param dto ���ݹ�����DTO
	 * @param ecode
	 * @param etype
	 * @param edesc
	 * @return
	 * @throws FrameException
	 * 
	 */
	public PageModel searchByCond(String querySQL ,String countSQL,
			List para,int pageIndex, int pageSize ,  
			DynamicDict dto, int ecode, int etype, String edesc) throws FrameException {

		Base.queryPage(this.getDbName(), querySQL, countSQL, para, pageIndex, pageSize, 
				dto,null, ecode, etype, edesc) ;
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}
	/**
	 * Insert ����
	 * @param vo
	 * @param etype
	 * @param ecode
	 * @param edesc
	 * @return
	 * @throws FrameException
	 * 
	 */
	public boolean insert(Map vo ,int  etype , int ecode ,String edesc) throws FrameException {
		List insertParams = translateInsertParams(vo) ;
		String SQL = getInsertSQL() ;
		return Base.update(this.getDbName(), SQL, insertParams, ecode, etype, edesc) > 0 ;
	}
	
	
	/**
	 * batch insert ����
	 * @param vo
	 * @param etype
	 * @param ecode
	 * @param edesc
	 * @return
	 * @throws FrameException
	 * 
	 */
	public boolean batchInsert(List vos ,int  etype , int ecode ,String edesc) throws FrameException {
		List insertParams = new ArrayList();
		for(int i=0;i<vos.size();i++){	
			insertParams.add(translateInsertParams((Map)vos.get(i))) ;
		}
		String SQL = getInsertSQL() ;
		return Base.batchUpdate(this.getDbName(), SQL, insertParams, ecode, etype, edesc).length > 0 ;
	}
	
	
	
	/**
	 * ��������Update����
	 * @param vo
	 * @param keyCondMap
	 * @param etype
	 * @param ecode
	 * @param edesc
	 * @return
	 * @throws FrameException
	 * 
	 */
  	public boolean updateByKey( Map vo , Map keyCondMap  ,int  etype , int ecode ,String edesc) throws FrameException {
  		List updateParams = translateUpdateParamsByKey( vo ,  keyCondMap ) ;
  		String SQL = this.getUpdateSQLByKey() ;
  		return Base.update(this.getDbName(), SQL, updateParams, ecode, etype, edesc) > 0 ;
	}
  	
  	/**
  	 * Update ����
  	 * @param vo ���¶��� 
  	 * @param whereCond ��λ��������
  	 * @param etype ��������
  	 * @param ecode �������
  	 * @param edesc ����������Ϣ
  	 * @return
  	 * @throws FrameException
  	 * 
  	 */
  	public boolean update(Map vo ,String whereCond ,List condParas ,int  etype , int ecode ,String edesc) throws FrameException {
  		List updateParams = translateUpdateParams(vo , condParas) ;
		String SQL = this.getUpdateSQL()+whereCond ;
		return Base.update(this.getDbName(), SQL, updateParams, ecode, etype, edesc) > 0 ;
	}
  	
  	
  	/**
	 * batch insert ����
	 * @param vo
	 * @param etype
	 * @param ecode
	 * @param edesc
	 * @return
	 * @throws FrameException
	 * 
	 */
	public boolean batchUpdate(List vos ,String whereCond ,List condParas ,int  etype , int ecode ,String edesc) throws FrameException {
		List updateParams = new ArrayList();
		for(int i=0;i<vos.size();i++){	
			updateParams.add(translateInsertParams((Map)vos.get(i))) ;
		}
		String SQL = this.getUpdateSQL()+whereCond ;
		return Base.batchUpdate(this.getDbName(), SQL, updateParams, ecode, etype, edesc).length > 0 ;
	}

	
	/**
	 * ��������ɾ��
	 * @param whereCond
	 * @param whereCondParams
	 * @param etype
	 * @param ecode
	 * @param edesc
	 * @return
	 * @throws FrameException
	 * 
	 */
	public long delete(String whereCond,List whereCondParams ,
			int  etype , int ecode ,String edesc ) throws FrameException {
		String SQL = getDeleteSQL() + "   " + whereCond ;
		return Base.update(this.getDbName(), SQL, whereCondParams, ecode, etype, edesc) ;
	}

	/**
	 * 
	 * ����������϶���Map�������ݼ�¼
	 * @param keyCondMap
	 * @param etype
	 * @param ecode
	 * @param edesc
	 * @return
	 * @throws FrameException
	 * 
	 */
	public int[] batchDeleteByKeys(List  keyCondMaps ,
			int  etype , int ecode ,String edesc) throws FrameException {
		String SQL = this.getDeleteSQLByKey() ;
		List deleteParams = new ArrayList();
		for(int i=0;i<keyCondMaps.size();i++){
			
			deleteParams.add( translateKeyCondMap( (Map)keyCondMaps.get(i)) ) ;
		}
		return Base.batchUpdate(this.getDbName(), SQL, deleteParams, ecode, etype, edesc) ;
	}
	
	
	/**
	 * 
	 * ����������϶���Map�������ݼ�¼
	 * @param keyCondMap
	 * @param etype
	 * @param ecode
	 * @param edesc
	 * @return
	 * @throws FrameException
	 * 
	 */
	public long deleteByKey(Map keyCondMap ,
			int  etype , int ecode ,String edesc) throws FrameException {
		String SQL = this.getDeleteSQLByKey() ;
		List whereCondParams = translateKeyCondMap( keyCondMap) ;
		return Base.update(this.getDbName(), SQL, whereCondParams, ecode, etype, edesc) ;
	}

	/**
	 * insert ��������Ҫ��map��InsertSQL����ת��ΪString�������
	 * ֱ�ӵ��ã����ܳ��쳣
	 * @param vo
	 * @return
	 * @throws FrameException
	 * 
	 */
	protected List translateInsertParams(Map map) throws FrameException{
		throw new FrameException("����ֱ�ӵ��ô˷�����Insert����ʱ��������Ҫʵ�ָķ���");
	}
	

	/**
	 * update ��������Ҫ��map��UpdateSQL����ת��ΪString�������
	 * @param vo ���¶���ֵ
	 * @param condParas ��λ���¼�¼����ֵ
	 * @return
	 * @throws FrameException
	 */
	protected List translateUpdateParams(Map vo , List condParas) throws FrameException{
		throw new FrameException("����ֱ�ӵ��ô˷�����Update����ʱ��������Ҫʵ�ָķ���");
	}
	
	/**
	 * update ��������Ҫ��map��UpdateSQL����ת��ΪString�������
	 * @param vo ���¶���ֵ
	 * @param keyCondMap �����������ϣ���λ��¼
	 * @return
	 * @throws FrameException
	 */
	protected List translateUpdateParamsByKey(Map vo , Map keyCondMap) throws FrameException{
		throw new FrameException("����ֱ�ӵ��ô˷�����Update����ʱ��������Ҫʵ�ָķ���");
	}
	
	/**
	 * ����Map���϶���
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 */
	protected List translateKeyCondMap(Map keyCondMap) throws FrameException{
		throw new FrameException("����ֱ�ӵ��ô˷�����������Ҫʵ�ָķ���");
	}

	/**
	 * ����������ѯ��¼
	 * @param whereCondParams
	 * @param whereCond
	 * @return
	 * @throws FrameException
	 */
  	public HashMap findByPrimaryKey(Map keyCondMap  ) throws FrameException {
  		List whereCondParams = this.translateKeyCondMap(keyCondMap) ;
		List arrayList = findBySql( getSQLSQLByKey(), whereCondParams , 1, Const.UN_JUDGE_ERROR, "");
		if (arrayList != null && arrayList.size()>0)
			return (HashMap)arrayList.get(0);
		else
			return getEmptyMap();
	}
	
  	/**
  	 * �Զ���SQL��ѯ
  	 * @param sql SQL
  	 * @param sqlParams ��������
  	 * @return
  	 * @throws DAOSystemException
  	 */
	public List findBySql(String sql, List whereCondParams ) throws FrameException {
		DynamicDict dto =  Base.query(this.getDbName(), sql, whereCondParams , Const.ACTION_RESULT,1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}


	/**
	 * ����������ѯ
	 * @param whereCond ����
	 * @param whereCondParams ����ֵ
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List findByCond(String whereCond,List whereCondParams ) throws FrameException {
		String SQL = this.getSelectSQL() + whereCond ;
		DynamicDict dto =  Base.query(this.getDbName(), SQL, whereCondParams , Const.ACTION_RESULT,1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}


	/**
  	 * �Զ���SQL��ѯ
  	 * @param sql SQL
  	 * @return
  	 * @throws DAOSystemException
  	 */
	public List findBySql(String sql) throws FrameException {
		List whereCondParams = null;
		DynamicDict dto =  Base.query(this.getDbName(), sql, whereCondParams , Const.ACTION_RESULT,1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}


	/**
	 * ����������ѯ
	 * @param whereCond ����
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List findByCond(String whereCond) throws FrameException {
		List whereCondParams = null;
		String SQL = this.getSelectSQL() + whereCond ;
		DynamicDict dto =  Base.query(this.getDbName(), SQL, whereCondParams , Const.ACTION_RESULT,1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}
	
	/**
	 * ���ط�ҳ��ѯ���
	 * @param whereCond ��ѯ����
	 * @param para ������������
	 * @param pageIndex ��ʼ��������
	 * @param pageSize ÿҳ����
	 * @return
	 * @throws FrameException
	 * 
	 */
	public PageModel searchByCond(String whereCond ,List para,int pageIndex, int pageSize) throws FrameException {
		String querySQL= getSelectSQL() +  whereCond;
		String countSQL= getSelectCountSQL() +  whereCond  ;

		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL, countSQL, para, pageIndex, pageSize, 
				null,Const.ACTION_RESULT, 1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}
	
	
	/**
	 * ���ط�ҳ��ѯ���
	 * @param querySQL ָ����ѯSQL
	 * @param countSQL ָ��������ѯSQL
	 * @param para ������������
	 * @param pageIndex ��ʼ��������
	 * @param pageSize ÿҳ����
	 * @return
	 * @throws FrameException
	 * 
	 */
	public PageModel searchByCond(String querySQL ,String countSQL,
			List para,int pageIndex, int pageSize ) throws FrameException {

		DynamicDict dto =Base.queryPage(this.getDbName(), querySQL, countSQL, para, pageIndex, pageSize, 
				null,null,  1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}
	
	/**
	 * ���ط�ҳ��ѯ���
	 * @param sql ָ����ѯSQL
	 * @param para ������������
	 * @param pageIndex ��ʼ��������
	 * @param pageSize ÿҳ����
	 * @return
	 * @throws FrameException
	 * 
	 */
	public PageModel searchBySql(String sql,
			List para,int pageIndex, int pageSize ) throws FrameException {
		String querySQL = sql;
		String countSQL = " select count(*) from  ( "+sql+ ")";
		
		DynamicDict dto =Base.queryPage(this.getDbName(), querySQL, countSQL, para, pageIndex, pageSize, 
				null,null,  1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}
	
	/**
	 * Insert ����
	 * @param vo
	 * @return
	 * @throws FrameException
	 * 
	 */
	public boolean insert(Map vo) throws FrameException {
		List insertParams = translateInsertParams(vo) ;
		String SQL = getInsertSQL() ;
		return Base.update(this.getDbName(), SQL, insertParams,  1, Const.UN_JUDGE_ERROR, "") > 0 ;
	}
	
	
  	
	/**
	 * ��������Update����
	 * @param vo
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */
  	public boolean updateByKey( Map vo , Map keyCondMap ) throws FrameException {
  		List updateParams = translateUpdateParamsByKey( vo ,  keyCondMap ) ;
  		String SQL = this.getUpdateSQLByKey() ;
  		return Base.update(this.getDbName(), SQL, updateParams, 1, Const.UN_JUDGE_ERROR, "") > 0 ;
	}
  	
  	/**
  	 * Update ����
  	 * @param vo ���¶��� 
  	 * @param whereCond ��λ��������
  	 * @return
  	 * @throws FrameException
  	 * 
  	 */
  	public boolean update(Map vo ,String whereCond ,List condParas ) throws FrameException {
  		List updateParams = translateUpdateParams(vo , condParas) ;
		String SQL = this.getUpdateSQL()+whereCond ;
		return Base.update(this.getDbName(), SQL, updateParams, 1, Const.UN_JUDGE_ERROR, "") > 0 ;
	}

	
	/**
	 * ��������ɾ��
	 * @param whereCond
	 * @param whereCondParams
	 * @return
	 * @throws FrameException
	 * 
	 */
	public long delete(String whereCond,List whereCondParams ) throws FrameException {
		String SQL = getDeleteSQL() + "  " + whereCond ;
		return Base.update(this.getDbName(), SQL, whereCondParams,  1, Const.UN_JUDGE_ERROR, "") ;
	}

	/**
	 * 
	 * ����������϶���Map�������ݼ�¼
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */
	public long deleteByKey(Map keyCondMap ) throws FrameException {
		String SQL = this.getDeleteSQLByKey() ;
		List whereCondParams = translateKeyCondMap( keyCondMap) ;
		return Base.update(this.getDbName(), SQL, whereCondParams, 1, Const.UN_JUDGE_ERROR, "") ;
	}
	
	
	/**
  	 * Update ����
  	 * @param sql ִ��SQL 
  	 * @param condParas �󶨲���
  	 * @return
  	 * @throws FrameException
  	 * 
  	 */
  	public boolean update(String sql ,List condParas ) throws FrameException {
		return Base.update(this.getDbName(), sql, condParas, 1, Const.UN_JUDGE_ERROR, "") > 0 ;
	}

}

