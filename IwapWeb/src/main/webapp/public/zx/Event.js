	//�¼�����
ZX.Event=function(name){
		this.name=name;
		this.eventfns=new Array();	
}
	//�����¼��ļ���ִ�к���
ZX.Event.prototype.addEventFn=function(fn){
		this.eventfns.push(fn);
}
//��ȡ�¼�������
ZX.Event.prototype.getEventName=function(){
	return this.name;
}
	//�����¼�
	//{data}�����¼�ʱ������������
ZX.Event.prototype.fireEvent=function(data){
		for(var key in this.eventfns){
				this.eventfns[key](data);			
		}		
}