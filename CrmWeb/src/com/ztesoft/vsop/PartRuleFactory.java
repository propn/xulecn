package com.ztesoft.vsop;

import java.util.HashMap;

import com.ztesoft.vsop.order.PartRuleinterface;

public class PartRuleFactory {
	static HashMap partRuleMapping = new HashMap();
	
	static {
		//²úÆ·¼øÈ¨
		partRuleMapping .put("1", new Rule1());
		partRuleMapping .put("2", new Rule2());
		partRuleMapping .put("3", new Rule3());
		partRuleMapping .put("4", new Rule4());
		partRuleMapping .put("5", new Rule5());
		partRuleMapping .put("6", new Rule6());
		partRuleMapping .put("7", new Rule7());
		partRuleMapping .put("8", new Rule8());
		partRuleMapping .put("9", new Rule9());
		partRuleMapping .put("10", new Rule10());
		partRuleMapping .put("11", new Rule11());
		partRuleMapping .put("12", new Rule12());
		
	}
	public HashMap match(String ruleType,HashMap map){
		PartRuleinterface partRule =(PartRuleinterface)partRuleMapping.get(ruleType);
		return partRule.authentication(map);
	}
}
