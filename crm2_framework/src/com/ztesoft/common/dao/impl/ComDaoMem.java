package com.ztesoft.common.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/*
 * @author RyoUehara 
 * @date 090822
 * @desc 根据传入的wherecond条件从ArrayList里查询出符合条件的记录，算法是全表扫描。主要用于加载完数据后的内存数据查询
 * @具体步骤：
 * */
public class ComDaoMem {
	public static void main(String[]args){
		ArrayList lList=new ArrayList();
		ArrayList rList=new ArrayList();
		HashMap vo1=new HashMap();
		vo1.put("produce_id", "1");
		vo1.put("produce_no", "2");
		vo1.put("product_id", "3");
		lList.add(vo1);
		vo1=new HashMap();
		vo1.put("produce_id", "1");
		vo1.put("produce_no", "22");
		vo1.put("product_id", "33");
		lList.add(vo1);
		
		HashMap vo2=new HashMap();
		vo2.put("produce_id", "1");
		vo2.put("produce_no", "232");
		vo2.put("product_id", "323");
		rList.add(vo2);
		vo2=new HashMap();
		vo2.put("produce_id", "3");
		vo2.put("produce_no", "444");
		vo2.put("product_id", "555");
		rList.add(vo2);
		
		ComDaoMem dao=new ComDaoMem();
		String whereCond="produce_id=produce_id";
		String selectItem="l/produce_id,r/produce_no,r/product_id";
		//selectItem="";
		whereCond="";
		ArrayList lstRet=dao.innerJoin(lList, rList, selectItem,whereCond);
		for(int i=0;i<lstRet.size();i++){
			System.out.print("\n");
			HashMap vo3=(HashMap)lstRet.get(i);
			System.out.print(vo3.get("produce_id"));
			System.out.print(",");
			System.out.print(vo3.get("produce_no"));
			System.out.print(",");
			System.out.print(vo3.get("product_id"));
		}
		try {
			dao.insert(lList, new String[]{"produce_id,produce_no"}, vo1);
			dao.insert(lList, new String[]{"produce_id,produce_no"}, vo2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//根据id查找对象
	public List findByCond(ArrayList lstData,HashMap whereCond){
		//如果条件是空，那么返回全部结果
		if (whereCond==null || whereCond.isEmpty()) return lstData; 
		//定义一个返回值
		List returnLstResult=new ArrayList();
		//如果传入的查找池是空，那么返回查找池
		if (lstData==null || lstData.isEmpty()) return lstData;
		//定义规格上规定需要多少个字段相等
		int intClsEqualCount=whereCond.size();
		//定义实际每条记录的相等字段数
		int intInstEqualCount=0;
		int lstDataSize=lstData.size();
		//定义实例数据循环时的每个字段的值和规格上每个字段值
		String clsKey="",clsVal="",instKey="",instVal="";
		//定义实例数据的一条记录
		HashMap instRecord=null;
		Map.Entry map=null;
		for(int i=0;i<lstDataSize;i++){
			instRecord=(HashMap)lstData.get(i);
			intInstEqualCount=0;
			for(Iterator it=whereCond.entrySet().iterator();it.hasNext();){
				map=(Map.Entry)it.next();
				clsKey=(String)map.getKey();
				clsVal=(String)map.getValue();
				instVal=(String)instRecord.get(clsKey);
				if(clsVal==null)clsVal="";
				if(instVal==null)instVal=""; 
				if (clsVal.equals(instVal))intInstEqualCount++;
				else break;
			}
			//如果每个字段都相等，那么这条记录符合条件，需要加入放回结果列表里
			if (intInstEqualCount==intClsEqualCount)returnLstResult.add(instRecord);
		}
		return returnLstResult;
	}
	
	//根据id查找对象
	public String findByCondSingleVal(ArrayList lstData,HashMap whereCond){
		//如果条件是空，那么返回全部结果
		if (whereCond==null || whereCond.isEmpty()) return ""; 
		//定义一个返回值
		//List returnLstResult=new ArrayList();
		//如果传入的查找池是空，那么返回查找池
		if (lstData==null || lstData.isEmpty()) return "";
		//定义规格上规定需要多少个字段相等
		int intClsEqualCount=whereCond.size();
		//定义实际每条记录的相等字段数
		int intInstEqualCount=0;
		int lstDataSize=lstData.size();
		//定义实例数据循环时的每个字段的值和规格上每个字段值
		String clsKey="",clsVal="",instKey="",instVal="";
		//定义实例数据的一条记录
		HashMap instRecord=null;
		Map.Entry map=null;
		String retCol="";
		for(int i=0;i<lstDataSize;i++){
			instRecord=(HashMap)lstData.get(i);
			intInstEqualCount=0;
			for(Iterator it=whereCond.entrySet().iterator();it.hasNext();){
				map=(Map.Entry)it.next();
				clsKey=(String)map.getKey();
				if (i==0)
				retCol=clsKey;
				clsVal=(String)map.getValue();
				instVal=(String)instRecord.get(clsKey);
				if(clsVal==null)clsVal="";
				if(instVal==null)instVal=""; 
				if (clsVal.equals(instVal))intInstEqualCount++;
				else break;
			}
			//如果每个字段都相等，那么这条记录符合条件，需要加入放回结果列表里
			if (intInstEqualCount==intClsEqualCount)return (String)instRecord.get(retCol);
		}
		return "";
	}
	
	//根据id查找对象
	public String findByCondSingleVal(ArrayList lstData,HashMap whereCond,String colName){
		//如果条件是空，那么返回全部结果
		if (whereCond==null || whereCond.isEmpty()) return ""; 
		//定义一个返回值
		//List returnLstResult=new ArrayList();
		//如果传入的查找池是空，那么返回查找池
		if (lstData==null || lstData.isEmpty()) return "";
		//定义规格上规定需要多少个字段相等
		int intClsEqualCount=whereCond.size();
		//定义实际每条记录的相等字段数
		int intInstEqualCount=0;
		int lstDataSize=lstData.size();
		//定义实例数据循环时的每个字段的值和规格上每个字段值
		String clsKey="",clsVal="",instKey="",instVal="";
		//定义实例数据的一条记录
		HashMap instRecord=null;
		Map.Entry map=null;
		for(int i=0;i<lstDataSize;i++){
			instRecord=(HashMap)lstData.get(i);
			intInstEqualCount=0;
			for(Iterator it=whereCond.entrySet().iterator();it.hasNext();){
				map=(Map.Entry)it.next();
				clsKey=(String)map.getKey();
				clsVal=(String)map.getValue();
				instVal=(String)instRecord.get(clsKey);
				if(clsVal==null)clsVal="";
				if(instVal==null)instVal=""; 
				if (clsVal.equals(instVal))intInstEqualCount++;
				else break;
			}
			//如果每个字段都相等，那么这条记录符合条件，需要加入放回结果列表里
			if (intInstEqualCount==intClsEqualCount)return (String)instRecord.get(colName);
		}
		return "";
	}
	
	//根据id删除对象
	public void deleteByCond(ArrayList lstData,HashMap whereCond){
		//如果条件是空，那么返回全部结果
		if (whereCond==null || whereCond.isEmpty()) return; 
		//定义一个返回值
		//List returnLstResult=new ArrayList();
		//如果传入的查找池是空，那么返回查找池
		if (lstData==null || lstData.isEmpty()) return;
		//定义规格上规定需要多少个字段相等
		int intClsEqualCount=whereCond.size();
		//定义实际每条记录的相等字段数
		int intInstEqualCount=0;
		//定义实例数据循环时的每个字段的值和规格上每个字段值
		String clsKey="",clsVal="",instKey="",instVal="";
		//定义实例数据的一条记录
		HashMap instRecord=null;
		Map.Entry map=null;
		for(Iterator it1=lstData.iterator();it1.hasNext();){
			instRecord=(HashMap)it1.next();
			intInstEqualCount=0;
			for(Iterator it2=whereCond.entrySet().iterator();it2.hasNext();){
				map=(Map.Entry)it2.next();
				clsKey=(String)map.getKey();
				clsVal=(String)map.getValue();
				instVal=(String)instRecord.get(clsKey);
				if(clsVal==null)clsVal="";
				if(instVal==null)instVal=""; 
				if (clsVal.equals(instVal))intInstEqualCount++;
				else break;
			}
			//如果每个字段都相等，那么这条记录符合条件，需要加入放回结果列表里
			if (intInstEqualCount==intClsEqualCount) it1.remove();
		}
	}
	
	//根据id插入对象(支持主键定义)
	public void insert(ArrayList lstData,String[] primaryKey,HashMap fields) throws Exception{
		if(fields==null || fields.isEmpty())return ;
		if(lstData==null) lstData=new ArrayList();
		//如果传入的查找池是空，那么新建一个查找池，并put入需要的字段
		if (lstData.isEmpty()) {
			lstData.add(fields);
			return;
		}
		//如果条件是空，那么返回全部结果
		if (primaryKey==null) {
			lstData.add(fields);
			return; 
		}

		//构造错误信息描述
		StringBuffer primaryKeyDesc=new StringBuffer();
		//构建临时存放插入的对象
		ArrayList lstTmpInst=new ArrayList();
		//构建主键的key和值
		HashMap hsPrimaryKey=new HashMap();
		for(int i=0;i<primaryKey.length;i++){
			String key=primaryKey[i];
			hsPrimaryKey.put(key, fields.get(key));
			primaryKeyDesc.append(",");
			primaryKeyDesc.append(key);
		}
		
		//定义规格上规定需要多少个字段相等
		int intClsEqualCount=hsPrimaryKey.size();
		//定义实际每条记录的相等字段数
		int intInstEqualCount=0;
		//定义实例数据循环时的每个字段的值和规格上每个字段值
		String clsKey="",clsVal="",instKey="",instVal="";
		//定义实例数据的一条记录
		HashMap instRecord=null;
		Map.Entry map=null;
		for(Iterator it1=lstData.iterator();it1.hasNext();){
			instRecord=(HashMap)it1.next();
			intInstEqualCount=0;
			for(Iterator it2=hsPrimaryKey.entrySet().iterator();it2.hasNext();){//根据主键值，查找记录
				map=(Map.Entry)it2.next();
				clsKey=(String)map.getKey();
				clsVal=(String)map.getValue();
				instVal=(String)instRecord.get(clsKey);
				if(clsVal==null)clsVal="";
				if(instVal==null)instVal=""; 
				if (clsVal.equals(instVal))intInstEqualCount++;
				else break;
			}
			//如果每个字段都相等，那么这条记录符合条件，就要报错
			if (intInstEqualCount==intClsEqualCount){
				throw new Exception("uniqued primary key:"+primaryKeyDesc.toString().substring(1));
			}
			lstTmpInst.add(fields);
		}
		if(lstTmpInst!=null && !lstTmpInst.isEmpty()){//如果有值，那么put进去
			lstData.addAll(lstTmpInst);
		}
	}
	//根据id更新对象
	public void update(ArrayList lstData,HashMap whereCond,HashMap fields){
		//如果条件是空，那么返回全部结果
		if (whereCond==null || whereCond.isEmpty()) return; 
		//定义一个返回值
		//List returnLstResult=new ArrayList();
		//如果传入的查找池是空，那么返回查找池
		if (lstData==null || lstData.isEmpty()) return;
		//定义规格上规定需要多少个字段相等
		int intClsEqualCount=whereCond.size();
		//定义实际每条记录的相等字段数
		int intInstEqualCount=0;
		//定义实例数据循环时的每个字段的值和规格上每个字段值
		String clsKey="",clsVal="",instKey="",instVal="";
		//定义实例数据的一条记录
		HashMap instRecord=null;
		Map.Entry map=null;
		for(Iterator it1=lstData.iterator();it1.hasNext();){
			instRecord=(HashMap)it1.next();
			intInstEqualCount=0;
			for(Iterator it2=whereCond.entrySet().iterator();it2.hasNext();){
				map=(Map.Entry)it2.next();
				clsKey=(String)map.getKey();
				clsVal=(String)map.getValue();
				instVal=(String)instRecord.get(clsKey);
				if(clsVal==null)clsVal="";
				if(instVal==null)instVal=""; 
				if (clsVal.equals(instVal))intInstEqualCount++;
				else break;
			}
			//如果每个字段都相等，那么这条记录符合条件，需要把更新的字段更新
			if (intInstEqualCount==intClsEqualCount){
				for(Iterator it3=fields.entrySet().iterator();it3.hasNext();){
					map=(Map.Entry)it3.next();
					clsKey=(String)map.getKey();
					clsVal=(String)map.getValue();
					if (instRecord.containsKey(clsKey)){
						instRecord.remove(clsKey);
						instRecord.put(clsKey, clsVal);
					}
				}
			}
		}
	}

	//执行表连接(只支持and条件连接，每个条件只支持=号)
	public ArrayList innerJoin(ArrayList lList,ArrayList rList,String selectKey,String whereCond){
		//定义新的数据集
		ArrayList lstRet=new ArrayList();
		//处理select的字段
		String [] lstSelectKey=null;
		if (selectKey!=null && !selectKey.equals(""))
			lstSelectKey=selectKey.split(",");
		
		//词法分析,把whereCond按照and分割成多个1=2形式的条件，再把1=2形式的条件,分别put进两个String[]里
		if (whereCond==null || whereCond.equals("")){
			//另外处理，制作笛卡尔乘积
			int lSize=lList.size();
			int rSize=rList.size();
			for(int j=0;j<lSize;j++){
				HashMap lVo=(HashMap)lList.get(j);
				for(int k=0;k<rSize;k++){
					HashMap rVo=(HashMap)rList.get(k);
					HashMap retVo=new HashMap();
					if (lstSelectKey!=null && lstSelectKey.length>0){
						for(int n=0;n<lstSelectKey.length;n++){
							String tmpVal=lstSelectKey[n];
							String []tmpUnit=tmpVal.split("/");
							String tableName=tmpUnit[0];
							String colName=tmpUnit[1];
							if (tableName.equals("l")){
								retVo.put(colName, lVo.get(colName));
							}
							else{
								retVo.put(colName, rVo.get(colName));
							}
						}
					}else{//全部的key
						retVo.putAll(lVo);
						retVo.putAll(rVo);
					}
					lstRet.add(retVo);
				}
			}
			lList=null;//去除原来的引用
			lList=lstRet;//指向新的引用
			return lstRet;
		}
		
		//匹配条件
		String[] andWhere=whereCond.split(" and ");
		if (andWhere!=null && andWhere.length>0){
			int andWhereLength=andWhere.length;
			String[] lWhere=new String[andWhereLength];
			String[] rWhere=new String[andWhereLength];
			for(int i=0;i<andWhereLength;i++){
				String oneWhereUnit=andWhere[i];//这个unit的形式是1=1
				String []strTmpUnit=oneWhereUnit.split("=");
				lWhere[i]=strTmpUnit[0];
				rWhere[i]=strTmpUnit[1];
			}
			//这时得到的是两个String[]，装着=号两边的值
			//比较String里每个字段的值是否有，如果有，那么就把这个记录的vo put进去
			for(int j=0;j<lList.size();j++){
				HashMap lVo=(HashMap)lList.get(j);
				for(int k=0;k<rList.size();k++){
					HashMap rVo=(HashMap)rList.get(k);
					boolean isAllEqual=true;
					for(int m=0;m<lWhere.length;m++){
						String strLKey=lWhere[m];
						String strRKey=rWhere[m];
						String strLVal=(String)lVo.get(strLKey);
						String strRVal=(String)rVo.get(strRKey);
						if (strLVal!=null && strRVal!=null && strLVal.equals(strRVal)){
							continue;
						}else{
							isAllEqual=false;
							break;
						}
					}
					if (isAllEqual){
						HashMap retVo=new HashMap();
						if (lstSelectKey!=null && lstSelectKey.length>0){
							for(int n=0;n<lstSelectKey.length;n++){
								String tmpVal=lstSelectKey[n];
								String []tmpUnit=tmpVal.split("/");
								String tableName=tmpUnit[0];
								String colName=tmpUnit[1];
								if (tableName.equals("l")){
									retVo.put(colName, lVo.get(colName));
								}
								else{
									retVo.put(colName, rVo.get(colName));
								}
							}
						}else{//全部的key
							retVo.putAll(lVo);
							retVo.putAll(rVo);
						}
						lstRet.add(retVo);
					}
				}
			}
			return lstRet;
		}	
		return lstRet;
	}
}
