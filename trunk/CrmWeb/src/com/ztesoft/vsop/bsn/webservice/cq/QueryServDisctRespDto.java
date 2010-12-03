/**
 * QueryServDisctRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryServDisctRespDto  implements java.io.Serializable {
    private java.lang.String errdescription;

    private int result;

    private ServDisctDto[] servDisctDtoArr;

    public QueryServDisctRespDto() {
    }

    public QueryServDisctRespDto(
           java.lang.String errdescription,
           int result,
           ServDisctDto[] servDisctDtoArr) {
           this.errdescription = errdescription;
           this.result = result;
           this.servDisctDtoArr = servDisctDtoArr;
    }


    /**
     * Gets the errdescription value for this QueryServDisctRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this QueryServDisctRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the result value for this QueryServDisctRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryServDisctRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the servDisctDtoArr value for this QueryServDisctRespDto.
     * 
     * @return servDisctDtoArr
     */
    public ServDisctDto[] getServDisctDtoArr() {
        return servDisctDtoArr;
    }


    /**
     * Sets the servDisctDtoArr value for this QueryServDisctRespDto.
     * 
     * @param servDisctDtoArr
     */
    public void setServDisctDtoArr(ServDisctDto[] servDisctDtoArr) {
        this.servDisctDtoArr = servDisctDtoArr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryServDisctRespDto)) return false;
        QueryServDisctRespDto other = (QueryServDisctRespDto) obj;
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
            this.result == other.getResult() &&
            ((this.servDisctDtoArr==null && other.getServDisctDtoArr()==null) || 
             (this.servDisctDtoArr!=null &&
              java.util.Arrays.equals(this.servDisctDtoArr, other.getServDisctDtoArr())));
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
        _hashCode += getResult();
        if (getServDisctDtoArr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getServDisctDtoArr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getServDisctDtoArr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryServDisctRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("servDisctDtoArr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servDisctDtoArr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServDisctDto"));
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
