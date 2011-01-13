<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
<ui:import library=""></ui:import>
<title>综合客服管理系统</title>
<script>
window.onunload=function(){
	onLogoutSystem();
}
function document.onkeydown() 
{ 
	if (event.keyCode==116) 
	{ 
		event.keyCode = 0; 
		event.cancelBubble = true; 
		return false; 
	}
}
</script>
<script>

  function PageModal(win, href, desc, id){
    this.win = win;
    this.href = href;
    this.desc = desc;
    this.id = id;
  }
  
  var winMaxCount = 3;
  var currentWinIndex = -1;
  var currentWinCount = 0;
  var currentWinTitleIndex = 0;
  var winList = new Array();
  var winListTitle = new Array();
  var blankPage2 = "../pbnews/PbNews.jsp";
  var blankDesc2 = "首页";
  var blankId2 = "homepage";  
  var blankPage = "../pbnews/PbNews.jsp";
  var blankDesc = "首页";
  var blankId = "";
  var _blankPage = "blank.htm";
  var _blankDesc = "";
  var _blankId = "";
  
  window.onload = function(){
    multiWindowInit();
  }
  
  function multiWindowInit(){
	  for(var i=0; i<winMaxCount; i++){
	    var page = new PageModal(frames["workspace"+(i+1)], "", "", "");
	    winList[winList.length] = page;			
	    
	    if(winMaxCount<=1){
	      var btn_closeAll = top.workspaceTitle.$("closeAllWorkspace");
	      btn_closeAll.style.display = "none";	 
	    }   
	  }	  
  }

  function switchTitleStyle(winIndex){
    if(winMaxCount>1){
      for(var i=0; i<winListTitle.length; i++){
        if(winListTitle[i].innerText==""){
            winListTitle[i].parentElement.className = "invisible";
        }
        else{
        	winListTitle[i].parentElement.className = ( winListTitle[i].winIndex==winIndex ? "selected" : "unselected" );
      	}
      }
    }
  }
  
  function getWinIndex(id){
    var result = null;
   	for(var i=0; i<winList.length; i++)
   	{
   	  if(winList[i].id == id)
   	  {
   	    result = i;
   	  }
   	}	
   	return result;
  }  
  
  function canHoldMorePage(){
    if(winMaxCount>1 && currentWinCount>=winMaxCount){
      return true;
      /*
      alert("超过最大窗口数限制（"+winMaxCount+"），请先关闭部分窗口");
      return false;
      */
    }else
      return true;
  }
  
  function getFirstAvailablePageIndex(){
    var result = null;
    for(var i=0; i<winList.length; i++){
      if(winList[i].id==""){
        result = i;
        break;
      }
    }
    if(winMaxCount==1)
      result = 0;
    currentWinTitleIndex = currentWinCount;
    if(result==null){
      for(var i=0; i<winListTitle.length; i++){
        if(winListTitle[i].parentElement.className=="selected"){
    	  result = winListTitle[i].winIndex;
    	  currentWinTitleIndex = i;
    	}
      }
    }
    return result;
  }
	
	function setWinVisible(winIndex){  	
	  var rowsStr = "";
	  for(var i=0; i<winMaxCount; i++){
	    rowsStr += ( (i>0) ? "," : "" );
	    rowsStr += ( (i==winIndex) ? "*" : "0" );
	  }			
	  top.workspace.rows = rowsStr;	  
	  
	  switchTitleStyle(winIndex);
	}
	
	function setWin(winIndex, href, desc, id){
	  if(winList.length==0){
	    multiWindowInit();
	  }
	  if(winList.length>0){
	      if(typeof(winIndex)=="undefined" || winIndex==null){
	        winIndex = 0;
	      }
		  winList[winIndex].win.location = href;
		  winList[winIndex].href = href;
		  winList[winIndex].desc = desc;
		  winList[winIndex].id = id;
	  }
	}
	
	function setWinTitle(winIndex, desc){
	  if(winMaxCount==1)
	  {
	    winListTitle[0].innerText = desc;
	    winListTitle[0].winIndex = winIndex;	  	  
	  }
	  else
	  {
	    winListTitle[currentWinTitleIndex].innerText = desc;
	    winListTitle[currentWinTitleIndex].winIndex = winIndex;	  
	  }
	}

	function setWinPage(winIndex, href, desc, id){
	  setWin(winIndex, href, desc, id);
	  setWinTitle(winIndex, desc);
	  setWinVisible(winIndex);
	}

  function openWin(href, title, id){  	
    var prev_index = getWinIndex(id);		   			 		   	
   	if(prev_index==null)
   	{ 
   	  if(canHoldMorePage()){
   	    var index = getFirstAvailablePageIndex();
   	    if(index!=null){      	      	      
	   	    setWinPage(index, href, title, id);
		    currentWinIndex = index;
			if(winMaxCount>1){
			    if(currentWinCount<winMaxCount)
			    	currentWinCount++;
			}
			else
			    currentWinCount=0;	   	   	  
	   	}	    
   	  }
   	}else{
   	  switchPage(prev_index);
   	}	   
  }			
  
  function switchPage(prev_index){
    setWinVisible(prev_index);
  }
			
	function closeWin(){
	  if(winMaxCount>1){
	    if(currentWinCount<=0) return;
	  }
	  if(confirm('确定要退出当前页面吗？')){
	    if(currentWinCount<=1){
	    	setWin(currentWinIndex, blankPage, blankDesc, blankId);
	    }
	    else{
	        setWin(currentWinIndex, _blankPage, _blankDesc, _blankId);
	    }
	    currentWinCount--;
	    currentWinIndex = (currentWinIndex>0 ? currentWinIndex-1 : (currentWinCount>0 ? currentWinIndex+1 : 0))
	    reArrayTitle();
	    setWinVisible(currentWinIndex);
	  }	  
	}	
	
	function closeWinWithoutPrompt(){
	  if(winMaxCount>1){
	    if(currentWinCount<=0) return;
	  }

	    if(currentWinCount<=1){
	    	setWin(currentWinIndex, blankPage, blankDesc, blankId);
	    }
	    else{
	        setWin(currentWinIndex, _blankPage, _blankDesc, _blankId);
	    }
	    currentWinCount--;
	    currentWinIndex = (currentWinIndex>0 ? currentWinIndex-1 : (currentWinCount>0 ? currentWinIndex+1 : 0))
	    reArrayTitle();
	    setWinVisible(currentWinIndex);
  
	}		

	function closeAllWin(){
	  
	  if(currentWinCount<=0) return;
	  
	  if(confirm('确定要关闭所有页面吗？')){
		  for(var i=0; i<winList.length; i++){
		    if(i==0){
		      setWin(i, blankPage, blankDesc, blankId);
		    }
		    else{
		      setWin(i, _blankPage, _blankDesc, _blankId);		    
		  	}
		  }
		  clearAllTitle();
		  currentWinCount = 0;
		  currentWinIndex = -1;
		  setWinVisible(0);
	  }  
	}
	
	function reArrayTitle(){
	  var index = 0;
	  for(var i=0; i<winList.length; i++){	    
	    if(winList[i].id != ""){
	      winListTitle[index].innerText = winList[i].desc;
	      winListTitle[index].winIndex = i;
	      index++;
	    }
	  }
	  for(var j=index; j<winListTitle.length; j++){
	    winListTitle[j].innerText = "";
	    winListTitle[j].winIndex = "";
	  }
	}
	
	function clearAllTitle(){
	  for(var i=0; i<winListTitle.length; i++){
	    winListTitle[i].innerText = "";
	    winListTitle[i].winIndex = "";
	  }
	}
	
	function refreshWin(){
	  if(currentWinIndex<0) currentWinIndex=0;
	  winList[currentWinIndex].win.location.reload();
	}
	
	function backHome(){
	  openWin(blankPage2, blankDesc2, blankId2);
	}
</script>
</head>

<frameset name="firstLevelFrameset" rows="51,*,21" frameborder="NO" border="0" framespacing="0">
		<frame src="firstLevelMenu.jsp" name="firstLevelMenu" scrolling="NO" noresize >
		<frameset name="secondLevelFrameset" cols="180,10,*" frameborder="NO" border="0" framespacing="0">
			  <frame src="secondLevelMenu.jsp" name="secondLevelMenu" scrolling="NO" noresize>
		   
				<frame src="slider.htm" name="slider">
				<frameset rows="24,*" frameborder="NO" border="0" framespacing="0">
						<frame src="workspaceTitle.jsp" name="workspaceTitle" scrolling="NO" noresize>
						<frameset rows="*,0,0" name="workspace" frameborder="NO" border="0" framespacing="0">
  						  <frame name="workspace1" src="../pbnews/PbNews.jsp" scrolling="NO" noresize>
						  <frame name="workspace2" scrolling="NO" noresize>
						  <frame name="workspace3" scrolling="NO" noresize>
						</frameset>
				</frameset>				
		</frameset>
		<frame src="copyright.htm" name="bottomFrame" scrolling="NO" noresize>
</frameset>


<noframes>
  <body></body>
</noframes>
</html>

