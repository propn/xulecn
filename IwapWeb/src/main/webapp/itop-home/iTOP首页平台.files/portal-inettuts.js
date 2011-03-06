
$(function() {
	$('#columns').portal({
		items : [{
			items : [{
				title : '个人事务',
				items : [{
					title : '我的日程',
					selected : true,
					content : '1.2010-10-09拜访中铁五局陈经理<br>2.2010-10-09拜访中铁五局陈经理<br>3.2010-10-09拜访中铁五局陈经理<br>4.2010-10-09拜访中铁五局陈经理<br>5.2010-10-09拜访中铁五局陈经理<br>6.2010-10-09拜访中铁五局陈经理<br>7.2010-10-09拜访中铁五局陈经理<br>'
				}, {
					title : '代办事项',
					url : 'http://www.toone.com.cn'
				}, {
					title : '部门事件',
					url : 'http://www.google.cn'
				}]
			}, {
				title : '合同管理',
				items : [{
					title : '最近合同',
					selected : true,
					content : '我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程我的日程'
				}, {
					title : '应收账款',
					url : 'http://www.toone.com.cn'
				}, {
					title : '大合同',
					url : 'http://www.google.cn'
				}]
			}]
		}, {
			items : [{
						title : '图表展示',
						items : [{
									title : '折线图',
									selected : true,
									content : '图表展示'
								}, {
									title : '柱形图',
									url : 'http://www.toone.com.cn'
								}, {
									title : '饼图',
									url : 'http://www.google.cn'
								}]
					}, {
						title : '代办流程',
						items : [{
									title : 'erp',
									selected : true,
									content : '代办流程'
								}, {
									title : 'crm',
									url : 'http://www.toone.com.cn'
								}, {
									title : 'pm',
									url : 'http://www.google.cn'
								}]
					}]
		}, {
			items : [{
						title : '数据列表',
						items : [{
									title : '数据列表1',
									selected : true,
									content : '数据列表'
								}, {
									title : '数据列表2',
									url : 'http://www.toone.com.cn'
								}, {
									title : '数据列表3',
									url : 'http://www.google.cn'
								}]
					}, {
						title : '物质管理',
						items : [{
									title : '物质管理',
									selected : true,
									content : '物质管理'
								}, {
									title : '物质管理',
									url : 'http://www.toone.com.cn'
								}, {
									title : '物质管理',
									url : 'http://www.google.cn'
								}]
					}]
		}]
	});

});