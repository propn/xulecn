package org.leixu.iap.core.fastm.sql;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.leixu.iap.core.fastm.DefaultInterceptor;
import org.leixu.iap.core.fastm.DynamicPart;
import org.leixu.iap.core.fastm.HelperParser;
import org.leixu.iap.core.fastm.ITemplate;
import org.leixu.iap.core.fastm.IValueInterceptor;
import org.leixu.iap.core.fastm.VariablePart;
import org.leixu.iap.core.util.bean.BeanUtils;

public class SqlHolderImpl implements SqlHolder{
	private static final String PARAM_TAG = "param:";
	private static final String TEXT_TAG = "text:";

	private IValueInterceptor interceptor = DefaultInterceptor.instance;
	
	// dynamic
	private ITemplate dynamicTemplate;

	// fixed template with param
	private ITemplate fixedTemplate;
	private Map fixedParamMap;
	private int fixedParamCount;

	// static sql
	private String fixedSql;

	/**
	 * 
	 * @param template
	 */
	public SqlHolderImpl(DynamicPart template){
		if(!template.getDynamicChildren().isEmpty()){
			this.dynamicTemplate = template;
			return;
		}

		List variables = template.getVariables();
		Map paramMap = processTemplateVariables(variables);

		Object textCount = null;
		if(paramMap != null)
			textCount = paramMap.remove("textCount");
		Map paramPlaces = makeParamPlaces(paramMap);
		String sql = template.toString(paramPlaces, interceptor);

		if(textCount == null){
			// no text place holder
			// just use the static unique sql
			this.fixedSql = sql;
		}else{
			// has text place holder
			// need to generate dynamic sql with template
			// we need to parse the sql again
			this.fixedTemplate = HelperParser.parse(sql);
			this.fixedParamMap = paramMap;
			
			Integer paramCount = (Integer)paramMap.remove("paramCount");
			this.fixedParamCount = paramCount.intValue();
		}
	}

	public SqlParam getSqlParam(Object data){
		SqlParamImpl sqlParam = new SqlParamImpl(); 
		if(fixedSql != null) {
			sqlParam.setSql(fixedSql);
		}else if(fixedTemplate != null){
			String sql = fixedTemplate.toString(data, interceptor);
			sqlParam.setSql(sql);
		}else if(dynamicTemplate != null){
			String str = dynamicTemplate.toString(data, interceptor);
			// now the str contains no dynamic parts 
			DynamicPart template = HelperParser.parse(str);

			SqlHolderImpl sqlHolderImpl = new SqlHolderImpl(template);
			return sqlHolderImpl.getSqlParam(data);
		}

		sqlParam.setParam(this.getParam(data));
		return sqlParam;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private Object getParam(Object data) {
		if(fixedParamCount == 0 || fixedParamMap == null) return data;

		Object[] parameters = new Object[fixedParamCount];

		for(Iterator iterator = fixedParamMap.entrySet().iterator(); iterator.hasNext();){
			Map.Entry entry = (Map.Entry)iterator.next();
			String name = entry.getKey().toString();
			int[] indice = (int[])entry.getValue();
			if(indice == null) continue;

			Object value = BeanUtils.getProperty(data, name);
			for(int i = 0; i < indice.length; i++){
				int index = indice[i];
				parameters[index] = value;
			}
		}
		return parameters;
	}

	/**
	 * 
	 * @param variables
	 * @return
	 */
	private Map processTemplateVariables(List variables){
		if(variables == null) 
			return null;
		int n = variables.size();
		if(n == 0) 
			return null;

		Map paramMap = new HashMap(); 
		int paramCount = 0;
		int textCount = 0;
		for(int i = 0; i < n; i++){
			VariablePart var = (VariablePart)variables.get(i);
			String name = var.getName();

			if(name.startsWith(PARAM_TAG)){
				String key = name.substring(PARAM_TAG.length()).trim();
				var.setName(key);
				put(paramMap, key, paramCount);
				paramCount++;
				
				continue;
			}
			
			if(name.startsWith(TEXT_TAG)){
				String key = name.substring(TEXT_TAG.length()).trim();
				var.setName(key);
				textCount++;

				continue;
			}
		}

		paramMap.put("paramCount", new Integer(paramCount));
		if(textCount != 0)
			paramMap.put("textCount", new Integer(textCount));
		return paramMap;
	}

	/**
	 * 
	 * @param map
	 * @param key
	 * @param i
	 */
	private void put(Map map, Object key, int index){
		int[] indice = (int[])map.get(key);
		if(indice == null) {
			indice = new int[]{index};
		}else{
			int n = indice.length;
			int[] newIndice = new int[n+1];
			System.arraycopy(indice, 0, newIndice, 0, n);
			newIndice[n] = index;
			indice = newIndice;
		}
		map.put(key, indice);
	}

	/**
	 * 
	 * @param varMap
	 * @return
	 */
	private Map makeParamPlaces(Map varMap){
		if(varMap == null || varMap.isEmpty()) 
			return Collections.EMPTY_MAP;

		Map valueMap = new HashMap();
		for(Iterator iterator = varMap.keySet().iterator(); iterator.hasNext();){
			Object key = iterator.next();
			valueMap.put(key, "?");
		}
		return valueMap;
	}

	public void setInterceptor(IValueInterceptor interceptor) {
		this.interceptor = interceptor;
	}
}
