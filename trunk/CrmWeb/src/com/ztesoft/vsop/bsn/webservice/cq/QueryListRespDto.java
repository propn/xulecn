/**
 * QueryListRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryListRespDto  implements java.io.Serializable {
    private java.lang.String durationSum;

    private java.lang.String errdescription;

    private ListDataDto[] listDataDtoArr;

    private java.lang.String rateSum;

    private int result;

    public QueryListRespDto() {
    }

    public QueryListRespDto(
           java.lang.String durationSum,
           java.lang.String errdescription,
           ListDataDto[] listDataDtoArr,
           java.lang.String rateSum,
           int result) {
           this.durationSum = durationSum;
           this.errdescription = errdescription;
           this.listDataDtoArr = listDataDtoArr;
           this.rateSum = rateSum;
           this.result = result;
    }


    /**
     * Gets the durationSum value for this QueryListRespDto.
     * 
     * @return durationSum
     */
    public java.lang.String getDurationSum() {
        return durationSum;
    }


    /**
     * Sets the durationSum value for this QueryListRespDto.
     * 
     * @param durationSum
     */
    public void setDurationSum(java.lang.String durationSum) {
        this.durationSum = durationSum;
    }


    /**
     * Gets the errdescription value for this QueryListRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this QueryListRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the listDataDtoArr value for this QueryListRespDto.
     * 
     * @return listDataDtoArr
     */
    public ListDataDto[] getListDataDtoArr() {
        return listDataDtoArr;
    }


    /**
     * Sets the listDataDtoArr value for this QueryListRespDto.
     * 
     * @param listDataDtoArr
     */
    public void setListDataDtoArr(ListDataDto[] listDataDtoArr) {
        this.listDataDtoArr = listDataDtoArr;
    }


    /**
     * Gets the rateSum value for this QueryListRespDto.
     * 
     * @return rateSum
     */
    public java.lang.String getRateSum() {
        return rateSum;
    }


    /**
     * Sets the rateSum value for this QueryListRespDto.
     * 
     * @param rateSum
     */
    public void setRateSum(java.lang.String rateSum) {
        this.rateSum = rateSum;
    }


    /**
     * Gets the result value for this QueryListRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryListRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryListRespDto)) return false;
        QueryListRespDto other = (QueryListRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.durationSum==null && other.getDurationSum()==null) || 
             (this.durationSum!=null &&
              this.durationSum.equals(other.getDurationSum()))) &&
            ((this.errdescription==null && other.getErrdescription()==null) || 
             (this.errdescription!=null &&
              this.errdescription.equals(other.getErrdescription()))) &&
            ((this.listDataDtoArr==null && other.getListDataDtoArr()==null) || 
             (this.listDataDtoArr!=null &&
              java.util.Arrays.equals(this.listDataDtoArr, other.getListDataDtoArr()))) &&
            ((this.rateSum==null && other.getRateSum()==null) || 
             (this.rateSum!=null &&
              this.rateSum.equals(other.getRateSum()))) &&
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
        if (getDurationSum() != null) {
            _hashCode += getDurationSum().hashCode();
        }
        if (getErrdescription() != null) {
            _hashCode += getErrdescription().hashCode();
        }
        if (getListDataDtoArr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListDataDtoArr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListDataDtoArr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRateSum() != null) {
            _hashCode += getRateSum().hashCode();
        }
        _hashCode += getResult();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryListRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durationSum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durationSum"));
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
        elemField.setFieldName("listDataDtoArr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listDataDtoArr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListDataDto"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateSum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rateSum"));
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
