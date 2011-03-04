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
		'ipText' : '必须为数字IP地址格式:如127.0.0.1',

		'chinese' : function(v) {
			var regix = eval('/[^\u4e00-\u9fa5]/');
			return !regix.test(v + "");

		},
		'chineseText' : '只能输入汉字!',

		'require' : function(v) {
			return require.test(v)
		},
		'requireText' : '必填内容不能为空!',
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
		'datetimeText' : '日期时间格式必须为0000-00-00 00:00:00 例如1999-11-10 20:30:29.',

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
		'dateText' : '日期时间格式必须为0000-00-00  例如1999-11-10',

		'number' : function(v) {
			return number.test(v);
		},
		'numberText' : '数字格式不正确',

		'integer' : function(v) {
			return integer.test(v);
		},
		'integerText' : '整数格式不正确',

		'double' : function(v) {
			return double.test(v);
		},
		'doubleText' : '小数填写不正确',
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
		'timeText' : '时间的格式必须处于00:00:00和23:59:59之间（时间中的:必须为半角格式）.',

		'email' : function(v) {
			return email.test(v);
		},
		'emailText' : '输入格式必须为emal如:"user@example.com"',
		'emailMask' : /[a-z0-9_\.\-@]/i,
		'url' : function(v) {
			return url.test(v);
		},
		'urlText' : '输入格式必须为url 如:"http:/' + '/www.example.com"',

		'alpha' : function(v) {
			return alpha.test(v);
		},
		'alphaText' : '只能包含字母或 _',
		'alphaMask' : /[a-z_]/i,
		'alphanum' : function(v) {
			return alphanum.test(v);
		},
		'idCard' : function(idcard) {

			var Errors = ["验证通过!", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!",
					"身份证号码校验错误!", "身份证地区非法!"];

			var area = {
				11 : "北京",
				12 : "天津",
				13 : "河北",
				14 : "山西",
				15 : "内蒙古",
				21 : "辽宁",
				22 : "吉林",
				23 : "黑龙江",
				31 : "上海",
				32 : "江苏",
				33 : "浙江",
				34 : "安徽",
				35 : "福建",
				36 : "江西",
				37 : "山东",
				41 : "河南",
				42 : "湖北",
				43 : "湖南",
				44 : "广东",
				45 : "广西",
				46 : "海南",
				50 : "重庆",
				51 : "四川",
				52 : "贵州",
				53 : "云南",
				54 : "西藏",
				61 : "陕西",
				62 : "甘肃",
				63 : "青海",
				64 : "宁夏",
				65 : "新疆",
				71 : "台湾",
				81 : "香港",
				82 : "澳门",
				91 : "国外"
			}

			var idcard, Y, JYM;
			var S, M;
			var idcard_array = [];
			idcard_array = idcard.split("");
			// 地区检验

			if (area[parseInt(idcard.substr(0, 2))] == null) {
				return Errors[4];
			}

			// 身份号码位数及格式检验
			switch (idcard.length) {
				case 15 :
					if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
							|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
									.substr(6, 2)) + 1900)
									% 4 == 0)) {
						Ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
					} else {
						Ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
					}
					if (Ereg.test(idcard))

						return Errors[0];
					else
						return Errors[2];
					break;

				case 18 :
					// 18位身份号码检测
					// 出生日期的合法性检查
					// 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
					// 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
					if (parseInt(idcard.substr(6, 4)) % 4 == 0
							|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
									.substr(6, 4))
									% 4 == 0)) {
						Ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
					} else {
						Ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
					}
					if (Ereg.test(idcard)) {// 测试出生日期的合法性
						// 计算校验位
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
						M = JYM.substr(Y, 1);// 判断校验位
						if (M == idcard_array[17])
							return Errors[0]; // 检测ID的校验位
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
		'alphanumText' : '只能包含字母，数字或 _',
		'alphanumMask' : /[a-z0-9_]/i
	};
}();