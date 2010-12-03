package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.crm.product.dao.ProductConfGrpDAO;
import com.ztesoft.crm.product.dao.ServRelationGrpDAO ;

public class ServRelationGrpBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchServRelationGrpData 改方法的参数名称
 		3. findServRelationGrpByCond(String group_id,String service_offer_id) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	private SequenceManageDAOImpl  sequenceManageDAO=new SequenceManageDAOImpl();

	public boolean insertProductConfGroup(DynamicDict dto ) throws Exception{
		Map param = Const.getParam(dto);
		String groupId = sequenceManageDAO.getNextSequenceWithDB("SEQ_PRODUCT_CONF_GROUP_ID",JNDINames.PM_DATASOURCE);
		//String sql="select nvl(max(conf_group_id),0)+1 from product_conf_group ";
		//String groupId = Base.query_string( JNDINames.PM_DATASOURCE,sql,1);
		param.put("conf_group_id",groupId);
		param.put("group_type","01L");
		param.put("state","00A");
		ProductConfGrpDAO dao = new ProductConfGrpDAO();
		boolean result = dao.insert(param);
		return result;
	}
	public boolean updateProductConfGroup(DynamicDict dto ) throws Exception{
		Map param = Const.getParam(dto);
		Map keyMap = new HashMap();
		keyMap.put("conf_group_id",Const.getStrValue(param,"conf_group_id"));
		ProductConfGrpDAO dao = new ProductConfGrpDAO();
		boolean result = dao.updateByKey(param,keyMap);
		return result;
	}
	public boolean deleteServConfGrp(DynamicDict dto) throws Exception{
		 Map param = Const.getParam(dto);
		 ProductConfGrpDAO dao = new ProductConfGrpDAO();
		 boolean result = dao.deleteByKey( param ) > 0 ;

		return result ;
	 }
	public Map getConfGroupById(DynamicDict dto ) throws Exception{
		Map param = Const.getParam(dto);

		ProductConfGrpDAO dao = new ProductConfGrpDAO();
		return dao.findByPrimaryKey(param);
	}
	 public boolean saveServRelaGrp(DynamicDict dto) throws Exception{
		 Map param = Const.getParam(dto);
		 ServRelationGrpDAO dao = new ServRelationGrpDAO();
		 List relaGrpList = (ArrayList)param.get("relaGrpList");

		 for(int i=0;i<relaGrpList.size();i++){
			 Map temp = (HashMap)relaGrpList.get(i);
			 temp.put("state","00A");
			 temp.put("seq","0");
			 dao.deleteByKey(temp);
			 dao.insert(temp) ;
		 }
		 return true;
	 }
	 public List getServRelaGrpOld(DynamicDict dto) throws Exception{
		 Map param = Const.getParam(dto);
		 List list = new ArrayList();
		 //list.add(Const.getStrValue(param,"service_offer_id"));
		 list.add(Const.getStrValue(param,"group_id"));
		 //list.add(Const.getStrValue(param,"relation_type_id"));
		 String sql ="select a.group_id,a.service_offer_id,a.relation_type_id,a.operation_flag,a.eff_date,a.exp_date,b.conf_group_name,c.service_offer_name " +
		 		"from service_relation_group a,product_conf_group b,service_offer c " +
		 		"where a.group_id = b.conf_group_id and a.service_offer_id = c.service_offer_id and b.group_type ='01L' " +
		 		"and a.state ='00A' and b.state ='00A' and a.group_id=? ";
		 ProductConfGrpDAO dao = new ProductConfGrpDAO();
		 List result = dao.findBySql(sql,list);
		 return result;
	 }
	 public List getServRelaGroupALL(DynamicDict dto) throws Exception{
		 //Map param = Const.getParam(dto);
		 List list = new ArrayList();
		 //list.add(Const.getStrValue(param,"service_offer_id"));
		 String sql ="select  b.conf_group_id,b.conf_group_name " +
		 		"from product_conf_group b " +
		 		"where  b.group_type ='01L' and b.state='00A' order by b.conf_group_id desc" ;
		 ProductConfGrpDAO dao = new ProductConfGrpDAO();
		 List result = dao.findBySql(sql,list);
		 return result;
	 }
	 public List getServRelaGroup(DynamicDict dto) throws Exception{
		 Map param = Const.getParam(dto);
		 List list = new ArrayList();
		 list.add(Const.getStrValue(param,"service_offer_id"));
		 String sql ="select distinct b.conf_group_id,b.conf_group_name " +
		 		"from service_relation_group a,product_conf_group b " +
		 		"where a.group_id = b.conf_group_id and b.group_type ='01L'  and b.state='00A' and a.service_offer_id =?" ;
		 ProductConfGrpDAO dao = new ProductConfGrpDAO();
		 List result = dao.findBySql(sql,list);
		 return result;
	 }

	 public boolean deleteServRelaGrp(DynamicDict dto) throws Exception{
		 Map param = Const.getParam(dto);
		 ServRelationGrpDAO dao = new ServRelationGrpDAO();
		 boolean result = dao.deleteByKey( param ) > 0 ;

		return result ;
	 }
}
