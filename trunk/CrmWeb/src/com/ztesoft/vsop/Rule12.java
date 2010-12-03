package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.order.PartRuleinterface;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * 
 * С��ͨ�û����ܶ���ȫ���Ĳ�Ʒ ȫ����Ʒ��135��ͷ�ģ�115��ͷ�Ǳ�ʡ�Ĳ�Ʒ 
 * c�����ܶ������ĳ���QQ��c�����Զ�ȫ���ĳ���qq
 *
 */
public class Rule12 implements PartRuleinterface{
	private static Logger logger = Logger.getLogger(Rule12.class);
	public HashMap authentication(HashMap map){
		
		
		//productIdҪ��crm�Ĳ�Ʒ���ͱ���ת����0:�ֻ�1��С��ͨ2���̻�
		String dcM = DcSystemParamManager.getParameter(VsopConstants.DC_MSISDN);
		String dcPhs = DcSystemParamManager.getParameter(VsopConstants.DC_PHS);
		//String dcPstn = DcSystemParamManager.getParameter(VsopConstants.DC_PSTN);

	
		SpProductVo spProductVoIn = (SpProductVo)map.get("SpProductVo");//���μ�Ȩ����ֵ��Ʒ
		String serviceId = spProductVoIn.getServiceId();//
		String productNo = spProductVoIn.getProductNo();
		String serviceName = spProductVoIn.getNameCn();
		String productNbr=spProductVoIn.getProductNbr();//��ֵ��Ʒ�����
		
		ArrayList productInfoLst = (ArrayList)map.get("productInfoLst");//�û���Ϣ
		HashMap resultMap = new HashMap();
		String SPProdSpecName = serviceName;
		try{
		String productNbr3=productNbr.substring(0,3);//��ֵ��Ʒǰ��λ
	
		if(productInfoLst!=null){
			for(int i=0;i<productInfoLst.size();i++){
				ProductVo productVo = (ProductVo)productInfoLst.get(i);
				if(productVo.getProductNo().equals(productNo)){
					String productId=productVo.getProductSpec();
					if(dcM.indexOf(productId) > -1){//C���ֻ�	

						if(VsopConstants.PRODUCTNBR_115_QQ.equals(productNbr)){//C�����û� ���ܶ���ȫʡ��qq��
							resultMap.put("Result","12");//���ʧ��
							resultMap.put("ProductNo",productNo);//����
							resultMap.put("SPProdSpecId",serviceId);
							resultMap.put("SPProdSpecName",SPProdSpecName);
							resultMap.put("FailureNote","C���û����ܶ���������ֵҵ��"+serviceName);
						}	else{//���Զ���
							resultMap.put("Result","0");//����ɹ�
							resultMap.put("ProductNo",productNo);//����
							resultMap.put("SPProdSpecId",serviceId);
							resultMap.put("SPProdSpecName",SPProdSpecName);
							resultMap.put("FailureNote","");
						}
					}
					if(dcPhs.indexOf(productId) > -1){//С��ͨ�û�
						if(VsopConstants.PRODUCTNBR_135.equals(productNbr3)){//С��ͨ���ܶ���ȫ����ֵ��Ʒ��
							resultMap.put("Result","12");//���ʧ��
							resultMap.put("ProductNo",productNo);//����
							resultMap.put("SPProdSpecId",serviceId);
							resultMap.put("SPProdSpecName",SPProdSpecName);
							resultMap.put("FailureNote","С��ͨ�û����ܶ���ȫ������ֵҵ��"+serviceName);
						}else{//���Զ���
							resultMap.put("Result","0");//����ɹ�
							resultMap.put("ProductNo",productNo);//����
							resultMap.put("SPProdSpecId",serviceId);
							resultMap.put("SPProdSpecName",SPProdSpecName);
							resultMap.put("FailureNote","");
						}
					}
				}
			}
		}
					
		}
		catch(Exception e){
			resultMap.put("Result","12");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","����ȫ����ֵ��Ʒ��Ʒ��Ȩ:"+e.toString());
		}
		return resultMap;
	}
}
