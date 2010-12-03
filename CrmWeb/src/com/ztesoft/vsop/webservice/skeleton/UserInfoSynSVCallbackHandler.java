/**
 * UserInfoSynSVCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


/**
 *  UserInfoSynSVCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class UserInfoSynSVCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public UserInfoSynSVCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public UserInfoSynSVCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for userInfoSynSV method
     * override this method for handling normal response from userInfoSynSV operation
     */
    public void receiveResultuserInfoSynSV(
        com.ztesoft.vsop.webservice.vo.UserInfoSynSVReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from userInfoSynSV operation
     */
    public void receiveErroruserInfoSynSV(java.lang.Exception e) {
    }
}
