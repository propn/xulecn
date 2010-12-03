/**
 * ServiceCapabilitySubscriptionSyncReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.crm.req;

public class ServiceCapabilitySubscriptionSyncReq  implements java.io.Serializable {
    private java.lang.String serviceCapabilityID;

    private java.lang.String srcDeviceID;

    private java.lang.String streamingNo;

    private int updateType;

    private java.lang.String userID;

    private int userIDType;

    public ServiceCapabilitySubscriptionSyncReq() {
    }

    public ServiceCapabilitySubscriptionSyncReq(
           java.lang.String serviceCapabilityID,
           java.lang.String srcDeviceID,
           java.lang.String streamingNo,
           int updateType,
           java.lang.String userID,
           int userIDType) {
           this.serviceCapabilityID = serviceCapabilityID;
           this.srcDeviceID = srcDeviceID;
           this.streamingNo = streamingNo;
           this.updateType = updateType;
           this.userID = userID;
           this.userIDType = userIDType;
    }


    /**
     * Gets the serviceCapabilityID value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @return serviceCapabilityID
     */
    public java.lang.String getServiceCapabilityID() {
        return serviceCapabilityID;
    }


    /**
     * Sets the serviceCapabilityID value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @param serviceCapabilityID
     */
    public void setServiceCapabilityID(java.lang.String serviceCapabilityID) {
        this.serviceCapabilityID = serviceCapabilityID;
    }


    /**
     * Gets the srcDeviceID value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @return srcDeviceID
     */
    public java.lang.String getSrcDeviceID() {
        return srcDeviceID;
    }


    /**
     * Sets the srcDeviceID value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @param srcDeviceID
     */
    public void setSrcDeviceID(java.lang.String srcDeviceID) {
        this.srcDeviceID = srcDeviceID;
    }


    /**
     * Gets the streamingNo value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }


    /**
     * Gets the updateType value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @return updateType
     */
    public int getUpdateType() {
        return updateType;
    }


    /**
     * Sets the updateType value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @param updateType
     */
    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }


    /**
     * Gets the userID value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @return userID
     */
    public java.lang.String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @param userID
     */
    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }


    /**
     * Gets the userIDType value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @return userIDType
     */
    public int getUserIDType() {
        return userIDType;
    }


    /**
     * Sets the userIDType value for this ServiceCapabilitySubscriptionSyncReq.
     * 
     * @param userIDType
     */
    public void setUserIDType(int userIDType) {
        this.userIDType = userIDType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceCapabilitySubscriptionSyncReq)) return false;
        ServiceCapabilitySubscriptionSyncReq other = (ServiceCapabilitySubscriptionSyncReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.serviceCapabilityID==null && other.getServiceCapabilityID()==null) || 
             (this.serviceCapabilityID!=null &&
              this.serviceCapabilityID.equals(other.getServiceCapabilityID()))) &&
            ((this.srcDeviceID==null && other.getSrcDeviceID()==null) || 
             (this.srcDeviceID!=null &&
              this.srcDeviceID.equals(other.getSrcDeviceID()))) &&
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo()))) &&
            this.updateType == other.getUpdateType() &&
            ((this.userID==null && other.getUserID()==null) || 
             (this.userID!=null &&
              this.userID.equals(other.getUserID()))) &&
            this.userIDType == other.getUserIDType();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getServiceCapabilityID() != null) {
            _hashCode += getServiceCapabilityID().hashCode();
        }
        if (getSrcDeviceID() != null) {
            _hashCode += getSrcDeviceID().hashCode();
        }
        if (getStreamingNo() != null) {
            _hashCode += getStreamingNo().hashCode();
        }
        _hashCode += getUpdateType();
        if (getUserID() != null) {
            _hashCode += getUserID().hashCode();
        }
        _hashCode += getUserIDType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceCapabilitySubscriptionSyncReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ServiceCapabilitySubscriptionSyncReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCapabilityID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceCapabilityID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srcDeviceID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "srcDeviceID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streamingNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streamingNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userIDType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userIDType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
