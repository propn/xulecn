
/**
 * SayHelloSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
    package org.leixu.services.hello.client;
    /**
     *  SayHelloSkeletonInterface java skeleton interface for the axisService
     */
    public interface SayHelloSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param say
         */

        
                public org.leixu.services.hello.service.SayResponse say
                (
                  org.leixu.services.hello.service.Say say
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getPerson
         */

        
                public org.leixu.services.hello.service.GetPersonResponse getPerson
                (
                  org.leixu.services.hello.service.GetPerson getPerson
                 )
            ;
        
         }
    