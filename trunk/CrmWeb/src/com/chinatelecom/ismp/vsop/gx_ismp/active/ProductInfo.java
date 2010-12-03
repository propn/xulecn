/**
 * ProductInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp.active;

public class ProductInfo  implements java.io.Serializable {
    private java.lang.String operType;

    private java.lang.String oproductID;

    private java.lang.String productID;

    public ProductInfo() {
    }

    public ProductInfo(
           java.lang.String operType,
           java.lang.String oproductID,
           java.lang.String productID) {
           this.operType = operType;
           this.oproductID = oproductID;
           this.productID = productID;
    }


    /**
     * Gets the operType value for this ProductInfo.
     * 
     * @return operType
     */
    public java.lang.String getOperType() {
        return operType;
    }


    /**
     * Sets the operType value for this ProductInfo.
     * 
     * @param operType
     */
    public void setOperType(java.lang.String operType) {
        this.operType = operType;
    }


    /**
     * Gets the oproductID value for this ProductInfo.
     * 
     * @return oproductID
     */
    public java.lang.String getOproductID() {
        return oproductID;
    }


    /**
     * Sets the oproductID value for this ProductInfo.
     * 
     * @param oproductID
     */
    public void setOproductID(java.lang.String oproductID) {
        this.oproductID = oproductID;
    }


    /**
     * Gets the productID value for this ProductInfo.
     * 
     * @return productID
     */
    public java.lang.String getProductID() {
        return productID;
    }


    /**
     * Sets the productID value for this ProductInfo.
     * 
     * @param productID
     */
    public void setProductID(java.lang.String productID) {
        this.productID = productID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProductInfo)) return false;
        ProductInfo other = (ProductInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.operType==null && other.getOperType()==null) || 
             (this.operType!=null &&
              this.operType.equals(other.getOperType()))) &&
            ((this.oproductID==null && other.getOproductID()==null) || 
             (this.oproductID!=null &&
              this.oproductID.equals(other.getOproductID()))) &&
            ((this.productID==null && other.getProductID()==null) || 
             (this.productID!=null &&
              this.productID.equals(other.getProductID())));
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
        if (getOperType() != null) {
            _hashCode += getOperType().hashCode();
        }
        if (getOproductID() != null) {
            _hashCode += getOproductID().hashCode();
        }
        if (getProductID() != null) {
            _hashCode += getProductID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProductInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "ProductInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oproductID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oproductID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
