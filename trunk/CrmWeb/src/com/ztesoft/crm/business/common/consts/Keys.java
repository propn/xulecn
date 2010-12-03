package com.ztesoft.crm.business.common.consts;

public final class Keys {

	
	//��������
	public final static String SERVICE_TYPE = "service_type";	
	// ҵ����
	public final static String ACTION_TYPE = "action_type";
	// �ͻ�����
	public final static String CUST_ORD_ID = "cust_ord_id";
	// ������ʶ
	public final static String ORD_ID = "ord_id";
	// �������ʶ
	public final static String ORD_ITEM_ID = "ord_item_id";
	// ����Ʒʵ����ʶ
	public final static String PRODUCT_OFFER_INSTANCE_ID = "product_offer_instance_id";
	// ������Ʒʵ����ʶ
	public final static String COMP_INST_ID = "comp_inst_id";
	// �ֶ���
	public final static String FIELD_NAME = "field_name";	
	//�ֶ�ֵ
	public final static String FIELD_VALUE = "field_value";
    //�ֶ�ԭֵ
	public final static String OLD_FIELD_VALUE = "old_field_value";
	//Ӫҵ����	
	public final static String STAFF_NO="staff_no";	
	//Ӫҵ����	
	public final static String SITE_NO="site_no";	
	//�����ṩ��ʶ	
	public final static String SERVICE_OFFER_ID="service_offer_id";	
	//������־
	public final static String OPER_FLAG="operFlag";	
	//��Ʒʵ����ʶ
	public final static String SERV_ID = "serv_id";
	//�ʻ���ʶ
	public final static String ACCT_ID = "acct_id";
	//��Ŀ���ʶ
	public final static String ACCT_ITEM_GROUP_ID = "acct_item_group_id";
	//ͣ������
	public final static String STOP_TYPE = "stop_type";
    
	public final static String BILL_TYPE = "bill_type";
	
	public final static String ACC_NBR_TYPE_CD = "acc_nbr_type_cd";
	
	//��ϵ��ʶ
	public final static String REL_ID = "rel_id";	
	//��ϵ����
	public final static String REL_TYPE = "rel_type";
    //����Ʒ��ϸ��ʶ
	public final static String OFFER_DETAIL_ID = "offer_detail_id";
	//����Ʒ��ʶ
	public final static String PRODUCT_OFFER_ID = "product_offer_id";	
	//�ͻ�ID
	public final static String CUST_ID = "cust_id";	
	//�����ıȶԷ���diff��ʹ��
	public final static String STATE_DATE="state_date";
	public final static String END_TIME ="end_time";
	public final static String BEG_TIME="beg_time";
	
	// ����Ψһ��ʶ��Ʒ/������ڲ����
	public final static String PRODUCT_ID = "product_id";	
	//������Ʒʵ����ʶ
	public final static String SERV_PRODUCT_ID = "serv_product_id";
	//�˻�ʵ����ʶ
	public final static String SERV_ACCT_ID = "serv_acct_id";	
	//����Ʒ��ʶ
	public final static String OFFER_ID = "offer_id";	
	//��Ʒ����
	public final static String PRODUCT_NAME = "product_name";	
	//ͬһ������
	public final static String SAME_ASK_NO = "same_ask_no";	
	//���Ա�ʶ
	public final static String ATTR_ID = "attr_id";	
	//����ȡֵ
	public final static String ATTR_VAL = "attr_val";	
	//���к�
	public final static String SEQ = "seq";
	//��Ʒ�����ʶ
	public final static String PRODUCT_FAMILY_ID = "product_family_id";
	//Ӫҵ��
	public final static String BUSINESS_ID = "business_id";
	//ͬ�ʶ�����
	public final static String ASK_ID = "ask_id";
	//������
	public final static String LAN_ID = "lan_id";
	//��Ӧ���ƷѵĻ�����Ʒ
	public final static String PRICE_ID = "price_id";
	//�漰ʵ����Ψһ��ʶ
	public final static String INSTANCE_ID = "instance_id";
    //����ʱ��
    public final static String CREATE_DATE = "create_date";
	
	
	
	
	
	
	
	
	
	//-------------------------------��-----------------------
	//��Ʒʵ����
	public final static String TABLE_SERV ="serv";	
	//��Ʒʵ����������
	public final static String TABLE_SERV_ATTR = "serv_attr";
	//������Ʒʵ����
	public final static String TABLE_SERV_PRODUCT = "serv_product";
	//������Ʒʵ����������
	public final static String TABLE_SERV_PRODUCT_ATTR = "serv_product_attr";
	//���ƹ�ϵ
	public final static String TABLE_SERV_ACCT = "serv_acct";	
	//����Ʒʵ����
	public final static String TABLE_OFFER_INST = "offer_inst";
	//����Ʒʵ������
	public final static String TABLE_OFFER_INST_ATTR = "offer_inst_attr";	
		
    //-------------------------------��-----------------------

    
    //�������������ݱȽ�ʱ����Ҫ�Ƚϵ��ֶ�
    public static final String IGNORE_FIELD_SERV               = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,";
    public static final String IGNORE_FIELD_SERV_ATTR          = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,";
    public static final String IGNORE_FIELD_SERV_PRODUCT       = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,";
    public static final String IGNORE_FIELD_SERV_PRODUCT_ATTR  = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,";
    public static final String IGNORE_FIELD_OFFER_INST         = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,create_date,eff_date,exp_date,"; //��Чʱ����ʱû�бȽ�
    public static final String IGNORE_FIELD_OFFER_INST_ATTR    = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,";
    public static final String IGNORE_FIELD_OFFER_INST_DETAIL  = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,eff_date,exp_date,";
    public static final String IGNORE_FIELD_SERV_ACCT          = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time," ;

}
