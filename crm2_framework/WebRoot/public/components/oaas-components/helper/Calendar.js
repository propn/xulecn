
//==================================================== ²ÎÊýÉè¶¨²¿·Ö =======================================================
var bMoveable=true;		//ÉèÖÃÈÕÀúÊÇ·ñ¿ÉÒÔÍÏ¶¯
var _VersionInfo="Version:2.0&#13;2.0×÷Õß:walkingpoison&#13;1.0×÷Õß: F.R.Huang(meizz)&#13;MAIL: meizz@hzcnc.com"	//°æ±¾ÐÅÏ¢

//==================================================== WEB Ò³ÃæÏÔÊ¾²¿·Ö =====================================================
var strFrame;		//´æ·ÅÈÕÀú²ãµÄHTML´úÂë
document.writeln('<iframe id=meizzDateLayer Author=wayx frameborder=0 style="position: absolute; width: 144; height: 211; z-index: 9998; display: none"></iframe>');
strFrame='<style>';
strFrame+='INPUT.button{BORDER-RIGHT: #ff9900 1px solid;BORDER-TOP: #ff9900 1px solid;BORDER-LEFT: #ff9900 1px solid;';
strFrame+='BORDER-BOTTOM: #ff9900 1px solid;BACKGROUND-COLOR: #fff8ec;font-family:ËÎÌå;}';
strFrame+='TD{FONT-SIZE: 9pt;font-family:ËÎÌå;}';
strFrame+='</style>';
strFrame+='<scr' + 'ipt>';
strFrame+='var datelayerx,datelayery;	/*´æ·ÅÈÕÀú¿Ø¼þµÄÊó±êÎ»ÖÃ*/';
strFrame+='var bDrag;	/*±ê¼ÇÊÇ·ñ¿ªÊ¼ÍÏ¶¯*/';
strFrame+='function document.onmousemove()	/*ÔÚÊó±êÒÆ¶¯ÊÂ¼þÖÐ£¬Èç¹û¿ªÊ¼ÍÏ¶¯ÈÕÀú£¬ÔòÒÆ¶¯ÈÕÀú*/';
strFrame+='{if(bDrag && window.event.button==1)';
strFrame+='	{var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+='		DateLayer.posLeft += window.event.clientX-datelayerx;/*ÓÉÓÚÃ¿´ÎÒÆ¶¯ÒÔºóÊó±êÎ»ÖÃ¶¼»Ö¸´Îª³õÊ¼µÄÎ»ÖÃ£¬Òò´ËÐ´·¨ÓëdivÖÐ²»Í¬*/';
strFrame+='		DateLayer.posTop += window.event.clientY-datelayery;}}';
strFrame+='function DragStart()		/*¿ªÊ¼ÈÕÀúÍÏ¶¯*/';
strFrame+='{var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+='	datelayerx=window.event.clientX;';
strFrame+='	datelayery=window.event.clientY;';
strFrame+='	bDrag=true;}';
strFrame+='function DragEnd(){		/*½áÊøÈÕÀúÍÏ¶¯*/';
strFrame+='	bDrag=false;}';
strFrame+='</scr' + 'ipt>';
strFrame+='<div style="z-index:9999;position: absolute; left:0; top:0;" onselectstart="return false"><span id=tmpSelectYearLayer Author=wayx style="z-index: 9999;position: absolute;top: 3; left: 19;display: none"></span>';
strFrame+='<span id=tmpSelectMonthLayer Author=wayx style="z-index: 9999;position: absolute;top: 3; left: 78;display: none"></span>';
strFrame+='<table border=1 cellspacing=0 cellpadding=0 width=142 height=160 bordercolor=#ff9900 bgcolor=#ff9900 Author="wayx">';
strFrame+='  <tr Author="wayx"><td width=142 height=23 Author="wayx" bgcolor=#FFFFFF><table border=0 cellspacing=1 cellpadding=0 width=140 Author="wayx" height=23>';
strFrame+='      <tr align=center Author="wayx"><td width=16 align=center bgcolor=#ff9900 style="font-size:12px;cursor: hand;color: #ffffff" ';
strFrame+='        onclick="parent.meizzPrevM()" title="ÏòÇ°·­ 1 ÔÂ" Author=meizz><b Author=meizz>&lt;</b>';
strFrame+='        </td><td width=60 align=center style="font-size:12px;cursor:default" Author=meizz ';
strFrame+='onmouseover="style.backgroundColor=\'#FFD700\'" onmouseout="style.backgroundColor=\'white\'" ';
strFrame+='onclick="parent.tmpSelectYearInnerHTML(this.innerText.substring(0,4))" title="µã»÷ÕâÀïÑ¡ÔñÄê·Ý"><span Author=meizz id=meizzYearHead></span></td>';
strFrame+='<td width=48 align=center style="font-size:12px;cursor:default" Author=meizz onmouseover="style.backgroundColor=\'#FFD700\'" ';
strFrame+=' onmouseout="style.backgroundColor=\'white\'" onclick="parent.tmpSelectMonthInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))"';
strFrame+='        title="µã»÷ÕâÀïÑ¡ÔñÔÂ·Ý"><span id=meizzMonthHead Author=meizz></span></td>';
strFrame+='        <td width=16 bgcolor=#ff9900 align=center style="font-size:12px;cursor: hand;color: #ffffff" ';
strFrame+='         onclick="parent.meizzNextM()" title="Ïòºó·­ 1 ÔÂ" Author=meizz><b Author=meizz>&gt;</b></td></tr>';
strFrame+='    </table></td></tr>';
strFrame+='  <tr Author="wayx"><td width=142 height=18 Author="wayx">';
strFrame+='<table border=1 cellspacing=0 cellpadding=0 bgcolor=#ff9900 ' + (bMoveable? 'onmousedown="DragStart()" onmouseup="DragEnd()"':'');
strFrame+=' BORDERCOLORLIGHT=#FF9900 BORDERCOLORDARK=#FFFFFF width=140 height=20 Author="wayx" style="cursor:' + (bMoveable ? 'move':'default') + '">';
strFrame+='<tr Author="wayx" align=center valign=bottom><td style="font-size:12px;color:#FFFFFF" Author=meizz>ÈÕ</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF" Author=meizz>Ò»</td><td style="font-size:12px;color:#FFFFFF" Author=meizz>¶þ</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF" Author=meizz>Èý</td><td style="font-size:12px;color:#FFFFFF" Author=meizz>ËÄ</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF" Author=meizz>Îå</td><td style="font-size:12px;color:#FFFFFF" Author=meizz>Áù</td></tr>';
strFrame+='</table></td></tr><!-- Author:F.R.Huang(meizz) http://www.meizz.com/ mail: meizz@hzcnc.com 2002-10-8 -->';
strFrame+='  <tr Author="wayx"><td width=142 height=120 Author="wayx">';
strFrame+='    <table border=1 cellspacing=2 cellpadding=0 BORDERCOLORLIGHT=#FF9900 BORDERCOLORDARK=#FFFFFF bgcolor=#fff8ec width=140 height=120 Author="wayx">';
var n=0; for (j=0;j<5;j++){ strFrame+= ' <tr align=center Author="wayx">'; for (i=0;i<7;i++){
strFrame+='<td width=20 height=20 id=meizzDay'+n+' style="font-size:12px" Author=meizz onclick=parent.meizzDayClick(this.innerText,0)></td>';n++;}
strFrame+='</tr>';}
strFrame+='      <tr align=center Author="wayx">';
for (i=35;i<39;i++)strFrame+='<td width=20 height=20 id=meizzDay'+i+' style="font-size:12px" Author=wayx onclick="parent.meizzDayClick(this.innerText,0)"></td>';
strFrame+='        <td colspan=3 align=right Author=meizz><span onclick=parent.closeLayer() style="font-size:12px;cursor: hand"';
strFrame+='         Author=meizz title="' + _VersionInfo + '"><u>¹Ø±Õ</u></span>&nbsp;</td></tr>';
strFrame+='    </table></td></tr><tr Author="wayx"><td Author="wayx">';
strFrame+='        <table border=0 cellspacing=1 cellpadding=0 width=100% Author="wayx" bgcolor=#FFFFFF>';
strFrame+='          <tr Author="wayx"><td Author=meizz align=left><input Author=meizz type=button class=button value="<<" title="ÏòÇ°·­ 1 Äê" onclick="parent.meizzPrevY()" ';
strFrame+='             onfocus="this.blur()" style="font-size: 12px; height: 20px"><input Author=meizz class=button title="ÏòÇ°·­ 1 ÔÂ" type=button ';
strFrame+='             value="< " onclick="parent.meizzPrevM()" onfocus="this.blur()" style="font-size: 12px; height: 20px"></td><td ';
strFrame+='             Author=meizz align=center><input Author=meizz type=button class=button value=Today onclick="parent.meizzToday()" ';
strFrame+='             onfocus="this.blur()" title="µ±Ç°ÈÕÆÚ" style="font-size: 12px; height: 20px; cursor:hand"></td><td ';
strFrame+='             Author=meizz align=right><input Author=meizz type=button class=button value=" >" onclick="parent.meizzNextM()" ';
strFrame+='             onfocus="this.blur()" title="Ïòºó·­ 1 ÔÂ" class=button style="font-size: 12px; height: 20px"><input ';
strFrame+='             Author=meizz type=button class=button value=">>" title="Ïòºó·­ 1 Äê" onclick="parent.meizzNextY()"';
strFrame+='             onfocus="this.blur()" style="font-size: 12px; height: 20px"></td>';
strFrame+='</tr></table></td></tr></table></div>';

window.frames.meizzDateLayer.document.writeln(strFrame);
window.frames.meizzDateLayer.document.close();		//½â¾öie½ø¶ÈÌõ²»½áÊøµÄÎÊÌâ

//==================================================== WEB Ò³ÃæÏÔÊ¾²¿·Ö ======================================================
var outObject;
var outButton;		//µã»÷µÄ°´Å¥
var outDate="";		//´æ·Å¶ÔÏóµÄÈÕÆÚ
var odatelayer=window.frames.meizzDateLayer.document.all;		//´æ·ÅÈÕÀú¶ÔÏó
function setday(tt,obj) //Ö÷µ÷º¯Êý
{
	if (arguments.length >  2){alert("¶Ô²»Æð£¡´«Èë±¾¿Ø¼þµÄ²ÎÊýÌ«¶à£¡");return;}
	if (arguments.length == 0){alert("¶Ô²»Æð£¡ÄúÃ»ÓÐ´«»Ø±¾¿Ø¼þÈÎºÎ²ÎÊý£¡");return;}
	var dads  = document.all.meizzDateLayer.style;
	var th = tt;
	var ttop  = tt.offsetTop;     //TT¿Ø¼þµÄ¶¨Î»µã¸ß
	var thei  = tt.clientHeight;  //TT¿Ø¼þ±¾ÉíµÄ¸ß
	var tleft = tt.offsetLeft;    //TT¿Ø¼þµÄ¶¨Î»µã¿í
	var ttyp  = tt.type;          //TT¿Ø¼þµÄÀàÐÍ
	while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}
	dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+6;
	dads.left = tleft;
	outObject = (arguments.length == 1) ? th : obj;
	outButton = (arguments.length == 1) ? null : th;	//Éè¶¨Íâ²¿µã»÷µÄ°´Å¥
	//¸ù¾Ýµ±Ç°ÊäÈë¿òµÄÈÕÆÚÏÔÊ¾ÈÕÀúµÄÄêÔÂ
	var reg = /^(\d+)-(\d{1,2})-(\d{1,2})$/; 
	var r = outObject.value.match(reg); 
	if(r!=null){
		r[2]=r[2]-1; 
		var d= new Date(r[1], r[2],r[3]); 
		if(d.getFullYear()==r[1] && d.getMonth()==r[2] && d.getDate()==r[3]){
			outDate=d;		//±£´æÍâ²¿´«ÈëµÄÈÕÆÚ
		}
		else outDate="";
			meizzSetDay(r[1],r[2]+1);
	}
	else{
		outDate="";
		meizzSetDay(new Date().getFullYear(), new Date().getMonth() + 1);
	}
	dads.display = '';

	event.returnValue=false;
}

var MonHead = new Array(12);    		   //¶¨ÒåÑôÀúÖÐÃ¿¸öÔÂµÄ×î´óÌìÊý
    MonHead[0] = 31; MonHead[1] = 28; MonHead[2] = 31; MonHead[3] = 30; MonHead[4]  = 31; MonHead[5]  = 30;
    MonHead[6] = 31; MonHead[7] = 31; MonHead[8] = 30; MonHead[9] = 31; MonHead[10] = 30; MonHead[11] = 31;

var meizzTheYear=new Date().getFullYear(); //¶¨ÒåÄêµÄ±äÁ¿µÄ³õÊ¼Öµ
var meizzTheMonth=new Date().getMonth()+1; //¶¨ÒåÔÂµÄ±äÁ¿µÄ³õÊ¼Öµ
var meizzWDay=new Array(39);               //¶¨ÒåÐ´ÈÕÆÚµÄÊý×é

function document.onclick() //ÈÎÒâµã»÷Ê±¹Ø±Õ¸Ã¿Ø¼þ	//ie6µÄÇé¿ö¿ÉÒÔÓÉÏÂÃæµÄÇÐ»»½¹µã´¦Àí´úÌæ
{ 
  with(window.event)
  { if (srcElement.getAttribute("Author")==null && srcElement != outObject && srcElement != outButton)
    closeLayer();
  }
}

function document.onkeyup()		//°´Esc¼ü¹Ø±Õ£¬ÇÐ»»½¹µã¹Ø±Õ
  {
    if (window.event.keyCode==27){
		if(outObject)outObject.blur();
		closeLayer();
	}
	else if(document.activeElement)
		if(document.activeElement.getAttribute("Author")==null && document.activeElement != outObject && document.activeElement != outButton)
		{
			closeLayer();
		}
  }

function meizzWriteHead(yy,mm)  //Íù head ÖÐÐ´Èëµ±Ç°µÄÄêÓëÔÂ
  {
	odatelayer.meizzYearHead.innerText  = yy + " Äê";
    odatelayer.meizzMonthHead.innerText = mm + " ÔÂ";
  }

function tmpSelectYearInnerHTML(strYear) //Äê·ÝµÄÏÂÀ­¿ò
{
  if (strYear.match(/\D/)!=null){alert("Äê·ÝÊäÈë²ÎÊý²»ÊÇÊý×Ö£¡");return;}
  var m = (strYear) ? strYear : new Date().getFullYear();
  if (m < 1000 || m > 9999) {alert("Äê·ÝÖµ²»ÔÚ 1000 µ½ 9999 Ö®¼ä£¡");return;}
  var n = m - 10;
  if (n < 1000) n = 1000;
  if (n + 26 > 9999) n = 9974;
  var s = "<select Author=meizz name=tmpSelectYear style='font-size: 12px' "
     s += "onblur='document.all.tmpSelectYearLayer.style.display=\"none\"' "
     s += "onchange='document.all.tmpSelectYearLayer.style.display=\"none\";"
     s += "parent.meizzTheYear = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";
  var selectInnerHTML = s;
  for (var i = n; i < n + 26; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option Author=wayx value='" + i + "' selected>" + i + "Äê" + "</option>\r\n";}
    else {selectInnerHTML += "<option Author=wayx value='" + i + "'>" + i + "Äê" + "</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.tmpSelectYearLayer.style.display="";
  odatelayer.tmpSelectYearLayer.innerHTML = selectInnerHTML;
  odatelayer.tmpSelectYear.focus();
}

function tmpSelectMonthInnerHTML(strMonth) //ÔÂ·ÝµÄÏÂÀ­¿ò
{
  if (strMonth.match(/\D/)!=null){alert("ÔÂ·ÝÊäÈë²ÎÊý²»ÊÇÊý×Ö£¡");return;}
  var m = (strMonth) ? strMonth : new Date().getMonth() + 1;
  var s = "<select Author=meizz name=tmpSelectMonth style='font-size: 12px' "
     s += "onblur='document.all.tmpSelectMonthLayer.style.display=\"none\"' "
     s += "onchange='document.all.tmpSelectMonthLayer.style.display=\"none\";"
     s += "parent.meizzTheMonth = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";
  var selectInnerHTML = s;
  for (var i = 1; i < 13; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option Author=wayx value='"+i+"' selected>"+i+"ÔÂ"+"</option>\r\n";}
    else {selectInnerHTML += "<option Author=wayx value='"+i+"'>"+i+"ÔÂ"+"</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.tmpSelectMonthLayer.style.display="";
  odatelayer.tmpSelectMonthLayer.innerHTML = selectInnerHTML;
  odatelayer.tmpSelectMonth.focus();
}

function closeLayer()               //Õâ¸ö²ãµÄ¹Ø±Õ
  {
    document.all.meizzDateLayer.style.display="none";
  }

function IsPinYear(year)            //ÅÐ¶ÏÊÇ·ñÈòÆ½Äê
  {
    if (0==year%4&&((year%100!=0)||(year%400==0))) return true;else return false;
  }

function GetMonthCount(year,month)  //ÈòÄê¶þÔÂÎª29Ìì
  {
    var c=MonHead[month-1];if((month==2)&&IsPinYear(year)) c++;return c;
  }
function GetDOW(day,month,year)     //ÇóÄ³ÌìµÄÐÇÆÚ¼¸
  {
    var dt=new Date(year,month-1,day).getDay()/7; return dt;
  }

function meizzPrevY()  //ÍùÇ°·­ Year
  {
    if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear--;}
    else{alert("Äê·Ý³¬³ö·¶Î§£¨1000-9999£©£¡");}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzNextY()  //Íùºó·­ Year
  {
    if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear++;}
    else{alert("Äê·Ý³¬³ö·¶Î§£¨1000-9999£©£¡");}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzToday()  //Today Button
  {
	var today;
    meizzTheYear = new Date().getFullYear();
    meizzTheMonth = new Date().getMonth()+1;
    today=new Date().getDate();
    //meizzSetDay(meizzTheYear,meizzTheMonth);
    if(outObject){
		outObject.value=meizzTheYear + "-" + meizzTheMonth + "-" + today;
    }
    closeLayer();
  }
function meizzPrevM()  //ÍùÇ°·­ÔÂ·Ý
  {
    if(meizzTheMonth>1){meizzTheMonth--}else{meizzTheYear--;meizzTheMonth=12;}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzNextM()  //Íùºó·­ÔÂ·Ý
  {
    if(meizzTheMonth==12){meizzTheYear++;meizzTheMonth=1}else{meizzTheMonth++}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }

function meizzSetDay(yy,mm)   //Ö÷ÒªµÄÐ´³ÌÐò**********
{
  meizzWriteHead(yy,mm);
  //ÉèÖÃµ±Ç°ÄêÔÂµÄ¹«¹²±äÁ¿Îª´«ÈëÖµ
  meizzTheYear=yy;
  meizzTheMonth=mm;
  
  for (var i = 0; i < 39; i++){meizzWDay[i]=""};  //½«ÏÔÊ¾¿òµÄÄÚÈÝÈ«²¿Çå¿Õ
  var day1 = 1,day2=1,firstday = new Date(yy,mm-1,1).getDay();  //Ä³ÔÂµÚÒ»ÌìµÄÐÇÆÚ¼¸
  for (i=0;i<firstday;i++)meizzWDay[i]=GetMonthCount(mm==1?yy-1:yy,mm==1?12:mm-1)-firstday+i+1	//ÉÏ¸öÔÂµÄ×îºó¼¸Ìì
  for (i = firstday; day1 < GetMonthCount(yy,mm)+1; i++){meizzWDay[i]=day1;day1++;}
  for (i=firstday+GetMonthCount(yy,mm);i<39;i++){meizzWDay[i]=day2;day2++}
  for (i = 0; i < 39; i++)
  { var da = eval("odatelayer.meizzDay"+i)     //ÊéÐ´ÐÂµÄÒ»¸öÔÂµÄÈÕÆÚÐÇÆÚÅÅÁÐ
    if (meizzWDay[i]!="")
      { 
		//³õÊ¼»¯±ß¿ò
		da.borderColorLight="#FF9900";
		da.borderColorDark="#FFFFFF";
		if(i<firstday)		//ÉÏ¸öÔÂµÄ²¿·Ö
		{
			da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
			da.title=(mm==1?12:mm-1) +"ÔÂ" + meizzWDay[i] + "ÈÕ";
			da.onclick=Function("meizzDayClick(this.innerText,-1)");
			if(!outDate)
				da.style.backgroundColor = ((mm==1?yy-1:yy) == new Date().getFullYear() && 
					(mm==1?12:mm-1) == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ?
					 "#FFD700":"#e0e0e0";
			else
			{
				da.style.backgroundColor =((mm==1?yy-1:yy)==outDate.getFullYear() && (mm==1?12:mm-1)== outDate.getMonth() + 1 && 
				meizzWDay[i]==outDate.getDate())? "#00ffff" :
				(((mm==1?yy-1:yy) == new Date().getFullYear() && (mm==1?12:mm-1) == new Date().getMonth()+1 && 
				meizzWDay[i] == new Date().getDate()) ? "#FFD700":"#e0e0e0");
				//½«Ñ¡ÖÐµÄÈÕÆÚÏÔÊ¾Îª°¼ÏÂÈ¥
				if((mm==1?yy-1:yy)==outDate.getFullYear() && (mm==1?12:mm-1)== outDate.getMonth() + 1 && 
				meizzWDay[i]==outDate.getDate())
				{
					da.borderColorLight="#FFFFFF";
					da.borderColorDark="#FF9900";
				}
			}
		}
		else if (i>=firstday+GetMonthCount(yy,mm))		//ÏÂ¸öÔÂµÄ²¿·Ö
		{
			da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
			da.title=(mm==12?1:mm+1) +"ÔÂ" + meizzWDay[i] + "ÈÕ";
			da.onclick=Function("meizzDayClick(this.innerText,1)");
			if(!outDate)
				da.style.backgroundColor = ((mm==12?yy+1:yy) == new Date().getFullYear() && 
					(mm==12?1:mm+1) == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ?
					 "#FFD700":"#e0e0e0";
			else
			{
				da.style.backgroundColor =((mm==12?yy+1:yy)==outDate.getFullYear() && (mm==12?1:mm+1)== outDate.getMonth() + 1 && 
				meizzWDay[i]==outDate.getDate())? "#00ffff" :
				(((mm==12?yy+1:yy) == new Date().getFullYear() && (mm==12?1:mm+1) == new Date().getMonth()+1 && 
				meizzWDay[i] == new Date().getDate()) ? "#FFD700":"#e0e0e0");
				//½«Ñ¡ÖÐµÄÈÕÆÚÏÔÊ¾Îª°¼ÏÂÈ¥
				if((mm==12?yy+1:yy)==outDate.getFullYear() && (mm==12?1:mm+1)== outDate.getMonth() + 1 && 
				meizzWDay[i]==outDate.getDate())
				{
					da.borderColorLight="#FFFFFF";
					da.borderColorDark="#FF9900";
				}
			}
		}
		else		//±¾ÔÂµÄ²¿·Ö
		{
			da.innerHTML="<b>" + meizzWDay[i] + "</b>";
			da.title=mm +"ÔÂ" + meizzWDay[i] + "ÈÕ";
			da.onclick=Function("meizzDayClick(this.innerText,0)");		//¸øtd¸³ÓèonclickÊÂ¼þµÄ´¦Àí
			//Èç¹ûÊÇµ±Ç°Ñ¡ÔñµÄÈÕÆÚ£¬ÔòÏÔÊ¾ÁÁÀ¶É«µÄ±³¾°£»Èç¹ûÊÇµ±Ç°ÈÕÆÚ£¬ÔòÏÔÊ¾°µ»ÆÉ«±³¾°
			if(!outDate)
				da.style.backgroundColor = (yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
					"#FFD700":"#e0e0e0";
			else
			{
				da.style.backgroundColor =(yy==outDate.getFullYear() && mm== outDate.getMonth() + 1 && meizzWDay[i]==outDate.getDate())?
					"#00ffff":((yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
					"#FFD700":"#e0e0e0");
				//½«Ñ¡ÖÐµÄÈÕÆÚÏÔÊ¾Îª°¼ÏÂÈ¥
				if(yy==outDate.getFullYear() && mm== outDate.getMonth() + 1 && meizzWDay[i]==outDate.getDate())
				{
					da.borderColorLight="#FFFFFF";
					da.borderColorDark="#FF9900";
				}
			}
		}
        da.style.cursor="hand"
      }
    else{da.innerHTML="";da.style.backgroundColor="";da.style.cursor="default"}
  }
}

function meizzDayClick(n,ex)  //µã»÷ÏÔÊ¾¿òÑ¡È¡ÈÕÆÚ£¬Ö÷ÊäÈëº¯Êý*************
{
  var yy=meizzTheYear;
  var mm = parseInt(meizzTheMonth)+ex;	//ex±íÊ¾Æ«ÒÆÁ¿£¬ÓÃÓÚÑ¡ÔñÉÏ¸öÔÂ·ÝºÍÏÂ¸öÔÂ·ÝµÄÈÕÆÚ
	//ÅÐ¶ÏÔÂ·Ý£¬²¢½øÐÐ¶ÔÓ¦µÄ´¦Àí
	if(mm<1){
		yy--;
		mm=12+mm;
	}
	else if(mm>12){
		yy++;
		mm=mm-12;
	}
	
  if (mm < 10){mm = "0" + mm;}
  if (outObject)
  {
    if (!n) {//outObject.value=""; 
      return;}
    if ( n < 10){n = "0" + n;}
    outObject.value= yy + "-" + mm + "-" + n ; //×¢£ºÔÚÕâÀïÄã¿ÉÒÔÊä³ö¸Ä³ÉÄãÏëÒªµÄ¸ñÊ½
    closeLayer(); 
  }
  else {closeLayer(); alert("ÄúËùÒªÊä³öµÄ¿Ø¼þ¶ÔÏó²¢²»´æÔÚ£¡");}
}


	