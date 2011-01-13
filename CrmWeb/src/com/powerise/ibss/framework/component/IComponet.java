package com.powerise.ibss.framework.component;

import com.powerise.ibss.framework.DynamicDict;

public interface IComponet {
	public DynamicDict execute(IComponet component ,DynamicDict dto) throws Exception  ;

}
