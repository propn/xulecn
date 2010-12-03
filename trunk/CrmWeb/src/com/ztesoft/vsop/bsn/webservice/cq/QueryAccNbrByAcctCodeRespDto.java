/**
 * QueryAccNbrByAcctCodeRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryAccNbrByAcctCodeRespDto  implements java.io.Serializable {
    private java.lang.String errdescription;

    private int result;

    private ServInfoDto[] servInfoDtoArr;

    public QueryAccNbrByAcctCodeRespDto() {
    }

    public QueryAccNbrByAcctCodeRespDto(
           java.lang.String errdescription,
           int result,
           ServInfoDto[] servInfoDtoArr) {
           this.errdescription = errdescription;
           this.result = result;
           this.servInfoDtoArr = servInfoDtoArr;
    }


    /**
     * Gets the errdescription value for this QueryAccNbrByAcctCodeRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this QueryAccNbrByAcctCodeRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the result value for this QueryAccNbrByAcctCodeRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryAccNbrByAcctCodeRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the servInfoDtoArr value for this QueryAccNbrByAcctCodeRespDto.
     * 
     * @return servInfoDtoArr
     */
    public ServInfoDto[] getServInfoDtoArr() {
        return servInfoDtoArr;
    }


    /**
     * Sets the servInfoDtoArr value for this QueryAccNbrByAcctCodeRespDto.
     * 
     * @param servInfoDtoArr
     */
    public void setServInfoDtoArr(ServInfoDto[] servInfoDtoArr) {
        this.servInfoDtoArr = servInfoDtoArr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryAccNbrByAcctCodeRespDto)) return false;
        QueryAccNbrByAcctCodeRespDto other = (QueryAccNbrByAcctCodeRespDto) obj;
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
            ((this.servInfoDtoArr==null && other.getServInfoDtoArr()==null) || 
             (this.servInfoDtoArr!=null &&
              java.util.Arrays.equals(this.servInfoDtoArr, other.getServInfoDtoArr())));
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
        if (getServInfoDtoArr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getServInfoDtoArr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getServInfoDtoArr(), i);
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
        new org.apache.axis.description.TypeDesc(QueryAccNbrByAcctCodeRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrByAcctCodeRespDto"));
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
        elemField.setFieldName("servInfoDtoArr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servInfoDtoArr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServInfoDto"));
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
