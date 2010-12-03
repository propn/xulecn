package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class EntityResourceVO extends ValueObject implements VO  {

    /** Ӫ����ԴID **/
    private String salesRescId = "";
    /** ��Դʵ������,���к� **/
    private String rescInstance2 = "";
    /** ����ID **/
    private String departId = "";
    /** ����ID **/
    private String operId = "";
    /** �ͻ�ID **/
    private String custId = "";
    /** ��Ҫ���µ�ʵ������Դ״̬,Ŀ����Դ״̬ **/
    private String rescState = "";
    /** �����Ӫ����Դ���ڵ�Ӫҵ�� **/
    private String businessId = "";
    /** ԭ�ɷ��� **/
    private String dealType = "";
    /** ��ϸԭ�� **/
	private String dealInfo = "";
    /** ǰ̨Ӫҵ�����ţ������ṩ **/
    private String acceptId = "";

    /** ���ǽӿڵĴ�������������ڲ�ʹ��,��Ҫ�޸���Դʵ����״̬ **/
    private String state = "";
    /** ���ǽӿڵĴ�������������ڲ�ʹ��,��Ҫ���µ�ʵ�������� **/
    private String amount = "";
    /** ���ֶ����ã�Ϊ�˱��벻���� **/
    private String status = "";

    public EntityResourceVO(){

    }

    public EntityResourceVO(String salesRescId, String rescInstance2, String departId,
                    String operId, String custId, String rescState, String amount) {
            super();
            this.salesRescId = salesRescId;
            this.rescInstance2 = rescInstance2;
            this.departId = departId;
            this.operId = operId;
            this.custId = custId;
            this.rescState = rescState;
            this.amount = amount;
    }

    public void loadFromHashMap(HashMap hashMap) {

    }

    public HashMap unloadToHashMap() {
            HashMap hashMap = new HashMap();
            return hashMap;
    }

    public List getKeyFields() {
            ArrayList arrayList = new ArrayList();
            return arrayList;
    }

    public String getTableName() {
            return "RC_SALE_LOG";
    }

    public String getCustId() {
            return custId;
    }

    public void setCustId(String custId) {
            this.custId = custId;
    }

    public String getDepartId() {
            return departId;
    }

    public void setDepartId(String departId) {
            this.departId = departId;
    }

    public String getOperId() {
            return operId;
    }

    public void setOperId(String operId) {
            this.operId = operId;
    }

    public String getRescInstance2() {
            return rescInstance2;
    }

    public void setRescInstance2(String rescInstance2) {
            this.rescInstance2 = rescInstance2;
    }

    public String getSalesRescId() {
            return salesRescId;
    }

    public void setSalesRescId(String salesRescId) {
            this.salesRescId = salesRescId;
    }

    public String getAmount() {
            return amount;
    }

    public void setAmount(String amount) {
            this.amount = amount;
    }

    public String getBusinessId() {
            return businessId;
    }

    public String getRescState() {
            return rescState;
    }

    public String getState() {
            return state;
    }

  public String getDealType() {
    return dealType;
  }

  public String getDealInfo() {
    return dealInfo;
  }

  public String getStatus() {
    return status;
  }

  public String getAcceptId() {
    return acceptId;
  }

  public void setBusinessId(String businessId) {
                this.businessId = businessId;
	}
	
	public void setRescState(String rescState) {
	        this.rescState = rescState;
	}
	
	public void setState(String state) {
	        this.state = state;
	}

  public void setDealType(String dealType) {
    this.dealType = dealType;
  }

  public void setDealInfo(String dealInfo) {
    this.dealInfo = dealInfo;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setAcceptId(String acceptId) {
    this.acceptId = acceptId;
  }

}
