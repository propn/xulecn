/**
 * QueryPaymentRecordRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryPaymentRecordRespDto  implements java.io.Serializable {
    private java.lang.String count;

    private java.lang.String errdescription;

    private PaymentRecordDto[] paymentRecordArr;

    private int result;

    public QueryPaymentRecordRespDto() {
    }

    public QueryPaymentRecordRespDto(
           java.lang.String count,
           java.lang.String errdescription,
           PaymentRecordDto[] paymentRecordArr,
           int result) {
           this.count = count;
           this.errdescription = errdescription;
           this.paymentRecordArr = paymentRecordArr;
           this.result = result;
    }


    /**
     * Gets the count value for this QueryPaymentRecordRespDto.
     * 
     * @return count
     */
    public java.lang.String getCount() {
        return count;
    }


    /**
     * Sets the count value for this QueryPaymentRecordRespDto.
     * 
     * @param count
     */
    public void setCount(java.lang.String count) {
        this.count = count;
    }


    /**
     * Gets the errdescription value for this QueryPaymentRecordRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this QueryPaymentRecordRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the paymentRecordArr value for this QueryPaymentRecordRespDto.
     * 
     * @return paymentRecordArr
     */
    public PaymentRecordDto[] getPaymentRecordArr() {
        return paymentRecordArr;
    }


    /**
     * Sets the paymentRecordArr value for this QueryPaymentRecordRespDto.
     * 
     * @param paymentRecordArr
     */
    public void setPaymentRecordArr(PaymentRecordDto[] paymentRecordArr) {
        this.paymentRecordArr = paymentRecordArr;
    }


    /**
     * Gets the result value for this QueryPaymentRecordRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryPaymentRecordRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryPaymentRecordRespDto)) return false;
        QueryPaymentRecordRespDto other = (QueryPaymentRecordRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.count==null && other.getCount()==null) || 
             (this.count!=null &&
              this.count.equals(other.getCount()))) &&
            ((this.errdescription==null && other.getErrdescription()==null) || 
             (this.errdescription!=null &&
              this.errdescription.equals(other.getErrdescription()))) &&
            ((this.paymentRecordArr==null && other.getPaymentRecordArr()==null) || 
             (this.paymentRecordArr!=null &&
              java.util.Arrays.equals(this.paymentRecordArr, other.getPaymentRecordArr()))) &&
            this.result == other.getResult();
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
        if (getCount() != null) {
            _hashCode += getCount().hashCode();
        }
        if (getErrdescription() != null) {
            _hashCode += getErrdescription().hashCode();
        }
        if (getPaymentRecordArr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaymentRecordArr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaymentRecordArr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getResult();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryPaymentRecordRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPaymentRecordRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("count");
        elemField.setXmlName(new javax.xml.namespace.QName("", "count"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errdescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errdescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentRecordArr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentRecordArr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "PaymentRecordDto"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
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
