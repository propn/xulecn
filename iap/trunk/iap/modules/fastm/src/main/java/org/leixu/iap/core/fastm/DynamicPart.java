package org.leixu.iap.core.fastm;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array; 


/**
 * <p>Title: Fast Template</p>
 * <p>Description: Fast Template For XML File (Using XML Comment as Tag)</p>
 * @author Wang Hailong
 * @version 1.0
 */

public class DynamicPart implements ITemplate {
    /**
     * 
     */
    static final Logger log = Logger.getLogger(DynamicPart.class.getName());

    /**
     * 
     */
    String name = null;

    /**
     * parts contained in this DynamicPart
     */
    List steps = null;

    /**
     * constructor
     *
     * @param name String
     */
    public DynamicPart(String name) {
		setName(name);
    }

    /**
     *
     * @return String
     */
    public String getName() {
        return name;
    }

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

    /**
     * get parts contained in this DynamicPart
     *
     * @return List parts contained in this DynamicPart
     */
    public List getSteps() {
        return steps;
    }

    /**
     * set parts to be contained in this DynamicPart
     *
     */
    public void setSteps(List steps) {
        this.steps = steps;
    }

    /**
     * add a part to the parts contained in this DynamicPart
     *
     * @param step ITemplate
     */
    public void addStep(ITemplate step) {
        if (steps == null) {
            steps = new ArrayList();
        }

        steps.add(step);
    }

    /**
     *
     * @param obj
     * @param writer
     */
    public void write(Object obj, PrintWriter writer){
        write(obj, writer, DefaultInterceptor.instance);
    }

    /**
     *
     * @param obj
     * @param writer
     * @param valueInterceptor
     */
	public void write(Object obj, PrintWriter writer, IValueInterceptor interceptor){
		if (obj == null) 
			return;

		boolean isIterator = false;

		if(obj.getClass().isArray()){
			// we may need to show this DynamicPart several times
			int n = Array.getLength(obj);
			for (int i = 0; i < n; i++) {
				Object o = Array.get(obj, i);
				stepDown(o, writer, interceptor);
			}
		}else if(obj instanceof Collection || (isIterator = obj instanceof Iterator)){
			Iterator iterator = isIterator ? (Iterator)obj : ((Collection)obj).iterator();
			for(;iterator.hasNext();){
				Object o = iterator.next();
				stepDown(o, writer, interceptor);
			}
		}else if(obj instanceof ResultSet){
			ResultSet rs = (ResultSet)obj;
			try{
				while(rs.next()){
					stepDown(rs, writer, interceptor);
				}
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}else if(obj instanceof ITemplate){
			// replace the whole dyn part with the Obj string
			writer.write(obj.toString());
		}else{
			stepDown(obj, writer, interceptor);
		} // end if - else - else
	} // end of the method

	/**
	 * 
	 * @param obj
	 * @param writer
	 */
	public void stepDown(Object obj, PrintWriter writer, IValueInterceptor interceptor){
		if(interceptor == null) interceptor = DefaultInterceptor.instance;

		// hold all parts together to make a result string
		if(steps == null) return;
		int nSteps = steps.size();

		// depth-first iteration, add up all parts together to make a result string
		for (int i = 0; i < nSteps; i++) {
			ITemplate step = (ITemplate) steps.get(i);

			if (step instanceof DynamicPart) {
				DynamicPart dynPart = (DynamicPart)step;
				String dynName = dynPart.getName();
				
				Object branch = null;
				branch = interceptor.getProperty(obj, dynName);

				dynPart.write(branch, writer, interceptor);
				continue;
			}

			// StaticPart or VariablePart or IgnoredPart
			step.write(obj, writer, interceptor);
		}// end for
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
     * @param interceptor
     * @return
     */
    public String toString(Object obj, IValueInterceptor interceptor){
        StringWriter strWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(strWriter);

        write(obj, printer, interceptor);
        return strWriter.toString();
    }

	/**
	 * output the template itself
	 */
	public String toString() {
		if (steps == null) return "";

		// hold all parts together to make a result string
		StringBuffer buf = new StringBuffer();

		int nSteps = steps.size();

		// depth-first iteration, add up all parts together to make a result string
		for (int i = 0; i < nSteps; i++) {
			ITemplate step = (ITemplate) steps.get(i);

			if (step instanceof DynamicPart) {
				DynamicPart dynPart = (DynamicPart)step;
				String dynName = dynPart.getName();
				buf.append("<!-- BEGIN DYNAMIC: " + dynName + " -->\n");
				buf.append(dynPart);
				buf.append("<!-- END DYNAMIC: " + dynName + " -->\n");
			}
			else { // StaticPart or VariablePart or IgnoredPart
				buf.append(step);
			}
		}

		return buf.toString();
	}

    /**
     *
     * @param level int
     * @return String
     */
    public String structure(int level) {
        StringBuffer buf = new StringBuffer();

        for(int i = 0; i < level; i++) buf.append(" ");
        buf.append("Dynamic: " + name + "\n");
        level++;

        int nSteps = steps.size();
        for (int i = 0; i < nSteps; i++) {
            ITemplate step = (ITemplate) steps.get(i);

            buf.append(step.structure(level));
        }

        return buf.toString();
    }

    /**
     *
     * @return List
     */
    public List getDynamicChildren() {
        if(steps == null)
        	return Collections.EMPTY_LIST;

        List dynChildren = new ArrayList();
        int nSteps = steps.size();

        for (int i = 0; i < nSteps; i++) {
            Object obj = steps.get(i);

            if (obj instanceof DynamicPart) {
                dynChildren.add(obj);
            }
        }

        return dynChildren;
    }

	/**
	 *
	 * @return List
	 */
	public List getVariables() {
		if(steps == null)
			return null;

		int nSteps = steps.size();
		List variables = new ArrayList();

		for (int i = 0; i < nSteps; i++) {
			Object obj = steps.get(i);

			if (obj instanceof VariablePart) {
				variables.add(obj);
			}
		}

		return variables;
	}

    /**
     * find the named dynamic part width-first
     * 
     * @param obj
     * @return DynamicPart
     */
    public DynamicPart findWidthFirst(Object obj) {
    	if(obj == null) 
    		return null;
        List dynChildren = getDynamicChildren();

        List queue = new LinkedList();
        queue.addAll(dynChildren);

        while (queue.size() > 0) {
            DynamicPart dynPart = (DynamicPart) queue.remove(0);

            if (obj.equals(dynPart.getName())) {
                return dynPart;
            }
            else {
                queue.addAll(dynPart.getDynamicChildren());
            }
        }

        return null;
    }

    /**
     * find the named dynamic part depth-first
     * 
     * @param obj
     * @return DynamicPart
     */
    public DynamicPart findDepthFirst(Object obj) {
		if(obj == null) return null;

        List dynChildren = getDynamicChildren();
        int nChildren = dynChildren.size();

        for(int i = 0; i < nChildren; i++){
            DynamicPart dynPart = (DynamicPart)dynChildren.get(i);

            if (obj.equals(dynPart.getName())){
                return dynPart;
            }else{
                // recursive
                dynPart = dynPart.findDepthFirst(obj);
                
                if(dynPart != null){
                    return dynPart;
                }
            }
        }

        return null;
    }

    /**
     * recursively called
     * return a new DynamicPart with node replaced by the parameter 
     * 
     * @param name
     * @return
     */
    public ITemplate replaceNode(String name, ITemplate newTemplate){
        if (steps == null) return null;
        if(this.name.compareTo(name) == 0) return newTemplate; 
        if(findDepthFirst(name) == null) return this;

        DynamicPart newDyn = new DynamicPart(this.name);

        int nSteps = steps.size();
        for (int i = 0; i < nSteps; i++) {
            ITemplate step = (ITemplate)steps.get(i);

            if (step instanceof DynamicPart) {
                DynamicPart dyn = (DynamicPart)step;
                step = dyn.replaceNode(name, newTemplate);
            }

            newDyn.addStep(step);
        }

        return newDyn;
    }

	/**
	 * 
	 * @param model
	 * @param includeNames
	 * @param prefix
	 * @return
	 */
	public Map getIncludes(Object model, String[] includeNames, String prefix){
		Map includes = new HashMap();
		for(int i = 0; i < includeNames.length; i++){
			String includeName = includeNames[i];
			DynamicPart part = findWidthFirst(prefix + includeName);
			String str = part.toString(model);
			StaticPart text = new StaticPart(str);
			includes.put(includeName, text);
		}
		return includes;
	}
}

