package com.ztesoft.oaas.dao.sms;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.ztesoft.oaas.vo.InformVO;
import com.ztesoft.oaas.vo.StaffSmsVo;

public class SendSmsBo {
	 static  Logger log=Logger.getLogger(SendSmsBo.class);
//	修改密码短信提醒
		public void sendSmsChangePwdNote(String staffCode) throws RemoteException, Exception{
			SmsDao sms=new SmsDaoImpl();			 
			String mobile="122"; 
			StaffSmsVo vo=sms.getStaffSmsVoByStaffcode(staffCode);
			if(vo==null)return ;		 
			mobile=vo.getMobile();
			
			InformVO note=new InformVO();
	 
		 
			String msgContent="中国电信温馨提示：您的密码已修改成功，请您妥善保管。感谢您的使用！"; 
			note.setDestNum(mobile);
			note.setChargeTypeId("10001");//计费号码
			note.setMsgContent(msgContent);//短信内容
			note.setSysFlagType("crm");
			note.setAcceptId("-1");//受理单号，此处没有
			note.setSendTime("0");
			note.setStateFlag("0");//待处理
			note.setStateMsg("待处理");
			
			sms.sendMsg(note);
		}
//		public void sendSmsLoginNote(StaffOnlineVO loginVo,String staffCode) throws RemoteException, Exception{
//			SmsDao sms=new SmsDaoImpl();	
//			String mobile="122";
//			String ids=loginVo.getLogInfoId();
//			 
//			StaffSmsVo vo=sms.getStaffSmsVoByStaffcode(staffCode);
//			if(vo==null)  return ;
//			
//			mobile=vo.getMobile();
//			
//			InformVO note=new InformVO();
//	 
//		 
//			String msgContent="您的营业工号"+staffCode+"于"+loginVo.getLogonDate()+"在"+loginVo.getIp()+"成功登录！";
//			note.setDestNum(mobile);
//			note.setChargeTypeId("10001");//计费号码
//			note.setMsgContent(msgContent);//短信内容
//			note.setSysFlagType("crm");
//			note.setAcceptId("-1");//受理单号，此处没有
//			note.setSendTime("0");
//			note.setStateFlag("0");//待处理
//			note.setStateMsg("待处理");
//			
//			sms.sendMsg(note);
//		}
	 
}
