package com.ztesoft.common.valueobject;

/**
 * dhtmlXtree�û����ݴ洢��
 * @author liupeidawn
 *
 */
public class TreeUserData implements java.io.Serializable{

	public String name="";
	
	public String value="";
 
	
	public TreeUserData(){
		
	}
	
	public TreeUserData(String name,String value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
