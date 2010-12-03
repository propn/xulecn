package com.ztesoft.component.common.log.dao.factory;

import com.ztesoft.component.common.log.dao.PbLogDAO;
import com.ztesoft.component.common.log.dao.impl.PbLogDAOImpl;

public class PbLogDAOFactory {

	public static PbLogDAO getPbLogDAO(){
		return new PbLogDAOImpl() ;
	}
}
