package com.ztesoft.uiext.graphic.jfreechart.bo;

import java.util.Map;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceGraphicVO;

public interface IProduceGraphicBO {

	/**
	 * ����ͼƬ����
	 * 
	 * @param title
	 * @param condition
	 * @return
	 * @throws ImonBussinessSystemException
	 */
	ProduceGraphicVO produce(String title, Map chartParams, Map paramMap)
			throws java.lang.RuntimeException;

}
