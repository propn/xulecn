package com.ztesoft.crm.business.common.logic.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.exception.BusiException;
import com.ztesoft.crm.business.common.logic.basis.OrdItemUtil;
import com.ztesoft.crm.business.common.logic.model.BusiObject;
import com.ztesoft.crm.business.common.logic.model.OrdItemIdManager;
import com.ztesoft.crm.business.common.logic.model.OrderItem;
import com.ztesoft.crm.business.common.logic.model.OrderItemDetail;

public class UtilTools {
    
    /** 
    * @Title: compareAttr 
    * @Description: 属性比对方法CompareAttr
    * @param keys 主键
    * @param nMaps 新属性信息
    * @param oMaps 老属性信息
    * @param instOrdItems 主表比较结果 List of OrderItem,用于判断已经删除的主表，然后删除相应的属性信息
    * @param instKeys 主表与属性表的关联字段
    * @param common 基本信息
    * @param noCompareStr 不要比较的Keys，用逗号分隔, 如果为空，则不比较默认的字段
    * @param    设定文件 
    * @return List    返回类型 
    * @throws 
    */ 
    public static List compareAttr(String[] keys, List nMaps, List oMaps, List instOrdItems, 
                                   String[] instKeys, Map common, String noCompareStr) {
        List list = new ArrayList();

        // 1.删除属性处理，
        for (int i = 0; i < instOrdItems.size(); i++) {
            OrderItem orderItem = (OrderItem)instOrdItems.get(i);
            // // 1.1如果instOrdItem的操作类不为D, 那么continue;
            if (!KeyValues.ACTION_TYPE_D.equals(orderItem.get(Keys.ACTION_TYPE))) {
                continue;
            }
            
            // 1.2从nMaps中删除主实例被删除的数据（例如删除的附属产品实例，那么将同时删除相应的附属产品附加属性）
            List nList = findAndRemove(orderItem.getAttributes(), instKeys, nMaps);
            // 1.3从原来的老数据中，根据被删除的实例标识，找到将要被删除的属性
            List oList = find(orderItem.getAttributes(), instKeys, oMaps);
            // 1.4 为每个需要删除的属性生成删除订单项:for(oMap in oList)生成删除订单项

            for (int j = 0; j < oList.size(); j++) {
                Map oMap = (Map) oList.get(i);
                oMap.put(Keys.ACTION_TYPE, KeyValues.ACTION_TYPE_D);
                list.add(oMap);
            }
        }

        // 2调用Compare生成其他的订单项。
        List comList = null;
        
        if (null == noCompareStr || "".equals(noCompareStr)) {
            comList = compare(keys, nMaps, oMaps, common);
        } else {
            comList = compare(keys, nMaps, oMaps, common, noCompareStr);
        }

        list.addAll(comList);
        // 3合并1和2的数据并返回（oMap中记录了经过处理后的最新属性信息）。
        
        return list;
    }

    /** 
    * @Title: compare 
    * @Description: 比较两个List of map的数据
    *  1. 根据比对的结果返回ordItem数据
    *  2. 在oMaps中记录了合并了nMap后的最新数据（将来做自动处理的时候需要）
    * @param @param keys  主键，唯一确定List中的一个Map值
    * @param @param nMaps 新值
    * @param @param oMaps 老值，比较后，将是新老值的合并信息
    * @param @param common 公共信息，用于填充字段
    * @param @param noCompareStr 不要比较的Keys，用逗号分隔, 如果为空，则不比较默认的字段
    * @param @return    
    * @return List    List of OrderItem
    * @throws 
    */ 
    public static List compare(String[] keys, List nMaps, List oMaps, Map common, String noCompareStr) { 
        //第一 判断入参
        if (null == keys || null == nMaps || null == oMaps || null == common) {
            return null;
        }
        
        List allMaps = new ArrayList(); //记录合并后的对象
        List res = new ArrayList();  //res记录生成的订单项

        //第二 逐个nMap与老信息列表比较，合并
        for (int i = 0; i < nMaps.size(); i++) {
            // 1.从oMaps中找到并删除老数据。
            Map nMap = (Map) nMaps.get(i);
            Map oMap = locateAndRemove(nMap, keys, oMaps);
            // 2.如果nMap的操作类型为"K"，那么不处理, allMaps.add(oMap);
            if (KeyValues.ACTION_TYPE_K.equals(nMap.get(Keys.ACTION_TYPE))) {
                allMaps.add(oMap);
            }
            // 3.如果nMap的操作类型为"A"，而且oMap == null，那么生成增加订单项, allMaps.add(nMap);
            else if (KeyValues.ACTION_TYPE_A.equals(nMap.get(Keys.ACTION_TYPE)) && oMap == null) {
                res.add(OrdItemUtil.create(oMap, nMap, common));
                allMaps.add(nMap);
            }
            // 4.如果nMap的操作类型为"A"，而且oMap != null 那么生成修改订单项, allMaps.add(nMap);
            else if (KeyValues.ACTION_TYPE_A.equals(nMap.get(Keys.ACTION_TYPE)) && oMap != null) {                
                OrderItem ordItem = OrdItemUtil.update(oMap, nMap, common, noCompareStr);
                
                if (ordItem != null)
                    res.add(ordItem);
                allMaps.add(nMap);
            }
            // 5.如果nMap的操作类型为"D"，而且oMap == null，那么不做处理,
            else if (KeyValues.ACTION_TYPE_D.equals(nMap.get(Keys.ACTION_TYPE)) && oMap == null) {
                continue;
            }
            // 6.如果nMap的操作类型为"D"，而且oMap ！= null，那么生成删除订单项。
            else if (KeyValues.ACTION_TYPE_D.equals(nMap.get(Keys.ACTION_TYPE)) && oMap != null) {
                res.add(OrdItemUtil.delete(oMap, nMap, common));
            }
            // 7.如果nMap的操作类型为"M"，而且oMap != null， 那么生成修改订项; allMaps.add(nMap);
            else if (KeyValues.ACTION_TYPE_M.equals(nMap.get(Keys.ACTION_TYPE)) && oMap != null) {     
                OrderItem ordItem = OrdItemUtil.update(oMap, nMap, common, noCompareStr);
                
                if (ordItem != null)
                    res.add(ordItem);
                allMaps.add(nMap);
            }
            // 8.如果nMap的操作类型为"M"，而且oMap==null, 那么生成增加订单项:
            else if (KeyValues.ACTION_TYPE_M.equals(nMap.get(Keys.ACTION_TYPE)) && oMap == null) {
                res.add(OrdItemUtil.create(oMap, nMap, common));
                allMaps.add(nMap);
            }
            else {
                throw new BusiException("比较时，没有传入操作类型!");
            }
        }
        
        // 第三：合并allMaps到oMaps上 oMaps.addAll(allMaps);
        oMaps.addAll(allMaps);
        
        // 第四：返回结果订单项res;
        return res;
    }

	/** 
	* @Title: compare 
	* @Description: 比较两个List of map的数据
	*  1. 根据比对的结果返回ordItem数据
	*  2. 在oMaps中记录了合并了nMap后的最新数据（将来做自动处理的时候需要）
	* @param @param keys  主键，唯一确定List中的一个Map值
	* @param @param nMaps 新值
	* @param @param oMaps 老值，比较后，将是新老值的合并信息
	* @param @param common 公共信息，用于填充字段
	* @param @return    
	* @return List    List of OrderItem
	* @throws 
	*/ 
	public static List compare(String[] keys, List nMaps, List oMaps, Map common) {		
		
		return compare(keys, nMaps, oMaps, common, null);
	}

	/** 
	* @Title: diff 
	* @Description: 比较新老信息，如果新老信息不同，用新值替换老值
	* @param @param oMap 老信息,比较后，将是新老合并信息
	* @param @param nMap 新信息
	* @param @return   
	* @return List    List of OrderItemDetail
	* @throws 
	*/ 
	public static List diff(Map oMap,Map nMap) {
		if (null == oMap || null == nMap) {
			return null;
		}
		
		// 对于nMap中的的每个值对
		List res = new ArrayList();
		Iterator iter_nMap = nMap.keySet().iterator();
		while (iter_nMap.hasNext()) {
			String key = iter_nMap.next().toString();
			if (Keys.ORD_ITEM_ID.equals(key) || Keys.CUST_ORD_ID.equals(key)
					|| Keys.ORD_ID.equals(key) || Keys.ACTION_TYPE.equals(key)
					|| Keys.STATE_DATE.equals(key) || Keys.END_TIME.equals(key)
					|| Keys.BEG_TIME.equals(key) || Keys.CREATE_DATE.equals(key)) {
				continue;
			}
			
			//将空字符串变为"",防止插数据库报错
			String newvalue = Const.getStrValue(nMap, key);
			String oldvalue = Const.getStrValue(oMap, key);
			
			// 如果value != oldValue，那么生成信息变得{
			if (!newvalue.equals(oldvalue)) {
				OrderItemDetail ordItemDetail = new OrderItemDetail();
				ordItemDetail.set(Keys.FIELD_NAME, key);
				ordItemDetail.set(Keys.FIELD_VALUE, newvalue);
				ordItemDetail.set(Keys.OLD_FIELD_VALUE, oldvalue);
				res.add(ordItemDetail);
				
				//用新值替换老值
				//oMap.put(key, newvalue);					
			}
		}
		
		return res;
	}
    
    
    /** 
    * @Title: diff 
    * @Description: 比较新老信息，如果新老信息不同，用新值替换老值
    * @param @param oMap 老信息,比较后，将是新老合并信息
    * @param @param nMap 新信息
    * @param @param noCompareKeys 不比较的Key值，用逗号分隔
    * @param @return   
    * @return List    List of OrderItemDetail
    * @throws 
    */ 
    public static List diff(Map oMap,Map nMap, String noCompareKeys) {
        if (null == oMap || null == nMap || null == noCompareKeys) {
            return null;
        }        
        //返回结果，List of OrderItem
        List res = new ArrayList();
        
        Iterator iter_nMap = nMap.keySet().iterator();
        while (iter_nMap.hasNext()) {            
            String key = iter_nMap.next().toString();
            if (null == key) {
                continue;
            }
            String tempKey = KeyValues.SPLIT_STRING + key + KeyValues.SPLIT_STRING;
            
            //判断是否为不需要比较的字段
            if (noCompareKeys.indexOf(tempKey) >= 0) {
               continue;
            }
             
            //将空字符串变为"",防止插数据库报错
            String newvalue = Const.getStrValue(nMap, key);
            String oldvalue = Const.getStrValue(oMap, key);
            
            // 如果value != oldValue，那么生成信息变得{
            if (!newvalue.equals(oldvalue)) {
                OrderItemDetail ordItemDetail = new OrderItemDetail();
                ordItemDetail.set(Keys.FIELD_NAME, key);
                ordItemDetail.set(Keys.FIELD_VALUE, newvalue);
                ordItemDetail.set(Keys.OLD_FIELD_VALUE, oldvalue);
                res.add(ordItemDetail);
                
                //用新值替换老值
                //oMap.put(key, newvalue);                    
            }
        }
        
        return res;
    }

	// 根据主键定位一个记录locate
	/** 
	* @Title: locate 
	* @Description: 根据主键，在老纪录列表中定位一个主键纪录和新值一样的纪录
	* @param @param nMap 新纪录
	* @param @param keys 主键
	* @param @param oMaps 老纪录列表
	* @param @return   
	* @return Map    根据主键定位的老记录信息
	* @throws 
	*/ 
	public static Map locate(Map nMap, String[] keys, List oMaps) {	
		if (null == nMap || null == keys || null == oMaps) {
			return null;
		}
		
		int num = keys.length;
		for (int i = 0; i < oMaps.size(); i++) {
			int k = 0;
			Map oMap = (Map) oMaps.get(i);
			for (int j = 0; j < num; j++) {
				String key = keys[j];
				String nValue = (String) nMap.get(key);
				String oValue = (String) oMap.get(key);
				
				//根据nmap中不包含主键信息，应该是要抛异常的
				if (null == nValue) {
					System.out.println("nMap 中不包含主键[" + key + "]信息");
					return null;
				}
				
				if (nValue.equals(oValue)) {
					k += 1;
				}
			}
			
			if (k == num) {
				return oMap;
			}
				
		}
		return null;
	}
	
	
	public static BusiObject locate(BusiObject nBusiObject, String[] keys, List onBusiObjects) {
		int num = keys.length;
		for (int i = 0; i < onBusiObjects.size(); i++) {
			int k = 0;
			BusiObject oBusiObject = (BusiObject) onBusiObjects.get(i);
			for (int j = 0; j < num; j++) {
				String key = keys[j];
				String nValue = (String) nBusiObject.get(key);
				String oValue = (String) oBusiObject.get(key);
				if (nValue.equals(oValue)) {
					k = k + 1;
				}
			}
			if (k == num)
				return oBusiObject;
		}
		return null;
	}
	

	/** 
	* @Title: locateAndRemove 
	* @Description: 根据主键定位一个记录,且从oMaps中删除找到的纪录
	* @param @param nMap 基线信息
	* @param @param keys 主键
	* @param @param oMaps 目标列表
	* @param @return    
	* @return Map    找到且从目标列表中删除的纪录信息
	* @throws 
	*/ 
	public static Map locateAndRemove(Map nMap, String[] keys, List oMaps) {
		Map delmap = locate(nMap, keys, oMaps);
		
		if (null == delmap) {
			return null;
		}
		
		oMaps.remove(delmap);
		
		return delmap;
	}

	// 根据属性查询多个记录并删除findAndRemove
	public static List findAndRemove(Map nMap, String[] keys, List subMaps) {
		List list = find(nMap, keys, subMaps);
		
		if (null == list || list.isEmpty()) {
			return list;
		}
		
		//删除记录
		for (int i = 0; i < list.size(); i++) {
			Map delmap = (Map)list.get(i);
			
			subMaps.remove(delmap);
		}
		
		return list;
	}

	// 根据属性查询多个记录find
	public static List find(Map nMap, String[] keys, List subMaps) {
		List list = new ArrayList();		
		int num = keys.length;
		
		for (int i = 0; i < subMaps.size(); i++) {
			int k = 0;
			Map oMap = (Map) subMaps.get(i);
			
			for (int j = 0; j < num; j++) {
				String key = keys[j];
				String nValue = (String) nMap.get(key);
				String oValue = (String) oMap.get(key);
				
				//在基线信息中没有找到key对应的值
				if (null == nValue) {
					System.out.println("nMap 中不包含属性[" + key + "]信息");
					return null;
				}				
				
				if (nValue.equals(oValue)) {
					k += 1;
				}
			}
			
			//属性完全匹配
			if (k == num) {
				list.add(oMap);
			}
		}
		
		return list;
	}
    
    
    //保存入参时，补齐非空字段，或者状态时间
    public static void addData(OrderItem ordItem) {
         
         if (null == ordItem.get(Keys.ORD_ITEM_ID) || "".equals(ordItem.get(Keys.ORD_ITEM_ID))) {              
             ordItem.set(Keys.ORD_ITEM_ID, OrdItemIdManager.getNextVal());
         }
         
         if (null == ordItem.get(Keys.STATE_DATE) || "".equals(ordItem.get(Keys.STATE_DATE))) {                
             ordItem.set(Keys.STATE_DATE, DateFormatUtils.getFormatedDate());
         }
         
         ordItem.set(Keys.PRICE_ID, "-1"); //销售品对应到计费写死
    }
    

}
