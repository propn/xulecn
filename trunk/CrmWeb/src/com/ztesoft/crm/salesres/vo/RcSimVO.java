package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcSimVO extends ValueObject implements VO {
    /** 主卡类型 **/
    public static final String Backup_MainCard = "0";

    /** 备卡类型 **/
    public static final String Backup_BackCard = "1";

    /** 白卡类型 **/
    public static final String Backup_BlankCard = "2";

    /** sim卡类型，1普通卡，4国际卡，2evdo卡 **/
    public static final String SimType_nomal = "1";
    public static final String SimType_inter = "4";
    public static final String SimType_evdo = "5";
    private String accept_id = "";
    private String cardmill = "";
    private String initTime = "";
    private String pukNo = "";
    private String simType = "";
    private String capacity = "";
    private String telCapacity = "";
    private String smsCapacity = "";
    private String backup = "";
    private String expDate = "";
    private String effDate = "";
    private String state = "";
    private String rescState = "";
    private String storageId = "";
    private String salesRescId = "";
    private String rescLevel = "";
    private String rescInstanceCode = "";
    private String rescInstanceId = "";
    private String imsiId = "";
    private String storageName = "";
    private String imsiId2 = "";

    /** 套卡对应的号码 **/
    private String dnNo = "";
    private String dnNo2 = "";
    private String wlanPwd = "";
    private String wlanAcct = "";
    private String msisdn = "";
    private String ki = "";
    private String pin2 = "";
    private String pin1 = "";
    private String puk2No = "";
    private String esn = "";
    private String umid = "";
    private String servCode = "";
    private String servName = "";
    private String cardType = "";
    private String cServType = "";
    private String acc = "";
    private String smsp = "";
    private String operId = "";
    private String departId = "";

    /** 双芯卡标识：普通UIM卡(0)，单芯语音卡(1)、单芯数据卡(2)，一卡双芯语音卡(3)，一卡双芯数据卡(4)。 **/
    private String simChipType = "";

    /** 该卡对应的双芯卡对应的卡号 **/
    private String simCodeCorr = "";
    public String cServTypeName = "";

    public RcSimVO() {
    }

    public RcSimVO(String paccept_id, String pcardmill, String pinitTime,
        String ppukNo, String psimType, String pcapacity, String ptelCapacity,
        String psmsCapacity, String pbackup, String pexpDate, String peffDate,
        String pstate, String prescState, String pstorageId,
        String psalesRescId, String prescLevel, String prescInstanceCode,
        String prescInstanceId, String pimsiId, String pstorageName,
        String pimsiId2, String psimChipType) {
        accept_id = paccept_id;
        cardmill = pcardmill;
        initTime = pinitTime;
        pukNo = ppukNo;
        simType = psimType;
        capacity = pcapacity;
        telCapacity = ptelCapacity;
        smsCapacity = psmsCapacity;
        backup = pbackup;
        expDate = pexpDate;
        effDate = peffDate;
        state = pstate;
        rescState = prescState;
        storageId = pstorageId;
        salesRescId = psalesRescId;
        rescLevel = prescLevel;
        rescInstanceCode = prescInstanceCode;
        rescInstanceId = prescInstanceId;
        imsiId = pimsiId;
        storageName = pstorageName;
        imsiId2 = pimsiId2;
        simChipType = psimChipType;
    }

    public void setCServTypeName(String servTypeName) {
        cServTypeName = servTypeName;
    }

    public String getCServTypeName() {
        return cServTypeName;
    }

    public String getCardmill() {
        return cardmill;
    }

    public String getInitTime() {
        return initTime;
    }

    public String getPukNo() {
        return pukNo;
    }

    public String getSimType() {
        return simType;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getTelCapacity() {
        return telCapacity;
    }

    public String getSmsCapacity() {
        return smsCapacity;
    }

    public String getBackup() {
        return backup;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getEffDate() {
        return effDate;
    }

    public String getState() {
        return state;
    }

    public String getRescState() {
        return rescState;
    }

    public String getStorageId() {
        return storageId;
    }

    public String getSalesRescId() {
        return salesRescId;
    }

    public String getRescLevel() {
        return rescLevel;
    }

    public String getRescInstanceCode() {
        return rescInstanceCode;
    }

    public String getRescInstanceId() {
        return rescInstanceId;
    }

    public String getImsiId() {
        return imsiId;
    }

    public String getStorageName() {
        return storageName;
    }

    public String getImsiId2() {
        return imsiId2;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getPin1() {
        return pin1;
    }

    public String getWlanPwd() {
        return wlanPwd;
    }

    public String getPuk2No() {
        return puk2No;
    }

    public String getKi() {
        return ki;
    }

    public String getPin2() {
        return pin2;
    }

    public String getWlanAcct() {
        return wlanAcct;
    }

    public String getEsn() {
        return esn;
    }

    public String getDnNo() {
        return dnNo;
    }

    public String getDnNo2() {
        return dnNo2;
    }

    public String getServName() {
        return servName;
    }

    public String getUmid() {
        return umid;
    }

    public String getCServType() {
        return cServType;
    }

    public String getCardType() {
        return cardType;
    }

    public String getServCode() {
        return servCode;
    }

    public void setCardmill(String pCardmill) {
        cardmill = pCardmill;
    }

    public void setInitTime(String pInitTime) {
        initTime = pInitTime;
    }

    public void setPukNo(String pPukNo) {
        pukNo = pPukNo;
    }

    public void setSimType(String pSimType) {
        simType = pSimType;
    }

    public void setCapacity(String pCapacity) {
        capacity = pCapacity;
    }

    public void setTelCapacity(String pTelCapacity) {
        telCapacity = pTelCapacity;
    }

    public void setSmsCapacity(String pSmsCapacity) {
        smsCapacity = pSmsCapacity;
    }

    public void setBackup(String pBackup) {
        backup = pBackup;
    }

    public void setExpDate(String pExpDate) {
        expDate = pExpDate;
    }

    public void setEffDate(String pEffDate) {
        effDate = pEffDate;
    }

    public void setState(String pState) {
        state = pState;
    }

    public void setRescState(String pRescState) {
        rescState = pRescState;
    }

    public void setStorageId(String pStorageId) {
        storageId = pStorageId;
    }

    public void setSalesRescId(String pSalesRescId) {
        salesRescId = pSalesRescId;
    }

    public void setRescLevel(String pRescLevel) {
        rescLevel = pRescLevel;
    }

    public void setRescInstanceCode(String pRescInstanceCode) {
        rescInstanceCode = pRescInstanceCode;
    }

    public void setRescInstanceId(String pRescInstanceId) {
        rescInstanceId = pRescInstanceId;
    }

    public void setImsiId(String imsiId) {
        this.imsiId = imsiId;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public void setImsiId2(String imsiId2) {
        this.imsiId2 = imsiId2;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setPin1(String pin1) {
        this.pin1 = pin1;
    }

    public void setWlanPwd(String wlanPwd) {
        this.wlanPwd = wlanPwd;
    }

    public void setPuk2No(String puk2No) {
        this.puk2No = puk2No;
    }

    public void setKi(String ki) {
        this.ki = ki;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public void setWlanAcct(String wlanAcct) {
        this.wlanAcct = wlanAcct;
    }

    public void setEsn(String esn) {
        this.esn = esn;
    }

    public void setDnNo(String dnNo) {
        this.dnNo = dnNo;
    }

    public void setDnNo2(String dnNo2) {
        this.dnNo2 = dnNo2;
    }

    public void setServName(String servName) {
        this.servName = servName;
    }

    public void setUmid(String umid) {
        this.umid = umid;
    }

    public void setCServType(String cServType) {
        this.cServType = cServType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setServCode(String servCode) {
        this.servCode = servCode;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("CARDMILL", this.cardmill);
        hashMap.put("INIT_TIME", this.initTime);
        hashMap.put("PUK_NO", this.pukNo);
        hashMap.put("SIM_TYPE", this.simType);
        hashMap.put("CAPACITY", this.capacity);
        hashMap.put("TEL_CAPACITY", this.telCapacity);
        hashMap.put("SMS_CAPACITY", this.smsCapacity);
        hashMap.put("BACKUP", this.backup);
        hashMap.put("EXP_DATE", this.expDate);
        hashMap.put("EFF_DATE", this.effDate);
        hashMap.put("STATE", this.state);
        hashMap.put("RESOURCE_STATE", this.rescState);
        hashMap.put("STORAGE_ID", this.storageId);
        hashMap.put("SALES_RESOURCE_ID", this.salesRescId);
        hashMap.put("RESOURCE_LEVEL", this.rescLevel);
        hashMap.put("RESOURCE_INSTANCE_CODE", this.rescInstanceCode);
        hashMap.put("RESOURCE_INSTANCE_ID", this.rescInstanceId);
        hashMap.put("SIM_CHIP_TYPE", this.simChipType);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.cardmill = (String) hashMap.get("CARDMILL");
            this.initTime = (String) hashMap.get("INIT_TIME");
            this.pukNo = (String) hashMap.get("PUK_NO");
            this.simType = (String) hashMap.get("SIM_TYPE");
            this.capacity = (String) hashMap.get("CAPACITY");
            this.telCapacity = (String) hashMap.get("TEL_CAPACITY");
            this.smsCapacity = (String) hashMap.get("SMS_CAPACITY");
            this.backup = (String) hashMap.get("BACKUP");
            this.expDate = (String) hashMap.get("EXP_DATE");
            this.effDate = (String) hashMap.get("EFF_DATE");
            this.state = (String) hashMap.get("STATE");
            this.rescState = (String) hashMap.get("RESOURCE_STATE");
            this.storageId = (String) hashMap.get("STORAGE_ID");
            this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
            this.rescLevel = (String) hashMap.get("RESOURCE_LEVEL");
            this.rescInstanceCode = (String) hashMap.get(
                    "RESOURCE_INSTANCE_CODE");
            this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
            this.simChipType = (String) hashMap.get("SIM_CHIP_TYPE");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("RESOURCE_INSTANCE_ID");

        return arrayList;
    }

    public String getTableName() {
        return "RC_SIM";
    }

    public String getAccept_id() {
        return accept_id;
    }

    public void setAccept_id(String accept_id) {
        this.accept_id = accept_id;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getSmsp() {
        return smsp;
    }

    public void setSmsp(String smsp) {
        this.smsp = smsp;
    }

    public String getSimCodeCorr() {
        return simCodeCorr;
    }

    public void setSimCodeCorr(String simCodeCorr) {
        this.simCodeCorr = simCodeCorr;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getSimChipType() {
        return simChipType;
    }

    public void setSimChipType(String psimChipType) {
        this.simChipType = psimChipType;
    }
}
