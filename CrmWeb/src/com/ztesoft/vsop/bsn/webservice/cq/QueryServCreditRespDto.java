/**
 * QueryServCreditRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryServCreditRespDto  implements java.io.Serializable {
    private java.lang.String creditAmount;

    private java.lang.String currentCreditAmount;

    private java.lang.String errdescription;

    private int result;

    private java.lang.String servName;

    public QueryServCreditRespDto() {
    }

    public QueryServCreditRespDto(
           java.lang.String creditAmount,
           java.lang.String currentCreditAmount,
           java.lang.String errdescription,
           int result,
           java.lang.String servName) {
           this.creditAmount = creditAmount;
           this.currentCreditAmount = currentCreditAmount;
           this.errdescription = errdescription;
           this.result = result;
           this.servName = servName;
    }


    /**
     * Gets the creditAmount value for this QueryServCreditRespDto.
     * 
     * @return creditAmount
     */
    public java.lang.String getCreditAmount() {
        return creditAmount;
    }


    /**
     * Sets the creditAmount value for this QueryServCreditRespDto.
     * 
     * @param creditAmount
     */
    public void setCreditAmount(java.lang.String creditAmount) {
        this.creditAmount = creditAmount;
    }


    /**
     * Gets the currentCreditAmount value for this QueryServCreditRespDto.
     * 
     * @return currentCreditAmount
     */
    public java.lang.String getCurrentCreditAmount() {
        return currentCreditAmount;
    }


    /**
     * Sets the currentCreditAmount value for this QueryServCreditRespDto.
     * 
     * @param currentCreditAmount
     */
    public void setCurrentCreditAmount(java.lang.String currentCreditAmount) {
        this.currentCreditAmount = currentCreditAmount;
    }


    /**
     * Gets the errdescription value for this QueryServCreditRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this QueryServCreditRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the result value for this QueryServCreditRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryServCreditRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the servName value for this QueryServCreditRespDto.
     * 
     * @return servName
     */
    public java.lang.String getServName() {
        return servName;
    }


    /**
     * Sets the servName value for this QueryServCreditRespDto.
     * 
     * @param servName
     */
    public void setServName(java.lang.String servName) {
        this.servName = servName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryServCreditRespDto)) return false;
        QueryServCreditRespDto other = (QueryServCreditRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.creditAmount==null && other.getCreditAmount()==null) || 
             (this.creditAmount!=null &&
              this.creditAmount.equals(other.getCreditAmount()))) &&
            ((this.currentCreditAmount==null && other.getCurrentCreditAmount()==null) || 
             (this.currentCreditAmount!=null &&
              this.currentCreditAmount.equals(other.getCurrentCreditAmount()))) &&
            ((this.errdescription==null && other.getErrdescription()==null) || 
             (this.errdescription!=null &&
              this.errdescription.equals(other.getErrdescription()))) &&
            this.result == other.getResult() &&
            ((this.servName==null && other.getServName()==null) || 
             (this.servName!=null &&
              this.servName.equals(other.getServName())));
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
        if (getCreditAmount() != null) {
            _hashCode += getCreditAmount().hashCode();
        }
        if (getCurrentCreditAmount() != null) {
            _hashCode += getCurrentCreditAmount().hashCode();
        }
        if (getErrdescription() != null) {
            _hashCode += getErrdescription().hashCode();
        }
        _hashCode += getResult();
        if (getServName() != null) {
            _hashCode += getServName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryServCreditRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServCreditRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentCreditAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currentCreditAmount"));
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
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servName"));
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
