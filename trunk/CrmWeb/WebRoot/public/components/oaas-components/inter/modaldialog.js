
function __createLayer(left,top,width,height,html)
{
	var divElm = document.createElement("DIV");
	divElm.style.visibility = "hidden";
	divElm.style.left = left;
	divElm.style.top = top;
	divElm.style.width = width;
	divElm.style.height =height;
	divElm.style.position = "absolute";
	document.body.appendChild(divElm);
	divElm.innerHTML = html;
	return divElm;
}


function __createDialog(left,top,width,height,html,title)
{
	var divhtml = "<table border=0 width=100% height=100% style='border-style:outset;border-width:1' cellPadding=0 cellSpacing=0><tr><td width='100%' height='20px'>"
	divhtml += "<table border=0 style='background-color:#0A246A' width='100%' height='100%'><tr><td width='100%' height='100%' id=_title>"+
		(title==null?"":("<B><FONT color=white>"+title+"</FONT></B>"))+
		"</td><td><img id='closeImg' src='"+g_imageUrl+"/icon_close.gif' border='1px' style='background-color:lightgrey' onclick='this._Dialog.Close();'></td></tr></table>";
	divhtml += "</td></tr><tr><td id='cellToInsertHtml' valign='top' align='left' style='background-color:lightgrey'>"+html+"</td></tr></table>";
	var divElm = __createLayer(left,top,width,height,divhtml);
	return divElm;
}


// Make an object visible
function showObject(elm) 
{
        elm.style.visibility = "visible";
}

// Hides an object
function hideObject(elm) 
{
        elm.style.visibility = "hidden";
}

function createOverlayLayer()
{
	var divElm = document.createElement("DIV");
	divElm.style.visibility = "visible";
	divElm.style.left = 0;
	divElm.style.top = 0;
	divElm.style.width = document.body.scrollWidth;
	divElm.style.height = document.body.scrollHeight;
	divElm.style.position = "absolute";
	divElm.style.background = "url("+g_imageUrl+"/transoverlay1.gif) repeat";
	document.body.appendChild(divElm);
	return divElm;
	
}

function __getCenter()
{
	var clientHeight;
	var clientWidth;
	var docTop;
	var docLeft;
	
    clientHeight = document.body.clientHeight;
    clientWidth = document.body.clientWidth;
    docTop = document.body.scrollTop;
    docLeft = document.body.scrollLeft;
    
    var loc = new Object();
    loc.x = docLeft + clientWidth/2;
    loc.y = docTop + clientHeight/2;
    return loc;
}

function DMD_Display(html,title)
{
	if (!this.dvo)
		this.dvo = createOverlayLayer();
	
	if (!this.dv)
	{
		this.dv = __createDialog(0,0,this.width,this.height,html,title);
		this.dv.all["closeImg"]._Dialog = this;		
	}
	else
	{
		this.dv.all["cellToInsertHtml"].innerHTML = html;
		this.dv.all["_title"].innerHTML = (title==null?"":("<B><FONT color=white>"+title+"</FONT></B>"));
	}
	
	var loc = __getCenter();
	this.dv.style.left = loc.x - this.width/2;
	this.dv.style.top = loc.y - this.height/2;

	this.ShowLayers(true);
}


function DMD_ShowLayers(show)
{
	if (show)
	{
		showObject(this.dv);
		showObject(this.dvo);
	}
	else
	{
		hideObject(this.dv);
		hideObject(this.dvo);	
	}
}

function DMD_Close()
{
	this.ShowLayers(false);
	if(this.onClose!=null)
		this.onClose();
}

function DHTMLModalDialog(width,height)
{
	this.width = width;
	this.height = height;
	
	this.dv = null;
	this.dvo = null;
	
	this.Close = DMD_Close;
	this.ShowLayers = DMD_ShowLayers;
	this.Display = DMD_Display
	
	this.onClose = null;
}
