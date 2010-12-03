package com.ztesoft.vsop.webservice.client;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.jx.ismp.IsmpCrmEngineSoapBindingStub;
import com.ztesoft.vsop.webservice.jx.ismp.req.NotifyUserStateReq;

/**
 * 用户状态同步客户端接口
 * @author yi.chengfeng
 *
 */
public class UserStateNotify {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	/**
	 * 用户状态同步
	 * VSOP->ISMP
	 * 江西本地
	 * @author yi.chengfeng 2010-8-6
	 * @param srcDeviceID 
	 * @param state 
	 * @param userID 
	 * @param streamingNo 
	 * @param userIDType 
	 * @return
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 */
	public int notifyUserStateOfJx(String srcDeviceID, String state, String userID, String streamingNo, String userIDType) throws MalformedURLException, RemoteException{
		String ismpServiceUrl = DcSystemParamManager.getParameter(VsopConstants.ISMP_SERVICE_URL);
		IsmpCrmEngineSoapBindingStub stub = new IsmpCrmEngineSoapBindingStub(new java.net.URL(ismpServiceUrl ), null);
		NotifyUserStateReq notifyUserStateReq = new NotifyUserStateReq();
		notifyUserStateReq.setSrcDeviceID(srcDeviceID);
		notifyUserStateReq.setState(state);
		notifyUserStateReq.setUserID(userID);
		notifyUserStateReq.setStreamingNo(streamingNo);
		notifyUserStateReq.setUserIDType(userIDType);
		com.ztesoft.vsop.webservice.jx.ismp.rsp.Response resp = stub.notifyUserState(notifyUserStateReq );
		return resp.getResultCode();
	}

}
