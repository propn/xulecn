package com.ztesoft.vsop.order.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class VsopCompareDealMidVO extends ValueObject implements VO {
	

	private String streaming_no;
	

	private String subscription_id;
	

	private String oa;
	

	private String oa_type;
	

	private String da;
	

	private String da_type;
	

	private String fa;
	

	private String fa_type;
	

	private String withdrawer;
	

	private String withdraw_reason;
	

	private String suspend_reason;
	

	private String spid;
	

	private String product_id;
	

	private String package_id;
	

	private String pproduct_offer_id;
	

	private String status;
	

	private String subscribe_time;
	

	private String effective_time;
	

	private String expire_time;
	

	private String suspend_time;
	

	private String resume_time;
	

	private String last_use_time;
	

	private String silence;
	

	private String channel_player_id;
	

	private String flag;
	

	private String old_status;
	

	private String old_package_id;
	

	private String old_pproduct_offer_id;
	

	private String file_name;
	

	public String getStreaming_no() {
		return streaming_no;
	}
	
	public void setStreaming_no(String streaming_no) {
		this.streaming_no = streaming_no;
	}
	public String getSubscription_id() {
		return subscription_id;
	}
	
	public void setSubscription_id(String subscription_id) {
		this.subscription_id = subscription_id;
	}
	public String getOa() {
		return oa;
	}
	
	public void setOa(String oa) {
		this.oa = oa;
	}
	public String getOa_type() {
		return oa_type;
	}
	
	public void setOa_type(String oa_type) {
		this.oa_type = oa_type;
	}
	public String getDa() {
		return da;
	}
	
	public void setDa(String da) {
		this.da = da;
	}
	public String getDa_type() {
		return da_type;
	}
	
	public void setDa_type(String da_type) {
		this.da_type = da_type;
	}
	public String getFa() {
		return fa;
	}
	
	public void setFa(String fa) {
		this.fa = fa;
	}
	public String getFa_type() {
		return fa_type;
	}
	
	public void setFa_type(String fa_type) {
		this.fa_type = fa_type;
	}
	public String getWithdrawer() {
		return withdrawer;
	}
	
	public void setWithdrawer(String withdrawer) {
		this.withdrawer = withdrawer;
	}
	public String getWithdraw_reason() {
		return withdraw_reason;
	}
	
	public void setWithdraw_reason(String withdraw_reason) {
		this.withdraw_reason = withdraw_reason;
	}
	public String getSuspend_reason() {
		return suspend_reason;
	}
	
	public void setSuspend_reason(String suspend_reason) {
		this.suspend_reason = suspend_reason;
	}
	public String getSpid() {
		return spid;
	}
	
	public void setSpid(String spid) {
		this.spid = spid;
	}
	public String getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getPackage_id() {
		return package_id;
	}
	
	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}
	public String getPproduct_offer_id() {
		return pproduct_offer_id;
	}
	
	public void setPproduct_offer_id(String pproduct_offer_id) {
		this.pproduct_offer_id = pproduct_offer_id;
	}
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubscribe_time() {
		return subscribe_time;
	}
	
	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	public String getEffective_time() {
		return effective_time;
	}
	
	public void setEffective_time(String effective_time) {
		this.effective_time = effective_time;
	}
	public String getExpire_time() {
		return expire_time;
	}
	
	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}
	public String getSuspend_time() {
		return suspend_time;
	}
	
	public void setSuspend_time(String suspend_time) {
		this.suspend_time = suspend_time;
	}
	public String getResume_time() {
		return resume_time;
	}
	
	public void setResume_time(String resume_time) {
		this.resume_time = resume_time;
	}
	public String getLast_use_time() {
		return last_use_time;
	}
	
	public void setLast_use_time(String last_use_time) {
		this.last_use_time = last_use_time;
	}
	public String getSilence() {
		return silence;
	}
	
	public void setSilence(String silence) {
		this.silence = silence;
	}
	public String getChannel_player_id() {
		return channel_player_id;
	}
	
	public void setChannel_player_id(String channel_player_id) {
		this.channel_player_id = channel_player_id;
	}
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getOld_status() {
		return old_status;
	}
	
	public void setOld_status(String old_status) {
		this.old_status = old_status;
	}
	public String getOld_package_id() {
		return old_package_id;
	}
	
	public void setOld_package_id(String old_package_id) {
		this.old_package_id = old_package_id;
	}
	public String getOld_pproduct_offer_id() {
		return old_pproduct_offer_id;
	}
	
	public void setOld_pproduct_offer_id(String old_pproduct_offer_id) {
		this.old_pproduct_offer_id = old_pproduct_offer_id;
	}
	public String getFile_name() {
		return file_name;
	}
	
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("streaming_no",this.streaming_no);	
				
		hashMap.put("subscription_id",this.subscription_id);	
				
		hashMap.put("oa",this.oa);	
				
		hashMap.put("oa_type",this.oa_type);	
				
		hashMap.put("da",this.da);	
				
		hashMap.put("da_type",this.da_type);	
				
		hashMap.put("fa",this.fa);	
				
		hashMap.put("fa_type",this.fa_type);	
				
		hashMap.put("withdrawer",this.withdrawer);	
				
		hashMap.put("withdraw_reason",this.withdraw_reason);	
				
		hashMap.put("suspend_reason",this.suspend_reason);	
				
		hashMap.put("spid",this.spid);	
				
		hashMap.put("product_id",this.product_id);	
				
		hashMap.put("package_id",this.package_id);	
				
		hashMap.put("pproduct_offer_id",this.pproduct_offer_id);	
				
		hashMap.put("status",this.status);	
				
		hashMap.put("subscribe_time",this.subscribe_time);	
				
		hashMap.put("effective_time",this.effective_time);	
				
		hashMap.put("expire_time",this.expire_time);	
				
		hashMap.put("suspend_time",this.suspend_time);	
				
		hashMap.put("resume_time",this.resume_time);	
				
		hashMap.put("last_use_time",this.last_use_time);	
				
		hashMap.put("silence",this.silence);	
				
		hashMap.put("channel_player_id",this.channel_player_id);	
				
		hashMap.put("flag",this.flag);	
				
		hashMap.put("old_status",this.old_status);	
				
		hashMap.put("old_package_id",this.old_package_id);	
				
		hashMap.put("old_pproduct_offer_id",this.old_pproduct_offer_id);	
				
		hashMap.put("file_name",this.file_name);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.streaming_no = Const.getStrValue(hashMap, "streaming_no");
						
			this.subscription_id = Const.getStrValue(hashMap, "subscription_id");
						
			this.oa = Const.getStrValue(hashMap, "oa");
						
			this.oa_type = Const.getStrValue(hashMap, "oa_type");
						
			this.da = Const.getStrValue(hashMap, "da");
						
			this.da_type = Const.getStrValue(hashMap, "da_type");
						
			this.fa = Const.getStrValue(hashMap, "fa");
						
			this.fa_type = Const.getStrValue(hashMap, "fa_type");
						
			this.withdrawer = Const.getStrValue(hashMap, "withdrawer");
						
			this.withdraw_reason = Const.getStrValue(hashMap, "withdraw_reason");
						
			this.suspend_reason = Const.getStrValue(hashMap, "suspend_reason");
						
			this.spid = Const.getStrValue(hashMap, "spid");
						
			this.product_id = Const.getStrValue(hashMap, "product_id");
						
			this.package_id = Const.getStrValue(hashMap, "package_id");
						
			this.pproduct_offer_id = Const.getStrValue(hashMap, "pproduct_offer_id");
						
			this.status = Const.getStrValue(hashMap, "status");
						
			this.subscribe_time = Const.getStrValue(hashMap, "subscribe_time");
						
			this.effective_time = Const.getStrValue(hashMap, "effective_time");
						
			this.expire_time = Const.getStrValue(hashMap, "expire_time");
						
			this.suspend_time = Const.getStrValue(hashMap, "suspend_time");
						
			this.resume_time = Const.getStrValue(hashMap, "resume_time");
						
			this.last_use_time = Const.getStrValue(hashMap, "last_use_time");
						
			this.silence = Const.getStrValue(hashMap, "silence");
						
			this.channel_player_id = Const.getStrValue(hashMap, "channel_player_id");
						
			this.flag = Const.getStrValue(hashMap, "flag");
						
			this.old_status = Const.getStrValue(hashMap, "old_status");
						
			this.old_package_id = Const.getStrValue(hashMap, "old_package_id");
						
			this.old_pproduct_offer_id = Const.getStrValue(hashMap, "old_pproduct_offer_id");
						
			this.file_name = Const.getStrValue(hashMap, "file_name");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "VSOP_COMPARE_DEAL_MID";
	}
	
}
