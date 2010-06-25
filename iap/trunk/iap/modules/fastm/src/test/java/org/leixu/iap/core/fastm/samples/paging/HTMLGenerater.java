package org.leixu.iap.core.fastm.samples.paging;

import java.io.File;

import org.leixu.iap.core.fastm.ITemplate;
import org.leixu.iap.core.fastm.Parser;
import org.leixu.iap.core.fastm.html.paging.Paginator;
import org.leixu.iap.core.fastm.samples.Common;

/**
 * @author hailong.wang
 *
 */
public class HTMLGenerater {
	public static void main(String[] args) throws Exception{
		ITemplate template = Parser.parse("template/paging/paging.html");

		int[] pageTotals = {10, 25, 60}; 

		Paginator paginator = new Paginator();
		paginator.setPageLink(new SamplePageLink());
		int n = pageTotals.length;
		for(int i = 0; i < n; i++){
			int pages = pageTotals[i];
			paginator.setPages(pages);
			
			String dirName = "template/paging/html/" + pages + "pages";
			File dir = new File(dirName);
			dir.mkdirs();

			for(int pageNo = 1; pageNo <= pages; pageNo++ ){
				Object model = paginator.calcPageLinks(pageNo);
				String file = dirName + "/" + pageNo + ".html";
				Common.write(template, model, file);
			}
		}
	}
}
