/**
 * ListTypeSumDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ListTypeSumDto  implements java.io.Serializable {
    private java.lang.String listTypeId;

    private java.lang.String listTypeName;

    private java.lang.String sumDesc;

    public ListTypeSumDto() {
    }

    public ListTypeSumDto(
           java.lang.String listTypeId,
           java.lang.String listTypeName,
           java.lang.String sumDesc) {
           this.listTypeId = listTypeId;
           this.listTypeName = listTypeName;
           this.sumDesc = sumDesc;
    }


    /**
     * Gets the listTypeId value for this ListTypeSumDto.
     * 
     * @return listTypeId
     */
    public java.lang.String getListTypeId() {
        return listTypeId;
    }


    /**
     * Sets the listTypeId value for this ListTypeSumDto.
     * 
     * @param listTypeId
     */
    public void setListTypeId(java.lang.String listTypeId) {
        this.listTypeId = listTypeId;
    }


    /**
     * Gets the listTypeName value for this ListTypeSumDto.
     * 
     * @return listTypeName
     */
    public java.lang.String getListTypeName() {
        return listTypeName;
    }


    /**
     * Sets the listTypeName value for this ListTypeSumDto.
     * 
     * @param listTypeName
     */
    public void setListTypeName(java.lang.String listTypeName) {
        this.listTypeName = listTypeName;
    }


    /**
     * Gets the sumDesc value for this ListTypeSumDto.
     * 
     * @return sumDesc
     */
    public java.lang.String getSumDesc() {
        return sumDesc;
    }


    /**
     * Sets the sumDesc value for this ListTypeSumDto.
     * 
     * @param sumDesc
     */
    public void setSumDesc(java.lang.String sumDesc) {
        this.sumDesc = sumDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListTypeSumDto)) return false;
        ListTypeSumDto other = (ListTypeSumDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.listTypeId==null && other.getListTypeId()==null) || 
             (this.listTypeId!=null &&
              this.listTypeId.equals(other.getListTypeId()))) &&
            ((this.listTypeName==null && other.getListTypeName()==null) || 
             (this.listTypeName!=null &&
              this.listTypeName.equals(other.getListTypeName()))) &&
            ((this.sumDesc==null && other.getSumDesc()==null) || 
             (this.sumDesc!=null &&
              this.sumDesc.equals(other.getSumDesc())));
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
        if (getListTypeId() != null) {
            _hashCode += getListTypeId().hashCode();
        }
        if (getListTypeName() != null) {
            _hashCode += getListTypeName().hashCode();
        }
        if (getSumDesc() != null) {
            _hashCode += getSumDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListTypeSumDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListTypeSumDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sumDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sumDesc"));
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
