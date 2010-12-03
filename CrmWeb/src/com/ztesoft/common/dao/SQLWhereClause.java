package com.ztesoft.common.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

public class SQLWhereClause {
	
	private StringBuffer whereCond = null ;
	private List para = null ;
	
	public static final boolean PAGING = true ;
	public static final boolean NO_PAGING = false ;
	
	private int pageSize = -1 ;
	private int pageIndex = -1 ;
	
	
	
	public SQLWhereClause(){
		
	}
	
	/**
	 * ����whereColumn����map�ֶ�ȫ��ƴ��Ϊ����
	 * @param whereColumn
	 */
	public SQLWhereClause(Map whereColumn){
		wrapSQL(whereColumn , null, null ) ;
	}
	
	/**
	 * ���ݹ�������ƴ����
	 * @param whereColumn ����map
	 * @param filterStr �����ֶΣ�ֵ��ʽΪc1,c2,c3...,ÿ��ֵ������map��key��Ӧ��������˲�������
	 */
	public SQLWhereClause(Map whereColumn , String filterStr){
		wrapSQL( whereColumn ,  filterStr , null ) ;
	}
	
	/**
	 * 
	 * @param whereColumn ����map
	 * @param filterStr �����ֶΣ�ֵ��ʽΪc1,c2,c3...,ÿ��ֵ������map��key��Ӧ��������˲�������
	 * @param changeName ������Щ���󣬰�ԭ��whereColumn �����key ����changeName ͬkey��Ӧ�� value���
	 */
	public SQLWhereClause(Map whereColumn , String filterStr , Map changeName ){
		wrapSQL( whereColumn ,  filterStr , changeName) ;
	}
	
	
	/**
	 * 
	 * ��ҳ���������Ƽ�ʹ�ø÷���
	 * @param dto
	 * @param filterStr �����ֶΣ�ֵ��ʽΪc1,c2,c3...,ÿ��ֵ������map��key��Ӧ��������˲�������
	 * @param changeName ������Щ���󣬰�ԭ��whereColumn �����key ����changeName ͬkey��Ӧ�� value���
	 * 
	 */
	public  SQLWhereClause(DynamicDict dto , String filterStr , Map changeName , boolean paging ){
		Map whereColumn = Const.getParam(dto) ;
		//��ҳ����
		if(paging == SQLWhereClause.PAGING){
			this.pageSize = Const.getPageSize(whereColumn) ;
			this.pageIndex = Const.getPageIndex(whereColumn) ;
		}
		
		wrapSQL( whereColumn ,  filterStr,  changeName ) ;
	}
	
	/**
	 * 
	 * ��ҳ���������Ƽ�ʹ�ø÷���
	 * @param dto
	 * @param filterStr �����ֶΣ�ֵ��ʽΪc1,c2,c3...,ÿ��ֵ������map��key��Ӧ��������˲�������
	 * @param changeName ������Щ���󣬰�ԭ��whereColumn �����key ����changeName ͬkey��Ӧ�� value���
	 * 
	 */
	public  SQLWhereClause(DynamicDict dto , String filterStr , Map changeName ){
		Map whereColumn = Const.getParam(dto) ;
		
		this.pageSize = Const.getPageSize(whereColumn) ;
		this.pageIndex = Const.getPageIndex(whereColumn) ;
		wrapSQL( whereColumn ,  filterStr,  changeName ) ;
	}
	private void wrapSQL(Map whereColumn , String filterStr, Map changeName ){
		if(whereColumn != null && !whereColumn.isEmpty()){
			//ת����������Ϊ�ַ�����
			String[] filterColumn = null ;
			if(filterStr != null && !"".equals(filterStr.trim()))
				filterColumn = filterStr.split(",") ;
			//��ʼ������
			whereCond = getEmptyStringBuffer() ;
			para = getEmptyList() ;
			//��֯����
			Iterator it = whereColumn.keySet().iterator() ;
			while( it.hasNext()) {
				String cname = (String)it.next() ;
				//�ж��Ƿ���Ҫ����
				if(contains(filterColumn ,  cname ))
					continue ;
				
				String cvalue = (String) whereColumn.get(cname) ;
				
				//ר������
				String haveChangeName = getChangeName( changeName ,  cname ) ;
				if(!"".equals(haveChangeName))
					cname = (String)it.next() ;
				
				whereCond.append(" and ").append(cname).append("=? ") ;
				para.add(cvalue) ;
			}
			
		}
	}
	
	/**
	 * ר������
	 * @param changeName ר������
	 * @param cname ��ת��
	 * @return
	 */
	private static String getChangeName(Map changeName , String cname ){
		if(changeName == null || changeName.isEmpty())
			return getEmptyStr() ;
		return changeName.containsKey(cname) ? 
				(String)changeName.get(cname) : getEmptyStr() ;
	}
	
	/**
	 * �ж������Ƿ����ֵΪcname������Ϊtrue
	 * @param filterColumn
	 * @param cname
	 * @return
	 */
	private static boolean contains(String[] filterColumn , String cname ){
		//����Ҫ����
		
		if(filterColumn == null || filterColumn.length == 0 ||
				cname == null || "".equals(cname.trim()))
			return false ;
		
		//�������Ƚ�
		boolean result = false ;
		for(int i = 0 ,j=filterColumn.length ; i < j ; i++){
			if(cname.equals(filterColumn[i])){
				result = true ;
				break ;
			}
				
		}
		return result ;
	}

	
	public List getPara() {
		if(this.para == null )
			return getEmptyList() ;
		return para;
	}
	
	public void setPara(List para) {
		this.para = para;
	}
	
	public String getWhereCond() {
		if(this.whereCond == null )
			return getEmptyStr() ;
		return whereCond.toString();
	}
	
	public void setWhereCond(StringBuffer whereCond) {
		this.whereCond = whereCond;
	}
	
	public static String getEmptyStr(){
		return "" ;
	}
	
	public static StringBuffer getEmptyStringBuffer(){
		return new StringBuffer() ;
	}
	
	public static List getEmptyList(){
		return new ArrayList() ;
	}
	public int getPageIndex() throws FrameException {
		if(pageIndex == -1)
			throw new FrameException("pageIndexδ����ʼ������ȷ��!") ;
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() throws FrameException {
		if(pageSize == -1)
			throw new FrameException("pageSizeδ����ʼ������ȷ��!") ;
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
