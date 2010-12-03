package com.ztesoft.crm.product.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.Base;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.product.dao.ServOffDAO;

/**
 *
 * @author chenfeng
 * @服务提供配置类
 */
public class ServiceOfferBo extends DictAction{

	   private SequenceManageDAOImpl  sequenceManageDAO=new SequenceManageDAOImpl();
	   //private   int etype;
	   private   int ecode;
	  // private   String edesc;
	   /**
	    * 获得产品大类目录
	    * @param dto
	    * @return String
	    * @throws Exception
	    */
       public String getProductFamily(DynamicDict dto) throws Exception{
    	   StringBuffer sbXml = new StringBuffer();
    	   DynamicDict aDict = Base.query(JNDINames.PM_DATASOURCE,"select PRODUCT_FAMILY_ID,PRODUCT_FAMILY_NAME from PRODUCT_FAMILY order by PRODUCT_FAMILY_ID",null,null,"family",ecode);
    	   ArrayList list = (ArrayList)aDict.getValueByName("family",-1);
    	   sbXml.append("<items>");
    	   for(int i=0;i<list.size();i++){
    	    	  Map temp = (HashMap)list.get(i);
    	    	  sbXml.append("<item ");
    	    	  sbXml.append(" nodeId = '"+temp.get("product_family_id").toString()+"'");
    	    	  sbXml.append(" nodeName = '"+temp.get("product_family_name").toString()+"'");
    	    	  sbXml.append(" nodeType = '"+"1"+"'");
    	    	  sbXml.append(">");
    	    	  sbXml.append("</item>");
    	   }
    	   sbXml.append("</items>");
    	  return sbXml.toString();
       }
       /**
	    * 获得动作类型和服务
	    * @param dto
	    * @return String
	    * @throws Exception
	    */
       public List getActionType(DynamicDict dto) throws Exception{
    	   ArrayList returnlist = new ArrayList();
    	   HashMap hashMap=(HashMap)dto.getValueByName("parameter");
    	   //StringBuffer sbXml = new StringBuffer();
    	   String nodeId = hashMap.get("nodeId").toString();
    	   String nodeType = hashMap.get("nodeType").toString();
    	   String parentNodeId = hashMap.get("parentNodeId").toString();
    	   if("1".equals(nodeType)){//双击第1层
    		   //DynamicDict actionType = Base.query(JNDINames.PM_DATASOURCE,"select action_type_id node_id,action_type_name node_name from action_type order by action_type_id",null,null,"actionType",ecode);
    		   String sql ="select prod_cat_type node_id,prod_cat_name node_name,2 node_type from product_cat where product_family_id = ?";
    		   DynamicDict actionType = Base.query(JNDINames.PM_DATASOURCE,sql,new String[]{nodeId},null,"actionType",ecode);
    		   returnlist = (ArrayList)actionType.getValueByName("actionType",-1);
    	   }
    	   if("2".equals(nodeType)){//双击第2层
    		   String sql="select action_type_id node_id,action_type_name node_name,3 node_type from action_type order by action_type_id";
    		   DynamicDict actionType = Base.query(JNDINames.PM_DATASOURCE,sql,null,null,"actionType",ecode);
    		   returnlist = (ArrayList)actionType.getValueByName("actionType",-1);
    	   }
    	   if("3".equals(nodeType)){//双击第3层
    		   DynamicDict actionType = Base.query(JNDINames.PM_DATASOURCE,"select service_offer_id node_id, service_offer_name node_name,4 node_type "+
						  " from service_offer a, product_cat b "+
						  " where a.prod_cat_type = b.prod_cat_type "+
						  " and a.state = '00A' "+
						  " and b.prod_cat_type = ? "+
						  " and a.action_type = ?"
						   ,new String[]{parentNodeId,nodeId},null,"actionType",ecode);
    		   		int i = actionType.getCountByName("actionType");
    		   		if(i!=0){
    		   			returnlist = (ArrayList)actionType.getValueByName("actionType",-1);
    		   		}
    	   }
    	  return returnlist;
       }

       public PageModel searchServOffData(DynamicDict dto ) throws Exception {
    	   PageModel result = new PageModel();
   		//条件处理
   		Map param = Const.getParam(dto) ;
   		List para = new ArrayList() ;
   		String nodeType = Const.getStrValue(param , "nodeType");
   		if(Const.containStrValue(param , "parentNodeId")){
   			para.add(Const.getStrValue(param , "parentNodeId")) ;
   		}
   		if(Const.containStrValue(param , "nodeId")){
   			para.add(Const.getStrValue(param , "nodeId")) ;
   		}

   		int pageSize = Const.getPageSize(param) ;
   		int pageIndex = Const.getPageIndex(param) ;


   		//调用DAO代码
   		if("3".equals(nodeType)){// 双击第3层 子节点为动作类型时才调用
   			ServOffDAO dao = new ServOffDAO();
   			String sql="select a.service_offer_id,"+
		       "a.service_offer_name,"+
		       "b.prod_cat_name,"+
		       "to_char(a.eff_date, 'yyyy-mm-dd') eff_date,"+
		       "to_char(a.exp_date, 'yyyy-mm-dd') exp_date,"+
		       "a.state,"+
		       "a.oper_date "+
			  "from service_offer a, product_cat b "+
			 "where a.prod_cat_type = b.prod_cat_type "+
			   "and a.state = '00A' "+
			   "and b.prod_cat_type = ? "+
			   "and a.action_type = ?";
   			String countsql="select count(a.service_offer_id) "+
						  "from service_offer a, product_cat b "+
						 "where a.prod_cat_type = b.prod_cat_type "+
						   "and a.state = '00A' "+
						   "and b.prod_cat_type = ? "+
						   "and a.action_type = ?";
   			result = dao.searchByCond(sql,countsql,para,pageIndex,pageSize);
   		}
   		return result ;
   	}

       public Map getServOffById(DynamicDict dto  ) throws Exception {
//    	 错误参数处理
   		int ecode = 1 ;
   		int etype = 1 ;
   		String edesc = "查询错误" ;

   		//调用DAO代码
   		Map keyCondMap = Const.getParam(dto) ;
   		ServOffDAO dao = new ServOffDAO();
   		Map result = dao.findByPrimaryKey( keyCondMap ,  etype ,  ecode , edesc ) ;
   		String askFlow = Const.getStrValue(result,"ask_flow");
   		String[] temp = askFlow.split(",");
   		String reAskFlow="";
   		String sql="select a.attr_value_desc from attribute_value a where a.attr_id = '50260793' and a.attr_value = ?";
   		for(int i=0;i<temp.length;i++){
   			String desc = Base.query_string(JNDINames.PM_DATASOURCE,sql,new String[]{temp[i]},1);
   			reAskFlow = desc+','+reAskFlow;
   		}
   		result.put("ask_flow_desc",reAskFlow.substring(0,reAskFlow.length()-1));
   		return result ;
       }

       public boolean insertServOff(DynamicDict dto  ) throws Exception {
   		Map param = Const.getParam(dto) ;
   		//Map ServOff = (Map) param.get("ServOff") ;
   		String servOffId=sequenceManageDAO.getNextSequenceWithDB("S_SERVICE_OFFER_ID",JNDINames.PM_DATASOURCE);
   		param.put("service_offer_id",servOffId);
   		param.put("seq","0");
   		Map loginMap = (HashMap)RequestContext.getContext().
		getHttpSession().getAttribute("LoginRtnParamsHashMap");
   		param.put("party_id",Const.getStrValue(loginMap,"vg_depart_id"));//操作点
   		param.put("party_role_id",Const.getStrValue(loginMap,"vg_oper_id"));//操作员
   		param.put("oper_region_id",Const.getStrValue(loginMap,"vg_business_id"));//操作员地域
   		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   		java.util.Date currentTime = new java.util.Date();//得到当前系统时间
   		String str_date1 = formatter.format(currentTime); //将日期时间格式化
   		param.put("state_date",str_date1);
   		param.put("oper_date",str_date1);
   		ServOffDAO dao = new ServOffDAO();
   		boolean result = dao.insert(param) ;
   		return result ;
   	}


   	public boolean updateServOff(DynamicDict dto ) throws Exception {
   		Map param = Const.getParam(dto) ;
   		String servofferId = Const.getStrValue(param,"service_offer_id");
		this.saveOldData(servofferId);
   		String keyStr = "service_offer_id";
   		Map keyCondMap  = Const.getMapForTargetStr(param,  keyStr) ;
   		ServOffDAO dao = new ServOffDAO();
   		Map loginMap = (HashMap)RequestContext.getContext().
		getHttpSession().getAttribute("LoginRtnParamsHashMap");
   		param.put("party_id",Const.getStrValue(loginMap,"vg_depart_id"));//操作点
   		param.put("party_role_id",Const.getStrValue(loginMap,"vg_oper_id"));//操作员
   		param.put("oper_region_id",Const.getStrValue(loginMap,"vg_business_id"));//操作员地域
   		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   		java.util.Date currentTime = new java.util.Date();//得到当前系统时间
   		String str_date1 = formatter.format(currentTime); //将日期时间格式化
   		param.put("state_date",str_date1);
   		param.put("oper_date",str_date1);
   		boolean result = dao.updateByKey( param, keyCondMap );

   		return result ;
   	}
   	public boolean deleteServOffById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		String servofferId = Const.getStrValue(keyCondMap,"service_offer_id");
		this.saveOldData(servofferId);
		ServOffDAO dao = new ServOffDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;

		return result ;
	}
   	public List getAskFlow(DynamicDict dto ) throws Exception {
   		Map param = Const.getParam(dto);
   		List paramlist = new ArrayList();
   		String attrId = Const.getStrValue(param,"attr_id");
   		paramlist.add(attrId);
   		String sql="select attr_value_id,attr_value,attr_value_desc from attribute_value  where attr_id= ?";
   		ServOffDAO dao = new ServOffDAO();
   		return dao.findBySql(sql,paramlist);
   	}
   	private void saveOldData(String servofferId)  throws Exception{
		DynamicDict servOfferOldDict = Base.query(JNDINames.PM_DATASOURCE, "select  to_char(a.$columns)," +
				"to_char(a.eff_date,'yyyy-mm-dd hh24:mi:ss') eff_date, " +
				"to_char(a.exp_date,'yyyy-mm-dd hh24:mi:ss') exp_date  " +
				"from service_offer a where service_offer_id = ? and state = '00A'   ", new String[]{servofferId}, ecode);
		String seq = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(seq),0)+1 from arc_service_offer where service_offer_id = ? ", new String[]{servofferId}, ecode);
		Base.dict__set_val(servOfferOldDict,"seq",0,seq);
		Base.action__execute(JNDINames.PM_DATASOURCE, ecode,servOfferOldDict,0," insert into arc_service_offer ($columns,state,OPER_DATE) values ($columns,'00X',sysdate) ",new String[]{});

	}



}
