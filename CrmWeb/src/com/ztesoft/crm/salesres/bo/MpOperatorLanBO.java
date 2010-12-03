package com.ztesoft.crm.salesres.bo;

import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.dao.MpOperatorLanDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;

public class MpOperatorLanBO {
	public PageModel qryOperLan(String operCode, String operName,int pi, int ps){
		PageModel pm = new PageModel();
		String sql = " a.oper_id = b.oper_id and b.depart_id = c.depart_id ";
		if(operCode!=null && !"".equals(operCode)){
			sql += " and b.oper_code = '"+operCode+"'";
		}
		if(operName!=null && !"".equals(operName)){
			sql += " and b.oper_name like '"+operName+"%'";
		}
		MpOperatorLanDAO dao = SrNSDAOFactory.getInstance().getMpOperatorLanDAO();
	   	pm = PageHelper.popupPageModel(dao,sql,pi,ps);
	   	
	   	return pm;
	}
	public String saveOperLan(String operIds){
		String flag = "1";
		String insertsql = "insert into rc_operator_lan(oper_id,lan_id,state)values (?,?,?)";
		SqlComDAO com = SrDAOFactory.getInstance().getSqlComDAO();
		if(operIds == null || "".equals(operIds))
			return "0";
		String []ids = operIds.split(",");
		String []params = new String[3];
		int count = 0;
		for(int i =0;i<ids.length;i++){
			if(isExist(ids[i])){
				count ++;
				continue;
			}
			params[0]=ids[i];
			params[1]="-1";
			params[2]="00A";			
			com.excute(insertsql, params);
		}
		if(ids.length==count){//全部已存在
			return "3";
		}
		if(count>0){//部分已存在
			flag = "2";
		}
		if(count==0){//全部都不是已存在
			flag = "1";
		}
		return flag;
	}
	
	public boolean isExist(String operId){
		
		String exisql = "select * from  rc_operator_lan where oper_id = ?";
		SqlComDAO com = SrDAOFactory.getInstance().getSqlComDAO();
		if(operId == null || "".equals(operId))
			return false;
		String []params = new String[1];
		params[0]=operId;
		return  com.checkExist(exisql, params);
	}
	
	
	public String delOperLan(String operId){
		String flag = "1";
		String delsql = "delete from  rc_operator_lan where oper_id = ?";
		SqlComDAO com = SrDAOFactory.getInstance().getSqlComDAO();
		if(operId == null || "".equals(operId))
			return "0";
		String []params = new String[1];
		params[0] = operId;
		com.excute(delsql, params);
		return flag;
	}
}
