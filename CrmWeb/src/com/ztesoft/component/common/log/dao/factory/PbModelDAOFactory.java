package com.ztesoft.component.common.log.dao.factory;

import com.ztesoft.component.common.log.dao.PbModelDAO;
import com.ztesoft.component.common.log.dao.impl.PbModelDAOImpl;

public class PbModelDAOFactory {
	public static PbModelDAO getPbModelDAO(){
		return new PbModelDAOImpl() ;
	}
}
