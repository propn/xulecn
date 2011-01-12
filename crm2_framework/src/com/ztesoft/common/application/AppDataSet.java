package com.ztesoft.common.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ztesoft.common.dao.ComDAO;
import com.ztesoft.common.dao.ComServiceDAO;
import com.ztesoft.common.dao.impl.ComDaoMem;
import com.ztesoft.common.util.JNDINames;

public class AppDataSet {
	//�����ﶨ��һ����ά���ݽṹArrayList+HashMap
	private ArrayList lstDataSet=new ArrayList();
	private String strSql="";
	private LinkedHashMap whereCond=new LinkedHashMap();
	private HashMap manualVoType=new LinkedHashMap();
	private HashMap manualVoName=new LinkedHashMap();
	private String jndiName=JNDINames.CRM_DATASOURCE;
	//����һ��dao
	ComDAO dao=ComServiceDAO.getInstance().getComDAO();
	
	public static void main(String[]args){
		//
	}
	
	public void setJndiName(String pJndiName){
		this.jndiName=pJndiName;
	}
	public void setStrSql(String strBaseSql){
		this.strSql=strBaseSql;
	}
	public ArrayList getDataSet(){
		return this.lstDataSet;
	}
	
	//����һ����ά����
	public ArrayList createDataSet(String pStrSql) throws Exception{
		this.strSql=pStrSql;
		this.whereCond=null;
		this.manualVoType=null;
		this.manualVoName=null;
		return this.createDataSet(pStrSql, whereCond, manualVoType, manualVoName);
	}
	//����һ����ά����
	public ArrayList createDataSet(String pStrSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws Exception{
		this.strSql=pStrSql;
		this.whereCond=whereCond;
		this.manualVoType=manualVoType;
		this.manualVoName=manualVoName;
		dao.setJndiName(this.jndiName);
		this.lstDataSet=(ArrayList)dao.findByCond(strSql, this.whereCond, this.manualVoType, this.manualVoName);
		return lstDataSet;
	}
	//��ȡ��ֵ
	public String getSingleFieldVal(String pStrSql,LinkedHashMap whereCond,HashMap manualVoType,HashMap manualVoName) throws Exception{
		this.strSql=pStrSql;
		this.whereCond=whereCond;
		this.manualVoType=manualVoType;
		this.manualVoName=manualVoName;
		dao.setJndiName(this.jndiName);
		return (String)dao.findByCondSingleVal(strSql, this.whereCond, this.manualVoType, this.manualVoName);
	}
	//����dataset���ֶ�ƴ��sql���,��ִ��sql���
	public ArrayList createDataSet(String strBaseSql,ArrayList _lst,String strWhereCond) throws Exception{
		//������������
		int mSize=_lst.size();
		int _size=100;
		ArrayList newList=new ArrayList();
		while(mSize>_size){
			ArrayList lst=new ArrayList();
			for(int i=0;i<_size;i++){
				lst.add(_lst.get(i));
			}
			ArrayList __lst=this.createDataSet(strBaseSql,lst,null,strWhereCond,"");
			newList.addAll(__lst);
			mSize=mSize-_size;
		}
		if(mSize>0){
			ArrayList lst=new ArrayList();
			for(int i=0;i<mSize;i++){
				lst.add(_lst.get(i));
			}
			ArrayList __lst=this.createDataSet(strBaseSql,lst,null,strWhereCond,"");
			newList.addAll(__lst);
		}
		return newList;
	}
	//����dataset���ֶ�ƴ��sql���,��ִ��sql���
	public ArrayList createDataSet(String strBaseSql,ArrayList _lst,String strWhereCond,String orderBy) throws Exception{
		//������������
		int mSize=_lst.size();
		int _size=100;
		ArrayList newList=new ArrayList();
		while(mSize>_size){
			ArrayList lst=new ArrayList();
			for(int i=0;i<_size;i++){
				lst.add(_lst.get(i));
			}
			ArrayList __lst=this.createDataSet(strBaseSql,lst,null,strWhereCond,orderBy);
			newList.addAll(__lst);
			mSize=mSize-_size;
		}
		if(mSize>0){
			ArrayList lst=new ArrayList();
			for(int i=0;i<mSize;i++){
				lst.add(_lst.get(i));
			}
			ArrayList __lst=this.createDataSet(strBaseSql,lst,null,strWhereCond,orderBy);
			newList.addAll(__lst);
		}
		return newList;
	}
	//����dataset���ֶ�ƴ��sql���,��ִ��sql���
	public ArrayList createDataSet(String strBaseSql,ArrayList _lst,LinkedHashMap whereCond,String strWhereCond,String orderBy) throws Exception{
		//������������
		int mSize=_lst.size();
		int _size=100;
		ArrayList newList=new ArrayList();
		while(mSize>_size){
			ArrayList lst=new ArrayList();
			for(int i=0;i<_size;i++){
				lst.add(_lst.get(i));
			}
			ArrayList __lst=this._createDataSet(strBaseSql,lst,whereCond,strWhereCond,orderBy);
			newList.addAll(__lst);
			mSize=mSize-_size;
		}
		if(mSize>0){
			ArrayList lst=new ArrayList();
			for(int i=0;i<mSize;i++){
				lst.add(_lst.get(i));
			}
			ArrayList __lst=this._createDataSet(strBaseSql,lst,whereCond,strWhereCond,orderBy);
			newList.addAll(__lst);
		}
		return newList;
	}
	//����dataset���ֶ�ƴ��sql���,��ִ��sql���
	private ArrayList _createDataSet(String strBaseSql,ArrayList _lst,LinkedHashMap whereCond,String strWhereCond,String orderBy) throws Exception{
		//��ȡ������sql
		StringBuffer strbWhereCond=new StringBuffer(strBaseSql);
		//��ȡ��Ҫ��ȡ���ֶΣ�����ƴ��where����
		if(strWhereCond!=null && strWhereCond.length()>0){
			strbWhereCond.append(" and (");
			String[]strbCond=strWhereCond.split("and");
			//��ȡinǰ���ֶ�
			for(int j=0;j<strbCond.length;j++){
				String[] col=strbCond[j].split("=");
				strbWhereCond.append(col[1]);
				if(j<strbCond.length-1)
				strbWhereCond.append(",");
			}
			strbWhereCond.append(") in(");
			if(_lst!=null && !_lst.isEmpty()){
				for(int i=0;i<_lst.size();i++){
					HashMap vo=(HashMap)_lst.get(i);
					strbWhereCond.append(" select ");
					for(int j=0;j<strbCond.length;j++){
						String[] col=strbCond[j].split("=");
						strbWhereCond.append(vo.get(col[0].trim()));
						if(j<strbCond.length-1)
						strbWhereCond.append(",");
					}
					strbWhereCond.append(" from dual ");
					if(i!=(_lst.size()-1)){
						strbWhereCond.append(" union all ");
					}
				}
				strbWhereCond.append(")");
				strbWhereCond.append(orderBy);
				this.strSql=strbWhereCond.toString();
				this.lstDataSet=(ArrayList) dao.findByCond(this.strSql, whereCond, null, null);
			}
		}
		return this.lstDataSet;
	}
	
	
}
