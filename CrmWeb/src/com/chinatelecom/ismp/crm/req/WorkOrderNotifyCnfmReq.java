/**
 * WorkOrderNotifyCnfmReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.crm.req;

public class WorkOrderNotifyCnfmReq  implements java.io.Serializable {
    private java.lang.String streamingNo;

    private java.lang.String orderNO;

    private java.lang.String constNo;

    private java.lang.Integer workOrderResult;

    private java.lang.String workOrderResultDes;

    private java.lang.String workOrderCnfmAdmin;

    private java.lang.String workOrderCnfmTime;

    private java.lang.String zipBizId;

    private java.lang.String accessNo;

    public WorkOrderNotifyCnfmReq() {
    }

    public WorkOrderNotifyCnfmReq(
           java.lang.String streamingNo,
           java.lang.String orderNO,
           java.lang.String constNo,
           java.lang.Integer workOrderResult,
           java.lang.String workOrderResultDes,
           java.lang.String workOrderCnfmAdmin,
           java.lang.String workOrderCnfmTime,
           java.lang.String zipBizId,
           java.lang.String accessNo) {
           this.streamingNo = streamingNo;
           this.orderNO = orderNO;
           this.constNo = constNo;
           this.workOrderResult = workOrderResult;
           this.workOrderResultDes = workOrderResultDes;
           this.workOrderCnfmAdmin = workOrderCnfmAdmin;
           this.workOrderCnfmTime = workOrderCnfmTime;
           this.zipBizId = zipBizId;
           this.accessNo = accessNo;
    }


    /**
     * Gets the streamingNo value for this WorkOrderNotifyCnfmReq.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this WorkOrderNotifyCnfmReq.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }


    /**
     * Gets the orderNO value for this WorkOrderNotifyCnfmReq.
     * 
     * @return orderNO
     */
    public java.lang.String getOrderNO() {
        return orderNO;
    }


    /**
     * Sets the orderNO value for this WorkOrderNotifyCnfmReq.
     * 
     * @param orderNO
     */
    public void setOrderNO(java.lang.String orderNO) {
        this.orderNO = orderNO;
    }


    /**
     * Gets the constNo value for this WorkOrderNotifyCnfmReq.
     * 
     * @return constNo
     */
    public java.lang.String getConstNo() {
        return constNo;
    }


    /**
     * Sets the constNo value for this WorkOrderNotifyCnfmReq.
     * 
     * @param constNo
     */
    public void setConstNo(java.lang.String constNo) {
        this.constNo = constNo;
    }


    /**
     * Gets the workOrderResult value for this WorkOrderNotifyCnfmReq.
     * 
     * @return workOrderResult
     */
    public java.lang.Integer getWorkOrderResult() {
        return workOrderResult;
    }


    /**
     * Sets the workOrderResult value for this WorkOrderNotifyCnfmReq.
     * 
     * @param workOrderResult
     */
    public void setWorkOrderResult(java.lang.Integer workOrderResult) {
        this.workOrderResult = workOrderResult;
    }


    /**
     * Gets the workOrderResultDes value for this WorkOrderNotifyCnfmReq.
     * 
     * @return workOrderResultDes
     */
    public java.lang.String getWorkOrderResultDes() {
        return workOrderResultDes;
    }


    /**
     * Sets the workOrderResultDes value for this WorkOrderNotifyCnfmReq.
     * 
     * @param workOrderResultDes
     */
    public void setWorkOrderResultDes(java.lang.String workOrderResultDes) {
        this.workOrderResultDes = workOrderResultDes;
    }


    /**
     * Gets the workOrderCnfmAdmin value for this WorkOrderNotifyCnfmReq.
     * 
     * @return workOrderCnfmAdmin
     */
    public java.lang.String getWorkOrderCnfmAdmin() {
        return workOrderCnfmAdmin;
    }


    /**
     * Sets the workOrderCnfmAdmin value for this WorkOrderNotifyCnfmReq.
     * 
     * @param workOrderCnfmAdmin
     */
    public void setWorkOrderCnfmAdmin(java.lang.String workOrderCnfmAdmin) {
        this.workOrderCnfmAdmin = workOrderCnfmAdmin;
    }


    /**
     * Gets the workOrderCnfmTime value for this WorkOrderNotifyCnfmReq.
     * 
     * @return workOrderCnfmTime
     */
    public java.lang.String getWorkOrderCnfmTime() {
        return workOrderCnfmTime;
    }


    /**
     * Sets the workOrderCnfmTime value for this WorkOrderNotifyCnfmReq.
     * 
     * @param workOrderCnfmTime
     */
    public void setWorkOrderCnfmTime(java.lang.String workOrderCnfmTime) {
        this.workOrderCnfmTime = workOrderCnfmTime;
    }


    /**
     * Gets the zipBizId value for this WorkOrderNotifyCnfmReq.
     * 
     * @return zipBizId
     */
    public java.lang.String getZipBizId() {
        return zipBizId;
    }


    /**
     * Sets the zipBizId value for this WorkOrderNotifyCnfmReq.
     * 
     * @param zipBizId
     */
    public void setZipBizId(java.lang.String zipBizId) {
        this.zipBizId = zipBizId;
    }


    /**
     * Gets the accessNo value for this WorkOrderNotifyCnfmReq.
     * 
     * @return accessNo
     */
    public java.lang.String getAccessNo() {
        return accessNo;
    }


    /**
     * Sets the accessNo value for this WorkOrderNotifyCnfmReq.
     * 
     * @param accessNo
     */
    public void setAccessNo(java.lang.String accessNo) {
        this.accessNo = accessNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WorkOrderNotifyCnfmReq)) return false;
        WorkOrderNotifyCnfmReq other = (WorkOrderNotifyCnfmReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo()))) &&
            ((this.orderNO==null && other.getOrderNO()==null) || 
             (this.orderNO!=null &&
              this.orderNO.equals(other.getOrderNO()))) &&
            ((this.constNo==null && other.getConstNo()==null) || 
             (this.constNo!=null &&
              this.constNo.equals(other.getConstNo()))) &&
            ((this.workOrderResult==null && other.getWorkOrderResult()==null) || 
             (this.workOrderResult!=null &&
              this.workOrderResult.equals(other.getWorkOrderResult()))) &&
            ((this.workOrderResultDes==null && other.getWorkOrderResultDes()==null) || 
             (this.workOrderResultDes!=null &&
              this.workOrderResultDes.equals(other.getWorkOrderResultDes()))) &&
            ((this.workOrderCnfmAdmin==null && other.getWorkOrderCnfmAdmin()==null) || 
             (this.workOrderCnfmAdmin!=null &&
              this.workOrderCnfmAdmin.equals(other.getWorkOrderCnfmAdmin()))) &&
            ((this.workOrderCnfmTime==null && other.getWorkOrderCnfmTime()==null) || 
             (this.workOrderCnfmTime!=null &&
              this.workOrderCnfmTime.equals(other.getWorkOrderCnfmTime()))) &&
            ((this.zipBizId==null && other.getZipBizId()==null) || 
             (this.zipBizId!=null &&
              this.zipBizId.equals(other.getZipBizId()))) &&
            ((this.accessNo==null && other.getAccessNo()==null) || 
             (this.accessNo!=null &&
              this.accessNo.equals(other.getAccessNo())));
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
        if (getStreamingNo() != null) {
            _hashCode += getStreamingNo().hashCode();
        }
        if (getOrderNO() != null) {
            _hashCode += getOrderNO().hashCode();
        }
        if (getConstNo() != null) {
            _hashCode += getConstNo().hashCode();
        }
        if (getWorkOrderResult() != null) {
            _hashCode += getWorkOrderResult().hashCode();
        }
        if (getWorkOrderResultDes() != null) {
            _hashCode += getWorkOrderResultDes().hashCode();
        }
        if (getWorkOrderCnfmAdmin() != null) {
            _hashCode += getWorkOrderCnfmAdmin().hashCode();
        }
        if (getWorkOrderCnfmTime() != null) {
            _hashCode += getWorkOrderCnfmTime().hashCode();
        }
        if (getZipBizId() != null) {
            _hashCode += getZipBizId().hashCode();
        }
        if (getAccessNo() != null) {
            _hashCode += getAccessNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WorkOrderNotifyCnfmReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WorkOrderNotifyCnfmReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streamingNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streamingNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderNO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderNO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("constNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "constNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workOrderResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workOrderResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workOrderResultDes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workOrderResultDes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workOrderCnfmAdmin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workOrderCnfmAdmin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workOrderCnfmTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workOrderCnfmTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipBizId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zipBizId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accessNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
