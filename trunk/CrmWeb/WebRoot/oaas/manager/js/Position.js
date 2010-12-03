function page_onLoad(){
	initPostion();
}

//��ʼ��TreeList�����������ʾ
function initPostion(){
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		var queryResult = reply.getResult() ;
		document.all.positionTreeView.loadByXML(queryResult);

		//����ϸ����Ӧ��Dataset�ϲ���һ���ռ�¼
		positionInfo.insertRecord(null);
		globleItem = document.all.positionTreeView.items[0]; 
		fillValues(globleItem);
	}
	
	ajaxCall.remoteCall("PrivilegeService","getPositionList",[],callBack);
}

//����һ�е���Ϣ����ϸ��Ϣ���
function fillValues(item){
	for(var ele in item){
		if(positionInfo.getField(ele)){
			positionInfo.setValue(ele, item[ele]);
		}
	}
}

//���ĵ���¼�������ϸ�������ʾ��ǰ�����еļ�¼����ϸ��Ϣ
function clickPosition(){
	var positionTree = document.all.positionTreeView;
	var item = positionTree.selectedItem;
	
	cleanValues() ;
	fillValues(item);
	positionInfo.setReadOnly("true");
	actionType = "";
}

//�����ϸ����ϵ���Ϣ
function cleanValues(){
	positionInfo.clearData();
	positionInfo.insertRecord(null);
}

var actionType = "";

//���Ӹ�λ
function addPosition_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�Ĳ��������Ӹ�λ!") ;
		return ;
	}
	actionType = "insert" ;
	positionInfo.setReadOnly(false);	
	cleanValues();
}

//�༭��λ��Ϣ
function editPosition_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�Ĳ����ٱ༭Ա��!") ;
		return ;
	}
	actionType = "update" ;
	positionInfo.setReadOnly(false);
}

//��ȡ��ǰ��ѡ�е�Ȩ�޼�¼��id
function getCurrentPositionId(){
	var positionTree = document.all.positionTreeView;
	var selItem = positionTree.selectedItem;
	if( selItem != null ){
		return selItem.positionId ;
	}else{
		return null ;
	}
}

//ɾ����λ��Ϣ
function deletePosition_onClick(){
	if( actionType != "" ){
		alert("���ȱ������ȡ����ǰ�Ĳ�����ɾ��Ա��!") ;
		return ;
	}
	if( !confirm( "��ȷ��Ҫɾ����ǰ��λ��¼��?" )){
		return ;
	}
		var currentPositionId = getCurrentPositionId();
		
		var ajaxCall = new NDAjaxCall(true);
		
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;
			if( deleteResult ){
				deletePositionFromTreeList();
				alert("ɾ����λ�ɹ���");
			}else{
				alert("��λ�Ѿ�������ʹ��,ɾ����λʧ��!" );
			}
		}
		
		ajaxCall.remoteCall("PrivilegeService","deletePosition",[currentPositionId],callBack);
}

//��TreeList��ɾ��һ����¼
function deletePositionFromTreeList(){
	var positionTree = document.all.positionTreeView;
	var selItem = positionTree.selectedItem;
	selItem.remove();
}

function btn_cancel_onClick(){
	actionType = "";
	positionInfo.setReadOnly(true);	
	clickPosition();
}

function btn_commitPosition_onClick(){
	if( actionType == "" ){
		return ;
	}
	
	if( !$("positionForm" ).validate()){
		return ;
	}
	var position = new Position();

	//��Dataset��������ݸ�ֵ���˵�����
	for(var ele in position){  
		if(positionInfo.getField(ele)){
			position[ele] = positionInfo.getValue( ele );
		} 
	}
	
	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( actionType == "insert" ){//���Ӳ���
			//�������˷��������ӵ�Ȩ�޵�ID
			refreshDatainfo(result);//��������Ժ󣬱������³�ʼ��������Dataset�������Ӧ��Ϣ				
			addPositionToTreeList();	 
			alert("���Ӹ�λ�ɹ���");	 
		}else if ( actionType == "update" ){//�༭����
			refreshTreeList();
			alert("�༭��λ�ɹ�!");
		}
		positionInfo.setReadOnly(true);	
		actionType = "";
	}
	
	var arr = new Array();
	arr[0] = position;
	
	if( actionType == "insert" ){//���Ӳ���
		ajaxCall.remoteCall("PrivilegeService","insertPosition",arr,callBack);
	}else if ( actionType == "update" ){//�༭����
		ajaxCall.remoteCall("PrivilegeService","updatePosition",arr,callBack);
	}
}

function refreshDatainfo(id){
	positionInfo.setValue("positionId", id ) ;
}

//��Update��ǰ�ĸ�λ�Ժ󣬸���TreeList�϶�Ӧ�ļ�¼
function refreshTreeList(){
	var positionTree = document.all.positionTreeView;
	var position = positionTree.selectedItem;
	
	for(var ele in position){
		if(positionInfo.getField(ele)){
			position[ele] = positionInfo.getValue( ele );
		} 
	}
	
	position.refresh();
}

//��TreeList������һ����¼ 
function addPositionToTreeList(){
	var positionTree = document.all.positionTreeView;
	var position = positionTree.createTreeNode();
	var position1 = new Position() ;
	
	for( var ele in position1 ){
		if(positionInfo.getField(ele)){
			position[ele] = positionInfo.getValue( ele ) ;
		}
	}
	positionTree.add(position);
	position.setSelected(); 
}

function Position(){
	this.positionId = "";
	this.positionName  = "";
	this.positionDesc = "";
	this.state = "";
	this.stateDate  = "";
}