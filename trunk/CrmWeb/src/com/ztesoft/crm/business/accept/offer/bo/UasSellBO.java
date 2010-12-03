/**
 * 
 */
package com.ztesoft.crm.business.accept.offer.bo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.DesEncrypt;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.crm.business.accept.offer.vo.OfferCatgeVO;
import com.ztesoft.crm.business.accept.offer.vo.PackagesInfoVO;

import com.ztesoft.crm.business.common.consts.Actions;
import com.ztesoft.crm.business.common.query.SqlMapExe;
import com.ztesoft.crm.business.common.query.Sqls;
import com.ztesoft.crm.business.common.sem.engine.BusiEngine;
import com.ztesoft.crm.business.common.specs.dao.OffCollectionDAO;
import com.ztesoft.crm.business.common.specs.vo.OffCollectionVO;
import com.ztesoft.crm.business.common.utils.SeqUtil;
import com.ztesoft.crm.product.dao.ProductCatgItemDAO;
import com.ztesoft.crm.product.vo.ProdCatgItemVO;
import com.ztesoft.oaas.dao.areacode.AreaCodeDAO;
import com.ztesoft.oaas.dao.areacode.AreaCodeDAOFactory;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAO;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAOFactory;
import com.ztesoft.oaas.dao.mpdepart.MpDepartDAO;
import com.ztesoft.oaas.dao.mpdepart.MpDepartDAOFactory;
import com.ztesoft.oaas.dao.mpdepartterm.MpDepartTermDAO;
import com.ztesoft.oaas.dao.mpdepartterm.MpDepartTermDAOFactory;
import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.organization.OrganizationDAOFactory;
import com.ztesoft.oaas.dao.organizationtype.OrganizationTypeDAO;
import com.ztesoft.oaas.dao.organizationtype.OrganizationTypeDAOFactory;
import com.ztesoft.oaas.dao.partner.PartnerDAO;
import com.ztesoft.oaas.dao.partner.PartnerDAOFactory;
import com.ztesoft.oaas.dao.party.PartyDAO;
import com.ztesoft.oaas.dao.party.PartyDAOFactory;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAO;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAOFactory;
import com.ztesoft.oaas.dao.priv.PrivDAO;
import com.ztesoft.oaas.dao.priv.PrivDAOFactory;
import com.ztesoft.oaas.dao.region.RegionDAO;
import com.ztesoft.oaas.dao.region.RegionDAOFactory;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAO;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAOFactory;
import com.ztesoft.oaas.dao.rrlan.RrLanDAO;
import com.ztesoft.oaas.dao.rrlan.RrLanDAOFactory;
import com.ztesoft.oaas.dao.staff.StaffDAO;
import com.ztesoft.oaas.dao.staff.StaffDAOFactory;
import com.ztesoft.oaas.dao.staffpriv.StaffPrivDAO;
import com.ztesoft.oaas.dao.staffpriv.StaffPrivDAOFactory;
import com.ztesoft.oaas.struct.EncryptRequest;
import com.ztesoft.oaas.struct.EncryptRespond;
import com.ztesoft.oaas.struct.LoginRequest;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;
import com.ztesoft.oaas.utils.OAASProxy;
import com.ztesoft.oaas.vo.AreaCodeVO;
import com.ztesoft.oaas.vo.MpDepartVO;
import com.ztesoft.oaas.vo.OrganizationTypeVO;
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.PartyRoleVO;
import com.ztesoft.oaas.vo.PartyVO;
import com.ztesoft.oaas.vo.RegionVO;
import com.ztesoft.oaas.vo.RrBusinessVO;
import com.ztesoft.oaas.vo.RrLanVO;
import com.ztesoft.oaas.vo.StaffVO;
/**
 * @author Administrator
 *
 */
public class UasSellBO extends DictAction{
	 
	private static SqlMapExe  sqlMapExe=SqlMapExe.getInstance();//SQL查询工具
	   private   int etype;
	   private   int ecode;
	   private   String edesc;
	
//销售品目录树的查询方法
	 public ArrayList executeQuery(DynamicDict dto) throws Exception {
		 ArrayList resultLst = new ArrayList();
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String parentMenuId  = (String)m.get("parentMenuId");
		 String[] params  = new String[2];
		 String sql = "select pkey from dc_public where stype ='10'";
		 String catalog_id = sqlMapExe.querySingleValue(sql);//获取营业受理销售目录树ID
		 String serialXml = "<items/>";
		 if(catalog_id==null||"".equals(catalog_id))
		 {
			 return resultLst;
		 }
		 ProductCatgItemDAO productCatgItemDAO = new ProductCatgItemDAO();
		 HashMap paramMap = new HashMap();
		 paramMap.put("parent_catalog_item_id", parentMenuId);
		 paramMap.put("catalog_id", catalog_id);
		 String findMenuCond = "and parent_catalog_item_id = ? and catalog_id = ? and state = '00A' ";
		 ArrayList alMenus = (ArrayList)productCatgItemDAO.findByCond(findMenuCond, this.translateWhereCondMap(paramMap), etype, ecode, edesc);
		 if(alMenus==null||alMenus.size() ==0)//如果为叶子节点 那么查询是否挂有套餐销售品
		 {
			 String[] sqlParams = new String[1];
			 sqlParams[0] = parentMenuId;
			 ArrayList alOffer = (ArrayList)sqlMapExe.queryForMapList("findOfferSql", sqlParams);
			 if(alOffer!=null)
			 {
				 for(int i = 0;i<alOffer.size();i++)
				 {
					 HashMap map=(HashMap)alOffer.get(i);
					 OfferCatgeVO  offerCatgVo = new OfferCatgeVO();
					 offerCatgVo.setCatgItemId((String) map.get("CATALOG_ITEM_ID".toLowerCase()));
			    	 offerCatgVo.setParentCatgItemId((String) map.get("PARENT_CATALOG_ITEM_ID".toLowerCase()));
			    	 offerCatgVo.setCatgItemName((String) map.get("OFFER_NAME".toLowerCase()));
			    	 offerCatgVo.setOfferId((String) map.get("OFFER_ID".toLowerCase()));
			    	 offerCatgVo.setOfferKind((String) map.get("OFFER_KIND".toLowerCase()));
			    	 offerCatgVo.setOfferComments((String) map.get("OFFER_COMMENTS".toLowerCase()));
			    	 resultLst.add(offerCatgVo);
				 }
				 return resultLst;//XMLSegBuilder.toXmlItems(resultLst);
			 }
			 else
			 {
				 return resultLst;
			 }
		 }
		 for(int i=0;i<alMenus.size();i++){
	    	  HashMap map=(HashMap)alMenus.get(i);
	    	  OfferCatgeVO  offerCatgVo = new OfferCatgeVO();
	    	  offerCatgVo.setCatgItemId((String) map.get("CATALOG_ITEM_ID".toLowerCase()));
	    	  offerCatgVo.setParentCatgItemId((String) map.get("PARENT_CATALOG_ITEM_ID".toLowerCase()));
	    	  offerCatgVo.setCatgItemName((String) map.get("CATALOG_ITEM_NAME".toLowerCase()));
	    	  offerCatgVo.setOfferId("");
	    	  resultLst.add(offerCatgVo);
	      }
		 return resultLst;
	 }
	 //销售品包的查询方法
	 public List executeQuery2(DynamicDict dto) throws Exception {
		 List resultLst = new ArrayList();
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String offerId  = (String)m.get("offerId");
		 String[] sqlParams = new String[2];//构建SQL工具查询参数
		 sqlParams[0] = offerId;
		 sqlParams[1] = offerId;
		 ArrayList alPackageMap = (ArrayList)sqlMapExe.queryForMapList("findOfferPacketSql", sqlParams);
		// SeqUtil seqUtil = new SeqUtil();
		 if(alPackageMap!=null)
		 {
			 for(int i = 0;i<alPackageMap.size();i++)
			 {
				 HashMap map=(HashMap)alPackageMap.get(i);
				 PackagesInfoVO  packageInfoVo = new PackagesInfoVO();
				 PackagesInfoVO  packageInfoVoAdd = new PackagesInfoVO();
				 packageInfoVo.loadFromHashMap(map);
				 String min_num = packageInfoVo.getRole_min_num();
				 String max_num = packageInfoVo.getRole_max_num();
	
	
				 packageInfoVo.setProduct_offer_instance_id(SeqUtil.getInst().getNext("PRODUCT_OFFER", "OFFER_ID"));
		    	 resultLst.add(packageInfoVo);
/*				 if(min_num!=null&&!"".equals(min_num)&&max_num!=null&&!"".equals(max_num))
				 {
					 if(Integer.parseInt(max_num)>1)
					 {
						 packageInfoVoAdd.loadFromHashMap(map);
						 packageInfoVoAdd.setProduct_offer_instance_id(SeqUtil.getInst().getNext("PRODUCT_OFFER", "OFFER_ID"));
						 resultLst.add(packageInfoVoAdd);
					 }
				 }*/
			 }
		 }
		return resultLst;
	 }
	 
	 //查询销售品属性
	 public List executeQuery3(DynamicDict dto) throws Exception {
		 List resultLst = new ArrayList();
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String offerId  = (String)m.get("packageId");
		 String product_offer_instance_id  = (String)m.get("product_offer_instance_id");
		 String rela_offer_instance_id  = (String)m.get("rela_offer_instance_id");
		 String[] sqlParams = new String[3];//构建SQL工具查询参数
		 sqlParams[0] = product_offer_instance_id;
		 sqlParams[1] = rela_offer_instance_id;
		 sqlParams[2] = offerId;
		 
		 ArrayList alPackageMap = (ArrayList)sqlMapExe.queryForMapList("findOfferAttrSql", sqlParams);
		return alPackageMap;
	 }
	 
	 
	 //根据名称获取销售品
	 public List executeQuery4(DynamicDict dto) throws Exception {
		 List resultLst = new ArrayList();
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String offerName  = (String)m.get("offerName");
		 String[] sqlParams = new String[1];//构建SQL工具查询参数
		 sqlParams[0] = "%"+offerName+"%";
		 ArrayList alOfferMap = (ArrayList)sqlMapExe.queryForMapList("findOfferByNameSql", sqlParams);
		 if(alOfferMap!=null)
		 {
			 for(int i = 0;i<alOfferMap.size();i++)
			 {
				 HashMap map=(HashMap)alOfferMap.get(i);
				 OfferCatgeVO  offerCatgVo = new OfferCatgeVO();
				 offerCatgVo.loadFromHashMap(map);
		    	 resultLst.add(offerCatgVo);
			 }
			
		 }
		return resultLst;
	 }
	 
	 //加载营业员热卖销售品
	 public List executeQuery5(DynamicDict dto) throws Exception {
		 List resultLst = new ArrayList();
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String partyRoleId  = (String)m.get("partyRoleId");
		 
		 String[] sqlParams = new String[1];//构建SQL工具查询参数
		 sqlParams[0] = partyRoleId;
		 ArrayList hotSaleOfferMap = (ArrayList)sqlMapExe.queryForMapList("hotSaleSql", sqlParams);
		 
		 for(int i=0;i<hotSaleOfferMap.size();i++){
	    	  HashMap map=(HashMap)hotSaleOfferMap.get(i);
	    	  OffCollectionVO  OffCollectionVo = new OffCollectionVO();
	    	  OffCollectionVo.setCollectionId((String) map.get("COLLECTIOIN_ID".toLowerCase()));
	    	  OffCollectionVo.setOfferName((String) map.get("OFFER_NAME".toLowerCase()));
	    	  OffCollectionVo.setProductOfferId((String) map.get("OFFER_ID".toLowerCase()));
	    	  OffCollectionVo.setOfferKind((String) map.get("OFFER_KIND".toLowerCase()));
	    	  OffCollectionVo.setOfferComments((String) map.get("OFFER_COMMENTS".toLowerCase()));
	    	  resultLst.add(OffCollectionVo);
	      }

		return resultLst;
	 }
	 //校验热卖套餐是否存在
	 public Map executeValid(DynamicDict dto) throws Exception {
		 Map m = (Map)dto.getValueByName("parameter") ;
		 HashMap retMap = new HashMap();
		 
		 String offerId  = (String)m.get("offerId");
		 String checkPass = "false";
		 String[] sqlParams = new String[1];//构建SQL工具查询参数
		 sqlParams[0] = offerId;
		 
		 String checkSql = "select 1 from offer_collection b where b.product_offer_id = ? ";
		 String offerExsit = sqlMapExe.querySingleValue(checkSql, sqlParams);
		 if(offerExsit==null||"".equals(offerExsit)) 
		 {
			 checkPass = "true";
		 }
		 retMap.put("checkPass", checkPass);
		 return retMap;
	 }
	 //增加热卖
	 public Map performAdd(DynamicDict dto) throws Exception {
		 Map m = (Map)dto.getValueByName("parameter") ;
		 HashMap retMap = new HashMap();
		 
		 String offerId  = (String)m.get("offerId");
		 String partyRoleId =  (String)m.get("partyRoleId");
		 String checkPass = "false";
		 String[] sqlParams = new String[1];//构建SQL工具查询参数
		 sqlParams[0] = offerId;
		 
		 String getSeqSql = "select seq_offer_collection.nextval from dual ";//获取序列
		 String collectionId = sqlMapExe.querySingleValue(getSeqSql);
		 OffCollectionVO OffCollectionVo = new OffCollectionVO();
		 OffCollectionDAO OffCollectionDao = new OffCollectionDAO();
		 OffCollectionVo.setCollectionId(collectionId);
		 OffCollectionVo.setPartyId(partyRoleId);
		 OffCollectionVo.setPartyRoleId(partyRoleId);
		 OffCollectionVo.setProductOfferId(offerId);
		 OffCollectionVo.setCollectionType("C");
		 
		 OffCollectionVo.setOperDate(DAOUtils.getFormatedDate());
		// OffCollectionVo.setOperDate(DAOUtils.getFormatedDate());
		 OffCollectionVo.setSeq("0");
		 OffCollectionVo.setState("00A");
		 
		 OffCollectionDao.insert(OffCollectionVo.unloadToHashMap(), etype, ecode, edesc);
		 
		 retMap.put("checkPass", checkPass);
		 return retMap;
	 }
	
	 //删除热卖
	 public Map performDel(DynamicDict dto) throws Exception {
		 Map m = (Map)dto.getValueByName("parameter") ;
		 HashMap retMap = new HashMap();
		 
		 String offerId  = (String)m.get("offerId");
		 String partyRoleId =  (String)m.get("partyRoleId");
		 String checkPass = "false";
		 List sqlParams = new ArrayList();//
		 sqlParams.add(offerId);
		 OffCollectionDAO OffCollectionDao = new OffCollectionDAO();
		 OffCollectionDao.delete(" and product_offer_id = ? ", sqlParams, etype, ecode, edesc);
		 retMap.put("checkPass", checkPass);
		 return retMap;
	 }
	//获取基础包
	 public Map executeQuery6(DynamicDict dto) throws Exception {
		 Map m = (Map)dto.getValueByName("parameter") ;
		 HashMap retMap = new HashMap();
		 
		 String offerId  = (String)m.get("offerId");

		 String[] sqlParams = new String[2];//构建SQL工具查询参数
		 sqlParams[0] = offerId;
		 sqlParams[1] = "20A";//基础包
		 ArrayList alPackageMap = (ArrayList)sqlMapExe.queryForMapList("findPackageSql", sqlParams);
		 if(alPackageMap!=null)
		 {
			 for(int i = 0;i<alPackageMap.size();i++)
			 {
				 HashMap map=(HashMap)alPackageMap.get(i);
				 PackagesInfoVO  packageInfoVo = new PackagesInfoVO();
				 packageInfoVo.loadFromHashMap(map);
				 retMap.put("package_id", packageInfoVo.getPackage_id());
				 retMap.put("package_name", packageInfoVo.getPackage_name());
			 }
			
		 }
		 return retMap;
	 }
	 //查询销售品成员构成
	 public ArrayList executeQuery7(DynamicDict dto) throws Exception 
		{
			ArrayList aList = new ArrayList();
			 Map m = (Map)dto.getValueByName("parameter") ;
			 String offer_id  = (String)m.get("offer_id");
			 String custId = (String)m.get("cust_id");
			 String lan_id = (String)m.get("lan_id");
				 String product_offer_instance_id  = (String)m.get("product_offer_instance_id");
				// String packet_type  = (String)m.get("packet_type");
				if (offer_id == null||"".equals(offer_id)||"undefined".equals(offer_id)) return aList;
				//if (packet_type == null || "".equals(packet_type)) return aList;
					String sql = " select a.offer_name,c.product_name as product,d.role_name,b.*,c.product_id,d.comp_role_id,? as product_offer_instance_id,a.packet_type,a.offer_kind ";                  
					sql = sql +  " from product_offer a , product_offer_detail b, product c,prod_comp_detail_role d ";
					sql = sql +  " where b.offer_id = ? ";              
					sql = sql +  " and b.state = '00A' ";                                     
					sql = sql +  " and a.offer_id = b.offer_id ";                             
					sql = sql +  " and a.state = '00A' ";                                     
					sql = sql +  " and c.product_id = b.element_id ";                         
					sql = sql +  " and c.state = '00A' ";
					sql = sql +  " and d.comp_role_id = b.comp_role_id ";
					sql = sql +  " and d.state = '00A' ";
					sql = sql +  " order by a.packet_type desc ";
					
					DynamicDict oDict = Base.query("CRMDB", sql, new String[] {product_offer_instance_id,offer_id},0xff47fe24,"OFFER_INFO");
					
					String pre_comp_role_id = "";
					/*String pre_nbr_use = "";
					String pre_nbr_useValue = "";*/
					HashMap roleMap  = new HashMap();
					for (int j = 0; j < oDict.getCountByName("OFFER_INFO"); j++)
					{
						HashMap hashmap = (HashMap)oDict.getValueByName("OFFER_INFO", j);
						
						String cur_comp_role_id = (String)hashmap.get("comp_role_id");	
						
						String role_serv_id = (String)roleMap.get(cur_comp_role_id);
						
	
					
						String rela_offer_id = this.getRelaOffer((String)hashmap.get("element_id"));
						HashMap nbrInfo = this.getUseNbrInfo(lan_id, custId, (String)hashmap.get("element_id"));
						
						if(nbrInfo!=null)
						{
							String cur_acc_nbrs = (String)nbrInfo.get("cur_acc_nbr");
							String cur_acc_nbr_values =  (String)nbrInfo.get("cur_acc_nbr_value");
							hashmap.put("nbr_use", cur_acc_nbrs);
							hashmap.put("nbr_useValues", cur_acc_nbr_values);
						}
						
						
						if(pre_comp_role_id.equals(cur_comp_role_id))
						{
							HashMap hm = (HashMap)aList.get(aList.size()-1);
							String cur_product_id = (String)hashmap.get("element_id");
							String cur_product_name = (String)hashmap.get("product");
							
							String cur_nbr_use = (String)hashmap.get("nbr_use");
							String cur_nbr_useValue = (String)hashmap.get("nbr_useValues");
							String  cur_nbr_uses = "";
							String  cur_nbr_useValues = "";
							if(cur_nbr_use!=null&&!"".equals(cur_nbr_use)){
								 cur_nbr_uses = ((String) hm.get("nbr_use")) + "|" + cur_nbr_use;
							}
							if(cur_nbr_useValue!=null&&!"".equals(cur_nbr_useValue)){
								  cur_nbr_useValues = ((String) hm.get("nbr_useValues")) + "|" + cur_nbr_useValue;
							}
						
							String product_ids = ((String) hm.get("element_id")) + "/" + cur_product_id;
							
				
							String rela_offer_ids = ((String) hm.get("rela_offer_id"))+"/"+rela_offer_id;
							String product_names = ((String) hm.get("product")) + "/" + cur_product_name;
							
							hm.put("nbr_use", cur_nbr_uses);
							hm.put("nbr_useValues", cur_nbr_useValues);
							
							hm.put("element_id", product_ids);
							hm.put("product", product_names);
							
							hm.put("rela_offer_id", rela_offer_ids);
							String serv_id = "";
							serv_id = SeqUtil.getInst().getNext("SERV", "SERV_ID");
							hashmap.put("serv_id", serv_id);
							String rela_offer_inst_id =  SeqUtil.getInst().getNext("PRODUCT_OFFER", "OFFER_ID");
							hashmap.put("rela_offer_inst_id", rela_offer_inst_id);//实例化关联销售品ID
						}
						else
						{
							String serv_id = "";
							serv_id =  SeqUtil.getInst().getNext("SERV", "SERV_ID");
							hashmap.put("serv_id", serv_id);
							String rela_offer_inst_id =  SeqUtil.getInst().getNext("PRODUCT_OFFER", "OFFER_ID");
							hashmap.put("rela_offer_inst_id", rela_offer_inst_id);
							hashmap.put("rela_offer_id", rela_offer_id);
							aList.add(hashmap);
							pre_comp_role_id = cur_comp_role_id;
							
						}
						
					}
	
					
					
			return aList;
		}
	 
	 private HashMap getUseNbrInfo (String lan_id,String custId,String prodId)throws Exception{
		HashMap rsMap =null;
		 
		DynamicDict dtos = new DynamicDict();
		HashMap params = new HashMap();
		params.put("lanId", lan_id);
		params.put("custId", custId);
		params.put("prodId", prodId);
		params.put("prodNo", "");
		dtos.setValueByName("parameter", params);
		List nbrInfo = this.executeQuery9(dtos);
		
		int showNum = 5;//前台最多显示5个号码
		if(nbrInfo!=null&&nbrInfo.size()!=0){
			rsMap = new HashMap();
			for(int i = 0;i<nbrInfo.size();i++){
				if(i>showNum)
					break;
				HashMap nbrMap = (HashMap)nbrInfo.get(i);
				String acc_nbr = (String)nbrMap.get("acc_nbr");
				String product_offer_instance_id = (String)nbrMap.get("product_offer_instance_id");
				String serv_id = (String)nbrMap.get("serv_id");
				
				String curNbr = (String)rsMap.get("cur_acc_nbr");
				String curNbrValue =  (String)rsMap.get("cur_acc_nbr_value");
				String acc_nbrValue = serv_id+";"+product_offer_instance_id+";"+prodId;
				if(curNbr!=null&&!"".equals(curNbr)){
					curNbr = curNbr+"/"+acc_nbr;
				}
				else{
					curNbr = acc_nbr;
				}
				if(curNbrValue!=null&&!"".equals(curNbrValue)){
					curNbrValue = curNbrValue+"/"+acc_nbrValue;
				}
				else{
					curNbrValue = acc_nbrValue;
				}
				rsMap.put("cur_acc_nbr", curNbr);
				rsMap.put("cur_acc_nbr_value", curNbrValue);
			}
		}
	
		 
		 
		 return rsMap;
	 }
	 
	 
	 private String getRelaOffer(String product_id)
	 {
		 String rela_offer_id = ""; 
		 String[] sqlParams = new String[1];//构建SQL工具查询参数
		 sqlParams[0] = product_id;
		 HashMap rsMap = new HashMap();
		 ArrayList offerList = (ArrayList)sqlMapExe.queryForMapList("findReleaOfferSql", sqlParams);
		 if(offerList!=null&&offerList.size()!=0)
		 {
			 rsMap = (HashMap)offerList.get(0);
			 rela_offer_id   = (String)rsMap.get("offer_id");
		 }
		 
		 return rela_offer_id;
	 }
	 
	 public String getInstSeq(DynamicDict dto)throws Exception {
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String tableCode  = (String)m.get("tableCode");
		 String fieldCode  = (String)m.get("fieldCode");
		 String instSeq = "";
		 instSeq = SeqUtil.getInst().getNext(tableCode, fieldCode);
		 return instSeq;
	 }
	 
	 //销售品包外优惠的查询方法
	 public List executeQuery8(DynamicDict dto) throws Exception {
		 List resultLst = new ArrayList();
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String product_id  = (String)m.get("product_id");
		 String rela_offer_id = this.getRelaOffer(product_id);
		 String rela_offer_instance_id = (String)m.get("rela_offer_instance_id");
		 String[] sqlParams = new String[2];//构建SQL工具查询参数
		 sqlParams[0] = rela_offer_instance_id;
		 sqlParams[1] = rela_offer_id;
		 ArrayList alPackageMap = (ArrayList)sqlMapExe.queryForMapList("findOfferOuterPacketSql", sqlParams);
		 /*if(alPackageMap!=null)
		 {
			 for(int i = 0;i<alPackageMap.size();i++)
			 {
				 HashMap map=(HashMap)alPackageMap.get(i);
				 HashMap mapAdd=(HashMap)map.clone();
				 
				 
				 String min_num = (String)map.get("role_min_num");
				 String max_num = (String)map.get("role_max_num");
				 if(min_num!=null&&!"".equals(min_num)&&max_num!=null&&!"".equals(max_num))
				 {
					 if(Integer.parseInt(max_num)>1)
					 {
						 alPackageMap.add(mapAdd);
						 i++;
					 }
				 }
			 }
		 }*/
		 return alPackageMap;
	 }
	 
	 //查询客户下在用号码
	 public List executeQuery9(DynamicDict dto) throws Exception {
		 List resultLst = new ArrayList();
		 Map m = (Map)dto.getValueByName("parameter") ;

		 String lanId  = (String)m.get("lanId");
		 String prodNo = (String)m.get("prodNo");
		 String custId = (String)m.get("custId");
		 String prodId = (String)m.get("prodId");
		 List  sqlParams = new ArrayList();
		// String[] sqlParams = null;//构建SQL工具查询参数
		 String findUsingOfferSql = "select a.acc_nbr, b.product_offer_instance_id,c.offer_name,a.serv_id from serv a, offer_inst b,product_offer c " +
			" where b.product_offer_id = c.offer_id  and a.comp_inst_id = b.comp_inst_id and b.offer_kind = '0' and a.user_cust_id = ? and a.lan_id = ? and a.product_id = ? ";
		 sqlParams.add(custId);
		 sqlParams.add(lanId);
		 sqlParams.add(prodId);
		 
		 if(prodNo!=null&&!"".equals(prodNo))
		 {
			 findUsingOfferSql += " and a.acc_nbr like ? ";
			 sqlParams.add("%"+prodNo+"%");
		 }
		
		 ArrayList alProdMap = (ArrayList)sqlMapExe.queryForMapListBySql(findUsingOfferSql, sqlParams);
		 return alProdMap;
	 }
	 
		public List translateWhereCondMap(Map keyCondMap) throws FrameException{
			if(keyCondMap == null || keyCondMap.isEmpty())
				return null ;
			
			List params = new ArrayList() ;	
			params.add((String)keyCondMap.get("parent_catalog_item_id")) ;
			params.add((String)keyCondMap.get("catalog_id")) ;
			return params  ;
		}
}
