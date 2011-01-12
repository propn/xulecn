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
 * 在每次单独的事务中，需要先后使用到不同的数据，这些数据有的来自于前段逻辑的计算，
 * 有的来自客户端传来的参数。为了省去数据在函数调用的参数传递，因此将这些数据放到一个专门的类中，通过指定的方法来设置和获取
 */
public class DynamicDict implements java.io.Serializable {

	public int flag;// 1:Action;0:Service ;其它表示出错编码

	public String msg;// 错误或提示信息

	public String exception;// 系统异常信息

	public String m_ActionId;

	private String m_ClassName;

	private Connection m_Conn = null;

	private String m_Verb = null;

	private String m_Name = null; //类数据的名称

	private boolean m_BOMode = false; //是否采用BO方式，支持嵌套
	
    private boolean unionXml = false; //使用统一xml结构，新版ajax框架使用
        
	private static Logger m_Logger = Logger.getLogger(DynamicDict.class);

	public String getVerb() {
		return m_Verb;
	}

	public void setVerb(String strVerb) {
		m_Verb = strVerb;
	}

	/** 访问方式，0：BHO访问，1：JSP访问 */
	private int VISIT_FLAG = 0;

	/**
	 * 参数值，考虑到存在多个相同参数名的值，采用ArrayList存储
	 */
	public HashMap m_Values = null;

	/**
	 * 缓存获取参数的方法类
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
		// easonwu 2009-11-30 启动报错...待查原因
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
					throw new FrameException(-12990102, "指定值的变量: " + aName
							+ " 指定的序号对象不存在");
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
	 * 根据名称获取参数(多个）的值，返回值为指定下标的对象，在取值时已经知道返回的是什? 样的数据类型,如果没有找到根据aThrow来决定是否抛出意外
	 *
	 * @param aName(
	 *            参数名称)
	 * @param aSeq(变量下标)
	 * @param aThrow(如果没有找到是否抛意外)
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
				obj = null; // null则返回空字符串
		}
		// 判断Object类型，如果是ArrayList，那么根据Seq来获取,
		// Visit_flag=1用于web访问的调用，web会把数据打成包的对象便于app层来使用
		else if (aSeq >= 0 && this.VISIT_FLAG != 1) // 在下标必须不小于0的情况下才有效，否则直接返回整个Object
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
	 * 根据名称获取参数的值，返回值为对象，在取值时已经知道返回的是什么样的数据类型
	 *
	 * @param aName(参数名称)
	 * @param aThrow(是否允许抛出意外)
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
	 * 根据名称获取参数的值，返回值为对象，在取值时已经知道返回的是什么样的数据类型
	 *
	 * @param aName
	 * @return java.lang.Object
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public Object getValueByName(String aName) throws FrameException {
		return getValueByName(aName, 0, false, null);
	}

	/**
	 * 设置指定参数名称对应的值，以Object来定义，可以是多种数据。
	 *
	 * @param aName
	 * @param aObj
	 * @throws FrameException
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public void setValueByName(String aName, Object aObj) throws FrameException {
		setValueByName(aName, aObj, 2); // 采用正常模式调用
	}

	/**
	 * 根据名称获取参数的值，返回值为对象，在取值时已经知道返回的是什么样的数据类型
	 *
	 * @param aName(参数名称)
	 * @param aSeq(序号)
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
	 * 设置指定参数名称对应的值，以Object来定义，对于同一名称参数，允许存在多条数据。
	 *
	 * @param aName
	 * @param aObj
	 *            参数值为Object对象
	 * @param aInsert(采用正常模式(0)
	 *            追加模式(1) 、替换模式(2)，如果采用正常模式，已经存在同名参数时，系统会抛出意外)
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
			// 当访问方式为JSP访问方式时，把ResultSet转换成ArrayList
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
				throw new FrameException(-12990013, "把ResultSet转换成ArrayList出错",
						sqle.getMessage());
			}
		}
		aName = aName.toUpperCase().trim();
		obj = m_Values.get(aName);

		if (aObj != null)
			if (aObj.getClass().getName().equals("java.lang.String")) {
				aObj = ((String) aObj).trim();
			}
//		 如果传入的是DynamicDict，则缺省就是追加模式，并且对于第一个对象加入的时候，首先就new arraylist，来保证数据的规整
			else if (aObj.getClass().getName().indexOf("DynamicDict") != -1) {
			m_BOMode = true;
			if (obj == null) {
				obj = new ArrayList();
			}
		}


		// 已经存在
		if (obj != null) {
			switch (aInsert) {
			case 0: // 正常模式,抛出意外
				throw new FrameException(-12990002, "Variable:" + aName+" aready exists");
			case 1: // 追加模式
				// 判断是否为ArrayList,是则直接添加，否则创建新的ArrayList,再将值添加进去
				if (obj.getClass().getName().equals("java.util.ArrayList")) {
					alObj = (ArrayList) obj;
					alObj.add(aObj);
					m_Values.remove(aName);
					m_Values.put(aName, alObj);
				} else {
					alObj = new ArrayList();
					alObj.add(obj); // 添加原来的值
					alObj.add(aObj); // 添加当前的值
					m_Values.remove(aName);
					m_Values.put(aName, alObj);
				}
				break;
			case 2: // 替换模式
				m_Values.remove(aName);
				m_Values.put(aName, aObj); // 替换成传入的参数对象，不区分原来的对象类型
				break;
			default:
				throw new FrameException(-12990003, "setValueByName(),invalid input param:" + aInsert);
			}
		} else {
			m_Values.put(aName, aObj);
		}
	}

	
	/**
	 * 获取指定名称参数的个数
	 *
	 * @param aName(参数名称)
	 * @param aName
	 * @return int(参数个数)
	 */
	public int getCountByName(String aName) {
		return getCountByName(aName, true);
	}

	/**
	 * 获取指定名称参数的个数
	 *
	 * @param aName(参数名称)
	 * @param aSkipArray(是否忽略数组，因为多条同名参数是存储在一个数组中，因此对于采用ArrayLi
	 *            st数组类型的参数需要特殊处理)
	 * @param aName
	 * @param aSkipArray
	 * @return int(参数的个数)
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
	 * 获取服务名称
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

	/** 删除值对象中的键值 */
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
	 *            以路径来获取指定路径的值，只支持hashmap+hashmap...+hashmap+string的格式
	 * @param strDefault
	 *            没有找到给定的缺省值
	 * @param bThrow
	 *            没有找到,而且缺省值为null,判断是否抛出异常
	 * @return
	 * @throws FrameException
	 */
	public String getValueByNameEx(String strPath, String strDefault,
			boolean bThrow) throws FrameException {
		String strValue = null, strName = null;
		StringTokenizer st = null;
		Object o = null;
		HashMap hmTemp = null;
		st = new StringTokenizer(strPath, ".");// 以 . 作为分隔符
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
					strValue = ""; // 给定一个空值
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
