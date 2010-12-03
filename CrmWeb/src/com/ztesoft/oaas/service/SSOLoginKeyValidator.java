/**
 * 
 */
package com.ztesoft.oaas.service;

import java.util.List;

import com.ztesoft.oaas.dao.ssologinkey.SSOLoginKeyDAO;
import com.ztesoft.oaas.dao.ssologinkey.SSOLoginKeyDAOFactory;
import com.ztesoft.oaas.vo.SSOLoginKeyVO;

public class SSOLoginKeyValidator {
	public Boolean validate( String staffCode, String loginKey ) {
		SSOLoginKeyDAO dao = SSOLoginKeyDAOFactory.getSSOLoginKeyDAO();
		List list = dao.findByCond( "STAFF_CODE = upper('" + staffCode + "')" ) ;
		if( list.size() > 0 ){
			SSOLoginKeyVO vo = (SSOLoginKeyVO)list.get(0) ;
			if( vo != null ){
				if( !loginKey.equals(vo.getLoginKey())){
					return Boolean.FALSE ;
				}
			}else{
				return Boolean.FALSE ;
			}
		}else{
			return Boolean.FALSE ;
		}
		
		return Boolean.TRUE ;
	}
}
