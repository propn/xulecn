/**
 * 
 */
package com.ztesoft.crm.business.common.query;

import java.util.HashMap;
import java.util.Map;


/**
 * @author LP
 * 提供SQL工具使用的SQL语句 
 */
public class Sqls {

	/******************************选购台 销售品信息处理SQL***********************************/
	private static Map sqls= new HashMap();
	//查询销售品目录元素下挂接的套餐销售品 K为销售品包 J为基础销售品
	private static String findOfferSql = " SELECT a.offer_id,a.offer_name,b.catalog_item_id,b.parent_catalog_item_id,a.offer_kind,a.offer_comments FROM product_offer a,PRODUCT_CATALOG_ITEM b,Product_Catalog_Item_Element c " +
			" where b.catalog_item_id = c.catalog_item_id and c.element_type = '10C' and c.element_id = a.offer_id and a.packet_type in ('20A') and offer_kind in ('1') and  b.catalog_item_id = ? and a.state = '00A'";
	
	//查询套餐销售品下挂接的基础包
	private static String findPackageSql = "select c.offer_id package_id,c.offer_name package_name,a.offer_id from product_offer a,product_offer_detail b,product_offer c where a.offer_id = b.offer_id " +
			"and b.element_type ='10C' and b.element_id = c.offer_id and a.offer_id = ? and c.packet_type = ? ";
	
	//查询基础销售包依赖的可选销售包
	private static String findOfferPacketSql = "  select d.* from (  select c.offer_id package_id,c.offer_name package_name,a.offer_id,c.packet_type,c.offer_kind,to_char(b.role_min_num) role_min_num,to_char(b.role_max_num) role_max_num" +
			" from product_offer a,product_offer_relation b,product_offer c where a.offer_id = b.offer_a_id " +
			" and b.offer_z_id = c.offer_id and c.offer_kind = '2' and b.RELATION_TYPE_ID = '10H' and a.offer_id = ?  and c.can_be_buy_alone = '1' " +
			" union " +
			" select a.offer_id package_id,a.offer_name package_name,a.offer_id,a.packet_type,a.offer_kind,'' role_min_num,'' role_max_num from product_offer a " +
			" where offer_id =? ) d order by d.packet_type ";
	
	//查询某类产品实例的包外优惠
	private static String findOfferOuterPacketSql = "select a.offer_id package_id,a.offer_name package_name,a.packet_type, a.offer_kind,b.offer_a_id rela_offer_id" +
			" ,? as rela_offer_instance_id,b.role_min_num,b.role_max_num " +
			" from product_offer a, product_offer_relation b where a.offer_id = b.offer_z_id and a.can_be_buy_alone = '0' " +
			" and b.offer_a_id = ? and b.relation_type_id  = '10H' ";
	
	//查询根据名称模糊查询套餐销售品
	private static String findOfferByNameSql = " SELECT a.offer_id,a.offer_name,a.offer_kind,a.offer_comments FROM product_offer a  " +
	" where a.offer_name like ? and a.packet_type = '20A' and a.offer_kind in ('1') ";
	
	//查询热卖销售品
	private static String hotSaleSql = " select a.offer_id,a.offer_name,a.offer_kind,a.offer_comments from product_offer a,offer_collection b where a.offer_id = b.product_offer_id " +
		" and b.party_role_id = ? ";
	
	//查询销售品属性
	private static String findOfferAttrSql = "  SELECT a.*,c.attr_name,c.attr_code,a.attr_value_type_id as input_method,c.attr_id,? as product_offer_instance_id,? as rela_offer_instance_id FROM product_offer_attr a,product_offer b,attribute c where a.offer_id = b.offer_id " +
			" and a.offer_attr_seq = c.attr_id and c.state = '00A' and a.state = '00A' " +
			" and sysdate>=c.eff_date and sysdate<=c.exp_date" +
			" and a.offer_id = ? ";
	
	//查询产品关联的基础销售品
	private static String findReleaOfferSql = " select a.offer_id,a.offer_name,a.offer_kind from product_offer a ,product_offer_detail b where a.offer_id = b.offer_id " +
			" and a.offer_kind = '0' and b.element_type ='10A' and b.element_id = ?";//offer_kind ==0 为基础销售品
	

	//查询销售品明细数据
	private static String findOfferDetailSql = " select a.offer_detail_id,a.offer_id,a.comp_role_id,element_type " +
			" from product_offer_detail a where  element_type = '10A' and a.offer_id = ?  and a.element_id = ? ";
	
	//查询销售品明细数据
	private static String findOfferDetailSqlByRole = " select a.offer_detail_id,a.offer_id,a.comp_role_id,element_type " +
			" from product_offer_detail a where  element_type = '10A' and a.offer_id = ?  and a.element_id = ? and a.comp_role_id = ?";
	
	
	/***************自动处理类-删除附属产品实例*******************/
	//查询互斥附属产品列表 全部
	private static String findMutexServProds = "select distinct d.* from ( select a.prod_z_id from product_relation a  where instr(?,''''||a.prod_a_id||'''')>0 and a.relation_type_id = ? " +
			" union select a.prod_a_id from product_relation a  where instr(?,''''||a.prod_z_id||'''')>0 and a.relation_type_id = ? ) d";
	
	//查询互斥附属产品列表 与A端互斥
	private static String findMutexServProds_a = "select distinct a.prod_z_id from product_relation a  where instr(?,''''||a.prod_a_id||'''')>0  and a.relation_type_id = ? ";
	
	//查询互斥附属产品列表 与Z端互斥
	private static String findMutexServProds_z = "select distinct a.prod_a_id from product_relation a  where instr(?,''''||a.prod_z_id||'''')>0 and a.relation_type_id = ? ";
	/***************自动处理类-删除互斥销售品*******************/
	//查询互斥销售品列表 与A端互斥
	private static String findMutexOffers_a = "select distinct offer_z_id from product_offer_relation where  instr(?,''''||offer_a_id||'''')>0 and relation_type_id = ? ";
	/******************************销售品订购、变更自动受理器*********************/
	//根据SERV_ID查询该主产品的有效销售品明细数据
	private static String findOfferDetailIds = "select distinct a.* from offer_inst_detail a where a.instance_id = ? and a.instance_type = ? ";
	
	/******************************服务订单相关处理*********************/
	//更新主单的订单ID
	private static String updateCompOrdAsk = "update ord_ask set ask_id = ? where ord_id = ? ";
	
	static{
		/******选购台 销售品信息处理SQL******/
		sqls.put("findOfferSql",findOfferSql);
		sqls.put("findPackageSql",findPackageSql);
		sqls.put("findOfferByNameSql",findOfferByNameSql);
		sqls.put("hotSaleSql",hotSaleSql);
		sqls.put("findOfferPacketSql",findOfferPacketSql);
		sqls.put("findOfferAttrSql",findOfferAttrSql);
		sqls.put("findOfferOuterPacketSql",findOfferOuterPacketSql);
		sqls.put("findReleaOfferSql",findReleaOfferSql);
		sqls.put("findOfferDetailSql",findOfferDetailSql);
		sqls.put("findOfferDetailSqlByRole",findOfferDetailSqlByRole);
		/***************自动处理类-删除附属产品实例*******************/
		sqls.put("findMutexServProds",findMutexServProds);
		sqls.put("findMutexServProds_a",findMutexServProds_a);
		sqls.put("findMutexServProds_z",findMutexServProds_z);
		/***************自动处理类-删除互斥销售品*******************/
		sqls.put("findMutexOffers_a",findMutexOffers_a);
		/******************************销售品订购、变更自动受理器*********************/
		sqls.put("findOfferDetailIds",findOfferDetailIds);
		/******************************服务订单相关处理*********************/
		sqls.put("updateCompOrdAsk",updateCompOrdAsk);
	}
	
	public static String getSql(String name){
		
		return (String) sqls.get(name);
	}

}
