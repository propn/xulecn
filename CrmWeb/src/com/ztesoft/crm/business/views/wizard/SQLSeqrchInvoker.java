/**
 * 
 */
package com.ztesoft.crm.business.views.wizard;

import java.util.Iterator;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.JNDINames;

/**
 * SQL�ű����������﷨�ṹ����: select flag,hintMessage from ... �������õ��﷨��${<������>}������:
 * 
 * select 1,'��Ʒ������!' from dual where not exists (select * from product_offer
 * where offer_id=${offerId} )
 * 
 * ��sql�����޼�¼��Ĭ��ΪУ��ɹ�
 * 
 */
public class SQLSeqrchInvoker implements SearchInvoker {
	/**
	 * ����������
	 */
	private String findBySql(String searchCondition, Map argMap) throws Exception {

		String sql = replaceSqlArguments(searchCondition, argMap);

		String result = Base.query_string(JNDINames.CRM_DATASOURCE, sql,
				Const.UN_JUDGE_ERROR);

		return result;
	}

	private String replaceSqlArguments(String searchCondition, Map argMap) {
		String sql = searchCondition;
		for (Iterator keys = argMap.keySet().iterator(); keys.hasNext();) {
			String key = (String) keys.next();
			String value = argMap.get(key).toString();
			// sql=sql.replaceAll("\n"," ");
			// �����ִ�Сд�滻SQL
			sql = sql.replaceAll("\\$\\{(?i)" + key.toLowerCase() + "\\}",
					value);
		}
		return sql;
	}

	public boolean invoke(String searchCondition, Map arguments) throws Exception {
		
		String result=findBySql(searchCondition,arguments);
		
		return (result!=null&&!"".equals(result));
	}
}
