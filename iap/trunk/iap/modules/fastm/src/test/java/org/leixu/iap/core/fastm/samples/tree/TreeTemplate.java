package org.leixu.iap.core.fastm.samples.tree;

import java.util.List;
import java.util.Map;

import org.leixu.iap.core.fastm.DynamicPart;
import org.leixu.iap.core.fastm.StaticPart;

public class TreeTemplate {

	public String toString(DynamicPart nodeTemplate, Object model) {
		if (model == null)
			return null;

		if (model instanceof Map) {
			Map node = (Map) model;
			String nodeName = nodeTemplate.getName();

			String childrenString = toString(nodeTemplate, node.get("children"));
			if (childrenString != null) {
				node.put(nodeName, new StaticPart(childrenString));
				node.put("hasChildren", node);
			}

			return nodeTemplate.toString(node);
		} else if (model instanceof List) {
			List children = (List) model;
			int n = children.size();
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < n; i++) {
				Map map = (Map) children.get(i);
				String child = toString(nodeTemplate, map);
				buf.append(child);
			}
			return buf.toString();
		}
		return "";
	}
}
