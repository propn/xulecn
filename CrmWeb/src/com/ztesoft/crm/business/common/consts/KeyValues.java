package com.ztesoft.crm.business.common.consts;

public final class KeyValues {

    
    public final static String NEGATIVE_ONE = "-1"; //默认-1型   
    
    
    public final static String SPLIT_STRING = ","; //逗号分隔符
    
	//业务动作
	public final static String ACTION_TYPE_A = "A"; //动作为新增/添加	
	public final static String ACTION_TYPE_D = "D"; //动作类型为删除/取消	
	public final static String ACTION_TYPE_K = "K"; //动作类型未做修改	
	public final static String ACTION_TYPE_M = "M"; //动作类型为修改类型
    
	
    //对数据库操作类型
	public final static String OPER_FLAG_A = "A"; //操作类型为新增/添加	
	public final static String OPER_FLAG_D = "D"; //操作类型为删除/取消	
	public final static String OPER_FLAG_K = "K"; //操作类型未做修改	
	public final static String OPER_FLAG_M = "M"; //操作类型为修改类型
	
	
    //真假标识
	public final static String IFTRUE_T = "T";	 //条件为TRUE	
	public final static String IFTRUE_F = "F";     //条件为FALSE

    
    //状态
    public static final String STATE_USING      = "00A";//有效
    public static final String STATE_CANCEL     = "00X";//注销
    public static final String STATE_ARCHIVE    = "00H";//归档
    public static final String STATE_ONWAY      = "00N";//在途
}
