package com.ztesoft.vsop.system.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeDeviceVO extends ValueObject implements VO {
	

	private String device_id;
	

	private String name;
	

	private String device_state_code;
	

	private String area_id;
	

	private String backup_device_id;
	

	private String ip;
	

	private String port;
	

	private String user_id;
	

	private String password;
	

	private String conn;
	

	private String driver;
	

	private String url;
	

	private String max_connect;
	

	private String priv_code;
	

	private String is_syn;
	

	private String communicate_protocol_id;
	

	private String ne_protocol_id;
	

	private String sys_code;
	
	private String communname;
	
	private String nename;
	
	private String ruleId;
	private String rulename;
	
	public String getCommunname() {
		return communname;
	}

	public void setCommunname(String communname) {
		this.communname = communname;
	}

	public String getNename() {
		return nename;
	}

	public void setNename(String nename) {
		this.nename = nename;
	}

	public String getDevice_id() {
		return device_id;
	}
	
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getDevice_state_code() {
		return device_state_code;
	}
	
	public void setDevice_state_code(String device_state_code) {
		this.device_state_code = device_state_code;
	}
	public String getArea_id() {
		return area_id;
	}
	
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getBackup_device_id() {
		return backup_device_id;
	}
	
	public void setBackup_device_id(String backup_device_id) {
		this.backup_device_id = backup_device_id;
	}
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConn() {
		return conn;
	}
	
	public void setConn(String conn) {
		this.conn = conn;
	}
	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMax_connect() {
		return max_connect;
	}
	
	public void setMax_connect(String max_connect) {
		this.max_connect = max_connect;
	}
	public String getPriv_code() {
		return priv_code;
	}
	
	public void setPriv_code(String priv_code) {
		this.priv_code = priv_code;
	}
	public String getIs_syn() {
		return is_syn;
	}
	
	public void setIs_syn(String is_syn) {
		this.is_syn = is_syn;
	}
	public String getCommunicate_protocol_id() {
		return communicate_protocol_id;
	}
	
	public void setCommunicate_protocol_id(String communicate_protocol_id) {
		this.communicate_protocol_id = communicate_protocol_id;
	}
	public String getNe_protocol_id() {
		return ne_protocol_id;
	}
	
	public void setNe_protocol_id(String ne_protocol_id) {
		this.ne_protocol_id = ne_protocol_id;
	}
	public String getSys_code() {
		return sys_code;
	}
	
	public void setSys_code(String sys_code) {
		this.sys_code = sys_code;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("device_id",this.device_id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("device_state_code",this.device_state_code);	
				
		hashMap.put("area_id",this.area_id);	
				
		hashMap.put("backup_device_id",this.backup_device_id);	
				
		hashMap.put("ip",this.ip);	
				
		hashMap.put("port",this.port);	
				
		hashMap.put("user_id",this.user_id);	
				
		hashMap.put("password",this.password);	
				
		hashMap.put("conn",this.conn);	
				
		hashMap.put("driver",this.driver);	
				
		hashMap.put("url",this.url);	
				
		hashMap.put("max_connect",this.max_connect);	
				
		hashMap.put("priv_code",this.priv_code);	
				
		hashMap.put("is_syn",this.is_syn);	
				
		hashMap.put("communicate_protocol_id",this.communicate_protocol_id);	
				
		hashMap.put("ne_protocol_id",this.ne_protocol_id);	
				
		hashMap.put("sys_code",this.sys_code);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.device_id = Const.getStrValue(hashMap, "device_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.device_state_code = Const.getStrValue(hashMap, "device_state_code");
						
			this.area_id = Const.getStrValue(hashMap, "area_id");
						
			this.backup_device_id = Const.getStrValue(hashMap, "backup_device_id");
						
			this.ip = Const.getStrValue(hashMap, "ip");
						
			this.port = Const.getStrValue(hashMap, "port");
						
			this.user_id = Const.getStrValue(hashMap, "user_id");
						
			this.password = Const.getStrValue(hashMap, "password");
						
			this.conn = Const.getStrValue(hashMap, "conn");
						
			this.driver = Const.getStrValue(hashMap, "driver");
						
			this.url = Const.getStrValue(hashMap, "url");
						
			this.max_connect = Const.getStrValue(hashMap, "max_connect");
						
			this.priv_code = Const.getStrValue(hashMap, "priv_code");
						
			this.is_syn = Const.getStrValue(hashMap, "is_syn");
						
			this.communicate_protocol_id = Const.getStrValue(hashMap, "communicate_protocol_id");
						
			this.ne_protocol_id = Const.getStrValue(hashMap, "ne_protocol_id");
						
			this.sys_code = Const.getStrValue(hashMap, "sys_code");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_DEVICE";
	}

	public String getRulename() {
		return rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
}
