/**
 * bean set
 */
ZX.Tree = function(config) {
	this.htmlObject=null;
	this.width='';//'100%'
	this.height='';
	this.rootId=0;
	
	ZX.apply(this, config);
	this.dhtmlTree=new dhtmlXTreeObject(this.htmlObject,this.getWidth(),this.getHeight(),0);
	

	this.initTree();
};
ZX.extend(ZX.Tree, ZX.Basis, {
	enableCheckBoxes:false,
	skin:'dhx_skyblue',
	imagePath:SystemContext.appContextPath+'/public/widgets/tree/codebase/imgs/csh_dhx_skyblue/',
	im0:'folderClosed.gif',//没有子节点的时候，图片样式
	im1:'folderOpen.gif',//包含子节点，展开的时候图片样式
	im2:'folderClosed.gif',//包含子节点，打开的时候图片public/widgets/tree/codebase/dhtmlxtree.js
	leafImg:"leaf.gif",
	aCo1:'',// - 没有选中的结点的颜色
	sCol:'',//- 选中的结点的颜色
	style:'',//节点文本样式

	service:'',//buffalo service

	method:'',//service method



	initTree:function() {
			ZX.Tree.superclass.constructor.call(this);
			this.addEvents(
		          "beforeappend",
		          "append",

		          "beforeremove",
		          "remove",

		          "beforeload",
		          "load",

		          "beforeexpandnode",
		          "expandnode",

		          "beforeclick",
		          "click",

		          "checkchange",
		          "dblclick",

		          "beforerightclick",
		          "rightclick"
		       );
		       this.setSkin(this.skin);
		       this.setImagePath(this.imagePath);
		       this.enableCheckBoxes(this.enableCheckBoxes);



	},

	//设置buffalo 远程请求服务
	setService:function(service){
		this.service=service;
	},
	//设置buffalo 远程请求方法
	setMethod:function(method){
		this.method=method;
	},
	getWidth:function(){
		return ZX.isEmpty(this.width)?'100%':(this.width.indexOf('%')>-1?this.width:this.width+'%');
	},

	getHeight:function(){
		return ZX.isEmpty(this.height)?'100%':(this.height.indexOf('%')>-1?this.height:this.height+'%');
	},
	setSkin:function(skin){

		this.dhtmlTree.setSkin(skin);
	},
	//设置图片路径
	setImagePath:function(imagePath){
	 	this.dhtmlTree.setImagePath(imagePath);
	},
	//是否显示checkbox功能
	enableCheckBoxes:function(enable){
		this.dhtmlTree.enableCheckBoxes(enable);
	},
	//对某些特定的结点使用单选按钮(代替多选框)
	enableRadiobuttons:function(nodeId,enable){
		this.dhtmlTree.enableRadiobuttons(nodeId,enable);
	},
	/*
	@parentNodeId 父节点ID
	@nodeId 节点ID
	@nodeText 节点名称
	/*optionStr值类似SELECT,CALL,TOP,CHILD,CHECKED*/
		/**
		最后一个使用逗号分隔的参数可以是以下值(只能是大写):
		SELECT - 插入后选择此结点
		CALL - 在选择时调用方法
		TOP - 在最上方插入此结点
		CHILD - 此结点有子结点
		CHECKED - 此结点的多选框被选中(如果有的话)
	*/
	append:function(parentNodeId,nodeId,nodeText,data){
		
		var userData = data.userDatas;
		var isLeaf  = "false";
		if(data.isLeaf){
			isLeaf = data.isLeaf;
		}  
		if(userData &&(userData.isLeaf || userData.isLeaf =="true" )|| isLeaf =="true"){
			
			this.dhtmlTree.insertNewChild(parentNodeId,nodeId,nodeText,0,this.leafImg,this.im1,this.im2,null);
		}else
		{
			this.dhtmlTree.insertNewChild(parentNodeId,nodeId,nodeText,0,this.im0,this.im1,this.im2,null);
		}
		
		//this.dhtmlTree.closeAllItems(parentNodeId); //所有节点不展示
	},
	//设置鼠标点击事件
	click:function(fn){
		this.dhtmlTree.setOnClickHandler(fn);
	},
	//设置节点双击事件
	dbclick:function(fn){
		this.dhtmlTree.setOnDblClickHandler(fn);
	},
	//设置鼠标右键点击事件
	rightclick:function(fn){
		this.dhtmlTree.setOnRightClickHandler(fn)
	},
	beforecheck:function(fn){
		this.dhtmlTree.attachEvent("onBeforeCheck", fn);
		//return false;
	},
	//checkbox选中的事件触发函数
	check:function(fn){//checkbox点击事件

		this.dhtmlTree.setOnCheckHandler(fn);
	},
	//设置用户自定义数据
	setUserData:function(itemId,name,value){
		if(name && name!="_BUFFALO_OBJECT_CLASS_" && typeof(value) == "object"){
			name = value.name;
			value = value.value;
		}
		this.dhtmlTree.setUserData(itemId,name,value);
	},
	//获取用户自定义数据
	getUserData:function(itemId,name){
		return this.dhtmlTree.getUserData(itemId,name);
	},
	//获取用户自定义数据(全集合)
	getAllUserData:function(itemId){
		return this.dhtmlTree.getAllUserData(itemId);
	},
	//获取父节点id
	getParentId:function(itemId){
		return this.dhtmlTree.getParentId(itemId);
	},
	//判断是否有下级节点
	hasChildren:function(itemId){
		return this.dhtmlTree.hasChildren(itemId);
	},
	//设置节点选中
	setItemSelected:function(fn){
		this.dhtmlTree.setItemSelected(fn)
	},
	//更改节点id
	changeItemId:function(itemId,newItemId) {
		this.dhtmlTree.changeItemId(itemId,newItemId);
	},
	//获取节点下所有子节点id以逗号分割
	getSubItems:function(itemId){
		return this.dhtmlTree.getSubItems(itemId);
	},
	getAllSubItems:function(itemId){
		return this.dhtmlTree.getAllSubItems(itemId);
	},
	//获取当前被选中的节点id
	getSelectedItemId:function(){
		return this.dhtmlTree.getSelectedItemId();
	},
	//获取当前被选中的节点text
	getSelectedItemText:function(){
		return this.dhtmlTree.getSelectedItemText();
	},
	//设置某个节点为选中状态
	setCheck:function(itemId,checked){//state - checkbox state (0/1/unsure)
		 var state=checked?1:0;
		 this.dhtmlTree.setCheck(itemId,state);
	},
	//设置下级节点为选中状态
	setSubChecked:function(itemId,checked){//state - checkbox state (0/1/unsure)
		 var state=checked?1:0;
		 this.dhtmlTree.setSubChecked(itemId,state);
	},
	//判断节点是否为选中状态
	isItemChecked:function(itemId){//(0 - unchecked,1 - checked, 2 - third state)
		var state=this.dhtmlTree.isItemChecked(itemId);
		return (state==1)
	},
	//获取节点状态：0 - 没有子节点, -1 - 节点合拢, 1 - 节点展开
	getOpenState:function(itemId){
		var state=this.dhtmlTree.getOpenState(itemId);
		return  state;
	},
	//合拢当前节点（一节）
	closeItem:function(itemId){
		 this.dhtmlTree.closeItem(itemId);
	},
	//打开当前节点（一节）
	openItem:function(itemId){
		 this.dhtmlTree.openItem(itemId);
	},
	//删除所有的子节点
	deleteChildItems:function(itemId){
		this.dhtmlTree.deleteChildItems(itemId);
	},
	//删除某个节点，同时选中上级节点
	deleteItem:function(itemId,selectParent){
		this.dhtmlTree.deleteItem(itemId,selectParent);
	},
	//获取所有无子节点的节点id
	getAllChildless:function(){
		return this.dhtmlTree.getAllChildless();
	},
	//获取所有无子节点的节点id
	getAllLeafs:function(){
		return this.dhtmlTree.getAllLeafs();
	},
	//获取所有有子节点的节点id
	getAllItemsWithKids:function(){
		return this.dhtmlTree.getAllItemsWithKids();
	},

	//获取所有被选中节点id，不包括第三中状态的节点（部分选中的节点）
	getAllChecked:function(){
		return this.dhtmlTree.getAllChecked();
	},

	//获取所有被选中节点id，包括第三中状态的节点（部分选中的节点））
	getAllCheckedBranches:function(){
		return this.dhtmlTree.getAllCheckedBranches();
	},

	//获取节点下所有未选中的节点id，不传递itemId表示从根节点开始查找
	getAllUnchecked:function(itemId){
		return this.dhtmlTree.getAllUnchecked(itemId);
	},
	//远程加载数据
	load:function(parentId,parameter,afterCall){
		//var parameters=[];
		if(ZX.isEmpty(this.service)||ZX.isEmpty(this.method)){
			alert('查询树加载参数无效!');
			return;
		}

		if(ZX.isEmpty(parameter)){
			parameter=[];
		}
		var me=this;
		Ajax.getAsy().remoteCall(this.service, this.method, parameter, function(reply){
			var r=reply.getResult();
			if(ZX.isArray(r)){
				
				me.loadByArray(parentId,r,afterCall);
			}else if(ZX.isObject(r)){
				
				me.loadByObject(parentId,r,afterCall);
			}else if(ZX.isString(r)){
				
				me.loadByXMLString(parentId,r,afterCall);
			}
		});
	},
	//通过数组进行加载数据
	loadByArray : function(parentId,datas,afterCall){
			
		for(var i=0;i<datas.length;i++){
			var data=datas[i];
			
			this.append(parentId,data.id,data.text,data);
		

			var userData = data.userDatas;
			for(var p in userData){
				this.setUserData(data.id,p,userData[p]);
			}
		}
		if(ZX.isFunction(afterCall))
			afterCall();
		//append:function(parentNodeId,nodeId,nodeText,optionStr){
	},
	loadByObject : function(parentId,data,afterCall){
			this.loadByArray(parentId,[data],afterCall);
	},
	loadByXMLString:function(xmlString,afterCall){
			//通过结果数据
			afterCall();
	}
});
