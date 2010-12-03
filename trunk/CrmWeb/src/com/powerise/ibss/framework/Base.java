package com.powerise.ibss.framework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.powerise.ibss.framedata.bo.FrameServiceController;
import com.powerise.ibss.framedata.vo.SQLArgs;
import com.powerise.ibss.framedata.vo.TfmActArgsVO;
import com.powerise.ibss.framedata.vo.TfmServicesVO;
import com.ztesoft.common.dao.ConnectionContext;

import com.ztesoft.common.dao.DAOSQLUtils;

/**
 * 
 * Baseʹ�÷�ʽ
 * 
 * ��Ϊ������ʹ�ã����� �� Base __base = new Base(conn); //��Ҫ�������� __base.query(...);
 *                          
 */

public class Base {

	public Base() {

	}

	/***************************************************************************
	 * 
	 * PART-A-4 ACTIONִ�з����� ��Σ�aDict������connection,��̬action_id,���󶨲��� ���Σ������
	 * select���򷵻ؽ����(dynamicdict)�������update����__updatecountΪ��¼��
	 * 
	 **************************************************************************/
	/**
	 * ִ��ACTION ���˵���� ecode ִ�е��־���� aDict, aName, aSeq
	 * SQL�����Դ��aName��ֵ��ʾHashMap��ʽ����ֵ��ʾ��aDict��ȡ dictr, name
	 * ���ؽ������name��ֵ��ʾHashMap��ʽ����ֵ��ʾֱ�ӷ��� dact ACTION etype, edesc ���������������Ϣ
	 * 
	 */
	private static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, String[] aName, int[] aSeq, DynamicDict dictr,
			String name, DynamicDict dact, int etype, String edesc)
			throws FrameException {
		// sql
		String sql = (String) dact.getValueByName("__sql");
		String sql_type = (String) dact.getValueByName("__sql_type", false);
		jndiName = jndiName.toUpperCase() ;
		// ��1���滻��������
		ArrayList aparaname = new ArrayList();
		int checkseq = 0, checkpos = -1;
		int icount = dact.getCountByName("__sql_in_arg");
		for (int i = 0; i < icount; i++) {
			String nam = (String) dact.getValueByName("__sql_in_arg", i);
			int seq = Integer.parseInt((String) dact.getValueByName(
					"__sql_in_seq", i));
			int flg = Integer.parseInt((String) dact.getValueByName(
					"__sql_in_flg", i));

			if (seq < 1)
				throw new FrameException(-92010401, "SQL�󶨲�����Ŵ���: "
						+ String.valueOf(seq) + " ( ��СΪ 1 ) ");
			if (seq > icount)
				throw new FrameException(-92010402, "SQL�󶨲�����Ŵ���: "
						+ String.valueOf(seq) + " ( ���Ϊ "
						+ String.valueOf(icount) + " )");

			String val = "";
			if ((aName == null) || (aName.length <= 0)) // DynamicDict��ȡֵ
			{
				int offset = 0;
				if ((aSeq != null) && (aSeq.length > 0))
					offset = aSeq[0];

				if (nam.startsWith("$")) // ֧�ֲ�������$��ַ
					nam = (String) dict__get_val(aDict, nam.substring(1),
							offset); // ȡaDict��p�е�ֵ

				val = (String) dict__get_val(aDict, nam, offset); // ȡaDict��p�е�ֵ
			} else // DynamicDict��HashMap��ʽȡֵ����aSeq�����0�����ȼ���ֵ
			{
				for (int j = aName.length - 1; j >= 0; j--) {
					HashMap h;

					if ((aSeq == null) || (aSeq.length <= 0))
						h = (HashMap) aDict.getValueByName(aName[j]);
					else if (aSeq.length <= 1)
						h = (HashMap) aDict.getValueByName(aName[j], aSeq[0]);
					else
						h = (HashMap) aDict.getValueByName(aName[j], aSeq[j]);

					if (h.get(nam) != null) {
						val = (String) h.get(nam);
						break;
					}
				}
			}

			// ��������(�󶨻��滻)
			if (flg == 3) {
				while (checkseq < aparaname.size()) {
					if ((checkpos = sql.indexOf('?', checkpos + 1)) < 0)
						throw new FrameException(-92010403, "SQL�滻������Ŵ���: "
								+ String.valueOf(seq) + " ( ���Ϊ "
								+ String.valueOf(icount) + " )");
					checkseq++;
				}

				if ((checkpos = sql.indexOf('?', checkpos + 1)) < 0)
					throw new FrameException(-92010403, "SQL�滻������Ŵ���: "
							+ String.valueOf(seq) + " ( ���Ϊ "
							+ String.valueOf(icount) + " )");

				sql = sql.substring(0, checkpos) + val
						+ sql.substring(checkpos + 1);
			} else {
				aparaname.add(nam);
			}
		}

		// ��2������ASQL
		if (sql.indexOf('$') >= 0) // ����֧��ASQL
			sql = SQLResolver.asql__parse(sql, aparaname);

		// ��3���󶨲�������
		ArrayList apara = new ArrayList();
		icount = aparaname.size();
		for (int i = 0; i < icount; i++) {
			String nam = (String) aparaname.get(i);

			String val = "";
			if ((aName == null) || (aName.length <= 0)) // DynamicDict��ȡֵ
			{
				if ((aSeq == null) || (aSeq.length <= 0))
					val = (String) dict__get_val(aDict, nam, 0); // ȡaDict��p�е�ֵ
				else
					val = (String) dict__get_val(aDict, nam, aSeq[0]); // ȡaDict��p�е�ֵ
			} else // DynamicDict��HashMap��ʽȡֵ����aSeq�����0�����ȼ���ֵ
			{
				for (int j = aName.length - 1; j >= 0; j--) {
					HashMap h;

					if ((aSeq == null) || (aSeq.length <= 0))
						h = (HashMap) aDict.getValueByName(aName[j]);
					else if (aSeq.length <= 1)
						h = (HashMap) aDict.getValueByName(aName[j], aSeq[0]);
					else
						h = (HashMap) aDict.getValueByName(aName[j], aSeq[j]);

					if (h.get(nam) != null) {
						val = (String) h.get(nam);
						break;
					}
				}
			}

			apara.add(val);
		}

		String[] para = new String[apara.size()];
		for (int i = 0; i < apara.size(); i++)
			para[i] = (String) apara.get(i);

		// ִ��SQL
		if (sql_type.equals("4")) // select
		{
			int write_type = (etype & ((dictr == null) ? WHEN : 0));

			if (write_type == WHEN_WRITE_BACK) {
				if ((name != null) && (!name.equals("")))
					write_type = 0;
			} else if (write_type == WHEN_WRITE_RECORDSET) {
				dictr = aDict;
				if ((name == null) || name.equals(""))
					name = "RECORDSET";
			}

			dictr = QueryHelper.query(jndiName, sql, para, dictr, name, ecode,
					etype, edesc);

			if (write_type == WHEN_WRITE_BACK) {
				Iterator it = dictr.m_Values.keySet().iterator();
				while (it.hasNext()) {
					String nam = (String) it.next();
					String val = (String) dictr.getValueByName(nam);

					if (val.equals(""))
						continue;

					if ((aName == null) || (aName.length <= 0)) // DynamicDict��ȡֵ
					{
						if ((aSeq == null) || (aSeq.length <= 0))
							dict__set_val(aDict, nam, 0, val);
						else
							dict__set_val(aDict, nam, aSeq[0], val);
					} else // DynamicDict��ȡHashMap
					{
						for (int j = aName.length - 1; j >= 0; j--) {
							HashMap h;

							if ((aSeq == null) || (aSeq.length <= 0))
								h = (HashMap) aDict.getValueByName(aName[j]);
							else if (aSeq.length <= 1)
								h = (HashMap) aDict.getValueByName(aName[j],
										aSeq[0]);
							else
								h = (HashMap) aDict.getValueByName(aName[j],
										aSeq[j]);

							if ((h.get(nam) != null) || (j == 0)) {
								h.put(nam, val);
							}
						}
					}
				}
			}

			return dictr;
		} else if (sql_type.equals("2")) // update
		{
			int update_count = update(jndiName, sql, para, ecode, etype, edesc);
			if (dictr == null)
				dictr = new DynamicDict();
			dictr.setValueByName("__count", String.valueOf(update_count));

			return dictr;
		} else
			throw new FrameException(-92010403,
					"ִ�еĶ�̬SQLӦ����select/update����(4/2):ʵ������:" + sql_type);
	}

	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, String[] aName, int[] aSeq, DynamicDict dictr,
			String name, String action_id, int etype, String edesc)
			throws FrameException {
		DynamicDict dact = action__by_id(action_id);
		return action__execute(jndiName, ecode, aDict, aName, aSeq, dictr,
				name, dact, etype, edesc);
	}

	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, DynamicDict dictr, String name, String action_id)
			throws FrameException {
		return action__execute(jndiName, ecode, aDict, null, null, dictr, name,
				action_id, 0, null);
	}

	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, DynamicDict dictr, String name, String sql,
			String[] paraname) throws FrameException {
		return action__execute(jndiName, ecode, aDict, null, null, dictr, name,
				sql, paraname, 0, null);
	}

	// ��ACTION_IDִ��ACTION�����ɼ򻯷���
	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, String action_id) throws FrameException {
		return action__execute(jndiName, ecode, aDict, null, null, null, null,
				action_id, 0, null);
	}

	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, String action_id, int etype, String edesc)
			throws FrameException {
		return action__execute(jndiName, ecode, aDict, null, null, null, null,
				action_id, etype, edesc);
	}

	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, int p, String action_id) throws FrameException {
		return action__execute(jndiName, ecode, aDict, null, new int[] { p },
				null, null, action_id, 0, null);
	}

	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, int p, String action_id, int etype, String edesc)
			throws FrameException {
		return action__execute(jndiName, ecode, aDict, null, new int[] { p },
				null, null, action_id, etype, edesc);
	}

	// ��SQLִ��ACTION�����ɼ򻯷���
	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, String sql, String[] paraname)
			throws FrameException {
		return action__execute(jndiName, ecode, aDict, null, null, null, null,
				sql, paraname, 0, null);
	}

	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, String sql, String[] paraname, int etype,
			String edesc) throws FrameException {
		return action__execute(jndiName, ecode, aDict, null, null, null, null,
				sql, paraname, etype, edesc);
	}

	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, int p, String sql, String[] paraname)
			throws FrameException {
		return action__execute(jndiName, ecode, aDict, null, new int[] { p },
				null, null, sql, paraname, 0, null);
	}

	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, int p, String sql, String[] paraname, int etype,
			String edesc) throws FrameException {
		return action__execute(jndiName, ecode, aDict, null, new int[] { p },
				null, null, sql, paraname, etype, edesc);
	}

	/*
	 * ��SQL��ACTION_IDִ��ACTION��2�ֻ�������
	 */
	public static DynamicDict action__execute(String jndiName, int ecode,
			DynamicDict aDict, String[] aName, int[] aSeq, DynamicDict dictr,
			String name, String sql, String[] paraname, int etype, String edesc)
			throws FrameException {
		DynamicDict dact = action__by_sql(sql, paraname);
		return action__execute(jndiName, ecode, aDict, aName, aSeq, dictr,
				name, dact, etype, edesc);
	}

	/**
	 * ����Action_ID��ѯ����sql������
	 */
	private static DynamicDict action__by_id(String serviceName)
			throws FrameException {
		DynamicDict dictr = new DynamicDict();
		TfmServicesVO serviceVO = FrameServiceController.getInstance().getServiceByName(serviceName);
		SQLArgs sqlArgs = serviceVO.getSqlArgs() ;
		// �����Ժ���
		{
			String sql = sqlArgs.getSQL();
			dictr.setValueByName("__sql", sql);
			if (sql.trim().toUpperCase().startsWith("SELECT")
					|| sql.trim().toUpperCase().startsWith("WITH"))
				dictr.setValueByName("__sql_type", "4");
			else
				dictr.setValueByName("__sql_type", "2");

			List args = sqlArgs.getArgs();
			if (args != null) {
				for (int i = 0; i < args.size(); i++) {
					TfmActArgsVO arg = (TfmActArgsVO) args.get(i);
					String ioType = arg.getIn_out_flag() ;
					if ("1".equals(ioType) || "3".equals(ioType)) // ���
					{
						dictr.setValueByName("__sql_in_arg",arg.getArg_name()
								.toUpperCase().trim(), 1);
						dictr.setValueByName("__sql_in_seq", String
								.valueOf(arg.getArg_seq()), 1);
						dictr.setValueByName("__sql_in_flg", ioType, 1);
					}
				}
			}
		}

		return dictr;
	}

	/*
	 * ����ASQL����ָ���󶨲����������ؽ������SQL������
	 */
	private static DynamicDict action__by_sql(String sql, String[] paraname)
			throws FrameException {

		DynamicDict dictr = new DynamicDict();

		dictr.setValueByName("__sql", sql);
		if (sql.trim().toUpperCase().startsWith("SELECT")
				|| sql.trim().toUpperCase().startsWith("WITH"))
			dictr.setValueByName("__sql_type", "4");
		else
			dictr.setValueByName("__sql_type", "2");

		if (paraname != null) {
			for (int i = 0; i < paraname.length; i++) {
				dictr.setValueByName("__sql_in_arg", paraname[i].toUpperCase()
						.trim(), 1);
				dictr.setValueByName("__sql_in_seq", String.valueOf(i + 1), 1);
				dictr.setValueByName("__sql_in_flg", "1", 1);
			}
		}

		return dictr;
	}

	/***************************************************************************
	 * 
	 * PART-A-3 DynmaicDict���󸽼Ӳ�����
	 * 
	 * ������
	 * 
	 **************************************************************************/

	/**
	 * ����������ָ��λ�ý���ֵ��ȡ������λ�ó�����Χ���򷵻�"")
	 */
	public static Object dict__get_val(DynamicDict aDict, String name, int seq)
			throws FrameException {
		if ((seq >= 0) && (seq < aDict.getCountByName(name))) {
			return aDict.getValueByName(name, seq);
		} else if (aDict.getCountByName(name) > 0) {
			Object obj = aDict.getValueByName(name);
			if (obj instanceof String) {
				return obj;
			} else {
				try {
					return obj.getClass().newInstance(); // ���ؿն���
				} catch (Throwable e) {
					throw new FrameException(-9900232,
							"��ȡ���ݰ�ʱ�������󣺸ö������Ͳ�֧��Ĭ��ʵ��");
				}
			}
		} else {
			return aDict.getValueByName(name, false);
		}
	}

	/**
	 * ����������ָ��λ�ý���ֵ�洢������λ�ó�����Χ��������ñ�������λ��)
	 */
	public static void dict__set_val(DynamicDict aDict, String name, int seq,
			Object value) throws FrameException {
		HashMap m_Values = aDict.m_Values;
		Object obj;
		ArrayList al;

		if (seq < 0)
			return;
		if (value == null)
			return;
		if (value instanceof String)
			value = ((String) value).trim();

		name = name.toUpperCase().trim();
		obj = m_Values.get(name);

		if (obj == null) // ���ԭ���޴˱���
		{
			if (seq == 0) {
				m_Values.put(name, value);
				return;
			}
			m_Values.put(name, al = new ArrayList());
		} else if (obj instanceof ArrayList) // �����ArrayList
		{
			al = (ArrayList) obj;
		} else // �������������
		{
			if (seq == 0) {
				m_Values.put(name, value);
				return;
			}
			al = new ArrayList();
			al.add(obj);
			m_Values.put(name, al);
		}

		int size = al.size();
		if (seq >= size) // �±곬���򲹳�
			for (int i = size; i <= seq; i++) {
				if (value instanceof String) {
					al.add("");
				} else {
					try {
						al.add(value.getClass().newInstance()); // �Ȳ���ն���
					} catch (Throwable e) {
						throw new FrameException(-9900231,
								"д�����ݰ�ʱ�������󣺸ö������Ͳ�֧��Ĭ��ʵ��");
					}
				}
			}
		al.set(seq, value);

		return;
	}

	/***************************************************************************
	 * 
	 * PART-A-2 SQL����������
	 * 
	 * 
	 **************************************************************************/

	/*
	 * ִ��ѡ�0-3bit���ڱ�����������4λ��������ѡ����磺
	 * 
	 * WHEN_NO_DATA_FOUND | OPTION_RETURN_STRUCT ��ʾ �޼�¼�������򷵻ز�ѯ����ͽ�����ṹ
	 * 
	 * WHEN_NO_DATA_FOUND | OPTION_RETURN_COLUMN ��ʾ �޼�¼����������C1, C2, C3
	 * ..������ʽ���ؽ��
	 * 
	 */
	public static final int WHEN = 15, // WHEN��������
			WHEN_NO_DATA_FOUND = 1, // ���ڼ���ѯ���
			WHEN_DATA_FOUND = 2, // ���ڼ���ѯ���
			WHEN_COUNT_IS_0 = 3, // ���ڼ���޸����
			WHEN_COUNT_IS_NOT_0 = 4, // ���ڼ���޸����
			WHEN_COUNT_IS_NOT_1 = 5, // ���ڼ���޸����
			WHEN_WRITE_BACK = 6, // ֻ��ACTION��Ч����ѯ�����д������
			WHEN_WRITE_RECORDSET = 7, // ֻ��ACTION��Ч����ѯ����ؼ��뵽RECORDSET����
			OPTION_RETURN_COLUMN = 16, // ��C1,C2,..���������ؽ����
			OPTION_RETURN_STRUCT = 32; // ͬʱ���ؽ�����ṹ��__field_name, __field_type

	private static final int OPTION_RETURN_FIRST1 = 64, // ֻ���ص�1����¼
			OPTION_LOGGED = 128; // ִ��ǰ��ӡ��־

	// ��ѯ1��long��Ĭ��ֵ0���ж���ֶ������������֮1��
	public static long query_long(String jndiName, String sql, String[] para,
			int ecode) throws FrameException {
		return query_long(jndiName, sql, para, ecode, 0, null);
	}

	// ��ѯ1��int��Ĭ��ֵ0���ж���ֶ������������֮1��
	public static long query_long(String jndiName, String sql, String[] para,
			int ecode, int etype, String edesc) throws FrameException {
		long long_integer = 0;
		String str = query_string(jndiName, sql, para, ecode, etype, edesc);
		if ((str != null) && !str.equals("")) {
			try {
				long_integer = Long.parseLong(str);
			} catch (NumberFormatException e) {
				throw new FrameException(-92010201, "���ָ�ʽ����" + str);
			}
		}

		return long_integer;
	}

	// ��ѯ1��int��Ĭ��ֵ0���ж���ֶ������������֮1��
	public static int query_int(String jndiName, int ecode, String sql)
			throws FrameException {
		return query_int(jndiName, sql, null, ecode, 0, null);
	}

	// ��ѯ1��int��Ĭ��ֵ0���ж���ֶ������������֮1��
	public static int query_int(String jndiName, int ecode, String sql,
			String[] para) throws FrameException {
		return query_int(jndiName, sql, para, ecode, 0, null);
	}

	// ��ѯ1��int��Ĭ��ֵ0���ж���ֶ������������֮1��
	public static int query_int(String jndiName, String sql, String[] para,
			int ecode, int etype, String edesc) throws FrameException {
		int integer = 0;
		String str = query_string(jndiName, sql, para, ecode, etype, edesc);
		if ((str != null) && !str.equals("")) {
			try {
				integer = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				throw new FrameException(-92010201, "���ָ�ʽ����" + str);
			}
		}

		return integer;
	}

	// ��ѯ1���ַ�����Ĭ��ֵ""���ж���ֶ������������֮1��
	public static String query_string(String jndiName, String sql, int ecode)
			throws FrameException {
		return query_string(jndiName, sql, null, ecode, 0, null);
	}

	// ��ѯ1���ַ�����Ĭ��ֵ""���ж���ֶ������������֮1��
	public static String query_string(String jndiName, String sql,
			String[] para, int ecode) throws FrameException {
		return query_string(jndiName, sql, para, ecode, 0, null);
	}

	// ��ѯ1���ַ�����Ĭ��ֵ""���ж���ֶ������������֮1��
	public static String query_string(String jndiName, String sql,
			String[] para, int ecode, int etype, String edesc)
			throws FrameException {
		String str = "";

		DynamicDict dictr = QueryHelper.query(jndiName, sql, para, null, null,
				ecode, etype, edesc);
		Iterator it = dictr.m_Values.keySet().iterator();
		if (it.hasNext())
			str = (String) dictr.getValueByName((String) it.next(), false);

		return str;
	}

	// ִ��SELECT��䣬ֱ�ӷ���,������ڶ��У�ֻ����һ�з�װΪHashMap��¼
	public static DynamicDict query(String jndiName, String sql, String[] para,
			int ecode) throws FrameException {
		return QueryHelper.query(jndiName, sql, para, null, null, ecode, 0,
				null);
	}

	// ִ��SELECT��䣬ֱ�ӷ���
	public static DynamicDict query(String jndiName, String sql, String[] para,
			int ecode, int etype, String edesc) throws FrameException {
		return QueryHelper.query(jndiName, sql, para, null, null, ecode, etype,
				edesc);
	}

//	 ִ��SELECT��䣬ֱ�ӷ���
	public static DynamicDict query(String jndiName, String sql, List para,String name ,
			int ecode, int etype, String edesc) throws FrameException {
		return QueryHelper.query(jndiName, sql, para, null, name, ecode, etype,
				edesc);
	}

	// ִ��SELECT��䣬ֱ�ӷ���
	public static DynamicDict query(String jndiName, String sql, List para,
			int ecode, int etype, String edesc) throws FrameException {
		return QueryHelper.query(jndiName, sql, para, null, null, ecode, etype,
				edesc);
	}
	// ִ��SELECT��䣬ֱ�ӷ���(HashMap��ʽ)
	public static DynamicDict query(String jndiName, String sql, String[] para,
			int ecode, String name) throws FrameException {
		return QueryHelper.query(jndiName, sql, para, null, name, ecode, 0,
				null);
	}

	// ִ��SELECT��䣬ֱ�ӷ���(HashMap��ʽ)
	public static DynamicDict query(String jndiName, String sql, String[] para,
			String name, int ecode, int etype, String edesc)
			throws FrameException {
		return QueryHelper.query(jndiName, sql, para, null, name, ecode, etype,
				edesc);
	}

	// ִ��SELECT��䣬׷�ӷ���
	public static DynamicDict query(String jndiName, String sql, String[] para,
			int ecode, DynamicDict dictr) throws FrameException {
		return QueryHelper.query(jndiName, sql, para, dictr, null, ecode, 0,
				null);
	}

	// ִ��SELECT��䣬׷�ӷ���
	public static DynamicDict query(String jndiName, int ecode,
			DynamicDict dictr, String sql, String[] para, int etype,
			String edesc) throws FrameException {
		return QueryHelper.query(jndiName, sql, para, dictr, null, ecode,
				etype, edesc);
	}

	public static DynamicDict query(String jndiName, String sql, String[] para,
			DynamicDict dictr, String name, int ecode) throws FrameException {
		return QueryHelper.query(jndiName, sql, para, dictr, name, ecode, 0,
				null);
	}
	
	public static DynamicDict queryPage(String jndiName ,String querySQL,String countSQL,
			String[] para,int pageIndex, int pageSize ,  
				DynamicDict dto, String name,
				int ecode, int etype, String edesc) throws FrameException{
		return QueryHelper.queryPage(jndiName, querySQL, countSQL, para, pageIndex, pageSize, 
				dto, name, ecode, etype, edesc) ;
	}

	public static DynamicDict queryPage(String jndiName ,String querySQL,String countSQL,
			List para,int pageIndex, int pageSize ,  
				DynamicDict dto, String name,
				int ecode, int etype, String edesc) throws FrameException{
		return QueryHelper.queryPage(jndiName, querySQL, countSQL, para, pageIndex, pageSize, 
				dto, name, ecode, etype, edesc) ;
	}
	// ִ��UPDATE��䣬���ؼ�¼��
	public static int update(String jndiName, String sql, String[] para,
			int ecode) throws FrameException {
		return update(jndiName, sql, para, ecode, 0, null);
	}

	// ִ��UPDATE��䣬���ؼ�¼��
	public static int update(String jndiName, String sql, String[] para,
			int ecode, int etype, String edesc) throws FrameException {
		if (sql.indexOf('$') >= 0) // ����֧��ASQL
			sql = SQLResolver.asql__parse(sql);

		return sql__update(ConnectionContext.getContext().getConnection(
				jndiName), ecode, sql, para, etype, edesc);
	}

//	 ִ��UPDATE��䣬���ؼ�¼��
	public static int update(String jndiName, String sql, List para,
			int ecode, int etype, String edesc) throws FrameException {
		if (sql.indexOf('$') >= 0) // ����֧��ASQL
			sql = SQLResolver.asql__parse(sql);

		return sql__update(ConnectionContext.getContext().getConnection(
				jndiName), ecode, sql, para, etype, edesc);
	}
	
	//����ִ��INSERT,����UPDATE��䣬���ؼ�¼��
	public static int[] batchUpdate(String jndiName, String sql, List para,
			int ecode, int etype, String edesc) throws FrameException {
		if (sql.indexOf('$') >= 0) // ����֧��ASQL
			sql = SQLResolver.asql__parse(sql);

		return sql__batch_update(ConnectionContext.getContext().getConnection(
				jndiName), ecode, sql, para, etype, edesc);
	}
	
	
	/**
	 * UPDATE,DELETE,INSERT -> update count
	 */
	private static int sql__update(Connection conn, int ecode, String sql,
			String[] para, int etype, String edesc) throws FrameException {
		sql = DAOSQLUtils.getFilterSQL(sql) ;
		if ((etype & OPTION_LOGGED) > 0) // Ҫ��¼��־
		{
			String spara = "";
			if (para != null)
				for (int i = 0; i < para.length; i++)
					spara += "," + para[i];

			if (!spara.equals(""))
				spara = spara.substring(1);

			LogHelper.log("SQL=" + sql + "\nPARA=" + spara);
		}

		PreparedStatement st = null;
		int count = -1;
		try {
			st = conn.prepareStatement(sql);
			if (para != null)
				for (int i = 0; i < para.length; i++)
					st.setString(i + 1, para[i]);
			count = st.executeUpdate();

			switch (etype & WHEN) {
			case WHEN_COUNT_IS_0:
				if (count == 0)
					throw new FrameException(ecode, edesc);
				break;

			case WHEN_COUNT_IS_NOT_0:
				if (count != 0)
					throw new FrameException(ecode, edesc);
				break;

			case WHEN_COUNT_IS_NOT_1:
				if (count != 1)
					throw new FrameException(ecode, edesc);

			default:
			}
		} catch (SQLException e) {
			throw new FrameException(ecode, "ִ��SQLʱ�쳣: " + e.getMessage());
		} finally {
			if (st != null)
				try {
					st.close();
				} catch (Throwable ee) {
				}
		}

		return count;
	}

	/**
	 * UPDATE,DELETE,INSERT -> update count
	 */
	private static int sql__update(Connection conn, int ecode, String sql,
			List para, int etype, String edesc) throws FrameException {
		
		sql= DAOSQLUtils.getFilterSQL(sql) ;
//		if ((etype & OPTION_LOGGED) > 0) // Ҫ��¼��־
//		{
			String spara = "";
			if (para != null)
				for (int i = 0 , j = para.size() ; i < j ; i++)
					spara += "," + para.get(i);

			if (!spara.equals(""))
				spara = spara.substring(1);
//			System.out.println("SQL=" + sql + "\nPARA=" + spara) ;
			LogHelper.log("SQL=" + sql + "\nPARA=" + spara);
//		}

		PreparedStatement st = null;
		int count = -1;
		try {
			st = conn.prepareStatement(sql);
			if (para != null)
				for (int i = 0 , j = para.size() ; i < j ; i++){
					setObject2Statement(  para.get(i), st ,  i+1 ) ;
				}
			count = st.executeUpdate();

			switch (etype & WHEN) {
			case WHEN_COUNT_IS_0:
				if (count == 0)
					throw new FrameException(ecode, edesc);
				break;

			case WHEN_COUNT_IS_NOT_0:
				if (count != 0)
					throw new FrameException(ecode, edesc);
				break;

			case WHEN_COUNT_IS_NOT_1:
				if (count != 1)
					throw new FrameException(ecode, edesc);

			default:
			}
		} catch (SQLException e) {
			throw new FrameException(ecode, "ִ��SQLʱ�쳣: " + e.getMessage());
		} finally {
			if (st != null)
				try {
					st.close();
				} catch (Throwable ee) {
				}
		}

		return count;
	}
	
	
	/**
	 * UPDATE,DELETE,INSERT -> update count
	 */
	private static int[] sql__batch_update(Connection conn, int ecode, String sql,
			List paras, int etype, String edesc) throws FrameException {
		sql= DAOSQLUtils.getFilterSQL(sql) ;
		LogHelper.log("SQL=" + sql + "\nPARA=" + paras.toString());
		PreparedStatement st = null;
		int[] counts;
		try {
			st = conn.prepareStatement(sql);
			if (paras != null)
				for(int k=0;k<paras.size();k++){
					List para=(List)paras.get(k);
					//���ò���
					for (int i = 0 , j = para.size() ; i < j ; i++){
						setObject2Statement(  para.get(i), st ,  i+1 ) ;
					}
					st.addBatch();//����Ԥ����
				}
				counts=st.executeBatch();
		} catch (SQLException e) {
			throw new FrameException(ecode, "ִ��SQLʱ�쳣: " + e.getMessage());
		} finally {
			if (st != null)
				try {
					st.close();
				} catch (Throwable ee) {
				}
		}

		return counts;
	}
	
	
	private static boolean isDateType(Object o ){
//		System.out.println(o.toString() +"======="+( o != null &&( 
//				"java.util.Date".equals(o.getClass().getName()) ||
//				"java.sql.Date".equals(o.getClass().getName())))) ;
		return o != null &&( 
				"java.util.Date".equals(o.getClass().getName()) ||
				"java.sql.Date".equals(o.getClass().getName())) ;
	}
	
	private static boolean isStringType(Object o ){
//		System.out.println(o.toString() +"======="+( o != null &&( 
//				"java.util.Date".equals(o.getClass().getName()) ||
//				"java.sql.Date".equals(o.getClass().getName())))) ;
		return o != null &&"java.lang.String".equals(o.getClass().getName()) ;
	}
	
	private static void setObject2Statement(Object o , PreparedStatement s , int i ) throws SQLException{
		if(isDateType( o )){
			s.setDate(i, (java.sql.Date)o) ;
		}else{//�Կպ��ַ�������������informix���ݿ�lvarcher�����ֶβ�������ض�����
			if(o == null || o.equals("")){
				s.setString(i, "") ;
			}else{
				if(isStringType(o)){
					s.setString(i, (java.lang.String)o) ;
				}else{
					s.setObject(i, o) ;
				}
			}
		}
		
	}
	public static void main(String[] args) {
		String[] param = new String[1];
		param[0] = "100001";

		StringBuffer s = new StringBuffer();
		
		java.util.Date d = new java.util.Date() ;
		System.out.println(d.getClass().getName().equals("java.util.Date"))  ;
		try {
			/**
			 dto.m_Values
			 [STYPE]=[100001, 100001, 100001]
			 [COMMENTS]=[, , ]
			 [PCODE]=[00A, 00H, 00X]
			 [PNAME]=[��Ч, �ѹ鵵, ע��]
			 [PKEY]=[00A, 00H, 00X]
			 
			 */
			//			DynamicDict dto = Base.query("Default", "select * from dc_public where stype=?", param , 3);
			/**
			 [{STYPE=100001, COMMENTS=, PCODE=00A, PNAME=��Ч, PKEY=00A}, 
			 {STYPE=100001, COMMENTS=, PCODE=00H, PNAME=�ѹ鵵, PKEY=00H}, {STYPE=100001, COMMENTS=, PCODE=00X, PNAME=ע��, PKEY=00X}]
			 List myList = (List) dto.m_Values.get("ABC") 
			 */
//			DynamicDict dto = Base.query("Default",
//					"select * from dc_public where stype=?", param, 3, "ABC");

			//			Object result = dto.getValueByName("result") ;
			//			if(result != null ){
//			List myList = (List) dto.m_Values.get("ABC");
//			//				Iterator it = myList.values().iterator();
//			//				while( it.hasNext()){
//			//					List k = (List)it.next() ;
//			Iterator mi = myList.iterator();
//			while (mi.hasNext()) {
//				Map dd = (Map) mi.next();
//				Iterator xd = dd.keySet().iterator();
//				while (xd.hasNext()) {
//
//					String kk = (String) xd.next();
//					String v = (String) dd.get(kk);
//					s.append("[" + kk + "]=" + v + ",");
//				}
//				s.append("\n");
//			}
			//					
			//				}
			//			}
//			System.out.println("RESULT==========" + s.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
