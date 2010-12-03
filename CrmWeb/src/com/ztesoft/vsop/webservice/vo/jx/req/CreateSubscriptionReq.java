/**
 * CreateSubscriptionReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package com.ztesoft.vsop.webservice.vo.jx.req;


/**
 *  CreateSubscriptionReq bean class
 */
public class CreateSubscriptionReq implements org.apache.axis2.databinding.ADBBean {
    /**
     * field for DA
     */
    protected java.lang.String localDA;

    /**
     * field for DAType
     */
    protected int localDAType;

    /**
     * field for FA
     */
    protected java.lang.String localFA;

    /**
     * field for FAType
     */
    protected int localFAType;

    /**
     * field for OA
     */
    protected java.lang.String localOA;

    /**
     * field for OAType
     */
    protected int localOAType;

    /**
     * field for PProductOfferID
     */
    protected java.lang.String localPProductOfferID;

    /**
     * field for PackageID
     * This was an Array!
     */
    protected java.lang.String[] localPackageID;

    /**
     * field for ProductID
     * This was an Array!
     */
    protected java.lang.String[] localProductID;

    /**
     * field for VerUserId
     * This was an Array!
     */
    protected java.lang.String[] localVerUserId;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localVerUserIdTracker = false;

    /**
     * field for SrcDeviceID
     */
    protected java.lang.String localSrcDeviceID;

    /**
     * field for SrcDeviceType
     */
    protected int localSrcDeviceType;

    /**
     * field for StreamingNo
     */
    protected java.lang.String localStreamingNo;

    /* This type was generated from the piece of schema that had
       name = CreateSubscriptionReq
       Namespace URI = http://req.crm.ismp.chinatelecom.com
       Namespace Prefix = ns1
     */
    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("http://req.crm.ismp.chinatelecom.com")) {
            return "ns1";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getDA() {
        return localDA;
    }

    /**
     * Auto generated setter method
     * @param param DA
     */
    public void setDA(java.lang.String param) {
        this.localDA = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getDAType() {
        return localDAType;
    }

    /**
     * Auto generated setter method
     * @param param DAType
     */
    public void setDAType(int param) {
        this.localDAType = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getFA() {
        return localFA;
    }

    /**
     * Auto generated setter method
     * @param param FA
     */
    public void setFA(java.lang.String param) {
        this.localFA = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getFAType() {
        return localFAType;
    }

    /**
     * Auto generated setter method
     * @param param FAType
     */
    public void setFAType(int param) {
        this.localFAType = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getOA() {
        return localOA;
    }

    /**
     * Auto generated setter method
     * @param param OA
     */
    public void setOA(java.lang.String param) {
        this.localOA = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getOAType() {
        return localOAType;
    }

    /**
     * Auto generated setter method
     * @param param OAType
     */
    public void setOAType(int param) {
        this.localOAType = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPProductOfferID() {
        return localPProductOfferID;
    }

    /**
     * Auto generated setter method
     * @param param PProductOfferID
     */
    public void setPProductOfferID(java.lang.String param) {
        this.localPProductOfferID = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String[]
     */
    public java.lang.String[] getPackageID() {
        return localPackageID;
    }

    /**
     * validate the array for PackageID
     */
    protected void validatePackageID(java.lang.String[] param) {
        if ((param != null) && (param.length < 1)) {
            throw new java.lang.RuntimeException();
        }
    }

    /**
     * Auto generated setter method
     * @param param PackageID
     */
    public void setPackageID(java.lang.String[] param) {
        validatePackageID(param);

        this.localPackageID = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param java.lang.String
     */
    public void addPackageID(java.lang.String param) {
        if (localPackageID == null) {
            localPackageID = new java.lang.String[] {  };
        }

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localPackageID);
        list.add(param);
        this.localPackageID = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return java.lang.String[]
     */
    public java.lang.String[] getProductID() {
        return localProductID;
    }

    /**
     * validate the array for ProductID
     */
    protected void validateProductID(java.lang.String[] param) {
        if ((param != null) && (param.length < 1)) {
            throw new java.lang.RuntimeException();
        }
    }

    /**
     * Auto generated setter method
     * @param param ProductID
     */
    public void setProductID(java.lang.String[] param) {
        validateProductID(param);

        this.localProductID = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param java.lang.String
     */
    public void addProductID(java.lang.String param) {
        if (localProductID == null) {
            localProductID = new java.lang.String[] {  };
        }

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localProductID);
        list.add(param);
        this.localProductID = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return java.lang.String[]
     */
    public java.lang.String[] getVerUserId() {
        return localVerUserId;
    }

    /**
     * validate the array for VerUserId
     */
    protected void validateVerUserId(java.lang.String[] param) {
    }

    /**
     * Auto generated setter method
     * @param param VerUserId
     */
    public void setVerUserId(java.lang.String[] param) {
        validateVerUserId(param);

        if (param != null) {
            //update the setting tracker
            localVerUserIdTracker = true;
        } else {
            localVerUserIdTracker = true;
        }

        this.localVerUserId = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param java.lang.String
     */
    public void addVerUserId(java.lang.String param) {
        if (localVerUserId == null) {
            localVerUserId = new java.lang.String[] {  };
        }

        //update the setting tracker
        localVerUserIdTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localVerUserId);
        list.add(param);
        this.localVerUserId = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSrcDeviceID() {
        return localSrcDeviceID;
    }

    /**
     * Auto generated setter method
     * @param param SrcDeviceID
     */
    public void setSrcDeviceID(java.lang.String param) {
        this.localSrcDeviceID = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getSrcDeviceType() {
        return localSrcDeviceType;
    }

    /**
     * Auto generated setter method
     * @param param SrcDeviceType
     */
    public void setSrcDeviceType(int param) {
        this.localSrcDeviceType = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStreamingNo() {
        return localStreamingNo;
    }

    /**
     * Auto generated setter method
     * @param param StreamingNo
     */
    public void setStreamingNo(java.lang.String param) {
        this.localStreamingNo = param;
    }

    /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
    public static boolean isReaderMTOMAware(
        javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try {
            isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(
                        org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        } catch (java.lang.IllegalArgumentException e) {
            isReaderMTOMAware = false;
        }

        return isReaderMTOMAware;
    }

    /**
     *
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(
        final javax.xml.namespace.QName parentQName,
        final org.apache.axiom.om.OMFactory factory)
        throws org.apache.axis2.databinding.ADBException {
        org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this,
                parentQName) {
                public void serialize(
                    org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                    throws javax.xml.stream.XMLStreamException {
                    CreateSubscriptionReq.this.serialize(parentQName, factory,
                        xmlWriter);
                }
            };

        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName,
            factory, dataSource);
    }

    public void serialize(final javax.xml.namespace.QName parentQName,
        final org.apache.axiom.om.OMFactory factory,
        org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException,
            org.apache.axis2.databinding.ADBException {
        java.lang.String prefix = null;
        java.lang.String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();

        if (namespace != null) {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace,
                    parentQName.getLocalPart());
            } else {
                if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(),
                    namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        } else {
            xmlWriter.writeStartElement(parentQName.getLocalPart());
        }

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "DA", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "DA");
            }
        } else {
            xmlWriter.writeStartElement("DA");
        }

        if (localDA == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localDA);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "DAType", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "DAType");
            }
        } else {
            xmlWriter.writeStartElement("DAType");
        }

        if (localDAType == java.lang.Integer.MIN_VALUE) {
            throw new org.apache.axis2.databinding.ADBException(
                "DAType cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localDAType));
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "FA", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "FA");
            }
        } else {
            xmlWriter.writeStartElement("FA");
        }

        if (localFA == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localFA);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "FAType", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "FAType");
            }
        } else {
            xmlWriter.writeStartElement("FAType");
        }

        if (localFAType == java.lang.Integer.MIN_VALUE) {
            throw new org.apache.axis2.databinding.ADBException(
                "FAType cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localFAType));
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "OA", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "OA");
            }
        } else {
            xmlWriter.writeStartElement("OA");
        }

        if (localOA == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localOA);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "OAType", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "OAType");
            }
        } else {
            xmlWriter.writeStartElement("OAType");
        }

        if (localOAType == java.lang.Integer.MIN_VALUE) {
            throw new org.apache.axis2.databinding.ADBException(
                "OAType cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localOAType));
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "PProductOfferID", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "PProductOfferID");
            }
        } else {
            xmlWriter.writeStartElement("PProductOfferID");
        }

        if (localPProductOfferID == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localPProductOfferID);
        }

        xmlWriter.writeEndElement();

        if (localPackageID != null) {
            namespace = "";

            boolean emptyNamespace = (namespace == null) ||
                (namespace.length() == 0);
            prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);

            for (int i = 0; i < localPackageID.length; i++) {
                if (localPackageID[i] != null) {
                    if (!emptyNamespace) {
                        if (prefix == null) {
                            java.lang.String prefix2 = generatePrefix(namespace);

                            xmlWriter.writeStartElement(prefix2, "packageID",
                                namespace);
                            xmlWriter.writeNamespace(prefix2, namespace);
                            xmlWriter.setPrefix(prefix2, namespace);
                        } else {
                            xmlWriter.writeStartElement(namespace, "packageID");
                        }
                    } else {
                        xmlWriter.writeStartElement("packageID");
                    }

                    xmlWriter.writeCharacters(localPackageID[i]);

                    xmlWriter.writeEndElement();
                } else {
                    // write null attribute
                    namespace = "";

                    if (!namespace.equals("")) {
                        prefix = xmlWriter.getPrefix(namespace);

                        if (prefix == null) {
                            prefix = generatePrefix(namespace);

                            xmlWriter.writeStartElement(prefix, "packageID",
                                namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        } else {
                            xmlWriter.writeStartElement(namespace, "packageID");
                        }
                    } else {
                        xmlWriter.writeStartElement("packageID");
                    }

                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "nil",
                        "1", xmlWriter);
                    xmlWriter.writeEndElement();
                }
            }
        } else {
            // write the null attribute
            // write null attribute
            java.lang.String namespace2 = "";

            if (!namespace2.equals("")) {
                java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                if (prefix2 == null) {
                    prefix2 = generatePrefix(namespace2);

                    xmlWriter.writeStartElement(prefix2, "packageID", namespace2);
                    xmlWriter.writeNamespace(prefix2, namespace2);
                    xmlWriter.setPrefix(prefix2, namespace2);
                } else {
                    xmlWriter.writeStartElement(namespace2, "packageID");
                }
            } else {
                xmlWriter.writeStartElement("packageID");
            }

            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
            xmlWriter.writeEndElement();
        }

        if (localProductID != null) {
            namespace = "";

            boolean emptyNamespace = (namespace == null) ||
                (namespace.length() == 0);
            prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);

            for (int i = 0; i < localProductID.length; i++) {
                if (localProductID[i] != null) {
                    if (!emptyNamespace) {
                        if (prefix == null) {
                            java.lang.String prefix2 = generatePrefix(namespace);

                            xmlWriter.writeStartElement(prefix2, "productID",
                                namespace);
                            xmlWriter.writeNamespace(prefix2, namespace);
                            xmlWriter.setPrefix(prefix2, namespace);
                        } else {
                            xmlWriter.writeStartElement(namespace, "productID");
                        }
                    } else {
                        xmlWriter.writeStartElement("productID");
                    }

                    xmlWriter.writeCharacters(localProductID[i]);

                    xmlWriter.writeEndElement();
                } else {
                    // write null attribute
                    namespace = "";

                    if (!namespace.equals("")) {
                        prefix = xmlWriter.getPrefix(namespace);

                        if (prefix == null) {
                            prefix = generatePrefix(namespace);

                            xmlWriter.writeStartElement(prefix, "productID",
                                namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        } else {
                            xmlWriter.writeStartElement(namespace, "productID");
                        }
                    } else {
                        xmlWriter.writeStartElement("productID");
                    }

                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "nil",
                        "1", xmlWriter);
                    xmlWriter.writeEndElement();
                }
            }
        } else {
            // write the null attribute
            // write null attribute
            java.lang.String namespace2 = "";

            if (!namespace2.equals("")) {
                java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                if (prefix2 == null) {
                    prefix2 = generatePrefix(namespace2);

                    xmlWriter.writeStartElement(prefix2, "productID", namespace2);
                    xmlWriter.writeNamespace(prefix2, namespace2);
                    xmlWriter.setPrefix(prefix2, namespace2);
                } else {
                    xmlWriter.writeStartElement(namespace2, "productID");
                }
            } else {
                xmlWriter.writeStartElement("productID");
            }

            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
            xmlWriter.writeEndElement();
        }

        if (localVerUserIdTracker) {
            if (localVerUserId != null) {
                namespace = "";

                boolean emptyNamespace = (namespace == null) ||
                    (namespace.length() == 0);
                prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);

                for (int i = 0; i < localVerUserId.length; i++) {
                    if (localVerUserId[i] != null) {
                        if (!emptyNamespace) {
                            if (prefix == null) {
                                java.lang.String prefix2 = generatePrefix(namespace);

                                xmlWriter.writeStartElement(prefix2,
                                    "VerUserId", namespace);
                                xmlWriter.writeNamespace(prefix2, namespace);
                                xmlWriter.setPrefix(prefix2, namespace);
                            } else {
                                xmlWriter.writeStartElement(namespace,
                                    "VerUserId");
                            }
                        } else {
                            xmlWriter.writeStartElement("VerUserId");
                        }

                        xmlWriter.writeCharacters(localVerUserId[i]);

                        xmlWriter.writeEndElement();
                    } else {
                        // write null attribute
                        namespace = "";

                        if (!namespace.equals("")) {
                            prefix = xmlWriter.getPrefix(namespace);

                            if (prefix == null) {
                                prefix = generatePrefix(namespace);

                                xmlWriter.writeStartElement(prefix,
                                    "VerUserId", namespace);
                                xmlWriter.writeNamespace(prefix, namespace);
                                xmlWriter.setPrefix(prefix, namespace);
                            } else {
                                xmlWriter.writeStartElement(namespace,
                                    "VerUserId");
                            }
                        } else {
                            xmlWriter.writeStartElement("VerUserId");
                        }

                        writeAttribute("xsi",
                            "http://www.w3.org/2001/XMLSchema-instance", "nil",
                            "1", xmlWriter);
                        xmlWriter.writeEndElement();
                    }
                }
            } else {
                // write the null attribute
                // write null attribute
                java.lang.String namespace2 = "";

                if (!namespace2.equals("")) {
                    java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                    if (prefix2 == null) {
                        prefix2 = generatePrefix(namespace2);

                        xmlWriter.writeStartElement(prefix2, "VerUserId",
                            namespace2);
                        xmlWriter.writeNamespace(prefix2, namespace2);
                        xmlWriter.setPrefix(prefix2, namespace2);
                    } else {
                        xmlWriter.writeStartElement(namespace2, "VerUserId");
                    }
                } else {
                    xmlWriter.writeStartElement("VerUserId");
                }

                // write the nil attribute
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "nil", "1",
                    xmlWriter);
                xmlWriter.writeEndElement();
            }
        }

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "srcDeviceID", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "srcDeviceID");
            }
        } else {
            xmlWriter.writeStartElement("srcDeviceID");
        }

        if (localSrcDeviceID == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localSrcDeviceID);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "srcDeviceType", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "srcDeviceType");
            }
        } else {
            xmlWriter.writeStartElement("srcDeviceType");
        }

        if (localSrcDeviceType == java.lang.Integer.MIN_VALUE) {
            throw new org.apache.axis2.databinding.ADBException(
                "srcDeviceType cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localSrcDeviceType));
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "streamingNo", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "streamingNo");
            }
        } else {
            xmlWriter.writeStartElement("streamingNo");
        }

        if (localStreamingNo == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localStreamingNo);
        }

        xmlWriter.writeEndElement();

        xmlWriter.writeEndElement();
    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix,
        java.lang.String namespace, java.lang.String attName,
        java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (xmlWriter.getPrefix(namespace) == null) {
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        xmlWriter.writeAttribute(namespace, attName, attValue);
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace,
        java.lang.String attName, java.lang.String attValue,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attValue);
        } else {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(namespace, attName, attValue);
        }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace,
        java.lang.String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String attributeNamespace = qname.getNamespaceURI();
        java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

        if (attributePrefix == null) {
            attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
        }

        java.lang.String attributeValue;

        if (attributePrefix.trim().length() > 0) {
            attributeValue = attributePrefix + ":" + qname.getLocalPart();
        } else {
            attributeValue = qname.getLocalPart();
        }

        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attributeValue);
        } else {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(namespace, attName, attributeValue);
        }
    }

    /**
     *  method to handle Qnames
     */
    private void writeQName(javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String namespaceURI = qname.getNamespaceURI();

        if (namespaceURI != null) {
            java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

            if (prefix == null) {
                prefix = generatePrefix(namespaceURI);
                xmlWriter.writeNamespace(prefix, namespaceURI);
                xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0) {
                xmlWriter.writeCharacters(prefix + ":" +
                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            } else {
                // i.e this is the default namespace
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    qname));
        }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (qnames != null) {
            // we have to store this data until last moment since it is not possible to write any
            // namespace data after writing the charactor data
            java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
            java.lang.String namespaceURI = null;
            java.lang.String prefix = null;

            for (int i = 0; i < qnames.length; i++) {
                if (i > 0) {
                    stringToWrite.append(" ");
                }

                namespaceURI = qnames[i].getNamespaceURI();

                if (namespaceURI != null) {
                    prefix = xmlWriter.getPrefix(namespaceURI);

                    if ((prefix == null) || (prefix.length() == 0)) {
                        prefix = generatePrefix(namespaceURI);
                        xmlWriter.writeNamespace(prefix, namespaceURI);
                        xmlWriter.setPrefix(prefix, namespaceURI);
                    }

                    if (prefix.trim().length() > 0) {
                        stringToWrite.append(prefix).append(":")
                                     .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                } else {
                    stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qnames[i]));
                }
            }

            xmlWriter.writeCharacters(stringToWrite.toString());
        }
    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(
        javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null) {
            prefix = generatePrefix(namespace);

            while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        return prefix;
    }

    /**
     * databinding method to get an XML representation of this object
     *
     */
    public javax.xml.stream.XMLStreamReader getPullParser(
        javax.xml.namespace.QName qName)
        throws org.apache.axis2.databinding.ADBException {
        java.util.ArrayList elementList = new java.util.ArrayList();
        java.util.ArrayList attribList = new java.util.ArrayList();

        elementList.add(new javax.xml.namespace.QName("", "DA"));

        elementList.add((localDA == null) ? null
                                          : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localDA));

        elementList.add(new javax.xml.namespace.QName("", "DAType"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localDAType));

        elementList.add(new javax.xml.namespace.QName("", "FA"));

        elementList.add((localFA == null) ? null
                                          : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localFA));

        elementList.add(new javax.xml.namespace.QName("", "FAType"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localFAType));

        elementList.add(new javax.xml.namespace.QName("", "OA"));

        elementList.add((localOA == null) ? null
                                          : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localOA));

        elementList.add(new javax.xml.namespace.QName("", "OAType"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localOAType));

        elementList.add(new javax.xml.namespace.QName("", "PProductOfferID"));

        elementList.add((localPProductOfferID == null) ? null
                                                       : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localPProductOfferID));

        if (localPackageID != null) {
            for (int i = 0; i < localPackageID.length; i++) {
                if (localPackageID[i] != null) {
                    elementList.add(new javax.xml.namespace.QName("",
                            "packageID"));
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localPackageID[i]));
                } else {
                    elementList.add(new javax.xml.namespace.QName("",
                            "packageID"));
                    elementList.add(null);
                }
            }
        } else {
            elementList.add(new javax.xml.namespace.QName("", "packageID"));
            elementList.add(null);
        }

        if (localProductID != null) {
            for (int i = 0; i < localProductID.length; i++) {
                if (localProductID[i] != null) {
                    elementList.add(new javax.xml.namespace.QName("",
                            "productID"));
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localProductID[i]));
                } else {
                    elementList.add(new javax.xml.namespace.QName("",
                            "productID"));
                    elementList.add(null);
                }
            }
        } else {
            elementList.add(new javax.xml.namespace.QName("", "productID"));
            elementList.add(null);
        }

        if (localVerUserIdTracker) {
            if (localVerUserId != null) {
                for (int i = 0; i < localVerUserId.length; i++) {
                    if (localVerUserId[i] != null) {
                        elementList.add(new javax.xml.namespace.QName("",
                                "VerUserId"));
                        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                localVerUserId[i]));
                    } else {
                        elementList.add(new javax.xml.namespace.QName("",
                                "VerUserId"));
                        elementList.add(null);
                    }
                }
            } else {
                elementList.add(new javax.xml.namespace.QName("", "VerUserId"));
                elementList.add(null);
            }
        }

        elementList.add(new javax.xml.namespace.QName("", "srcDeviceID"));

        elementList.add((localSrcDeviceID == null) ? null
                                                   : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localSrcDeviceID));

        elementList.add(new javax.xml.namespace.QName("", "srcDeviceType"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localSrcDeviceType));

        elementList.add(new javax.xml.namespace.QName("", "streamingNo"));

        elementList.add((localStreamingNo == null) ? null
                                                   : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localStreamingNo));

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,
            elementList.toArray(), attribList.toArray());
    }

    /**
     *  Factory class that keeps the parse method
     */
    public static class Factory {
        /**
         * static method to create the object
         * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
         *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
         * Postcondition: If this object is an element, the reader is positioned at its end element
         *                If this object is a complex type, the reader is positioned at the end element of its outer element
         */
        public static CreateSubscriptionReq parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            CreateSubscriptionReq object = new CreateSubscriptionReq();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix = "";
            java.lang.String namespaceuri = "";

            try {
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.getAttributeValue(
                            "http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                    java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "type");

                    if (fullTypeName != null) {
                        java.lang.String nsPrefix = null;

                        if (fullTypeName.indexOf(":") > -1) {
                            nsPrefix = fullTypeName.substring(0,
                                    fullTypeName.indexOf(":"));
                        }

                        nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                        java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                    ":") + 1);

                        if (!"CreateSubscriptionReq".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (CreateSubscriptionReq) com.ztesoft.vsop.webservice.vo.jx.req.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                reader.next();

                java.util.ArrayList list8 = new java.util.ArrayList();

                java.util.ArrayList list9 = new java.util.ArrayList();

                java.util.ArrayList list10 = new java.util.ArrayList();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "DA").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setDA(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "DAType").equals(
                            reader.getName())) {
                    java.lang.String content = reader.getElementText();

                    object.setDAType(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "FA").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setFA(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "FAType").equals(
                            reader.getName())) {
                    java.lang.String content = reader.getElementText();

                    object.setFAType(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "OA").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setOA(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "OAType").equals(
                            reader.getName())) {
                    java.lang.String content = reader.getElementText();

                    object.setOAType(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "PProductOfferID").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setPProductOfferID(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "packageID").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        list8.add(null);

                        reader.next();
                    } else {
                        list8.add(reader.getElementText());
                    }

                    //loop until we find a start element that is not part of this array
                    boolean loopDone8 = false;

                    while (!loopDone8) {
                        // Ensure we are at the EndElement
                        while (!reader.isEndElement()) {
                            reader.next();
                        }

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone8 = true;
                        } else {
                            if (new javax.xml.namespace.QName("", "packageID").equals(
                                        reader.getName())) {
                                nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                        "nil");

                                if ("true".equals(nillableValue) ||
                                        "1".equals(nillableValue)) {
                                    list8.add(null);

                                    reader.next();
                                } else {
                                    list8.add(reader.getElementText());
                                }
                            } else {
                                loopDone8 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setPackageID((java.lang.String[]) list8.toArray(
                            new java.lang.String[list8.size()]));
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "productID").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        list9.add(null);

                        reader.next();
                    } else {
                        list9.add(reader.getElementText());
                    }

                    //loop until we find a start element that is not part of this array
                    boolean loopDone9 = false;

                    while (!loopDone9) {
                        // Ensure we are at the EndElement
                        while (!reader.isEndElement()) {
                            reader.next();
                        }

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone9 = true;
                        } else {
                            if (new javax.xml.namespace.QName("", "productID").equals(
                                        reader.getName())) {
                                nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                        "nil");

                                if ("true".equals(nillableValue) ||
                                        "1".equals(nillableValue)) {
                                    list9.add(null);

                                    reader.next();
                                } else {
                                    list9.add(reader.getElementText());
                                }
                            } else {
                                loopDone9 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setProductID((java.lang.String[]) list9.toArray(
                            new java.lang.String[list9.size()]));
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "VerUserId").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        list10.add(null);

                        reader.next();
                    } else {
                        list10.add(reader.getElementText());
                    }

                    //loop until we find a start element that is not part of this array
                    boolean loopDone10 = false;

                    while (!loopDone10) {
                        // Ensure we are at the EndElement
                        while (!reader.isEndElement()) {
                            reader.next();
                        }

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone10 = true;
                        } else {
                            if (new javax.xml.namespace.QName("", "VerUserId").equals(
                                        reader.getName())) {
                                nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                        "nil");

                                if ("true".equals(nillableValue) ||
                                        "1".equals(nillableValue)) {
                                    list10.add(null);

                                    reader.next();
                                } else {
                                    list10.add(reader.getElementText());
                                }
                            } else {
                                loopDone10 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setVerUserId((java.lang.String[]) list10.toArray(
                            new java.lang.String[list10.size()]));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "srcDeviceID").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setSrcDeviceID(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "srcDeviceType").equals(
                            reader.getName())) {
                    java.lang.String content = reader.getElementText();

                    object.setSrcDeviceType(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "streamingNo").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setStreamingNo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement()) {
                    // A start element we are not expecting indicates a trailing invalid property
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
