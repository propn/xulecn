//int�͵Ķ�Ӧnullֵ
var NULL_INT = -2147483648;

//long�͵Ķ�Ӧnullֵ
var NULL_LONG = "-9999999999999998";

//Ĭ�ϵ�ʧЧ����
var EXP_DATE =  "2030-01-01 00:00:00";

//Ĭ�ϸ��ڵ��id
var NULL_PARENTINT = -1;

//Ĭ�Ϸ�ҳ��С
var PAGESIZE = 10;



//Ȩ����֤������Ϣ
var FAIL_VALID_PRIVILEGE = '�Բ���,��û�д�Ȩ��.';
var ALERT_BIGGEROREQURE = 'Ӧ�ô��ڵ���';
var ALERT_NOWTIME = '��ǰʱ��.';
var ALERT_NOWDATE = '��ǰ����.';


//�ͻ����� add by zhangjing on 2005-03-15 14:00
var CUSTOMERTYPE_SET = new Array(
									new Array ("","��ѡ��..."),
									new Array ("22A","��ҵ�ͻ�"),
									new Array ("22B","��ͻ�"),
									new Array ("22C","����ͻ�"),
                                                                        new Array ("22D","�����ͻ�"),
									new Array ("22E","����绰"),
                                                                        new Array ("22F","����"),
									new Array ("22H","����")
								);
//Э������ add by qin.bin on 2005-03-15 14:00
var AGREEMENTTYPE_SET = new Array(
									new Array ("26A","��Ʒ���񶩹�Э��"),
									new Array ("26B","��Ϣ���"),
									new Array ("26C","����ע��Э��")
								);
//Э��״̬ add by qin.bin on 2005-03-15 14:00
var AGREEMENSTATE_SET = new Array(

									new Array ("27A","�Ѿ�ִ��"),
									new Array ("27B","����ִ��")
								);
//Э��״̬ add by qin.bin on 2005-03-15 14:00
var AGREEMENSOURCE_SET = new Array(

									new Array ("28A","�ͻ���Դ"),
									new Array ("28B","����Ա��Դ")
								);

// add by qin.bin on 2005-03-15 14:00
var IS_SET = new Array(
							new Array ("F","��"),
							new Array ("T","��")
								);


//�ͻ���ʶ add by qin.bin on 2005-03-15 14:00
var TYPEFLAG_SET = new Array(

							new Array ("0","���˿ͻ�"),
							new Array ("1","��ҵ�ͻ�")
								);
//��;����  add by luan.yang on 2005-03-16 16:06
var LARGEAREANUM_SET = new Array(new Array ("023","����"));

//��ѯ��ʽ  add by luan.yang on 2005-03-23 11:41
var QUERYMODE_SET = new Array(
                              new Array ("0","���豸�Ų��ͬ��"),
                              /*���첻��Ҫ�����ʹ���
                              new Array ("1","���豸�Ų��豸��"),
                              */
                              new Array ("2","����ͬ�Ų��ͬ��"),
                              new Array ("3","���ͻ�������ͬ��"),
                              new Array ("4","���豸�Ų�ͻ���ͬ��")
                              );

//���ɽ������ѯ��ʽ  add by luan.yang on 2005-04-28 15:49
var LATEFEE_QUERYMODE_SET = new Array(
                              new Array ("0","���豸�Ų�"),
                              new Array ("1","����ͬ�Ų�")
                              );

//����ԭ�� add by luan.yang on 2005-03-24 15:11
var UNCASHREASON_SET = new Array(
                                  new Array("ʵ��δ��","ʵ��δ��"),
                                  new Array("�������","�������"),
                                  new Array("��Ʊδ��","��Ʊδ��"),
                                  new Array("����ԭ��","����ԭ��")
                                  );

//����ԭ�� add by luan.yang on 2005-04-28 14:43
var UNCASH_DEALMONEY_SET = new Array(
                                  new Array("0","�˿�"),
                                  new Array("1","תΪԤ���")
                                  );

//���˲������ add by luan.yang on 2005-04-21 14:35
var CASH_OPERATE_TYPE_SET = new Array(
                                      new Array("5KA","����"),
                                      new Array("5KB","����")
                                      );

//������Ŀ���ͱ�־ add by luan.yang on 2005-06-06 17:45
var TOTAL_ACCT_TYPE_SET = new Array(
                                      new Array("5GA","���������Ŀ����"),
                                      new Array("5GB","�����������Ŀ����")
                                      );

//������Ŀ���ͱ�־ add by luan.yang on 2005-06-06 17:45
var CHARGE_ACCT_TYPE_SET = new Array(
                                      new Array("5NA","������Ŀ����"),
                                      new Array("5NB","�Ƿ�����Ŀ����")
                                      );

//���ɽ������ʽ add by luan.yang on 2005-03-28 11:57
var LATEFEECHANGEMODE_SET = new Array(
                                      new Array("1","��������"),
                                      new Array("2","���ۼ���"),
                                      new Array("3","������"),
                                      new Array("4","�ܶ�ⶥ"),
                                      new Array("5","�����ⶥ")
                                      );

//���ʷ�Χ add by luan.yang on 2005-03-29 9:04
var COMPAREACCT_RANGE_SET=new Array(
                                 new Array ("perComp","���˶���"),
                                 new Array ("teamComp","�������"),
                                 new Array ("siteComp","Ӫҵ������")
                                );

//�����˽�ɫ���� add by luan.yang on 2005-04-07 10:48
var REGION_LEVELTYPE_SET = new Array(
                          new Array ("1","���Ź�˾"),
                          new Array ("3","������"),
                          new Array ("5","Ӫҵ��"),
                          new Array ("7","97����")
                          );

//���ɽ����״̬ add by luan.yang on 2005-04-12 14:23
var LATEFEE_STATE_SET = new Array(
                          new Array ("00R","������"),
                          new Array ("00A","��Ч"),
                          new Array ("00X","ע��"),
                          new Array ("00H","�ѹ鵵"),
                          new Array ("00C","δͨ������")
                  );


//�������ù���_�������� add by luan.yang on 2005-04-07 15:04
var SYSPARAM_DATETYPE_SET = new Array(
                          new Array ("1","�ַ���"),
                          new Array ("2","����"),
                          new Array ("3","������"),
                          new Array ("4","������"),
                          new Array ("5","������")
                  );

//�������ù���_�Ƿ��� add by luan.yang on 2005-04-09 14:02
var SYSPARAM_ISCUSTOMIZED_SET = new Array(
                          new Array ("F","�����Զ���"),
                          new Array ("T","���Զ���")
                  );

//�������ù���_������� add by luan.yang on 2005-04-09 14:04
var SYSPARAM_ENTRY_SET = new Array(
                          new Array ("0","����"),
                          new Array ("1","���")
                  );

//�������ù���_���� add by luan.yang on 2005-04-09 14:12
var SYSPARAM_ENCRYPT_SET = new Array(
                          new Array ("F","������"),
                          new Array ("T","����")
                  );

//��ͷ������ add by luan.yang on 2005-06-02 10:53
var PRECISION_SET = new Array(
                          new Array ("0","Ԫ"),
                          new Array ("1","��"),
                          new Array ("2","��")
                          );

//���ղ��˷��������ȼ� add by luan.yang on 2005-06-07 14:03
var ADD_RED_PRI_SET = new Array(
                          new Array ("0","�����ȼ�"),
                          new Array ("1","�����ȼ�")
                          );

//���ɽ������ѯ��ʽ  add by luan.yang on 2005-04-28 15:49
var ADD_RED_OBJECT_SET = new Array(
                              new Array ("0","���豸�Ų�"),
                              new Array ("1","����ͬ�Ų�")
                              );

//������Ŀ��ʽ  add by luan.yang on 2005-06-13 9:19
var AUDIT_ITEM_SET = new Array(
                              new Array ("0","Ƿ��ƽ�����"),
                              new Array ("1","Ԥ�滰�����ƽ��"),
                              new Array ("2","Ӫҵ�շ�ƽ��")
                              );

//������Ŀʱ�䷽ʽ  add by luan.yang on 2005-06-13 9:21
var AUDIT_TIME_ITEM_SET = new Array(
                              new Array ("0","���»���"),
                              new Array ("1","���ջ���")
                              );

//�豸���ϼ��⾫�� add by luan.yang on 2005-06-14 17:22
var REDU_PRECISION_SET = new Array(
                              new Array ("1","������"),
                              new Array ("2","������"),
                              new Array ("3","С������λ")
                              );

//���յּ�����״̬ add by luan.yang on 2005-07-14 19:33
var ADDREDU_STATE_SET = new Array(
                              new Array ("00A","��Ч"),
                              new Array ("00X","�ѻ���")
                              );


//�豸���ϼ���״̬ add by luan.yang on 2005-07-05 17:22
var REDU_STATE_SET = new Array(
                              new Array ("1","�Ѽ���"),
                              new Array ("2","�ѻ���")
                              );

//���֧������
var BALANCE_PAYOUT_OPERTYPE_SET = new Array(
                                  new Array("5UA","��"),
                                  new Array("5UB","���ת��"),
                                  new Array("5UC","��"),
                                  new Array("5UD","����"),
                                  new Array("5UE","�������"),
                                  new Array("5UH","ȡ"),
                                  new Array("5UI","��"),
                                  new Array("5UJ","����"),
                                  new Array("5UK","��ͨ����"),
                                  new Array("5UL","���ת��"),
                                  new Array("5UM","ǿ������"),
                                  new Array("5UN","֧ȡ����"),
                                  new Array("5UR","�������"),
                                  new Array("5UO","ǿ�ƿ۷�"),
                                  new Array("LOCK","����"),
                                  new Array("5UX","��ͷ֧ȡ"),
                                  new Array("5UY","��ͷ֧ȡ����")
                                  );



//�����ʺŵ��������͡�  add by qin.bin on 2005-03-16 19.59
var BANKACCTTYPE_SET = new Array(
                                 new Array ("5DA","��ͨ����"),
                                 new Array ("5DB","���л���")
                                );
//Ԥ�������͡�  add by qin.bin on 2005-03-16 19.59
var PREPAIDTYPE_SET = new Array(
                                 new Array ("5AA","��ͨԤ����"),
                                 new Array ("5AB","������Ԥ����")
                                );
//��Ʒ���  add by qin.bin on 2005-03-16 19.59
var  PRODUCT_CLASS_SET= new Array(
                                 new Array ("10A","����Ʒ"),
                                 new Array ("10B","������Ʒ")
                                );
//��Ʒ���͡�  add by qin.bin on 2005-03-16 19.59
var  PRODUCT_TYPE_SET= new Array(
                                 new Array ("11A","�ɵ�������"),
                                 new Array ("11B","���ɵ�������")
                                );
//״̬  add by qin.bin on 2005-03-16 19.59
var STATE_SET = new Array(
                                 new Array ("00A","��Ч"),
                                 new Array ("00X","ע��"),
                                 new Array ("00H","�ѹ鵵")
                                );
//�����Դ״̬  add by qin.bin on 2005-03-16 19.59
var BANLANCE_SOURCE_STATE_SET = new Array(
                                 new Array ("00A","��Ч"),
                                 new Array ("00X","ע��"),
                                 new Array ("00B","���뱻����"),
                                 new Array ("00C","�������"),
                                 new Array ("00H","�ѹ鵵")
                                );
//���֧��״̬  add by qin.bin on 2005-03-16 19.59
var BANLANCE_PAYOUT_STATE_SET = new Array(
                                 new Array ("00A","��Ч"),
                                 new Array ("00X","ע��"),
                                 new Array ("00B","֧ȡ������"),
                                 new Array ("00C","֧ȡ����"),
                                 new Array ("00H","�ѹ鵵")
                                );
//serv״̬  add by qin.bin on 2005-03-16 19.59
var SERV_STATE_SET = new Array(
                                 new Array ("2HA","����"),
                                 new Array ("2HB","ע��"),
                                 new Array ("2HC","�û�Ҫ��ͣ��"),
                                 new Array ("2HD","Ƿ��ͣ��"),
                                 new Array ("2HE","Ƿ��ͣ�����û�Ҫ��ͣ��"),
                                 new Array ("2HF","�ѹ鵵"),
                                 new Array ("2HG","��ͣ"),
                                 new Array ("2HI","ͣ������"),
                                 new Array ("2HJ","��ǰͣ��")
                                );

//���ڿ۷���߶�����  add by qin.bin on 2005-03-16 19.59
var CYCLE_TYPE_SET = new Array(
                                 new Array ("5LA","�ܶ�ⶥ"),
                                 new Array ("5LB","������ⶥ"),
                                 new Array ("5LC","�ʻ��ⶥ"),
                                 new Array ("5LD","����ⶥ")
                                );
//������ȡ��־  add by qin.bin on 2005-03-16 19.59
var ALLOW_DRAW_SET=new Array(
                                 new Array ("5CA","������ȡ"),
                                 new Array ("5CB","��������ȡ")

                                );
//�ṩ��Ʊ��־  add by qin.bin on 2005-03-16 19.59
var INV_OFFER_SET=new Array(
                                 new Array ("5BA","�ṩ��Ʊ"),
                                 new Array ("5BB","���ṩ��Ʊ")
                                );
//���ʱ�־  add by qin.bin on 2005-03-16 19.59
var ADJUST_FLAG_SET=new Array(
                                 new Array ("5CA","�������ʹ��"),
                                 new Array ("5CB","���������ʹ��")
                                );
//֧���������� add by qin.bin on 2005-03-17 19.59
var PAYOUT_OPER_TYPE_SET=new Array(
                                 new Array("5UH","ȡ"),
                                 new Array("5UI","��"),
                                 new Array("5UJ","����"),
                                 new Array("5UK","��ͨ����"),
                                 new Array("5UL","���ת��"),
                                 new Array("5UM","ǿ������"),
                                 new Array("5UN","֧ȡ����"),
                                 new Array("5UR","�������"),
                                 new Array("5UO","ǿ�ƿ۷�")
                                );
//����������� add by qin.bin on 2005-03-17 19.59
var SOURCE_OPER_TYPE_SET=new Array(
                                 new Array("5UA","��"),
                                 new Array("5UB","���ת��"),
                                 new Array("5UC","��"),
                                 new Array("5UD","����"),
                                 new Array("5UE","�������")
                                );
//������Դ���� add by qin.bin on 2005-03-17 19.59
var SOURCE_TYPE_SET=new Array(
                                 new Array ("5VA","�ֽ�Ԥ���"),
                                 new Array ("5VB","����"),
                                 new Array ("5VC","���пۿ�ת���"),
                                 new Array ("5VD","Ѻ��")
                                );
//������Դ��ǰ״̬  add by qin.bin on 2005-03-16 19.59
var SOURCE_USE_STATE_SET = new Array(
                                 new Array ("00A","�ۼ���"),
                                 new Array ("00X","δ�ۼ�"),
                                 new Array ("00H","�ۼ���")
                                );

//��ͷ�������� add by zhang.jing on 2005-03-26 13.00
var DEALMONEY_TYPE_SET=new Array(
                                 new Array ("ceil","��ȡ"),
                                 new Array ("floor","��ȡ"),
                                 new Array ("fact","ʵ��"),
                                 new Array ("round","��������")
                                );



//����ҵ������ add by zhang.jing on 2005-04-12 19.32
//modified by luan.yang on 2005-07-05 16:28
var PAYMENT_STATE_SET=new Array(
                                 new Array ("5JA","������"),
                                 new Array ("5JB","����"),
                                 new Array ("5JC","������"),
                                 new Array ("5JD","����"),
                                 new Array ("5JT","��������"),
                                 new Array ("5JF","����תԤ��"),
                                 new Array ("XXX","����Ư��")
                                );


//������ȡ��־���� add by zhang.jing on 2005-04-18 16.32
var ALLOWDRAW_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("5CA","����"),
                                 new Array ("5CB","������")
                                );

//�ṩ��Ʊ��־���� add by zhang.jing on 2005-04-18 16.32
var INVOFFER_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("5BA","�ṩ"),
                                 new Array ("5BB","���ṩ")
                                );

//���ʱ�־���� add by zhang.jing on 2005-04-18 16.32
var ADJUSTFLAG_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("5CA","����"),
                                 new Array ("5CB","������")
                                );

//������߿۷����� add by zhang.jing on 2005-04-22 15:22
var CYCLEUPPERTYPE_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("5LA","�ܶ�ⶥ"),
                                 new Array ("5LB","������ⶥ"),
                                 new Array ("5LC","�ʻ��ⶥ"),
                                 new Array ("5LD","����ⶥ")
                                );

//������Ϳ۷����� add by zhang.jing on 2005-04-22 15:22
var CYCLELOWERTYPE_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("5LA","�ܶ�ⶥ"),
                                 new Array ("5LB","������ⶥ"),
                                 new Array ("5LC","�ʻ��ⶥ"),
                                 new Array ("5LD","����ⶥ")
                                );


//������Ϳ۷����� add by zhang.jing on 2005-04-22 16:37
var BALANCETYPERULE_RULETYPE_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("8RA","��Чʱ��������"),
                                 new Array ("8RB","ʧЧʱ��������"),
                                 new Array ("8RC","������߿۷Ѽ������"),
                                 new Array ("8RD","������Ϳ۷Ѽ������")
                                );

//�ص�ͻ���־ add by zhang.jing on 2005-04-26 20.08
var ISVIP_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("T","�ص�ͻ�"),
                                 new Array ("F","���ص�ͻ�")
                                );

//���������� add by zhang.jing on 2005-04-27 09.38
var OBJTYPE_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("5BA","�ʻ�"),
                                 new Array ("5BB","�ͻ�"),
                                 new Array ("5BC","�û�")
                         );

//�������ʼ�� add by zhang.jing on 2005-04-27 09.38
var OBJID_SET=new Array(
                                 new Array ("","��ѡ��")
                         );


//����������� add by zhang.jing on 2005-04-27 11.19
var SOURCE_OPER_TYPE_SET2=new Array(
																 new Array ("","��ѡ��"),
                                 new Array("5UA","��"),
                                 new Array("5UB","���ת��"),
                                 new Array("5UC","��"),
                                 new Array("5UD","����"),
                                 new Array("5UE","�������")
                                );
//������Դ���� add by zhang.jing on 2005-04-27 11.19
var SOURCE_TYPE_SET2=new Array(
//                                 new Array ("","��ѡ��"),
                                 new Array ("5VA","�ֽ�Ԥ���"),
                                 new Array ("5VJ","֧ƱԤ���")
                               );


//�Ƿ�������ɽ� add by zhang.jing on 2005-06-06 19:44
var LATE_FEE_FLAG_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("T","�������ɽ�"),
                                 new Array ("F","���������ɽ�")
                               );

//���ݺϴ��־ add by zhang.jing on 2005-06-06 19:44
var PACK_BILL_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("T","�����ںϴ򵥾�"),
                                 new Array ("F","�����ڷִ򵥾�")
                               );

//����ģʽ add by zhang.jing on 2005-06-06 19:44
var CASH_MODE_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("0","����"),
                                 new Array ("1","����")
                               );

//������ݷ�ʽ add by zhang.jing on 2005-06-06 19:44
var OUTPUT_METHOD_SET=new Array(
                                 new Array ("","��ѡ��"),
                                 new Array ("0","Ʊ��"),
                                 new Array ("1","�����ļ�")
                               );
//�ʵ������ add by QIN.BIN on 2005-06-06 19:44
var CLASSIFY_SET=new Array(
                                 new Array ("55A","�����ʵ���"),
                                 new Array ("55B","�Ƿ����ʵ���")
                               );
//�������ȡ��ʽ
var TRANS_TYPE=new Array(new Array("1","������ȡ"),new Array("2","ȫ����ȡ"));
//���������з�ʽ
var TRANS_RUN_TYPE=new Array(new Array("1","ÿ��ָ��ʱ�䵥λ����"),new Array("2","�ֹ�����"));
//������ʱ������λ
var TRANS_TIME_UNIT=new Array(new Array("1","��"),new Array("2","��"),
                              new Array("3","��"),new Array("4","ʱ"),
                              new Array("5","��"),new Array("6","��"));
//�����������
var TRANS_PARAM_TYPE=new Array(new Array("8BA","����"),new Array("8BB","������"),
                               new Array("8BC","�ַ�����"),new Array("8BD","������"),
                               new Array("8BE","������"));
//��������Ƿ���Զ���
var TRANS_PARAM_IS_CUSTOMIZ=new Array(new Array("0","���ɶ���"),new Array("1","�ɶ���"),new Array("2","���붨��"));
//����֮��Ĺ�ϵ
var TRANS_RELATION_TYPE=new Array(new Array("0","������ϵ"),new Array("1","�����ϵ"));
//����������Ŀ�����ݴ洢��ʽ
var TRANS_STORE_TYPE=new Array(new Array("1","�ļ��洢"),new Array("2","���ݿ��洢"));
//���������ݳ�ȡ��ʽ
var TRANS_STYLE=new Array(new Array("1","��sql����ȡ"),new Array("2","���ƺ�̨�����ȡ"),new Array("3","�洢���̳�ȡ"));
//��������״̬
var TRANS_LOGSTATE=new Array(new Array("",""),new Array("1","������"),new Array("2","����ʧ��"),new Array("3","����ɹ�"));
//���������з�ʽ
var TRANS_RUN_TYPEEMPTY=new Array(new Array("",""),new Array("1","ÿ��ָ��ʱ�䵥λ����"),new Array("2","�ֹ�����"));
//����������
var TRANS_SUB_TRANSTYPE=new Array(new Array("0","����"),new Array("1","�ش���"));
//��������״̬
var TRANS_CMD_STATE=new Array(
                                 new Array ("1","δ����"),
                                 new Array ("2","����ɹ�"),
                                 new Array ("3","����ʧ��"),
                                 new Array ("4","��ʼ����"),
                                 new Array ("5","���ڴ���"),
                                 new Array ("6","���ݻ���")
                               );
var TRANS_CMD_DEALTYPE=new Array(
				new Array("1","���ݳ�ȡ"),
				new Array("2","����"),
				new Array("3","�ش���")
																 )                               
var TRANS_TARGET=new Array(
				new Array("0","��Ӫ�����������ӿ�"),
				new Array("1","�����ӿ�"),
				new Array("2","��Ӫ�����ӿ�"),
				new Array("3","����")
			  );	
//Ƿ��ҵ����������
var OWE_BUSINESSTYPE_NAME=new Array(new Array("0","�ȴ�����"),new Array("1","����ɹ�"),
                                  new Array("2","����ʧ��"),new Array("3","��������"),
                                  new Array("4","�����Ѿ���ȡ��״̬"),new Array("5","ȷ�ϳɹ�"),
                                  new Array("6","ȷ��ʧ��"));
//�������ò�����
var TRANS_PARAM_NAME= new Array(new Array("region_id","�����"),new Array("start_time","��ʼʱ��"),new Array("end_time","����ʱ��"),
                                new Array("event_type_id","�¼�����"),new Array("deal_date","����ʱ��"),
                                new Array("create_date","��������ʱ��"),new Array("billing_cycle_id","���ڱ�ʶ"));



//�޶�ƻ����ö��� add by zhangjing on 2005-06-17 15:34
var TARGETTYPE_SET = new Array(
									new Array ("","��ѡ��"),
									new Array ("A","�ʻ��޶�ƻ�"),
									new Array ("S","�û��޶�ƻ�")
								);



//�޶����� add by zhangjing on 2005-06-18 11:15
var SUMEVENTTYPE_SET = new Array(
									new Array ("","��ѡ��..."),
									new Array ("48A","һ�����¼�"),
									new Array ("48B","ʹ���¼�"),
									new Array ("48C","�������¼�"),
									new Array ("48D","�Ż��¼�")
								);

//���ֹ������ο����� add by ji.yangyang on 2005-6-30 8:34
var BONUS_REF_OBJECT = new Array(
									new Array ("0","����"),
									new Array ("1","����ʱ��\(����\)"),
									new Array ("2","���ö�"),
									new Array ("3","�ۼ�Ƿ������")
								);

//���ʷ�ʽ add by ji.yangyang on 2005-7-6 17:20
var ADJUST_TYPE = new Array(
									new Array ("1","�����"),
									new Array ("2","������")
								);

//����״̬ add by ji.yangyang on 2005-7-6 17:20
var ADJUST_STATE = new Array(
									new Array ("00A","����"),
									new Array ("00X","����")
								);


//�յ���ѡ�� add by zhangjing on 2005-07-19 13:50
var BLANK_SET = new Array(
									new Array ("","��ѡ��")
								);


//�ֶ��Ƿ񶨳� add by zhangjing on 2005-07-19 16:05
var FIX_LENGTH_FLAG_SET = new Array(
									new Array ("","��ѡ��"),
									new Array ("T","���ֶζ���"),
									new Array ("F","������")
								);


//�Ƿ���Ҫ�ļ�ͷ add by zhangjing on 2005-07-19 16:05
var FILE_HEADER_FLAG_SET = new Array(
									new Array ("","��ѡ��"),
									new Array ("T","��Ҫ�ļ�ͷ"),
									new Array ("F","����Ҫ�����ļ�ͷ")
								);


var FIELDTYPE_SET = new Array(
									new Array ("1","���"),
									new Array ("2","����"),
									new Array ("3","���л�ִ")
								);

//�����ʲ������� add by zhang.jing
var ADJUST_OPER_TYPE_SET = new Array(
									new Array ("","��ѡ��"),
									new Array("5UC","��(�����������)"),
									new Array("5UI","��(���ʼ������)")
								);


//������Ͷ���ʧЧʱ��ƫ������
var OFFSET_TYPE_SET = new Array(
									new Array ("","��ѡ��"),
									new Array("0","����ƫ��"),
									new Array("1","����ƫ��")
								);
								
//�������ݼ�����������
var DATASET_COMMAND_TYPE = new Array(
									new Array("0","SQL���"),
									new Array("1","�洢����")
								);
//��������
var FLOW_TYPE_SET=new Array(
									new Array("1","ʵʱ����"),
									new Array("2","����������")
								);
//����״̬
var FLOW_STATE_SET=new Array(
									new Array("00A","��Ч"),
									new Array("00B","������"),
									new Array("00X","��Ч")
								);
//������������
var TIME_TYPE_SET=new Array(
									new Array("001","Сʱ"),
									new Array("002","��")
								);
//�Ƿ��ظ�ִ��
var CYC_EXEC_SET=new Array(
									new Array("1","��Ҫ�ظ�ִ��"),
									new Array("2","�����ظ�����")
								);
//�ڵ����
var NODE_TYPE_SET=new Array(
									new Array("1","�׽ڵ�"),
									new Array("2","�м�ڵ�"),
									new Array("3","β�ڵ�")
								);
//�ڵ�ִ��ģʽ
var NODE_EXEC_MOD_SET=new Array(
									new Array("1","�Զ�"),
									new Array("2","�˹�")
								);
//�ڵ������ƶ���
var NODE_SERVICE_OWER_SET=new Array(
									new Array("1","��̨���ؽ���"),
									new Array("2","��������")
								);
//�жϽ������߼�
var RULE_RESULT_SET=new Array(
									new Array("1","ʹ���жϽ��Ϊ��"),
									new Array("0","ʹ���жϽ��Ϊ��")
								);
//������ڵ�Ӱ������
var NODE_FLOW_SET=new Array(
									new Array("1","ֻҪ��Ӧ������ڵ����������Ϳ���ִ����һ�ڵ�"),
									new Array("0","����Ҫ���е�����ڵ�������������ִ����һ�ڵ�")
								);

//���У����״̬
var AUDIT_RESULT_STAT = new Array(
	new Array("001","������"),
	new Array("002","�ɹ�����"),
	new Array("003","�쳣����")
);

//97�ӿڱ�
//�������״̬
var OPERATE_STATE = new Array(
				         new Array("","��ѡ��"),
				         new Array("0", "δ����"),
				         new Array("1", "�Ѵ���"),
				         new Array("2", "���ܴ���"),
				         new Array("5", "��δ����"),
				         new Array("6", "����")
							);
			
//������ص�ͻ���־
var SERVER_VIP_FLAG = new Array(
                   new Array ("", "��ѡ��"),
                   new Array ("T", "�ص�ͻ�"),
                   new Array ("F", "���ص�ͻ�")
                 );

//�ͻ����ص�ͻ���ʾ
var CUSTOMER_VIP_FLAG = new Array(
										new Array("", "��ѡ��"),
										new Array("10", "ȫ���Դ�ͻ�"),
										new Array("20", "ȫʡ�Դ�ͻ�"),
										new Array("30", "�������ͻ�")
									);

//�ͻ�����ϵ��ʾ
var CUSTOMER_CONNECT_FLAG = new Array(
												new Array("", "��ѡ��"),
												new Array("1", "�̶��绰")
											);

//�ͻ���������
var CUSTOMER_ACTION = new Array(
									new Array("", "��ѡ��"),
									new Array("01", "����"),
									new Array("02", "�޸�"),
									new Array("03", "ɾ��")
								);

//�ʻ���������
var ACCOUNT_ACTION = new Array(
									new Array("", "��ѡ��"),
									new Array("01", "�����ʻ�"),
									new Array("02", "���������ϵ"),
									new Array("03", "ɾ�������ϵ"),
									new Array("04", "�����������ϵ�����ʻ�"),
									new Array("05", "�����������ϵ�����ʻ�"),
									new Array("06", "�޸��ʻ���Ϣ")
								);

//�ʻ����ʵ�Ͷ�ݷ�ʽ
var ACCOUNT_MAIL_METHOD = new Array(
											new Array("", "��ѡ��"),
											new Array("001", "�ʼ�"),
											new Array("002", "����"),
											new Array("003", "E-Mail"),
											new Array("004", "��Ͷ��")
										);

//�ʻ����ʺ�ͬ������
var ACCOUNT_NUMBER_STYLE = new Array(
											new Array("01", "97��ͬ��"),
											new Array("02", "�Ʒ����ɺ�ͬ��"),
											new Array("03", "���ݵ绰����ѡ��")
										);
										
//��Դ��������
var PRODUCT_ACTION = new Array(
									new Array("", "��ѡ��"),
									new Array("01", "������Ʒ"),
									new Array("02", "���ٲ�Ʒ")
								);

//�Żݷ������ѱ�ʾ
var DISCT_FLAG = new Array(
							new Array("", "��ѡ��"),
							new Array("0", "δ����"),
							new Array("1", "������")
						);

//�Żݷ�����������
var DISCT_ACTION = new Array(
								new Array("", "��ѡ��"),
								new Array("01", "�����Żݹ���"),
								new Array("02", "ɾ���Żݹ���")
							);
							
//Ⱥ����
var GROUP_TYPE = new Array(
                                                           new Array("11A","VPNȺ"),
                                                           new Array("11B","CENTREXȺ"),
                                                           new Array("11C","������")
                                                        );

//Ⱥ״̬
 var ANTI_STATE_SET = new Array(
                                 new Array ("��Ч","00A"),
                                 new Array ("ע��","00X"),
                                 new Array ("�ѹ鵵","00H")
                                );
//���ʷ�ʽ add by xu.yiliang on 2005-11-20
var NODE_TYPE = new Array(
									new Array ("1","Ԥ������"),
									new Array ("2","���ػ���"),
									new Array ("3","���ۻ���"),
									new Array ("4","���ʻ���")
								);

//���۰�����������
var OBJECT_TYPE = new Array(
								new Array("80A", "��Ʒʵ��"),
								new Array("80B", "��Ʒ��ʵ��"),
								new Array("80C", "��Ʒʵ��"),
								new Array("80H", "�¼�"),
								new Array("80I", "�ͻ�"),
								new Array("80J", "�ʻ�"),
								new Array("80P", "ָ�����Ĳ�Ʒʵ���ܼ�"),
								new Array("80Q", "ָ�����Ĳ�Ʒ��ʵ���ܼ�"),
								new Array("80R", "ָ�������ʻ��ܼ�"),
								new Array("80U", "���۰����Ķ���")
							);

//���ۼƻ�����
var PRICING_PLAN_TYPE = new Array(
								new Array("20A", "�ʻ��࣬�������ʻ��Ż�"),
								new Array("20B", "�����࣬�����������Ż�"),
								new Array("20C", "�ͻ��࣬�����ڿͻ��Ż�"),
								new Array("20P", "��Ʒ�࣬�����ڲ�Ʒ����Ʒ������Ʒ�Ķ��ۼƻ��Ϳͻ����Ķ��ۼƻ�")
							);

//���ۼƻ�״̬
var PRICING_PLAN_STATE = new Array(
								new Array("00A", "��Ч"),
								new Array("00X", "ע��"),
								new Array("00H", "�ѹ鵵")
							);


//��Ʒ��ϸ����
var ELEMENT_TYPE = new Array(
								new Array("10A", "��Ʒ"),
								new Array("10B", "��Ʒ��"),
								new Array("10C", "��Ʒ"),
								new Array("10D", "���ۼƻ�")
							);							
//add by jiyangyang
//��������
var COUNTY_TYPE = new Array(
								new Array("0", "����"),
								new Array("1", "ũ��"),
								new Array("2", "����"),
								new Array("3", "ȫ����")
							);
							
//��������
var RESID_FLAG = new Array(
								new Array("0", "סլ"),
								new Array("1", "�칫"),
								new Array("2", "DID")
							);
//���ѷ�ʽ
var PAYMENT_METHOD_SELECT = new Array(
								new Array("11", "�ֽ�"),
								new Array("12", "֧Ʊ"),
								new Array("13", "ǰ̨POS"),
								new Array("20", "���ո���"),
								new Array("30", "���ÿ�"),
								new Array("99", "���")
							);


//add by xu.yiliang 2006-02-08
//�쳣�������
var SERV_ABNORMAL_TYPE=new Array(new Array("0","--�����쳣����--"),
									new Array("1","�޺���"),
									new Array("2","���û���"),
									new Array("3","�޺������û���"),
									new Array("4","��Ĭ���ʻ�"),
									new Array("5","���Ĭ���ʻ�"),
									new Array("6","Ĭ���ʻ������ϲ�һ��"),
									new Array("7","�ж�����Ч��¼")
								);

//add by xu.yiliang 2006-02-22
//����ִ��״̬
var TASK_DEAL_STATE=new Array(
									new Array("00A","�ȴ�����"),
									new Array("00B","������"),
									new Array("00D","����ɹ�"),
									new Array("00E","����ʧ��")
								);

var SERV_AUDIT_ITEM=new Array(new Array("0","--���з�����Ŀ--"),
									new Array("1","�̶���"),
									new Array("2","�л���"),
									new Array("3","����������"),
									new Array("4","��Ϣ��"),
									new Array("5","����������"),
									new Array("6","IP��������"),
									new Array("7","Ѱ����"),
									new Array("8","���ڳ�����"),
									new Array("9","���ʳ�����"),
									new Array("10","������")
								);


var SERV_AUDIT_STYLE=new Array(
									new Array("1",">="),
									new Array("2","<=")
								);

// add by yangsg  
//��������
var ACTION_SET = new Array(
                      new Array("","��ѡ��"),
                      new Array("A1","װ��"),
                      new Array("A5","�ƻ�"),
                      new Array("A8","���"),                    
                      new Array("B2","����"),
                      new Array("B3","ͣ��"),
                      new Array("B5","����"),                      
                      new Array("B7","��Ʒ��Ǩ") ,                     
                      new Array("C2","ͣ������"),
                      new Array("C5","��ǰͣ��"),
                      new Array("B6","�ĸ�����Ʒ"),
                      new Array("B1","�������ϵ"),
                      new Array("B9","�ۺ���Ϣ�޸�")
                       );


//add by xu.yiliang
//�绰����
var PHONE_TYPE= new Array(
new Array("0","Ԥ����"),
new Array("1","�󸶷�")
);