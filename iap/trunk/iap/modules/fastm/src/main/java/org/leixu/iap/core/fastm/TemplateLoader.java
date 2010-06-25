/**
 * Fastm -- fast template
 */
package org.leixu.iap.core.fastm;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import org.leixu.iap.core.util.map.ReadWriteMap;

/**
 * @author hailong.wang
 */

public class TemplateLoader implements ITemplate {
	final Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * the file to load
     */
    private String fileName;

    private String charsetName;
    
    /**
     * the file's last modified time
     */
    private volatile long fileTime;

    /**
     * the parsed result template
     */
    private volatile ITemplate template;

    /**
     * the template file path
     * @return String
     */
    public String getFileName(){
        return fileName;
    }

	public String getCharsetName() {
		return charsetName;
	}

	/**
     * load the template. record the file timestamp.
     *
     * @param fileName String
     * @throws IOException
     */
    public TemplateLoader(String fileName) throws IOException {
    	this(fileName, "utf-8");
    }

    /**
     * 
     * @param fileName
     * @param charsetName
     * @throws IOException
     */
    public TemplateLoader(String fileName, String charsetName) throws IOException {
        this.fileName = fileName;
        this.charsetName = charsetName;
        
        forceReload();
    }
    
    /**
     * each time a template is required,
     * the template loader check the file timestamp,
     * if the file timestamp is changed, the template loader reload the template.
     * if not return the current template
     *
     * @throws IOException
     * @return ITemplate
     */
    public ITemplate getTemplate(){
    	return template;
    }

	/**
	 * 
	 * @return
	 */
    public boolean fileChanged(){
		long theFiletime = new File(fileName).lastModified();
		return fileTime != theFiletime;
    }
    
    /**
     * 
     * @throws IOException
     */
    public void forceReload() throws IOException{
		template = Parser.parse(fileName, charsetName);
		fileTime = new File(fileName).lastModified();
		
		tagCache.clear();
    }

	/**
	 * 
	 * @throws IOException
	 */
    public void checkReload() throws IOException{
    	if(fileChanged())forceReload();    		
    }

    /**
     * implements ITemplate
     */
    public String toString(Object obj){
   		return getTemplate().toString(obj);
    }

    /**
     *
     * @param obj
     * @param valueInterceptor
     * @return
     */
    public String toString(Object obj, IValueInterceptor valueInterceptor){
   		return getTemplate().toString(obj, valueInterceptor);
    }

	/**
	 * implements ITemplate
	 */
	public void write(Object obj, PrintWriter writer){
		getTemplate().write(obj, writer);
	}

    /**
     * 
     * @param obj
     * @param writer
     * @param valueInterceptor
     */
    public void write(Object obj, PrintWriter writer, IValueInterceptor valueInterceptor){
        getTemplate().write(obj, writer, valueInterceptor);
    }

	/**
	 * 
	 * @param level
	 * @return
	 */    
    public String structure(int level){
		return getTemplate().structure(level);
    }

	public final Map tagCache = new ReadWriteMap();
	
	public DynamicPart getDynamicPart(String name){
		DynamicPart cached = (DynamicPart)tagCache.get(name);
		if(cached != null)
			return cached;

		DynamicPart dyn = (DynamicPart)getTemplate();
		DynamicPart target = dyn.findWidthFirst(name);

		if(target != null){
			tagCache.put(name, target);
			return target;
		}
		
		return null;
	}
}
