package com.powerise.ibss.framework;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.powerise.ibss.util.SysSet;

/**
 * ��ÿ�ε����������У���Ҫ�Ⱥ�ʹ�õ���ͬ�����ݣ���Щ�����е�������ǰ���߼��ļ��㣬
 * �е����Կͻ��˴����Ĳ�����Ϊ��ʡȥ�����ں������õĲ������ݣ���˽���Щ���ݷŵ�һ��ר�ŵ����У�ͨ��ָ���ķ��������úͻ�ȡ
 */
public class DynamicDict implements java.io.Serializable {

	public int flag;// 1:Action;0:Service ;������ʾ�������

	public String msg;// �������ʾ��Ϣ

	public String exception;// ϵͳ�쳣��Ϣ

	public String m_ActionId;

	private String m_ClassName;

	private Connection m_Conn = null;

	private String m_Verb = null;

	private String m_Name = null; //�����ݵ�����

	private boolean m_BOMode = false; //�Ƿ����BO��ʽ��֧��Ƕ��
	
    private boolean unionXml = false; //ʹ��ͳһxml�ṹ���°�ajax���ʹ��
        
	private static Logger m_Logger = Logger.getLogger(DynamicDict.class);

	public String getVerb() {
		return m_Verb;
	}

	public void setVerb(String strVerb) {
		m_Verb = strVerb;
	}

	/** ���ʷ�ʽ��0��BHO���ʣ�1��JSP���� */
	private int VISIT_FLAG = 0;

	/**
	 * ����ֵ�����ǵ����ڶ����ͬ��������ֵ������ArrayList�洢
	 */
	public HashMap m_Values = null;

	/**
	 * �����ȡ�����ķ�����
	 */
	private HashMap m_Methods = null;

	public DynamicDict() {
		m_Values = new HashMap();
		if(SysSet.surportAjax)
			m_BOMode=true;
//                if (SysSet.isAjax)
//                  m_BOMode = true;
        }

	public DynamicDict(int flag) {
		m_Values = new HashMap();
		VISIT_FLAG = flag;
		// easonwu 2009-11-30 ��������...����ԭ��
//		if(SysSet.surportAjax)
//			m_BOMode=true;
//    if(SysSet.isAjax)
//      m_BOMode = true;
	}

	public DynamicDict(boolean BOMode) {
		m_Values = new HashMap();
		m_BOMode = BOMode;
	}

	public void destroy() {
		if (m_Values != null) {
			m_Values.clear();
			m_Values = null;
		}
	}

	public void clear() {
		if (m_Values != null) {
			m_Values.clear();
		}
	}
	public boolean isBOMode()
	{
		return m_BOMode;
	}
	public void setBOByName(String aName, DynamicDict aDict)
			throws FrameException {
		setValueByName(aName, aDict, 1);
	}

	public int getBOCountByName(String aName) throws FrameException {
		return 0;
	}

	public DynamicDict getBOByName(String aName) throws FrameException {
		return getBOByName(aName, 0, true);
	}

	public DynamicDict getBOByName(String aName, int aSeq)
			throws FrameException {
		return getBOByName(aName, aSeq, true);
	}

	public DynamicDict getBOByName(String aName, int aSeq, boolean aThrow)
			throws FrameException {
		DynamicDict dict = null;
		Object obj = null;

		aName = aName.toUpperCase().trim();
		obj = m_Values.get(aName);
		if (obj == null) {
			if (aThrow)
				throw new FrameException(-12990101, "Can't find variable: " + aName);
			else
				return null;
		} else {
			if (obj.getClass().getName().indexOf("ArrayList") != -1) {
				ArrayList al = (ArrayList) obj;
				if (aSeq >= 0 && aSeq < al.size()) {
					dict = (DynamicDict) al.get(aSeq);
				} else {
					throw new FrameException(-12990102, "ָ��ֵ�ı���: " + aName
							+ " ָ������Ŷ��󲻴���");
				}
			} else
				throw new FrameException(-12990103, "Variable: " + aName
						+ " isn't BO");
		}
		return dict;
	}

	public ArrayList getBOListByName(String aName, boolean aThrow)
			throws FrameException {
		ArrayList alList = null;
		Object obj = null;

		aName = aName.toUpperCase().trim();
		obj = m_Values.get(aName);
		if (obj == null) {
			if (aThrow)
				throw new FrameException(-12990104, "Can't find variable: " + aName);
			else
				return null;
		} else {
			if (obj.getClass().getName().indexOf("ArrayList") != -1)
				alList = (ArrayList) obj;
			else
				throw new FrameException(-12990105, "Variable: " + aName
						+ "  isn't BO");
		}

		return alList;
	}

	public Object getValueByName(String aName, int aSeq, boolean aThrow)
			throws FrameException {
		return getValueByName(aName, aSeq, aThrow, null);
	}

	/**
	 * �������ƻ�ȡ����(�������ֵ������ֵΪָ���±�Ķ�����ȡֵʱ�Ѿ�֪�����ص���ʲ? ������������,���û���ҵ�����aThrow�������Ƿ��׳�����
	 *
	 * @param aName(
	 *            ��������)
	 * @param aSeq(�����±�)
	 * @param aThrow(���û���ҵ��Ƿ�������)
	 * @param aName
	 * @param aSeq
	 * @param aThrow
	 * @return Object
	 * @throws FrameException
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public Object getValueByName(String aName, int aSeq, boolean aThrow,
			String strDefault) throws FrameException {
		Object obj;
		ArrayList alObj;
		aName = aName.toUpperCase().trim();
		obj = m_Values.get(aName);
		if (obj == null) {
			if (aThrow)
				throw new FrameException(-12990001, "Can't find variable: " + aName);
			else if (strDefault != null)
				obj = strDefault;
			else
				obj = null; // null�򷵻ؿ��ַ���
		}
		// �ж�Object���ͣ������ArrayList����ô����Seq����ȡ,
		// Visit_flag=1����web���ʵĵ��ã�web������ݴ�ɰ��Ķ������app����ʹ��
		else if (aSeq >= 0 && this.VISIT_FLAG != 1) // ���±���벻С��0������²���Ч������ֱ�ӷ�������Object
		{
			if (obj.getClass().getName().equals("java.util.ArrayList")) {
				alObj = (ArrayList) obj;
				if ((alObj.size() > 0) && (aSeq < alObj.size())) {
					obj = alObj.get(aSeq);
				} else
					obj = null;
				if (obj == null && aThrow)
					throw new FrameException(-12990001, "Can't find variable: " + aName);
			}
		}
		return obj;
	}

	public Object getValueByName(String aName, String strDefault)
			throws FrameException {
		return getValueByName(aName, 0, false, strDefault);
	}

	/**
	 * �������ƻ�ȡ������ֵ������ֵΪ������ȡֵʱ�Ѿ�֪�����ص���ʲô������������
	 *
	 * @param aName(��������)
	 * @param aThrow(�Ƿ������׳�����)
	 * @param aName
	 * @param aThrow
	 * @return Object
	 * @throws FrameException
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public Object getValueByName(String aName, boolean aThrow)
			throws FrameException {
		return getValueByName(aName, 0, aThrow, null);
	}

	/**
	 * �������ƻ�ȡ������ֵ������ֵΪ������ȡֵʱ�Ѿ�֪�����ص���ʲô������������
	 *
	 * @param aName
	 * @return java.lang.Object
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public Object getValueByName(String aName) throws FrameException {
		return getValueByName(aName, 0, false, null);
	}

	/**
	 * ����ָ���������ƶ�Ӧ��ֵ����Object�����壬�����Ƕ������ݡ�
	 *
	 * @param aName
	 * @param aObj
	 * @throws FrameException
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public void setValueByName(String aName, Object aObj) throws FrameException {
		setValueByName(aName, aObj, 2); // ��������ģʽ����
	}

	/**
	 * �������ƻ�ȡ������ֵ������ֵΪ������ȡֵʱ�Ѿ�֪�����ص���ʲô������������
	 *
	 * @param aName(��������)
	 * @param aSeq(���)
	 * @param aName
	 * @param aSeq
	 * @return Object
	 * @throws FrameException
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public Object getValueByName(String aName, int aSeq) throws FrameException {
		return getValueByName(aName, aSeq, true, null);
	}

	/**
	 * ����ָ���������ƶ�Ӧ��ֵ����Object�����壬����ͬһ���Ʋ�����������ڶ������ݡ�
	 *
	 * @param aName
	 * @param aObj
	 *            ����ֵΪObject����
	 * @param aInsert(��������ģʽ(0)
	 *            ׷��ģʽ(1) ���滻ģʽ(2)�������������ģʽ���Ѿ�����ͬ������ʱ��ϵͳ���׳�����)
	 * @param aInsert
	 * @throws FrameException
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public void setValueByName(String aName, Object aObj, int aInsert)
			throws FrameException {
		Object obj;
//		int iObj;
		ArrayList alObj;
		
		if (aObj == null)
			return;
		if (aObj.getClass().getName().indexOf("ResultSet") != -1
				&& this.VISIT_FLAG == 1) {
			// �����ʷ�ʽΪJSP���ʷ�ʽʱ����ResultSetת����ArrayList
			try {
				ArrayList arr = new ArrayList();
				ResultSet rs = (ResultSet) aObj;
				ResultSetMetaData rsmd = rs.getMetaData();
				int cols = rsmd.getColumnCount();
				String[] arr1 = new String[cols];
				for (int i = 1; i <= cols; i++) {
					arr1[i - 1] = rsmd.getColumnName(i);
				}
				HashMap fields;

				while (rs.next()) {
					fields = new HashMap();
					for (int i = 0; i < cols; i++) {
						if (rs.getString(arr1[i]) != null)
							fields.put(arr1[i], rs.getString(arr1[i]));
					}
					arr.add(fields);
				}
				if (arr.size() > 0)
					aObj = arr;
				else
					aObj = null;
			} catch (SQLException sqle) {
				throw new FrameException(-12990013, "��ResultSetת����ArrayList����",
						sqle.getMessage());
			}
		}
		aName = aName.toUpperCase().trim();
		obj = m_Values.get(aName);

		if (aObj != null)
			if (aObj.getClass().getName().equals("java.lang.String")) {
				aObj = ((String) aObj).trim();
			}
//		 ����������DynamicDict����ȱʡ����׷��ģʽ�����Ҷ��ڵ�һ����������ʱ�����Ⱦ�new arraylist������֤���ݵĹ���
			else if (aObj.getClass().getName().indexOf("DynamicDict") != -1) {
			m_BOMode = true;
			if (obj == null) {
				obj = new ArrayList();
			}
		}


		// �Ѿ�����
		if (obj != null) {
			switch (aInsert) {
			case 0: // ����ģʽ,�׳�����
				throw new FrameException(-12990002, "Variable:" + aName+" aready exists");
			case 1: // ׷��ģʽ
				// �ж��Ƿ�ΪArrayList,����ֱ����ӣ����򴴽��µ�ArrayList,�ٽ�ֵ��ӽ�ȥ
				if (obj.getClass().getName().equals("java.util.ArrayList")) {
					alObj = (ArrayList) obj;
					alObj.add(aObj);
					m_Values.remove(aName);
					m_Values.put(aName, alObj);
				} else {
					alObj = new ArrayList();
					alObj.add(obj); // ���ԭ����ֵ
					alObj.add(aObj); // ��ӵ�ǰ��ֵ
					m_Values.remove(aName);
					m_Values.put(aName, alObj);
				}
				break;
			case 2: // �滻ģʽ
				m_Values.remove(aName);
				m_Values.put(aName, aObj); // �滻�ɴ���Ĳ������󣬲�����ԭ���Ķ�������
				break;
			default:
				throw new FrameException(-12990003, "setValueByName(),invalid input param:" + aInsert);
			}
		} else {
			m_Values.put(aName, aObj);
		}
	}

	
	/**
	 * ��ȡָ�����Ʋ����ĸ���
	 *
	 * @param aName(��������)
	 * @param aName
	 * @return int(��������)
	 */
	public int getCountByName(String aName) {
		return getCountByName(aName, true);
	}

	/**
	 * ��ȡָ�����Ʋ����ĸ���
	 *
	 * @param aName(��������)
	 * @param aSkipArray(�Ƿ�������飬��Ϊ����ͬ�������Ǵ洢��һ�������У���˶��ڲ���ArrayLi
	 *            st�������͵Ĳ�����Ҫ���⴦��)
	 * @param aName
	 * @param aSkipArray
	 * @return int(�����ĸ���)
	 */
	public int getCountByName(String aName, boolean aSkipArray) {
		Object obj;
		int iObj;
		ArrayList alObj;
		obj = m_Values.get(aName.toUpperCase().trim());
		if (obj == null)
			iObj = 0;
		else {
			if (aSkipArray
					&& (obj.getClass().getName().equals("java.util.ArrayList"))) {
				alObj = (ArrayList) obj;
				iObj = alObj.size();
			} else
				iObj = 1;
		}
		return iObj;
	}

	/**
	 * ��ȡ��������
	 *
	 * @return String
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public String getServiceName() throws FrameException {
		return m_ActionId;
	}

	public void SetConnection(Connection aConn) {
		/*
		 * if (m_Conn != null) { try { m_Conn.close(); m_Conn = null; } catch
		 * (SQLException e) { ; } }
		 */
		m_Conn = aConn;
	}

	public Connection GetConnection() {
		return m_Conn;
	}

	/** ɾ��ֵ�����еļ�ֵ */
	public void remove(String key) {
		this.m_Values.remove(key.toUpperCase().trim());
	}

	public String toString() {
		return m_Values.toString();
	}

	public String getValueByNameEx(String strPath) throws FrameException {
		return getValueByNameEx(strPath, null, true);
	}

	public String getValueByNameEx(String strPath, String strDefault)
			throws FrameException {
		return getValueByNameEx(strPath, strDefault, true);
	}

	/**
	 *
	 * @param strPath
	 *            ��·������ȡָ��·����ֵ��ֻ֧��hashmap+hashmap...+hashmap+string�ĸ�ʽ
	 * @param strDefault
	 *            û���ҵ�������ȱʡֵ
	 * @param bThrow
	 *            û���ҵ�,����ȱʡֵΪnull,�ж��Ƿ��׳��쳣
	 * @return
	 * @throws FrameException
	 */
	public String getValueByNameEx(String strPath, String strDefault,
			boolean bThrow) throws FrameException {
		String strValue = null, strName = null;
		StringTokenizer st = null;
		Object o = null;
		HashMap hmTemp = null;
		st = new StringTokenizer(strPath, ".");// �� . ��Ϊ�ָ���
		hmTemp = m_Values;
		while (st.hasMoreTokens()) {
			strName = st.nextToken();
			o = hmTemp.get(strName.trim().toUpperCase());
			if (o == null)
				break;
			if (o.getClass().getName().endsWith("HashMap")) {
				hmTemp = (HashMap) o;
				o = null;
			}
		}
		if (o != null && o.getClass().getName().endsWith("String") == false)
			o = null;
		if (o == null) {
			if (strDefault == null) {
				if (bThrow)
					throw new FrameException(22990004, "Can't find variable:" + strPath);
				else
					strValue = ""; // ����һ����ֵ
			} else
				strValue = strDefault;
		} else
			strValue = (String) o;
		st = null;
		return strValue;
	}
  public boolean isUnionXml() {
    return unionXml;
  }
  public void setUnionXml(boolean unionXml) {
    this.unionXml = unionXml;
  }
  public void setBOMode(boolean m_BOMode) {
    this.m_BOMode = m_BOMode;
  }
}
