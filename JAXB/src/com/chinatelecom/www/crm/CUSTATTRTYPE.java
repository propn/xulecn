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
 * �ͻ���չ��������
 * 
 * <p>Java class for CUST_ATTR_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CUST_ATTR_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CUST_ATTR_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CUST_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ATTR_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ATTR_VALUE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STATUS_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STATUS_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CUST_ATTR_TYPE", propOrder = {
    "custattrid",
    "custid",
    "attrcd",
    "attrvalue",
    "statuscd",
    "statusdate"
})
public class CUSTATTRTYPE {

    @XmlElement(name = "CUST_ATTR_ID", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String custattrid;
    @XmlElement(name = "CUST_ID", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String custid;
    @XmlElement(name = "ATTR_CD", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String attrcd;
    @XmlElement(name = "ATTR_VALUE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String attrvalue;
    @XmlElement(name = "STATUS_CD", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String statuscd;
    @XmlElement(name = "STATUS_DATE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String statusdate;

    /**
     * Gets the value of the custattrid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUSTATTRID() {
        return custattrid;
    }

    /**
     * Sets the value of the custattrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUSTATTRID(String value) {
        this.custattrid = value;
    }

    /**
     * Gets the value of the custid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUSTID() {
        return custid;
    }

    /**
     * Sets the value of the custid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUSTID(String value) {
        this.custid = value;
    }

    /**
     * Gets the value of the attrcd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRCD() {
        return attrcd;
    }

    /**
     * Sets the value of the attrcd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRCD(String value) {
        this.attrcd = value;
    }

    /**
     * Gets the value of the attrvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRVALUE() {
        return attrvalue;
    }

    /**
     * Sets the value of the attrvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRVALUE(String value) {
        this.attrvalue = value;
    }

    /**
     * Gets the value of the statuscd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUSCD() {
        return statuscd;
    }

    /**
     * Sets the value of the statuscd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUSCD(String value) {
        this.statuscd = value;
    }

    /**
     * Gets the value of the statusdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUSDATE() {
        return statusdate;
    }

    /**
     * Sets the value of the statusdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUSDATE(String value) {
        this.statusdate = value;
    }

}