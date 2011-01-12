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
 * SQL词法解释处理器
 * 重构至Base.java
 *
 */
public class SQLResolver {
	private SQLResolver(){
		
	}

	/***************************************************************************
	 * 
	 * PART-A-0
	 * 
	 * ASQL解析方法集
	 * 
	 * 描述： 对asql串中的扩展键，进行分析，返回生成的sql及参数列表
	 * 
	 * 处理过程： 对输入的sql搜索扩展键位置，对扩展键进行语法解析形成结果矩阵，将结果矩阵语义处理，生成 目标sql及绑定参数列表
	 * 
	 * 用法：支持3种句型场景
	 * 
	 * 1。在select的表达式列表中： SELECT [Exprs,] [expr_part]$columns[expr_part] [,Exprs]
	 * FROM Table_list 其中，expr_part表示包围$columns的表达式片段 注意：
	 * (1)$columns左测的表达失片段不能包含','，因为分析方法向左是1次遍历无法区分，但是右侧没问题；
	 * 因为左侧分析之后已经得到分析范围信息(如要支持则要在分析前先向左编历1次确定范围)；
	 * (2)如果包围$columns的是to_char方法，则对日期型自动加'yyyy-mm-dd hh24:mi:ss'格式；
	 * (3)如果$columns位于子查询中，则只影响该子查询，不影响外围查询； (4)如果1个语句中包含多个$columns，则从前到后顺序解析；
	 * 例如： select param_value,$columns from tsm_paramater，解析成： select
	 * param_value,param_id,param_desc from tsm_paramater
	 * 
	 * select a.param_value,to_char(a.$columns)||'123' from tsm_paramater
	 * a,tvlsm_bureau b 解析成： select a.param_value,to_char(a.param_id)||'123'
	 * param_id,to_char(a.param_desc)||'123' param_desc from tsm_paramater
	 * a,tvlsm_bureau b
	 * 
	 * 2。在insert into的列列表中： INSERT INTO Table_name [Alia] ([Cols,] $columns
	 * [Cols]) 其中，Cols为$columns两侧的指定列名 注意：
	 * (1)Table_name及Alia与(号之间必须有空格，因为分析方法向左1次遍历不足以分辨(号之前是否要分隔； 例如： insert into
	 * tsm_paramater a (param_value, $columns) values (?) 解析成； insert into
	 * tsm_paramater a(param_value,param_id,param_desc) values (?,?,?)
	 * 并且将参数名param_id,param_desc加到apara数组的适当位置
	 * 
	 * 3。在insert into values的绑定参数列表中： INSERT INTO Table_name [Alia] [(Cols)]
	 * Values ([Exprs,] [expr_part]$columns[expr_part] [,Exprs])
	 * 其中，expr_part为$columns两侧的表达式片段 注意：
	 * (1)Table_name及Alia与(号之间必须有空格，因为分析方法向左1次遍历不足以分辨(号之前是否要分隔；
	 * (2)$columns可以包围在表达式中，但表达式内左侧不能有','号；
	 * (3)对日期型的字段，由$columns生成的?自动加上to_date('yyyy-mm-dd hh24:mi:ss')格式 例如： insert
	 * into tsm_paramater a (param_value, $columns) values (?) 解析成； insert into
	 * tsm_paramater a (param_value,param_id,param_desc) values (?,?,?)
	 * 并且将参数名param_id,param_desc加到apara数组的适当位置
	 * 
	 * 报错信息： -9201000* 分析错误 -9201001* 构造select过程错误 -9201002* 构造into_values过程错误
	 * -9201003* 构造values过程错误
	 * 
	 **************************************************************************/

	//========================================  asql__parse ======================================================
	// 解析包含扩展键的ASQL，返回解析后的SQL
	public static String asql__parse(String sql) throws FrameException {
		StringBuffer asql = new StringBuffer(sql);
		asql__parse(ConnectionContext.getContext().getConnection(), asql, null);

		return asql.toString();

	}

	// 解析包含扩展键的ASQL，返回解析后的SQL及参数列表，适用于需要对ASQL生成?绑定参数时
	public static String asql__parse(String sql, ArrayList apara)
			throws FrameException {
		StringBuffer asql = new StringBuffer(sql);
		asql__parse(ConnectionContext.getContext().getConnection(), asql, apara);

		return asql.toString();
	}

	private static final String ASQL_CMD_COLUMNS = "COLUMNS"; // 定义扩展键

	private static void asql__parse(Connection conn, StringBuffer asql,
			ArrayList apara) throws FrameException {
		ArrayList lists = new ArrayList(); // 保存解析结果矩阵
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
							throw new FrameException(-92010001, "ASQL语法错误");
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

	private static final String // 分隔符
	ASQL_SPLITS = " ,\n\r\t()";

	private static final String[] ASQL_TKS = // 识别关键字
	{ "SELECT", "DISTINCT", "FROM", "WHERE", "GROUP", "HAVING", "UNION",
			"ORDER", "INSERT", "INTO", "VALUES", };

	private static final int // 词法token
			ASQL_T_SELECT = 1,
			ASQL_T_DISTINCT = 2, ASQL_T_FROM = 3,
			ASQL_T_WHERE = 4,
			ASQL_T_GROUP = 5, ASQL_T_HAVING = 6,
			ASQL_T_UNION = 7,
			ASQL_T_ORDER = 8, ASQL_T_INSERT = 9,
			ASQL_T_INTO = 10,
			ASQL_T_VALUES = 11, ASQL_T = 0, ASQL_T_END = 99;

	private static final int[] ASQL_TOKENS = // 与ASQL_TKS数组的顺序要一致
	{ ASQL_T_SELECT, ASQL_T_DISTINCT, ASQL_T_FROM, ASQL_T_WHERE, ASQL_T_GROUP,
			ASQL_T_HAVING, ASQL_T_UNION, ASQL_T_ORDER, ASQL_T_INSERT,
			ASQL_T_INTO, ASQL_T_VALUES, ASQL_T, ASQL_T_END, };

	private static final int // 语法状态
			ASQL_S = 0,
			ASQL_S_SELECT = 1,
			ASQL_S_SELECT_FROM = 2,
			ASQL_S_SELECT_FROM_TABLE = 3,
			ASQL_S_INSERT = 4,
			ASQL_S_INSERT_VALUES = 5,
			ASQL_S_VALUES = 6,
			ASQL_S_VALUES_INTO = 7, ASQL_S_VALUES_INTO_OK = 8;

	private static final int // 语法状态GOTO表的列定义
			ASQL_GOTO_S = 0, // 源状态
			ASQL_GOTO_D = 1, // 源方向
			ASQL_GOTO_T = 2, // token
			ASQL_GOTO_TOS = 3; // 目的状态

	private static final int[][] ASQL_GOTO = // 语法状态GOTO表，只记录发生跳变的状态
	{
			// 源状态 源方向 token 目的状态
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
	 * 语法状态转换方法
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
	 * 方法：int asql__parse(StringBuffer asql, int lp, int rp, ArrayList lists)
	 * 
	 * 局部语法解析
	 * 
	 * 描述： 对asql中的指定扩展键，向2端分析，并形成分析结果，保存在lists矩阵中
	 * 
	 * 入参： asql,apara,lp,rp lists 返回分析结果词矩阵
	 * 
	 */
	private static int asql__parse_a_key(StringBuffer asql, int lp, int rp,
			ArrayList lists) throws FrameException {
		ArrayList list;
		lists.clear();
		lists.add(list = new ArrayList());

		int[] ap = new int[6]; // 用于向词法分析传递参数
		int level = 0, top = 0, context = 0, direct = -1, limit = 1;
		int token = 0, p = rp - 1, s = ASQL_S, tos = s;
		int qcount = 0, lqcount = 0, rqcount = 0;
		StringBuffer tkbuf = new StringBuffer();

		label_parse_end: for (;;) {
			// 词法取词
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

			// 查状态转换表
			tos = asql__goto(s, direct, token);

			if (tos != s) // 状态跃迁
			{
				switch (tos) {
				// 首先向左无限分析至select，其次继续向右有限分析至from，最后向右有限分析至表名结束
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
					if (token == ASQL_T_END) // 对此情况简化语义处理
						list.add("WHERE");
					break label_parse_end; // select句型归约成功

				// 首先向左无限分析至into，其次继续向右有限分析至values
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

				// 首先向左无限分析至values，其次继续向左有限分析至into，最后向右无限分析至结束
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
					throw new FrameException(-92010002, "ASQL语法错误,遇到未期的终止符");
			}

			s = tos;
		}

		return tos;
	}

	/**
	 * 方法：int asql__lexical(StringBuffer asql, int direct, int[] ap,
	 * StringBuffer tkbuf )
	 * 
	 * 词法分析
	 * 
	 * 向左分析，相当于从sql语法树中从当前结点向父、兄方向搜索下1结点，并返回当前结点至下1结点之间的文本；
	 * 向右分析，相当于从sql语法树中从当前结点向父、弟方向搜索下1结点，并返回当前结点至下1结点之间的文本；
	 * 
	 * 例如： 对串 "select a.user_id,nvl(max(a.user_seq)+1,0) user_seq,to_char(a. "
	 * 向左分析，则出词序列为： "to_char(a.", "nvl(max(a.user_seq)+1,0) user_seq",
	 * "a.user_id", "select"
	 * 
	 * 描述： 为简化分析，引入特征值： level(级别)记录当前位置在语法树中的层次，初始为0，例如向左分析中，经过1个")"则减少1；
	 * top(最高级)记录历史分析中已达的最高层次，初始为0；
	 * limit(限制级)记录当前分析可达的最层次，用于限定分析范围，例如对子查询的分析不能跨入主查询范围；
	 * context(上下文)记录当前位置的上下文环境，=0表示在sql列列表中(以','分割)，=1表示在sql句中(以' \n\r\t'分割)
	 * 分析过程中，根据上列特征值决定出词。
	 * 
	 */

	private static final int // 定义入参数组下标
			ASQL_I_P = 0,
			ASQL_I_LEVEL = 1, ASQL_I_TOP = 2,
			ASQL_I_LIMIT = 3,
			ASQL_I_CONTEXT = 4, ASQL_I_QCOUNT = 5;

	private static int asql__lexical(StringBuffer asql, int direct, int[] ap,
			StringBuffer tkbuf) {
		final int BIT_SPLIT = 0x01, // 当前字符之前分割
		BIT_APPEND = 0x02, // 当前字符加入缓冲
		BIT_LEVEL_UP = 0x04, // 级别-1
		BIT_LEVEL_DOWN = 0x08, // 级别+1
		BIT_END = 0x10; // 结束

		int p = ap[ASQL_I_P], // 开始字符位置
		level = ap[ASQL_I_LEVEL], // 当前级别
		top = ap[ASQL_I_TOP], // 历史最高级别
		limit = ap[ASQL_I_LIMIT], // 限制级
		context = ap[ASQL_I_CONTEXT], // 上下文，区分当前语义位置，0=列表,1=sql语句
		qcount = ap[ASQL_I_QCOUNT]; // 问号个数

		char c;
		int bits = 0;
		boolean quota = false;
		String tkstr = "";
		StringBuffer tkstrbuf = new StringBuffer(); // 分析缓冲

		while (p >= 0 && p < asql.length()) {
			c = asql.charAt(p);
			tkstr = "";
			bits = 0;

			if (!quota) // 非引号状态
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
							&& (asql__lexical_context(asql, direct, p) < 0)) {// 顶级当前上下文为表达式列表,需要前看关键字决定下一上下文
						context = -1;
						limit = top;
					}
					bits |= ((level <= top) && (context < 0)) ? BIT_SPLIT
							: BIT_APPEND;
					break;

				case '(':
					if (direct < 0) {
						if ((level <= top) && (limit <= 0)) {// limit仅在此用于限制分析范围
							bits |= BIT_SPLIT | BIT_END;
						} else if ((level <= top + 1)
								&& (asql__lexical_context(asql, direct, p) < 0)) {// 次顶级需要前看是否关键字决定下一上下文
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
						if ((level <= top) && (limit <= 0)) {// limit仅在此用于限制分析范围
							bits |= BIT_SPLIT | BIT_END;
						} else if ((level <= top + 1)
								&& (asql__lexical_context(asql, direct, p) < 0)) {// 次顶级需要前看是否关键字决定下一上下文
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
					if ((level <= top) && (context < 0)) {// 顶级关键字需要前看是否分隔符
						if ((p > 0) && (p < asql.length() - 1))
							if (ASQL_SPLITS.indexOf(asql.charAt(p + direct)) >= 0)
								bits |= BIT_SPLIT;
					}
					bits |= BIT_APPEND;
					if (c == '?')
						qcount++;
				}
			} else // 引号状态
			{
				switch (c) {
				case '\'': // 前看1个字符判断当前字符是否为串内转义符
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

		if (tkstr.equals("")) // 超出范围则取缓冲
			tkstr = asql__lexical_flush(tkstrbuf, direct);

		int token = ASQL_T;
		for (int i = 0; (i < ASQL_TKS.length) && (token == ASQL_T); i++)
			// 首先查token定义表
			if (tkstr.equalsIgnoreCase(ASQL_TKS[i]))
				token = ASQL_TOKENS[i];

		if (token == ASQL_T) // 其次看是否解析结束
			if (tkstr.equals(""))
				token = ASQL_T_END;

		// 返回结果
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
	 * 前看若干字符判断上下文
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
	 * 输出缓冲区内容
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
	 * 方法：String[] asql__item_split(String in)
	 * 
	 * 功能：解析 item -> expr | expr name ,返回 String[2]{name,expr}
	 * 
	 * 例如： 输入字符串 输出字符串数组 "nvl(max(a.user_seq)+1,0) user_seq"
	 * "nvl(max(a.user_seq)+1,0)", "user_seq" "a.user_id " "a.user_id" ,
	 * "user_id" "user_id" "user_id" , "user_id"
	 * 
	 * 描述： 自右向左读item： 遇到a-z,A-Z,.,_,0-9 进入接受name状态 继续遇到分隔符 进入确认name状态
	 * 遇到a-z,A-Z,_,0-9,) 认为expr name句型返回name，遇到其他字符则认为expr句型返回expr
	 * 
	 * 返回name 检查是否以字母开头，是否包含点
	 * 
	 */

	private static String asql__item_name(String in) {
		return asql__item_split(in)[0];
	}

	private static String asql__item_expr(String in) {
		return asql__item_split(in)[1];
	}

	private static String[] asql__item_split(String in) {
		final int S_INIT = 0, S_ACCEPTING = 1, S_CHECKING = 2; // 初始状态,接受name状态,确认name状态

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
			if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && c != '_') // 检查name启始字符规则
			{
				name = in.trim();
				expr = in.trim();
			} else if (name.lastIndexOf('.') >= 0) // 检查name不能包含'.'
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
	 * 语义处理
	 * 
	 * 描述： asql__build_select 用于处理select句型
	 * 
	 * 语义处理的输入，是语法解析结果矩阵，输出是生成的sql和调整过的绑定参数列表
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

		// 1：处理分析结果
		for (int i = 0; i < 3; i++) {
			list = (ArrayList) lists.get(i);
			for (int j = list.size() - 2; j >= 0; j--) {
				tkstr = (String) list.get(j);

				if (i == 0)// 包围扩展键的方法、前部分列列表
				{
					if ((j == 0)
							&& tkstr.toUpperCase().endsWith(
									'$' + ASQL_CMD_COLUMNS.toUpperCase())) // 包围扩展键
						lfun = tkstr.substring(0, tkstr.length()
								- ASQL_CMD_COLUMNS.length() - 1);
					else
						// 列
						h_col_list.add(asql__item_name(tkstr).toUpperCase());
				} else if (i == 1) // 后部分列列表
				{
					if ((j == 0)
							&& tkstr.toUpperCase().startsWith(
									'$' + ASQL_CMD_COLUMNS.toUpperCase())) // 包围扩展键
						rfun = tkstr.substring(ASQL_CMD_COLUMNS.length() + 1);
					else
						// 列
						h_col_list.add(asql__item_name(tkstr).toUpperCase());
				} else if ((i == 2)) // 表名列表
				{
					table_name = asql__item_expr(tkstr);
					h_tab_list.put(asql__item_name(tkstr).toUpperCase(),
							table_name);
				}
			}
		}

		if (table_name.equals(""))
			throw new FrameException(-92010011, "ASQL语法错误：未明确的表名");

		if (lfun.trim().endsWith(".")) // 查别名
		{
			alia_name = lfun.substring(0, lfun.lastIndexOf(".")).trim();
			for (int j = lfun.lastIndexOf(".") - 1; j >= 0; j--) {
				char c = lfun.charAt(j);
				if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')
						&& (c < '0' || c > '9') && c != '_') // 检查别名组成规则
				{
					alia_name = lfun.substring(j + 1, lfun.length() - 1);
					break;
				}
			}
		}

		if (!alia_name.equals("")) // 按别名查表名
			table_name = (String) h_tab_list.get(alia_name.toUpperCase());
		if (table_name == null)
			throw new FrameException(-92010012, "ASQL语法错误：未明确的表名");

		if (!lfun.equals(""))
			lp = asql.toString().lastIndexOf(lfun, lp) - 1;
		if (!rfun.equals(""))
			rp = asql.toString().indexOf(rfun, rp) + rfun.length();

		// 2。生成asql
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

				{// 对直接包围日期型的to_char方法做特别处理，加上yyyy-mm-dd hh24:mi:ss
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
			throw new FrameException(-92010013, "ASQL分析错误:分析表字段失败:"
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
	 * 语义处理
	 * 
	 * 描述： asql__build_insert 用于处理insert句型
	 * 
	 * 语义处理的输入，是语法解析结果矩阵，输出是生成的sql和调整过的绑定参数列表
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
					if (i == 0)// 左侧
					{
						if (j == list.size() - 2) // 表名及0-1个字段
						{
							if (tkstr.toUpperCase().endsWith(
									'$' + ASQL_CMD_COLUMNS.toUpperCase()))
								tkstr = tkstr.substring(0, tkstr.length()
										- ASQL_CMD_COLUMNS.length() - 1);

							int split = tkstr.indexOf('(');
							if (split < 0)
								throw new FrameException(-92010021, "ASQL语法错误");
							table_name = asql__item_expr(tkstr.substring(0,
									split).trim()); // 表名

							String col = tkstr.substring(split + 1).trim();
							if (!col.equals(""))
								h_col_list.add(tkstr.substring(split + 1)
										.trim().toUpperCase()); // 第1列
						} else {
							h_col_list.add(tkstr.trim().toUpperCase()); // 其他列
						}
					} else// 右侧
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

		// 2。生成asql
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
			throw new FrameException(-92010022, "ASQL分析错误:分析表字段失败:"
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
	 * 语义处理
	 * 
	 * 描述： asql__build_values 用于处理values句型
	 * 
	 * 语义处理的输入，是语法解析结果矩阵，输出是生成的sql和调整过的绑定参数列表
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

		// 两侧?数
		int rqcount = ((Integer) lists.get(lists.size() - 1)).intValue(); // 右侧?总数
		int lqcount = ((Integer) lists.get(lists.size() - 2)).intValue(); // 左侧?总数

		// 左侧列数
		list = (ArrayList) lists.get(0);
		tkstr = (String) list.get(list.size() - 2);
		lcols = list.size() - 2;
		tkstr = (String) list.get(0);
		if (tkstr.toUpperCase().endsWith('$' + ASQL_CMD_COLUMNS.toUpperCase())) // 包围扩展键
			if (lcols > 0)
				lfun = tkstr.substring(0, tkstr.length()
						- ASQL_CMD_COLUMNS.length() - 1);
			else
				lfun = tkstr.substring(tkstr.indexOf('(') + 1, tkstr.length()
						- ASQL_CMD_COLUMNS.length() - 1);

		// 表名及列列表
		list = (ArrayList) lists.get(1);
		table_name = (String) list.get(list.size() - 2);

		if (list.size() > 2)// 指定了列或别名
		{
			String cols = (String) list.get(0);
			int pos = cols.indexOf('(');
			if (cols.indexOf('(') >= 0) // 有列
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
					if (load) // 未指定列的要产生列
						a_col_list.add(rm.getColumnName(j));
					htype.put(rm.getColumnName(j), new Integer(rm
							.getColumnType(j)));
				}
			} catch (SQLException e) {
				throw new FrameException(-92010031, "ASQL分析错误:分析表字段失败:"
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

		// 右侧列数
		list = (ArrayList) lists.get(2);
		rcols = list.size() - 1;
		tkstr = (String) list.get(0);
		if (tkstr.toUpperCase()
				.startsWith('$' + ASQL_CMD_COLUMNS.toUpperCase())) // 包围扩展键
			if (rcols > 0)
				rfun = tkstr.substring(ASQL_CMD_COLUMNS.length() + 1);
			else
				rfun = tkstr.substring(ASQL_CMD_COLUMNS.length() + 1, tkstr
						.lastIndexOf(')'));

		if (!lfun.equals(""))
			lp = asql.toString().lastIndexOf(lfun, lp) - 1;
		if (!rfun.equals(""))
			rp = asql.toString().indexOf(rfun, rp) + rfun.length();

		// 生成SQL
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
			{// 对直接包围日期型的to_char方法做特别处理，加上yyyy-mm-dd hh24:mi:ss
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
			boolean comma = false; // columns为空时，必须向左、或右吃掉1个逗号
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
