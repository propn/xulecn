package com.ztesoft.crm.business.common.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServProductRestrict extends Attributes implements java.io.Serializable{

	public String product_name;
	
	public String product_id;
	
	public int restrict_type;//3必须,2缺省，1可选
	
	
	
	 //	深度拷贝ServProductRestrict对象实例的数据,必须注意的是：放置在ServProductRestrict中的对象数据都必须是序列化的Serializable
	public Object clone() {
		ServProductRestrict cloneObj = null;
		ObjectOutputStream oo = null;
		ObjectInputStream oi = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(out);
			oo.writeObject(this);
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			oi = new ObjectInputStream(in);
			cloneObj = (ServProductRestrict) oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(oo!=null){
				try {
					oo.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(oi!=null){
				try {
					oi.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
		}
		return cloneObj;
	}



	public int getRestrict_type() {
		return restrict_type;
	}



	public void setRestrict_type(int restrict_type) {
		this.restrict_type = restrict_type;
	}
}
