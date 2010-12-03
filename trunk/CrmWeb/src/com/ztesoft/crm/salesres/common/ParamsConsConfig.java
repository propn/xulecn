package com.ztesoft.crm.salesres.common;

public class ParamsConsConfig {
    public static String RcEntityFreeState = "A"; //����״̬;
    public static String RcEntityUseState = "B"; //ռ��״̬;
    public static String RescState_damage = "C"; // ��
    public static String RescState_reserve = "D"; // Ԥ��
    public static String RescState_preOccupy = "E"; // Ԥռ
    public static String RescState_test = "F"; // ����
    public static String RescState_recycle = "G"; // ��ʧ
    public static String RescState_frozen = "Q"; // ����
    public static String RescState_preAssemble = "R"; // Ԥ��
    public static String RescState_preMadeCard = "T"; // ���ƿ�
    public static String RescState_preMakeCard = "d"; // ���ƿ�
    public static String RescState_writeCard = "e"; // д��
    public static String RescState_preSelect = "U"; // Ԥѡ��
    public static String RescState_preSetUse = "0"; // Ԥ��ͨ
    public static String RescState_fixing = "A1"; // ά��
    
    public static String Rc_No_FamilyId = "101";
    public static String Rc_SIM_FamilyId = "102";
    public static String Rc_Terminal_FamilyId = "103";
    public static String Rc_Card_FamilyId = "104";
    public static String RELEASE_PRE_OCC_NO_TIME = "3600";
    public static String Rc_No_Recycle = "36";//������ռ������
    
    

    /** Ӫ����Դʵ������Ч״̬ **/
    public static final String RcEntityStateValide = "00A";

    /** Ӫ����Դʵ������Ч״̬ **/
    public static final String RcEntityStateInValide = "00X";
    public static String RcEntityOrgType = ""; //�һ���ʱ������һ�����Դ��Ӧ����֯�ļ���,ע�⣬����Ƕ������Ҫд�� '3','4','5' �ĸ�ʽ
    public static String ObjTypeOfProduct = "002"; //����CRMϵͳ 002 ������������ǲ�Ʒʵ��
    public static ParamsConsConfig paramsConfig = null;
    public static String PROV_ID_JX = "2"; //����csss��־
    public static String PROV_ID_GX = "20"; //����crm��־
    public static String PROV_ID_HN = "11"; //����csss��־
    public static String PROV_ID_CQ = "31"; //����crm��־
    public static String PROV_ID_TJ = "3"; //���crm��־
    public static String SALESRESC_MANAGE_MODEL_ENTITY = "0"; //Ӫ����Դ����ģʽΪʵ��洢
    public static String SALESRESC_MANAGE_MODEL_MASS = "1"; //Ӫ����Դ����ģʽΪ�����洢
    public static String RcEntity_USAGE_STATE_INSTORAGE = "n"; //��Դʵ����;֮�ڿ��
    public static String RcEntity_USAGE_STATE_STANDBY = "b"; //��Դʵ����;֮���ݻ�
    public static String RcEntity_USAGE_STATE_TEST = "t"; //��Դʵ����;֮���Ի�
    public static String SEQ_USAGE_LOG_ID = "seq_usage_log_id";
    public static String RcUsageLog_TRAIN_TYPE_INSTORAGE_TO_STANDBY = "2";
    public static String RcUsageLog_TRAIN_TYPE_STANDBY_TO_INSTORAGE = "1";
    public static String rcUsage_Issue_CUSTNAME = "0";
    public static String rcUsage_Issue_CUSTNO = "1";
    public static String rcUsage_Issue_CUSTIDNO = "2";
    public static String rcUsage_Issue_USERNO = "3";
    public static String rcUsage_Issue_STAFFNO = "10";
    public static String rcUsage_Issue_STAFFNONAME = "11";

    public static String rcUsage_Issue_Way_CUST="0";
    public static String rcUsage_Issue_Way_STAFF="1";
    public static String rcUsage_Issue_Way_OTHER="2";
    public static String rcUsage_RETURN_FLAG_DEFAULT="1";
    public static String rcUsage_RETURN_FLAG_RETURNED="0";
    
    
    public static String TABLENOEXISTS="940";
    public static String COLUMNNOTNULL="1400";
    public static String PKCONSTRAINTS="1";
    
    //public static String Rc_No_FamilyId = "Rc_No_FamilyId";
    //public static String Rc_SIM_FamilyId = "Rc_SIM_FamilyId";
    /**
     * ��ȡʵ������
     * @return
     */
    public static ParamsConsConfig getInstance() {
        if (paramsConfig == null) {
            paramsConfig = new ParamsConsConfig();
        }

        return paramsConfig;
    }
}
