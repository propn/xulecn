package com.ztesoft.crm.business.common.cache;

import java.util.List;
import java.util.Map;

public class ProductOfferDetail {

	
//	OFFER_DETAIL_ID,COMP_ROLE_ID,OFFER_ID,ROLE_MIN_NUM,ROLE_MAX_NUM,ELEMENT_TYPE,ELEMENT_ID,ELEMENT_DESC
	//OBJECT_AMOUNT_START,OBJECT_AMOUNT_END,COMP_ROLE_NAME
	
	String offer_detail_id;
	String comp_role_id;
	String offer_id;
	String role_min_num;
	String role_max_num;
	String element_type;
	
	String element_id;
	String element_desc;
	String object_amount_start;
	String object_amount_end;
	String comp_role_name;
	
	
	//�Գ�Ա���Ե�ȡֵԼ��
	public List attrRestrict;
	
	public Map  priceRestrict;//����Ʒ����͵ı�׼�ʷѣ����ֻ�������
	
	public List servProductRestrict;
	

}
