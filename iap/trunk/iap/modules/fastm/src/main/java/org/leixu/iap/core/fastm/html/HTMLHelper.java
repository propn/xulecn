package org.leixu.iap.core.fastm.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.leixu.iap.core.util.bean.BeanUtils;

public class HTMLHelper {
	/**
	 * 
	 * @param value
	 * @param optionInfo
	 * @return
	 */
	public static List buildOptions(Object value, IOptionInfo optionInfo){
		Object[] values = optionInfo.getValues();
		if(values == null) return null;

		Object[] displays = optionInfo.getDisplays();

		int n = values.length;
		String valueName = optionInfo.getValueName();
		String displayName = optionInfo.getDisplayName();
		List list = new ArrayList(n);

		for(int i = optionInfo.getStartIndex(); i < n; i++){
			Object option = values[i];
   			if(option == null)
   				continue;
			Object display = displays[i];

			Map map = new HashMap();
			map.put(valueName, option);
			map.put(displayName, display);
			String selected = option.equals(value) ? "selected" : "";
			map.put("selected", selected);
			list.add(map);
		}

		return list;
	}

	/**
	 * 
	 * @param value
	 * @param data
	 * @return
	 */
	public static Map buildRadios(Object value, Object[] data){
		Map map = buildEmptyRadios(data);
		if(value != null) map.put(value, "checked");
		return map;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Map buildEmptyRadios(Object[] data){
		Map map = new HashMap();
		for(int i = 0; i < data.length; i++){
			map.put(data[i], "");
		}
		return map;
	}

	/**
	 * 
	 * @param data
	 *   {
	 *       {"propertyName", OptionInfo type, or Object[] type}
	 *   }
	 * @return
	 */
	public static Map mapOptions(Object bean, IOptionEntry[] data){
		Map map = new HashMap();
		for(int i = 0; i < data.length; i++){
			IOptionEntry entry = data[i];
			String propertyName = entry.getPropertyName();
			IOptionInfo optionInfo = entry.getOptionInfo(); 

			Object value = BeanUtils.getProperty(bean, propertyName);
			List options = buildOptions(value, optionInfo);

			map.put(propertyName, options);
		}
		return map;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Map mapRadios(Object bean, IRadioEntry[] data){
		Map map = new HashMap();
		for(int i = 0; i < data.length; i++){
			IRadioEntry entry = data[i];
			String propertyName = entry.getPropertyName();
			Object[] values = entry.getValues();

			Object value = BeanUtils.getProperty(bean, propertyName);
			Map radios = buildRadios(value, values);

			map.put(propertyName, radios);
		}
		return map;
	}

	/**
	 * 
	 * @param bean
	 * @param optionsData
	 * @param radiosData
	 * @return
	 */
	public static Map mapOptionsRadios(Object bean, 
			IOptionEntry[] optionsData, IRadioEntry[] radiosData){

		Map optionsMap = mapOptions(bean, optionsData);
		Map radiosMap = mapRadios(bean, radiosData);
		
		Map map = new HashMap();
		map.put("option", optionsMap);
		map.put("radio", radiosMap);
		return map;
	}
}
