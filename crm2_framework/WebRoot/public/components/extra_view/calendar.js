/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/

var Calendar = {

  days : null,
  fourNextClick : false,
  time_focus_obj : null,
  old_control : null,
  new_control : null,
  calendarDatatype:null,
  tmp_calendarControl_hour:null,
  tmp_calendarControl_minute:null,
  tmp_calendarControl_second:null,	

	year_onpropertychange : function(){
		if (!_calender_year.processing && event.propertyName=="value"){
			if (_calender_year.value.length==4){
				_calender_year.processing=true;
				Calendar.changeDate(System.getInt(_calender_year.value), _calendarControl.month);
				_calender_year.processing=false;
			}
		}
	},
	
	month_onpropertychange : function(){
		if (!_calender_month.processing && Document.activeElement==_calender_month && event.propertyName=="value"){
			if (_calender_month.value.length>0){
				_calender_month.processing=true;
				Calendar.changeDate(_calendarControl.year, System.getInt(_calender_month.value-1));
				_calender_month.processing=false;
			}
		}
	},
	
	hour_onpropertychange : function(){
		if (!_calender_hour.processing && Document.activeElement==_calender_hour && event.propertyName=="value"){
			if (_calender_hour.value.length>0){
				_calender_hour.processing=true;
				var hourValue = _calender_hour.value ;
				if( isNaN( hourValue ) ){
					alert("请输入数字类型的小时!");
					_calender_hour.value = "00";
				}else{
					var hourNum = parseFloat(hourValue);
					if( hourNum > 24 || hourNum < 0 ){
						alert("请输入介于0到24的小时数!");
						_calender_hour.value = "00";						
					}
				}
				
				_calendarControl.hour = _calender_hour.value ;
				Calendar.changeDate(_calendarControl.year, _calendarControl.month);
				
				_calender_hour.processing=false;
			}
		}
	},
	minute_onpropertychange : function(){
		if (!_calender_minute.processing && Document.activeElement==_calender_minute && event.propertyName=="value"){
			if (_calender_minute.value.length>0){
				_calender_minute.processing=true;
				var minuteValue = _calender_minute.value ;
				if( isNaN( minuteValue ) ){
					alert("请输入数字类型的分钟数!");
					_calender_minute.value = "00";
				}else{
					var minuteNum = parseFloat(minuteValue);
					if( minuteNum > 60 || minuteNum < 0 ){
						alert("请输入介于0到60的分钟数!");
						_calender_minute.value = "00";						
					}
				}
				
				_calendarControl.minute = _calender_minute.value ;
				Calendar.changeDate(_calendarControl.year, _calendarControl.month);
				
				
				_calender_minute.processing=false;
			}
		}
	},
	second_onpropertychange : function(){
		if (!_calender_second.processing && Document.activeElement==_calender_second && event.propertyName=="value"){
			if (_calender_second.value.length>0){
				_calender_second.processing=true;
				var secondValue = _calender_second.value ;
				if( isNaN( secondValue ) ){
					alert("请输入数字类型的秒数!");
					_calender_second.value = "00";
				}else{
					var secondNum = parseFloat(secondValue);
					if( secondNum > 60 || secondNum < 0 ){
						alert("请输入介于0到60的小时数!");
						_calender_second.value = "00";						
					}
				}
				
				_calendarControl.second = _calender_second.value ;
				Calendar.changeDate(_calendarControl.year, _calendarControl.month);
				
				
				_calender_second.processing=false;
			}
		}
	},

	setDate : function(date){
		Calendar.changeDate(date.getFullYear(),date.getMonth(),date.getDate());
	},
	
	changeDate : function(year, month, day){
	
		if(year<1900){
			//alert("选择时间不合法");
			year = 1900; 
			month = 0;
			day = 1;
			_calender_year.innerText=year;
		}else if(year>=2030){
			//alert("选择时间不合法");
			year = 2029; 
			month = 11;
			day = 31;			
			_calender_year.innerText=year;			
		}
		
		_calendarControl.hour = _calender_hour.value ;
		_calendarControl.minute = _calender_minute.value ;
		_calendarControl.second = _calender_second.value ;		
		
		if (_calendarControl.year==year 
		&& _calendarControl.month==month 
		&& (!day || _calendarControl.day==day)) return;
	
		if (_calendarControl.year!=year || _calendarControl.month!=month){
			_calendarControl.year=year;
			_calendarControl.month=month;
	
			if (month==0){
				 _calendarControl.preMonth=11
				 _calendarControl.preYear=_calendarControl.year-1
			}else{
				 _calendarControl.preMonth=_calendarControl.month-1
				 _calendarControl.preYear=_calendarControl.year
			}
			if (month==11){
				_calendarControl.nextMonth=0
				_calendarControl.nextYear=_calendarControl.year+1
			}else{
				_calendarControl.nextMonth=_calendarControl.month+1
				_calendarControl.nextYear=_calendarControl.year
	
			}
			_calendarControl.startday=(new Date(year,month,1)).getDay()
			if (_calendarControl.startday==0) _calendarControl.startday=7
			var curNumdays=Calendar.getNumberOfDays(_calendarControl.month,_calendarControl.year)
			var preNumdays=Calendar.getNumberOfDays(_calendarControl.preMonth,_calendarControl.preYear)
			var nextNumdays=Calendar.getNumberOfDays(_calendarControl.nextMonth,_calendarControl.nextYear)
			var startDate=preNumdays-_calendarControl.startday+1
			var endDate=42-curNumdays-_calendarControl.startday
	
			_calender_month.value=(_calendarControl.month+1);
			_calender_year.innerText=_calendarControl.year
	
			var datenum=0;
			for (var i=startDate;i<=preNumdays;i++){			
	      		
				var cell = calendarData.cells[datenum+7];
				cell.monthAttribute="pre";
				cell.className="cell_trailing";
				cell.innerText=i;
				datenum++;
			}
			for (var i=1;i<=curNumdays;i++){
			
				var cell = calendarData.cells[datenum+7];
				cell.monthAttribute="cur";
				if (datenum != _calendarControl.activeCellIndex){
					cell.className="cell_day";
				}
				cell.innerText=i;
				datenum++;
			}
			for (var i=1;i<=endDate;i++){
				var cell = calendarData.cells[datenum+7];
				cell.monthAttribute="next";
				cell.className="cell_trailing";
				cell.innerText=i;
				datenum++;
			}
		}
		
		if (day) _calendarControl.day=day;
		var z = _calendarControl.day+_calendarControl.startday-1+7;
		Calendar.setActiveCell(calendarData.cells[_calendarControl.day+_calendarControl.startday-1+7]);

	},
	
	setActiveCell : function(cell){
	
		function setActiveCell(cellIndex){
			var cell = calendarData.cells[_calendarControl.activeCellIndex+7];
			if (cell.monthAttribute=="cur"){
				cell.className="cell_day";
			}
			else{
				cell.className="cell_trailing";
			}
	
			var cell = calendarData.cells[cellIndex+7];
			cell.className="cell_selected";
	
			_calendarControl.activeCellIndex=cellIndex;
		}
	
		if (cell.tagName.toLowerCase()!="td") return;
		var _activeCellIndex=cell.parentElement.rowIndex*7+cell.cellIndex-7;
	
		with(_calendarControl){
					
			//if (activeCellIndex==_activeCellIndex) return;
	
			var monthAttribute=cell.monthAttribute;
						
			switch (monthAttribute){
				case "pre":{
					Calendar.changeDate(preYear,preMonth,Calendar.getNumberOfDays(preMonth,preYear)-startday+_activeCellIndex+1);
					setActiveCell(startday+day-1);
					break
				}
				case "cur":{
					Calendar.changeDate(year,month,_activeCellIndex-startday+1);
					setActiveCell(_activeCellIndex);
					break
				}
				case "next":{
					if (Calendar.fourNextClick==true){
						Calendar.fourNextClick = false;
										
						if (nextYear == year){
						  Calendar.changeDate(year,month,Calendar.getNumberOfDays(month,year));						
						}
					     else if (nextYear != year){
						   Calendar.changeDate(nextYear,0,Calendar.getNumberOfDays(0,nextYear));
						  							
					     }
					}
					else
					{
						Calendar.changeDate(nextYear,nextMonth,_activeCellIndex-Calendar.getNumberOfDays(month,year)-startday+1);
				    }
			
					setActiveCell(startday+day-1);
					break
				}
			}
		}
	},
	
	cell_onclick : function(cell){
        Calendar.fourNextClick = false ;
		Calendar.setActiveCell(cell);
		Dropdown.selected();
	},
	
	onkeydown : function(){
		switch(event.keyCode){
			case 33:{//PgUp
				if (event.ctrlKey){
					Calendar.changeDate(_calendarControl.year-1,_calendarControl.month)
				}else{
					Calendar.changeDate(_calendarControl.preYear,_calendarControl.preMonth)
				}
				break
			}
			case 34:{//PgDn
				if (event.ctrlKey){
					 Calendar.changeDate(_calendarControl.year+1,_calendarControl.month)
				}else{
					 Calendar.changeDate(_calendarControl.nextYear,_calendarControl.nextMonth)
				}
				break
			}
			case 35:{//End
			   	var index=Calendar.getNumberOfDays(_calendarControl.month,_calendarControl.year) +_calendarControl.startday-1
				Calendar.setActiveCell(calendarData.cells[index+7+7])
				break
			}
			case 36:{//Home
				Calendar.setActiveCell(calendarData.cells[_calendarControl.startday+7+7])
				break
			}
			case 37:{//<--
				var index=_calendarControl.activeCellIndex-1;
				if (index<0) index=0;
				Calendar.setActiveCell(calendarData.cells[index+7])
				break
			}
			case 38:{//上箭头
				if (_calendarControl.activeCellIndex<14){
					var day=Calendar.getNumberOfDays(_calendarControl.preMonth,_calendarControl.preYear)+_calendarControl.day-7;
					Calendar.setDate(new Date(_calendarControl.preYear, _calendarControl.preMonth, day));
				}
				else{
					var index=_calendarControl.activeCellIndex-7;
					Calendar.setActiveCell(calendarData.cells[index+7]);
				}
				break
			}
			case 39:{//-->
				var index=_calendarControl.activeCellIndex+1;
				if (index>=calendarData.cells.length-7) index=calendarData.cells.length-8;
				Calendar.setActiveCell(calendarData.cells[index+7])
				break
			}
			case 40:{//下箭头
				if (_calendarControl.activeCellIndex>41){
					var day=7-(Calendar.getNumberOfDays(_calendarControl.month,_calendarControl.year)-_calendarControl.day);
					Calendar.setDate(new Date(_calendarControl.nextYear, _calendarControl.nextMonth, day));
				}
				else{
					var index=_calendarControl.activeCellIndex+7;
					Calendar.setActiveCell(calendarData.cells[index+7]);
				}
				break
			}
		}
	},
	
	today_onclick : function(){
		var today=new Date();
	 	_calendarControl.todayDay=today.getDate();
		_calendarControl.todayMonth=today.getMonth();
		_calendarControl.todayYear=today.getFullYear();
		_calender_hour.value=today.getHours();
		_calender_minute.value=today.getMinutes();
		_calender_second.value=today.getSeconds();
		Calendar.changeDate(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay)
		var index=_calendarControl.todayDay+_calendarControl.startday-1;
		Calendar.setActiveCell(calendarData.cells[index+7]);
		Dropdown.selected();
	},

	clearDate_onclick : function(){
		Calendar.changeDate(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay)
		var index=_calendarControl.todayDay+_calendarControl.startday-1;
		Calendar.setActiveCell(calendarData.cells[index+7]);
		Dropdown.selected("");
	},	
	// add by wq 
	time_focus:function(obj){
		Calendar.time_focus_obj =obj;
	},
	
	timeChange:function(arrow){
	     
		 if (Calendar.time_focus_obj ==null){
		 	Calendar.time_focus_obj = _calender_hour;
		 	_calender_hour.focus();
		 }
		 var val = parseFloat(Calendar.time_focus_obj.value);
		 //old_alert(parseFloat(Calendar.time_focus_obj.value));
		 
		 var iMax = 59 ;
		 var iMin = 0 ;
		 if (Calendar.time_focus_obj.id =="_calender_hour"){
		 	  iMax = 23 ;
		 }
		 	 if (arrow=="up"){
		 	 	  if (val ==iMax){
		       val = iMin ;	
		      }
		      else{
		        val++ ;	
		      }
		   }
		   else if (arrow=="down"){
  	 	 	  if (val ==iMin){
		        val = iMax ;	
		      }
		      else{
    		   	 val-- ;	
		      }
		   }
		  if (val <10){
     		 Calendar.time_focus_obj.value = "0" + val;	
		 }else
		 {
   		   Calendar.time_focus_obj.value = "" + val;	
		 }

		_calendarControl.hour = _calender_hour.value;
		_calendarControl.minute = _calender_minute.value;
		_calendarControl.second = _calender_second.value;
	},
	
	addSeconds_onclick:function(){ 
      this.timeChange("up");
		},
	
	reduceSeconds_onclick:function(){
		  this.timeChange("down");
		},
	
	getNumberOfDays : function(month,year){
		var numDays=new Array(31,28,31,30,31,30,31,31,30,31,30,31)
		n=numDays[month]	
		if (month==1 && (year%4==0 && year%100!=0 || year%400==0)) n++
		return n
	},
	
  	confirm_onclick : function(){
	  Dropdown.selected();	
	},
    numberString:function(datacontrol,type){
       var x ="" ;
       if(calendarDatatype== "date"){       
       datacontrol=datacontrol+"00:00:00";
       }
       else if(calendarDatatype=="yearmonth"){datacontrol=datacontrol+"-01 00:00:00";
       }
       else if(calendarDatatype=="time"){datacontrol="1900-01-01 "+datacontrol;
       }       
       switch (type){
            
				case 1:x = datacontrol.substr(0,4);         
						  break;
			    case 2:x = datacontrol.substr(5,2);
						  break;
			    case 3:x = datacontrol.substr(8,2);
						  break;
				case 4:x = datacontrol.substr(11,2);
						  break;
			    case 5:x = datacontrol.substr(14,2);
						  break;
				case 6:x = datacontrol.substr(17,2);
						  break;			  
	  }
	
       if (x.substr(0,1)=="0"){
         x = x.substr(1,x.length);
       } 
       
       var i = System.getInt(x);
       if (type< 4){
          return i ;
       }
       else{
         var s  = "" + i ;
         if ((type >=4) && (i < 10)) {
           s = "0" + "" + i ;
         }   
         return s ;
       }
       return i ;
    }	
}

var dropDownDate = null;

function makeCalendar(){
	if(!dropDownDate){
		dropDownDate=createDropDown("dropDownDate");
		var __t=dropDownDate;
		__t.type="date";
		__t.tag="";
		__t.cachable="true";
		//_array_dropdown[_array_dropdown.length]=__t;
		initDropDown(__t);
	}
}


function createCalendar(parent_element,dataType,realDataType){
	function _calendar(){
	 	var today=new Date();
	 	this.todayDay=today.getDate();
		this.todayMonth=today.getMonth();
		this.todayYear=today.getFullYear();
	 	this.activeCellIndex=0;
	}
	
	function _Times(){
	 	var today=new Date();
		this.todayHours=today.getHours();
		this.todayMinutes=today.getMinutes();
		this.todaySeconds=today.getSeconds();	 	
	}	
	
	if (typeof(CalendarTable)=="object") {
		CalendarTable.removeNode(true);
	}
	Calendar.days=new Array(Const.Sunday, Const.Monday, Const.Tuesday, Const.Wednesday, Const.Thursday, Const.Friday, Const.Saturday);
	_calendarControl=new _calendar();
	// modify by wq add datatype2
	_calendarTimes=new _Times();
	calendarDatatype = dataType;
	if ((dataType==null) || (dataType=="undefind")){
	  dataType = realDataType;
	  calendarDatatype = realDataType;
	}
	var tmpHTML="";

    var tmp_calendarControl_todayYear = "" ;
	var tmp_calendarControl_todayMonth = "";
	var tmp_calendarControl_todayDay  ="" ;
	
	tmp_calendarControl_hour = _calendarTimes.todayHours;
	tmp_calendarControl_minute =_calendarTimes.todayMinutes;
	tmp_calendarControl_second =_calendarTimes.todaySeconds;
		
	tmpHTML+="<TABLE id=\"CalendarTable\" class=\"calendar\" width=200px cellspacing=0 cellpadding=1 rule=all>";
	tmpHTML+="<TR class=\"title\" valign=top><TD>";
	tmpHTML+="<TABLE WIDTH=100% CELLSPACING=1 CELLPADDING=0>";
	hides=" style='display:none' ";
	if(dataType=="time"){
		hides=" style='display:none' ";		
	}else{
		hides="";
	}
	tmpHTML+="<TR "+hides+"><TD align=right>";
	tmpHTML+="<INPUT type=button class=button extra=button value=3 title=\""+Const.LastYear+"\" style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:18px;HEIGHT:20px\" onclick=\"Calendar.fourNextClick = true ; Calendar.changeDate(_calendarControl.year-1,_calendarControl.month)\">";
	tmpHTML+="</TD><TD width=1>";
	tmpHTML+="<INPUT id=\"_calender_year\" type=text class=editor size=4 maxlength=4 onpropertychange=\"Calendar.fourNextClick = true ;return Calendar.year_onpropertychange()\">";
	tmpHTML+="</TD><TD align=left width=20px>";
	tmpHTML+="<INPUT type=button class=button extra=button value=4 title=\""+Const.NextYear+"\" style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:18px;HEIGHT:20px\" onclick=\"Calendar.fourNextClick = true ;Calendar.changeDate(_calendarControl.year+1,_calendarControl.month)\">";
	tmpHTML+="</TD>";
	tmpHTML+="<TD align=right width=20px>";
	tmpHTML+="<INPUT type=button class=button extra=button value=3 title=\""+Const.LastMonth+"\" style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:18px;HEIGHT:20px\" onclick=\"Calendar.fourNextClick = true ;Calendar.changeDate(_calendarControl.preYear,_calendarControl.preMonth)\">";
	tmpHTML+="</TD><TD width=1>";
	tmpHTML+="<INPUT id=\"_calender_month\" type=text class=editor size=2 maxlength=2 onpropertychange=\"return Calendar.month_onpropertychange()\">";
	tmpHTML+="</TD><TD align=left>";
	tmpHTML+="<INPUT type=button class=button extra=button value=4 title=\""+Const.NextMonth+"\" style=\"FONT-SIZE: 8;FONT-FAMILY:webdings;WIDTH:18px;HEIGHT:20px\" onclick=\"Calendar.fourNextClick = true ;Calendar.changeDate(_calendarControl.nextYear,_calendarControl.nextMonth)\">";
	tmpHTML+="</TD></TR>";
	tmpHTML+="</TABLE></TD></TR>";	
	//2007-07-17 add time information by xuruihao.modify by wq 2007.11.02
	if(!(dataType=="datetime"||dataType=="time")){	
		hides=" style='display:none' ";
		
	}else{
		hides="";
	}
	tmpHTML+="<TR class=\"title\" "+hides+"><TD>";
	
	tmpHTML+="<TABLE WIDTH=82% align=center border=0 CELLSPACING=0 CELLPADDING=0>";
	tmpHTML+="<TR><TD rowspan=2 align=right>时间(T):</TD><TD rowspan=2 align=right><div class=m_calendarborder><input value=\""+_calendarTimes.todayHours+"\" size=\"2\" id=\"_calender_hour\" type=\"text\" class=editorm maxlength=2 onpropertychange=\"return Calendar.hour_onpropertychange()\" onfocus=\"Calendar.time_focus(this)\"></input>:<input value=\""+_calendarTimes.todayMinutes+"\" size=\"2\" id=\"_calender_minute\" type=\"text\" class=editorm maxlength=2 onpropertychange=\"return Calendar.minute_onpropertychange()\" onfocus=\"Calendar.time_focus(this)\"></input>:<input value=\""+_calendarTimes.todaySeconds+"\" size=\"2\" id=\"_calender_second\" type=\"text\" class=editorm maxlength=2 onpropertychange=\"return Calendar.second_onpropertychange()\" onfocus=\"Calendar.time_focus(this)\"></input><div></TD>";
	tmpHTML+="<TD><INPUT type=button class=button2 extra=button title=增加 value=5 style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:18px;HEIGHT:10px;line-height:5px;marign-top:0;padding-top:0\" onclick=\" Calendar.addSeconds_onclick()\"></TD></TR>"
	tmpHTML+="<TR><TD><INPUT type=button class=button2 extra=button title=减少 value=6 style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:18px;HEIGHT:10px;line-height:5px;marign-top:0;padding-top:0\" onclick=\"Calendar.reduceSeconds_onclick()\"></TD></TR></TABLE></TD></TR>"
	if(!(dataType== "date"||dataType=="datetime")){
		hides=" style='display:none' ";
	}else{
		hides="";
	}	
	tmpHTML+="<TR "+hides+"><TD>";	
	
	tmpHTML+="<TABLE border=1 bordercolor=silver id=\"calendarData\" HEIGHT=100% WIDTH=100% CELLSPACING=0 CELLPADDING=0 style=\"BORDER-COLLAPSE: collapse\"";
	tmpHTML+="onclick=\"Calendar.cell_onclick(event.srcElement)\">";
	tmpHTML+="<TR height=20px style=\"background-image: url("+Global.theme_root+"/table_header.gif)\">";
	for (var i=0;i<=6;i++){
		tmpHTML+="<TD align=center>"+Calendar.days[i]+"</TD>";
	}
	tmpHTML+="</TR>";
	for(var i=0;i<=5;i++){
		tmpHTML+="<TR>";
		for(var j=0;j<=6;j++){
			tmpHTML+="<TD align=center></TD>";
		}
		tmpHTML+="</TR>";
	}
	tmpHTML+="</TABLE></TD></TR>";
	tmpHTML+="<TR class=\"footer\"><TD align=right>";
	tmpHTML+="<INPUT extra=button class=button type=button style=\"margin-right:1px;\" id=\"button_clearDate\" value=\"清空\" onclick=\"Calendar.clearDate_onclick()\">";	
	if(dataType=="yearmonth" || dataType=="time"){
		tmpHTML+="<INPUT extra=button class=button type=button id=\"button_cofirm\" style=\"width:146px;\" value='确   定' onclick=\"Calendar.confirm_onclick()\">";
	}
	else{
	tmpHTML+="<INPUT extra=button class=button type=button id=\"button_today\" value=\""+Const.Today+" "+_calendarControl.todayYear+"-"+(_calendarControl.todayMonth+1)+"-"+_calendarControl.todayDay+"\" onclick=\"Calendar.today_onclick()\">";
	}	
	tmpHTML+="</TD></TR></TABLE>";
	if (parent_element){		
		parent_element.innerHTML=tmpHTML;
		}
	else
		{document.body.innerHTML=tmpHTML;
		}
		Calendar.fourNextClick = true ;
// add by wq 
		if (Calendar.old_control.value!=""){
  		  _calendarControl.todayYear = Calendar.numberString(Calendar.old_control.value,1);
		  _calendarControl.todayMonth = Calendar.numberString(Calendar.old_control.value,2) - 1;
		  _calendarControl.todayDay = Calendar.numberString(Calendar.old_control.value,3);
		  tmp_calendarControl_hour =Calendar.numberString(Calendar.old_control.value,4);
		  tmp_calendarControl_minute =Calendar.numberString(Calendar.old_control.value,5);
		  tmp_calendarControl_second =Calendar.numberString(Calendar.old_control.value,6);	
		 
		}
		
        if (Calendar.new_control!=Calendar.old_control){
		    Calendar.new_control=Calendar.old_control;
		    Calendar.changeDate(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay);
			_calender_hour.value =  tmp_calendarControl_hour;
	    	_calender_minute.value =  tmp_calendarControl_minute;
		    _calender_second.value =  tmp_calendarControl_second;
		    Calendar.time_focus(_calender_hour);		   
		}
		else{
	      Calendar.changeDate(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay);
		  _calender_hour.value =  tmp_calendarControl_hour;
	      _calender_minute.value =  tmp_calendarControl_minute;
		  _calender_second.value =  tmp_calendarControl_second;		
		  Calendar.time_focus(_calender_hour);  				   
		}
		if(_calender_hour.value.length==1){
			 _calender_hour.value="0"+_calender_hour.value;
		  }
		if(_calender_minute.value.length==1){
	 		_calender_minute.value="0"+_calender_minute.value;
	      }
		if(_calender_second.value.length==1){
	 		_calender_second.value="0"+_calender_second.value;
		   }
		_calendarControl.hour = _calender_hour.value;
		_calendarControl.minute = _calender_minute.value;
		_calendarControl.second = _calender_second.value;
        
}