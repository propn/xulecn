/**
 * 此文件必须在index.js 加载之前加载 此文件中脚本说明： 文件中所有数据均来源于后台，与后台结合时由后台生成
 * 
 */
// 定义外观和个性化相关的参数
var sys_status = {
	styleName : 'default', // 风格样式名称
	animate : true, // 是否显示动画
	animateTime : 200, // 动画时间，单位为毫秒
	showBanner : false, // 是否显示banner
	showSmsBar : true, // 是否显示消息通知栏
	startMenuLock : false, // 开始菜单是否锁定
	menuPosition : 'left', // 可选择'left'、 'right'
	menuExpand : "01", // 默认展开的一级菜单项
	stateful : false
// 是否记忆任务栏已经打开的任务
};
// 登录信息，包括用户和机构信息
var loginInfo = {
	user : {
		uid : 1,
		userid : 'chenjs',
		username : '陈甲森'
	},
	org : {
		uid : 1,
		orgid : 'cx',
		orgname : '产品规划和体验部'
	}
};
// 门户切换下拉菜单
var portalMenu = [ {
	text : '知识管理系统',

	url : 'xxx'
}, {
	text : '合同管理系统',
	url : 'xxx'
}, {
	text : 'PM项目管理系统',
	url : 'xxx'
}, {
	text : '考勤系统',
	url : 'xxx'
} ];

// 系统设置下拉菜单
var settingMenu = [ {
	text : '外观和个性化',
	type : 'group',
	items : [ {
		text : '更换主题',
		url : 'xxx'
	}, {
		text : '自定义桌面',
		url : 'xxx'
	}, {
		text : '自定义开始菜单',
		url : 'xxx'
	}, {
		text : '自定义常用任务栏',
		url : 'xxx'
	} ]

}, {
	text : '用户账号',
	type : 'group',
	items : [ {
		text : '个人信息',
		url : 'xxx'
	}, {
		text : '密码修改',
		url : 'xxx'
	}, {
		text : '安全日志',
		url : 'xxx'
	} ]

}, {
	text : '系统设置',
	type : 'group',
	items : [ {
		text : '基础数据设置',
		url : 'xxx'
	} ]

} ];

// 帮助下拉菜单
var helpMenu = [ {
	text : '帮助',
	url : 'xxx'
}, {
	text : '客服',
	url : 'xxx'
}, {
	text : '技术支持',
	url : 'xxx'
}, {
	type : 'line'
}, {
	text : '问题反馈',
	url : 'xxx'
}, {
	text : '调查问卷',
	url : 'xxx'
}, {
	type : 'line'
}, {
	text : '新手模式',
	url : 'xxx'
}, {
	type : 'line'
}, {
	text : '关于本系统',
	url : 'xxx'
} ];

// 退出、注销菜单
var sysMenu = [ {
	text : '退出',
	url : 'xxx'
}, {
	text : '注销',
	url : 'xxx'
}, {
	type : 'line'
}, {
	text : '设置默认项',
	url : 'xxx'
} ];

// 开始菜单项
var func_array = [];
// 以关联数组的方式定义一、二、三级菜单，以m开头为一级，f开头为二三级，某些情况下二级菜单就是末级菜单
// 一级
func_array["m01"] = [ "信息交流", "message" ];// 说明：‘m01’为id，‘信息交流’为一级菜单显示的名称，‘message’为图标名称
// 二级
func_array["f1"] = [ "信息交流", "@message_exchange", "@message_exchange" ];// 说明：‘f1’为id，‘信息交流’为二级菜单显示的名称，第一个‘@message_exchange’为二级菜单编码，

// 三级
func_array["f11"] = [ "留言板", "message/message_exchange/leave_message",
		"message" ];// 说明：‘f11’为id，‘留言板’为三级菜单显示的名称，‘message/message_exchange/leave_message’为三级菜单路径，
func_array["f12"] = [ "网上调查", "message/message_exchange/check", "message" ];
func_array["f13"] = [ "通讯录", "message/message_exchange/communication",
		"message" ];
func_array["f14"] = [ "电子论坛", "message/message_exchange/forum", "message" ];
func_array["f15"] = [ "常用链接", "message/message_exchange/link", "message" ];
func_array["f16"] = [ "短信中心", "message/message_exchange/message_center",
		"message" ];
func_array["f17"] = [ "新闻中心", "message/message_exchange/news_center", "message" ];
func_array["f18"] = [ "公告中心", "message/message_exchange/announcement",
		"message" ];
func_array["f3"] = [ "信息管理", "@message_manage", "@message_manage" ];
func_array["f31"] = [ "留言板", "message/message_manage/leave_message_t",
		"message" ];
func_array["f32"] = [ "网上调查", "message/message_manage/check_t", "message" ];
func_array["f33"] = [ "通讯录", "message/message_manage/communication_t",
		"message" ];
func_array["f34"] = [ "电子论坛", "message/message_manage/forum_t", "message" ];
func_array["f35"] = [ "常用链接", "message/message_manage/link_t", "message" ];

func_array["m10"] = [ "个人事务", "personal" ];
func_array["f130"] = [ "个人事务", "@personal_work", "@personal_work" ];
func_array["f141"] = [ "待办事宜", "personal/personal_work/stay", "personal" ];
func_array["f132"] = [ "已办事宜", "personal/personal_work/then", "personal" ];
func_array["f133"] = [ "个人日程", "personal/personal_work/personal_schedule",
		"personal" ];
func_array["f5"] = [ "个人设置", "@personal_site", "@personal_site" ];
func_array["f51"] = [ "帐号维护", "personal/personal_site/account", "personal" ];
func_array["f52"] = [ "快捷方式", "personal/personal_site/quick", "personal" ];
func_array["f53"] = [ "用户组", "personal/personal_site/users", "personal" ];
func_array["f54"] = [ "默认身份", "personal/personal_site/default_status",
		"personal" ];
func_array["f131"] = [ "个人资料", "@personal_data", "@personal_data" ];
func_array["f142"] = [ "个人文件夹", "personal/personal_data/files", "personal" ];
func_array["f143"] = [ "个人名片夹", "personal/personal_data/card", "personal" ];
func_array["f134"] = [ "个人收藏夹", "personal/personal_data/collect", "personal" ];

func_array["m13"] = [ "行政事务", "erp" ];
func_array["f44"] = [ "领导日程", "@schedule", "@schedule" ];
func_array["f45"] = [ "查看日程", "erp/schedule/examine_schedule", "erp" ];
func_array["f46"] = [ "记录日程", "erp/schedule/record_schedule", "erp" ];
func_array["f47"] = [ "协调冲突日程", "erp/schedule/joint_schedule", "erp" ];
func_array["f48"] = [ "日程报表", "erp/schedule/schedule_report", "erp" ];
func_array["f64"] = [ "会议管理", "@meeting", "@meeting" ];
func_array["f75"] = [ "会议室登记", "erp/meeting/meeting_register", "erp" ];
func_array["f76"] = [ "会议室申请", "erp/meeting/meeting_apply", "erp" ];
func_array["f67"] = [ "分配会议室", "erp/meeting/meeting_assign", "erp" ];
func_array["f68"] = [ "会议信息", "erp/meeting/meeting_info", "erp" ];
func_array["f69"] = [ "设备和用品维护", "erp/meeting/device", "erp" ];
func_array["f65"] = [ "办公用品管理", "@official", "@official" ];
func_array["f651"] = [ "供应商维护", "erp/official/suppliers", "erp" ];
func_array["f652"] = [ "物品编号", "erp/official/itemnum", "erp" ];
func_array["f653"] = [ "入库登记", "erp/official/storage", "erp" ];
func_array["f654"] = [ "申请领用", "erp/official/recipients", "erp" ];
func_array["f655"] = [ "出库确认", "erp/official/export", "erp" ];
func_array["f656"] = [ "领用明细表", "erp/official/recipients_List", "erp" ];
func_array["f657"] = [ "金额明细表", "erp/official/amountlist", "erp" ];
func_array["f658"] = [ "入库台帐", "erp/official/in_accounting", "erp" ];
func_array["f659"] = [ "出库台帐", "erp/official/out_accounting", "erp" ];
func_array["f660"] = [ "库存台帐", "erp/official/save_accounting", "erp" ];
func_array["f66"] = [ "工作任务单", "@task", "@task" ];
func_array["f661"] = [ "任务管理", "erp/task/task_management", "erp" ];
func_array["f662"] = [ "我的任务", "erp/task/mytask", "erp" ];
func_array["f663"] = [ "任务查询", "erp/task/task_search", "erp" ];
func_array["f70"] = [ "车辆管理", "@car", "@car" ];
func_array["f701"] = [ "车辆类型", "erp/car/car_management", "erp" ];
func_array["f702"] = [ "车辆信息", "erp/car/car_infor", "erp" ];
func_array["f703"] = [ "车辆处理", "erp/car/treatment", "erp" ];
func_array["f704"] = [ "驾驶员信息", "erp/car/pilot", "erp" ];
func_array["f705"] = [ "费用类型", "erp/car/costtype", "erp" ];
func_array["f706"] = [ "车辆费用", "erp/car/car_cost", "erp" ];
func_array["f707"] = [ "预警管理", "erp/car/warning", "erp" ];
func_array["f708"] = [ "车辆申请", "erp/car/application", "erp" ];
func_array["f709"] = [ "分配车辆", "erp/car/distribution", "erp" ];
func_array["f710"] = [ "派车单管理", "erp/car/sentcar", "erp" ];
func_array["f711"] = [ "行车记录", "erp/car/record", "erp" ];
func_array["f712"] = [ "综合查询", "erp/car/general", "erp" ];
func_array["f713"] = [ "行车记录统计", "erp/car/statistics", "erp" ];

func_array["m20"] = [ "文档管理", "file" ];
func_array["f24"] = [ "资料库", "@material", "@material" ];
func_array["f241"] = [ "资料检索", "file/material/infor_search", "file" ];

func_array["m60"] = [ "公文处理", "document" ];
func_array["f162"] = [ "公文处理", "@document_handle", "@document_handle" ];
func_array["f173"] = [ "待办公文", "document/document_handle/stay_doc", "document" ];
func_array["f174"] = [ "新增公文", "document/document_handle/new_doc", "document" ];
func_array["f175"] = [ "已办公文", "document/document_handle/then_doc", "document" ];
func_array["f176"] = [ "经办公文", "document/document_handle/handling", "document" ];
func_array["f177"] = [ "归资料库", "document/document_handle/database",
		"ddocumentoc" ];
func_array["f178"] = [ "分发任务", "document/document_handle/distribution_task",
		"document" ];
func_array["f179"] = [ "传阅任务", "document/document_handle/circulation_doc",
		"document" ];
func_array["f163"] = [ "统计查询", "@statistical_inquire", "@statistical_inquire" ];
func_array["f184"] = [ "公文查询", "document/statistical_inquire/doc_search",
		"document" ];
func_array["f185"] = [ "公文台帐", "document/statistical_inquire/doc_accounting",
		"document" ];
func_array["f164"] = [ "公文管理", "@document_manage", "@document_manage" ];
func_array["f195"] = [ "流程监控", "document/document_manage/flow_monitor",
		"document" ];
func_array["f165"] = [ "基础设置", "@base_site", "@base_site" ];
func_array["f210"] = [ "常用词", "document/base_site/commonly", "document" ];
func_array["f211"] = [ "流程设置", "document/base_site/flow_set", "document" ];
func_array["f212"] = [ "印章签名", "document/base_site/seal", "document" ];
func_array["f213"] = [ "公文模版", "document/base_site/templates", "document" ];
func_array["f214"] = [ "内部单位", "document/base_site/internal", "document" ];
func_array["f215"] = [ "图片签名", "document/base_site/signature", "document" ];
func_array["f166"] = [ "督办管理", "@urge", "@urge" ];
func_array["f167"] = [ "任务督办", "document/urge/supervision", "document" ];
func_array["f168"] = [ "督办任务跟踪", "document/urge/tracking", "document" ];
func_array["f169"] = [ "超期任务统计", "document/urge/extended", "document" ];

func_array["m70"] = [ "集团应用", "clique" ];
func_array["f200"] = [ "集团应用", "@clique_clique", "@clique_clique" ];
func_array["f201"] = [ "公文交换管理", "clique/clique_clique/doc_exchange", "clique" ];

var shortcutArray = Array(1, 11, 12, 13, 14, 15, 16, 17, 18, 3, 31, 32, 33, 34,
		35, 130, 141, 132, 133, 5, 51, 52, 53, 54, 131, 142, 143, 134, 44, 45,
		46, 47, 48, 64, 75, 76, 67, 68, 69, 65, 651, 652, 653, 654, 655, 656,
		657, 658, 659, 660, 66, 661, 662, 663, 70, 701, 702, 703, 704, 705,
		706, 707, 708, 709, 710, 711, 712, 713, 24, 241, 162, 173, 174, 175,
		176, 177, 178, 179, 163, 184, 185, 164, 195, 165, 210, 211, 212, 213,
		214, 215, 166, 167, 168, 169, 200, 201);

// -- 一级菜单 开头加上m等于sys_function.js文件中的func_array中一级菜单的id --
var first_array = [ "01", "10", "13", "20", "60", "70" ];
// -- 二级菜单 --
var second_array = [];
second_array["m01"] = [ "1", "3" ];
second_array["m10"] = [ "130", "5", "131" ];
second_array["m13"] = [ "44", "64", "65", "66", "70" ];
second_array["m20"] = [ "24" ];
second_array["m60"] = [ "162", "163", "164", "165", "166" ];
second_array["m70"] = [ "200" ];

// -- 三级菜单 --
var third_array = [];
third_array["f1"] = [ "11", "12", "13", "14", "15", "16", "17", "18" ];
third_array["f3"] = [ "31", "32", "33", "34", "35" ];
third_array["f130"] = [ "141", "132", "133" ];
third_array["f5"] = [ "51", "52", "53", "54" ];
third_array["f131"] = [ "142", "143", "134" ];
third_array["f44"] = [ "45", "46", "47", "48" ];
third_array["f64"] = [ "75", "76", "67", "68", "69" ];
third_array["f65"] = [ "651", "652", "653", "654", "655", "656", "657", "658",
		"659", "660" ];
third_array["f66"] = [ "661", "662", "663" ];
third_array["f70"] = [ "701", "702", "703", "704", "705", "706", "707", "708",
		"709", "710", "711", "712", "713" ];
third_array["f24"] = [ "241" ];
third_array["f162"] = [ "173", "174", "175", "176", "177", "178", "179" ];
third_array["f163"] = [ "184", "185" ];
third_array["f164"] = [ "195" ];
third_array["f165"] = [ "210", "211", "212", "213", "214", "215" ];
third_array["f166"] = [ "167", "168", "169" ];
third_array["f200"] = [ "201" ];
