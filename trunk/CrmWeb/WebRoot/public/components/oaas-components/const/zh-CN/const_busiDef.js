//int型的对应null值
var NULL_INT = -2147483648;

//long型的对应null值
var NULL_LONG = "-9999999999999998";

//默认的失效日期
var EXP_DATE =  "2030-01-01 00:00:00";

//默认父节点的id
var NULL_PARENTINT = -1;

//默认分页大小
var PAGESIZE = 10;



//权限验证报错信息
var FAIL_VALID_PRIVILEGE = '对不起,您没有此权限.';
var ALERT_BIGGEROREQURE = '应该大于等于';
var ALERT_NOWTIME = '当前时间.';
var ALERT_NOWDATE = '当前日期.';


//客户类型 add by zhangjing on 2005-03-15 14:00
var CUSTOMERTYPE_SET = new Array(
									new Array ("","请选择..."),
									new Array ("22A","商业客户"),
									new Array ("22B","大客户"),
									new Array ("22C","公务客户"),
                                                                        new Array ("22D","公话客户"),
									new Array ("22E","公务电话"),
                                                                        new Array ("22F","它网"),
									new Array ("22H","其他")
								);
//协议类型 add by qin.bin on 2005-03-15 14:00
var AGREEMENTTYPE_SET = new Array(
									new Array ("26A","产品服务订购协议"),
									new Array ("26B","信息变更"),
									new Array ("26C","服务注销协议")
								);
//协议状态 add by qin.bin on 2005-03-15 14:00
var AGREEMENSTATE_SET = new Array(

									new Array ("27A","已经执行"),
									new Array ("27B","正在执行")
								);
//协议状态 add by qin.bin on 2005-03-15 14:00
var AGREEMENSOURCE_SET = new Array(

									new Array ("28A","客户来源"),
									new Array ("28B","操作员来源")
								);

// add by qin.bin on 2005-03-15 14:00
var IS_SET = new Array(
							new Array ("F","否"),
							new Array ("T","是")
								);


//客户标识 add by qin.bin on 2005-03-15 14:00
var TYPEFLAG_SET = new Array(

							new Array ("0","个人客户"),
							new Array ("1","企业客户")
								);
//长途区号  add by luan.yang on 2005-03-16 16:06
var LARGEAREANUM_SET = new Array(new Array ("023","重庆"));

//查询方式  add by luan.yang on 2005-03-23 11:41
var QUERYMODE_SET = new Array(
                              new Array ("0","按设备号查合同账"),
                              /*重庆不需要此销帐功能
                              new Array ("1","按设备号查设备账"),
                              */
                              new Array ("2","按合同号查合同账"),
                              new Array ("3","按客户编码查合同帐"),
                              new Array ("4","按设备号查客户合同帐")
                              );

//滞纳金申请查询方式  add by luan.yang on 2005-04-28 15:49
var LATEFEE_QUERYMODE_SET = new Array(
                              new Array ("0","按设备号查"),
                              new Array ("1","按合同号查")
                              );

//反销原因 add by luan.yang on 2005-03-24 15:11
var UNCASHREASON_SET = new Array(
                                  new Array("实际未交","实际未交"),
                                  new Array("找零错误","找零错误"),
                                  new Array("发票未打","发票未打"),
                                  new Array("其他原因","其他原因")
                                  );

//反销原因 add by luan.yang on 2005-04-28 14:43
var UNCASH_DEALMONEY_SET = new Array(
                                  new Array("0","退款"),
                                  new Array("1","转为预存款")
                                  );

//销账操作类别 add by luan.yang on 2005-04-21 14:35
var CASH_OPERATE_TYPE_SET = new Array(
                                      new Array("5KA","付款"),
                                      new Array("5KB","冲正")
                                      );

//计总帐目类型标志 add by luan.yang on 2005-06-06 17:45
var TOTAL_ACCT_TYPE_SET = new Array(
                                      new Array("5GA","参与计总帐目类型"),
                                      new Array("5GB","不参与计总帐目类型")
                                      );

//费用帐目类型标志 add by luan.yang on 2005-06-06 17:45
var CHARGE_ACCT_TYPE_SET = new Array(
                                      new Array("5NA","费用帐目类型"),
                                      new Array("5NB","非费用帐目类型")
                                      );

//滞纳金调整方式 add by luan.yang on 2005-03-28 11:57
var LATEFEECHANGEMODE_SET = new Array(
                                      new Array("1","减免天数"),
                                      new Array("2","打折减免"),
                                      new Array("3","减免金额"),
                                      new Array("4","总额封顶"),
                                      new Array("5","天数封顶")
                                      );

//对帐范围 add by luan.yang on 2005-03-29 9:04
var COMPAREACCT_RANGE_SET=new Array(
                                 new Array ("perComp","个人对帐"),
                                 new Array ("teamComp","班组对帐"),
                                 new Array ("siteComp","营业厅对帐")
                                );

//参与人角色类型 add by luan.yang on 2005-04-07 10:48
var REGION_LEVELTYPE_SET = new Array(
                          new Array ("1","集团公司"),
                          new Array ("3","本地网"),
                          new Array ("5","营业区"),
                          new Array ("7","97局向")
                          );

//滞纳金调整状态 add by luan.yang on 2005-04-12 14:23
var LATEFEE_STATE_SET = new Array(
                          new Array ("00R","待审批"),
                          new Array ("00A","有效"),
                          new Array ("00X","注销"),
                          new Array ("00H","已归档"),
                          new Array ("00C","未通过审批")
                  );


//参数配置管理_参数类型 add by luan.yang on 2005-04-07 15:04
var SYSPARAM_DATETYPE_SET = new Array(
                          new Array ("1","字符型"),
                          new Array ("2","整型"),
                          new Array ("3","浮点型"),
                          new Array ("4","布尔型"),
                          new Array ("5","日期型")
                  );

//参数配置管理_是否定制 add by luan.yang on 2005-04-09 14:02
var SYSPARAM_ISCUSTOMIZED_SET = new Array(
                          new Array ("F","不可以定制"),
                          new Array ("T","可以定制")
                  );

//参数配置管理_输入输出 add by luan.yang on 2005-04-09 14:04
var SYSPARAM_ENTRY_SET = new Array(
                          new Array ("0","输入"),
                          new Array ("1","输出")
                  );

//参数配置管理_加密 add by luan.yang on 2005-04-09 14:12
var SYSPARAM_ENCRYPT_SET = new Array(
                          new Array ("F","不加密"),
                          new Array ("T","加密")
                  );

//零头处理精度 add by luan.yang on 2005-06-02 10:53
var PRECISION_SET = new Array(
                          new Array ("0","元"),
                          new Array ("1","角"),
                          new Array ("2","分")
                          );

//抵收补退费用项优先级 add by luan.yang on 2005-06-07 14:03
var ADD_RED_PRI_SET = new Array(
                          new Array ("0","有优先级"),
                          new Array ("1","无优先级")
                          );

//滞纳金申请查询方式  add by luan.yang on 2005-04-28 15:49
var ADD_RED_OBJECT_SET = new Array(
                              new Array ("0","按设备号查"),
                              new Array ("1","按合同号查")
                              );

//稽核项目方式  add by luan.yang on 2005-06-13 9:19
var AUDIT_ITEM_SET = new Array(
                              new Array ("0","欠费平衡稽核"),
                              new Array ("1","预存话费余额平衡"),
                              new Array ("2","营业收费平衡")
                              );

//稽核项目时间方式  add by luan.yang on 2005-06-13 9:21
var AUDIT_TIME_ITEM_SET = new Array(
                              new Array ("0","按月稽核"),
                              new Array ("1","按日稽核")
                              );

//设备故障减租精度 add by luan.yang on 2005-06-14 17:22
var REDU_PRECISION_SET = new Array(
                              new Array ("1","按整月"),
                              new Array ("2","按半月"),
                              new Array ("3","小数后两位")
                              );

//补收抵减减租状态 add by luan.yang on 2005-07-14 19:33
var ADDREDU_STATE_SET = new Array(
                              new Array ("00A","有效"),
                              new Array ("00X","已回退")
                              );


//设备故障减租状态 add by luan.yang on 2005-07-05 17:22
var REDU_STATE_SET = new Array(
                              new Array ("1","已减租"),
                              new Array ("2","已回退")
                              );

//余额支出类型
var BALANCE_PAYOUT_OPERTYPE_SET = new Array(
                                  new Array("5UA","存"),
                                  new Array("5UB","余额转入"),
                                  new Array("5UC","补"),
                                  new Array("5UD","开户"),
                                  new Array("5UE","存入冲正"),
                                  new Array("5UH","取"),
                                  new Array("5UI","扣"),
                                  new Array("5UJ","冲帐"),
                                  new Array("5UK","普通销户"),
                                  new Array("5UL","余额转出"),
                                  new Array("5UM","强制销户"),
                                  new Array("5UN","支取冲正"),
                                  new Array("5UR","分配回退"),
                                  new Array("5UO","强制扣费"),
                                  new Array("LOCK","锁定"),
                                  new Array("5UX","零头支取"),
                                  new Array("5UY","零头支取冲正")
                                  );



//银行帐号的所属类型。  add by qin.bin on 2005-03-16 19.59
var BANKACCTTYPE_SET = new Array(
                                 new Array ("5DA","普通托收"),
                                 new Array ("5DB","银行划帐")
                                );
//预付费类型。  add by qin.bin on 2005-03-16 19.59
var PREPAIDTYPE_SET = new Array(
                                 new Array ("5AA","普通预付费"),
                                 new Array ("5AB","智能网预付费")
                                );
//产品类别。  add by qin.bin on 2005-03-16 19.59
var  PRODUCT_CLASS_SET= new Array(
                                 new Array ("10A","主产品"),
                                 new Array ("10B","附属产品")
                                );
//产品类型。  add by qin.bin on 2005-03-16 19.59
var  PRODUCT_TYPE_SET= new Array(
                                 new Array ("11A","可单独购买"),
                                 new Array ("11B","不可单独购买")
                                );
//状态  add by qin.bin on 2005-03-16 19.59
var STATE_SET = new Array(
                                 new Array ("00A","有效"),
                                 new Array ("00X","注销"),
                                 new Array ("00H","已归档")
                                );
//余额来源状态  add by qin.bin on 2005-03-16 19.59
var BANLANCE_SOURCE_STATE_SET = new Array(
                                 new Array ("00A","有效"),
                                 new Array ("00X","注销"),
                                 new Array ("00B","存入被回退"),
                                 new Array ("00C","存入冲正"),
                                 new Array ("00H","已归档")
                                );
//余额支出状态  add by qin.bin on 2005-03-16 19.59
var BANLANCE_PAYOUT_STATE_SET = new Array(
                                 new Array ("00A","有效"),
                                 new Array ("00X","注销"),
                                 new Array ("00B","支取被回退"),
                                 new Array ("00C","支取冲正"),
                                 new Array ("00H","已归档")
                                );
//serv状态  add by qin.bin on 2005-03-16 19.59
var SERV_STATE_SET = new Array(
                                 new Array ("2HA","正常"),
                                 new Array ("2HB","注销"),
                                 new Array ("2HC","用户要求停机"),
                                 new Array ("2HD","欠费停机"),
                                 new Array ("2HE","欠费停机及用户要求停机"),
                                 new Array ("2HF","已归档"),
                                 new Array ("2HG","单停"),
                                 new Array ("2HI","停机保号"),
                                 new Array ("2HJ","拆前停机")
                                );

//帐期扣费最高额类型  add by qin.bin on 2005-03-16 19.59
var CYCLE_TYPE_SET = new Array(
                                 new Array ("5LA","总额封顶"),
                                 new Array ("5LB","余额对象封顶"),
                                 new Array ("5LC","帐户封顶"),
                                 new Array ("5LD","服务封顶")
                                );
//允许提取标志  add by qin.bin on 2005-03-16 19.59
var ALLOW_DRAW_SET=new Array(
                                 new Array ("5CA","允许提取"),
                                 new Array ("5CB","不允许提取")

                                );
//提供发票标志  add by qin.bin on 2005-03-16 19.59
var INV_OFFER_SET=new Array(
                                 new Array ("5BA","提供发票"),
                                 new Array ("5BB","不提供发票")
                                );
//调帐标志  add by qin.bin on 2005-03-16 19.59
var ADJUST_FLAG_SET=new Array(
                                 new Array ("5CA","允许调帐使用"),
                                 new Array ("5CB","不允许调帐使用")
                                );
//支出操作类型 add by qin.bin on 2005-03-17 19.59
var PAYOUT_OPER_TYPE_SET=new Array(
                                 new Array("5UH","取"),
                                 new Array("5UI","扣"),
                                 new Array("5UJ","冲帐"),
                                 new Array("5UK","普通销户"),
                                 new Array("5UL","余额转出"),
                                 new Array("5UM","强制销户"),
                                 new Array("5UN","支取冲帐"),
                                 new Array("5UR","分配回退"),
                                 new Array("5UO","强制扣费")
                                );
//收入操作类型 add by qin.bin on 2005-03-17 19.59
var SOURCE_OPER_TYPE_SET=new Array(
                                 new Array("5UA","存"),
                                 new Array("5UB","余额转入"),
                                 new Array("5UC","补"),
                                 new Array("5UD","开户"),
                                 new Array("5UE","存入冲正")
                                );
//收入来源类型 add by qin.bin on 2005-03-17 19.59
var SOURCE_TYPE_SET=new Array(
                                 new Array ("5VA","现金预存款"),
                                 new Array ("5VB","补退"),
                                 new Array ("5VC","银行扣款转余额"),
                                 new Array ("5VD","押金")
                                );
//收入来源当前状态  add by qin.bin on 2005-03-16 19.59
var SOURCE_USE_STATE_SET = new Array(
                                 new Array ("00A","扣减中"),
                                 new Array ("00X","未扣减"),
                                 new Array ("00H","扣减完")
                                );

//零头处理类型 add by zhang.jing on 2005-03-26 13.00
var DEALMONEY_TYPE_SET=new Array(
                                 new Array ("ceil","上取"),
                                 new Array ("floor","下取"),
                                 new Array ("fact","实收"),
                                 new Array ("round","四舍五入")
                                );



//销帐业务类型 add by zhang.jing on 2005-04-12 19.32
//modified by luan.yang on 2005-07-05 16:28
var PAYMENT_STATE_SET=new Array(
                                 new Array ("5JA","已生成"),
                                 new Array ("5JB","销帐"),
                                 new Array ("5JC","被反销"),
                                 new Array ("5JD","冲正"),
                                 new Array ("5JT","银行托收"),
                                 new Array ("5JF","托收转预存"),
                                 new Array ("XXX","银行漂单")
                                );


//允许提取标志类型 add by zhang.jing on 2005-04-18 16.32
var ALLOWDRAW_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("5CA","允许"),
                                 new Array ("5CB","不允许")
                                );

//提供发票标志类型 add by zhang.jing on 2005-04-18 16.32
var INVOFFER_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("5BA","提供"),
                                 new Array ("5BB","不提供")
                                );

//调帐标志类型 add by zhang.jing on 2005-04-18 16.32
var ADJUSTFLAG_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("5CA","允许"),
                                 new Array ("5CB","不允许")
                                );

//帐期最高扣费类型 add by zhang.jing on 2005-04-22 15:22
var CYCLEUPPERTYPE_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("5LA","总额封顶"),
                                 new Array ("5LB","余额对象封顶"),
                                 new Array ("5LC","帐户封顶"),
                                 new Array ("5LD","服务封顶")
                                );

//帐期最低扣费类型 add by zhang.jing on 2005-04-22 15:22
var CYCLELOWERTYPE_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("5LA","总额封顶"),
                                 new Array ("5LB","余额对象封顶"),
                                 new Array ("5LC","帐户封顶"),
                                 new Array ("5LD","服务封顶")
                                );


//帐期最低扣费类型 add by zhang.jing on 2005-04-22 16:37
var BALANCETYPERULE_RULETYPE_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("8RA","生效时间计算规则"),
                                 new Array ("8RB","失效时间计算规则"),
                                 new Array ("8RC","帐期最高扣费计算规则"),
                                 new Array ("8RD","帐期最低扣费计算规则")
                                );

//重点客户标志 add by zhang.jing on 2005-04-26 20.08
var ISVIP_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("T","重点客户"),
                                 new Array ("F","非重点客户")
                                );

//余额对象类型 add by zhang.jing on 2005-04-27 09.38
var OBJTYPE_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("5BA","帐户"),
                                 new Array ("5BB","客户"),
                                 new Array ("5BC","用户")
                         );

//余额对象初始化 add by zhang.jing on 2005-04-27 09.38
var OBJID_SET=new Array(
                                 new Array ("","请选择")
                         );


//收入操作类型 add by zhang.jing on 2005-04-27 11.19
var SOURCE_OPER_TYPE_SET2=new Array(
																 new Array ("","请选择"),
                                 new Array("5UA","存"),
                                 new Array("5UB","余额转入"),
                                 new Array("5UC","补"),
                                 new Array("5UD","开户"),
                                 new Array("5UE","存入冲正")
                                );
//收入来源类型 add by zhang.jing on 2005-04-27 11.19
var SOURCE_TYPE_SET2=new Array(
//                                 new Array ("","请选择"),
                                 new Array ("5VA","现金预存款"),
                                 new Array ("5VJ","支票预存款")
                               );


//是否计算滞纳金 add by zhang.jing on 2005-06-06 19:44
var LATE_FEE_FLAG_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("T","计算滞纳金"),
                                 new Array ("F","不计算滞纳金")
                               );

//单据合打标志 add by zhang.jing on 2005-06-06 19:44
var PACK_BILL_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("T","多帐期合打单据"),
                                 new Array ("F","按帐期分打单据")
                               );

//销帐模式 add by zhang.jing on 2005-06-06 19:44
var CASH_MODE_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("0","销帐"),
                                 new Array ("1","托收")
                               );

//输出数据方式 add by zhang.jing on 2005-06-06 19:44
var OUTPUT_METHOD_SET=new Array(
                                 new Array ("","请选择"),
                                 new Array ("0","票据"),
                                 new Array ("1","电子文件")
                               );
//帐单项类别 add by QIN.BIN on 2005-06-06 19:44
var CLASSIFY_SET=new Array(
                                 new Array ("55A","费用帐单项"),
                                 new Array ("55B","非费用帐单项")
                               );
//事务定义抽取方式
var TRANS_TYPE=new Array(new Array("1","增量抽取"),new Array("2","全量抽取"));
//事务定义运行方式
var TRANS_RUN_TYPE=new Array(new Array("1","每隔指定时间单位运行"),new Array("2","手工触发"));
//事务定义时间间隔单位
var TRANS_TIME_UNIT=new Array(new Array("1","年"),new Array("2","月"),
                              new Array("3","日"),new Array("4","时"),
                              new Array("5","分"),new Array("6","秒"));
//事务参数类型
var TRANS_PARAM_TYPE=new Array(new Array("8BA","整数"),new Array("8BB","浮点数"),
                               new Array("8BC","字符串型"),new Array("8BD","布尔型"),
                               new Array("8BE","日期型"));
//事务参数是否可以定制
var TRANS_PARAM_IS_CUSTOMIZ=new Array(new Array("0","不可定制"),new Array("1","可定制"),new Array("2","必须定制"));
//事务之间的关系
var TRANS_RELATION_TYPE=new Array(new Array("0","倚赖关系"),new Array("1","互斥关系"));
//事务中描述目标数据存储方式
var TRANS_STORE_TYPE=new Array(new Array("1","文件存储"),new Array("2","数据库表存储"));
//事务中数据抽取方式
var TRANS_STYLE=new Array(new Array("1","用sql语句抽取"),new Array("2","定制后台程序抽取"),new Array("3","存储过程抽取"));
//事务运行状态
var TRANS_LOGSTATE=new Array(new Array("",""),new Array("1","处理中"),new Array("2","处理失败"),new Array("3","处理成功"));
//事务定义运行方式
var TRANS_RUN_TYPEEMPTY=new Array(new Array("",""),new Array("1","每隔指定时间单位运行"),new Array("2","手工触发"));
//事务处理类型
var TRANS_SUB_TRANSTYPE=new Array(new Array("0","运行"),new Array("1","重处理"));
//事务命令状态
var TRANS_CMD_STATE=new Array(
                                 new Array ("1","未处理"),
                                 new Array ("2","处理成功"),
                                 new Array ("3","处理失败"),
                                 new Array ("4","开始处理"),
                                 new Array ("5","正在处理"),
                                 new Array ("6","数据汇总")
                               );
var TRANS_CMD_DEALTYPE=new Array(
				new Array("1","数据抽取"),
				new Array("2","回退"),
				new Array("3","重处理")
																 )                               
var TRANS_TARGET=new Array(
				new Array("0","经营分析及渠道接口"),
				new Array("1","渠道接口"),
				new Array("2","经营分析接口"),
				new Array("3","其它")
			  );	
//欠费业务类型名称
var OWE_BUSINESSTYPE_NAME=new Array(new Array("0","等待处理"),new Array("1","处理成功"),
                                  new Array("2","处理失败"),new Array("3","放弃处理"),
                                  new Array("4","工单已经提取的状态"),new Array("5","确认成功"),
                                  new Array("6","确认失败"));
//参数配置测试用
var TRANS_PARAM_NAME= new Array(new Array("region_id","地域号"),new Array("start_time","开始时间"),new Array("end_time","结束时间"),
                                new Array("event_type_id","事件类型"),new Array("deal_date","处理时间"),
                                new Array("create_date","数据生成时间"),new Array("billing_cycle_id","帐期标识"));



//限额计划适用对象 add by zhangjing on 2005-06-17 15:34
var TARGETTYPE_SET = new Array(
									new Array ("","请选择"),
									new Array ("A","帐户限额计划"),
									new Array ("S","用户限额计划")
								);



//限额类型 add by zhangjing on 2005-06-18 11:15
var SUMEVENTTYPE_SET = new Array(
									new Array ("","请选择..."),
									new Array ("48A","一次性事件"),
									new Array ("48B","使用事件"),
									new Array ("48C","周期性事件"),
									new Array ("48D","优惠事件")
								);

//积分规则计算参考对象 add by ji.yangyang on 2005-6-30 8:34
var BONUS_REF_OBJECT = new Array(
									new Array ("0","费用"),
									new Array ("1","在网时间\(月数\)"),
									new Array ("2","信用度"),
									new Array ("3","累计欠费月数")
								);

//调帐方式 add by ji.yangyang on 2005-7-6 17:20
var ADJUST_TYPE = new Array(
									new Array ("1","按金额"),
									new Array ("2","按比例")
								);

//调帐状态 add by ji.yangyang on 2005-7-6 17:20
var ADJUST_STATE = new Array(
									new Array ("00A","调帐"),
									new Array ("00X","回退")
								);


//空的请选择 add by zhangjing on 2005-07-19 13:50
var BLANK_SET = new Array(
									new Array ("","请选择")
								);


//字段是否定长 add by zhangjing on 2005-07-19 16:05
var FIX_LENGTH_FLAG_SET = new Array(
									new Array ("","请选择"),
									new Array ("T","各字段定长"),
									new Array ("F","不定长")
								);


//是否需要文件头 add by zhangjing on 2005-07-19 16:05
var FILE_HEADER_FLAG_SET = new Array(
									new Array ("","请选择"),
									new Array ("T","需要文件头"),
									new Array ("F","不需要生成文件头")
								);


var FIELDTYPE_SET = new Array(
									new Array ("1","金额"),
									new Array ("2","其他"),
									new Array ("3","银行回执")
								);

//余额调帐操作类型 add by zhang.jing
var ADJUST_OPER_TYPE_SET = new Array(
									new Array ("","请选择"),
									new Array("5UC","补(调帐增加余额)"),
									new Array("5UI","扣(调帐减少余额)")
								);


//余额类型定义失效时间偏移类型
var OFFSET_TYPE_SET = new Array(
									new Array ("","请选择"),
									new Array("0","按月偏移"),
									new Array("1","按日偏移")
								);
								
//报表数据集命令行类型
var DATASET_COMMAND_TYPE = new Array(
									new Array("0","SQL语句"),
									new Array("1","存储过程")
								);
//流程类型
var FLOW_TYPE_SET=new Array(
									new Array("1","实时流程"),
									new Array("2","批处理流程")
								);
//流程状态
var FLOW_STATE_SET=new Array(
									new Array("00A","有效"),
									new Array("00B","待发布"),
									new Array("00X","无效")
								);
//流程周期类型
var TIME_TYPE_SET=new Array(
									new Array("001","小时"),
									new Array("002","天")
								);
//是否重复执行
var CYC_EXEC_SET=new Array(
									new Array("1","需要重复执行"),
									new Array("2","无需重复运行")
								);
//节点分类
var NODE_TYPE_SET=new Array(
									new Array("1","首节点"),
									new Array("2","中间节点"),
									new Array("3","尾节点")
								);
//节点执行模式
var NODE_EXEC_MOD_SET=new Array(
									new Array("1","自动"),
									new Array("2","人工")
								);
//节点服务控制对象
var NODE_SERVICE_OWER_SET=new Array(
									new Array("1","后台主控进程"),
									new Array("2","流程引擎")
								);
//判断结果真或者假
var RULE_RESULT_SET=new Array(
									new Array("1","使用判断结果为假"),
									new Array("0","使用判断结果为真")
								);
//多输入节点影响流程
var NODE_FLOW_SET=new Array(
									new Array("1","只要对应的输出节点满足条件就可以执行下一节点"),
									new Array("0","必须要所有的输入节点满足条件才能执行下一节点")
								);

//审核校验结果状态
var AUDIT_RESULT_STAT = new Array(
	new Array("001","处理中"),
	new Array("002","成功结束"),
	new Array("003","异常结束")
);

//97接口表
//服务表处理状态
var OPERATE_STATE = new Array(
				         new Array("","请选择"),
				         new Array("0", "未处理"),
				         new Array("1", "已处理"),
				         new Array("2", "不能处理"),
				         new Array("5", "尚未稽核"),
				         new Array("6", "撤单")
							);
			
//服务表重点客户标志
var SERVER_VIP_FLAG = new Array(
                   new Array ("", "请选择"),
                   new Array ("T", "重点客户"),
                   new Array ("F", "非重点客户")
                 );

//客户表重点客户标示
var CUSTOMER_VIP_FLAG = new Array(
										new Array("", "请选择"),
										new Array("10", "全国性大客户"),
										new Array("20", "全省性大客户"),
										new Array("30", "本地网客户")
									);

//客户表联系标示
var CUSTOMER_CONNECT_FLAG = new Array(
												new Array("", "请选择"),
												new Array("1", "固定电话")
											);

//客户表动作类型
var CUSTOMER_ACTION = new Array(
									new Array("", "请选择"),
									new Array("01", "新增"),
									new Array("02", "修改"),
									new Array("03", "删除")
								);

//帐户表动作类型
var ACCOUNT_ACTION = new Array(
									new Array("", "请选择"),
									new Array("01", "新增帐户"),
									new Array("02", "增加帐务关系"),
									new Array("03", "删除帐务关系"),
									new Array("04", "过所有帐务关系给新帐户"),
									new Array("05", "过所有帐务关系给旧帐户"),
									new Array("06", "修改帐户信息")
								);

//帐户表帐单投递方式
var ACCOUNT_MAIL_METHOD = new Array(
											new Array("", "请选择"),
											new Array("001", "邮寄"),
											new Array("002", "传真"),
											new Array("003", "E-Mail"),
											new Array("004", "不投递")
										);

//帐户表帐合同号类型
var ACCOUNT_NUMBER_STYLE = new Array(
											new Array("01", "97合同号"),
											new Array("02", "计费生成合同号"),
											new Array("03", "根据电话号码选择")
										);
										
//资源表动作类型
var PRODUCT_ACTION = new Array(
									new Array("", "请选择"),
									new Array("01", "新增产品"),
									new Array("02", "减少产品")
								);

//优惠方案表付费标示
var DISCT_FLAG = new Array(
							new Array("", "请选择"),
							new Array("0", "未受理"),
							new Array("1", "已受理")
						);

//优惠方案动作类型
var DISCT_ACTION = new Array(
								new Array("", "请选择"),
								new Array("01", "增加优惠规则"),
								new Array("02", "删除优惠规则")
							);
							
//群类型
var GROUP_TYPE = new Array(
                                                           new Array("11A","VPN群"),
                                                           new Array("11B","CENTREX群"),
                                                           new Array("11C","虚拟网")
                                                        );

//群状态
 var ANTI_STATE_SET = new Array(
                                 new Array ("有效","00A"),
                                 new Array ("注销","00X"),
                                 new Array ("已归档","00H")
                                );
//调帐方式 add by xu.yiliang on 2005-11-20
var NODE_TYPE = new Array(
									new Array ("1","预处理环节"),
									new Array ("2","拣重环节"),
									new Array ("3","批价环节"),
									new Array ("4","合帐环节")
								);

//定价包含对象类型
var OBJECT_TYPE = new Array(
								new Array("80A", "产品实例"),
								new Array("80B", "产品包实例"),
								new Array("80C", "商品实例"),
								new Array("80H", "事件"),
								new Array("80I", "客户"),
								new Array("80J", "帐户"),
								new Array("80P", "指定类别的产品实例总集"),
								new Array("80Q", "指定类别的产品包实例总集"),
								new Array("80R", "指定类别的帐户总集"),
								new Array("80U", "定价包含的对象")
							);

//定价计划类型
var PRICING_PLAN_TYPE = new Array(
								new Array("20A", "帐户类，适用于帐户优惠"),
								new Array("20B", "捆绑类，适用于捆绑优惠"),
								new Array("20C", "客户类，适用于客户优惠"),
								new Array("20P", "产品类，适用于产品、产品包、商品的定价计划和客户化的定价计划")
							);

//定价计划状态
var PRICING_PLAN_STATE = new Array(
								new Array("00A", "有效"),
								new Array("00X", "注销"),
								new Array("00H", "已归档")
							);


//商品明细类型
var ELEMENT_TYPE = new Array(
								new Array("10A", "产品"),
								new Array("10B", "产品包"),
								new Array("10C", "商品"),
								new Array("10D", "定价计划")
							);							
//add by jiyangyang
//城乡属性
var COUNTY_TYPE = new Array(
								new Array("0", "城市"),
								new Array("1", "农村"),
								new Array("2", "公话"),
								new Array("3", "全进网")
							);
							
//镇企属性
var RESID_FLAG = new Array(
								new Array("0", "住宅"),
								new Array("1", "办公"),
								new Array("2", "DID")
							);
//付费方式
var PAYMENT_METHOD_SELECT = new Array(
								new Array("11", "现金"),
								new Array("12", "支票"),
								new Array("13", "前台POS"),
								new Array("20", "托收付款"),
								new Array("30", "信用卡"),
								new Array("99", "免费")
							);


//add by xu.yiliang 2006-02-08
//异常类型类别
var SERV_ABNORMAL_TYPE=new Array(new Array("0","--所有异常类型--"),
									new Array("1","无号码"),
									new Array("2","无用户名"),
									new Array("3","无号码无用户名"),
									new Array("4","无默认帐户"),
									new Array("5","多个默认帐户"),
									new Array("6","默认帐户与资料不一致"),
									new Array("7","有多条有效记录")
								);

//add by xu.yiliang 2006-02-22
//任务执行状态
var TASK_DEAL_STATE=new Array(
									new Array("00A","等待处理"),
									new Array("00B","处理中"),
									new Array("00D","处理成功"),
									new Array("00E","处理失败")
								);

var SERV_AUDIT_ITEM=new Array(new Array("0","--所有费用项目--"),
									new Array("1","固定费"),
									new Array("2","市话费"),
									new Array("3","本地网费用"),
									new Array("4","信息费"),
									new Array("5","互联网费用"),
									new Array("6","IP长话费用"),
									new Array("7","寻呼费"),
									new Array("8","国内长话费"),
									new Array("9","国际长话费"),
									new Array("10","其他费")
								);


var SERV_AUDIT_STYLE=new Array(
									new Array("1",">="),
									new Array("2","<=")
								);

// add by yangsg  
//动作类型
var ACTION_SET = new Array(
                      new Array("","请选择"),
                      new Array("A1","装机"),
                      new Array("A5","移机"),
                      new Array("A8","拆机"),                    
                      new Array("B2","过户"),
                      new Array("B3","停机"),
                      new Array("B5","复机"),                      
                      new Array("B7","商品变迁") ,                     
                      new Array("C2","停机保号"),
                      new Array("C5","拆前停机"),
                      new Array("B6","改附属产品"),
                      new Array("B1","改帐务关系"),
                      new Array("B9","综合信息修改")
                       );


//add by xu.yiliang
//电话类型
var PHONE_TYPE= new Array(
new Array("0","预付费"),
new Array("1","后付费")
);