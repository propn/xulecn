
package org.leixu.iap.core.fastm.samples.paging;

import java.util.HashMap;
import java.util.Map;

import org.leixu.iap.core.fastm.html.paging.IPageLink;

/**
 * @author hailong.wang
 *
 */
public class SamplePageLink implements IPageLink {
	public Map makeLink(int pageNo) {
		Map link = new HashMap();
		link.put("link", pageNo + ".html");
		link.put("pageIndex", new Integer(pageNo));
		return link;
	}
}
