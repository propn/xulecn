package org.leixu.iap.core.fastm.samples.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.leixu.iap.core.fastm.DynamicPart;
import org.leixu.iap.core.fastm.Parser;
import org.leixu.iap.core.fastm.StaticPart;
import org.leixu.iap.core.fastm.samples.Common;

public class TreeDemo {

	public static List makeNodes() {
		List nodeList = new ArrayList();

		Map a = new HashMap();
		a.put("name", "A");
		List aChildren = new ArrayList();
		a.put("children", aChildren);
		{
			Map a1 = new HashMap();
			a1.put("name", "A1");
			List a1Children = new ArrayList();
			a1.put("children", a1Children);
			{
				Map a11 = new HashMap();
				a11.put("name", "A1-1");
				a1Children.add(a11);

				Map a12 = new HashMap();
				a12.put("name", "A1-2");
				a1Children.add(a12);
			}
			aChildren.add(a1);
		}
		nodeList.add(a);

		Map b = new HashMap();
		b.put("name", "B");
		List bChildren = new ArrayList();
		b.put("children", bChildren);
		{
			Map b1 = new HashMap();
			b1.put("name", "B1");
			bChildren.add(b1);
		}
		nodeList.add(b);

		Map c = new HashMap();
		c.put("name", "C");
		nodeList.add(c);

		return nodeList;
	}

	public static void write(String srcFile, String destFile) throws Exception {
		List nodes = makeNodes();
		DynamicPart template = (DynamicPart) Parser.parse(srcFile);
		DynamicPart nodePart = template.findWidthFirst("node");

		TreeTemplate treeTemplate = new TreeTemplate();
		String nodesString = treeTemplate.toString(nodePart, nodes);

		Map model = new HashMap();
		model.put("node", new StaticPart(nodesString));

		Common.write(template, model, destFile);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		write("template/tree/tree.html", "template/tree/tree.m.html");
		write("template/tree/tree.xml", "template/tree/tree.m.xml");
	}
}
