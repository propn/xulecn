package org.leixu.iap.core.fastm;

import java.io.IOException;
import java.util.Map;

import org.leixu.iap.core.util.loader.ObjectLoader;
import org.leixu.iap.core.util.map.ReadWriteMap;

public class TemplateLocatoer implements ObjectLoader{
	private Map map = new ReadWriteMap();
	private ObjectLoader pathLocator;

	public TemplateLocatoer(ObjectLoader pathLocator) {
		this.pathLocator = pathLocator;
	}

	public ITemplate findTemplate(String templatePath) throws IOException{
		int tag = templatePath.indexOf("#");
		String filePath = templatePath;
		String tagName = null;
		if(tag > 0){
			filePath = templatePath.substring(0, tag);
			tagName = templatePath.substring(tag + 1); 
		}

		TemplateLoader templateLoader = (TemplateLoader)map.get(filePath);
		if(templateLoader == null){
			String fileName = (String)pathLocator.load(filePath);
			templateLoader = new TemplateLoader(fileName);
			map.put(filePath, templateLoader);
		}else {
			templateLoader.checkReload();
		}

		ITemplate ret = templateLoader;

		if(tagName != null){
			ITemplate target = templateLoader.getDynamicPart(tagName);
			if(target != null)
				ret = target;
		}

		return ret;
	}

	public Object load(Object path) {
		try {
			return findTemplate((String)path);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
