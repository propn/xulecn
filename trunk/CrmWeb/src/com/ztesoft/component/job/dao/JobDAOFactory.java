package com.ztesoft.component.job.dao;

import com.ztesoft.component.job.dao.impl.CrmJobClustorDAOImpl;
import com.ztesoft.component.job.dao.impl.CrmJobClustorStateDAOImpl;
import com.ztesoft.component.job.dao.impl.CrmJobDAOImpl;
import com.ztesoft.component.job.dao.impl.CrmJobRunLogDAOImpl;

public class JobDAOFactory {
  
	public static CrmJobDAO getCrmJobDAO(){
		return new CrmJobDAOImpl();
	}
	public static CrmJobRunLogDAO getCrmJobRunLogDAO(){
		return new CrmJobRunLogDAOImpl();
	}
	public static CrmJobClustorDAO getCrmJobClustorDAO(){
		return new CrmJobClustorDAOImpl();
	}
	public static CrmJobClustorStateDAO getCrmJobClustorStateDAO(){
		return new CrmJobClustorStateDAOImpl();
	}
}
