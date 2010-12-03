/**
 * ChangeInfoDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ChangeInfoDto  implements java.io.Serializable {
    private java.lang.String cardNo;

    private java.lang.String changeDate;

    private java.lang.String changeReason;

    private java.lang.String changeType;

    private java.lang.String comment;

    private java.lang.String newBalance;

    private java.lang.String oldBalance;

    private java.lang.String partyRole;

    public ChangeInfoDto() {
    }

    public ChangeInfoDto(
           java.lang.String cardNo,
           java.lang.String changeDate,
           java.lang.String changeReason,
           java.lang.String changeType,
           java.lang.String comment,
           java.lang.String newBalance,
           java.lang.String oldBalance,
           java.lang.String partyRole) {
           this.cardNo = cardNo;
           this.changeDate = changeDate;
           this.changeReason = changeReason;
           this.changeType = changeType;
           this.comment = comment;
           this.newBalance = newBalance;
           this.oldBalance = oldBalance;
           this.partyRole = partyRole;
    }


    /**
     * Gets the cardNo value for this ChangeInfoDto.
     * 
     * @return cardNo
     */
    public java.lang.String getCardNo() {
        return cardNo;
    }


    /**
     * Sets the cardNo value for this ChangeInfoDto.
     * 
     * @param cardNo
     */
    public void setCardNo(java.lang.String cardNo) {
        this.cardNo = cardNo;
    }


    /**
     * Gets the changeDate value for this ChangeInfoDto.
     * 
     * @return changeDate
     */
    public java.lang.String getChangeDate() {
        return changeDate;
    }


    /**
     * Sets the changeDate value for this ChangeInfoDto.
     * 
     * @param changeDate
     */
    public void setChangeDate(java.lang.String changeDate) {
        this.changeDate = changeDate;
    }


    /**
     * Gets the changeReason value for this ChangeInfoDto.
     * 
     * @return changeReason
     */
    public java.lang.String getChangeReason() {
        return changeReason;
    }


    /**
     * Sets the changeReason value for this ChangeInfoDto.
     * 
     * @param changeReason
     */
    public void setChangeReason(java.lang.String changeReason) {
        this.changeReason = changeReason;
    }


    /**
     * Gets the changeType value for this ChangeInfoDto.
     * 
     * @return changeType
     */
    public java.lang.String getChangeType() {
        return changeType;
    }


    /**
     * Sets the changeType value for this ChangeInfoDto.
     * 
     * @param changeType
     */
    public void setChangeType(java.lang.String changeType) {
        this.changeType = changeType;
    }


    /**
     * Gets the comment value for this ChangeInfoDto.
     * 
     * @return comment
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this ChangeInfoDto.
     * 
     * @param comment
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }


    /**
     * Gets the newBalance value for this ChangeInfoDto.
     * 
     * @return newBalance
     */
    public java.lang.String getNewBalance() {
        return newBalance;
    }


    /**
     * Sets the newBalance value for this ChangeInfoDto.
     * 
     * @param newBalance
     */
    public void setNewBalance(java.lang.String newBalance) {
        this.newBalance = newBalance;
    }


    /**
     * Gets the oldBalance value for this ChangeInfoDto.
     * 
     * @return oldBalance
     */
    public java.lang.String getOldBalance() {
        return oldBalance;
    }


    /**
     * Sets the oldBalance value for this ChangeInfoDto.
     * 
     * @param oldBalance
     */
    public void setOldBalance(java.lang.String oldBalance) {
        this.oldBalance = oldBalance;
    }


    /**
     * Gets the partyRole value for this ChangeInfoDto.
     * 
     * @return partyRole
     */
    public java.lang.String getPartyRole() {
        return partyRole;
    }


    /**
     * Sets the partyRole value for this ChangeInfoDto.
     * 
     * @param partyRole
     */
    public void setPartyRole(java.lang.String partyRole) {
        this.partyRole = partyRole;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChangeInfoDto)) return false;
        ChangeInfoDto other = (ChangeInfoDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cardNo==null && other.getCardNo()==null) || 
             (this.cardNo!=null &&
              this.cardNo.equals(other.getCardNo()))) &&
            ((this.changeDate==null && other.getChangeDate()==null) || 
             (this.changeDate!=null &&
              this.changeDate.equals(other.getChangeDate()))) &&
            ((this.changeReason==null && other.getChangeReason()==null) || 
             (this.changeReason!=null &&
              this.changeReason.equals(other.getChangeReason()))) &&
            ((this.changeType==null && other.getChangeType()==null) || 
             (this.changeType!=null &&
              this.changeType.equals(other.getChangeType()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment()))) &&
            ((this.newBalance==null && other.getNewBalance()==null) || 
             (this.newBalance!=null &&
              this.newBalance.equals(other.getNewBalance()))) &&
            ((this.oldBalance==null && other.getOldBalance()==null) || 
             (this.oldBalance!=null &&
              this.oldBalance.equals(other.getOldBalance()))) &&
            ((this.partyRole==null && other.getPartyRole()==null) || 
             (this.partyRole!=null &&
              this.partyRole.equals(other.getPartyRole())));
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
        if (getCardNo() != null) {
            _hashCode += getCardNo().hashCode();
        }
        if (getChangeDate() != null) {
            _hashCode += getChangeDate().hashCode();
        }
        if (getChangeReason() != null) {
            _hashCode += getChangeReason().hashCode();
        }
        if (getChangeType() != null) {
            _hashCode += getChangeType().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        if (getNewBalance() != null) {
            _hashCode += getNewBalance().hashCode();
        }
        if (getOldBalance() != null) {
            _hashCode += getOldBalance().hashCode();
        }
        if (getPartyRole() != null) {
            _hashCode += getPartyRole().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeInfoDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ChangeInfoDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cardNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "changeDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeReason");
        elemField.setXmlName(new javax.xml.namespace.QName("", "changeReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "changeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "newBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oldBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partyRole");
        elemField.setXmlName(new javax.xml.namespace.QName("", "partyRole"));
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
