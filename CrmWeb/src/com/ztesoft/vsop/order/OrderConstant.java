package com.ztesoft.vsop.order;

public interface OrderConstant {
	
	/*
	 * ����������
	 */
	public static final String ORDER_ITEM_CD_CUST = "01"; //ORDER_ITEM_CD ���������ͷ�Ϊ��01=�ͻ�������
	public static final String ORDER_ITEM_CD_ACC = "02"; //����������,02=�˻�������
	public static final String ORDER_ITEM_CD_PRODOFFER = "03"; //ORDER_ITEM_CD ���������� 03=����Ʒ������
	public static final String ORDER_ITEM_CD_PRODUCT = "04"; //ORDER_ITEM_CD ���������� 04=��Ʒ������
	/*
	 * ��ֵ��Ʒ����
	 */
	public static final String PROD_OFFER_TYPE_PRODUCT_OFFER_ID = "0";  //����ֵ����Ʒ
	public static final String PROD_OFFER_TYPE_PACKAGE_ID = "1";    //��ֵ�����ײ�
	public static final String PROD_OFFER_TYPE_PPROD_OFFER_ID = "2";  //����ͳ+��ֵ�������ײ�
	//	��������
	public static final String orderTypeOfAdd = "0"; //����
	public static final String orderTypeOfDel = "1"; //�˶�
	public static final String orderTypeOfAll = "2"; //ȫ���˶�
	//����ͨ�����Ķ�������
	public static final String orderTypeOfInstall = "10"; //��װ
	public static final String orderTypeOfModifyState = "11";//�û�״̬���
	public static final String orderTypeOfModifyNo = "12"; //�ĺ�
	public static final String orderTypeOfModifyAProduct = "13"; //��ҵ������������Ʒ
	public static final String orderTypeOfModifyVProduct = "14"; //����ֵ��Ʒ
	public static final String orderTypeOfUninstall = "15"; //���
	public static final String orderTypeOfModifyPayType = "16"; //�������ͱ��
	public static final String orderTypeOfModifyService = "20"; //�ķ�����
	
	public static final String orderStateOfCreated = "00A"; //��Ч
	public static final String orderStateOfUnconfirm = "00B"; //��ȷ��
	public static final String orderStateOfDelete = "00X"; //ʧЧ
	
	public static final String VProductActionTypeOfAdd = "0";  //�¶���Ʒ
	public static final String VProductActionTypeOfPendding = "01";  //�������в�Ʒ
	public static final String VProductActionTypeOfReplace = "2";  //�滻���в�Ʒ
	public static final String VProductActionTypeOfDel = "3";  //�˶���Ʒ
	public static final String VProductActionTypeOfCancelFromPackage = "4";  //��Ʒ�˳��ײ�
	
	public static final String ORDER_ACTIVE_STATE_UNACTIVE = "0";  //������
//	public static final String ORDER_ACTIVE_STATE_UNACTIVE = "1";  //
	public static final String ORDER_ACTIVE_STATE_SUCCESS = "2";  //����ɹ�
	
	public static final String PROD_SPEC_CODE_TELEPHONE = "001"; //�̻�
	public static final String PROD_SPEC_CODE_PHS = "002";  //С��ͨ
	public static final String PROD_SPEC_CODE_MOBILE = "003";  //�ֻ�
	public static final String PROD_SPEC_CODE_WAN = "004";  //���
	
}
