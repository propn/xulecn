/**
 * QueryCashFeeRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryCashFeeRespDto  implements java.io.Serializable {
    private java.lang.String cashFee;

    private java.lang.String desc;

    private java.lang.String lateFee;

    private int result;

    public QueryCashFeeRespDto() {
    }

    public QueryCashFeeRespDto(
           java.lang.String cashFee,
           java.lang.String desc,
           java.lang.String lateFee,
           int result) {
           this.cashFee = cashFee;
           this.desc = desc;
           this.lateFee = lateFee;
           this.result = result;
    }


    /**
     * Gets the cashFee value for this QueryCashFeeRespDto.
     * 
     * @return cashFee
     */
    public java.lang.String getCashFee() {
        return cashFee;
    }


    /**
     * Sets the cashFee value for this QueryCashFeeRespDto.
     * 
     * @param cashFee
     */
    public void setCashFee(java.lang.String cashFee) {
        this.cashFee = cashFee;
    }


    /**
     * Gets the desc value for this QueryCashFeeRespDto.
     * 
     * @return desc
     */
    public java.lang.String getDesc() {
        return desc;
    }


    /**
     * Sets the desc value for this QueryCashFeeRespDto.
     * 
     * @param desc
     */
    public void setDesc(java.lang.String desc) {
        this.desc = desc;
    }


    /**
     * Gets the lateFee value for this QueryCashFeeRespDto.
     * 
     * @return lateFee
     */
    public java.lang.String getLateFee() {
        return lateFee;
    }


    /**
     * Sets the lateFee value for this QueryCashFeeRespDto.
     * 
     * @param lateFee
     */
    public void setLateFee(java.lang.String lateFee) {
        this.lateFee = lateFee;
    }


    /**
     * Gets the result value for this QueryCashFeeRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryCashFeeRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryCashFeeRespDto)) return false;
        QueryCashFeeRespDto other = (QueryCashFeeRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cashFee==null && other.getCashFee()==null) || 
             (this.cashFee!=null &&
              this.cashFee.equals(other.getCashFee()))) &&
            ((this.desc==null && other.getDesc()==null) || 
             (this.desc!=null &&
              this.desc.equals(other.getDesc()))) &&
            ((this.lateFee==null && other.getLateFee()==null) || 
             (this.lateFee!=null &&
              this.lateFee.equals(other.getLateFee()))) &&
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
        if (getCashFee() != null) {
            _hashCode += getCashFee().hashCode();
        }
        if (getDesc() != null) {
            _hashCode += getDesc().hashCode();
        }
        if (getLateFee() != null) {
            _hashCode += getLateFee().hashCode();
        }
        _hashCode += getResult();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryCashFeeRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCashFeeRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashFee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashFee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lateFee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lateFee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
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
