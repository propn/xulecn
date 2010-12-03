package com.ztesoft.oaas.dao.mmdatapriv;

public class MmDataPrivDAOFactory {
	public static MmDataPrivDAO getMmDataPrivDAO(){
		return new MmDataPrivDAOImpl() ;
	}
}
