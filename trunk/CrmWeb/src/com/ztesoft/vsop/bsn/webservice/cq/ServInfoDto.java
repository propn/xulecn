/**
 * ServInfoDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ServInfoDto  implements java.io.Serializable {
    private java.lang.String accNbr;

    private java.lang.String productId;

    private java.lang.String userName;

    public ServInfoDto() {
    }

    public ServInfoDto(
           java.lang.String accNbr,
           java.lang.String productId,
           java.lang.String userName) {
           this.accNbr = accNbr;
           this.productId = productId;
           this.userName = userName;
    }


    /**
     * Gets the accNbr value for this ServInfoDto.
     * 
     * @return accNbr
     */
    public java.lang.String getAccNbr() {
        return accNbr;
    }


    /**
     * Sets the accNbr value for this ServInfoDto.
     * 
     * @param accNbr
     */
    public void setAccNbr(java.lang.String accNbr) {
        this.accNbr = accNbr;
    }


    /**
     * Gets the productId value for this ServInfoDto.
     * 
     * @return productId
     */
    public java.lang.String getProductId() {
        return productId;
    }


    /**
     * Sets the productId value for this ServInfoDto.
     * 
     * @param productId
     */
    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }


    /**
     * Gets the userName value for this ServInfoDto.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this ServInfoDto.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServInfoDto)) return false;
        ServInfoDto other = (ServInfoDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accNbr==null && other.getAccNbr()==null) || 
             (this.accNbr!=null &&
              this.accNbr.equals(other.getAccNbr()))) &&
            ((this.productId==null && other.getProductId()==null) || 
             (this.productId!=null &&
              this.productId.equals(other.getProductId()))) &&
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
        if (getAccNbr() != null) {
            _hashCode += getAccNbr().hashCode();
        }
        if (getProductId() != null) {
            _hashCode += getProductId().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServInfoDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServInfoDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accNbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accNbr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productId"));
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
