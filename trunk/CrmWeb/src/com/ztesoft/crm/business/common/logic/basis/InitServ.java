/**   
* @Title: InitServ.java 
* @Package com.ztesoft.crm.business.common.logic.basis 
* @Description: ��Ʒ��ʼ������
* @author wanjf
* @date 2010-1-27 ����05:17:10 
* @version V1.0  
*/ 
package com.ztesoft.crm.business.common.logic.basis;

import java.util.Map;

import com.ztesoft.crm.business.common.logic.model.Serv;

/** 
 * @ClassName: InitServ 
 * @Description: ��Ʒ��ʼ������ 
 * @author wanjf
 * @date 2010-1-27 ����05:17:10 
 *  
 */
public class InitServ {
	public Serv execute(Map map) {
		Serv serv = new Serv();
		
		serv.loadFromMap(map);
		
		//00N         ��;
		serv.set("state", "00N"); 
		serv.set("product_family_id", ""); //Ҫ��ѯ

		
		return serv;
	}
	
}
