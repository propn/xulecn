/**
 * QueryAccNbrRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryAccNbrRespDto  implements java.io.Serializable {
    private java.lang.String[] accNbrArr;

    private java.lang.String acctCode;

    private java.lang.String action;

    private int count;

    private java.lang.String errdescription;

    private int result;

    private int[] usertypeArr;

    public QueryAccNbrRespDto() {
    }

    public QueryAccNbrRespDto(
           java.lang.String[] accNbrArr,
           java.lang.String acctCode,
           java.lang.String action,
           int count,
           java.lang.String errdescription,
           int result,
           int[] usertypeArr) {
           this.accNbrArr = accNbrArr;
           this.acctCode = acctCode;
           this.action = action;
           this.count = count;
           this.errdescription = errdescription;
           this.result = result;
           this.usertypeArr = usertypeArr;
    }


    /**
     * Gets the accNbrArr value for this QueryAccNbrRespDto.
     * 
     * @return accNbrArr
     */
    public java.lang.String[] getAccNbrArr() {
        return accNbrArr;
    }


    /**
     * Sets the accNbrArr value for this QueryAccNbrRespDto.
     * 
     * @param accNbrArr
     */
    public void setAccNbrArr(java.lang.String[] accNbrArr) {
        this.accNbrArr = accNbrArr;
    }


    /**
     * Gets the acctCode value for this QueryAccNbrRespDto.
     * 
     * @return acctCode
     */
    public java.lang.String getAcctCode() {
        return acctCode;
    }


    /**
     * Sets the acctCode value for this QueryAccNbrRespDto.
     * 
     * @param acctCode
     */
    public void setAcctCode(java.lang.String acctCode) {
        this.acctCode = acctCode;
    }


    /**
     * Gets the action value for this QueryAccNbrRespDto.
     * 
     * @return action
     */
    public java.lang.String getAction() {
        return action;
    }


    /**
     * Sets the action value for this QueryAccNbrRespDto.
     * 
     * @param action
     */
    public void setAction(java.lang.String action) {
        this.action = action;
    }


    /**
     * Gets the count value for this QueryAccNbrRespDto.
     * 
     * @return count
     */
    public int getCount() {
        return count;
    }


    /**
     * Sets the count value for this QueryAccNbrRespDto.
     * 
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }


    /**
     * Gets the errdescription value for this QueryAccNbrRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this QueryAccNbrRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the result value for this QueryAccNbrRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryAccNbrRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the usertypeArr value for this QueryAccNbrRespDto.
     * 
     * @return usertypeArr
     */
    public int[] getUsertypeArr() {
        return usertypeArr;
    }


    /**
     * Sets the usertypeArr value for this QueryAccNbrRespDto.
     * 
     * @param usertypeArr
     */
    public void setUsertypeArr(int[] usertypeArr) {
        this.usertypeArr = usertypeArr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryAccNbrRespDto)) return false;
        QueryAccNbrRespDto other = (QueryAccNbrRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accNbrArr==null && other.getAccNbrArr()==null) || 
             (this.accNbrArr!=null &&
              java.util.Arrays.equals(this.accNbrArr, other.getAccNbrArr()))) &&
            ((this.acctCode==null && other.getAcctCode()==null) || 
             (this.acctCode!=null &&
              this.acctCode.equals(other.getAcctCode()))) &&
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            this.count == other.getCount() &&
            ((this.errdescription==null && other.getErrdescription()==null) || 
             (this.errdescription!=null &&
              this.errdescription.equals(other.getErrdescription()))) &&
            this.result == other.getResult() &&
            ((this.usertypeArr==null && other.getUsertypeArr()==null) || 
             (this.usertypeArr!=null &&
              java.util.Arrays.equals(this.usertypeArr, other.getUsertypeArr())));
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
        if (getAccNbrArr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccNbrArr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccNbrArr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAcctCode() != null) {
            _hashCode += getAcctCode().hashCode();
        }
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        _hashCode += getCount();
        if (getErrdescription() != null) {
            _hashCode += getErrdescription().hashCode();
        }
        _hashCode += getResult();
        if (getUsertypeArr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsertypeArr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsertypeArr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryAccNbrRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accNbrArr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accNbrArr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acctCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("count");
        elemField.setXmlName(new javax.xml.namespace.QName("", "count"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errdescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errdescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usertypeArr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usertypeArr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
