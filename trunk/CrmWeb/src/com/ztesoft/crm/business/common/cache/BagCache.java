package com.ztesoft.crm.business.common.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
public final class BagCache {
	
	public BagCache(){}

	/*static DcProdProductbagDAO bagDao=new DcProdProductbagDAOImpl();*/
	
	private static Map attrCache = Collections.synchronizedMap(new HashMap());
	
	static{
		loadData();
	}
	/*//���������ײ͵����ݵ�������
	public static List getList(){
		
		//ȡ���е��ײ����ݻ�������
		return bagDao.findBySql(DcProdProductbagDAOImpl.SQL_ALL_SELECT);   // 
	
	}*/
	
	private final static String KEY_PREFIX="__BAG_KEY_LAN_ID";
	
	public static void loadData(){
/*		List list=getList();
		List mapKeys=new ArrayList();
		//һ��ѭ�����з������
		Iterator it=list.iterator();	
		while(it.hasNext()){
			DcProdProductbagVO vo=(DcProdProductbagVO)it.next();
			String key=KEY_PREFIX+vo.getLanId();
			if(!mapKeys.contains(key)){
				mapKeys.add(key);
				attrCache.put(key, new ArrayList());
			}
			((ArrayList)attrCache.get(key)).add(vo);
		}
	}
	//ͨ����������ʶ��Ʒ�Ƶı�ʶ��ȡ�����ײ͵�����
	public static List getBagsByLanIdAndBrandId(String lanId,String brandId){
		
		List result=new ArrayList();
		//��ȡ��ǰ�����������е��ײ�
		List currentLanList=(List) attrCache.get(KEY_PREFIX+lanId);
		Iterator it=currentLanList.iterator();
		while(it.hasNext()){
			DcProdProductbagVO vo=(DcProdProductbagVO)it.next();
			//�Ա�Ʒ���Ƿ�һ��
			if(vo.getBrandId().equals(brandId)){
				result.add(vo);
			}
		}
		return result;
	}
	//ͨ����������ʶ���ײ͵����ƻ�ȡ�����ײ͵�����
	public static List getBagsByLanIdAndBagName(String lanId,String bagName){	
		List result=new ArrayList();
		//��ȡ��ǰ�����������е��ײ�
		List currentLanList=(List) attrCache.get(KEY_PREFIX+lanId);
		Iterator it=currentLanList.iterator();
		while(it.hasNext()){
			DcProdProductbagVO vo=(DcProdProductbagVO)it.next();
			//�Ա�Ʒ���Ƿ�һ��
			if(vo.getProbagName().indexOf(bagName)>-1){
				result.add(vo);
			}
		}
		return result;
		
	}
	*/
	}
}
