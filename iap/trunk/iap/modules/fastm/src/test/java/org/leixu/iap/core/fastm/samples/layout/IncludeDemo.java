package org.leixu.iap.core.fastm.samples.layout;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.leixu.iap.core.fastm.DynamicPart;
import org.leixu.iap.core.fastm.ITemplate;
import org.leixu.iap.core.fastm.Parser;
import org.leixu.iap.core.fastm.samples.Common;

public class IncludeDemo  extends TestCase{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Map model = new HashMap();

		model.put("message", "my message1");
		model.put("content", "my content1");
		include(model);
		Common.write("template/layout/page1.html", model, "template/layout/page1.m.html");

		model.put("message", "my message2");
		model.put("content", "my content2");
		include(model);
		Common.write("template/layout/page2.html", model, "template/layout/page2.m.html");
	}

	static final String[] includeNames = {"header", "footer"};

	public static void include(Map model) throws Exception{
		ITemplate standard = Parser.parse("template/layout/standard.html");
		DynamicPart dyn = (DynamicPart)standard;
		Map include = dyn.getIncludes(model, includeNames, "include.");
		model.put("include", include);
	}
	
	/**
     * Rigourous Test :-)
     */
    public void testApp()throws Exception
    {
    	Map model = new HashMap();

		model.put("message", "my message1");
		model.put("content", "my content1");
		include(model);
		Common.write("template/layout/page1.html", model, "template/layout/page1.m.html");

		model.put("message", "my message2");
		model.put("content", "my content2");
		include(model);
		Common.write("template/layout/page2.html", model, "template/layout/page2.m.html");
        assertTrue( true );
    }
	
}
