package com.ztesoft.crm.salesres.common;

import java.lang.reflect.Method;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.dao.QueryFilterFactory;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.PageModel;

/**
 * 
 * @Classname : PageManager
 * @Description : ��ҳ��������
 * @Copyright ? 2006 ZTEsoft.
 * @Author : fjy
 * @Create Date : 2006-3-30
 * 
 * @Last Modified :
 * @Modified by :
 * @Version : 1.0
 */
public class PageHelperFlx {

	/**
	 * ��DAO�и��ݲ�ѯ�������ͷ�ҳ�����û�ȡ��ǰ�ķ�ҳ��PageModel�������������б�
	 * 
	 * @param dao
	 * @param whereCond
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static PageModel popupPageModel(DAO dao, String whereCond,
			String countFuncName, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();

		// ���� totalCount
		
		int totalCount = 0;
		long totalSum = 0;
		if(countFuncName!=null&&countFuncName.trim().length()>0){
			Object[] args = new Object[]{whereCond};
			Object rtnObj = null;
			try{
			   rtnObj = PageHelperFlx.invokeMethod(dao, countFuncName, args);
			}catch(Exception e){
				throw new RuntimeException(e);
			}
			if(rtnObj!=null){
				long[] arr = (long[])rtnObj;
				if(arr.length>0){
					totalCount = (int)arr[0];
					if(arr.length>1){
						totalSum = arr[1];
					}
				}
			}
		}else{
			totalCount = new Long(dao.countByCond(whereCond)).intValue(); // long
		}
																			// int
		pageModel.setTotalCount(totalCount);
		//pageModel.setTotalSum(totalSum);

		// �ռ�¼�Ĵ���
		if (totalCount == 0) {
			return new PageModel();
		}
		// pageCount
		if (totalCount % pageSize > 0) {
			pageModel.setPageCount(totalCount / pageSize + 1);
		} else {
			pageModel.setPageCount(totalCount / pageSize);
		}

		// �߽�Ĵ���
		if (pageIndex < 0) {
			pageModel.setPageIndex(1);
		} else if (pageIndex > pageModel.getPageCount()) {
			pageModel.setPageIndex(pageModel.getPageCount());
		} else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}

		if (pageSize > CrmConstants.MAX_PAGE_SIZE) {
			// throw new CommonException(new
			// CommonError(CommonError.PAGE_TOO_LARGE_ERROR));
		}

		// ͨ��QueryFilter��DAO�й��˳���ǰ�ķ�ҳ��Ϣ��
		// pageModel����Ϣֱ�����������pageModel�л�ȡ��
		QueryFilter queryFilter = QueryFilterFactory
				.getPageQueryFilter(pageModel);
		dao.findByCond(whereCond, queryFilter);

		return pageModel;
	}

	public static Object invokeMethod(Object obj, String methodName,
			Object[] args) throws Exception {
		Object result = null;
		if (obj == null || methodName == null || methodName.trim().length() < 1)
			return result;
		Class objClass = obj.getClass();
		Class[] argsClass = null;
		if (args != null && args.length > 0) {
			argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				if (args[i] != null)
					argsClass[i] = args[i].getClass();
			}
		}
		Method method = objClass.getDeclaredMethod(methodName, argsClass);
		method.setAccessible(true);
		result = method.invoke(obj, args);
		return result;
	}

}
