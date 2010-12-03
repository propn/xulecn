/**
 * ProdRelation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp;

public class ProdRelation  implements java.io.Serializable {
    private java.lang.Integer opType;

    private java.lang.String relationType;

    private java.lang.String rproductID;

    public ProdRelation() {
    }

    public ProdRelation(
           java.lang.Integer opType,
           java.lang.String relationType,
           java.lang.String rproductID) {
           this.opType = opType;
           this.relationType = relationType;
           this.rproductID = rproductID;
    }


    /**
     * Gets the opType value for this ProdRelation.
     * 
     * @return opType
     */
    public java.lang.Integer getOpType() {
        return opType;
    }


    /**
     * Sets the opType value for this ProdRelation.
     * 
     * @param opType
     */
    public void setOpType(java.lang.Integer opType) {
        this.opType = opType;
    }


    /**
     * Gets the relationType value for this ProdRelation.
     * 
     * @return relationType
     */
    public java.lang.String getRelationType() {
        return relationType;
    }


    /**
     * Sets the relationType value for this ProdRelation.
     * 
     * @param relationType
     */
    public void setRelationType(java.lang.String relationType) {
        this.relationType = relationType;
    }


    /**
     * Gets the rproductID value for this ProdRelation.
     * 
     * @return rproductID
     */
    public java.lang.String getRproductID() {
        return rproductID;
    }


    /**
     * Sets the rproductID value for this ProdRelation.
     * 
     * @param rproductID
     */
    public void setRproductID(java.lang.String rproductID) {
        this.rproductID = rproductID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdRelation)) return false;
        ProdRelation other = (ProdRelation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.opType==null && other.getOpType()==null) || 
             (this.opType!=null &&
              this.opType.equals(other.getOpType()))) &&
            ((this.relationType==null && other.getRelationType()==null) || 
             (this.relationType!=null &&
              this.relationType.equals(other.getRelationType()))) &&
            ((this.rproductID==null && other.getRproductID()==null) || 
             (this.rproductID!=null &&
              this.rproductID.equals(other.getRproductID())));
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
        if (getOpType() != null) {
            _hashCode += getOpType().hashCode();
        }
        if (getRelationType() != null) {
            _hashCode += getRelationType().hashCode();
        }
        if (getRproductID() != null) {
            _hashCode += getRproductID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdRelation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "ProdRelation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relationType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rproductID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rproductID"));
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
