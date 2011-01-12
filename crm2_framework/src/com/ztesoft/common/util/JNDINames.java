package com.ztesoft.common.util;

/**
 * This class is the central location to store the internal
 * JNDI names of various entities. Any change here should
 * also be reflected in the deployment descriptors.
 */
public class JNDINames {
    private JNDINames(){} // prevent instanciation

    /** JNDI name of the ibss database source */
    public static final String CRM_DATASOURCE = "CRMDB" ;

    public static final String BSN_DATASOURCE = "CRMDB" ;
    public static final String CRM_RCDB = "CRMDB" ;
    public static final String CRM_INVOICE = "CRMDB" ;
    
    public static final String DEFAULT_DATASOURCE = "DEFAULT" ;
    public static final String LOG_DATASOURCE = "LOGDB" ;
    
    public static final String CM_DATASOURCE = "CRMDB" ;
    public static final String PM_DATASOURCE = "CRMDB" ;
    public static final String ORD_DATASOURCE = "CRMDB" ;
    public static final String RM_DATASOURCE = "CRMDB" ;
    public static final String OAAS_DATASOURCE = "CRMDB" ;
      
    
    
}

