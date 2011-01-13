package test;


import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;

public class MyAction2  implements IAction {
	
	public String testService(DynamicDict dto) throws Exception {
		return "TEST--";
	}
	
	
	/**
	 * 采用if...else方式选择执行方法
	 */
	public int perform(DynamicDict dto) throws FrameException {
		if(dto.getValueByName(Const.ACTION_METHOD) == null)
			return -1 ;
		
		String methodName = (String) dto.getValueByName(Const.ACTION_METHOD);
		
		if("".equals(methodName.trim()))
			return -1 ;
		
		try {
			Object result = null ;
			if("testService".equalsIgnoreCase(methodName)){
				result = testService( dto);
			}
			dto.setValueByName(Const.ACTION_RESULT, result);
			return 0 ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1 ;
	}
}
