/* @edit moejoe(lmh_user@hotmail.com) */
function Pagination(thisName,containerCtl,pagiFuncName,pageSize){
this.name=thisName;
this._page=1;
this._totalPage=0;
this._size=pageSize||15;
this._pagiFuncNm=pagiFuncName||"qryPagination";
this._containerNm=containerCtl;
this.paginate=function(recordCount){
if(this._size<1)this._size=(recordCount==0)?1:recordCount;
this._totalPage=Math.ceil(recordCount/this._size);
var str=(this._page>1)?('<a href="#" onclick="'+this.name+'.goPage_(1)"><img src="'+gWebAbsPath+'/images/button/button_page_frist.gif" align="absmiddle" border="0"></a><a href="#" onclick="'+this.name+'.goPage_('+(this._page-1)+')"><img src="'+gWebAbsPath+'/images/button/button_page_pre.gif" align="absmiddle" border="0"></a>')
:('<span style="color:#666666"><img src="'+gWebAbsPath+'/images/button/button_page_frist.gif" align="absmiddle" border="0"></span><span style="color:#666666"><img src="'+gWebAbsPath+'/images/button/button_page_pre.gif" align="absmiddle" border="0"></span>');
str+=(this._page<this._totalPage)?('<a href="#" onclick="'+this.name+'.goPage_('+(this._page+1)+')"><img src="'+gWebAbsPath+'/images/button/button_page_next.gif" align="absmiddle" border="0"></a><a href="#" onclick="'+this.name+'.goPage_('+this._totalPage+')"><img src="'+gWebAbsPath+'/images/button/button_page_last.gif" align="absmiddle" border="0"></a>')
:('<span style="color:#666666"><img src="'+gWebAbsPath+'/images/button/button_page_next.gif" align="absmiddle" border="0"></span><span style="color:#666666"><img src="'+gWebAbsPath+'/images/button/button_page_last.gif" align="absmiddle" border="0"></span>');
str+='转到<input type="text" maxlength="4" value="'+this._page+'" style="width:20px;font-size:10px;height:16px"><b>/'+this._totalPage+'</b><img style="cursor:hand" align="absmiddle" border="0" src="'+gWebAbsPath+'/images/button/button_go.gif" onclick="'+this.name+'.goPage_(this.previousSibling.previousSibling.value, true)">';
document.all(this._containerNm).innerHTML=str;
str=null;
}
this.goPage_=function(pPageNo,bInput){
if(bInput)pPageNo=pPageNo.replace(/(^\s*)|(\s*$)/g,"");
if(isNaN(pPageNo))alert("请录入一个页数！");
else{
this._page=parseInt((pPageNo<2)?1:((pPageNo>this._totalPage)?this._totalPage:pPageNo),10);
eval(this._pagiFuncNm+"()");
}
}
this.getPageIndex=function(){
return this._page;
}
this.getPageSize=function(){
return this._size;
}
this.setPageSize=function(pSize){
if(isNaN(pSize))alert("必须为数字！");
this._size=pSize;
}
}
//<img src="../images/box_bar_left.gif" width="5" height="24" align="absmiddle">