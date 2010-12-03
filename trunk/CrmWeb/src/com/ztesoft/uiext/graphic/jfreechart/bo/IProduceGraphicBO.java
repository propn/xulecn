package com.ztesoft.uiext.graphic.jfreechart.bo;

import java.util.Map;

import com.ztesoft.uiext.graphic.jfreechart.vo.ProduceGraphicVO;

public interface IProduceGraphicBO {

	/**
	 * 产生图片的类
	 * 
	 * @param title
	 * @param condition
	 * @return
	 * @throws ImonBussinessSystemException
	 */
	ProduceGraphicVO produce(String title, Map chartParams, Map paramMap)
			throws java.lang.RuntimeException;

}
