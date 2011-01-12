ZX.VTypes = function() {
	var alpha = /^[a-zA-Z_]+/;
	var alphanum = /^[a-zA-Z0-9_]+$/;
	var email = /^(\w+)([-+.][\w]+)*@(\w[-\w]*\.){1,5}([A-Za-z]){2,4}$/;
	var url = /(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
	var time = /^([1-9]|1[0-9]):([0-5][0-9])(\s[a|p]m)$/i;
	var require = /.+/;
	var number = /^\d+$/;
	var integer = /^[-\+]?\d+$/;
	var double = /^[-\+]?\d+(\.\d+)?$/;

	return {
		'ip' : function(v) {
			return /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(v)
		},
		'ipText' : '����Ϊ����IP��ַ��ʽ:��127.0.0.1',

		'chinese' : function(v) {
			var regix = eval('/[^\u4e00-\u9fa5]/');
			return !regix.test(v + "");

		},
		'chineseText' : 'ֻ�����뺺��!',

		'require' : function(v) {
			return require.test(v)
		},
		'requireText' : '�������ݲ���Ϊ��!',
		'datetime' : function(v) {
			var a = v
					.match(/^(\d{0,4})-(\d{0,2})-(\d{0,2}) (\d{0,2}):(\d{0,2}):(\d{0,2})$/);
			if (a == null) {
				return false;
			}
			if (a[2] >= 13 || a[3] >= 32 || a[4] >= 24 || a[5] >= 60
					|| a[6] >= 60) {
				return false;
			}
			return true;
		},
		'datetimeText' : '����ʱ���ʽ����Ϊ0000-00-00 00:00:00 ����1999-11-10 20:30:29.',

		'date' : function(v) {
			var a = v.match(/^(\d{0,4})-(\d{0,2})-(\d{0,2})$/);
			if (a == null) {
				return false;
			}
			if (a[2] >= 13 || a[3] >= 32 || a[4] >= 24 || a[5] >= 60
					|| a[6] >= 60) {
				return false;
			}
			return true;
		},
		'dateText' : '����ʱ���ʽ����Ϊ0000-00-00  ����1999-11-10',

		'number' : function(v) {
			return number.test(v);
		},
		'numberText' : '���ָ�ʽ����ȷ',

		'integer' : function(v) {
			return integer.test(v);
		},
		'integerText' : '������ʽ����ȷ',

		'double' : function(v) {
			return double.test(v);
		},
		'doubleText' : 'С����д����ȷ',
		'time' : function(v) {
			var a = v.match(/^(\d{0,2}):(\d{0,2}):(\d{0,2})$/);
			if (a == null) {
				return false;
			}
			if (a[1].length < 2 || a[2].length < 2 || a[3].length < 2) {
				return false;
			}
			if (a[1] >= 24 || a[2] >= 60 || a[3] >= 60) {
				return false;
			}
			return true;
		},
		'timeText' : 'ʱ��ĸ�ʽ���봦��00:00:00��23:59:59֮�䣨ʱ���е�:����Ϊ��Ǹ�ʽ��.',

		'email' : function(v) {
			return email.test(v);
		},
		'emailText' : '�����ʽ����Ϊemal��:"user@example.com"',
		'emailMask' : /[a-z0-9_\.\-@]/i,
		'url' : function(v) {
			return url.test(v);
		},
		'urlText' : '�����ʽ����Ϊurl ��:"http:/' + '/www.example.com"',

		'alpha' : function(v) {
			return alpha.test(v);
		},
		'alphaText' : 'ֻ�ܰ�����ĸ�� _',
		'alphaMask' : /[a-z_]/i,
		'alphanum' : function(v) {
			return alphanum.test(v);
		},
		'idCard' : function(idcard) {

			var Errors = ["��֤ͨ��!", "���֤����λ������!", "���֤����������ڳ�����Χ���зǷ��ַ�!",
					"���֤����У�����!", "���֤�����Ƿ�!"];

			var area = {
				11 : "����",
				12 : "���",
				13 : "�ӱ�",
				14 : "ɽ��",
				15 : "���ɹ�",
				21 : "����",
				22 : "����",
				23 : "������",
				31 : "�Ϻ�",
				32 : "����",
				33 : "�㽭",
				34 : "����",
				35 : "����",
				36 : "����",
				37 : "ɽ��",
				41 : "����",
				42 : "����",
				43 : "����",
				44 : "�㶫",
				45 : "����",
				46 : "����",
				50 : "����",
				51 : "�Ĵ�",
				52 : "����",
				53 : "����",
				54 : "����",
				61 : "����",
				62 : "����",
				63 : "�ຣ",
				64 : "����",
				65 : "�½�",
				71 : "̨��",
				81 : "���",
				82 : "����",
				91 : "����"
			}

			var idcard, Y, JYM;
			var S, M;
			var idcard_array = [];
			idcard_array = idcard.split("");
			// ��������

			if (area[parseInt(idcard.substr(0, 2))] == null) {
				return Errors[4];
			}

			// ��ݺ���λ������ʽ����
			switch (idcard.length) {
				case 15 :
					if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
							|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
									.substr(6, 2)) + 1900)
									% 4 == 0)) {
						Ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// ���Գ������ڵĺϷ���
					} else {
						Ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// ���Գ������ڵĺϷ���
					}
					if (Ereg.test(idcard))

						return Errors[0];
					else
						return Errors[2];
					break;

				case 18 :
					// 18λ��ݺ�����
					// �������ڵĺϷ��Լ��
					// ��������:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
					// ƽ������:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
					if (parseInt(idcard.substr(6, 4)) % 4 == 0
							|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
									.substr(6, 4))
									% 4 == 0)) {
						Ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// ����������ڵĺϷ���������ʽ
					} else {
						Ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// ƽ��������ڵĺϷ���������ʽ
					}
					if (Ereg.test(idcard)) {// ���Գ������ڵĺϷ���
						// ����У��λ
						S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10]))
								* 7
								+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
								* 9
								+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
								* 10
								+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
								* 5
								+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
								* 8
								+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
								* 4
								+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
								* 2
								+ parseInt(idcard_array[7])
								* 1
								+ parseInt(idcard_array[8])
								* 6
								+ parseInt(idcard_array[9]) * 3;
						Y = S % 11;
						M = "F";
						JYM = "10X98765432";
						M = JYM.substr(Y, 1);// �ж�У��λ
						if (M == idcard_array[17])
							return Errors[0]; // ���ID��У��λ
						else
							return Errors[3];
					} else
						return Errors[2];
					break;

				default :
					return Errors[1];
					break;

			}
		},
		'alphanumText' : 'ֻ�ܰ�����ĸ�����ֻ� _',
		'alphanumMask' : /[a-z0-9_]/i
	};
}();