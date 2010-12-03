/**
 * ListDataDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ListDataDto  implements java.io.Serializable {
    private java.lang.String[] fieldData;

    private java.lang.String[][] fieldName;

    private java.lang.String listTypeId;

    private java.lang.String listTypeName;

    public ListDataDto() {
    }

    public ListDataDto(
           java.lang.String[] fieldData,
           java.lang.String[][] fieldName,
           java.lang.String listTypeId,
           java.lang.String listTypeName) {
           this.fieldData = fieldData;
           this.fieldName = fieldName;
           this.listTypeId = listTypeId;
           this.listTypeName = listTypeName;
    }


    /**
     * Gets the fieldData value for this ListDataDto.
     * 
     * @return fieldData
     */
    public java.lang.String[] getFieldData() {
        return fieldData;
    }


    /**
     * Sets the fieldData value for this ListDataDto.
     * 
     * @param fieldData
     */
    public void setFieldData(java.lang.String[] fieldData) {
        this.fieldData = fieldData;
    }


    /**
     * Gets the fieldName value for this ListDataDto.
     * 
     * @return fieldName
     */
    public java.lang.String[][] getFieldName() {
        return fieldName;
    }


    /**
     * Sets the fieldName value for this ListDataDto.
     * 
     * @param fieldName
     */
    public void setFieldName(java.lang.String[][] fieldName) {
        this.fieldName = fieldName;
    }


    /**
     * Gets the listTypeId value for this ListDataDto.
     * 
     * @return listTypeId
     */
    public java.lang.String getListTypeId() {
        return listTypeId;
    }


    /**
     * Sets the listTypeId value for this ListDataDto.
     * 
     * @param listTypeId
     */
    public void setListTypeId(java.lang.String listTypeId) {
        this.listTypeId = listTypeId;
    }


    /**
     * Gets the listTypeName value for this ListDataDto.
     * 
     * @return listTypeName
     */
    public java.lang.String getListTypeName() {
        return listTypeName;
    }


    /**
     * Sets the listTypeName value for this ListDataDto.
     * 
     * @param listTypeName
     */
    public void setListTypeName(java.lang.String listTypeName) {
        this.listTypeName = listTypeName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListDataDto)) return false;
        ListDataDto other = (ListDataDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fieldData==null && other.getFieldData()==null) || 
             (this.fieldData!=null &&
              java.util.Arrays.equals(this.fieldData, other.getFieldData()))) &&
            ((this.fieldName==null && other.getFieldName()==null) || 
             (this.fieldName!=null &&
              java.util.Arrays.equals(this.fieldName, other.getFieldName()))) &&
            ((this.listTypeId==null && other.getListTypeId()==null) || 
             (this.listTypeId!=null &&
              this.listTypeId.equals(other.getListTypeId()))) &&
            ((this.listTypeName==null && other.getListTypeName()==null) || 
             (this.listTypeName!=null &&
              this.listTypeName.equals(other.getListTypeName())));
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
        if (getFieldData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFieldData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFieldData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFieldName() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFieldName());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFieldName(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getListTypeId() != null) {
            _hashCode += getListTypeId().hashCode();
        }
        if (getListTypeName() != null) {
            _hashCode += getListTypeName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListDataDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListDataDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fieldData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fieldName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listTypeName"));
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
