/**
 * IsmpVSOPEngine_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp.active;

public interface IsmpVSOPEngine_PortType extends java.rmi.Remote {
    public com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPResp subActionToISMP(com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPReq req) throws java.rmi.RemoteException;
    public com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionFromVSOPResp unSubActionToISMP(com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionToISMPReq req) throws java.rmi.RemoteException;
    public com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPResp subsInstSynFromISMP(com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPReq req) throws java.rmi.RemoteException;
}
