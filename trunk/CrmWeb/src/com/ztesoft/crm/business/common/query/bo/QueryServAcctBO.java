package com.ztesoft.crm.business.common.query.bo;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.business.common.query.dao.QueryServAcctDAO;

/**
 * 用于根据各种条件，查询实例的帐务定制关系
 * @author lindy
 *
 */
public class QueryServAcctBO {

	public QueryServAcctBO(){
		
	}
	
	/**
	 * 根据帐号ID查询帐务定制关系
	 * @param acctId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws FrameException 
	 */
	public PageModel queryServAcctByAcctId(String acctId,Integer pageIndex,Integer pageSize) throws FrameException{
		QueryServAcctDAO queryDao = new QueryServAcctDAO();
		return queryServAcctByAcctAndState(acctId, "00A",pageIndex, pageSize);
	}
	
	/**
	 * 根据帐号ID和实例状态查询帐务定制关系
	 * @param acctId
	 * @param state
	 * @param pageIndex
	 * @param pageSize
	 * @return  
	 * @throws FrameException
	 */
	public PageModel queryServAcctByAcctAndState(String acctId,String state,
				Integer pageIndex,Integer pageSize) throws FrameException{
		QueryServAcctDAO queryDao = new QueryServAcctDAO();
		return queryDao.queryServAcctByAcctIdAndState(acctId,state,pageIndex, pageSize);
	}
	
	/**
	 * 根据帐户ID，查帐户是否与实例有关系
	 * @param acctId
	 * @return  0：该帐户不存在在用的支付关系，且不存在受理中订单的支付关系。1：该帐户存在在用的支付关系。2：该帐户存在受理中订单的支付关系。
	 * 			3:该帐户即存在在用的支付关系，又存在受理中订单的支付关系。
	 * @throws FrameException
	 */
	public String checkServServAcctRelate(String acctId)throws FrameException{
		String userStr = checkAcctRelateByState(acctId, "00A");
		String acceptStr = checkAcctRelateByState(acctId, "00N");
		if( "0".equals(userStr) && "0".equals(acceptStr)){
			return "0";
		}else if(!"0".equals(userStr) && !"0".equals(acceptStr)){
			return "3";
		}else if(!"0".equals(userStr)){
			return "1";
		}else if(!"0".equals(acceptStr)){
			return "2";
		}
		
		return "0";
		
	}
	
	/**
	 * 根据实例状态和帐户ID，查帐户是否与实例有关系
	 * @param acctId
	 * @param state
	 * @return
	 * @throws FrameException
	 */
	public String checkAcctRelateByState(String acctId,String state)throws FrameException{
		QueryServAcctDAO queryDao = new QueryServAcctDAO();
		return queryDao.queryServAcctState(acctId, state);
	}
}
