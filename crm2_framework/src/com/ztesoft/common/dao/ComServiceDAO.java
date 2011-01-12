package com.ztesoft.common.dao;

import com.ztesoft.common.dao.impl.ComDAOImpl;

/**
 * ͨ��dao������
 * @time 2008-09-02
 * @author AiTanaka
 */
public class ComServiceDAO {

	private static ComServiceDAO instance = null;

	private ComServiceDAO() {

	}

	/* ��ȡ����ʵ�� */
	public static ComServiceDAO getInstance() {
		if(instance==null)
			instance=new ComServiceDAO();
		
		return instance;
	}

	//��ȡdaoִ����
	public ComDAO getComDAO(){
		return new ComDAOImpl();
	}

	//��ȡextdaoִ����
	public ComExtendDAO getComExtendDAO(){
		return new ComExtendDAO();
	}
}
