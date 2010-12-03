package com.ztesoft.vsop.product.dao;

import java.util.ArrayList;
import java.util.List;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.ztesoft.common.dao.AbstractDAO;

public abstract class AbstractProdDAO extends AbstractDAO{
	private String BATCH_DELETE = null  ;
	
	protected abstract String getBatchDeleteSQL() ;
	
	/**
	 * 批量删除
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public boolean batchDelete(String productId ) throws Exception {
		List para = new ArrayList() ;
		para.add(productId) ;
		return Base.update( getDbName() , getBatchDeleteSQL() , para, Const.UN_JUDGE_ERROR, Const.UN_JUDGE_ERROR, null)>1 ;
	}
	
	/**
	 * 批量新增
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public int[] batchInsert(List para ) throws Exception{
		return Base.batchUpdate(getDbName() , this.getInsertSQL(), para,  Const.UN_JUDGE_ERROR,  Const.UN_JUDGE_ERROR, null) ;
	}
}
