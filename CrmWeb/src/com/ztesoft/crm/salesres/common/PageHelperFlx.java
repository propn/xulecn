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
 * @Description : 分页管理工具类
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
	 * 从DAO中根据查询条件，和分页的设置获取当前的分页的PageModel（包含了数据列表）
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

		// 计算 totalCount
		
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

		// 空记录的处理
		if (totalCount == 0) {
			return new PageModel();
		}
		// pageCount
		if (totalCount % pageSize > 0) {
			pageModel.setPageCount(totalCount / pageSize + 1);
		} else {
			pageModel.setPageCount(totalCount / pageSize);
		}

		// 边界的处理
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

		// 通过QueryFilter在DAO中过滤出当前的分页信息。
		// pageModel的信息直接在输入参数pageModel中获取。
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
