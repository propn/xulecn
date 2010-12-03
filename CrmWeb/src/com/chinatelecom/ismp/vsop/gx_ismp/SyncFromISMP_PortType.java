/**
 * SyncFromISMP_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp;

public interface SyncFromISMP_PortType extends java.rmi.Remote {
    public com.chinatelecom.ismp.vsop.gx_ismp.CSPInfoSyncToVSOPResp syncCSPInfoToVSOP(com.chinatelecom.ismp.vsop.gx_ismp.CSPInfoSyncToVSOPReq req) throws java.rmi.RemoteException;
    public com.chinatelecom.ismp.vsop.gx_ismp.ProductSyncFromISMPResp syncProductFromISMP(com.chinatelecom.ismp.vsop.gx_ismp.ProductSyncFromISMPReq req) throws java.rmi.RemoteException;
    public com.chinatelecom.ismp.vsop.gx_ismp.PackageSyncFromISMPResp syncPackageFromISMP(com.chinatelecom.ismp.vsop.gx_ismp.PackageSyncFromISMPReq req) throws java.rmi.RemoteException;
}
