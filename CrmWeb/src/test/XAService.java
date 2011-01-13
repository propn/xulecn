package test;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictService;

public class XAService extends DictService{
	public boolean doAction() throws Exception{
		
		DynamicDict dto = this.getServiceDTO("XAAction", "doAction") ;
		dto.setValueByName(Const.ACTION_PARAMETER, "my xa test") ;
		ActionDispatch.dispatch(dto) ;
		
		return ((Boolean)dto.getValueByName(Const.ACTION_RESULT)).booleanValue() ;
	}
	
	public static void main(String[] args ){
		XAService s = new XAService() ;
		try {
			System.out.println(s.doAction() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
