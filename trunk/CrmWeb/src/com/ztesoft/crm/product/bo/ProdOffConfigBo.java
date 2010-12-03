package com.ztesoft.crm.product.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.product.bo.util.QueryCenter;
import com.ztesoft.crm.product.dao.AcctItemTypeDAO;
import com.ztesoft.crm.product.dao.ArcOffRelationDefDAO;
import com.ztesoft.crm.product.dao.ArcProdOffGrpRelationDAO;
import com.ztesoft.crm.product.dao.ArcProductAttrDAO;
import com.ztesoft.crm.product.dao.ArcProductOffAttrDAO;
import com.ztesoft.crm.product.dao.ArcProductOffBureauDAO;
import com.ztesoft.crm.product.dao.ArcProductOffDetaDAO;
import com.ztesoft.crm.product.dao.ArcProductOffRelationDAO;
import com.ztesoft.crm.product.dao.ArcProductOffServDAO;
import com.ztesoft.crm.product.dao.ArcTpmBusiPriceDAO;
import com.ztesoft.crm.product.dao.ArcTpmBusiPriceParaDAO;
import com.ztesoft.crm.product.dao.ArcTpmBusiPricePlanDAO;
import com.ztesoft.crm.product.dao.OffRelationDefDAO;
import com.ztesoft.crm.product.dao.OffWarrRequementDAO;
import com.ztesoft.crm.product.dao.ProdCompDetaRoleDAO;
import com.ztesoft.crm.product.dao.ProductCatgItemBureauDAO;
import com.ztesoft.crm.product.dao.ProductCatgItemRoleDAO;
import com.ztesoft.crm.product.dao.ProductConfGrpDAO;
import com.ztesoft.crm.product.dao.ProductOffAttrDAO;
import com.ztesoft.crm.product.dao.ProductOffBureauDAO;
import com.ztesoft.crm.product.dao.ProductOffDetaDAO;
import com.ztesoft.crm.product.dao.ProductOffGrpRelationDAO;
import com.ztesoft.crm.product.dao.ProductOffRelationDAO;
import com.ztesoft.crm.product.dao.ProductOffRelationGrpDAO;
import com.ztesoft.crm.product.dao.ProductOffServDAO;
import com.ztesoft.crm.product.dao.ServOffDAO;
import com.ztesoft.crm.product.dao.TpmBusiPriceDAO;
import com.ztesoft.crm.product.dao.TpmBusiPriceParaDAO;
import com.ztesoft.crm.product.dao.TpmBusiPricePlanDAO;
import com.ztesoft.crm.product.dao.OffRelationRestrictedDAO;
import com.ztesoft.crm.product.dao.ArcOffRelationRestrictedDAO;

/**
 * 
 * @author liruxin
 * @comment 销售品配置业务类
 */
public class ProdOffConfigBo extends DictAction{
	   private QueryCenter    queryCenter=new QueryCenter();
	   private ProductOffDetaDAO  productOffDetaDAO=new ProductOffDetaDAO();
	   private ProductOffServDAO  productOffServDAO=new ProductOffServDAO();
	   private SequenceManageDAOImpl  sequenceManageDAO=new SequenceManageDAOImpl();
	   private ProductOffBureauDAO    productOffBureauDAO=new ProductOffBureauDAO();
	   private ProductOffAttrDAO      productOffAttrDAO=new ProductOffAttrDAO();
	   private ProductOffRelationDAO   productOffRelationDAO=new ProductOffRelationDAO();
	   private ProductOffRelationGrpDAO productOffRelationGrpDAO=new ProductOffRelationGrpDAO();
	   private ArcProductOffDetaDAO     arcProductOffDetaDAO=new ArcProductOffDetaDAO();
	   private ArcProductOffServDAO     arcProductOffServDAO=new ArcProductOffServDAO();
	   private ArcProductOffBureauDAO    arcProductOffBureauDAO=new ArcProductOffBureauDAO();
	   private ArcProductAttrDAO         arcProductAttrDAO=new ArcProductAttrDAO();
	   private ArcProductOffAttrDAO      arcProductOffAttrDAO=new ArcProductOffAttrDAO();
	   private ArcProductOffRelationDAO   arcProductOffRelationDAO=new ArcProductOffRelationDAO();
	   private ProductConfGrpDAO          productConfGrpDAO=new ProductConfGrpDAO();
	   private ProductOffGrpRelationDAO   productOffGrpRelationDAO=new ProductOffGrpRelationDAO();
	   private ArcProdOffGrpRelationDAO   arcProdOffGrpRelationDAO=new ArcProdOffGrpRelationDAO();
	   private OffWarrRequementDAO        offWarrRequementDAO=new OffWarrRequementDAO();
	   private ProductCatgItemBureauDAO   productCatgItemBureauDAO=new ProductCatgItemBureauDAO();
	   private ProductCatgItemRoleDAO     productCatgItemRoleDAO=new ProductCatgItemRoleDAO();
	   private TpmBusiPriceDAO            tpmBusiPriceDAO=new TpmBusiPriceDAO();
	   private ArcTpmBusiPriceDAO         arcTpmBusiPriceDAO=new ArcTpmBusiPriceDAO();
	   private TpmBusiPricePlanDAO        tpmBusiPricePlanDAO=new TpmBusiPricePlanDAO();
	   private ArcTpmBusiPricePlanDAO     arcTpmBusiPricePlanDAO=new ArcTpmBusiPricePlanDAO();
	   private TpmBusiPriceParaDAO        tpmBusiPriceParaDAO=new TpmBusiPriceParaDAO();
	   private ArcTpmBusiPriceParaDAO     arcTpmBusiPriceParaDAO=new ArcTpmBusiPriceParaDAO();
	   private OffRelationDefDAO          offRelationDefDAO=new OffRelationDefDAO();
	   private ArcOffRelationDefDAO       arcOffRelationDefDAO=new ArcOffRelationDefDAO();
	   private OffRelationRestrictedDAO   OffRelationRestrictedDAO=new OffRelationRestrictedDAO();
	   private ArcOffRelationRestrictedDAO   arcOffRelationRestrictedDAO=new ArcOffRelationRestrictedDAO();
	   private   int etype;
	   private   int ecode;
	   private   String edesc;
       
	   /**
	    * 保存销售品明细
	    * @param dto
	    * @throws Exception
	    */
	   public void saveProdOffDeta(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      List  detaMaps=(ArrayList)hashMap.get("offDetaArr");
		      for(int i=0;i<detaMaps.size();i++){
		    	  List    whereCondParams=new ArrayList();
		    	  HashMap  detaMap=(HashMap)detaMaps.get(i);
		    	  String offer_detail_id=(String)detaMap.get("offer_detail_id");
		    	  String seq=(String)detaMap.get("seq");
		    	  whereCondParams.add(offer_detail_id);
		    	  whereCondParams.add(seq);
		    	  List list=productOffDetaDAO.findBySql(" select * from product_offer_detail where " +
		    	  		" offer_detail_id=?  and seq=? ",whereCondParams);
		    	  if (!list.isEmpty()){
		    		   //更新明细
		    		  HashMap oldMap=(HashMap)list.get(0);
		    		  arcProductOffDetaDAO.insert(oldMap);
		    		  productOffDetaDAO.deleteByKey(detaMap);
		    		  detaMap.put("seq",String.valueOf(Integer.parseInt(detaMap.get("seq").toString())+1));
		    		  productOffDetaDAO.insert(detaMap);
		    	  }else{
		    		   //新增明细
		    		  detaMap.put("seq","0");
		    		  String offDetaId=sequenceManageDAO.getNextSequenceWithDB("S_OFFER_DETAIL_ID",JNDINames.PM_DATASOURCE);
		    		  detaMap.put("offer_detail_id",offDetaId);
		    		  detaMap.put("ord_action_type","1");
		    		  detaMap.put("ord_no","1");
		    		  detaMap.put("event_seq","1");
		    		  detaMap.put("oper_date",DateFormatUtils.getFormatedDateTime());
		    		  detaMap.put("state","00A");
		    		  productOffDetaDAO.insert(detaMap);
		    	  }
		      }
	   }
	   
	    /**
	     * 增加产品角色
	     * @param dto
	     * @return boolean
	     * @throws Exception
	     */
		public boolean insertProdCompDetaRole(DynamicDict dto) throws Exception{
			Map para=Const.getParam(dto);
			Map map = (Map) para.get("ProdCompDetaRole");
			map.put("oper_date",DateFormatUtils.getFormatedDateTime());
			map.put("state","00A");
			map.put("oper_date",DateFormatUtils.getFormatedDateTime());
			map.put("state_date",DateFormatUtils.getFormatedDateTime());
			map.put("create_date",DateFormatUtils.getFormatedDateTime());
			map.put("seq","0");
			String roleId=sequenceManageDAO.getNextSequenceWithDB("S_ROLE_ID",JNDINames.PM_DATASOURCE);
			map.put("comp_role_id",roleId);
			ProdCompDetaRoleDAO dao = new ProdCompDetaRoleDAO();
			boolean result = dao.insert(map) ;
			return result ;
		}
		
		/**
		 * 查询产品角色
		 * @param dto
		 * @return list
		 * @throws Exception 
		 */
		public PageModel searchProdCompDetaRoleData(DynamicDict dto ) throws Exception{
			//条件处理
			Map param = Const.getParam(dto) ;
			StringBuffer whereCond = new StringBuffer() ;
			List para = new ArrayList() ;
			if(Const.containStrValue(param , "role_id")){
				whereCond.append(" and comp_role_id = ? ");
				para.add(Const.getStrValue(param , "role_id")) ;
			}
			if(Const.containStrValue(param , "role_name")){
				String roleName=(String)param.get("role_name");
				whereCond.append(" and role_name like '%"+roleName+"%' ");
			}
			int pageSize = Const.getPageSize(param);
			int pageIndex = Const.getPageIndex(param);
			//调用DAO代码
			ProdCompDetaRoleDAO dao = new ProdCompDetaRoleDAO();
			PageModel result = dao.searchByCond(whereCond.toString(),para,pageIndex,pageSize);
			return result ;
		}
		
		
		/**
		 * 查询帐目类型
		 * @param dto
		 * @return PageModel
		 * @throws Exception
		 */
		public PageModel qryAcctItemType(DynamicDict dto ) throws Exception{
			//条件处理
			Map param = Const.getParam(dto) ;
			StringBuffer whereCond = new StringBuffer() ;
			List para = new ArrayList() ;
			if(Const.containStrValue(param , "acct_item_name")){
				String acct_item_name=(String)param.get("acct_item_name");
				whereCond.append(" and name like '%"+acct_item_name+"%' ");
			}
			int pageSize = Const.getPageSize(param);
			int pageIndex = Const.getPageIndex(param);
			//调用DAO代码
			AcctItemTypeDAO dao = new AcctItemTypeDAO();
			PageModel result = dao.searchByCond(whereCond.toString(),para,pageIndex,pageSize);
			return result ;
		}

		
		
		/**
		 * 查询服务提供
		 * @param dto
		 * @return list
		 * @throws Exception 
		 */
		public PageModel qryServOff(DynamicDict dto ) throws Exception{
			//条件处理
			Map param = Const.getParam(dto) ;
			StringBuffer whereCond = new StringBuffer() ;
			List para = new ArrayList() ;
			if(Const.containStrValue(param , "action_id")){
				whereCond.append(" and action_id = ? ");
				para.add(Const.getStrValue(param , "action_id")) ;
			}
			if(Const.containStrValue(param , "serv_offer_name")){
				String serv_offer_name=(String)param.get("serv_offer_name");
				whereCond.append(" and service_offer_name like '%"+serv_offer_name+"%' ");
			}
			int pageSize = Const.getPageSize(param);
			int pageIndex = Const.getPageIndex(param);
			//调用DAO代码
			ServOffDAO dao = new ServOffDAO();
			PageModel result = dao.searchByCond(whereCond.toString(),para,pageIndex,pageSize);
			return result;
		}
		
		/**
		 * 查询商品服务提供
		 * @param dto
		 * @return  List
		 * @throws Exception
		 */
	    public List qryProdOffServ(DynamicDict dto) throws Exception{
    	      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
    	      String  offer_id=(String)hashMap.get("offer_id");
    	      List  whereCondParams=new ArrayList();
    	      whereCondParams.add(offer_id);
    	      List result=productOffServDAO.findBySql(" select a.*, b.service_offer_name from " +
    	      		" product_offer_service a, service_offer b " +
    	      		" where a.service_offer_id = b.service_offer_id " +
    	      		" and a.offer_id = ? ",whereCondParams,etype,ecode,edesc);
    	      return result ;
	    }
	    
	    
	   /**
	    * 保存销售品明细
	    * @param dto
	    * @throws Exception
	    */
	   public void saveProdOffServ(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      List  detaMaps=(ArrayList)hashMap.get("prodOffServArr");
		      String offer_id=(String)hashMap.get("offer_id");
	    	  List    whereCondParams=new ArrayList();
	    	  whereCondParams.add(offer_id);
	    	  List oldLst=productOffServDAO.findByCond(" and offer_id=?",whereCondParams);
	    	  for(int i=0;i<oldLst.size();i++){
	    		  //写归档日志
	    		  HashMap oldMap=(HashMap)oldLst.get(i);
	    		  String oldServOffId=(String)oldMap.get("service_offer_id");
	    		  List paramLst=new ArrayList();
	    		  paramLst.add(oldServOffId);
	    		  paramLst.add(offer_id);
	    		  List  arcMapLst=arcProductOffServDAO.findBySql("select max(seq) seq from arc_product_offer_service " +
	    		  		" where service_offer_id=? and  offer_id=? ",paramLst);
	    		  if (!arcMapLst.isEmpty()){
	    			  String seq=(String)((HashMap)arcMapLst.get(0)).get("seq");
	    			  if (seq!=null && !"".equals(seq)){
	    				  long  currVal=Long.parseLong(seq)+1;
			    		  seq=String.valueOf(currVal);
			    		  oldMap.put("seq",seq);
	    			  }
	    		  }
	    		  
	    		  arcProductOffServDAO.insert(oldMap);
	    	  }
	    	  productOffServDAO.delete(" and offer_id=? " +
	    	  		"  ",whereCondParams);
		      for(int i=0;i<detaMaps.size();i++){
		    	  HashMap  servOffMap=(HashMap)detaMaps.get(i);
		    	  String  seq=(String)servOffMap.get("seq");
		    	  if (seq!=null && !"".equals(seq)){
		    		  long  currVal=Long.parseLong(seq)+1;
		    		  seq=String.valueOf(currVal);
		    	  }
		    	  else
		    		  seq="0"; 
		    	  servOffMap.put("state","00A");
		    	  servOffMap.put("seq",seq);
		    	  productOffServDAO.insert(servOffMap);
		      }
	   }
	   
	   
	   /**
	    * 查询销售品区域限制
	    * @param dto
	    * @return list
	    * @throws Exception
	    */
	   public List qryProdOffRegion(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
    	      String offer_id=(String)hashMap.get("offer_id");
    	      List  whereCondParams=new ArrayList();
    	      whereCondParams.add(offer_id);
    	      List list=productOffBureauDAO.findBySql("select a.*,b.region_name from product_offer_bureau a,region b " +
    	      		" where a.region_id=b.region_id " +
    	      		" and a.offer_id=?",whereCondParams);
    	      return list;
	   }
	   
	   /**
	    * 保存或者删除销售品区域限制
	    * @param dto
	    * @throws Exception
	    */
	   public void saveOrUpdateProdOffRegion(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      List  regionMaps=(ArrayList)hashMap.get("prodOffRegionArr");
	    	  List    whereCondParams=new ArrayList();
	    	  String offer_id=(String)hashMap.get("offer_id");
	    	  whereCondParams.add(offer_id);
	    	  List oldLst=productOffBureauDAO.findByCond(" and offer_id=?",whereCondParams);
	    	  for(int i=0;i<oldLst.size();i++){
	    		  //写归档日志
	    		  HashMap oldMap=(HashMap)oldLst.get(i);
	    		  String oldRegionId=(String)oldMap.get("region_id");
	    		  List paramLst=new ArrayList();
	    		  paramLst.add(oldRegionId);
	    		  paramLst.add(offer_id);
	    		  List  arcMapLst=arcProductOffBureauDAO.findBySql("select max(seq) seq from arc_product_offer_bureau " +
	    		  		" where region_id=? and  offer_id=? ",paramLst);
	    		  if (!arcMapLst.isEmpty()){
	    			  String seq=(String)((HashMap)arcMapLst.get(0)).get("seq");
	    			  if (seq!=null && !"".equals(seq)){
	    				  long  currVal=Long.parseLong(seq)+1;
			    		  seq=String.valueOf(currVal);
			    		  oldMap.put("seq",seq);
	    			  }
	    		  }
	    		  arcProductOffBureauDAO.insert(oldMap);
	    	  }
	    	  productOffBureauDAO.delete(" and offer_id=? " +
	    	  		"  ",whereCondParams);
		      for(int i=0;i<regionMaps.size();i++){
		    	  HashMap map=(HashMap)regionMaps.get(i);
		    	  String  seq=(String)map.get("seq");
		    	  if (seq!=null && !"".equals(seq)){
		    		  long  currVal=Long.parseLong(seq)+1;
		    		  seq=String.valueOf(currVal);
		    	  }else
		    		  seq="0";
		    	  map.put("state","00A");
		    	  map.put("seq",seq);
		    	  productOffBureauDAO.insert(map);
		      }
	   }
	   
	   /**
	    * 查询销售品属性
	    * @param dto
	    * @return list
	    * @throws Exception
	    */
	   public List qryProdOffAttr(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
 	          String offer_id=(String)hashMap.get("offer_id");
 	          List  whereCondParams=new ArrayList();
 	          whereCondParams.add(offer_id);
 	          StringBuffer sql=new StringBuffer();
 	          sql.append("select a.*, b.attr_name                  ");
 	          sql.append("  from product_offer_attr a, attribute b ");
 	          sql.append(" where a.offer_attr_seq = b.attr_id      ");
 	          sql.append("   and a.offer_id = ?                    ");
 	          List list=productOffAttrDAO.findBySql(sql.toString(),whereCondParams);
 	          return list;
	   }
	   
	   
	   /**
	    * 保存或者删除销售品属性
	    * @param dto
	    * @throws Exception
	    */
	   public void saveOrUpdateProdOffAttr(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      List  regionMaps=(ArrayList)hashMap.get("prodOffAttrArr");
	    	  List   whereCondParams=new ArrayList();
	    	  String offer_id=(String)hashMap.get("offer_id");
	    	  whereCondParams.add(offer_id);
	    	  List oldLst=productOffAttrDAO.findByCond(" and offer_id=?",whereCondParams);
	    	  for(int i=0;i<oldLst.size();i++){
	    		  //写归档日志
	    		  HashMap oldMap=(HashMap)oldLst.get(i);
	    		  String oldAttrId=(String)oldMap.get("offer_attr_seq");
	    		  List paramLst=new ArrayList();
	    		  paramLst.add(oldAttrId);
	    		  paramLst.add(offer_id);
	    		  List  arcMapLst=arcProductOffAttrDAO.findBySql("select max(seq) seq from arc_product_offer_attr " +
	    		  		" where offer_attr_seq=? and  offer_id=? ",paramLst);
	    		  if (!arcMapLst.isEmpty()){
	    			  String seq=(String)((HashMap)arcMapLst.get(0)).get("seq");
	    			  if (seq!=null && !"".equals(seq)){
	    				  long  currVal=Long.parseLong(seq)+1;
			    		  seq=String.valueOf(currVal);
			    		  oldMap.put("seq",seq);
	    			  }
	    		  }
	    		  arcProductOffAttrDAO.insert(oldMap);
	    	  }
	    	  
	    	  productOffAttrDAO.delete(" and offer_id=? " +
	    	  		"  ",whereCondParams);
		      for(int i=0;i<regionMaps.size();i++){
		    	  HashMap map=(HashMap)regionMaps.get(i);
		    	  String  seq=(String)map.get("seq");
		    	  if (seq!=null && !"".equals(seq)){
		    		  long  currVal=Long.parseLong(seq)+1;
		    		  seq=String.valueOf(currVal);
		    	  }else
		    		  seq="0";
		    	  map.put("state","00A");
		    	  map.put("seq",seq);
		    	  map.put("excluablity","1");
		    	  map.put("ord_action_type","1");
		    	  map.put("oper_date",DateFormatUtils.getFormatedDateTime());
		    	  map.put("ord_no","1");
		    	  map.put("event_seq","1");
		    	  productOffAttrDAO.insert(map);
		      }
	   }
	   
	   /**
	    * 查询销售品关系
	    * @param dto
	    * @return list
	    * @throws Exception
	    */
	   public List qryProdOffRela(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	          String offer_id=(String)hashMap.get("offer_id");
	          String rela_type=(String)hashMap.get("rela_type");
	          List  whereCondParams=new ArrayList();
	          whereCondParams.add(offer_id);
	          whereCondParams.add(rela_type);
	          StringBuffer sql=new StringBuffer();
	          if (!"10A".equals(rela_type)){
		          sql.append("select a.*, c.offer_name, b.role_name                                     ");  
		          sql.append("  from product_offer_relation a, prod_comp_detail_role b, product_offer c ");  
		          sql.append(" where a.offer_z_id = c.offer_id(+)                                       ");  
		          sql.append("   and a.comp_role_id = b.comp_role_id(+)                                 ");  
		          sql.append("   and a.offer_a_id = ?                                                   ");  
		          sql.append("   and a.relation_type_id = ?                                             ");  
	          }else{
	        	  whereCondParams.add(offer_id);
		          whereCondParams.add(rela_type);
	        	  sql.append("select a.*, c.offer_name, b.role_name                                      ");
	        	  sql.append("  from product_offer_relation a, prod_comp_detail_role b, product_offer c  ");
	        	  sql.append(" where a.offer_z_id = c.offer_id(+)                                        ");
	        	  sql.append("   and a.comp_role_id = b.comp_role_id(+)                                  ");
	        	  sql.append("   and a.offer_a_id =?                                                     ");
	        	  sql.append("   and a.relation_type_id =?                                               ");
	        	  sql.append("union                                                                      ");
	        	  sql.append("select a.*, c.offer_name, b.role_name                                      ");
	        	  sql.append("  from product_offer_relation a, prod_comp_detail_role b, product_offer c  ");
	        	  sql.append(" where a.offer_a_id = c.offer_id(+)                                        ");
	        	  sql.append("   and a.comp_role_id = b.comp_role_id(+)                                  ");
	        	  sql.append("   and a.offer_z_id =?                                                     ");
	        	  sql.append("   and a.relation_type_id =?                                               ");
	          }
	          List list=productOffRelationDAO.findBySql(sql.toString(),whereCondParams);
	          return list;
	   }
	   
	   
	   /**
	    * 保存销售品关系
	    * @param dto
	    * @throws Exception
	    */
	   public HashMap saveOrUpdateProdOffRela(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      List  regionMaps=(ArrayList)hashMap.get("prodOffRelaArr");
	    	  List    whereCondParams=new ArrayList();
	    	  HashMap errMap=new HashMap();
	    	  errMap.put("flag","true");
	    	  String offer_id=(String)hashMap.get("offer_id");
	    	  String relation_type_id=(String)hashMap.get("relation_type_id");
	    	  whereCondParams.add(offer_id);
	    	  whereCondParams.add(relation_type_id);
	    	  //校验销售品关系
	    	  for(int i=0;i<regionMaps.size();i++){
	    		  String offer_a_id=(String)((HashMap)regionMaps.get(i)).get("offer_a_id");
	    		  String offer_z_id=(String)((HashMap)regionMaps.get(i)).get("offer_z_id");
	    		  List paramLst=new ArrayList();
	    		  paramLst.add(offer_a_id);
	    		  paramLst.add(offer_z_id);
	    		  List offRelaLst=productOffRelationDAO.findBySql("select * from product_offer_relation " +
	    		  		" where offer_a_id=? and offer_z_id=? " +
	    		  		" and relation_type_id!='"+relation_type_id+"'",paramLst);
	    		  if (!offRelaLst.isEmpty()){
	    			   for(int j=0;j<offRelaLst.size();j++){
	    				   HashMap relaMap=(HashMap)offRelaLst.get(j);
	    				   String relaTypeId=(String)relaMap.get("relation_type_id");
	    				   if (relaTypeId!=null && !"".equals(relaTypeId)){
	    					   //关系类型不为空
	    					   String sql="select b.attr_value_desc from attribute a, attribute_value b " +
	    					   		" where a.attr_code = ? " +
	    					   		" and a.attr_id = b.attr_id " +
	    					   		" and b.attr_value = ? ";
	    					   int errCode=0;
	    					   String oldRelaTypeName=Base.query_string(JNDINames.PM_DATASOURCE,sql,
	    							   new String[]{"PRODUCT_OFFER_RELATION_RELATION_TYPE_ID",relaTypeId},errCode);
	    					   String currRelaTypeName=Base.query_string(JNDINames.PM_DATASOURCE,sql,
	    							   new String[]{"PRODUCT_OFFER_RELATION_RELATION_TYPE_ID",relation_type_id},errCode);
	    					   String qryOffNameSql="select offer_name from product_offer where offer_id=? ";
	    					   String offName=Base.query_string(JNDINames.PM_DATASOURCE,qryOffNameSql,
	    							   new String[]{offer_z_id},errCode);
	    					   String errMsg="销售品"+offName+"已有"+oldRelaTypeName+"！\n" +
	    					                  oldRelaTypeName+"和"+currRelaTypeName+"不能同时存在！";
	    					   errMap.put("flag","false");
	    					   errMap.put("errMsg",errMsg);
	    					   return errMap;
	    				   }
	    			   }
	    		  }
	    	  }
	    	  
	    	  
	    	  List oldLst=productOffRelationDAO.findByCond(" and offer_a_id=? and relation_type_id=? ",whereCondParams);
	    	  for(int i=0;i<oldLst.size();i++){
	    		  //写归档日志
	    		  HashMap oldMap=(HashMap)oldLst.get(i);
	    		  arcProductOffRelationDAO.insert(oldMap);
	    	  }
	    	  productOffRelationDAO.delete(" and offer_a_id=? and relation_type_id=? " +
	    	  		"  ",whereCondParams);
	    	  
	    	  if ("10A".equals(relation_type_id)){
	    		  List Lst=productOffRelationDAO.findByCond(" and offer_z_id=? and relation_type_id=? ",whereCondParams);
		    	  for(int j=0;j<Lst.size();j++){
		    		  //写归档日志
		    		  HashMap oldMap=(HashMap)Lst.get(j);
		    		  arcProductOffRelationDAO.insert(oldMap);
		    	  }	    		  
		    	  productOffRelationDAO.delete(" and offer_z_id=? and relation_type_id=? " +
			    	  		"  ",whereCondParams);
	    	  }	  
	    	  
		      for(int i=0;i<regionMaps.size();i++){
		    	  HashMap map=(HashMap)regionMaps.get(i);
		    	  String  seq=(String)map.get("seq");
		    	  if (seq!=null && !"".equals(seq)){
		    		  long  currVal=Long.parseLong(seq)+1;
		    		  seq=String.valueOf(currVal);
		    	  }else
		    		  seq="0";
		    	  if (map.get("relation_id")==null || "".equals(map.get("relation_id"))){
		    		  String relationId=sequenceManageDAO.getNextSequenceWithDB("S_RELATION_ID",JNDINames.PM_DATASOURCE);
		    		  map.put("relation_id",relationId);
			    	  map.put("state","00A");
			    	  map.put("excluablity","1");
			    	  map.put("ord_action_type","1");
			    	  map.put("oper_date",DateFormatUtils.getFormatedDateTime());
			    	  map.put("ord_no","1");
			    	  map.put("event_seq","1");
		    	  }
		    	  map.put("seq",seq);
		    	  productOffRelationDAO.insert(map);
		      }
		      return errMap;
	  }
	   
	   
	  /**
	   * 查询销售品关系分组
	   * @param dto
	   * @return list
	   * @throws Exception
	   */ 
	  public List qryProdOffRelaGrp(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	         String group_id=(String)hashMap.get("group_id");
	         List    whereCondParams=new ArrayList();
	         whereCondParams.add(group_id);
	         StringBuffer sql=new StringBuffer();
	         sql.append("select a.*, b.offer_name                               ");
	         sql.append("  from PRODUCT_OFFER_RELATION_GROUP a, product_offer b ");
	         sql.append(" where a.offer_id = b.offer_id                         ");
	         sql.append("   and a.group_id = ?                                  ");
	         List list=productOffRelationGrpDAO.findBySql(sql.toString(),whereCondParams);
	         return list;
	  }
	  
	  
	   /**
	    * 保存销售品关系组
	    * @param dto
	    * @throws Exception
	    */
	   public void saveOrUpdateProdOffRelaGrp(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      List  regionMaps=(ArrayList)hashMap.get("prodOffRelaGrpArr");
		      List    whereCondParams=new ArrayList();
		      
	    	  String group_id=(String)hashMap.get("group_id");
	    	  whereCondParams.add(group_id);
	    	  productOffRelationGrpDAO.delete(" and group_id=?  " +
	    	  		"  ",whereCondParams);
	    	  
		      for(int i=0;i<regionMaps.size();i++){
		    	  HashMap map=(HashMap)regionMaps.get(i);
	    		  map.put("seq","0");
		    	  map.put("state","00A");
		    	  map.put("excluablity","1");
		    	  map.put("ord_action_type","1");
		    	  map.put("oper_date",DateFormatUtils.getFormatedDateTime());
		    	  map.put("ord_no","1");
		    	  map.put("event_seq","1");
		    	  productOffRelationGrpDAO.insert(map);
		      }
	  }

	   
	  /**
	   * 查询销售品关系分组
	   * @param dto
	   * @return list
	   * @throws Exception
	   */ 
	  public List qryProdOffRelaConfGrp(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	         String group_name=(String)hashMap.get("group_name");
	         String conf_group_id=(String)hashMap.get("conf_group_id");
	         StringBuffer sql=new StringBuffer();
	         sql.append(" select * from PRODUCT_CONF_GROUP   where 1=1 and state='00A' ");
	         if (group_name!=null && !"".equals(group_name)){
	        	 sql.append(" and conf_group_name like '%"+group_name+"%' ");
	         }
	         if (conf_group_id!=null && !"".equals(conf_group_id)){
	        	 sql.append(" and conf_group_id="+conf_group_id+" ");
	         }
	         List list=productConfGrpDAO.findBySql(sql.toString());
		     return list;
	  }
 
      /**
       * 增加销售品关系组
       * @param dto
       * @return  String
       * @throws Exception
       */	  
	  public  String addProdOffRelaConfGrp(DynamicDict dto)throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      String confGrpId=sequenceManageDAO.getNextSequenceWithDB("SEQ_PRODUCT_CONF_GROUP_ID",JNDINames.PM_DATASOURCE);
		      hashMap.put("state","00A");
		      hashMap.put("state_date",DateFormatUtils.getFormatedDateTime());
		      hashMap.put("create_date",DateFormatUtils.getFormatedDateTime());
		      hashMap.put("conf_group_id",confGrpId);
		      productConfGrpDAO.insert(hashMap);
		      return confGrpId;
	  }
	  
	  
      /**
       * 修改销售品关系组
       * @param dto
       * @return  String
       * @throws Exception
       */	  
	  public  String updateProdOffRelaConfGrp(DynamicDict dto)throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      productConfGrpDAO.updateByKey(hashMap,hashMap);
		      return (String)hashMap.get("conf_group_id");
	  }

	  
      /**
       * 删除销售品关系组
       * @param dto
       * @return  String
       * @throws Exception
       */	  
	  public  String delProdOffRelaConfGrp(DynamicDict dto)throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      productConfGrpDAO.deleteByKey(hashMap);
		      return (String)hashMap.get("conf_group_id");
	  }
	  
	  
	  
	  
	  /**
	   * 查询销售品关系分组
	   * @param dto
	   * @return list
	   * @throws Exception
	   */ 
	  public List qryProdOffGrpRela(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	         String group_id=(String)hashMap.get("group_id");
	         String relation_type_id=(String)hashMap.get("relation_type_id");
	         List    whereCondParams=new ArrayList();
	         whereCondParams.add(relation_type_id);
	         whereCondParams.add(group_id);
	         StringBuffer sql=new StringBuffer();
	         if (!"10A".equals(relation_type_id)){
		         sql.append("select a.*, b.conf_group_name                               ");
		         sql.append("  from PRODUCT_OFFER_GROUP_RELATION a, PRODUCT_CONF_GROUP b ");
		         sql.append(" where a.group_z_id = b.conf_group_id                       ");
		         sql.append("   and b.relation_type_id = ?                               ");
		         sql.append("   and a.group_a_id = ?                                     ");
	         }else{
	        	 whereCondParams.add(relation_type_id);
		         whereCondParams.add(group_id);
	        	 sql.append("select a.*, b.conf_group_name                               ");
	        	 sql.append("  from PRODUCT_OFFER_GROUP_RELATION a, PRODUCT_CONF_GROUP b ");
	        	 sql.append(" where a.group_z_id = b.conf_group_id                       ");
	        	 sql.append("   and b.relation_type_id = ?                               ");
	        	 sql.append("   and a.group_a_id = ?                                     ");
	        	 sql.append("union                                                       ");
	        	 sql.append("select a.*, b.conf_group_name                               ");
	        	 sql.append("  from PRODUCT_OFFER_GROUP_RELATION a, PRODUCT_CONF_GROUP b ");
	        	 sql.append(" where a.group_a_id = b.conf_group_id                       ");
	        	 sql.append("   and b.relation_type_id = ?                               ");
	        	 sql.append("   and a.group_z_id = ?                                     ");
	         }
	         List list=productOffGrpRelationDAO.findBySql(sql.toString(),whereCondParams);
		     return list;
	  }
	  
	  
	  
	   /**
	    * 保存销售品关系组
	    * @param dto
	    * @throws Exception
	    */
	   public void saveOrUpdateProdOffGrpRela(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      List  regionMaps=(ArrayList)hashMap.get("prodOffGrpRelaArr");
		      List  whereCondParams=new ArrayList();
		      String group_id=(String)hashMap.get("group_id");
		      String relation_type_id=(String)hashMap.get("relation_type_id");
	    	  whereCondParams.add(group_id);
	    	  whereCondParams.add(relation_type_id);
	    	  List oldLst=productOffGrpRelationDAO.findByCond(" and group_a_id=? and relation_type_id=? ",whereCondParams);
	    	  for(int i=0;i<oldLst.size();i++){
	    		  //写归档日志
	    		  HashMap oldMap=(HashMap)oldLst.get(i);
	    		  arcProdOffGrpRelationDAO.insert(oldMap);
	    	  }
	    	  productOffGrpRelationDAO.delete(" and group_a_id=? and relation_type_id=?  " +
	    	  		"  ",whereCondParams);
		      for(int i=0;i<regionMaps.size();i++){
		    	  HashMap map=(HashMap)regionMaps.get(i);
		    	  String  seq=(String)map.get("seq");
		    	  if (seq!=null && !"".equals(seq)){
		    		  long  currVal=Long.parseLong(seq)+1;
		    		  seq=String.valueOf(currVal);
		    	  }else
		    		  seq="0";
		    	  map.put("seq",seq);
		    	  map.put("state","00A");
		    	  map.put("excluablity","1");
		    	  map.put("ord_action_type","1");
		    	  map.put("oper_date",DateFormatUtils.getFormatedDateTime());
		    	  map.put("state_date",DateFormatUtils.getFormatedDateTime());
		    	  map.put("create_date",DateFormatUtils.getFormatedDateTime());
		    	  map.put("ord_no","1");
		    	  map.put("event_seq","1");
		    	  productOffGrpRelationDAO.insert(map);
		      }
	  }

	  /**
	   * 查询销售品担保信息
	   * @param dto
	   * @return list
	   * @throws Exception
	   */ 
	  public List qryOffWarr(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     String  offer_id=(String)hashMap.get("offer_id");
		     List  whereCondParams=new ArrayList();
		     whereCondParams.add(offer_id);
		     StringBuffer sql=new StringBuffer();
		     sql.append("select a.*                                                      ");
		    // sql.append("       b.service_offer_name restrict_serv_off_name,              ");
		    // sql.append("       c.service_offer_name conflicted_service_name              ");
		     sql.append("  from offer_warr_requement a  ");
		    // sql.append(" where a.restrict_services = b.service_offer_id(+)               ");
		    // sql.append("   and a.conflicted_services = c.service_offer_id(+)             ");
		     sql.append("   where a.offer_id = ?                                            ");
		     List list=offWarrRequementDAO.findBySql(sql.toString(),whereCondParams);
		     for(int i=0;i<list.size();i++){
		    	 HashMap map=(HashMap)list.get(i);
		    	 String restrict_services=(String)map.get("restrict_services");
		    	 if (restrict_services!=null && !"".equals(restrict_services)){
		    		 String servOffSql=" select service_offer_name from service_offer " +
		    		 		" where service_offer_id in ("+restrict_services+")";
		    		 List servOffLst=offWarrRequementDAO.findBySql(servOffSql);
		    		 String servOffNames="";
		    		 for(int j=0;j<servOffLst.size();j++){
		    			 HashMap servOffMap=(HashMap)servOffLst.get(j);
		    			 String service_offer_name=(String)servOffMap.get("service_offer_name");
		    			 if (j==0) servOffNames=service_offer_name;
		    			 else
		    				 servOffNames=servOffNames+","+service_offer_name;
		    		 }
		    		 map.put("restrict_serv_off_name",servOffNames);
		    	 }
		    	 
		    	 String conflicted_services=(String)map.get("conflicted_services");
		    	 
		    	 if (conflicted_services!=null && !"".equals(conflicted_services)){
		    		 String servOffSql=" select service_offer_name from service_offer " +
		    		 		" where service_offer_id in ("+conflicted_services+")";
		    		 List servOffLst=offWarrRequementDAO.findBySql(servOffSql);
		    		 String servOffNames="";
		    		 for(int j=0;j<servOffLst.size();j++){
		    			 HashMap servOffMap=(HashMap)servOffLst.get(j);
		    			 String service_offer_name=(String)servOffMap.get("service_offer_name");
		    			 if (j==0) servOffNames=service_offer_name;
		    			    else
		    				 servOffNames=servOffNames+","+service_offer_name;
		    		 }
		    		 map.put("conflicted_service_name",servOffNames);
		    	 }
		     }
		     return list;
	  }
	  
	  /**
	   * 增加销售品担保信息
	   * @param dto
	   * @throws Exception
	   */
	  public void saveOrDelOffWarr(DynamicDict dto)  throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     List  offWarrLst=(ArrayList)hashMap.get("prodOffWarrArr");
		     List  whereCondParams=new ArrayList();
		     String offer_id=(String)hashMap.get("offer_id");
		     whereCondParams.add(offer_id);
		     offWarrRequementDAO.delete(" and offer_id=? ",whereCondParams);
		      for(int i=0;i<offWarrLst.size();i++){
		    	  HashMap map=(HashMap)offWarrLst.get(i);
		    	  String offerWarrId=sequenceManageDAO.getNextSequenceWithDB("S_OFFER_WARR_ID",JNDINames.PM_DATASOURCE);
		    	  map.put("offer_warr_requement_id",offerWarrId);
		    	  offWarrRequementDAO.insert(map);
		      }
	  }
	  
	  
	  /**
	   * 修改销售品担保信息
	   * @param dto
	   * @throws Exception
	   */
	  public void updateOffWarr(DynamicDict dto)  throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     offWarrRequementDAO.updateByKey(hashMap,hashMap);
	  }
	  
	  
	  /**
	   * 删除销售品担保信息
	   * @param dto
	   * @throws Exception
	   */
	  public void deleteOffWarr(DynamicDict dto)  throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     offWarrRequementDAO.deleteByKey(hashMap);
	  }
	  
	  
	  /**
	   * 查询关系分组
	   * @param dto
	   * @return list
	   * @throws Exception
	   */
	  public List qryRelaGrp(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     String  relation_type_id=(String)hashMap.get("relation_type_id");
		     String sql=" select conf_group_id group_id, conf_group_name group_name from " +
		     		    " PRODUCT_CONF_GROUP " +
		     		    " where relation_type_id = ?" +
		     		    " and group_type=? ";
		     List  paramLst=new ArrayList();
		     paramLst.add(relation_type_id);
		     //销售品组
		     paramLst.add("01L");
		     List list=productConfGrpDAO.findBySql(sql,paramLst);
		     return list;
	  }
	  
	  
	  
	  /**
	   * 查询目录节点区域限制
	   * @param dto
	   * @return  list
	   * @throws Exception
	   */
	  public List qryOffCataItemReg(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	         String catalog_item_id=(String)hashMap.get("catalog_item_id");
	         List    whereCondParams=new ArrayList();
	         whereCondParams.add(catalog_item_id);
	         StringBuffer sql=new StringBuffer();
	         sql.append("select a.*, b.region_name city_name            ");
	         sql.append("  from product_catalog_item_bureau a, region b ");
	         sql.append(" where a.city_no = b.region_id                 ");
	         sql.append("   and a.catalog_item_id = ?                   ");
	         List list=productCatgItemBureauDAO.findBySql(sql.toString(),whereCondParams);
	         return list;
	  }
	  
	  /**
	   * 保存销售品目录节点区域限制
	   * @param dto
	   * @throws Exception
	   */
	  public void saveOffCataItemReg(DynamicDict dto)  throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     List  itemRegArr=(ArrayList)hashMap.get("prodOffCataItemRegArr");
		     List  whereCondParams=new ArrayList();
		     String catalog_item_id=(String)hashMap.get("catalog_item_id");
		     whereCondParams.add(catalog_item_id);
		     productCatgItemBureauDAO.delete(" and catalog_item_id=? ",whereCondParams);
		     for(int i=0;i<itemRegArr.size();i++){
		    	  HashMap map=(HashMap)itemRegArr.get(i);
		    	  map.put("seq","0");
		    	  map.put("state","00A");
		    	  map.put("oper_date",DateFormatUtils.getFormatedDateTime());
		    	  productCatgItemBureauDAO.insert(map);
		     }
	  }
	  
	  /**
	   * 查询角色
	   * @param dto
	   * @return
	   * @throws Exception
	   */
	  public List qryRoles(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	         List    whereCondParams=new ArrayList();
	         StringBuffer sql=new StringBuffer();
	         sql.append("select * from roles where 1=1  ");
	         List list=productCatgItemRoleDAO.findBySql(sql.toString());
	         return list;
	  }
	  
	  /**
	   * 查询产品目录节点角色约束
	   * @param dto
	   * @return
	   * @throws Exception
	   */
	  public List qryProdCataItemRole(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     String catalog_item_id=(String)hashMap.get("catalog_item_id");
	         List    whereCondParams=new ArrayList();
	         whereCondParams.add(catalog_item_id);
	         StringBuffer sql=new StringBuffer();
	         sql.append("select a.*, b.role_name                     ");
	         sql.append("  from product_catalog_item_role a, roles b ");
	         sql.append(" where a.role_id = b.role_id                ");
	         sql.append("   and a.catalog_item_id = ?                ");
	         List list=productCatgItemRoleDAO.findBySql(sql.toString(),whereCondParams);
	         return list;
	  }
	  
	  /**
	   * 保存产品目录角色
	   * @param dto
	   * @throws Exception
	   */
	  public void saveProdCataItemRole(DynamicDict dto)  throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     List  itemRoleArr=(ArrayList)hashMap.get("prodOffCataItemRoleArr");
		     List  whereCondParams=new ArrayList();
		     String catalog_item_id=(String)hashMap.get("catalog_item_id");
		     whereCondParams.add(catalog_item_id);
		     productCatgItemRoleDAO.delete(" and catalog_item_id=? ",whereCondParams);
		     for(int i=0;i<itemRoleArr.size();i++){
		    	  HashMap map=(HashMap)itemRoleArr.get(i);
		    	  map.put("seq","0");
		    	  map.put("state","00A");
		    	  map.put("oper_date",DateFormatUtils.getFormatedDateTime());
		    	  productCatgItemRoleDAO.insert(map);
		     }
	  }
	  
	  
	  /**
	   * 查询定价对象属主
	   * @param dto
	   * @return list
	   * @throws Exception
	   */
	  public List qryPriceObjectOwner(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     List  whereCondParams=new ArrayList();
		     String  offer_id=(String)hashMap.get("offer_id");
		     whereCondParams.add("10A");
		     whereCondParams.add(offer_id);
		     StringBuffer sql=new StringBuffer();
		     sql.append("select product_id price_object_id, product_name price_object_name,product_id restricted_product_id ,product_name ");
		     sql.append("  from product                                                    ");
		     sql.append(" where product_id in (select element_id                           ");
		     sql.append("                        from product_offer_detail                 ");
		     sql.append("                       where element_type = ?                     ");
		     sql.append("                         and offer_id = ?)                        ");
		     List list=productCatgItemRoleDAO.findBySql(sql.toString(),whereCondParams);
		     return list;
	  }
    
	/**
	 * 查询营业定价
	 * @param dto
	 * @return list
	 * @throws Exception
	 */  
	public List qryTpmBusiPrice(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     List  whereCondParams=new ArrayList();
		     String  offer_id=(String)hashMap.get("offer_id");
		     whereCondParams.add("10C");
		     whereCondParams.add(offer_id);
		     StringBuffer sql=new StringBuffer();
		     sql.append("select a.*, b.service_offer_name,c.name acct_item_type_name," +
		     		"d.product_name price_object_name,'0' reference_offer ");
		     sql.append("  from tpm_busi_price a, service_offer b, acct_item_type c,product d  ");
		     sql.append(" where a.service_offer_id = b.service_offer_id    " +
		     		" and a.price_object_id=d.product_id          ");
		     sql.append("   and a.acct_item_type_id = c.acct_item_type_id(+)         ");
		     sql.append("   and a.limit_object_type = ?                              ");
		     sql.append("   and a.limit_object_id = ?                                ");
		     List list=productCatgItemRoleDAO.findBySql(sql.toString(),whereCondParams);
		     return list;
	 }
	
	 /**
	  * 保存营业定价
	  * @param dto
	  * @throws Exception
	  */
	 public void saveOrUpdateTpmBusiPrice(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      List  priceArr=(ArrayList)hashMap.get("prodOffPriceArr");
		      List  pricePlanArr=(ArrayList)hashMap.get("prodOffPriceArr"); 
		      List  whereCondParams=new ArrayList();
		      String limit_object_id=(String)hashMap.get("limit_object_id");
	    	  whereCondParams.add(limit_object_id);
	    	  whereCondParams.add("10C");
	    	  
	    	  List oldLst=tpmBusiPriceDAO.findByCond(" and limit_object_id=? and limit_object_type=? ",whereCondParams);
	    	  for(int i=0;i<oldLst.size();i++){
	    		  //写归档日志
	    		  HashMap oldMap=(HashMap)oldLst.get(i);
	    		  String oldPriceId=(String)oldMap.get("price_id");
	    		  List paramLst=new ArrayList();
	    		  paramLst.add(oldPriceId);
	    		  
	    		  List  arcMapLst=arcTpmBusiPriceDAO.findBySql("select max(seq) seq from arc_tpm_busi_price " +
	    		  		" where price_id=?  ",paramLst);
	    		  if (!arcMapLst.isEmpty()){
	    			  String seq=(String)((HashMap)arcMapLst.get(0)).get("seq");
	    			  if (seq!=null && !"".equals(seq)){
	    				  long  currVal=Long.parseLong(seq)+1;
			    		  seq=String.valueOf(currVal);
			    		  oldMap.put("seq",seq);
	    			  }
	    		  }
	    		  arcTpmBusiPriceDAO.insert(oldMap);
	    	  }
	    	  tpmBusiPriceDAO.delete(" and limit_object_id=? and limit_object_type=? ",whereCondParams);
	    	  
		      for(int i=0;i<priceArr.size();i++){
		    	  HashMap map=(HashMap)priceArr.get(i);
		    	  String  seq=(String)map.get("seq");
		    	  if (seq!=null && !"".equals(seq)){
		    		  long  currVal=Long.parseLong(seq)+1;
		    		  seq=String.valueOf(currVal);
		    	  }else
		    		  seq="0";
		    	  map.put("seq",seq);
		    	  map.put("state","00A");
		    	  map.put("oper_date",DateFormatUtils.getFormatedDateTime());
		    	  tpmBusiPriceDAO.insert(map);
		      }
	  }
	 
	 
	 
	  /**
	   * 获得定价计划标识
	   * @param dto
	   * @return
	   * @throws Exception
	   */
	  public  String getNewPriceId(DynamicDict dto)throws Exception{
	          String priceId=sequenceManageDAO.getNextSequenceWithDB("S_PRICE_ID",JNDINames.PM_DATASOURCE);
	          return priceId;
      }

	  
     /**
      * 查询营业定价计划
      * @param dto
      * @return list
      * @throws Exception
      */ 	  
	 public  List qryPricePlan(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     List  whereCondParams=new ArrayList();
		     String  plan_object_type=(String)hashMap.get("plan_object_type");
		     String  plan_object_id=(String)hashMap.get("plan_object_id");
		     String  price_id=(String)hashMap.get("price_id");
		     whereCondParams.add(plan_object_type);
		     whereCondParams.add(plan_object_id);
		     whereCondParams.add(price_id);
		     StringBuffer sql=new StringBuffer();
		     sql.append("select a.*, b.region_name                ");
		     sql.append("  from tpm_busi_price_plan a, region b   ");
		     sql.append(" where a.region_id = b.region_id         ");
		     sql.append("   and a.plan_object_type = ?            ");
		     sql.append("   and a.plan_object_id = ?              ");
		     sql.append("   and a.price_id = ?                    ");
		     List list=tpmBusiPricePlanDAO.findBySql(sql.toString(),whereCondParams);
		     for(int i=0;i<list.size();i++){
		    	 HashMap map=(HashMap)list.get(i);
		    	 map.put("price_object_type",plan_object_type);
		    	 map.put("price_object_id",plan_object_id);
		     }
		     return list;
	 }

	 
	 /**
	  * 保存销售品定价计划
	  * @param dto
	  * @throws Exception
	  */
	 public void saveOffPricePlan(DynamicDict dto) throws Exception{
	          HashMap map=(HashMap)dto.getValueByName("parameter");
	          map.put("seq","0");
	    	  map.put("state","00A");
	    	  map.put("oper_date",DateFormatUtils.getFormatedDateTime());
	          tpmBusiPricePlanDAO.insert(map);
     }

	 /**
	  * 删除销售品定价计划
	  * @param dto
	  * @throws Exception
	  */
	 public void deleteOffPricePlan(DynamicDict dto) throws Exception{
	         HashMap map=(HashMap)dto.getValueByName("parameter");
	         List  whereCondParams=new ArrayList();
	         String  plan_object_type=(String)map.get("plan_object_type");
		     String  plan_object_id=(String)map.get("plan_object_id");
		     String  price_id=(String)map.get("price_id");
		     String  region_id=(String)map.get("region_id");
		     whereCondParams.add(plan_object_type);
		     whereCondParams.add(plan_object_id);
		     whereCondParams.add(price_id);
		     whereCondParams.add(region_id);
		     List oldLst=tpmBusiPricePlanDAO.findBySql("select * from tpm_busi_price_plan where " +
	         		" PLAN_OBJECT_TYPE=? and PLAN_OBJECT_ID=? and PRICE_ID=? and region_id=? ",whereCondParams);
		     if (!oldLst.isEmpty()){
		    	  HashMap oldMap=(HashMap)oldLst.get(0);
		    	  List  arcMapLst=tpmBusiPricePlanDAO.findBySql("select max(seq) seq from arc_tpm_busi_price_plan where " +
			         		" PLAN_OBJECT_TYPE=? and PLAN_OBJECT_ID=?" +
			         		"  and PRICE_ID=? and region_id=? ",whereCondParams);
		    	  if (!arcMapLst.isEmpty()){
		    			  String seq=(String)((HashMap)arcMapLst.get(0)).get("seq");
		    			  if (seq!=null && !"".equals(seq)){
		    				  long  currVal=Long.parseLong(seq)+1;
				    		  seq=String.valueOf(currVal);
				    		  oldMap.put("seq",seq);
		    			  }
		    	   }
		    	  String oldPriceId=(String)oldMap.get("price_id");
		    	  if (oldPriceId!=null && !"".equals(oldPriceId)){
			    	  arcTpmBusiPricePlanDAO.insert(oldMap);
			    	  tpmBusiPricePlanDAO.delete(" and PLAN_OBJECT_TYPE=? and PLAN_OBJECT_ID=? and PRICE_ID=? and region_id=? ",whereCondParams);
		          }
		     }
     }
	 
	 
	 
	 /**
	  * 查询产品属性
	  * @param dto
	  * @return list
	  * @throws Exception
	  */
	 public  List qryProdAttr(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     List  whereCondParams=new ArrayList();
		     String  plan_object_id=(String)hashMap.get("plan_object_id");
		     whereCondParams.add(plan_object_id);
		     StringBuffer sql=new StringBuffer();
		     sql.append("select a.*, b.attr_name,b.attr_code ");
		     sql.append("  from product_attr a, attribute b ");
		     sql.append(" where a.attr_seq = b.attr_id      ");
		     sql.append("   and a.product_id = ?            ");
		     List list=tpmBusiPricePlanDAO.findBySql(sql.toString(),whereCondParams);
		     return list;
	 }
	 
	 
	/**
	 * 查询营业定价参数
	 * @param dto
	 * @return list
	 * @throws Exception
	 */ 
    public  List qryOffPriceParam(DynamicDict dto) throws Exception{
		     HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		     List  whereCondParams=new ArrayList();
		     String  plan_object_type=(String)hashMap.get("plan_object_type");
		     String  plan_object_id=(String)hashMap.get("plan_object_id");
		     String  price_id=(String)hashMap.get("price_id");
		     whereCondParams.add(plan_object_type);
		     whereCondParams.add(plan_object_id);
		     whereCondParams.add(price_id);
		     StringBuffer sql=new StringBuffer();
		     sql.append(" and owner_object_type=? and owner_object_id=? and price_id=? ");
		     List list=tpmBusiPriceParaDAO.findByCond(sql.toString(),whereCondParams);
		     for(int i=0;i<list.size();i++){
		    	 HashMap map=(HashMap)list.get(i);
		    	 map.put("price_object_type",plan_object_type);
		    	 map.put("price_object_id",plan_object_id);
		     }
		     return list;
	 }
    
    
    /**
     * 保存营业定价参数
     * @param dto
     * @throws Exception
     */
	public void saveOrUpdateOffPriceParam(DynamicDict dto) throws Exception{
	         HashMap map=(HashMap)dto.getValueByName("parameter");
	         List  priceParamArr=(ArrayList)map.get("priceParamArr");
	         if (priceParamArr==null) return;
	         for(int i=0;i<priceParamArr.size();i++){
	        	 HashMap currMap=(HashMap)priceParamArr.get(i);
	        	 tpmBusiPriceParaDAO.deleteByKey(currMap);
	        	 List  whereCondParams=new ArrayList();
	        	 String price_id=(String)currMap.get("price_id");
	        	 String owner_object_type=(String)currMap.get("owner_object_type");
	        	 String owner_object_id=(String)currMap.get("owner_object_id");
	        	 String para_name=(String)currMap.get("para_name");
	        	 whereCondParams.add(price_id);
	        	 whereCondParams.add(owner_object_type);
	        	 whereCondParams.add(owner_object_id);
	        	 whereCondParams.add(para_name);
	        	 List paramLst=arcTpmBusiPriceParaDAO.findByCond(" and price_id=?  " +
	        	 		" and owner_object_type=? " +
	        	 		" and owner_object_id=? " +
	        	 		" and para_name=? ",whereCondParams);
	        	 String seq="0";
	        	 if (!paramLst.isEmpty()){
	        		 HashMap paraMap=(HashMap)paramLst.get(0);
	        		 seq=(String)paraMap.get("seq");
	        		 if (seq!=null && !"".equals(seq)){
	        			 long  currVal=Long.parseLong(seq)+1;
			    		 seq=String.valueOf(currVal);
			    		 paraMap.put("seq",seq);
			    		 arcTpmBusiPriceParaDAO.insert(paraMap);
	        		 }else{
	        			  seq="0";
	        			  currMap.put("seq",seq);
	        			  currMap.put("state","00A");
	        			  currMap.put("oper_date",DateFormatUtils.getFormatedDateTime());
	     		   	      arcTpmBusiPriceParaDAO.insert(map);
	        		 }		 
	        	 }
	        	 currMap.put("seq",seq);
	        	 currMap.put("state","00A");
	        	 currMap.put("oper_date",DateFormatUtils.getFormatedDateTime());
		   	     tpmBusiPriceParaDAO.insert(currMap);
	         }
    }
	
	
   /**
    * 删除定价参数
    * @param dto
    * @throws Exception
    */	
   public  void delPriceParam(DynamicDict dto)throws Exception{
		   HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		   long delCount=tpmBusiPriceParaDAO.deleteByKey(hashMap);
		   List  whereCondParams=new ArrayList();
      	   String price_id=(String)hashMap.get("price_id");
      	   String owner_object_type=(String)hashMap.get("owner_object_type");
      	   String owner_object_id=(String)hashMap.get("owner_object_id");
      	   String para_name=(String)hashMap.get("para_name");
      	   whereCondParams.add(price_id);
      	   whereCondParams.add(owner_object_type);
      	   whereCondParams.add(owner_object_id);
      	   whereCondParams.add(para_name);
      	   List paramLst=arcTpmBusiPriceParaDAO.findByCond(" and price_id=?  " +
     	 		" and owner_object_type=? " +
     	 		" and owner_object_id=? " +
     	 		" and para_name=? ",whereCondParams);
	      String seq="0";
	      if (!paramLst.isEmpty()){
     		  HashMap paraMap=(HashMap)paramLst.get(0);
     		  seq=(String)paraMap.get("seq");
     		  if (seq!=null && !"".equals(seq)){
     			 long  currVal=Long.parseLong(seq)+1;
		    		 seq=String.valueOf(currVal);
		    		 paraMap.put("seq",seq);
		    		 arcTpmBusiPriceParaDAO.insert(paraMap);
     		  }else{
     			     paraMap.put("seq",seq);
	    		     arcTpmBusiPriceParaDAO.insert(paraMap);
     		  }	 
	     }else{
	    	 if (delCount>0) arcTpmBusiPriceParaDAO.insert(hashMap);
	     }
   }
   
   
      /**
       * 查询销售品内关系定义
       * @param dto
       * @return list
       * @throws Exception
       */
	  public  List qryOffRelaDef(DynamicDict dto) throws Exception{
		      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		      List  whereCondParams=new ArrayList();
			  String  offer_id=(String)hashMap.get("offer_id");
			  whereCondParams.add(offer_id);
			  StringBuffer sql=new StringBuffer();
		      sql.append(" select * from OFFER_RELATION_DEF where product_offer_id=? ");
			  List list=offRelationDefDAO.findBySql(sql.toString(),whereCondParams);
			  return list;
	  }
	  
	  /**
	   * 保存销售品内关系定义
	   * @param dto
	   * @throws Exception
	   */
	  public void  saveOffRelaDef(DynamicDict dto) throws Exception{
		      HashMap map=(HashMap)dto.getValueByName("parameter");
		      String relationId=sequenceManageDAO.getNextSequenceWithDB("S_RELATION_ID",JNDINames.PM_DATASOURCE);
   		      map.put("relation_id",relationId);
	    	  map.put("state","00A");
	    	  map.put("excluablity","1");
	    	  map.put("ord_action_type","1");
	    	  map.put("oper_date",DateFormatUtils.getFormatedDateTime());
	    	  map.put("state_date",DateFormatUtils.getFormatedDateTime());
	    	  map.put("ord_no","1");
	    	  map.put("event_seq","1");
	    	  map.put("seq","0");
	    	  offRelationDefDAO.insert(map);
	  }
	  
	  /**
	   * 更新销售品内关系定义
	   * @param dto
	   * @throws Exception
	   */
	  public void  updateOffRelaDef(DynamicDict dto) throws Exception{
		      HashMap map=(HashMap)dto.getValueByName("parameter");
		      HashMap oldMap=offRelationDefDAO.findByPrimaryKey(map);
		      HashMap arcMap=arcOffRelationDefDAO.findByPrimaryKey(map);
		      String arcSeq=(String)arcMap.get("seq");
		      if (arcSeq!=null && !"".equals(arcSeq)){
	    			 long  currVal=Long.parseLong(arcSeq)+1;
	    			 arcSeq=String.valueOf(currVal);
	    			 oldMap.put("seq",arcSeq);
	    			 arcOffRelationDefDAO.insert(oldMap);
		      }else{
		    	  arcOffRelationDefDAO.insert(oldMap);
		      }
	    	  offRelationDefDAO.updateByKey(map,map);
      }
	  
	 /**
	  * 删除销售品关系定义
	  * @param dto
	  * @throws Exception
	  */ 
	 public void  deleteOffRelaDef(DynamicDict dto) throws Exception{
	      HashMap map=(HashMap)dto.getValueByName("parameter");
	      HashMap oldMap=offRelationDefDAO.findByPrimaryKey(map);
	      HashMap arcMap=arcOffRelationDefDAO.findByPrimaryKey(map);
	      String arcSeq=(String)arcMap.get("seq");
	      offRelationDefDAO.deleteByKey(map);
	      if (arcSeq!=null && !"".equals(arcSeq)){
    			 long  currVal=Long.parseLong(arcSeq)+1;
    			 arcSeq=String.valueOf(currVal);
    			 oldMap.put("seq",arcSeq);
    			 arcOffRelationDefDAO.insert(oldMap);
	      }else{
	    	     arcOffRelationDefDAO.insert(oldMap);
	      }
      }
	 
	 
	  /**
	   * 查询销售品关系限制
	   * @param dto
	   * @return
	   * @throws Exception
	   */
	  public  List qryOffRelaRest(DynamicDict dto) throws Exception{
	      HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	      List  whereCondParams=new ArrayList();
		  String  relation_id=(String)hashMap.get("relation_id");
		  whereCondParams.add(relation_id);
		  StringBuffer sql=new StringBuffer();
	      sql.append(" select a.*, b.product_name from OFFER_RELATION_RESTRICTED a, product b " +
		      		 " where a.relation_id = ? " +
		      		 " and a.restricted_product_id = b.product_id ");
		  List list=offRelationDefDAO.findBySql(sql.toString(),whereCondParams);
		  return list;
      }
	  
	  
	  
	  /**
	   * 保存关系限制
	   * @param dto
	   * @throws Exception
	   */
	  public void  saveOffRelaRest(DynamicDict dto) throws Exception{
		      HashMap map=(HashMap)dto.getValueByName("parameter");
		      String restrict_id=sequenceManageDAO.getNextSequenceWithDB("S_OFFER_RESTRICTED_ID",JNDINames.PM_DATASOURCE);
			  map.put("offer_restricted_id",restrict_id);
	    	  map.put("state","00A");
	    	  map.put("excluablity","1");
	    	  map.put("ord_action_type","1");
	    	  map.put("oper_date",DateFormatUtils.getFormatedDateTime());
	    	  map.put("state_date",DateFormatUtils.getFormatedDateTime());
	    	  map.put("ord_no","1");
	    	  map.put("event_seq","1");
	    	  map.put("seq","0");
	    	  OffRelationRestrictedDAO.insert(map);
      }
 
	 /**
	  * 更新销售品关系限制
	  * @param dto
	  * @throws Exception
	  */ 
	 public void  updateOffRelaRest(DynamicDict dto) throws Exception{
		      HashMap map=(HashMap)dto.getValueByName("parameter");
		      HashMap oldMap=OffRelationRestrictedDAO.findByPrimaryKey(map);
		      HashMap arcMap=arcOffRelationRestrictedDAO.findByPrimaryKey(map);
		      String arcSeq=(String)arcMap.get("seq");
		      if (arcSeq!=null && !"".equals(arcSeq)){
	    			 long  currVal=Long.parseLong(arcSeq)+1;
	    			 arcSeq=String.valueOf(currVal);
	    			 oldMap.put("seq",arcSeq);
	    			 arcOffRelationRestrictedDAO.insert(oldMap);
		      }else{
		    	  arcOffRelationRestrictedDAO.insert(oldMap);
		      }
		      OffRelationRestrictedDAO.updateByKey(map,map);
      }
	 
	 
	 /**
	  * 删除销售品关系限制
	  * @param dto
	  * @throws Exception
	  */
	 public void  deleteOffRelaRest(DynamicDict dto) throws Exception{
	      HashMap map=(HashMap)dto.getValueByName("parameter");
	      HashMap oldMap=OffRelationRestrictedDAO.findByPrimaryKey(map);
	      HashMap arcMap=arcOffRelationRestrictedDAO.findByPrimaryKey(map);
	      String arcSeq=(String)arcMap.get("seq");
	      OffRelationRestrictedDAO.deleteByKey(map);
	      if (arcSeq!=null && !"".equals(arcSeq)){
   			 long  currVal=Long.parseLong(arcSeq)+1;
   			 arcSeq=String.valueOf(currVal);
   			 oldMap.put("seq",arcSeq);
   			arcOffRelationRestrictedDAO.insert(oldMap);
	      }else{
	    	  arcOffRelationRestrictedDAO.insert(oldMap);
	      }
     }


	  
	  





	
	
	

	


	 
	 
	 
	 








	  
	  
	  
	  


	  
	  
	  
 

	   
	   
	   

	   
	   
	   
	   

	   
	   
	   
	   



	    
	    

       
}
