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
 * 客户联系人类型
 * 
 * <p>Java class for CUST_CONTACT_INFO_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CUST_CONTACT_INFO_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CONTACT_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CUST_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HEAD_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CONTACT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CONTACT_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CONTACT_GENDER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CONTACT_ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CONTACT_EMPLOYER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HOME_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFFICE_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MOBILE_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CONTACT_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="E_MAIL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POSTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POST_ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STATUS_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STATUS_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CREATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CUST_CONTACT_INFO_TYPE", propOrder = {
    "contactid",
    "custid",
    "headflag",
    "contacttype",
    "contactname",
    "contactgender",
    "contactaddress",
    "contactemployer",
    "homephone",
    "officephone",
    "mobilephone",
    "contactdesc",
    "email",
    "postcode",
    "postaddress",
    "fax",
    "statuscd",
    "statusdate",
    "createdate"
})
public class CUSTCONTACTINFOTYPE {

    @XmlElement(name = "CONTACT_ID", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected String contactid;
    @XmlElement(name = "CUST_ID", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String custid;
    @XmlElement(name = "HEAD_FLAG", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String headflag;
    @XmlElement(name = "CONTACT_TYPE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String contacttype;
    @XmlElement(name = "CONTACT_NAME", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String contactname;
    @XmlElement(name = "CONTACT_GENDER", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String contactgender;
    @XmlElement(name = "CONTACT_ADDRESS", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String contactaddress;
    @XmlElement(name = "CONTACT_EMPLOYER", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String contactemployer;
    @XmlElement(name = "HOME_PHONE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String homephone;
    @XmlElement(name = "OFFICE_PHONE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String officephone;
    @XmlElement(name = "MOBILE_PHONE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String mobilephone;
    @XmlElement(name = "CONTACT_DESC", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String contactdesc;
    @XmlElement(name = "E_MAIL", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String email;
    @XmlElement(name = "POSTCODE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String postcode;
    @XmlElement(name = "POST_ADDRESS", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String postaddress;
    @XmlElement(name = "FAX", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String fax;
    @XmlElement(name = "STATUS_CD", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String statuscd;
    @XmlElement(name = "STATUS_DATE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String statusdate;
    @XmlElement(name = "CREATE_DATE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String createdate;

    /**
     * Gets the value of the contactid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTID() {
        return contactid;
    }

    /**
     * Sets the value of the contactid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTID(String value) {
        this.contactid = value;
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
     * Gets the value of the headflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHEADFLAG() {
        return headflag;
    }

    /**
     * Sets the value of the headflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHEADFLAG(String value) {
        this.headflag = value;
    }

    /**
     * Gets the value of the contacttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTTYPE() {
        return contacttype;
    }

    /**
     * Sets the value of the contacttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTTYPE(String value) {
        this.contacttype = value;
    }

    /**
     * Gets the value of the contactname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTNAME() {
        return contactname;
    }

    /**
     * Sets the value of the contactname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTNAME(String value) {
        this.contactname = value;
    }

    /**
     * Gets the value of the contactgender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTGENDER() {
        return contactgender;
    }

    /**
     * Sets the value of the contactgender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTGENDER(String value) {
        this.contactgender = value;
    }

    /**
     * Gets the value of the contactaddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTADDRESS() {
        return contactaddress;
    }

    /**
     * Sets the value of the contactaddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTADDRESS(String value) {
        this.contactaddress = value;
    }

    /**
     * Gets the value of the contactemployer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTEMPLOYER() {
        return contactemployer;
    }

    /**
     * Sets the value of the contactemployer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTEMPLOYER(String value) {
        this.contactemployer = value;
    }

    /**
     * Gets the value of the homephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHOMEPHONE() {
        return homephone;
    }

    /**
     * Sets the value of the homephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHOMEPHONE(String value) {
        this.homephone = value;
    }

    /**
     * Gets the value of the officephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOFFICEPHONE() {
        return officephone;
    }

    /**
     * Sets the value of the officephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOFFICEPHONE(String value) {
        this.officephone = value;
    }

    /**
     * Gets the value of the mobilephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOBILEPHONE() {
        return mobilephone;
    }

    /**
     * Sets the value of the mobilephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOBILEPHONE(String value) {
        this.mobilephone = value;
    }

    /**
     * Gets the value of the contactdesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTDESC() {
        return contactdesc;
    }

    /**
     * Sets the value of the contactdesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTDESC(String value) {
        this.contactdesc = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMAIL() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMAIL(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the postcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSTCODE() {
        return postcode;
    }

    /**
     * Sets the value of the postcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSTCODE(String value) {
        this.postcode = value;
    }

    /**
     * Gets the value of the postaddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSTADDRESS() {
        return postaddress;
    }

    /**
     * Sets the value of the postaddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSTADDRESS(String value) {
        this.postaddress = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAX() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAX(String value) {
        this.fax = value;
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

    /**
     * Gets the value of the createdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCREATEDATE() {
        return createdate;
    }

    /**
     * Sets the value of the createdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCREATEDATE(String value) {
        this.createdate = value;
    }

}
