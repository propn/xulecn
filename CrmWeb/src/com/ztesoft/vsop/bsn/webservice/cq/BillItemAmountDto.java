/**
 * BillItemAmountDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class BillItemAmountDto  implements java.io.Serializable {
    private java.lang.String billAmount;

    private java.lang.String billItem;

    private java.lang.String disctAmount;

    private java.lang.String oldBillAmount;

    private java.lang.String payDate;

    private java.lang.String state;

    public BillItemAmountDto() {
    }

    public BillItemAmountDto(
           java.lang.String billAmount,
           java.lang.String billItem,
           java.lang.String disctAmount,
           java.lang.String oldBillAmount,
           java.lang.String payDate,
           java.lang.String state) {
           this.billAmount = billAmount;
           this.billItem = billItem;
           this.disctAmount = disctAmount;
           this.oldBillAmount = oldBillAmount;
           this.payDate = payDate;
           this.state = state;
    }


    /**
     * Gets the billAmount value for this BillItemAmountDto.
     * 
     * @return billAmount
     */
    public java.lang.String getBillAmount() {
        return billAmount;
    }


    /**
     * Sets the billAmount value for this BillItemAmountDto.
     * 
     * @param billAmount
     */
    public void setBillAmount(java.lang.String billAmount) {
        this.billAmount = billAmount;
    }


    /**
     * Gets the billItem value for this BillItemAmountDto.
     * 
     * @return billItem
     */
    public java.lang.String getBillItem() {
        return billItem;
    }


    /**
     * Sets the billItem value for this BillItemAmountDto.
     * 
     * @param billItem
     */
    public void setBillItem(java.lang.String billItem) {
        this.billItem = billItem;
    }


    /**
     * Gets the disctAmount value for this BillItemAmountDto.
     * 
     * @return disctAmount
     */
    public java.lang.String getDisctAmount() {
        return disctAmount;
    }


    /**
     * Sets the disctAmount value for this BillItemAmountDto.
     * 
     * @param disctAmount
     */
    public void setDisctAmount(java.lang.String disctAmount) {
        this.disctAmount = disctAmount;
    }


    /**
     * Gets the oldBillAmount value for this BillItemAmountDto.
     * 
     * @return oldBillAmount
     */
    public java.lang.String getOldBillAmount() {
        return oldBillAmount;
    }


    /**
     * Sets the oldBillAmount value for this BillItemAmountDto.
     * 
     * @param oldBillAmount
     */
    public void setOldBillAmount(java.lang.String oldBillAmount) {
        this.oldBillAmount = oldBillAmount;
    }


    /**
     * Gets the payDate value for this BillItemAmountDto.
     * 
     * @return payDate
     */
    public java.lang.String getPayDate() {
        return payDate;
    }


    /**
     * Sets the payDate value for this BillItemAmountDto.
     * 
     * @param payDate
     */
    public void setPayDate(java.lang.String payDate) {
        this.payDate = payDate;
    }


    /**
     * Gets the state value for this BillItemAmountDto.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this BillItemAmountDto.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BillItemAmountDto)) return false;
        BillItemAmountDto other = (BillItemAmountDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.billAmount==null && other.getBillAmount()==null) || 
             (this.billAmount!=null &&
              this.billAmount.equals(other.getBillAmount()))) &&
            ((this.billItem==null && other.getBillItem()==null) || 
             (this.billItem!=null &&
              this.billItem.equals(other.getBillItem()))) &&
            ((this.disctAmount==null && other.getDisctAmount()==null) || 
             (this.disctAmount!=null &&
              this.disctAmount.equals(other.getDisctAmount()))) &&
            ((this.oldBillAmount==null && other.getOldBillAmount()==null) || 
             (this.oldBillAmount!=null &&
              this.oldBillAmount.equals(other.getOldBillAmount()))) &&
            ((this.payDate==null && other.getPayDate()==null) || 
             (this.payDate!=null &&
              this.payDate.equals(other.getPayDate()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState())));
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
        if (getBillAmount() != null) {
            _hashCode += getBillAmount().hashCode();
        }
        if (getBillItem() != null) {
            _hashCode += getBillItem().hashCode();
        }
        if (getDisctAmount() != null) {
            _hashCode += getDisctAmount().hashCode();
        }
        if (getOldBillAmount() != null) {
            _hashCode += getOldBillAmount().hashCode();
        }
        if (getPayDate() != null) {
            _hashCode += getPayDate().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BillItemAmountDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillItemAmountDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "billAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "billItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disctAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "disctAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldBillAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oldBillAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "payDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("", "state"));
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
