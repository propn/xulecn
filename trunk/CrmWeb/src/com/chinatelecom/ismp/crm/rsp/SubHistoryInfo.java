/**
 * SubHistoryInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.crm.rsp;

public class SubHistoryInfo  implements java.io.Serializable {
    private java.lang.String ID;

    private int IDType;

    private java.lang.String operationTime;

    private int operationType;

    public SubHistoryInfo() {
    }

    public SubHistoryInfo(
           java.lang.String ID,
           int IDType,
           java.lang.String operationTime,
           int operationType) {
           this.ID = ID;
           this.IDType = IDType;
           this.operationTime = operationTime;
           this.operationType = operationType;
    }


    /**
     * Gets the ID value for this SubHistoryInfo.
     * 
     * @return ID
     */
    public java.lang.String getID() {
        return ID;
    }


    /**
     * Sets the ID value for this SubHistoryInfo.
     * 
     * @param ID
     */
    public void setID(java.lang.String ID) {
        this.ID = ID;
    }


    /**
     * Gets the IDType value for this SubHistoryInfo.
     * 
     * @return IDType
     */
    public int getIDType() {
        return IDType;
    }


    /**
     * Sets the IDType value for this SubHistoryInfo.
     * 
     * @param IDType
     */
    public void setIDType(int IDType) {
        this.IDType = IDType;
    }


    /**
     * Gets the operationTime value for this SubHistoryInfo.
     * 
     * @return operationTime
     */
    public java.lang.String getOperationTime() {
        return operationTime;
    }


    /**
     * Sets the operationTime value for this SubHistoryInfo.
     * 
     * @param operationTime
     */
    public void setOperationTime(java.lang.String operationTime) {
        this.operationTime = operationTime;
    }


    /**
     * Gets the operationType value for this SubHistoryInfo.
     * 
     * @return operationType
     */
    public int getOperationType() {
        return operationType;
    }


    /**
     * Sets the operationType value for this SubHistoryInfo.
     * 
     * @param operationType
     */
    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubHistoryInfo)) return false;
        SubHistoryInfo other = (SubHistoryInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ID==null && other.getID()==null) || 
             (this.ID!=null &&
              this.ID.equals(other.getID()))) &&
            this.IDType == other.getIDType() &&
            ((this.operationTime==null && other.getOperationTime()==null) || 
             (this.operationTime!=null &&
              this.operationTime.equals(other.getOperationTime()))) &&
            this.operationType == other.getOperationType();
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
        if (getID() != null) {
            _hashCode += getID().hashCode();
        }
        _hashCode += getIDType();
        if (getOperationTime() != null) {
            _hashCode += getOperationTime().hashCode();
        }
        _hashCode += getOperationType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubHistoryInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "SubHistoryInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IDType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IDType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operationTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
