/**
 * ProductOPCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp;

public class ProductOPCode  implements java.io.Serializable {
    private java.lang.String accessNO;

    private java.lang.String featureStr;

    private java.lang.Integer matchMode;

    private java.lang.Integer opType;

    public ProductOPCode() {
    }

    public ProductOPCode(
           java.lang.String accessNO,
           java.lang.String featureStr,
           java.lang.Integer matchMode,
           java.lang.Integer opType) {
           this.accessNO = accessNO;
           this.featureStr = featureStr;
           this.matchMode = matchMode;
           this.opType = opType;
    }


    /**
     * Gets the accessNO value for this ProductOPCode.
     * 
     * @return accessNO
     */
    public java.lang.String getAccessNO() {
        return accessNO;
    }


    /**
     * Sets the accessNO value for this ProductOPCode.
     * 
     * @param accessNO
     */
    public void setAccessNO(java.lang.String accessNO) {
        this.accessNO = accessNO;
    }


    /**
     * Gets the featureStr value for this ProductOPCode.
     * 
     * @return featureStr
     */
    public java.lang.String getFeatureStr() {
        return featureStr;
    }


    /**
     * Sets the featureStr value for this ProductOPCode.
     * 
     * @param featureStr
     */
    public void setFeatureStr(java.lang.String featureStr) {
        this.featureStr = featureStr;
    }


    /**
     * Gets the matchMode value for this ProductOPCode.
     * 
     * @return matchMode
     */
    public java.lang.Integer getMatchMode() {
        return matchMode;
    }


    /**
     * Sets the matchMode value for this ProductOPCode.
     * 
     * @param matchMode
     */
    public void setMatchMode(java.lang.Integer matchMode) {
        this.matchMode = matchMode;
    }


    /**
     * Gets the opType value for this ProductOPCode.
     * 
     * @return opType
     */
    public java.lang.Integer getOpType() {
        return opType;
    }


    /**
     * Sets the opType value for this ProductOPCode.
     * 
     * @param opType
     */
    public void setOpType(java.lang.Integer opType) {
        this.opType = opType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProductOPCode)) return false;
        ProductOPCode other = (ProductOPCode) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accessNO==null && other.getAccessNO()==null) || 
             (this.accessNO!=null &&
              this.accessNO.equals(other.getAccessNO()))) &&
            ((this.featureStr==null && other.getFeatureStr()==null) || 
             (this.featureStr!=null &&
              this.featureStr.equals(other.getFeatureStr()))) &&
            ((this.matchMode==null && other.getMatchMode()==null) || 
             (this.matchMode!=null &&
              this.matchMode.equals(other.getMatchMode()))) &&
            ((this.opType==null && other.getOpType()==null) || 
             (this.opType!=null &&
              this.opType.equals(other.getOpType())));
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
        if (getAccessNO() != null) {
            _hashCode += getAccessNO().hashCode();
        }
        if (getFeatureStr() != null) {
            _hashCode += getFeatureStr().hashCode();
        }
        if (getMatchMode() != null) {
            _hashCode += getMatchMode().hashCode();
        }
        if (getOpType() != null) {
            _hashCode += getOpType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProductOPCode.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "ProductOPCode"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessNO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accessNO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("featureStr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "featureStr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matchMode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matchMode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
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
