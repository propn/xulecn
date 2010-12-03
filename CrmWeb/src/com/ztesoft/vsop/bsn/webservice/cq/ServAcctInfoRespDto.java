/**
 * ServAcctInfoRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ServAcctInfoRespDto  implements java.io.Serializable {
    private AcctInfoDto[] acctInfoDtoArr;

    private java.lang.String billingMode;

    private java.lang.String errdescription;

    private int result;

    private java.lang.String servId;

    private java.lang.String userName;

    public ServAcctInfoRespDto() {
    }

    public ServAcctInfoRespDto(
           AcctInfoDto[] acctInfoDtoArr,
           java.lang.String billingMode,
           java.lang.String errdescription,
           int result,
           java.lang.String servId,
           java.lang.String userName) {
           this.acctInfoDtoArr = acctInfoDtoArr;
           this.billingMode = billingMode;
           this.errdescription = errdescription;
           this.result = result;
           this.servId = servId;
           this.userName = userName;
    }


    /**
     * Gets the acctInfoDtoArr value for this ServAcctInfoRespDto.
     * 
     * @return acctInfoDtoArr
     */
    public AcctInfoDto[] getAcctInfoDtoArr() {
        return acctInfoDtoArr;
    }


    /**
     * Sets the acctInfoDtoArr value for this ServAcctInfoRespDto.
     * 
     * @param acctInfoDtoArr
     */
    public void setAcctInfoDtoArr(AcctInfoDto[] acctInfoDtoArr) {
        this.acctInfoDtoArr = acctInfoDtoArr;
    }


    /**
     * Gets the billingMode value for this ServAcctInfoRespDto.
     * 
     * @return billingMode
     */
    public java.lang.String getBillingMode() {
        return billingMode;
    }


    /**
     * Sets the billingMode value for this ServAcctInfoRespDto.
     * 
     * @param billingMode
     */
    public void setBillingMode(java.lang.String billingMode) {
        this.billingMode = billingMode;
    }


    /**
     * Gets the errdescription value for this ServAcctInfoRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this ServAcctInfoRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the result value for this ServAcctInfoRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this ServAcctInfoRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the servId value for this ServAcctInfoRespDto.
     * 
     * @return servId
     */
    public java.lang.String getServId() {
        return servId;
    }


    /**
     * Sets the servId value for this ServAcctInfoRespDto.
     * 
     * @param servId
     */
    public void setServId(java.lang.String servId) {
        this.servId = servId;
    }


    /**
     * Gets the userName value for this ServAcctInfoRespDto.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this ServAcctInfoRespDto.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServAcctInfoRespDto)) return false;
        ServAcctInfoRespDto other = (ServAcctInfoRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acctInfoDtoArr==null && other.getAcctInfoDtoArr()==null) || 
             (this.acctInfoDtoArr!=null &&
              java.util.Arrays.equals(this.acctInfoDtoArr, other.getAcctInfoDtoArr()))) &&
            ((this.billingMode==null && other.getBillingMode()==null) || 
             (this.billingMode!=null &&
              this.billingMode.equals(other.getBillingMode()))) &&
            ((this.errdescription==null && other.getErrdescription()==null) || 
             (this.errdescription!=null &&
              this.errdescription.equals(other.getErrdescription()))) &&
            this.result == other.getResult() &&
            ((this.servId==null && other.getServId()==null) || 
             (this.servId!=null &&
              this.servId.equals(other.getServId()))) &&
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
        if (getAcctInfoDtoArr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAcctInfoDtoArr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAcctInfoDtoArr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBillingMode() != null) {
            _hashCode += getBillingMode().hashCode();
        }
        if (getErrdescription() != null) {
            _hashCode += getErrdescription().hashCode();
        }
        _hashCode += getResult();
        if (getServId() != null) {
            _hashCode += getServId().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServAcctInfoRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServAcctInfoRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctInfoDtoArr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acctInfoDtoArr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AcctInfoDto"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingMode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "billingMode"));
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
        elemField.setFieldName("servId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
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
