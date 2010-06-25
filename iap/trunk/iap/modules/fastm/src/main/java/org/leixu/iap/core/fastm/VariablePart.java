/*
 * Fastm
 */
package org.leixu.iap.core.fastm;

import java.io.PrintWriter;


/**
 * @author hailong.wang
 *
 */
public class VariablePart implements ITemplate {
	/**
	 * variable name
	 */
	private String name; // without {}
	public String getName(){
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/** constructor **/
	public VariablePart(String name) {
		setName(name);
	}

    /**
     *
     * @param obj
     * @return
     */
    public String toString(Object obj){
        return toString(obj, DefaultInterceptor.instance);
    }

    /**
     *
     * @param obj
     * @param valueInterceptor
     * @return
     */
	public String toString(Object obj, IValueInterceptor valueInterceptor){
		if(valueInterceptor == null) valueInterceptor = DefaultInterceptor.instance;
		
		if(obj == null) return name;
		if(obj instanceof Object[]){
			Object[] a = (Object[])obj;
			if(a.length > 0) obj = a[0];
		}

		Object value = valueInterceptor.getProperty(obj, name);
		if(value == null) 
			return this.toString();

		return value.toString();
	}

    /**
     *
     * @param obj
     * @param writer
     */
	public void write(Object obj, PrintWriter writer){
        write(obj, writer, DefaultInterceptor.instance);
	}

    public void write(Object obj, PrintWriter writer, IValueInterceptor valueInterceptor){
        writer.write(toString(obj, valueInterceptor));
    }

	/**
	 * override Object.toString
	 */
	public String toString(){return "{" + name + "}";}

	/**
	 * return a String to represent this node 
	 */
	public String structure(int level){
		StringBuffer buf = new StringBuffer();

		for(int i = 0; i < level; i++) buf.append(" ");
		buf.append("Variable: " + name + "\n");

		return buf.toString();
	}
}
