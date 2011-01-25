//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.01.25 at 01:43:57 下午 CST 
//


package com.chinatelecom.www.crm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 查询客户输出
 * 
 * <p>Java class for QRY_CUST_RES_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QRY_CUST_RES_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PUB_RES" type="{http://www.chinatelecom.com/crm/comm/}PUB_REQ_TYPE"/>
 *         &lt;element name="QRY_RES" type="{http://www.chinatelecom.com/crm/comm/}QRY_RES_TYPE" minOccurs="0"/>
 *         &lt;element name="CUST" type="{http://www.chinatelecom.com/crm/comm/}CUST_TYPE" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QRY_CUST_RES_TYPE", propOrder = {
    "pubres",
    "qryres",
    "cust"
})
public class QRYCUSTRESTYPE {

    @XmlElement(name = "PUB_RES", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected PUBREQTYPE pubres;
    @XmlElement(name = "QRY_RES", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected QRYRESTYPE qryres;
    @XmlElement(name = "CUST", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected List<CUSTTYPE> cust;

    /**
     * Gets the value of the pubres property.
     * 
     * @return
     *     possible object is
     *     {@link PUBREQTYPE }
     *     
     */
    public PUBREQTYPE getPUBRES() {
        return pubres;
    }

    /**
     * Sets the value of the pubres property.
     * 
     * @param value
     *     allowed object is
     *     {@link PUBREQTYPE }
     *     
     */
    public void setPUBRES(PUBREQTYPE value) {
        this.pubres = value;
    }

    /**
     * Gets the value of the qryres property.
     * 
     * @return
     *     possible object is
     *     {@link QRYRESTYPE }
     *     
     */
    public QRYRESTYPE getQRYRES() {
        return qryres;
    }

    /**
     * Sets the value of the qryres property.
     * 
     * @param value
     *     allowed object is
     *     {@link QRYRESTYPE }
     *     
     */
    public void setQRYRES(QRYRESTYPE value) {
        this.qryres = value;
    }

    /**
     * Gets the value of the cust property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cust property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCUST().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CUSTTYPE }
     * 
     * 
     */
    public List<CUSTTYPE> getCUST() {
        if (cust == null) {
            cust = new ArrayList<CUSTTYPE>();
        }
        return this.cust;
    }

}
