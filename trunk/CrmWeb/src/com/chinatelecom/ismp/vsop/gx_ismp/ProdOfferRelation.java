/**
 * ProdOfferRelation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp;

public class ProdOfferRelation  implements java.io.Serializable {
    private java.lang.String opType;

    private java.lang.Integer relationType;

    private java.lang.String rprodOfferID;

    public ProdOfferRelation() {
    }

    public ProdOfferRelation(
           java.lang.String opType,
           java.lang.Integer relationType,
           java.lang.String rprodOfferID) {
           this.opType = opType;
           this.relationType = relationType;
           this.rprodOfferID = rprodOfferID;
    }


    /**
     * Gets the opType value for this ProdOfferRelation.
     * 
     * @return opType
     */
    public java.lang.String getOpType() {
        return opType;
    }


    /**
     * Sets the opType value for this ProdOfferRelation.
     * 
     * @param opType
     */
    public void setOpType(java.lang.String opType) {
        this.opType = opType;
    }


    /**
     * Gets the relationType value for this ProdOfferRelation.
     * 
     * @return relationType
     */
    public java.lang.Integer getRelationType() {
        return relationType;
    }


    /**
     * Sets the relationType value for this ProdOfferRelation.
     * 
     * @param relationType
     */
    public void setRelationType(java.lang.Integer relationType) {
        this.relationType = relationType;
    }


    /**
     * Gets the rprodOfferID value for this ProdOfferRelation.
     * 
     * @return rprodOfferID
     */
    public java.lang.String getRprodOfferID() {
        return rprodOfferID;
    }


    /**
     * Sets the rprodOfferID value for this ProdOfferRelation.
     * 
     * @param rprodOfferID
     */
    public void setRprodOfferID(java.lang.String rprodOfferID) {
        this.rprodOfferID = rprodOfferID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdOfferRelation)) return false;
        ProdOfferRelation other = (ProdOfferRelation) obj;
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
            ((this.rprodOfferID==null && other.getRprodOfferID()==null) || 
             (this.rprodOfferID!=null &&
              this.rprodOfferID.equals(other.getRprodOfferID())));
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
        if (getRprodOfferID() != null) {
            _hashCode += getRprodOfferID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdOfferRelation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "ProdOfferRelation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relationType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rprodOfferID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rprodOfferID"));
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
