package com.ztesoft.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.ComDAO;
import com.ztesoft.common.dao.ComServiceDAO;
import com.ztesoft.common.dao.impl.GetBaseServiceSQL;
import com.ztesoft.common.util.DcSystemParamUtil;
import com.ztesoft.common.util.PageModel;
/**
 *@author AyaKoizumi
 *@date 20100824
 *@desc ����dao���룬��dao����
 * */
public class ComExtendDAO{

	private static HashMap tableFieldsMap=null;//���һ�����ȫ���ֶ�����
	private static HashMap tablePriFieldsMap=null;//���һ����������ֶ�����
	private static HashMap tableFieldsTypeMap=null;//���һ������ֶ�����
	private static HashMap tableFieldsNameMap=null;//���һ������ֶ������� ���磺select 1 as cnt from dual ͨ��HashMap���ŵ��ֶ�ӳ�䣬���԰��ֶ�cnt����ΪC_N_T 
	private SqlMapExe dao=SqlMapExe.getInstance();

	private ComDAO comdao=ComServiceDAO.getInstance().getComDAO();
	

	public void addTablePriFieldsMap(String tableName,String[]fields){
		if(tablePriFieldsMap==null){
			tablePriFieldsMap=new HashMap();
		}
		if(!tablePriFieldsMap.containsKey(tableName))
		tablePriFieldsMap.put(tableName, fields);
	}
	public void addTableFieldsMap(String tableName,String[]fields){
		if(tableFieldsMap==null){
			tableFieldsMap=new HashMap();
		}
		if(!tableFieldsMap.containsKey(tableName))
		tableFieldsMap.put(tableName, fields);
	}
	
	public String querySingleValue(String sql){
		return dao.querySingleValue(sql);
	}
	
	public String querySingleValue(String sql,String[]params){
		return dao.querySingleValue(sql, params);
	}
	public int executeUpdate(String sql,String[]params){
		return dao.excuteUpdate(sql, converToArrList(params));
	}
	public List execForMapList(String sql,String[]params){
		return dao.execForMapList(sql, params);
	}
	
	public List execForMapList(String sql,String[]params,int pageIndex,int pageSize){
		String _sql=this.getPageSql(sql,pageIndex,pageSize);
		return dao.execForMapList(_sql, params);
	}
	
	public PageModel query(String sql,String[]params,int pageIndex,int pageSize){
		String _sql=this.getPageSql(sql,pageIndex,pageSize);
		List list=dao.execForMapList(_sql, params);
		_sql=getTotalCountSql(sql);
		String tatalCount=this.querySingleValue(_sql, params);
		PageModel pageModel=new PageModel();
		
		int _tatalCount=Integer.parseInt(tatalCount);
		// pageCount
		if (_tatalCount % pageSize > 0) {
			pageModel.setPageCount(_tatalCount / pageSize + 1);
		} else {
			pageModel.setPageCount(_tatalCount / pageSize);
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
			pageModel.setPageSize(_tatalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}
		
//		pageModel.setPageIndex(Integer.parseInt(tatalCount)/pageSize);
//		pageModel.setPageSize(pageSize);
		pageModel.setList(list);
		pageModel.setTotalCount(Integer.parseInt(tatalCount));
		return pageModel;
	}

	public PageModel queryNocount(String sql,String[]params,int pageIndex,int pageSize){
		String _sql=this.getPageSql(sql,pageIndex,pageSize);
		List list=dao.execForMapList(_sql, params);
		PageModel pageModel=new PageModel();
		
//		_sql=getTotalCountSql(sql);
		String tatalCount="1000";//dao.querySingleValue(sql, params);
		
		int _tatalCount=Integer.parseInt(tatalCount);
		// pageCount
		if (_tatalCount % pageSize > 0) {
			pageModel.setPageCount(_tatalCount / pageSize + 1);
		} else {
			pageModel.setPageCount(_tatalCount / pageSize);
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
			pageModel.setPageSize(_tatalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}
		

//		pageModel.setPageIndex(pageIndex);
//		pageModel.setPageSize(pageSize);
		pageModel.setList(list);
		pageModel.setTotalCount(Integer.parseInt(tatalCount));
		return pageModel;
	}

	public String getPageSql(String sql,int pageIndex,int pageSize){
		int startIndex = (pageIndex - 1) * pageSize+1;
		int endIndex = startIndex + pageSize ;
		sql = "select * from (select my_table.*,rownum as my_rownum from( "+
		sql +
				" ) my_table where rownum< " + endIndex + 
				") where my_rownum>= " + startIndex ;
		return sql;
	}
	private String getTotalCountSql(String sql){
		sql = "select count(9) from( "+sql +" )" ;
		return sql;
	}
	
	private ArrayList converToArrList(String []inputStr){
		ArrayList retList=new ArrayList();
		if(inputStr==null || inputStr.length==0)return retList;
		for(int i=0;i<inputStr.length;i++){
			retList.add(inputStr[i]);
		}
		return retList;
	}

	//���isByPrimaryKeyΪtrue����ô��ȡtableName������ֵ���в�ѯ���������ôֱ�Ӹ��ݴ����fieldsVo���в�ѯ
	public ArrayList select(String tableName,HashMap fieldsVo,boolean isByPrimaryKey) throws Exception{
		comdao.setTableName(tableName);
		if(isByPrimaryKey)
			return comdao.findByCond("", this.getTablePriFieldView(tableName, fieldsVo), 
					this.getManualVoType(tableName), this.getManualVoName(tableName));
		else
			return comdao.findByCond("", this.getTableFieldView(tableName, fieldsVo), 
					this.getManualVoType(tableName), this.getManualVoName(tableName));
	}
	//���isByPrimaryKeyΪtrue����ô��ȡtableName������ֵ����ɾ�����������ôֱ�Ӹ��ݴ����fieldsVo����ɾ��
	public int delete(String tableName,HashMap fieldsVo,boolean isByPrimaryKey) throws Exception{
		comdao.setTableName(tableName);
		if(isByPrimaryKey)
			return comdao.deleteByCond("", this.getTablePriFieldView(tableName, fieldsVo), 
					this.getManualVoType(tableName), this.getManualVoName(tableName));
		else
			return comdao.deleteByCond("", this.getTableFieldView(tableName, fieldsVo), 
					this.getManualVoType(tableName), this.getManualVoName(tableName));
	}

	public int insert(String tableName,HashMap fieldsVo) throws Exception{
		comdao.setTableName(tableName);
		return comdao.insert("", this.getTableFieldView(tableName, fieldsVo), 
				this.getManualVoType(tableName), this.getManualVoName(tableName));
	}
	//���isByPrimaryKeyΪtrue����ô��ȡtableName������ֵ���и��£��������ôֱ�Ӹ��ݴ����fieldsVo���и���
	public int update(String tableName,HashMap fieldsVo,HashMap whereCond) throws Exception{
		comdao.setTableName(tableName);
		//��ȡ�����ֶ�
		LinkedHashMap updateFields=getTableFieldView(tableName,fieldsVo);
		//���ݴ�����ֶ�ֵ
		LinkedHashMap backFields=(LinkedHashMap)updateFields.clone();
		//��ȡ����
		LinkedHashMap _whereCond=getTablePriFieldView(tableName,fieldsVo);
		//�����ֶβ�����
		for(Iterator it=_whereCond.entrySet().iterator();it.hasNext();){
			Map.Entry map=(Map.Entry)it.next();
			String key=(String)map.getKey();
			updateFields.remove(key);
		}
		if(whereCond==null || whereCond.isEmpty()){
			return comdao.update("", updateFields, _whereCond, this.getManualVoType(tableName), this.getManualVoName(tableName));
		}else{
			return comdao.update("", updateFields, this.toLinked(whereCond), this.getManualVoType(tableName), this.getManualVoName(tableName));
		}
	}
	
	private LinkedHashMap toLinked(HashMap vo){
		if(vo==null || vo.isEmpty())return null;
		LinkedHashMap reVo=new LinkedHashMap();
		for(Iterator it=vo.entrySet().iterator();it.hasNext();){
			Map.Entry map=(Map.Entry)it.next();
			String key=(String)map.getKey();
			String val=(String)map.getValue();
			reVo.put(key, val);
		}
		return reVo;
	}
	public void setManualVoType(String tableName,HashMap manualVoType){
		if(tableFieldsTypeMap==null)tableFieldsTypeMap=new HashMap();
		if(!tableFieldsTypeMap.containsKey(tableName)){
			tableFieldsTypeMap.put(tableName, manualVoType);
		}
	}
	public void setManualVoName(String tableName,HashMap manualVoName){
		if(tableFieldsNameMap==null)tableFieldsNameMap=new HashMap();
		if(!tableFieldsNameMap.containsKey(tableName)){
			tableFieldsNameMap.put(tableName, manualVoName);
		}
	}
	
	public HashMap getManualVoType(String tableName){
		if(tableFieldsTypeMap==null || !tableFieldsTypeMap.containsKey(tableName))
			return new HashMap();
		else
			return (HashMap)tableFieldsTypeMap.get(tableName);
	}
	public HashMap getManualVoName(String tableName){
		if(tableFieldsNameMap==null || !tableFieldsTypeMap.containsKey(tableName))
			return new HashMap();
		else
			return (HashMap)tableFieldsNameMap.get(tableName);
	}
	
	//���ݱ�������ȡ�����ֶε�ֵ
	private LinkedHashMap getTablePriFieldView(String tableName,HashMap fieldsVo) throws Exception{
		if(tableName==null || tableName.equals("")) throw new Exception("��������Ϊ��");
		LinkedHashMap fields=new LinkedHashMap();
		String[]_fields=this.getTablePriFields(tableName);
		if(_fields!=null){
			for(int i = 0 ; i < _fields.length ; i++){
				String field = _fields[i] ;
				if(fieldsVo.containsKey(field)){
					fields.put(field, fieldsVo.get(field));
				}
			}
			return fields;
		}else{
			return new LinkedHashMap();//this.getTableFieldView(tableName,fieldsVo);
		}
	}
	//���ݱ�������ȡָ���ֶε�ֵ
	private LinkedHashMap getTableFieldView(String tableName,HashMap fieldsVo) throws Exception{
		if(tableName==null || tableName.equals("")) throw new Exception("��������Ϊ��");
		LinkedHashMap fields=new LinkedHashMap();
		String[]_fields=this.getTableFields(tableName);
		for(int i = 0 ; i < _fields.length ; i++){
			String field = _fields[i] ;
			if(fieldsVo.containsKey(field)){
				fields.put(field, fieldsVo.get(field));
			}
		}
		return fields;
	}
	//���ݱ�������ȡָ���ֶ�����
	private String []getTableFields(String tableName) throws Exception{
		if(tableName==null || tableName.equals("")) throw new Exception("��������Ϊ��");
		String []ret=(String [])this.getTableFieldsMap(tableName);
		if(ret==null || ret.length==0){
			throw new Exception("���������޷��ҵ���Ӧ�ֶ�");
		}else{
			return ret;
		}
	}
	//���ݱ�������ȡ�����ֶε�ֵ
	private String []getTablePriFields(String tableName) throws Exception{
		if(tableName==null || tableName.equals("")) throw new Exception("��������Ϊ��");
		String []ret=(String [])this.getTablePriFieldsMap(tableName);
		if(ret==null || ret.length==0){
			return null;
		}else{
			return ret;
		}
	}
	//���ݱ������ӻ����ȡ�ֶ�����
	public String[] getTableFieldsMap(String tableName) throws Exception{
		if(tableFieldsMap==null || tableFieldsMap.isEmpty() || !tableFieldsMap.containsKey(tableName)){
			throw new Exception("���������޷��ҵ���Ӧ�ֶ�");
		}
		return (String[] )tableFieldsMap.get(tableName);
	}
	//���ݱ������ӻ����ȡ��������
	public String[] getTablePriFieldsMap(String tableName) throws Exception{
		if(tablePriFieldsMap==null || tablePriFieldsMap.isEmpty() || !tablePriFieldsMap.containsKey(tableName)){
			return null;
		}
		return (String[] )tablePriFieldsMap.get(tableName);
	}
	
	//��ʼ��Ҫ����ı�
	public void initTableFields(String[]tables){
		//������
		this.initTablePriFieldsMap(tables);
		//���ֶ�
		this.initTableFieldsMap(tables);
	}
	private final static String USER="OM_USER";
	//������
	private void initTablePriFieldsMap(String[]tables){
		String user = DcSystemParamUtil.getSysParamByCache(USER).trim();
		for( int j = 0 ; j< tables.length ; j++){
			String tableName = tables[j] ;
			try {
				String []tmp=this.getTablePriFieldsMap(tableName.toLowerCase());
				if(tmp==null){
					String dbName=GetBaseServiceSQL.getDbName(tableName.toLowerCase());
					String strSQL=
						"select distinct a.column_name from sys.dba_cons_columns a,sys.dba_constraints b "+
						"where a.constraint_name=b.constraint_name and a.owner=b.owner "+
						"and b.constraint_type='P' and a.table_name=? and a.owner=?";
					List tableContruct=dao.execForMapList(strSQL, new String[]{tableName.toUpperCase(),dbName});
					if(tableContruct==null || tableContruct.isEmpty()){
						tableContruct=dao.execForMapList(strSQL, new String[]{tableName.toUpperCase(),user});
					}
					if(tableContruct==null || tableContruct.isEmpty()){
						strSQL=
							"select distinct a.column_name from sys.dba_cons_columns a,sys.dba_constraints b "+
							"where a.constraint_name=b.constraint_name and a.owner=b.owner "+
							"and b.constraint_type='P' and a.table_name=?";
						tableContruct=dao.execForMapList(strSQL, new String[]{tableName.toUpperCase()});
					}
					if(tableContruct!=null && !tableContruct.isEmpty()){
						String [] _tabFields=new String[tableContruct.size()];
						for(int i=0;i<tableContruct.size();i++){
							HashMap vo=(HashMap)tableContruct.get(i);
							_tabFields[i]=((String)vo.get("column_name")).toLowerCase();
						}
						this.addTablePriFieldsMap(tableName.toLowerCase(), _tabFields);
					}
				}
			} catch (Exception e) {
				String dbName=GetBaseServiceSQL.getDbName(tableName.toLowerCase());
				String strSQL=
					"select distinct a.column_name from sys.dba_cons_columns a,sys.dba_constraints b "+
					"where a.constraint_name=b.constraint_name and a.owner=b.owner "+
					"and b.constraint_type='P' and a.table_name=? and a.owner=?";
				List tableContruct=dao.execForMapList(strSQL, new String[]{tableName.toUpperCase(),dbName});
				if(tableContruct==null || tableContruct.isEmpty()){
					tableContruct=dao.execForMapList(strSQL, new String[]{tableName.toUpperCase(),user});
				}
					
				if(tableContruct!=null && !tableContruct.isEmpty()){
					String [] _tabFields=new String[tableContruct.size()];
					for(int i=0;i<tableContruct.size();i++){
						HashMap vo=(HashMap)tableContruct.get(i);
						_tabFields[i]=((String)vo.get("column_name")).toLowerCase();
					}
					this.addTablePriFieldsMap(tableName.toLowerCase(), _tabFields);
				}
			}
		}
	}
	//���ֶ�
	private void initTableFieldsMap(String []tables){
		String user = DcSystemParamUtil.getSysParamByCache(USER).trim();
		for( int j = 0 ; j< tables.length ; j++){
			String tableName = tables[j] ;
			tableName=tableName.toUpperCase();
			try {
				String []tmp=this.getTableFieldsMap(tableName.toLowerCase());
				if(tmp==null){
					String dbName=GetBaseServiceSQL.getDbName(tableName);
					String strSQL=
						"select distinct column_name From sys.dba_tab_cols where table_name=? and owner=?";
					List tableContruct=dao.execForMapList(strSQL, new String[]{tableName,dbName});
					if(tableContruct==null || tableContruct.isEmpty()){
						tableContruct=dao.execForMapList(strSQL, new String[]{tableName.toUpperCase(),user});
					}
					if(tableContruct==null || tableContruct.isEmpty()){
						strSQL=
							"select distinct column_name From sys.dba_tab_cols where table_name=?";
						tableContruct=dao.execForMapList(strSQL, new String[]{tableName.toUpperCase()});
					}
					if(tableContruct!=null && !tableContruct.isEmpty()){
						String [] _tabFields=new String[tableContruct.size()];
						for(int i=0;i<tableContruct.size();i++){
							HashMap vo=(HashMap)tableContruct.get(i);
							_tabFields[i]=((String)vo.get("column_name")).toLowerCase();
						}
						this.addTableFieldsMap(tableName.toLowerCase(), _tabFields);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				String dbName=GetBaseServiceSQL.getDbName(tableName);
				String strSQL=
					"select distinct column_name From sys.dba_tab_cols where table_name=? and owner=?";
				List tableContruct=dao.execForMapList(strSQL, new String[]{tableName.toUpperCase(),dbName});
				if(tableContruct==null || tableContruct.isEmpty()){
					tableContruct=dao.execForMapList(strSQL, new String[]{tableName.toUpperCase(),user});
				}
				if(tableContruct==null || tableContruct.isEmpty()){
					strSQL=
						"select distinct column_name From sys.dba_tab_cols where table_name=?";
					tableContruct=dao.execForMapList(strSQL, new String[]{tableName.toUpperCase()});
				}
				if(tableContruct!=null && !tableContruct.isEmpty()){
					String [] _tabFields=new String[tableContruct.size()];
					for(int i=0;i<tableContruct.size();i++){
						HashMap vo=(HashMap)tableContruct.get(i);
						_tabFields[i]=((String)vo.get("column_name")).toLowerCase();
					}
					this.addTableFieldsMap(tableName.toLowerCase(), _tabFields);
				}
			}
		}
	}
}
