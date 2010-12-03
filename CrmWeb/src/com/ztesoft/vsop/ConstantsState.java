package com.ztesoft.vsop;

public class ConstantsState {
	public static final String SecondConfirmMsg_UnDeal = "0";//0 待发送
	public static final String SecondConfirmMsg_SendSuccess = "1";//1发送成功
	public static final String SecondConfirmMsg_SendFailure = "2";//2发送失败
	public static final String SecondConfirmMsg_ReplySuccess = "3";//3回复成功
	public static final String SecondConfirmMsg_ReplyFailure = "4";//4回复失败
	public static final String SecondConfirmMsg_Invalid = "5";//作废
	public static final String SecondConfirmMsg_Dealing = "-1";//处理中
	
	public static final String SecondConfirmMsg_Unreply = "-1";//未回复
	public static final String SecondConfirmMsg_ReplyYes = "1";//回复 是
	public static final String SecondConfirmMsg_ReplyNo = "0";//回复 否
	
	public static final String USER_STATE_USED = "00A";  //有效
	public static final String USER_STATE_STOP = "00B";  //停机
	public static final String USER_STATE_00C = "00C";	//拆机
	public static final String USER_STATE_UNUSED = "00X";	//失效
	
	public static final String UserInfoSyncMsg_UnDeal = "0";//0 待发送
	public static final String UserInfoSyncMsg_SendSuccess = "1";//1发送成功
	public static final String UserInfoSyncMsg_SendFailure = "2";//2发送失败
	public static final String UserInfoSyncMsg_Invalid = "5";//作废
	public static final String UserInfoSyncMsg_Dealing = "-1";//处理中
	
	public static final String Active_Type_OrderBack="0";// 工单人工回单
	public static final String Active_Type_NeOrderReSendPlatforms="1";// 子工单重送业务平台
	public static final String Active_Type_NeOrderBack="2";// 子工单人工回单
	public static final String Active_Type_subProdreSendPlatforms="3";// 增值产品重送
	public static final String Active_Type_subProdFeedback="4";// 增值产品人工回单
	
}
