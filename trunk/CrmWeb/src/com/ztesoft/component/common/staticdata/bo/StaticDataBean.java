/**
 * Classname    : StaticDataBean
 * Description  : ȡ��̬����
 * Author       : cwf
 * Date         : 2004-06-01
 *
 * Last Update  : 2004-06-02
 * Author       : cwf
 * Version      : 1.0
 */

package com.ztesoft.component.common.staticdata.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.component.common.staticdata.dao.StaticAttrDAOFactory;
import com.ztesoft.component.common.staticdata.dao.StaticDataDAO;
import com.ztesoft.component.common.staticdata.vo.DcSqlVO;
import com.ztesoft.component.common.staticdata.vo.StaticAttrListVO;

public class StaticDataBean extends DictAction{

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(StaticDataBean.class);
	
	private static StaticDataBean bean = new StaticDataBean();
	
	public static StaticDataBean getInstance(){
		return bean ;
	}
	
	public String getSql(DynamicDict dto) throws Exception{
		String dcName = (String)dto.getValueByName("parameter") ;
		StaticDataDAO staticDataDAO = StaticAttrDAOFactory.getInstance()
				.getStaticDataDAO();
		staticDataDAO.setDto(dto);
		String dcSql = staticDataDAO.getSql(dcName);
		return dcSql;
	}

	public ArrayList getStaticData(DynamicDict dto)throws Exception  {
		String staticDataSql = (String)dto.getValueByName("parameter") ;
		StaticDataDAO staticDataDAO = StaticAttrDAOFactory.getInstance()
				.getStaticDataDAO();
		staticDataDAO.setDto(dto);
		return staticDataDAO.getStaticData(staticDataSql);
	}

	public List getStaticDataByCode(DynamicDict dto) throws Exception{
		String dcName = (String)dto.getValueByName("parameter") ;
		List staticList = null;

		// ������Ҫ��bsn�������ȡ
//		staticList = checkBsnStaticData(dcName);
//		if (staticList == null) {
		dto.setValueByName("parameter", getSql(dto)) ;
		staticList = getStaticData(dto);
//		}

		return staticList;
	}

	public ArrayList getAllStaticData(DynamicDict dto) throws Exception {
		StaticDataDAO staticDataDAO = StaticAttrDAOFactory.getInstance()
				.getStaticDataDAO();
		staticDataDAO.setDto(dto) ;
		ArrayList sqlList = staticDataDAO.getAllSql();
		ArrayList dataList = new ArrayList();
		DcSqlVO sqlVO = null;
		String attrCode = null;
		List staticList = null;

		for (Iterator iter = sqlList.iterator(); iter.hasNext();) {
			sqlVO = (DcSqlVO) iter.next();
			attrCode = sqlVO.getDcName();
			
			try {
				// ������Ҫ��bsn�������ȡ
				staticList = checkBsnStaticData(attrCode);
				if (staticList == null && !sqlVO.getDcSql().equals("")) {
					//StaticData
					staticList = staticDataDAO.getStaticData(sqlVO.getDcSql());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("getAllStaticData fail in attrCode: "
						+ attrCode);
				continue;
			}

			StaticAttrListVO staticAttrListVO = new StaticAttrListVO();
			staticAttrListVO.setAttrCode(sqlVO.getDcName());
			staticAttrListVO.setDataList((ArrayList) staticList);	
			
			logger.debug("[ attrCode = "+sqlVO.getDcName()+" ][ size = "+staticList.size()+" ]");
			
			dataList.add(staticAttrListVO);
		}

		return dataList;
	}

	/**
	 * ��BSN����Դ��ȡ��̬����:
	 * 
	 * ��ͬ��attrCode������������£�
	 * 
	 * ʹ��ԭʼVO���͵�attrCode
	 * DC_ACCT_ITEM_GROUP
	 * DC_BILL_FORMAT
	 * DC_BILLING_CYCLE
	 * DC_BILL_REQUEMENT
	 * 
	 * ����ʹ��AttrVO����ת����attrCode
	 * DC_ACCT_ITEM_GROUP_DC
	 * DC_BANK_BRANCH
	 * DC_BANK
	 * DC_BILL_FORMAT_DC
	 * DC_BILLING_CYCLE_TYPE
	 * 
	 * 
	 * DC_ACCT_ITEM_GROUP  -- ԭʼVO����List
	 * DC_ACCT_ITEM_GROUP_DC  -- AttrVO����List
	 * DC_BANK_BRANCH	   -- AttrVO����List
	 * DC_BANK           -- AttrVO����List
	 * DC_BILL_FORMAT    -- ԭʼVO����List
	 * DC_BILL_FORMAT_DC  -- AttrVO����List
	 * DC_BILLING_CYCLE   -- ԭʼVO����List
	 * DC_BILLING_CYCLE_TYPE -- AttrVO����List
	 * DC_BILL_REQUEMENT  -- ԭʼVO����List
	 * @param attrCode
	 * @return
	 */
	private List checkBsnStaticData(String attrCode) {

		List staticList = null;
	
		return staticList;

	}
	
	public String[] getSidFlag()  throws Exception{
		StaticDataDAO staticDataDAO =StaticAttrDAOFactory.getInstance().getStaticDataDAO();
		String[] ret  = staticDataDAO.getSidFlag();
		return ret;
	} 
	
	public void  setSidFlag(String remoteSidFlag,String localSidFlag,String maintinceOperId) throws Exception {
		StaticDataDAO staticDataDAO =StaticAttrDAOFactory.getInstance().getStaticDataDAO();		
		staticDataDAO.setSidFlag(remoteSidFlag,localSidFlag,maintinceOperId);
	} 


}
