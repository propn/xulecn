package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;

import com.ztesoft.component.common.staticdata.service.StaticAttrService;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * ҵ��������Ȩ
 */
public class Rule4 implements PartRuleinterface{
	public HashMap authentication(HashMap map){
		SpProductVo spProductVoIn = (SpProductVo)map.get("SpProductVo");
		String serviceId = spProductVoIn.getServiceId();
		String productNo = spProductVoIn.getProductNo();
		String serviceName = spProductVoIn.getNameCn();
		String serviceCapabilityId = spProductVoIn.getServiceCapabilityId();
		ArrayList productInfoLst = (ArrayList)map.get("productInfoLst");
		HashMap resultMap = new HashMap();
		boolean flag = false;
		String SPProdSpecName = serviceName;
		String capabilityId = "";
		try{
		String[] serviceCapabilityStr = serviceCapabilityId.split(",");
		if(productInfoLst!=null&&!serviceCapabilityId.equals("")){
			for(int i=0;i<productInfoLst.size();i++){
				ProductVo productVo = (ProductVo)productInfoLst.get(i);
				if(productVo.getProductNo().equals(productNo)){
					for(int m=0;m<serviceCapabilityStr.length;m++){
						String id = serviceCapabilityStr[m];
						capabilityId = id;
						flag = false;
						ArrayList serviceCapabilityLst = productVo.getServiceCapability();
						if(serviceCapabilityLst!=null && serviceCapabilityLst.size()>0){
							for(int n=0;n<serviceCapabilityLst.size();n++){
								HashMap serviceCapabilityMap = (HashMap)serviceCapabilityLst.get(n);
								if(id.equals((String)serviceCapabilityMap.get("productId"))){
									flag = true;
								}
							}
						}
						if(!flag){
							break;
						}
					}
					
				}
			}
		}
		else{
			flag = true;
		}
		
		
		if(!flag){//ȱ�ٸ�ҵ������
			resultMap.put("Result","4");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","����"+SPProdSpecName+"ȱ��ҵ������"+findNameById(capabilityId));
		}
		else{
			resultMap.put("Result","0");//����ɹ�
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","");
		}
		}
		catch(Exception e){
			resultMap.put("Result","4");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","ҵ��������Ȩ:"+e.toString());
		}
		return resultMap;
	}
	
	public String findNameById(String Id) throws Exception{
		StaticAttrService StaticAttrService = new StaticAttrService();
		ArrayList valLst = StaticAttrService.getStaticAttr("dc_service_ability");
		String name = "";
		if(valLst!=null){
			for(int i=0;i<valLst.size();i++){
				StaticAttrVO staticAttrVO= (StaticAttrVO)valLst.get(i);
				if(staticAttrVO.getAttrValue().equals(Id)){
					name = staticAttrVO.getAttrValueDesc();
				}
			}
		}
		
		return name;
	}
}
