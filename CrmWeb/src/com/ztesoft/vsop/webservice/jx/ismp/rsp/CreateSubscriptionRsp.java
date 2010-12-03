/**
 * CreateSubscriptionRsp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.webservice.jx.ismp.rsp;

public class CreateSubscriptionRsp  implements java.io.Serializable {
    private java.lang.String effectiveTime;

    private java.lang.String expireTime;

    private int resultCode;

    private java.lang.String streamingNo;

    public CreateSubscriptionRsp() {
    }

    public CreateSubscriptionRsp(
           java.lang.String effectiveTime,
           java.lang.String expireTime,
           int resultCode,
           java.lang.String streamingNo) {
           this.effectiveTime = effectiveTime;
           this.expireTime = expireTime;
           this.resultCode = resultCode;
           this.streamingNo = streamingNo;
    }


    /**
     * Gets the effectiveTime value for this CreateSubscriptionRsp.
     * 
     * @return effectiveTime
     */
    public java.lang.String getEffectiveTime() {
        return effectiveTime;
    }


    /**
     * Sets the effectiveTime value for this CreateSubscriptionRsp.
     * 
     * @param effectiveTime
     */
    public void setEffectiveTime(java.lang.String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }


    /**
     * Gets the expireTime value for this CreateSubscriptionRsp.
     * 
     * @return expireTime
     */
    public java.lang.String getExpireTime() {
        return expireTime;
    }


    /**
     * Sets the expireTime value for this CreateSubscriptionRsp.
     * 
     * @param expireTime
     */
    public void setExpireTime(java.lang.String expireTime) {
        this.expireTime = expireTime;
    }


    /**
     * Gets the resultCode value for this CreateSubscriptionRsp.
     * 
     * @return resultCode
     */
    public int getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this CreateSubscriptionRsp.
     * 
     * @param resultCode
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }


    /**
     * Gets the streamingNo value for this CreateSubscriptionRsp.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this CreateSubscriptionRsp.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateSubscriptionRsp)) return false;
        CreateSubscriptionRsp other = (CreateSubscriptionRsp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.effectiveTime==null && other.getEffectiveTime()==null) || 
             (this.effectiveTime!=null &&
              this.effectiveTime.equals(other.getEffectiveTime()))) &&
            ((this.expireTime==null && other.getExpireTime()==null) || 
             (this.expireTime!=null &&
              this.expireTime.equals(other.getExpireTime()))) &&
            this.resultCode == other.getResultCode() &&
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo())));
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
        if (getEffectiveTime() != null) {
            _hashCode += getEffectiveTime().hashCode();
        }
        if (getExpireTime() != null) {
            _hashCode += getExpireTime().hashCode();
        }
        _hashCode += getResultCode();
        if (getStreamingNo() != null) {
            _hashCode += getStreamingNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateSubscriptionRsp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "CreateSubscriptionRsp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effectiveTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "effectiveTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expireTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "expireTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streamingNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streamingNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
