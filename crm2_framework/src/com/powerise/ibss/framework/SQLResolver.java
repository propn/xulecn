package com.powerise.ibss.framework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import com.ztesoft.common.dao.ConnectionContext;

/**
 * 
 * SQL�ʷ����ʹ�����
 * �ع���Base.java
 *
 */
public class SQLResolver {
	private SQLResolver(){
		
	}

	/***************************************************************************
	 * 
	 * PART-A-0
	 * 
	 * ASQL����������
	 * 
	 * ������ ��asql���е���չ�������з������������ɵ�sql�������б�
	 * 
	 * ������̣� �������sql������չ��λ�ã�����չ�������﷨�����γɽ�����󣬽�����������崦������ Ŀ��sql���󶨲����б�
	 * 
	 * �÷���֧��3�־��ͳ���
	 * 
	 * 1����select�ı��ʽ�б��У� SELECT [Exprs,] [expr_part]$columns[expr_part] [,Exprs]
	 * FROM Table_list ���У�expr_part��ʾ��Χ$columns�ı��ʽƬ�� ע�⣺
	 * (1)$columns���ı��ʧƬ�β��ܰ���','����Ϊ��������������1�α����޷����֣������Ҳ�û���⣻
	 * ��Ϊ������֮���Ѿ��õ�������Χ��Ϣ(��Ҫ֧����Ҫ�ڷ���ǰ���������1��ȷ����Χ)��
	 * (2)�����Χ$columns����to_char����������������Զ���'yyyy-mm-dd hh24:mi:ss'��ʽ��
	 * (3)���$columnsλ���Ӳ�ѯ�У���ֻӰ����Ӳ�ѯ����Ӱ����Χ��ѯ�� (4)���1������а������$columns�����ǰ����˳�������
	 * ���磺 select param_value,$columns from tsm_paramater�������ɣ� select
	 * param_value,param_id,param_desc from tsm_paramater
	 * 
	 * select a.param_value,to_char(a.$columns)||'123' from tsm_paramater
	 * a,tvlsm_bureau b �����ɣ� select a.param_value,to_char(a.param_id)||'123'
	 * param_id,to_char(a.param_desc)||'123' param_desc from tsm_paramater
	 * a,tvlsm_bureau b
	 * 
	 * 2����insert into�����б��У� INSERT INTO Table_name [Alia] ([Cols,] $columns
	 * [Cols]) ���У�ColsΪ$columns�����ָ������ ע�⣺
	 * (1)Table_name��Alia��(��֮������пո���Ϊ������������1�α��������Էֱ�(��֮ǰ�Ƿ�Ҫ�ָ��� ���磺 insert into
	 * tsm_paramater a (param_value, $columns) values (?) �����ɣ� insert into
	 * tsm_paramater a(param_value,param_id,param_desc) values (?,?,?)
	 * ���ҽ�������param_id,param_desc�ӵ�apara������ʵ�λ��
	 * 
	 * 3����insert into values�İ󶨲����б��У� INSERT INTO Table_name [Alia] [(Cols)]
	 * Values ([Exprs,] [expr_part]$columns[expr_part] [,Exprs])
	 * ���У�expr_partΪ$columns����ı��ʽƬ�� ע�⣺
	 * (1)Table_name��Alia��(��֮������пո���Ϊ������������1�α��������Էֱ�(��֮ǰ�Ƿ�Ҫ�ָ���
	 * (2)$columns���԰�Χ�ڱ��ʽ�У������ʽ����಻����','�ţ�
	 * (3)�������͵��ֶΣ���$columns���ɵ�?�Զ�����to_date('yyyy-mm-dd hh24:mi:ss')��ʽ ���磺 insert
	 * into tsm_paramater a (param_value, $columns) values (?) �����ɣ� insert into
	 * tsm_paramater a (param_value,param_id,param_desc) values (?,?,?)
	 * ���ҽ�������param_id,param_desc�ӵ�apara������ʵ�λ��
	 * 
	 * ������Ϣ�� -9201000* �������� -9201001* ����select���̴��� -9201002* ����into_values���̴���
	 * -9201003* ����values���̴���
	 * 
	 **************************************************************************/

	//========================================  asql__parse ======================================================
	// ����������չ����ASQL�����ؽ������SQL
	public static String asql__parse(String sql) throws FrameException {
		StringBuffer asql = new StringBuffer(sql);
		asql__parse(ConnectionContext.getContext().getConnection(), asql, null);

		return asql.toString();

	}

	// ����������չ����ASQL�����ؽ������SQL�������б���������Ҫ��ASQL����?�󶨲���ʱ
	public static String asql__parse(String sql, ArrayList apara)
			throws FrameException {
		StringBuffer asql = new StringBuffer(sql);
		asql__parse(ConnectionContext.getContext().getConnection(), asql, apara);

		return asql.toString();
	}

	private static final String ASQL_CMD_COLUMNS = "COLUMNS"; // ������չ��

	private static void asql__parse(Connection conn, StringBuffer asql,
			ArrayList apara) throws FrameException {
		ArrayList lists = new ArrayList(); // ��������������
		int lp = -1, rp;
		boolean quota = false;

		while (++lp < asql.length()) {
			char c = asql.charAt(lp);
			if (!quota) {
				switch (c) {
				case '\'':
					quota = true;
					break;
				case '$':
					rp = lp + ASQL_CMD_COLUMNS.length() + 1;
					if (asql.substring(lp, rp).toUpperCase().indexOf(
							ASQL_CMD_COLUMNS.toUpperCase()) >= 0) {
						int s = asql__parse_a_key(asql, lp - 1, rp, lists);
						switch (s) {
						case ASQL_S_SELECT_FROM_TABLE:
							asql__build_select(conn, asql, apara, lp - 1, rp,
									lists);
							break;
						case ASQL_S_INSERT_VALUES:
							asql__build_insert(conn, asql, apara, lp - 1, rp,
									lists);
							break;
						case ASQL_S_VALUES_INTO_OK:
							asql__build_values(conn, asql, apara, lp - 1, rp,
									lists);
							break;
						default:
							throw new FrameException(-92010001, "ASQL�﷨����");
						}
					}
					break;
				}
			} else {
				switch (c) {
				case '\'':
					if (lp + 1 < asql.length() && asql.charAt(lp + 1) == '\'')
						lp++;
					else
						quota = false;
					break;
				}
			}
		}

	}

	private static final String // �ָ���
	ASQL_SPLITS = " ,\n\r\t()";

	private static final String[] ASQL_TKS = // ʶ��ؼ���
	{ "SELECT", "DISTINCT", "FROM", "WHERE", "GROUP", "HAVING", "UNION",
			"ORDER", "INSERT", "INTO", "VALUES", };

	private static final int // �ʷ�token
			ASQL_T_SELECT = 1,
			ASQL_T_DISTINCT = 2, ASQL_T_FROM = 3,
			ASQL_T_WHERE = 4,
			ASQL_T_GROUP = 5, ASQL_T_HAVING = 6,
			ASQL_T_UNION = 7,
			ASQL_T_ORDER = 8, ASQL_T_INSERT = 9,
			ASQL_T_INTO = 10,
			ASQL_T_VALUES = 11, ASQL_T = 0, ASQL_T_END = 99;

	private static final int[] ASQL_TOKENS = // ��ASQL_TKS�����˳��Ҫһ��
	{ ASQL_T_SELECT, ASQL_T_DISTINCT, ASQL_T_FROM, ASQL_T_WHERE, ASQL_T_GROUP,
			ASQL_T_HAVING, ASQL_T_UNION, ASQL_T_ORDER, ASQL_T_INSERT,
			ASQL_T_INTO, ASQL_T_VALUES, ASQL_T, ASQL_T_END, };

	private static final int // �﷨״̬
			ASQL_S = 0,
			ASQL_S_SELECT = 1,
			ASQL_S_SELECT_FROM = 2,
			ASQL_S_SELECT_FROM_TABLE = 3,
			ASQL_S_INSERT = 4,
			ASQL_S_INSERT_VALUES = 5,
			ASQL_S_VALUES = 6,
			ASQL_S_VALUES_INTO = 7, ASQL_S_VALUES_INTO_OK = 8;

	private static final int // �﷨״̬GOTO����ж���
			ASQL_GOTO_S = 0, // Դ״̬
			ASQL_GOTO_D = 1, // Դ����
			ASQL_GOTO_T = 2, // token
			ASQL_GOTO_TOS = 3; // Ŀ��״̬

	private static final int[][] ASQL_GOTO = // �﷨״̬GOTO��ֻ��¼���������״̬
	{
			// Դ״̬ Դ���� token Ŀ��״̬
			{ ASQL_S, -1, ASQL_T_DISTINCT, ASQL_S_SELECT, },
			{ ASQL_S, -1, ASQL_T_SELECT, ASQL_S_SELECT, },
			{ ASQL_S_SELECT, 1, ASQL_T_FROM, ASQL_S_SELECT_FROM, },
			{ ASQL_S_SELECT_FROM, 1, ASQL_T_WHERE, ASQL_S_SELECT_FROM_TABLE, },
			{ ASQL_S_SELECT_FROM, 1, ASQL_T_GROUP, ASQL_S_SELECT_FROM_TABLE, },
			{ ASQL_S_SELECT_FROM, 1, ASQL_T_HAVING, ASQL_S_SELECT_FROM_TABLE, },
			{ ASQL_S_SELECT_FROM, 1, ASQL_T_UNION, ASQL_S_SELECT_FROM_TABLE, },
			{ ASQL_S_SELECT_FROM, 1, ASQL_T_ORDER, ASQL_S_SELECT_FROM_TABLE, },
			{ ASQL_S_SELECT_FROM, 1, ASQL_T_END, ASQL_S_SELECT_FROM_TABLE, },
			{ ASQL_S, -1, ASQL_T_INTO, ASQL_S_INSERT, },
			{ ASQL_S_INSERT, 1, ASQL_T_VALUES, ASQL_S_INSERT_VALUES, },
			{ ASQL_S_INSERT, 1, ASQL_T_SELECT, ASQL_S_INSERT_VALUES, },
			{ ASQL_S, -1, ASQL_T_VALUES, ASQL_S_VALUES, },
			{ ASQL_S_VALUES, -1, ASQL_T_INTO, ASQL_S_VALUES_INTO, },
			{ ASQL_S_VALUES_INTO, 1, ASQL_T_END, ASQL_S_VALUES_INTO_OK, }, };

	/**
	 * �﷨״̬ת������
	 */
	private static int asql__goto(int s, int direct, int token) {

		for (int i = 0; i < ASQL_GOTO.length; i++)
			if ((ASQL_GOTO[i][ASQL_GOTO_S] == s)
					&& (ASQL_GOTO[i][ASQL_GOTO_D] == direct)
					&& (ASQL_GOTO[i][ASQL_GOTO_T] == token))
				return ASQL_GOTO[i][ASQL_GOTO_TOS];

		return s;
	}

	/**
	 * 
	 * ������int asql__parse(StringBuffer asql, int lp, int rp, ArrayList lists)
	 * 
	 * �ֲ��﷨����
	 * 
	 * ������ ��asql�е�ָ����չ������2�˷��������γɷ��������������lists������
	 * 
	 * ��Σ� asql,apara,lp,rp lists ���ط�������ʾ���
	 * 
	 */
	private static int asql__parse_a_key(StringBuffer asql, int lp, int rp,
			ArrayList lists) throws FrameException {
		ArrayList list;
		lists.clear();
		lists.add(list = new ArrayList());

		int[] ap = new int[6]; // ������ʷ��������ݲ���
		int level = 0, top = 0, context = 0, direct = -1, limit = 1;
		int token = 0, p = rp - 1, s = ASQL_S, tos = s;
		int qcount = 0, lqcount = 0, rqcount = 0;
		StringBuffer tkbuf = new StringBuffer();

		label_parse_end: for (;;) {
			// �ʷ�ȡ��
			ap[ASQL_I_P] = p;
			ap[ASQL_I_LEVEL] = level;
			ap[ASQL_I_TOP] = top;
			ap[ASQL_I_LIMIT] = limit;
			ap[ASQL_I_CONTEXT] = context;
			ap[ASQL_I_QCOUNT] = qcount;

			token = asql__lexical(asql, direct, ap, tkbuf);

			p = ap[ASQL_I_P];
			level = ap[ASQL_I_LEVEL];
			top = ap[ASQL_I_TOP];
			limit = ap[ASQL_I_LIMIT];
			context = ap[ASQL_I_CONTEXT];
			qcount = ap[ASQL_I_QCOUNT];

			if (token != ASQL_T_END)
				list.add(tkbuf.toString());

			// ��״̬ת����
			tos = asql__goto(s, direct, token);

			if (tos != s) // ״̬ԾǨ
			{
				switch (tos) {
				// �����������޷�����select����μ����������޷�����from������������޷�������������
				case ASQL_S_SELECT:
					lists.add(list = new ArrayList());
					direct = 1;
					context = 0;
					level = 0;
					limit = top;
					p = lp + 1;
					break;
				case ASQL_S_SELECT_FROM:
					lists.add(list = new ArrayList());
					context = 0;
					break;
				case ASQL_S_SELECT_FROM_TABLE:
					if (token == ASQL_T_END) // �Դ���������崦��
						list.add("WHERE");
					break label_parse_end; // select���͹�Լ�ɹ�

				// �����������޷�����into����μ����������޷�����values
				case ASQL_S_INSERT:
					lists.add(list = new ArrayList());
					direct = 1;
					context = 0;
					level = 0;
					limit = top;
					p = lp + 1;
					break;
				case ASQL_S_INSERT_VALUES:
					break label_parse_end;

				// �����������޷�����values����μ����������޷�����into������������޷���������
				case ASQL_S_VALUES:
					lists.add(list = new ArrayList());
					lqcount = qcount;
					break;
				case ASQL_S_VALUES_INTO:
					lists.add(list = new ArrayList());
					direct = 1;
					context = 0;
					level = 0;
					top = 0;
					limit = 1;
					p = lp + 1;
					qcount = 0;
					break;
				case ASQL_S_VALUES_INTO_OK:
					rqcount = qcount;
					lists.add(new Integer(lqcount));
					lists.add(new Integer(rqcount));
					break label_parse_end;
				}
			} else {
				if (token == ASQL_T_END)
					throw new FrameException(-92010002, "ASQL�﷨����,����δ�ڵ���ֹ��");
			}

			s = tos;
		}

		return tos;
	}

	/**
	 * ������int asql__lexical(StringBuffer asql, int direct, int[] ap,
	 * StringBuffer tkbuf )
	 * 
	 * �ʷ�����
	 * 
	 * ����������൱�ڴ�sql�﷨���дӵ�ǰ����򸸡��ַ���������1��㣬�����ص�ǰ�������1���֮����ı���
	 * ���ҷ������൱�ڴ�sql�﷨���дӵ�ǰ����򸸡��ܷ���������1��㣬�����ص�ǰ�������1���֮����ı���
	 * 
	 * ���磺 �Դ� "select a.user_id,nvl(max(a.user_seq)+1,0) user_seq,to_char(a. "
	 * ������������������Ϊ�� "to_char(a.", "nvl(max(a.user_seq)+1,0) user_seq",
	 * "a.user_id", "select"
	 * 
	 * ������ Ϊ�򻯷�������������ֵ�� level(����)��¼��ǰλ�����﷨���еĲ�Σ���ʼΪ0��������������У�����1��")"�����1��
	 * top(��߼�)��¼��ʷ�������Ѵ����߲�Σ���ʼΪ0��
	 * limit(���Ƽ�)��¼��ǰ�����ɴ�����Σ������޶�������Χ��������Ӳ�ѯ�ķ������ܿ�������ѯ��Χ��
	 * context(������)��¼��ǰλ�õ������Ļ�����=0��ʾ��sql���б���(��','�ָ�)��=1��ʾ��sql����(��' \n\r\t'�ָ�)
	 * ���������У�������������ֵ�������ʡ�
	 * 
	 */

	private static final int // ������������±�
			ASQL_I_P = 0,
			ASQL_I_LEVEL = 1, ASQL_I_TOP = 2,
			ASQL_I_LIMIT = 3,
			ASQL_I_CONTEXT = 4, ASQL_I_QCOUNT = 5;

	private static int asql__lexical(StringBuffer asql, int direct, int[] ap,
			StringBuffer tkbuf) {
		final int BIT_SPLIT = 0x01, // ��ǰ�ַ�֮ǰ�ָ�
		BIT_APPEND = 0x02, // ��ǰ�ַ����뻺��
		BIT_LEVEL_UP = 0x04, // ����-1
		BIT_LEVEL_DOWN = 0x08, // ����+1
		BIT_END = 0x10; // ����

		int p = ap[ASQL_I_P], // ��ʼ�ַ�λ��
		level = ap[ASQL_I_LEVEL], // ��ǰ����
		top = ap[ASQL_I_TOP], // ��ʷ��߼���
		limit = ap[ASQL_I_LIMIT], // ���Ƽ�
		context = ap[ASQL_I_CONTEXT], // �����ģ����ֵ�ǰ����λ�ã�0=�б�,1=sql���
		qcount = ap[ASQL_I_QCOUNT]; // �ʺŸ���

		char c;
		int bits = 0;
		boolean quota = false;
		String tkstr = "";
		StringBuffer tkstrbuf = new StringBuffer(); // ��������

		while (p >= 0 && p < asql.length()) {
			c = asql.charAt(p);
			tkstr = "";
			bits = 0;

			if (!quota) // ������״̬
			{
				switch (c) {
				case '\'':
					bits |= BIT_APPEND;
					quota = true;
					break;

				case ',':
					bits |= ((level <= top) ? BIT_SPLIT : BIT_APPEND);
					break;

				case ' ':
				case '\r':
				case '\n':
				case '\t':
					if ((level <= top) && (context == 0)
							&& (asql__lexical_context(asql, direct, p) < 0)) {// ������ǰ������Ϊ���ʽ�б�,��Ҫǰ���ؼ��־�����һ������
						context = -1;
						limit = top;
					}
					bits |= ((level <= top) && (context < 0)) ? BIT_SPLIT
							: BIT_APPEND;
					break;

				case '(':
					if (direct < 0) {
						if ((level <= top) && (limit <= 0)) {// limit���ڴ��������Ʒ�����Χ
							bits |= BIT_SPLIT | BIT_END;
						} else if ((level <= top + 1)
								&& (asql__lexical_context(asql, direct, p) < 0)) {// �ζ�����Ҫǰ���Ƿ�ؼ��־�����һ������
							context = -1;
							limit = top - 1;
							bits |= BIT_LEVEL_UP | BIT_APPEND | BIT_SPLIT;
						} else {
							bits |= BIT_LEVEL_UP | BIT_APPEND;
						}
					} else {
						bits |= BIT_LEVEL_DOWN | BIT_APPEND;
					}
					break;

				case ')':
					if (direct > 0) {
						if ((level <= top) && (limit <= 0)) {// limit���ڴ��������Ʒ�����Χ
							bits |= BIT_SPLIT | BIT_END;
						} else if ((level <= top + 1)
								&& (asql__lexical_context(asql, direct, p) < 0)) {// �ζ�����Ҫǰ���Ƿ�ؼ��־�����һ������
							context = -1;
							limit = top - 1;
							bits |= BIT_LEVEL_UP | BIT_APPEND | BIT_SPLIT;
						} else {
							bits |= BIT_LEVEL_UP | BIT_APPEND;
						}
					} else {
						bits |= BIT_LEVEL_DOWN | BIT_APPEND;
					}
					break;

				default:
					if ((level <= top) && (context < 0)) {// �����ؼ�����Ҫǰ���Ƿ�ָ���
						if ((p > 0) && (p < asql.length() - 1))
							if (ASQL_SPLITS.indexOf(asql.charAt(p + direct)) >= 0)
								bits |= BIT_SPLIT;
					}
					bits |= BIT_APPEND;
					if (c == '?')
						qcount++;
				}
			} else // ����״̬
			{
				switch (c) {
				case '\'': // ǰ��1���ַ��жϵ�ǰ�ַ��Ƿ�Ϊ����ת���
					if (direct < 0) {
						if ((p > 0) && (asql.charAt(p - 1) == '\''))
							tkstrbuf.append(asql.charAt(p--));
						else
							quota = false;
					} else {
						if ((p < asql.length() - 1)
								&& (asql.charAt(p + 1) == '\''))
							tkstrbuf.append(asql.charAt(p++));
						else
							quota = false;
					}

				default:
					bits |= BIT_APPEND;
				}
			}

			if ((bits & BIT_APPEND) != 0)
				tkstrbuf.append(c);
			if ((bits & BIT_SPLIT) != 0)
				tkstr = asql__lexical_flush(tkstrbuf, direct);
			if ((bits & BIT_LEVEL_UP) != 0)
				level--;
			if ((bits & BIT_LEVEL_DOWN) != 0)
				level++;
			top = ((level < top) ? level : top);

			if ((bits & BIT_END) != 0)
				break;
			else
				p += direct;

			if (!tkstr.equals(""))
				break;
		}

		if (tkstr.equals("")) // ������Χ��ȡ����
			tkstr = asql__lexical_flush(tkstrbuf, direct);

		int token = ASQL_T;
		for (int i = 0; (i < ASQL_TKS.length) && (token == ASQL_T); i++)
			// ���Ȳ�token�����
			if (tkstr.equalsIgnoreCase(ASQL_TKS[i]))
				token = ASQL_TOKENS[i];

		if (token == ASQL_T) // ��ο��Ƿ��������
			if (tkstr.equals(""))
				token = ASQL_T_END;

		// ���ؽ��
		ap[ASQL_I_P] = p;
		ap[ASQL_I_LEVEL] = level;
		ap[ASQL_I_TOP] = top;
		ap[ASQL_I_LIMIT] = limit;
		ap[ASQL_I_CONTEXT] = context;
		ap[ASQL_I_QCOUNT] = qcount;
		tkbuf.setLength(0);
		tkbuf.append(tkstr);

		return token;
	}

	/**
	 * ǰ�������ַ��ж�������
	 */
	private static int asql__lexical_context(StringBuffer asql, int direct,
			int p) {
		int context = 0, p0, p1;
		for (int j = 0; j < ASQL_TKS.length; j++) {
			String tkkey = ASQL_TKS[j];
			if (direct < 0) {
				p1 = p;
				p0 = p1 - tkkey.length();
				if (p0 < 0)
					continue;
				if ((p0 > 0) && (ASQL_SPLITS.indexOf(asql.charAt(p0 - 1)) < 0))
					continue;
			} else {
				p0 = p + 1;
				p1 = p0 + tkkey.length();
				if (p1 > asql.length())
					continue;
				if ((p1 < asql.length())
						&& (ASQL_SPLITS.indexOf(asql.charAt(p1)) < 0))
					continue;
			}

			if (asql.substring(p0, p1).equalsIgnoreCase(tkkey)) {
				context = -1;
				break;
			}
		}
		return context;
	}

	/**
	 * �������������
	 */
	private static String asql__lexical_flush(StringBuffer tkstrbuf, int direct) {
		String tkstr;
		if (direct < 0)
			tkstr = tkstrbuf.reverse().toString().trim();
		else
			tkstr = tkstrbuf.toString().trim();

		tkstrbuf.setLength(0);
		return tkstr;
	}

	/**
	 * 
	 * ������String[] asql__item_split(String in)
	 * 
	 * ���ܣ����� item -> expr | expr name ,���� String[2]{name,expr}
	 * 
	 * ���磺 �����ַ��� ����ַ������� "nvl(max(a.user_seq)+1,0) user_seq"
	 * "nvl(max(a.user_seq)+1,0)", "user_seq" "a.user_id " "a.user_id" ,
	 * "user_id" "user_id" "user_id" , "user_id"
	 * 
	 * ������ ���������item�� ����a-z,A-Z,.,_,0-9 �������name״̬ ���������ָ��� ����ȷ��name״̬
	 * ����a-z,A-Z,_,0-9,) ��Ϊexpr name���ͷ���name�����������ַ�����Ϊexpr���ͷ���expr
	 * 
	 * ����name ����Ƿ�����ĸ��ͷ���Ƿ������
	 * 
	 */

	private static String asql__item_name(String in) {
		return asql__item_split(in)[0];
	}

	private static String asql__item_expr(String in) {
		return asql__item_split(in)[1];
	}

	private static String[] asql__item_split(String in) {
		final int S_INIT = 0, S_ACCEPTING = 1, S_CHECKING = 2; // ��ʼ״̬,����name״̬,ȷ��name״̬

		String name, expr;
		int p = 0, pl = in.length(), s = S_INIT;

		for (p = in.length() - 1; p >= 0; p--) {
			char c = in.charAt(p);

			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				if (s == S_INIT)
					continue;
				else
					s = S_CHECKING;
			} else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
					|| (c >= '0' && c <= '9') || c == '_' || c == '.'
					|| c == '\'' || c == '?') {
				if (s == S_INIT) {
					pl = p;
					s = S_ACCEPTING;
				} else if (s == S_ACCEPTING) {
					pl = p;
				} else {
					break;
				}
			} else if (c == ')') {
				break;
			} else {
				pl = in.length();
				break;
			}
		}

		if ((pl > 0) && pl < in.length()) {
			name = in.substring(pl).trim();
			expr = in.substring(0, pl).trim();
			char c = name.charAt(0);
			if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && c != '_') // ���name��ʼ�ַ�����
			{
				name = in.trim();
				expr = in.trim();
			} else if (name.lastIndexOf('.') >= 0) // ���name���ܰ���'.'
			{
				name = name.substring(name.lastIndexOf('.') + 1, name.length())
						.trim();
				expr = in.trim();
			}
		} else {
			name = in.trim();
			expr = in.trim();
		}

		return new String[] { name, expr };
	}

	/**
	 * ���崦��
	 * 
	 * ������ asql__build_select ���ڴ���select����
	 * 
	 * ���崦������룬���﷨�������������������ɵ�sql�͵������İ󶨲����б�
	 * 
	 */
	private static void asql__build_select(Connection conn, StringBuffer asql,
			ArrayList apara, int lp, int rp, ArrayList lists)
			throws FrameException {
		String tkstr;
		ArrayList list;

		String lfun = "", rfun = "", table_name = "", alia_name = "";
		HashSet h_col_list = new HashSet();
		HashMap h_tab_list = new HashMap();

		// 1������������
		for (int i = 0; i < 3; i++) {
			list = (ArrayList) lists.get(i);
			for (int j = list.size() - 2; j >= 0; j--) {
				tkstr = (String) list.get(j);

				if (i == 0)// ��Χ��չ���ķ�����ǰ�������б�
				{
					if ((j == 0)
							&& tkstr.toUpperCase().endsWith(
									'$' + ASQL_CMD_COLUMNS.toUpperCase())) // ��Χ��չ��
						lfun = tkstr.substring(0, tkstr.length()
								- ASQL_CMD_COLUMNS.length() - 1);
					else
						// ��
						h_col_list.add(asql__item_name(tkstr).toUpperCase());
				} else if (i == 1) // �󲿷����б�
				{
					if ((j == 0)
							&& tkstr.toUpperCase().startsWith(
									'$' + ASQL_CMD_COLUMNS.toUpperCase())) // ��Χ��չ��
						rfun = tkstr.substring(ASQL_CMD_COLUMNS.length() + 1);
					else
						// ��
						h_col_list.add(asql__item_name(tkstr).toUpperCase());
				} else if ((i == 2)) // �����б�
				{
					table_name = asql__item_expr(tkstr);
					h_tab_list.put(asql__item_name(tkstr).toUpperCase(),
							table_name);
				}
			}
		}

		if (table_name.equals(""))
			throw new FrameException(-92010011, "ASQL�﷨����δ��ȷ�ı���");

		if (lfun.trim().endsWith(".")) // �����
		{
			alia_name = lfun.substring(0, lfun.lastIndexOf(".")).trim();
			for (int j = lfun.lastIndexOf(".") - 1; j >= 0; j--) {
				char c = lfun.charAt(j);
				if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')
						&& (c < '0' || c > '9') && c != '_') // ��������ɹ���
				{
					alia_name = lfun.substring(j + 1, lfun.length() - 1);
					break;
				}
			}
		}

		if (!alia_name.equals("")) // �����������
			table_name = (String) h_tab_list.get(alia_name.toUpperCase());
		if (table_name == null)
			throw new FrameException(-92010012, "ASQL�﷨����δ��ȷ�ı���");

		if (!lfun.equals(""))
			lp = asql.toString().lastIndexOf(lfun, lp) - 1;
		if (!rfun.equals(""))
			rp = asql.toString().indexOf(rfun, rp) + rfun.length();

		// 2������asql
		StringBuffer columns = new StringBuffer();
		String sql = "select * from " + table_name + " where rownum <1";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			ResultSetMetaData rm = rs.getMetaData();

			for (int j = 1; j <= rm.getColumnCount(); j++) {
				String nam = rm.getColumnName(j);
				int typ = rm.getColumnType(j);

				if (h_col_list.contains(nam))
					continue;

				if (columns.length() != 0)
					columns.append(',');

				String exp = lfun + nam + rfun;

				{// ��ֱ�Ӱ�Χ�����͵�to_char�������ر�������yyyy-mm-dd hh24:mi:ss
					int pos = lfun.lastIndexOf("(");
					if (pos > 0)
						if (lfun.substring(0, pos).toUpperCase().endsWith(
								"TO_CHAR"))
							if (rfun.trim().startsWith(")"))
								if ((typ == Types.TIMESTAMP)
										|| (typ == Types.DATE)
										|| (typ == Types.TIME))
									exp = lfun + nam
											+ ",'yyyy-mm-dd hh24:mi:ss'" + rfun;
								else
									exp = lfun.substring(0, pos
											- "TO_CHAR".length())
											+ lfun.substring(pos + 1)
											+ nam
											+ rfun.trim().substring(1);
				}

				if (!exp.equals(nam))
					columns.append(exp + " " + nam);
				else
					columns.append(nam);
			}
		} catch (SQLException e) {
			throw new FrameException(-92010013, "ASQL��������:�������ֶ�ʧ��:"
					+ table_name);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Throwable ee) {
				}
			if (st != null)
				try {
					st.close();
				} catch (Throwable ee) {
				}
		}

		asql__write_result(asql, lp, rp, columns);

		return;
	}

	/**
	 * ���崦��
	 * 
	 * ������ asql__build_insert ���ڴ���insert����
	 * 
	 * ���崦������룬���﷨�������������������ɵ�sql�͵������İ󶨲����б�
	 * 
	 */
	private static void asql__build_insert(Connection conn, StringBuffer asql,
			ArrayList apara, int lp, int rp, ArrayList lists)
			throws FrameException {
		String tkstr;
		ArrayList list;

		String table_name = "";
		HashSet h_col_list = new HashSet();

		check_point_1: for (int i = 0; i < 2; i++) {
			list = (ArrayList) lists.get(i);
			{
				for (int j = list.size() - 2; j >= 0; j--) {
					tkstr = (String) list.get(j);
					if (i == 0)// ���
					{
						if (j == list.size() - 2) // ������0-1���ֶ�
						{
							if (tkstr.toUpperCase().endsWith(
									'$' + ASQL_CMD_COLUMNS.toUpperCase()))
								tkstr = tkstr.substring(0, tkstr.length()
										- ASQL_CMD_COLUMNS.length() - 1);

							int split = tkstr.indexOf('(');
							if (split < 0)
								throw new FrameException(-92010021, "ASQL�﷨����");
							table_name = asql__item_expr(tkstr.substring(0,
									split).trim()); // ����

							String col = tkstr.substring(split + 1).trim();
							if (!col.equals(""))
								h_col_list.add(tkstr.substring(split + 1)
										.trim().toUpperCase()); // ��1��
						} else {
							h_col_list.add(tkstr.trim().toUpperCase()); // ������
						}
					} else// �Ҳ�
					{
						if (j == list.size() - 2)
							if (tkstr.toUpperCase().startsWith(
									'$' + ASQL_CMD_COLUMNS.toUpperCase()))
								tkstr = tkstr.substring(ASQL_CMD_COLUMNS
										.length() + 1);

						StringTokenizer st = new StringTokenizer(tkstr, "(),");
						while (st.hasMoreTokens())
							h_col_list.add(st.nextToken().trim().toUpperCase());
					}

					if (j == 1)
						break;
				}
			}
		}

		// 2������asql
		StringBuffer columns = new StringBuffer();
		String sql = "select * from " + table_name + " where rownum <1";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			ResultSetMetaData rm = rs.getMetaData();

			for (int j = 1; j <= rm.getColumnCount(); j++) {
				String nam = rm.getColumnName(j);
				int typ = rm.getColumnType(j);

				if (h_col_list.contains(nam))
					continue;

				if (columns.length() != 0)
					columns.append(',');

				columns.append(nam);
			}
		} catch (SQLException e) {
			throw new FrameException(-92010022, "ASQL��������:�������ֶ�ʧ��:"
					+ table_name);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Throwable ee) {
				}
			if (st != null)
				try {
					st.close();
				} catch (Throwable ee) {
				}
		}

		asql__write_result(asql, lp, rp, columns);

		return;
	}

	/**
	 * ���崦��
	 * 
	 * ������ asql__build_values ���ڴ���values����
	 * 
	 * ���崦������룬���﷨�������������������ɵ�sql�͵������İ󶨲����б�
	 * 
	 */
	private static void asql__build_values(Connection conn, StringBuffer asql,
			ArrayList apara, int lp, int rp, ArrayList lists)
			throws FrameException {
		String tkstr;
		ArrayList list;

		String table_name = "";
		int lcols = 0, rcols = 0;
		String lfun = "", rfun = "";
		ArrayList a_col_list = new ArrayList();

		// ����?��
		int rqcount = ((Integer) lists.get(lists.size() - 1)).intValue(); // �Ҳ�?����
		int lqcount = ((Integer) lists.get(lists.size() - 2)).intValue(); // ���?����

		// �������
		list = (ArrayList) lists.get(0);
		tkstr = (String) list.get(list.size() - 2);
		lcols = list.size() - 2;
		tkstr = (String) list.get(0);
		if (tkstr.toUpperCase().endsWith('$' + ASQL_CMD_COLUMNS.toUpperCase())) // ��Χ��չ��
			if (lcols > 0)
				lfun = tkstr.substring(0, tkstr.length()
						- ASQL_CMD_COLUMNS.length() - 1);
			else
				lfun = tkstr.substring(tkstr.indexOf('(') + 1, tkstr.length()
						- ASQL_CMD_COLUMNS.length() - 1);

		// ���������б�
		list = (ArrayList) lists.get(1);
		table_name = (String) list.get(list.size() - 2);

		if (list.size() > 2)// ָ�����л����
		{
			String cols = (String) list.get(0);
			int pos = cols.indexOf('(');
			if (cols.indexOf('(') >= 0) // ����
			{
				if (pos > 0)
					cols = cols.substring(pos);
				StringTokenizer st = new StringTokenizer(cols, "(),");
				while (st.hasMoreTokens())
					a_col_list.add(st.nextToken().trim().toUpperCase());
			}
		}

		HashMap htype = new HashMap();
		{
			boolean load = a_col_list.isEmpty();
			String sql = "select * from " + table_name + " where rownum <1";
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				ResultSetMetaData rm = rs.getMetaData();

				for (int j = 1; j <= rm.getColumnCount(); j++) {
					if (load) // δָ���е�Ҫ������
						a_col_list.add(rm.getColumnName(j));
					htype.put(rm.getColumnName(j), new Integer(rm
							.getColumnType(j)));
				}
			} catch (SQLException e) {
				throw new FrameException(-92010031, "ASQL��������:�������ֶ�ʧ��:"
						+ table_name);
			} finally {
				if (rs != null)
					try {
						rs.close();
					} catch (Throwable ee) {
					}
				if (st != null)
					try {
						st.close();
					} catch (Throwable ee) {
					}
			}
		}

		// �Ҳ�����
		list = (ArrayList) lists.get(2);
		rcols = list.size() - 1;
		tkstr = (String) list.get(0);
		if (tkstr.toUpperCase()
				.startsWith('$' + ASQL_CMD_COLUMNS.toUpperCase())) // ��Χ��չ��
			if (rcols > 0)
				rfun = tkstr.substring(ASQL_CMD_COLUMNS.length() + 1);
			else
				rfun = tkstr.substring(ASQL_CMD_COLUMNS.length() + 1, tkstr
						.lastIndexOf(')'));

		if (!lfun.equals(""))
			lp = asql.toString().lastIndexOf(lfun, lp) - 1;
		if (!rfun.equals(""))
			rp = asql.toString().indexOf(rfun, rp) + rfun.length();

		// ����SQL
		StringBuffer columns = new StringBuffer();
		int j = lqcount;
		for (int i = lcols; i < a_col_list.size() - rcols; i++) {
			String nam = (String) a_col_list.get(i);
			int typ = (htype.get(nam) == null ? Types.CHAR : ((Integer) htype
					.get(nam)).intValue());

			apara.add(j++, nam);

			if (i > lcols)
				columns.append(",");

			String exp = lfun + '?' + rfun;
			{// ��ֱ�Ӱ�Χ�����͵�to_char�������ر�������yyyy-mm-dd hh24:mi:ss
				if ((typ == Types.TIMESTAMP) || (typ == Types.DATE)
						|| (typ == Types.TIME))
					exp = lfun + "TO_DATE(?,'yyyy-mm-dd hh24:mi:ss')" + rfun;
			}

			columns.append(exp);
		}

		asql__write_result(asql, lp, rp, columns);

		return;

	}

	private static void asql__write_result(StringBuffer asql, int lp, int rp,
			StringBuffer columns) {
		if (columns.length() == 0) {
			boolean comma = false; // columnsΪ��ʱ���������󡢻��ҳԵ�1������
			for (int i = lp; i >= 0; i--) {
				char c = asql.charAt(i);
				if ((c != '\n') && (c != '\r') && (c != '\t') && (c != ' ')) {
					if (c == ',') {
						lp = i - 1;
						comma = true;
					}
					break;
				}
			}

			if (!comma) {
				for (int i = rp; i < asql.length(); i++) {
					char c = asql.charAt(i);
					if ((c != '\n') && (c != '\r') && (c != '\t') && (c != ' ')) {
						if (c == ',') {
							rp = i + 1;
							comma = true;
						}
						break;
					}
				}
			}
		}

		asql.delete(lp + 1, rp);
		asql.insert(lp + 1, columns);
	}
	

}
