/**
 * CreditValueChangeRequDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class CreditValueChangeRequDto  implements java.io.Serializable {
    private java.lang.String creditValue;

    private java.lang.String effDate;

    private java.lang.String expDate;

    private java.lang.String queryCode;

    private java.lang.String queryType;

    private int userType;

    public CreditValueChangeRequDto() {
    }

    public CreditValueChangeRequDto(
           java.lang.String creditValue,
           java.lang.String effDate,
           java.lang.String expDate,
           java.lang.String queryCode,
           java.lang.String queryType,
           int userType) {
           this.creditValue = creditValue;
           this.effDate = effDate;
           this.expDate = expDate;
           this.queryCode = queryCode;
           this.queryType = queryType;
           this.userType = userType;
    }


    /**
     * Gets the creditValue value for this CreditValueChangeRequDto.
     * 
     * @return creditValue
     */
    public java.lang.String getCreditValue() {
        return creditValue;
    }


    /**
     * Sets the creditValue value for this CreditValueChangeRequDto.
     * 
     * @param creditValue
     */
    public void setCreditValue(java.lang.String creditValue) {
        this.creditValue = creditValue;
    }


    /**
     * Gets the effDate value for this CreditValueChangeRequDto.
     * 
     * @return effDate
     */
    public java.lang.String getEffDate() {
        return effDate;
    }


    /**
     * Sets the effDate value for this CreditValueChangeRequDto.
     * 
     * @param effDate
     */
    public void setEffDate(java.lang.String effDate) {
        this.effDate = effDate;
    }


    /**
     * Gets the expDate value for this CreditValueChangeRequDto.
     * 
     * @return expDate
     */
    public java.lang.String getExpDate() {
        return expDate;
    }


    /**
     * Sets the expDate value for this CreditValueChangeRequDto.
     * 
     * @param expDate
     */
    public void setExpDate(java.lang.String expDate) {
        this.expDate = expDate;
    }


    /**
     * Gets the queryCode value for this CreditValueChangeRequDto.
     * 
     * @return queryCode
     */
    public java.lang.String getQueryCode() {
        return queryCode;
    }


    /**
     * Sets the queryCode value for this CreditValueChangeRequDto.
     * 
     * @param queryCode
     */
    public void setQueryCode(java.lang.String queryCode) {
        this.queryCode = queryCode;
    }


    /**
     * Gets the queryType value for this CreditValueChangeRequDto.
     * 
     * @return queryType
     */
    public java.lang.String getQueryType() {
        return queryType;
    }


    /**
     * Sets the queryType value for this CreditValueChangeRequDto.
     * 
     * @param queryType
     */
    public void setQueryType(java.lang.String queryType) {
        this.queryType = queryType;
    }


    /**
     * Gets the userType value for this CreditValueChangeRequDto.
     * 
     * @return userType
     */
    public int getUserType() {
        return userType;
    }


    /**
     * Sets the userType value for this CreditValueChangeRequDto.
     * 
     * @param userType
     */
    public void setUserType(int userType) {
        this.userType = userType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreditValueChangeRequDto)) return false;
        CreditValueChangeRequDto other = (CreditValueChangeRequDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.creditValue==null && other.getCreditValue()==null) || 
             (this.creditValue!=null &&
              this.creditValue.equals(other.getCreditValue()))) &&
            ((this.effDate==null && other.getEffDate()==null) || 
             (this.effDate!=null &&
              this.effDate.equals(other.getEffDate()))) &&
            ((this.expDate==null && other.getExpDate()==null) || 
             (this.expDate!=null &&
              this.expDate.equals(other.getExpDate()))) &&
            ((this.queryCode==null && other.getQueryCode()==null) || 
             (this.queryCode!=null &&
              this.queryCode.equals(other.getQueryCode()))) &&
            ((this.queryType==null && other.getQueryType()==null) || 
             (this.queryType!=null &&
              this.queryType.equals(other.getQueryType()))) &&
            this.userType == other.getUserType();
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
        if (getCreditValue() != null) {
            _hashCode += getCreditValue().hashCode();
        }
        if (getEffDate() != null) {
            _hashCode += getEffDate().hashCode();
        }
        if (getExpDate() != null) {
            _hashCode += getExpDate().hashCode();
        }
        if (getQueryCode() != null) {
            _hashCode += getQueryCode().hashCode();
        }
        if (getQueryType() != null) {
            _hashCode += getQueryType().hashCode();
        }
        _hashCode += getUserType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreditValueChangeRequDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CreditValueChangeRequDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "effDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "expDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userType"));
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
