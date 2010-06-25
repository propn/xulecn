package org.leixu.iap.core.fastm.html.paging;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * @author hailong.wang
 *
 */
public class Paginator {
	static final Integer ONE = new Integer(1);

	private int maxShow = 5; //at most show 5 + 5 = 10 page numbers
	public void setMaxShow(int maxShow){
		this.maxShow = maxShow;
	}
	
	private int pages;
	private IPageLink pageLink;

	public int getPages() {
		return pages;
	}

	public void calcPages(int count, int pageSize) {
		pages = count / pageSize;
		if(count % pageSize != 0) pages++;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setPageLink(IPageLink pageLink) {
		this.pageLink = pageLink;
	}

	protected Map makeLink(int pageIndex){
		if(pageLink == null)
			return Collections.EMPTY_MAP;
		
		return pageLink.makeLink(pageIndex);
	}

	/**
	 * 
	 * @param valueSet
	 * @param nPages
	 * @param pageIndex
	 * @param pageLink
	 */
	public Map calcPageLinks(int pageIndex){
		if(pages == 0)
			return null;

		Map model = new HashMap();

		int deduct = maxShow - 1;
		int maxShow_2 = maxShow + maxShow; 

		int loopStart = 1;
		int loopEnd = pages;

		if(pages > maxShow_2 + 1){
			int startLimit = pages - maxShow_2;
			int left = pageIndex - maxShow;
			if(left <= 0) left = 1;
			if(startLimit >= left) startLimit = left;

			if(pageIndex > startLimit){
				loopStart = startLimit;
			}else if(pageIndex > maxShow + 2){
				loopStart = pageIndex - deduct;
			}

			if(pageIndex <= maxShow){
				loopEnd = deduct + deduct + 1;  
			}else { // maxShow ..
				loopEnd = pageIndex + deduct;
				if(loopEnd >= pages - 2) loopEnd = pages;
			}
		}

		List links = new ArrayList();
		model.put("link", links);
		model.put("pages", new Integer(pages));

		if(loopStart > 1){
			Map link = makeLink(1);
			link.put("notCurrent", link);
			links.add(link);

			int i = loopStart - (loopStart - 1) / 2;
			link = makeLink(i);			
			link.put("mid", link);
			links.add(link);
		}

		int prev = pageIndex - 1;
		int next = pageIndex + 1;

		for(int i = loopStart; i <= loopEnd; i++){
			Map link = makeLink(i);			
			links.add(link);

			if(i == pageIndex){
				link.put("current", link);
				continue;
			}

			if(i == prev) {
				link.put("prev", link);
				continue;
			}
			
			if(i == next){
				link.put("next", link);
				continue;
			}

			link.put("notCurrent", link);
		}

		if(loopEnd < pages){
			int i = loopEnd + (pages - loopEnd) / 2;
			Map link = makeLink(i);
			link.put("mid", link);
			links.add(link);

			link = makeLink(pages);
			link.put("notCurrent", link);
			links.add(link);
		}
		
		return model;
	}
}
