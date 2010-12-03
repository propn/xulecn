package com.ztesoft.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ��˵��: ��ҳ��Ϣ�ķ�װ�࣬��¼�˵�ǰҳ����Ϣ�͵�ǰҳ�����ݡ�
 * 
 * Copyright ? 2006 zte Co.Ltd.
 * All rights reserved.
 * @author fjy
 * @version 1.0
 * @since 2006-3-7
 * @modified  by  fjy  2006-3-7
 */
public class PageModel implements Serializable {
	//�ܼ�¼��
	private int totalCount;
	//��ǰҳ����
	private int pageIndex;
	//��ҳ��
	private int pageCount;
	//ÿҳ�ļ�¼����
	private int pageSize;
	//��ǰҳ���ݵĶ����б�
	private List list;
	
	/**
	 * ��ʼ���ǿյ�list��
	 * 
	 * PageModel.java������
	 */
	public PageModel(){
		
		this.totalCount = 0;
		this.pageIndex = 1;
		this.pageCount = 1;
		this.pageSize = 10;
		this.list = new ArrayList();
		
	}
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	
}
