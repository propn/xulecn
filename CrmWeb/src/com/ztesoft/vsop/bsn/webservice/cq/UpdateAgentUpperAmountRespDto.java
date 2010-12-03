/**
 * UpdateAgentUpperAmountRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class UpdateAgentUpperAmountRespDto  extends AgentBaseRespDto  implements java.io.Serializable {
    private java.lang.String upperAmount;

    public UpdateAgentUpperAmountRespDto() {
    }

    public UpdateAgentUpperAmountRespDto(
           java.lang.String desc,
           int result,
           java.lang.String upperAmount) {
        super(
            desc,
            result);
        this.upperAmount = upperAmount;
    }


    /**
     * Gets the upperAmount value for this UpdateAgentUpperAmountRespDto.
     * 
     * @return upperAmount
     */
    public java.lang.String getUpperAmount() {
        return upperAmount;
    }


    /**
     * Sets the upperAmount value for this UpdateAgentUpperAmountRespDto.
     * 
     * @param upperAmount
     */
    public void setUpperAmount(java.lang.String upperAmount) {
        this.upperAmount = upperAmount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateAgentUpperAmountRespDto)) return false;
        UpdateAgentUpperAmountRespDto other = (UpdateAgentUpperAmountRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.upperAmount==null && other.getUpperAmount()==null) || 
             (this.upperAmount!=null &&
              this.upperAmount.equals(other.getUpperAmount())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getUpperAmount() != null) {
            _hashCode += getUpperAmount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateAgentUpperAmountRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentUpperAmountRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("upperAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "upperAmount"));
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
