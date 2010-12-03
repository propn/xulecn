/**
 * ActiveSVCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.ordermonitor.webservice;


/**
 *  ActiveSVCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class ActiveSVCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public ActiveSVCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public ActiveSVCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for active method
     * override this method for handling normal response from active operation
     */
    public void receiveResultactive(
        com.ztesoft.vsop.ordermonitor.webservice.vo.ActiveResp result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from active operation
     */
    public void receiveErroractive(java.lang.Exception e) {
    }
}
