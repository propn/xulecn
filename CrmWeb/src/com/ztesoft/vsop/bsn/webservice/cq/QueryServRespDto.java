/**
 * QueryServRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryServRespDto  implements java.io.Serializable {
    private int billingModeId;

    private java.lang.String errdescription;

    private int result;

    private ServAcctRespDto[] servAcctRespDtoArr;

    private long servId;

    private java.lang.String userName;

    public QueryServRespDto() {
    }

    public QueryServRespDto(
           int billingModeId,
           java.lang.String errdescription,
           int result,
           ServAcctRespDto[] servAcctRespDtoArr,
           long servId,
           java.lang.String userName) {
           this.billingModeId = billingModeId;
           this.errdescription = errdescription;
           this.result = result;
           this.servAcctRespDtoArr = servAcctRespDtoArr;
           this.servId = servId;
           this.userName = userName;
    }


    /**
     * Gets the billingModeId value for this QueryServRespDto.
     * 
     * @return billingModeId
     */
    public int getBillingModeId() {
        return billingModeId;
    }


    /**
     * Sets the billingModeId value for this QueryServRespDto.
     * 
     * @param billingModeId
     */
    public void setBillingModeId(int billingModeId) {
        this.billingModeId = billingModeId;
    }


    /**
     * Gets the errdescription value for this QueryServRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this QueryServRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the result value for this QueryServRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryServRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the servAcctRespDtoArr value for this QueryServRespDto.
     * 
     * @return servAcctRespDtoArr
     */
    public ServAcctRespDto[] getServAcctRespDtoArr() {
        return servAcctRespDtoArr;
    }


    /**
     * Sets the servAcctRespDtoArr value for this QueryServRespDto.
     * 
     * @param servAcctRespDtoArr
     */
    public void setServAcctRespDtoArr(ServAcctRespDto[] servAcctRespDtoArr) {
        this.servAcctRespDtoArr = servAcctRespDtoArr;
    }


    /**
     * Gets the servId value for this QueryServRespDto.
     * 
     * @return servId
     */
    public long getServId() {
        return servId;
    }


    /**
     * Sets the servId value for this QueryServRespDto.
     * 
     * @param servId
     */
    public void setServId(long servId) {
        this.servId = servId;
    }


    /**
     * Gets the userName value for this QueryServRespDto.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this QueryServRespDto.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryServRespDto)) return false;
        QueryServRespDto other = (QueryServRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.billingModeId == other.getBillingModeId() &&
            ((this.errdescription==null && other.getErrdescription()==null) || 
             (this.errdescription!=null &&
              this.errdescription.equals(other.getErrdescription()))) &&
            this.result == other.getResult() &&
            ((this.servAcctRespDtoArr==null && other.getServAcctRespDtoArr()==null) || 
             (this.servAcctRespDtoArr!=null &&
              java.util.Arrays.equals(this.servAcctRespDtoArr, other.getServAcctRespDtoArr()))) &&
            this.servId == other.getServId() &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName())));
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
        _hashCode += getBillingModeId();
        if (getErrdescription() != null) {
            _hashCode += getErrdescription().hashCode();
        }
        _hashCode += getResult();
        if (getServAcctRespDtoArr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getServAcctRespDtoArr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getServAcctRespDtoArr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Long(getServId()).hashCode();
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryServRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingModeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "billingModeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
        elemField.setFieldName("servAcctRespDtoArr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servAcctRespDtoArr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServAcctRespDto"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userName"));
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
