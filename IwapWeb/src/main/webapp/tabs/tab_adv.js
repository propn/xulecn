
var index = 0;
	function addTab(){
		index++;
		$('#header').tabs('add',{
			title:'New Tab ' + index,
			content:'Tab Body ' + index,
			iconCls:'icon-save'
		},2);
	}
	/*
	 * 获取选中的标签
	 * */
	function getSelected(){
		var tab = $('#tt').tabs('getSelected');
		alert('Selected: '+tab.panel('options').title);
	}
	function update(){
		index++;
		var tab = $('#tt').tabs('getSelected');
		$('#tt').tabs('update', {
			tab: tab,
			options:{
				title:'new title'+index,
				iconCls:'icon-save'
			}
		});
	}
/*设置标签名称*/
	function setTitle(){
		$('#header').tabs('setTitle','修改过的标题');
	
	}
		$(function(){
			$("#header").tabs({
				//bc : '#tab-body',
				title:'测试标签页',
				items:[
					{
						title:'desktop',
						closable:false,
						//selected:true,
						content:'tab 1'
					},{
						title:'toone',
						//selected:true,
						url:'http://www.toone.com.cn'
					},{
						title:'google',
						url:'http://www.google.com'
					}
				]
			});
			//$("#tabs").tabs('add',{});
		});