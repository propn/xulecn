package com.ztesoft.oaas.dao.sms;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.ztesoft.oaas.vo.InformVO;
import com.ztesoft.oaas.vo.StaffSmsVo;

public class SendSmsBo {
	 static  Logger log=Logger.getLogger(SendSmsBo.class);
//	�޸������������
		public void sendSmsChangePwdNote(String staffCode) throws RemoteException, Exception{
			SmsDao sms=new SmsDaoImpl();			 
			String mobile="122"; 
			StaffSmsVo vo=sms.getStaffSmsVoByStaffcode(staffCode);
			if(vo==null)return ;		 
			mobile=vo.getMobile();
			
			InformVO note=new InformVO();
	 
		 
			String msgContent="�й�������ܰ��ʾ�������������޸ĳɹ����������Ʊ��ܡ���л����ʹ�ã�"; 
			note.setDestNum(mobile);
			note.setChargeTypeId("10001");//�ƷѺ���
			note.setMsgContent(msgContent);//��������
			note.setSysFlagType("crm");
			note.setAcceptId("-1");//�����ţ��˴�û��
			note.setSendTime("0");
			note.setStateFlag("0");//������
			note.setStateMsg("������");
			
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
//			String msgContent="����Ӫҵ����"+staffCode+"��"+loginVo.getLogonDate()+"��"+loginVo.getIp()+"�ɹ���¼��";
//			note.setDestNum(mobile);
//			note.setChargeTypeId("10001");//�ƷѺ���
//			note.setMsgContent(msgContent);//��������
//			note.setSysFlagType("crm");
//			note.setAcceptId("-1");//�����ţ��˴�û��
//			note.setSendTime("0");
//			note.setStateFlag("0");//������
//			note.setStateMsg("������");
//			
//			sms.sendMsg(note);
//		}
	 
}
