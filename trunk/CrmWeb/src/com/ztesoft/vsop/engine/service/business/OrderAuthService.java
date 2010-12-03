package com.ztesoft.vsop.engine.service.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.help.AuthenticationMainOutter;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
/**
 * ������Ȩ����
 * @author cooltan
 *
 */
public class OrderAuthService extends AbstractBusinessService {

	public OrderAuthService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(false);
	}

	public Map concreteBusinessOpertions(Map in) throws Exception {
		//1������Ȩ
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		AuthenticationMainOutter aAuthenticationMainOutter=new AuthenticationMainOutter();
		Map authOut=new HashMap();
		aAuthenticationMainOutter.auth(order, authOut);
		//2�����ؽ��
		String resultCode=null;
		String resultMsg=null;
		List resultInfoList=null;
		
		boolean success=true;
		if(order!=null){
			List offerList=order.getProductOfferInfoList();
			if(offerList!=null&&offerList.size()>0){
				for(java.util.Iterator it=offerList.iterator();it.hasNext();){
					ProductOfferInfo aProductOfferInfo=(ProductOfferInfo)it.next();
					List vproductList=aProductOfferInfo.getVproductInfoList();
					if(vproductList==null||vproductList.size()<=0){
						success=false;
						break;
					}
				}
			}else{
				success=false;
			}
		}
		if(!success){
			in.put("resultCode", "20");
			in.put("resultMsg", "ϵͳ�����쳣��û�ж���/�˶���ֵ��Ʒ������ϵ����Ա");
			in.put("retBusiObject", new java.util.ArrayList());
			return in;
		}
		
		
		if(authOut!=null){
			//ResultCode=0��Ȩͨ�� ResultDesc����Ȩ���� resultInfo����Ȩ��ͨ����ֵ��Ʒ����
			resultCode=(String)authOut.get("ResultCode");
			resultMsg=(String)authOut.get("ResultDesc");
			resultInfoList=(List)authOut.get("resultInfo");
		}
		
		in.put("resultCode", resultCode);
		in.put("resultMsg", resultMsg);
		in.put("retBusiObject", resultInfoList);
		return in;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
