/*
 * ���ܣ�����һ������ִ�������ѵ�ʱ�� 
 * ע�⣺�벻Ҫ������onunload����֮ǰ���ø÷���; ���������������ô�����
 * �ֹ�����save������������ٽ��
 */ 
function TimeLogger(allAjaxCall, globalName, globalComments)
{
	this.records = [];
	this.ajaxCalls = {}; // class.method = comments
	this.allAjaxCall = allAjaxCall||false;
	this.globalName = globalName;
	this.globalComments = globalComments;
	// �������
	this.callDepth = 0;
	this.globalId = this.beg(this.globalName,  this.globalComments);
	// �ڹرմ��ڵ�ʱ�򱣴�����
	var oldunload = window.onbeforeunload;
	var timeLogger = this;
	if(oldunload == null || oldunload == undefined){
		window.onbeforeunload =  function()
		{
			timeLogger.save();
		}; 
		return;
	}	
	// ���ԭ���Ѿ�����Ӧ���¼������������ǲ����ƻ������ȵ���Ӧ�ó���ģ���Ϊ���ajax���������⣬����������±��ƻ�
	window.onbeforeunload = function()
	{
		timeLogger.save();
		oldunload.apply(null, arguments);
	};
}

TimeLogger.isExtAjaxCall = false;

TimeLogger.prototype = {
	// ��ʼһ�μ�¼
	beg : function(name, comments)
	{
		var id = this.records.length;
		this.records[id] = {name : name, comments: comments, begTime : new Date(), callDepth :"" + this.callDepth, duaring : "0"};
		return id;
	},
	// ����һ�μ�¼
	end : function (id){
		//this.records[id].endTime = new Date();
		this.records[id].duaring = "" + ( (new Date()).getTime() - this.records[id].begTime.getTime());
		delete this.records[id].begTime;
	},
	// �������ݵ������, �첽����, ��ʱ����Ҳ�����κδ���
	save : function()
	{
		this.end(this.globalId);
		function callBack(reply){}
		NDAjaxCall.getAsyncInstance().remoteCall("TimeLogger", "save", [this.records], callBack);
		this.records = [];	
		this.globalId = this.beg(this.globalName, this.globalComments);
	},
	// ��չ��̬��ȫ�ֺ���
	extGlbFn : function(root, name, comments)
	{
		var timeLogger = this;
		var fn = root[name];
		root[name] = function()
		{
			timeLogger.callDepth = timeLogger.callDepth  + 1;
			var id = timeLogger.beg(name, comments);
			var ret = fn.apply(null, arguments);
			timeLogger.end(id);
			timeLogger.callDepth = timeLogger.callDepth - 1;			
			return ret;
		};
	},
	// ��չ���ͳ�Ա����
	extObjFn : function(obj, name, comments)
	{
		var timeLogger = this;
		var fn = obj[name];
		obj[name] = function()
		{
			timeLogger.callDepth = timeLogger.callDepth  + 1;
			var id = timeLogger.beg(name, comments);
			var ret = fn.apply(obj, arguments);
			timeLogger.end(id);
			timeLogger.callDepth = timeLogger.callDepth - 1;			
			return ret;
		};
	},
	// ��չ���Ա����, ���ඨ������չ
	extMemFn : function(cls, name, comments)
	{
		var timeLogger = this;
		var fn = cls.prototype[name];
		cls.prototype[name] = function()
		{
			timeLogger.callDepth = timeLogger.callDepth  + 1;
			var id = timeLogger.beg(name, comments);
			var ret = fn.apply(this, arguments);
			timeLogger.end(id);
			timeLogger.callDepth = timeLogger.callDepth - 1;		
			return ret;	
		};
	},
	
	extAJAXCallOnPageLoad : function()
	{
		var old_page_onLoad = window.page_onLoad;
		var timeLogger = this;
		window.page_onLoad = function()
		{
			timeLogger.extAJAXCall();
			if (old_page_onLoad != undefined && old_page_onLoad != null) return old_page_onLoad();
		};
	},
	
	// ��չAJAX��remoteCall������ ���ص����������õ�ʱ����Ϊ�Ѿ������ˣ���ȶȲ�������
	extAJAXCall : function()
	{
		// ֻ����չһ�Σ������һ��ʵ����չ�ˣ���ô�ڶ���ʵ���Ͳ�����չ
		if (TimeLogger.isExtAjaxCall ) return;
		TimeLogger.isExtAjaxCall = true;
		
		var timeLogger = this;
		var oldFunc = NDAjaxCall.prototype.remoteCall;
		
		NDAjaxCall.prototype.remoteCall =  function()
		{
			var cls = arguments[0];
			var fnc = arguments[1];
			if (!timeLogger.allAjaxCall){
				var axcs = timeLogger.ajaxCalls;
				if (axcs[cls] == undefined || axcs[cls] == null){
					return oldFunc.apply(this, arguments);
				}
				
				var mayICall = axcs[cls]["allFunction"]||axcs[cls][fnc];				
				if (mayICall == undefined || mayICall == null){
					return oldFunc.apply(this, arguments);
				}	
		
				var comments =  axcs[cls][fnc]||axcs[cls]["allFunction"]||"";
			}else {
				var comments = "";
			}
			
			var name = "AJAXCall:" + cls + "." + fnc;
			var callInstance = {};
			callInstance.id = timeLogger.beg(name+".server", comments);
			callInstance.callBack = arguments[3]; 
			arguments[3] = function()
			{
				// ��ɼ�¼����˵Ĵ���ʱ��TimeLogger�ı���Ҳ�ǵ�����AJAX�ģ���һ�β��������ڡ� ��save��ʱ���Ѿ�������ˡ�
				if (timeLogger.records[callInstance.id] != undefined && timeLogger.records[callInstance.id] != null)
					timeLogger.end(callInstance.id);
				// ��¼�ص�����(�ͻ���)�Ĵ���ʱ��
				var clientDealId = timeLogger.beg(name+".client", comments)	
				var ret = callInstance.callBack.apply(null, arguments);
				timeLogger.end(clientDealId);
				return ret;
			};		
			return oldFunc.apply(this, arguments);
		};
	},
	// ��һ��ajax������ӵ������б���, ���func == allFunction, ��ʾ��cls���еķ���������
	addAJAXCall : function(cls, func, comments)
	{
		if (this.ajaxCalls[cls] == undefined || this.ajaxCalls[cls] == null)
			this.ajaxCalls[cls] = {};
		this.ajaxCalls[cls][func] = comments||"";
	}
};