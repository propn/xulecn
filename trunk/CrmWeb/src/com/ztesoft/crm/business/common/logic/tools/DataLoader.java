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
	
	/*******DAOʵ��********/
	public static ServDAO servDao = new ServDAO();
	public static ServAttrDAO servAttrDao = new ServAttrDAO();
	
	
	
	/**
	 * ��ѯ��Ʒʵ������ 
	 * @param id �����Ʒʵ��SERV_ID
	 * @return
	 */
	public Map selectServData(String id) throws Exception{
		
		Map servMap = new HashMap();//��Ʒ������
		List params = new ArrayList();//��������
		
		params.add(id);
		//��ѯ��Ʒ��ʵ������
		List servList = servDao.findByCond(" and serv_id = ?��", params);
		if(servList!=null&&servList.size()!=0)
			servMap = (HashMap)servList.get(0);
		else
			return servMap;
		//��ѯ��Ʒ���Ա�ʵ������
		List servAttrList = servAttrDao.findByCond(" and serv_id = ?��", params);
		if(servAttrList!=null&&servAttrList.size()!=0){
			for(int i = 0;i<servAttrList.size();i++){
				Map servAttrMap = new HashMap();//��Ʒ���Ա�����
				servAttrMap = (HashMap)servAttrList.get(i);
				servMap.put(servAttrMap.get("field_name"), servAttrMap.get("attr_val"));//��������
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
