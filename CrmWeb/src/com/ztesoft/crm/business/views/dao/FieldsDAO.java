package com.ztesoft.crm.business.views.dao;

import java.util.List;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.util.JNDINames;

public class FieldsDAO {

	private String dbName =  JNDINames.PM_DATASOURCE;

	private String SELECT_SQL = "select product_id,attr_id,a.field_name,attr_name,attr_code,a.attr_length,"
			+ "	is_null,is_check,is_edit,is_make, check_message,colspan,attr_value_type_id,make_field,a.page_url "
			+ " from  product_attr a, attribute b where a.attr_seq = b.attr_id "
			+ " and a.product_id=? and a.state='00A'";

	public List findBySql(List whereCondParams) throws FrameException {
		DynamicDict dto = Base.query(this.getDbName(), SELECT_SQL,
				whereCondParams, Const.ACTION_RESULT, 1, Const.UN_JUDGE_ERROR,
				"");
		return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT));
	}

	public List findBySql(String sql, List whereCondParams)
			throws FrameException {
		DynamicDict dto = Base.query(this.getDbName(), sql, whereCondParams,
				Const.ACTION_RESULT, 1, Const.UN_JUDGE_ERROR, "");
		return DataTranslate._List(dto.m_Values.get(Const.ACTION_RESULT));
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
