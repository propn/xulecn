package com.ztesoft.vsop.system.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SpIntSysVO extends ValueObject implements VO {
	

	private String int_sys_id;
	

	private String communicate_protocol_id;
	

	private String sys_code;
	

	private String name;
	

	private String state;
	

	private String sys_ip;
	

	private String sys_port;
	

	private String sys_conn;
	

	private String sys_user;
	

	private String sys_pwd;
	

	private String sys_vendor;
	

	private String comments;
	

	private String sys_driver;
	

	private String sys_url;
	

	private String ne_protocol_id;
	
	private String communname;
	
	private String nename;
	
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

	public String getInt_sys_id() {
		return int_sys_id;
	}
	
	public void setInt_sys_id(String int_sys_id) {
		this.int_sys_id = int_sys_id;
	}
	public String getCommunicate_protocol_id() {
		return communicate_protocol_id;
	}
	
	public void setCommunicate_protocol_id(String communicate_protocol_id) {
		this.communicate_protocol_id = communicate_protocol_id;
	}
	public String getSys_code() {
		return sys_code;
	}
	
	public void setSys_code(String sys_code) {
		this.sys_code = sys_code;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getSys_ip() {
		return sys_ip;
	}
	
	public void setSys_ip(String sys_ip) {
		this.sys_ip = sys_ip;
	}
	public String getSys_port() {
		return sys_port;
	}
	
	public void setSys_port(String sys_port) {
		this.sys_port = sys_port;
	}
	public String getSys_conn() {
		return sys_conn;
	}
	
	public void setSys_conn(String sys_conn) {
		this.sys_conn = sys_conn;
	}
	public String getSys_user() {
		return sys_user;
	}
	
	public void setSys_user(String sys_user) {
		this.sys_user = sys_user;
	}
	public String getSys_pwd() {
		return sys_pwd;
	}
	
	public void setSys_pwd(String sys_pwd) {
		this.sys_pwd = sys_pwd;
	}
	public String getSys_vendor() {
		return sys_vendor;
	}
	
	public void setSys_vendor(String sys_vendor) {
		this.sys_vendor = sys_vendor;
	}
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSys_driver() {
		return sys_driver;
	}
	
	public void setSys_driver(String sys_driver) {
		this.sys_driver = sys_driver;
	}
	public String getSys_url() {
		return sys_url;
	}
	
	public void setSys_url(String sys_url) {
		this.sys_url = sys_url;
	}
	public String getNe_protocol_id() {
		return ne_protocol_id;
	}
	
	public void setNe_protocol_id(String ne_protocol_id) {
		this.ne_protocol_id = ne_protocol_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("int_sys_id",this.int_sys_id);	
				
		hashMap.put("communicate_protocol_id",this.communicate_protocol_id);	
				
		hashMap.put("sys_code",this.sys_code);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("sys_ip",this.sys_ip);	
				
		hashMap.put("sys_port",this.sys_port);	
				
		hashMap.put("sys_conn",this.sys_conn);	
				
		hashMap.put("sys_user",this.sys_user);	
				
		hashMap.put("sys_pwd",this.sys_pwd);	
				
		hashMap.put("sys_vendor",this.sys_vendor);	
				
		hashMap.put("comments",this.comments);	
				
		hashMap.put("sys_driver",this.sys_driver);	
				
		hashMap.put("sys_url",this.sys_url);	
				
		hashMap.put("ne_protocol_id",this.ne_protocol_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.int_sys_id = Const.getStrValue(hashMap, "int_sys_id");
						
			this.communicate_protocol_id = Const.getStrValue(hashMap, "communicate_protocol_id");
						
			this.sys_code = Const.getStrValue(hashMap, "sys_code");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.sys_ip = Const.getStrValue(hashMap, "sys_ip");
						
			this.sys_port = Const.getStrValue(hashMap, "sys_port");
						
			this.sys_conn = Const.getStrValue(hashMap, "sys_conn");
						
			this.sys_user = Const.getStrValue(hashMap, "sys_user");
						
			this.sys_pwd = Const.getStrValue(hashMap, "sys_pwd");
						
			this.sys_vendor = Const.getStrValue(hashMap, "sys_vendor");
						
			this.comments = Const.getStrValue(hashMap, "comments");
						
			this.sys_driver = Const.getStrValue(hashMap, "sys_driver");
						
			this.sys_url = Const.getStrValue(hashMap, "sys_url");
						
			this.ne_protocol_id = Const.getStrValue(hashMap, "ne_protocol_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SP_INT_SYS";
	}
	
}
