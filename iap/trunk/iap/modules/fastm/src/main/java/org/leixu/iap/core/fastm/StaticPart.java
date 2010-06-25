/*
 * Fastm
 */
package org.leixu.iap.core.fastm;

import java.io.PrintWriter;

/**
 * @author hailong.wang
 *
 */
public class StaticPart implements ITemplate {
	/**
	 * the static HTML, WML, XML text
	 */
	String str = null;
		
	public StaticPart(String str) {
		this.str = str;
	}
		
	/**
	 * implements ITemplate
	 * just return the static HTML, WML, XML text
	 */
	public String toString(Object obj) {
		return str;
	}

    /**
     *
     * @param obj
     * @param valueInterceptor
     * @return
     */
    public String toString(Object obj, IValueInterceptor valueInterceptor) {
        return str;
    }

	/**
	 * 
	 * @param obj
	 * @param writer
	 */
	public void write(Object obj, PrintWriter writer){
		if(obj == null) return;
		writer.write(str);
	}

    /**
     * 
     * @param obj
     * @param writer
     * @param valueInterceptor
     */
    public void write(Object obj, PrintWriter writer, IValueInterceptor valueInterceptor){
        write(obj, writer);
    }

	/**
	 * override Object.toString() 
	 */
	public String toString(){
		return str; 
	}

	/**
	 * return a String to represent this node 
	 */
	public String structure(int level){
		StringBuffer buf = new StringBuffer();
		
		for(int i = 0; i < level; i++) buf.append(" ");
		buf.append("Static \n");

		return buf.toString();
	}
}
