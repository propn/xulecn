package com.ztesoft.common.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class PageHelper {

	/**
	 * ��DAO�и��ݲ�ѯ�������ͷ�ҳ�����û�ȡ��ǰ�ķ�ҳ��PageModel�������������б�
	 * 
	 * @param dao
	 * @param whereCond
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static PageModel popupPageModel(DAO dao, String countCond, String whereCond, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();

		// ���� totalCount
		int totalCount = new Long(dao.countByCond(countCond)).intValue(); // long  to  int
		pageModel.setTotalCount(totalCount);

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
		} else if(pageIndex>pageModel.getPageCount()){
			pageModel.setPageIndex(pageModel.getPageCount());
		}else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}
		
		if(pageSize > CrmConstants.MAX_PAGE_SIZE){
			//throw new CommonException(new CommonError(CommonError.PAGE_TOO_LARGE_ERROR));
		}

		// ͨ��QueryFilter��DAO�й��˳���ǰ�ķ�ҳ��Ϣ��
		// pageModel����Ϣֱ�����������pageModel�л�ȡ��
		QueryFilter queryFilter = QueryFilterFactory.getPageQueryFilter(pageModel);
		dao.findByCond(whereCond, queryFilter);

		return pageModel;
	}
	
	/**
	 * ��DAO�и��ݲ�ѯ�������ͷ�ҳ�����û�ȡ��ǰ�ķ�ҳ��PageModel�������������б�
	 * 
	 * @param dao
	 * @param whereCond
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static PageModel popupPageModel(DAO dao, String whereCond, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();

		// ���� totalCount
		int totalCount = new Long(dao.countByCond(whereCond)).intValue(); // long  to  int
		pageModel.setTotalCount(totalCount);

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
		} else if(pageIndex>pageModel.getPageCount()){
			pageModel.setPageIndex(pageModel.getPageCount());
		}else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}
		
		if(pageSize > CrmConstants.MAX_PAGE_SIZE){
			//throw new CommonException(new CommonError(CommonError.PAGE_TOO_LARGE_ERROR));
		}

		// ͨ��QueryFilter��DAO�й��˳���ǰ�ķ�ҳ��Ϣ��
		// pageModel����Ϣֱ�����������pageModel�л�ȡ��
		QueryFilter queryFilter = QueryFilterFactory.getPageQueryFilter(pageModel);
		dao.findByCond(whereCond, queryFilter);

		return pageModel;
	}
	
	/**
	 * ��DAO�и��ݲ�ѯ�������ͷ�ҳ�����û�ȡ��ǰ�ķ�ҳ��PageModel�������������б�
	 * 
	 * @param dao
	 * @param whereCond
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static PageModel popupPageModelNoCount(DAO dao, String whereCond, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();

		// ���� totalCount
		int totalCount = 10000; // long  to  int
		pageModel.setTotalCount(totalCount);

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
		} else if(pageIndex>pageModel.getPageCount()){
			pageModel.setPageIndex(pageModel.getPageCount());
		}else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}
		
		if(pageSize > CrmConstants.MAX_PAGE_SIZE){
			//throw new CommonException(new CommonError(CommonError.PAGE_TOO_LARGE_ERROR));
		}

		// ͨ��QueryFilter��DAO�й��˳���ǰ�ķ�ҳ��Ϣ��
		// pageModel����Ϣֱ�����������pageModel�л�ȡ��
		QueryFilter queryFilter = QueryFilterFactory.getPageQueryFilter(pageModel);
		dao.findByCond(whereCond, queryFilter);

		return pageModel;
	}
	
	/*
	*//**
	 * ��DAO�и��ݲ�ѯ�������ͷ�ҳ�����û�ȡ��ǰ�ķ�ҳ��PageModel�������������б�
	 * 
	 * @param dao
	 * @param whereCond
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 *//*
	public static PageModel popupPageModel(SQLQry sqlQry, String countSql,String sql, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();
		// ���� totalCount
		int totalCount = new Long(sqlQry.selectCount(countSql)).intValue(); // long  to  int
		pageModel.setTotalCount(totalCount);

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
		} else if(pageIndex>pageModel.getPageCount()){
			pageModel.setPageIndex(pageModel.getPageCount());
		}else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}
		
		if(pageSize > CrmConstants.MAX_PAGE_SIZE){
			//throw new CommonException(new CommonError(CommonError.PAGE_TOO_LARGE_ERROR));
		}

		// ͨ��QueryFilter��DAO�й��˳���ǰ�ķ�ҳ��Ϣ��
		// pageModel����Ϣֱ�����������pageModel�л�ȡ��
		QueryFilter queryFilter = QueryFilterFactory.getPageQueryFilter(pageModel);
		
		
		sqlQry.select(sql, queryFilter);

		return pageModel;
	}
	*/
	
	/**
	 * ��list�и��ݲ�ѯ�������ͷ�ҳ�����û�ȡ��ǰ�ķ�ҳ��PageModel�������������б�
	 * 
	 * @param list
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static PageModel popupPageModel(List list, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();

		// ���� totalCount
		if(list==null) return pageModel;
		
		int totalCount = list.size(); // long  to  int
		pageModel.setTotalCount(totalCount);

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
		} else if(pageIndex>pageModel.getPageCount()){
			pageModel.setPageIndex(pageModel.getPageCount());
		}else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}
		
		if(pageSize > CrmConstants.MAX_PAGE_SIZE){
			//throw new CommonException(new CommonError(CommonError.PAGE_TOO_LARGE_ERROR));
		}
		
		int index = 0;
		int count = 0;
		int locationInt = (pageModel.getPageIndex() - 1) * pageModel.getPageSize();
		//logger.debug(list.size());
		Iterator iter = list.iterator();
		ArrayList dataList = new ArrayList();
		while(iter.hasNext() && count < pageSize){			
			index++;
			if(index > locationInt){
				count++;
				dataList.add(iter.next());
			}
			else
			  iter.next();			
		}
		//logger.debug(dataList.size());
		pageModel.setList(dataList);
        
		//pageModel.setTotalCount(dataList.size());
	
		return pageModel;
	}


}
