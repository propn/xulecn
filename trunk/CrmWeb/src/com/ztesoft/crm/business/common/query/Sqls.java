/**
 * 
 */
package com.ztesoft.crm.business.common.query;

import java.util.HashMap;
import java.util.Map;


/**
 * @author LP
 * �ṩSQL����ʹ�õ�SQL��� 
 */
public class Sqls {

	/******************************ѡ��̨ ����Ʒ��Ϣ����SQL***********************************/
	private static Map sqls= new HashMap();
	//��ѯ����ƷĿ¼Ԫ���¹ҽӵ��ײ�����Ʒ KΪ����Ʒ�� JΪ��������Ʒ
	private static String findOfferSql = " SELECT a.offer_id,a.offer_name,b.catalog_item_id,b.parent_catalog_item_id,a.offer_kind,a.offer_comments FROM product_offer a,PRODUCT_CATALOG_ITEM b,Product_Catalog_Item_Element c " +
			" where b.catalog_item_id = c.catalog_item_id and c.element_type = '10C' and c.element_id = a.offer_id and a.packet_type in ('20A') and offer_kind in ('1') and  b.catalog_item_id = ? and a.state = '00A'";
	
	//��ѯ�ײ�����Ʒ�¹ҽӵĻ�����
	private static String findPackageSql = "select c.offer_id package_id,c.offer_name package_name,a.offer_id from product_offer a,product_offer_detail b,product_offer c where a.offer_id = b.offer_id " +
			"and b.element_type ='10C' and b.element_id = c.offer_id and a.offer_id = ? and c.packet_type = ? ";
	
	//��ѯ�������۰������Ŀ�ѡ���۰�
	private static String findOfferPacketSql = "  select d.* from (  select c.offer_id package_id,c.offer_name package_name,a.offer_id,c.packet_type,c.offer_kind,to_char(b.role_min_num) role_min_num,to_char(b.role_max_num) role_max_num" +
			" from product_offer a,product_offer_relation b,product_offer c where a.offer_id = b.offer_a_id " +
			" and b.offer_z_id = c.offer_id and c.offer_kind = '2' and b.RELATION_TYPE_ID = '10H' and a.offer_id = ?  and c.can_be_buy_alone = '1' " +
			" union " +
			" select a.offer_id package_id,a.offer_name package_name,a.offer_id,a.packet_type,a.offer_kind,'' role_min_num,'' role_max_num from product_offer a " +
			" where offer_id =? ) d order by d.packet_type ";
	
	//��ѯĳ���Ʒʵ���İ����Ż�
	private static String findOfferOuterPacketSql = "select a.offer_id package_id,a.offer_name package_name,a.packet_type, a.offer_kind,b.offer_a_id rela_offer_id" +
			" ,? as rela_offer_instance_id,b.role_min_num,b.role_max_num " +
			" from product_offer a, product_offer_relation b where a.offer_id = b.offer_z_id and a.can_be_buy_alone = '0' " +
			" and b.offer_a_id = ? and b.relation_type_id  = '10H' ";
	
	//��ѯ��������ģ����ѯ�ײ�����Ʒ
	private static String findOfferByNameSql = " SELECT a.offer_id,a.offer_name,a.offer_kind,a.offer_comments FROM product_offer a  " +
	" where a.offer_name like ? and a.packet_type = '20A' and a.offer_kind in ('1') ";
	
	//��ѯ��������Ʒ
	private static String hotSaleSql = " select a.offer_id,a.offer_name,a.offer_kind,a.offer_comments from product_offer a,offer_collection b where a.offer_id = b.product_offer_id " +
		" and b.party_role_id = ? ";
	
	//��ѯ����Ʒ����
	private static String findOfferAttrSql = "  SELECT a.*,c.attr_name,c.attr_code,a.attr_value_type_id as input_method,c.attr_id,? as product_offer_instance_id,? as rela_offer_instance_id FROM product_offer_attr a,product_offer b,attribute c where a.offer_id = b.offer_id " +
			" and a.offer_attr_seq = c.attr_id and c.state = '00A' and a.state = '00A' " +
			" and sysdate>=c.eff_date and sysdate<=c.exp_date" +
			" and a.offer_id = ? ";
	
	//��ѯ��Ʒ�����Ļ�������Ʒ
	private static String findReleaOfferSql = " select a.offer_id,a.offer_name,a.offer_kind from product_offer a ,product_offer_detail b where a.offer_id = b.offer_id " +
			" and a.offer_kind = '0' and b.element_type ='10A' and b.element_id = ?";//offer_kind ==0 Ϊ��������Ʒ
	

	//��ѯ����Ʒ��ϸ����
	private static String findOfferDetailSql = " select a.offer_detail_id,a.offer_id,a.comp_role_id,element_type " +
			" from product_offer_detail a where  element_type = '10A' and a.offer_id = ?  and a.element_id = ? ";
	
	//��ѯ����Ʒ��ϸ����
	private static String findOfferDetailSqlByRole = " select a.offer_detail_id,a.offer_id,a.comp_role_id,element_type " +
			" from product_offer_detail a where  element_type = '10A' and a.offer_id = ?  and a.element_id = ? and a.comp_role_id = ?";
	
	
	/***************�Զ�������-ɾ��������Ʒʵ��*******************/
	//��ѯ���⸽����Ʒ�б� ȫ��
	private static String findMutexServProds = "select distinct d.* from ( select a.prod_z_id from product_relation a  where instr(?,''''||a.prod_a_id||'''')>0 and a.relation_type_id = ? " +
			" union select a.prod_a_id from product_relation a  where instr(?,''''||a.prod_z_id||'''')>0 and a.relation_type_id = ? ) d";
	
	//��ѯ���⸽����Ʒ�б� ��A�˻���
	private static String findMutexServProds_a = "select distinct a.prod_z_id from product_relation a  where instr(?,''''||a.prod_a_id||'''')>0  and a.relation_type_id = ? ";
	
	//��ѯ���⸽����Ʒ�б� ��Z�˻���
	private static String findMutexServProds_z = "select distinct a.prod_a_id from product_relation a  where instr(?,''''||a.prod_z_id||'''')>0 and a.relation_type_id = ? ";
	/***************�Զ�������-ɾ����������Ʒ*******************/
	//��ѯ��������Ʒ�б� ��A�˻���
	private static String findMutexOffers_a = "select distinct offer_z_id from product_offer_relation where  instr(?,''''||offer_a_id||'''')>0 and relation_type_id = ? ";
	/******************************����Ʒ����������Զ�������*********************/
	//����SERV_ID��ѯ������Ʒ����Ч����Ʒ��ϸ����
	private static String findOfferDetailIds = "select distinct a.* from offer_inst_detail a where a.instance_id = ? and a.instance_type = ? ";
	
	/******************************���񶩵���ش���*********************/
	//���������Ķ���ID
	private static String updateCompOrdAsk = "update ord_ask set ask_id = ? where ord_id = ? ";
	
	static{
		/******ѡ��̨ ����Ʒ��Ϣ����SQL******/
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
		/***************�Զ�������-ɾ��������Ʒʵ��*******************/
		sqls.put("findMutexServProds",findMutexServProds);
		sqls.put("findMutexServProds_a",findMutexServProds_a);
		sqls.put("findMutexServProds_z",findMutexServProds_z);
		/***************�Զ�������-ɾ����������Ʒ*******************/
		sqls.put("findMutexOffers_a",findMutexOffers_a);
		/******************************����Ʒ����������Զ�������*********************/
		sqls.put("findOfferDetailIds",findOfferDetailIds);
		/******************************���񶩵���ش���*********************/
		sqls.put("updateCompOrdAsk",updateCompOrdAsk);
	}
	
	public static String getSql(String name){
		
		return (String) sqls.get(name);
	}

}
