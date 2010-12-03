package com.ztesoft.vsop.engine.service;

import java.util.Map;

public interface IAccess {
	public  Map concreteInOpertion(Map in) throws Exception;
	public  Map concreteOutOpertion(Map in) throws Exception;
}
