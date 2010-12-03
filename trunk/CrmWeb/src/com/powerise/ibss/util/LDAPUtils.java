package com.powerise.ibss.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.naming.CommunicationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;




public class LDAPUtils{

	private static final String LDAP_CONFIG = "ldap";
	private static final String ldap_properties ="ldap.properties";
	//LDAP config resouce boundle name
	private static final String LDAP_OBJ_CLASS_STRING = "objectclass";
	private static String LDAP_OBJ_CLASS ="";
	//LDAP USER'S DN
	private static String USER_DN="";
	private static String DN_FIND="";
	//LDAP Object class String
	private static Hashtable handler = new Hashtable(); // LDAP handler

	private static DirContext ctx;
	private static Properties env;
	private  static String ErrorMessage="";
	private  static int ErrorCode=0;
    private  static LDAPUtils lDapHhandler = null;
    private static int ret =0;
    private static InputStream is = null;
    //private static int MaxID=0;
	private synchronized void addEntry(String entryDN, Attributes atts)
		throws NamingException {
		ctx.createSubcontext(entryDN, atts);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (26/12/2000 AM 9:34:12)
	 * @param entryDN String
	 * @exception NamingException 
	 */
	private static synchronized void deleteEntry(String entryDN)
		throws NamingException {
		ctx.destroySubcontext(entryDN);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (26/12/2000 AM 9:34:12)
	 */
	private static void loadConfig() throws Exception {
		try {
			
			//ResourceBundle rb = ResourceBundle.getBundle( LDAP_CONFIG,Locale.getDefault());
			is = ((LDAPUtils)Class.forName("com.powerise.ibss.util.LDAPUtils").newInstance()).getClass().getResourceAsStream(ldap_properties); 
			Properties clientProps = new Properties(); 
			  try {
			   clientProps.load(is); 
			   is.close();
			  }catch(Exception ee){
			  }
			Properties tempEnv = new Properties();
			
			tempEnv.put(
				"java.naming.factory.initial",
				(String)clientProps.get("factory.initial"));
			
			tempEnv.put(
				"java.naming.provider.url",
				(String)clientProps.get("provider.url"));
			System.out.println((String)clientProps.get("provider.url"));
			tempEnv.put(
				"java.naming.security.credentials",
				(String)clientProps.get("credentials"));
			tempEnv.put(
				"java.naming.security.principal",
				(String)clientProps.get("principal"));
            USER_DN = (String)clientProps.get("cfg.container");
            DN_FIND = (String)clientProps.get("cfg.find");
            LDAP_OBJ_CLASS = (String)clientProps.get("cfg.objectclass");
			env = tempEnv;
		   
		} catch (Exception exp) {
			throw new Exception("System Error: could not get the ldap config Env!\n" + exp.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (26/12/2000 AM 9:34:12)
	 */
	private static LDAPUtils getHandler() throws  Exception{

		LDAPUtils ldapHandler = (LDAPUtils) handler.get(LDAP_CONFIG);
		try {

			if (ldapHandler == null) {
				if (env == null) {
					loadConfig();
				}
				ldapHandler = new LDAPUtils();
				ldapHandler.env = env;
			
				ldapHandler.ctx = new InitialDirContext(env);
		        
				handler.put(LDAP_CONFIG, ldapHandler);
			}
		} catch (NamingException namingExp) {
			throw new Exception("System Error : could not InitialDirContext!\n" + namingExp.toString());
		}catch(Exception exp){
			throw exp;			
		}
		
		return ldapHandler;
	}	


	/**
	 * Creation date: (2000-12-25 15:35:53)
	 * addUser is to add a user entry in LDAP server
	 * @param UserPrincipal
	 * @return NamingEnumeration
	 */
	private synchronized NamingEnumeration ldapSearch(
		String base,
		String filter)
		throws NamingException {
		NamingEnumeration results = null;
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			results = ctx.search(base, filter, constraints);
		} catch (CommunicationException e) {
			ctx = new InitialDirContext(env);
			return ldapSearch(base, filter);
		}
		return results;
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (26/12/2000 AM 9:34:12)
	 * @param entryDN String
	 * @param entryDN Attributes
	 * @exception NamingException
	 */
	private synchronized void modifyEntry(String entryDN, Attributes atts)
		throws NamingException {
		try {
			ctx.modifyAttributes(
				entryDN,
				InitialDirContext.REPLACE_ATTRIBUTE,
				atts);
		} catch (CommunicationException e) {
			ctx = new InitialDirContext(env);
			modifyEntry(entryDN, atts);
		}

	}

	/**
	 * Insert the method's description here.
	 * Creation date: (26/12/2000 AM 9:34:12)
	 * @param as BasicAttributes
	 * @param attrName String
	 * @param attrValue String
	 */
	private static void putAttribute(
		BasicAttributes as,
		String attrName,
		String attrValue) {
		BasicAttribute ac = new BasicAttribute(attrName);
		ac.add(attrValue);
		as.put(ac);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (26/12/2000 AM 9:34:12)
	 * @param as BasicAttributes
	 * @param attrName String
	 * @param attrValues String[]
	 */
	private static void putAttributes(
		BasicAttributes as,
		String attrName,
		String[] attrValues) {
		BasicAttribute ac = new BasicAttribute(attrName);
		for (int i = 0; i < attrValues.length; i++) {
			ac.add(attrValues[i]);
		}
		as.put(ac);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (26/12/2000 AM 9:34:12)
	 * @param entryDN String
	 * @param entryDN Attributes
	 * @exception NamingException
	 */
	private synchronized void modifyEntry(
		String entryDN,
		int mod_op,
		Attributes atts)
		throws NamingException {
		try {
			ctx.modifyAttributes(entryDN, mod_op, atts);
		} catch (CommunicationException e) {
			ctx = new InitialDirContext(env);
			modifyEntry(entryDN, mod_op, atts);
		}

	}
	
	
	//���⹫���ӿ�
	
	public static int getRetCode(){
		return ErrorCode;
	}
	private static void setRetCode(int i){
		ErrorCode = i;
	}
	public static String getErrMsg(){
		return ErrorMessage;
	}
	
	private static  void setErrMsg(String errMsg){
		ErrorMessage = errMsg;
	}
	//delete a user information FROM LDAP
	public static int DelFromLDAP(String uid){
		int ret =0;
		try{
			if(lDapHhandler == null){
		    		lDapHhandler = getHandler();
		    	}
		}catch(NamingException ne){
			setErrMsg(ne.getExplanation());
			return 1;
		}catch(Exception fe){
			setErrMsg(fe.getMessage());
			return 1;
		}
		try{
			String del = getUserDNString(uid);
			deleteEntry(del);
			ret =0;
		}catch(NamingException ne){
			ret =1;
			setErrMsg(ne.getExplanation());
		}
		return ret;
		
	}
	//add a user information to LDAP
	    public static int addToLDAP(HashMap hm) {
	    	int ret =0;
	    	try{
	    		if(lDapHhandler == null){
		    		lDapHhandler = getHandler();
		    	}
	    	}catch(NamingException ne){
	    		setErrMsg(ne.getExplanation());
	    		return 1;
	    	}catch(Exception fe){
	    		setErrMsg(fe.getMessage());
	    		return 1;
	    	}
	    	try{	    		
	    		String id ="";    	
		    	BasicAttributes as = new BasicAttributes();
		    	LDAPUtils.putAttribute(
					as,
					LDAPUtils.LDAP_OBJ_CLASS_STRING,
					LDAPUtils.LDAP_OBJ_CLASS);
				Set set  = hm.entrySet();	
		    	Iterator it = set.iterator();
	    		while(it.hasNext())	{	    		
	    		  Map.Entry me = (Map.Entry)it.next();	    		  
	    		  String name = (String)me.getKey();
	    		  String value = (String)me.getValue();	    		  
	    		  if(name.toUpperCase().equals("BFIBSSPASSWD")){
	    		  	value = TEA.Encrypt(value);
	    		  }
	    		  if(name.toUpperCase().equals("BFIBSSID")){
	    		  	id = value;
	    		  }
	    		  LDAPUtils.putAttribute(as,name.toUpperCase(),value);
	    		}		    	
				
				String dn = getUserDNString(id);
				
				lDapHhandler.addEntry(dn, as);
			}catch(NamingException ne){
				setErrMsg(ne.getExplanation());
				return 1;
			}catch(Exception fe){
				setErrMsg(fe.toString());
				return 1;
			}
			return 0;
	    }
	    
	    //get the user dn
	    private static String getUserDNString(String ID){
	    	String ret= "BFIBSSID=";	    
	    	ret = ret + ID;
	    	ret = ret +",";
	    	ret = ret + LDAPUtils.USER_DN;
	    	//System.out.println("dn=:" + ret);
	    	return ret;
	    }
	    
	    //update the LDAP
	    public static int updateToLDAP(HashMap hm){
	    	int ret =0;
	    	try{
	    		if(lDapHhandler == null){
		    		lDapHhandler = getHandler();
		    	}
	    	}catch(NamingException ne){
	    		setErrMsg(ne.getExplanation());
	    		return 1;
	    	}catch(Exception fe){
	    		setErrMsg(fe.getMessage());
	    		return 1;
	    	}
	    	try{
		    	String id ="";    	
		    	BasicAttributes as = new BasicAttributes();
		    	LDAPUtils.putAttribute(
					as,
					LDAPUtils.LDAP_OBJ_CLASS_STRING,
					LDAPUtils.LDAP_OBJ_CLASS);
	
		    	Set set  = hm.entrySet();	
	    		Iterator it = set.iterator();
	    		while(it.hasNext())	{
	    		  Map.Entry me = (Map.Entry)it.next();
	    		  String name = (String)me.getKey();
	    		  String value = (String)me.getValue();
	    		  if(name.toUpperCase().equals("BFIBSSPASSWD")){
	    		  	value = TEA.Encrypt(value);
	    		  }
	    		  if(name.toUpperCase().equals("BFIBSSID")){
	    		  	id = value;
	    		  }
	    		  LDAPUtils.putAttribute(as,name.toUpperCase(),value);
	    		}		    	
				
				String dn = getUserDNString(id);
				
				
				lDapHhandler.modifyEntry(dn, as);
			}catch(NamingException ne){
				setErrMsg(ne.getExplanation());
				return 1;
			}catch(Exception fe){
				setErrMsg(fe.getMessage());
				return 1;
			}
			return 0;
	    }
	    
	    //find the Max BFIBSSID
	    public static int getMaxID(){
	    	int MaxID =0;	    	
	    	try{
	    		if(lDapHhandler == null){
		    		lDapHhandler = getHandler();
		    	}
	    	}catch(NamingException ne){
	    		setErrMsg(ne.toString() + ne.getExplanation() );
	    		return 0;    		
	    	}catch(Exception fe){
	    		setErrMsg(fe.getMessage());	
	    		return 0;    		
	    	}	    	
	        String dnName = LDAPUtils.DN_FIND;
	    	String strFind ="(&(BFIBSSID=*)(objectclass=BFIBSS))";		    
		    try{
		    	NamingEnumeration ne = lDapHhandler.ldapSearch(dnName, strFind);
			    if(!ne.hasMore()){			    	
			    	return 1;
			    }else{
			    	while(ne.hasMore()){
			    		SearchResult sr = (SearchResult) ne.next();						
						Attributes attrs = sr.getAttributes();
						if(attrs == null){							
							return 1;
						}else{						
							for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
								Attribute attr = (Attribute) ae.next();
								String id = attr.getID().toUpperCase();
								String value ="";
								/* For each value of the attribute. */
								for (Enumeration vals = attr.getAll(); 
								vals.hasMoreElements();
								value= (String)vals.nextElement());							
								if(id.equals("BFIBSSID")){
									boolean isNum = true;
									for(int i=0;i<value.length();i++){
										char c=value.charAt(i);
										if(c>=48 && c<=57){
											if(c==48 && i ==0){
												isNum = false;
												break;
											}
										}else{
											isNum = false;
											break;
										}
									}
									if(isNum==true){
										if(MaxID< Integer.parseInt(value)){
											MaxID = Integer.parseInt(value);
										}
									}
									break;
								}								
							}							
						}
			    	}
			    }
		    }catch(NamingException ne){	    		
	    		setErrMsg(ne.toString());
	    		return 0;
	    	}catch(Exception fe){	    		
	    		setErrMsg(fe.getMessage());
	    		return 0;
	    	}	    	 
	    	 return MaxID;
	    
	    }
	    	
	    //search the ldap 
	    public static ArrayList searchLDAP(HashMap hm ) {	    	
	    	ArrayList SEARCH = new ArrayList();
	    	try{
	    		if(lDapHhandler == null){
		    		lDapHhandler = getHandler();
		    	}
	    	}catch(NamingException ne){
	    		setErrMsg(ne.toString() + ne.getExplanation() );	
	    		setRetCode(1);
	    		return null;    		
	    	}catch(Exception fe){
	    		setErrMsg(fe.getMessage());	
	    		setRetCode(1);
	    		return null;    		
	    	}
	    	try{
	    		
	    		StringBuffer strSearch=new StringBuffer();
	    		int iCount =0;
	    		Set set  = hm.entrySet();	
	    		Iterator it = set.iterator();
	    		while(it.hasNext())	{
	    		  Map.Entry me = (Map.Entry)it.next();
	    		  String name = (String)me.getKey();
	    		  String value = (String)me.getValue();
	    		  
	    		  if(name.toUpperCase().equals("BFIBSSID")){
	    		  	if(!value.equals("")){
	    		  		strSearch.append("(");
	    		  		strSearch.append(name);
	    		        strSearch.append("=");
	    		  		strSearch.append(value);
	    		  		strSearch.append(")");
	    		  		iCount++;
	    		  	}
	    		  }else  if(name.toUpperCase().equals("BFIBSSPASSWD")){
	    		  	if(!value.equals("")){
	    		  		strSearch.append("(");
	    		  		strSearch.append(name);
	    		        strSearch.append("=");
		    		  	value = TEA.Encrypt(value);
		    		  	strSearch.append(value);
		    		  	strSearch.append(")");
		    		  	iCount++;
		    		  }
	    		  }else{
	    		  	if(!value.equals("")){
	    		  		strSearch.append("(");
	    		  		strSearch.append(name);
	    		        strSearch.append("=");
		    		  	strSearch.append(value);
		    		  	strSearch.append(")");
		    		  	iCount++;
		    		  }
	    		  }	    		  
	    		}
	    		String strFind = strSearch.toString();
	    		if(iCount>1){
	    			strFind = "(&" + strFind +")";
	    		}else{
	    			strFind=strFind;
	    		}
	    		
	    	    //LDAPUtils handler = LDAPUtils.getHandler();
	    	    String dnName = LDAPUtils.DN_FIND;
	    	    
			    NamingEnumeration ne = lDapHhandler.ldapSearch(dnName, strFind);
			    if(!ne.hasMore()){
			    	setRetCode(2);
			    	return null;
			    }else{
			    	while(ne.hasMore()){
			    		SearchResult sr = (SearchResult) ne.next();						
						Attributes attrs = sr.getAttributes();
						if(attrs == null){
							setRetCode(2);
							return null;
						}else{
							HashMap vhm = new HashMap();
							for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
								Attribute attr = (Attribute) ae.next();
								String id = attr.getID().toUpperCase();
								String value ="";
								/* For each value of the attribute. */
								for (Enumeration vals = attr.getAll(); 
								vals.hasMoreElements();
								value= (String)vals.nextElement());							
								if(id.equals("BFIBSSPASSWD")){
									if(value.length()<16)
										vhm.put(id,value/*TEA.Decrypt(value)*/);
									else
										vhm.put(id,TEA.Decrypt(value));
								}
								else{
									vhm.put(id,value);
								}
							}
							SEARCH.add(vhm);
						}
			    	}
			    }
			   
			    
	    	}catch(NamingException ne){
	    		setRetCode(3);
	    		setErrMsg(ne.toString());
	    		return null;
	    	}catch(Exception fe){
	    		setRetCode(3);
	    		setErrMsg(fe.getMessage());
	    		return null;
	    	}
	    	 setRetCode(0);
	    	 return SEARCH;
	    }
	    
	    //validate the user
	   public static int validatetoLDAP(String uID,String pwd){
	   		ret =0;
	   		if(uID == null || uID.trim().equals("")){
	   			setErrMsg("please input the user id!");
	   			ret =4;
	   			return ret;
	   		}
	   		if(pwd == null || pwd.trim().equals("")){
	   			setErrMsg("please input the user pwd!");
	   			ret =4;
	   			return ret;
	   		}
	   		try{
	    		if(lDapHhandler == null){
		    		lDapHhandler = getHandler();
		    	}
	    	}catch(NamingException ne){
	    		setErrMsg(ne.getExplanation());	
	    		ret =-1;
	    		return ret;    		
	    	}catch(Exception fe){
	    		setErrMsg(fe.getMessage());	
	    		ret =-1;
	    		return ret;    		
	    	}
	    	try{
	    		StringBuffer strSearch=new StringBuffer();
	    		strSearch.append("(&");
	    		strSearch.append("(BFIBSSID="+uID+")");
	    		strSearch.append("(BFIBSSPASSWD=" + TEA.Encrypt(pwd)+")");
	    		strSearch.append(")");
	    		//LDAPUtils handler = LDAPUtils.getHandler();
	    	    String dnName = LDAPUtils.DN_FIND;	    	    
			    NamingEnumeration ne = lDapHhandler.ldapSearch(dnName, strSearch.toString());
	    	    if(!ne.hasMore()){	
	    	        ret =3;		    	
			    	return ret;  //���û������ڻ��û�ID���û��������!
			    }else{
				    while(ne.hasMore()){
			    		SearchResult sr = (SearchResult) ne.next();					
						Attributes attrs = sr.getAttributes();
						if(attrs == null){
							ret =3;
							return ret;//���û������ڻ��û�ID���û��������!
						}else{
							HashMap vhm = new HashMap();
							for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
								Attribute attr = (Attribute) ae.next();
								String id = attr.getID().toUpperCase();
								String value ="";
								/* For each value of the attribute. */
								for (Enumeration vals = attr.getAll(); vals.hasMoreElements();
								value= (String)vals.nextElement());
								
								if(id.equals("BFIBSSSTATUS")){
									ret = Integer.parseInt(value);
									break;
								}							
							}
						
						}
			    	}
			    }
		    
	    	}catch(NamingException ne){	    		
	    		setErrMsg(ne.toString());
	    		return -1;
	    	}catch(Exception fe){
	    		
	    		setErrMsg(fe.getMessage());
	    		return -1;
	    	}
	   		return ret;
	   }
	
}
