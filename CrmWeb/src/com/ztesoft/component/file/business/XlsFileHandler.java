package com.ztesoft.component.file.business;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

public interface XlsFileHandler {

	//��֤ÿ�м�¼����processFile����
	public boolean validate(Sheet sheet);

	//����ListΪÿ�м�¼������,listΪHandlerMsg����
	public List processFile(Sheet sheet, Map param);

}
