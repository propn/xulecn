package com.ztesoft.component.common.staticdata.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.DataUtil;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.common.staticdata.dao.DcSqlDAO;
import com.ztesoft.component.common.staticdata.dao.StaticAttrDAO;
import com.ztesoft.component.common.staticdata.dao.StaticAttrDAOFactory;

public class StaticAttrBean extends DictAction{
	
	private static StaticAttrBean bean = new StaticAttrBean();

	public static StaticAttrBean getInstance(){
		return bean;
	}
	public ArrayList getStaticAttr(DynamicDict dto ) throws Exception {

		String attrCode = (String)dto.getValueByName("parameter") ;
		List attrList = new ArrayList();
		StaticAttrDAO sd = StaticAttrDAOFactory.getInstance().getStaticAttrDAO();
		attrList = sd.findByCode(attrCode);

		return (ArrayList) attrList;
	}

	public ArrayList getAllStaticAttr(DynamicDict dto) throws Exception {

		StaticAttrDAO staticAttrDAO = StaticAttrDAOFactory.getInstance()
				.getStaticAttrDAO();
		ArrayList attrList = staticAttrDAO.findAllStaticAttr();
		dto.setValueByName("result", attrList) ;
		return attrList;
	}
	
	/**
	 * 加载静态数据配置信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel listDataOfDcSql(DynamicDict dto) throws Exception {
		Map m = Const.getParam(dto) ;
		String dcName = Const.getStrValue(m, "dcName");
		int pi = Const.getPageIndex(m) ;
		int ps = Const.getPageSize(m) ;
		
		StringBuffer whereCond = new StringBuffer() ;
		List param = new ArrayList() ;
		
		if(!DataUtil.ifEmpty(dcName)){
			whereCond.append(" and dc_name = ?") ;
			param.add(dcName.trim()) ;
		}
		
		return new DcSqlDAO().searchByCond(whereCond.toString(), param, pi, ps) ;
	}

}

