/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package com.ztesoft.vsop.webservice.vo.jx.req;


/**
 *  ExtensionMapper class
 */
public class ExtensionMapper {
    public static java.lang.Object getTypeObject(
        java.lang.String namespaceURI, java.lang.String typeName,
        javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "SubscriptionSyncReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.SubscriptionSyncReq.Factory.parse(reader);
        }

        if ("http://rsp.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "CreateSubscriptionRsp".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.CreateSubscriptionRsp.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "GetSubscriptionReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.GetSubscriptionReq.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "NotifyUserFeeTypeReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserFeeTypeReq.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "WithdrawAllSubscriptionReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.WithdrawAllSubscriptionReq.Factory.parse(reader);
        }

        if ("http://rsp.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "WithdrawSubscriptionRsp".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.WithdrawSubscriptionRsp.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "createISMPUserReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.CreateISMPUserReq.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "ModifySubscriptionReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.ModifySubscriptionReq.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "ServiceCapabilitySubscriptionSyncReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.ServiceCapabilitySubscriptionSyncReq.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "WorkOrderNotifyCnfmReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyCnfmReq.Factory.parse(reader);
        }

        if ("http://rsp.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "SubHistoryInfo".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.SubHistoryInfo.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "CreateSubscriptionReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.CreateSubscriptionReq.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "ResumeSubscriptionReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.ResumeSubscriptionReq.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "OrderRelationUpdateNotifyReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.OrderRelationUpdateNotifyReq.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "SuspendSubscriptionReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.SuspendSubscriptionReq.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "WithdrawSubscriptionReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.WithdrawSubscriptionReq.Factory.parse(reader);
        }

        if ("http://crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "ArrayOf_tns1_SubHistoryInfo".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.ArrayOf_tns1_SubHistoryInfo.Factory.parse(reader);
        }

        if ("http://rsp.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "GetSubscriptionRsp".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.GetSubscriptionRsp.Factory.parse(reader);
        }

        if ("http://crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "ArrayOf_tns1_SubInfo".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.ArrayOf_tns1_SubInfo.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "WorkOrderNotifyReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyReq.Factory.parse(reader);
        }

        if ("http://rsp.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "QuerySubscriptionHistoryRsp".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.QuerySubscriptionHistoryRsp.Factory.parse(reader);
        }

        if ("http://rsp.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "ModifySubscriptionRsp".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.ModifySubscriptionRsp.Factory.parse(reader);
        }

        if ("http://rsp.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "Response".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.Response.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "NotifyUserStateReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserStateReq.Factory.parse(reader);
        }

        if ("http://req.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "QuerySubscriptionHistoryReq".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.req.QuerySubscriptionHistoryReq.Factory.parse(reader);
        }

        if ("http://rsp.crm.ismp.chinatelecom.com".equals(namespaceURI) &&
                "SubInfo".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.jx.SubInfo.Factory.parse(reader);
        }

        throw new org.apache.axis2.databinding.ADBException("Unsupported type " +
            namespaceURI + " " + typeName);
    }
}
