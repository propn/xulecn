package com.ztesoft.common.dao;

import com.ztesoft.common.dao.impl.ComDAOImpl;

/**
 * 通用dao工厂类
 * @time 2008-09-02
 * @author AiTanaka
 */
public class ComServiceDAO {

	private static ComServiceDAO instance = null;

	private ComServiceDAO() {

	}

	/* 获取工厂实例 */
	public static ComServiceDAO getInstance() {
		if(instance==null)
			instance=new ComServiceDAO();
		
		return instance;
	}

	//获取dao执行类
	public ComDAO getComDAO(){
		return new ComDAOImpl();
	}

	//获取extdao执行类
	public ComExtendDAO getComExtendDAO(){
		return new ComExtendDAO();
	}
}
