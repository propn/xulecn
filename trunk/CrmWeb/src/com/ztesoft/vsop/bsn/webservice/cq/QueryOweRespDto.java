/**
 * QueryOweRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryOweRespDto  implements java.io.Serializable {
    private java.lang.String errdescription;

    private java.lang.String oweCharge;

    private java.lang.String oweCycle;

    private int oweFlag;

    private int result;

    public QueryOweRespDto() {
    }

    public QueryOweRespDto(
           java.lang.String errdescription,
           java.lang.String oweCharge,
           java.lang.String oweCycle,
           int oweFlag,
           int result) {
           this.errdescription = errdescription;
           this.oweCharge = oweCharge;
           this.oweCycle = oweCycle;
           this.oweFlag = oweFlag;
           this.result = result;
    }


    /**
     * Gets the errdescription value for this QueryOweRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this QueryOweRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the oweCharge value for this QueryOweRespDto.
     * 
     * @return oweCharge
     */
    public java.lang.String getOweCharge() {
        return oweCharge;
    }


    /**
     * Sets the oweCharge value for this QueryOweRespDto.
     * 
     * @param oweCharge
     */
    public void setOweCharge(java.lang.String oweCharge) {
        this.oweCharge = oweCharge;
    }


    /**
     * Gets the oweCycle value for this QueryOweRespDto.
     * 
     * @return oweCycle
     */
    public java.lang.String getOweCycle() {
        return oweCycle;
    }


    /**
     * Sets the oweCycle value for this QueryOweRespDto.
     * 
     * @param oweCycle
     */
    public void setOweCycle(java.lang.String oweCycle) {
        this.oweCycle = oweCycle;
    }


    /**
     * Gets the oweFlag value for this QueryOweRespDto.
     * 
     * @return oweFlag
     */
    public int getOweFlag() {
        return oweFlag;
    }


    /**
     * Sets the oweFlag value for this QueryOweRespDto.
     * 
     * @param oweFlag
     */
    public void setOweFlag(int oweFlag) {
        this.oweFlag = oweFlag;
    }


    /**
     * Gets the result value for this QueryOweRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryOweRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryOweRespDto)) return false;
        QueryOweRespDto other = (QueryOweRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.errdescription==null && other.getErrdescription()==null) || 
             (this.errdescription!=null &&
              this.errdescription.equals(other.getErrdescription()))) &&
            ((this.oweCharge==null && other.getOweCharge()==null) || 
             (this.oweCharge!=null &&
              this.oweCharge.equals(other.getOweCharge()))) &&
            ((this.oweCycle==null && other.getOweCycle()==null) || 
             (this.oweCycle!=null &&
              this.oweCycle.equals(other.getOweCycle()))) &&
            this.oweFlag == other.getOweFlag() &&
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
        if (getErrdescription() != null) {
            _hashCode += getErrdescription().hashCode();
        }
        if (getOweCharge() != null) {
            _hashCode += getOweCharge().hashCode();
        }
        if (getOweCycle() != null) {
            _hashCode += getOweCycle().hashCode();
        }
        _hashCode += getOweFlag();
        _hashCode += getResult();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryOweRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryOweRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errdescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errdescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oweCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oweCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oweCycle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oweCycle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oweFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oweFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
