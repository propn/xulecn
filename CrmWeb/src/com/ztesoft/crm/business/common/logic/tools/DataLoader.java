package com.ztesoft.crm.business.common.logic.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.crm.business.common.inst.dao.ServAttrDAO;
import com.ztesoft.crm.business.common.inst.dao.ServDAO;
import com.ztesoft.crm.business.common.utils.SeqUtil;

/**
 * @author liupeidawn
 *
 */
public class DataLoader {
	private static DataLoader instance=null;
	
	private DataLoader(){};
	
	public static DataLoader getInst() {

		if(instance==null){
			synchronized(DataLoader.class){
				
				if(instance==null){
					instance=new DataLoader();
				}	
			}
		}
		return instance;
	}
	
	/*******DAO实例********/
	public static ServDAO servDao = new ServDAO();
	public static ServAttrDAO servAttrDao = new ServAttrDAO();
	
	
	
	/**
	 * 查询产品实例数据 
	 * @param id 传入产品实例SERV_ID
	 * @return
	 */
	public Map selectServData(String id) throws Exception{
		
		Map servMap = new HashMap();//产品表结果集
		List params = new ArrayList();//参数集合
		
		params.add(id);
		//查询产品表实例数据
		List servList = servDao.findByCond(" and serv_id = ?　", params);
		if(servList!=null&&servList.size()!=0)
			servMap = (HashMap)servList.get(0);
		else
			return servMap;
		//查询产品属性表实例数据
		List servAttrList = servAttrDao.findByCond(" and serv_id = ?　", params);
		if(servAttrList!=null&&servAttrList.size()!=0){
			for(int i = 0;i<servAttrList.size();i++){
				Map servAttrMap = new HashMap();//产品属性表结果集
				servAttrMap = (HashMap)servAttrList.get(i);
				servMap.put(servAttrMap.get("field_name"), servAttrMap.get("attr_val"));//设置属性
			}
		}
		return servMap;
	}
	
	
	
	/**
	 * @param tableNames
	 * @param id
	 * @return
	 */
	public Map selectServProductData(List tableNames,String id){
		
		Map result=new HashMap();
		
		
	//	result
		
		return result;
	}
	
	
}
