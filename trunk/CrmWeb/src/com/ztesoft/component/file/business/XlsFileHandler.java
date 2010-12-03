package com.ztesoft.component.file.business;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

public interface XlsFileHandler {

	//验证每行记录，供processFile调用
	public boolean validate(Sheet sheet);

	//返回List为每行记录处理结果,list为HandlerMsg集合
	public List processFile(Sheet sheet, Map param);

}
