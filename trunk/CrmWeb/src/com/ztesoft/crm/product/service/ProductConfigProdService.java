package com.ztesoft.crm.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
/**
 * 产品配置
 * @author liruxin
 *
 */
public class ProductConfigProdService extends DictService{ 
	   
	   /**
	    * 获得产品目录节点
	    * @param map
	    * @return String
	    * @throws Exception
	    */
	   public String getProductCatalogItem(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGPRODBO" ,"getProductCatalogItem");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT")) ;
	   }
	   
	   /**
	    * 获得产品目录
	    * @param map
	    * @return String
	    * @throws Exception
	    */
	   public String getProductCatalog(HashMap map) throws Exception{
		    Map param = new HashMap() ; 
			param.put("ProductCatalog", map) ;			
			String result = DataTranslate._String(
					ServiceManager.callJavaBeanService("PRODUCTCONFIGPRODBO",
							"getProductCatalog" ,param)) ;
			return result ;
	   }
	   
	   /**
	    * 增加产品目录
	    * @param map
	    * @throws Exception
	    */
	   public String  addProdCata(HashMap map) throws Exception{			
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGPRODBO" ,"addProdCata");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT"));
	   }
	   
	   /**
	    * 修改产品目录
	    * @param map
	    * @throws Exception
	    */
	   public String  updateProdCata(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGPRODBO" ,"updateProdCata");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT"));
	   }
	   
	   /**
	    * 查询产品目录
	    * @param map
	    * @return List
	    * @throws Exception
	    */
	   public List qryProdCata(HashMap map)  throws Exception{
		      List list=   DataTranslate._List(
		    		 ServiceManager.callJavaBeanService("PRODUCTCONFIGPRODBO" ,"qryProdCata" , map));
		      return list;
	   }
	   
	   /**
	    * 查询产品目录节点元素
	    * @param map
	    * @return  List
	    * @throws Exception
	    */
	   public  List getProdCataItems(HashMap map)throws Exception{
			   DynamicDict dto = getServiceDTO("PRODUCTCONFIGPRODBO" ,"getProdCataItems");
			   dto.setValueByName("parameter",map);
			   dto=ActionDispatch.dispatch(dto);
			   return ((ArrayList)dto.getValueByName("RESULT"));
	   }
	   
	   /**
	    * 增加产品目录节点元素
	    * @param map
	    * @throws Exception
	    */
	   public String  addProdCataItem(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGPRODBO" ,"addProdCataItem");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT"));
	   }
	   
	   /**
	    * 修改产品目录节点元素
	    * @param map
	    * @throws Exception
	    */
	   public void  updateProdCataItem(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGPRODBO" ,"updateProdCataItem");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }

	   /**
	    * 删除产品目录节点元素
	    * @param map
	    * @throws Exception
	    */
	   public void  deleteProdCataItem(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGPRODBO" ,"deleteProdCataItem");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   /**
	    * 判断当前节点是不是最下级节点
	    * @param map
	    * @return HashMap
	    * @throws Exception
	    */
	   public HashMap judgeFinalNode(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGPRODBO" ,"judgeFinalNode");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
		      return ((HashMap)dto.getValueByName("RESULT"));
	   }

	   
}
