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
public class ProductConfigService extends DictService{ 
	   
	   /**
	    * ��ò�ƷĿ¼�ڵ�
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
	    * ��ò�ƷĿ¼
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
	    * ���Ӳ�ƷĿ¼
	    * @param map
	    * @throws Exception
	    */
	   public void  addProdCata(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"addProdCata");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   /**
	    * �޸Ĳ�ƷĿ¼
	    * @param map
	    * @throws Exception
	    */
	   public void  updateProdCata(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"updateProdCata");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   /**
	    * ��ѯ��ƷĿ¼
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
	    * ��ѯ��ƷĿ¼�ڵ�Ԫ��
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
	    * ���Ӳ�ƷĿ¼�ڵ�Ԫ��
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
	    * �޸Ĳ�ƷĿ¼�ڵ�Ԫ��
	    * @param map
	    * @throws Exception
	    */
	   public void  updateProdCataItem(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"updateProdCataItem");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }

	   /**
	    * ɾ����ƷĿ¼�ڵ�Ԫ��
	    * @param map
	    * @throws Exception
	    */
	   public void  deleteProdCataItem(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"deleteProdCataItem");
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
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"judgeFinalNode");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
		      return ((HashMap)dto.getValueByName("RESULT"));
	   }
	   
	   /**
	    * ���ӻ��޸�����Ʒ
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
	    * ��ѯ����Ʒ
	    * @param map
	    * @return List
	    * @throws Exception
	    */
	   public  List qryProdOff(HashMap map)throws Exception{
		       List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODUCTCONFIGBO" ,"qryProdOff" ,map));
			   return list;
	   }
	   
	   
	   /**
	    * ��ѯ����Ʒ��ϸ
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public  List qryProdOffDeta(HashMap map)throws Exception{
	       List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODUCTCONFIGBO" ,"qryProdOffDeta",map));
		   return list;
       }

	   /**
	    * ��ѯ���ڵ�
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
	    * ���ز�Ʒ��������Ʒ��ѯ���
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
	    * ��ѯ����ƷĿ¼�ڵ�Ԫ��
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public  List qryProdOffCataItemEle(HashMap map)throws Exception{
	       List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODUCTCONFIGBO" ,"qryProdOffCataItemEle",map));
		   return list;
       }

	   
	   /**
	    * ɾ����ƷĿ¼�ڵ�Ԫ��
	    * @param map
	    * @throws Exception
	    */
	   public void  saveOrUpdateCataItemEle(HashMap map)throws Exception{
		      DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"saveOrUpdateCataItemEle");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
	   }

	   
	   
	   
	   
	   

	   
}
