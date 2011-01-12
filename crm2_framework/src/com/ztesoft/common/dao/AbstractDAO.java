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
 * 代码生成器继承类
 * 代码生成器实现的DAO只需要实现相应抽象类即可
 * DAO ：封装SQL 与 SQL相关差异性 
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
	
	
	
	//空HashMap对象
	public HashMap getEmptyMap() {
			return new HashMap();
	}

	/**
	 * 根据主键查询记录
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
  	 * 自定义SQL查询
  	 * @param sql SQL
  	 * @param sqlParams 条件参数
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
	 * 根据条件查询
	 * @param whereCond 条件
	 * @param whereCondParams 条件值
	 * @param etype 错误类型
	 * @param ecode 错误编码
	 * @param edesc 错误描述
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
	 * 返回分页查询结果
	 * @param whereCond 查询条件
	 * @param para 条件参数集合
	 * @param pageIndex 开始数据索引
	 * @param pageSize 每页数据
	 * @param dto 传递过来的DTO
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
	 * 返回分页查询结果
	 * @param querySQL 指定查询SQL
	 * @param countSQL 指定总数查询SQL
	 * @param para 条件参数集合
	 * @param pageIndex 开始数据索引
	 * @param pageSize 每页数据
	 * @param dto 传递过来的DTO
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
	 * Insert 操作
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
	 * batch insert 操作
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
	 * 根据主键Update操作
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
  	 * Update 操作
  	 * @param vo 更新对象 
  	 * @param whereCond 定位数据条件
  	 * @param etype 错误类型
  	 * @param ecode 错误编码
  	 * @param edesc 错误描述信息
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
	 * batch insert 操作
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
	 * 根据条件删除
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
	 * 根据组件集合对象Map更新数据记录
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
	 * 根据组件集合对象Map更新数据记录
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
	 * insert 操作，需要把map按InsertSQL次序转化为String数组对象
	 * 直接调用，会跑出异常
	 * @param vo
	 * @return
	 * @throws FrameException
	 * 
	 */
	protected List translateInsertParams(Map map) throws FrameException{
		throw new FrameException("不能直接调用此方法，Insert操作时，子类需要实现改方法");
	}
	

	/**
	 * update 操作，需要把map按UpdateSQL次序转化为String数组对象
	 * @param vo 更新对象值
	 * @param condParas 定位更新记录条件值
	 * @return
	 * @throws FrameException
	 */
	protected List translateUpdateParams(Map vo , List condParas) throws FrameException{
		throw new FrameException("不能直接调用此方法，Update操作时，子类需要实现改方法");
	}
	
	/**
	 * update 操作，需要把map按UpdateSQL次序转化为String数组对象
	 * @param vo 更新对象值
	 * @param keyCondMap 主键条件集合，定位记录
	 * @return
	 * @throws FrameException
	 */
	protected List translateUpdateParamsByKey(Map vo , Map keyCondMap) throws FrameException{
		throw new FrameException("不能直接调用此方法，Update操作时，子类需要实现改方法");
	}
	
	/**
	 * 主键Map集合对象
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 */
	protected List translateKeyCondMap(Map keyCondMap) throws FrameException{
		throw new FrameException("不能直接调用此方法，子类需要实现改方法");
	}

	/**
	 * 根据主键查询记录
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
  	 * 自定义SQL查询
  	 * @param sql SQL
  	 * @param sqlParams 条件参数
  	 * @return
  	 * @throws DAOSystemException
  	 */
	public List findBySql(String sql, List whereCondParams ) throws FrameException {
		DynamicDict dto =  Base.query(this.getDbName(), sql, whereCondParams , Const.ACTION_RESULT,1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}


	/**
	 * 根据条件查询
	 * @param whereCond 条件
	 * @param whereCondParams 条件值
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
  	 * 自定义SQL查询
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
	 * 根据条件查询
	 * @param whereCond 条件
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
	 * 返回分页查询结果
	 * @param whereCond 查询条件
	 * @param para 条件参数集合
	 * @param pageIndex 开始数据索引
	 * @param pageSize 每页数据
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
	 * 返回分页查询结果
	 * @param querySQL 指定查询SQL
	 * @param countSQL 指定总数查询SQL
	 * @param para 条件参数集合
	 * @param pageIndex 开始数据索引
	 * @param pageSize 每页数据
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
	 * 返回分页查询结果
	 * @param sql 指定查询SQL
	 * @param para 条件参数集合
	 * @param pageIndex 开始数据索引
	 * @param pageSize 每页数据
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
	 * Insert 操作
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
	 * 根据主键Update操作
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
  	 * Update 操作
  	 * @param vo 更新对象 
  	 * @param whereCond 定位数据条件
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
	 * 根据条件删除
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
	 * 根据组件集合对象Map更新数据记录
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
  	 * Update 操作
  	 * @param sql 执行SQL 
  	 * @param condParas 绑定参数
  	 * @return
  	 * @throws FrameException
  	 * 
  	 */
  	public boolean update(String sql ,List condParas ) throws FrameException {
		return Base.update(this.getDbName(), sql, condParas, 1, Const.UN_JUDGE_ERROR, "") > 0 ;
	}

}

