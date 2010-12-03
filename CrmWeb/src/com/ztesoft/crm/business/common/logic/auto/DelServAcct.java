package com.ztesoft.crm.business.common.logic.auto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.OfferConsts;
import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.OfferInstDetail;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.customer.custinfo.dao.AcctDAO;
/**
 * @author liupeidawn
 *
 */	
public class DelServAcct implements AutoProcessor {

	/* �ڱ�������Ʒʵ����Ϣ��ʱ��ͨ���ù�����Ĭ�ϸ�����Ա����Ʒ����������Ϣ
	 * �����롿List servs ,CompInst compInst
	 */
	public void execute(ProcessParameter parameter) throws Exception {
		CompInst compInst = parameter.compInst;
		List servs = parameter.servs;
		
		String compAcctFlag = "0";//���Դ���  1Ϊ���� ����Ϊ����Ҫ ����ʵ������Ҫ��ǰ̨��Ϣ��ȡ
		
		String offer_kind = compInst.get(CompInst.OFFER_KIND);
		//�����һ����������Ʒʵ�������Ҳ�������Ϊ����,��ô��ȡ�����ͻ���ȱʡ�˻�������������Ϣ��Ȼ�󷵻ء�
		if(OfferConsts.OFFER_KIND_0.equals(offer_kind)){
			List offerInstDetails = compInst.getOfferInstDetails();
			if(offerInstDetails!=null && offerInstDetails.size()!=0){
				for(int i =0;i<offerInstDetails.size();i++){
					OfferInstDetail offerInstDetail = (OfferInstDetail)offerInstDetails.get(i);
					//��ȡ��ϸ��Ӧ�Ĳ�Ʒʵ��ID
					String instanc_id = offerInstDetail.get(BusiTables.OFFER_INST_DETAIL.instance_id);
					if(servs!=null && servs.size()!=0){
						for(int j =0;j<servs.size();j++){
							Serv serv = (Serv)servs.get(j);
							
							if(serv.get(Serv.SERV_ID).equals(instanc_id)){
								String custId = serv.get(BusiTables.SERV.cust_id);
								String defAcct = this.getDefAcct(custId);//���ݿͻ�ID��ȡĬ�ϵ��˻�
								serv.addServAcct("-1",defAcct);
							}
						}
						
					}
				}
			}
		}else if("1".equals(compAcctFlag)) {//�����һ���������Ʒ������Ҫ�����
			List offerInstDetails = compInst.getOfferInstDetails();
			if(offerInstDetails!=null && offerInstDetails.size()!=0){
				for(int i =0;i<offerInstDetails.size();i++){
					OfferInstDetail offerInstDetail = (OfferInstDetail)offerInstDetails.get(i);
					//��ȡ��ϸ��Ӧ�Ĳ�Ʒʵ��ID
					String instanc_id = offerInstDetail.get(BusiTables.OFFER_INST_DETAIL.instance_id);
					if(servs!=null && servs.size()!=0){
						for(int j =0;j<servs.size();j++){
							Serv serv = (Serv)servs.get(j);
							String custId = serv.get(BusiTables.SERV.cust_id);
							if(serv.get(Serv.SERV_ID).equals(instanc_id)){
								//����������õ�serv��ϸ����ô��ȡ��Ӧ�ĺ�ͬ����Ϊ�������񡣻�ȡ�����ȼ�Ϊ�̻���ADSL��CDMA��С��ͨ��
								if(!ServConsts.ACTION_TYPE_A.equals(serv.get(Serv.ACTION_TYPE))){
									//�˴���ʱ����
								}else{
									String defAcct = this.getDefAcct(custId);
									serv.addServAcct("-1",defAcct);
								}
							}
						}
					}
				}
			}
		}else{//�����Ҫ�����
			List offerInstDetails = compInst.getOfferInstDetails();
			if(offerInstDetails!=null && offerInstDetails.size()!=0){
				for(int i =0;i<offerInstDetails.size();i++){
					OfferInstDetail offerInstDetail = (OfferInstDetail)offerInstDetails.get(i);
					//��ȡ��ϸ��Ӧ�Ĳ�Ʒʵ��ID
					String instanc_id = offerInstDetail.get(BusiTables.OFFER_INST_DETAIL.instance_id);
					if(servs!=null && servs.size()!=0){
						for(int j =0;j<servs.size();j++){
							Serv serv = (Serv)servs.get(j);
							if(serv.get(Serv.SERV_ID).equals(instanc_id)){
								if(!ServConsts.ACTION_TYPE_A.equals(serv.get(Serv.ACTION_TYPE))){//����������Ͳ�ΪA����ô������
									continue;
								}
								String custId = serv.get(BusiTables.SERV.cust_id);
								String defAcct = this.getDefAcct(custId);
								serv.addServAcct("-1",defAcct);
							}
						}
						
					}
				}
			}
		}
	}
	
	/**
	 * ��ȡ�ͻ�ȱʡ�˻���־
	 * @param custId
	 * @return
	 */
	public String getDefAcct(String custId) throws Exception{
		String defAcct = "";
		AcctDAO acctDao = new AcctDAO();
		List whereCondParams = new ArrayList();
		whereCondParams.add(custId);
		List accList = acctDao.findByCond(" and cust_id = ? and def_acct_flag = 'Y' ", whereCondParams);
		if(accList!=null && accList.size()!=0){
			HashMap accMap = (HashMap)accList.get(0);
			defAcct = (String)accMap.get("acct_id");
		}
		return defAcct;
	}
}
