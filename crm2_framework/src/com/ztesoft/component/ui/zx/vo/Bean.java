package com.ztesoft.component.ui.zx.vo;

import java.util.ArrayList;
import java.util.List;

public class Bean implements java.io.Serializable{

	
	public String id="";
	
	public String name="";
	
	public List fields=new ArrayList();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
