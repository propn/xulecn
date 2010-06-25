package org.leixu.iap.core.fastm;

import java.io.PrintWriter;

/**
 * <p>Title: Fast Template</p>
 * <p>Description: Fast Template For XML File (Using XML Comment as Tag)</p>
 * @author Wang Hailong
 * @version 1.0
 */

public interface ITemplate{
    /**
     * generate the result String according to IValueSet Data Tree
     *
     * @param obj
     * @return String the result String
     */
    public String toString(Object obj);

    /**
     *
     * @param obj
     * @param valueInterceptor
     * @return
     */
    public String toString(Object obj, IValueInterceptor interceptor);

    /**
     * 
     * @param obj
     * @param writer
     */
	public void write(Object obj, PrintWriter writer);

    /**
     *
     * @param obj
     * @param writer
     * @param valueInterceptor
     */
    public void write(Object obj, PrintWriter writer, IValueInterceptor interceptor);

    /**
     * for debug. show the structure
     *
     * @param level int
     * @return String
     */
    public String structure(int level);
}
