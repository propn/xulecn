package com.ztesoft.crm.business.common.inst.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.exception.BusiException;
import com.ztesoft.crm.business.common.logic.model.OrderItem;

public abstract class BusiDAO extends AbstractDAO {

	//list of orderitem
	public void saveNewinfo(List saveList){
		//判断入参
		if (null == saveList || saveList.isEmpty()) {
			return;
		}
		
		List addList = new ArrayList();
		List delList = new ArrayList();
		List modList = new ArrayList();
		
		Iterator iter = saveList.iterator();
		while(iter.hasNext()){
			OrderItem item = (OrderItem)iter.next();
			if(item != null){
				String action_type = (String)item.get(Keys.ACTION_TYPE);
				if(KeyValues.ACTION_TYPE_A.equals(action_type)){
					addList.add(item.getAttributes());
				}else if(KeyValues.ACTION_TYPE_D.equals(action_type)){
					delList.add(item.getAttributes());
				}else if(KeyValues.ACTION_TYPE_M.equals(action_type)){
					modList.add(item); //插入OrderItem
					
				}
			}
		}
		
		try {
			this.batchInsert(addList, -1, -1, "---");
			this.batchDeleteByKeys(delList, -1, -1, "---");
			this.batchUpdate(modList);
			} catch (FrameException e) {
            throw new BusiException(e);
		}
	}
	
    //List of OrderItem
	public void batchUpdate(List modList)throws FrameException {
		throw new FrameException("子类没有实现batchUpdate方法");
	}

}
