package com.ztesoft.crm.business.common.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.query.SqlMapExe;

public class Product extends Attributes{

	private static SqlMapExe  sqlmapexe = SqlMapExe.getInstance();//SQL查询工具
	private String product_id = null;//产品标识
	private String product_name = null;//产品名称
	private String offer_id = null;//基础销售品规格标识
	private String product_family_id = null; //产品家族标识

	public Product(String product_id){
		this.product_id=product_id;
		this.load();
	}
	public void load(){
		 //1.查询基础销售品规格标识
		getofferid();
		
		//查询产品名称
		getproductname();
				 
		//2.加载属性信息
		this.attrRestrict = getattrinfo();
	}
	

	//查询基础销售品规格标识
	private String getofferid() {
		//查询条件
		 String[] sqlParams = new String[]{this.product_id};//构建SQL工具查询参数
		 
		 ArrayList productinfo = (ArrayList)sqlmapexe.queryForMapList("findReleaOfferSql", sqlParams);
		 
		 //该产品与配置基础销售品没有对应关系
		 if (0 == productinfo.size()) {
			 System.out.println("该产品[" + this.product_id + "]与配置基础销售品没有对应关系");
			 return null;
		 } else if (1 < productinfo.size()) {
			 System.out.println("该产品[" + this.product_id + "]与多个基础销售品有对应关系，取任意一个");			 
		 } else {
			 //do nothing
		 }
		 
		 //正常情况下只有一条记录
		 Map map = (Map)productinfo.get(0);
		 
		 return this.offer_id = (String)map.get(Keys.OFFER_ID);
	}
	
	//查询产品名称
	private void getproductname() {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select  t.product_name, t.product_family_id from product t where state = '00A' ");
		sql.append("  and sysdate between eff_date and exp_date and product_id = ? ");
		
		 List retList = sqlmapexe.getLowercaseKeyMapList(sql.toString(), new String[]{this.product_id});
		 
		 //有结果
		 if (null != retList && !retList.isEmpty()) {
			 Map retMap = (Map)retList.get(0);
			 
			 this.product_name = Const.getStrValue(retMap, Keys.PRODUCT_NAME);
			 this.product_family_id = Const.getStrValue(retMap, Keys.PRODUCT_FAMILY_ID);
		 }
		 
	}
	
	//加载属性信息
	private List getattrinfo() {
		StringBuffer sql = new StringBuffer();
		List attrRestricts = new ArrayList();

		//查询产品属性
		sql.append(" select a.product_id, b.attr_id, a.field_name, b.attr_name, b.attr_code, a.make_field,  ");
		sql.append(" a.attr_length, a.default_value, b.min_value, b.max_value, a.is_null, a.attr_value_type_id, ");
		sql.append(" a.is_check,  a.is_edit,  a.is_make,  a.check_message, a.table_name, a.colspan ");
		sql.append("  from  product_attr a, attribute b  ");		
		sql.append("  where a.attr_seq = b.attr_id  and a.state='00A'");
		sql.append(" and b.state = '00A' and a.product_id= ? order by b.attr_id ");
		
		List ret = sqlmapexe.getLowercaseKeyMapList(sql.toString(), new String[]{this.product_id});
		
		if (null == ret) {
			return attrRestricts;
		}
		
		for (int i = 0; i < ret.size(); i++) {
			Map map = (Map)ret.get(i);
			
			AttrRestrict attrrestrict = new AttrRestrict();
			
			attrrestrict.loadFromMap(map);
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
	 //	深度拷贝Product对象实例的数据,必须注意的是：放置在Product中的对象数据都必须是序列化的Serializable
	public Object clone() {
		Product cloneObj = null;
		ObjectOutputStream oo = null;
		ObjectInputStream oi = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(out);
			oo.writeObject(this);
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			oi = new ObjectInputStream(in);
			cloneObj = (Product) oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(oo!=null){
				try {
					oo.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(oi!=null){
				try {
					oi.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
		}
		return cloneObj;
	}
	
	public String getOffer_id() {
		return offer_id;
	}
	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_family_id() {
		return product_family_id;
	}
	public void setProduct_family_id(String product_family_id) {
		this.product_family_id = product_family_id;
	}

	
	
	
}
