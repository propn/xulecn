//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.01.25 at 01:43:57 ���� CST 
//


package com.chinatelecom.www.crm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ����ͨ������
 * 
 * <p>Java class for ORDER_REQ_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ORDER_REQ_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ORDER_ITEM_GROUP_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ORDER_REQ_TYPE", propOrder = {
    "orderitemgroupid"
})
public class ORDERREQTYPE {

    @XmlElement(name = "ORDER_ITEM_GROUP_ID", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected String orderitemgroupid;

    /**
     * Gets the value of the orderitemgroupid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORDERITEMGROUPID() {
        return orderitemgroupid;
    }

    /**
     * Sets the value of the orderitemgroupid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORDERITEMGROUPID(String value) {
        this.orderitemgroupid = value;
    }

}