package com.ztesoft.oaas.vo;

import java.io.Serializable;

public class InformVO implements Serializable {
	 private String  serialNo;// SERIAL_NO            NUMBER(12)                      not null,
	 private String  destNum;//  DEST_NUM             VARCHAR2(20)                    not null,
	 private String chargeTypeId;//   CHARGE_TYPE_ID       VARCHAR2(20)                    not null,
	 private String msgContent;//   MSG_CONTENT          VARCHAR2(200)                   not null,
	 private String sysFlagType;//   SYS_FLAG_TYPE        CHAR(9)                         not null,
	 private String acceptId;//  ACCEPT_ID            VARCHAR2(32)                    not null,
	 private String sendTime;//   SEND_TIME            NUMBER(1),
	 private String stateFlag;// STATE_FLAG           CHAR(1),
	 private String stateMsg;// STATE_MSG            VARCHAR2(500),
     public void setSerialNo(String serialNo){
    	 this.serialNo=serialNo;
     }
     public String getSerialNO(){
    	 return serialNo;
     }
     public void setDestNum(String destNum){
    	 this.destNum=destNum;
     }
     public String getDestNum(){
    	 return destNum;
     }
     public void setChargeTypeId(String chargeTypeId){
    	 this.chargeTypeId=chargeTypeId;
     }
     public String getChargeTypeId(){
    	 return chargeTypeId;
     }
     public void setMsgContent(String msgContent){
    	 this.msgContent=msgContent;
     }
     public String getMsgContent(){
    	 return this.msgContent;
     }
     public void setSysFlagType(String sysFlagType ){
    	 this.sysFlagType=sysFlagType;
     }
     public String getSysFlagType(){
    	 return sysFlagType;
     }
     public void setAcceptId(String acceptId){
    	 this.acceptId=acceptId;
     }
     public String getAcceptId(){
    	return acceptId;
     }
      
	 
	 public void setSendTime(String sendTime){
    	 this.sendTime=sendTime;
     }
     public String getSendTime(){
    	return sendTime;
     }
     
     public void setStateFlag(String stateFlag){
    	 this.stateFlag=stateFlag;
     }
     public String getStateFlag(){
    	return stateFlag;
     }
     
     public void setStateMsg(String stateMsg){
    	 this.stateMsg=stateMsg;
     }
     public String getStateMsg(){
    	return stateMsg;
     }
     
}
