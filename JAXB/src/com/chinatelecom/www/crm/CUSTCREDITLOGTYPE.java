//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.01.25 at 01:43:57 下午 CST 
//


package com.chinatelecom.www.crm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 客户信用度变更记录类型
 * 
 * <p>Java class for CUST_CREDIT_LOG_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CUST_CREDIT_LOG_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CUST_CREDIT_LOG_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CUST_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CREDIT_LEVEL_CHANGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CREDIT_VALUE_CHANGE" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="CREDIT_CHANGE_REASON" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CHANGE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CUST_CREDIT_LOG_TYPE", propOrder = {
    "custcreditlogid",
    "custid",
    "creditlevelchange",
    "creditvaluechange",
    "creditchangereason",
    "changedate"
})
public class CUSTCREDITLOGTYPE {

    @XmlElement(name = "CUST_CREDIT_LOG_ID", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected String custcreditlogid;
    @XmlElement(name = "CUST_ID", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String custid;
    @XmlElement(name = "CREDIT_LEVEL_CHANGE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String creditlevelchange;
    @XmlElement(name = "CREDIT_VALUE_CHANGE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected Object creditvaluechange;
    @XmlElement(name = "CREDIT_CHANGE_REASON", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String creditchangereason;
    @XmlElement(name = "CHANGE_DATE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String changedate;

    /**
     * Gets the value of the custcreditlogid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUSTCREDITLOGID() {
        return custcreditlogid;
    }

    /**
     * Sets the value of the custcreditlogid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUSTCREDITLOGID(String value) {
        this.custcreditlogid = value;
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
     * Gets the value of the creditlevelchange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCREDITLEVELCHANGE() {
        return creditlevelchange;
    }

    /**
     * Sets the value of the creditlevelchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCREDITLEVELCHANGE(String value) {
        this.creditlevelchange = value;
    }

    /**
     * Gets the value of the creditvaluechange property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getCREDITVALUECHANGE() {
        return creditvaluechange;
    }

    /**
     * Sets the value of the creditvaluechange property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setCREDITVALUECHANGE(Object value) {
        this.creditvaluechange = value;
    }

    /**
     * Gets the value of the creditchangereason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCREDITCHANGEREASON() {
        return creditchangereason;
    }

    /**
     * Sets the value of the creditchangereason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCREDITCHANGEREASON(String value) {
        this.creditchangereason = value;
    }

    /**
     * Gets the value of the changedate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCHANGEDATE() {
        return changedate;
    }

    /**
     * Sets the value of the changedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHANGEDATE(String value) {
        this.changedate = value;
    }

}
