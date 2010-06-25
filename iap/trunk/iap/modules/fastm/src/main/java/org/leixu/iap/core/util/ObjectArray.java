/*
 * Lightor
 */
package org.leixu.iap.core.util;

/**
 * @author hailong.wang
 *
 */
public class ObjectArray {
	protected Object[] objs = null;
	public String delimiter = " \n"; 

	protected volatile transient int hashCode = 0;
	protected volatile transient String str = null;

	public ObjectArray(Object[] objs){
		this.objs = objs;
	}
	
	public Object[] get(){
		return objs;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static Object wrap(Object obj){
		if(obj instanceof Object[]){
			return new ObjectArray((Object[])obj);
		}
		return obj;
	}

	/**
	 * 
	 */
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		if(this == obj)
			return true;

		if(!(obj instanceof ObjectArray)) 
			return false;

		ObjectArray another = (ObjectArray)obj;
		Object[] others = another.get();
		
		if(objs == others)
			return true;
		
		if(objs == null || others == null)
			return false;

		int n = objs.length; 
		if(n != others.length) 
			return false;
		
		for(int i = 0; i < n; i++){
			Object o = objs[i];
			Object other = others[i];

			if(o != other){
				if(o == null || other == null) 
					return false;
				Object a1 = wrap(o);
				Object a2 = wrap(other);
				if(!a1.equals(a2)) return false;
			}
		}

		return true;
	}

	/**
	 * 
	 */
	public String toString(){
		if(str != null) 
			return str;
		
		if(objs == null)
			return "";

		StringBuffer buf = new StringBuffer(); 
		for(int i = 1; i < objs.length; i++){
			Object obj = objs[i];
			if(obj != null){
				buf.append(wrap(obj) + delimiter);
			}
		}
		str = buf.toString();
		return str;
	}

	/**
	 * 
	 */
	public int hashCode(){
		if(hashCode != 0) 
			return hashCode;

		int hash = 0;
		for(int i = 0; i < objs.length; i++){
			Object obj = objs[i];
			if(obj != null){
				String str = obj.toString();
				int h = str.hashCode();
				hash += h;
			}
		}
		hashCode = hash;

		return hashCode;
	}

}
