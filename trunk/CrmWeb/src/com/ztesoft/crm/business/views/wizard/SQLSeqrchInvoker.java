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
 * SQL脚本调用器，语法结构如下: select flag,hintMessage from ... 参数引用的语法：${<参数名>}，比如:
 * 
 * select 1,'商品不存在!' from dual where not exists (select * from product_offer
 * where offer_id=${offerId} )
 * 
 * 当sql返回无记录，默认为校验成功
 * 
 */
public class SQLSeqrchInvoker implements SearchInvoker {
	/**
	 * 拦截器定义
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
			// 不区分大小写替换SQL
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
