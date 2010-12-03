package com.ztesoft.component.file.business;

import java.util.List;
import java.util.Map;

public interface TxtFileHandler {
	
	//验证每行记录，供processFile调用
	public boolean validate(Map aRecord) ;
	
	//返回List为每行记录处理结果,list为HandlerMsg集合
	public List processFile(List datas , Map param ) ;

}
