package com.ztesoft.component.file.business;

import java.util.List;
import java.util.Map;

public interface TxtFileHandler {
	
	//��֤ÿ�м�¼����processFile����
	public boolean validate(Map aRecord) ;
	
	//����ListΪÿ�м�¼������,listΪHandlerMsg����
	public List processFile(List datas , Map param ) ;

}
