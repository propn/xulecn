package com.ztesoft.common.dict;

import java.lang.reflect.Method;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.IAction;

/**
 * 
 * @author easonwu 2009-11-25
 * buffalo  service���輯�ɸĳ�����
 * 
 *
 */
public abstract class DictAction implements IAction {

	public int perform(DynamicDict dto) throws Exception {
		String methodName = (String) dto.getValueByName(Const.ACTION_METHOD);
		Class[] args = new Class[1];
		args[0] = dto.getClass();

		Object[] methodParamValue = new Object[1];
		methodParamValue[0] = dto;
//		try {
			Method _method = this.getClass().getMethod(methodName, args);
			
			Object result = _method.invoke(this, methodParamValue);
			dto.setValueByName(Const.ACTION_RESULT, result);
//		} catch (SecurityException e) {
//			throw new FrameException(e.getMessage());
//		} catch (NoSuchMethodException e) {
//			throw new FrameException(e.getMessage());
//		} catch (IllegalArgumentException e) {
//			throw new FrameException(e.getMessage());
//		} catch (IllegalAccessException e) {
//			throw new FrameException(e.getMessage());
//		} catch (InvocationTargetException e) {
//			e.printStackTrace() ;
//			throw new FrameException(e.getMessage());
//		}

		return 0;
	}
}
