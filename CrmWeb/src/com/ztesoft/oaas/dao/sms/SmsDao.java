package com.ztesoft.oaas.dao.sms;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.oaas.vo.InformVO;
import com.ztesoft.oaas.vo.SmsVo;
import com.ztesoft.oaas.vo.StaffSmsVo;

public interface SmsDao extends DAO {
	public void sendMsg(SmsVo smsVo)throws DAOSystemException;
	public int  insertStaffCodeMobile(String staffCode,String mobile)throws DAOSystemException;
	public int setStaffMobile(String staffCode,String mobile)throws DAOSystemException;
	public void sendMsg(InformVO smsVo) throws DAOSystemException;
	public  StaffSmsVo getStaffSmsVoByStaffcode(String staffcode) throws DAOSystemException;
}
