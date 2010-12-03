package com.ztesoft.crm.business.common.logic.auto;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.ztesoft.crm.business.common.cache.AttrRestrict;
import com.ztesoft.crm.business.common.cache.SpecsData;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.logic.tools.DataLoader;

/**
 * Ĭ������Ʒʵ����Ϣ����
 * @author liupeidawn
 *
 */
public class DefServAttr implements AutoProcessor {

	/*
	 * @author lp
	 * ������һ������Ʒʵ������������Ʒʵ��������Ʒ��Ǩ���ʷѱ���������µ��������Ʒ��ʱ�򣬿��Ե��ø÷���������Լ����Ҫ�������������Ե�ȡֵ��
	 * �����롿Serv serv List offerDetailIds Map common 
	 */
	public void execute(ProcessParameter parameter)throws Exception {
		String servAction = parameter.serv.get(Serv.ACTION_TYPE);
		if(!ServConsts.ACTION_TYPE_A.equals(servAction)){//�����Ϊ���� ��Ҫ��ѯ�û�����ʵ������
			HashMap servMap = (HashMap)DataLoader.getInst().selectServData(parameter.serv.get(Serv.SERV_ID));
			//��������� ��Ҫ�����±������Ϣ���õ�SERV������
			//HashMap cloneMap=new HashMap();
			Iterator  uIte=servMap.keySet().iterator();
			while(uIte.hasNext()){
				 String uKey=(String)uIte.next();//���±������ֶ���
				 String uValue = (String)servMap.get(uKey);//���±������ֶ�ֵ
				 
				 String nValue = parameter.serv.get(uKey);//ǰ̨��Ӧ�ֶε�ֵ
				 String[] feilds = BusiTables.SERV.TABLE_FIELDS;
				 boolean continuFlag = true;
				 for(int i = 0;i<feilds.length;i++){
					 if(feilds[i].equals(uKey)){//���SERV��������KEYֵ ����������Ϣ ���ﲻ��������Ϣ������
						 continuFlag = false;
						 break;
					 }
				 }
				 if(continuFlag) continue;
				 if(nValue==null||"".equals(nValue)){
					 parameter.serv.set(uKey, uValue);
				 }
			}
		}
		//��������Ϣ����Լ��Ĭ�ϴ���
		if(parameter.offerDetailIds!=null && parameter.offerDetailIds.size()!=0){
			List servAttrRestricts  = SpecsData.getServAttrRestricts(parameter.offerDetailIds);
			if(servAttrRestricts!=null&&servAttrRestricts.size()!=0){
				for(int i = 0;i<servAttrRestricts.size();i++){
					AttrRestrict servAttrRestrict = (AttrRestrict)servAttrRestricts.get(i);
					String fieldname = servAttrRestrict.getField_name();
					String nValue = parameter.serv.get(fieldname);//��ȡ��ǰֵ
					
					HashMap checkMap = (HashMap)servAttrRestrict.isMeet(nValue);//����Լ��У��
					
					String defValue  = (String)checkMap.get("defvalue");
					String check =  (String)checkMap.get("check");
					if ("false".equals(check)){//�����ǰֵΪ�� ��ô���ó�Ĭ��ֵ
						parameter.serv.set(fieldname,defValue);
					}
				}
			}
		}

	}
	

}
