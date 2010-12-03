
/**
 * ServerActionToISMPSVCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */

    package com.ztesoft.vsop.webservice.skeleton;

    /**
     *  ServerActionToISMPSVCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ServerActionToISMPSVCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ServerActionToISMPSVCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ServerActionToISMPSVCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for subActionFromVSOP method
            * override this method for handling normal response from subActionFromVSOP operation
            */
           public void receiveResultsubActionFromVSOP(
                    com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from subActionFromVSOP operation
           */
            public void receiveErrorsubActionFromVSOP(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for chgActionFromVSOP method
            * override this method for handling normal response from chgActionFromVSOP operation
            */
           public void receiveResultchgActionFromVSOP(
                    com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from chgActionFromVSOP operation
           */
            public void receiveErrorchgActionFromVSOP(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unSubActionFromVSOP method
            * override this method for handling normal response from unSubActionFromVSOP operation
            */
           public void receiveResultunSubActionFromVSOP(
                    com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unSubActionFromVSOP operation
           */
            public void receiveErrorunSubActionFromVSOP(java.lang.Exception e) {
            }
                


    }
    