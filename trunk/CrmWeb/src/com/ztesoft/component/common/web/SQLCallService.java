package com.ztesoft.component.common.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.ServiceManager;

/**
 * 
 * SQL配置服务，页面调用方式
 * @author easonwu 2010-01-27
 *
 */
public class SQLCallService {
	
	/**
	 * 非分页SQL调用
	 * @param service
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Object execService(String service , Map param ) throws Exception {
		return ServiceManager.callSQLService(service, param) ;
	}

	/**
	 * 分页SQL调用
	 * @param service
	 * @param param
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Object execPagineService(String service , Map param , int pageIndex , int pageSize ) throws Exception {
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		return ServiceManager.callSQLService(service, param) ;
	}
	
	public static void main(String[] args ){
		SQLCallService s = new SQLCallService() ;
		
		
		try {
			Map param1 = new HashMap() ;
			param1.put("id", "8899") ;
			param1.put("name", "python") ;
			param1.put("age", "7") ;
			Object o1 = s.execService("USER_INSERT", param1) ;
			int i = 0 ;
			//3	easonwu_1	123
			Map param2 = new HashMap() ;
			param2.put("id", "3") ;
			param2.put("name", "easonwu") ;
			param2.put("age", "27") ;
			Object o2 = s.execService("USER_UPDATE", param2) ;
			//3条记录
			Map param3 = new HashMap() ;
			param3.put("name", "%easonwu%") ;
			param3.put("age", "1") ;
			List o3 = (List)s.execService("GET_USERLIST", param3) ;
			
			//32
			Map param4 = new HashMap() ;
			param4.put("id", "32") ;
			Object o4 = s.execService("USER_DELETE", param4) ;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
