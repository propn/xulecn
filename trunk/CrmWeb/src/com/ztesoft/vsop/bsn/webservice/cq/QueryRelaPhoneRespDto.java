/**
 * QueryRelaPhoneRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryRelaPhoneRespDto  implements java.io.Serializable {
    private java.lang.String action;

    private java.lang.String date;

    private java.lang.String errdescription;

    private java.lang.String phone;

    private int relaamount;

    private java.lang.String relaphone;

    private int result;

    public QueryRelaPhoneRespDto() {
    }

    public QueryRelaPhoneRespDto(
           java.lang.String action,
           java.lang.String date,
           java.lang.String errdescription,
           java.lang.String phone,
           int relaamount,
           java.lang.String relaphone,
           int result) {
           this.action = action;
           this.date = date;
           this.errdescription = errdescription;
           this.phone = phone;
           this.relaamount = relaamount;
           this.relaphone = relaphone;
           this.result = result;
    }


    /**
     * Gets the action value for this QueryRelaPhoneRespDto.
     * 
     * @return action
     */
    public java.lang.String getAction() {
        return action;
    }


    /**
     * Sets the action value for this QueryRelaPhoneRespDto.
     * 
     * @param action
     */
    public void setAction(java.lang.String action) {
        this.action = action;
    }


    /**
     * Gets the date value for this QueryRelaPhoneRespDto.
     * 
     * @return date
     */
    public java.lang.String getDate() {
        return date;
    }


    /**
     * Sets the date value for this QueryRelaPhoneRespDto.
     * 
     * @param date
     */
    public void setDate(java.lang.String date) {
        this.date = date;
    }


    /**
     * Gets the errdescription value for this QueryRelaPhoneRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this QueryRelaPhoneRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the phone value for this QueryRelaPhoneRespDto.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this QueryRelaPhoneRespDto.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the relaamount value for this QueryRelaPhoneRespDto.
     * 
     * @return relaamount
     */
    public int getRelaamount() {
        return relaamount;
    }


    /**
     * Sets the relaamount value for this QueryRelaPhoneRespDto.
     * 
     * @param relaamount
     */
    public void setRelaamount(int relaamount) {
        this.relaamount = relaamount;
    }


    /**
     * Gets the relaphone value for this QueryRelaPhoneRespDto.
     * 
     * @return relaphone
     */
    public java.lang.String getRelaphone() {
        return relaphone;
    }


    /**
     * Sets the relaphone value for this QueryRelaPhoneRespDto.
     * 
     * @param relaphone
     */
    public void setRelaphone(java.lang.String relaphone) {
        this.relaphone = relaphone;
    }


    /**
     * Gets the result value for this QueryRelaPhoneRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryRelaPhoneRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryRelaPhoneRespDto)) return false;
        QueryRelaPhoneRespDto other = (QueryRelaPhoneRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.errdescription==null && other.getErrdescription()==null) || 
             (this.errdescription!=null &&
              this.errdescription.equals(other.getErrdescription()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            this.relaamount == other.getRelaamount() &&
            ((this.relaphone==null && other.getRelaphone()==null) || 
             (this.relaphone!=null &&
              this.relaphone.equals(other.getRelaphone()))) &&
            this.result == other.getResult();
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
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getErrdescription() != null) {
            _hashCode += getErrdescription().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        _hashCode += getRelaamount();
        if (getRelaphone() != null) {
            _hashCode += getRelaphone().hashCode();
        }
        _hashCode += getResult();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryRelaPhoneRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryRelaPhoneRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("", "date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errdescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errdescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relaamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relaamount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relaphone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relaphone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
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