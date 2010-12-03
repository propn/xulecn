package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class EntityResourceVO extends ValueObject implements VO  {

    /** 营销资源ID **/
    private String salesRescId = "";
    /** 资源实例编码,序列号 **/
    private String rescInstance2 = "";
    /** 部门ID **/
    private String departId = "";
    /** 工号ID **/
    private String operId = "";
    /** 客户ID **/
    private String custId = "";
    /** 需要更新的实例的资源状态,目标资源状态 **/
    private String rescState = "";
    /** 代表该营销资源所在的营业区 **/
    private String businessId = "";
    /** 原由分类 **/
    private String dealType = "";
    /** 详细原由 **/
	private String dealInfo = "";
    /** 前台营业受理编号，必须提供 **/
    private String acceptId = "";

    /** 不是接口的传入参数，程序内部使用,需要修改资源实例的状态 **/
    private String state = "";
    /** 不是接口的传入参数，程序内部使用,需要更新的实例的数量 **/
    private String amount = "";
    /** 此字段无用，为了编译不报错 **/
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
