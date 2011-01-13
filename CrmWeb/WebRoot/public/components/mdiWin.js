function mywin()
{
	this.winlist = [];   //�����б�
	this.maxWins = 8;				//��󴰿���
	this.tagTitleWidth = 148;		//��ǩ���
	this.indentWidth = 10;			//��ǩ�������
	this.currentwin = null;
	this.addwin = addwin;                        //�½����ڷ���
	this.refreshwin = refreshwin;                //ˢ�´���
	this.removewin = removewin;                        //�Ƴ�����
	this.removeall = removeall;                                        //�Ƴ����д���
	this.activewin = activewin;                        //�����
	this.container = container;
	this.padLeft = padLeft;					// ���������Ե�ľ���
	this.padRight = padRight;				// �������ұ�Ե�ľ���
	this.scrollWidth = scrollWidth;

	function refreshwin(obj)
	{
		if(obj == null)return;
		if(0<obj.index && obj.index<window.frames.length){
		    try{
				window.frames[obj.index].location.reload();
			}
			catch(e){
			    initAlternateData() ;
			    window.frames[obj.index].location = obj.url;
			}
		}
	}
	function container(url,title)
	{
		for(var i=0;i<this.winlist.length;i++)
		{
			if(this.winlist[i].title == title && this.winlist[i].url == url)
			{
				return i;
			}
		}
		return -1;
	}
	function activewin(oEl)                 //�����
	{
		//����̨�з���ʱ���ܹرմ���
		if(!checkFeeWin(this.currentwin.url, this.currentwin)) return false;
		
		if(oEl == null){
			this.currentwin = null;
			return
		}
		//�ͻ���ͼ
		if(this.currentwin.url.indexOf("custinfo")>-1){
			var currUrl = oEl.url;
			if(currUrl.indexOf("View.jsp")>-1){
				var currWin = window.frames[oEl.index];
				if (currWin &&currWin.document){
					var oldCustId = currWin.custId; 
					if(oldCustId!=custId){
						currWin.queryData();
					}
				}
			}
		}
		//������������ģ���ˢ�¹��ﳵ������--���Ǵ�ҳ��ʵ������ת������
		if(this.currentwin.url.indexOf("ManualFeeMgt.jsp")>-1){
			var currUrl = oEl.url;
			if(currUrl.indexOf("custService.jsp")>-1){
				var currWin = window.frames[oEl.index];
				if (currWin &&currWin.document){
					var shopCartDataset = currWin.document.all("shoppingCartTab");
					if(shopCartDataset){
						if(shopCartDataset.getDataset()){
							shopCartDataset.getDataset().reloadData();
						}
					}
				}
			}
		}
		
		var tempzindex = this.currentwin.style.zIndex;
		this.currentwin.wintitle.style.zIndex = this.currentwin.index;
		//this.currentwin.style.display = "none";
		this.currentwin.wintitle.style.backgroundImage = 'url(../public/skins/bsn/multiwin/tab1.gif)';
		this.currentwin.style.zIndex = 0;
		oEl.wintitle.style.zIndex = tempzindex;
		//oEl.style.display = "";
		oEl.wintitle.style.backgroundImage = 'url(../public/skins/bsn/multiwin/tab2.gif)';
		oEl.style.zIndex = this.maxWins+1;		
		this.currentwin = oEl;

		//���������ʾ������
		var mleft = parseInt(titlelist.style.marginLeft);
		if (isNaN(mleft))
			mleft = 0;
		var padleft = this.padLeft(oEl);
		var padright = this.padRight(oEl);
		var clientwidth = titlelist.parentElement.clientWidth
		if(padleft + mleft > clientwidth)
		{
			titlelist.style.marginLeft = clientwidth - padleft;			
		}
		if(padright < clientwidth && mleft < 0)
		{
			mleft = clientwidth - this.scrollWidth();
			if(mleft>0)
				mleft = 0;
			titlelist.style.marginLeft = mleft;
		}
		if(padleft + mleft < this.tagTitleWidth)
		{
			titlelist.style.marginLeft = - (padleft - this.tagTitleWidth);
		}
		
	}

	function padLeft(oEl)
	{
		var padleft = oEl.index * this.tagTitleWidth - this.indentWidth*(oEl.index-1);
		return padleft;
	}

	function padRight(oEl)
	{
		var count = (this.winlist.length - oEl.index) + 1;
		var padright = this.tagTitleWidth * count - this.indentWidth*(count-1);
		return padright;
	}

	function addwin(url,title,code)                                        //�����ľ���ʵ��
	{
		//����̨�з���ʱ���ܹرմ���
		if (this.currentwin)
			if(!checkFeeWin(this.currentwin.url, this.currentwin)) return false;
			
		var con = this.container(url,title);
		if(con>-1)
		{
			this.activewin(this.winlist[con]);
			return;
		}
		if(this.winlist.length >= this.maxWins)
		{
			alert("��󴰿�������"+this.maxWins+"�������ȹرղ��ִ����ٴ򿪡�");
			return false;
		}
		
		if(this.currentwin){
		    this.currentwin.style.zIndex = 0;
		}
		else{
		    mywindowsLayer.style.zIndex = this.maxWins;
		}

		oDIV = window.document.createElement( "TABLE" );
		mywindows.insertAdjacentElement( "beforeEnd" , oDIV );
		this.winlist[this.winlist.length] = oDIV;                //���б�����Ӵ������
		oDIV.url = url;
		oDIV.title = title;
		oDIV.index = this.winlist.length;
		oDIV.className = "win";
		oDIV.width = "100%";
		oDIV.height = "100%";
		oDIV.cellSpacing=0;
		
		var iframe = document.createElement("<iframe src='"+url+"' class = 'win1' width='100%' height='100%' frameborder='0' scrolling='no' allowTransparency='true'></iframe>");
		oDIV.insertRow().insertCell().appendChild(iframe); 
		iframe.code = code;
		
		var oTitle = window.document.createElement( "SPAN" );
		titlelist.insertAdjacentElement( "beforeEnd" , oTitle );
		oTitle.className ='wintitle';
		oTitle.style.width = this.tagTitleWidth;
		oTitle.style.backgroundImage = 'url(../public/skins/bsn/multiwin/tab2.gif)';
		oTitle.style.left = this.winlist.length == 1 ? 0 : this.winlist[this.winlist.length-2].wintitle.style.pixelLeft - this.indentWidth;
		oTitle.title = title;
		title = subStr(title,16);
		oTitle.innerHTML= title == null ? "unkown windows" : title;
		oTitle.win=oDIV;
		oTitle.onclick = new Function("win.activewin(this.win)");
		oTitle.ondblclick = new Function("win.removewin(win.currentwin)");

		if(this.currentwin != null) {
			this.currentwin.wintitle.style.backgroundImage = 'url(../public/skins/bsn/multiwin/tab1.gif)';
			//this.currentwin.style.display = "none";
			this.currentwin.wintitle.style.zIndex = this.currentwin.index;
		}
		oDIV.style.zIndex = this.maxWins+1;
		oTitle.style.zIndex = this.maxWins+1;
		oDIV.wintitle = oTitle;		

		var scrollwidth = this.scrollWidth();
		if(scrollwidth > titlelist.parentElement.clientWidth)
		{
			titlelist.style.marginLeft = titlelist.parentElement.clientWidth - scrollwidth;
		}
		

		this.currentwin = oDIV;
		return oDIV;
	}

	function scrollWidth()
	{
		var n = this.winlist.length;
		var scrollwidth = this.tagTitleWidth*n - this.indentWidth*(n-1);
		return scrollwidth;
	}

	function removewin(obj)        //�Ƴ�����
	{
		//����̨�з���ʱ���ܹرմ���
		if(!checkFeeWin(obj.url, obj)) return false;
		
		if(obj == null)return;
		var temparr = [];
		var afterwin = false;
		for(var i=0;i<this.winlist.length;i++)
		{
			if(afterwin) this.winlist[i].wintitle.style.left =  this.winlist[i].wintitle.style.pixelLeft + this.indentWidth;
			if(this.winlist[i] != obj)
				temparr[temparr.length] = this.winlist[i];
			else
				afterwin = true;
		}
		this.winlist = temparr;
		if(this.currentwin == obj){
			this.activewin(this.winlist[this.winlist.length-1]);
		}
		if(0<obj.index && obj.index<window.frames.length){
			window.frames[obj.index].location = "blank.htm";
		}
		obj.wintitle.win = null;		
		obj.wintitle.removeNode(true) ;
		obj.wintitle = null;
		obj.removeNode(true) ;
		obj = null;

	}

	function removeall()        //�Ƴ����д���
	{
		var wincount = this.winlist.length;
		for(var i=wincount-1;i>=0;i--){
			if (this.removewin(this.winlist[i])==false)
				return;
		}
	}
}

function tabScroll(direction)
{
	tabScrollStop();
	direction == "right" ? tabMoveRight() : tabMoveLeft();
}

function tabMoveRight()
{
	tabMove("right",30);
	timer=setTimeout(tabMoveRight,10);
}

function tabMoveLeft()
{
	tabMove("left",30);
	timer=setTimeout(tabMoveLeft,10);
}

function tabScrollStop()
{
	clearTimeout(timer);
	timer = null;
}

function tabMove(direction,speed)
{
	var mleft = parseInt(titlelist.style.marginLeft);
	if (isNaN(mleft))
		mleft = 0;
	if(direction=="right")
	{
		if(titlelist.parentElement.clientWidth >= titlelist.parentElement.scrollWidth)
		{
			tabScrollStop();
			return;
		}
		else
		{
			titlelist.style.marginLeft = mleft - speed;
		}
	}
	else
	{
		if(mleft + speed >=0)
		{
			titlelist.style.marginLeft = 0;
			tabScrollStop();
			return;
		}
		else
		{
			titlelist.style.marginLeft = mleft + speed;
		}
	}
}
var timer = null;
var win = new mywin();                        //�½�����
var wins = [];
//ȫ�ֵĿͻ�ID
var custId ="";
var custName ="";

function AddWin(Url,Title,Code)
{
	Url = LocalAction.getLocalMenuURL(Url);
	wins[wins.length] = win.addwin(Url, Title, Code);                        //��Ӵ��壻
}

function subStr(str,len)
{
	var strlength=0;
	var newstr = "";
	for (var i=0;i<str.length;i++)
	{
		if(str.charCodeAt(i)>=1000)
			strlength += 2;
		else
			strlength += 1;
		if(strlength > len)
		{
			newstr += "...";
			break;
		}
		else
		{
			newstr += str.substr(i,1);
		}
	}
	return newstr;
}
//����̨�з���ʱ���ܹرմ���
function checkFeeWin(url, obj){

/*
	if( url.indexOf( "FeeManagement.jsp") > 0 ){
		var currWin = window.frames[obj.index];
		if (!currWin || !currWin.document)return true;
		var objText = currWin.document.getElementById('text_datasetChargeValitor_text_Fee');
		var confirm = currWin.confirmFlag;
		if (objText!=null && confirm!=null){
			var textFee = objText.value;
			if (confirm == 0 && textFee!=null&& textFee!="" && (parseFloat(textFee)-0!=0)){
				  window.alert("���ò�Ϊ0,������������̨��ʾ�ķ��ý����շ�,Ȼ�����շѰ�ť!");
					return false;					 
			}
		}
	}*/
	return true;
}


