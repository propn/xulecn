package com.ztesoft.oaas.dao.cpromotion;

public class CPromotionDAOFactory {
	
	public static CPromotionDAO getCPromotionDAO(){
		return new CPromotionDAOImpl();
	}
}
