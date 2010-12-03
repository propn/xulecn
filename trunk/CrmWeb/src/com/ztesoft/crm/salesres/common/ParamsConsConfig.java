package com.ztesoft.crm.salesres.common;

public class ParamsConsConfig {
    public static String RcEntityFreeState = "A"; //可用状态;
    public static String RcEntityUseState = "B"; //占用状态;
    public static String RescState_damage = "C"; // 损坏
    public static String RescState_reserve = "D"; // 预留
    public static String RescState_preOccupy = "E"; // 预占
    public static String RescState_test = "F"; // 测试
    public static String RescState_recycle = "G"; // 丢失
    public static String RescState_frozen = "Q"; // 冻结
    public static String RescState_preAssemble = "R"; // 预配
    public static String RescState_preMadeCard = "T"; // 已制卡
    public static String RescState_preMakeCard = "d"; // 待制卡
    public static String RescState_writeCard = "e"; // 写卡
    public static String RescState_preSelect = "U"; // 预选卡
    public static String RescState_preSetUse = "0"; // 预开通
    public static String RescState_fixing = "A1"; // 维修
    
    public static String Rc_No_FamilyId = "101";
    public static String Rc_SIM_FamilyId = "102";
    public static String Rc_Terminal_FamilyId = "103";
    public static String Rc_Card_FamilyId = "104";
    public static String RELEASE_PRE_OCC_NO_TIME = "3600";
    public static String Rc_No_Recycle = "36";//号码回收间隔天数
    
    

    /** 营销资源实例中有效状态 **/
    public static final String RcEntityStateValide = "00A";

    /** 营销资源实例中无效状态 **/
    public static final String RcEntityStateInValide = "00X";
    public static String RcEntityOrgType = ""; //兑换的时候允许兑换的资源对应的组织的级别,注意，如果是多个级别要写成 '3','4','5' 的格式
    public static String ObjTypeOfProduct = "002"; //江西CRM系统 002 代表对象类型是产品实例
    public static ParamsConsConfig paramsConfig = null;
    public static String PROV_ID_JX = "2"; //江西csss标志
    public static String PROV_ID_GX = "20"; //广西crm标志
    public static String PROV_ID_HN = "11"; //河南csss标志
    public static String PROV_ID_CQ = "31"; //重庆crm标志
    public static String PROV_ID_TJ = "3"; //天津crm标志
    public static String SALESRESC_MANAGE_MODEL_ENTITY = "0"; //营销资源管理模式为实体存储
    public static String SALESRESC_MANAGE_MODEL_MASS = "1"; //营销资源管理模式为存量存储
    public static String RcEntity_USAGE_STATE_INSTORAGE = "n"; //资源实体用途之在库机
    public static String RcEntity_USAGE_STATE_STANDBY = "b"; //资源实体用途之备份机
    public static String RcEntity_USAGE_STATE_TEST = "t"; //资源实体用途之测试机
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
     * 获取实例对象
     * @return
     */
    public static ParamsConsConfig getInstance() {
        if (paramsConfig == null) {
            paramsConfig = new ParamsConsConfig();
        }

        return paramsConfig;
    }
}
