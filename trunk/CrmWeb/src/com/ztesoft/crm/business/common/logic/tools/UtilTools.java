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
    * @Description: ���ԱȶԷ���CompareAttr
    * @param keys ����
    * @param nMaps ��������Ϣ
    * @param oMaps ��������Ϣ
    * @param instOrdItems ����ȽϽ�� List of OrderItem,�����ж��Ѿ�ɾ��������Ȼ��ɾ����Ӧ��������Ϣ
    * @param instKeys ���������Ա�Ĺ����ֶ�
    * @param common ������Ϣ
    * @param noCompareStr ��Ҫ�Ƚϵ�Keys���ö��ŷָ�, ���Ϊ�գ��򲻱Ƚ�Ĭ�ϵ��ֶ�
    * @param    �趨�ļ� 
    * @return List    �������� 
    * @throws 
    */ 
    public static List compareAttr(String[] keys, List nMaps, List oMaps, List instOrdItems, 
                                   String[] instKeys, Map common, String noCompareStr) {
        List list = new ArrayList();

        // 1.ɾ�����Դ���
        for (int i = 0; i < instOrdItems.size(); i++) {
            OrderItem orderItem = (OrderItem)instOrdItems.get(i);
            // // 1.1���instOrdItem�Ĳ����಻ΪD, ��ôcontinue;
            if (!KeyValues.ACTION_TYPE_D.equals(orderItem.get(Keys.ACTION_TYPE))) {
                continue;
            }
            
            // 1.2��nMaps��ɾ����ʵ����ɾ�������ݣ�����ɾ���ĸ�����Ʒʵ������ô��ͬʱɾ����Ӧ�ĸ�����Ʒ�������ԣ�
            List nList = findAndRemove(orderItem.getAttributes(), instKeys, nMaps);
            // 1.3��ԭ�����������У����ݱ�ɾ����ʵ����ʶ���ҵ���Ҫ��ɾ��������
            List oList = find(orderItem.getAttributes(), instKeys, oMaps);
            // 1.4 Ϊÿ����Ҫɾ������������ɾ��������:for(oMap in oList)����ɾ��������

            for (int j = 0; j < oList.size(); j++) {
                Map oMap = (Map) oList.get(i);
                oMap.put(Keys.ACTION_TYPE, KeyValues.ACTION_TYPE_D);
                list.add(oMap);
            }
        }

        // 2����Compare���������Ķ����
        List comList = null;
        
        if (null == noCompareStr || "".equals(noCompareStr)) {
            comList = compare(keys, nMaps, oMaps, common);
        } else {
            comList = compare(keys, nMaps, oMaps, common, noCompareStr);
        }

        list.addAll(comList);
        // 3�ϲ�1��2�����ݲ����أ�oMap�м�¼�˾�������������������Ϣ����
        
        return list;
    }

    /** 
    * @Title: compare 
    * @Description: �Ƚ�����List of map������
    *  1. ���ݱȶԵĽ������ordItem����
    *  2. ��oMaps�м�¼�˺ϲ���nMap����������ݣ��������Զ������ʱ����Ҫ��
    * @param @param keys  ������Ψһȷ��List�е�һ��Mapֵ
    * @param @param nMaps ��ֵ
    * @param @param oMaps ��ֵ���ȽϺ󣬽�������ֵ�ĺϲ���Ϣ
    * @param @param common ������Ϣ����������ֶ�
    * @param @param noCompareStr ��Ҫ�Ƚϵ�Keys���ö��ŷָ�, ���Ϊ�գ��򲻱Ƚ�Ĭ�ϵ��ֶ�
    * @param @return    
    * @return List    List of OrderItem
    * @throws 
    */ 
    public static List compare(String[] keys, List nMaps, List oMaps, Map common, String noCompareStr) { 
        //��һ �ж����
        if (null == keys || null == nMaps || null == oMaps || null == common) {
            return null;
        }
        
        List allMaps = new ArrayList(); //��¼�ϲ���Ķ���
        List res = new ArrayList();  //res��¼���ɵĶ�����

        //�ڶ� ���nMap������Ϣ�б�Ƚϣ��ϲ�
        for (int i = 0; i < nMaps.size(); i++) {
            // 1.��oMaps���ҵ���ɾ�������ݡ�
            Map nMap = (Map) nMaps.get(i);
            Map oMap = locateAndRemove(nMap, keys, oMaps);
            // 2.���nMap�Ĳ�������Ϊ"K"����ô������, allMaps.add(oMap);
            if (KeyValues.ACTION_TYPE_K.equals(nMap.get(Keys.ACTION_TYPE))) {
                allMaps.add(oMap);
            }
            // 3.���nMap�Ĳ�������Ϊ"A"������oMap == null����ô�������Ӷ�����, allMaps.add(nMap);
            else if (KeyValues.ACTION_TYPE_A.equals(nMap.get(Keys.ACTION_TYPE)) && oMap == null) {
                res.add(OrdItemUtil.create(oMap, nMap, common));
                allMaps.add(nMap);
            }
            // 4.���nMap�Ĳ�������Ϊ"A"������oMap != null ��ô�����޸Ķ�����, allMaps.add(nMap);
            else if (KeyValues.ACTION_TYPE_A.equals(nMap.get(Keys.ACTION_TYPE)) && oMap != null) {                
                OrderItem ordItem = OrdItemUtil.update(oMap, nMap, common, noCompareStr);
                
                if (ordItem != null)
                    res.add(ordItem);
                allMaps.add(nMap);
            }
            // 5.���nMap�Ĳ�������Ϊ"D"������oMap == null����ô��������,
            else if (KeyValues.ACTION_TYPE_D.equals(nMap.get(Keys.ACTION_TYPE)) && oMap == null) {
                continue;
            }
            // 6.���nMap�Ĳ�������Ϊ"D"������oMap ��= null����ô����ɾ�������
            else if (KeyValues.ACTION_TYPE_D.equals(nMap.get(Keys.ACTION_TYPE)) && oMap != null) {
                res.add(OrdItemUtil.delete(oMap, nMap, common));
            }
            // 7.���nMap�Ĳ�������Ϊ"M"������oMap != null�� ��ô�����޸Ķ���; allMaps.add(nMap);
            else if (KeyValues.ACTION_TYPE_M.equals(nMap.get(Keys.ACTION_TYPE)) && oMap != null) {     
                OrderItem ordItem = OrdItemUtil.update(oMap, nMap, common, noCompareStr);
                
                if (ordItem != null)
                    res.add(ordItem);
                allMaps.add(nMap);
            }
            // 8.���nMap�Ĳ�������Ϊ"M"������oMap==null, ��ô�������Ӷ�����:
            else if (KeyValues.ACTION_TYPE_M.equals(nMap.get(Keys.ACTION_TYPE)) && oMap == null) {
                res.add(OrdItemUtil.create(oMap, nMap, common));
                allMaps.add(nMap);
            }
            else {
                throw new BusiException("�Ƚ�ʱ��û�д����������!");
            }
        }
        
        // �������ϲ�allMaps��oMaps�� oMaps.addAll(allMaps);
        oMaps.addAll(allMaps);
        
        // ���ģ����ؽ��������res;
        return res;
    }

	/** 
	* @Title: compare 
	* @Description: �Ƚ�����List of map������
	*  1. ���ݱȶԵĽ������ordItem����
	*  2. ��oMaps�м�¼�˺ϲ���nMap����������ݣ��������Զ������ʱ����Ҫ��
	* @param @param keys  ������Ψһȷ��List�е�һ��Mapֵ
	* @param @param nMaps ��ֵ
	* @param @param oMaps ��ֵ���ȽϺ󣬽�������ֵ�ĺϲ���Ϣ
	* @param @param common ������Ϣ����������ֶ�
	* @param @return    
	* @return List    List of OrderItem
	* @throws 
	*/ 
	public static List compare(String[] keys, List nMaps, List oMaps, Map common) {		
		
		return compare(keys, nMaps, oMaps, common, null);
	}

	/** 
	* @Title: diff 
	* @Description: �Ƚ�������Ϣ�����������Ϣ��ͬ������ֵ�滻��ֵ
	* @param @param oMap ����Ϣ,�ȽϺ󣬽������Ϻϲ���Ϣ
	* @param @param nMap ����Ϣ
	* @param @return   
	* @return List    List of OrderItemDetail
	* @throws 
	*/ 
	public static List diff(Map oMap,Map nMap) {
		if (null == oMap || null == nMap) {
			return null;
		}
		
		// ����nMap�еĵ�ÿ��ֵ��
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
			
			//�����ַ�����Ϊ"",��ֹ�����ݿⱨ��
			String newvalue = Const.getStrValue(nMap, key);
			String oldvalue = Const.getStrValue(oMap, key);
			
			// ���value != oldValue����ô������Ϣ���{
			if (!newvalue.equals(oldvalue)) {
				OrderItemDetail ordItemDetail = new OrderItemDetail();
				ordItemDetail.set(Keys.FIELD_NAME, key);
				ordItemDetail.set(Keys.FIELD_VALUE, newvalue);
				ordItemDetail.set(Keys.OLD_FIELD_VALUE, oldvalue);
				res.add(ordItemDetail);
				
				//����ֵ�滻��ֵ
				//oMap.put(key, newvalue);					
			}
		}
		
		return res;
	}
    
    
    /** 
    * @Title: diff 
    * @Description: �Ƚ�������Ϣ�����������Ϣ��ͬ������ֵ�滻��ֵ
    * @param @param oMap ����Ϣ,�ȽϺ󣬽������Ϻϲ���Ϣ
    * @param @param nMap ����Ϣ
    * @param @param noCompareKeys ���Ƚϵ�Keyֵ���ö��ŷָ�
    * @param @return   
    * @return List    List of OrderItemDetail
    * @throws 
    */ 
    public static List diff(Map oMap,Map nMap, String noCompareKeys) {
        if (null == oMap || null == nMap || null == noCompareKeys) {
            return null;
        }        
        //���ؽ����List of OrderItem
        List res = new ArrayList();
        
        Iterator iter_nMap = nMap.keySet().iterator();
        while (iter_nMap.hasNext()) {            
            String key = iter_nMap.next().toString();
            if (null == key) {
                continue;
            }
            String tempKey = KeyValues.SPLIT_STRING + key + KeyValues.SPLIT_STRING;
            
            //�ж��Ƿ�Ϊ����Ҫ�Ƚϵ��ֶ�
            if (noCompareKeys.indexOf(tempKey) >= 0) {
               continue;
            }
             
            //�����ַ�����Ϊ"",��ֹ�����ݿⱨ��
            String newvalue = Const.getStrValue(nMap, key);
            String oldvalue = Const.getStrValue(oMap, key);
            
            // ���value != oldValue����ô������Ϣ���{
            if (!newvalue.equals(oldvalue)) {
                OrderItemDetail ordItemDetail = new OrderItemDetail();
                ordItemDetail.set(Keys.FIELD_NAME, key);
                ordItemDetail.set(Keys.FIELD_VALUE, newvalue);
                ordItemDetail.set(Keys.OLD_FIELD_VALUE, oldvalue);
                res.add(ordItemDetail);
                
                //����ֵ�滻��ֵ
                //oMap.put(key, newvalue);                    
            }
        }
        
        return res;
    }

	// ����������λһ����¼locate
	/** 
	* @Title: locate 
	* @Description: �������������ϼ�¼�б��ж�λһ��������¼����ֵһ���ļ�¼
	* @param @param nMap �¼�¼
	* @param @param keys ����
	* @param @param oMaps �ϼ�¼�б�
	* @param @return   
	* @return Map    ����������λ���ϼ�¼��Ϣ
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
				
				//����nmap�в�����������Ϣ��Ӧ����Ҫ���쳣��
				if (null == nValue) {
					System.out.println("nMap �в���������[" + key + "]��Ϣ");
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
	* @Description: ����������λһ����¼,�Ҵ�oMaps��ɾ���ҵ��ļ�¼
	* @param @param nMap ������Ϣ
	* @param @param keys ����
	* @param @param oMaps Ŀ���б�
	* @param @return    
	* @return Map    �ҵ��Ҵ�Ŀ���б���ɾ���ļ�¼��Ϣ
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

	// �������Բ�ѯ�����¼��ɾ��findAndRemove
	public static List findAndRemove(Map nMap, String[] keys, List subMaps) {
		List list = find(nMap, keys, subMaps);
		
		if (null == list || list.isEmpty()) {
			return list;
		}
		
		//ɾ����¼
		for (int i = 0; i < list.size(); i++) {
			Map delmap = (Map)list.get(i);
			
			subMaps.remove(delmap);
		}
		
		return list;
	}

	// �������Բ�ѯ�����¼find
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
				
				//�ڻ�����Ϣ��û���ҵ�key��Ӧ��ֵ
				if (null == nValue) {
					System.out.println("nMap �в���������[" + key + "]��Ϣ");
					return null;
				}				
				
				if (nValue.equals(oValue)) {
					k += 1;
				}
			}
			
			//������ȫƥ��
			if (k == num) {
				list.add(oMap);
			}
		}
		
		return list;
	}
    
    
    //�������ʱ������ǿ��ֶΣ�����״̬ʱ��
    public static void addData(OrderItem ordItem) {
         
         if (null == ordItem.get(Keys.ORD_ITEM_ID) || "".equals(ordItem.get(Keys.ORD_ITEM_ID))) {              
             ordItem.set(Keys.ORD_ITEM_ID, OrdItemIdManager.getNextVal());
         }
         
         if (null == ordItem.get(Keys.STATE_DATE) || "".equals(ordItem.get(Keys.STATE_DATE))) {                
             ordItem.set(Keys.STATE_DATE, DateFormatUtils.getFormatedDate());
         }
         
         ordItem.set(Keys.PRICE_ID, "-1"); //����Ʒ��Ӧ���Ʒ�д��
    }
    

}
