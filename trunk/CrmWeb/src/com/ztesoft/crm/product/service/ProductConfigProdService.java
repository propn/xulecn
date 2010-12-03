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
 * ��Ʒ����
 * @author liruxin
 *
 */
public class ProductConfigProdService extends DictService{ 
	   
	   /**
	    * ��ò�ƷĿ¼�ڵ�
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
	    * ��ò�ƷĿ¼
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
	    * ���Ӳ�ƷĿ¼
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
	    * �޸Ĳ�ƷĿ¼
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
	    * ��ѯ��ƷĿ¼
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
	    * ��ѯ��ƷĿ¼�ڵ�Ԫ��
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
	    * ���Ӳ�ƷĿ¼�ڵ�Ԫ��
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
	    * �޸Ĳ�ƷĿ¼�ڵ�Ԫ��
	    * @param map
	    * @throws Exception
	    */
	   public void  updateProdCataItem(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGPRODBO" ,"updateProdCataItem");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }

	   /**
	    * ɾ����ƷĿ¼�ڵ�Ԫ��
	    * @param map
	    * @throws Exception
	    */
	   public void  deleteProdCataItem(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGPRODBO" ,"deleteProdCataItem");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   /**
	    * �жϵ�ǰ�ڵ��ǲ������¼��ڵ�
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
