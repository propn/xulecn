/**
 * ListTypeRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ListTypeRespDto  implements java.io.Serializable {
    private int eventTypeId;

    private int listTypeId;

    private java.lang.String normalBaseTb;

    private java.lang.String type;

    public ListTypeRespDto() {
    }

    public ListTypeRespDto(
           int eventTypeId,
           int listTypeId,
           java.lang.String normalBaseTb,
           java.lang.String type) {
           this.eventTypeId = eventTypeId;
           this.listTypeId = listTypeId;
           this.normalBaseTb = normalBaseTb;
           this.type = type;
    }


    /**
     * Gets the eventTypeId value for this ListTypeRespDto.
     * 
     * @return eventTypeId
     */
    public int getEventTypeId() {
        return eventTypeId;
    }


    /**
     * Sets the eventTypeId value for this ListTypeRespDto.
     * 
     * @param eventTypeId
     */
    public void setEventTypeId(int eventTypeId) {
        this.eventTypeId = eventTypeId;
    }


    /**
     * Gets the listTypeId value for this ListTypeRespDto.
     * 
     * @return listTypeId
     */
    public int getListTypeId() {
        return listTypeId;
    }


    /**
     * Sets the listTypeId value for this ListTypeRespDto.
     * 
     * @param listTypeId
     */
    public void setListTypeId(int listTypeId) {
        this.listTypeId = listTypeId;
    }


    /**
     * Gets the normalBaseTb value for this ListTypeRespDto.
     * 
     * @return normalBaseTb
     */
    public java.lang.String getNormalBaseTb() {
        return normalBaseTb;
    }


    /**
     * Sets the normalBaseTb value for this ListTypeRespDto.
     * 
     * @param normalBaseTb
     */
    public void setNormalBaseTb(java.lang.String normalBaseTb) {
        this.normalBaseTb = normalBaseTb;
    }


    /**
     * Gets the type value for this ListTypeRespDto.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this ListTypeRespDto.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListTypeRespDto)) return false;
        ListTypeRespDto other = (ListTypeRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.eventTypeId == other.getEventTypeId() &&
            this.listTypeId == other.getListTypeId() &&
            ((this.normalBaseTb==null && other.getNormalBaseTb()==null) || 
             (this.normalBaseTb!=null &&
              this.normalBaseTb.equals(other.getNormalBaseTb()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        _hashCode += getEventTypeId();
        _hashCode += getListTypeId();
        if (getNormalBaseTb() != null) {
            _hashCode += getNormalBaseTb().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListTypeRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListTypeRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eventTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("normalBaseTb");
        elemField.setXmlName(new javax.xml.namespace.QName("", "normalBaseTb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
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
