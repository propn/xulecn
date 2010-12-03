package com.ztesoft.vsop.engine.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class InterfaceLogVO extends ValueObject implements VO {

	private String logId ;//日志标识 主键
	private String srcSysId ;//接入系统 来源系统
	private String interfaceId ;//接口编码 accessCode
	private String accNbr ;//产品号码 prod_inst.acc_nbr
	private String nbrType ; //产品类型 prod_inst.prod_id
	private String lanCode ;//区号  prod_inst.lan_id
	private String servId ;//服务编码 serviceCode
	private String startTime ;//开始时间
	private String endTime ;//返回时间
	private String processTime ;//耗时 单位：毫秒
	private String transactionId ;//流水号 streamNO
	private String result ;//处理结果
	private String resultDesc ;//结果描述
	private String partitionId ;//分区标识

	public InterfaceLogVO() {}

	public InterfaceLogVO( String plogId, String psrcSysId, String pinterfaceId, String paccNbr, String pnbrType, String planCode, String pservId, String pstartTime, String pendTime, String pprocessTime, String ptransactionId, String presult, String presultDesc, String ppartitionId ) {
		logId = plogId;
		srcSysId = psrcSysId;
		interfaceId = pinterfaceId;
		accNbr = paccNbr;
		nbrType = pnbrType;
		lanCode = planCode;
		servId = pservId;
		startTime = pstartTime;
		endTime = pendTime;
		processTime = pprocessTime;
		transactionId = ptransactionId;
		result = presult;
		resultDesc = presultDesc;
		partitionId = ppartitionId;
	}

	public String getLogId() {
		return logId;
	}

	public String getSrcSysId() {
		return srcSysId;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public String getAccNbr() {
		return accNbr;
	}

	public String getNbrType() {
		return nbrType;
	}

	public String getLanCode() {
		return lanCode;
	}

	public String getServId() {
		return servId;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getProcessTime() {
		return processTime;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getResult() {
		return result;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public String getPartitionId() {
		return partitionId;
	}

	public void setLogId(String pLogId) {
		logId = pLogId;
	}

	public void setSrcSysId(String pSrcSysId) {
		srcSysId = pSrcSysId;
	}

	public void setInterfaceId(String pInterfaceId) {
		interfaceId = pInterfaceId;
	}

	public void setAccNbr(String pAccNbr) {
		accNbr = pAccNbr;
	}

	public void setNbrType(String pNbrType) {
		nbrType = pNbrType;
	}

	public void setLanCode(String pLanCode) {
		lanCode = pLanCode;
	}

	public void setServId(String pServId) {
		servId = pServId;
	}

	public void setStartTime(String pStartTime) {
		startTime = pStartTime;
	}

	public void setEndTime(String pEndTime) {
		endTime = pEndTime;
	}

	public void setProcessTime(String pProcessTime) {
		processTime = pProcessTime;
	}

	public void setTransactionId(String pTransactionId) {
		transactionId = pTransactionId;
	}

	public void setResult(String pResult) {
		result = pResult;
	}

	public void setResultDesc(String pResultDesc) {
		resultDesc = pResultDesc;
	}

	public void setPartitionId(String pPartitionId) {
		partitionId = pPartitionId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LOG_ID",this.logId);
		hashMap.put("SRC_SYS_ID",this.srcSysId);
		hashMap.put("INTERFACE_ID",this.interfaceId);
		hashMap.put("ACC_NBR",this.accNbr);
		hashMap.put("NBR_TYPE",this.nbrType);
		hashMap.put("LAN_CODE",this.lanCode);
		hashMap.put("SERVICE_ID",this.servId);
		hashMap.put("START_TIME",this.startTime);
		hashMap.put("END_TIME",this.endTime);
		hashMap.put("PROCESS_TIME",this.processTime);
		hashMap.put("TRANSACTION_ID",this.transactionId);
		hashMap.put("RESULT",this.result);
		hashMap.put("RESULT_DESC",this.resultDesc);
		hashMap.put("PARTITION_ID",this.partitionId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.logId = (String) hashMap.get("LOG_ID");
			this.srcSysId = (String) hashMap.get("SRC_SYS_ID");
			this.interfaceId = (String) hashMap.get("INTERFACE_ID");
			this.accNbr = (String) hashMap.get("ACC_NBR");
			this.nbrType = (String) hashMap.get("NBR_TYPE");
			this.lanCode = (String) hashMap.get("LAN_CODE");
			this.servId = (String) hashMap.get("SERVICE_ID");
			this.startTime = (String) hashMap.get("START_TIME");
			this.endTime = (String) hashMap.get("END_TIME");
			this.processTime = (String) hashMap.get("PROCESS_TIME");
			this.transactionId = (String) hashMap.get("TRANSACTION_ID");
			this.result = (String) hashMap.get("RESULT");
			this.resultDesc = (String) hashMap.get("RESULT_DESC");
			this.partitionId = (String) hashMap.get("PARTITION_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "INTERFACE_LOG";
	}

}
