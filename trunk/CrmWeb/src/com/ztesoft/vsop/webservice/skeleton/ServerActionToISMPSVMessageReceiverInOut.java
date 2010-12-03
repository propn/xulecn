
/**
 * ServerActionToISMPSVMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
        package com.ztesoft.vsop.webservice.skeleton;

        /**
        *  ServerActionToISMPSVMessageReceiverInOut message receiver
        */

        public class ServerActionToISMPSVMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        ServerActionToISMPSVSkeleton skel = (ServerActionToISMPSVSkeleton)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJava(op.getName().getLocalPart())) != null)){

        

            if("subActionFromVSOP".equals(methodName)){
                
                com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse subActionFromVSOPReqResponse1 = null;
	                        com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq wrappedParam =
                                                             (com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               subActionFromVSOPReqResponse1 =
                                                   
                                                   
                                                         skel.subActionFromVSOP(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), subActionFromVSOPReqResponse1, false);
                                    } else 

            if("chgActionFromVSOP".equals(methodName)){
                
                com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse chgActionFromVSOPReqResponse3 = null;
	                        com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq wrappedParam =
                                                             (com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               chgActionFromVSOPReqResponse3 =
                                                   
                                                   
                                                         skel.chgActionFromVSOP(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), chgActionFromVSOPReqResponse3, false);
                                    } else 

            if("unSubActionFromVSOP".equals(methodName)){
                
                com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse unSubActionFromVSOPReqResponse5 = null;
	                        com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq wrappedParam =
                                                             (com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unSubActionFromVSOPReqResponse5 =
                                                   
                                                   
                                                         skel.unSubActionFromVSOP(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unSubActionFromVSOPReqResponse5, false);
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq.class.equals(type)){
                
                           return com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse.class.equals(type)){
                
                           return com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq.class.equals(type)){
                
                           return com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse.class.equals(type)){
                
                           return com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq.class.equals(type)){
                
                           return com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse.class.equals(type)){
                
                           return com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    