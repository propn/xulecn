package com.ztesoft.crm.business.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.query.SqlMapExe;

public class ProductOffer extends Attributes{

	private static SqlMapExe  sqlmapexe = SqlMapExe.getInstance();//SQL查询工具
	private String offer_id = null;
	private String offer_name = null;
	private String offer_comments = null;
	private String can_be_buy_alone = null;
	private String offer_kind = null;
	private String packet_type = null;
	public List details = new ArrayList();

	
	public ProductOffer(String offer_id){
		this.offer_id=offer_id;
		this.load();
	}
	public void load(){
		 //1.查询销售品规格信息
		getofferinfo();		
				 
		//2.加载属性信息
		this.attrRestrict = getofferattrinfo();
	}
	
	
	//查询销售品信息
	private void getofferinfo() {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select t.offer_id, t.offer_name, t.offer_comments, t.can_be_buy_alone, ");
		sql.append(" t.offer_kind, t.packet_type, t.*  from product_offer t where offer_id = ? ");
		sql.append("  and state = '00A' and sysdate between t.eff_date and t.exp_date ");
		
		List list = sqlmapexe.queryForMapListBySql(sql.toString(), new String[]{this.offer_id});
		
		if (null == list || list.isEmpty()) {
			return;
		}
		
		//理论上只有一个,如果有多个，也只出第一个
		Map map = (Map)list.get(0);  
		
		this.offer_name = (String)Const.getStrValue(map, "offer_name");
		this.offer_comments = (String)Const.getStrValue(map, "offer_comments");
		this.can_be_buy_alone = (String)Const.getStrValue(map, "can_be_buy_alone");
		this.offer_kind = (String)Const.getStrValue(map, "offer_kind");
		this.packet_type = (String)Const.getStrValue(map, "packet_type");
	}
	
	//加载属性信息
	private List getofferattrinfo() {
		StringBuffer sql = new StringBuffer();
		List attrRestricts = new ArrayList();

		//查询销售品属性
		sql.append(" select a.offer_id, b.attr_id, a.field_name, b.attr_name, b.attr_code, a.make_field, ");
		sql.append(" a.attr_length, a.default_value, b.min_value, b.max_value, a.is_null, a.attr_value_type_id, ");
		sql.append(" a.is_check,  a.is_edit,  a.is_make,  a.check_message, a.colspan ");
		sql.append("  from  product_offer_attr a, attribute b ");		
		sql.append("  where a.offer_attr_seq = b.attr_id  and a.state='00A'");
		sql.append(" and b.state = '00A' and a.offer_id= ? order by b.attr_id ");
		
		List ret = sqlmapexe.getLowercaseKeyMapList(sql.toString(), new String[]{this.offer_id});
		
		if (null == ret || ret.isEmpty()) {
			return attrRestricts;
		}
		
		for (int i = 0; i < ret.size(); i++) {
			Map map = (Map)ret.get(i);
			
			AttrRestrict attrrestrict = new AttrRestrict();
			
			attrrestrict.loadFromMap(map);
			attrrestrict.setTable_name(Keys.TABLE_OFFER_INST_ATTR);  //写死表名
			attrRestricts.add(attrrestrict);
			
			attrrestrict.setValue_name_map(loadattrvaluemap(attrrestrict.getAttr_id()));
		}
		
		return attrRestricts;
	}
	
    //加载属性值信息
	private Map loadattrvaluemap(String sattrid) {
		StringBuffer sql = new StringBuffer();
		Map map = new HashMap();

		//查询产品属性
		sql.append(" select t.attr_id, t.attr_value_id, t.attr_value_cd, t.attr_value ");
		sql.append(" from attribute_value t ");
		sql.append(" where  t.state = '00A' and attr_id = ? order by t.attr_value_id");
		
		List attrvalues = sqlmapexe.getLowercaseKeyMapList(sql.toString(), new String[]{sattrid});
		
		if (null == attrvalues) {
			return map;
		}
		
		for (int i = 0; i < attrvalues.size(); i++) {
			Map retmap = (Map)attrvalues.get(i);
			
			String key = (String)retmap.get("attr_value_id");
			String value = (String)retmap.get("attr_value");
			
			map.put(key, value);
		}
		
		return map;
	}
	

	
	public String getCan_be_buy_alone() {
		return can_be_buy_alone;
	}
	public void setCan_be_buy_alone(String can_be_buy_alone) {
		this.can_be_buy_alone = can_be_buy_alone;
	}
	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	public String getOffer_comments() {
		return offer_comments;
	}
	public void setOffer_comments(String offer_comments) {
		this.offer_comments = offer_comments;
	}
	public String getOffer_id() {
		return offer_id;
	}
	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}
	public String getOffer_kind() {
		return offer_kind;
	}
	public void setOffer_kind(String offer_kind) {
		this.offer_kind = offer_kind;
	}
	public String getOffer_name() {
		return offer_name;
	}
	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}
	public String getPacket_type() {
		return packet_type;
	}
	public void setPacket_type(String packet_type) {
		this.packet_type = packet_type;
	}
	
}



