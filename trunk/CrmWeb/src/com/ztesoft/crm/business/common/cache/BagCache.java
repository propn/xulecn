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
	/*//加载所有套餐的数据到缓存中
	public static List getList(){
		
		//取所有的套餐数据缓存起来
		return bagDao.findBySql(DcProdProductbagDAOImpl.SQL_ALL_SELECT);   // 
	
	}*/
	
	private final static String KEY_PREFIX="__BAG_KEY_LAN_ID";
	
	public static void loadData(){
/*		List list=getList();
		List mapKeys=new ArrayList();
		//一层循环进行分组操作
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
	//通过本地网标识和品牌的标识获取所有套餐的数据
	public static List getBagsByLanIdAndBrandId(String lanId,String brandId){
		
		List result=new ArrayList();
		//获取当前本地网上所有的套餐
		List currentLanList=(List) attrCache.get(KEY_PREFIX+lanId);
		Iterator it=currentLanList.iterator();
		while(it.hasNext()){
			DcProdProductbagVO vo=(DcProdProductbagVO)it.next();
			//对比品牌是否一致
			if(vo.getBrandId().equals(brandId)){
				result.add(vo);
			}
		}
		return result;
	}
	//通过本地网标识和套餐的名称获取所有套餐的数据
	public static List getBagsByLanIdAndBagName(String lanId,String bagName){	
		List result=new ArrayList();
		//获取当前本地网上所有的套餐
		List currentLanList=(List) attrCache.get(KEY_PREFIX+lanId);
		Iterator it=currentLanList.iterator();
		while(it.hasNext()){
			DcProdProductbagVO vo=(DcProdProductbagVO)it.next();
			//对比品牌是否一致
			if(vo.getProbagName().indexOf(bagName)>-1){
				result.add(vo);
			}
		}
		return result;
		
	}
	*/
	}
}
