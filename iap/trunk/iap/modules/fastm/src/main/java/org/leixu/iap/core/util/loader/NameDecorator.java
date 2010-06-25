package org.leixu.iap.core.util.loader;

public class NameDecorator implements ObjectLoader{
	private String prefix = "";
	private String postfix = "";
	
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public NameDecorator(){}
	public NameDecorator(String prefix, String postfix){
		setPrefix(prefix);
		setPostfix(postfix);
	}

	public Object load(Object path){
		return prefix + path + postfix;
	}
}
