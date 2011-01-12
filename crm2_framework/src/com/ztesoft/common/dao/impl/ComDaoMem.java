package com.ztesoft.common.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/*
 * @author RyoUehara 
 * @date 090822
 * @desc ���ݴ����wherecond������ArrayList���ѯ�����������ļ�¼���㷨��ȫ��ɨ�衣��Ҫ���ڼ��������ݺ���ڴ����ݲ�ѯ
 * @���岽�裺
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
	//����id���Ҷ���
	public List findByCond(ArrayList lstData,HashMap whereCond){
		//��������ǿգ���ô����ȫ�����
		if (whereCond==null || whereCond.isEmpty()) return lstData; 
		//����һ������ֵ
		List returnLstResult=new ArrayList();
		//�������Ĳ��ҳ��ǿգ���ô���ز��ҳ�
		if (lstData==null || lstData.isEmpty()) return lstData;
		//�������Ϲ涨��Ҫ���ٸ��ֶ����
		int intClsEqualCount=whereCond.size();
		//����ʵ��ÿ����¼������ֶ���
		int intInstEqualCount=0;
		int lstDataSize=lstData.size();
		//����ʵ������ѭ��ʱ��ÿ���ֶε�ֵ�͹����ÿ���ֶ�ֵ
		String clsKey="",clsVal="",instKey="",instVal="";
		//����ʵ�����ݵ�һ����¼
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
			//���ÿ���ֶζ���ȣ���ô������¼������������Ҫ����Żؽ���б���
			if (intInstEqualCount==intClsEqualCount)returnLstResult.add(instRecord);
		}
		return returnLstResult;
	}
	
	//����id���Ҷ���
	public String findByCondSingleVal(ArrayList lstData,HashMap whereCond){
		//��������ǿգ���ô����ȫ�����
		if (whereCond==null || whereCond.isEmpty()) return ""; 
		//����һ������ֵ
		//List returnLstResult=new ArrayList();
		//�������Ĳ��ҳ��ǿգ���ô���ز��ҳ�
		if (lstData==null || lstData.isEmpty()) return "";
		//�������Ϲ涨��Ҫ���ٸ��ֶ����
		int intClsEqualCount=whereCond.size();
		//����ʵ��ÿ����¼������ֶ���
		int intInstEqualCount=0;
		int lstDataSize=lstData.size();
		//����ʵ������ѭ��ʱ��ÿ���ֶε�ֵ�͹����ÿ���ֶ�ֵ
		String clsKey="",clsVal="",instKey="",instVal="";
		//����ʵ�����ݵ�һ����¼
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
			//���ÿ���ֶζ���ȣ���ô������¼������������Ҫ����Żؽ���б���
			if (intInstEqualCount==intClsEqualCount)return (String)instRecord.get(retCol);
		}
		return "";
	}
	
	//����id���Ҷ���
	public String findByCondSingleVal(ArrayList lstData,HashMap whereCond,String colName){
		//��������ǿգ���ô����ȫ�����
		if (whereCond==null || whereCond.isEmpty()) return ""; 
		//����һ������ֵ
		//List returnLstResult=new ArrayList();
		//�������Ĳ��ҳ��ǿգ���ô���ز��ҳ�
		if (lstData==null || lstData.isEmpty()) return "";
		//�������Ϲ涨��Ҫ���ٸ��ֶ����
		int intClsEqualCount=whereCond.size();
		//����ʵ��ÿ����¼������ֶ���
		int intInstEqualCount=0;
		int lstDataSize=lstData.size();
		//����ʵ������ѭ��ʱ��ÿ���ֶε�ֵ�͹����ÿ���ֶ�ֵ
		String clsKey="",clsVal="",instKey="",instVal="";
		//����ʵ�����ݵ�һ����¼
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
			//���ÿ���ֶζ���ȣ���ô������¼������������Ҫ����Żؽ���б���
			if (intInstEqualCount==intClsEqualCount)return (String)instRecord.get(colName);
		}
		return "";
	}
	
	//����idɾ������
	public void deleteByCond(ArrayList lstData,HashMap whereCond){
		//��������ǿգ���ô����ȫ�����
		if (whereCond==null || whereCond.isEmpty()) return; 
		//����һ������ֵ
		//List returnLstResult=new ArrayList();
		//�������Ĳ��ҳ��ǿգ���ô���ز��ҳ�
		if (lstData==null || lstData.isEmpty()) return;
		//�������Ϲ涨��Ҫ���ٸ��ֶ����
		int intClsEqualCount=whereCond.size();
		//����ʵ��ÿ����¼������ֶ���
		int intInstEqualCount=0;
		//����ʵ������ѭ��ʱ��ÿ���ֶε�ֵ�͹����ÿ���ֶ�ֵ
		String clsKey="",clsVal="",instKey="",instVal="";
		//����ʵ�����ݵ�һ����¼
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
			//���ÿ���ֶζ���ȣ���ô������¼������������Ҫ����Żؽ���б���
			if (intInstEqualCount==intClsEqualCount) it1.remove();
		}
	}
	
	//����id�������(֧����������)
	public void insert(ArrayList lstData,String[] primaryKey,HashMap fields) throws Exception{
		if(fields==null || fields.isEmpty())return ;
		if(lstData==null) lstData=new ArrayList();
		//�������Ĳ��ҳ��ǿգ���ô�½�һ�����ҳأ���put����Ҫ���ֶ�
		if (lstData.isEmpty()) {
			lstData.add(fields);
			return;
		}
		//��������ǿգ���ô����ȫ�����
		if (primaryKey==null) {
			lstData.add(fields);
			return; 
		}

		//���������Ϣ����
		StringBuffer primaryKeyDesc=new StringBuffer();
		//������ʱ��Ų���Ķ���
		ArrayList lstTmpInst=new ArrayList();
		//����������key��ֵ
		HashMap hsPrimaryKey=new HashMap();
		for(int i=0;i<primaryKey.length;i++){
			String key=primaryKey[i];
			hsPrimaryKey.put(key, fields.get(key));
			primaryKeyDesc.append(",");
			primaryKeyDesc.append(key);
		}
		
		//�������Ϲ涨��Ҫ���ٸ��ֶ����
		int intClsEqualCount=hsPrimaryKey.size();
		//����ʵ��ÿ����¼������ֶ���
		int intInstEqualCount=0;
		//����ʵ������ѭ��ʱ��ÿ���ֶε�ֵ�͹����ÿ���ֶ�ֵ
		String clsKey="",clsVal="",instKey="",instVal="";
		//����ʵ�����ݵ�һ����¼
		HashMap instRecord=null;
		Map.Entry map=null;
		for(Iterator it1=lstData.iterator();it1.hasNext();){
			instRecord=(HashMap)it1.next();
			intInstEqualCount=0;
			for(Iterator it2=hsPrimaryKey.entrySet().iterator();it2.hasNext();){//��������ֵ�����Ҽ�¼
				map=(Map.Entry)it2.next();
				clsKey=(String)map.getKey();
				clsVal=(String)map.getValue();
				instVal=(String)instRecord.get(clsKey);
				if(clsVal==null)clsVal="";
				if(instVal==null)instVal=""; 
				if (clsVal.equals(instVal))intInstEqualCount++;
				else break;
			}
			//���ÿ���ֶζ���ȣ���ô������¼������������Ҫ����
			if (intInstEqualCount==intClsEqualCount){
				throw new Exception("uniqued primary key:"+primaryKeyDesc.toString().substring(1));
			}
			lstTmpInst.add(fields);
		}
		if(lstTmpInst!=null && !lstTmpInst.isEmpty()){//�����ֵ����ôput��ȥ
			lstData.addAll(lstTmpInst);
		}
	}
	//����id���¶���
	public void update(ArrayList lstData,HashMap whereCond,HashMap fields){
		//��������ǿգ���ô����ȫ�����
		if (whereCond==null || whereCond.isEmpty()) return; 
		//����һ������ֵ
		//List returnLstResult=new ArrayList();
		//�������Ĳ��ҳ��ǿգ���ô���ز��ҳ�
		if (lstData==null || lstData.isEmpty()) return;
		//�������Ϲ涨��Ҫ���ٸ��ֶ����
		int intClsEqualCount=whereCond.size();
		//����ʵ��ÿ����¼������ֶ���
		int intInstEqualCount=0;
		//����ʵ������ѭ��ʱ��ÿ���ֶε�ֵ�͹����ÿ���ֶ�ֵ
		String clsKey="",clsVal="",instKey="",instVal="";
		//����ʵ�����ݵ�һ����¼
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
			//���ÿ���ֶζ���ȣ���ô������¼������������Ҫ�Ѹ��µ��ֶθ���
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

	//ִ�б�����(ֻ֧��and�������ӣ�ÿ������ֻ֧��=��)
	public ArrayList innerJoin(ArrayList lList,ArrayList rList,String selectKey,String whereCond){
		//�����µ����ݼ�
		ArrayList lstRet=new ArrayList();
		//����select���ֶ�
		String [] lstSelectKey=null;
		if (selectKey!=null && !selectKey.equals(""))
			lstSelectKey=selectKey.split(",");
		
		//�ʷ�����,��whereCond����and�ָ�ɶ��1=2��ʽ���������ٰ�1=2��ʽ������,�ֱ�put������String[]��
		if (whereCond==null || whereCond.equals("")){
			//���⴦�������ѿ����˻�
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
					}else{//ȫ����key
						retVo.putAll(lVo);
						retVo.putAll(rVo);
					}
					lstRet.add(retVo);
				}
			}
			lList=null;//ȥ��ԭ��������
			lList=lstRet;//ָ���µ�����
			return lstRet;
		}
		
		//ƥ������
		String[] andWhere=whereCond.split(" and ");
		if (andWhere!=null && andWhere.length>0){
			int andWhereLength=andWhere.length;
			String[] lWhere=new String[andWhereLength];
			String[] rWhere=new String[andWhereLength];
			for(int i=0;i<andWhereLength;i++){
				String oneWhereUnit=andWhere[i];//���unit����ʽ��1=1
				String []strTmpUnit=oneWhereUnit.split("=");
				lWhere[i]=strTmpUnit[0];
				rWhere[i]=strTmpUnit[1];
			}
			//��ʱ�õ���������String[]��װ��=�����ߵ�ֵ
			//�Ƚ�String��ÿ���ֶε�ֵ�Ƿ��У�����У���ô�Ͱ������¼��vo put��ȥ
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
						}else{//ȫ����key
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
