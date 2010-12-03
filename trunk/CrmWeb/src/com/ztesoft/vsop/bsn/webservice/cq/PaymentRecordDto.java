/**
 * PaymentRecordDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class PaymentRecordDto  implements java.io.Serializable {
    private java.lang.String paymentAcctCode;

    private java.lang.String paymentAddress;

    private java.lang.String paymentAmount;

    private java.lang.String paymentBillingCycle;

    private java.lang.String paymentDate;

    private java.lang.String paymentId;

    public PaymentRecordDto() {
    }

    public PaymentRecordDto(
           java.lang.String paymentAcctCode,
           java.lang.String paymentAddress,
           java.lang.String paymentAmount,
           java.lang.String paymentBillingCycle,
           java.lang.String paymentDate,
           java.lang.String paymentId) {
           this.paymentAcctCode = paymentAcctCode;
           this.paymentAddress = paymentAddress;
           this.paymentAmount = paymentAmount;
           this.paymentBillingCycle = paymentBillingCycle;
           this.paymentDate = paymentDate;
           this.paymentId = paymentId;
    }


    /**
     * Gets the paymentAcctCode value for this PaymentRecordDto.
     * 
     * @return paymentAcctCode
     */
    public java.lang.String getPaymentAcctCode() {
        return paymentAcctCode;
    }


    /**
     * Sets the paymentAcctCode value for this PaymentRecordDto.
     * 
     * @param paymentAcctCode
     */
    public void setPaymentAcctCode(java.lang.String paymentAcctCode) {
        this.paymentAcctCode = paymentAcctCode;
    }


    /**
     * Gets the paymentAddress value for this PaymentRecordDto.
     * 
     * @return paymentAddress
     */
    public java.lang.String getPaymentAddress() {
        return paymentAddress;
    }


    /**
     * Sets the paymentAddress value for this PaymentRecordDto.
     * 
     * @param paymentAddress
     */
    public void setPaymentAddress(java.lang.String paymentAddress) {
        this.paymentAddress = paymentAddress;
    }


    /**
     * Gets the paymentAmount value for this PaymentRecordDto.
     * 
     * @return paymentAmount
     */
    public java.lang.String getPaymentAmount() {
        return paymentAmount;
    }


    /**
     * Sets the paymentAmount value for this PaymentRecordDto.
     * 
     * @param paymentAmount
     */
    public void setPaymentAmount(java.lang.String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }


    /**
     * Gets the paymentBillingCycle value for this PaymentRecordDto.
     * 
     * @return paymentBillingCycle
     */
    public java.lang.String getPaymentBillingCycle() {
        return paymentBillingCycle;
    }


    /**
     * Sets the paymentBillingCycle value for this PaymentRecordDto.
     * 
     * @param paymentBillingCycle
     */
    public void setPaymentBillingCycle(java.lang.String paymentBillingCycle) {
        this.paymentBillingCycle = paymentBillingCycle;
    }


    /**
     * Gets the paymentDate value for this PaymentRecordDto.
     * 
     * @return paymentDate
     */
    public java.lang.String getPaymentDate() {
        return paymentDate;
    }


    /**
     * Sets the paymentDate value for this PaymentRecordDto.
     * 
     * @param paymentDate
     */
    public void setPaymentDate(java.lang.String paymentDate) {
        this.paymentDate = paymentDate;
    }


    /**
     * Gets the paymentId value for this PaymentRecordDto.
     * 
     * @return paymentId
     */
    public java.lang.String getPaymentId() {
        return paymentId;
    }


    /**
     * Sets the paymentId value for this PaymentRecordDto.
     * 
     * @param paymentId
     */
    public void setPaymentId(java.lang.String paymentId) {
        this.paymentId = paymentId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentRecordDto)) return false;
        PaymentRecordDto other = (PaymentRecordDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.paymentAcctCode==null && other.getPaymentAcctCode()==null) || 
             (this.paymentAcctCode!=null &&
              this.paymentAcctCode.equals(other.getPaymentAcctCode()))) &&
            ((this.paymentAddress==null && other.getPaymentAddress()==null) || 
             (this.paymentAddress!=null &&
              this.paymentAddress.equals(other.getPaymentAddress()))) &&
            ((this.paymentAmount==null && other.getPaymentAmount()==null) || 
             (this.paymentAmount!=null &&
              this.paymentAmount.equals(other.getPaymentAmount()))) &&
            ((this.paymentBillingCycle==null && other.getPaymentBillingCycle()==null) || 
             (this.paymentBillingCycle!=null &&
              this.paymentBillingCycle.equals(other.getPaymentBillingCycle()))) &&
            ((this.paymentDate==null && other.getPaymentDate()==null) || 
             (this.paymentDate!=null &&
              this.paymentDate.equals(other.getPaymentDate()))) &&
            ((this.paymentId==null && other.getPaymentId()==null) || 
             (this.paymentId!=null &&
              this.paymentId.equals(other.getPaymentId())));
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
        if (getPaymentAcctCode() != null) {
            _hashCode += getPaymentAcctCode().hashCode();
        }
        if (getPaymentAddress() != null) {
            _hashCode += getPaymentAddress().hashCode();
        }
        if (getPaymentAmount() != null) {
            _hashCode += getPaymentAmount().hashCode();
        }
        if (getPaymentBillingCycle() != null) {
            _hashCode += getPaymentBillingCycle().hashCode();
        }
        if (getPaymentDate() != null) {
            _hashCode += getPaymentDate().hashCode();
        }
        if (getPaymentId() != null) {
            _hashCode += getPaymentId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentRecordDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "PaymentRecordDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentAcctCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentAcctCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentBillingCycle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentBillingCycle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
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
