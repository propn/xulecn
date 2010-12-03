package com.ztesoft.crm.business.common.logic.auto;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.crm.business.common.cache.SpecsData;
import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.logic.model.ServProduct;
/**
 * @author liupeidawn
 *
 */
public class DelServProduct implements AutoProcessor {

	
	/* ��ϵͳ�Զ�����ӪҵԱ�ֹ���������Ӧ�ĸ�����Ʒ��ϵͳͨ���ù����Զ�ɾ������ĸ�����Ʒ������ɾ�������ڸø�����Ʒ���Żݣ�ϵͳ���ڹ���У���ʱ����ʾ����ɾ��������Ʒ��
	 * �����롿Serv serv 

	 */
	public void execute(ProcessParameter parameter) throws Exception {
		List servProds = parameter.serv.getServProducts();//��ȡ����Ʒ�µĸ�����Ʒ�б�
		List useServProds = new ArrayList();//���õĸ�����Ʒ
		List addServProds = new ArrayList();//����Ϊ���ӵĸ�����Ʒ
		if(servProds!=null&&servProds.size()!=0){
			for(int i =0;i<servProds.size();i++){
				ServProduct servProd = (ServProduct)servProds.get(i);
				if(ServConsts.ACTION_TYPE_A.equals(servProd.get(Serv.ACTION_TYPE))){
					addServProds.add(servProd);
				}else{
					useServProds.add(servProd);
				}
				
			}
		/***************************���������õ����Ƚ� ɾ�����õ��������Ļ���ĸ�����Ʒ**********************/	
		 List addMutexServProds = SpecsData.getMutexServProds(addServProds,"A");//������������Ʒ����Ĳ�Ʒ�б�
		 if(addMutexServProds!=null && addMutexServProds.size()!=0){
				for(int i=0;i<addMutexServProds.size();i++){
						ServProduct addMutexServProd = (ServProduct)addMutexServProds.get(i);
						for(int j=0;j<useServProds.size();j++){
							ServProduct useServProd = (ServProduct)useServProds.get(j);
							//��������б�ĸ�����Ʒ�������б��д���  ��ô �����ó�D
							if(useServProd.get(Serv.PRODUCT_ID).equals(addMutexServProd.get(Serv.PRODUCT_ID))){
								ServProduct curServProd = parameter.serv.getServProductById(useServProd.get(Serv.PRODUCT_ID));
								//��������õĸ�����ƷΪ��ѡ ��ô��Ҫ���� ��ȥɾ���������Ļ����ѡ������Ʒ ���� ֱ����Ϊɾ������
								if(ServConsts.RESTRICT_TYPE_3.equals(curServProd.get("restrict_type"))){
									List curServProds = new ArrayList();
									curServProds.add(curServProd);
									List addRemoveList = SpecsData.getMutexServProds(curServProds,"A");
									SpecsData.removeMutexServProd(addRemoveList, addServProds, parameter);
									
								}else{
									curServProd.set(Serv.ACTION_TYPE, "D");//���ó�ɾ��
								}
							}
							
						}
					}
		 		}
		 /***************************���������������Ƚ� ɾ������һ��**********************/	
		 List mutexServProds = new ArrayList();
		 mutexServProds = SpecsData.getMutexServProds(addServProds,"A");//������������Ʒ����Ĳ�Ʒ�б�(A��)
			 if(mutexServProds!=null && mutexServProds.size()!=0){
				 for(int i=0;i<addMutexServProds.size();i++){
					 ServProduct mutexServProd = (ServProduct)mutexServProds.get(i);
					 for(int j=0;j<addServProds.size();j++){
						 ServProduct addServProd = (ServProduct)addServProds.get(j);
						 if(addServProd.get(Serv.PRODUCT_ID).equals(mutexServProd.get(Serv.PRODUCT_ID))){//ֱ��ɾ��
							 parameter.serv.delServProductByID(addServProd.get(Serv.PRODUCT_ID));
							 addServProds.remove(j);
						 }
					 }
				 }
			 }
/*			 mutexServProds = new ArrayList();
			 mutexServProds = SpecsData.getMutexServProds(addServProds,"Z");//������������Ʒ����Ĳ�Ʒ�б�(Z��)
			 if(mutexServProds!=null && mutexServProds.size()!=0){
				 for(int i=0;i<addMutexServProds.size();i++){
					 ServProduct mutexServProd = (ServProduct)mutexServProds.get(i);
					 for(int j=0;j<addServProds.size();j++){
						 ServProduct addServProd = (ServProduct)addServProds.get(j);
						 if(addServProd.get(Serv.PRODUCT_ID).equals(mutexServProd.get(Serv.PRODUCT_ID))){//ֱ��ɾ��
							 parameter.serv.delServProductByID(addServProd.get(Serv.PRODUCT_ID));
							 addServProds.remove(j);
						 }
					 }
				 }
			 }*/	 
			}
		}
	
	

		
}
	
	
	


