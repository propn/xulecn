/**
 * OrderRelationUpdateNotifyReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.crm.req;

public class OrderRelationUpdateNotifyReq  implements java.io.Serializable {
    private int optType;

    private java.lang.String packageID;

    private java.lang.String productId;

    private java.lang.String streamingNo;

    private java.lang.String userID;

    private int userIDType;

    public OrderRelationUpdateNotifyReq() {
    }

    public OrderRelationUpdateNotifyReq(
           int optType,
           java.lang.String packageID,
           java.lang.String productId,
           java.lang.String streamingNo,
           java.lang.String userID,
           int userIDType) {
           this.optType = optType;
           this.packageID = packageID;
           this.productId = productId;
           this.streamingNo = streamingNo;
           this.userID = userID;
           this.userIDType = userIDType;
    }


    /**
     * Gets the optType value for this OrderRelationUpdateNotifyReq.
     * 
     * @return optType
     */
    public int getOptType() {
        return optType;
    }


    /**
     * Sets the optType value for this OrderRelationUpdateNotifyReq.
     * 
     * @param optType
     */
    public void setOptType(int optType) {
        this.optType = optType;
    }


    /**
     * Gets the packageID value for this OrderRelationUpdateNotifyReq.
     * 
     * @return packageID
     */
    public java.lang.String getPackageID() {
        return packageID;
    }


    /**
     * Sets the packageID value for this OrderRelationUpdateNotifyReq.
     * 
     * @param packageID
     */
    public void setPackageID(java.lang.String packageID) {
        this.packageID = packageID;
    }


    /**
     * Gets the productId value for this OrderRelationUpdateNotifyReq.
     * 
     * @return productId
     */
    public java.lang.String getProductId() {
        return productId;
    }


    /**
     * Sets the productId value for this OrderRelationUpdateNotifyReq.
     * 
     * @param productId
     */
    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }


    /**
     * Gets the streamingNo value for this OrderRelationUpdateNotifyReq.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this OrderRelationUpdateNotifyReq.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }


    /**
     * Gets the userID value for this OrderRelationUpdateNotifyReq.
     * 
     * @return userID
     */
    public java.lang.String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this OrderRelationUpdateNotifyReq.
     * 
     * @param userID
     */
    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }


    /**
     * Gets the userIDType value for this OrderRelationUpdateNotifyReq.
     * 
     * @return userIDType
     */
    public int getUserIDType() {
        return userIDType;
    }


    /**
     * Sets the userIDType value for this OrderRelationUpdateNotifyReq.
     * 
     * @param userIDType
     */
    public void setUserIDType(int userIDType) {
        this.userIDType = userIDType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderRelationUpdateNotifyReq)) return false;
        OrderRelationUpdateNotifyReq other = (OrderRelationUpdateNotifyReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.optType == other.getOptType() &&
            ((this.packageID==null && other.getPackageID()==null) || 
             (this.packageID!=null &&
              this.packageID.equals(other.getPackageID()))) &&
            ((this.productId==null && other.getProductId()==null) || 
             (this.productId!=null &&
              this.productId.equals(other.getProductId()))) &&
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo()))) &&
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
        _hashCode += getOptType();
        if (getPackageID() != null) {
            _hashCode += getPackageID().hashCode();
        }
        if (getProductId() != null) {
            _hashCode += getProductId().hashCode();
        }
        if (getStreamingNo() != null) {
            _hashCode += getStreamingNo().hashCode();
        }
        if (getUserID() != null) {
            _hashCode += getUserID().hashCode();
        }
        _hashCode += getUserIDType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderRelationUpdateNotifyReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "OrderRelationUpdateNotifyReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("optType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "optType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productId"));
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
