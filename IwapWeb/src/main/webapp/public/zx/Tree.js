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
	im0:'folderClosed.gif',//û���ӽڵ��ʱ��ͼƬ��ʽ
	im1:'folderOpen.gif',//�����ӽڵ㣬չ����ʱ��ͼƬ��ʽ
	im2:'folderClosed.gif',//�����ӽڵ㣬�򿪵�ʱ��ͼƬpublic/widgets/tree/codebase/dhtmlxtree.js
	leafImg:"leaf.gif",
	aCo1:'',// - û��ѡ�еĽ�����ɫ
	sCol:'',//- ѡ�еĽ�����ɫ
	style:'',//�ڵ��ı���ʽ

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

	//����buffalo Զ���������
	setService:function(service){
		this.service=service;
	},
	//����buffalo Զ�����󷽷�
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
	//����ͼƬ·��
	setImagePath:function(imagePath){
	 	this.dhtmlTree.setImagePath(imagePath);
	},
	//�Ƿ���ʾcheckbox����
	enableCheckBoxes:function(enable){
		this.dhtmlTree.enableCheckBoxes(enable);
	},
	//��ĳЩ�ض��Ľ��ʹ�õ�ѡ��ť(�����ѡ��)
	enableRadiobuttons:function(nodeId,enable){
		this.dhtmlTree.enableRadiobuttons(nodeId,enable);
	},
	/*
	@parentNodeId ���ڵ�ID
	@nodeId �ڵ�ID
	@nodeText �ڵ�����
	/*optionStrֵ����SELECT,CALL,TOP,CHILD,CHECKED*/
		/**
		���һ��ʹ�ö��ŷָ��Ĳ�������������ֵ(ֻ���Ǵ�д):
		SELECT - �����ѡ��˽��
		CALL - ��ѡ��ʱ���÷���
		TOP - �����Ϸ�����˽��
		CHILD - �˽�����ӽ��
		CHECKED - �˽��Ķ�ѡ��ѡ��(����еĻ�)
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
		
		//this.dhtmlTree.closeAllItems(parentNodeId); //���нڵ㲻չʾ
	},
	//����������¼�
	click:function(fn){
		this.dhtmlTree.setOnClickHandler(fn);
	},
	//���ýڵ�˫���¼�
	dbclick:function(fn){
		this.dhtmlTree.setOnDblClickHandler(fn);
	},
	//��������Ҽ�����¼�
	rightclick:function(fn){
		this.dhtmlTree.setOnRightClickHandler(fn)
	},
	beforecheck:function(fn){
		this.dhtmlTree.attachEvent("onBeforeCheck", fn);
		//return false;
	},
	//checkboxѡ�е��¼���������
	check:function(fn){//checkbox����¼�

		this.dhtmlTree.setOnCheckHandler(fn);
	},
	//�����û��Զ�������
	setUserData:function(itemId,name,value){
		if(name && name!="_BUFFALO_OBJECT_CLASS_" && typeof(value) == "object"){
			name = value.name;
			value = value.value;
		}
		this.dhtmlTree.setUserData(itemId,name,value);
	},
	//��ȡ�û��Զ�������
	getUserData:function(itemId,name){
		return this.dhtmlTree.getUserData(itemId,name);
	},
	//��ȡ�û��Զ�������(ȫ����)
	getAllUserData:function(itemId){
		return this.dhtmlTree.getAllUserData(itemId);
	},
	//��ȡ���ڵ�id
	getParentId:function(itemId){
		return this.dhtmlTree.getParentId(itemId);
	},
	//�ж��Ƿ����¼��ڵ�
	hasChildren:function(itemId){
		return this.dhtmlTree.hasChildren(itemId);
	},
	//���ýڵ�ѡ��
	setItemSelected:function(fn){
		this.dhtmlTree.setItemSelected(fn)
	},
	//���Ľڵ�id
	changeItemId:function(itemId,newItemId) {
		this.dhtmlTree.changeItemId(itemId,newItemId);
	},
	//��ȡ�ڵ��������ӽڵ�id�Զ��ŷָ�
	getSubItems:function(itemId){
		return this.dhtmlTree.getSubItems(itemId);
	},
	getAllSubItems:function(itemId){
		return this.dhtmlTree.getAllSubItems(itemId);
	},
	//��ȡ��ǰ��ѡ�еĽڵ�id
	getSelectedItemId:function(){
		return this.dhtmlTree.getSelectedItemId();
	},
	//��ȡ��ǰ��ѡ�еĽڵ�text
	getSelectedItemText:function(){
		return this.dhtmlTree.getSelectedItemText();
	},
	//����ĳ���ڵ�Ϊѡ��״̬
	setCheck:function(itemId,checked){//state - checkbox state (0/1/unsure)
		 var state=checked?1:0;
		 this.dhtmlTree.setCheck(itemId,state);
	},
	//�����¼��ڵ�Ϊѡ��״̬
	setSubChecked:function(itemId,checked){//state - checkbox state (0/1/unsure)
		 var state=checked?1:0;
		 this.dhtmlTree.setSubChecked(itemId,state);
	},
	//�жϽڵ��Ƿ�Ϊѡ��״̬
	isItemChecked:function(itemId){//(0 - unchecked,1 - checked, 2 - third state)
		var state=this.dhtmlTree.isItemChecked(itemId);
		return (state==1)
	},
	//��ȡ�ڵ�״̬��0 - û���ӽڵ�, -1 - �ڵ��£, 1 - �ڵ�չ��
	getOpenState:function(itemId){
		var state=this.dhtmlTree.getOpenState(itemId);
		return  state;
	},
	//��£��ǰ�ڵ㣨һ�ڣ�
	closeItem:function(itemId){
		 this.dhtmlTree.closeItem(itemId);
	},
	//�򿪵�ǰ�ڵ㣨һ�ڣ�
	openItem:function(itemId){
		 this.dhtmlTree.openItem(itemId);
	},
	//ɾ�����е��ӽڵ�
	deleteChildItems:function(itemId){
		this.dhtmlTree.deleteChildItems(itemId);
	},
	//ɾ��ĳ���ڵ㣬ͬʱѡ���ϼ��ڵ�
	deleteItem:function(itemId,selectParent){
		this.dhtmlTree.deleteItem(itemId,selectParent);
	},
	//��ȡ�������ӽڵ�Ľڵ�id
	getAllChildless:function(){
		return this.dhtmlTree.getAllChildless();
	},
	//��ȡ�������ӽڵ�Ľڵ�id
	getAllLeafs:function(){
		return this.dhtmlTree.getAllLeafs();
	},
	//��ȡ�������ӽڵ�Ľڵ�id
	getAllItemsWithKids:function(){
		return this.dhtmlTree.getAllItemsWithKids();
	},

	//��ȡ���б�ѡ�нڵ�id��������������״̬�Ľڵ㣨����ѡ�еĽڵ㣩
	getAllChecked:function(){
		return this.dhtmlTree.getAllChecked();
	},

	//��ȡ���б�ѡ�нڵ�id������������״̬�Ľڵ㣨����ѡ�еĽڵ㣩��
	getAllCheckedBranches:function(){
		return this.dhtmlTree.getAllCheckedBranches();
	},

	//��ȡ�ڵ�������δѡ�еĽڵ�id��������itemId��ʾ�Ӹ��ڵ㿪ʼ����
	getAllUnchecked:function(itemId){
		return this.dhtmlTree.getAllUnchecked(itemId);
	},
	//Զ�̼�������
	load:function(parentId,parameter,afterCall){
		//var parameters=[];
		if(ZX.isEmpty(this.service)||ZX.isEmpty(this.method)){
			alert('��ѯ�����ز�����Ч!');
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
	//ͨ��������м�������
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
			//ͨ���������
			afterCall();
	}
});
