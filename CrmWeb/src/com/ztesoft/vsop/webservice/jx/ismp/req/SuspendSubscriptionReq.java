/**
 * SuspendSubscriptionReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.webservice.jx.ismp.req;

public class SuspendSubscriptionReq  implements java.io.Serializable {
    private java.lang.String ID;

    private int IDType;

    private java.lang.String srcDeviceID;

    private int srcDeviceType;

    private java.lang.String streamingNO;

    private java.lang.String userID;

    private int userIDType;

    public SuspendSubscriptionReq() {
    }

    public SuspendSubscriptionReq(
           java.lang.String ID,
           int IDType,
           java.lang.String srcDeviceID,
           int srcDeviceType,
           java.lang.String streamingNO,
           java.lang.String userID,
           int userIDType) {
           this.ID = ID;
           this.IDType = IDType;
           this.srcDeviceID = srcDeviceID;
           this.srcDeviceType = srcDeviceType;
           this.streamingNO = streamingNO;
           this.userID = userID;
           this.userIDType = userIDType;
    }


    /**
     * Gets the ID value for this SuspendSubscriptionReq.
     * 
     * @return ID
     */
    public java.lang.String getID() {
        return ID;
    }


    /**
     * Sets the ID value for this SuspendSubscriptionReq.
     * 
     * @param ID
     */
    public void setID(java.lang.String ID) {
        this.ID = ID;
    }


    /**
     * Gets the IDType value for this SuspendSubscriptionReq.
     * 
     * @return IDType
     */
    public int getIDType() {
        return IDType;
    }


    /**
     * Sets the IDType value for this SuspendSubscriptionReq.
     * 
     * @param IDType
     */
    public void setIDType(int IDType) {
        this.IDType = IDType;
    }


    /**
     * Gets the srcDeviceID value for this SuspendSubscriptionReq.
     * 
     * @return srcDeviceID
     */
    public java.lang.String getSrcDeviceID() {
        return srcDeviceID;
    }


    /**
     * Sets the srcDeviceID value for this SuspendSubscriptionReq.
     * 
     * @param srcDeviceID
     */
    public void setSrcDeviceID(java.lang.String srcDeviceID) {
        this.srcDeviceID = srcDeviceID;
    }


    /**
     * Gets the srcDeviceType value for this SuspendSubscriptionReq.
     * 
     * @return srcDeviceType
     */
    public int getSrcDeviceType() {
        return srcDeviceType;
    }


    /**
     * Sets the srcDeviceType value for this SuspendSubscriptionReq.
     * 
     * @param srcDeviceType
     */
    public void setSrcDeviceType(int srcDeviceType) {
        this.srcDeviceType = srcDeviceType;
    }


    /**
     * Gets the streamingNO value for this SuspendSubscriptionReq.
     * 
     * @return streamingNO
     */
    public java.lang.String getStreamingNO() {
        return streamingNO;
    }


    /**
     * Sets the streamingNO value for this SuspendSubscriptionReq.
     * 
     * @param streamingNO
     */
    public void setStreamingNO(java.lang.String streamingNO) {
        this.streamingNO = streamingNO;
    }


    /**
     * Gets the userID value for this SuspendSubscriptionReq.
     * 
     * @return userID
     */
    public java.lang.String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this SuspendSubscriptionReq.
     * 
     * @param userID
     */
    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }


    /**
     * Gets the userIDType value for this SuspendSubscriptionReq.
     * 
     * @return userIDType
     */
    public int getUserIDType() {
        return userIDType;
    }


    /**
     * Sets the userIDType value for this SuspendSubscriptionReq.
     * 
     * @param userIDType
     */
    public void setUserIDType(int userIDType) {
        this.userIDType = userIDType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SuspendSubscriptionReq)) return false;
        SuspendSubscriptionReq other = (SuspendSubscriptionReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ID==null && other.getID()==null) || 
             (this.ID!=null &&
              this.ID.equals(other.getID()))) &&
            this.IDType == other.getIDType() &&
            ((this.srcDeviceID==null && other.getSrcDeviceID()==null) || 
             (this.srcDeviceID!=null &&
              this.srcDeviceID.equals(other.getSrcDeviceID()))) &&
            this.srcDeviceType == other.getSrcDeviceType() &&
            ((this.streamingNO==null && other.getStreamingNO()==null) || 
             (this.streamingNO!=null &&
              this.streamingNO.equals(other.getStreamingNO()))) &&
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
        if (getID() != null) {
            _hashCode += getID().hashCode();
        }
        _hashCode += getIDType();
        if (getSrcDeviceID() != null) {
            _hashCode += getSrcDeviceID().hashCode();
        }
        _hashCode += getSrcDeviceType();
        if (getStreamingNO() != null) {
            _hashCode += getStreamingNO().hashCode();
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
        new org.apache.axis.description.TypeDesc(SuspendSubscriptionReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "SuspendSubscriptionReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IDType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IDType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srcDeviceID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "srcDeviceID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srcDeviceType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "srcDeviceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streamingNO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streamingNO"));
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
