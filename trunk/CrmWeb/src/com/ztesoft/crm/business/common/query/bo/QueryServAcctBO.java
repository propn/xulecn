package com.ztesoft.crm.business.common.query.bo;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.business.common.query.dao.QueryServAcctDAO;

/**
 * ���ڸ��ݸ�����������ѯʵ���������ƹ�ϵ
 * @author lindy
 *
 */
public class QueryServAcctBO {

	public QueryServAcctBO(){
		
	}
	
	/**
	 * �����ʺ�ID��ѯ�����ƹ�ϵ
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
	 * �����ʺ�ID��ʵ��״̬��ѯ�����ƹ�ϵ
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
	 * �����ʻ�ID�����ʻ��Ƿ���ʵ���й�ϵ
	 * @param acctId
	 * @return  0�����ʻ����������õ�֧����ϵ���Ҳ����������ж�����֧����ϵ��1�����ʻ��������õ�֧����ϵ��2�����ʻ����������ж�����֧����ϵ��
	 * 			3:���ʻ����������õ�֧����ϵ���ִ��������ж�����֧����ϵ��
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
	 * ����ʵ��״̬���ʻ�ID�����ʻ��Ƿ���ʵ���й�ϵ
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
