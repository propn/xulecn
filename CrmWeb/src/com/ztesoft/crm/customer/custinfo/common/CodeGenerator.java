/**
 * CodeGenerator.java
 * function:	
 * created: nik
 * ÉÏÎç11:37:28
 * modifid: ...
 */
package com.ztesoft.crm.customer.custinfo.common;

import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;

/**
 * CodeGenerator.java
 * @function:
 * 
 * @author nik
 * @since 2010-1-23
 * @modified  
 */
public final class CodeGenerator {
	public static String generatorCustCode(){
		String custCode = "";
		String Str = "";
		String seq ="";
		SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
		seq = sequenceManageDAO.getNextSequence("U_CUST", "CUST_CODE");		
		int n = seq.length();
		if (n < 8){
			for (int k = 0; k < 8 - n; k++)	Str += "0";
		    Str = Str + seq;
		}else  Str = seq.substring(seq.length()-8,seq.length());
		custCode = "2023" + Str+"0000";
		return custCode;
	} 
	
	
}
