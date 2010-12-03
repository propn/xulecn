package com.ztesoft.vsop.webservice.bo;

import org.apache.log4j.Logger;

/**
 * 
 * @author cooltan
 *
 */

public class AllOutSystemSimulateBo {
	public static AllOutSystemSimulateBo instance=new AllOutSystemSimulateBo();
	private static Logger logger = Logger.getLogger(AllOutSystemSimulateBo.class);
	
	public static AllOutSystemSimulateBo getInstance(){
		return instance;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String subResultFromVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subResultFromVSOP cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#subResultFromVSOP");
		
	}
	public String sendRQMessage(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("sendRQMessage cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#sendRQMessage");
		
	}
	public String subActionFromVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subActionFromVSOP cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#subActionFromVSOP");
		
	}
	public String chgActionFromVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("chgActionFromVSOP cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#chgActionFromVSOP");
		
	}
	public String unSubActionFromVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("unSubActionFromVSOP cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#unSubActionFromVSOP");
		
	}
	public String subsInstSynToHB(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subsInstSynToHB cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#subsInstSynToHB");
		
	}
	public String subResulToUser(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subResulToUser cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#subResulToUser");
		
	}
	public String workListVSOPToFK(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("workListVSOPToFK cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#workListVSOPToFK");
		
	}
	public String feeCheck(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("feeCheck cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#feeCheck");
		
	}
	public String staffAuthFromVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("staffAuthFromVSOP cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#staffAuthFromVSOP");
		
	}

}
