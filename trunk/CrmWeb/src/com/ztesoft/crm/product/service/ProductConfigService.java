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
public class ProductConfigService extends DictService{ 
	   
	   /**
	    * 获得产品目录节点
	    * @param map
	    * @return String
	    * @throws Exception
	    */
	   public String getProductCatalogItem(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"getProductCatalogItem");
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
			//System.out.println("+++++++++ProductBureau+++++++++++"+param.toString());
			String result = DataTranslate._String(
					ServiceManager.callJavaBeanService("PRODUCTCONFIGBO",
							"getProductCatalog" ,param)) ;
			return result ;
		   
		     /* DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"getProductCatalog");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT")) ;*/
	   }
	   
	   /**
	    * 增加产品目录
	    * @param map
	    * @throws Exception
	    */
	   public void  addProdCata(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"addProdCata");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   /**
	    * 修改产品目录
	    * @param map
	    * @throws Exception
	    */
	   public void  updateProdCata(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"updateProdCata");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   /**
	    * 查询产品目录
	    * @param map
	    * @return List
	    * @throws Exception
	    */
	   public List qryProdCata(HashMap map)  throws Exception{
		      List list=   DataTranslate._List(
		    		 ServiceManager.callJavaBeanService("PRODUCTCONFIGBO" ,"qryProdCata" , map));
		      return list;
	   }
	   
	   /**
	    * 查询产品目录节点元素
	    * @param map
	    * @return  List
	    * @throws Exception
	    */
	   public  List getProdCataItems(HashMap map)throws Exception{
			   DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"getProdCataItems");
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
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"addProdCataItem");
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
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"updateProdCataItem");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }

	   /**
	    * 删除产品目录节点元素
	    * @param map
	    * @throws Exception
	    */
	   public void  deleteProdCataItem(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"deleteProdCataItem");
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
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"judgeFinalNode");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
		      return ((HashMap)dto.getValueByName("RESULT"));
	   }
	   
	   /**
	    * 增加或修改销售品
	    * @param map
	    * @return HashMap
	    * @throws Exception
	    */
	   public HashMap saveOrUpdateProdOff(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"saveOrUpdateProdOff");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
		      return ((HashMap)dto.getValueByName("RESULT"));
	   }
	   
	   
	   /**
	    * 查询销售品
	    * @param map
	    * @return List
	    * @throws Exception
	    */
	   public  List qryProdOff(HashMap map)throws Exception{
		       List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODUCTCONFIGBO" ,"qryProdOff" ,map));
			   return list;
	   }
	   
	   
	   /**
	    * 查询销售品明细
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public  List qryProdOffDeta(HashMap map)throws Exception{
	       List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODUCTCONFIGBO" ,"qryProdOffDeta",map));
		   return list;
       }

	   /**
	    * 查询根节点
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public  String getProdCataRoot(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"getProdCataRoot");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT"));
       }
	   
	   
	   /**
	    * 返回产品或者销售品查询结果
	    * @param map
	    * @return String
	    * @throws Exception
	    */
	   public String getCataItemEleQryResult(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"getCataItemEleQryResult");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT"));
	   }

	   
	   /**
	    * 查询销售品目录节点元素
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public  List qryProdOffCataItemEle(HashMap map)throws Exception{
	       List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODUCTCONFIGBO" ,"qryProdOffCataItemEle",map));
		   return list;
       }

	   
	   /**
	    * 删除产品目录节点元素
	    * @param map
	    * @throws Exception
	    */
	   public void  saveOrUpdateCataItemEle(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"saveOrUpdateCataItemEle");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }

	   
	   
	   
	   
	   

	   
}
