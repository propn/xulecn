/**
 * AddAgentPropertyReqDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class AddAgentPropertyReqDto  extends AgentPropertyDto  implements java.io.Serializable {
    public AddAgentPropertyReqDto() {
    }

    public AddAgentPropertyReqDto(
           java.lang.String acctGroupId,
           java.lang.String agentType,
           java.lang.String alarmAmount,
           java.lang.String ceilFlag,
           java.lang.String charge,
           java.lang.String depositAmount,
           java.lang.String lastSettleDate,
           java.lang.String partyId,
           java.lang.String scopeFlag,
           java.lang.String settletCharge,
           java.lang.String state,
           java.lang.String stateDate,
           java.lang.String upperAmount) {
        super(
            acctGroupId,
            agentType,
            alarmAmount,
            ceilFlag,
            charge,
            depositAmount,
            lastSettleDate,
            partyId,
            scopeFlag,
            settletCharge,
            state,
            stateDate,
            upperAmount);
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddAgentPropertyReqDto)) return false;
        AddAgentPropertyReqDto other = (AddAgentPropertyReqDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj);
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AddAgentPropertyReqDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AddAgentPropertyReqDto"));
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
