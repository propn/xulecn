/**
 * SPCPSyncServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


/**
 *  SPCPSyncServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class SPCPSyncServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public SPCPSyncServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public SPCPSyncServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for CPSPInfoSync method
     * override this method for handling normal response from CPSPInfoSync operation
     */
    public void receiveResultCPSPInfoSync(
        com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from CPSPInfoSync operation
     */
    public void receiveErrorCPSPInfoSync(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for CPSPCapabilitySync method
     * override this method for handling normal response from CPSPCapabilitySync operation
     */
    public void receiveResultCPSPCapabilitySync(
        com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from CPSPCapabilitySync operation
     */
    public void receiveErrorCPSPCapabilitySync(java.lang.Exception e) {
    }
}
