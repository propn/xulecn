package test;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;

public class XAAction  extends DictAction{
	public boolean doAction(DynamicDict dto) throws Exception{
		String t = (String)dto.getValueByName(Const.ACTION_PARAMETER) ;
		System.out.println("parameter========"+t) ;
		XADAO dao = new XADAO() ;
		boolean b1 =  dao.iappInsert() ;
		boolean b2 =  dao.easonwuInsert() ;
		
		System.out.println("R1========"+b1 +".............R2==========="+b2) ;
		return b1 && b2 ;
	}
}
