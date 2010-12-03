/**
 * 
 */
package com.ztesoft.vsop.engine.bo;

import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.ErrorCode;
import com.ztesoft.vsop.engine.dao.SPSignInfoSynDao;
import com.ztesoft.vsop.engine.vo.SPSignInfoVO;

/**
 * <pre>
 * Title:SP/CP业务能力同步业务逻辑
 * Description: SP/CP业务能力同步业务逻辑 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class SPSignInfoSynBO {
	
	SPSignInfoSynDao dao = new SPSignInfoSynDao();
	/**
	 * 执行业务逻辑
	 * @param vo
	 * @param in
	 * @return
	 */
	public Map execute(SPSignInfoVO vo, Map in)throws Exception {
		boolean flag = true;
		//如果同步类型为“1” 则为增加
		if ("1".equals(vo.getOPFlag())) {
			List  spSignInfoList = vo.getSPSignInfo();
			dao.addSPSignInfo(vo.getSPSignInfo());
			flag = false;
		}
		
		//如果同步类型为“2” 则为修改
		if ("2".equals(vo.getOPFlag())) {
			dao.updateSPSignInfo(vo.getSPSignInfo());
			flag = false;
		}
		
		//如果同步类型为“3” 则为删除
		if ("3".equals(vo.getOPFlag())) {
			dao.deteteSPSignInfo(vo.getSPSignInfo());
			flag = false;
		}
		//假如上面三种操作都没走到 说明上送数据异常
		if(flag){
			in.put("resultCode", "1002");
			in.put("resultDesc", ErrorCode.DATA_ERROR);
		}
		return in;

	}
}
