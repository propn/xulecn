package com.ztesoft.crm.product.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
/*******************
 * ҵ��������
 * @author cf
 *@since 2010-01
 *******************/
public class ServiceOfferService extends DictService{

	   /**
	    * ��ȡ����Ŀ¼-��Ʒ����
	    * @param map
	    * @return String
	    * @throws Exception
	    */
	   public String getProductFamily(HashMap map) throws Exception{
		   Map param = new HashMap();
			String result = DataTranslate._String(
					ServiceManager.callJavaBeanService("ServiceOfferBo",
							"getProductFamily" ,param)) ;
			return result ;
	   }
	   /**
	    * ��ȡ����Ŀ¼-�������ͺͷ���
	    * @param map
	    * @return String
	    * @throws Exception
	    */
	   public List getActionType(HashMap map) throws Exception{
			return  DataTranslate._List(
					ServiceManager.callJavaBeanService("ServiceOfferBo",
							"getActionType" ,map)) ;
	   }
	   /**
	    * ���������Ϣ-��ѯ�����б�
	    * @param nodeId
	    * @param nodeType
	    * @param parentNodeId
	    * @param pageIndex
	    * @param pageSize
	    * @return
	    * @throws Exception
	    */
	   public PageModel searchServOffData(String nodeId,String nodeType,String parentNodeId,int pageIndex , int pageSize ) throws Exception{
		   Map param = new HashMap() ;
		   param.put("nodeId",nodeId);
		   param.put("nodeType",nodeType);
		   param.put("parentNodeId",parentNodeId);
		   param.put("pageIndex", new Integer(pageIndex)) ;
		   param.put("pageSize", new Integer(pageSize)) ;

		   return  DataTranslate._PageModel(
					ServiceManager.callJavaBeanService("ServiceOfferBo",
							"searchServOffData" ,param)) ;
	   }
	   /**
	    * ���������Ϣ-���ݽڵ��ѯ����form
	    * @param nodeId
	    * @return
	    * @throws Exception
	    */
	   public Map getServOffById(String nodeId)throws Exception{
		   Map param = new HashMap() ;
		   param.put("service_offer_id",nodeId);
		   return  DataTranslate._Map(
					ServiceManager.callJavaBeanService("ServiceOfferBo",
							"getServOffById" ,param)) ;
	   }
	   /**
	    * ���������Ϣ-����
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public boolean insertServOff(HashMap map) throws Exception{
		   return  DataTranslate._boolean(
					ServiceManager.callJavaBeanService("ServiceOfferBo",
							"insertServOff" ,map)) ;
	   }
	   /**
	    * ���������Ϣ-�޸�
	    * @param map
	    * @return
	    * @throws Exception
	    */
	   public boolean updateServOff(HashMap map) throws Exception{
		   return  DataTranslate._boolean(
					ServiceManager.callJavaBeanService("ServiceOfferBo",
							"updateServOff" ,map)) ;
	   }
	   /**
	    * ���������Ϣ-ɾ��
	    * @param servOffId
	    * @return
	    * @throws Exception
	    */
	   public boolean deleteServOffById(String servOffId) throws Exception{
		   Map param = new HashMap() ;
		   param.put("service_offer_id",servOffId);
		   return  DataTranslate._boolean(
					ServiceManager.callJavaBeanService("ServiceOfferBo",
							"deleteServOffById" ,param)) ;
	   }
	   /**
	    * ���������Ϣ-ȡҵ��ѡ���б�
	    * @param attrId
	    * @return
	    * @throws Exception
	    */
	   public List getAskFlow(String attrId) throws Exception{
		   Map param = new HashMap() ;
		   param.put("attr_id",attrId);
		   return  DataTranslate._List(
					ServiceManager.callJavaBeanService("ServiceOfferBo",
							"getAskFlow" ,param)) ;
	   }
	   /**
	    * ���񷢲����� ����
	    * @param ServBureau
	    * @return
	    * @throws Exception
	    */
	   public boolean saveServBureau(List ServBureau ) throws Exception {

		   Map param = new HashMap();
		   param.put("ServBureauList",ServBureau);
		   boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("ServBureauBO","saveServBureau",param));
		   return result;
		}
	   /**
	    * ҵ������-��ȡ��¼�������б�
	    * @return
	    * @throws Exception
	    */
	   public List getBureauLeftLan() throws Exception {
		    Map param = new HashMap() ;

			List result = DataTranslate._List(
					ServiceManager.callJavaBeanService("ServBureauBO",
							"getBureauLeftLan" ,param)) ;

			return result ;
	   }
	   /**
	    * ҵ������-��ȡ��¼������ ��Ӧ����
	    * @param regionId
	    * @param pageIndex
	    * @param pageSize
	    * @return
	    * @throws Exception
	    */
	   public PageModel getBureauLeftRegion(String regionId,int pageIndex,int pageSize) throws Exception {
		    Map param = new HashMap() ;
		    param.put("parent_region_id", regionId) ;
			param.put("pageIndex", new Integer(pageIndex)) ;
			param.put("pageSize", new Integer(pageSize)) ;
			PageModel result = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService("ServBureauBO",
							"getBureauLeftRegion" ,param)) ;

			return result ;
	   }
	   /**
	    * ҵ������-��ѯ��ѡ����
	    * @param servOfferID
	    * @param pageIndex
	    * @param pageSize
	    * @return
	    * @throws Exception
	    */
	   public PageModel searchServBureauData(String servOfferID,int pageIndex , int pageSize) throws Exception {
			Map param = new HashMap() ;
			param.put("service_offer_id",servOfferID);
			param.put("pageIndex", new Integer(pageIndex)) ;
			param.put("pageSize", new Integer(pageSize)) ;

			PageModel result = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService("ServBureauBO",
							"searchServBureauOld" ,param)) ;

			return result ;
		}


	   /**
	    * �����ϵ-��ѯ���÷����б�
	    * @param prod_cat_type
	    * @param action_type
	    * @param pageIndex
	    * @param pageSize
	    * @return
	    * @throws Exception
	    */
	   public PageModel searchServData(String prod_cat_type,String action_type,int pageIndex , int pageSize) throws Exception {
			Map param = new HashMap() ;
			param.put("prod_cat_type",prod_cat_type);
			param.put("action_type",action_type);
			param.put("pageIndex", new Integer(pageIndex)) ;
			param.put("pageSize", new Integer(pageSize)) ;

			PageModel result = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService("ServRelationBO",
							"searchServData" ,param)) ;

			return result ;
		}
	   /**
	    * �����ϵ-��ѯ��ѡ�����ϵ
	    * @param serv_a_id
	    * @param relation_type_id
	    * @return
	    * @throws Exception
	    */
	   public List searchServRelationData(String serv_a_id,String relation_type_id) throws Exception {
			Map param = new HashMap() ;
			param.put("serv_a_id",serv_a_id);
			param.put("relation_type_id",relation_type_id);

			return DataTranslate._List(
					ServiceManager.callJavaBeanService("ServRelationBO",
							"searchServRelationData" ,param)) ;
		}
	   /**
	    * �����ϵ-����
	    * @param servRela
	    * @return
	    * @throws Exception
	    */
	   public boolean saveServRelation(List servRela) throws Exception{

		   Map param = new HashMap();
		   param.put("servRelaList",servRela);
		   boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("ServRelationBO","saveServRelation",param));
		   return result;
	   }
	   /**
	    * �����ϵ-ɾ��
	    * @param relationId
	    * @return
	    * @throws Exception
	    */
	   public boolean deleteServRelationById(Map relationId) throws Exception{
		   boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("ServRelationBO",
					"deleteServRelationById" ,relationId)) ;
		   return result;
	   }
	   /**
	    * ��ϵȺ-��������ɾ��
	    * @param param
	    * @return
	    * @throws Exception
	    */
	   public boolean deleteServRelaGrp(Map param) throws Exception{
		   boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("ServRelationGrpBO",
					"deleteServRelaGrp" ,param)) ;
		   return result;
	   }

	   /**
	    * ��ϵȺ-������ϵ����Ⱥ
	    * @param confGrp
	    * @return
	    * @throws Exception
	    */
	   public boolean insertProductConfGroup(Map confGrp) throws Exception{
		   boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("ServRelationGrpBO",
					"insertProductConfGroup" ,confGrp)) ;
		   return result;
	   }
	   public boolean updateProductConfGroup(Map confGrp) throws Exception{
		   boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("ServRelationGrpBO",
					"updateProductConfGroup" ,confGrp)) ;
		   return result;
	   }
	   public boolean deleteServConfGrp(Map confGrp) throws Exception{
		   boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("ServRelationGrpBO",
					"deleteServConfGrp" ,confGrp)) ;
		   return result;
	   }
	   public Map getConfGroupById(String confGrpId) throws Exception{
		   Map confGrp = new HashMap();
		   confGrp.put("conf_group_id",confGrpId);
		   return DataTranslate._Map(ServiceManager.callJavaBeanService("ServRelationGrpBO",
					"getConfGroupById" ,confGrp)) ;
	   }
	   /**
	    * ��ϵȺ-��ù�ϵȺҵ��ʵ��
	    * @param servOffId
	    * @param relaType
	    * @param grpId
	    * @return
	    * @throws Exception
	    */
	   public List getServRelaGrpOld(String servOffId,String relaType,String grpId) throws Exception{
		   Map param = new HashMap();
		   param.put("service_offer_id",servOffId);
		   param.put("relation_type_id",relaType);
		   param.put("group_id",grpId);
		   List result = DataTranslate._List(ServiceManager.callJavaBeanService("ServRelationGrpBO","getServRelaGrpOld",param));
		   return result;
	   }
	   /**
	    * ��ϵȺ-�����ϵȺҵ��ʵ��
	    * @param relaGrp
	    * @return
	    * @throws Exception
	    */
	   public boolean saveServRelaGrp(List relaGrp) throws Exception{
		   Map param = new HashMap();
		   param.put("relaGrpList",relaGrp);
		   boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("ServRelationGrpBO","saveServRelaGrp",param));
		   return result;
	   }
	   /**
	    * ��ϵȺ-��ȡ���й�ϵȺ��
	    * @param servOffId
	    * @return
	    * @throws Exception
	    */
	   public List getServRelaGroupALL(String servOffId) throws Exception{
		   Map param = new HashMap();
		   //param.put("service_offer_id",servOffId);
		   List result = DataTranslate._List(ServiceManager.callJavaBeanService("ServRelationGrpBO","getServRelaGroupALL",param));
		   return result;
	   }
	   /**
	    * ��ϵȺ-���ݷ���ID�������Ⱥ��
	    * @param servOffId
	    * @return
	    * @throws Exception
	    */
	   public List getServRelaGroup(String servOffId) throws Exception{
		   Map param = new HashMap();
		   param.put("service_offer_id",servOffId);
		   List result = DataTranslate._List(ServiceManager.callJavaBeanService("ServRelationGrpBO","getServRelaGroup",param));
		   return result;
	   }

	   /**
	    * ҵ�����Կ���-��ȡ����ID
	    * @param objType
	    * @param servoffId
	    * @return
	    * @throws Exception
	    */
	   public List getObjectId(String objType,String servoffId) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("service_offer_id", servoffId) ;
		   if("10A".equals(objType)){
			   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_CARD_TYPE_CONTROL_0006", param)) ;
		   }
		   if("10C".equals(objType)){
			   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_CARD_TYPE_CONTROL_0007", param)) ;
		   }
		   return rtlist;
	   }
	   /**
	    * ҵ�����Կ���-��ȡ�ֶ�
	    * @param objType
	    * @param objId
	    * @return
	    * @throws Exception
	    */
	   public List getFiledName(String objType,String objId) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();

		   if("10A".equals(objType)){
			   param.put("product_id", objId) ;
			   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_CARD_TYPE_CONTROL_0002", param)) ;
		   }else if("10C".equals(objType)){
			   param.put("offer_id", objId) ;
			   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_CARD_TYPE_CONTROL_0003", param)) ;
		   }else{
			   param.put("card_type", objId) ;
			   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_CARD_TYPE_CONTROL_0004", param)) ;
		   }
		   return rtlist;
	   }
	   /**
	    * ҵ�����Կ���-����
	    * @param cardList
	    * @return
	    * @throws Exception
	    */
	   public boolean saveCardAttrControl(List cardList) throws Exception{
		   Map param = new HashMap();
		   param.put("cardList",cardList);
		   boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("TpmCardAttrControlBO","saveTpmCardAttrControl",param));
		   return result;
	   }
	   public boolean deleteTpmCardAttrControlById(Map param) throws Exception{
		   boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("TpmCardAttrControlBO","deleteTpmCardAttrControlById",param));
		   return result;
	   }
	   /**
	    * ҵ�����Կ���-��ѯ
	    * @param servOffId
	    * @return
	    * @throws Exception
	    */
	   public List searchTpmCardAttrControlData(String servOffId) throws Exception{
		   Map param = new HashMap();
		   param.put("service_offer_id",servOffId);
		   return DataTranslate._List(ServiceManager.callJavaBeanService("TpmCardAttrControlBO","searchTpmCardAttrControlData",param));
	   }

	   /**
	    * �����ע-����
	    * @param param
	    * @return
	    * @throws Exception
	    */
	   public boolean insertTpmNote(Map param) throws Exception{
		   return DataTranslate._boolean(ServiceManager.callJavaBeanService("TpmNoteBO","insertTpmNote",param));
	   }
	   public boolean updateTpmNote(Map param) throws Exception{
		   return DataTranslate._boolean(ServiceManager.callJavaBeanService("TpmNoteBO","updateTpmNote",param));
	   }
	   public boolean deleteTpmNoteById(String noteId) throws Exception{
		   Map param = new HashMap();
		   param.put("note_id",noteId);
		   return DataTranslate._boolean(ServiceManager.callJavaBeanService("TpmNoteBO","deleteTpmNoteById",param));
	   }
	   public List getTpmNote(String ownerId) throws Exception{
		   Map param = new HashMap();
		   param.put("owner_id",ownerId);
		   return DataTranslate._List(ServiceManager.callJavaBeanService("TpmNoteBO","getTpmNote",param));
	   }
	   public List getTpmNoteBureau(String noteId) throws Exception{
		   Map param = new HashMap();
		   param.put("note_id",noteId);
		   return DataTranslate._List(ServiceManager.callJavaBeanService("TpmNoteBO","getTpmNoteBureau",param));
	   }
	   public boolean saveNoteBureau(List noteBureau) throws Exception{
		   Map param = new HashMap();
		   param.put("noteBureau",noteBureau);
		   return DataTranslate._boolean(ServiceManager.callJavaBeanService("TpmNoteBO","saveNoteBureau",param));
	   }
	   public boolean deleteTpmNoteBureauById(Map param) throws Exception{
		   return DataTranslate._boolean(ServiceManager.callJavaBeanService("TpmNoteBO","deleteTpmNoteBureauById",param));
	   }
	   public List getOwnerId(String ownerType,String ownerName) throws Exception{
		   Map param = new HashMap();
		   param.put("owner_type",ownerType);
		   param.put("owner_name",ownerName);
		   return DataTranslate._List(ServiceManager.callJavaBeanService("TpmNoteBO","getOwnerId",param));
	   }
}
