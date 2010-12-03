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

	
	/* 当系统自动或者营业员手工的增量相应的附属产品后，系统通过该功能自动删除互斥的附属产品，但不删除依赖于该附属产品的优惠，系统将在规则校验的时候提示不能删除附属产品。
	 * 【输入】Serv serv 

	 */
	public void execute(ProcessParameter parameter) throws Exception {
		List servProds = parameter.serv.getServProducts();//获取主产品下的附属产品列表
		List useServProds = new ArrayList();//在用的附属产品
		List addServProds = new ArrayList();//动作为增加的附属产品
		if(servProds!=null&&servProds.size()!=0){
			for(int i =0;i<servProds.size();i++){
				ServProduct servProd = (ServProduct)servProds.get(i);
				if(ServConsts.ACTION_TYPE_A.equals(servProd.get(Serv.ACTION_TYPE))){
					addServProds.add(servProd);
				}else{
					useServProds.add(servProd);
				}
				
			}
		/***************************新增与在用的做比较 删除在用的与新增的互斥的附属产品**********************/	
		 List addMutexServProds = SpecsData.getMutexServProds(addServProds,"A");//与新增附属产品互斥的产品列表
		 if(addMutexServProds!=null && addMutexServProds.size()!=0){
				for(int i=0;i<addMutexServProds.size();i++){
						ServProduct addMutexServProd = (ServProduct)addMutexServProds.get(i);
						for(int j=0;j<useServProds.size();j++){
							ServProduct useServProd = (ServProduct)useServProds.get(j);
							//如果互斥列表的附属产品在在用列表中存在  那么 就设置成D
							if(useServProd.get(Serv.PRODUCT_ID).equals(addMutexServProd.get(Serv.PRODUCT_ID))){
								ServProduct curServProd = parameter.serv.getServProductById(useServProd.get(Serv.PRODUCT_ID));
								//如果该在用的附属产品为必选 那么需要保留 而去删除掉新增的互斥必选附属产品 否则 直接置为删除动作
								if(ServConsts.RESTRICT_TYPE_3.equals(curServProd.get("restrict_type"))){
									List curServProds = new ArrayList();
									curServProds.add(curServProd);
									List addRemoveList = SpecsData.getMutexServProds(curServProds,"A");
									SpecsData.removeMutexServProd(addRemoveList, addServProds, parameter);
									
								}else{
									curServProd.set(Serv.ACTION_TYPE, "D");//设置成删除
								}
							}
							
						}
					}
		 		}
		 /***************************新增与新增的做比较 删除其中一个**********************/	
		 List mutexServProds = new ArrayList();
		 mutexServProds = SpecsData.getMutexServProds(addServProds,"A");//与新增附属产品互斥的产品列表(A端)
			 if(mutexServProds!=null && mutexServProds.size()!=0){
				 for(int i=0;i<addMutexServProds.size();i++){
					 ServProduct mutexServProd = (ServProduct)mutexServProds.get(i);
					 for(int j=0;j<addServProds.size();j++){
						 ServProduct addServProd = (ServProduct)addServProds.get(j);
						 if(addServProd.get(Serv.PRODUCT_ID).equals(mutexServProd.get(Serv.PRODUCT_ID))){//直接删除
							 parameter.serv.delServProductByID(addServProd.get(Serv.PRODUCT_ID));
							 addServProds.remove(j);
						 }
					 }
				 }
			 }
/*			 mutexServProds = new ArrayList();
			 mutexServProds = SpecsData.getMutexServProds(addServProds,"Z");//与新增附属产品互斥的产品列表(Z端)
			 if(mutexServProds!=null && mutexServProds.size()!=0){
				 for(int i=0;i<addMutexServProds.size();i++){
					 ServProduct mutexServProd = (ServProduct)mutexServProds.get(i);
					 for(int j=0;j<addServProds.size();j++){
						 ServProduct addServProd = (ServProduct)addServProds.get(j);
						 if(addServProd.get(Serv.PRODUCT_ID).equals(mutexServProd.get(Serv.PRODUCT_ID))){//直接删除
							 parameter.serv.delServProductByID(addServProd.get(Serv.PRODUCT_ID));
							 addServProds.remove(j);
						 }
					 }
				 }
			 }*/	 
			}
		}
	
	

		
}
	
	
	


