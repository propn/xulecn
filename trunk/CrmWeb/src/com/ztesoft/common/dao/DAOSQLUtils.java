package com.ztesoft.common.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.ConfigFileHelper;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.StringUtils;
/**
 *
 * Classname : DAOSQLUtils Description :
 * ��ȡ��Դ�ļ����õ���ķֿ���û�����������������й��˴Ӷ��õ���Էֿ����ȷ���
 */
public class DAOSQLUtils {

	private static Logger logger = Logger.getLogger(DAOSQLUtils.class);

	private static DAOSQLUtils instance = null;

	public static int CRM_DB = 1;

	public static int BSN_DB = 2;

	private int DB_FLAG = 1;

	private static String KEY_DBTYPE_INFORMIX = "informix";

	private static String KEY_DBTYPE_ORACLE = "oracle";

	private static String KEY_UPDATE = "update";

	private static String KEY_FROM = " from ";

	private static String KEY_FROM_NOSPACE = "from";

	private static String KEY_INTO = "into";

	private static String KEY_JOIN = "join";

	private static String KEY_SET = " set ";

	private static String KEY_SET_NOSPACE = "set";

	private static String KEY_WHERE = "where";

	private static String KEY_DOT = ",";

	private static String KEY_ON = "on";

	private static String KEY_OUTER = "outer";

	private static String S_SPACE = " ";

	private static String S_DOT = ",";

	private static String[] SQL_KEY_SET = { KEY_UPDATE, KEY_SET_NOSPACE,
			KEY_FROM_NOSPACE, KEY_INTO, KEY_JOIN, KEY_WHERE, KEY_ON };

	private static String dbType = null;

	private static String defaultUser = null;

	private static String defaultDatabase = null;

	private static String defaultDatabaseServer = null;

	private static Properties properties = new Properties();

	private static Properties b_properties = new Properties();

	private static String b_dbType = null;

	private static String b_defaultUser = null;

	private static String b_defaultDatabase = null;

	private static String b_defaultDatabaseServer = null;

	/**
	 * �����ݽ�����sql���°���"���ݿ���.����"�ķ�ʽ��������֯sql���ء�
	 *
	 * @param sql
	 */
	public static String getFilterSQL(String sql) {
		//if( sql.trim().startsWith("PAGEMODEL")){
			//return sql.replaceAll("PAGEMODEL","");
		//}
		if("true".equalsIgnoreCase(CrmConstants.SHOW_SQL))
			logger.debug("newsql:[" + sql + "]");
		System.out.println("newsql:[" + sql + "]") ;
		return sql ;
//		return singleton().convertSql(sql, CRM_DB);

	}

	/**
	 * �����ݽ�����where����������ϳ�key=? ����ʽ��������?�Ĳ���
	 * ���磺������һ��a.accept_id='10000000' and a.state='2' and a.product_id=b.product_id
	 * ��ô����һ��HashMap�������һ��key:strSqlװ����a.accept_id=? and a.state=? and a.product_id=b.product_id
	 * ����һ��key:whereCondװ����һ��ArrayList whereCond������װ��ֵ��10000000......
	 * @param sql
	 */
	//add by RyoUehara 090702��һ��where�ַ���ת���ɲ�����ʽ��where�ַ����������ز����б�
	public static HashMap getWhereCond(String whereCond){
		HashMap vo=new HashMap();
		if(whereCond.toLowerCase().indexOf("dual")>=0){
			vo.put("strSql", whereCond);
			vo.put("whereCond", null);
			return vo;
		}
		StringBuffer allStrSql=new StringBuffer();
		char[] charWhereCond=whereCond.toCharArray();
		String singleQuotation="'";
		String isNumber="1234567890";
		String isChar="abcdefghijklmnopqrstuvwxyz";
		String tmpVal="";
		String nextVal="";
		String preVal="";
		int singleQuoStartIdx=0;
		int singleQuoEndIdx=0;
		boolean isSingelStart=false;
		int isNumberStartIdx=0;
		int isNumberEndIdx=0;
		boolean isNumberStart=false;
		int singleQuoCount=0;
		ArrayList lstWhereCond= new ArrayList();
		boolean isOw=false;
		int charWhereCondSize=charWhereCond.length;
		for(int i=0;i<charWhereCondSize;i++){
			tmpVal=String.valueOf(charWhereCond[i]);

			if ((i+1)<charWhereCond.length){
				nextVal=String.valueOf(charWhereCond[i+1]);
			}else{
				nextVal="";
			}

			if (tmpVal.equals(singleQuotation) && isSingelStart==false){//�ռ������ſ�ʼ
				singleQuoStartIdx=i;
				singleQuoEndIdx=i;
				isSingelStart=true;
				singleQuoCount++;
				continue;
			}
			if (tmpVal.equals(singleQuotation) && isSingelStart==true){//��ÿ���ռ��ĵ����ż���
				singleQuoCount++;
			}
			if (tmpVal.equals(singleQuotation) && !nextVal.equals(singleQuotation) && isSingelStart==true && singleQuoCount%2==0){//�ռ������Ž���
				singleQuoEndIdx=i;
				String val=whereCond.substring(singleQuoStartIdx+1,singleQuoEndIdx);
				//�����%��ô��Ϊ��like�����������ʱ������
				if (val.indexOf("%")>=0){
					val=whereCond.substring(singleQuoStartIdx,singleQuoEndIdx);
					//��λ״̬
					singleQuoStartIdx=0;
					singleQuoEndIdx=0;
					singleQuoCount=0;
					isSingelStart=false;
					allStrSql.append(val);
					allStrSql.append(tmpVal);
					continue;
				}
				//�ռ���Ҫ��ֵ
				allStrSql.append("?");
				lstWhereCond.add(val);
				//��λ״̬
				singleQuoStartIdx=0;
				singleQuoEndIdx=0;
				singleQuoCount=0;
				isSingelStart=false;
				continue;
			}
			//����Ǹ���
			if(tmpVal.equals("-") && isSingelStart==false){
				//��Ҫ����ǰ���Ƿ��з����ֵĺ���ĸ������У���ô��֤������������ǲ����������û����������һЩ+=�����Ⱦ�֤���Ǹ���,�ո���
				for (int j=i-1;j>=0;j--){
					preVal=String.valueOf(charWhereCond[j]);
					if (!preVal.equals("")){
						if (isChar.indexOf(preVal.toLowerCase())>=0 || isNumber.indexOf(preVal)>=0 ){//������
							//allStrSql.append(tmpVal);
							break;
						}
						else{
							isOw=true;
							break;
						}
					}
				}
				if (isOw)
				continue;
			}
			if ((i-1)<charWhereCond.length && (i-1)>=0){
				preVal=String.valueOf(charWhereCond[i-1]);
			}else{
				preVal="";
			}
			if (isChar.indexOf(preVal.toLowerCase())<0 && isNumber.indexOf(tmpVal)>=0 && isNumberStart==false &&  isSingelStart==false){//�ռ����ֿ�ʼ
				boolean isCanStart=true;
				for (int j=i-1;j>=0;j--){//��ǰ�ң�������ҵ�һ����ĸ����ô�Ͳ���������,
					preVal=String.valueOf(charWhereCond[j]);
					if (preVal==null || preVal.equals(" ")){//����ǿո���ô����Ϊ������
						break;
					}
					else if (isChar.indexOf(preVal.toLowerCase())>=0){//�������ĸ
						isCanStart=false;
						break;
					}
					if(isNumber.indexOf(preVal)<0){//���ǰ�滹�����֣���ô�ż�����ǰ�ң���Ȼ����Ϊ��������
						break;
					}
				}
				if(isCanStart){
					isNumberStartIdx=i;
					isNumberEndIdx=i;
					isNumberStart=true;
					continue;
				}
			}

			//����Ѿ����ռ����ֵ�״̬�����Ǻ���һλ��.,����.�ĺ���һλ�������֣���ô����Ϊ���ֽ�����
			if (isNumberStart==true && isSingelStart==false && tmpVal.equals(".") && isNumber.indexOf(nextVal)>=0){
				//
			}else{
				if (isNumber.indexOf(tmpVal)<0 && isNumberStart==true && isSingelStart==false){//�ռ����ֽ���
					isNumberEndIdx=i;
					String val=whereCond.substring(isNumberStartIdx,isNumberEndIdx);
					//�ռ���Ҫ��ֵ
					allStrSql.append("?");
					if (isOw)//����Ǹ�������ô
						val="-"+val;
					isOw=false;
					lstWhereCond.add(val);
					//��λ״̬
					isNumberEndIdx=0;
					isNumberStart=false;
				}
			}
			if (isSingelStart==false && isNumberStart==false && isOw==false){
				allStrSql.append(tmpVal);
			}
		}

		if (isNumber.indexOf(tmpVal)>=0 && isNumberStart==true && isSingelStart==false){//���ֽ�β����
			isNumberEndIdx=charWhereCondSize;
			String val=whereCond.substring(isNumberStartIdx,isNumberEndIdx);
			//�ռ���Ҫ��ֵ
			allStrSql.append("?");
			if (isOw)//����Ǹ�������ô
				val="-"+val;
			isOw=false;
			lstWhereCond.add(val);
			//��λ״̬
			isNumberEndIdx=0;
			isNumberStart=false;
		}

		vo.put("strSql", allStrSql.toString());
		vo.put("whereCond", lstWhereCond);
		return vo;
	}

	/**
	 * �����ݽ�����sql���°���"���ݿ���@������:�û���.����"�ķ�ʽ��������֯sql���ء�
	 *
	 * @param sql
	 *            Ҫת����sql��䡣
	 * @param dbFlag
	 *            ���ݿ�ı�ʾ����ͬ�ı�ʾ���Ӳ�ͬ�����ݿ⡣ 1 ���� jdbc/crm 2 ���� jdbc/bsn
	 * @return
	 */
	public static String getFilterSQL(String sql, int dbFlag) {
		if("true".equalsIgnoreCase(CrmConstants.SHOW_SQL))
			logger.debug("newsql:[" + sql + "]");
		return sql ;
//		if( sql.trim().startsWith("PAGEMODEL")){
//			return StringUtils.replaceAll(sql,"PAGEMODEL","");
//		}
//		return singleton().convertSql(sql, dbFlag);
	}

	/**
	 * ֱ��ת������
	 * @param tableName
	 * @return
	 */
	public static String getTableName(String tableName) {
		return getTableName(tableName, CRM_DB);
	}

	/**
	 * ֱ��ת������
	 * @param tableName
	 * @param dbFlag
	 * @return
	 */
	public static String getTableName(String tableName, int dbFlag) {
		return tableName ;
//		return singleton().getTotalTableName(tableName, dbFlag);
	}

	/**
	 * ��ȡ���ʵ��
	 * @return
	 */
	protected static DAOSQLUtils singleton() {
		return SingletonHolder.instance ;
	}


	private DAOSQLUtils(){
		init() ;
	}
	static class SingletonHolder{
		static DAOSQLUtils instance = new DAOSQLUtils() ;
	}

	/**
	 * ��ʼ������ȡ�����ļ���������ʵ��
	 *
	 */
	private  void init() {

		FileInputStream fis = null;
		try {

			//read crm  config
			String fileName = "";
			File cfgFile = null;
		    if(DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_INFORMIX)){
		    	properties = ConfigFileHelper.getInformixDBConfigFile() ;
		    }else if(DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_ORACLE)){
		    	properties = ConfigFileHelper.getOracleDBConfigFile() ;
		    }

			dbType = properties.getProperty("DBTYPE");
			defaultUser = properties.getProperty("DEFAULT.USER");
			defaultDatabase = properties.getProperty("DEFAULT.DATABASE");
			defaultDatabaseServer = properties
					.getProperty("DEFAULT.DATABASESERVER");

//			//read bsn config
//			File bsnFile = null;
//		    if(DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_INFORMIX)){
//		    	bsnFile = new File("./cfg/"+ "BSN_Resources.properties");
//				if(!bsnFile.exists()){
//					fileName = CrmConstants.WEB_INF_PATH + "BSN_Resources.properties";
//					bsnFile = new File(fileName);
//				}
//		    }else if(DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_ORACLE)){
//		    	bsnFile = new File("./cfg/"+ "BSN_Resources_ORA.properties");
//				if(!bsnFile.exists()){
//					fileName = CrmConstants.WEB_INF_PATH + "BSN_Resources_ORA.properties";
//					bsnFile = new File(fileName);
//				}
//		    }
//
//			if(bsnFile==null || !bsnFile.exists()){
//				return;
//			}
//			fis = new FileInputStream(bsnFile);
//			b_properties.load(fis);
//			fis.close();
//
//			b_dbType = b_properties.getProperty("DBTYPE");
//			b_defaultUser = b_properties.getProperty("DEFAULT.USER");
//			b_defaultDatabase = b_properties.getProperty("DEFAULT.DATABASE");
//			b_defaultDatabaseServer = b_properties
//					.getProperty("DEFAULT.DATABASESERVER");

		}

		catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException ex) {
				}
			}
		}

	}

	/**
	 * ��ȡ����������д�������������Ե���ʹ�ô˺���ת������
	 *
	 * @param tableName
	 *            String
	 * @return String
	 */
	protected String getTotalTableName(String tableName, int dbFlag) {

		String newValue = null;
		if (tableName != null && !tableName.trim().equals("")) {

			Object key = tableName.toLowerCase().trim();
			String value = null;
			try {
				if (dbFlag == CRM_DB) {
					value = properties.getProperty(key.toString());
				} else if (dbFlag == BSN_DB && b_properties != null) {
					value = b_properties.getProperty(key.toString());
				}

			} catch (MissingResourceException ex) {
				// û����Դ�Ĵ���
			}

			if (value == null || value.trim().equals("")) {
				newValue = convertTableStr(getDefaultUser(),
						getDefaultDatabase(), getDefaultDatabaseServer(), key
								.toString());
			} else {

				String[] values = StringUtils.split(value,KEY_DOT);
				if (values != null && values.length == 3) {
					newValue = convertTableStr(values[0], values[1], values[2],
							key.toString());
				} else {
					newValue = key.toString();
				}

			}

		}

		return newValue;

	}

	private String getDbType() {
		if (this.DB_FLAG == this.BSN_DB) {
			return b_dbType;
		} else
			return dbType;
	}

	private String getDefaultDatabase() {
		if (this.DB_FLAG == this.BSN_DB) {
			return b_defaultDatabase;
		} else
			return defaultDatabase;
	}

	private String getDefaultUser() {
		if (this.DB_FLAG == this.BSN_DB) {
			return b_defaultUser;
		} else
			return defaultUser;
	}

	private String getDefaultDatabaseServer() {
		if (this.DB_FLAG == this.BSN_DB) {
			return b_defaultDatabaseServer;
		} else
			return defaultDatabaseServer;
	}

    static class Counter{
        int num=0;
    }

    /**
     * ������䣬�õ����б���
     *
     * @param sqlStr
     *            String
     * @return HashMap
     */
    public HashMap analyseSql(String sqlStr) {
        HashMap hm = new HashMap();

        // �ʷ�����
        String[] words = splitWord(sqlStr);

        HashMap tables = new HashMap();


        // �﷨����
        tableAnalyse(new Counter(), words, tables);

        return tables;
    }

    /**
     * �ִʣ�����SQL����֮ǰ���Ƚ��дʷ��������ҳ�SQL����������Ĵʻ�
     *
     * @author leadyu
     * @param sql
     * @return
     */
    private String[] splitWord(String sql) {
        ArrayList words = new ArrayList();

        String[] spaceSplit = StringUtils.split(sql," ");

        for (int i = 0; i < spaceSplit.length; i++) {
            String word = spaceSplit[i];
            word = StringUtils.replaceAll(word,",", "#,#");
            word = StringUtils.replaceAll(word,"(", "#(#");
            word = StringUtils.replaceAll(word,")", "#)#");

            String[] ws = StringUtils.split(word,"#");
            for(int c=0;c<ws.length;c++){
                if(ws[c]!=null && !"".equals(ws[c].trim())){
                    words.add(ws[c]);
                }
            }

        }

        String[] result=new String[words.size()];
        words.toArray(result);
        return result;
    }


    /**
     * �Ա������﷨����
     *
     * @param words
     * @return
     */
    private void tableAnalyse(Counter i, String[] words, Map tables) {

        for (; i.num < words.length; i.num++) {
            //�������'('������һ���Ӿ��﷨����
            if ("(".equals(words[i.num].trim().toLowerCase())) {
                i.num++;
                tableAnalyse(i, words, tables);
                continue;
            }
            // �������')'�˳���ǰ�Ӿ��﷨����
            if (")".equals(words[i.num].trim().toLowerCase())) {
                return;
            }

            // �������from
            if ("from".equals(words[i.num].trim().toLowerCase())) {
                //System.out.print("");
                for (i.num++; i.num < words.length; i.num++) {
                    String fromNext = words[i.num];

                    // �������'('������һ���Ӿ��﷨����
                    if ("(".equals(fromNext.trim().toLowerCase())) {
                        i.num++;
                        tableAnalyse(i, words, tables);
                        continue;
                    }
                    // �������')'�˳���ǰ�Ӿ��﷨����
                    if (")".equals(fromNext.trim().toLowerCase())) {
                        return;
                    }
                    //�������'where,into'from���ʽ����
                    if ("where".equals(fromNext.trim().toLowerCase()) || "into".equals(fromNext.trim().toLowerCase())) {
                        break;
                    }
                    // �����,��ǰ��ľ��Ǳ���
                    if (",".equals(fromNext.trim())) {
                        //ʲôҲ����
                    }else{
                        //�������ǰ����from����,�Լ�join˵���Ǳ���
                        if("from".equals(words[i.num-1]) || ",".equals(words[i.num-1]) || "join".equals(words[i.num-1])){
                        	//��һ���������:������ʺ�����from����ǰ��from���ʽ���� add by RyoUehara 090914
                        	if (i.num+1<words.length){
                        		String currentNext=words[i.num+1];
                        		if ("from".equals(currentNext.toLowerCase())){
                        			break;
                        		}
                        	}
                            tables.put(words[i.num],words[i.num]);
                            //System.out.print("");
                        }
                    }
                }
            }

            if(i.num>=words.length)break;

            // �������update
            if ("update".equals(words[i.num].trim().toLowerCase())) {
                i.num++;
                if(i.num>=words.length)break;
                tables.put(words[i.num],words[i.num]);

            }

            if(i.num>=words.length)break;

            // �������into
            if ("into".equals(words[i.num].trim().toLowerCase())) {
                i.num++;
                if(i.num>=words.length)break;
                tables.put(words[i.num],words[i.num]);

            }

        }

    }

	private void replaceTableNames(HashMap tableNamesHM, int dbFlag) {
		if (tableNamesHM != null && tableNamesHM.keySet() != null) {
			Iterator it = tableNamesHM.keySet().iterator();
			Object key = null;
			String newValue = null;
			while (it.hasNext()) {
				key = it.next();

				newValue = getTotalTableName(key.toString(), dbFlag);

				tableNamesHM.put(key, newValue);

			}
		}

	} // replaceTableNames

	/**
	 * ������䣬�滻�������Ҫ���ڷָ�Ĺؼ���ΪСд������ǰ��,��Ҫ�ӿո�
	 *
	 * @param tableNamesHM
	 *            HashMap
	 */

	private String sqlKeyToLowerCase(String sqlStr) {
		String[] strs = StringUtils.split(sqlStr," ");
		String value = null;
		for (int k = 0; k < strs.length; k++) {

			value = strs[k].trim();

			for (int i = 0; i < SQL_KEY_SET.length; i++) {
				if (value.equalsIgnoreCase(SQL_KEY_SET[i])) {
					strs[k] = SQL_KEY_SET[i];
					break;
				}
			}

		}
		String newStr = null;
		StringBuffer sb = new StringBuffer(S_SPACE);
		for (int k = 0; k < strs.length; k++) {
			sb.append(S_SPACE).append(strs[k]).append(S_SPACE);

		}
		newStr = sb.toString();
		newStr = StringUtils.replaceAll(newStr,",", " , ");

		newStr = StringUtils.replaceAll(newStr, "(", " (");

		return newStr;
	} // sqlKeyToLowerCase

	/**
	 * ��������ǰ׺ iom1@crm:informix.
	 *
	 * @param user
	 *            String
	 * @param database
	 *            String
	 * @param databaseServer
	 *            String
	 * @param tableName
	 *            String
	 * @return String
	 */
	private String convertTableStr(String user, String database,
			String databaseServer, String tableName) {
		StringBuffer sb = new StringBuffer();
		// for tableName
//		if("n_product_offer_instance_detail".equalsIgnoreCase(tableName)){
//			tableName = "n_prod_offer_instance_detail";
//		}else if("l_product_offer_instance_detail".equalsIgnoreCase(tableName)){
//			tableName = "l_prod_offer_instance_detail";
//		}else if("u_product_offer_instance_detail".equalsIgnoreCase(tableName)){
//			tableName = "u_prod_offer_instance_detail";
//		}else if("order".equalsIgnoreCase(tableName)){
//			tableName = "wq_order";
//		}

        if(tableName.indexOf('.')>=0){
            return tableName;
        }

		if (this.getDbType().equals(this.KEY_DBTYPE_INFORMIX)) {
			sb.append(database).append("@").append(databaseServer).append(":")
					.append(user).append(".").append(tableName);
				return sb.toString();
		} else if (this.getDbType().equals(this.KEY_DBTYPE_ORACLE)) {
			sb.append(user).append(".").append(tableName);
				return sb.toString();
		} else{
			return tableName;

		}

	} // convertTableStr

	/**
	 * ������װsql���
	 *
	 * @param sqlStr
	 *            String
	 * @param tableNamesHM
	 *            HashMap
	 * @return String
	 */
	private String assembleSql(String sqlStr, HashMap tableNamesHM) {
		String newSqlStr = sqlStr + S_SPACE;
		Iterator it = tableNamesHM.keySet().iterator();
		String key = null;
		String value = null;
		while (it.hasNext()) {
			key = (String) it.next();
			value = (String) tableNamesHM.get(key);
			newSqlStr = StringUtils.replaceAll(newSqlStr,S_SPACE + key + S_SPACE, S_SPACE
					+ value + S_SPACE);

		}
        newSqlStr = StringUtils.replaceAll(newSqlStr,S_SPACE +","+ S_SPACE, ",");
		return newSqlStr;

	} // assembleSql

	/**
	 *
	 * @param sqlStr
	 *            String
	 * @return String
	 */
	protected String convertSql(String sqlStr, int dbFlag) {
		HashMap tableNamesHM = null;
		// long begin = System.currentTimeMillis();
		// logger.debug("old:[" + sqlStr + "]");

		sqlStr = sqlKeyToLowerCase(sqlStr);

		// logger.debug("mid:[" + sqlStr + "]");

		// ����sqlStr,�õ���Ҫ����ǰ׺�ı�������
		tableNamesHM = analyseSql(sqlStr);

		// �滻����ǰ׺
		replaceTableNames(tableNamesHM, dbFlag);

		// ��װ�µ����
		String newStr = assembleSql(sqlStr, tableNamesHM);
		// logger.debug("invoke _convertSql cost:[ "
		// + (System.currentTimeMillis() - begin) + "ms]");

		tableNamesHM.clear();
		newStr = StringUtils.replaceAll(newStr, " (", "(");
		if("true".equalsIgnoreCase(CrmConstants.SHOW_SQL))
			logger.debug("newsql:[" + newStr + "]");
		return newStr;

	}

	public static void main(String[] args) {
		/**
		String sql = "select t1.*,(select 1 from t5 left join t6 on w=d and w in(select 1 from t8) and w in(12323) left join t7) d from t1,t2,(select * from t3,t4 where t3.s=t4.d and t3.s in (11212)) tt where tt.e in (12123) into t9";
        String sql2="delete from t1 where tt.e in (select * from t2,t3)";
        String sql3="insert into t1(t,y)values(a,1)";

        String sql4="update t1 set d=f,e=f where tt.e in (select * from t2,t3)";
        HashMap tables=null;
        long t=0;
		try {
            long begin=System.currentTimeMillis();
            tables=DAOSQLUtils.singleton().analyseSql(sql);

            long end=System.currentTimeMillis();
            t=end-begin;
		} catch (Exception ex) {
			ex.printStackTrace();
		}



        **/
        String sql = "select t1.*,(select 1 from t5 left join t6 on w=d and w in(select 1 from t8) and w in(12323) left join t7) d from t1,t2,(select * from t3,t4 where t3.s=t4.d and t3.s in (11212)) tt where tt.e in (12123) into t9";

        long begin=System.currentTimeMillis();
        sql=sql.replaceAll("t", "wrf");

        long end=System.currentTimeMillis();


	}


	public static String doPreFilter(boolean filterTableName,String sql,int pageIndex, int pageSize ) {
		if( filterTableName ){
			sql = DAOSQLUtils.getFilterSQL(sql) ;
		}
		int startIndex = (pageIndex - 1) * pageSize+1;
		int endIndex = startIndex + pageSize ;
		sql = "select * from (select my_table.*,rownum as my_rownum from( "+
		sql +
				" ) my_table where rownum< " + endIndex +
				") where my_rownum>= " + startIndex ;
		return sql;
	}
	
	public static String doPreFilter_informix(boolean filterTableName,String sql,int pageIndex, int pageSize ) {
		if( filterTableName ){
			sql = DAOSQLUtils.getFilterSQL(sql) ;
		}
		int startIndex = (pageIndex - 1) * pageSize;
		int endIndex = startIndex + pageSize ;
		sql = "select skip "+ startIndex +" first " + endIndex + " * from ("+sql +" )";
		return sql;
	}


}
