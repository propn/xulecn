package com.ztesoft.crm.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;

/**
 * 
 * @author liruxin
 * @comment ����Ʒ���÷����� 
 */
public class ProdOffConfigService extends DictService {
	   
	   /**
	    * ��������Ʒ��ϸ
	    * @param map
	    * @throws Exception
	    */
	   public void saveProdOffDeta(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveProdOffDeta");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }
	   
	   /**
	    * ���Ӳ�Ʒ��ɫ
	    * @param ProdCompDetaRole
	    * @return 
	    * @throws Exception
	    */
	   public boolean insertProdCompDetaRole(HashMap ProdCompDetaRole ) throws Exception{
			boolean result = DataTranslate._boolean(
					ServiceManager.callJavaBeanService("PRODOFFCONFIGBO",
							"insertProdCompDetaRole" ,ProdCompDetaRole)) ;
			return result ;
	   }
	   
	   /**
	    * ��ѯ��Ʒ��ɫ
	    * @param role_id
	    * @param role_name
	    * @param pageIndex
	    * @param pageSize
	    * @return PageModel
	    * @throws Exception
	    */ 
	   public PageModel searchProdCompDetaRoleData(String role_id,String role_name,
				int pageIndex , int pageSize) throws Exception{
			Map param = new HashMap() ;
			if (role_id!=null && !"".equals(role_id))
			    param.put("role_id", role_id);
			if (role_name!=null && !"".equals(role_name))
			    param.put("role_name",role_name) ;
			param.put("pageIndex", new Integer(pageIndex)) ;
			param.put("pageSize", new Integer(pageSize));
			PageModel result = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService("PRODOFFCONFIGBO",
							"searchProdCompDetaRoleData" ,param));
			return result;
		}
	   
	   
	   /**
	    * ��ѯ��Ŀ����
	    * @param acct_item_name
	    * @param pageIndex
	    * @param pageSize
	    * @return PageModel
	    * @throws Exception
	    */
	   public PageModel qryAcctItemType(String acct_item_name,
				int pageIndex , int pageSize) throws Exception{
			Map param = new HashMap() ;
			if (acct_item_name!=null && !"".equals(acct_item_name))
			    param.put("acct_item_name", acct_item_name);
			param.put("pageIndex", new Integer(pageIndex)) ;
			param.put("pageSize", new Integer(pageSize));
			PageModel result = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService("PRODOFFCONFIGBO",
							"qryAcctItemType" ,param));
			return result;
		}

	   
	   
	   /**
	    * ��ѯ�����ṩ
	    * @param action_id
	    * @param service_offer_name
	    * @param pageIndex
	    * @param pageSize
	    * @return  PageModel
	    * @throws Exception
	    */
	   public PageModel qryServOff(String action_id,String service_offer_name,
				int pageIndex , int pageSize) throws Exception{
			Map param = new HashMap() ;
			if (action_id!=null && !"".equals(action_id))
			    param.put("action_id", action_id);
			if (service_offer_name!=null && !"".equals(service_offer_name))
			    param.put("serv_offer_name",service_offer_name) ;
			param.put("pageIndex", new Integer(pageIndex)) ;
			param.put("pageSize", new Integer(pageSize));
			PageModel result = DataTranslate._PageModel(
		    ServiceManager.callJavaBeanService("PRODOFFCONFIGBO","qryServOff" ,param));
			return result;
		}
	   
	   
	   /**
	    * ��ѯ��Ʒ�����ṩ
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public List qryProdOffServ(HashMap map)  throws Exception{
		      List list=   DataTranslate._List(
		    		 ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryProdOffServ",map));
		      return list;
	   }
	   
	   
	   /**
	    * ��������Ʒ�����ṩ
	    * @param map
	    * @throws Exception
	    */
	   public void saveProdOffServ(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveProdOffServ");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }

	   
	   /**
	    * ��ѯ����Ʒ��������
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public List qryProdOffRegion(HashMap map)  throws Exception{
		      List list=   DataTranslate._List(
		    		 ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryProdOffRegion",map));
		      return list;
	   }
       
	   
	   /**
	    * ��������Ʒ��������
	    * @param map
	    * @throws Exception
	    */
	   public void saveOrUpdateProdOffRegion(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOrUpdateProdOffRegion");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }
	   
	   /**
	    * ��������Ʒ����
	    * @param map
	    * @throws Exception
	    */
	   public void saveOrUpdateProdOffAttr(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOrUpdateProdOffAttr");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }

	   /**
	    * ��ѯ����Ʒ����
	    * @param map
	    * @return List
	    * @throws Exception
	    */
	   public List qryProdOffAttr(HashMap map)  throws Exception{
		      List list=   DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryProdOffAttr",map));
		      return list;
	   }
	   
	   /**
	    * ��ѯ����Ʒ��ϵ
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public List qryProdOffRela(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryProdOffRela",map));
		      return list;
	   }
	   
	   /**
	    * ��������Ʒ��ϵ
	    * @param map
	    * @throws Exception
	    */
	   public HashMap saveOrUpdateProdOffRela(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOrUpdateProdOffRela");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
		      return ((HashMap)dto.getValueByName("RESULT")) ;
	   }

	   
	   /**
	    * ��ѯ����Ʒ��ϵ����
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public List qryProdOffRelaGrp(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryProdOffRelaGrp",map));
		      return list;
	   }
	   
	   
	   /**
	    * ��������Ʒ��ϵ
	    * @param map
	    * @throws Exception
	    */
	   public void saveOrUpdateProdOffRelaGrp(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOrUpdateProdOffRelaGrp");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
		      
	   }
	   
	   
	   /**
	    * ��ѯ����Ʒ��ϵ���鶨��
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public List qryProdOffRelaConfGrp(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryProdOffRelaConfGrp",map));
		      return list;
	   }
	   
	   
	   
	   /**
	    * ��������Ʒ��ϵ���鶨��
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public String addProdOffRelaConfGrp(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"addProdOffRelaConfGrp");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT")) ;
	   }
	   
	   
	   /**
	    * �޸�����Ʒ��ϵ���鶨��
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public String updateProdOffRelaConfGrp(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"updateProdOffRelaConfGrp");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT")) ;
	   }
	   
	   
	   /**
	    * ɾ������Ʒ��ϵ���鶨��
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public String delProdOffRelaConfGrp(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"delProdOffRelaConfGrp");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT")) ;
	   }
	   
	   
	   /**
	    * ��ѯ����Ʒ��ϵ���鶨��
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public List qryProdOffGrpRela(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryProdOffGrpRela",map));
		      return list;
	   }
	   
	   
	   
	   /**
	    * ��������Ʒ��ϵ
	    * @param map
	    * @throws Exception
	    */
	   public void saveOrUpdateProdOffGrpRela(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOrUpdateProdOffGrpRela");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }
	   
	   /**
	    * ��ѯ����Ʒ������Ϣ
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public List qryOffWarr(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryOffWarr",map));
		      return list;
	   }
	   
	   
	   /**
	    * ��������Ʒ������Ϣ
	    * @param map
	    * @throws Exception
	    */
	   public void saveOrDelOffWarr(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOrDelOffWarr");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }
	   
	   /**
	    * �޸�����Ʒ������Ϣ
	    * @param map
	    * @throws Exception
	    */
	   public void updateOffWarr(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"updateOffWarr");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }

	   /**
	    * ɾ������Ʒ������Ϣ
	    * @param map
	    * @throws Exception
	    */
	   public void deleteOffWarr(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"deleteOffWarr");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }

	   
	   /**
	    * ��ѯ��ϵ����
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public List qryRelaGrp(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryRelaGrp",map));
		      return list;
	   }
	   
	   /**
	    * ��ѯĿ¼�ڵ���������
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public List qryOffCataItemReg(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryOffCataItemReg",map));
		      return list;
	   }
	   
	   
	   /**
	    * ����Ŀ¼�ڵ���������
	    * @param map
	    * @throws Exception
	    */
	   public void saveOffCataItemReg(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOffCataItemReg");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }
	   
	   /**
	    * ��ѯ��ɫ
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public List qryRoles(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryRoles",map));
		      return list;
	   }

	   /**
	    * ��ѯĿ¼��ɫ
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public List qryProdCataItemRole(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryProdCataItemRole",map));
		      return list;
	   }
	   
	   
	   /**
	    * ��������ƷĿ¼��ɫ
	    * @param map
	    * @throws Exception
	    */
	   public void saveProdCataItemRole(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveProdCataItemRole");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }
	   
	   /**
	    * ��ѯ���۶�������
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public List qryPriceObjectOwner(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryPriceObjectOwner",map));
		      return list;
	   }
       
	   
	   /**
	    * ��ѯӪҵ����
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public List qryTpmBusiPrice(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryTpmBusiPrice",map));
		      return list;
	   }
	   
	   
	   /**
	    * ����Ӫҵ����
	    * @param map
	    * @throws Exception
	    */
	   public void saveOrUpdateTpmBusiPrice(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOrUpdateTpmBusiPrice");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }
	   
	   
	   /**
	    * ��ö��۱�ʶ
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public String getNewPriceId(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"getNewPriceId");
		      dto.setValueByName("parameter",map) ;
		      dto = ActionDispatch.dispatch(dto);
		      return ((String)dto.getValueByName("RESULT")) ;
	   }
	   
	   /**
	    * ��ѯ���ۼƻ�
	    * @param plan_object_type
	    * @param plan_object_id
	    * @param price_id
	    * @return list
	    * @throws Exception
	    */
	   public List qryPricePlan(String plan_object_type,String plan_object_id,String price_id)throws Exception{
		      HashMap map=new HashMap();
		      map.put("plan_object_type",plan_object_type);
		      map.put("plan_object_id",plan_object_id);
		      map.put("price_id",price_id);
		      List list=DataTranslate._List(
		    		 ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryPricePlan",map));
		      return list;
	   }
	   
	   
	   /**
	    * ��������Ʒ���ۼƻ�
	    * @param map
	    * @throws Exception
	    */
	   public void saveOffPricePlan(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOffPricePlan");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
	   }

	   /**
	    * ɾ������Ʒ���ۼƻ�
	    * @param map
	    * @throws Exception
	    */
	   public void deleteOffPricePlan(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"deleteOffPricePlan");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }

	   /**
	    * ��ѯ��Ʒ����
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public List qryProdAttr(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryProdAttr",map));
		      return list;
	   }
	   
	   /**
	    * ��ѯӪҵ���۲���
	    * @param plan_object_type
	    * @param plan_object_id
	    * @param price_id
	    * @return list
	    * @throws Exception
	    */
	   public List qryOffPriceParam(String plan_object_type,String plan_object_id,String price_id)throws Exception{
		      HashMap map=new HashMap();
		      map.put("plan_object_type",plan_object_type);
		      map.put("plan_object_id",plan_object_id);
		      map.put("price_id",price_id);
		      List list=DataTranslate._List(
		    		 ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryOffPriceParam",map));
		      return list;
	   }
       
	   
	   /**
	    * �������¶��۲���
	    * @param map
	    * @throws Exception
	    */
	   public void saveOrUpdateOffPriceParam(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOrUpdateOffPriceParam");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
	   }

	   /**
	    * ɾ�����۲���
	    * @param map
	    * @throws Exception
	    */
	   public void delPriceParam(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"delPriceParam");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto); 
	   }
	   
	   /**
	    * ��ѯ��ϵ����
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public List qryOffRelaDef(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryOffRelaDef",map));
		      return list;
	   }
	   
	   
	   /**
	    * �����ϵ����
	    * @param map
	    * @throws Exception
	    */
	   public void saveOffRelaDef(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOffRelaDef");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   /**
	    * ���¹�ϵ����
	    * @param map
	    * @throws Exception
	    */
	   public void updateOffRelaDef(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"updateOffRelaDef");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   
	   /**
	    * ɾ����ϵ����
	    * @param map
	    * @throws Exception
	    */
	   public void deleteOffRelaDef(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"deleteOffRelaDef");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   /**
	    * ��ѯ����Ʒ�ڳ�Ա��ϵ����
	    * @param map
	    * @return list
	    * @throws Exception
	    */
	   public List qryOffRelaRest(HashMap map)  throws Exception{
		      List list=DataTranslate._List(ServiceManager.callJavaBeanService("PRODOFFCONFIGBO" ,"qryOffRelaRest",map));
		      return list;
	   }
	   
	   
	   /**
	    * ��������Ʒ�ڳ�Ա��ϵ����
	    * @param map
	    * @throws Exception
	    */
	   public void saveOffRelaRest(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"saveOffRelaRest");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   
	   /**
	    * �޸�����Ʒ�ڳ�Ա��ϵ����
	    * @param map
	    * @throws Exception
	    */
	   public void updateOffRelaRest(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"updateOffRelaRest");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
	   }
	   
	   /**
	    * ɾ������Ʒ�ڳ�Ա��ϵ����
	    * @param map
	    * @throws Exception
	    */
	   public void deleteOffRelaRest(HashMap map) throws Exception{
		      DynamicDict dto = getServiceDTO("PRODOFFCONFIGBO" ,"deleteOffRelaRest");
		      dto.setValueByName("parameter",map);
		      dto = ActionDispatch.dispatch(dto);
	   }





	   
	   





	   
	   





	   
	   


	   
	   
	   



	   
	   
	   
	   










	   
	   

	   

	   
	   
	   
	   
	   




        
}
